package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;

public class StockProduccion extends Stock {

	private int puntoPedido;

	
	public StockProduccion(int id, int cantidad, int puntoPedido, Insumo ins, Planta planta) {
		super(id,cantidad,ins,planta);
		this.puntoPedido = puntoPedido;
	}


	public StockProduccion(int id, Insumo ins, Planta planta) {
		super(id,ins,planta);
		this.puntoPedido = 0;
	}



	public StockProduccion() {
		super();
	}


	@Override
	public Integer getCantidad() {
		return this.cantidad;
	}
	
	@Override
	public Insumo getInsumo() {
		return this.ins;
	}
	
	@Override
	public void setInsumo(Insumo ins) {
		this.ins = ins;
		
	}
	
	@Override
	public void setPlanta(Planta planta) {
		this.planta = planta;
	}
	
	@Override
	public Planta getPlanta() {
		return this.planta;
	}

	@Override
	public Integer getPuntoPedido() {
		return this.puntoPedido;
	}


	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.setCantidad( Integer.valueOf(datos.get(1)));
			this.setPuntoPedido(Integer.valueOf(datos.get(6)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPuntoPedido(Integer puntoPedido) {
		this.puntoPedido = puntoPedido;
		
	}


	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.getId()+"");
		lista.add(Integer.toString(this.getCantidad()));
		if(this.getInsumo() instanceof InsumoLiquido)
			lista.add("L");
		else lista.add("G");
		lista.add(Integer.toString(this.getInsumo().getId()));
		if(this.getPlanta() instanceof PlantaProduccion)
			lista.add("P");
		else lista.add("A");
		lista.add(Integer.toString(this.getPlanta().getId()));
		lista.add(Integer.toString(this.getPuntoPedido()));
		return lista;
	}
	

}
