package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.*;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;

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
	public List<InsumoLiquido> listaInsumosLiquidos(){
		return insumoDao.listaInsumosLiquidos();
	}

	@Override
	public void editarInsumoNoLiquido(Integer id, Insumo insumo) {
		insumoDao.editarInsumoNoLiquido(id, insumo);
	}
	
	@Override
	public void editarInsumoLiquido(Integer id, Insumo insumo) {
		insumoDao.editarInsumoLiquido(id, insumo);
	}

	@Override
	public void eliminarInsumo(Insumo insumo) {
		insumoDao.eliminarInsumo(insumo);
	}

	@Override
	public Insumo buscarInsumoNoLiquido(Integer id) {
		return insumoDao.buscarInsumoNoLiquido(id);
	}
	
	@Override
	public Insumo buscarInsumoLiquido(Integer id) {
		return insumoDao.buscarInsumoLiquido(id);
	}
}
