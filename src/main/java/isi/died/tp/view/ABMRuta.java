package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.controller.PlantaController;
import isi.died.tp.controller.RutaController;
import isi.died.tp.service.*;
import isi.died.tp.model.*;

public class ABMRuta {

	private RutaController controller;
	private PlantaController pc;
	private JFrame ventana;

	public ABMRuta(JFrame ventana, RutaController rc, PlantaController pc){
		this.controller = rc;
		this.pc = pc;
		this.ventana = ventana;
	}

	public void agregarRuta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Agregar Nueva Ruta"), errorRuta = new JLabel()
				, errorDistancia = new JLabel(), errorPesoMax = new JLabel(), errorDuracion = new JLabel();
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JTextField tDistancia = new JTextField(20), tDuracion = new JTextField(20),
				tPesoMaximo = new JTextField(20);
		List<Planta> lista = new ArrayList<Planta>();

		JComboBox<String> seleccionarP1 = new JComboBox<String>(), seleccionarP2 = new JComboBox<String>();

		lista.addAll(pc.listaPlantas());
		seleccionarP1.addItem("Seleccione");
		for (Planta p : lista) {
			seleccionarP1.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");				
		}

		if(lista.isEmpty())
			seleccionarP1.setEnabled(false);

		seleccionarP2.addItem("Seleccione");
		for (Planta p : lista) {	
			seleccionarP2.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");		
		}

		if(lista.isEmpty())
			seleccionarP2.setEnabled(false);

		seleccionarP1.setSelectedItem("Seleccione");



		List<Planta> seleccionadoP1 = new ArrayList<Planta>();
		List<Planta> seleccionadoP2 = new ArrayList<Planta>();
		seleccionarP1.addActionListener (a -> {
			switch(seleccionarP1.getSelectedIndex()){
			case 0:
				aceptar.setEnabled(false);
				seleccionadoP1.clear();
				break;
			default:
				seleccionadoP1.clear();
				seleccionadoP1.add(lista.get(seleccionarP1.getSelectedIndex()-1));
				if(!seleccionadoP2.isEmpty() && !seleccionadoP1.get(0).equals(seleccionadoP2.get(0))) {
					aceptar.setEnabled(true);
				}else {
					aceptar.setEnabled(false);
				}
				break;
			}
		});

		seleccionarP2.addActionListener (a -> {
			switch(seleccionarP2.getSelectedIndex()){
			case 0:
				aceptar.setEnabled(false);
				seleccionadoP2.clear();
				break;
			default:
				seleccionadoP2.clear();
				seleccionadoP2.add(lista.get(seleccionarP2.getSelectedIndex()-1));
				if(!seleccionadoP1.isEmpty() && !seleccionadoP2.get(0).equals(seleccionadoP1.get(0))) {
					aceptar.setEnabled(true);
				}else {
					aceptar.setEnabled(false);
				}
				break;
			}
		});

		aceptar.setEnabled(false);



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
		constraints.insets.set(5, 136, 15, 0);
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

		//LOS COMBOBOX ACTIVAN EL BOTON ACEPTAR SOLO SI LA SELECCION DE PLANTAS ES CORRECTA
		aceptar.addActionListener(a -> {

			Integer distancia = null;
			Double duracion = null;
			Integer pesoMax = null;
			if(controller.existeRuta(seleccionadoP1.get(0),seleccionadoP2.get(0)))
				errorRuta.setText("Esta Ruta ya existe");
			else {
				errorRuta.setText("");
				errorDistancia.setText("");
				errorDuracion.setText("");
				errorPesoMax.setText("");
				try {

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
						controller.agregarRuta(0, seleccionadoP1.get(0), seleccionadoP2.get(0), distancia, duracion, pesoMax);
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
			}
		});
		constraints.insets.set(55,190,0,90);
		constraints.anchor=GridBagConstraints.WEST;

		constraints.gridx=2;
		panel.add(volver, constraints);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());


		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx=2;
		constraints.insets.set(5, 5, 15, 90);
		constraints.gridy=2;

		panel.add(seleccionarP1, constraints);

		constraints.gridy=3;

		panel.add(seleccionarP2, constraints);

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
		//constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.gridx=3;
		constraints.gridy=3;
		constraints.insets.set(5, 0, 15, 0);
		
		errorRuta.setPreferredSize(new Dimension(160, 16));
		errorRuta.setForeground(Color.red);
		panel.add(errorRuta,constraints);
		
		constraints.gridy=4;
		errorDistancia.setPreferredSize(new Dimension(180, 16));
		errorDistancia.setForeground(Color.red);
		panel.add(errorDistancia,constraints);

		constraints.gridy=5;
		errorDuracion.setPreferredSize(new Dimension(180, 16));
		errorDuracion.setForeground(Color.red);
		panel.add(errorDuracion,constraints);

		constraints.gridy=6;
		errorPesoMax.setPreferredSize(new Dimension(180, 16));
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
