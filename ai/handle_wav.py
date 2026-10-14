import os
import sys
import shutil
import librosa
import numpy as np
from PIL import Image
from pydub import AudioSegment

# ============ 路径全部基于脚本自身目录 + 命令行入参 ============
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))

# ffmpeg：优先同目录 ffmpeg/bin/ffmpeg.exe，其次环境变量 FFMPEG_PATH
_ffmpeg_local = os.path.join(SCRIPT_DIR, 'ffmpeg', 'bin', 'ffmpeg.exe')
if os.path.exists(_ffmpeg_local):
    AudioSegment.converter = _ffmpeg_local
elif os.environ.get('FFMPEG_PATH'):
    AudioSegment.converter = os.environ['FFMPEG_PATH']

step_duration = 5000
split_duration = 15000
SIGNAL_LENGTH = 5
SAMPLE_RATE = 32000
SPEC_SHAPE = (224, 224)
FMIN = 80
FMAX = 16000


def convert_wav_mlpng(wav_path, dst_path):
    signal, sampling_rate = librosa.load(path=wav_path, duration=SIGNAL_LENGTH, sr=SAMPLE_RATE)
    hop_length = int(SIGNAL_LENGTH * SAMPLE_RATE / (SPEC_SHAPE[1] - 1))
    mel_spec = librosa.feature.melspectrogram(y=signal, sr=SAMPLE_RATE, n_fft=2048,
                                              hop_length=hop_length, n_mels=SPEC_SHAPE[0],
                                              fmin=FMIN, fmax=FMAX)
    mel_spec = librosa.power_to_db(mel_spec, ref=np.max)
    mel_spec -= mel_spec.min()
    mel_spec /= mel_spec.max()
    im = Image.fromarray(mel_spec * 255.0).convert('L')
    im.save(dst_path)


def split_wav_file(wav_path, base_path):
    if os.path.exists(base_path):
        shutil.rmtree(base_path)
    os.makedirs(base_path)
    sound = AudioSegment.from_wav(wav_path)
    duration = sound.duration_seconds * 1000
    if duration > split_duration:
        begin = 0
        for index in range(200):
            end = begin + split_duration
            if end < duration:
                new_file_path = os.path.join(base_path, '{}.wav'.format(index + 1))
                cut_wav = sound[begin:end]
                cut_wav.export(new_file_path, format='wav')
                begin += step_duration
            else:
                new_file_path = os.path.join(base_path, '{}.wav'.format(index + 1))
                cut_wav = sound[-split_duration:]
                cut_wav.export(new_file_path, format='wav')
                break
    else:
        new_file_path = os.path.join(base_path, '1.wav')
        sound.export(new_file_path, format='wav')


def copy_files_to_reach_target_count(folder_path, target_count=16):
    files = [f for f in os.listdir(folder_path) if os.path.isfile(os.path.join(folder_path, f))]
    if len(files) >= target_count:
        return
    num_files_needed = target_count - len(files)
    num_existing_files = len(files)
    for i in range(num_files_needed):
        src_file = os.path.join(folder_path, files[i % num_existing_files])
        base_name, extension = os.path.splitext(files[i % num_existing_files])
        new_file_name = '{0}_copy{1}{2}'.format(base_name, i + 1, extension)
        dst_file = os.path.join(folder_path, new_file_name)
        shutil.copy2(src_file, dst_file)


if __name__ == '__main__':
    wav_file_path = sys.argv[1]
    test_voice_path = os.path.join(SCRIPT_DIR, 'test_voice', 'type')
    test_img_path = os.path.join(SCRIPT_DIR, 'test_img', 'type')

    for p in (test_voice_path, test_img_path):
        if os.path.exists(p):
            shutil.rmtree(p)
        os.makedirs(p)

    split_wav_file(wav_file_path, test_voice_path)
    for wav_name in os.listdir(test_voice_path):
        wav_full = os.path.join(test_voice_path, wav_name)
        img_name = wav_name.partition('.')[0] + '.png'
        convert_wav_mlpng(wav_full, os.path.join(test_img_path, img_name))

    copy_files_to_reach_target_count(test_img_path)
    print('PREPROCESS_DONE', len(os.listdir(test_img_path)))