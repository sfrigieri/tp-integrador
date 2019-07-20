package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Stock;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.model.StockProduccion;

public interface StockService {

	public List<StockProduccion> listaStocksProduccion();

	public List<StockAcopio> listaStocksAcopio();

	public void eliminarStock(Stock stock);

	public void editarStock(Stock stock);

	public void agregarStock(Stock stock);

	public StockAcopio buscarStockAcopio(Integer id);

	public StockProduccion buscarStockProduccion(Integer id);

	public void eliminarStocksProduccion(List<StockProduccion> lista);

}
