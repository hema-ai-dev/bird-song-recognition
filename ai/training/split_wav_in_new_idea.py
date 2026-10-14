# 将训练集中的 wav 文件以 15s 为单位切分子文件，每次递进 5s
# 用法: python split_wav_in_new_idea.py --data <原始wav根目录> [--out 输出目录] [--split 15000] [--step 5000]
# 原始数据目录要求: data/<类别名>/*.wav  （类别名 = 鸟种文件夹，ImageFolder 用）
import os
import sys
import argparse
from pydub import AudioSegment
from tqdm import tqdm

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))

# ffmpeg：优先上级目录 ffmpeg/bin/ffmpeg.exe，其次环境变量 FFMPEG_PATH
_ffmpeg_local = os.path.join(os.path.dirname(SCRIPT_DIR), 'ffmpeg', 'bin', 'ffmpeg.exe')
if os.path.exists(_ffmpeg_local):
    AudioSegment.converter = _ffmpeg_local
elif os.environ.get('FFMPEG_PATH'):
    AudioSegment.converter = os.environ['FFMPEG_PATH']


# 将数据根目录解析为 {wav绝对路径: [类别名, 文件名]} 键值对
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


# 将 wav 拆分为多个 15s 子文件；不足 15s 不动；剩余不足 15s 从后往前取
def split_wav_file(wav_path, label, base_path, split_duration, step_duration):
    cat, name = label[0], label[1]
    new_cat_path = os.path.join(base_path, cat)
    os.makedirs(new_cat_path, exist_ok=True)
    sound = AudioSegment.from_wav(wav_path)
    duration = sound.duration_seconds * 1000  # 音频时长（ms）

    if duration > split_duration:
        begin = 0
        for index in range(200):
            end = begin + split_duration
            if end < duration:
                new_file_path = os.path.join(new_cat_path, '{}_{}'.format(index + 1, name))
                sound[begin:end].export(new_file_path, format='wav')
                begin += step_duration
            else:
                new_file_path = os.path.join(new_cat_path, '{}_{}'.format(index + 1, name))
                sound[-split_duration:].export(new_file_path, format='wav')
                break
    else:
        new_file_path = os.path.join(new_cat_path, '1_{}'.format(name))
        sound.export(new_file_path, format='wav')


def run_split(path_dict, base_path, split_duration, step_duration):
    for wav_path in tqdm(path_dict, desc='切分 wav'):
        split_wav_file(wav_path, path_dict[wav_path], base_path, split_duration, step_duration)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='训练集 wav 切分（15s 窗 / 5s 步长）')
    parser.add_argument('--data', required=True, help='原始 wav 根目录（其下每个子目录为一个鸟种类别）')
    parser.add_argument('--out', default=os.path.join(SCRIPT_DIR, 'train_split_data_15_5s'), help='切分结果输出目录')
    parser.add_argument('--split', type=int, default=15000, help='切分窗长 ms')
    parser.add_argument('--step', type=int, default=5000, help='滑动步长 ms')
    args = parser.parse_args()

    voice_path_dict = convert_folder_to_pathdict(args.data)
    print('共发现 {} 个 wav 文件'.format(len(voice_path_dict)))
    run_split(voice_path_dict, args.out, args.split, args.step)
    print('SPLIT_DONE -> {}'.format(args.out))
