/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.InscripcionDAO;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloInscripcion {

    private int insc_cod;
    private int dni_alu;
    private Date insc_fecha;
    private String insc_nombre;
    private int insc_car_cod;
    private String alu_nombre;
    private String car_nombre;
    private InscripcionDAO inscDAO = new InscripcionDAO();

    public ModeloInscripcion() {
    }

    public ModeloInscripcion(int insc_cod, int dni_alu, Date insc_fecha, String insc_nombre, int insc_car_cod, String alu_nombre, String car_nombre) {
        this.insc_cod = insc_cod;
        this.dni_alu = dni_alu;
        this.insc_fecha = insc_fecha;
        this.insc_nombre = insc_nombre;
        this.insc_car_cod = insc_car_cod;
        this.alu_nombre = alu_nombre;
        this.car_nombre = car_nombre;
    }

    public int getInsc_cod() {
        return insc_cod;
    }

    public void setInsc_cod(int insc_cod) {
        this.insc_cod = insc_cod;
    }

    public int getDni_alu() {
        return dni_alu;
    }

    public void setDni_alu(int dni_alu) {
        this.dni_alu = dni_alu;
    }

    public Date getInsc_fecha() {
        return insc_fecha;
    }

    public void setInsc_fecha(Date insc_fecha) {
        this.insc_fecha = insc_fecha;
    }

    public String getInsc_nombre() {
        return insc_nombre;
    }

    public void setInsc_nombre(String insc_nombre) {
        this.insc_nombre = insc_nombre;
    }

    public int getInsc_car_cod() {
        return insc_car_cod;
    }

    public void setInsc_car_cod(int insc_car_cod) {
        this.insc_car_cod = insc_car_cod;
    }

    public String getAlu_nombre() {
        return alu_nombre;
    }

    public void setAlu_nombre(String alu_nombre) {
        this.alu_nombre = alu_nombre;
    }

    public String getCar_nombre() {
        return car_nombre;
    }

    public void setCar_nombre(String car_nombre) {
        this.car_nombre = car_nombre;
    }

    public boolean VerifInsCod(int codigo) {
        boolean verificado = false;
        try {
            String codigoAux = String.valueOf(codigo);
            if (codigoAux.length() <= 0) {
                return verificado;
            } else {
                for (int i = 0; i < codigoAux.length(); i++) {
                    if (Character.isLetter(codigoAux.charAt(i))) {
                        verificado = false;
                        break;
                    } else {
                        verificado = true;
                    }
                }
            }

        } catch (Exception ex) {

        }
        return verificado;
    }

    public boolean verifDNI(int dni) {
        boolean dniVerif = true;
        try {

            String dniAux = String.valueOf(dni);
            if (dniAux.length() <= 0) {
                dniVerif = false;
                return dniVerif;
            } else {
                for (int i = 0; i < dniAux.length(); i++) {
                    if (Character.isLetter(dniAux.charAt(i))) {
                        dniVerif = false;
                        break;
                    } else {
                        dniVerif = true;
                    }
                }
            }

        } catch (Exception ex) {

        }
        return dniVerif;
    }

    public boolean verifCarCod(int carCod) {
        boolean carCodVerif = true;
        try {
            String carCodAux = String.valueOf(carCod);
            if (carCodAux.length() <= 0) {
                carCodVerif = false;
                return carCodVerif;
            } else {
                for (int i = 0; i < carCodAux.length(); i++) {
                    if (Character.isLetter(carCodAux.charAt(i))) {
                        carCodVerif = false;
                        break;
                    } else {
                        carCodVerif = true;
                    }
                }
            }

        } catch (Exception ex) {

        }
        return carCodVerif;
    }
    
    public String[] Buscar(ModeloInscripcion inscripcion){
        return inscDAO.Buscar(inscripcion);
    }

    public void Agregar(ModeloInscripcion modInsc) {
        inscDAO.AgregarInsc(modInsc);
    }
    
    public void Modificar(ModeloInscripcion inscripcion){
        inscDAO.Modificar(inscripcion);
    }
    
    public void Quitar(ModeloInscripcion inscripcion){
        inscDAO.Quitar(inscripcion);
    }
    
     public void Refresh(JTable tablaInsc) {
        ArrayList<ModeloInscripcion> inscripciones = inscDAO.Consulta();
        DefaultTableModel tablaModelo = (DefaultTableModel) tablaInsc.getModel();
        for (int i = tablaModelo.getRowCount(); i > 0; i--) {
            tablaModelo.removeRow(i - 1);
        }
        Object[] arreglo = new Object[6];
        if (inscripciones.size() > 0) {
            for (int i = 0; i < inscripciones.size(); i++) {
                arreglo[0] = inscripciones.get(i).getInsc_cod();
                arreglo[1] = inscripciones.get(i).getDni_alu();
                arreglo[2] = inscripciones.get(i).getInsc_fecha();
                arreglo[3] = inscripciones.get(i).getInsc_car_cod();
                arreglo[4] = inscripciones.get(i).getAlu_nombre();
                arreglo[5] = inscripciones.get(i).getCar_nombre();
                tablaModelo.addRow(arreglo);
            }
        }
        tablaInsc.setModel(tablaModelo);
        inscripciones.clear();
    }

}
