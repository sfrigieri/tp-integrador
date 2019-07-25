package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.controller.RutaController;
import isi.died.tp.service.*;
import isi.died.tp.model.*;

public class ABMRuta {

	private RutaController controller;
	private JFrame ventana;
	
	public ABMRuta(JFrame ventana, RutaService rs){
		this.controller = new RutaController(rs);
		this.ventana = ventana;
	}
	
	public void agregarRuta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Agregar Nueva Ruta"),
				errorPlantaFin = new JLabel(), errorDistancia = new JLabel(), errorPesoMax = new JLabel(), errorDuracion = new JLabel();
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JTextField tDistancia = new JTextField(20), tDuracion = new JTextField(20),
					tPesoMaximo = new JTextField(20);
		JComboBox<String> plantaInicio = new JComboBox<String>(), plantaFin = new JComboBox<String>();
			
		//titulo
		constraints.insets.set(0, 20, 75, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);
	
		//labels
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(5, 175, 15, 0);
		constraints.gridx=1;
		constraints.gridwidth=1;
		
		constraints.gridy=2;
		panel.add(new JLabel("Planta Inicio: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Planta Fin: "), constraints);

		constraints.gridy=4;
		panel.add(new JLabel("Distancia (Km): "), constraints);
		
		constraints.gridy=5;
		panel.add(new JLabel("Duracion (Min): "), constraints);
		
		constraints.gridy=6;
		panel.add(new JLabel("Peso Máximo (Ton): "), constraints);
		
		//botones
		constraints.insets.set(55,50,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;

		constraints.anchor=GridBagConstraints.EAST;
		panel.add(aceptar, constraints);
		aceptar.addActionListener(a -> {
			Planta inicio;
			Planta fin;
			Integer distancia = null;
			Double duracion = null;
			Integer pesoMax = null;
					
			errorPlantaFin.setText("");
			errorDistancia.setText("");
			errorDuracion.setText("");
			errorPesoMax.setText("");
			try {
				if(plantaInicio.getSelectedItem() == plantaFin.getSelectedItem()) {
					errorPlantaFin.setText("Debe seleccionar plantas diferentes");
					return;
				}else {
					//inicio = controller.buscarPlantaPorNombre((String)plantaInicio.getSelectedItem());
					//fin = controller.buscarPlantaPorNombre((String)plantaFin.getSelectedItem());
					inicio = new PlantaAcopio(0, "A");
					fin = new PlantaProduccion(1, "P");
				}
				if(tDistancia.getText().isEmpty()) {
					errorDistancia.setText("Debe ingresar una distancia");
					return;
				}else {
					distancia = Integer.parseInt(tDistancia.getText());
				}
				if(tDuracion.getText().isEmpty()) {
					errorDuracion.setText("Debe ingresar una duración");
					return;
				}else{
					duracion = Double.parseDouble(tDuracion.getText());
				}
				if(tPesoMaximo.getText().isEmpty()) {
					errorPesoMax.setText("Debe ingresar un peso máximo");
					return;
				} else {
					pesoMax = Integer.parseInt(tPesoMaximo.getText());
				}
						
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar la nueva ruta con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					controller.agregarRuta(0, inicio, fin, distancia, duracion, pesoMax);
				}					
						
			}catch(NumberFormatException nfex) {
				if(distancia == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Distancia debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if(duracion == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo Duracion debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					} else {
						if (pesoMax == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo Peso Máximo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					}
				}
			}
		});
		constraints.insets.set(55,190,0,90);
		constraints.anchor=GridBagConstraints.WEST;
		
		constraints.gridx=2;
		panel.add(volver, constraints);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		
		//esLiquido.addItemListener(esLiquidoListener);
		
		/*plantaInicio.addActionListener (a -> {
			String planta1 = (String)plantaInicio.getSelectedItem();
			
		});*/
		
		//comboBoxes plantas	//cambiarlo por un for con getNombre de cada Planta de listaPlantas
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx=2;
		constraints.insets.set(5, 5, 15, 90);
		constraints.gridy=2;
		plantaInicio.addItem("PlantaAcopio1");
		plantaInicio.addItem("PlantaAcopio2");
		plantaInicio.addItem("PlantaProduccion1");
		plantaInicio.addItem("PlantaProduccion2");
		plantaInicio.addItem("PlantaProduccion3");
		panel.add(plantaInicio, constraints);
		
		constraints.gridy=3;
		plantaFin.addItem("PlantaAcopio1");
		plantaFin.addItem("PlantaAcopio2");
		plantaFin.addItem("PlantaProduccion1");
		plantaFin.addItem("PlantaProduccion2");
		plantaFin.addItem("PlantaProduccion3");
		panel.add(plantaFin, constraints);
		
		//campos de texto
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		
		constraints.gridy=4;
		panel.add(tDistancia, constraints);
		constraints.gridy=5;
		panel.add(tDuracion, constraints);
		constraints.gridy=6;
		panel.add(tPesoMaximo, constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.gridx=3;
		constraints.gridy=3;
		errorPlantaFin.setPreferredSize(new Dimension(230, 16));
		errorPlantaFin.setForeground(Color.red);
		panel.add(errorPlantaFin,constraints);
				
		constraints.gridy=4;
		errorDistancia.setPreferredSize(new Dimension(230, 16));
		errorDistancia.setForeground(Color.red);
		panel.add(errorDistancia,constraints);
				
		constraints.gridy=5;
		errorDuracion.setPreferredSize(new Dimension(230, 16));
		errorDuracion.setForeground(Color.red);
		panel.add(errorDuracion,constraints);
		
		constraints.gridy=6;
		errorPesoMax.setPreferredSize(new Dimension(230, 16));
		errorPesoMax.setForeground(Color.red);
		panel.add(errorPesoMax,constraints);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}
