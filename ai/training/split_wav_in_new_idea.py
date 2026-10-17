# 将训练集中的 wav 文件以 15s 为单位切分子文件，每次递进 5s
# 用法:
#   python split_wav_in_new_idea.py --data <原始wav根目录> [--out 输出目录] [--split 15000] [--step 5000]
# 原始数据目录要求: data/<类别名>/*.wav  （类别名 = 鸟种文件夹，ImageFolder 用）
#
# 默认按"原始录音"分组划分 train/val/test（同一段录音的所有片段只进入同一个集合，
# 避免相邻滑窗片段泄漏到不同集合导致验证指标虚高）：
#   --train-ratio 0.7 --val-ratio 0.15 --test-ratio 0.15 --seed 42
# 输出结构: <out>/train/<类别>/<序号>_<原文件名>.wav（val/test 同理）
# 旧行为（不分组，全部输出到 <out>/<类别>/）可用 --no-split 恢复。
import os
import sys
import argparse
import random
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


# 按"原始录音"把每个类别的 wav 文件分入 train/val/test
def assign_split(wav_paths, train_ratio, val_ratio, seed):
    random.seed(seed)
    paths = sorted(wav_paths)  # 排序保证可复现
    random.shuffle(paths)
    n = len(paths)
    n_train = int(round(n * train_ratio))
    n_val = int(round(n * val_ratio))
    if n_train + n_val > n:  # 比例四舍五入溢出时收紧
        n_val = max(0, n - n_train)
    train_set = set(paths[:n_train])
    val_set = set(paths[n_train:n_train + n_val])
    test_set = set(paths[n_train + n_val:])
    return train_set, val_set, test_set


def run_split(path_dict, base_path, split_duration, step_duration,
              split_mode=True, train_ratio=0.7, val_ratio=0.15, seed=42):
    if not split_mode:
        for wav_path in tqdm(path_dict, desc='切分 wav'):
            split_wav_file(wav_path, path_dict[wav_path], base_path, split_duration, step_duration)
        return

    # 按类别分组后，对每个类别内的"录音文件"划分
    by_cat = {}
    for wav_path, label in path_dict.items():
        by_cat.setdefault(label[0], []).append(wav_path)

    split_names = {'train': train_ratio, 'val': val_ratio, 'test': 1.0 - train_ratio - val_ratio}
    stats = {'train': 0, 'val': 0, 'test': 0}
    for cat, wav_paths in tqdm(by_cat.items(), desc='划分并切分'):
        train_set, val_set, _ = assign_split(wav_paths, train_ratio, val_ratio, seed)
        for wav_path in wav_paths:
            if wav_path in train_set:
                split_name = 'train'
            elif wav_path in val_set:
                split_name = 'val'
            else:
                split_name = 'test'
            stats[split_name] += 1
            split_wav_file(wav_path, path_dict[wav_path],
                           os.path.join(base_path, split_name),
                           split_duration, step_duration)
    print('录音分组: train={} val={} test={}（按原始录音文件计数）'.format(
        stats['train'], stats['val'], stats['test']))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='训练集 wav 切分（15s 窗 / 5s 步长，默认按录音分组划分 train/val/test）')
    parser.add_argument('--data', required=True, help='原始 wav 根目录（其下每个子目录为一个鸟种类别）')
    parser.add_argument('--out', default=os.path.join(SCRIPT_DIR, 'train_split_data_15_5s'), help='切分结果输出目录')
    parser.add_argument('--split', type=int, default=15000, help='切分窗长 ms')
    parser.add_argument('--step', type=int, default=5000, help='滑动步长 ms')
    parser.add_argument('--train-ratio', type=float, default=0.7, help='训练集录音占比')
    parser.add_argument('--val-ratio', type=float, default=0.15, help='验证集录音占比')
    parser.add_argument('--seed', type=int, default=42, help='分组随机种子（保证可复现）')
    parser.add_argument('--no-split', action='store_true', help='不分组，恢复旧行为（全部输出到 <out>/<类别>/）')
    args = parser.parse_args()

    if args.train_ratio + args.val_ratio >= 1.0:
        print('错误: --train-ratio 与 --val-ratio 之和必须小于 1（留出测试集）')
        sys.exit(1)

    voice_path_dict = convert_folder_to_pathdict(args.data)
    print('共发现 {} 个 wav 文件'.format(len(voice_path_dict)))
    run_split(voice_path_dict, args.out, args.split, args.step,
              split_mode=not args.no_split,
              train_ratio=args.train_ratio, val_ratio=args.val_ratio, seed=args.seed)
    print('SPLIT_DONE -> {}'.format(args.out))
