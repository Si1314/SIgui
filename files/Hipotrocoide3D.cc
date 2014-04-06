//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Hipotrocoide3D.h"

//---------------------------------------------------------------------------

Hipotrocoide3D::Hipotrocoide3D(GLfloat R,GLfloat r,GLfloat d,int NP,int NQ,GLfloat tam) : Malla(NP*NQ*2,NP*NQ*4,NP*NQ*4)
{
    this->R=R;
    this->r=r;
    this->d=d;
    this->NP=NP;
    this->NQ=NQ;
    this->tam=tam;
    this->nVueltas=calculaVueltas();
    coche=new Coche(tam*0.8,tam*0.8,tam*0.8,5,5,5);
    acumCoche=0;
    cuantoDerrape=-1.5;
}
Hipotrocoide3D::~Hipotrocoide3D()
{
        delete coche;
}
//--------------------------------------------------------------------------

GLfloat Hipotrocoide3D::funcionX(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;

        GLfloat val1=(A)*cos(val); 
        GLfloat val2=d*cos(B*val);
        return val1+val2;
}

GLfloat Hipotrocoide3D::primeraDerivadaX(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;

        GLfloat val1=-A*sin(val);
        GLfloat val2=-d*B*sin(B*val);
        return val1+val2;
}

GLfloat Hipotrocoide3D::segundaDerivadaX(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;
        
        GLfloat val1=-A*cos(val);
        GLfloat val2=-d*B*B*cos(B*val);
        return val1+val2;
}

GLfloat Hipotrocoide3D::funcionZ(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;

        GLfloat val1=A*sin(val);
        GLfloat val2=-d*sin(B*val);
        return val1+val2;
}

GLfloat Hipotrocoide3D::primeraDerivadaZ(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;

        GLfloat val1=A*cos(val);
        GLfloat val2=-d*B*cos(B*val);
        return val1+val2;
}

GLfloat Hipotrocoide3D::segundaDerivadaZ(GLfloat val)
{
        GLfloat A=R-r;
        GLfloat B=(R-r)/r;
        
        GLfloat val1=-A*sin(val);
        GLfloat val2=d*B*B*sin(B*val);
        return val1+val2;
}

PV3D* Hipotrocoide3D::funcion(GLfloat val)
{
        GLfloat x= funcionX(degToRad(val));
        GLfloat z=funcionZ(degToRad(val));
        return new PV3D(x,0,z,0);
}
PV3D* Hipotrocoide3D::primeraDerivada(GLfloat val)
{
        GLfloat x= primeraDerivadaX(degToRad(val));
        GLfloat z=primeraDerivadaZ(degToRad(val));
        return new PV3D(x,0,z,0);
}
PV3D* Hipotrocoide3D::segundaDerivada(GLfloat val)
{
        GLfloat x= segundaDerivadaX(degToRad(val));
        GLfloat z=segundaDerivadaZ(degToRad(val));
        return new PV3D(x,0,z,0);
}

