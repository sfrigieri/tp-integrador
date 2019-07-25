package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Camion;

public interface CamionService {
	
	public void agregarCamion(Camion camion);

	public List<Camion> listaCamiones();

	public void editarCamion(Camion camion);

	public void eliminarCamion(Camion camion);

	public Camion buscarCamion(Integer id);

	public boolean existenCamiones();
	
}
