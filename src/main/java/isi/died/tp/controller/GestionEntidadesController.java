package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.service.*;
import isi.died.tp.view.*;

public class GestionEntidadesController {
	private ABMInsumo interfazInsumo;
	private ABMPlantaAcopio interfazPlantaAcopio;
	private ABMPlantaProduccion interfazPlantaProduccion;
	private ABMStock interfazStock;
	private ABMCamion interfazCamion;
	private ABMRuta interfazRuta;
	private PlantaService plantaService;
	private StockService stockService;
	private InsumoService insumoService;
	private RutaService rutaService;
	
	public GestionEntidadesController(JFrame ventana) {
			
		plantaService = new PlantaServiceDefault();
		insumoService = new InsumoServiceDefault();
		rutaService = new RutaServiceDefault(plantaService);
		stockService = new StockServiceDefault(plantaService, insumoService);

		interfazInsumo = new ABMInsumo(ventana, insumoService);
		plantaService.setInsumoService(insumoService);
		plantaService.setStockService(stockService);
		plantaService.setRutaService(rutaService);
		//insumoService.setPlantaService(plantaService);
		
		interfazRuta = new ABMRuta(ventana,plantaService);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case AGREGAR_INSUMO: interfazInsumo.agregarInsumo(); break;
		case MODIFICAR_INSUMO: interfazInsumo.modificarInsumo(); break;
		case ELIMINAR_INSUMO: interfazInsumo.eliminarInsumo(); break;
		
		case AGREGAR_RUTA: interfazRuta.agregarRuta(); break;
		}
	}
}
