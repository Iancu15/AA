def P(x):
	while(true):
		if GA() == x:
			sol = 1
			break
		if GB() == x:
			sol = 0
			break
		if GC() == x:
			sol = -1
			break

def PA(x):
	if P(x) == 1:
		return 1
	return 0

def PB(x):
	if P(x) == 0:
		return 1
	return 0

def PC(x):
	if P(x) == -1:
		return 1
	return 0

# exista PA, PB, PC ce respecta structura => A, B si C recursive
