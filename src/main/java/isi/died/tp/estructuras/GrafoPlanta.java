package isi.died.tp.estructuras;

import java.util.HashSet;
import java.util.List;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;

public class GrafoPlanta extends Grafo<Planta> {

	public void imprimirDistanciaAdyacentes(Planta inicial) {
		List<Planta> adyacentes = super.getAdyacentes(inicial);
		for(Planta unAdyacente: adyacentes) {
			Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
			System.out.println("camino de "+inicial.getNombre()+" a "+
					unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() );
		}
	}
	// a
	public Planta buscarPlanta(Planta inicial, Insumo i, Integer saltos){ return new Planta(); }
	// b
	public Planta buscarPlanta(Planta inicial, Insumo i){ return new Planta(); }
	// c
	public Planta buscarPlanta(Insumo i){return new Planta(); }




	public List<Planta> buscarPlantasN(Vertice<Planta> v1,int saltos){

		HashSet<Vertice<Planta>> visitados = new HashSet<Vertice<Planta>>();

		return this.buscarPlantasN(Vertice<Planta> v1, saltos-1, visitados);

	}

	private List<Planta> buscarPlantasN(Vertice<Planta> v1,int saltos,HashSet<Vertice<Planta>> visitados){

		List<Planta> plantas = new ArrayList<>();

		if(!visitados.contains(v1) && saltos == 0) {   
			plantas.add(v1.getValor());
			return plantas;
		}	

		if(saltos > 0) {
			visitados.add(v1);

			for(Vertice<Planta> ady: super.getAdyacentes(v1)) {
				List<Planta> restoDelCamino = buscarPlantasN(ady, v2, saltos-1, visitados);
				if(restoDelCamino != null) {
					plantas.addAll(restoDelCamino);
				}
			}
		}
	}

	return plantas;
}


public List<T> buscarCaminos(T v1,T v2,int saltos){
	Vertice<T> origen = this.getNodo(v1);
	Vertice<T> destino = this.getNodo(v2);
	HashSet<Vertice<T>> visitados = new HashSet<Vertice<T>>();

	return this.buscarCaminos(origen, destino, saltos, visitados);

}

private List<T> buscarCaminos(Vertice<T> v1,Vertice<T> v2,int saltos,HashSet<Vertice<T>> visitados){

	List<T> caminos = new ArrayList<>();

	if(!visitados.contains(v1)) {   

		if(v1.equals(v2)) {
			if(saltos == 0) {
				caminos.add(v2.getValor());
				return caminos;
			}
			return null;
		}	
		if(saltos > 0) {
			visitados.add(v1);

			for(Vertice<T> ady: this.getAdyacentes(v1)) {
				List<T> restoDelCamino = buscarCaminos(ady, v2, saltos-1, visitados);
				if(restoDelCamino != null) {
					caminos.add(v1.getValor());
					caminos.addAll(restoDelCamino);
				}
			}
		}
	}

	return caminos;
}


}