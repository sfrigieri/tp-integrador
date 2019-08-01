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
	public static CamionController camionController;
	public static RutaController rutaController;
	
	public GestionEntidadesController(JFrame ventana) {
			
		plantaService = new PlantaServiceDefault();
		plantaController = new PlantaController(plantaService);
		insumoService = new InsumoServiceDefault(plantaService);
		insumoController = new InsumoController(insumoService);
		stockService = new StockServiceDefault(plantaService, insumoService);
		plantaService.setInsumoService(insumoService);
		plantaService.setStockService(stockService);
		stockController = new StockController(stockService);
		camionService = new CamionServiceDefault();
		rutaService = new RutaServiceDefault(plantaService, camionService);
		camionController = new CamionController(camionService);
		rutaController = new RutaController(rutaService);
		
		interfazInsumo = new ABMInsumo(ventana, insumoController, stockController);

		interfazPlanta = new ABMPlanta(ventana, plantaController, stockController);
		
		interfazStock = new ABMStock(ventana, stockController, plantaController, insumoController);
		
		interfazCamion = new ABMCamion(ventana, camionController);
		
		interfazRuta = new ABMRuta(ventana, rutaController, plantaController);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case AGREGAR_INSUMO: interfazInsumo.agregarInsumo(); break;
		case MODIFICAR_INSUMO: interfazInsumo.modificarInsumo(); break;
		case ELIMINAR_INSUMO: interfazInsumo.eliminarInsumo(true); break;
		
		case AGREGAR_PLANTA: interfazPlanta.agregarPlanta(); break;
		case MODIFICAR_PLANTA: interfazPlanta.modificarPlanta(); break;
		case ELIMINAR_PLANTA: interfazPlanta.eliminarPlanta(true); break;
		
		case AGREGAR_STOCK: interfazStock.agregarStock(); break;
		case MODIFICAR_STOCK: interfazStock.modificarStock(); break;
		case ELIMINAR_STOCK: interfazStock.eliminarStock(true); break;
		
		case AGREGAR_CAMION: interfazCamion.agregarCamion(); break;
		case MODIFICAR_CAMION: interfazCamion.modificarCamion(); break;
		case ELIMINAR_CAMION: interfazCamion.eliminarCamion(); break;
		
		case AGREGAR_RUTA: interfazRuta.agregarRuta(); break;
		}
	}
}
