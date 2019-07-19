package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.StockService;

public class StockController {

	private StockService ss;
	
	public StockController(StockService ss) {
		this.ss = ss;
	}
	

	public List<StockProduccion> listaStocksProduccion(){
		return this.ss.listaStocksProduccion();
	}

	
	public List<StockAcopio> listaStocksAcopio(){
		return this.ss.listaStocksAcopio();
	}

	
	public void eliminarStock(Stock stock){
		this.ss.eliminarStock(stock);
	}
	
	public void eliminarStocksProduccion(List<Stock> lista){
		this.ss.eliminarStocksProduccion(lista);
	}

	
	public void editarStock(Stock stock){
		this.ss.editarStock(stock);
	}

	
	public void agregarStock(Stock stock){
		this.ss.agregarStock(stock);
	}
	
	
	public StockAcopio buscarStockAcopio(Integer id){
		return this.ss.buscarStockAcopio(id);
	}

	
	public StockProduccion buscarStockProduccion(Integer id){
		return this.ss.buscarStockProduccion(id);
	}

}