void Hipotrocoide3D::construye()
{
     GLfloat intervaloToma =(GLfloat)(360.0*this->nVueltas/NQ);

        //construimos un objeto con el lapiz
    
        PV3D* centro= new PV3D(0,0,0,1);
        Poligono* p= new Poligono(centro,tam,NP);
        PV3D** puntos=p->getVertices();
        
        for(int i=0;i<NQ;i++)
        {
                GLfloat toma=intervaloToma*i;
                PV3D* Tt=primeraDerivada(toma); Tt->normaliza();
                PV3D* segundaderivada=segundaDerivada(toma);
                PV3D* primeraderivada=primeraDerivada(toma);
                PV3D* Bt=primeraderivada->productoVectorial(segundaderivada); Bt->normaliza();
                PV3D* Nt=Bt->productoVectorial(Tt);
                PV3D* Ct=funcion(toma);
                        Ct->setPv(1);                    


                for(int j=0;j<NP;j++)
                {
                   
                   int numvertice=NP*i+j;
                   PV3D* clon=puntos[j]->clone();
                   PV3D* punto=clon->multiplicaMatriz(Nt,Bt,Tt,Ct);
                   vertices[numvertice]=punto;
                   delete clon;  
                }

                //deletes de los objetos ya no necesarios
               delete Tt;
               delete Bt;
               delete segundaderivada;
               delete primeraderivada;
               delete Nt;
               delete Ct;

        } //fin del for para cada toma

/*

             //construir las caras
               for(int j=0;j<NP;j++)
               {

                    int numcara= NP*(i)+j ;
                    caras[numcara]= new Cara(4);
                    VerticeNormal** arrayParcial= new VerticeNormal*[4];

                    int verticeBase=numcara;
                    int a= (verticeBase) % (NP*NQ);
                    int b= (sucesor(verticeBase))% (NP*NQ);
                    int c=  (sucesor(verticeBase)+NP)% (NP*NQ);
                    int d=  (verticeBase+NP)% (NP*NQ);


                    arrayParcial[0]=new VerticeNormal(a,numcara);
                    arrayParcial[1]=new VerticeNormal(b,numcara);
                    arrayParcial[2]=new VerticeNormal(c,numcara);
                    arrayParcial[3]=new VerticeNormal(d,numcara);


                    caras[numcara]->addVerticeNormal(arrayParcial);
               }
 */

 //construccion especial con triangulos

         int numcara=0;
        int nVerticeActual=NP*NQ;
         for(int i=0;i<NQ;i++)
         {
                //i es el numero de de tomas a realizar
                for(int j=0;j<NP;j++)
                {
                        //j es el numero de puntos de la aproximacion a circunferencia

                      int verticeBase=i*NP+j;//vertice base es el vertice de menor numero que forma la cara

                    int a= (verticeBase) % (NP*NQ);
                    int b= (sucesor(verticeBase))% (NP*NQ);
                    int c=  (sucesor(verticeBase)+NP)% (NP*NQ);
                    int d=  (verticeBase+NP)% (NP*NQ);

                    GLfloat sumax=vertices[a]->getX()+ vertices[b]->getX()+vertices[c]->getX()+vertices[d]->getX();
                    GLfloat sumay=vertices[a]->getY()+ vertices[b]->getY()+vertices[c]->getY()+vertices[d]->getY();
                    GLfloat sumaz=vertices[a]->getZ()+ vertices[b]->getZ()+vertices[c]->getZ()+vertices[d]->getZ();

                    sumax=sumax/4;
                    sumay=sumay/4;
                    sumaz=sumaz/4;

                    PV3D* centro= new PV3D(sumax,sumay,sumaz,1);//punto central de una cara
                    vertices[nVerticeActual]=centro->clone();
                    delete centro;

                    //cara extra 1
                    caras[numcara]= new Cara(3);
                    VerticeNormal** arrayParcial1= new VerticeNormal*[3];

                        arrayParcial1[0]=new VerticeNormal(b,numcara);
                        arrayParcial1[1]=new VerticeNormal(nVerticeActual,numcara);
                        arrayParcial1[2]=new VerticeNormal(a,numcara);

                    caras[numcara]->addVerticeNormal(arrayParcial1);
                    numcara++;

                    //cara extra 2
                    caras[numcara]= new Cara(3);
                    VerticeNormal** arrayParcial2= new VerticeNormal*[3];

                        arrayParcial2[0]=new VerticeNormal(c,numcara);
                        arrayParcial2[1]=new VerticeNormal(nVerticeActual,numcara);
                        arrayParcial2[2]=new VerticeNormal(b,numcara);

                    caras[numcara]->addVerticeNormal(arrayParcial2);
                    numcara++;

                     //cara extra 3
                    caras[numcara]= new Cara(3);
                    VerticeNormal** arrayParcial3= new VerticeNormal*[3];

                        arrayParcial3[0]=new VerticeNormal(d,numcara);
                        arrayParcial3[1]=new VerticeNormal(nVerticeActual,numcara);
                        arrayParcial3[2]=new VerticeNormal(c,numcara);

                    caras[numcara]->addVerticeNormal(arrayParcial3);
                    numcara++;

                    //cara extra 4
                    caras[numcara]= new Cara(3);
                    VerticeNormal** arrayParcial4= new VerticeNormal*[3];

                        arrayParcial4[0]=new VerticeNormal(a,numcara);
                        arrayParcial4[1]=new VerticeNormal(nVerticeActual,numcara);
                        arrayParcial4[2]=new VerticeNormal(d,numcara);

                    caras[numcara]->addVerticeNormal(arrayParcial4);
                    numcara++;

                    nVerticeActual++;//aumentamos en 1 el verticeActual(vertices extras)
                }
         }


        //construir las primeras caras(desde toma 0 a toma NQ


        for(int i=0;i<this->numeroCaras;i++)
        {
                normales[i]= this->CalculoVectorNormalPorNewell(caras[i]);
        }



        delete p;

}//fin funcion construye

