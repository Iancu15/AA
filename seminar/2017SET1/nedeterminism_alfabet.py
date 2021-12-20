def alfabet(S, sigma, k):
    str = new string # creeaza un "", .append("a") pe un sir gol face "a"
    for i in 0...k-1:
        letter = choice(sigma)
        str.append(letter)

    for substr in S:
        for indexstr in 0...k-1:
            currentindex = indexstr
            matches = True
            for indexsubstr in 0...substr.size()
                if currentindex == k or str[currentindex] != substr[indexsubstr]:
                    matches = False
                    break
                # daca e egal continua sa verifice
                currentindex++

            if (matches == False):
                fail

    success

    Complexitate: O(k + s * k * k) = O(s * k^2), unde s este numarul de subsiruri
    din S, consider ca substr.size() este in cel mai rau caz egal cu k, daca
    cumva index-ul lui str a ajuns la k oricum iese din for
