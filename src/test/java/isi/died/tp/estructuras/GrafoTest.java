package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import isi.died.tp.model.*;

public class GrafoTest {

	GrafoPlanta grafo;
	private Planta plantaA;
	private Planta plantaB;
	private Planta plantaC;
	private Planta plantaD;
	private Planta plantaE;
	private Planta plantaF;
	private Insumo InsumoA;
	private Insumo InsumoB;
	private Insumo InsumoC;
	private Insumo InsumoD;
	private Insumo InsumoE;
	private Insumo InsumoF;

	Grafo<Integer> grafo2 = new Grafo<Integer>();

	@Before
	public void init() {
		
		grafo2.addNodo(0);
		grafo2.addNodo(2);
		grafo2.addNodo(3);
		grafo2.addNodo(4);
		grafo2.addNodo(6);
		grafo2.conectar(0, 2);
		grafo2.conectar(0, 3);
		grafo2.conectar(2, 3);
		grafo2.conectar(2, 4);
		grafo2.conectar(6, 3);


		plantaA = new PlantaAcopio(1, "A");
		plantaB = new PlantaProduccion(2, "B");
		plantaC = new PlantaProduccion(3, "C");
		plantaD = new PlantaProduccion(4, "D");
		plantaE = new PlantaProduccion(5, "E");
		plantaF = new PlantaAcopio(6, "F");

		// CostoTotal PlantaA = 140928.0
		InsumoA = new Insumo(1, 30, 500); 
		InsumoB = new Insumo(2, 300, 20);
		InsumoC = new Insumo(3, 140, 39);
		InsumoD = new Insumo(4, 600, 10);
		InsumoE = new Insumo(5, 1000, 100);
		InsumoF = new Insumo(6, 292, 29);

		plantaA.addInsumo(InsumoA);
		plantaA.addInsumo(InsumoB);
		plantaA.addInsumo(InsumoC);
		plantaA.addInsumo(InsumoD);
		plantaA.addInsumo(InsumoE);
		plantaA.addInsumo(InsumoF);

		plantaB.addStock(1, 3, 5, InsumoA); //Necesita 2
		plantaB.addStock(2, 5, 10, InsumoB);//Necesita 5
		//CostoTotal PlantaB = 1590.0

		plantaC.addStock(3, 30, 10, InsumoC);
		plantaC.addStock(9, 5, 7, InsumoE); //Necesita 2  
		plantaC.addStock(10, 3, 5, InsumoA); //Necesita 2
		//CostoTotal PlantaC = 9290.0

		plantaD.addStock(4, 5, 10, InsumoB); //Necesita 5
		plantaD.addStock(5, 4, 15, InsumoD); //Necesita 9
		//CostoTotal PlantaD = 3900.0

		plantaE.addStock(6, 4, 15, InsumoD); //Necesita 9
		plantaE.addStock(7, 35, 50, InsumoE); //Necesita 15
		plantaE.addStock(8, 25, 10, InsumoF); //CostoTotal PlantaE = 44700.0


		grafo = new GrafoPlanta();

		/*					850km
		 *      -> B -----------------> D 
		 *350km/   ^ \                  |
		 *    /    |  \                 |
		 *   A     |   \ 800km          / 750km
		 *    \    |    \----          /
		 *450km\   |600km   |         /
		 *      \  | 	    v        /
		 *       ->C ------>F<-----E<
		 * 			  400km    300km
		 */          

		grafo.addNodo(plantaA);
		grafo.addNodo(plantaB);
		grafo.addNodo(plantaC);
		grafo.addNodo(plantaD);
		grafo.addNodo(plantaE);
		grafo.addNodo(plantaF);
		grafo.conectar(plantaA, plantaB,350);
		grafo.conectar(plantaA, plantaC,450);
		grafo.conectar(plantaB, plantaF,800);
		grafo.conectar(plantaB, plantaD,850);
		grafo.conectar(plantaC, plantaB,600);
		grafo.conectar(plantaC, plantaF,400);
		grafo.conectar(plantaD, plantaE,750);
		grafo.conectar(plantaE, plantaF,300);


	}


	@Test
	public void testEsCompleto() {

		assertFalse(grafo.esCompleto());

		grafo.conectar(plantaB, plantaE,950);

		grafo.conectar(plantaD, plantaF,900);

		grafo.conectar(plantaC, plantaD,1050);
		grafo.conectar(plantaC, plantaE,700);

		grafo.conectar(plantaA, plantaF,800);
		grafo.conectar(plantaA, plantaD,1000);
		grafo.conectar(plantaA, plantaE,800);

		assertTrue(grafo.esCompleto());
	}


	@Test
	public void testExcentricidad() {

		assertTrue(grafo.excentricidad(grafo.getNodo(plantaA)) == 1950);

		assertTrue(grafo.excentricidad(grafo.getNodo(plantaB)) == 1600);

		grafo.conectar(plantaB, plantaE,950);

		assertTrue(grafo.excentricidad(grafo.getNodo(plantaB)) == 950);
		assertTrue(grafo.excentricidad(grafo.getNodo(plantaA)) == 1300);

		assertTrue(grafo.excentricidad(grafo.getNodo(plantaF)) == 0);

	}

