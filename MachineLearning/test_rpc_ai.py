import rpc_ai as ai
import numpy as np

EMPTY_ARRAY = np.array([])
SCISSORS = [2,2]
SMALL_TEST = np.array([[2,2], [1,0], [2,2], [0,0], [2,2], [2,2], [2,2]])
FULL_TEST = np.array([[0,1], [2,1], [1,0], [2,0], [2,1], [1,0], [0,1], [2,1],
    [1,0], [0,1], [2,1], [1,0], [0,2], [0,1], [1,1], [1,2], [2,1], [2,2],
    [0,2], [2,1]])


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
    assert np.allclose(ai.next_signs_for_pair(SCISSORS, previous_games), [2,2,2])

def test_next_signs_for_pair_whith_some_mathing_pairs():
    previous_games = np.array([SCISSORS, [1,0], SCISSORS, [0,0], SCISSORS,
        SCISSORS, [2,1]])
    assert np.allclose(ai.next_signs_for_pair(SCISSORS, previous_games), [1,0,2,2])

def test_next_signs_for_pair_whith_some_mathing_pairs2():
    previous_games = np.array([[1,1], [1,0], [2,1], [1,1], [0,0], [2,0],
        [1,1], [1,1], [2,1], [1,1]])
    assert np.allclose(ai.next_signs_for_pair([1,1], previous_games), [1,0,1,2])


""" Test next_signs_for_single function """

def Test_next_signs_for_sign_when_no_previous_signs():
    assert ai.next_signs_for_single([2], EMPTY_ARRAY) == []

def test_next_signs_for_sign_when_no_mathing_pairs_found():
    previous_games = np.array([[1,1]])
    assert ai.next_signs_for_single([2], previous_games) == []

def test_next_signs_for_sign_when_only_one_mathing_sign_is_last_one():
    previous_games = np.array([SCISSORS])
    assert ai.next_signs_for_single([2], previous_games) == []

def test_next_signs_for_sign_when_one_mathing_sign_with_next_sign():
    previous_games = np.array([SCISSORS, [1,0]])
    assert ai.next_signs_for_single([2], previous_games) == [1]

def test_next_signs_for_sign_whith_all_mathing_signs():
    previous_games = np.array([SCISSORS, [2,0], [2,1], SCISSORS])
    assert np.allclose(ai.next_signs_for_single([2], previous_games), [2,2,2])

def test_next_signs_for_sign_whith_some_mathing_signs():
    previous_games = np.array([[2,1], [1,0], [2,1], [0,0], [2,0], [2,2], [2,1]])
    assert np.allclose(ai.next_signs_for_single([2], previous_games), [1,0,2,2])

def test_next_signs_for_sign_whith_some_mathing_signs2():
    previous_games = np.array([[1,1], [1,0], [2,1], [1,1], [0,0], [2,0],
        [1,2], [1,0], [2,1], [1,1]])
    assert np.allclose(ai.next_signs_for_single([1], previous_games), [1,2,0,1,2])


""" Test rps_frequencies function """

def test_frequencies_when_array_is_empty():
    assert np.allclose(ai.rps_frequencies(EMPTY_ARRAY), [0,0,0])

def test_frequencies_when_array_has_one_digit():
    signs = np.array([1])
    assert np.allclose(ai.rps_frequencies(signs), [0,1,0])

def test_frequencies_when_array_has_two_different_digits():
    signs = np.array([1,0])
    assert np.allclose(ai.rps_frequencies(signs), [1,1,0])

def test_frequencies_when_array_has_three_different_digits():
    signs = np.array([1,0,2])
    assert np.allclose(ai.rps_frequencies(signs), [1,1,1])

def test_frequencies_when_array_has_same_digits():
    signs = np.array([1,0,1,1])
    assert np.allclose(ai.rps_frequencies(signs), [1,3,0])


""" Test bayes_from_next_pairs function """

def test_next_pairs_with_no_previous_games():
    bfnp, bfnp_size = ai.bayes_from_next_pairs(EMPTY_ARRAY)
    assert bfnp_size == 0
    assert np.allclose(bfnp, [0,0,0])

def test_next_pairs_with_two_different_games():
    previous_games = np.array([[1,1], [2,0]])
    bfnp, bfnp_size = ai.bayes_from_next_pairs(previous_games)
    assert bfnp_size == 0
    assert np.allclose(bfnp, [0,0,0])

