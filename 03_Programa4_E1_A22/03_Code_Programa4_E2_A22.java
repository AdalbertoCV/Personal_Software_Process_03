/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
*Número de Programa: 4
*Nombre: Adalberto Cerrillo Vázquez
*Fecha: 17/Octubre/2022
*Descripción: Programa para obtener el valor de la integral entre dos limites por el metodo de simpson
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

import java.util.Scanner;
import java.lang.Math;
//clase programa 4 tipo I/O
public class Programa4{
	public static void main(String args[]){
	    Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el limite inferior. (Ingrese (-inf)) para menos infinito.");
		String li = entrada.nextLine();
		Double a = 0.0;
		//Si se ingresa un numero se toma como el limite inferior
		try{
		    a = Double.parseDouble(li);
		}
		//en caso de no ingresarse un numero se verifica que se ingrese el -inf
		catch(Exception e){
		    if (!(li.equalsIgnoreCase("-inf"))){
			    a = null;
			}
		}
		System.out.println("Ingrese el limite superior.");
		double b = entrada.nextDouble();
		simpson integrar = new simpson();
		System.out.println("Valor de la integral: " + integrar.calcularValor(a,b));
	}
}

//clase de calculo de la integral de simpson
class simpson{
    // funcion de distribucion normal
    public double F(double x){
	    return Math.exp(-Math.pow(x,2)/2)/Math.sqrt(2* 3.1416);
	}
	// suma de las formulas 4F para los numeros impares entre 1 y N-1
	public double sumImpar(double N, double W, double a){
	    double suma = 0.0;
		for (int i=1; i<N-1; i = i+2){
		    suma = suma + (4*F(a+(i*W)));
		}
		return suma;
	}
	// suma de las formulas 2F para los numeros impares entre 2 y N-2
	public double sumPar(double N, double W, double a){
	    double suma = 0.0;
		for (int i=2; i<N-2; i = i+2){
		    suma = suma + (2*F(a+(i*W)));
		}
		return suma;
	}
	
	//obtenemos la integral utilizando la formula
	public double obtenerIntegral(double W, double N, double a, double b){
	    return ((W/3) * (F(a) + sumImpar(N,W,a) + sumPar(N,W,a) + F(b))) + 0.5;
	}
	
	//metodo para implementar el algoritmo y calcular el valor hasta que sea menor que el error
	public double calcularValor(double a,double b){
	    //error solicitado
	    double E=0.00001;
		double N = 20.0;
		double W = (b-a)/N;
		double res = 0.0;
		double resAnterior = 0.0;
		res = obtenerIntegral(W,N,a,b);
		while(!(Math.abs((res- resAnterior))<=E)){
		    resAnterior = res;
			N = 2*N;
			W = (b-a)/N;
			res = obtenerIntegral(W,N,a,b);
		}
		return res;
	}
}
