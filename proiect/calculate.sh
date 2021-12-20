#!/bin/bash

i=0;
declare -A info
for k in {0..3}; do
	for l in {0..2}; do
		info[k, l]
	done
done

for file in out/*.out; do
    i=$i+1
    j = 0
    while read line; do
        time=`echo $line | awk -F"|" '{print $1}'`
        $info[j, 0]+=$time
        correct=`echo $line | awk -F"|" '{print $2}'`
        $info[j, 1]+=$correct
        chrom=`echo $line | awk -F"|" '{print $3}'`
        $info[j, 2]+=$chrom
        j=$j+1
    done < $file
done

echo "" > results.out
for k in {0, 3}; do
	time = $info[j, 0]/$i
	correct = $info[j, 1]/$i
	chrom = $info[j, 2]/$i
	echo $time + " " + $correct + " " + $chrom + "\n" >> results.out
done
