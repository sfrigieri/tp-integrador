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
	
	public Insumo(int stock) {
		this.stock = stock;
		}
	
	public Insumo() {
		this.id = 1;
	};
	
	@Override
	public int compareTo (Insumo i) {
		return (this.stock-i.stock);
	}

	public boolean hayStock(double cant) {
		return false;
	}

	public void actualizarStock(double cant) {
	
		
	}

	public int getStock() {
		return this.stock;
	}
}
