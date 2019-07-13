package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.*;
import isi.died.tp.model.Insumo;

public class InsumoServiceDefault implements InsumoService {

	private InsumoDao insumoDao;

	public InsumoServiceDefault() {
		super();
		this.insumoDao = new InsumoDaoDefault();
	}
	
	@Override
	public Insumo agregarInsumo(Insumo insumo) {
		insumoDao.agregarInsumo(insumo);
		return insumo;
	}
	
	@Override
	public List<Insumo> listaInsumos() {
		return insumoDao.listaInsumos();
	}

	@Override
	public void editarInsumo(Integer id, Insumo insumo) {
		insumoDao.editarInsumo(id, insumo);

	}

	@Override
	public void eliminarInsumo(Insumo insumo) {
		insumoDao.eliminarInsumo(insumo);

	}

	@Override
	public Insumo buscarInsumo(Integer id) {
		return insumoDao.buscarInsumo(id);
	}
}
