import os
from decimal import Decimal

files = [f for f in os.listdir('./out')]
info = [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]
i = 0
for f in files:
    i += 1
    f = open("./out/" + f, "r")
    numberOfNodes = int(f.readline())
    for k in range(4):
        infoline = f.readline().split(" ")
        for j in range(3):
            info[k][j] += float(infoline[j])
            if (j == 0):
                info[k][3] += float(infoline[0])/numberOfNodes

out = open("results.out", "w")
for z in range(4):
    for w in range(4):
        if (w == 0 or w == 3):
            data = "{:.2E}".format(info[z][w]/i)
        else:
            data = "{:.2f}".format(info[z][w]/i)
        out.write(str(data) + " ")
    out.write("\n")
