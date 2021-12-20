def P'(x, y, z):
    P(w)
    return 1

Pentru o intrare oarecare P si w pentru PO am construit o intrare P' pentru
STOP3 astfel incat PO(P, w) = 1 <=> STOP3(P') = 1

Conform definitei lui P', acesta se opreste pe cele 3 input-uri daca si P(w)
se opreste si merge la infinit in caz ca P(w) merge la infinit.

Pasul 2: PO(P, w) = 1 => STOP3(P') = 1. Daca P se opreste pe w (PO(P, w) = 1),
atunci se va opri si P' pe cele 3 intrari (STOP3(P'))

Pasul 3: STOP3(P') = 1 => PO(P, w) = 1. Daca P' se opreste pe cele 3 intrari
(STOP3(P') = 1), atunci inseamna ca si P se va opri pe intrarea w(PO(P, w) = 1)
