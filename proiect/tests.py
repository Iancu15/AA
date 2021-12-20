from numpy import random

n = int(input("Number of tests: "))
max_nodes = int(input("Maximum number of nodes: "))
min_nodes = int(input("Minimum number of nodes: "))
if min_nodes > max_nodes:
    print("ERROR -> Lower bound bigger than upper bound")
    exit()

for i in range(0, n):
    f = open("in/test" + str(i) + ".in", "w")
    print("in/test" + str(i) + ".in")
    # In caz ca numarul minim si maxim e acelasi se ia numarul maxim
    if max_nodes == min_nodes:
        number_of_nodes = max_nodes;
    else:
        number_of_nodes = random.randint(min_nodes, max_nodes)

    f.write(str(number_of_nodes))
    # creez o lista formata din listele nodurilor vecini
    neighborslist = list()
    for j in range(0, number_of_nodes):
        neighbors = list()
        neighborslist.append(neighbors)

    for j in range(0, number_of_nodes):
        f.write('\n')
        number_of_neighbors = random.randint(0, number_of_nodes)

        # aleg aleatoriu vecinii excluzand nodul curent j
        neighbors = random.choice(list(range(0, j)) + list(range(j + 1, number_of_nodes)), number_of_neighbors)
        # adaug in listele vecinilor nodului curent doar nodurile care au
        # index-ul mai mare si care nu se afla in lista de vecini a acestuia si
        # de asemenea adaug nodul curent in listele acestor vecini
        # astfel nodurile cu index mai mic isi alegi vecinii cu indexul mai mare
        # acest lucru imi ofera o adaugare de noduri ordonata
        for neighbor in neighbors:
            if (neighbor > j) and (neighbor not in neighborslist[j]):
                neighborslist[j].append(neighbor)
                neighborslist[neighbor].append(j)

        # daca niciun nod cu index mai mic nu a ales nodul curent vecin si
        # aleatoriu nu s-a pus la dispozitia nodului curent niciun nod cu index
        # mai mare, atunci se adauga -1 pentru output
        if not neighborslist[j]:
            neighborslist[j].append(-1)

        # afisez fiecare nod vecin in linia corespunzatoare din output cu spatii
        # intre ele
        for neighbor in neighborslist[j]:
            f.write(str(neighbor) + ' ')
    f.close()
