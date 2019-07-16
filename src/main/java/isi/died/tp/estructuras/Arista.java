package isi.died.tp.estructuras;

public class Arista<T> {
	private Vertice<T> inicio;
	private Vertice<T> fin;
	private Integer valor;

	public Arista(){
		valor=1;
	} 
	
	public Arista(Vertice<T> ini,Vertice<T> fin){
		this();
		this.inicio = ini;
		this.fin = fin;
	}

	public Arista(Vertice<T> ini,Vertice<T> fin,Integer val){
		this(ini,fin);
		this.valor= val;
	}
	
	public Vertice<T> getInicio() {
		return inicio;
	}
	
	public void setInicio(Vertice<T> inicio) {
		this.inicio = inicio;
	}
	
	public Vertice<T> getFin() {
		return fin;
	}
	
	public void setFin(Vertice<T> fin) {
		this.fin = fin;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	
	@Override
	public String toString() {
		return "( "+this.inicio.getValor()+" --> "+this.fin.getValor()+" )";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fin == null) ? 0 : fin.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Arista<?>) && ((Arista<?>)obj).getValor().equals(this.valor); 
	}
	
	
	public double getDuracionViajeMin() {
		return 0;
	}


	public void setDuracionViajeMin(double duracionViajeMin) {
	}


	public int getPesoMaxTon() {
		return 0;
	}



	public void setPesoMax(int pesoMaxTon) {
		
	}


	public int getPesoEnCurso() {
		return 0;
	}


	public void setPesoEnCurso(int pesoEnCursoTon) {

	}

	
}
