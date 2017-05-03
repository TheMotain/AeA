# !/bin/sh

for i in 50 100 200 400; do
    for j in 0.1 0.3 0.5 0.7 0.9; do
        echo $i $j
        java -jar $1 $2 1 $i $j | grep "Temps"
    done
done
