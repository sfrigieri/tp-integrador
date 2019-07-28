package isi.died.tp.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.model.Planta;


public class ControlPanel extends JPanel {
    
    private JComboBox<Planta> cmbVertice1; 
    private JComboBox<Planta> cmbVertice2; 
    private JTextField txtLongitudCamino; 
    private JButton btnBuscarCamino; 
    private static GrafoPlantaController glc = GestionLogisticaController.grafoController;
    private List<Planta> listaVertices;
        

    public void buscarCaminos( List<Planta> listaVertices){
    	this.listaVertices = listaVertices;
    	this.cmbVertice1 = new JComboBox(listaVertices.toArray()); 
        this.cmbVertice2 = new JComboBox(listaVertices.toArray()); 
        this.txtLongitudCamino = new JTextField(5); 
        this.btnBuscarCamino = new JButton("Buscar Camino");
        this.btnBuscarCamino.addActionListener(
                e -> { 
                    Planta origen = this.listaVertices.get(cmbVertice1.getSelectedIndex());
                    Planta destino = this.listaVertices.get(cmbVertice2.getSelectedIndex());
                    glc.buscarCaminos(origen,destino); 
                }
        );
        this.add(new JLabel("Vertice Origen"));        
    	this.add(cmbVertice1);
    	this.add(new JLabel("Vertice Destino"));
    	this.add(cmbVertice2);
    	this.add(new JLabel("Cantidad de saltos"));        
    	this.add(txtLongitudCamino);        
    	this.add(btnBuscarCamino);        
    }


    
}
