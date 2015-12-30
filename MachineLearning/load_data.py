import numpy as np

def read_training_data():
    """
    Reads training data and labels created by Labeler in RPCRunner.
    :return: tuple (X, y) where X is list of the 650 bits training images and y
    the corresponding labels {0, 1, 2}
    """
    data_file = open('../RPCRunner/data/data', 'rb')
    labels_file = open('../RPCRunner/data/labels', 'rb')
    labels = np.loadtxt(labels_file, dtype=np.int8)
    data = np.fromstring(np.array([data_file.read(650) for i in labels]),
            dtype=np.uint8)
    return np.reshape(data, (-1, 650)), labels

def read_input_data():
    """
    Reads temporary saved input data created by RPCRunner.
    :return: data as a list of 650 bits
    """
    temp_file = open('../RPCRunner/data/temp', 'rb')
    data = np.fromstring(temp_file.read(650), dtype=np.uint8)
    return np.reshape(data, (-1, 650))

def read_past_games(user):
    """
    Reads previous games by a given user. The outcome consists of a 2D array
    with users games on the first column and ai on the second.
    :return: past games in 2D array
    """
    with open('../RPCRunner/data/players/' + user) as file:
        games = [[int(sign) for sign in line.split()] for line in file]
    return np.array(games)
