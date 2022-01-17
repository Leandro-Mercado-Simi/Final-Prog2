/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.*;

/**
 *
 * @author Usuario
 */
public class NuevaConexion {

    public Connection conn = null;

    public Connection conectar(String servidor, String bd, String usuario, String password) throws ClassNotFoundException, SQLException {
        //com.mysql.cj.jdbc.Driver - com.mysql.jdbc.Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + bd, usuario, password);
            return this.conn;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    public void desconectar() throws SQLException {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (Exception ex) {
            }
        }
    }

    public void desconectar(ResultSet rs) throws SQLException {
        this.desconectar();
        rs.close();
    }
}
