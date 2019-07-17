package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;



public class Grafo<T> {
	protected List<Arista<T>> aristas;
	protected List<Vertice<T>> vertices;

	public Grafo(){
		this.aristas = new ArrayList<Arista<T>>();
		this.vertices = new ArrayList<Vertice<T>>();
	}

	public void addNodo(T nodo){
		this.addNodo(new Vertice<T>(nodo));
	}

	private void addNodo(Vertice<T> nodo){
		this.vertices.add(nodo);
	}

	public void conectar(T n1,T n2){
		this.conectar(getNodo(n1), getNodo(n2), 1);
	}

	public void conectar(T n1,T n2,Integer valor){
		this.conectar(getNodo(n1), getNodo(n2), valor);
	}

	private void conectar(Vertice<T> nodo1,Vertice<T> nodo2,Integer valor){
		this.aristas.add(new Arista<T>(nodo1,nodo2,valor));
	}

	public Vertice<T> getNodo(T valor){
		return this.vertices.get(this.vertices.indexOf(new Vertice<T>(valor)));
	}

	public void removeNodo(T valor) {
		Vertice<T> vertice = new Vertice<T>(valor);
		if(vertices.contains(vertice)) {
			aristas.removeIf(v -> v.getInicio().equals(vertice) || v.getFin().equals(vertice));
			vertices.remove(vertice);
		}
	}
	
	public List<T> getAdyacentes(T valor){ 
		Vertice<T> unNodo = this.getNodo(valor);
		List<T> salida = new ArrayList<T>();
		for(Arista<T> enlace : this.aristas){
			if( enlace.getInicio().equals(unNodo)){
				salida.add(enlace.getFin().getValor());
			}
		}
		return salida;
	}

	
	public void eliminarNodo(T val) {
		if(vertices.contains(new Vertice<T>(val))) {
			aristas.removeIf(v -> v.getInicio().equals(new Vertice<T>(val)) || v.getFin().equals(new Vertice<T>(val)));
			vertices.remove(new Vertice<T>(val));
		}
	}
	
	
    public Boolean esVacio(){
    	if(this.vertices!= null && !this.vertices.isEmpty()) return false;
    	return true;
    }

    public List<T> listaVertices(){
    	List<T> lista = new ArrayList<>();
    	this.vertices.forEach(v -> lista.add(v.getValor()));
    	return lista;
    }
    
    
    public List<Vertice<T>> getAdyacentes(Vertice<T> unNodo){ 
		List<Vertice<T>> salida = new ArrayList<Vertice<T>>();
		for(Arista<T> enlace : this.aristas){
			if( enlace.getInicio().equals(unNodo)){
				salida.add(enlace.getFin());
			}
		}
		return salida;
	}

	public void reemplazarNodo(T anterior, T nuevo) {
		if(vertices.contains(new Vertice<T>(anterior))) {
			for(Vertice<T> v: vertices) {
				if(v.equals(new Vertice<T>(anterior))) {
					v.setValor(nuevo);
				}
			}
		}
		
	}
	
	public void imprimirAristas(){
		System.out.println(this.aristas.toString());
	}
	
	public List<Vertice<T>> getPredecesores(Vertice<T> v){
		List<Vertice<T>> predecesores = new ArrayList<Vertice<T>>();
		
		for(Arista<T> arista: this.aristas) {
			if(arista.getFin().equals(this.getNodo(v.getValor())))
				predecesores.add(arista.getInicio());
		}
		
		return predecesores;
	}

	public Integer gradoEntrada(Vertice<T> vertice){
		Integer res =0;
		for(Arista<T> arista : this.aristas){
			if(arista.getFin().equals(vertice)) ++res;
		}
		return res;
	}

	public Integer gradoSalida(Vertice<T> vertice){
		Integer res =0;
		for(Arista<T> arista : this.aristas){
			if(arista.getInicio().equals(vertice)) ++res;
		}
		return res;
	}
	
	
	public List<Arista<T>> getAristasSalientes(Vertice<T> vertice){
		List<Arista<T>>  res = new ArrayList<Arista<T>>();
		for(Arista<T> arista : this.aristas){
			if(arista.getInicio().equals(vertice)) res.add(arista);
		}
		return res;
	}

