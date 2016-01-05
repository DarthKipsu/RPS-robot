import rpc_ai as ai
import numpy as np

EMPTY_ARRAY = np.array([])
SCISSORS = [2,2]


""" Test bayes_from_next_pairs function """

def test_next_pairs_with_no_previous_games():
    assert ai.bayes_from_next_pairs(EMPTY_ARRAY) == -1

def test_next_pairs_with_only_few_previous_games():
    previous_games = np.array([[1,1], [2,0]])
    assert ai.bayes_from_next_pairs(previous_games) == -1


""" Test next_signs_for_pair function """

def test_next_signs_for_pair_when_no_previous_pairs():
    assert ai.next_signs_for_pair(SCISSORS, EMPTY_ARRAY) == []

def test_next_signs_for_pair_when_no_mathing_pairs_found():
    previous_games = np.array([[1,1]])
    assert ai.next_signs_for_pair(SCISSORS, previous_games) == []

def test_next_signs_for_pair_when_only_one_mathing_pair_is_last_one():
    previous_games = np.array([SCISSORS])
    assert ai.next_signs_for_pair(SCISSORS, previous_games) == []

def test_next_signs_for_pair_when_one_mathing_pair_with_next_pair():
    previous_games = np.array([SCISSORS, [1,0]])
    assert ai.next_signs_for_pair(SCISSORS, previous_games) == [1]

def test_next_signs_for_pair_whith_all_mathing_pairs():
    previous_games = np.array([SCISSORS, SCISSORS, SCISSORS, SCISSORS])
    assert (ai.next_signs_for_pair(SCISSORS, previous_games) == [2,2,2]).all()

def test_next_signs_for_pair_whith_some_mathing_pairs():
    previous_games = np.array([SCISSORS, [1,0], SCISSORS, [0,0], SCISSORS,
        SCISSORS, [2,1]])
    assert (ai.next_signs_for_pair(SCISSORS, previous_games) == [1,0,2,2]).all()

def test_next_signs_for_pair_whith_some_mathing_pairs2():
    previous_games = np.array([[1,1], [1,0], [2,1], [1,1], [0,0], [2,0],
        [1,1], [1,1], [2,1], [1,1]])
    assert (ai.next_signs_for_pair([1,1], previous_games) == [1,0,1,2]).all()
