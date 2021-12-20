def sequence(V, k):
    n = size(V)
    x = choice(0...n-1)
    if (n-1-x <= k):
        fail
    for i in x...x+k:
        if (V[i] != V[i+1]):
            fail
    success
