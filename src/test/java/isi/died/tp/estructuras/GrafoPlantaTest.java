package isi.died.tp.estructuras;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.model.*;


public class GrafoPlantaTest {

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
	
	
	@Before
	public void init() {
		
		plantaA = new PlantaAcopio(1, "A");
		plantaB = new PlantaProduccion(2, "B");
		plantaC = new PlantaProduccion(3, "C");
		plantaD = new PlantaProduccion(4, "D");
		plantaE = new PlantaProduccion(5, "E");
		plantaF = new PlantaAcopio(6, "F");
		/*
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
*/
		
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
	public void testCostoTotal() {
		
		assertTrue(plantaA.costoTotal() == 140928.0);
		
		assertTrue(plantaB.costoTotal() == 1590.0);
		
		assertTrue(plantaC.costoTotal() == 9290.0);
		
		assertTrue(plantaD.costoTotal() == 3900.0);
		
		assertTrue(plantaE.costoTotal() == 44700.0);
		
		assertTrue(plantaF.costoTotal() == 0.0);


	}
	
	@Test
	public void testStockEntre() {
		
		List<Insumo> listaA = new ArrayList<Insumo>();
		assertTrue(plantaA.stockEntre(0,5).equals(listaA));
		listaA.add(InsumoD);
		assertTrue(plantaA.stockEntre(0,10).equals(listaA));
		
		List<Insumo> listaB = new ArrayList<Insumo>();
		listaB.add(InsumoA);
		assertTrue(plantaB.stockEntre(0,3).equals(listaB));
		listaB.add(InsumoB);
		assertTrue(plantaB.stockEntre(0,5).equals(listaB));
		
		List<Insumo> listaC = new ArrayList<Insumo>();
		listaC.add(InsumoA);
		assertTrue(plantaC.stockEntre(0,3).equals(listaC));
		listaC.remove(InsumoA);
		listaC.add(InsumoC);
		assertTrue(plantaC.stockEntre(10,40).equals(listaC));
		listaC.add(InsumoE);
		assertTrue(plantaC.stockEntre(4,40).equals(listaC));
	}
	
	
	@Test
	public void testNecesitaInsumo() {
		
		assertFalse(plantaA.necesitaInsumo(InsumoA));
		
		assertTrue(plantaB.necesitaInsumo(InsumoA));
		
		assertTrue(plantaC.necesitaInsumo(InsumoE));
		
		assertFalse(plantaC.necesitaInsumo(InsumoC));
		
		assertTrue(plantaD.necesitaInsumo(InsumoD));
		
		assertFalse(plantaE.necesitaInsumo(InsumoF));
		
		assertFalse(plantaF.necesitaInsumo(InsumoF));

	}
	
	
	@Test
	public void testBuscarPlantaA() {

	 	Planta p = grafo.buscarPlanta(plantaA, InsumoE, 3);
		assertTrue(p.equals(plantaE));
		
		p = grafo.buscarPlanta(plantaA, InsumoE, 1);
		assertTrue(p.equals(plantaC));
		
		p = grafo.buscarPlanta(plantaA, InsumoE, 0);
		assertTrue(p == null);
	
	}
	
	@Test
	public void testBuscarPlantaB() {
		Planta p = grafo.buscarPlanta(plantaA, InsumoA);
		assertTrue(p.equals(plantaB));
		
		p = grafo.buscarPlanta(plantaA, InsumoB);
		assertTrue(p.equals(plantaB));
		
		p = grafo.buscarPlanta(plantaA, InsumoC);
		assertTrue(p == null);
		
		p = grafo.buscarPlanta(plantaA, InsumoD);
		assertTrue(p.equals(plantaD));
		
		//plantaC es la más cercana, aunque PlantaE necesite más.
		p = grafo.buscarPlanta(plantaA, InsumoE);
		assertTrue(p.equals(plantaC));

	}
	
	@Test
	public void testBuscarPlantaC() {
		Planta p = grafo.buscarPlanta(InsumoA);
		assertTrue(p.equals(plantaB));
		
		p = grafo.buscarPlanta(InsumoB);
		assertTrue(p.equals(plantaB));
		
		p = grafo.buscarPlanta(InsumoC);
		assertTrue(p == null);
		
		p = grafo.buscarPlanta(InsumoD);
		assertTrue(p.equals(plantaD));
		
		//plantaE es la que más necesita, aunque PlantaC esté más cerca.
		p = grafo.buscarPlanta(InsumoE);
		assertTrue(p.equals(plantaE));
		
		p = grafo.buscarPlanta(InsumoF);
		assertTrue(p == null);

	}

}
