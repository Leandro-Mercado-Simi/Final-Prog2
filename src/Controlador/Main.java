/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaMenuPrincipal;

/**
 *
 * @author Usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaMenuPrincipal menuVentana = new VistaMenuPrincipal();
        ControladorMenuPrincipal contMenu = new ControladorMenuPrincipal(menuVentana);
    }
    
}
