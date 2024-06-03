package juego;

import java.awt.event.*;

public class Controlador implements WindowListener, ActionListener 
{
    private Modelo m;
    private Vista v;

    public Controlador(Modelo modelo, Vista vista) 
    {
        this.m = modelo;
        this.v = vista;
        v.addWindowListener(this);
		v.jugadores.addWindowListener(this);
		v.detallesJugadores.addWindowListener(this);
		v.jugar.addActionListener(this);
		v.aceptar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource().equals(v.jugar)) 
        {
        	v.jugadores.setVisible(true);
        } 
        else if (e.getSource().equals(v.aceptar)) 
        {
        	v.jugadores.setVisible(false);
            int numJugadores = v.getNumeroDeJugadores();
            m.setNumeroDeJugadores(numJugadores);
            m.showDetallesJugadores(numJugadores);
        }
        else if(e.getSource().equals(v.comenzar))
        {
        	
        }
    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
        if (v.jugadores.isActive()) 
        {
            v.jugadores.setVisible(false);
        } 
        else 
        {
            System.exit(0);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) 
    { 
    	
    }
    
    @Override
    public void windowClosed(WindowEvent e) 
    {
    	
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) 
    { 
    	
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) 
    {
    	
    }
    
    @Override
    public void windowIconified(WindowEvent e) 
    { 
    	
    }
    
    @Override
    public void windowOpened(WindowEvent e) 
    { 
    	
    }
}