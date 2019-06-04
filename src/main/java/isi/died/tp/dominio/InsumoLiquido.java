package isi.died.tp.dominio;
import isi.died.tp.dominio.*;

public class InsumoLiquido extends Insumo{
	
	double densidad;
	
	
	public InsumoLiquido(double dens,double litros) {
		this.unidadDeMedida = Medidas.LITRO;
		this.densidad = dens;
		this.peso = (litros/1000)*this.densidad;
	}


	@Override
	public boolean hayStock(double litros) {
		
		return false;
	}
}
