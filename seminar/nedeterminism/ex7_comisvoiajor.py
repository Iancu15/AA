def comisvoiajor(graf):
    n = size(graf) # numarul de noduri
    V = new vector[n+1]
    x = choice(1...n)
    V[1] = x
    for i in 2...n:
        x = choice(vecinii lui x)
        V[i] = x

    if (V[1] != V[n]): # trebuie sa se intoarca de unde a plecat
        false

    for i in 1...n-1:
        for j in i+1...n:
            if (V[i] == V[j]):
                fail
    success
