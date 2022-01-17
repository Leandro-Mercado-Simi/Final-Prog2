/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Modelo.ModeloCursado;
import Modelo.ModeloInscripcion;
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
public class InscripcionDAO {

    private Modelo.ModeloInscripcion modInsc;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloInscripcion> Consulta() {
        ArrayList<ModeloInscripcion> inscripciones = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            PreparedStatement consulta = conexion.prepareStatement("SELECT inscripcion.insc_cod, inscripcion.insc_nombre, inscripcion.ins_fecha, inscripcion.ins_car_cod, alumno.alu_nombre, carrera.car_nombre FROM inscripcion, alumno, carrera WHERE carrera.car_cod = inscripcion.ins_car_cod AND alumno.alu_dni = inscripcion.insc_nombre");
            //PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM inscripcion");
            rs = consulta.executeQuery();
            while (rs.next()) {
                ModeloInscripcion plantilla = new ModeloInscripcion();
                plantilla.setInsc_cod(rs.getInt("insc_cod"));
                plantilla.setDni_alu(rs.getInt("insc_nombre"));
                plantilla.setInsc_fecha(rs.getDate("ins_fecha"));
                plantilla.setInsc_car_cod(rs.getInt("ins_car_cod"));
                plantilla.setAlu_nombre(rs.getString("alu_nombre"));
                plantilla.setCar_nombre(rs.getString("car_nombre"));
                inscripciones.add(plantilla);
            }
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inscripciones;
    }

    public boolean AgregarInsc(ModeloInscripcion inscripcion) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "INSERT INTO inscripcion (insc_nombre, ins_fecha, ins_car_cod) VALUES (?,?,?)";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, inscripcion.getDni_alu());
            prstmt.setDate(2, inscripcion.getInsc_fecha());
            prstmt.setInt(3, inscripcion.getInsc_car_cod());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public String[] Buscar(ModeloInscripcion inscripcion) {
        String arreglo[] = new String[3];
        try {
            System.out.println("Entré al try");
            System.out.println(inscripcion.getDni_alu());
            System.out.println(inscripcion.getInsc_car_cod());
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT alu_nombre, alu_apellido FROM alumno WHERE alu_dni=?";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setString(1, String.valueOf(inscripcion.getDni_alu()));
            ResultSet rs = prstmt.executeQuery();
            System.out.println("Antes del if");
            if (rs.next()) {
                System.out.println("Adentro del if");
                System.out.println(rs.getString(1));
                arreglo[0] = rs.getString(1);
                arreglo[1] = rs.getString(2);
            }

            String query = "SELECT car_nombre FROM carrera WHERE car_cod=?";
            prstmt = (PreparedStatement) conexion.prepareStatement(query);
            System.out.println(inscripcion.getInsc_car_cod());
            prstmt.setInt(1, inscripcion.getInsc_car_cod());
            rs = prstmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                arreglo[2] = rs.getString(1);
            }
            prstmt.close();
            conexion.close();
            rs.close();
            return arreglo;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arreglo;
    }

    public void Modificar(ModeloInscripcion inscripcion) {
        try {
            String sqlQuery = "UPDATE inscripcion SET insc_nombre=?, ins_fecha=?, ins_car_cod=? WHERE insc_cod=?";
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement preparedStmt = (PreparedStatement) conexion.prepareStatement(sqlQuery);

            preparedStmt.setString(1, String.valueOf(inscripcion.getDni_alu()));
            preparedStmt.setDate(2, inscripcion.getInsc_fecha());
            preparedStmt.setString(3, String.valueOf(inscripcion.getInsc_car_cod()));
            preparedStmt.setString(4, String.valueOf(inscripcion.getInsc_cod()));

            preparedStmt.executeUpdate();
            preparedStmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean Quitar(ModeloInscripcion inscripcion) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM inscripcion WHERE insc_cod=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setString(1, String.valueOf(inscripcion.getInsc_cod()));
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
