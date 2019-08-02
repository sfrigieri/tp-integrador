package isi.died.tp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.view.AristaView;
import isi.died.tp.view.VerticeView;



public class GrafoPanel extends JPanel {

	public static JFrame framePadre;
	private static GrafoPlantaController controller;

	private List<VerticeView<Planta>> vertices;
	private List<AristaView<Planta>> aristas;
	private List<AristaView<Planta>> aristasPintadas;
	private VerticeView<Planta> plantaAux;
	private RutaView rutaAux;
	private Boolean mostrarFlujo = false;
	private Boolean repaint = false;

	public GrafoPanel(JFrame fp, GrafoPlantaController grafoController) {

		framePadre = fp;
		this.vertices = new ArrayList<VerticeView<Planta>>();
		this.aristas = new ArrayList<AristaView<Planta>>();
		this.aristasPintadas = new ArrayList<AristaView<Planta>>();
		controller = grafoController;

		plantaAux = null;
		rutaAux = null;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {

				if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2 && !event.isConsumed()) {
					event.consume();
					AristaView<Planta> vRuta = clicEnRuta(event.getPoint());

						if(vRuta != null) {
							int result = JOptionPane.showConfirmDialog(getParent(),"Desea Eliminar la Ruta seleccionada: "+
									vRuta.getOrigen().getNombre()+" a "+vRuta.getDestino().getNombre(),"Acción a Realizar",
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	
							if (result == JOptionPane.OK_OPTION) {
								grafoController.eliminarRuta(vRuta);
								aristas.remove(vRuta);
								grafoController.setRutasEnPanel(aristas);
								repaint = true;
								repaint();
							}


						}

					rutaAux = null;

				}else 
					if (event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 1 && !event.isConsumed()) {
						event.consume();
						VerticeView<Planta> vPlanta = clicEnUnNodo(event.getPoint());

						if(vPlanta != null) {
							if(rutaAux == null) {
								rutaAux = new RutaView();
								rutaAux.setOrigen(vPlanta);
							}
							else {
								rutaAux.setDestino(vPlanta);
								grafoController.crearRuta(rutaAux);
								rutaAux = null;
								grafoController.setRutasEnPanel(aristas);
							}	
						}


					}

			}

			@Override
			public void mouseReleased(MouseEvent event) {
				Point point = event.getPoint();
				VerticeView<Planta> vPlanta = clicEnUnNodo(point);
				if(plantaAux != null && vPlanta == null) {
					plantaAux.setCoordenadaX(point.x);
					plantaAux.setCoordenadaY(point.y);
					controller.setPlantasEnPanel(vertices);
					revalidate();
					repaint = true;
					repaint();
					plantaAux = null;


				}
			}

		} );

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				VerticeView<Planta> vPlanta = clicEnUnNodo(event.getPoint());
				if(vPlanta != null) {
					plantaAux = vPlanta;
				}
			}
		});
	}

	public void resetPlantas() {
		this.vertices = new ArrayList<VerticeView<Planta>>();
	}

	public void resetRutas() {
		this.aristas = new ArrayList<AristaView<Planta>>();
		this.aristasPintadas = new ArrayList<AristaView<Planta>>();
	}


	public void agregar(AristaView<Planta> arista){
		this.aristas.add(arista);
	}    

	public void agregar(VerticeView<Planta> vert){
		this.vertices.add(vert);
	}

	public void marcarCamino(Recorrido camino, List<PlantaProduccion> plantas){

		for(Ruta r : camino.getRecorrido()) {
			for(AristaView<Planta> a : this.aristas) {
				if(a.getOrigen().getValor().equals(r.getInicio().getValor()) && a.getDestino().getValor().equals(r.getFin().getValor())) {
					if(plantas.contains(a.getOrigen().getValor()))
						a.getOrigen().setColor(Color.GREEN);
					if(plantas.contains(a.getDestino().getValor()))
						a.getDestino().setColor(Color.GREEN);
					a.setColor(Color.GREEN);
					a.setFormatoLinea(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				}
			}
		}
	}

	public void marcarCamino(Recorrido re, long numColor){

		for(Ruta r : re.getRecorrido()) {
			for(AristaView<Planta> a : this.aristas) {
				if(a.getOrigen().getValor().equals(r.getInicio().getValor()) && a.getDestino().getValor().equals(r.getFin().getValor())) {
					if(this.aristasPintadas.contains(a) || (this.getReversed(a) != null && this.aristasPintadas.contains(this.getReversed(a))))
						a.setColor(Color.WHITE);

					else
						a.setColor(this.getCaminoColor(numColor));
					a.setFormatoLinea(new BasicStroke(3.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
					this.aristasPintadas.add(a);
				}
				else 
					if(a.getDestino().getValor().equals(r.getInicio().getValor()) && a.getOrigen().getValor().equals(r.getFin().getValor())) {
						a.setColor(Color.gray.brighter());
						a.setFormatoLinea(new BasicStroke(0.0001f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
					}
			}
		}
		repaint = true;
		repaint();

	}




	public void desmarcarCaminos(){

		for(AristaView<Planta> a : this.aristas) {
			if(a.getOrigen().getValor() instanceof PlantaProduccion)
				a.getOrigen().setColor(Color.BLUE);
			else
				a.getOrigen().setColor(Color.BLACK);

			if(a.getDestino().getValor() instanceof PlantaProduccion)
				a.getDestino().setColor(Color.BLUE);
			else
				a.getDestino().setColor(Color.BLACK);

			a.setColor(Color.DARK_GRAY);
			a.setFormatoLinea(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		}
		
		this.aristasPintadas.clear();
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
			if(this.getReversed(a) == null)
				g2d.drawString(a.etiqueta(),((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-30,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2));
			else if(!this.aristasPintadas.contains(this.getReversed(a))) {
				this.aristasPintadas.add(a);
				g2d.drawString(a.etiqueta()+", "+a.getOrigen().getValor().getId()+" a "+a.getDestino().getValor().getId(),
						((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-30,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2));
			}
			else
				g2d.drawString(a.etiqueta()+", "+a.getOrigen().getValor().getId()+" a "+a.getDestino().getValor().getId()
						,((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-30,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2)+13);
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
				tx.translate(line.x2+x, line.y2+y+35);
			else
				tx.translate(line.x2+x, line.y2+y-20);
			tx.rotate((angle-Math.PI/2d)); 
			//System.out.println(angle);
			Color c = getFlechaColor(apuntadas.stream().filter(p -> p.equals(a.getDestino().getValor())).count());
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
		if(angle < -1 && angle > -1.53)
			return 9;
		if(angle <= -1.53 && angle > -2.2)
			return 11;
		if(angle > 0.5 && angle < 1)
			return 7;
		if(angle  >=0 && angle <= 0.5)
			return 5;
		if(angle < -0.5 && angle >= -1)
			return 5;
		if(angle < 0 && angle >= -0.5)
			return 5;
		if(angle <= -2 && angle > -2.4)
			return 15;
		if(angle <= -2.4 && angle > -3)
			return 17;
		if(angle <= -3 && angle > -4)
			return 15;
		if(angle >= 1 && angle < 1.6)
			return 9;
		if(angle >= 1.6 && angle  <2.0)
			return 13;
		if(angle >= 2 && angle  <2.2)
			return 13;
		if(angle >= 2.2 && angle < 3)
			return 15;
		if(angle >= 3 && angle < 4)
			return 15;
		return 9;
	}

	private int getCordY(double angle) {
		if(angle < -1 && angle > -1.5)
			return 39;
		if(angle <= -1.5 && angle > -2.2)
			return 37;
		if(angle > 0.5 && angle < 1)
			return 26;
		if(angle < -0.5 && angle >= -1)
			return 36;
		if(angle < -0.35 && angle >= -0.5)
			return 34;
		if(angle <= -2 && angle > -2.4)
			return 36;
		if(angle <= -2.4 && angle > -3)
			return 33;
		if(angle <= -3 && angle > -4)
			return 31;
		if(angle >= 1 && angle < 2)
			return 22;
		if(angle >= 2 && angle < 2.2)
			return 24;
		if(angle >= 2.2 && angle < 3)
			return 27;
		return 30;
	}

	private Color getFlechaColor(long count) {
		int i = (int) count;
		if(i == 0)
			return Color.magenta.darker();
		if(i == 1)
			return Color.cyan;
		if(i == 2)
			return Color.ORANGE;
		if(i == 3)
			return Color.YELLOW.darker();
		if(i == 4)
			return Color.red;
		if(i == 5)
			return Color.BLUE;
		if(i == 6)
			return Color.green.darker();
		if(i == 7)
			return Color.pink;
		if(i == 8)
			return Color.gray.darker();
		if(i == 9)
			return Color.GREEN;
		if(i == 10)
			return Color.magenta;
		if(i == 11)
			return Color.cyan.darker();
		if(i == 12)
			return Color.orange.darker();
		if(i == 13)
			return Color.YELLOW;
		if(i == 14)
			return Color.red.brighter();
		if(i == 15)
			return Color.pink.darker();

		return Color.black;
	}

	public Color getCaminoColor(long count) {
		int i = (int) count;
		if(i == 0)
			return Color.green.darker();
		if(i == 1)
			return Color.red.brighter();
		if(i == 2)
			return Color.cyan;
		if(i == 3)
			return Color.black.brighter();
		if(i == 4)
			return Color.ORANGE;
		if(i == 5)
			return Color.BLUE;
		if(i == 6)
			return Color.GREEN;
		if(i == 7)
			return Color.pink;
		if(i == 8)
			return Color.YELLOW.darker();
		if(i == 9)
			return Color.red.darker();
		if(i == 10)
			return Color.magenta;
		if(i == 11)
			return Color.cyan.darker();
		if(i == 12)
			return Color.orange.darker();
		if(i == 13)
			return Color.magenta.darker();
		if(i == 14)
			return Color.YELLOW;
		if(i == 15)
			return Color.pink.darker();

		return Color.black;
	}

	private VerticeView<Planta> clicEnUnNodo(Point p) {
		for (VerticeView<Planta> v : this.vertices) {
			if (v.getNodo().contains(p)) {
				return v;
			}
		}
		return null;
	}

	private AristaView<Planta> clicEnRuta(Point p) {
		for (AristaView<Planta> r : this.aristas) {
			if (r.getLinea().intersects(p.getX(),p.getY(),p.getX()-2,p.getY()-2) || r.getLinea().intersects(p.getX()+2,p.getY()+2,p.getX(),p.getY()) ) {
				return r;
			}
		}
		return null;
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		dibujarVertices(g2d);
		if(!mostrarFlujo)
			dibujarRutas(g2d);
		else
			marcarFlujo(g2d);
	}



	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 400);
	}



	public void clearAristas() {
		this.aristasPintadas.clear();
	}

	public List<VerticeView<Planta>> plantasEnPanel() {
		return this.vertices;
	}

	public List<AristaView<Planta>> rutasEnPanel() {
		return this.aristas;
	}


	public void marcarFlujoActual() {
		this.mostrarFlujo = true;
		repaint = true;
		repaint();
	}

	private void marcarFlujo(Graphics2D g2d) {
		AffineTransform tx = new AffineTransform();
		List<Planta> apuntadas = new ArrayList<Planta>();

		for (AristaView<Planta> a : this.aristas) {
			g2d.setPaint(Color.DARK_GRAY);

			if(a.getPesoEnCurso() == a.getPesoMaxTon())
				g2d.setPaint(Color.RED);
			else if(a.getPesoEnCurso() != 0)
				g2d.setPaint(Color.blue);
			if(this.getReversed(a) == null)
				g2d.drawString("En curso: "+a.getPesoEnCurso()+", Resto: "+(a.getPesoMaxTon()-a.getPesoEnCurso()),((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-40,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2));
			else if(!this.aristasPintadas.contains(this.getReversed(a)) && this.getReversed(a).getPesoEnCurso() == 0) {
				this.aristasPintadas.add(a);
				g2d.drawString("En curso: "+a.getPesoEnCurso()+", Resto: "+(a.getPesoMaxTon()-a.getPesoEnCurso()),
						((a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2)-40,((a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2));
			}

			if(this.getReversed(a) == null || (!this.aristasPintadas.contains(this.getReversed(a)) && this.getReversed(a).getPesoEnCurso() == 0)) {
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
				Color c = getFlechaColor(apuntadas.stream().filter(p -> p.equals(a.getDestino().getValor())).count());
				g2d.setPaint(c);

				Graphics2D g = (Graphics2D) g2d.create();
				g.setTransform(tx);   
				g.fill(arrowHead);
				g.dispose();

				apuntadas.add(a.getDestino().getValor());
			}
		}

		this.aristasPintadas.clear();
	}

	public void isRepaint() {
		repaint = true;

	}

}
