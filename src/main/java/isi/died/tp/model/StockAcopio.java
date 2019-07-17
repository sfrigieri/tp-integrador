package isi.died.tp.model;

import java.util.ArrayList;
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
	public void setInsumo(Insumo ins) {
		this.ins = ins;
		
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


	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.setCantidad( Integer.valueOf(datos.get(1)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.getId()+"");
		lista.add(Integer.toString(this.getCantidad()));
		lista.add(Integer.toString(this.getInsumo().getId()));
		lista.add(Integer.toString(this.getPlanta().getId()));
		return lista;
	}




}
