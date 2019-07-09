package isi.died.tp.model;


public class InsumoLiquido extends Insumo{
	
	double densidad;
	
	

	public InsumoLiquido(int id_, String descrip, double costo_, boolean esRef, double dens, double litros) {
		super(id_, descrip, Unidad.LITRO, costo_, (litros/1000)*dens, esRef);
		this.densidad = dens;
	}
	
	@Override
	public boolean hayStock(double litros) {
		
		return false;
	}
}
