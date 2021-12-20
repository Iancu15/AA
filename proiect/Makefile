CC = g++
CFLAGS = -Wall -Wextra -O2 -g
LDLIBS = -lm

build: algo_test

make_tests:
	python tests.py

run_tests: algo_test
	./run_tests.sh
	python calculatetests.py

algo.o: algo.cpp
	g++ algo.cpp -c $(CFLAGS)

algo_test.o:
	g++ algo_test.cpp -c $(CFLAGS)

algo_test: algo_test.o algo.o
	g++ algo_test.o algo.o -o algo_test $(CFLAGS)

.PHONY: clean
clean:
	rm -rf *.o algo_test
