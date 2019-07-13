package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantaProduccion extends Planta {

	private List<StockProduccion> listaDeStock;
	
	public PlantaProduccion() {
		super();
	}
	
	public PlantaProduccion(int id, String nombre) {
		super(id, nombre);
		listaDeStock = new ArrayList<StockProduccion>();
	}

	@Override
	public Boolean necesitaInsumo(Insumo i) {
		Stock stock = this.getStock(i);
		if(stock != null && stock.getPuntoPedido()>stock.getCantidad())
			return true;
		
		return false;
	}
	
	@Override
	public Integer cantidadNecesariaInsumo(Insumo i) {
		Stock stock = this.getStock(i);
			if(stock != null) {
				int cantidadNecesaria = stock.getPuntoPedido()-stock.getCantidad();
				if(cantidadNecesaria > 0)
					return cantidadNecesaria;
			}
		 return 0;
	}
	
	@Override
	public Double costoTotal() {
		return listaDeStock
				.stream()
				.distinct()
				.mapToDouble(( e)-> e.getInsumo().getCosto()*e.getCantidad())
				.sum();
		
		}
	
	@Override
	public List<Insumo> stockEntre(Integer s1, Integer s2) {
		return listaDeStock
				.stream()
				.filter((s) -> ( s.getCantidad()>=s1 && s.getCantidad()<=s2))
				.map((s)  -> ( s.getInsumo()))
				.collect(Collectors.toList());
		
		}

	@Override
	public void addStock(int id, int cantidad, int puntoPedido, Insumo ins, Planta planta) {
		if(this.getStock(ins) == null)
			listaDeStock.add(new StockProduccion(id, cantidad, puntoPedido, ins, planta));
		}
	
	@Override
	public Stock getStock(Insumo ins) {
		for(Stock stock : this.listaDeStock) {
			if(stock.getInsumo().equals(ins)) return stock;				
			}
		return null;
		
		}
	
	@Override
	public Stock getStock(int id) {
		for(Stock stock : this.listaDeStock) {
			if(stock.getId() == id) return stock;				
			}
		return null;
		
		}
	
	@Override
	public Stock getStock(Stock s) {
		for(Stock stock : this.listaDeStock) {
			if(stock.equals(s)) return stock;				
			}
		return null;
	}
	
	@Override
	public void removeStock(Insumo ins) {
		listaDeStock.removeIf(s -> s.getInsumo().equals(ins));
		
		}

	public List<StockProduccion> getListaDeStock() {
		return listaDeStock;
	}

	public void setListaDeStock(List<StockProduccion> listaDeStock) {
		this.listaDeStock = listaDeStock;
	}

	@Override
	public List<String> asCsvRow() {
		
		return null;
	}
	
	@Override
	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.nombre = datos.get(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
