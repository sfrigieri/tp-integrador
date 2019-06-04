package isi.died.tp.dominio;

public class Insumo implements Comparable<Insumo>{

	protected enum Medidas {KILO, PIEZA, GRAMO, METRO, LITRO, M3, M2}
	
	int id;
	String descripcion;
	Medidas unidadDeMedida;
	double costo;
	int stock;
	double peso;
	boolean esRefrigerado;
	
	@Override
	public int compareTo (Insumo i) {
		return (this.stock-i.stock);
	}
}
