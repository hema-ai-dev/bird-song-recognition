import os
import sys
from efficientnet_pytorch import EfficientNet
import torch
from torchvision import transforms, datasets
from torch.utils.data import DataLoader
from torch import nn

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
IMG_DIR = os.path.join(SCRIPT_DIR, 'test_img')
CKPT_PATH = os.path.join(SCRIPT_DIR, 'models', 'checkpoint1.pth')

class_num = 100
bet_name = 'efficientnet-b3'
device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')

transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize([0.5, 0.5, 0.5], [0.5, 0.5, 0.5])
])

if not os.path.isdir(IMG_DIR) or len(os.listdir(IMG_DIR)) == 0:
    print('PREDICT_ERROR: 谱图目录不存在或为空:', IMG_DIR, file=sys.stderr)
    sys.exit(2)

if not os.path.exists(CKPT_PATH):
    print('PREDICT_ERROR: 模型权重不存在:', CKPT_PATH, file=sys.stderr)
    sys.exit(2)

try:
    dataset_test = datasets.ImageFolder(root=IMG_DIR, transform=transform)
    test_loader = DataLoader(dataset=dataset_test, batch_size=16, shuffle=False, drop_last=False)

    model_ft = EfficientNet.from_name(bet_name)
    num_ftrs = model_ft._fc.in_features
    model_ft._fc = nn.Linear(num_ftrs, class_num)

    checkpoint = torch.load(CKPT_PATH, map_location=device)
    model = model_ft.to(device)
    model.load_state_dict(checkpoint['state_dict'])
    model.eval()
except Exception as e:
    print('PREDICT_ERROR: 模型加载失败:', repr(e), file=sys.stderr)
    sys.exit(2)

test_answer_label = []
test_answer_prob = []
filenames = [i.replace('test_img/', '').replace('test_img\\', '') for i, j in dataset_test.imgs]

for inputs, labels in test_loader:
    inputs = inputs.to(device)
    outputs = model(inputs)
    prob, preds = torch.max(outputs, 1)
    test_answer_prob.extend(list(prob.data.cpu().numpy()))
    test_answer_label.extend(list(preds.data.cpu().numpy()))

class_indices = {'B000': 0, 'B001': 1, 'B002': 2, 'B003': 3, 'B004': 4, 'B005': 5, 'B006': 6, 'B007': 7, 'B008': 8,
                 'B009': 9, 'B010': 10, 'B011': 11, 'B012': 12, 'B013': 13, 'B014': 14, 'B015': 15, 'B016': 16,
                 'B017': 17, 'B018': 18, 'B019': 19, 'B020': 20, 'B021': 21, 'B022': 22, 'B023': 23, 'B024': 24,
                 'B025': 25, 'B026': 26, 'B027': 27, 'B028': 28, 'B029': 29, 'B030': 30, 'B031': 31, 'B032': 32,
                 'B033': 33, 'B034': 34, 'B035': 35, 'B036': 36, 'B037': 37, 'B038': 38, 'B039': 39, 'B040': 40,
                 'B041': 41, 'B042': 42, 'B043': 43, 'B044': 44, 'B045': 45, 'B046': 46, 'B047': 47, 'B048': 48,
                 'B049': 49, 'B050': 50, 'B051': 51, 'B052': 52, 'B053': 53, 'B054': 54, 'B055': 55, 'B056': 56,
                 'B057': 57, 'B058': 58, 'B059': 59, 'B060': 60, 'B061': 61, 'B062': 62, 'B063': 63, 'B064': 64,
                 'B065': 65, 'B066': 66, 'B067': 67, 'B068': 68, 'B069': 69, 'B070': 70, 'B071': 71, 'B072': 72,
                 'B073': 73, 'B074': 74, 'B075': 75, 'B076': 76, 'B077': 77, 'B078': 78, 'B079': 79, 'B080': 80,
                 'B081': 81, 'B082': 82, 'B083': 83, 'B084': 84, 'B085': 85, 'B086': 86, 'B087': 87, 'B088': 88,
                 'B089': 89, 'B090': 90, 'B091': 91, 'B092': 92, 'B093': 93, 'B094': 94, 'B095': 95, 'B096': 96,
                 'B097': 97, 'B098': 98, 'B099': 99}

account_dict = {}
probility_dict = {}
for p, pro, file_name in zip(test_answer_label, test_answer_prob, filenames):
    test_wav_name = file_name[:15]
    temp_vote_list = account_dict.get(test_wav_name)
    temp_prob_list = probility_dict.get(test_wav_name)
    if not temp_vote_list:
        account_dict[test_wav_name] = [list(class_indices.keys())[p]]
        probility_dict[test_wav_name] = [pro]
    else:
        temp_vote_list.append(list(class_indices.keys())[p])
        temp_prob_list.append(pro)

answer_list = []
for test_wav_name in account_dict:
    account_list = account_dict[test_wav_name]
    maxlabel = max(account_list, key=account_list.count)
    answer_list.append((test_wav_name, maxlabel))

if not answer_list:
    print('PREDICT_EMPTY')
    sys.exit(3)
else:
    print(answer_list[0][1])
