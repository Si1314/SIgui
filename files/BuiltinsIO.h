#include <stdio.h>

int ConsoleIn_Int(){
	int i;
	scanf ("%d",&i);
	return i;
}

void ConsoleOut_Int(int i){
	printf ("%d\n",i);
}

void ConsoleOut_String(char *string){
	printf ("%s\n", string);
}
