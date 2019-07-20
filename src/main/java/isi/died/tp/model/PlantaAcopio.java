package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantaAcopio extends Planta {
	
	private List<StockAcopio> listaDeStocks;
	private List<Insumo> listaDeInsumos;
	Boolean esOrigen;
	
	public PlantaAcopio(int id, String nombre) {
		super(id, nombre);
		listaDeStocks = new ArrayList<StockAcopio>();
		listaDeInsumos  = new ArrayList<Insumo>();
		this.esOrigen = null;
	}
	
	public PlantaAcopio(int id, String nombre, Boolean esOrigen) {
		super(id, nombre);
		listaDeStocks = new ArrayList<StockAcopio>();
		listaDeInsumos  = new ArrayList<Insumo>();
		this.esOrigen = esOrigen;
	}


	public PlantaAcopio() {
		super();
		this.pageRank = 1.0;
	}
	
	@Override
	public void addInsumos(List<Insumo> lista) {
		this.listaDeInsumos.addAll(lista);
	}
	
	public List<Insumo> getListaDeInsumos(){
		return this.listaDeInsumos;
	}
	
	@Override
	public Boolean esOrigen() {
		return this.esOrigen;
	}
	
	@Override
	public void setEsOrigen(Boolean valor) {
		this.esOrigen = valor;
	}
	
	
	@Override
	public void removeStock(Insumo ins) {
		for(Insumo i : listaDeInsumos)
			if(i.equals(ins))
				i.removeStock();
		
		}
	
	@Override
	public Double costoTotal() {
		return listaDeInsumos
				.stream()
				.distinct()
				.mapToDouble(( e)-> e.getCosto()*e.getStock().getCantidad())
				.sum();
		
		}
	
	
	@Override
	public List<Insumo> stockEntre(Integer s1, Integer s2) {
		return listaDeInsumos
				.stream()
				.filter((s) -> ( s.getStock().getCantidad()>=s1 && s.getStock().getCantidad()<=s2))
				.collect(Collectors.toList());
		
		}
	
	@Override
	public void addInsumo(Insumo ins) {
		listaDeInsumos.add(ins);
		}
	
	@Override
	public Insumo getInsumo(int idIns) {
		for(Insumo insumo : this.listaDeInsumos) {
			if(insumo.getId() == idIns) return insumo;				
			}
		return null;
		
		}
	
	@Override
	public void removeInsumo(Insumo ins) {
		listaDeInsumos.removeIf(s -> s.equals(ins));
		
		}

	@Override
	public void addStock(Stock stock) {
		listaDeStocks.add((StockAcopio) stock);
		
		if(this.esOrigen != null && this.esOrigen) {
			Insumo ins = this.getInsumo(stock.getInsumo().getId());
			if(ins != null) ins.setStock((StockAcopio) stock);
		}
	}

	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add(this.nombre);
		lista.add(this.pageRank.toString());
		lista.add(Boolean.toString(this.esOrigen));
		return lista;
	}
	
	@Override
	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.nombre = datos.get(1);
			this.pageRank = Double.valueOf(datos.get(2));
			this.esOrigen = Boolean.valueOf(datos.get(3)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
