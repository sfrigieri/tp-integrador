package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.view.ABMCamion;
import isi.died.tp.view.ABMInsumo;
import isi.died.tp.view.ABMPlantaAcopio;
import isi.died.tp.view.ABMPlantaProduccion;
import isi.died.tp.view.ABMStock;
import isi.died.tp.view.GestionEntidades;

public class MenuController {

	private GestionEntidades interfazGestionEntidades;
	
	public MenuController(InsumoDao dao, JFrame ventana) {
		interfazGestionEntidades = new GestionEntidades(dao, ventana);
		//interfazGestionLogistica = new GestionLogistica(dao, ventana);
		//interfazGestionEnvios = new GestionEnvios(dao, ventana);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case GESTION_ENTIDADES: interfazGestionEntidades.mostrarMenu(); break;
		//case GESTION_LOGISTICA: interfazGestionLogistica.mostrarMenu(); break;
		//case GESTION_ENVIOS: interfazGestionEnvios.mostrarMenu(); break;
		}
	}
}
