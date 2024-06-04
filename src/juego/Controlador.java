package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
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
		v.estadisticas.addActionListener(this);
		v.aceptar.addActionListener(this);
		v.comenzar.addActionListener(this);
		v.volver.addActionListener(this);
    }

    @Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(v.jugar)) 
		{
			v.jugadores.setLocationRelativeTo(null);
			v.jugadores.setVisible(true);
		} 
		else if (e.getSource().equals(v.aceptar)) 
		{
			v.numJugadores = v.choJugadores.getSelectedIndex() + 2;;
			v.jugadores.setVisible(false);
			v.detallesJugadores.removeAll();
			v.detallesJugadores.setLayout(new GridLayout(v.numJugadores + 1, 2));
			v.detallesJugadores.setSize(400, 50 * v.numJugadores);
			v.detallesJugadores.setBackground(Color.blue);
			v.detallesJugadores.setLocationRelativeTo(null);
			for (int i = 1; i <= v.numJugadores; i++) 
			{
				if(i == 1)
				{
					v.detallesJugadores.add(new Label("Nombre del jugador rojo:"));
					v.detallesJugadores.add(v.nombreRojo);
				}
				else if(i == 2)
				{
					v.detallesJugadores.add(new Label("Nombre del jugador amarillo:"));
					v.detallesJugadores.add(v.nombreAmarillo);
				}
				else if(i == 3)
				{
					v.detallesJugadores.add(new Label("Nombre del jugador azul:"));
					v.detallesJugadores.add(v.nombreAzul);
				}
				else if(i == 4)
				{
					v.detallesJugadores.add(new Label("Nombre del jugador verde:"));
					v.detallesJugadores.add(v.nombreVerde);
				}
			}
			v.detallesJugadores.add(v.comenzar);
			v.detallesJugadores.setVisible(true);
		}
		else if (e.getSource().equals(v.comenzar)) 
		{
			v.detallesJugadores.setVisible(false);
			this.v.setVisible(false);
			v.partida.setLayout(new BorderLayout());
			v.partida.setVisible(true);
			v.partida.setSize(1200, 838);
			v.partida.setLocationRelativeTo(null);

			Panel tableroPanel = new Panel() 
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) 
				{
					super.paint(g);
					g.drawImage(v.tablero, 0, 0, this);
				}
			};
			tableroPanel.setPreferredSize(new Dimension(800, 920));
			v.partida.add(tableroPanel, BorderLayout.EAST); // Agregar el tablero al este

			Panel jugadoresPanel = new Panel();
			jugadoresPanel.setLayout(new GridLayout(0, 1, 0, 50)); // Añadir un espacio vertical entre los componentes
			v.jugadorRojo.setText(v.jugadorRojo.getText() + v.nombreRojo.getText());
			jugadoresPanel.add(v.jugadorRojo);
			if (v.numJugadores >= 2) 
			{
				v.jugadorAmarillo.setText(v.jugadorAmarillo.getText() + v.nombreAmarillo.getText());
				jugadoresPanel.add(v.jugadorAmarillo);
			}
			if (v.numJugadores >= 3) 
			{
				v.jugadorAzul.setText(v.jugadorAzul.getText() + v.nombreAzul.getText());
				jugadoresPanel.add(v.jugadorAzul);
			}
			if (v.numJugadores >= 4) 
			{
				v.jugadorVerde.setText(v.jugadorVerde.getText() + v.nombreVerde.getText());
				jugadoresPanel.add(v.jugadorVerde);
			}
			Panel espacioInferior = new Panel();
			jugadoresPanel.add(espacioInferior);

			v.partida.add(jugadoresPanel, BorderLayout.WEST);

			v.partida.addWindowListener(this);
		}
		else if(e.getSource().equals(v.estadisticas))
		{
			v.dlgEstadisticas.setLayout(new FlowLayout());
			m.conectar();
			v.listado.append(m.dameJugadores());
			
			v.dlgEstadisticas.addWindowListener(this);
			v.dlgEstadisticas.add(v.listado);
			v.dlgEstadisticas.add(v.volver);
			v.volver.addActionListener(this);
			v.dlgEstadisticas.setSize(350, 200);
			v.dlgEstadisticas.setResizable(false);
			v.dlgEstadisticas.setBackground(Color.blue);
			v.dlgEstadisticas.setLocationRelativeTo(null);
			v.dlgEstadisticas.setVisible(true);
		}
		else if(e.getSource().equals(v.volver))
		{
			v.listado.setText("");
			v.dlgEstadisticas.setVisible(false);
			m.desconectar();
		}

	}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (v.jugadores.isActive()) 
		{
			v.jugadores.setVisible(false);
		} 
		else if(v.detallesJugadores.isActive())
		{
			v.detallesJugadores.setVisible(false);
		}
		else if(v.partida.isActive())
		{
			v.partida.setVisible(false);
			System.exit(0);
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