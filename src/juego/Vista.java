package juego;

import java.awt.*;

import javax.swing.BoxLayout;

public class Vista extends Frame {
	private static final long serialVersionUID = 1L;
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
	Frame juego = new Frame("Partida");
	Image tablero;
	
	int numJugadores;

	public Vista() 
	{
		setBackground(Color.blue);
		setLocation(550, 250);
		setSize(300, 225);
		setTitle("Parchís");
		setResizable(false);
		setLayout(new BorderLayout());
		herramienta = getToolkit();
		imagen = herramienta.getImage("logo.png");
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
		jugadores.setLocationRelativeTo(this);
		jugadores.setSize(200, 150);
		jugadores.setBackground(Color.blue);
		jugadores.add(lblJugadores);
		choJugadores.add("2 jugadores");
		choJugadores.add("3 jugadores");
		choJugadores.add("4 jugadores");
		jugadores.add(choJugadores);
		jugadores.add(aceptar);
		
		detallesJugadores.removeAll();
		detallesJugadores.setLayout(new GridLayout(numJugadores + 1, 2));
		detallesJugadores.setSize(300, 50 * numJugadores);
		detallesJugadores.setBackground(Color.blue);
		detallesJugadores.setLocationRelativeTo(null);
		detallesJugadores.add(comenzar);
		juego.setLocationRelativeTo(null);
		juego.setSize(800, 800);
		juego.setLayout(new FlowLayout());
		imagen = herramienta.getImage("tablero.jpg");
		
		setVisible(true);
	}

	

	public int getNumeroDeJugadores() 
	{
		return choJugadores.getSelectedIndex() + 2;
	}

	@Override
	public void paint(Graphics g) 
	{
		g.drawImage(imagen, 55, 15, this);
		g.drawImage(tablero, 55, 15, juego);
	}
}