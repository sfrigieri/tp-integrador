package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.view.GestionEntidades;
import isi.died.tp.view.GestionEnvios;
import isi.died.tp.view.GestionLogistica;

public class MenuController {

	private GestionEntidades interfazGestionEntidades;
	private GestionEnvios interfazGestionEnvios;
	private GestionLogistica interfazGestionLogistica;
	
	public MenuController(JFrame ventana) {
		interfazGestionEntidades = new GestionEntidades(ventana);
		interfazGestionLogistica = new GestionLogistica(ventana);
		interfazGestionEnvios = new GestionEnvios(ventana);
	}
	
	
	public void opcion(OpcionesMenu op, JFrame ventana) {
		switch(op) {
		case GESTION_ENTIDADES: interfazGestionEntidades.mostrarMenu(); break;
		case GESTION_LOGISTICA: interfazGestionLogistica.mostrarMenu(); break;
		case GESTION_ENVIOS: interfazGestionEnvios.mostrarMenu(); break;
		}
	}
}