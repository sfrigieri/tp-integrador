package isi.died.tp.estructuras;

@FunctionalInterface
public interface Condicion<T> {
	public Boolean cumple(T t);
}
