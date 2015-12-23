import numpy as np

epoch_count = 10

def perceptron(data, label):
    """
    A perceptron algorithm for training a multiclass linear classifier, using
    the pocket modification to be able to use with data not linearry seperable.
    :input: training data and corresponding labels where + sign data items have
    label +1 and - sign data items label -1
    :return: weight vector w defining pred_y = sign(w * x)
    """
    w = np.zeros(len(data[0]), np.int32)
    pocket = np.copy(w)
    pocket_count = 0
    for epoch in range(epoch_count):
        converged = True
        correct_count = 0
        for i in range(len(data)):
            prediction = np.sign(w.dot(data[i]))
            if prediction != label[i]:
                if correct_count > pocket_count:
                    pocket = np.copy(w)
                    pocket_count = correct_count
                correct_count = 0
                w += label[i] * data[i]
                converged = False
            else:
                correct_count += 1
        if converged:
            return w
    if pocket_count == 0:
        print('Something not right: 0 pockets')
        return w
    return pocket
