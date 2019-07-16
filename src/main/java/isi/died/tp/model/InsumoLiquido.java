package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;

public class InsumoLiquido extends Insumo{
	
	double densidad;
	
	

	public InsumoLiquido(int id_, String descrip, double costo_, boolean esRef, double dens, double litros) {
		super(id_, descrip, Unidad.LITRO, costo_, (litros/1000)*dens, esRef);
		this.densidad = dens;
	}
	
	public InsumoLiquido() {}

	public double getDensidad() {
		return this.densidad;
	}
	
	@Override
	public List<String> asCsvRow() {
		//int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add(this.descripcion.toString());
		lista.add(this.unidadDeMedida.toString());
		lista.add(Double.toString(this.costo));
		lista.add(Double.toString(this.peso));
		lista.add(Boolean.toString(this.esRefrigerado));
		lista.add(Double.toString(this.densidad));
		return lista;
	}

	@Override
	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.descripcion = datos.get(1);
			this.unidadDeMedida = Unidad.valueOf(datos.get(2));
			this.costo = Double.valueOf(datos.get(3));
			this.peso = Double.valueOf(datos.get(4));
			this.esRefrigerado = Boolean.valueOf(datos.get(5));
			this.densidad = Double.valueOf(datos.get(6));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
