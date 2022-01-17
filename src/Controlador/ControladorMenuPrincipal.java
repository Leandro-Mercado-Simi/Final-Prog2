/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.VistaMenuPrincipal;
import Modelo.*;
import Vista.VistaAlumno;
import Vista.VistaCarrera;
import Vista.VistaCursado;
import Vista.VistaInscripcion;
import Vista.VistaMateria;
import Vista.VistaProfesor;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ControladorMenuPrincipal implements ActionListener {

    public VistaMenuPrincipal menuPrincipal;

    public ControladorMenuPrincipal(VistaMenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        menuPrincipal.setVisible(true);
        escucharBotones();
    }

    public void escucharBotones() {
        this.menuPrincipal.jB_Alumno.addActionListener(this);
        this.menuPrincipal.jB_profesor.addActionListener(this);
        this.menuPrincipal.jB_materia.addActionListener(this);
        this.menuPrincipal.jB_Inscripcion.addActionListener(this);
        this.menuPrincipal.jB_Cursado.addActionListener(this);
        this.menuPrincipal.jB_Carreras.addActionListener(this);
        this.menuPrincipal.jB_salir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuPrincipal.jB_Alumno)) {
            Modelo.ModeloAlumno modAlu = new ModeloAlumno();
//            modAlu.llego();
            Vista.VistaAlumno aluVista = new VistaAlumno();
            Controlador.ControladorAlumno contAlu = new ControladorAlumno(modAlu, aluVista);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_profesor)) {
            Modelo.ModeloProfesor modProf = new ModeloProfesor();
            Vista.VistaProfesor vistaProf = new VistaProfesor();
            Controlador.ControladorProfesor contProf = new ControladorProfesor(modProf, vistaProf);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_materia)) {
            Modelo.ModeloMateria modMat = new ModeloMateria();
            Vista.VistaMateria vistaMat = new VistaMateria();
            Controlador.ControladorMateria contNot = new ControladorMateria(modMat, vistaMat);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_Cursado)) {
            Modelo.ModeloCursado modCur = new ModeloCursado();
            Vista.VistaCursado vistaCur = new VistaCursado();
            Controlador.ControladorCursado contCur = new ControladorCursado(modCur, vistaCur);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_Carreras)) {
            Modelo.ModeloCarrera modCar = new ModeloCarrera();
            Vista.VistaCarrera vistaCar = new VistaCarrera();
            Controlador.ControladorCarrera contCar = new ControladorCarrera(modCar, vistaCar);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_Inscripcion)) {
            Modelo.ModeloInscripcion modInsc = new ModeloInscripcion();
            Vista.VistaInscripcion vistaInsc = new VistaInscripcion();
            Controlador.ControladorInscripcion contInsc = new ControladorInscripcion(modInsc, vistaInsc);
            menuPrincipal.setVisible(false);

        } else if (e.getSource().equals(menuPrincipal.jB_salir)) {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el sistema?", "Salir", JOptionPane.YES_NO_OPTION);
            if (eleccion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

}
