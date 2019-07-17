package isi.died.tp.model;

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


	public void loadFromStringRow(List<String> filaStock) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<String> asCsvRow() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
