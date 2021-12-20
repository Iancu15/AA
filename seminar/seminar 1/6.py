Metoda 1:
def PAuB(x):
    if PA(x) or PA(x - 1):
        return 1
    return 0

# Se observa clar in functie ca daca A e recursiva si PAuB e recursiva
# Daca A ar fi recursiv enumerabila fara sa fie recursiva, atunci PAuB
# ar fi recursiv enumerabila ca ar continua la infinit cu PA, ceea ce nu este
# Daca ar fi nerecursiva, dar fara sa fie recursiv enumerabila, atunci PAuB
# n-ar fi recursiva
# deci sigur A e recursiva

Metoda 2:

def PAuB(x):
    intoarce perechea care contine x

def PA(x):
    if PAuB(x) il are pe x pe prima pozitie:
        return 1
    return 0

def PA(x):
    if PAuB(x) and PAuB(x + 1): ????
        return 1
    return 0

def PB(x):
    if PA(x - 1):
        return 1
    return 0

# Daca A e recursiva, automat si B este recursiva
