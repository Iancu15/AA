def partitionare(S, t):
    S1 = new set
    S2 = new set
    for elem in S:
        isPartOfS1 = choice(TRUE, FALSE)
        if (isPartOfS1):
            S1.add(elem)
        else:
            S2.add(elem)

    sum1 = 0
    for elem in S1:
        sum1 += elem

    sum2 = 0
    for elem in S2:
        sum2 += elem

    if (sum1 != sum2):
        fail
    success
