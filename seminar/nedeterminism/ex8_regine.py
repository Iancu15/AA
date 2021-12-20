def regine():
	V = new vector[8]
	for i in 1...8:
		regina = new Regina # regina e o clasa cu campurile x si y
		regina.x = choice(1...8)
		regina.y = choice(1...8)
		V.add(regina)

	for regina1 in 1...7:
		for regina2 in i+1...8:
			if (regina1.x == regina2.x || regina1.y == regina2.y):
				fail
			if (abs(regina1.x - regina2.x) == abs(regina1.y - regina2.y)):
				fail
	success