def test_next_pairs_with_all_different_games():
    previous_games = np.array([[1,1], [2,0], [2,1], [0,2], [1,0]])
    bfnp, bfnp_size = ai.bayes_from_next_pairs(previous_games)
    assert bfnp_size == 0
    assert np.allclose(bfnp, [0,0,0])

def test_next_pairs_with_one_repeated():
    previous_games = np.array([[1,1], [2,0], [2,1], [0,2], [1,1]])
    bfnp, bfnp_size = ai.bayes_from_next_pairs(previous_games)
    assert bfnp_size == 1
    assert np.allclose(bfnp, [-1,1,0])

def test_next_pairs_with_two_repeated():
    previous_games = np.array([[1,1], [2,0], [1,1], [0,2], [1,1]])
    bfnp, bfnp_size = ai.bayes_from_next_pairs(previous_games)
    assert bfnp_size == 2
    assert np.allclose(bfnp, [-0.5,0,0.5])

def test_next_pairs_with_small_test():
    bfnp, bfnp_size = ai.bayes_from_next_pairs(SMALL_TEST)
    assert bfnp_size == 4
    assert np.allclose(bfnp, [-0.25,0.25,0])

def test_next_pairs_with_full_test():
    bfnp, bfnp_size = ai.bayes_from_next_pairs(FULL_TEST)
    assert bfnp_size == 5
    assert np.allclose(bfnp, [0.6,0.2,-0.8])


""" Test bayes_from_next_singles function """

def test_next_singles_with_no_previous_games():
    bfnp, bfnp_size = ai.bayes_from_next_singles(EMPTY_ARRAY)
    assert bfnp_size == 0
    assert np.allclose(bfnp, [0,0,0])

def test_next_singles_with_two_different_games():
    previous_games = np.array([[1,1], [2,0]])
    bfnp, bfnp_size = ai.bayes_from_next_singles(previous_games)
    assert bfnp_size == 0
    assert np.allclose(bfnp, [0,0,0])

def test_next_singles_with_all_different_games():
    previous_games = np.array([[1,1], [2,0], [2,1], [0,2], [1,0]])
    bfnp, bfnp_size = ai.bayes_from_next_singles(previous_games)
    assert bfnp_size == 1
    assert np.allclose(bfnp, [-1,1,0])

def test_next_singles_with_one_repeated():
    previous_games = np.array([[1,1], [2,0], [2,1], [0,2], [1,1]])
    bfnp, bfnp_size = ai.bayes_from_next_singles(previous_games)
    assert bfnp_size == 1
    assert np.allclose(bfnp, [-1,1,0])

def test_next_singles_with_two_repeated():
    previous_games = np.array([[1,1], [2,0], [1,1], [0,2], [1,1]])
    bfnp, bfnp_size = ai.bayes_from_next_singles(previous_games)
    assert bfnp_size == 2
    assert np.allclose(bfnp, [-0.5,0,0.5])

def test_next_singles_with_small_test():
    bfnp, bfnp_size = ai.bayes_from_next_singles(SMALL_TEST)
    assert bfnp_size == 4
    assert np.allclose(bfnp, [-0.25,0.25,0])

def test_next_singles_with_full_test():
    bfnp, bfnp_size = ai.bayes_from_next_singles(FULL_TEST)
    assert bfnp_size == 7
    assert np.allclose(bfnp, [0.285714,0.142857,-0.42857])


""" Test select_next_move_against(user) function """

def test_next_move_returns_integer_between_0_2_when_no_past_games():
    for i in range(100):
        next_move, method = ai.select_next_move_against(EMPTY_ARRAY)
        assert next_move >= 0
        assert next_move <= 2

def test_next_move_returns_random_int_when_no_past_games():
    next_move, method = ai.select_next_move_against(EMPTY_ARRAY)
    assert method == "random"

def test_next_move_returns_random_int_when_to_few_past_games():
    previous_games = np.array([[1,1], [2,0], [2,1], [0,2], [1,0]])
    next_move, method = ai.select_next_move_against(previous_games)
    assert method == "random"

def test_next_move_with_small_test():
    next_move, method = ai.select_next_move_against(SMALL_TEST)
    assert next_move == 0
    assert method == "Bayes next pairs"

def test_next_move_with_full_test():
    next_move, method = ai.select_next_move_against(FULL_TEST)
    assert next_move == 2
    assert method == "Bayes next pairs"
