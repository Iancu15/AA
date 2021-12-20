def P'(x):
    if x == 5:
        P(w)
    return 1

Pentru intrarile P(oricare problema) si w pentru PO construim o intrare P'
astfel incat PO(P, w) = 1 <=> P5(P') = 1

Daca se opreste P pentru intrarea w, atunci se va opri si P', daca nu se opreste
atunci nici P' nu se va opri.

Pasul 2: PO(P, w) => P5(P'). Cand P se opreste pe w (PO(P,w) = 1), P' se va opri
si el pentru x = 5.

Pasul 3: P5(P') => PO(P, w). Daca P' se opreste pentru input-ul 5 inseamna
ca s-a oprit si P(w)
