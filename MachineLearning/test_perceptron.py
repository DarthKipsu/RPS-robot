import perceptron as p
import numpy as np

def test_perceptron_empty():
    data = np.array([])
    labels = np.array([])
    assert np.allclose(p.perceptron(data, labels), [])

def test_perceptron_2d():
    data = np.array([[1,1],[1,1],[0,0],[2,0],[0,0],[1,1],[0,0],[0,2]])
    labels = np.array([1,1,-1,1,-1,1,-1,1])
    assert np.allclose(p.perceptron(data, labels), [1,1])

def test_perceptron_3d():
    data = np.array([[1,1,0],[1,1,0],[0,0,0],[2,0,0],[0,0,0],[1,1,0],[0,0,0],[0,2,0]])
    labels = np.array([1,1,-1,1,-1,1,-1,1])
    assert np.allclose(p.perceptron(data, labels), [1,1,0])

def test_perceptron_3d_random():
    data = np.array([[2,4,1],[5,4,6],[1,2,0],[6,2,1],[1,-1,4],[4,1,2],[1,3,2],[4,2,6]])
    labels = np.array([1,1,-1,1,-1,1,-1,1])
    assert np.allclose(p.perceptron(data, labels), [6,-4,-3])

def test_perceptron_4d_random():
    data = np.array([[2,4,1,-9],[5,4,6,-3],[1,2,0,9],[6,2,1,-6],[1,-1,4,6],[4,1,2,-3],[1,3,2,8],[4,2,6,-5]])
    labels = np.array([1,1,-1,1,-1,1,-1,1])
    assert np.allclose(p.perceptron(data, labels), [2,4,1,-9])

def test_perceptron_4d_not_separable():
    data = np.array([[2,4,1,-9],[0,1,0,9],[1,2,0,9],[6,2,1,-6],[1,-1,4,6],[4,1,2,-3],[1,3,2,8],[4,2,6,-5]])
    labels = np.array([1,1,-1,1,-1,1,-1,1])
    assert np.allclose(p.perceptron(data, labels), [1,3,1,-9])
