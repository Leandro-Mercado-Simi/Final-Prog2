/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Conexion.CarrerasDAO;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloCarrera {

    private int car_cod;
    private String car_nombre;
    private String duracion;
    private CarrerasDAO carDAO = new CarrerasDAO();

    public ModeloCarrera() {
    }

    public ModeloCarrera(int car_cod, String car_nombre, String duracion) {
        this.car_cod = car_cod;
        this.car_nombre = car_nombre;
        this.duracion = duracion;
    }

    public int getCar_cod() {
        return car_cod;
    }

    public void setCar_cod(int car_cod) {
        this.car_cod = car_cod;
    }

    public String getCar_nombre() {
        return car_nombre;
    }

    public void setCar_nombre(String car_nombre) {
        this.car_nombre = car_nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public boolean ValidarCod(int cod) {
        boolean valido = true;
        try {
            String codAux = String.valueOf(cod);
            for (int i = 0; i < codAux.length(); i++) {
                if (Character.isDigit(codAux.charAt(i))) {
                    valido = true;
                } else {
                    valido = false;
                    break;
                }
            }
        } catch (Exception ex) {

        }
        return valido;
    }

    public boolean ValidarNombre(String aux) {
        boolean nomValido = false;
        if (aux.length() <= 0) {
            return nomValido;
        } else {
            nomValido = true;
        }
        return nomValido;
    }

    public boolean ValidarDuracion(String dur) {
        boolean durValida = false;
        if (dur.length() <= 0 || dur.contains("años") == false) {
            return durValida;
        } else {
            durValida = true;
        }
        return durValida;
    }

    public void CargaDatos(ModeloCarrera modCar) {
        carDAO.Cargar(modCar);
    }

    public void ModificarDatos(ModeloCarrera modCar) {
        carDAO.Modificar(modCar);
    }

    public void QuitarCarrera(int codigo) {
        carDAO.QuitarCarrera(codigo);
    }

    public void Refresh(JTable tablaCarrera) {
        ArrayList<ModeloCarrera> carreras = carDAO.Consultar();
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaCarrera.getModel();
        for (int i = modeloTabla.getRowCount(); i > 0; i--) {
            modeloTabla.removeRow(i - 1);
        }
        Object[] array = new Object[3];
        if (carreras.size() > 0) {
            for (int i = 0; i < carreras.size(); i++) {
                array[0] = carreras.get(i).getCar_cod();
                array[1] = carreras.get(i).getCar_nombre();
                array[2] = carreras.get(i).getDuracion();
                modeloTabla.addRow(array);
            }
        }
        tablaCarrera.setModel(modeloTabla);
        carreras.clear();
    }

}
