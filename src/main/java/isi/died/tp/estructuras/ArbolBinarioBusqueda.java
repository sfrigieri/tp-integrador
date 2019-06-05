package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.List;


public class ArbolBinarioBusqueda<E extends Comparable<E>> extends Arbol<E> {

	protected Arbol<E> izquierdo;
	protected Arbol<E> derecho;
	
	public ArbolBinarioBusqueda(){
		this.valor=null;
		this.izquierdo=new ArbolVacio<E>();
		this.derecho=new ArbolVacio<E>();
	}
	
	public ArbolBinarioBusqueda(E e){
		this.valor=e;
		this.izquierdo=new ArbolVacio<E>();
		this.derecho=new ArbolVacio<E>();
	}
	
	public ArbolBinarioBusqueda(E e,Arbol<E> i,Arbol<E> d){
		this.valor=e;
		this.izquierdo=i;
		this.derecho=d;
	}
	
	@Override
	public List<E> preOrden() {
		List<E> lista = new ArrayList<E>();
		lista.add(this.valor);
		lista.addAll(this.izquierdo.preOrden());
		lista.addAll(this.derecho.preOrden());
		return lista;
	}
	@Override
	public List<E> inOrden() {
		List<E> lista = new ArrayList<E>();
		lista.addAll(this.izquierdo.preOrden());
		lista.add(this.valor);
		lista.addAll(this.derecho.preOrden());
		return lista;
	}
	@Override
	public List<E> posOrden() {
		List<E> lista = new ArrayList<E>();
		lista.addAll(this.izquierdo.preOrden());
		lista.addAll(this.derecho.preOrden());
		lista.add(this.valor);
		return lista;

	}
	@Override
	public boolean esVacio() {
		return false;
	}
        
	@Override
	public E valor() {
		return this.valor;
	}
	
	@Override
	public Arbol<E> izquierdo() {
		return this.izquierdo;
	}
	
	@Override
	public Arbol<E> derecho() {
		return this.derecho;
	}


	@Override
	public void agregar(E a) {
		if(this.valor.compareTo(a)<1) {
			if (this.derecho.esVacio()) this.derecho = new ArbolBinarioBusqueda<E>(a);
			else this.derecho.agregar(a);
		}else {
			if (this.izquierdo.esVacio()) this.izquierdo= new ArbolBinarioBusqueda<E>(a);
			else this.izquierdo.agregar(a);
		}
	}
	
	@Override
	public boolean equals(Arbol<E> unArbol) {
		return this.valor.equals(unArbol.valor()) && this.izquierdo.equals(unArbol.izquierdo()) && this.derecho.equals(unArbol.derecho());
	}

	@Override
	public boolean contiene(E unValor) {
		// TODO 1.a
		return this.valor.equals(unValor) || this.izquierdo.contiene(unValor) || this.derecho.contiene(unValor);
	}

	@Override
	public Integer profundidad() {
		// TODO 1.b
		if (this.izquierdo.esVacio() && this.derecho.esVacio())
			return 0;
		return profundidad(this.izquierdo, this.derecho);
	}

	private Integer profundidad(Arbol<E> izq,Arbol<E> der) {
		return 1+Math.max(izq.profundidad(), der.profundidad());
	}
	
	@Override
	public Integer cuentaNodosDeNivel(int nivel) {
		// TODO 1.c
		if (nivel == 0)
			if (this.valor != null)
				return 1;
			else 
				return 0;
		return this.izquierdo.cuentaNodosDeNivel(nivel-1) + this.derecho.cuentaNodosDeNivel(nivel-1) ;
	}

	
	@Override
	public boolean esCompleto() {
		// TODO 1.d
		if(this.esLleno())
			return true;
		//Si n-1 es lleno
		if(this.profundidad() != 0 && this.esLleno(this.profundidad()-1)){
			
			ArrayList<Arbol> valoresHoja = new ArrayList<Arbol>();
			
			this.getHojas(valoresHoja, this, this.profundidad()-1);
			
			//Al menos la hoja de más a la izquierda debe no ser vacía
			if(valoresHoja.get(0).esVacio())
				return false;
			
			boolean anteriorVacio = false;
			
			//Y las demás hojas deben ser continuamente vacías o no vacías
			for(Arbol<E> a : valoresHoja) {
			
				if(a.esVacio())
					anteriorVacio = true;
				else if(anteriorVacio)
						return false;
	
			}
			
			return true;
		}
		else
			return false;
	}

	private void getHojas(ArrayList<Arbol> valoresHoja, ArbolBinarioBusqueda<E> padre,int nivel) {
		
		if(nivel == 0) {
			valoresHoja.add(padre.izquierdo);
			valoresHoja.add(padre.derecho);
		}
		else {
			getHojas(valoresHoja, (ArbolBinarioBusqueda<E>) padre.izquierdo, nivel-1);
			getHojas(valoresHoja, (ArbolBinarioBusqueda<E>) padre.derecho, nivel-1);
		}

	}
	
	@Override
	public boolean esLleno() {
		// TODO 1.e
		if(this.valor == null)
			return false;
		return this.esLleno(this.profundidad());
	
	}
	
	private boolean esLleno(int nivel) {
		
		return Math.pow(2,nivel) == this.cuentaNodosDeNivel(nivel);
	}
	
	@Override
	public ArrayList<E> rango(E inicio,E fin){
		ArrayList<E> lista = new ArrayList<E>();
		int compararMin =this.valor.compareTo(inicio);
		int compararMax =this.valor.compareTo(fin);
		
		if(compararMax <= 0 && compararMin >= 0) {
			lista.addAll(this.izquierdo().rango(inicio, fin));
			lista.add(valor);
			lista.addAll(this.derecho().rango(inicio, fin));
		}else {
			if(compararMax < 0) 
				lista.addAll(this.derecho().rango(inicio, fin));
			else
				lista.addAll(this.izquierdo().rango(inicio, fin));
		}
		
		return lista;
	}
	
}