	public List<T> recorridoAnchura(Vertice<T> inicio){
		List<T> resultado = new ArrayList<T>();
		//estructuras auxiliares
		Queue<Vertice<T>> pendientes = new LinkedList<Vertice<T>>();
		HashSet<Vertice<T>> marcados = new HashSet<Vertice<T>>();
		marcados.add(inicio);
		pendientes.add(inicio);

		while(!pendientes.isEmpty()){
			Vertice<T> actual = pendientes.poll();
			List<Vertice<T>> adyacentes = this.getAdyacentes(actual);
			resultado.add(actual.getValor());
			for(Vertice<T> v : adyacentes){
				if(!marcados.contains(v)){ 
					pendientes.add(v);
					marcados.add(v);
				}
			}
		}		
		//System.out.println(resultado);
		return resultado;
	}

	public List<T> recorridoProfundidad(Vertice<T> inicio){
		List<T> resultado = new ArrayList<T>();
		Stack<Vertice<T>> pendientes = new Stack<Vertice<T>>();
		HashSet<Vertice<T>> marcados = new HashSet<Vertice<T>>();
		marcados.add(inicio);
		pendientes.push(inicio);

		while(!pendientes.isEmpty()){
			Vertice<T> actual = pendientes.pop();
			List<Vertice<T>> adyacentes = this.getAdyacentes(actual);
			resultado.add(actual.getValor());
			for(Vertice<T> v : adyacentes){
				if(!marcados.contains(v)){ 
					pendientes.push(v);
					marcados.add(v);
				}
			}
		}		
		//System.out.println(resultado);
		return resultado;
	}

	public List<T> recorridoTopologico(){
		List<T> resultado = new ArrayList<T>();
		Map<Vertice<T>,Integer> gradosVertice = new HashMap<Vertice<T>,Integer>();
		for(Vertice<T> vert : this.vertices){
			gradosVertice.put(vert, this.gradoEntrada(vert));
		}
		while(!gradosVertice.isEmpty()){

			List<Vertice<T>> nodosSinEntradas = gradosVertice.entrySet()
					.stream()
					.filter( x -> x.getValue()==0)
					.map( p -> p.getKey())
					.collect( Collectors.toList());

			if(nodosSinEntradas.isEmpty()) System.out.println("TIENE CICLOS");

			for(Vertice<T> v : nodosSinEntradas){
				resultado.add(v.getValor());
				gradosVertice.remove(v);
				for(Vertice<T> ady: this.getAdyacentes(v)){
					gradosVertice.put(ady,gradosVertice.get(ady)-1);
				}
			}
		}

		System.out.println(resultado);
		return resultado;
	}

	private boolean esAdyacente(Vertice<T> v1,Vertice<T> v2){
		List<Vertice<T>> ady = this.getAdyacentes(v1);
		for(Vertice<T> unAdy : ady){
			if(unAdy.equals(v2)) return true;
		}
		return false;
	}

	public boolean esAdyacente(T n1, T n2) {
		Vertice<T> origen = this.getNodo(n1);
		Vertice<T> destino = this.getNodo(n2);
		return this.esAdyacente(origen, destino);
	}

	public List<List<Vertice<T>>> caminos(T v1,T v2){
		return this.caminos(new Vertice<T>(v1), new Vertice<T>(v2));
	}


	public List<List<Vertice<T>>> caminos(Vertice<T> v1,Vertice<T> v2){
		List<List<Vertice<T>>>salida = new ArrayList<List<Vertice<T>>>();
		List<Vertice<T>> marcados = new ArrayList<Vertice<T>>();
		marcados.add(v1);
		buscarCaminosAux(v1,v2,marcados,salida);
		return salida;
	}
	
	
	private void buscarCaminosAux(Vertice<T> v1,Vertice<T> v2, List<Vertice<T>> marcados, List<List<Vertice<T>>> todos) {

		// Vector copiaMarcados;
		List<Vertice<T>>  copiaMarcados =null;
		;

		for(Vertice<T> ady: this.getAdyacentes(v1)){
			System.out.println(">> " + ady);
			copiaMarcados = marcados.stream().collect(Collectors.toList());
			if(ady.equals(v2)) {
				copiaMarcados.add(v2);
				todos.add(new ArrayList<Vertice<T>>(copiaMarcados));
				System.out.println("ENCONTRO CAMINO "+ todos.toString());
			} else {
				if( !copiaMarcados.contains(ady)) {
					copiaMarcados.add(ady);
					this.buscarCaminosAux(ady,v2,copiaMarcados,todos);
				}
			}
		}

	}

