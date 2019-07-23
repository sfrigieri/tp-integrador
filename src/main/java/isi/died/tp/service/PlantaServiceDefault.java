package isi.died.tp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isi.died.tp.dao.*;
import isi.died.tp.model.*;
import isi.died.tp.estructuras.*;

public class PlantaServiceDefault implements PlantaService {


	private PlantaDao plantaDao;
	private InsumoService is;
	private StockService ss;
	private RutaService rs;

	public PlantaServiceDefault() {
		super();
		this.plantaDao = new PlantaDaoDefault();
	}

	@Override
	public void setInsumoService(InsumoService is) {
		this.is = is;

	}

	@Override
	public void setStockService(StockService ss) {
		this.ss = ss;

	}

	@Override
	public void setRutaService(RutaService rs) {
		this.rs = rs;
	}

	//Al cargar las rutas, en RutaService se pasa la lista correspondiente al Grafo
	@Override
	public void setRutas(List<Ruta> lista) {
		List<Arista<Planta>> listaAux = new ArrayList<Arista<Planta>>();
		for(Arista<Planta> ruta : lista)
			listaAux.add(ruta);

		plantaDao.setRutas(listaAux);
		/*for(Arista<Planta> ruta : lista)
			System.out.println(ruta.getInicio().getValor().getNombre()+" - "+ruta.getFin().getValor().getNombre());
		System.out.println("FIN");*/
	}

	@Override
	public void setStocksProduccion(List<StockProduccion> lista) {
		for(StockProduccion s: lista) {
			for(Planta p : this.listaPlantasProduccion()) {
				if(p.equals(s.getPlanta()))
					p.addStock(s);
			}
		}

	}

	@Override
	public void addInsumos(List<Insumo> lista) {
		plantaDao.addInsumos(lista);

	}

	@Override
	public Planta agregarPlanta(Planta planta) {
		plantaDao.agregarPlanta(planta);
		return planta;
	}

	@Override
	public List<Planta> listaPlantas() {
		return plantaDao.listaPlantas();
	}


	@Override
	public List<PlantaProduccion> listaPlantasProduccion() {
		return plantaDao.listaPlantasProduccion();
	}

	@Override
	public List<PlantaAcopio> listaPlantasAcopio() {
		return plantaDao.listaPlantasAcopio();
	}


	@Override
	public void editarPlanta(Planta planta) {
		plantaDao.editarPlanta(planta);

	}

	@Override
	public void eliminarPlanta(Planta planta) {
		//Grafo Planta elimina el nodo y sus rutas incidentes
		plantaDao.eliminarPlanta(planta);
		//Entonces es necesario: actualizar desde PlantaService la LISTA_RUTAS en RutaService
		rs.setRutas(plantaDao.listaRutas());
		//Y, si era PlantaProduccion, eliminar sus stocksProduccion
		if(planta instanceof PlantaProduccion)
			ss.eliminarStocksProduccion(((PlantaProduccion) planta).getListaDeStock());
	}

	@Override
	public PlantaProduccion buscarPlantaProduccion(Integer id) {
		return plantaDao.buscarPlantaProduccion(id);
	}

	@Override
	public PlantaAcopio buscarPlantaAcopio(Integer id) {
		return plantaDao.buscarPlantaAcopio(id);
	}

	// Item 4.2 a)
	@Override
	public List<PlantaProduccion> buscarPlantasNecesitanInsumo(Insumo ins) {
		List<PlantaProduccion> lista = new ArrayList<PlantaProduccion>();

		for(PlantaProduccion p : this.listaPlantasProduccion())
			if(p.necesitaInsumo(ins))
				lista.add(p);

		return lista;
	}


	// Item 4.2 b)
	@Override
	public Recorrido mejorCaminoEnvio(List<PlantaProduccion> plantas) {

		if(!plantas.isEmpty()) {
			List<Recorrido> caminosPosibles = this.buscarCaminosInfo(this.buscarAcopioInicial(), this.buscarAcopioFinal());

			if(caminosPosibles.isEmpty())
				return null;

			List<Recorrido> caminosConteniendoPlantas = this.filtrarCaminosContienen(caminosPosibles, plantas);

			if(caminosConteniendoPlantas.isEmpty())
				return null;

			Recorrido mejorCaminoActual = null;

			for(Recorrido r : caminosConteniendoPlantas) {
				if(mejorCaminoActual == null)
					mejorCaminoActual = r;
				else 
					mejorCaminoActual = this.mejorCaminoEntre(mejorCaminoActual, r);
			}

			return mejorCaminoActual;
		}

		return null;

	}



	private Recorrido mejorCaminoEntre(Recorrido r1, Recorrido r2) {

		if(r1.getDuracionTotal() < r2.getDuracionTotal())
			return r1;
		else
			if(r1.getDuracionTotal() > r2.getDuracionTotal())
				return r2;
			else
				if(r1.getDistanciaTotal() < r2.getDistanciaTotal())
					return r1;

		return r2;
	}


