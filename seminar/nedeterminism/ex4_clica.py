def clique(V, E, k):
    W = new set # va contine nodurile clicii
    for node in V:
        isPartOfClique = choice(TRUE, FALSE)
        if (isPartOfClique):
            W.add(node)

    if W.size() != k:
        fail

    for node in W:
        for node2 in W:
            if node1 == node2:
                continue
            if edge (node node2) doesn't exist in E:
                fail

    success