	@Test	
	public void testExisteCaminoRec() {

		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaA),0));
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaF),4));
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaB),0));
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaB),1));
		
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaC),2));
		
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaF),2));
		
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaB),grafo.getNodo(plantaE),1));

	}
	
	
	@Test	
	public void testExisteCaminoRec2() {

		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaA),1,100));
		
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaF),2,100));
		
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaF),4,850));
		
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaB),1,100));
		
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaB),1,350));
		
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaA),grafo.getNodo(plantaC),1,100));
		
		assertFalse(grafo.existeCaminoRec(grafo.getNodo(plantaB),grafo.getNodo(plantaE),2,100));
		
		assertTrue(grafo.existeCaminoRec(grafo.getNodo(plantaB),grafo.getNodo(plantaE),2,1600));
	}
	
	
	@Test	
	public void testExisteCaminoRecCond() {

		
		
		Condicion<Integer> cond = (e) -> (e%2 == 0);
		assertTrue(grafo2.existeCaminoRecCond(grafo2.getNodo(0),grafo2.getNodo(4),2, cond));
		assertFalse(grafo2.existeCaminoRecCond(grafo2.getNodo(0),grafo2.getNodo(6),3, cond));
		grafo2.conectar(4, 6);
		assertTrue(grafo2.existeCaminoRecCond(grafo2.getNodo(0),grafo2.getNodo(6),3, cond));
		grafo2.conectar(2, 6);
		assertTrue(grafo2.existeCaminoRecCond(grafo2.getNodo(0),grafo2.getNodo(6),2, cond));
		
		assertFalse(grafo2.existeCaminoRecCond(grafo2.getNodo(0),grafo2.getNodo(3),4, cond));
		
	}
	

	@Test	
	public void testBuscarCaminosCond() {

		grafo2.conectar(2, 6);
		grafo2.conectar(4, 6);
		grafo2.addNodo(8);
		grafo2.conectar(6, 8);
		grafo2.conectar(0, 8);

		Condicion<Integer> cond = (e) -> (e%2 == 0);
		List<List<Vertice<Integer>>> caminos = grafo2.buscarCaminosCond(0,6,2, cond);
		List<Vertice<Integer>> lista = new ArrayList<Vertice<Integer>>();
		lista.add(new Vertice<Integer>(0)); lista.add(new Vertice<Integer>(2)); lista.add(new Vertice<Integer>(6)); 

		assertTrue(caminos.size() == 1 && caminos.get(0).equals(lista));


		List<Vertice<Integer>> lista2 = new ArrayList<Vertice<Integer>>();
		lista2.add(new Vertice<Integer>(0)); lista2.add(new Vertice<Integer>(2)); lista2.add(new Vertice<Integer>(4));  lista2.add(new Vertice<Integer>(6)); 

		List<Vertice<Integer>> lista3 = new ArrayList<Vertice<Integer>>();
		lista3.add(new Vertice<Integer>(0)); lista3.add(new Vertice<Integer>(2)); lista3.add(new Vertice<Integer>(6)); 

		caminos = grafo2.buscarCaminosCond(0,6,3, cond);

		assertTrue(caminos.size() == 2 && caminos.get(0).equals(lista2) && caminos.get(1).equals(lista3));

		List<Vertice<Integer>> lista4 = new ArrayList<Vertice<Integer>>();
		lista4.add(new Vertice<Integer>(0)); lista4.add(new Vertice<Integer>(8));
		caminos = grafo2.buscarCaminosCond(0,8,2, cond);

		assertTrue(caminos.size() == 1 && caminos.get(0).equals(lista4));

		//System.out.println(caminos.toString());
		caminos = grafo2.buscarCaminosCond(0,8,4, cond);

		assertTrue(caminos.size() == 3);
	}

	
	@Test	
	public void testExisteCaminoRecCondSaltoImpar() {

		
		Condicion<Integer> cond = (e) -> (e%2 == 0);
		assertTrue(grafo2.existeCaminoRecCondSaltoImpar(grafo2.getNodo(0), cond));
		assertTrue(grafo2.existeCaminoRecCondSaltoImpar(grafo2.getNodo(2), cond));
		assertFalse(grafo2.existeCaminoRecCondSaltoImpar(grafo2.getNodo(3), cond));
		grafo2.addNodo(8);
		grafo2.conectar(3, 8);
		assertFalse(grafo2.existeCaminoRecCondSaltoImpar(grafo2.getNodo(6), cond));
		grafo2.conectar(3, 0);
		assertTrue(grafo2.existeCaminoRecCondSaltoImpar(grafo2.getNodo(6), cond));
	}

	
	
	@Test	
	public void testAlcanzarSumideros() {
		
		assertTrue(grafo.alcanzarSumideros(grafo.getNodo(plantaA),1) == 0);
		
		assertTrue(grafo.alcanzarSumideros(grafo.getNodo(plantaA),2) == 1);
		
		assertTrue(grafo.alcanzarSumideros(grafo.getNodo(plantaA),3) == 1);

		assertTrue(grafo.alcanzarSumideros(grafo.getNodo(plantaA),4) == 1);
		
		assertTrue(grafo.alcanzarSumideros(grafo.getNodo(plantaA),5) == 1);
	}

}
