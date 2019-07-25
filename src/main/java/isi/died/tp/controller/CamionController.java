package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.Camion;
import isi.died.tp.service.CamionService;

public class CamionController {

	private CamionService cs;
	
	public CamionController(CamionService camionService) {
		this.cs =  camionService;
	}

	public void agregarCamion(Camion camion) {
		cs.agregarCamion(camion);
	}

	
	public List<Camion> listaCamiones() {
		return cs.listaCamiones();
	}


	public void editarCamion(Camion camion) {
		cs.editarCamion(camion);
	}


	public void eliminarCamion(Camion camion) {
		cs.eliminarCamion(camion);
		
	}


	public Camion buscarCamion(Integer id) {
		return cs.buscarCamion(id);
	}

	public boolean existenCamiones() {
		return cs.existenCamiones();
	}

}
