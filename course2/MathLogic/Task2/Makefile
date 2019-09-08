PACKAGE=Task2.zip

all: solver

run: solver
	java -Xms256m -Xmx512m -cp src Solver

solver:
	javac -cp src src/Solver.java

pack:
	zip $(PACKAGE) -r Makefile src
