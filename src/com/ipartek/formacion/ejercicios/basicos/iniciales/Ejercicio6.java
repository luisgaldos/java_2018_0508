package com.ipartek.formacion.ejercicios.basicos.iniciales;
/*
 * Escribe un programa java que declare una variable B de tipo entero y as�gnale un valor. A continuaci�n muestra un mensaje indicando si el valor de B es positivo o negativo. Consideraremos el 0 como positivo. Utiliza el operador condicional ( ? : ) dentro del println para resolverlo.
Si por ejemplo B = 1 la salida ser�
1 es positivo
Si fuese por ejemplo B = -1 la salida ser�:
-1 es negativo
*/
public class Ejercicio6 {

	public static void main(String[] args) {
		//VARIABLES
		int b=-40;
		System.out.println(b+ (b>=0 ? " es positivo ":"es negativo"));
	}

}
