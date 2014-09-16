#include "BuiltinsIO.h"

bool whileEstrictaCreciente(){
    int a = -1;
    int b = ConsoleIn_Int();
    while(b>a && b!=-1){
        ConsoleOut_Int(b);
        a = b;
        b = ConsoleIn_Int();
    }
    return b==-1;
}

int nfibonacci(int a){
	if(a<0)
		return -1;
	if(a==0 || a==1)
		return 1;
	int fibo1 = 1;
	int fibo2 = 1;
	int auxf;
	for(int i=2; i<a; i++){
		auxf = fibo1 + fibo2;
		fibo1 = fibo2;
		fibo2 = auxf;
	}
	return fibo1 + fibo2;
}

int inverso(int a){
	int aux = a;    
	int aux2 = aux % 10;
	int re = 0;
	while(aux>0){
        		re *= 10;
		re += aux2;
		ConsoleOut_Int(aux);
		aux2= aux / 10;
        		aux = aux2;
		aux2 = aux % 10;
    	}
    	return re;
}

bool esCapicua(int a){
	int aux = inverso(a);
	ConsoleOut_Int(aux);
	if (aux == a)
		return true;
	else
		return false;
	
}


// -------------------------------------------------------------------------------------

int deco2(bool a, bool b){
    int r =  0;
    if(a) 
        r+=2;
    if(b) 
        r+=1;
    return r;
} 

int deco2b(bool a,bool b){
    int r = 0;
    if (a){
        if(b){
            r = 3;
        }else{
            r = 2;
        }
    }else{
        if(b){
            r = 1;
        }else{
            r = 0;
        }
    }
    return r;
}

int introduceSuma(){
    int a = ConsoleIn_Int();
    int b = ConsoleIn_Int();
    int c = ConsoleIn_Int();
    return a+b+c;
}






bool capicuaConvexa5(){
    int a1 = ConsoleIn_Int();
    int b1 = ConsoleIn_Int();
    int c = ConsoleIn_Int();
    int b2 = ConsoleIn_Int();
    int a2 = ConsoleIn_Int();
    
    return (a1==a2)&&(b1==b2)&&(a1<b1)&&(b1<c);
}

int modulo4(int a){
int aux = a%4;
if (aux==0) return aux;
if (aux==1) return aux;
if (aux==2) return aux;
if (aux==3) return aux;
    else return -1;
}

bool esModulo5(int a){
    return a%5 == 0;
}

int multDiez(int a){
    int b;
    if(a<0){
        b = -a;
    }else{
        b = a; 
    }
    int c = 10;
    int r = 0;
    while(c > 0){
        r += a;
        c--;
    }
    return r;
}

int dimeSiPos(int a){
int r;
if(a>0){
r = 1;
}else{
r = 0;
}
return r;
}


int potencia(int a, int b){
    if(a>0&&b>=0){
        int c = b;
        int r = 1;
        while(c>0){
            r += a;
            c--;
        }
        return r;
    }else{
        return -1;
    }
}

int factorial(int a){
if(a>=0){
int fact = 1;
for(int i=2;i<=a;i++){
fact *=i;
}
return fact;
}else{
return -1;
}
}

int introduceEntero(){
int a = ConsoleIn_Int();
return a;
}

int reproduceEntero(int a){
ConsoleOut_Int(a);
return 0;
}

int llamadaFactorial(int a){
return factorial(a);
}

int ifLlamada(int a, int b){
int r;
if(a<=0){
r = factorial(b);
}else{
r = potencia(b,a);
}
return r;
}

int pruebaIf01(int a){
if (a==0) return 1;
if (a>0) return 3;
if (a<0) return -2;
}

int pruebaIf02(int a){
if(a>=0){
return 2;
}else{
return -2;
}
}

int pruebaIf03(int a){
if (a>=0){ 
return 2;
}
return 3;
}

int pruebaIf04(int a){
if (a==0) return 1;
else return -1;
}

int pruebaIf05(int a){
if (a==0) return 1;
else {return -1;}
}

int pruebaFor01(int a){
if(a>0){
int r = 0;
for(int i=0;i<10;i++){
r += a;
}
return r;
}else{
return -1;
}
}

int pruebaWhile01(int a){
int r = a;
while(r>0){
ConsoleOut_Int(r);
r--;
}
return 0;
}
