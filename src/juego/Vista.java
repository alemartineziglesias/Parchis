package juego;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
	Panel jugadoresPanel = new Panel();
	Panel tableroPanel;
	Image tablero;
	Image rojo;
	Image amarillo;
	Image azul;
	Image verde;
	Image dado;
	Label resultado = new Label("");
	Dialog dlgEstadisticas = new Dialog(this, "Estadísticas", true);
	TextArea listado = new TextArea(5, 40);
	Button volver = new Button("Volver");
	ImagePanel dadoPanel;
	
	int numJugadores;
	int anchuraRojo = 120;
	int alturaRojo = 120;
	int anchuraAzul = 120;
	int alturaAzul = 640;
	int anchuraAmarillo = 640;
	int alturaAmarillo = 640;
	int anchuraVerde = 640;
	int alturaVerde = 120;
	int contadorRojo = 0;
	int contadorAzul = 0;
	int contadorAmarillo = 0;
	int contadorVerde = 0;
	boolean salidaRojo = false;
	boolean salidaAzul = false;
	boolean salidaAmarillo = false;
	boolean salidaVerde = false;
	int metaRojo = 0;
	int metaAzul = 0;
	int metaAmarillo = 0;
	int metaVerde = 0;
	int turno = 1;

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
		herramienta = getToolkit();
		imagen = herramienta.getImage("logo.png");
		tablero = herramienta.getImage("tablero.jpg");
		rojo = herramienta.getImage("rojo.png");
		amarillo = herramienta.getImage("amarillo.png");
		azul = herramienta.getImage("azul.png");
		verde = herramienta.getImage("verde.png");

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
	}
	
	static class ImagePanel extends JPanel 
	{
		private static final long serialVersionUID = 1L;
		private BufferedImage image;
		private int ancho;
		private int alto;

		public ImagePanel(String imagePath, int ancho, int alto) 
		{
			try 
			{
				image = ImageIO.read(new File(imagePath));
				this.ancho = ancho;
				this.alto = alto;
				setPreferredSize(new Dimension(ancho, alto));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			if (image != null) 
			{
				int x = (this.getWidth() - ancho) / 2;
				int y = (this.getHeight() - alto) / 2;
				g.drawImage(image, x, y, ancho, alto, this);
			}
		}
	}
}