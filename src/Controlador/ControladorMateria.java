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
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorMateria implements ActionListener, MouseListener {

    public Modelo.ModeloMateria modMat = new Modelo.ModeloMateria();
    public Vista.VistaMateria vistaMat = new Vista.VistaMateria();
    public VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();

    public ControladorMateria(ModeloMateria modMat, VistaMateria vistaMat) {
        this.modMat = modMat;
        this.vistaMat = vistaMat;
        this.vistaMat.setVisible(true);
        escucharBotones();
        this.modMat.RefrescarDatos(this.vistaMat.jTable_Materias);
    }

    public void escucharBotones() {
        this.vistaMat.jB_NuevaMateria.addActionListener(this);
        this.vistaMat.jB_ModificarMateria.addActionListener(this);
        this.vistaMat.jTable_Materias.addMouseListener(this);
        this.vistaMat.jB_QuitarMateria.addActionListener(this);
        this.vistaMat.jB_SalirMateria.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vistaMat.jB_NuevaMateria)) {

            try {
                //Validar el código de la materia
                this.modMat.setMat_cod(Integer.parseInt(this.vistaMat.jTF_CodigoMateria.getText()));
                if (this.modMat.ValidarCodigo(this.modMat.getMat_cod()) == false) {
                    JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
                }
                //validar el nombre de la materia
                this.modMat.setMat_nombre(this.vistaMat.jTF_NombreMateria.getText());
                if (this.modMat.ValidarNombre(this.modMat.getMat_nombre()) == false) {
                    JOptionPane.showConfirmDialog(null, "El nombre ingresado no es válido");
                }
                //validar el dni del profesor
                this.modMat.setProf_dni(Integer.parseInt(this.vistaMat.jTF_MateriaDNIProf.getText()));
                if (this.modMat.ValidarDniProf(this.modMat.getProf_dni(), this.modMat)) {
                    this.modMat.RefrescarDatos(vistaMat.jTable_Materias);
                    LimpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error corrobore los datos ingresados");
                }

            } catch (Exception ex) {

            }

        } else if (e.getSource().equals(vistaMat.jB_ModificarMateria)) {

            try {
                this.modMat.setMat_cod(Integer.parseInt(this.vistaMat.jTF_CodigoMateria.getText()));
                this.modMat.setMat_nombre(this.vistaMat.jTF_NombreMateria.getText());
                this.modMat.setProf_dni(Integer.parseInt(this.vistaMat.jTF_MateriaDNIProf.getText()));
                if (this.modMat.ModificarMateria(modMat) == false) {
                    JOptionPane.showMessageDialog(null, "No se han podido registrar los cambios");
                } else {
                    this.modMat.RefrescarDatos(this.vistaMat.jTable_Materias);
                    LimpiarCampos();
                    JOptionPane.showMessageDialog(null, "Cambios registrados con éxito");
                }
            } catch (Exception ex) {

            }

        } else if (e.getSource().equals(vistaMat.jB_QuitarMateria)) {
            try {
                int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (eleccion == JOptionPane.YES_OPTION) {
                    this.modMat.Quitar(Integer.parseInt(this.vistaMat.jTF_CodigoMateria.getText()));
                    LimpiarCampos();
                    this.modMat.RefrescarDatos(this.vistaMat.jTable_Materias);
                }
            } catch (Exception ex) {

            }

        } else if (e.getSource().equals(vistaMat.jB_SalirMateria)) {
            this.vistaMat.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);
        }
    }

    public void LimpiarCampos() {
        this.vistaMat.jTF_CodigoMateria.setText(" ");
        this.vistaMat.jTF_NombreMateria.setText(" ");
        this.vistaMat.jTF_MateriaDNIProf.setText(" ");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaMat.jTable_Materias.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaMat.jTF_CodigoMateria.setText(String.valueOf(vistaMat.jTable_Materias.getValueAt(seleccion, 0)));
                this.vistaMat.jTF_NombreMateria.setText(String.valueOf(vistaMat.jTable_Materias.getValueAt(seleccion, 1)));
                this.vistaMat.jTF_MateriaDNIProf.setText(String.valueOf(vistaMat.jTable_Materias.getValueAt(seleccion, 2)));
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
