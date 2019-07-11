package isi.died.tp.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isi.died.tp.estructuras.GrafoPlanta;
import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.*;

public class PlantaServiceTest {
	PlantaService gps;
	Camion camion;
	List<StockAcopio> listaDisponibles; 
	
	GrafoPlanta grafo;
	private Planta plantaA;
	private Planta plantaB;
	private Planta plantaC;
	private Planta plantaD;
	private Planta plantaE;
	private Planta plantaF;
	
	@Before
	public void init() {
	
		gps = new PlantaServiceDefault();
		camion = new Camion(1, 20, false, 2);
		
		listaDisponibles = new ArrayList<StockAcopio>();
		listaDisponibles.add(new StockAcopio(1, 1, new Insumo(1, null, null, 10, 1, false), new PlantaProduccion(1, null)));
		listaDisponibles.add(new StockAcopio(2, 1, new Insumo(2, null, null, 20, 1, false), new PlantaProduccion(2, null)));
		listaDisponibles.add(new StockAcopio(3, 1, new Insumo(3, null, null, 30, 1, false), new PlantaProduccion(3, null)));

		plantaA = new PlantaAcopio(1, "A");
		plantaB = new PlantaProduccion(2, "B");
		plantaC = new PlantaProduccion(3, "C");
		plantaD = new PlantaProduccion(4, "D");
		plantaE = new PlantaProduccion(5, "E");
		plantaF = new PlantaAcopio(6, "F");
		
		grafo = new GrafoPlanta();
			
			/*					850T
			 *      -> B -----------------> D 
			 *350T /   ^ \                  |
			 *    /    |  \                 |
			 *   A     |   \ 800T           / 750T
	 	     *    \    |    \----          /
	         *450T \   |600T    |         /
	         *      \  | 	    v        /
	 		 *       ->C ------>F<-----E<
			 * 			  400T    300T
			 */          
			
			grafo.addNodo(plantaA);
			grafo.addNodo(plantaB);
			grafo.addNodo(plantaC);
			grafo.addNodo(plantaD);
			grafo.addNodo(plantaE);
			grafo.addNodo(plantaF);
		grafo.conectar(plantaA, plantaB,350,0,350);
		grafo.conectar(plantaA, plantaC,450,0,450);
		grafo.conectar(plantaB, plantaF,800,0,800);
		grafo.conectar(plantaB, plantaD,850,0,850);
		grafo.conectar(plantaC, plantaB,600,0,600);
		grafo.conectar(plantaC, plantaF,400,0,400);
		grafo.conectar(plantaD, plantaE,750,0,750);
		grafo.conectar(plantaE, plantaF,300,0,300);

	}
	
	
	@Test
	public void testMejorSeleccionEnvio() {
		
		List<StockAcopio> listaSeleccion = gps.generarMejorSeleccionEnvio(camion, listaDisponibles);
		assertTrue(listaSeleccion.size() == 2 && listaSeleccion.get(0).getInsumo().getCosto()+listaSeleccion.get(1).getInsumo().getCosto() == 50);
		
		listaDisponibles = new ArrayList<StockAcopio>();
		listaDisponibles.add(new StockAcopio(1, 1, new Insumo(1, null, null, 10, 2, false), new PlantaProduccion(1, null)));
		listaDisponibles.add(new StockAcopio(2, 1, new Insumo(2, null, null, 20, 1, false), new PlantaProduccion(2, null)));
		listaDisponibles.add(new StockAcopio(3, 1, new Insumo(3, null, null, 30, 3, false), new PlantaProduccion(3, null)));
		listaSeleccion = gps.generarMejorSeleccionEnvio(camion, listaDisponibles);
		assertTrue(listaSeleccion.size() == 1 && listaSeleccion.get(0).getInsumo().getCosto() == 20);

	}
	
	@Test
	public void FlujoMaximoRedTest(){
		
		int flujoMaximo = 0;
    	int flujoActual = Integer.MAX_VALUE;
    	
    	while(flujoActual > 0) {
    		flujoActual = grafo.buscarProximoFlujoDisponible(grafo.getNodo(plantaA));
    		System.out.println(flujoActual);
    		flujoMaximo = flujoMaximo + flujoActual;

        }
        
		assertTrue(flujoMaximo == 800);
		
	}
}
