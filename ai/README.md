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

完整训练流水线（3 个脚本，按顺序执行）：

```bash
cd ai/training
# ① wav 切分：15s 窗 / 5s 步长滑窗（ImageFolder 结构，类别=鸟种文件夹）
python split_wav_in_new_idea.py --data <原始wav根目录> --out train_split_data_15_5s
# ② 切分产物 -> Mel 频谱图（每段取前 5s，生成 train_split_img_new_5s）
python prepare_image_from_wav_new_func.py --data train_split_data_15_5s --out train_split_img_new_5s --signal 5
# ③ 训练 EfficientNet-B3（默认读 train_split_img_new_5s，也可传入参指定）
python train_with_torch.py
```

训练超参（`train_with_torch.py` 内）：batch 48 / epoch 100 / Adam lr 0.001（每 10 epoch ×0.8）/ 交叉熵 / 验证集选最优权重。训练结束后打印 `class_to_idx`（文件夹名→标签），用于生成推理侧的 B000-B099 映射。

| 文件 | 说明 |
|---|---|
| `split_wav_in_new_idea.py` | 训练集切分（15s/5s 滑窗） |
| `prepare_image_from_wav_new_func.py` | 切分 wav → 224 维 Mel 谱图 |
| `train_with_torch.py` | EfficientNet-B3 训练入口（超参、训练/验证循环、保存最优 checkpoint） |
| `test.py` | 原始小实验（pydub 读取示例，可忽略） |

数据集不在仓库内：原训练数据 `BirdsData-BirdsSong-2s-30min-20spec`（20 类、每类数百个 2s 片段，共 4.4G）与 checkpoint1.pth 的来源训练集（100 类）均需自行准备，路径按上面参数传入即可。
