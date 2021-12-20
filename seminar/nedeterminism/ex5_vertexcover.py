def acoperire(V, E, k):
    W = new set # va contine nodurile clicii
    for node in V:
        isPartOfSubset = choice(TRUE, FALSE)
        if (isPartOfSubset):
            W.add(node)

    if W.size() != k:
        fail

    for edge in E:
        isPart = 0
        for node in W:
            if (edge.node1 == node || edge.node2 == node): # presupun ca intrarea e corecta
                isPart++

        if (isPart != 2):
            fail
    success
