#/bin/gnuplot

set terminal 'png' size 1000,1000
set grid

set output 'casSimple.png'
plot 'casSimple-lengthOccur-3.plot' using 1:2
set output 'casSimpleAvecTerminal.png'
plot 'casSimpleAvecTerminal-lengthOccur-3.plot' using 1:2
set output 'casSimpleAvecTerminalEtBoucle.png'
plot 'casSimpleAvecTerminalEtBoucle-lengthOccur-3.plot' using 1:2
set output 'exempleFigure2.png'
plot 'exempleFigure2-lengthOccur-3.plot' using 1:2
