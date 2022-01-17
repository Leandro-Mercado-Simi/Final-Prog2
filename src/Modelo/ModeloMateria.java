/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.MateriaDAO;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloMateria {

    private int mat_cod;
    private String mat_nombre;
    private int prof_dni;
    private ModeloProfesor profe;
    private MateriaDAO matDAO = new MateriaDAO();

    public ModeloMateria() {
    }

    public ModeloMateria(int mat_cod, String mat_nombre, int prof_dni) {
        this.mat_cod = mat_cod;
        this.mat_nombre = mat_nombre;
        this.prof_dni = prof_dni;
    }

    public int getMat_cod() {
        return mat_cod;
    }

    public void setMat_cod(int mat_cod) {
        this.mat_cod = mat_cod;
    }

    public String getMat_nombre() {
        return mat_nombre;
    }

    public void setMat_nombre(String mat_nombre) {
        this.mat_nombre = mat_nombre;
    }

    public int getProf_dni() {
        return prof_dni;
    }

    public void setProf_dni(int prof_dni) {
        this.prof_dni = prof_dni;
    }

    public boolean ValidarCodigo(int cod) {
        boolean codValido = true;
        String aux = String.valueOf(cod);
        for (int i = 0; i < aux.length(); i++) {
            if (Character.isDefined(aux.charAt(i))) {
                codValido = true;
            } else {
                codValido = false;
                break;
            }
        }
        return codValido;
    }

    public boolean ValidarNombre(String nombreMat) {
        boolean nombreValido = true;
        for (int i = 0; i < nombreMat.length(); i++) {
            if (Character.isLetter(nombreMat.charAt(i)) || Character.isDigit(nombreMat.charAt(i))) {
                nombreValido = true;
            } else {
                nombreValido = false;
                break;
            }
        }
        return nombreValido;
    }

    public boolean ValidarDniProf(int dniProfesor, ModeloMateria materia) {

        if (matDAO.VerificarProfesor(dniProfesor)) {
            matDAO.Nueva(materia);
            return true;
        } else {
            return false;
        }

    }

    public boolean ModificarMateria(ModeloMateria materia) {
        
        return matDAO.ModificarMateria(materia);
        
    }
    
    public void Quitar(int cod){
        
        matDAO.QuitarMateria(cod);
    }

    public void RefrescarDatos(JTable tabMat) {
        ArrayList<ModeloMateria> materias = matDAO.Consutlar();
        DefaultTableModel tablaMod = (DefaultTableModel) tabMat.getModel();
        for (int i = tablaMod.getRowCount(); i > 0; i--) {
            tablaMod.removeRow(i - 1);
        }
        Object[] columnas = new Object[3];
        if (materias.size() > 0) {
            for (int i = 0; i < materias.size(); i++) {
                columnas[0] = materias.get(i).getMat_cod();
                columnas[1] = materias.get(i).getMat_nombre();
                columnas[2] = materias.get(i).getProf_dni();
                tablaMod.addRow(columnas);
            }
        }
        tabMat.setModel(tablaMod);
        materias.clear();
    }

}
