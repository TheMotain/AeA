#!/bin/sh

for arnMessage in `ls $1`; do
    for microARN in `ls $2`; do
        echo "ARN messagé : $arnMessage"
        echo "Micro ARN : $microARN"
        echo "\nRésultat:\n"
        java -jar Hybridation.jar $1/${arnMessage} $2/${microARN}
        echo "\n------------------------------------------------------------------\n"
    done
done