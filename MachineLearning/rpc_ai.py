import load_data as reader
import numpy as np
import sys

def select_next_move_against(user):
    past_games = reader.read_past_games(user)
    last_i = len(past_games) - 1
    last_game = past_games[len(past_games) - 1]
    similar_game_indexes = np.array(np.where((past_games[:last_i,0] == last_game[0])
        & (past_games[:last_i,1] == last_game[1]))[0])
    similar_game_indexes[:] += 1
    similar_game_next_moves = past_games[similar_game_indexes][:,0]
    next_move_freq = np.bincount(similar_game_next_moves)
    most_likely_next = np.argmax(next_move_freq)
    print(most_likely_next)

if __name__ == "__main__":
    select_next_move_against(sys.argv[1])