	private List<Recorrido> filtrarCaminosContienen(List<Recorrido> caminos, List<PlantaProduccion> plantas) {

		List<Recorrido> caminosConteniendoPlantas = new ArrayList<Recorrido>();
		List<PlantaProduccion> listaAux = plantas.stream().collect(Collectors.toList());

		for(Recorrido recorrido : caminos) {
			for(Ruta ruta : recorrido.getRecorrido()) {
				if(listaAux.contains(ruta.getInicio().getValor()))
					listaAux.remove(ruta.getInicio().getValor());

				if(listaAux.contains(ruta.getFin().getValor()))
					listaAux.remove(ruta.getFin().getValor());
			}

			if(listaAux.isEmpty())
				caminosConteniendoPlantas.add(recorrido);

			listaAux = plantas.stream().collect(Collectors.toList());
		}	

		return caminosConteniendoPlantas;
	}


	// Item 4.3
	@Override
	public List<Recorrido> buscarCaminosInfo(Planta p1, Planta p2) {
		return plantaDao.buscarCaminosInfo(p1,p2);
	}


	// Item 5.1
	@Override
	public int flujoMaximoRed(Planta origen){

		int flujoMaximo = 0;
		int flujoActual = Integer.MAX_VALUE;

		while(flujoActual > 0) {
			flujoActual = this.plantaDao.buscarProximoFlujoDisponible(new Vertice<Planta>(origen));
			flujoMaximo = flujoMaximo + flujoActual;

		}

		plantaDao.resetFlujo();

		return flujoMaximo;
	}


	// Item 5.2
	@Override
	public void generarPageRanks() {


		List<Planta> plantas = plantaDao.listaPlantas();
		double difPromedio;	

		do {
			List<Double> nuevosPageRanks = new ArrayList<Double>();
			difPromedio = 0;	

			for(Planta p: plantas) {
				nuevosPageRanks.add(plantaDao.generarNuevoPageRank(p));
			}

			if(nuevosPageRanks.size() != 0)
				for(int i = 0; i < plantas.size(); i++) {
					difPromedio = difPromedio + Math.abs(nuevosPageRanks.get(i) - plantas.get(i).getPageRank());
					plantas.get(i).setPageRank(nuevosPageRanks.get(i));
					//System.out.println(plantas.get(i).getNombre()+" - Page Rank: "+plantas.get(i).getPageRank());
				}

			difPromedio = difPromedio / nuevosPageRanks.size();
		} while(difPromedio > 0.000001);

		for(int i = 0; i < plantas.size(); i++) {
			System.out.println(plantas.get(i).getNombre()+" - Page Rank: "+plantas.get(i).getPageRank());
		}

	}

	@Override
	public void resetPageRanks() {
		plantaDao.resetPageRanks();
	}


	//Item 5.3 a) "EL sistema debe mostrar una lista de los insumos faltantes y cantidad"
	@Override
	public List<StockAcopio> generarStockFaltante() {
		List<StockAcopio> lista = new ArrayList<StockAcopio>();

		for(PlantaProduccion p : this.listaPlantasProduccion())
			lista.addAll(this.generarStockFaltante(p));

		return lista;
	}

	private List<StockAcopio> generarStockFaltante(PlantaProduccion p) {
		List<StockAcopio> lista = new ArrayList<StockAcopio>();

		for(Insumo i : is.listaInsumos()) {
			int cant = p.cantidadNecesariaInsumo(i);
			if(cant != 0)	//No corresponde Id, StocksAcopio utilizados temporalmente 
				lista.add(new StockAcopio(-1,cant,i,p));

		}

		for(Insumo il : is.listaInsumosLiquidos()) {
			int cant = p.cantidadNecesariaInsumo(il);
			if(cant != 0)	//No corresponde Id, StocksAcopio utilizados temporalmente 
				lista.add(new StockAcopio(-1,cant,il,p));

		}

		lista.sort((s1,s2) -> s1.getCantidad().compareTo(s2.getCantidad()));
		return lista;
	}



