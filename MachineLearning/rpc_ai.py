#!/usr/bin/env python
import load_data as reader
import numpy as np
from random import randint
import sys

SIGN_WEIGHTS = np.array([[0,1,-1], [-1,0,1], [1,-1,0]])

def next_signs_for_pair(pair, past_games):
    if (past_games.size == 0):
        return []
    last_i = len(past_games) - 1
    next_pair_indexes = np.array(np.where((past_games[:last_i,0] == pair[0])
        & (past_games[:last_i,1] == pair[1]))[0])
    if (next_pair_indexes.size == 0):
        return []
    next_pair_indexes += 1
    return past_games[next_pair_indexes][:,0]

def rps_frequencies(signs):
    zeros = np.zeros(3)
    if (len(signs) == 0):
        return zeros
    frequencies = np.bincount(signs)
    return np.concatenate((frequencies, zeros))[:3]

def bayes_from_next_pairs(past_games):
    if (len(past_games) == 0):
        return np.zeros(3), 0
    last_game = past_games[len(past_games) - 1]
    similar_game_next_moves = next_signs_for_pair(last_game, past_games)
    next_move_freq = rps_frequencies(similar_game_next_moves)
    freq_sum = np.sum(next_move_freq)
    if (freq_sum == 0):
        return np.zeros(3), 0
    next_move_freq /= freq_sum
    return np.dot(SIGN_WEIGHTS, next_move_freq), freq_sum

def select_next_move_against(past_games):
    """
    First version of machine learning for selecting what to play next. Chooses
    by looking at what the user has most frequently played after the same kind
    of choise as made with last game and chooses the bayes optimal from them.

    If no sufficient data is available, prints a random digit between 0 to 2
    """
    bfnp, bfnp_size = bayes_from_next_pairs(past_games)
    if (bfnp_size > 0):
        return np.argmin(bfnp), "Bayes next pairs"
    else:
        return randint(0,2), "random"

if __name__ == "__main__":
    past_games = reader.read_past_games(sys.argv[1])
    next_move, method = select_next_move_against(past_games)
    print(next_move)
    print(method)
