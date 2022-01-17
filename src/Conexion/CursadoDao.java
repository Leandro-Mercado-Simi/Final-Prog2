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
public class CursadoDao {

    private ModeloCursado cursado;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloCursado> Consulta() {
        ArrayList<ModeloCursado> cursos = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            try (PreparedStatement consulta = conexion.prepareStatement("SELECT cursado.cur_alu_dni, cursado.cur_mat_cod, cursado.cur_nota, materia.mat_nombre FROM cursado, materia WHERE materia.mat_cod = cursado.cur_mat_cod")) {
                rs = consulta.executeQuery();
                while (rs.next()) {
                    ModeloCursado plantilla = new ModeloCursado();
                    plantilla.setCur_alu_dni(rs.getInt("cur_alu_dni"));
                    plantilla.setCur_mat_cod(rs.getInt("cur_mat_cod"));
                    plantilla.setNota(rs.getDouble("cur_nota"));
                    plantilla.setCur_mat_nombre(rs.getString("mat_nombre"));
                    cursos.add(plantilla);
                }
            }
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cursos;
    }

    public boolean VerificarDniAlumno(int dniRecibido) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT * FROM alumno WHERE alu_dni = ?";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, dniRecibido);
            ResultSet rs = prstmt.executeQuery();
            if (rs.next()) {
                prstmt.close();
                conexion.close();
                rs.close();
                return true;
            }

        } catch (ClassNotFoundException | SQLException ex) {

        }
        return false;

    }

    public boolean VerifCod(int codigo) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT * FROM materia WHERE mat_cod = ?";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, codigo);
            ResultSet rs = prstmt.executeQuery();
            if (rs.next()) {
                prstmt.close();
                conexion.close();
                rs.close();
                return true;
            }

        } catch (ClassNotFoundException | SQLException ex) {

        }
        return false;
    }

    public boolean Agregar(ModeloCursado cursado) {
        boolean existe = false;
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT * FROM cursado WHERE cur_alu_dni = ? AND cur_mat_cod=?";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, cursado.getCur_alu_dni());
            prstmt.setInt(2, cursado.getCur_mat_cod());
            ResultSet rs = prstmt.executeQuery();
            if (rs.next()) {
                existe = true;
                prstmt.close();
                conexion.close();
                rs.close();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            existe = false;
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        try {
            if (!existe) {
                Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
                PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
                prstmt.executeQuery();
                String sql = "INSERT INTO cursado (cur_alu_dni, cur_mat_cod, cur_nota) VALUES (?,?,?)";
                prstmt = (PreparedStatement) conexion.prepareStatement(sql);
                prstmt.setInt(1, cursado.getCur_alu_dni());
                prstmt.setInt(2, cursado.getCur_mat_cod());
                prstmt.setDouble(3, cursado.getNota());
                prstmt.execute();
                prstmt.close();
                nuevaCon.desconectar();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    public String[] Buscar(ModeloCursado cursado) {
        String arreglo[] = new String[3];
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT alu_nombre, alu_apellido FROM alumno WHERE alu_dni=?";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, cursado.getCur_alu_dni());
            ResultSet rs = prstmt.executeQuery();
            if (rs.next()) {
                arreglo[0] = rs.getString(1);
                arreglo[1] = rs.getString(2);
            }

            String query = "SELECT mat_nombre FROM materia WHERE mat_cod=?";
            prstmt = (PreparedStatement) conexion.prepareStatement(query);
            prstmt.setInt(1, cursado.getCur_mat_cod());
            rs = prstmt.executeQuery();
            if (rs.next()) {
                arreglo[2] = rs.getString(1);
            }
            prstmt.close();
            conexion.close();
            rs.close();
            return arreglo;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arreglo;
    }

    public boolean Modificar(ModeloCursado cursado) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = ("UPDATE cursado SET cur_nota=? WHERE cur_alu_dni=? AND cur_mat_cod=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setDouble(1, cursado.getNota());
            prstmt.setInt(2, cursado.getCur_alu_dni());
            prstmt.setInt(3, cursado.getCur_mat_cod());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean Quitar(ModeloCursado cursado) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM cursado WHERE cur_alu_dni=? AND cur_mat_cod=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, cursado.getCur_alu_dni());
            prstmt.setInt(2, cursado.getCur_mat_cod());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
