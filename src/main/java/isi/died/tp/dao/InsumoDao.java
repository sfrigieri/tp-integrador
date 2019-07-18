package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;

public interface InsumoDao {

	public void agregarInsumo(Insumo insumo);

	public List<Insumo> listaInsumos();
	
	public List<Insumo> listaInsumosLiquidos();

	public void editarInsumoNoLiquido(Insumo insumo);
	public void editarInsumoLiquido(Insumo insumo);

	public void eliminarInsumo(Insumo insumo);

	public Insumo buscarInsumoNoLiquido(Integer id);
	public Insumo buscarInsumoLiquido(Integer id);

	public void setStocksAcopio(List<StockAcopio> lista);
	
}