	public TreeMap<Integer,T> caminosMinimoDikstra(T valorOrigen){
		Vertice<T> vOrigen = new Vertice<T>(valorOrigen);
		Map<Vertice<T>, Integer> caminosResultado = this.caminosMinimoDikstra(vOrigen);
		TreeMap<Integer,T> resultado = new TreeMap<Integer,T>();
		for(Entry<Vertice<T>, Integer> unNodo : caminosResultado.entrySet()) {
			if(unNodo.getValue() != Integer.MAX_VALUE)
				resultado.put(unNodo.getValue(), unNodo.getKey().getValor());
		}
		return resultado;
	}

	private Map<Vertice<T>, Integer> caminosMinimoDikstra(Vertice<T> origen) {

		// inicializo todas las distancias a INFINITO
		Map<Vertice<T>, Integer> distancias = new HashMap<Vertice<T>, Integer>();
		for(Vertice<T> unVertice : this.vertices) {
			distancias.put(unVertice, Integer.MAX_VALUE);
		}
		distancias.put(origen, 0);

		// guardo visitados y pendientes de visitar
		Set<Vertice<T>> visitados = new HashSet<Vertice<T>>();
		TreeMap<Integer,Vertice<T>> aVisitar= new TreeMap<Integer,Vertice<T>>();

		aVisitar.put(0,origen);
	
		while (!aVisitar.isEmpty()) {
			Entry<Integer, Vertice<T>> nodo = aVisitar.pollFirstEntry();
			visitados.add(nodo.getValue());

			int nuevaDistancia = Integer.MIN_VALUE;
			List<Vertice<T>> adyacentes = this.getAdyacentes(nodo.getValue());

			for(Vertice<T> unAdy : adyacentes) {
				if(!visitados.contains(unAdy)) {
					Arista<T> enlace = this.buscarArista(nodo.getValue(), unAdy);
					if(enlace !=null) {
						nuevaDistancia = enlace.getValor().intValue();
					}
					int distanciaHastaAdy = distancias.get(nodo.getValue()).intValue();
					int distanciaAnterior = distancias.get(unAdy).intValue();
					if(distanciaHastaAdy  + nuevaDistancia < distanciaAnterior ) {
						distancias.put(unAdy, distanciaHastaAdy  + nuevaDistancia);
						aVisitar.put(distanciaHastaAdy  + nuevaDistancia,unAdy);
					}        			
				}
			}    		
		}
		//System.out.println("DISTANCIAS DESDE "+origen);
		//System.out.println("Resultado: "+distancias);
		return distancias;
	}

	protected Arista<T> buscarArista(T v1, T v2){
		return this.buscarArista(new Vertice<T>(v1), new Vertice<T>(v2));
	}


	protected Arista<T> buscarArista(Vertice<T> v1, Vertice<T> v2){
		for(Arista<T> unaArista : this.aristas) {

			if(unaArista.getInicio().equals(v1) && unaArista.getFin().equals(v2)) return unaArista;
		}
		return null;
	}

