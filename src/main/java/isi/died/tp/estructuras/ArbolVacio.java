package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.List;

public class ArbolVacio<E extends Comparable<E>> extends Arbol<E> {

	public ArbolVacio(){
		this.valor=null;
	}
	
	@Override
	public List<E> preOrden() {
		return new ArrayList<E>();
	}

	@Override
	public List<E> inOrden() {
		return new ArrayList<E>();
	}

	@Override
	public List<E> posOrden() {
		return new ArrayList<E>();
	}

	@Override
	public boolean esVacio() {
		return true;
	}

	@Override
	public E valor() {
		return null;
	}

	@Override
	public Arbol<E> izquierdo() {
		return null;
	}

	@Override
	public Arbol<E> derecho() {
		return null;
	}

	@Override
	public boolean contiene(E unValor) {
		return false;
	}

	@Override
	public boolean equals(Arbol<E> unArbol) {
		return unArbol.esVacio();
	}

	@Override
	public void agregar(E a) {
		// TODO Auto-generated method stub
		
	}
        @Override
	public Integer profundidad() {
		return 0;
	}

	@Override
	public Integer cuentaNodosDeNivel(int nivel) {
		return 0;
	}

	@Override
	public boolean esCompleto() {
		return false;
	}

	@Override
	public boolean esLleno() {
		return false;
	}

	@Override
	public ArrayList<E> rango(E inicio,E fin){
		return new ArrayList<E>();
	}

	@Override
	public List<E> rangoComp(E inicio, E fin) {
		return new ArrayList<E>();
	};

}
