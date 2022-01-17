/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Modelo.ModeloAlumno;
import java.sql.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class AlumnoDAO {

    private Modelo.ModeloAlumno alumno;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloAlumno> consultarTabla() {
        ArrayList<Modelo.ModeloAlumno> alumnos = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM alumno");
            rs = consulta.executeQuery();
            while (rs.next()) {
                ModeloAlumno plantilla = new ModeloAlumno();
                plantilla.setDni(String.valueOf(rs.getInt("alu_dni")));
                plantilla.setNombre(rs.getString("alu_nombre"));
                plantilla.setApellido(rs.getString("alu_apellido"));
                plantilla.setFec_nac(rs.getDate("alu_fec_nac"));
                plantilla.setDomicilio(rs.getString("alu_domicilio"));
                plantilla.setTelefono(rs.getString("alu_telefono"));
                alumnos.add(plantilla);
            }
            consulta.close();
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return alumnos;
    }

    public boolean Nuevo(ModeloAlumno alumno) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "INSERT INTO alumno (alu_dni, alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono) VALUES (?,?,?,?,?,?)";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, Integer.parseInt(alumno.getDni()));
            prstmt.setString(2, alumno.getNombre());
            prstmt.setString(3, alumno.getApellido());
            prstmt.setDate(4, alumno.getFec_nac());
            prstmt.setString(5, alumno.getDomicilio());
            prstmt.setString(6, alumno.getTelefono());
            prstmt.execute();
            System.out.println("Entré alumnoDAO");
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean Modificar(ModeloAlumno alumno) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            //"UPDATE alumno SET (alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono, alu_insc_cod WHERE alu_dni = ?) VALUES (?,?,?,?,?,?,0)"
            String sql = ("UPDATE alumno SET alu_dni=?, alu_nombre=?, alu_apellido=?, alu_fec_nac = ?, alu_domicilio = ?, alu_telefono = ? WHERE alu_dni=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, Integer.parseInt(alumno.getDni()));
            prstmt.setString(2, alumno.getNombre());
            prstmt.setString(3, alumno.getApellido());
            System.out.println("Alumno: " + alumno.getFec_nac());
            prstmt.setDate(4, alumno.getFec_nac());
            prstmt.setString(5, alumno.getDomicilio());
            prstmt.setString(6, alumno.getTelefono());
            prstmt.setInt(7, Integer.parseInt(alumno.getDni()));
            prstmt.execute();
            System.out.println("Entré alumnoDAO");
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean Quitar(int dni) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM alumno WHERE alu_dni=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            System.out.println("DNI ALUMNO DAO = " + dni);
            prstmt.setInt(1, dni);
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean dniRepetidoDAO(ModeloAlumno alumno) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = ("SELECT * FROM alumno WHERE alu_dni=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setString(1, alumno.getDni());
            ResultSet rs = prstmt.executeQuery();
            if (rs.next()) {
                prstmt.close();
                nuevaCon.desconectar(rs);
                return true;
            }
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
