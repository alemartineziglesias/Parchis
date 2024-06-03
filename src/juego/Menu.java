package juego;


import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

public class Menu extends Frame implements WindowListener, ActionListener 
{
	private static final long serialVersionUID = 1L;

	// Vista (View) components
	Image imagen;
	Toolkit herramienta;
	Button jugar = new Button("Jugar");
	Button ayuda = new Button("Ayuda");
	Button estadisticas = new Button("Estadísticas");
	Dialog jugadores = new Dialog(this, "Elige el número de jugadores", true);
	Label lblJugadores = new Label("Elige el número de jugadores:");
	Choice choJugadores = new Choice();
	Button aceptar = new Button("Aceptar");
	Frame detallesJugadores = new Frame("Detalles de Jugadores");
	Button comenzar = new Button("Empezar");
	Frame partida = new Frame("Partida");
	Image tablero;

	public Menu() 
	{
		setBackground(Color.blue);
		setLocation(550, 250);
		setSize(300, 225);
		setTitle("Parchís");
		setResizable(false);
		setLayout(new BorderLayout());

		herramienta = getToolkit();
		imagen = herramienta.getImage("logo.png");
		tablero = herramienta.getImage("tablero.jpg");

		Panel panelBotones = new Panel();
		panelBotones.setLayout(new FlowLayout());
		Panel botonesVertical = new Panel();
		botonesVertical.setLayout(new BoxLayout(botonesVertical, BoxLayout.Y_AXIS));
		botonesVertical.add(jugar);
		botonesVertical.add(ayuda);
		botonesVertical.add(estadisticas);
		panelBotones.add(botonesVertical);
		add(panelBotones, BorderLayout.SOUTH);

		jugadores.setLayout(new GridLayout(4, 1));
		jugadores.setSize(200, 150);
		jugadores.setBackground(Color.blue);
		jugadores.add(lblJugadores);
		choJugadores.add("2 jugadores");
		choJugadores.add("3 jugadores");
		choJugadores.add("4 jugadores");
		jugadores.add(choJugadores);
		jugadores.add(aceptar);

		addWindowListener(this);
		jugadores.addWindowListener(this);
		detallesJugadores.addWindowListener(this);
		jugar.addActionListener(this);
		aceptar.addActionListener(this);
		comenzar.addActionListener(this);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Menu();
	}

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.drawImage(imagen, 55, 15, this);
		g.drawImage(tablero, 0, 0, partida.getWidth(), partida.getHeight(), partida);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jugar)) 
		{
			jugadores.setLocationRelativeTo(this);
			jugadores.setVisible(true);
		} 
		else if (e.getSource().equals(aceptar)) 
		{
			int numJugadores = choJugadores.getSelectedIndex() + 2;;
			jugadores.setVisible(false);
			detallesJugadores.removeAll();
			detallesJugadores.setLayout(new GridLayout(numJugadores + 1, 2));
			detallesJugadores.setSize(300, 50 * numJugadores);
			detallesJugadores.setBackground(Color.blue);
			detallesJugadores.setLocationRelativeTo(null);
			for (int i = 1; i <= numJugadores; i++) 
			{
				detallesJugadores.add(new Label("Nombre del jugador " + i + ":"));
				detallesJugadores.add(new TextField());
			}
			detallesJugadores.add(comenzar);
			detallesJugadores.setVisible(true);
		}
		else if(e.getSource().equals(comenzar))
		{
		    detallesJugadores.setVisible(false);
		    this.setVisible(false);
		    partida.setLayout(new BorderLayout()); // Establece el layout del Frame partida como BorderLayout
		    partida.setVisible(true);
		    partida.setSize(1200, 838);
		    partida.setLocationRelativeTo(null);

		    Panel tableroPanel = new Panel() 
		    {
		        private static final long serialVersionUID = 1L;

		        @Override
		        public void paint(Graphics g) {
		            super.paint(g);
		            g.drawImage(tablero, 0, 0, this);
		        }
		    };
		    tableroPanel.setPreferredSize(new Dimension(800, 920));
		    partida.add(tableroPanel, BorderLayout.EAST); // Agrega el panel del tablero a la región este del BorderLayout
		    partida.addWindowListener(this);
		    partida.setBackground(Color.blue);
		}

	}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (jugadores.isActive()) 
		{
			jugadores.setVisible(false);
		} 
		else if(partida.isActive())
		{
			partida.setVisible(false);
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