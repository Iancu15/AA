def independentset(V, E, k):
    S = new set # nodurile din independent set
    for node in V:
        isPartOfSet = choice(TRUE, FALSE)
        if (isPartOfSet):
            S.add(node)

    if S.size() != k:
        fail

    for edge in E:
        if (edge1.node1 e in S && edge.node2 e in S):
            fail
    success
