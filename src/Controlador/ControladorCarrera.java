/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorCarrera implements ActionListener, MouseListener {

    public Modelo.ModeloCarrera modCar;
    public Vista.VistaCarrera vistaCar;
    public Vista.VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();
    boolean codValido;
    boolean nomValido;
    boolean durValida;

    public ControladorCarrera(ModeloCarrera modCar, VistaCarrera vistaCar) {
        this.modCar = modCar;
        this.vistaCar = vistaCar;
        this.vistaCar.setVisible(true);
        EscucharBotones();
        modCar.Refresh(this.vistaCar.jTabla_Carrera);

    }

    public void EscucharBotones() {
        this.vistaCar.jB_AgregarCarrera.addActionListener(this);
        this.vistaCar.jB_ModificarCarrera.addActionListener(this);
        this.vistaCar.jB_QuitarCarrera.addActionListener(this);
        this.vistaCar.jB_SalirCarrera.addActionListener(this);
        this.vistaCar.jTabla_Carrera.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaCar.jB_AgregarCarrera)) {
            try {
                this.modCar.setCar_cod(Integer.parseInt(this.vistaCar.jTF_Cod_Carrera.getText()));
                this.modCar.setCar_nombre(this.vistaCar.jTF_Nombre_Carrera.getText());
                this.modCar.setDuracion(this.vistaCar.jTF_Duracion_Carrera.getText());
                if (Mensajes()) {
                    this.modCar.CargaDatos(modCar);
                    JOptionPane.showMessageDialog(null, "Se ha agregado la carrera");
                    this.modCar.Refresh(this.vistaCar.jTabla_Carrera);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Corrobore los datos ingresados");
            }
            
            
        } else if (e.getSource().equals(this.vistaCar.jB_ModificarCarrera)) {
            try {
                this.modCar.setCar_cod(Integer.parseInt(this.vistaCar.jTF_Cod_Carrera.getText()));
                this.modCar.setCar_nombre(this.vistaCar.jTF_Nombre_Carrera.getText());
                this.modCar.setDuracion(this.vistaCar.jTF_Duracion_Carrera.getText());
                if (MensajesModificar()) {
                    this.modCar.ModificarDatos(modCar);
                    JOptionPane.showMessageDialog(null, "Los cambios se han registrado con éxito");
                    LimpiarCampos();
                    modCar.Refresh(this.vistaCar.jTabla_Carrera);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se han podido registrar los cambios");
            }
            
            
        } else if (e.getSource().equals(this.vistaCar.jB_QuitarCarrera)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                this.modCar.QuitarCarrera(Integer.parseInt(this.vistaCar.jTF_Cod_Carrera.getText()));
                LimpiarCampos();
                this.modCar.Refresh(this.vistaCar.jTabla_Carrera);
            }
            
            
        } else if (e.getSource().equals(this.vistaCar.jB_SalirCarrera)) {
            this.vistaCar.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);

        }

    }

    public boolean Mensajes() {
        System.out.println("Estoy en el método mensajes");
        if (this.modCar.ValidarCod(this.modCar.getCar_cod())) {
            codValido = true;
        } else {
            codValido = false;
            JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
        }
        if (this.modCar.ValidarNombre(this.modCar.getCar_nombre())) {
            nomValido = true;
        } else {
            nomValido = false;
            JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido");
        }
        if (this.modCar.ValidarDuracion(this.modCar.getDuracion())) {
            durValida = true;
        } else {
            durValida = false;
            JOptionPane.showMessageDialog(null, "La duración ingresada no es válida, ingrese la cantidad de años");
        }
        if (codValido && nomValido && durValida) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean MensajesModificar() {
        if (this.modCar.ValidarNombre(this.modCar.getCar_nombre())) {
            System.out.println("nombre " +this.modCar.getCar_nombre());
            nomValido = true;
        } else {
            nomValido = false;
            JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido");
        }
        if (this.modCar.ValidarDuracion(this.modCar.getDuracion())) {
            System.out.println("duración " +this.modCar.getDuracion());
            durValida = true;
        } else {
            durValida = false;
            JOptionPane.showMessageDialog(null, "La duración ingresada no es válida, ingrese la cantidad de años");
        }
        if (nomValido && durValida) {
            return true;
        } else {
            return false;
        }
    }


    public void LimpiarCampos() {
        this.vistaCar.jTF_Cod_Carrera.setText(" ");
        this.vistaCar.jTF_Nombre_Carrera.setText(" ");
        this.vistaCar.jTF_Duracion_Carrera.setText(" ");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaCar.jTabla_Carrera.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaCar.jTF_Cod_Carrera.setText(String.valueOf(this.vistaCar.jTabla_Carrera.getValueAt(seleccion, 0)));
                this.vistaCar.jTF_Nombre_Carrera.setText(String.valueOf(this.vistaCar.jTabla_Carrera.getValueAt(seleccion, 1)));
                this.vistaCar.jTF_Duracion_Carrera.setText(String.valueOf(this.vistaCar.jTabla_Carrera.getValueAt(seleccion, 2)));

            }
        }

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
