package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.estructuras.*;

public class ArbolBinarioBusquedaTest {

	private ArbolVacio arbol;
	private ArbolBinarioBusqueda<Long> arbol1;
	private ArbolBinarioBusqueda<Long> arbol2;
	private ArbolBinarioBusqueda<Long> arbol3;
	private ArbolBinarioBusqueda<Long> arbol4;
	private ArbolBinarioBusqueda<Long> arbol5;
	private ArbolBinarioBusqueda<Long> arbol6;
	
	@Before
	public void init() {
		arbol = new ArbolVacio();
		arbol1 = new ArbolBinarioBusqueda(0);
		arbol2 = new ArbolBinarioBusqueda(0,new ArbolVacio(),new ArbolBinarioBusqueda(1));
		arbol3 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1),new ArbolVacio());
		arbol4 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4,new ArbolVacio(), new ArbolVacio()),new ArbolBinarioBusqueda(28)), new ArbolBinarioBusqueda(13,new ArbolBinarioBusqueda(43),new ArbolBinarioBusqueda(33)));
		arbol5 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4),new ArbolBinarioBusqueda(28)), new ArbolVacio());
		arbol6 = new ArbolBinarioBusqueda(0,new ArbolBinarioBusqueda(1,new ArbolBinarioBusqueda(4),new ArbolBinarioBusqueda(28)), new ArbolBinarioBusqueda(304934));
		
	}
	
	@Test
	public void testContiene() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsArbolOfE() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgregar() {
		fail("Not yet implemented");
	}

	@Test
	public void testProfundidad() {
		assertTrue(arbol.profundidad().equals(0));
		assertTrue(arbol4.profundidad().equals(2));
	}

	@Test
	public void testCuentaNodosDeNivel() {
		assertTrue(arbol1.esCompleto());
	}

	@Test
	public void testEsCompleto() {
		assertTrue(arbol1.esCompleto());
		assertFalse(arbol2.esCompleto());
		assertTrue(arbol3.esCompleto());
		assertTrue(arbol4.esCompleto());
		assertFalse(arbol5.esCompleto());
		assertTrue(arbol6.esCompleto());
	}

	@Test
	public void testEsLleno() {
		assertTrue(arbol1.esLleno());
		assertFalse(arbol2.esLleno());
		assertFalse(arbol3.esLleno());
		assertTrue(arbol4.esLleno());
		assertFalse(arbol5.esLleno());
		assertFalse(arbol6.esLleno());
	}

}