	//Item 5.3 c) Si se procede a seleccionar camión y generar solución,
	//deben generarse los pedidos posibles con el stock disponible en PlantaAcopio inicial
	@Override
	public List<StockAcopio> generarStockFaltanteDisponible() {
		List<StockAcopio> lista = new ArrayList<StockAcopio>();
		List<StockAcopio> listaAux;
		//listaAux recibe stocks ordenados de menor a mayor cantidad faltante.
		//lo que permite maximizar la cantidad de envíos a realizar por camión/viaje
		for(Insumo i : is.listaInsumos()) {
			listaAux = generarStockFaltante(i);
			if(i.getStock() != null) {
				Integer cantDisponible = i.getStock().getCantidad();

				if(cantDisponible != null) {

					for(StockAcopio s : listaAux) {
						if(cantDisponible > 0 && s.getCantidad() <= cantDisponible) {
							lista.add(s);
							cantDisponible = cantDisponible - s.getCantidad();
						}
					}
				}
			}
		}

		for(Insumo i : is.listaInsumosLiquidos()) {
			listaAux = generarStockFaltante(i);
			if(i.getStock() != null) {
				Integer cantDisponible = i.getStock().getCantidad();

				if(cantDisponible != null) {

					for(StockAcopio s : listaAux) {
						if(cantDisponible > 0 && s.getCantidad() <= cantDisponible) {
							lista.add(s);
							cantDisponible = cantDisponible - s.getCantidad();
						}
					}
				}
			}
		}

		return lista;
	}

	private List<StockAcopio> generarStockFaltante(Insumo ins) {
		List<StockAcopio> lista = new ArrayList<StockAcopio>();

		for(PlantaProduccion p : this.listaPlantasProduccion()){
			int cant = p.cantidadNecesariaInsumo(ins);
			if(cant != 0)	//No corresponde Id, StocksAcopio utilizados temporalmente 
				lista.add(new StockAcopio(-1,cant,ins,p));

		}
		//Se ordena la lista de stock, de menor a mayor cantidad faltante
		//para maximizar la cantidad de envíos a diferentes plantas
		lista.sort((s1,s2) -> s1.getCantidad().compareTo(s2.getCantidad()));
		return lista;
	}

	// Item 5.3 c) Luego con la lista de StocksAcopio disponibles y el camión seleccionado
	// se genera la mejor selección para el envío
	@Override
	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {

		List<Integer> costos = new ArrayList<Integer>();
		List<Integer> pesos = new ArrayList<Integer>();
		int capCamion = (int) camion.getCapacidad();

		int cantStocks = listaDisponibles.size();

		//Recibo arraylist con StockAcopio,  respetando posiciones creo array pesos y array costos.
		for(StockAcopio stock : listaDisponibles) {
			costos.add((int) (stock.getCantidad()*stock.getInsumo().getCosto()));
		}

		for(StockAcopio stock : listaDisponibles) {
			pesos.add((int) (stock.getCantidad()*stock.getInsumo().getPeso()));
		}


		int i, p; 
		int[][] resultado = new int[cantStocks+1][capCamion+1]; 

		//Si la cant Stock es cero o la capacidad es cero, el costo total será cero.
		for (p = 0; p <= capCamion; p++)
			resultado[0][p] = 0;

		for (i = 0; i <= cantStocks; i++) 
			resultado[i][0] = 0;   

		//Comenzando con el primer stock de la lista, 
		for (i = 1; i <= cantStocks; i++) { 
			for (p = 1; p <= capCamion; p++){ 
				//Si el peso del stock i no supere a la cap del camión
				if (pesos.get(i-1) <= p) 
					//El peso del stock i es IGUAL al peso ocupado hasta el momento por stocks previos seleccionados
					// Siempre se busca maximizar la relacion Costo/Peso: 
					//Para el mismo peso ocupado, al INCLUIR al stock i, puedo aumentar el costo resultante?

					//Entonces debo realizar una comparación con el costo total acumulado previamente (SIN el stock i)
					//Procedo a descontar del peso disponible el peso del stock i y consultar si al incluirlo a la selección,
					//su costo + el mejor costo posible utilizando el peso restante (p actual - peso i)
					//supera al mejor costo obtenido previamente para el peso p actual.
					//Si lo supera guardo el costo de esta nueva combinación, sino mantengo el mejor resultado previo.
					resultado[i][p] = Math.max(costos.get(i-1) + resultado[i-1][p-pesos.get(i-1)],  resultado[i-1][p]); 
				else
					resultado[i][p] = resultado[i-1][p]; 
			} 
		} 
		//System.out.println(resultado[cantStocks][capCamion]);
		//Busco stocks que pertenecen a la selección final
		int temp = capCamion;
		List<StockAcopio> listaResultante = new ArrayList<StockAcopio>();

		for (i = cantStocks; i > 0 && temp > 0; i--)  {
			//Si el costo i es diferente a i-1 para la misma capacidad, entonces el stock i pertenece a la selección.
			if (resultado[i][temp] != resultado[i - 1][temp]) {
				//el peso de i se encuentra en la posición i-1
				temp = temp - pesos.get(i-1);
				//el stock i seleccionado se encuentra en la posición i-1
				listaResultante.add(listaDisponibles.get(i-1));
			}
		}

		return listaResultante;
	}

	@Override
	public PlantaAcopio buscarAcopioInicial() {
		return plantaDao.buscarAcopioInicial();
	}

	@Override
	public PlantaAcopio buscarAcopioFinal() {
		return plantaDao.buscarAcopioFinal();
	}


}
