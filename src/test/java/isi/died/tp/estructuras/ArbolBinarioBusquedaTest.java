package isi.died.tp.estructuras;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class ArbolBinarioBusquedaTest {

	private ArbolVacio<Integer> arbol;
	private ArbolBinarioBusqueda<Integer> arbol1;
	private ArbolBinarioBusqueda<Integer> arbol2;
	private ArbolBinarioBusqueda<Integer> arbol3;
	private ArbolBinarioBusqueda<Integer> arbol4;
	private ArbolBinarioBusqueda<Integer> arbol5;
	private ArbolBinarioBusqueda<Integer> arbol6;
	
	@Before
	public void init() {
		arbol = new ArbolVacio<Integer>();
		arbol1 = new ArbolBinarioBusqueda<Integer>(0);
		arbol2 = new ArbolBinarioBusqueda<Integer>(0,new ArbolVacio<Integer>(),new ArbolBinarioBusqueda<Integer>(1));
		arbol3 = new ArbolBinarioBusqueda<Integer>(0,new ArbolBinarioBusqueda<Integer>(1),new ArbolVacio<Integer>());
		arbol4 = new ArbolBinarioBusqueda<Integer>(0,new ArbolBinarioBusqueda<Integer>(1,new ArbolBinarioBusqueda<Integer>(4,new ArbolVacio<Integer>(), new ArbolVacio<Integer>()),new ArbolBinarioBusqueda<Integer>(28)), new ArbolBinarioBusqueda<Integer>(13,new ArbolBinarioBusqueda<Integer>(43),new ArbolBinarioBusqueda<Integer>(33)));
		arbol5 = new ArbolBinarioBusqueda<Integer>(0,new ArbolBinarioBusqueda<Integer>(1,new ArbolBinarioBusqueda<Integer>(4),new ArbolBinarioBusqueda<Integer>(28)), new ArbolVacio<Integer>());
		arbol6 = new ArbolBinarioBusqueda<Integer>(0,new ArbolBinarioBusqueda<Integer>(1,new ArbolBinarioBusqueda<Integer>(4),new ArbolBinarioBusqueda<Integer>(28)), new ArbolBinarioBusqueda<Integer>(304934));
		
	}
	
	@Test
	public void testContiene() {
		assertFalse(arbol.contiene(28));
		assertTrue(arbol4.contiene(28));
		assertFalse(arbol4.contiene(5));
	}
	
	@Ignore
	@Test
	public void testEqualsArbolOfE() {
		fail("Not yet implemented");
	}

	@Ignore
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
		assertTrue(arbol4.cuentaNodosDeNivel(0).equals(1));
		assertTrue(arbol4.cuentaNodosDeNivel(1).equals(2));
		assertTrue(arbol4.cuentaNodosDeNivel(2).equals(4));
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
