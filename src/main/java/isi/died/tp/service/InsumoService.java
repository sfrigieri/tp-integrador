package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Insumo;
import isi.died.tp.model.StockAcopio;

public interface InsumoService {

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
