/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.AlumnoDAO;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;

/**
 * Me falta parsear los datos que tengan que ser números para poder agregarlos a
 * la base de datos
 *
 * @author Usuario
 */
public class ModeloAlumno {

    private String dni;
    private String nombre;
    private String apellido;
    private Date fec_nac;
    private String domicilio;
    private String telefono;
    private int insc_cod;
    private AlumnoDAO alu = new AlumnoDAO();

    public ModeloAlumno() {

    }

    public ModeloAlumno(String dni, String nombre, String apellido, Date fec_nac, String domicilio, String telefono, int insc_cod) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fec_nac = fec_nac;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.insc_cod = insc_cod;
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

    public int getInsc_cod() {
        return insc_cod;
    }

    public void setInsc_cod(int insc_cod) {
        this.insc_cod = insc_cod;
    }

//    public void llego(){
//        JOptionPane.showMessageDialog(null, "Acá hay 1 alumno");
//    }
    public boolean DNIValido(String i) {
        //i = dni;
        boolean boolDni = false;
        try{
        if (i.length() > 8 || i.length() < 7) {
            return boolDni;
        }
        for (int j = 0; j < i.length(); j++) {
            if (Character.isDigit(i.charAt(j))) {
                boolDni = true;

            } else {
                boolDni = false;
                break;
            }
        }
        Integer.parseInt(dni);
        }catch(NullPointerException e){
            //System.out.println("Estoy en el catch dnivalido");
        }
//        System.out.println("Valor del boolean = " + boolDni);
        return boolDni;
    }

    public boolean NombreValido(String nom) {
        boolean nomValido = false;
        if (nom.length() == 0) {
//            JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido");
            return nomValido;
        } else {
            for (int i = 0; i < nom.length(); i++) {
                if (Character.isLetter(nom.charAt(i))) {
                    nomValido = true;
                } else {
//                    JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido");
                }
            }
        }
        return nomValido;
    }

    public boolean ApellidoValido(String ape) {
        boolean apeValido = false;
        if (ape.length() == 0) {
//            JOptionPane.showMessageDialog(null, "El apellido ingresado no es válido");
            return apeValido;
        } else {
            for (int i = 0; i < ape.length(); i++) {
                if (Character.isLetter(ape.charAt(i))) {
                    apeValido = true;
                } else {
//                    JOptionPane.showMessageDialog(null, "El apellido ingresado no es válido");
                }
            }
        }
        return apeValido;
    }

    public boolean DomValido(String dom) {
        boolean direccionValida;
        if (dom.length() == 0) {
//            JOptionPane.showMessageDialog(null, "La dirección ingresada no es válida");
            direccionValida = false;
            return direccionValida;
        } else {
            direccionValida = true;
        }
        return direccionValida;
    }

    public boolean validarTel(String tel) {
        boolean telValido = false;
        if (tel.length() <= 0 || tel.length() > 10) {
//            JOptionPane.showMessageDialog(null, "El teléfono ingresado no es válido");
            return telValido;
        } else {
            for (int i = 0; i < tel.length(); i++) {
                if (Character.isDigit(tel.charAt(i))) {
                    telValido = true;
                } else {
                    telValido = false;
//                    JOptionPane.showMessageDialog(null, "El teléfono ingresado no es válido");
                    break;
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
    
    public boolean dniRep(ModeloAlumno alumno){
        //System.out.println("REPETIDO: " + alu.dniRepetidoDAO(alumno));
        return alu.dniRepetidoDAO(alumno);
    }

    public boolean Carga() {
        boolean cargaValida;
        if ((DNIValido(dni) == true) && (NombreValido(nombre) == true) && (ApellidoValido(apellido) == true) && (DomValido(domicilio) == true) && (validarTel(telefono) == true) && (validarFec(fec_nac) == true)) {
            cargaValida = true;
        } else {
            cargaValida = false;
        }

        return cargaValida;
    }

    public boolean NuevoAlumno(ModeloAlumno alumno) {

        if (alu.Nuevo(alumno)) {
            return true;
        } else {
            return false;
        }
    }

    public void Refresh(JTable tablaAlumno) {
        ArrayList<ModeloAlumno> alumnos = alu.consultarTabla();
        DefaultTableModel tablaModelo = (DefaultTableModel) tablaAlumno.getModel();
        for (int i = tablaModelo.getRowCount(); i > 0; i--) {
            tablaModelo.removeRow(i - 1);
        }
        Object[] arreglo = new Object[6];
        if (alumnos.size() > 0) {
            for (int i = 0; i < alumnos.size(); i++) {
                arreglo[0] = alumnos.get(i).getDni();
                arreglo[1] = alumnos.get(i).getNombre();
                arreglo[2] = alumnos.get(i).getApellido();
                arreglo[3] = alumnos.get(i).getFec_nac();
                arreglo[4] = alumnos.get(i).getDomicilio();
                arreglo[5] = alumnos.get(i).getTelefono();
                tablaModelo.addRow(arreglo);
            }
        }
        tablaAlumno.setModel(tablaModelo);
        alumnos.clear();
    }

    public boolean ModificarAlumno(ModeloAlumno alumno) {
        if (alu.Modificar(alumno) == true) {
            return true;
        } else {
            return false;
        }

    }
    
    public void QuitarAlumno(int dni){
        alu.Quitar(dni);
    }

}
