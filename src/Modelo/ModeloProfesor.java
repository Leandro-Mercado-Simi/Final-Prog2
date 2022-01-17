/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.ProfesorDAO;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloProfesor {

    private String dni;
    private String nombre;
    private String apellido;
    private Date fec_nac;
    private String domicilio;
    private String telefono;
    private ProfesorDAO profDAO = new ProfesorDAO();

    public ModeloProfesor() {

    }

    public ModeloProfesor(String dni, String nombre, String apellido, Date fec_nac, String domicilio, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fec_nac = fec_nac;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(Date fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean validarDni(String dniAux) {
        boolean dniValido = true;
        if (dniAux.length() < 7 || dniAux.length() > 8) {
            dniValido = false;
            return dniValido;
        } else {
            for (int i = 0; i < dniAux.length(); i++) {
                if (Character.isDigit(dniAux.charAt(i))) {
                    dniValido = true;
                } else {
                    dniValido = false;
                    break;
                }
            }
        }
        return dniValido;
    }

    public boolean validarNombre(String nom) {
        boolean nomValido = true;
        if (nom.length() == 0) {
            nomValido = false;
            return nomValido;
        } else {
            for (int i = 0; i < nom.length(); i++) {
                if (Character.isLetter(nom.charAt(i))) {
                    nomValido = true;
                } else {
                    nomValido = false;
                }
            }
        }
        return nomValido;
    }

    public boolean validarApellido(String ape) {
        boolean apellidoValido = true;
        if (ape.length() == 0) {
            apellidoValido = false;
            return apellidoValido;
        } else {
            for (int i = 0; i < ape.length(); i++) {
                if (Character.isLetter(ape.charAt(i))) {
                    apellidoValido = true;
                } else {
                    apellidoValido = false;
                }
            }
        }
        return apellidoValido;
    }

    public boolean validarDomicilio(String dir) {
        boolean dirValida = true;
        if (dir.length() == 0) {
            dirValida = false;
            return dirValida;
        } else {
            dirValida = true;
        }
        return dirValida;
    }

    public boolean validarTel(String tel) {
        boolean telValido = true;
        if (tel.length() <= 0 || tel.length() > 10) {
            telValido = false;
            return telValido;
        } else {
            for (int i = 0; i < tel.length(); i++) {
                if (Character.isDigit(tel.charAt(i))) {
                    telValido = true;
                } else {
                    telValido = false;
                }
            }
        }
        return telValido;
    }

    public boolean validarFec(Date fec) {
        System.out.println("TXT = " + fec_nac);
        boolean fecValida = true;
        return fecValida;
    }

    public boolean ValidarDatos() {
        boolean datosValidos = true;
        if ((validarDni(dni) == true) && (validarNombre(nombre) == true) && (validarApellido(apellido) == true) && (validarDomicilio(domicilio) == true) && (validarTel(telefono) == true) && (validarFec(fec_nac) == true)) {
            datosValidos = true;
            return datosValidos;
        } else {
            datosValidos = false;
            return datosValidos;
        }
    }

    public boolean NuevoProf(ModeloProfesor profesor) {
        if (profDAO.Nuevo(profesor)) {
            return true;
        } else {
            return false;
        }
    }

    public void Refrescar(JTable tablaProf) {
        ArrayList<ModeloProfesor> profesores = profDAO.ConsultarTabla();
        DefaultTableModel tablaModelo = (DefaultTableModel) tablaProf.getModel();
        for (int i = tablaModelo.getRowCount(); i > 0; i--) {
            tablaModelo.removeRow(i - 1);
        }
        Object[] arreglo = new Object[6];
        if (profesores.size() > 0) {
            for (int i = 0; i < profesores.size(); i++) {
                arreglo[0] = profesores.get(i).getDni();
                arreglo[1] = profesores.get(i).getNombre();
                arreglo[2] = profesores.get(i).getApellido();
                arreglo[3] = profesores.get(i).getFec_nac();
                arreglo[4] = profesores.get(i).getDomicilio();
                arreglo[5] = profesores.get(i).getTelefono();
                tablaModelo.addRow(arreglo);
            }
        }
        tablaProf.setModel(tablaModelo);
        profesores.clear();
    }

    public boolean ModificarProf(ModeloProfesor profesor) {
        if (profDAO.Modificar(profesor)) {
            return true;
        } else {
            return false;
        }
    }
    
    public void QuitarProfesor(int dni){
        profDAO.Quitar(dni);
    }
}
