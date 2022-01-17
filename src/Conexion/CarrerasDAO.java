/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class CarrerasDAO {

    private ModeloCarrera modCar;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloCarrera> Consultar() {
        ArrayList<ModeloCarrera> carreras = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM carrera")) {
                rs = consulta.executeQuery();
                while (rs.next()) {
                    ModeloCarrera plantilla = new ModeloCarrera();
                    plantilla.setCar_cod(rs.getInt("car_cod"));
                    plantilla.setCar_nombre(rs.getString("car_nombre"));
                    plantilla.setDuracion(rs.getString("car_duracion"));
                    carreras.add(plantilla);
                }
            }
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarrerasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return carreras;
    }

    public boolean Cargar(ModeloCarrera carrera) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "INSERT INTO carrera (car_cod, car_nombre, car_duracion) VALUES (?,?,?)";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, carrera.getCar_cod());
            prstmt.setString(2, carrera.getCar_nombre());
            prstmt.setString(3, carrera.getDuracion());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarrerasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean Modificar(ModeloCarrera carrera) {
        try {
            System.out.println("Modificar DAO " + carrera.getCar_nombre());
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = ("UPDATE carrera SET car_nombre=?, car_duracion=? WHERE car_cod=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setString(1, carrera.getCar_nombre());
            prstmt.setString(2, carrera.getDuracion());
            prstmt.setString(3, String.valueOf(carrera.getCar_cod()));
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarrerasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean QuitarCarrera(int cod) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM carrera WHERE car_cod=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, cod);
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarrerasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
