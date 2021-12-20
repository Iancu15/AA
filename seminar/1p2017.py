def P(x):
    if (GA(x) == x):
        return 1
    if (GB(x) == x):
        return 0
    if (GC(x) == x):
        return -1

# programul se termina in timp finit

def PA(x):
    if P(x) == 1:
        return 1
    return 0

# PA apeleaza un program care se termina in timp finit si in functie de acesta
# ofera raspunsul de adevar sau de fals => A este recursiva

def PB(x):
    if P(x) == 0:
        return 1
    return 0

def PC(x):
    if P(x) == -1:
        return 1
    return 0
