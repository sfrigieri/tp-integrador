package isi.died.tp.model;

import java.util.List;

public class StockAcopio extends Stock {

	public StockAcopio(int id, int cantidad, Insumo ins, Planta planta) {
		super(id,cantidad,ins,planta);
	}


	public StockAcopio(int id, Insumo ins, Planta planta) {
		super(id,ins,planta);
	}

	public StockAcopio(int id, int cantidad) {
		super(id,cantidad);
	}

	public StockAcopio() {
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
	public Planta getPlanta() {
		return this.planta;
	}

	@Override
	public void setPlanta(Planta planta) {
		this.planta = planta;
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
