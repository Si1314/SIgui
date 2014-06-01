//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Cubo.h"

//---------------------------------------------------------------------------

Cubo::Cubo(GLfloat x,GLfloat y,GLfloat z):Malla(8,6,6)
{
        this->x=x;
        this->y=y;
        this->z=z;

}

Cubo::~Cubo(){}
//----------------------------------------------------------------------------

void Cubo::construye()
{


        vertices[0]=new PV3D(0,y,0,1);
        vertices[4]=new PV3D(0,y,z,1);

        vertices[1]=new PV3D(0,0,0,1);
        vertices[5]=new PV3D(0,0,z,1);

        vertices[2]=new PV3D(x,0,0,1);
        vertices[6]=new PV3D(x,0,z,1);

        vertices[3]=new PV3D(x,y,0,1);
        vertices[7]=new PV3D(x,y,z,1);

     

       int nVerticesPorCara=4;

           for(int j=0;j<nVerticesPorCara;j++)
           {
              int numcara= j;
              caras[numcara]= new Cara(nVerticesPorCara);
              VerticeNormal** arrayParcial= new VerticeNormal*[nVerticesPorCara];

              int base=j;
              int suc=(j+1)%nVerticesPorCara;

              int a= base;
              int b= suc;
              int c= suc+nVerticesPorCara;
              int d= base+nVerticesPorCara;


              arrayParcial[0]=new VerticeNormal(a,numcara);
              arrayParcial[1]=new VerticeNormal(b,numcara);
              arrayParcial[2]=new VerticeNormal(c,numcara);
              arrayParcial[3]=new VerticeNormal(d,numcara);


              caras[numcara]->addVerticeNormal(arrayParcial);

           }

        

        //tapa 1
        caras[4]=new Cara(nVerticesPorCara);
        VerticeNormal** arrayCara4= new VerticeNormal*[nVerticesPorCara];
        arrayCara4[0]=new VerticeNormal(0,4);
        arrayCara4[1]=new VerticeNormal(3,4);
        arrayCara4[2]=new VerticeNormal(2,4);
        arrayCara4[3]=new VerticeNormal(1,4);
        caras[4]->addVerticeNormal(arrayCara4);

        //tapa 2
        caras[5]=new Cara(nVerticesPorCara);
        VerticeNormal** arrayCara5= new VerticeNormal*[nVerticesPorCara];
        arrayCara5[0]=new VerticeNormal(4,5);
        arrayCara5[1]=new VerticeNormal(5,5);
        arrayCara5[2]=new VerticeNormal(6,5);
        arrayCara5[3]=new VerticeNormal(7,5);
        caras[5]->addVerticeNormal(arrayCara5);


        for(int i=0; i<this->numeroCaras;i++)
        {
            PV3D* normal=this->CalculoVectorNormalPorNewell(caras[i]);
            normales[i]= normal;
        }

}


#pragma package(smart_init)
