package isi.died.tp.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.view.AristaView;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.RutaView;
import isi.died.tp.view.VerticeView;

public class GrafoPlantaController {

	private static GrafoPanel grafoView;
	private static PlantaController pc;
	private static RutaController rc;
	private List<VerticeView<Planta>> plantasEnPanel;
	private List<AristaView<Planta>> rutasEnPanel;

	public GrafoPlantaController(JFrame framePadre) {
		grafoView = GestionLogisticaController.grafoPanel;
		pc = GestionEntidadesController.plantaController;
		rc = GestionEntidadesController.rutaController;
		//GestionLogistica.getPlantasBuscarCaminos(pc.listaPlantas());
		this.plantasEnPanel = new ArrayList<VerticeView<Planta>>();
		this.rutasEnPanel = new ArrayList<AristaView<Planta>>();
	}

	public void setPlantas() {
		
		PlantaAcopio origen = pc.buscarAcopioInicial();
		PlantaAcopio fin = pc.buscarAcopioFinal();
		VerticeView<Planta> v;
		if(origen != null) {
			v = new VerticeView<Planta>(20,250,Color.BLACK);
			v.setId(origen.getId());
			v.setNombre(origen.getNombre());
			v.setValor(origen);
			grafoView.agregar(v);
			grafoView.repaint();
		}
		
		if(fin != null) {
			v = new VerticeView<Planta>(650,250,Color.BLACK);
			v.setId(fin.getId());
			v.setNombre(fin.getNombre());
			v.setValor(fin);
			grafoView.agregar(v);
			grafoView.repaint();
		}
			
		List<PlantaProduccion> lista = pc.listaPlantasProduccion();
		
		if(!lista.isEmpty()) {
		int y = 0;
		int x = 0;
		int i = 1;
		for(Planta p : lista){
			
			if(x > 650)
				x= 0;
			
			x +=70;
			if(i == 1) {
				y =100;	
			}
			if(i == 2) {
				y =150;	
			}
			if(i == 3) {
				y =350;
				i = 1;
			}
			
			i++;
			v = new VerticeView<Planta>(x,y,Color.BLUE);
			v.setId(p.getId());
			v.setNombre(p.getNombre());
			v.setValor(p);
			grafoView.agregar(v);
			grafoView.repaint();
		}
			
		}
		
		this.plantasEnPanel.addAll(grafoView.plantasEnPanel());
	}
	
	public void setRutas() {
		List<Ruta> rutas = rc.listaRutas();
		
		for(Ruta r : rutas) {
			grafoView.agregar(new RutaView(r.getId(),this.buscarVertice(r.getInicio().getValor()),
					this.buscarVertice(r.getFin().getValor()), r.getValor(),r.getDuracionViajeMin(),r.getPesoMaxTon()));
		}
		
		this.rutasEnPanel.addAll(grafoView.rutasEnPanel());
		grafoView.repaint();
	}
	
	
	public void crearVertice(Integer coordenadaX, Integer coordenadaY, Color color, Planta mc) {
		VerticeView<Planta> v = new VerticeView<Planta>(coordenadaX, coordenadaY, color);
		v.setId(mc.getId());
		v.setNombre(mc.getNombre());
		this.plantasEnPanel.add(v);
		grafoView.agregar(v);
		grafoView.repaint();
	}

	public void crearRuta(AristaView<Planta> arista) {

		if(!rc.existeRuta((Planta) arista.getOrigen().getValor(),(Planta) arista.getDestino().getValor())) {
			if(arista.getOrigen().equals(arista.getDestino()))
				return;
			else {
				Integer duracionViajeMin = null, distanciaKm = null, pesoMax = null; 
				while(distanciaKm == null)
					distanciaKm = getValor("Distancia en Km");
				while(pesoMax == null)
					distanciaKm = getValor("Peso Máximo en Toneladas");
				while(duracionViajeMin == null)
					duracionViajeMin = getValor("Distancia en Km");

				if(distanciaKm != null && pesoMax != null && duracionViajeMin != null)
					rc.agregarRuta((Planta) arista.getOrigen().getValor(), (Planta) arista.getDestino().getValor(),
							distanciaKm, Double.valueOf(duracionViajeMin), pesoMax);
			}
		}
		grafoView.agregar(arista);
		grafoView.repaint();
	}

	private Integer getValor(String string) {
		JTextField field = new JTextField(5);
		JPanel panel = new JPanel();
		panel.add(new JLabel(string));
		panel.add(field);
		int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese",
				JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				Integer.parseInt(field.getText());
				if (Integer.valueOf(field.getText()) > 0) {
					return Integer.valueOf(field.getText());
				}else {
					JOptionPane.showConfirmDialog(grafoView.getParent(), "El valor debe ser positivo.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				} }
			catch(NumberFormatException nfex) {
				JOptionPane.showConfirmDialog(grafoView.getParent(), "El valor debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}


		}
		return null;
	}



	public void buscarMejorCamino(List<PlantaProduccion> plantas) {
		Recorrido camino = pc.mejorCaminoEnvio(plantas);
		if(camino != null) {
			grafoView.caminoPintar(camino);
			grafoView.repaint();
		}
		else
			JOptionPane.showConfirmDialog(grafoView.getParent(), "No existe un Camino que incluya a todas las Plantas.", "Información", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	public void buscarCaminos(Planta p1, Planta p2) {
		List<Recorrido> caminos = pc.buscarCaminosInfo(p1, p2);
		if(caminos != null) {
			for(Recorrido camino : caminos)
				grafoView.caminoPintar(camino);
			grafoView.repaint();
		}
		else
			JOptionPane.showConfirmDialog(grafoView.getParent(), "No existen Caminos que unan ambas Plantas.", "Información", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	public List<Planta> listaVertices() {
		return pc.listaPlantas();
	}


	public boolean existeVertice(Planta p) {
		if(!this.plantasEnPanel.isEmpty()) {
			for(VerticeView<Planta> v : this.plantasEnPanel) {
				if(v.getValor().equals(p)) return true;
			}
		}
		return false;
	}

	public VerticeView<Planta> buscarVertice(Planta p) {
		if(!this.plantasEnPanel.isEmpty()) {
			for(VerticeView<Planta> v : this.plantasEnPanel) {
				if(v.getValor().equals(p)) return v;
			}
		}
		return null;
	}

}

