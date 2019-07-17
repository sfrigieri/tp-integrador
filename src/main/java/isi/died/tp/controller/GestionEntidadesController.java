package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.service.InsumoService;
import isi.died.tp.service.InsumoServiceDefault;
import isi.died.tp.service.PlantaService;
import isi.died.tp.service.PlantaServiceDefault;
import isi.died.tp.service.StockService;
import isi.died.tp.service.StockServiceDefault;
import isi.died.tp.view.ABMCamion;
import isi.died.tp.view.ABMInsumo;
import isi.died.tp.view.ABMPlantaAcopio;
import isi.died.tp.view.ABMPlantaProduccion;
import isi.died.tp.view.ABMRuta;
import isi.died.tp.view.ABMStock;

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
	
	public GestionEntidadesController(JFrame ventana) {
		interfazInsumo = new ABMInsumo(ventana);
		
		plantaService = new PlantaServiceDefault(null,null);
		stockService = new StockServiceDefault(plantaService);
		insumoService = new InsumoServiceDefault();
		
		plantaService.setInsumoService(insumoService);
		plantaService.setStockService(stockService);
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
