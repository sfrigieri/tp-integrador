package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class PlantaAcopio extends Planta {
	
	private List<Pedido> listaDePedidos;
	private List<Insumo> listaDeInsumos;
	
	
	public PlantaAcopio(int id, String nombre) {
		super(id, nombre);
		listaDePedidos = new ArrayList<Pedido>();
		listaDeInsumos  = new ArrayList<Insumo>();
	}

	public PlantaAcopio() {
		super();
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
	public Insumo getInsumo(int IdIns) {
		for(Insumo insumo : this.listaDeInsumos) {
			if(insumo.equals(IdIns)) return insumo;				
			}
		return null;
		
		}
	
	@Override
	public void removeInsumo(Insumo ins) {
		listaDeInsumos.removeIf(s -> s.equals(ins));
		
		}

	@Override
	public void addPedido(Pedido pedido) {
		listaDePedidos.add(pedido);
		}

	@Override
	public List<String> asCsvRow() {
		// TODO Auto-generated method stub
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
