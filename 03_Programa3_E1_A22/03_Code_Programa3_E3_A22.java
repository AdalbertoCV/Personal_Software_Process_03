/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
*Número de Programa: 3
*Nombre: Adalberto Cerrillo Vázquez
*Fecha: 03/Octubre/2022
*Descripción: Programa para obtener el valor de la correlacion entre dos conjuntos de datos.
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

import java.lang.Math;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Programa3{
    public static void main(String[] args)throws Exception{
	    correlacion cor = new correlacion();
		Scanner teclado = new Scanner(System.in);
		System.out.println("Ingrese el path de el archivo a leer. (Ejemplo: PSP/Programa3.java)");
		String archivo = teclado.nextLine();
		ListaEnlazada x = new ListaEnlazada();
		ListaEnlazada y = new ListaEnlazada();
		cor.leerArchivo(archivo,x,y);
		double productos, sumax, sumay, sumaxcuadrada, sumaycuadrada;
		productos = cor.sumaProductos(x,y);
		sumax = cor.sumatoriaVariable(x);
		sumay = cor.sumatoriaVariable(y);
		sumaxcuadrada = cor.sumaCuadrados(x);
		sumaycuadrada = cor.sumaCuadrados(y);
		cor.imprimirSumatorias(sumaxcuadrada,sumaycuadrada, sumax, sumay, productos);
		double resultado = cor.calcularCoeficiente(sumaxcuadrada,sumaycuadrada, sumax, sumay, productos, x.numElementos());
		System.out.println("Valor de correlacion: " + resultado);
	}
}

class correlacion{
    // se lee el archivo que el usuario selecciono.
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
	
	// se obtiene la suma de los productos de x e y 
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
	
	// se obtiene la sumatoria de x o y
	public double sumatoriaVariable(ListaEnlazada valores){
	    double suma = 0.0;
        Nodo iter = valores.getPrimero();
        while (iter!=null){
            suma = suma + (Double.parseDouble((String)iter.getContenido()));
            iter = iter.getNodoDer();
        }
        return suma;
	}

    // se obtiene la suma de los cuadrados de x o y
    public double sumaCuadrados(ListaEnlazada valores){
        double suma = 0.0;
        Nodo iter = valores.getPrimero();
        while (iter!=null){
            suma = suma + (Double.parseDouble((String)iter.getContenido()) * Double.parseDouble((String)iter.getContenido()));
            iter = iter.getNodoDer();
        }
        return suma;
    }
	
	// se imprimen todas las sumatorias obtenidas
	public void imprimirSumatorias(double cuadradox, double cuadradoy, double sumatoriax, double sumatoriay, double productos){
        System.out.println("Ex = " + sumatoriax);
		System.out.println("Ey = " + sumatoriay);
		System.out.println("Exy = " + productos);
		System.out.println("Ex^2 = " + cuadradox);
		System.out.println("Ey^2 = " + cuadradoy);
	}
	
	// se obtiene el valor del coeficiente
	public double calcularCoeficiente(double cuadradox, double cuadradoy, double sumatoriax, double sumatoriay, double productos, int n){
	    return ((n*productos)-(sumatoriax*sumatoriay))/Math.sqrt(((n*cuadradox)-(Math.pow(sumatoriax,2))) * ((n*cuadradoy)-Math.pow(sumatoriay,2)));
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