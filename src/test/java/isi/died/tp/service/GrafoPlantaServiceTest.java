package isi.died.tp.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import isi.died.tp.model.*;

public class GrafoPlantaServiceTest {
	GrafoPlantaService gps;
	Camion camion;
	List<StockAcopio> listaDisponibles; 
	
	@Before
	public void init() {
	
		gps = new GrafoPlantaServiceDefault();
		camion = new Camion(1, 20, false, 2);
		
		listaDisponibles = new ArrayList<StockAcopio>();
		listaDisponibles.add(new StockAcopio(1, 1, new Insumo(1, null, null, 10, 1, false), new PlantaProduccion(1, null)));
		listaDisponibles.add(new StockAcopio(2, 1, new Insumo(2, null, null, 20, 1, false), new PlantaProduccion(2, null)));
		listaDisponibles.add(new StockAcopio(3, 1, new Insumo(3, null, null, 30, 1, false), new PlantaProduccion(3, null)));

		
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
	
}