	public void floydWarshall() {
		int cantVertices= this.vertices.size();
		int[][] matrizDistancias = new int[cantVertices][cantVertices];

		for(int i=0; i<cantVertices;i++) {
			for(int j=0; j<cantVertices;j++) {
				if(i== j) {
					matrizDistancias[i][j] = 0;        			
				}else {
					Arista<T> arista = this.buscarArista(this.vertices.get(i), this.vertices.get(j));
					if(arista!=null) {
						matrizDistancias[i][j] = arista.getValor().intValue();        			
					} else {
						matrizDistancias[i][j] = 9999;        			
					}
				}
			}    		
		}
		imprimirMatriz(matrizDistancias);

		for (int k = 0; k < cantVertices; k++) 
		{ 
			// Pick all vertices as source one by one 
			for (int i = 0; i < cantVertices; i++) 
			{ 
				// Pick all vertices as destination for the 
				// above picked source 
				for (int j = 0; j < cantVertices; j++) 
				{ 
					// If vertex k is on the shortest path from 
					// i to j, then update the value of dist[i][j] 
					if (matrizDistancias[i][k] + matrizDistancias[k][j] < matrizDistancias[i][j]) 
						matrizDistancias[i][j] = matrizDistancias[i][k] + matrizDistancias[k][j]; 
				} 
			} 
			System.out.println("MATRIZ "+k);
			imprimirMatriz(matrizDistancias);
		} 

	}

	public void imprimirMatriz(int[][] m) {
		for(int i=0; i<m.length;i++) {
			System.out.print("[ ");
			for(int j=0; j<m[i].length;j++) {
				System.out.print(i+":"+j+" = "+m[i][j]+ ",   ");
			}
			System.out.println(" ]");
		}



	}

