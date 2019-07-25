package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.CamionDao;
import isi.died.tp.dao.CamionDaoDefault;
import isi.died.tp.model.Camion;

public class CamionServiceDefault implements CamionService {

	CamionDao camionDao;
	
	public CamionServiceDefault() {
		super();
		this.camionDao = new CamionDaoDefault();
	}
	
	@Override
	public void agregarCamion(Camion camion) {
		camionDao.agregarCamion(camion);
	}

	@Override
	public List<Camion> listaCamiones() {
		return camionDao.listaCamiones();
	}

	@Override
	public void editarCamion(Camion camion) {
		camionDao.editarCamion(camion);
	}

	@Override
	public void eliminarCamion(Camion camion) {
		camionDao.eliminarCamion(camion);
		
	}

	@Override
	public Camion buscarCamion(Integer id) {
		return camionDao.buscarCamion(id);
	}

}
