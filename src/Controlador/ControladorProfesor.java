/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.*;
import Vista.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorProfesor implements ActionListener, MouseListener {

    public Modelo.ModeloProfesor modProf = new ModeloProfesor();
    public Vista.VistaProfesor vistaProf = new VistaProfesor();
    public VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();

    public ControladorProfesor(ModeloProfesor modProf, VistaProfesor vistaProf) {
        this.modProf = modProf;
        this.vistaProf = vistaProf;
        this.vistaProf.setVisible(true);
        escucharBotones();
        this.modProf.Refrescar(this.vistaProf.jTable_Profesores);
    }

    public void escucharBotones() {
        this.vistaProf.jB_AgregarProfesor.addActionListener(this);
        this.vistaProf.jB_ModificarProfesor.addActionListener(this);
        this.vistaProf.jTable_Profesores.addMouseListener(this);
        this.vistaProf.jB_QuitarProfesor.addActionListener(this);
        this.vistaProf.jB_SalirProfesor.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(vistaProf.jB_AgregarProfesor)) {
            try {
                //Validar DNI
                this.modProf.setDni(this.vistaProf.jTF_DniProfesor.getText());
                this.modProf.validarDni(modProf.getDni());
                //validar Nombre
                this.modProf.setNombre(this.vistaProf.jTF_NombreProfesor.getText());
                this.modProf.validarNombre(this.modProf.getNombre());
                //validar apellido
                this.modProf.setApellido(this.vistaProf.jTF_ApellidoProfesor.getText());
                this.modProf.validarApellido(this.modProf.getApellido());
                //validar dirección
                this.modProf.setDomicilio(this.vistaProf.jTF_DireccionProfesor.getText());
                this.modProf.validarDomicilio(modProf.getDomicilio());
                //validar telefono
                this.modProf.setTelefono(this.vistaProf.jTF_TelefonoProfesor.getText());
                this.modProf.validarTel(this.modProf.getTelefono());
                //Validar fecha de nacimiento

                java.util.Date fechaDate = this.vistaProf.jDC_fecha_Prof.getDate();
                Date fechaSql = new Date(fechaDate.getTime());
                this.modProf.setFec_nac(fechaSql);
                this.modProf.validarFec(modProf.getFec_nac());

                //metodo para verificar los datos ingresados
                if (this.modProf.ValidarDatos() == true) {
                    modProf.NuevoProf(modProf);
                    JOptionPane.showMessageDialog(null, "El Profesor: " + this.vistaProf.jTF_NombreProfesor.getText() + " ha sido cargado con éxito");
                    LimpiarCampos();
                    modProf.Refrescar(this.vistaProf.jTable_Profesores);
                } else {
                    JOptionPane.showMessageDialog(null, "Campos incompletos o datos erróneos");
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Tiene que ingresar su fecha de nacimiento");
            }

        } else if (e.getSource().equals(vistaProf.jB_ModificarProfesor)) {
            try {
                this.modProf.setDni(this.vistaProf.jTF_DniProfesor.getText());
                this.modProf.setNombre(this.vistaProf.jTF_NombreProfesor.getText());
                this.modProf.setApellido(this.vistaProf.jTF_ApellidoProfesor.getText());
                java.util.Date fechaDate = (this.vistaProf.jDC_fecha_Prof.getDate());
                Date fechaSql = new Date(fechaDate.getTime());
                this.modProf.setFec_nac(fechaSql);
                this.modProf.setDomicilio(this.vistaProf.jTF_DireccionProfesor.getText());
                this.modProf.setTelefono(this.vistaProf.jTF_TelefonoProfesor.getText());
                //this.modProf.ModificarProf(modProf);
                if (modProf.ModificarProf(modProf)) {
                    JOptionPane.showMessageDialog(null, "Se han registrado los cambios");
                    LimpiarCampos();
                    modProf.Refrescar(this.vistaProf.jTable_Profesores);
                } else {
                    JOptionPane.showMessageDialog(null, "No se han podido registrar los cambios");
                }

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Tiene que ingresar su fecha de nacimiento");
            }

        } else if (e.getSource().equals(vistaProf.jB_QuitarProfesor)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                modProf.QuitarProfesor(Integer.parseInt(vistaProf.jTF_DniProfesor.getText()));
                LimpiarCampos();
                modProf.Refrescar(this.vistaProf.jTable_Profesores);
            }

        } else if (e.getSource().equals(vistaProf.jB_SalirProfesor)) {
            this.vistaProf.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);
        }

    }

    public void LimpiarCampos() {
        this.vistaProf.jTF_DniProfesor.setText(" ");
        this.vistaProf.jTF_NombreProfesor.setText(" ");
        this.vistaProf.jTF_ApellidoProfesor.setText(" ");
        this.vistaProf.jTF_DireccionProfesor.setText(" ");
        this.vistaProf.jTF_TelefonoProfesor.setText(" ");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaProf.jTable_Profesores.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaProf.jTF_DniProfesor.setText(String.valueOf(vistaProf.jTable_Profesores.getValueAt(seleccion, 0)));
                this.vistaProf.jTF_NombreProfesor.setText(String.valueOf(vistaProf.jTable_Profesores.getValueAt(seleccion, 1)));
                this.vistaProf.jTF_ApellidoProfesor.setText(String.valueOf(vistaProf.jTable_Profesores.getValueAt(seleccion, 2)));
                this.vistaProf.jDC_fecha_Prof.setDate((java.util.Date) vistaProf.jTable_Profesores.getValueAt(seleccion, 3));
                this.vistaProf.jTF_DireccionProfesor.setText(String.valueOf(vistaProf.jTable_Profesores.getValueAt(seleccion, 4)));
                this.vistaProf.jTF_TelefonoProfesor.setText(String.valueOf(vistaProf.jTable_Profesores.getValueAt(seleccion, 5)));
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
