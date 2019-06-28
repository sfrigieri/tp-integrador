package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;

public class GrafoPlanta extends Grafo<Planta> {

	public void imprimirDistanciaAdyacentes( Planta inicial) {
		List<Planta> adyacentes = super.getAdyacentes(inicial);
		for(Planta unAdyacente: adyacentes) {
			Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
			System. out. println("camino de "+inicial. getNombre()+" a "+
					unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() ); 
		}
	}

	public Planta buscarPlanta(Planta inicial, Insumo i, Integer saltos){ 
		
		List<Planta> plantas = buscarPlantasN(this.getNodo(inicial),saltos);
		
		return new Planta(); }

	public Planta buscarPlanta(Planta inicial, Insumo i){ return new Planta(); }

	public Planta buscarPlanta(Insumo i){return new Planta(); }



	private List<Planta> buscarPlantasN(Vertice<Planta> v1,int saltos){

		HashSet<Vertice<Planta>> visitados = new HashSet<Vertice<Planta>>();
		
		return this.buscarPlantasN(v1, saltos, visitados);

	}

	private List<Planta> buscarPlantasN(Vertice<Planta> v1,int saltos,HashSet<Vertice<Planta>> visitados){

		List<Planta> plantas = new ArrayList<Planta>();

		if(!visitados.contains(v1) && saltos >= 0) {   
			plantas.add(v1.getValor());
			visitados.add(v1);

			for(Vertice<Planta> ady: this.getAdyacentes(v1)) {
				List<Planta> restoDelCamino = buscarPlantasN(ady, saltos-1, visitados);
				if(restoDelCamino != null) plantas.addAll(restoDelCamino);
			}
		}

	return plantas;
}


}