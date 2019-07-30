package isi.died.tp.view;

import java.awt.BasicStroke;
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
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

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
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.app.Main;
import isi.died.tp.view.AristaView;
import isi.died.tp.view.VerticeView;



public class GrafoPanel extends JPanel {

	public static JFrame framePadre;
	private Queue<Color> colaColores;
	private static GrafoPlantaController controller;

	private List<VerticeView<Planta>> vertices;
	private List<AristaView<Planta>> aristas;
	private List<AristaView<Planta>> aristasPintadas;
	private AristaView<Planta> rutaAux;
	private VerticeView<Planta> plantaAux;
	private static GestionLogisticaController glc;
	private Boolean agregarRuta = false;
	private Boolean agregarPlanta = false;
	private Boolean eliminarPlanta = false;
	private Boolean buscarCaminos = false;
	private Boolean buscarMejorCamino = false;
	private Boolean moverPlanta = true;
	private Boolean repaint = false;

	public GrafoPanel(JFrame fp) {

		framePadre = fp;
		this.vertices = new ArrayList<VerticeView<Planta>>();
		this.aristas = new ArrayList<AristaView<Planta>>();
		this.aristasPintadas = new ArrayList<AristaView<Planta>>();
		glc = GestionLogistica.controller;
		controller = GestionLogisticaController.grafoController;
		this.colaColores = new LinkedList<Color>();
		this.colaColores.add(Color.RED);
		this.colaColores.add(Color.BLUE);
		this.colaColores.add(Color.ORANGE);
		this.colaColores.add(Color.CYAN);
		rutaAux = null;
		plantaAux = null;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				/*if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 1 && !event.isConsumed()) {
					event.consume();
					if(moverPlanta) {
						VerticeView<Planta> vPlanta = clicEnUnNodo(event.getPoint());
						if(vPlanta != null)
					}
				}
				if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2 && !event.isConsumed()) {
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
				if(moverPlanta) {
					Point point = event.getPoint();
					VerticeView<Planta> vPlanta = clicEnUnNodo(point);
					if(plantaAux != null && vPlanta == null) {
						plantaAux.setCoordenadaX(point.x);
						plantaAux.setCoordenadaY(point.y);
						controller.setPlantasEnPanel(vertices);
						//controller.setPlantasEnPanel(vertices.stream().collect(Collectors.toList()));
						//controller.setRutasEnPanel(aristas.stream().collect(Collectors.toList()));
						//vertices.removeAll(vertices);
						//aristas.removeAll(aristas);
						//paintComponent(plantaAux);
						revalidate();
						repaint = true;
						repaint();
						plantaAux = null;
						//controller.repaint();
						//revalidate();
					}

				}
				/*VerticeView<Planta> vDestino = clicEnUnNodo(event.getPoint());
				if (rutaAux!=null && vDestino != null) {
					//                	if (!controller.existeArista(rutaAux.getOrigen().getId(),vDestino.getId())) {
					rutaAux.setDestino(vDestino);
					controller.crearRuta(rutaAux);
					rutaAux = null;
					//                	}else {
					//                		controller.dibujarAristaExistente(rutaAux);
					//                	}
				}*/
			}

		} );

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				if(moverPlanta) {
					VerticeView<Planta> vPlanta = clicEnUnNodo(event.getPoint());
					if(vPlanta != null) {
						plantaAux = vPlanta;
					}
				}
				/*VerticeView<Planta> vOrigen = clicEnUnNodo(event.getPoint());
				if (rutaAux==null && vOrigen != null) {
					rutaAux = new AristaView<Planta>();                    
					rutaAux.setOrigen(vOrigen);
				}*/
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
			for(AristaView<Planta> a : this.aristas) {
				if(a.getOrigen().getValor().equals(r.getInicio().getValor()) && a.getDestino().getValor().equals(r.getFin().getValor())) {
					a.getOrigen().setColor(Color.GREEN);
					a.getDestino().setColor(Color.GREEN);
					a.setColor(Color.GREEN);
					a.setFormatoLinea(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				}
			}
		}
	}

	public void desmarcarCaminos(){

			for(AristaView<Planta> a : this.aristas) {
					a.getOrigen().setColor(Color.BLUE);
					a.getDestino().setColor(Color.BLUE);
					a.setColor(Color.DARK_GRAY);
					a.setFormatoLinea(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
			}
	}
	
	public void marcarVertices(List<PlantaProduccion> lista){

		for(Planta p : lista) {
			for(VerticeView<Planta> v : this.vertices) {
				if(v.getValor().equals(p)) {
					v.setColor(Color.RED);
				}
			}
		}
		repaint = true;
		repaint();
	}

	public void desmarcarVertices(){

		for(VerticeView<Planta> v : this.vertices) {
			if(v.getValor() instanceof PlantaProduccion && v.getColor().equals(Color.RED) ) {
				v.setColor(Color.BLUE);
			}
			repaint = true;		
			repaint();
		}

	}

	private void dibujarVertices(Graphics2D g2d) {
		for (VerticeView<Planta> v : this.vertices) {
			g2d.setPaint(Color.RED);
			g2d.drawString(v.etiqueta(),v.getCoordenadaX()-5,v.getCoordenadaY()-5);
			g2d.setPaint(v.getColor());
			g2d.fill(v.getNodo());
		}
	}

	private void dibujarRutas(Graphics2D g2d) {

		AffineTransform tx = new AffineTransform();
		List<Planta> apuntadas = new ArrayList<Planta>();

		for (AristaView<Planta> a : this.aristas) {
			g2d.setPaint(Color.DARK_GRAY);
			if(this.getReversed(a) == null || (this.getReversed(a) != null  && !this.aristasPintadas.contains(this.getReversed(a)))) {
				this.aristasPintadas.add(a);
				g2d.drawString(a.etiqueta(),((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-30,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2));
			}
			else
				g2d.drawString(a.etiqueta(),((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-30,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2)+13);
			g2d.setPaint(a.getColor());
			g2d.setStroke ( a.getFormatoLinea());
			g2d.draw(a.getLinea());

			Line2D.Double line = new Line2D.Double(a.getOrigen().getCoordenadaX(), a.getOrigen().getCoordenadaY(), a.getDestino().getCoordenadaX(), a.getDestino().getCoordenadaY());

			Polygon arrowHead = new Polygon();  
			arrowHead.addPoint( 0,5);
			arrowHead.addPoint( -5, -5);
			arrowHead.addPoint( 5,-5);
			tx.setToIdentity();

			double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
			int y = getCordY(angle);
			int x = getCordX(angle);
			if(!repaint)
				tx.translate(line.x2+x, line.y2+y+20);
			else
				tx.translate(line.x2+x, line.y2+10);
			tx.rotate((angle-Math.PI/2d)); 
			//System.out.println(angle);
			Color c = getArrowHeadColor(apuntadas.stream().filter(p -> p.equals(a.getDestino().getValor())).count());
			g2d.setPaint(c);

			Graphics2D g = (Graphics2D) g2d.create();
			g.setTransform(tx);   
			g.fill(arrowHead);
			g.dispose();

			apuntadas.add(a.getDestino().getValor());

		}
		
		this.aristasPintadas.clear();
	}

	private AristaView<Planta> getReversed(AristaView<Planta> a) {
		for (AristaView<Planta> b : this.aristas) {
			if(b.getOrigen().getValor().equals(a.getDestino().getValor()) && b.getDestino().getValor().equals(a.getOrigen().getValor()))
				return b;
		}
		return null;
	}

	private int getCordX(double angle) {
		if(angle < -1 && angle > -1.5)
			return 8;
		if(angle <= -1.5 && angle > -2.2)
			return 11;
		if(angle > 0.5 && angle < 1)
			return 6;
		if(angle < -0.5 && angle >= -1)
			return 5;
		if(angle <= -2 && angle > -3)
			return 11;
		if(angle <= -3 && angle > -4)
			return 11;
		if(angle >= 1 && angle < 2)
			return 10;
		if(angle >= 2 && angle < 3)
			return 13;
		return 9;
	}

	private int getCordY(double angle) {
		if(angle < -1 && angle > -1.5)
			return 39;
		if(angle <= -1.5 && angle > -2.2)
			return 37;
		if(angle > 0.5 && angle < 1)
			return 27;
		if(angle < -0.5 && angle >= -1)
			return 38;
		if(angle <= -2 && angle > -3)
			return 38;
		if(angle <= -3 && angle > -4)
			return 31;
		if(angle >= 1 && angle < 2)
			return 27;
		if(angle >= 2 && angle < 3)
			return 36;
		return 30;
	}

	private Color getArrowHeadColor(long count) {
		int i = (int) count;
		if(i == 0)
			return Color.magenta;
		if(i == 1)
			return Color.cyan;
		if(i == 2)
			return Color.GRAY;
		if(i == 3)
			return Color.YELLOW;
		if(i == 4)
			return Color.GREEN;

		return Color.red;
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

	public List<AristaView<Planta>> rutasEnPanel() {
		return this.aristas;
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
