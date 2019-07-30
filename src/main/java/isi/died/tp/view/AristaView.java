package isi.died.tp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import isi.died.tp.view.VerticeView;


public class AristaView<T> {

    private VerticeView<T> origen;
    private VerticeView<T> destino;
    private Shape linea;
    private Stroke formatoLinea;
    private Paint color;
    protected Integer valor;

    public AristaView() {
    }

	public AristaView(VerticeView<T> ini,VerticeView<T> fin,Integer val){
		this(ini,fin);
		this.valor= val;
	}

	public AristaView(VerticeView<T> ini,VerticeView<T> fin){
		this();
		this.origen = ini;
		this.destino = fin;
	}
	/**
     * Define que el color de la linea es un degradado (color) 
 desde la coordenada origen de la linea, tomando como base el color origen de la linea
 hacia la coordenada destino de la linea, tomando como base el color destino de la linea
     * @return 
     */
    public Paint getColor() {
//        if(this.color==null) this.color = new GradientPaint(origen.getCoordenadaX() + 10,origen.getCoordenadaY() + 10,destino.getColorBase(),destino.getCoordenadaX() + 10, destino.getCoordenadaY() + 10,origen.getColorBase());
        if(this.color==null) this.color = Color.DARK_GRAY;
    	return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    
    public boolean pertenece(Point2D p) {
        return this.linea.contains(p);
    }

    /**
     * Dibuja una linea 2D desde el las coordenadas del nodo origen 
     * hacia las coordenadas del nodo destino
     * @return 
     */
    public Shape getLinea() {
        this.linea = new Line2D.Double(origen.getCoordenadaX() + 10, origen.getCoordenadaY() + 10, destino.getCoordenadaX() + 10, destino.getCoordenadaY() + 10);
        return linea;
    }

    public void setLinea(Shape linea) {
        this.linea = linea;
    }

    /**
     * Determina el grosor de la linea.
     * @return 
     */
    public Stroke getFormatoLinea() {
        if(this.formatoLinea==null)  this.formatoLinea = new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);       
        return formatoLinea;
    }

    public void setFormatoLinea(Stroke formatoLinea) {
        this.formatoLinea = formatoLinea;
    }

    public VerticeView<T> getOrigen() {
        return origen;
    }

    public void setOrigen(VerticeView<T> origen) {
        this.origen = origen;
    }

    public VerticeView<T> getDestino() {
        return destino;
    }

    public void setDestino(VerticeView<T> destino) {
        this.destino = destino;
    }

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
    @Override
    public String toString() {
        return "Arista{" + "origen=" + origen + ", destino=" + destino + ", linea=" + linea + ", formatoLinea=" + formatoLinea + ", gradiente=" + color + '}';
    }

	public double getDuracionViajeMin() {
		return 0;
	}


	public void setDuracionViajeMin(double duracionViajeMin) {
	}


	public int getPesoMaxTon() {
		return 0;
	}



	public void setPesoMax(int pesoMaxTon) {
		
	}


	public int getPesoEnCurso() {
		return 0;
	}


	public void setPesoEnCurso(int pesoEnCursoTon) {

	}

	public String etiqueta() {
		return "";
	}

}

