package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.view.GestionEntidades;
import isi.died.tp.view.GestionEnvios;

public class MenuController {

	private GestionEntidades interfazGestionEntidades;
	private GestionEnvios interfazGestionEnvios;
	
	public MenuController(JFrame ventana) {
		interfazGestionEntidades = new GestionEntidades(ventana);
		//interfazGestionLogistica = new GestionLogistica(dao, ventana);
		interfazGestionEnvios = new GestionEnvios(ventana);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case GESTION_ENTIDADES: interfazGestionEntidades.mostrarMenu(); break;
		//case GESTION_LOGISTICA: interfazGestionLogistica.mostrarMenu(); break;
		case GESTION_ENVIOS: interfazGestionEnvios.mostrarMenu(); break;
		}
	}
}