import numpy as np
import rpc_ai as ai

def draw(player_move, ai_move):
    return player_move == ai_move

def ai_won(player_move, ai_move):
    return (((player_move == 0) & (ai_move == 1)) |
            ((player_move == 1) & (ai_move == 2)) |
            ((player_move == 2) & (ai_move == 0)))

def efficiency_test(player_move):
    moves = []
    # all wins, pairs wins, 3 singles wins, 2 singles wins, then losses, draws
    statistics = np.zeros(12)
    for i in range(len(player_move)):
        ai_move, method = ai.select_next_move_against(np.array(moves))
        moves.append([player_move[i], ai_move])
        if method == "Bayes next pairs":
            if draw(player_move[i], ai_move):
                statistics[9] += 1
            elif ai_won(player_move[i], ai_move):
                statistics[1] += 1
            else:
                statistics[5] += 1
        elif method == "3 previous singles":
            if draw(player_move[i], ai_move):
                statistics[10] += 1
            elif ai_won(player_move[i], ai_move):
                statistics[2] += 1
            else:
                statistics[6] += 1
        elif method == "2 previous singles":
            if draw(player_move[i], ai_move):
                statistics[11] += 1
            elif ai_won(player_move[i], ai_move):
                statistics[3] += 1
            else:
                statistics[7] += 1
        else:
            if draw(player_move[i], ai_move):
                statistics[8] += 1
            elif ai_won(player_move[i], ai_move):
                statistics[0] += 1
            else:
                statistics[4] += 1
    not_draws = len(player_move)-statistics[8]-statistics[9]-statistics[10]-statistics[11]
    print(len(player_move), "games played:")
    print("won", (statistics[0]+statistics[1]+statistics[2]+statistics[3])/not_draws*100, "% of games")
    print("lost", (statistics[4]+statistics[5]+statistics[6]+statistics[7])/not_draws*100, "% of games")
    print(statistics[8]+statistics[9]+statistics[10]+statistics[11], "draws")
    print()
    print(statistics[0]+statistics[4]+statistics[8], "random guesses")
    print()
    print("Bayes next pairs")
    print("W", statistics[1])
    print("L", statistics[5])
    print("D", statistics[9])
    print()
    print("3 previous singles")
    print("W", statistics[2])
    print("L", statistics[6])
    print("D", statistics[10])
    print()
    print("2 previous singles")
    print("W", statistics[3])
    print("L", statistics[7])
    print("D", statistics[11])

efficiency_test([2,0,2,1,1,2,1,2,1,0,2,0,1,2,0,2,1,1,0,2,0,2,1,1,1,2,1,1,0,2,0,
    1,2,2,2,2,2,2,1,2,0,2,2,2,1,2,2,0,0,0,0,2,1,1,1,1,2,2,2,1,1,2,1,2,1,0,0,0,2,
    2,2,1,2,1,0,0,0,0,2,1,2,2,2,2,1,0,2,0,2,0,0,0,0,2,2,1,1,2,1,2])
print("----------------------")
efficiency_test([2,1,1,0,0,2,0,1,2,0,0,1,2,0,0,2,2,1,1,2,1,2,1,0,0,0,0,2,1,2,2,
    1,0,1,2,1,0,2,1,0,0,1,2,1,1,0,2,2,1,0,2,1,0,2,2,0,1,2,1,1,0,0,1,2,1,2,2,2,1,
    2,0,1,2,0,0,2,1,2,1,2,0,0,1,2,2,1,0,1,2,1,2,1,2,1,0,1,2,1,0,0])
