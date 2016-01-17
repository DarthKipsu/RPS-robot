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

def matching_recent(suffixes, n):
    """
    Returns the next moves after a game that matches the n most recent games
    played. Takes an array of suffixes generated from past games, with length
    at least n + 1, where the first item in the array is the sign played
    immediately after that suffix.
    """
    if len(suffixes) < n:
        return []
    recent = np.array(suffixes[:n,0])
    for i in range(n):
        suffixes = suffixes[suffixes[:,i+1] == recent[i]]
    return suffixes[:,0]

def n_previous(past_games, n):
    """
    Takes past games and the number of previous games we want to match n. Finds
    all matches of n length compared to n most recent games and returns the
    Bayes optimal from what was played immediately after those matches.
    """
    if len(past_games) <= n:
        return np.array([0,0,0]), 0
    games = past_games[:,0]
    suffixes = np.array([games[i:i+1+n] for i in range(len(games)-n)])
    next_move = matching_recent(suffixes, n)
    frequencies = rps_frequencies(np.array(next_move))
    return np.dot(SIGN_WEIGHTS, frequencies), np.sum(frequencies)

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
    Deprecated, not currently in use because of poor performance!
    """
    return (past_games[:last_i,0] == last_game[0])

def choose_top_choise(size_1, val_1, size_2, val_2, size_3, val_3):
    return ((size_1 > 1) & (((val_1 <= val_2) | (size_2 < 2))
        & ((size_3 < 2) | (val_1 <= val_3))))

def choose_second_choise(size_1, val_1, size_2, val_2):
    return ((size_1 > 1) & ((val_1 <= val_2) | (size_2 < 2)))

def choose_last_choise(size_1):
    return size_1 > 1

def select_next_move_against(past_games):
    """
    Uses machine learning to select what to play next. Calls for different ways
    to determine the weights for selecting different signs.

    If no sufficient data is available, return a random digit between 0 to 2
    """
    bfnp, bfnp_size = bayes_from(past_games, same_pairs)
    prev_2, size_2 = n_previous(past_games, 2)
    suf, suf_size = suffixes_from(past_games)
    min_bfnp = np.argmin(bfnp)
    min_2 = np.argmin(prev_2)
    min_suf = np.argmin(suf)
    if choose_top_choise(bfnp_size, bfnp[min_bfnp], size_2,
            prev_2[min_2], suf_size, suf[min_suf]):
        return min_bfnp, "Bayes next pairs"
    elif choose_second_choise(suf_size, suf[min_suf], size_2, prev_2[min_2]):
        return min_suf, "longest similar range"
    elif choose_last_choise(size_2):
        return min_2, "2 previous singles"
    else:
        return randint(0,2), "random"

if __name__ == "__main__":
    past_games = reader.read_past_games(sys.argv[1])
    next_move, method = select_next_move_against(past_games)
    print(next_move)
    print(method)
