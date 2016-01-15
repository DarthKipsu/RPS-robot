#!/usr/bin/env python
import load_data as reader
import numpy as np
from random import randint
import sys

SIGN_WEIGHTS = np.array([[0,1,-1], [-1,0,1], [1,-1,0]])

def next_signs(past_games, next_func, last_game):
    """
    Takes a previous game sign [user played], and a list of past games. Finds
    all similar user played signs from past games and returns a list of signs
    user played right after playing that sign.
    """
    if (len(past_games) == 0):
        return []
    last_i = len(past_games) - 1
    next_pair_indexes = np.array(np.where(next_func(past_games, last_i, last_game))[0])
    if (len(next_pair_indexes) == 0):
        return []
    next_pair_indexes += 1
    return past_games[next_pair_indexes][:,0]

def rps_frequencies(signs):
    """
    Takes a list of signs and returns a list indicating by index, how many times
    each rock=0, paper=1 and scissors=2 appeared on that list.
    """
    zeros = np.zeros(3)
    if (len(signs) == 0):
        return zeros
    frequencies = np.bincount(signs)
    return np.concatenate((frequencies, zeros))[:3]

def bayes_from(past_games, next_func):
    """
    Takes a list of past games and based on similar signs than the sign last
    played, returns a double containing first the weights for choosing each sign,
    and the amount of datapoints used to calculate those weights.
    """
    if (len(past_games) <= 1):
        return np.zeros(3), 0
    last_game = past_games[len(past_games) - 1]
    similar_game_next_moves = next_signs(past_games, next_func, last_game)
    next_move_freq = rps_frequencies(similar_game_next_moves)
    freq_sum = np.sum(next_move_freq)
    return np.dot(SIGN_WEIGHTS, next_move_freq), freq_sum

def next_pairs(past_games, last_i, last_game):
    return ((past_games[:last_i,0] == last_game[0])
        & (past_games[:last_i,1] == last_game[1]))

def next_singles(past_games, last_i, last_game):
    return (past_games[:last_i,0] == last_game[0])

def select_next_move_against(past_games):
    """
    Uses machine learning to select what to play next. Calls for different ways
    to determine the weights for selecting different signs.

    If no sufficient data is available, return a random digit between 0 to 2
    (TODO: select better boundaries for sufficient data)
    """
    bfnp, bfnp_size = bayes_from(past_games, next_pairs)
    bfns, bfns_size = bayes_from(past_games, next_singles)
    if ((bfnp_size > 1) | (bfns_size > 1)):
        if (bfnp[np.argmin(bfnp)] <= bfns[np.argmin(bfns)]):
            return np.argmin(bfnp), "Bayes next pairs"
        else:
            return np.argmin(bfns), "Bayes next singles"
    else:
        return randint(0,2), "random"

if __name__ == "__main__":
    past_games = reader.read_past_games(sys.argv[1])
    next_move, method = select_next_move_against(past_games)
    print(next_move)
    print(method)
