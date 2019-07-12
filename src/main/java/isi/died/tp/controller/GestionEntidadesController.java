package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.view.ABMCamion;
import isi.died.tp.view.ABMInsumo;
import isi.died.tp.view.ABMPlantaAcopio;
import isi.died.tp.view.ABMPlantaProduccion;
import isi.died.tp.view.ABMStock;

public class GestionEntidadesController {
	private ABMInsumo interfazInsumo;
	private ABMPlantaAcopio interfazPlantaAcopio;
	private ABMPlantaProduccion interfazPlantaProduccion;
	private ABMStock interfazStock;
	private ABMCamion interfazCamion;
	
	
	public GestionEntidadesController(InsumoDao dao, JFrame ventana) {
		interfazInsumo = new ABMInsumo(dao, ventana);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case AGREGAR_INSUMO: interfazInsumo.agregarInsumo(); break;
		case MODIFICAR_INSUMO: interfazInsumo.modificarInsumo(); break;
		}
	}
}
