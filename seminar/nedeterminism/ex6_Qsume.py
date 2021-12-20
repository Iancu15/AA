def Probl(M, s):
    [n m] = size(M)
    L = new Set
    for i in n:
        for j in m:
            isPartOfSubset = choice(TRUE, FALSE)
            if (isPartOfSubset):
                S = new struct
                S.number = M[i][j]
                S.x = i
                S.y = j
                L.add(S)

    sum = 0
    for S in L:
        sum += S.number

    if (sum < s):
        fail

    for S1 in L:
        isConnected = TRUE
        for S2 in L:
            if (S1 == S2):
                continue
