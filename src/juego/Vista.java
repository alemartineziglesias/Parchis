package juego;

import java.awt.*;

import javax.swing.BoxLayout;

public class Vista extends Frame 
{
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
	Dialog dlgEstadisticas = new Dialog(this, "Estadísticas", true);
	TextArea listado = new TextArea(5, 40);
	Button volver = new Button("Volver");
	
	int numJugadores;

	public Vista() 
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
		

		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.drawImage(imagen, 55, 15, this);
		g.drawImage(tablero, 0, 0, partida.getWidth(), partida.getHeight(), partida);
	}
}