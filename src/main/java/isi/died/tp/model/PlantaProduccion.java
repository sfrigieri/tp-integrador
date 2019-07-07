package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantaProduccion extends Planta {

	private List<Stock> listaDeStock;
	
	
	
	public PlantaProduccion(int id, String nombre) {
		super(id, nombre);
		listaDeStock = new ArrayList<Stock>();
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
	public void addStock(int id, int cantidad, int puntoPedido, Insumo ins) {
		if(this.getStock(ins) == null)
			listaDeStock.add(new Stock(id, cantidad, puntoPedido, ins));
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
	
}
