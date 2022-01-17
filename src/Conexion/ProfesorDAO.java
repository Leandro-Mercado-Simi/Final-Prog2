/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Modelo.ModeloAlumno;
import Modelo.ModeloProfesor;
import java.sql.Connection;
import java.sql.Date;
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
public class ProfesorDAO {

    private Modelo.ModeloProfesor profesor;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloProfesor> ConsultarTabla() {
        ArrayList<ModeloProfesor> profesores = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM profesor")) {
                rs = consulta.executeQuery();
                while (rs.next()) {
                    ModeloProfesor plantilla = new ModeloProfesor();
                    plantilla.setDni(String.valueOf(rs.getInt("prof_dni")));
                    plantilla.setNombre(rs.getString("prof_nombre"));
                    plantilla.setApellido(rs.getString("prof_apellido"));
                    plantilla.setFec_nac(rs.getDate("prof_fec_nac"));
                    plantilla.setDomicilio(rs.getString("prof_domicilio"));
                    plantilla.setTelefono(rs.getString("prof_telefono"));
                    profesores.add(plantilla);
                }
            }
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return profesores;
    }

    public boolean Nuevo(ModeloProfesor profesor) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "INSERT INTO profesor (prof_dni, prof_nombre, prof_apellido, prof_fec_nac, prof_domicilio, prof_telefono) VALUES (?,?,?,?,?,?)";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, Integer.parseInt(profesor.getDni()));
            prstmt.setString(2, profesor.getNombre());
            prstmt.setString(3, profesor.getApellido());
            prstmt.setDate(4, (Date) profesor.getFec_nac());
            prstmt.setString(5, profesor.getDomicilio());
            prstmt.setString(6, profesor.getTelefono());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean Modificar(ModeloProfesor profesor) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            //"UPDATE alumno SET (alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono, alu_insc_cod WHERE alu_dni = ?) VALUES (?,?,?,?,?,?,0)"
            String sql = ("UPDATE profesor SET prof_dni=?, prof_nombre=?, prof_apellido=?, prof_fec_nac = ?, prof_domicilio = ?, prof_telefono = ? WHERE prof_dni=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, Integer.parseInt(profesor.getDni()));
            prstmt.setString(2, profesor.getNombre());
            prstmt.setString(3, profesor.getApellido());
            prstmt.setDate(4, (Date) profesor.getFec_nac());
            prstmt.setString(5, profesor.getDomicilio());
            prstmt.setString(6, profesor.getTelefono());
            prstmt.setInt(7, Integer.parseInt(profesor.getDni()));
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean Quitar(int dni) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM profesor WHERE prof_dni=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, dni);
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
