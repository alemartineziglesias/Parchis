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
	TextField nombreRojo = new TextField(20);
	TextField nombreAmarillo = new TextField(20);
	TextField nombreAzul = new TextField(20);
	TextField nombreVerde = new TextField(20);
	Button comenzar = new Button("Empezar");
	Frame partida = new Frame("Partida");
	Label jugadorRojo = new Label("Rojo: ");
	Label jugadorAzul = new Label("Azul: ");
	Label jugadorAmarillo = new Label("Amarillo: ");
	Label jugadorVerde = new Label("Verde: ");
	Image tablero;

	int numJugadores;

	public Menu() 
	{
		setBackground(Color.blue);
		setLocationRelativeTo(null);
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
			numJugadores = choJugadores.getSelectedIndex() + 2;;
			jugadores.setVisible(false);
			detallesJugadores.removeAll();
			detallesJugadores.setLayout(new GridLayout(numJugadores + 1, 2));
			detallesJugadores.setSize(400, 50 * numJugadores);
			detallesJugadores.setBackground(Color.blue);
			detallesJugadores.setLocationRelativeTo(null);
			for (int i = 1; i <= numJugadores; i++) 
			{
				if(i == 1)
				{
					detallesJugadores.add(new Label("Nombre del jugador rojo:"));
					detallesJugadores.add(nombreRojo);
				}
				else if(i == 2)
				{
					detallesJugadores.add(new Label("Nombre del jugador amarillo:"));
					detallesJugadores.add(nombreAmarillo);
				}
				else if(i == 3)
				{
					detallesJugadores.add(new Label("Nombre del jugador azul:"));
					detallesJugadores.add(nombreAzul);
				}
				else if(i == 4)
				{
					detallesJugadores.add(new Label("Nombre del jugador verde:"));
					detallesJugadores.add(nombreVerde);
				}
			}
			detallesJugadores.add(comenzar);
			detallesJugadores.setVisible(true);
		}
		else if (e.getSource().equals(comenzar)) {
			detallesJugadores.setVisible(false);
			this.setVisible(false);
			partida.setLayout(new BorderLayout()); // Cambiar el layout a BorderLayout
			partida.setVisible(true);
			partida.setSize(1200, 838);
			partida.setLocationRelativeTo(null);

			Panel tableroPanel = new Panel() {
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) {
					super.paint(g);
					g.drawImage(tablero, 0, 0, this);
				}
			};
			tableroPanel.setPreferredSize(new Dimension(800, 920));
			partida.add(tableroPanel, BorderLayout.EAST); // Agregar el tablero al este

			Panel jugadoresPanel = new Panel();
			jugadoresPanel.setLayout(new GridLayout(0, 1, 0, 50)); // Añadir un espacio vertical entre los componentes
			jugadorRojo.setText(jugadorRojo.getText() + nombreRojo.getText());
			jugadoresPanel.add(jugadorRojo);
			if (numJugadores >= 2) {
				jugadorAmarillo.setText(jugadorAmarillo.getText() + nombreAmarillo.getText());
				jugadoresPanel.add(jugadorAmarillo);
			}
			if (numJugadores >= 3) {
				jugadorAzul.setText(jugadorAzul.getText() + nombreAzul.getText());
				jugadoresPanel.add(jugadorAzul);
			}
			if (numJugadores >= 4) {
				jugadorVerde.setText(jugadorVerde.getText() + nombreVerde.getText());
				jugadoresPanel.add(jugadorVerde);
			}

			// Agregar un Panel vacío para el espacio
			Panel espacioInferior = new Panel();
			jugadoresPanel.add(espacioInferior);

			partida.add(jugadoresPanel, BorderLayout.WEST); // Agregar los jugadores al oeste

			partida.addWindowListener(this);
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