int Hipotrocoide3D::sucesor(int val)
{
        int valaux=val+1;
        if(valaux%NP==0)
        {
          return valaux-NP;
        }
        return valaux;
}

GLfloat Hipotrocoide3D::degToRad(GLfloat deg)
{
    GLfloat num=deg*2*PI;
    GLfloat den=360;

    return num/den;
}


GLfloat Hipotrocoide3D::calculaVueltas()
{
     /*
     GLfloat valor=(R-r)/r;
     GLfloat porcentajeVuelta=valor*360;
     while(porcentajeVuelta<0)
     {
      porcentajeVuelta=porcentajeVuelta+360;
     }

     while(porcentajeVuelta>360)
     {
     porcentajeVuelta=porcentajeVuelta-360;
     }

     GLfloat devolver=360.0/porcentajeVuelta;
     return ceil(devolver);
     */
     return (r*6)/mcd(R*6,r*6);

}

void Hipotrocoide3D::dibuja(bool relleno,bool dibujaNormales,PV3D* color,bool dibujaMalla,bool derrape)
{
        if(dibujaMalla)
                Malla::dibuja(relleno,dibujaNormales,color);

        //dibujo  del coche
        PV3D* Tt=primeraDerivada(acumCoche); Tt->normaliza();
        PV3D* segundaderivada=segundaDerivada(acumCoche);
        PV3D* primeraderivada=primeraDerivada(acumCoche);
        PV3D* Bt=primeraderivada->productoVectorial(segundaderivada); Bt->normaliza();
        PV3D* Nt=Bt->productoVectorial(Tt);
        PV3D* Ct=funcion(acumCoche);
                Ct->setPv(1);
      /*
        GLfloat m[]={   Nt->getX(),Bt->getX(),Tt->getX(),Ct->getX(),
                        Nt->getY(),Bt->getY(),Tt->getY(),Ct->getY(),
                        Nt->getZ(),Bt->getZ(),Tt->getZ(),Ct->getZ(),
                        0,0,0,1};
       */

       GLfloat m[] ={   Nt->getX(),Nt->getY(),Nt->getZ(),Nt->getPv(),
                        Bt->getX(),1,Bt->getZ(),Bt->getPv(),//el 1 es para que salga hacia arriba
                        Tt->getX(),Tt->getY(),Tt->getZ(),Tt->getPv(),
                        Ct->getX(),Ct->getY(),Ct->getZ(),Ct->getPv()};

        glMatrixMode(GL_MODELVIEW);        
        glPushMatrix();
               
                glMultMatrixf(m);
                if(derrape)
                        glTranslated(0,0,cuantoDerrape);

                dibujaCoche(dibujaNormales,relleno);
              
        glPopMatrix();

        delete Tt;
        delete Bt;
        delete segundaderivada;
        delete primeraderivada;
        delete Nt;
        delete Ct;

}
void Hipotrocoide3D::dibujaCoche(bool dibujaNormales,bool relleno)
{
      coche->dibuja(dibujaNormales,!relleno,acumCoche); 
}

void Hipotrocoide3D::addAcum(GLfloat cantidad)
{
        acumCoche=acumCoche+cantidad;

}



