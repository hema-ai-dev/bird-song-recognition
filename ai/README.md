# AI 鸟鸣识别链（ai/）

本项目 AI 识别核心：wav → 15s 滑窗切片 → Mel 谱图 → EfficientNet-B3 分类 → 16 图投票 → 输出鸟种编码（B000-B099，100 类）。

## 文件说明

| 文件/目录 | 说明 |
|---|---|
| `handle_wav.py` | 预处理：切分 wav → 生成 Mel 谱图（16 张补齐）到 `test_img/type/` |
| `predict_with_torch.py` | 推理：加载 `models/checkpoint1.pth`，EfficientNet-B3 投票输出鸟种编码 |
| `requirements.txt` | Python 依赖清单（torch/torchvision/efficientnet_pytorch/librosa/pydub/Pillow） |
| `models/checkpoint1.pth` | 训练好的权重（131MB，100 类，**GitHub Releases 分发**：[下载](https://github.com/hema-ai-dev/bird-song-recognition/releases/download/v1.0.0/checkpoint1.pth)） |
| `python/` | 绿色免安装 Python 3.10 + torch cu121（Windows x64，随本机分发；不入 git） |
| `ffmpeg/` | 音频切分依赖（pydub 调用；不入 git） |

## 直接运行（Windows，用自带的绿色环境）

```bat
cd ai
set FFMPEG_PATH=%CD%\ffmpeg\bin\ffmpeg.exe
python\python.exe handle_wav.py 你的录音.wav
python\python.exe predict_with_torch.py
```

输出最后一行为鸟种编码（如 `B000`），后端 `KeshuController` 据此回查数据库。

## 自行搭建环境（Linux / macOS / 其他平台）

```bash
pip install -r requirements.txt        # torch 建议按官网选择 cuda 版本
mkdir -p models
# 下载权重（GitHub Releases）：
curl -L -o models/checkpoint1.pth https://github.com/hema-ai-dev/bird-song-recognition/releases/download/v1.0.0/checkpoint1.pth
export FFMPEG_PATH=/usr/bin/ffmpeg     # 或安装 ffmpeg 并加入 PATH
python handle_wav.py 你的录音.wav
python predict_with_torch.py
```

> 绿色 `python/` 目录是 Windows 版，Linux 容器内不可用；Docker 场景需按上面方式在镜像里自行装依赖。

## 训练（training/）

完整训练流水线（3 个脚本，按顺序执行）。

> **训练/验证/测试分离（重要）**：切分脚本默认按「原始录音」分组划分
> train / val / test（比例 70%/15%/15%，随机种子 42）。同一段录音的所有滑窗片段
> 只会进入同一个集合，避免相邻片段泄漏到不同集合导致验证准确率虚高。
> 切分后再分别对 train、val 生成谱图。

```bash
cd ai/training
# ① wav 切分：15s 窗 / 5s 步长滑窗，按原始录音分组输出 train/val/test 三个子目录
python split_wav_in_new_idea.py --data <原始wav根目录> --out train_split_data_15_5s
# ② 分别对训练集、验证集生成 Mel 频谱图（每段取前 5s）
python prepare_image_from_wav_new_func.py --data train_split_data_15_5s/train --out train_split_img_new_5s/train --signal 5
python prepare_image_from_wav_new_func.py --data train_split_data_15_5s/val --out train_split_img_new_5s/val --signal 5
# ③ 训练 EfficientNet-B3（读 train_split_img_new_5s 下的 train/val 子目录）
python train_with_torch.py train_split_img_new_5s
# 可选：测试集单独评估（另起脚本，不参与训练循环）
python prepare_image_from_wav_new_func.py --data train_split_data_15_5s/test --out train_split_img_new_5s/test --signal 5
```

`train_with_torch.py` 强制要求训练集与验证集来自不同目录（根目录含 `train/`、`val/`
子目录，或 `--data=... --val=...` 显式指定），并校验两个集合的类别映射一致；
读取同一个目录的旧行为已移除。验证集 DataLoader 不 shuffle、不 drop_last，
保证验证指标计算完整。

训练超参（`train_with_torch.py` 内）：batch 48 / epoch 100 / Adam lr 0.001（每 10 epoch ×0.8）/ 交叉熵 / 验证集选最优权重 / 全局随机种子 42。训练结束后打印 `class_to_idx`（文件夹名→标签），用于生成推理侧的 B000-B099 映射。

| 文件 | 说明 |
|---|---|
| `split_wav_in_new_idea.py` | 训练集切分（15s/5s 滑窗，默认按原始录音分组划分 train/val/test） |
| `prepare_image_from_wav_new_func.py` | 切分 wav → 224 维 Mel 谱图 |
| `train_with_torch.py` | EfficientNet-B3 训练入口（强制训练/验证分离、超参、训练循环、保存最优 checkpoint） |
| `test.py` | 原始小实验（pydub 读取示例，可忽略） |

数据集不在仓库内：原训练数据 `BirdsData-BirdsSong-2s-30min-20spec`（20 类、每类数百个 2s 片段，共 4.4G）与 checkpoint1.pth 的来源训练集（100 类）均需自行准备，路径按上面参数传入即可。
