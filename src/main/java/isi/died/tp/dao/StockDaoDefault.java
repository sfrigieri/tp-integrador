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
			if(filaStock.get(2).matches("L"))
				aux.setInsumo(is.buscarInsumoLiquido(Integer.valueOf(filaStock.get(3))));
			else
				aux.setInsumo(is.buscarInsumoNoLiquido(Integer.valueOf(filaStock.get(3))));
			if(filaStock.get(4).matches("P"))
				aux.setPlanta(ps.buscarPlantaProduccion(Integer.valueOf(filaStock.get(5))));
			else
				aux.setPlanta(ps.buscarPlantaAcopio(Integer.valueOf(filaStock.get(5))));
			LISTA_STOCKS_ACOPIO.add(aux);
		}
		
		is.setStocksAcopio(LISTA_STOCKS_ACOPIO);
	}
	public void cargarListaStocksProduccion() {
		List<List<String>> stocksProduccion = dataSource.readFile("stocksProduccion.csv");
		for(List<String> filaStock : stocksProduccion) {
			StockProduccion aux = new StockProduccion();
			aux.loadFromStringRow(filaStock);
			if(filaStock.get(2).matches("L"))
				aux.setInsumo(is.buscarInsumoLiquido(Integer.valueOf(filaStock.get(3))));
			else
				aux.setInsumo(is.buscarInsumoNoLiquido(Integer.valueOf(filaStock.get(3))));
			if(filaStock.get(4).matches("P"))
				aux.setPlanta(ps.buscarPlantaProduccion(Integer.valueOf(filaStock.get(5))));
			else
				aux.setPlanta(ps.buscarPlantaAcopio(Integer.valueOf(filaStock.get(5))));
			LISTA_STOCKS_PRODUCCION.add(aux);
		}
		
		ps.setStocksProduccion(LISTA_STOCKS_PRODUCCION);
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
		
		if(stock.getPlanta() instanceof PlantaProduccion) 
			ps.buscarPlantaProduccion(stock.getPlanta().getId()).addStock(stock);
		else
			ps.buscarPlantaAcopio(stock.getPlanta().getId()).addStock(stock);
		
	}
	
	@Override
	public void editarStock(Stock stock) {
		Stock old = null;
		
		if(stock instanceof StockProduccion) {
			old = buscarStockProduccion(stock.getId());
			if (old != null)
				LISTA_STOCKS_PRODUCCION.remove(old);
			LISTA_STOCKS_PRODUCCION.add((StockProduccion)stock);
			this.actualizarArchivoProduccion();
		}
		else {
			old = buscarStockAcopio(stock.getId());
			if (old != null)
				LISTA_STOCKS_ACOPIO.remove(old);
			LISTA_STOCKS_ACOPIO.add((StockAcopio)stock);
			this.actualizarArchivoAcopio();
		}
		
		if(stock.getPlanta() instanceof PlantaProduccion) 
			ps.buscarPlantaProduccion(stock.getPlanta().getId()).addStock(stock);
		else
			ps.buscarPlantaAcopio(stock.getPlanta().getId()).addStock(stock);
		
	}
	
	
	@Override
	public void eliminarStock(Stock stock) {
		
		//Si es stockProduccion, debo buscar la Planta correspondiente y eliminarlo de listaDeStocks
		//Si es stockAcopio, lo mismo, pero se eliminará del insumo correspondiente.
		if(stock.getPlanta() instanceof PlantaProduccion) 
			ps.buscarPlantaProduccion(stock.getPlanta().getId()).removeStock(stock.getInsumo());
		else
			ps.buscarPlantaAcopio(stock.getPlanta().getId()).removeStock(stock.getInsumo());
		
		if (stock instanceof StockProduccion) {
			LISTA_STOCKS_PRODUCCION.remove(stock);
			actualizarArchivoProduccion();
		} else {
			LISTA_STOCKS_ACOPIO.remove(stock);
			actualizarArchivoAcopio();
		}
		
	}
	
	@Override
	public void eliminarStocksProduccion(List<StockProduccion> lista) {
		LISTA_STOCKS_PRODUCCION.removeAll(lista);
		actualizarArchivoProduccion();
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
