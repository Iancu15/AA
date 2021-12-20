def PAuB(x):
    if PA(x) or PA(x - 1):
        return 1
    return 0

# PPRA ca A este nerecursiva => stiind ca A este recursiv enumerabila ca
# apartine RE - R => PAuB poate sa cicleze pentru x nu apartine A sau B pentru
# ca am presupus ca A este nerecursiva, dar recursiv enumerabila => PAuB este
# recursiva enumerabila => contradictie pentru ca stim ca PAuB este recursiva
# => presupunerea facuta e falsa si A este de fapt recursiva

def PB(x):
    if PA(x - 1):
        return 1
    return 0

# cum am demonstrat mai sus ca A este recursiva atunci si B trebuie sa fie
# recursiva