	public Boolean existeCamino(Vertice<T> v1, Vertice<T> v2, Integer n) {

		Stack<Vertice<T>> visitar = new Stack<Vertice<T>>();
		HashSet<Vertice<T>> visitados = new HashSet<Vertice<T>>();

		visitar.push(v1);
		int saltos = 0;

		while(!visitar.empty()) {
			saltos++;
			Vertice<T> vInicio = visitar.pop();
			for(Vertice<T> unAdya : this.getAdyacentes(vInicio)) {
				if(saltos<=n && unAdya.equals(v2)) return true;
				if(!visitados.contains(unAdya)) {
					visitar.push(unAdya);
					visitados.add(unAdya);
				}
			}
		}
		return false;
	}


	
	public Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n) {
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		visitados.add(v1);
		return existeCaminoRec(v1, v2, n,visitados );
	}

	private Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n, List<Vertice<T>> visitados) {
		if (n < 0)
			return false;
		if(n >= 0 && v1.equals(v2))
			return true;
		for (Vertice<T> unAdya : this.getAdyacentes(v1)) {
			List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());
			if (!copiaVisitados.contains(unAdya)) {
				copiaVisitados.add(unAdya);
				Boolean existe = existeCaminoRec(unAdya, v2, n - 1, copiaVisitados);
				if (existe)
					return true;
			}

		}
		return false;
	}
	

	
	public Boolean existeCaminoRecCond(Vertice<T> v1, Vertice<T> v2, Integer n, Condicion<T> cond) {
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		visitados.add(v1);
		return existeCaminoRecCond(v1, v2, n,visitados, cond);
	}

	private Boolean existeCaminoRecCond(Vertice<T> v1, Vertice<T> v2, Integer n, List<Vertice<T>> visitados, Condicion<T> cond) {
		if (n < 0 || !cond.cumple(v1.getValor()) || !cond.cumple(v2.getValor()) )
			return false;
		if(n >= 0 && v1.equals(v2))
			return true;
		for (Vertice<T> unAdya : this.getAdyacentes(v1)) {
			List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());
			if (!copiaVisitados.contains(unAdya)) {
				copiaVisitados.add(unAdya);
				Boolean existe = existeCaminoRecCond(unAdya, v2, n - 1, copiaVisitados, cond);
				if (existe)
					return true;
			}

		}
		return false;
	}
	
	
	
	public Boolean existeCaminoRecCondSaltoImpar(Vertice<T> v1, Condicion<T> cond) {
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		visitados.add(v1);
		return existeCaminoRecCondSaltoImpar(v1,0, visitados, cond);
	}

	private Boolean existeCaminoRecCondSaltoImpar(Vertice<T> v1, Integer n, List<Vertice<T>> visitados, Condicion<T> cond) {

		if( n%2 != 0 && cond.cumple(v1.getValor()) && visitados.size()>1)
			return true;
		
		for (Vertice<T> unAdya : this.getAdyacentes(v1)) {

			List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());
			if (!copiaVisitados.contains(unAdya)) {
				copiaVisitados.add(unAdya);
				Boolean existe = existeCaminoRecCondSaltoImpar(unAdya, n + 1, copiaVisitados, cond);
				if (existe)
					return true;
			}

		}
		
		return false;
	}
	
	
	
	

	public List<List<Vertice<T>>> buscarCaminosCond(T v1,T v2,int saltos, Condicion<T> cond){
		Vertice<T> origen = this.getNodo(v1);
		Vertice<T> destino = this.getNodo(v2);
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		List<List<Vertice<T>>> caminos = new ArrayList<List<Vertice<T>>>();
		this.buscarCaminosCond(origen, destino, saltos, visitados,caminos, cond);
	
		return caminos;	

	}

	private void buscarCaminosCond(Vertice<T> v1,Vertice<T> v2,int saltos,List<Vertice<T>> visitados,List<List<Vertice<T>>> caminos, Condicion<T> cond){

		if(saltos >= 0 && cond.cumple(v1.getValor()) && cond.cumple(v2.getValor())) { 

			List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());

			if(!copiaVisitados.contains(v1)) {   
				copiaVisitados.add(v1);

				if(v1.equals(v2))
					caminos.add(copiaVisitados);	
				else
					for(Vertice<T> ady: this.getAdyacentes(v1)) {
						buscarCaminosCond(ady, v2, saltos-1, copiaVisitados,caminos, cond);
					}
			}
		}	
	}
	
	
	public Integer alcanzarSumideros(Vertice<T> v1,int saltos){
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		List<Vertice<T>> sumideros = new ArrayList<Vertice<T>>();
		visitados.add(v1);
		this.alcanzarSumideros(v1,saltos, visitados,sumideros);
		return sumideros.size();
	

	}

	private void alcanzarSumideros(Vertice<T> v1,int saltos,List<Vertice<T>> visitados,List<Vertice<T>> sumideros){

		if(saltos >= 0) { 

				if(saltos == 0  && this.getAdyacentes(v1).size() == 0 && !sumideros.contains(v1))
					sumideros.add(v1);

					for(Vertice<T> ady: this.getAdyacentes(v1)) {
						if(!visitados.contains(ady)) { 
							List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());
							copiaVisitados.add(ady);
							alcanzarSumideros(ady,saltos-1, copiaVisitados,sumideros);
						}
					}
		}	
	}
	
	public Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n, Integer max) {
		List<Vertice<T>> visitados = new ArrayList<Vertice<T>>();
		visitados.add(v1);
		return existeCaminoRec(v1, v2, n,visitados,0, max );
	}

	private Boolean existeCaminoRec(Vertice<T> v1, Vertice<T> v2, Integer n, List<Vertice<T>> visitados,Integer costo, Integer max) {
		if (n < 0)
			return false;
		if(n >= 0 && v1.equals(v2) && costo<= max) {
			System.out.println("Vertice "+v1.getValor()+" Costo "+costo+" Saltos restantes "+n);
			return true;
		}
		for (Vertice<T> unAdya : this.getAdyacentes(v1)) {
			List<Vertice<T>> copiaVisitados = visitados.stream().collect(Collectors.toList());
			if (!copiaVisitados.contains(unAdya)) {
				copiaVisitados.add(unAdya);
				Boolean existe = existeCaminoRec(unAdya, v2, n - 1, copiaVisitados,costo+ (Integer) this.buscarArista(v1, unAdya).getValor(), max);
				if (existe)
					return true;
			}

		}
		return false;
	}

	public Boolean esCompleto(){
		
		for(Vertice<T> v1 : this.vertices) {
			for(Vertice<T> v2 : this.vertices) {
				if(!v1.equals(v2))
					if(!this.esAdyacente(v1, v2) && !this.esAdyacente(v2, v1))
						return false;
			}
		}
		return true;
	}
	
	
	public Integer excentricidad(Vertice<T> v1){
		TreeMap<Integer,T> caminosPorDistancia = this.caminosMinimoDikstra(v1.getValor());
		if(!caminosPorDistancia.isEmpty())
			return caminosPorDistancia.lastKey();
		return null;
	}
	
	
}
