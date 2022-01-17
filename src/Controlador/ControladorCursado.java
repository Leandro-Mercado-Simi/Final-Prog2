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
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorCursado implements ActionListener, MouseListener {

    public Modelo.ModeloCursado modCur;
    public Vista.VistaCursado vistaCur;
    public Vista.VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();
    boolean dniValido = false;
    boolean codValido = false;
    boolean notaValida = false;

    public ControladorCursado(ModeloCursado modCur, VistaCursado vistaCur) {
        this.modCur = modCur;
        this.vistaCur = vistaCur;
        this.vistaCur.setVisible(true);
        EscucharBotones();
        this.modCur.Refresh(this.vistaCur.jTable_Tabla_Notas);
    }

    public void EscucharBotones() {
        this.vistaCur.jB_AgregarNota.addActionListener(this);
        this.vistaCur.jB_Modificar_Nota.addActionListener(this);
        this.vistaCur.jTable_Tabla_Notas.addMouseListener(this);
        this.vistaCur.jB_Quitar_Nota.addActionListener(this);
        this.vistaCur.jB_Salir_Notas.addActionListener(this);
        this.vistaCur.jB_Buscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaCur.jB_AgregarNota)) {
            try {
                //DNI DEL ALUMNO
                this.modCur.setCur_alu_dni(Integer.parseInt(this.vistaCur.jTF_Dni_Alu.getText()));
                if (modCur.ValidarDNI(this.modCur.getCur_alu_dni()) == true) {
                    System.out.println(this.modCur.getCur_alu_dni());
                    dniValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El DNI ingresado no es válido");
                }
                //CÓDIGO DE LA MATERIA
                this.modCur.setCur_mat_cod(Integer.parseInt(this.vistaCur.jTF_Cod_Mat.getText()));
                if (modCur.ValidarCodigo(this.modCur.getCur_mat_cod()) == true) {
                    System.out.println(this.modCur.getCur_mat_cod());
                    codValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
                }
                //NOTA
                this.modCur.setNota(Double.parseDouble(this.vistaCur.jTF_Nota.getText()));
                if (this.modCur.ValidarNota(this.modCur.getNota()) == true) {
                    System.out.println(this.modCur.getNota());
                    notaValida = true;
                } else {
                    JOptionPane.showMessageDialog(null, "La nota ingresada no es válida");
                }
                if (dniValido && codValido && notaValida) {
                    modCur.Cargar(modCur);
                    modCur.Refresh(this.vistaCur.jTable_Tabla_Notas);
                } else{
                    
                }
                    

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Corrobore los datos ingresados");
            }
        } else if (e.getSource().equals(this.vistaCur.jB_Buscar)) {
            Rellenar();

        } else if (e.getSource().equals(this.vistaCur.jB_Modificar_Nota)) {
            try {
                this.modCur.setCur_alu_dni(Integer.parseInt(this.vistaCur.jTF_Dni_Alu.getText()));
                this.modCur.setCur_mat_cod(Integer.parseInt(this.vistaCur.jTF_Cod_Mat.getText()));
                this.modCur.setCur_mat_nombre(this.vistaCur.jTF_Nombre_Materia.getText());
                this.modCur.setNota(Double.parseDouble(this.vistaCur.jTF_Nota.getText()));
                modCur.Modificar(modCur);
                JOptionPane.showMessageDialog(null, "Los cambios se han registrado con éxito");
                LimpiarCampos();
                modCur.Refresh(this.vistaCur.jTable_Tabla_Notas);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se han podido registrar los cambios");
            }

        } else if (e.getSource().equals(this.vistaCur.jB_Quitar_Nota)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                this.modCur.setCur_alu_dni(Integer.parseInt(this.vistaCur.jTF_Dni_Alu.getText()));
                this.modCur.setCur_mat_cod(Integer.parseInt(this.vistaCur.jTF_Cod_Mat.getText()));
                this.modCur.Quitar(modCur);
                LimpiarCampos();
                modCur.Refresh(this.vistaCur.jTable_Tabla_Notas);
            }

        } else if (e.getSource().equals(this.vistaCur.jB_Salir_Notas)) {
            this.vistaCur.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);
        }
    }

    public void LimpiarCampos() {
        this.vistaCur.jTF_Dni_Alu.setText(" ");
        this.vistaCur.jTF_Nombre_alu.setText(" ");
        this.vistaCur.jTF_Cod_Mat.setText(" ");
        this.vistaCur.jTF_Nota.setText(" ");
        this.vistaCur.jTF_Nombre_Materia.setText(" ");
    }

    public void Rellenar() {
        this.modCur.setCur_alu_dni(Integer.parseInt(this.vistaCur.jTF_Dni_Alu.getText()));
        this.modCur.setCur_mat_cod(Integer.parseInt(this.vistaCur.jTF_Cod_Mat.getText()));
        Object arreglo[] = this.modCur.Buscar(modCur);
        if (arreglo[0] == null) {
            this.vistaCur.jTF_Nombre_alu.setText("El dni ingresado no coincide con el de ningún alumno");
        } else {
            this.vistaCur.jTF_Nombre_alu.setText(arreglo[0] + " " + arreglo[1]);
        }

        if (arreglo[2] == null) {
            this.vistaCur.jTF_Nombre_Materia.setText("El código ingresado no coincide con el de ninguna materia");
        } else {
            this.vistaCur.jTF_Nombre_Materia.setText(arreglo[2].toString());
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaCur.jTable_Tabla_Notas.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaCur.jTF_Dni_Alu.setText(String.valueOf(this.vistaCur.jTable_Tabla_Notas.getValueAt(seleccion, 0)));
                this.vistaCur.jTF_Cod_Mat.setText(String.valueOf(this.vistaCur.jTable_Tabla_Notas.getValueAt(seleccion, 1)));
                this.vistaCur.jTF_Nombre_Materia.setText(String.valueOf(this.vistaCur.jTable_Tabla_Notas.getValueAt(seleccion, 2)));
                this.vistaCur.jTF_Nota.setText(String.valueOf(this.vistaCur.jTable_Tabla_Notas.getValueAt(seleccion, 3)));
                Rellenar();
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
