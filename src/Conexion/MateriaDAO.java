/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Modelo.*;
import java.sql.Connection;
//import java.sql.Date;
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
public class MateriaDAO {

    private ModeloMateria materia;
    private NuevaConexion nuevaCon = new NuevaConexion();

    public ArrayList<ModeloMateria> Consutlar() {
        ArrayList<ModeloMateria> materias = new ArrayList<>();
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            ResultSet rs;
            try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM materia")) {
                rs = consulta.executeQuery();
                while (rs.next()) {
                    ModeloMateria plantilla = new ModeloMateria();
                    plantilla.setMat_cod(rs.getInt("mat_cod"));
                    plantilla.setMat_nombre(rs.getString("mat_nombre"));
                    plantilla.setProf_dni(rs.getInt("mat_prof_dni"));
                    materias.add(plantilla);
                }
            }
            nuevaCon.desconectar(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return materias;
    }

    public boolean VerificarProfesor(int dniRecibido) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "SELECT * FROM profesor WHERE prof_dni = ?";
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

    public boolean Nueva(ModeloMateria materia) {

        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = "INSERT INTO materia (mat_cod, mat_nombre, mat_prof_dni) VALUES (?,?,?)";
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, materia.getMat_cod());
            prstmt.setString(2, materia.getMat_nombre());
            prstmt.setString(3, String.valueOf(materia.getProf_dni()));
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public boolean ModificarMateria(ModeloMateria materia) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            PreparedStatement prstmt = conexion.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            prstmt.executeQuery();
            String sql = ("UPDATE materia SET mat_cod =?, mat_nombre=?, mat_prof_dni=? WHERE mat_cod=?");
            prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, materia.getMat_cod());
            prstmt.setString(2, materia.getMat_nombre());
            prstmt.setString(3, String.valueOf(materia.getProf_dni()));
            prstmt.setInt(4, materia.getMat_cod());
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean QuitarMateria(int codigo) {
        try {
            Connection conexion = nuevaCon.conectar("localhost", "final_prog2", "root", "root");
            String sql = ("DELETE FROM materia WHERE mat_cod=?");
            PreparedStatement prstmt = (PreparedStatement) conexion.prepareStatement(sql);
            prstmt.setInt(1, codigo);
            prstmt.execute();
            prstmt.close();
            nuevaCon.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
