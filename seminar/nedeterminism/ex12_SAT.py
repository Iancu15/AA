def SAT(expr):
    n = expr.numbeOfVariables # presupun ca se stiu
    V = new boolean vector[n]
    for i in 1...n:
        V[i] = choice(True, False)

    for clause in expr:
        isClauseTrue = False
        for var in clause:
            varState = V[abs(var)]
            if (var > 0 && varState == True):
                isClauseTrue = True
            if (var < 0 && varState == False):
                isClauseTrue = True
        if isClauseTrue == False:
            fail

    success
