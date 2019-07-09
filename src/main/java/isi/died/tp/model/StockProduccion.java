package isi.died.tp.model;

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



	@Override
	public Integer getCantidad() {
		return this.cantidad;
	}
	
	@Override
	public Insumo getInsumo() {
		return this.ins;
	}
	
	@Override
	public Integer getPuntoPedido() {
		return this.puntoPedido;
	}
	

}
