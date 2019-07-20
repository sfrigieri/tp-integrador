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
	private InsumoController insumoController;
	private StockController stockController;
	
	public GestionEntidadesController(JFrame ventana) {
			
		plantaService = new PlantaServiceDefault();
		insumoService = new InsumoServiceDefault(plantaService);
		insumoController = new InsumoController(insumoService);
		stockService = new StockServiceDefault(plantaService, insumoService);
		stockController = new StockController(stockService);
		//Luego, internamente, se asigna rutaService llamando a PlantaService:setRutaService(rs)
		rutaService = new RutaServiceDefault(plantaService);


		interfazInsumo = new ABMInsumo(ventana, insumoController, stockController);
		plantaService.setInsumoService(insumoService);
		plantaService.setStockService(stockService);
		plantaService.setRutaService(rutaService);
		interfazPlanta = new ABMPlanta(ventana, plantaService);
		
		interfazRuta = new ABMRuta(ventana,plantaService);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case AGREGAR_INSUMO: interfazInsumo.agregarInsumo(); break;
		case MODIFICAR_INSUMO: interfazInsumo.modificarInsumo(); break;
		case ELIMINAR_INSUMO: interfazInsumo.eliminarInsumo(true); break;
		
		case AGREGAR_PLANTA: interfazPlanta.agregarPlanta(); break;
		case MODIFICAR_PLANTA: interfazPlanta.modificarPlanta(); break;
		
		case AGREGAR_RUTA: interfazRuta.agregarRuta(); break;
		}
	}
}
