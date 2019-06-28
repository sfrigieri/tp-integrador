package isi.died.tp.dominio;

import java.util.List;
import java.util.stream.Collectors;

public class PlantaProduccion extends Planta {

	private List<Stock> listaDeStock;
	
	@Override
	public Boolean necesitaInsumo(Insumo i) {
		for(Stock stock : this.listaDeStock) {
			if(stock.getInsumo().equals(i) && stock.getPuntoPedido()>stock.getCantidad())
				return true;
		}
		 return false;
	}
	
	@Override
	public Integer cantidadNecesariaInsumo(Insumo i) {
		for(Stock stock : this.listaDeStock) {
			if(stock.getInsumo().equals(i)) {
				int cantidadNecesaria = stock.getPuntoPedido()-stock.getCantidad();
				if(cantidadNecesaria > 0)
					return cantidadNecesaria;
			}
		}
		 return 0;
	}
	
	
	public Double costoTotal() {
		return listaDeStock
				.stream()
				.distinct()
				.mapToDouble(( e)-> e.getInsumo().getCosto()*e.getCantidad())
				.sum();
		
		}
	

	public List<Insumo> stockEntre(Integer s1, Integer s2) {
		return listaDeStock
				.stream()
				.filter((s) -> ( s.getCantidad()>=s1 && s.getCantidad()<=s2))
				.map((s)  -> ( s.getInsumo()))
				.collect(Collectors.toList());
		
		}


	public List<Stock> getListaDeStock() {
		return listaDeStock;
	}


	public void setListaDeStock(List<Stock> listaDeStock) {
		this.listaDeStock = listaDeStock;
	}
	
}
