#!/usr/bin/env python
import load_data as reader
import numpy as np
import perceptron as ml
import sys

from sklearn.metrics import confusion_matrix

def all_vs_all(training_data, training_labels, test_data):
    """
    An initial version of a prediction calculation based on all-vs-all paragdim
    and a perceptron algorithm.
    TODO: Seperate calculating weights from training data and making the
    prediction to be able to make several prediction in row more time efficiently
    :return: the prediction for all the data we are testing with
    """
    n = len(training_data)
    predictions = np.array([np.empty(n)])
    votes = np.zeros((10, len(test_data)))
    for i in range(3):
        for j in range(i+1, 3):
            data = training_data[(training_labels == i) | (training_labels == j)]
            j_labels = training_labels[(training_labels == i) | (training_labels == j)]
            labels = np.ones(len(j_labels), np.int32)
            labels[j_labels == j] = -1

            weights = ml.perceptron(data, labels)
            prediction = test_data.dot(weights)
            
            i_votes = np.zeros(len(test_data))
            i_votes[prediction > 0] = 1
            votes[i] += i_votes

            j_votes = np.zeros(len(test_data))
            j_votes[prediction <= 0] = 1
            votes[j] += j_votes

    return np.argmax(votes, axis=0)

def test():
    X, y = reader.read_training_data()

    n = int(2 * len(X) / 3)
    training_data = X[0:n]
    training_labels = y[0:n]
    
    test_data = X[n:]
    test_labels = y[n:]
    
    result = confusion_matrix(test_labels, all_vs_all(training_data, training_labels, test_data))

    print("Rocks:", len(X[y==0]))
    print("Papers:", len(X[y==1]))
    print("Scissors:", len(X[y==2]))
    print(result)
    print("Error rate", (1 - np.sum(np.diagonal(result)) / float(len(test_data))))

def main():
    X, y = reader.read_training_data()
    test_data = reader.read_input_data()
    result = all_vs_all(X, y, test_data)
    print(result[0])

if __name__ == "__main__":
    main()
