package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.service.*;
import isi.died.tp.view.*;

public class GestionEntidadesController {
	private ABMInsumo interfazInsumo;
	private ABMPlanta interfazPlanta;
	private ABMStock interfazStock;
	private ABMCamion interfazCamion;
	private ABMRuta interfazRuta;
	private PlantaService plantaService;
	private StockService stockService;
	private InsumoService insumoService;
	private RutaService rutaService;
	private CamionService camionService;
	public static InsumoController insumoController;
	public static PlantaController plantaController;
	public static StockController stockController;
	
	public GestionEntidadesController(JFrame ventana) {
			
		plantaService = new PlantaServiceDefault();
		plantaController = new PlantaController(plantaService);
		insumoService = new InsumoServiceDefault(plantaService);
		insumoController = new InsumoController(insumoService);
		stockService = new StockServiceDefault(plantaService, insumoService);
		insumoService.setStockService(stockService);
		plantaService.setInsumoService(insumoService);
		plantaService.setStockService(stockService);
		stockController = new StockController(stockService);
		camionService = new CamionServiceDefault();
		rutaService = new RutaServiceDefault(plantaService, camionService);

		
		
		interfazInsumo = new ABMInsumo(ventana, insumoController, stockController);

		interfazPlanta = new ABMPlanta(ventana, plantaController, stockController);
		
		interfazRuta = new ABMRuta(ventana,rutaService);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case AGREGAR_INSUMO: interfazInsumo.agregarInsumo(); break;
		case MODIFICAR_INSUMO: interfazInsumo.modificarInsumo(); break;
		case ELIMINAR_INSUMO: interfazInsumo.eliminarInsumo(true); break;
		
		case AGREGAR_PLANTA: interfazPlanta.agregarPlanta(); break;
		case MODIFICAR_PLANTA: interfazPlanta.modificarPlanta(); break;
		case ELIMINAR_PLANTA: interfazPlanta.eliminarPlanta(true); break;
		
		case AGREGAR_RUTA: interfazRuta.agregarRuta(); break;
		}
	}
}
