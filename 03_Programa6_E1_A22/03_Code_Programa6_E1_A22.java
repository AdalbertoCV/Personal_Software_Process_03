/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
*Número de Programa: 6
*Nombre: Adalberto Cerrillo Vázquez
*Fecha: 17/Noviembre/2022
*Descripción: Programa para obtener los intervalos de prediccion.
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.lang.Math;

//Clase programa 6 de tipo I/O 
public class Programa6{
    public static void main (String[] args) throws Exception {
        Scanner t = new Scanner(System.in);
        System.out.println("Ingrese la direccion absoluta del archivo con datos:");
        coeficientes c = new coeficientes();
        ListaEnlazada x = new ListaEnlazada();
        ListaEnlazada y = new ListaEnlazada();
        String archivo =t.nextLine();
        boolean archivoLeido = false;
		// pedimos la ruta del archivo de texto, si falla mostramos un mensaje de error
        while(!archivoLeido){
            try{
                c.leerArchivo(archivo,x,y);
                archivoLeido = true;
            }
            catch(Exception e){
                System.out.println("Archivo no encontrado, Intentelo de nuevo.");
                System.out.println("Ingrese la direccion absoluta del archivo con datos:");
                archivo =t.nextLine();
            }
        }
		System.out.println("Ingrese el valor para xK: ");
		double xk = t.nextDouble();
        double n = x.numElementos()* 1.0;
        double mediax = c.obtenerMedia(x);
        ListaEnlazada coef = c.obtenerCoeficientes(x,y);
        Nodo iter = coef.getPrimero();
        double b0 = (double) iter.getContenido();
        iter = iter.getNodoDer();
        double b1 = (double) iter.getContenido();
        intervalos i = new intervalos();
        double yk = i.yk(b0,b1,xk);
        double rango = i.rango(n,x,y,b0,b1,mediax,xk);
        double UPI = yk + rango;
        double LPI = yk - rango;
		// se imprimen todos los datos
        System.out.println("B0: " + b0);
        System.out.println("B1: " + b1);
        System.out.println("yk: " + yk);
        System.out.println("Range: " + rango);
        System.out.println("UPI: " + UPI);
        System.out.println("LPI: " + LPI);
    }
}

//clase para calcular los intervalos de prediccion
class intervalos{
	//metodo para calcular la prediccion mejorada yk
    public double yk(double b0, double b1,double xk){
        return b0 + (b1*xk);
    }

    //calculamos el rango de prediccion del 70% utilizando la formula
    public double rango(double n,ListaEnlazada x, ListaEnlazada y, double b0, double b1, double mediax, double xk){
        //calculamos el valor de x para una integral con el valor de p y dof
		integral i = new integral();
        double t = i.calcularX(0.35,n-2);
        double desv = desviacion(n,x,y,b0,b1);
        double raiz = calcularRaiz(x,mediax,n,xk);
        return t * desv * raiz;
    }

    //calculamos la raiz de la parte derecha de la formula del rango 
    public double calcularRaiz(ListaEnlazada x, double mediax,double n, double xk){
        double resultado = 1 +1/n;
        double dividendo = Math.pow((xk-mediax),2);
        double suma = 0.0;
        Nodo iter = x.getPrimero();
        while (iter!=null){
            suma =  suma + Math.pow((Double.parseDouble((String) iter.getContenido())-mediax),2);
            iter = iter.getNodoDer();
        }
        double divisor = suma;
        resultado = resultado + (dividendo/divisor);
        return Math.sqrt(resultado);
    }

    // calculamos la desviacion eestandar 
    public double desviacion(double n, ListaEnlazada x, ListaEnlazada y, double b0, double b1){
        double resultado = 1/(n-2);
        double sumatoria = 0.0;
        Nodo iterx = x.getPrimero();
        Nodo itery = y.getPrimero();
        while (iterx!=null){
            sumatoria = sumatoria + Math.pow((Double.parseDouble((String) itery.getContenido()) - b0 - b1 * Double.parseDouble((String) iterx.getContenido())),2);
            iterx = iterx.getNodoDer();
            itery = itery.getNodoDer();
        }
        resultado = resultado * sumatoria;
        return Math.sqrt(resultado);
    }
}

