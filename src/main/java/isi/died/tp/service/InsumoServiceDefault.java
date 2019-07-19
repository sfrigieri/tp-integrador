package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.*;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.StockAcopio;

public class InsumoServiceDefault implements InsumoService {

	private InsumoDao insumoDao;
	
	public InsumoServiceDefault(PlantaService plantaService) {
		super();
		this.insumoDao = new InsumoDaoDefault(plantaService);
	}
	
	@Override
	public void agregarInsumo(Insumo insumo) {
		insumoDao.agregarInsumo(insumo);
	}
	
	@Override
	public List<Insumo> listaInsumos() {
		return insumoDao.listaInsumos();
	}
	
	@Override
	public List<Insumo> listaInsumosLiquidos(){
		return insumoDao.listaInsumosLiquidos();
	}

	@Override
	public void editarInsumoNoLiquido(Insumo insumo) {
		insumoDao.editarInsumoNoLiquido(insumo);
	}
	
	@Override
	public void editarInsumoLiquido(Insumo insumo) {
		insumoDao.editarInsumoLiquido(insumo);
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

	@Override
	public void setStocksAcopio(List<StockAcopio> lista) {
		insumoDao.setStocksAcopio(lista);
		
	}
}
