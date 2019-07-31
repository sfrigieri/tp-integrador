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
import isi.died.tp.model.Insumo;
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
		pc = GestionEntidadesController.plantaController;
		rc = GestionEntidadesController.rutaController;
		this.plantasEnPanel = new ArrayList<VerticeView<Planta>>();
		this.rutasEnPanel = new ArrayList<AristaView<Planta>>();
	}

	public void setGrafoPanel(GrafoPanel gw) {
		grafoView = gw;
	}

	public void setRutasEnPanel(List<AristaView<Planta>> lista) {
		this.rutasEnPanel = lista;
	}

	public void setPlantasEnPanel(List<VerticeView<Planta>> lista) {
		this.plantasEnPanel = lista;
	}

	public void setPlantas() {

		PlantaAcopio origen = pc.buscarAcopioInicial();
		PlantaAcopio fin = pc.buscarAcopioFinal();
		VerticeView<Planta> v;
		grafoView.resetPlantas();
		if(origen != null) {
			v = new VerticeView<Planta>(20,150,Color.BLACK);
			v.setId(origen.getId());
			v.setNombre(origen.getNombre());
			v.setValor(origen);
			grafoView.agregar(v);
			grafoView.repaint();
		}

		if(fin != null) {
			v = new VerticeView<Planta>(700,150,Color.BLACK);
			v.setId(fin.getId());
			v.setNombre(fin.getNombre());
			v.setValor(fin);
			grafoView.agregar(v);
			grafoView.repaint();
		}

		List<PlantaProduccion> lista = pc.listaPlantasProduccion();

		if(!lista.isEmpty()) {
			int y = 20;
			int x = 300;
			for(Planta p : lista){

				if(y>350)
					x= 500;

				if(y > 350)
					y =40;	

				v = new VerticeView<Planta>(x,y,Color.BLUE);
				y+=70;
				v.setId(p.getId());
				v.setNombre(p.getNombre());
				v.setValor(p);
				grafoView.agregar(v);
			
			}
			grafoView.repaint();
		}

		this.plantasEnPanel.addAll(grafoView.plantasEnPanel());
	}

	public void setRutas() {
		List<Ruta> rutas = rc.listaRutas();
		grafoView.resetRutas();
		for(Ruta r : rutas) {
			grafoView.agregar(new RutaView(r.getId(),this.buscarVertice(r.getInicio().getValor()),
					this.buscarVertice(r.getFin().getValor()), r.getValor(),r.getDuracionViajeMin(),r.getPesoMaxTon(), r.getPesoEnCurso()));
		}

		this.rutasEnPanel.addAll(grafoView.rutasEnPanel());
		grafoView.repaint();
	}


	public void crearVertice(Integer coordenadaX, Integer coordenadaY, Color color, Planta p) {
		VerticeView<Planta> v = new VerticeView<Planta>(coordenadaX, coordenadaY, color);
		v.setId(p.getId());
		v.setNombre(p.getNombre());
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


	public boolean marcarPlantas(Insumo ins) {
		grafoView.desmarcarVertices();
		List<PlantaProduccion> lista = pc.buscarPlantasNecesitanInsumo(ins);
		if(!lista.isEmpty()) {
			grafoView.marcarVertices(lista);
			return true;
		}
		return false;
	}

	public void desmarcarPlantas() {

		grafoView.desmarcarVertices();
		
	}

	public void buscarMejorCamino(Recorrido camino, List<PlantaProduccion> plantas) {
		if(camino != null) {
			grafoView.marcarCamino(camino, plantas);
			grafoView.repaint();
		}
	}

	public void marcarFlujoActual() {
		this.setPlantas();
		this.setRutas();
		grafoView.marcarFlujoActual();
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

	public void desmarcarCaminos() {
		grafoView.desmarcarCaminos();
	}

	public List<List<JLabel>> marcarCaminos(List<Recorrido> caminos) {
		List<List<JLabel>> lista = new ArrayList<List<JLabel>>();
		if(!caminos.isEmpty()) {
			long i = 0;
			for(Recorrido camino : caminos) {
				lista.add(grafoView.marcarCamino(camino, i));
				i++;
			}
		}
		grafoView.clearAristas();
		return lista;
	}


}

