# !/bin/sh

for i in 100 200 400 500; do
    for j in 0.1 0.3 0.5 0.7 0.9; do
        echo $i $j "-----------------------------------------------"
        java -jar $1 $2 10 $i $j | grep "Temps"
    done
done
