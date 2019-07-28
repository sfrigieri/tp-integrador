package isi.died.tp.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.view.AristaView;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.VerticeView;

public class GrafoPlantaController {

	private static GrafoPanel grafoView;
	private static PlantaController pc;
	private static RutaController rc;
	private List<Planta> plantas;
	private List<VerticeView<Planta>> verticesDibujados;


	public GrafoPlantaController(JFrame framePadre) {
		grafoView = GestionLogisticaController.grafoPanel;
		pc = GestionEntidadesController.plantaController;
		rc = GestionEntidadesController.rutaController;
		//GestionLogistica.getPlantasBuscarCaminos(pc.listaPlantas());
		this.verticesDibujados = new ArrayList<VerticeView<Planta>>();
	}


	public void crearVertice(Integer coordenadaX, Integer coordenadaY, Color color, Planta mc) {
		VerticeView<Planta> v = new VerticeView<Planta>(coordenadaX, coordenadaY, color);
		v.setId(mc.getId());
		v.setNombre(mc.getNombre());
		this.verticesDibujados.add(v);
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
		if(!this.verticesDibujados.isEmpty()) {
			for(VerticeView<Planta> v : this.verticesDibujados) {
				if(v.getValor().equals(p)) return true;
			}
		}
		return false;
	}

	public VerticeView<Planta> buscarVertice(Planta p) {
		if(!this.verticesDibujados.isEmpty()) {
			for(VerticeView<Planta> v : this.verticesDibujados) {
				if(v.getValor().equals(p)) return v;
			}
		}
		return null;
	}

}

