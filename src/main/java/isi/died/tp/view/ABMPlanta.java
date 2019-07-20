package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.controller.PlantaController;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.Unidad;
import isi.died.tp.service.PlantaService;

public class ABMPlanta {
	private PlantaController controller;
	private JFrame ventana;
	
	public ABMPlanta(JFrame ventana,PlantaService plantaService){
		this.controller = new PlantaController(plantaService, null, null);
		this.ventana = ventana;
	}
	
	public void agregarPlanta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorNombre = new JLabel(), errorTipo = new JLabel(), errorOrigen = new JLabel(),
				encabezado = new JLabel("Agregar Nueva Planta");
		JTextField nombrePlanta = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> tipoPlanta = new JComboBox<String>();
		JCheckBox esOrigen = new JCheckBox();
		List<PlantaAcopio> listaPlantasAcopio = controller.listaPlantasAcopio();
		
		//titulo
		constraints.insets.set(5, 5, 40, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 5, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
				
		constraints.gridy=1;
		panel.add(new JLabel("Tipo de Planta: "), constraints);
		
		constraints.gridy=2;
		panel.add(new JLabel("Nombre: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Es Origen: "), constraints);
		
		//combobox tipoPlanta
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		tipoPlanta.addItem("Acopio");
		tipoPlanta.addItem("Producción");
		tipoPlanta.setSelectedItem("Acopio");
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		panel.add(tipoPlanta, constraints);	
		
		//campo texto nombre
		constraints.gridx = 2;
		constraints.gridy = 2;
		panel.add(nombrePlanta, constraints);
		
		//checkbox
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets.set(0, 0, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 3;
		panel.add(esOrigen, constraints);
		
		//listener tipoPlanta
		tipoPlanta.addActionListener (a -> {
			switch((String)tipoPlanta.getSelectedItem()){
			case "Acopio":
				esOrigen.setEnabled(true);
				break;
			case "Producción":
				esOrigen.setEnabled(false);
				break;
			}
		});
		
		//botones
		constraints.insets.set(25,0,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=2;
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
						
		constraints.gridx=1;
		aceptar.addActionListener(a -> {
			String tipo;
			String nombre;
			Boolean origen = false, existeOrigen = false, existeSumidero = false;
			errorOrigen.setText("");
			errorTipo.setText("");
			errorNombre.setText("");
			
			if ((String)tipoPlanta.getSelectedItem() == "Acopio") {
				origen = esOrigen.isSelected();
				//verifico si ya hay cargado un origen / sumidero
				for(PlantaAcopio planta : listaPlantasAcopio) {
					if (planta.esOrigen())
						existeOrigen = true;
					else
						existeSumidero = true;
				}
				if(existeOrigen && existeSumidero) {
					errorTipo.setText("Ya existen ambas Plantas de Acopio");
					return;
				} else {
					if (origen && existeOrigen) {
						errorOrigen.setText("Ya existe una Planta de Acopio origen");
						return;
					} else if (!origen && existeSumidero) {
						errorOrigen.setText("Ya existe una Planta de Acopio sumidero");
						return;
					} else {
						tipo = (String)tipoPlanta.getSelectedItem();
					}
				}
			} else {
				tipo = (String)tipoPlanta.getSelectedItem();
			}
			if(nombrePlanta.getText().isEmpty()) {
				errorNombre.setText("Debe ingresar un nombre");
				return;
			} else {
				nombre = nombrePlanta.getText();
			}
			
			if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar la nueva planta con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
				if (tipo == "Acopio") { //
					Planta planta = new PlantaAcopio(0, nombre, origen);
					controller.agregarPlanta(planta);
					JOptionPane.showConfirmDialog(ventana, "Planta creada exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				} else {
					Planta planta = new PlantaProduccion(0, nombre);
					controller.agregarPlanta(planta);
					JOptionPane.showConfirmDialog(ventana, "Planta creada exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}
			}
			
		});
		panel.add(aceptar, constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
				
		constraints.gridy=1;
		errorTipo.setPreferredSize(new Dimension(230, 16));
		errorTipo.setForeground(Color.red);
		panel.add(errorTipo,constraints);
				
		constraints.gridy=2;
		errorNombre.setPreferredSize(new Dimension(230, 16));
		errorNombre.setForeground(Color.red);
		panel.add(errorNombre,constraints);
				
		constraints.gridy=3;
		errorOrigen.setPreferredSize(new Dimension(230, 16));
		errorOrigen.setForeground(Color.red);
		panel.add(errorOrigen,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}