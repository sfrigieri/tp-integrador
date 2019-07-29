package isi.died.tp.view;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


public class VerticeView<T> {
    private Paint color;
    private Color colorBase;

    private Integer coordenadaX;
    private Integer coordenadaY;
    private final Integer RADIO = 20;
    private Shape nodo;

    private String nombre;
    private Integer id;
    private T valor;

    public VerticeView() {
    }

    public VerticeView(Integer coordenadaX, Integer coordenadaY,Color color) {
        this.colorBase = color;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.nodo= new Ellipse2D.Double(coordenadaX,coordenadaY,RADIO,RADIO);
    }

    public boolean pertenece(Point2D p){
        return this.nodo.contains(p);
    }

    public Paint getColor() {
//        if(color==null) color = new GradientPaint(coordenadaX,coordenadaY,colorBase,coordenadaX+RADIO, coordenadaY+RADIO,Color.WHITE);
        if(color == null) color = colorBase;
    	return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    
    public Integer getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(Integer coordX) {
        this.coordenadaX = coordX;
        this.nodo = new Ellipse2D.Double(coordX,this.coordenadaY,this.RADIO,this.RADIO);
    }

    public Integer getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(Integer coordY) {
    	 this.coordenadaY = coordY;
         this.nodo = new Ellipse2D.Double(this.coordenadaX,coordY,this.RADIO,this.RADIO);
    }

    public Shape getNodo() {
        return nodo;
    }

    public void setNodo(Shape nodo) {
        this.nodo = nodo;
    }

    public String etiqueta() {
    	return "["+id+"]"+this.nombre;
    }
    

    
    public Color getColorBase() {
        return colorBase;
    }

    public void setColorBase(Color colorBase) {
        this.colorBase = colorBase;
    }
    
    public String getNombre() {
		return nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	@Override
    public String toString() {
        return "Vertice{" + "coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + '}';
    }

    
    
}

