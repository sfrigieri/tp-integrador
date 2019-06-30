package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;

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


	public Planta buscarPlanta(Planta inicial, Insumo i){

		//Retorno Dikstra modificado para recibir un TreeMap con valores ordenados naturamente.
		TreeMap<Integer,Planta> plantasPorDistancia = this.caminosMinimoDikstra(inicial);

		while (!plantasPorDistancia.isEmpty()){ 
			//La primer planta será la de menor distancia
			Planta actual = plantasPorDistancia.pollFirstEntry().getValue();
			if(actual.necesitaInsumo(i)) return actual;
		}

		return null;
	}

	public Planta buscarPlanta(Insumo i){

		//Asumiendo PlantaAcopio como Fuente y PlantaFinal como Sumidero
		Planta inicial = this.vertices
				.stream()
				.filter( v -> this.gradoEntrada(v)==0)
				.findFirst().get().getValor();

		//Retorno Dikstra modificado para recibir un TreeMap con valores ordenados naturalmente.
		TreeMap<Integer,Planta> plantasPorDistancia = this.caminosMinimoDikstra(inicial);

		Planta plantaPrioritaria = null;
		int cantMax = 0;

		while (!plantasPorDistancia.isEmpty()){ 

			Planta actual = plantasPorDistancia.pollFirstEntry().getValue();
			int cantNecesaria = actual.cantidadNecesariaInsumo(i);

			if(cantNecesaria > cantMax) {
				cantMax = cantNecesaria;
				plantaPrioritaria = actual;
			}	
		}

		return plantaPrioritaria;

	}



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