#!/usr/bin/env python
import load_data as reader
import numpy as np
from random import randint
import sys

def select_next_move_against(user):
    """
    First version of machine learning for selecting what to play next. Chooses
    by looking at what the user has most frequently played after the same kind
    of choise as made with last game and chooses the bayes optimal from them.

    If no sufficient data is available, prints a random digit between 0 to 2

    TODO: when not enough similar game indexes use only what user played.
    """
    past_games = reader.read_past_games(user)
    last_i = len(past_games) - 1
    if (last_i < 3):
        print(randint(0,2))
        return
    last_game = past_games[last_i]
    similar_game_indexes = np.array(np.where((past_games[:last_i,0] == last_game[0])
        & (past_games[:last_i,1] == last_game[1]))[0])
    if (len(similar_game_indexes) < 1):
        print(randint(0,2))
        return
    similar_game_indexes[:] += 1
    similar_game_next_moves = past_games[similar_game_indexes][:,0]
    next_move_freq = np.bincount(similar_game_next_moves)
    while (len(next_move_freq) < 3):
        print(randint(0,2))
        return
    freq_sum = np.sum(next_move_freq)
    most_likely_next = np.argmax(next_move_freq)
    user_percentages = np.array([next_move_freq[i] / freq_sum for i in range(3)])
    weights = np.array([[0,1,-1], [-1,0,1], [1,-1,0]])
    bayes_weights = np.dot(weights, user_percentages)
    print(np.argmin(bayes_weights))

if __name__ == "__main__":
    select_next_move_against(sys.argv[1])
