package isi.died.tp.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

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

	private static JFrame framePadre;
	private static GrafoPanel grafoView;
	private static PlantaController pc;
	private static RutaController rc;
	private List<VerticeView<Planta>> plantasEnPanel;
	private List<AristaView<Planta>> rutasEnPanel;
	private static boolean proximoCamino;
	
	public GrafoPlantaController(JFrame v) {
		pc = GestionEntidadesController.plantaController;
		rc = GestionEntidadesController.rutaController;
		this.plantasEnPanel = new ArrayList<VerticeView<Planta>>();
		this.rutasEnPanel = new ArrayList<AristaView<Planta>>();
		framePadre = v;
		proximoCamino = true;
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


	public void crearRuta(AristaView<Planta> arista) {

		if(!rc.existeRuta((Planta) arista.getOrigen().getValor(),(Planta) arista.getDestino().getValor())) {
			if(arista.getOrigen().equals(arista.getDestino()))
				JOptionPane.showConfirmDialog(framePadre, "Planta Origen y Destino deben ser diferentes.", "Creación Ruta",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			else {
				Integer duracionViajeMin = null, distanciaKm = null, pesoMax = null; 
				while(distanciaKm == null) 
					distanciaKm = getValorNumerico("Distancia en Km");

				if(distanciaKm != -1) {
					while(pesoMax == null) 
						pesoMax = getValorNumerico("Peso Máximo en Toneladas");

					if(pesoMax != -1) {	
						while(duracionViajeMin == null) 
							duracionViajeMin = getValorNumerico("Duración Viaje en Minutos");

						if(duracionViajeMin != -1) {
							rc.agregarRuta((Planta) arista.getOrigen().getValor(), (Planta) arista.getDestino().getValor(),
									distanciaKm, Double.valueOf(duracionViajeMin), pesoMax);

							arista.setValor(distanciaKm);
							arista.setDuracionViajeMin(duracionViajeMin);
							arista.setPesoMax(pesoMax);

							grafoView.agregar(arista);
							grafoView.isRepaint();
							grafoView.repaint();

						}

					}

				}
			}


		}
		else
			JOptionPane.showConfirmDialog(framePadre, "La Ruta establecida ya existe en el Sistema.", "Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

	}
	
	public void eliminarRuta(AristaView<Planta> vRuta) {
		rc.eliminarRuta(rc.buscarRuta(vRuta.getId()));
		
	}

	private Integer getValorNumerico(String string) {
		JTextField field = new JTextField(5);
		JPanel panel = new JPanel();
		panel.add(new JLabel(string));
		panel.add(field);
		int result = JOptionPane.showConfirmDialog(null, panel, "Ingrese",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

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

			return null;

		}

		return -1;

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

	public void marcarCaminos(List<Recorrido> caminos) {
		List<List<JLabel>> lista = new ArrayList<List<JLabel>>();
		if(!caminos.isEmpty()) {
			long i = 0;


			for(Recorrido camino : caminos) {
				lista.add(labelsCamino(camino, i));
				i++;
			}

			i = 0;

			JPanel panel;
			GridBagConstraints constraints;

			if(caminos.size() > 1) {
				for(Recorrido camino : caminos) {
					if(proximoCamino) {
						grafoView.desmarcarCaminos();
						grafoView.marcarCamino(camino, i);
						JPanel panelInterior = new JPanel(new GridBagLayout());
						constraints = new GridBagConstraints();

						constraints.gridx=2;
						constraints.gridy=1;
						panelInterior.add(new JLabel("    Distancia       Duración      Peso Máximo"), constraints);
						List<JLabel> labels = lista.get((int) i);
						constraints.gridx=1;
						constraints.gridy=2;
						constraints.insets.set(0,0,25,0);
						panelInterior.add(labels.get(0), constraints);
						constraints.gridx=2;

						panelInterior.add(labels.get(1), constraints);
						UIManager.put("OptionPane.cancelButtonText", "Ver Todos");
						UIManager.put("OptionPane.okButtonText", "Ver Próximo");
						int result = JOptionPane.showConfirmDialog(null,panelInterior,"Información",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);


						if (result == JOptionPane.OK_OPTION)
							proximoCamino = true;
						else
							proximoCamino = false;
					}
					i++;
				}

				proximoCamino =true;
				UIManager.put("OptionPane.cancelButtonText", "Cancelar");
				UIManager.put("OptionPane.okButtonText", "Aceptar");
			}


			JFrame popup = new JFrame("Información de Caminos Disponibles");
			popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			int ycord = 2;
			constraints = new GridBagConstraints();
			panel = new JPanel(new GridBagLayout());
			i = 0;
			grafoView.desmarcarCaminos();
			panel = new JPanel(new GridBagLayout());
			constraints = new GridBagConstraints();
			panel.setPreferredSize( new Dimension(400,70+caminos.size()*20));

			for(Recorrido camino : caminos) {

				grafoView.marcarCamino(camino, i);
				i++;
			}

			ycord = 2;
			constraints.gridx=2;
			constraints.gridy=1;
			panel.add(new JLabel("    Distancia       Duración      Peso Máximo"), constraints);
			for(List<JLabel> labels : lista) {
				constraints.gridx=1;
				constraints.gridy=ycord;
				panel.add(labels.get(0), constraints);
				constraints.gridx=2;
				panel.add(labels.get(1), constraints);
				ycord++;
			}
			popup.setContentPane(panel);
			popup.pack();
			popup.setLocationRelativeTo(framePadre);
			popup.setVisible(true);
		}

	}


	private List<JLabel> labelsCamino(Recorrido re, long numColor){


		List<JLabel> labels = new ArrayList<JLabel>();

		JLabel infoCamino1 = new JLabel(numColor+1+"° Camino");
		infoCamino1.setForeground(grafoView.getCaminoColor(numColor));
		String info1, info2;

		if(re.getDistanciaTotal() < 100)
			info1 = "  "+re.getDistanciaTotal();
		else
			info1 = ""+re.getDistanciaTotal();

		if(Math.round(((BigDecimal.valueOf(re.getDuracionTotal()/60)).remainder(BigDecimal.ONE).floatValue()*60)) < 10)
			info2 = (Double.valueOf(re.getDuracionTotal()/60)).intValue()+"h "
					+Math.round(((BigDecimal.valueOf(re.getDuracionTotal()/60)).remainder(BigDecimal.ONE).floatValue()*60))+" ";
		else
			info2 = (Double.valueOf(re.getDuracionTotal()/60)).intValue()+"h "
					+Math.round(((BigDecimal.valueOf(re.getDuracionTotal()/60)).remainder(BigDecimal.ONE).floatValue()*60))+"";

		JLabel infoCamino2 = new JLabel(info1+"Km          "+info2+" min.          "+
				re.getPesoMax()+" Ton.");
		labels.add(infoCamino1);
		labels.add(infoCamino2);


		return labels;
	}





}

