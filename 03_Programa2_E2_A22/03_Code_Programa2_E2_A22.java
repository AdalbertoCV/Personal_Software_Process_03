/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
*Número de Programa: 2
*Nombre: Adalberto Cerrillo Vázquez
*Fecha: 17/Septiembre/2022
*Descripción: Programa para contar lineas de codigo de los programas de PSP.
/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Programa2{
    public static void main (String args[]) throws Exception{
        Scanner entrada = new Scanner(System.in);
        contador contador1 = new contador();
	    System.out.println("Ingrese el path del archivo .java a contar");
	    String archivo = entrada.nextLine();
        contador1.leerClases(archivo);
    }
}

//clase para contar las lineas de cada clase en un archivo
class contador {
    public void leerClases(String nombreArchivo) throws Exception {
        File archivo = new File(nombreArchivo);
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea;
        int contadorLlaves = 0;
        int contLineas = 0;
        int lineasTotales = 0;
        int contmetodos = 0;
        String nombreClase = "";
        boolean claseInicio = false;
		// mientras existan lineas de texto seran procesadas.
        while ((linea = lector.readLine()) != null) { 
            linea = linea.trim();
			if (!linea.isEmpty()) {
			    if (!((linea.charAt(0) == '/')|| (linea.charAt(0) == '*'))){
						lineasTotales++;
						String[] lineaTokenizada = linea.split(" ");
						String ultimaCadena = lineaTokenizada[lineaTokenizada.length - 1];
						//se encuentra una clase publica
						if (lineaTokenizada[0].equals("public") && lineaTokenizada[1].equals("class")) { 
							claseInicio = true;
							nombreClase = lineaTokenizada[2];
							nombreClase = nombreClase.replace("{", "");
						} 
						else {
							//se encuentra una clase
							if (lineaTokenizada[0].equals("class")) { 
								claseInicio = true;
								nombreClase = lineaTokenizada[1];
								nombreClase = nombreClase.replace("{", "");
							} 
							else {
								//se encuentra un metodo o atributo
								if (lineaTokenizada[0].equals("public") || lineaTokenizada[0].equals("private") || lineaTokenizada[0].equals("protected")) {
									if (ultimaCadena.charAt(ultimaCadena.length() - 1) == '{'){
										contmetodos++;
									}
								}
							}
						}
						//suma para calcular el balanceo de llaves (apertura)
						if (ultimaCadena.charAt(ultimaCadena.length() - 1) == '{') { 
							contadorLlaves++;
						}
						//resta para calcular el balanceo de llaves (cierre)
						if (ultimaCadena.charAt(ultimaCadena.length() - 1) == '}') {
							contadorLlaves--;
						}
						if (claseInicio) {
							contLineas++;
						}
						// si se cerro la clase, se imprimen sus datos.
						if (contadorLlaves == 0 && claseInicio) { 
							System.out.println("Clase: " + nombreClase);
							System.out.println("Numero de Metodos: " + contmetodos);
							System.out.println("Numero de lineas: " + contLineas);
							System.out.println("------------------------------");
							claseInicio = false;
							contLineas = 0;
							contmetodos = 0;
						}
				}
            }
        }
        System.out.println("Lineas totales: " +lineasTotales);
    }
}