#!/bin/bash

for file in in/*.in; do
	echo $file
	./algo_test $file
done
