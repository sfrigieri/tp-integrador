package isi.died.tp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.RedPlantasView;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;
public class GestionLogisticaController {

	public static GrafoPlantaController grafoController;
	public static GrafoPanel grafoPanel;
	public static InsumoController ic;
	public static PlantaController pc;
	private static RedPlantasView redPlantasView;
	private static JFrame framePadre;
	private Boolean firstTimeRed; 
	private Boolean firstTimeCaminos; 
	
	public GestionLogisticaController(JFrame v) {
		framePadre = v;
		firstTimeRed = true;
		firstTimeCaminos = true;
		ic = GestionEntidadesController.insumoController;
		pc = GestionEntidadesController.plantaController;
	}


	public void opcion(OpcionesMenuLogistica op) {
		switch(op) {
		case GESTION_RED_PLANTAS: 
			grafoController = new GrafoPlantaController(framePadre);
			grafoPanel = new GrafoPanel(framePadre, grafoController);
			grafoController.setGLC(this);
			grafoController.setGrafoPanel(grafoPanel);
			redPlantasView = new RedPlantasView(framePadre, this);
			redPlantasView.setRedPlantas(firstTimeRed); 
			firstTimeRed = false; 
			break;
		case BUSCAR_CAMINOS: 			
			grafoController = new GrafoPlantaController(framePadre);
			grafoPanel = new GrafoPanel(framePadre, grafoController);
			grafoController.setGLC(this);
			grafoController.setGrafoPanel(grafoPanel);
			redPlantasView = new RedPlantasView(framePadre, this);
			redPlantasView.buscarCaminos(firstTimeCaminos);
			firstTimeCaminos = false; 
			break;
			//case AGREGAR_PLANTA_PRODUCCION: grafoPanel.; break;
			//case AGREGAR_RUTA: grafoPanel.; break;
		}
	}


	public List<Insumo> listaInsumos() {
		return ic.listaInsumos();
	}


	public List<Insumo> listaInsumosLiquidos() {
		return ic.listaInsumosLiquidos();
	}


	public void refrescarGrafo() {
		grafoController.desmarcarPlantas();
		grafoController.desmarcarCaminos();
	}


	public boolean buscarFaltante(Insumo insumo) {
		return grafoController.marcarPlantas(insumo);

	}

	public void refrescarListasPlantas() {
		redPlantasView.refrescarListasPlantas();
	}

	public boolean buscarMejorCamino(Insumo seleccionado) {
		List<PlantaProduccion> plantas = pc.buscarPlantasNecesitanInsumo(seleccionado);
		Recorrido recorrido = pc.mejorCaminoEnvio(plantas);
		if(recorrido != null) {
			System.out.println("Mejor Camino para Envío Insumo: "+seleccionado.getDescripcion());
			System.out.println("Distancia Total: "+recorrido.getDistanciaTotal()+"Km");
			System.out.println("Duración Viaje: "+recorrido.getDuracionTotal()+" minutos");
			System.out.println("Recorrido:");
			for(Ruta ruta : recorrido.getRecorrido())
				System.out.print(ruta.getInicio().getValor().getNombre()+"--"+ruta.getFin().getValor().getNombre());
			System.out.println(" ");
			System.out.println(" ");

			grafoController.buscarMejorCamino(recorrido, plantas);
			JOptionPane.showConfirmDialog(framePadre,"Mejor Camino con Distancia: "+recorrido.getDistanciaTotal()+
					"Km y Duración Aprox: "+(Double.valueOf(recorrido.getDuracionTotal()/60)).intValue()+"h "+Math.round(((BigDecimal.valueOf(recorrido.getDuracionTotal()/60)).remainder(BigDecimal.ONE).floatValue()*60))+" min.", "Información",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}


	public List<Planta> listaPlantas() {
		return pc.listaPlantas();
	}


	public Integer buscarCaminos(Planta p1, Planta p2) {
		List<Recorrido> caminos =  pc.buscarCaminosInfo(p1, p2);
		int i = 1;
		for (Recorrido r : caminos) {
			System.out.println(" ");
			System.out.println("Rec n° "+i+" Distancia Total: "+r.getDistanciaTotal()+"Km");
			System.out.println("Peso Máximo: "+r.getPesoMax()+" Toneladas");
			System.out.println("Duración: "+(Double.valueOf(r.getDuracionTotal()/60)).intValue()+"h "
			+Math.round(((BigDecimal.valueOf(r.getDuracionTotal()/60)).remainder(BigDecimal.ONE).floatValue()*60))+" min.");
			System.out.println("Recorrido:");
			for(Ruta ruta : r.getRecorrido())
				System.out.print(ruta.getInicio().getValor().getNombre()+"--"+ruta.getFin().getValor().getNombre());
			System.out.println(" ");
			i++;
		}
		System.out.println("-----------------------------");
		
		grafoController.marcarCaminos(caminos);
		
		return caminos.size();
	}





}
