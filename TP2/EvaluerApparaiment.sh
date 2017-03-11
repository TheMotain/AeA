#!/bin/sh

for microARN in `ls $1`; do
    echo "ARN messagé : $microARN"
    echo "\nRésultat:\n"
    java -jar Apparaiment.jar $1/${microARN}
    echo "\n------------------------------------------------------------------\n"
done