/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
*Número de Programa: 5
*Nombre: Adalberto Cerrillo Vázquez
*Fecha: 02/Noviembre/2022
*Descripción: Programa para obtener el valor de x para que una integracion de 0 a x de p
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

import java.util.Scanner;
import java.lang.Math;

//Clase Programa 5, clase tipo I/O
public class Programa5 {
    public static void main (String args[]){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese el valor de p: ");
        double p = entrada.nextDouble();
        System.out.println("Ingrese el valor de dof: ");
        double dof = entrada.nextDouble();
        integral itg = new integral();
        double res = itg.calcularX(p,dof);
        System.out.println("X = " + res);
    }
}

//clase para calcular la integral 
class integral{
	//funcion gamma,donde se evaluan los posible 3 valores de entrada (enteros mayores a 1, 1 y valores decimales de mitad)
    public double gamma(double z){
        // si el valor es .5
		if(z==.5){
            return Math.pow(Math.PI,z);
        }
		//si el valor es 1
        if(z==1){
            return z;
        }
		// si el valor es decimal mitad y es valor a 1
        if(((z-.5)%1==0) && z>1){
            double factorial=z-1;
            for(double i=factorial-1;i>=1;i--){
                factorial=factorial*i;
            }
            return factorial*.5*gamma(.5);
        }
		// si el valor es entero y es mayor a 1
        if(z % 1==0 && z>1){
            double factorial=z-1;
            for(double i=factorial-1;i>=1;i--){
                factorial=factorial*i;
            }
            return factorial;
        }
        return 0;
    }

    // funcion para calcular la parte izquierda de la funcion g
    public double parteIzquierda(double dof){
        return gamma((dof+1)/2)/((Math.sqrt(dof*Math.PI))* gamma(dof/2));
    }
    
	//funcion para calcular g, (usando simpson)
    public double g(double x,double dof){
        simpson s = new simpson();
        return parteIzquierda(dof) * (s.calcularValor(0,x,dof));
    }

    // funcion para calcular el valor de x
    public double calcularX(double p, double dof){
        double x = 0.1;
        double d = 0.5;
        double res = g(x,dof);
        double resAnterior = 0.0;
        double error = res-resAnterior;
        while((Math.abs(error)>0.0001)){
            if(res>p){
                d=d/2;
                x-=d;
            }
            else{
                x+=d;
            }
            resAnterior = res;
            res = g(x,dof);
            error = res-resAnterior;
        }
        return x;
    }
}

//clase de calculo de la integral de simpson
class simpson{
    // funcion de integracion
    public double F(double x,double dof){
        return Math.pow(1 + (Math.pow(x,2)/dof),-((dof+1)/2));
    }
    // suma de las formulas 4F para los numeros impares entre 1 y N-1
    public double sumImpar(double N, double W, double a){
        double suma = 0.0;
        for (int i=1; i<=N-1; i = i+2){
            suma = suma + (F(a+(i*W),N));
        }
        return 4*suma;
    }
    // suma de las formulas 2F para los numeros pares entre 2 y N-2
    public double sumPar(double N, double W, double a){
        double suma = 0.0;
        for (int i=2; i<=N-2; i = i+2){
            suma = suma + (F(a+(i*W),N));
        }
        return 2*suma;
    }

    //obtenemos la integral utilizando la formula
    public double obtenerIntegral(double W, double N, double a, double b){
        return ((((W/3) * (F(a,N) + sumImpar(N,W,a) + sumPar(N,W,a) + F(b,N)))));
    }

    //metodo para implementar el algoritmo y calcular el valor hasta que sea menor que el error
    public double calcularValor(double a,double b,double N){
        //error solicitado
        double E=0.00001;
        double W = (b-a)/N;
        double resAnterior = 0.0;
        double res = 0.0;
        // aumentamos los grados de libertad para disminuir el error
		if(N<5){
            N = 20.0;
            W = (b-a)/N;
            res = obtenerIntegral(W,N,a,b);
            while(!(Math.abs((res- resAnterior))<=E)){
                resAnterior = res;
                N = 2*N;
                W = (b-a)/N;
                res = obtenerIntegral(W,N,a,b);
            }
        }
        else{
			//controlamos la entrada de grados para evitar numeros impares
            if(N%2>0){
                N++;
            }
            W = (b-a)/N;
            res = obtenerIntegral(W,N,a,b);
        }
        return res;
    }
}