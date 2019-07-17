package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.*;
import isi.died.tp.model.*;

public class StockServiceDefault implements StockService {

	private StockDao stockDao;
	private PlantaService ps;

	public StockServiceDefault(PlantaService ps, InsumoService is) {
		super();
		this.stockDao = new StockDaoDefault(ps, is);
		this.ps = ps;
	}
	
	@Override
	public List<StockProduccion> listaStocksProduccion(){
		return this.stockDao.listaStocksProduccion();
	}

	@Override
	public List<StockAcopio> listaStocksAcopio(){
		return this.stockDao.listaStocksAcopio();
	}

	@Override
	public void eliminarStock(Stock stock){
		this.stockDao.eliminarStock(stock);
	}

	@Override
	public void editarStock(Integer id, Stock stock){
		this.stockDao.editarStock(id,stock);
	}

	@Override
	public void agregarStock(Stock stock){
		this.stockDao.agregarStock(stock);
	}
	
	@Override
	public StockAcopio buscarStockAcopio(Integer id){
		return this.stockDao.buscarStockAcopio(id);
	}

	@Override
	public StockProduccion buscarStockProduccion(Integer id){
		return this.stockDao.buscarStockProduccion(id);
	}

	@Override
	public Stock buscarStock(Integer id){
		return this.stockDao.buscarStock(id);
	}
}
