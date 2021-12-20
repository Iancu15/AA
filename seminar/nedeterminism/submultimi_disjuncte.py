def submultimi(M, k):
    listOfSubsets = new list de tip set
    for i in 1...k:
        subset = new set
        for elem in M:
            if (choice(True, False)):
                subset.add(subset)
        listOfSubsets.add(subset)

    for subset1 in listOfSubsets:
        for elem1 in subset1:
            for subset2 in listOfSubsets:
                if subset1 == subset2: # presupun ca compara adresa
                    continue
                for elem2 in subset2:
                    if (elem1 == elem2):
                        fail

    success

    Complexitate: O(k * |M| + k * |M| * k * |M|) = O(k^2 * |M|^2)
