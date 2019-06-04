package isi.died.tp.estructuras;

import  isi.died.tp.estructuras.*;

public class App {

	public static void main(String[] args) {
		
	final ArbolVacio arbol = new ArbolVacio();
	final ArbolBinarioBusqueda<Integer> arbol1 = new ArbolBinarioBusqueda(0);
	final ArbolBinarioBusqueda<Integer> arbol2 = new ArbolBinarioBusqueda(0,new ArbolVacio(),new ArbolBinarioBusqueda(1));
	final ArbolBinarioBusqueda<Integer> arbol3 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1),new ArbolVacio());
	final ArbolBinarioBusqueda<Integer> arbol4 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4,new ArbolVacio(), new ArbolVacio()),new ArbolBinarioBusqueda(28)), new ArbolBinarioBusqueda(13,new ArbolBinarioBusqueda(43),new ArbolBinarioBusqueda(33)));
	final ArbolBinarioBusqueda<Integer> arbol5 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4),new ArbolBinarioBusqueda(28)), new ArbolVacio());
	final ArbolBinarioBusqueda<Integer> arbol6 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4),new ArbolBinarioBusqueda(28)), new ArbolBinarioBusqueda(304934));
	System.out.println("Arbol contiene 28? "+arbol4.contiene(28));
	System.out.println("Arbol contiene 5? "+arbol4.contiene(5));
	System.out.println("Profundidad Arbol Vacio es: "+arbol.profundidad());
	System.out.println("Profundidad Arbol Binario4 es: "+arbol4.profundidad());
	System.out.println("Cantidad de Nodos en Nivel de Arbol Binario4:");
	System.out.println("0 es: "+arbol4.cuentaNodosDeNivel(0));
	System.out.println("1 es: "+arbol4.cuentaNodosDeNivel(1));
	System.out.println("2 es: "+arbol4.cuentaNodosDeNivel(2));
	System.out.println("Arbol Binario1 es Completo?: "+arbol1.esCompleto());
	System.out.println("Arbol Binario1 es Lleno?: "+arbol1.esLleno());
	System.out.println("Arbol Binario2 es Completo?: "+arbol2.esCompleto());
	System.out.println("Arbol Binario2 es Lleno?: "+arbol2.esLleno());
	System.out.println("Arbol Binario3 es Completo?: "+arbol3.esCompleto());
	System.out.println("Arbol Binario3 es Lleno?: "+arbol3.esLleno());
	System.out.println("Arbol Binario4 es Completo?: "+arbol4.esCompleto());
	System.out.println("Arbol Binario4 es Lleno?: "+arbol4.esLleno());
	System.out.println("Arbol Binario5 es Completo?: "+arbol5.esCompleto());
	System.out.println("Arbol Binario5 es Lleno?: "+arbol5.esLleno());
	System.out.println("Arbol Binario6 es Completo?: "+arbol6.esCompleto());
	System.out.println("Arbol Binario6 es Lleno?: "+arbol6.esLleno());
	}

}
