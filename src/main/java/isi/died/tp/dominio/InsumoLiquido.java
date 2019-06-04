package isi.died.tp.dominio;

public class InsumoLiquido extends Insumo{
	
	double densidad;
	
	
	public InsumoLiquido(double dens) {
		this.unidadDeMedida = LITRO;
		this.densidad = dens;
	}

	public Pedido NuevoPedido(litros) {
		if(this.hayStock(litros))
			this.peso = (litros/1000)*this.densidad;
		
	}
}
