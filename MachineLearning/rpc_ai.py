#!/usr/bin/env python
import load_data as reader
import numpy as np
from random import randint
import sys

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

def bayes_from_next_pairs(past_games):
    if (past_games.size == 0):
        return -1
    last_game = past_games[len(past_games) - 1]
    similar_game_next_moves = next_signs_for_pair(last_game, past_games)
    next_move_freq = np.bincount(similar_game_next_moves)
    if (next_move_freq.size < 3):
        return -1
    freq_sum = np.sum(next_move_freq)
    most_likely_next = np.argmax(next_move_freq)
    user_percentages = np.array([next_move_freq[i] / freq_sum for i in range(3)])
    weights = np.array([[0,1,-1], [-1,0,1], [1,-1,0]])
    bayes_weights = np.dot(weights, user_percentages)
    return np.argmin(bayes_weights)

def select_next_move_against(user):
    """
    First version of machine learning for selecting what to play next. Chooses
    by looking at what the user has most frequently played after the same kind
    of choise as made with last game and chooses the bayes optimal from them.

    If no sufficient data is available, prints a random digit between 0 to 2

    TODO: when not enough similar game indexes use only what user played.
    """
    past_games = reader.read_past_games(user)
    bfnp = bayes_from_next_pairs(past_games)
    if (bfnp >= 0):
        print(bfnp)
    else:
        print(randint(0,2))

if __name__ == "__main__":
    select_next_move_against(sys.argv[1])
