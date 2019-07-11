package isi.died.tp.service;

import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.*;
import isi.died.tp.model.*;
import isi.died.tp.estructuras.*;

public class PlantaServiceDefault implements PlantaService {


	private PlantaDao dao;


	public PlantaServiceDefault() {
		super();
		this.dao = new PlantaDaoDefault();
	}

	@Override
    public int flujoMaximoRed(Vertice<Planta> origen){
       
    	int flujoMaximo = 0;
    	int flujoActual = Integer.MAX_VALUE;
    	
    	while(flujoActual > 0) {
    		flujoActual = this.dao.buscarProximoFlujoDisponible(origen);
    		flujoMaximo = flujoMaximo + flujoActual;

        }
         
    	dao.resetFlujo();
    	
        return flujoMaximo;
    }

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
		
		
		for (i = 1; i <= cantStocks; i++) { 
			for (p = 1; p <= capCamion; p++){
				if (pesos.get(i-1) <= p) 
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
}
