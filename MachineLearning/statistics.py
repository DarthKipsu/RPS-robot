#!/usr/bin/env python
import load_data as reader
import numpy as np
import sys

def stats_for(user):
    """
    Prints to console the number of games played by user, the number of draws
    against the computer AI and the number of times the computer AI has won.
    """
    past_games = reader.read_past_games(user)
    draws = past_games[past_games[:,0] == past_games[:,1]]
    ai_wins = past_games[(((past_games[:,0] == 0) & (past_games[:,1] == 1) |
        ((past_games[:,0] == 1) & (past_games[:,1] == 2))) |
        ((past_games[:,0] == 2) & (past_games[:,1] == 0)))]
    print(len(past_games))
    print(len(draws))
    print(len(ai_wins))

if __name__ == "__main__":
    stats_for(sys.argv[1])
