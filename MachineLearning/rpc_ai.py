#!/usr/bin/env python
import load_data as reader
import numpy as np
from random import randint
import sys

SIGN_WEIGHTS = np.array([[0,1,-1], [-1,0,1], [1,-1,0]])

def next_signs(past_games, next_func):
    """
    Takes a list of past games and a function by which next indexes are selected.
    Finds all similar user played signs from past games and returns a list of
    signs user played right after playing that sign.
    """
    if (len(past_games) == 0):
        return []
    last_i = len(past_games) - 1
    last_game = past_games[last_i]
    next_moves = np.array(np.where(next_func(past_games, last_i, last_game))[0])
    if (len(next_moves) == 0):
        return []
    next_moves += 1
    return past_games[next_moves][:,0]

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

def update_suffix_moves(j, suffix, longest_range, next_move):
    """
    Checks if longest_range and/or next_move need to be updated after a suffix
    either ends correctly or no longer matches past games.
    """
    if j > longest_range:
        return j, [suffix[0]]
    elif j == longest_range:
        next_move = np.append(next_move, [suffix[0]])
    return longest_range, next_move

def suffix_no_longer_match_past(suffix, j, games):
    """
    Returns true if suffix next index does not meet expected from past games.
    """
    return suffix[j+1] != games[j]

def suffix_ends_correctly(j, suffix):
    """
    Returns true if we just checked the last suffix index. Because we did not
    go to suffix_no_longer_match_past branch, the suffix must match.
    """
    return j == len(suffix) - 2

def suffixes_from(past_games):
    """
    Finds the longest range matching what has been played previously from past
    games and returns the bayes optimal based on what was played after the 
    longest range last time. If several same length ranges are found, they are
    all used when calculating the optimal. Returns also the length of the
    longest range to be used when estimating which algorithm to use when
    selecting the best move for next game.
    """
    if len(past_games) == 0:
        return np.array([0,0,0]), 0
    games = past_games[:,0]
    longest_range = 0
    next_move = []
    for i in range(len(games)-1):
        suffix = games[i:]
        if longest_range > len(suffix):
            break
        for j in range(len(suffix)-1):
            if suffix_no_longer_match_past(suffix, j, games):
                if j != 0:
                    longest_range, next_move = update_suffix_moves(
                            j, suffix, longest_range, next_move)
                break
            elif suffix_ends_correctly(j, suffix):
                longest_range, next_move = update_suffix_moves(
                        j+1, suffix, longest_range, next_move)
    frequencies = rps_frequencies(np.array(next_move))
    return np.dot(SIGN_WEIGHTS, frequencies), longest_range

def bayes_from(past_games, next_func):
    """
    Takes a list of past games and a function by which next indexes are selected.
    Based on similar signs than the sign last played, returns a double
    containing first the weights for choosing each sign, and the amount of
    datapoints used to calculate those weights.
    """
    if (len(past_games) <= 1):
        return np.zeros(3), 0
    similar_game_next_moves = next_signs(past_games, next_func)
    next_move_freq = rps_frequencies(similar_game_next_moves)
    freq_sum = np.sum(next_move_freq)
    return np.dot(SIGN_WEIGHTS, next_move_freq), freq_sum

def same_pairs(past_games, last_i, last_game):
    """
    Returns true when both player and AI played the same moves as in last_game.
    """
    return ((past_games[:last_i,0] == last_game[0])
        & (past_games[:last_i,1] == last_game[1]))

def same_player_signs(past_games, last_i, last_game):
    """
    Return true when player played the same move as in last game.
    """
    return (past_games[:last_i,0] == last_game[0])

def select_next_move_against(past_games):
    """
    Uses machine learning to select what to play next. Calls for different ways
    to determine the weights for selecting different signs.

    If no sufficient data is available, return a random digit between 0 to 2
    (TODO: select better boundaries for sufficient data)
    """
    bfnp, bfnp_size = bayes_from(past_games, same_pairs)
    bfns, bfns_size = bayes_from(past_games, same_player_signs)
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
