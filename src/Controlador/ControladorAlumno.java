/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.*;
import Modelo.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Usuario
 */
public class ControladorAlumno implements ActionListener, MouseListener {

    public Modelo.ModeloAlumno modAlu;
    public VistaAlumno vistaAlu;
    public VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();
    private java.util.Date fechaDate1;
    boolean nomValido;
    boolean apellidoValido;
    boolean dirValida;
    boolean telValido;

    public ControladorAlumno(ModeloAlumno modAlu, VistaAlumno vistaAlu) {
        this.modAlu = modAlu;
        this.vistaAlu = vistaAlu;
        this.vistaAlu.setVisible(true);
        escucharBotones();
        this.modAlu.Refresh(vistaAlu.jTable_Alumnos);
    }

    public void escucharBotones() {
        this.vistaAlu.jB_Agregar.addActionListener(this);
        this.vistaAlu.jB_Modificar.addActionListener(this);
        this.vistaAlu.jTable_Alumnos.addMouseListener(this);
        this.vistaAlu.jB_Quitar.addActionListener(this);
        this.vistaAlu.jB_Salir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vistaAlu.jB_Agregar)) {
           // try {
                //DNI
                this.modAlu.setDni(this.vistaAlu.jTF_DniAlu.getText());//traigo la variable del textField
                this.modAlu.DNIValido(modAlu.getDni());//llamo al método para validar y paso como parámetro lo que recibi
                this.modAlu.dniRep(modAlu);
                //Nombre
                this.modAlu.setNombre(this.vistaAlu.jTF_NombreAlu.getText());
                this.modAlu.NombreValido(modAlu.getNombre());
                //Apellido
                this.modAlu.setApellido(this.vistaAlu.jTF_ApellidoAlu.getText());
                this.modAlu.ApellidoValido(modAlu.getApellido());
                //Dirección
                this.modAlu.setDomicilio(this.vistaAlu.jTF_DireccionAlu.getText());
                this.modAlu.DomValido(modAlu.getDomicilio());
                //Telefono
                this.modAlu.setTelefono(this.vistaAlu.jTF_TelefonoAlu.getText());
                this.modAlu.validarTel(modAlu.getTelefono());
                //Fecha

                java.util.Date fechaDate = this.vistaAlu.jDC_fecha_Alu.getDate();
                Date fechasql = new Date(fechaDate.getTime());
                this.modAlu.setFec_nac(fechasql);
                this.modAlu.validarFec(modAlu.getFec_nac());

                //Metodo para verificar que todos los datos hayan sido cargados correctamente
                if (this.modAlu.Carga() == true) {
                    modAlu.NuevoAlumno(modAlu);
                    JOptionPane.showMessageDialog(null, "El alumno " + this.vistaAlu.jTF_NombreAlu.getText() + " ha sido cargado con éxito");
                    LimpiarCampos();
                    modAlu.Refresh(this.vistaAlu.jTable_Alumnos);
                } else {
                    JOptionPane.showMessageDialog(null, "Campos incompletos o datos erróneos");
                }
//            } catch (NullPointerException ex) {
//                JOptionPane.showMessageDialog(null, "Tiene que ingresar su fecha de nacimiento");
//            }

        } else if (e.getSource().equals(vistaAlu.jB_Modificar)) {
            try {
                this.modAlu.setDni(this.vistaAlu.jTF_DniAlu.getText());
                this.modAlu.setNombre(this.vistaAlu.jTF_NombreAlu.getText());
                this.modAlu.setApellido(this.vistaAlu.jTF_ApellidoAlu.getText());
                fechaDate1 = (this.vistaAlu.jDC_fecha_Alu.getDate());
                Date fechasql1 = new Date(fechaDate1.getTime());
//                System.out.println("FECHA SQL = " + fechasql1);
                this.modAlu.setFec_nac(fechasql1);
//                this.modAlu.validarFec(modAlu.getFec_nac());
                this.modAlu.setDomicilio(this.vistaAlu.jTF_DireccionAlu.getText());
                this.modAlu.setTelefono(this.vistaAlu.jTF_TelefonoAlu.getText());
                if (Mensajes()) {
                    this.modAlu.ModificarAlumno(modAlu);
                    JOptionPane.showMessageDialog(null, "Se han registrado los cambios");
                    LimpiarCampos();
                    modAlu.Refresh(this.vistaAlu.jTable_Alumnos);
                }

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Tiene que ingresar su fecha de nacimiento");
            }

        } else if (e.getSource().equals(vistaAlu.jB_Quitar)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                modAlu.QuitarAlumno(Integer.parseInt(vistaAlu.jTF_DniAlu.getText()));
                LimpiarCampos();
                modAlu.Refresh(this.vistaAlu.jTable_Alumnos);
            }

        } else if (e.getSource().equals(vistaAlu.jB_Salir)) {
            this.vistaAlu.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);

        }
    }

    public boolean Mensajes() {
        if (this.modAlu.NombreValido(this.vistaAlu.jTF_NombreAlu.getText())) {
            nomValido = true;
        } else {
            nomValido = false;
            JOptionPane.showMessageDialog(null, "Nombre ingresado no válido");
        }
        if (this.modAlu.ApellidoValido(this.vistaAlu.jTF_ApellidoAlu.getText())) {
            apellidoValido = true;
        } else {
            apellidoValido = false;
            JOptionPane.showMessageDialog(null, "Apellido ingresado no válido");
        }
        if (this.modAlu.DomValido(this.vistaAlu.jTF_DireccionAlu.getText())) {
            dirValida = true;
        } else {
            dirValida = false;
            JOptionPane.showMessageDialog(null, "Dirección ingresada no válida");
        }
        if (this.modAlu.validarTel(this.vistaAlu.jTF_TelefonoAlu.getText())) {
            telValido = true;
        } else {
            telValido = false;
            JOptionPane.showMessageDialog(null, "Teléfono ingresado no válido");
        }
        if (nomValido && apellidoValido && dirValida && telValido) {
            return true;
        } else {
            return false;
        }
    }

    public void LimpiarCampos() {
        this.vistaAlu.jTF_DniAlu.setText("");
        this.vistaAlu.jTF_NombreAlu.setText("");
        this.vistaAlu.jTF_ApellidoAlu.setText("");
        this.vistaAlu.jTF_DireccionAlu.setText("");
        this.vistaAlu.jTF_TelefonoAlu.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaAlu.jTable_Alumnos.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaAlu.jTF_DniAlu.setText(String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 0)));
                //System.out.println("Clickeado: " + String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 0)));
                this.vistaAlu.jTF_NombreAlu.setText(String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 1)));
                this.vistaAlu.jTF_ApellidoAlu.setText(String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 2)));
                this.vistaAlu.jDC_fecha_Alu.setDate((java.util.Date) vistaAlu.jTable_Alumnos.getValueAt(seleccion, 3));
                this.vistaAlu.jTF_DireccionAlu.setText(String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 4)));
                this.vistaAlu.jTF_TelefonoAlu.setText(String.valueOf(vistaAlu.jTable_Alumnos.getValueAt(seleccion, 5)));
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
