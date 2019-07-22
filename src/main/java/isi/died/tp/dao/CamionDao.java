package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.model.Camion;

public interface CamionDao {

	public Camion buscarCamion(Integer id);

	public void agregarCamion(Camion camion);

	public void editarCamion(Camion camion);

	public void eliminarCamion(Camion camion);

	public List<Camion> listaCamiones();

}
