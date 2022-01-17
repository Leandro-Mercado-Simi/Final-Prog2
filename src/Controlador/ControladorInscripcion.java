/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import Modelo.*;
import Vista.*;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorInscripcion implements ActionListener, MouseListener {

    public Modelo.ModeloInscripcion modInsc;
    public Vista.VistaInscripcion vistaInsc;
    public Vista.VistaMenuPrincipal menuPrincipal = new VistaMenuPrincipal();
    boolean codVerif;
    boolean dniVerif;
    boolean carCodVerif;

    public ControladorInscripcion(ModeloInscripcion modInsc, VistaInscripcion vistaInsc) {
        this.modInsc = modInsc;
        this.vistaInsc = vistaInsc;
        this.vistaInsc.setVisible(true);
        EscucharBotones();
        this.modInsc.Refresh(this.vistaInsc.jTabla_Insc);
    }

    public void EscucharBotones() {
        this.vistaInsc.jB_AgregarInsc.addActionListener(this);
        this.vistaInsc.jTabla_Insc.addMouseListener(this);
        this.vistaInsc.jB_ModificarInsc.addActionListener(this);
        this.vistaInsc.jB_QuitarInsc.addActionListener(this);
        this.vistaInsc.jB_SalirInsc.addActionListener(this);
        this.vistaInsc.jB_Buscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vistaInsc.jB_AgregarInsc)) {
            //DNI_ALU
            this.modInsc.setDni_alu(Integer.parseInt(this.vistaInsc.jTF_dniAlu.getText()));
            if (this.modInsc.verifDNI(this.modInsc.getDni_alu())) {
                dniVerif = true;
            } else {
                dniVerif = false;
                JOptionPane.showMessageDialog(null, "El dni ingresado no es válido");
            }
            //FECHA_INS
            java.util.Date fecha = this.vistaInsc.jDC_fechaInsc.getDate();
            Date fechaSql = new Date(fecha.getTime());
            this.modInsc.setInsc_fecha(fechaSql);
            //INSC_CAR_COD
            this.modInsc.setInsc_car_cod(Integer.parseInt(this.vistaInsc.jTF_CodCarrera.getText()));
            if (this.modInsc.verifCarCod(this.modInsc.getInsc_car_cod())) {
                carCodVerif = true;
            } else {
                carCodVerif = false;
                JOptionPane.showMessageDialog(null, "El código de carrera ingresado no es válido");
            }
            if (dniVerif && carCodVerif) {
                this.modInsc.Agregar(modInsc);
                this.modInsc.Refresh(this.vistaInsc.jTabla_Insc);
            }
        } else if (e.getSource().equals(this.vistaInsc.jB_Buscar)) {
            Rellenar();

        } else if (e.getSource().equals(this.vistaInsc.jB_ModificarInsc)) {
            this.modInsc.setInsc_cod(Integer.parseInt(this.vistaInsc.jTF_Cod_insc.getText()));
            this.modInsc.setDni_alu(Integer.parseInt(this.vistaInsc.jTF_dniAlu.getText()));
            java.util.Date fecha = this.vistaInsc.jDC_fechaInsc.getDate();
            Date fechaSql = new Date(fecha.getTime());
            this.modInsc.setInsc_fecha(fechaSql);
            this.modInsc.setInsc_car_cod(Integer.parseInt(this.vistaInsc.jTF_CodCarrera.getText()));
            if (MensajesModificar()) {
                this.modInsc.Modificar(modInsc);
                this.modInsc.Refresh(this.vistaInsc.jTabla_Insc);
                LimpiarCampos();
                JOptionPane.showMessageDialog(null, "Cambios registrados");
            }
        } else if (e.getSource().equals(this.vistaInsc.jB_QuitarInsc)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Seguro desea eliminar los datos?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                this.modInsc.setInsc_cod(Integer.parseInt(this.vistaInsc.jTF_Cod_insc.getText()));
                this.modInsc.Quitar(modInsc);
                LimpiarCampos();
                this.modInsc.Refresh(this.vistaInsc.jTabla_Insc);
            }
        } else if (e.getSource().equals(this.vistaInsc.jB_SalirInsc)) {
            this.vistaInsc.dispose();
            ControladorMenuPrincipal menu = new ControladorMenuPrincipal(menuPrincipal);
        }
    }

    public void Rellenar() {
        this.modInsc.setDni_alu(Integer.parseInt(this.vistaInsc.jTF_dniAlu.getText()));
        this.modInsc.setInsc_car_cod(Integer.parseInt(this.vistaInsc.jTF_CodCarrera.getText()));
        Object arreglo[] = this.modInsc.Buscar(modInsc);
        if (arreglo[0] == null) {
            this.vistaInsc.jTF_NomAlu.setText("El dni ingresado no es válido");
        } else {
            this.vistaInsc.jTF_NomAlu.setText(arreglo[0] + " " + arreglo[1]);
        }
        if (arreglo[2] == null) {
            this.vistaInsc.jTF_NomCarrera.setText("El código ingresado no es válido");
        } else {
            this.vistaInsc.jTF_NomCarrera.setText(arreglo[2].toString());
        }
    }

    public boolean MensajesModificar() {
        if (this.modInsc.verifDNI(this.modInsc.getDni_alu())) {
            dniVerif = true;
        } else {
            dniVerif = false;
            JOptionPane.showMessageDialog(null, "El dni ingresado no es válido");
        }
        if (this.modInsc.verifCarCod(this.modInsc.getInsc_car_cod())) {
            carCodVerif = true;
        } else {
            carCodVerif = false;
            JOptionPane.showMessageDialog(null, "El código de carrera ingresado no es válido");
        }
        if (dniVerif && carCodVerif) {
            return true;
        } else {
            return false;
        }
    }

    public void LimpiarCampos() {
        this.vistaInsc.jTF_Cod_insc.setText(" ");
        this.vistaInsc.jTF_dniAlu.setText(" ");
        this.vistaInsc.jTF_CodCarrera.setText(" ");
        this.vistaInsc.jTF_NomCarrera.setText(" ");
        this.vistaInsc.jTF_NomAlu.setText(" ");
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            int seleccion = this.vistaInsc.jTabla_Insc.rowAtPoint(me.getPoint());
            if (seleccion > -1) {
                this.vistaInsc.jTF_Cod_insc.setText(String.valueOf(this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 0)));
                this.vistaInsc.jTF_dniAlu.setText(String.valueOf(this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 1)));
                this.vistaInsc.jDC_fechaInsc.setDate((java.util.Date) this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 2));
                this.vistaInsc.jTF_CodCarrera.setText(String.valueOf(this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 3)));
                this.vistaInsc.jTF_NomAlu.setText(String.valueOf(this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 4)));
                this.vistaInsc.jTF_NomCarrera.setText(String.valueOf(this.vistaInsc.jTabla_Insc.getValueAt(seleccion, 5)));
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
