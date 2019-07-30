package isi.died.tp.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;

import isi.died.tp.app.Main;
import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.controller.OpcionesMenuLogistica;
import isi.died.tp.model.Planta;


public class GestionLogistica {
	private static JFrame ventana;
	public static GestionLogisticaController controller;
	private static GrafoPlantaController grafoController;

	public GestionLogistica(JFrame v) {
		ventana = v;
		controller = new GestionLogisticaController(v);
		grafoController = GestionLogisticaController.grafoController;
	}

	public static void mostrarMenu() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Gestión de Logística");
		JButton gestionVisual = new JButton("Gestión Visual Red de Plantas"),
				buscarCaminos = new JButton("Caminos entre Plantas"),
				volver = new JButton("Volver");

		//titulo
		constraints.insets=new Insets(5, 5, 120, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);


		//botones ocultos
		constraints.insets.set(0, 105, 30, 0);
		constraints.gridwidth=1;
		constraints.gridx=6;
		constraints.fill=GridBagConstraints.HORIZONTAL;

		constraints.gridy=3;
		panel.add(gestionVisual, constraints);

		constraints.gridy=5;
		panel.add(buscarCaminos, constraints);


		gestionVisual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenuLogistica.GESTION_RED_PLANTAS);
			}
		});

		buscarCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenuLogistica.MEJOR_CAMINO_ENVIO);
			}
		});


		//boton volver
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.SOUTH;
		constraints.insets.set(50, 15, 5, 0);
		constraints.gridx = 6;
		constraints.gridwidth = 2;
		constraints.gridy=10;
		panel.add(volver, constraints);

		//listener volver
		volver.addActionListener(a -> Main.mostrarInterfaz());

		//manejo ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión de Logística");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);


	}
	
    public void getPlantasBuscarCaminos( List<Planta> listaPlantas){
    	
        JComboBox<Planta> opPlantaOrigen;
        JComboBox<Planta> opPlantaDestino;
        JButton buscar;
        JPanel panel = new JPanel();
    	opPlantaOrigen = new JComboBox<Planta>((Planta[]) listaPlantas.toArray()); 
    	opPlantaDestino = new JComboBox<Planta>((Planta[]) listaPlantas.toArray());  
    	buscar = new JButton("Buscar Caminos");
    	buscar.addActionListener(
                e -> { 
                    Planta origen = listaPlantas.get(opPlantaOrigen.getSelectedIndex());
                    Planta destino = listaPlantas.get(opPlantaDestino.getSelectedIndex());
                    grafoController.buscarCaminos(origen,destino); 
                }
        );
        panel.add(new JLabel("Planta Origen"));        
        panel.add(opPlantaOrigen);
        panel.add(new JLabel("Planta Destino"));
        panel.add(opPlantaDestino);      
        panel.add(buscar);        
    }

}	
	