def P'(x, y):
    if x == y:
        P(w) // inlocuiesc cu VID(P'')
    return 1

Pentru o intrare oarecare P, w pentru PO construim o intrare P' pentru
EQP astfel incat PO(P, w) = 1 <=> EQP(P') = 1. Rescriu apelarea in functie
de datele de intrare.

Pasul 2: PO(P, w) = 1 => EQP(P') = 1, daca P(w) se opreste asta inseamna ca
si P' se opreste pentru P1 si P2 avand intrari la fel.

Pasul 3: EQP(P') = 1 => PO(P, w) = 1, daca cele doua programe s-au oprit
atunci si P(w) s-a oprit.
