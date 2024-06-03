package juego;

import java.awt.event.*;

public class Controlador implements WindowListener, ActionListener {
    private Modelo model;
    private Vista view;

    public Controlador(Modelo model, Vista view) {
        this.model = model;
        this.view = view;
        this.view.addMenuListeners(this, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.jugar)) {
            view.showJugadorDialog();
        } else if (e.getSource().equals(view.aceptar)) {
            view.hideJugadorDialog();
            int numJugadores = view.getNumeroDeJugadores();
            model.setNumeroDeJugadores(numJugadores);
            view.showDetallesJugadores(numJugadores);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (e.getSource().equals(view.jugadores) && view.jugadores.isActive()) {
            view.hideJugadorDialog();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) { }
    @Override
    public void windowClosed(WindowEvent e) { }
    @Override
    public void windowDeactivated(WindowEvent e) { }
    @Override
    public void windowDeiconified(WindowEvent e) { }
    @Override
    public void windowIconified(WindowEvent e) { }
    @Override
    public void windowOpened(WindowEvent e) { }

    public static void main(String[] args) {
        Modelo model = new Modelo();
        Vista view = new Vista();
        new Controlador(model, view);
    }
}