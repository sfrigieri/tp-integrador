package isi.died.tp.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class StockDaoDefault implements StockDao {

	private static List<StockAcopio> LISTA_STOCKS_ACOPIO  = new ArrayList<StockAcopio>();
	private static List<StockProduccion> LISTA_STOCKS_PRODUCCION = new ArrayList<StockProduccion>();
	private static Integer ULTIMO_ID_PROD;
	private static Integer ULTIMO_ID_ACOP;
	private PlantaService ps;
	private InsumoService is;
	
	private CsvSource dataSource;
	
	public StockDaoDefault(PlantaService ps, InsumoService is) {
		this.ps = ps;
		this.is = is;
		dataSource = new CsvSource();
		if(LISTA_STOCKS_ACOPIO.isEmpty())
			this.cargarListaStocksAcopio();
		if (LISTA_STOCKS_PRODUCCION.isEmpty())
			this.cargarListaStocksProduccion();
		ULTIMO_ID_PROD = this.maxIdProd();
		ULTIMO_ID_ACOP = this.maxIdAcop();
	}
	
	private int maxIdProd() {

		int maxID = 0;
		
		for(StockProduccion stock: LISTA_STOCKS_PRODUCCION) {
			if(stock.getId()>maxID)
				maxID = stock.getId();
		}
		
		return maxID;
	}

	private int maxIdAcop() {

		int maxID = 0;
		
		for(Stock stock: LISTA_STOCKS_ACOPIO) {
			if(stock.getId()>maxID)
				maxID = stock.getId();
		}
		
		return maxID;
	}

	
	public void cargarListaStocksAcopio() {
		List<List<String>> stocks = dataSource.readFile("stocksAcopio.csv");
		for(List<String> filaStock : stocks) {
			StockAcopio aux = new StockAcopio();
			aux.loadFromStringRow(filaStock);
			aux.setInsumo(is.buscarInsumo(Integer.valueOf(filaStock.get(2))));
			aux.setPlanta(ps.buscarPlanta(Integer.valueOf(filaStock.get(3))));
			LISTA_STOCKS_ACOPIO.add(aux);
		}
	}
	public void cargarListaStocksProduccion() {
		List<List<String>> stocksProduccion = dataSource.readFile("stocksProduccion.csv");
		for(List<String> filaStock : stocksProduccion) {
			StockProduccion aux = new StockProduccion();
			aux.loadFromStringRow(filaStock);
			aux.setInsumo(is.buscarInsumo(Integer.valueOf(filaStock.get(2))));
			aux.setPlanta(ps.buscarPlanta(Integer.valueOf(filaStock.get(3))));
			LISTA_STOCKS_PRODUCCION.add(aux);
		}
	}


	@Override
	public Stock buscarStock(Integer id) {
		for(Stock actual : LISTA_STOCKS_ACOPIO)
			if(actual.getId() == id)
				return actual;
		for(StockProduccion actual : LISTA_STOCKS_PRODUCCION)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	
	@Override
	public StockProduccion buscarStockProduccion(Integer id) {
		for(StockProduccion actual : LISTA_STOCKS_PRODUCCION)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	
	
	@Override
	public StockAcopio buscarStockAcopio(Integer id) {
		for(StockAcopio actual : LISTA_STOCKS_ACOPIO)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	
	@Override
	public void agregarStock(Stock stock) {
		if (stock instanceof StockProduccion) {
			stock.setId(++ULTIMO_ID_PROD);
			LISTA_STOCKS_PRODUCCION.add((StockProduccion)stock);
			try {
				dataSource.agregarFilaAlFinal("stocksProduccion.csv", stock);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			stock.setId(++ULTIMO_ID_ACOP);
			LISTA_STOCKS_ACOPIO.add((StockAcopio)stock);	
			try {
				dataSource.agregarFilaAlFinal("stocksAcopio.csv", stock);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ps.buscarPlanta(stock.getPlanta().getId()).addStock(stock);
	}
	
	@Override
	public void editarStock(Integer id, Stock stock) {
		Stock old = null;
		
		old = buscarStock(id);
		
		if (old != null && old instanceof StockProduccion) {
			LISTA_STOCKS_PRODUCCION.remove(old);
		} else {
			if(old != null) 
				LISTA_STOCKS_ACOPIO.remove(old);
		}
			
		if (stock instanceof StockProduccion) {
			LISTA_STOCKS_PRODUCCION.add((StockProduccion)stock);
			this.actualizarArchivoProduccion();
		}
		else {
			LISTA_STOCKS_ACOPIO.add((StockAcopio)stock);
			this.actualizarArchivoAcopio();
		}
		
		//Cada tipo de planta implementa su método addStock, no es necesario diferenciar.
		ps.buscarPlanta(stock.getPlanta().getId()).addStock(stock);
				
		
	}
	
	
	@Override
	public void eliminarStock(Stock stock) {
		if (stock instanceof StockProduccion) {
			LISTA_STOCKS_PRODUCCION.remove(stock);
			actualizarArchivoProduccion();
		} else {
			LISTA_STOCKS_ACOPIO.remove(stock);
			actualizarArchivoAcopio();
		}
		
		ps.buscarPlanta(stock.getPlanta().getId()).removeStock(stock.getInsumo());
		
	}
	
	@Override
	public List<StockAcopio> listaStocksAcopio() {
		return LISTA_STOCKS_ACOPIO;
	}
	@Override
	public List<StockProduccion> listaStocksProduccion() {
		return LISTA_STOCKS_PRODUCCION;
	}
	
	private void actualizarArchivoAcopio() {
		File archivoStocks = new File("stocksAcopio.csv");
		archivoStocks.delete();
		try {
			archivoStocks.createNewFile();
			for(Stock actual: LISTA_STOCKS_ACOPIO) {
				try {
					dataSource.agregarFilaAlFinal("stocksAcopio.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
					}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	private void actualizarArchivoProduccion() {
		File archivoStocks = new File("stocksProduccion.csv");
		archivoStocks.delete();
		try {
			archivoStocks.createNewFile();
			for(Stock actual: LISTA_STOCKS_PRODUCCION) {
				try {
					dataSource.agregarFilaAlFinal("stocksProduccion.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
					}
			
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
