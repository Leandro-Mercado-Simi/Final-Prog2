/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.CursadoDao;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloCursado {

    private int cur_alu_dni;
    private int cur_mat_cod;
    private double nota;
    private String cur_mat_nombre;
    private CursadoDao curDao = new CursadoDao();

    public ModeloCursado() {
    }

    public ModeloCursado(int cur_alu_dni, int cur_mat_cod, double nota) {
        this.cur_alu_dni = cur_alu_dni;
        this.cur_mat_cod = cur_mat_cod;
        this.nota = nota;
    }

    public String getCur_mat_nombre() {
        return cur_mat_nombre;
    }

    public void setCur_mat_nombre(String cur_mat_nombre) {
        this.cur_mat_nombre = cur_mat_nombre;
    }

    public int getCur_alu_dni() {
        return cur_alu_dni;
    }

    public void setCur_alu_dni(int cur_alu_dni) {
        this.cur_alu_dni = cur_alu_dni;
    }

    public int getCur_mat_cod() {
        return cur_mat_cod;
    }

    public void setCur_mat_cod(int cur_mat_cod) {
        this.cur_mat_cod = cur_mat_cod;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public boolean ValidarDNI(int dni) {
        boolean esNumerico = true;
        try {
            String aux = String.valueOf(dni);
            if (aux.length() > 8 || aux.length() < 7) {
                esNumerico = false;
                //acá va un return porque yo lo digo
            }
            for (int i = 0; i < aux.length(); i++) {
                if (Character.isDigit(aux.charAt(i))) {
                    esNumerico = true;
                } else {
                    esNumerico = false;
                    break;
                }
            }

            if (esNumerico) {
                curDao.VerificarDniAlumno(this.cur_alu_dni);
            } else {
                return false;
            }

        } catch (Exception ex) {

        }
        return curDao.VerificarDniAlumno(this.cur_alu_dni);
    }

    public boolean ValidarCodigo(int codigo) {
        boolean codValido = true;
        try {
            String codAux = String.valueOf(codigo);
            for (int i = 0; i < codAux.length(); i++) {
                if (Character.isDigit(codAux.charAt(i))) {
                    codValido = true;
                } else {
                    codValido = false;
                    break;
                }
            }
            if (codValido) {
                curDao.VerifCod(this.cur_mat_cod);
            } else {
                return false;
            }

        } catch (Exception ex) {

        }
        return curDao.VerifCod(this.cur_mat_cod);
    }

    public boolean ValidarNota(double nota) {
        boolean notaValida = true;
        if (nota < 0 || nota > 10) {
            notaValida = false;
        } else {
            notaValida = true;
        }
        return notaValida;
    }

    public void Cargar(ModeloCursado cursado) {
        curDao.Agregar(cursado);
    }

    public String[] Buscar(ModeloCursado cursado) {
        return curDao.Buscar(cursado);
    }

    public void Modificar(ModeloCursado cursado) {
        curDao.Modificar(cursado);
    }

    public void Quitar(ModeloCursado cursado) {
        curDao.Quitar(cursado);
    }

    public void Refresh(JTable tablaCursado) {
        ArrayList<ModeloCursado> cursos = curDao.Consulta();
        DefaultTableModel tablaModelo = (DefaultTableModel) tablaCursado.getModel();
        for (int i = tablaModelo.getRowCount(); i > 0; i--) {
            tablaModelo.removeRow(i - 1);
        }
        Object[] arreglo = new Object[4];
        if (cursos.size() > 0) {
            for (int i = 0; i < cursos.size(); i++) {
                arreglo[0] = cursos.get(i).getCur_alu_dni();
                arreglo[1] = cursos.get(i).getCur_mat_cod();
                arreglo[2] = cursos.get(i).getCur_mat_nombre();
                arreglo[3] = cursos.get(i).getNota();
                tablaModelo.addRow(arreglo);
            }
        }
        tablaCursado.setModel(tablaModelo);
        cursos.clear();
    }

}
