package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.controller.OpcionesMenuLogistica;
import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;
import isi.died.tp.app.Main;
import isi.died.tp.view.AristaView;
import isi.died.tp.view.VerticeView;



public class GrafoPanel extends JPanel {

	public static JFrame framePadre;
	private Queue<Color> colaColores;
	private static GrafoPlantaController controller;

	private List<VerticeView<Planta>> vertices;
	private List<AristaView<Planta>> aristas;

	private AristaView<Planta> rutaAux;
	private static GestionLogisticaController glc;
	private Boolean agregarRuta;
	private Boolean agregarPlanta;
	private Boolean eliminarPlanta;
	private Boolean insumoSeleccionado;
	private Boolean buscarCaminos;
	private Boolean buscarMejorCamino;

	public GrafoPanel(JFrame fp) {

		framePadre = fp;
		this.vertices = new ArrayList<VerticeView<Planta>>();
		this.aristas = new ArrayList<AristaView<Planta>>();
		glc = GestionLogistica.controller;
		controller = GestionLogisticaController.grafoController;
		this.colaColores = new LinkedList<Color>();
		this.colaColores.add(Color.RED);
		this.colaColores.add(Color.BLUE);
		this.colaColores.add(Color.ORANGE);
		this.colaColores.add(Color.CYAN);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				/*if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2 && !event.isConsumed()) {
					event.consume();
					Object[] plantas = controller.listaVertices().toArray();
					Object verticeMatSeleccionado;
					try {
						verticeMatSeleccionado = JOptionPane.showInputDialog(framePadre, 
								"¿Qué material corresponde con el vertice?",
								"Agregar Vertice",
								JOptionPane.QUESTION_MESSAGE, 
								null, 
								plantas, 
								plantas[0]);
						if (verticeMatSeleccionado != null & !controller.existeVertice((Planta)verticeMatSeleccionado)) {
							Color aux = ((Planta)verticeMatSeleccionado).esLibro()?Color.RED:Color.BLUE;
							controller.crearVertice(event.getX(), event.getY(), aux,(Planta) verticeMatSeleccionado);
							//Confirmo que no haya una arista existente
							//	                        if (rutaAux!=null) {
							//	                        	if (controller.existeArista(rutaAux.getOrigen().getId(),((Planta)verticeMatSeleccionado).getId())) {
							//		                        	AristaView<Planta> existente = new AristaView<Planta>();
							//		                        	existente.setOrigen(rutaAux.getOrigen());
							//		                        	existente.setDestino(controller.buscarVertice((Planta)verticeMatSeleccionado));
							//		                        	controller.dibujarAristaExistente(existente);
							//		                        	if(controller.existeArista(existente.getDestino().getId(), existente.getOrigen().getId())) {
							//		                        		VerticeView<Planta> auxV = existente.getDestino();
							//		                        		existente.setDestino(existente.getOrigen());
							//		                        		existente.setOrigen(auxV);
							//		                        		controller.dibujarAristaExistente(existente);
							//		                        	}
							//		                        }
							//		                    }
						}else {
							JOptionPane.showConfirmDialog(framePadre, "Ese material ya fue añadido", "Material existente", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						JOptionPane.showConfirmDialog(framePadre, "No quedan más plantas para agregar", "Sin plantas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}  
					//					System.out.println("vertices: "+vertices);
				}else {
					if(event.getButton() == MouseEvent.BUTTON3 && event.getClickCount() == 1 && !event.isConsumed()) {
						event.consume();
						//                		System.out.println("Mostrar Menu");
						JPopupMenu menu = new JPopupMenu();
						JMenuItem menuItem;
						menuItem = new JMenuItem("Agregar Planta Producción");
						menuItem.addActionListener(a -> glc.opcion(OpcionesMenuLogistica.AGREGAR_PLANTA_PRODUCCION));
						menu.add(menuItem);
						menuItem = new JMenuItem("Agregar Ruta");
						menuItem.addActionListener(a -> glc.opcion(OpcionesMenuLogistica.AGREGAR_RUTA));
						menu.add(menuItem);
						menu.show(event.getComponent(), event.getX(), event.getY());
					}                	
				}
*/
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				VerticeView<Planta> vDestino = clicEnUnNodo(event.getPoint());
				if (rutaAux!=null && vDestino != null) {
					//                	if (!controller.existeArista(rutaAux.getOrigen().getId(),vDestino.getId())) {
					rutaAux.setDestino(vDestino);
					controller.crearRuta(rutaAux);
					rutaAux = null;
					//                	}else {
					//                		controller.dibujarAristaExistente(rutaAux);
					//                	}
				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				VerticeView<Planta> vOrigen = clicEnUnNodo(event.getPoint());
				if (rutaAux==null && vOrigen != null) {
					rutaAux = new AristaView<Planta>();                    
					rutaAux.setOrigen(vOrigen);
				}
			}
		});
	}

	public void agregar(AristaView<Planta> arista){
		this.aristas.add(arista);
	}    

	public void agregar(VerticeView<Planta> vert){
		this.vertices.add(vert);
	}

	public void caminoPintar(Recorrido camino){

		for(Ruta r : camino.getRecorrido()) {
			for(AristaView<Planta> av : this.aristas) {
				if(av.getOrigen().equals(r.getInicio().getValor()) && av.getDestino().equals(r.getFin().getValor())) {
					av.setColor(Color.RED);
					av.getOrigen().setColor(Color.GREEN);
					av.getDestino().setColor(Color.GREEN);
				}
			}
		}
	}


	private void dibujarVertices(Graphics2D g2d) {
		for (VerticeView<Planta> v : this.vertices) {
			//            g2d.setPaint(Color.BLUE);
			g2d.drawString(v.etiqueta(),v.getCoordenadaX()-5,v.getCoordenadaY()-5);
			g2d.setPaint(v.getColor());
			g2d.fill(v.getNodo());
		}
	}

	private void dibujarRutas(Graphics2D g2d) {
		//        System.out.println(this.aristas);
		for (AristaView<Planta> a : this.aristas) {
			g2d.setPaint(a.getColor());
			g2d.setStroke ( a.getFormatoLinea());
			g2d.draw(a.getLinea());
			//dibujo una flecha al final
			// con el color del origen para que se note
			g2d.setPaint(Color.BLACK);
			Polygon flecha = new Polygon();  
			flecha.addPoint(a.getDestino().getCoordenadaX(), a.getDestino().getCoordenadaY()+7);
			flecha.addPoint(a.getDestino().getCoordenadaX()+20, a.getDestino().getCoordenadaY()+10);
			flecha.addPoint(a.getDestino().getCoordenadaX(), a.getDestino().getCoordenadaY()+18);
			g2d.fillPolygon(flecha);
		}
	}

	private VerticeView<Planta> clicEnUnNodo(Point p) {
		for (VerticeView<Planta> v : this.vertices) {
			if (v.getNodo().contains(p)) {
				return v;
			}
		}
		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		dibujarVertices(g2d);
		dibujarRutas(g2d);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 400);
	}


	public void setAuxiliarOrigen(VerticeView<Planta> v) {
		this.rutaAux = new AristaView<Planta>();
		this.rutaAux.setOrigen(v);
	}
	
	public List<VerticeView<Planta>> plantasEnPanel() {
		return this.vertices;
	}


	public void buscarCaminos() {
		JFrame popup = new JFrame("Buscar Caminos");
		JPanel panel = new JPanel(new GridBagLayout());
		List<Planta> plantas = new ArrayList<Planta>();

		for(VerticeView<Planta> v: vertices) {
			plantas.add(v.getValor());
		}

		JComboBox<Planta> listaInicio = new JComboBox<Planta>(),
				listaFin = new JComboBox<Planta>();

		for(Planta p: plantas) {
			listaInicio.addItem(p);
			listaFin.addItem(p);
		}
		JLabel label = new JLabel();
		GridBagConstraints cons = new GridBagConstraints();
		JButton aceptar = new JButton("Aceptar"), cancelar = new JButton("Cancelar");

		cons.insets = new Insets(5, 5, 5, 5);
		cons.gridx = 0;
		cons.gridx = 0;
		label.setText("Seleccione Planta Inicial: ");
		panel.add(label, cons);

		cons.gridx = 1;
		panel.add(listaInicio, cons);

		cons.gridx=0;
		cons.gridy=1;
		label = new JLabel("Seleccione Planta Final: ");
		panel.add(label, cons);

		cons.gridx=1;
		panel.add(listaFin, cons);


		cons.gridx=0;
		cons.gridy=3;
		cancelar.addActionListener(a -> popup.dispose());
		panel.add(cancelar, cons);

		cons.gridx=1;
		aceptar.addActionListener(a -> {
			Planta inicio, fin;

			try {
				inicio = (Planta) listaInicio.getSelectedItem();
				fin = (Planta) listaFin.getSelectedItem();
				controller.buscarCaminos(inicio, fin);
				popup.dispose();
				//else JOptionPane.showConfirmDialog(popup, "Ingrese", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				JOptionPane.showConfirmDialog(popup, "El campo Número de saltos debe ser un número entero.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.add(aceptar, cons);


		popup.setContentPane(panel);
		popup.pack();
		popup.setLocationRelativeTo(framePadre);
		popup.setVisible(true);
	}



}
