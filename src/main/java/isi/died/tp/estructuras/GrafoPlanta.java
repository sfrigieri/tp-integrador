package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import isi.died.tp.model.*;

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

		PlantaAcopio inicial = null;
		Planta plantaPrioritaria = null;

		for(Vertice<Planta> p : this.vertices) {
			if(p.getValor() instanceof PlantaAcopio && p.getValor().esOrigen())
				inicial = (PlantaAcopio) p.getValor();
		}

		//Retorno Dikstra modificado para recibir un TreeMap con valores ordenados naturalmente.
		if(inicial != null) {
			TreeMap<Integer,Planta> plantasPorDistancia = this.caminosMinimoDikstra(inicial);


			int cantMax = 0;

			while (!plantasPorDistancia.isEmpty()){ 

				Planta actual = plantasPorDistancia.pollFirstEntry().getValue();
				int cantNecesaria = actual.cantidadNecesariaInsumo(i);

				if(cantNecesaria > cantMax) {
					cantMax = cantNecesaria;
					plantaPrioritaria = actual;
				}	
			}
		}
		return plantaPrioritaria;

	}



	private List<Planta> buscarPlantasCercanas(Vertice<Planta> v1,int saltos){

		List<Vertice<Planta>> visitados = new ArrayList<Vertice<Planta>>();
		visitados.add(v1);
		return this.buscarPlantasCercanas(v1, saltos, visitados);

	}

	private List<Planta> buscarPlantasCercanas(Vertice<Planta> v1,int saltos,List<Vertice<Planta>> visitados){

		List<Planta> plantas = new ArrayList<Planta>();
		List<Vertice<Planta>> copiaVisitados = visitados.stream().collect(Collectors.toList());
		if(saltos >= 0) {
			if(!copiaVisitados.contains(v1)) {
				plantas.add(v1.getValor());
				copiaVisitados.add(v1);
			}

			for(Vertice<Planta> ady: this.getAdyacentes(v1))
				plantas.addAll(buscarPlantasCercanas(ady, saltos-1, copiaVisitados));
		}

		return plantas;
	}


	public List<Recorrido> buscarCaminosInfo(Planta p1,Planta p2){
		Vertice<Planta> origen = this.getNodo(p1);
		Vertice<Planta> destino = this.getNodo(p2);
		List<Planta> visitados = new ArrayList<Planta>();
		List<Ruta> recActual = new ArrayList<Ruta>();
		List<Recorrido> recorridos = new ArrayList<Recorrido>();
		
		this.buscarCaminosInfo(origen, destino,visitados,recorridos, recActual);
		
		for(Recorrido r : recorridos)
			r.calcularInfo();
		
		return recorridos;	

	}

	private void buscarCaminosInfo(Vertice<Planta> v1,Vertice<Planta> v2,List<Planta> visitados,List<Recorrido> recorridos, List<Ruta> recActual){

		List<Planta> copiaVisitados = visitados.stream().collect(Collectors.toList());

		if(!copiaVisitados.contains(v1.getValor())) {   
			copiaVisitados.add(v1.getValor());
			//System.out.println("p:"+v1.getValor().getNombre()+" cant caminos: "+this.getAristasSalientes(v1).size()); 
			if(v1.equals(v2))
				recorridos.add(new Recorrido(recActual));
			else
				for(Arista<Planta> ruta : this.getAristasSalientes(v1)) {
					Ruta rutaAux = (Ruta) ruta;
					//if(!recActual.contains(rutaAux)){ 
						//System.out.println("p1:"+ruta.getInicio().getValor().getNombre()+" p2: "+ruta.getFin().getValor().getNombre()); 
						List<Ruta> copiaRecActual = recActual.stream().collect(Collectors.toList());
						copiaRecActual.add(rutaAux);
						buscarCaminosInfo(ruta.getFin(), v2, copiaVisitados,recorridos, copiaRecActual);
					//}
				}
		}

	}


	@Override
	public void conectar(Planta n1,Planta n2){
		this.conectar(getNodo(n1), getNodo(n2), 0,0.0,0);
	}


	public void conectar(Planta n1,Planta n2, int distanciaKm, double duracionViaje, int pesoMax){
		this.conectar(this.getNodo(n1), this.getNodo(n2), distanciaKm, duracionViaje, pesoMax);
	}


	public void conectar(Vertice<Planta> nodo1,Vertice<Planta> nodo2, int distanciaKm, double duracionViaje, int pesoMax){
		this.aristas.add(new Ruta(nodo1, nodo2, distanciaKm, duracionViaje, pesoMax));
		//acomodar el 0 del new ruta
	}

	public void setRutas(List<Arista<Planta>> lista) {
		this.aristas = lista;
	}

	public List<Arista<Planta>> getRutas() {
		return this.aristas;

	}



	public void resetFlujo() {
		for (Arista<Planta> ruta : this.aristas)
			ruta.setPesoEnCurso(0);
	}



	public int buscarProximoFlujoDisponible(Vertice<Planta> vertice) {
		//Búsqueda en anchura para encontrar algún camino con flujo disponible y utilizarlo
		//Se retorna el flujo máximo posible a utilizar, previamente habiendo actualizado el flujo en cada arista.
		//Si ya no hay más flujo disponible, retornará cero.


		//Recorrido en anchura pero guardando el recorrido previo para luego actualizar el flujo
		Set<Vertice<Planta>> visitados = new HashSet<Vertice<Planta>>();
		Queue<PorVisitar> aVisitarCola = new LinkedList<PorVisitar>();

		aVisitarCola.add(new PorVisitar(vertice, null, null, Integer.MAX_VALUE));

		while(!aVisitarCola.isEmpty()) {

			PorVisitar aVisitar = aVisitarCola.poll();
			Vertice<Planta> visitar = aVisitar.getVertice();

			//if(this.getAdyacentes(visitar).size() == 0) {
			if(visitar.getValor() instanceof PlantaAcopio && !visitar.getValor().esOrigen()) {
				//Si llegué al sumidero, flujoActual tendrá el valor de flujo máximo posible por ese camino
				int flujoActual = aVisitar.getFlujoEnCurso();

				// Volver por el mismo camino
				while(aVisitar != null && aVisitar.getRuta() != null){
					// sumo al flujo previo el nuevo flujo máximo posible que llegará al sumidero
					aVisitar.getRuta().setPesoEnCurso(aVisitar.getRuta().getPesoEnCurso() + flujoActual);
					aVisitar = aVisitar.getAnterior();
				}

				return flujoActual;
			}

			if(!visitados.contains(visitar)) {
				visitados.add(visitar);

				for(Arista<Planta> ruta : this.getAristasSalientes(visitar)) {
					if(ruta.getPesoMaxTon() - ruta.getPesoEnCurso() > 0)
						aVisitarCola.add(new PorVisitar(ruta.getFin(), aVisitar, ruta, Math.min(ruta.getPesoMaxTon() - ruta.getPesoEnCurso(), aVisitar.getFlujoEnCurso())));
				}
			}
		}
		return 0;
	}


	class PorVisitar {
		Arista<Planta> ruta;
		PorVisitar anterior;
		Vertice<Planta> vertice;                   
		int flujoEnCurso;

		public PorVisitar(Vertice<Planta> vertice, PorVisitar anterior, Arista<Planta> ruta, int flujoEnCurso) {
			this.vertice = vertice;
			this.anterior = anterior;
			this.ruta = ruta;
			this.flujoEnCurso = flujoEnCurso;
		}

		public Arista<Planta> getRuta() {
			return ruta;
		}

		public void setRuta(Ruta ruta) {
			this.ruta = ruta;
		}

		public PorVisitar getAnterior() {
			return anterior;
		}

		public void setAnterior(PorVisitar anterior) {
			this.anterior = anterior;
		}

		public Vertice<Planta> getVertice() {
			return vertice;
		}

		public void setVertice(Vertice<Planta> vertice) {
			this.vertice = vertice;
		}

		public int getFlujoEnCurso() {
			return flujoEnCurso;
		}

		public void setFlujoEnCurso(int flujoEnCurso) {
			this.flujoEnCurso = flujoEnCurso;
		}    


	}


	public Boolean existeCamino(Planta p1, Planta p2) {
		return this.existeCamino(new Vertice<Planta>(p1),new Vertice<Planta>(p2));
		
	}

	public Boolean existenPlantas() {
		return ! this.vertices.isEmpty();
	}



}