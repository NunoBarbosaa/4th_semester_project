digital_twin: connections.o planner.o main.o 
	gcc -Wall -g connections.o planner.o main.o -o digital_twin -lrt -lpthread -lssl -lcrypto

main.o: main.c
	gcc -Wall -g -c main.c -o main.o -lrt -lpthread -lssl -lcrypto

planner.o: planner.c 
	gcc -Wall -g -c planner.c -o planner.o -lrt -lpthread

connections.o: connections.c 
	gcc -Wall -g -c connections.c -o connections.o -lssl -lcrypto

run: digital_twin
	./digital_twin

clean:
	rm -f *.o main

dbg: digital_twin
	gdb -tui ./digital_twin

