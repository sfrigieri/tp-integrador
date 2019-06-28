package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.PlantaProduccion;

public class GrafoPlanta extends Grafo<Planta> {

	public void imprimirDistanciaAdyacentes( Planta inicial) {
		
		List<Planta> adyacentes = super.getAdyacentes(inicial);
		
		for(Planta unAdyacente: adyacentes) {
			
			Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
			System.out.println("camino de "+inicial. getNombre()+" a "+unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() ); 
		}
	}

	public Planta buscarPlanta(Planta inicial, Insumo i, Integer saltos){ 
		
		List<Planta> plantasCercanas = buscarPlantasCercanas(this.getNodo(inicial),saltos);
		
		if (!plantasCercanas.isEmpty()){
			for(Planta p : plantasCercanas){ 
				if(p.necesitaInsumo(i)) return p;
			}
		}
		return null;
	}


	public Planta buscarPlanta(Planta inicial, Insumo i){ return new Planta(); }

	public Planta buscarPlanta(Insumo i){return new Planta(); }



	private List<Planta> buscarPlantasCercanas(Vertice<Planta> v1,int saltos){

		HashSet<Vertice<Planta>> visitados = new HashSet<Vertice<Planta>>();
		visitados.add(v1);
		return this.buscarPlantasCercanas(v1, saltos, visitados);

	}

	private List<Planta> buscarPlantasCercanas(Vertice<Planta> v1,int saltos,HashSet<Vertice<Planta>> visitados){

		List<Planta> plantas = new ArrayList<Planta>();

		if(saltos >= 0) {
			if(!visitados.contains(v1)) {
				plantas.add(v1.getValor());
				visitados.add(v1);
			}
			
			for(Vertice<Planta> ady: this.getAdyacentes(v1))
				plantas.addAll(buscarPlantasCercanas(ady, saltos-1, visitados));
		}

	return plantas;
}


}