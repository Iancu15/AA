def FaceAlgo(P, E):
    distribution = new Set de Pair(elem din E, elem din P)
    for empl in E:
        project = choice(P)
        pair = new Pair(empl, project)
        distribution.add(pair)

    for pair in distribution:
        gotTheRightEmployee = False
        for neededEmployee in pair.project.Ep:
            if (neededEmployee == pair.employee):
                gotTheRightEmployee = True
        if (!gotTheRightEmployee):
            fail

    success

    Complexitate: O(E + E * E) = O(E^2)
