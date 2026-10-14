# 读取 wav 切分产物，逐个转为高频 Mel 频谱图（灰度 PNG）
# 用法: python prepare_image_from_wav_new_func.py --data <切分后的wav目录> [--out 输出谱图目录] [--signal 5]
# 参考 https://blog.csdn.net/hhhhhhhhhhwwwwwwwwww/article/details/116031884 的方法生成频谱图
import os
import sys
import argparse
import librosa
import numpy as np
from PIL import Image
import tqdm

SAMPLE_RATE = 32000
SPEC_SHAPE = (224, 224)  # height x width
FMIN = 80
FMAX = 16000


# 将 wav 目录解析为 {wav绝对路径: [类别名, 文件名]} 键值对
def convert_folder_to_pathdict(data_path):
    path_dict = {}
    for cat_dir in os.listdir(data_path):
        cat_path = os.path.join(data_path, cat_dir)
        if not os.path.isdir(cat_path):
            continue
        for sound_name in os.listdir(cat_path):
            if not sound_name.lower().endswith('.wav'):
                continue
            path_dict[os.path.join(cat_path, sound_name)] = [cat_dir, sound_name]
    return path_dict


# 读取 wav 音频文件，转换为 Mel 频谱图并保存为灰度 png
def convert_wav_mlpng(wav_path, dst_path, signal_length):
    signal, sampling_rate = librosa.load(path=wav_path, duration=signal_length, sr=SAMPLE_RATE)
    hop_length = int(signal_length * SAMPLE_RATE / (SPEC_SHAPE[1] - 1))
    mel_spec = librosa.feature.melspectrogram(y=signal,
                                              sr=SAMPLE_RATE,
                                              n_fft=2048,
                                              hop_length=hop_length,
                                              n_mels=SPEC_SHAPE[0],
                                              fmin=FMIN,
                                              fmax=FMAX)
    mel_spec = librosa.power_to_db(mel_spec, ref=np.max)
    # 归一化
    mel_spec -= mel_spec.min()
    mel_spec /= mel_spec.max()
    im = Image.fromarray(mel_spec * 255.0).convert('L')
    im.save(dst_path)


# 逐个 wav 生成谱图，按类别放入 输出目录/<类别>/ 下（ImageFolder 结构）
def convert_pathdict_to_mlpng(path_dict, dst_root, signal_length):
    os.makedirs(dst_root, exist_ok=True)
    for wav_path in tqdm.tqdm(path_dict, desc='生成谱图'):
        label = path_dict[wav_path][0]
        cat_path = os.path.join(dst_root, label)
        os.makedirs(cat_path, exist_ok=True)
        full_dst_path = os.path.join(cat_path, path_dict[wav_path][1].replace('.wav', '.png'))
        if not os.path.exists(full_dst_path):
            convert_wav_mlpng(wav_path=wav_path, dst_path=full_dst_path, signal_length=signal_length)
        else:
            print(full_dst_path, '已存在，跳过。')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='wav 切分产物 -> Mel 频谱图')
    parser.add_argument('--data', required=True, help='切分后的 wav 目录（其下每个子目录为一个鸟种类别）')
    parser.add_argument('--out', required=True, help='谱图输出目录（ImageFolder 结构）')
    parser.add_argument('--signal', type=int, default=5, help='每个谱图取前几秒音频（5 对应 train_split_img_new_5s）')
    args = parser.parse_args()

    path_dict = convert_folder_to_pathdict(args.data)
    print('共发现 {} 个 wav 片段'.format(len(path_dict)))
    convert_pathdict_to_mlpng(path_dict, args.out, args.signal)
    print('IMAGE_DONE -> {}'.format(args.out))
