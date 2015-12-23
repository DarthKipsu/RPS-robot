import numpy as np

def read_training_data():
    data_file = open('../RPCRunner/data/data', 'rb')
    labels_file = open('../RPCRunner/data/labels', 'rb')
    labels = np.loadtxt(labels_file, dtype=np.int8)
    data = np.fromstring(np.array([data_file.read(650) for i in labels]), dtype=np.uint8)
    return (np.reshape(data, (-1, 650)), labels)

print(read_training_data())
