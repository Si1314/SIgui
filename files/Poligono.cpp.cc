//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Poligono.h"

//---------------------------------------------------------------------------
Poligono::Poligono(PV3D* centro,GLdouble radio,int nlados)
{
   this->nlados=nlados;
   this->centro=centro;
   this->radio=radio;  
   calculaDibujo();

}


Poligono::~Poligono()
{

   for(int i=0;i<nlados;i++)
   {
    delete vertices[i];

   }
   delete[] vertices;
   
   delete lapiz;
   delete centro;
   
 

}

PV3D** Poligono::getVertices()
{
 return vertices;
}

int Poligono::getNVertices()
{
 return nlados;
}

//-------------------------------------------------------------------------
void Poligono::calculaDibujo()
{
    lapiz= new Lapiz(centro->clone(),0);

          GLdouble alfa=2*PI/nlados;
          //GLdouble radio=(tam/2)/(sin(alfa/2));
          GLdouble tam=radio*sin(alfa/2)*2;
          lapiz->forward(radio);
          GLdouble beta=(PI-alfa)/2;
          lapiz->turn(PI-beta);
          dibujaRegularLado(nlados,tam,lapiz);


}

 void Poligono::dibujaRegularLado(int nlados,GLdouble longitud,Lapiz* lapiz)
 {
        vertices= new PV3D*[nlados];
          for(int i=0;i<nlados;i++)
          {
                PV3D *p1=lapiz->getPunto()->clone();
                lapiz->forward(longitud);
                lapiz->turn(2*PI/nlados);
                vertices[i]=p1;
          }

 }

 

#pragma package(smart_init)
