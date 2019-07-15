package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.RutaDao;
import isi.died.tp.dao.RutaDaoDefault;
import isi.died.tp.estructuras.Ruta;

public class RutaServiceDefault implements RutaService {

	private RutaDao rutaDao;

	public RutaServiceDefault() {
		super();
		this.rutaDao = new RutaDaoDefault();
	}
	
	@Override
	public Ruta agregarRuta(Ruta ruta) {
		rutaDao.agregarRuta(ruta);
		return ruta;
	}
	
	@Override
	public List<Ruta> listaRutas() {
		return rutaDao.listaRutas();
	}

	@Override
	public void editarRuta(Integer id, Ruta ruta) {
		rutaDao.editarRuta(id, ruta);

	}

	@Override
	public void eliminarRuta(Ruta ruta) {
		rutaDao.eliminarRuta(ruta);

	}

	@Override
	public Ruta buscarRuta(Integer id) {
		return rutaDao.buscarRuta(id);
	}
}
