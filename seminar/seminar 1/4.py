Se deduce ca B nu este recursiv enumerabila
PPRA ca B este recursiv enumerabila

def PA(x):
    if PB(x):
        return 1
    if PAmB(x):
        return 1
    return 0

# Programul intoarce 1 pentru x apartine A si poate sa mearga la infinit daca
# nu apartine B care e inclus in A => PA e recursiv enumerabila ceea ce se
# contrazice cu enuntul => ipoteza gresita si B nu este nici macar recursiv
# enumerabila