//clase coeficientes, para obtener los coeficientes b0 y b1
class coeficientes{
	// obtenemos los coeficientes b0 y b1 y los regresamos en una pequela lista ligada
    public ListaEnlazada obtenerCoeficientes(ListaEnlazada x, ListaEnlazada y){
        ListaEnlazada res = new ListaEnlazada();
        double sumaProd = sumaProductos(x,y);
        double mediax = obtenerMedia(x);
        double mediay = obtenerMedia(y);
        double sumCuadrados = sumaCuadrados(x);
        int n = x.numElementos();
        double b1 = b1(sumaProd,mediax,mediay,n,sumCuadrados);
        double b0 = b0(b1,mediay,mediax);
        res.agregar(b0);
        res.agregar(b1);
        return res;
    }
	//leemos el archivo de datos
    public void leerArchivo(String nombre, ListaEnlazada x, ListaEnlazada y) throws Exception{
        File archivo = new File(nombre);
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = lector.readLine()) != null){
            String[] valores = linea.split(",");
            x.agregar(valores[0]);
            y.agregar(valores[1]);
        }
    }

    // obtnemos la media de una lista 
    public double obtenerMedia(ListaEnlazada valores){
        int numElementos = valores.numElementos();
        double media = 0.0;
        Nodo iterador = valores.getPrimero();
        while(iterador!=null){
            media = media + Double.parseDouble((String)iterador.getContenido());
            iterador = iterador.getNodoDer();
        }
        return media/numElementos;
    }

    // obtenemos la sumatoria de los productos x*y
    public double sumaProductos(ListaEnlazada x, ListaEnlazada y){
        double suma = 0.0;
        Nodo iterx = x.getPrimero();
        Nodo itery = y.getPrimero();
        while (iterx != null){
            suma = suma + (Double.parseDouble((String)iterx.getContenido()) * Double.parseDouble((String)itery.getContenido()));
            iterx = iterx.getNodoDer();
            itery = itery.getNodoDer();
        }
        return suma;
    }

    //obtnemos la sumatoria de los cuadrados de una variable
    public double sumaCuadrados(ListaEnlazada valores){
        double suma = 0.0;
        Nodo iter = valores.getPrimero();
        while (iter!=null){
            suma = suma + (Double.parseDouble((String)iter.getContenido()) * Double.parseDouble((String)iter.getContenido()));
            iter = iter.getNodoDer();
        }
        return suma;
    }

    // obtenemos el coeficiente b1
    public double b1(double sumaProductos, double mediax, double mediay,int numelems, double sumacuadradosx){
        return (sumaProductos - (numelems*mediax*mediay)) / (sumacuadradosx - (numelems* (mediax*mediax)));

    }

    //obtenemos el coeficiente b0
    public double b0(double b1, double mediay,double mediax){
        return mediay - (b1*mediax);
    }
}

class Nodo{
    private Nodo NodoDer;
    private Object contenido;

    public Nodo(Object cont){
        this.NodoDer = null;
        this.contenido = cont;
    }

    public void setNodoDer(Nodo nodo){
        this.NodoDer = nodo;
    }

    public Nodo getNodoDer(){
        return NodoDer;
    }

    public Object getContenido(){
        return contenido;
    }
}

class ListaEnlazada{
    private Nodo primero;
    private Nodo ultimo;

    public ListaEnlazada(){
        this.primero = null;
        this.ultimo = null;
    }

    public int numElementos(){
        int num = 0;
        Nodo iter = primero;
        while (iter != null){
            num++;
            iter = iter.getNodoDer();
        }
        return num;
    }

    public void agregar(Object contenido){
        Nodo nuevo = new Nodo(contenido);
        if (ultimo==null){
            primero = nuevo;
            ultimo = nuevo;
        }
        else{
            if (numElementos()>1){
                ultimo.setNodoDer(nuevo);
                ultimo = nuevo;
            }
            else{
                ultimo = nuevo;
                primero.setNodoDer(ultimo);
            }
        }
    }

    public Nodo getPrimero(){
        return primero;
    }
}

class integral{
    public double gamma(double z){
        double res = z-1;
        int cont = 2;
        while(cont< z){
            if(z-cont == 0.5){
                res = Math.sqrt(Math.PI) * res * 0.5;
            }
            else{
                res = (z - cont) * res;
            }
            cont++;
        }
        return res;
    }

    // funcion para calcular la parte izquierda de la funcion g
    public double parteIzquierda(double dof){
        return gamma((dof+1)/2)/((Math.sqrt(dof*Math.PI))* gamma(dof/2));
    }

    public double parteDerecha(double x, double dof){
        return Math.pow((1 + ((x*x)/dof)),-((dof+1)/2));
    }

    //funcion para calcular g, (usando simpson)
    public double g(double x,double dof){
        return parteIzquierda(dof) * parteDerecha(x,dof);
    }

    // funcion para calcular el valor de x
    public double calcularX(double p, double dof){
        simpson s = new simpson();
        double x = 1.0;
        double d = 0.5;
        double errorAnterior = 0.0;
        double res = s.calcularValor(dof,x);
        if(res<p){
            x=x+d;
        }
        else{
            x=x-d;
        }
        double error = res-p;
        while((Math.abs(error)>0.00001)){
            if(res>p){
                x-=d;
                res = s.calcularValor(dof,x);
            }
            else{
                x+=d;
                res = s.calcularValor(dof,x);
            }
            errorAnterior = error;
            error = (res-p)/p;
            if((error*errorAnterior)<0){
                d=d/2;
            }
        }
        return x;
    }
}

//clase de calculo de la integral de simpson
class simpson{
    // suma de las formulas 4F para los numeros impares entre 1 y N-1
    public double sumImpar(double[] arreglo, double dof,double N){
        integral ig = new integral();
        double sum = 0.0;
        for (int i = 1; i<N; i = i+2){
            sum = sum + (4*ig.g(arreglo[i],dof));
        }
        return sum;
    }
    // suma de las formulas 2F para los numeros pares entre 2 y N-2
    public double sumPar(double[] arreglo, double dof, double N){
        integral ig = new integral();
        double sum = 0.0;
        for (int i = 2; i<N-1; i = i+2){
            sum = sum + (2*ig.g(arreglo[i],dof));
        }
        return sum;
    }

    public void distribucionNormal(double[] arreglo, double N, double w){
        for (int i =0; i<=N;i++){
            arreglo[i] = (i*w);
        }
    }

    //metodo para implementar el algoritmo y calcular el valor hasta que sea menor que el error
    public double calcularValor(double dof,double x){
        integral i = new integral();
        double N = 20.0;
        double[] nDist = new double[21];
        double W = x/N;
        distribucionNormal(nDist,N,W);
        double t = i.g(0,dof);
        t = t + sumImpar(nDist,dof,N);
        t = t+ sumPar(nDist,dof,N);
        t = t + i.g(nDist[20],dof);
        t = (W/3) * t;
        return t;
    }
}