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

	// Modelo (Model)
	private int numeroDeJugadores;

	public Menu() 
	{
		// Configuración de la vista (View)
		setBackground(Color.blue);
		setLocation(550, 250);
		setSize(300, 225);
		setTitle("Parchís");
		setResizable(false);
		setLayout(new BorderLayout());

		herramienta = getToolkit();
		imagen = herramienta.getImage("logo.png");
		imagen = herramienta.getImage("tablero.jpg");

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

		// Añadir listeners
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
	public void paint(Graphics g) {
		g.drawImage(imagen, 55, 15, this);
		g.drawImage(tablero, 55, 15, this.partida);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jugar)) {
			showJugadorDialog();
		} 
		else if (e.getSource().equals(aceptar)) 
		{
			hideJugadorDialog();
			int numJugadores = getNumeroDeJugadores();
			setNumeroDeJugadores(numJugadores);
			showDetallesJugadores(numJugadores);
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
		}
		else if(e.getSource().equals(comenzar))
		{
			detallesJugadores.setVisible(false);
			jugadores.setVisible(false);
			this.setVisible(false);
			partida.setVisible(true);
			partida.setSize(800, 800);
			partida.setLocationRelativeTo(null);
		}
	}

	// Métodos de la Vista (View)
	public void showJugadorDialog() 
	{
		jugadores.setLocationRelativeTo(this);
		jugadores.setVisible(true);
	}

	public void hideJugadorDialog() 
	{
		jugadores.setVisible(false);
	}

	public void showDetallesJugadores(int numJugadores) 
	{
		

		

		detallesJugadores.setVisible(true);
	}

	public int getNumeroDeJugadores() 
	{
		return choJugadores.getSelectedIndex() + 2;
	}

	// Métodos del Modelo (Model)
	public int getNumeroDeJugadoresModel() 
	{
		return numeroDeJugadores;
	}

	public void setNumeroDeJugadores(int numeroDeJugadores) 
	{
		this.numeroDeJugadores = numeroDeJugadores;
	}

	// Métodos de los listeners de ventana
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (jugadores.isActive()) 
		{
			hideJugadorDialog();
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