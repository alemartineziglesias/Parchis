package juego;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Menu extends Frame implements WindowListener, ActionListener, MouseListener
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
	Panel tableroPanel;
	Image tablero;
	Panel jugadoresPanel = new Panel();
	Image rojo;
	Image amarillo;
	Image azul;
	Image verde;
	Image dado;
	Label resultado = new Label("");
	Dialog dlgEstadisticas = new Dialog(this, "Estadísticas", true);
	TextArea listado = new TextArea(5, 40);
	Button volver = new Button("Volver");
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/parchis";
	String login = "root";
	String password = "Mr9+agar";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	private ImagePanel dadoPanel;

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
		rojo = herramienta.getImage("rojo.png");
		amarillo = herramienta.getImage("amarillo.png");
		azul = herramienta.getImage("azul.png");
		verde = herramienta.getImage("verde.png");
		dado = herramienta.getImage("dado.png");

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
		estadisticas.addActionListener(this);
		aceptar.addActionListener(this);
		comenzar.addActionListener(this);
		volver.addActionListener(this);
		ayuda.addActionListener(this);


		setVisible(true);
	}

	class ImagePanel extends JPanel 
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

	public static void main(String[] args) 
	{
		new Menu();
	}

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		g.drawImage(imagen, 55, 15, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
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
		else if (e.getSource().equals(comenzar)) 
		{
			detallesJugadores.setVisible(false);
			this.setVisible(false);
			partida.setLayout(new BorderLayout());
			partida.setVisible(true);
			partida.setSize(1200, 838);
			partida.setLocationRelativeTo(null);

			tableroPanel = new Panel() 
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) 
				{
					super.paint(g);
					g.drawImage(tablero, 0, 0, this);
					g.drawImage(rojo, anchuraRojo, alturaRojo, 40, 40, this);
					g.drawImage(amarillo, anchuraAmarillo, alturaAmarillo, 40, 40, this);
					if(numJugadores == 3)
					{
						g.drawImage(azul, anchuraAzul, alturaAzul, 40, 40, this);
					}
					else if(numJugadores > 3)
					{
						g.drawImage(azul, anchuraAzul, alturaAzul, 40, 40, this);
						g.drawImage(verde, anchuraVerde, alturaVerde, 40, 40, this);
					}
				}
			};
			tableroPanel.setPreferredSize(new Dimension(900, 920));
			partida.add(tableroPanel, BorderLayout.EAST);

			jugadoresPanel.setPreferredSize(new Dimension(200, 800));
			jugadoresPanel.setLayout(new GridLayout(0, 1, 0, 50));
			jugadorRojo.setText(jugadorRojo.getText() + nombreRojo.getText());
			jugadoresPanel.add(jugadorRojo);
			if (numJugadores >= 2) 
			{
				jugadorAmarillo.setText(jugadorAmarillo.getText() + nombreAmarillo.getText());
				jugadoresPanel.add(jugadorAmarillo);
			}
			if (numJugadores >= 3) 
			{
				jugadorAzul.setText(jugadorAzul.getText() + nombreAzul.getText());
				jugadoresPanel.add(jugadorAzul);
			}
			if (numJugadores == 4) 
			{
				jugadorVerde.setText(jugadorVerde.getText() + nombreVerde.getText());
				jugadoresPanel.add(jugadorVerde);
			}
			int anchoDado = 0;
			int altoDado = 0;
			if(numJugadores == 2)
			{
				anchoDado = 170;
				altoDado = 170;
			}
			else if(numJugadores == 3)
			{
				anchoDado = 140;
				altoDado = 140;
			}
			else
			{
				anchoDado = 100;
				altoDado = 100;
			}
			dadoPanel = new ImagePanel("dado.png", anchoDado, altoDado);
			dadoPanel.setBackground(Color.white);
			dadoPanel.addMouseListener(this);
			jugadoresPanel.add(dadoPanel);
			if(numJugadores == 2)
			{
				resultado.setFont(new Font("Terminal", Font.PLAIN, 150));
			}
			else if(numJugadores == 3)
			{
				resultado.setFont(new Font("Terminal", Font.PLAIN, 120));
			}
			else
			{
				resultado.setFont(new Font("Terminal", Font.PLAIN, 90));
			}
			resultado.setAlignment(Label.CENTER);
			jugadoresPanel.add(resultado);
			Panel espacioInferior = new Panel();
			jugadoresPanel.add(espacioInferior);
			partida.add(jugadoresPanel, BorderLayout.WEST);

			partida.addWindowListener(this);
		}
		else if(e.getSource().equals(estadisticas))
		{
			dlgEstadisticas.setLayout(new FlowLayout());
			conectar();
			listado.append(dameJugadores());

			dlgEstadisticas.addWindowListener(this);
			dlgEstadisticas.add(listado);
			dlgEstadisticas.add(volver);
			volver.addActionListener(this);
			dlgEstadisticas.setSize(350, 200);
			dlgEstadisticas.setResizable(false);
			dlgEstadisticas.setBackground(Color.blue);
			dlgEstadisticas.setLocationRelativeTo(null);
			dlgEstadisticas.setVisible(true);
		}
		else if(e.getSource().equals(volver))
		{
			listado.setText("");
			dlgEstadisticas.setVisible(false);
			desconectar();
		}
		else if(e.getSource().equals(ayuda)) 
		{
			try 
			{
				File pdfFile = new File("Manual_de_Usuario_Programa_Gestión.pdf");
				if (Desktop.isDesktopSupported() && pdfFile.exists()) 
				{
					Desktop.getDesktop().open(pdfFile);
				} 
				else 
				{
					System.out.println("No se puede abrir el archivo PDF.");
				}
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}

	}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (jugadores.isActive()) 
		{
			jugadores.setVisible(false);
		} 
		else if(detallesJugadores.isActive())
		{
			detallesJugadores.setVisible(false);
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

	public boolean conectar()
	{
		boolean conexionCorrecta = true;

		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
			conexionCorrecta = false;
		}

		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
			conexionCorrecta = false;
		}
		return conexionCorrecta;
	}

	public String dameJugadores()
	{
		String contenido = "";
		String sentencia = "SELECT * FROM jugadores;";

		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

			rs = statement.executeQuery(sentencia);

			while(rs.next())
			{
				contenido = contenido + "ID: " + rs.getInt("idJugador") + " | Nombre: " 

				+ rs.getString("nombreJugador") + " | Victorias: " + rs.getInt("victoriasJugador") + "\n";
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL: " + e.toString());
		}
		return contenido;
	}

	public void desconectar()
	{
		try
		{
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("Error al cerrar " + e.toString());
		}
		catch(NullPointerException npe)
		{

		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource().equals(dadoPanel))
		{
			int tirada = (int) ((Math.random() * 6) + 1);
			boolean turnoRojo = true;
			boolean turnoAmarillo = false;
			boolean turnoAzul = false;
			boolean turnoVerde = false;
			resultado.setText(String.valueOf(tirada));
			if(numJugadores == 2)
			{
				if(turno == 1) 
				{
					resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					if(tirada == 5 & salidaRojo == false) 
					{
						anchuraRojo = 280;
						alturaRojo = 160;
						tableroPanel.repaint();
						salidaRojo = true;
					}
					else if(salidaRojo == true)
					{
						metaRojo = metaRojo + tirada;
						switch(metaRojo)
						{
						case 1:
							anchuraRojo = 280;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraRojo = 280;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraRojo = 320;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraRojo = 280;
							alturaRojo = 319;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraRojo = 239;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraRojo = 199;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraRojo = 160;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraRojo = 120;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraRojo = 80;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraRojo = 40;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraRojo = 1;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraRojo = 1;
							alturaRojo = 359;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraRojo = 1;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraRojo = 40;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraRojo = 80;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraRojo = 120;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraRojo = 160;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraRojo = 199;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraRojo = 239;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraRojo = 281;
							alturaRojo = 441;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraRojo = 319;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraRojo = 281;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraRojo = 281;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraRojo = 281;
							alturaRojo = 602;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraRojo = 281;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraRojo = 281;
							alturaRojo = 681;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraRojo = 281;
							alturaRojo = 721;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraRojo = 281;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraRojo = 361;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraRojo = 481;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraRojo = 481;
							alturaRojo = 720;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraRojo = 481;
							alturaRojo = 680;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraRojo = 481;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraRojo = 481;
							alturaRojo = 601;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraRojo = 481;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraRojo = 481;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraRojo = 441;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraRojo = 481;
							alturaRojo = 442;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraRojo = 522;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraRojo = 562;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraRojo = 601;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraRojo = 641;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraRojo = 680;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraRojo = 720;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraRojo = 760;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraRojo = 760;
							alturaRojo = 401;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraRojo = 760;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraRojo = 720;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraRojo = 680;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraRojo = 641;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraRojo = 602;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraRojo = 562;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraRojo = 522;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraRojo = 482;
							alturaRojo = 320;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraRojo = 442;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraRojo = 482;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraRojo = 482;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraRojo = 482;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraRojo = 482;
							alturaRojo = 119;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraRojo = 482;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraRojo = 482;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraRojo = 482;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraRojo = 400;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraRojo = 400;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraRojo = 400;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraRojo = 400;
							alturaRojo = 120;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraRojo = 400;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraRojo = 400;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraRojo = 400;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraRojo = 400;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraRojo = 380;
							alturaRojo = 340;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorRojo = 0;
						turno = 2;
					}
					else if(tirada == 6 & salidaRojo == true)
					{
						contadorRojo = contadorRojo + 1;
						if(contadorRojo == 3)
						{
							anchuraRojo = 120;
							alturaRojo = 120;
							tableroPanel.repaint();
							salidaRojo = false;
							contadorRojo = 0;
							metaRojo = 0;
							turno = 2;
						}
					}
				} 
				else if(turno == 2) 
				{
					resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					if(tirada == 5 & salidaAmarillo == false) 
					{
						anchuraAmarillo = 480;
						alturaAmarillo = 603;
						tableroPanel.repaint();
						salidaAmarillo = true;
					}
					else if(salidaAmarillo == true)
					{
						metaAmarillo = metaAmarillo + tirada;
						switch(metaAmarillo)
						{
						case 1:
							anchuraAmarillo = 481;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraAmarillo = 481;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraAmarillo = 441;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraAmarillo = 481;
							alturaAmarillo = 442;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraAmarillo = 522;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraAmarillo = 562;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraAmarillo = 601;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraAmarillo = 641;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraAmarillo = 680;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraAmarillo = 720;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraAmarillo = 760;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraAmarillo = 760;
							alturaAmarillo = 401;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraAmarillo = 760;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraAmarillo = 720;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraAmarillo = 680;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraAmarillo = 641;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraAmarillo = 602;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraAmarillo = 562;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraAmarillo = 522;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraAmarillo = 482;
							alturaAmarillo = 320;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraAmarillo = 442;
							alturaAmarillo = 280;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraAmarillo = 482;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraAmarillo = 482;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraAmarillo = 482;
							alturaAmarillo = 159;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraAmarillo = 482;
							alturaAmarillo = 119;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraAmarillo = 482;
							alturaAmarillo = 80;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraAmarillo = 482;
							alturaAmarillo = 40;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraAmarillo = 482;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraAmarillo = 400;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraAmarillo = 280;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraAmarillo = 280;
							alturaAmarillo = 42;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraAmarillo = 280;
							alturaAmarillo = 81;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraAmarillo = 280;
							alturaAmarillo = 121;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraAmarillo = 280;
							alturaAmarillo = 160;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraAmarillo = 280;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraAmarillo = 280;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraAmarillo = 320;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraAmarillo = 280;
							alturaAmarillo = 319;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraAmarillo = 239;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraAmarillo = 199;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraAmarillo = 160;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraAmarillo = 120;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraAmarillo = 80;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraAmarillo = 40;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraAmarillo = 1;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraAmarillo = 1;
							alturaAmarillo = 359;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraAmarillo = 1;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraAmarillo = 40;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraAmarillo = 80;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraAmarillo = 120;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraAmarillo = 160;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraAmarillo = 199;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraAmarillo = 239;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraAmarillo = 281;
							alturaAmarillo = 441;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraAmarillo = 319;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraAmarillo = 281;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraAmarillo = 281;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraAmarillo = 281;
							alturaAmarillo = 602;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraAmarillo = 281;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraAmarillo = 281;
							alturaAmarillo = 681;	
							tableroPanel.repaint();
							break;
						case 61:
							anchuraAmarillo = 281;
							alturaAmarillo = 721;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraAmarillo = 281;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraAmarillo = 361;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraAmarillo = 361;
							alturaAmarillo = 720;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraAmarillo = 361;
							alturaAmarillo = 680;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraAmarillo = 361;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraAmarillo = 361;
							alturaAmarillo = 601;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraAmarillo = 361;
							alturaAmarillo = 561;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraAmarillo = 361;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraAmarillo = 361;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraAmarillo = 381;
							alturaAmarillo = 421;
							tableroPanel.repaint();
							break;
						}
					}

					if(tirada != 6)
					{
						contadorAmarillo = 0;
						turno = 1;
					}
					else if(tirada == 6 & salidaAmarillo == true)
					{
						contadorAmarillo = contadorAmarillo + 1;
						if(contadorAmarillo == 3)
						{
							anchuraAmarillo = 640;
							alturaAmarillo = 640;
							tableroPanel.repaint();
							salidaAmarillo = false;
							contadorAmarillo = 0;
							metaAmarillo = 0;
							turno = 1;
						}
					}
				}
			}
			else if(numJugadores == 3)
			{
				if(turno == 1)
				{
					resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					turnoAzul = false;
					if(tirada == 5 & salidaRojo == false) 
					{
						anchuraRojo = 280;
						alturaRojo = 160;
						tableroPanel.repaint();
						salidaRojo = true;
					}
					else if(salidaRojo == true)
					{
						metaRojo = metaRojo + tirada;
						switch(metaRojo)
						{
						case 1:
							anchuraRojo = 280;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraRojo = 280;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraRojo = 320;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraRojo = 280;
							alturaRojo = 319;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraRojo = 239;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraRojo = 199;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraRojo = 160;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraRojo = 120;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraRojo = 80;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraRojo = 40;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraRojo = 1;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraRojo = 1;
							alturaRojo = 359;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraRojo = 1;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraRojo = 40;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraRojo = 80;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraRojo = 120;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraRojo = 160;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraRojo = 199;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraRojo = 239;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraRojo = 281;
							alturaRojo = 441;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraRojo = 319;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraRojo = 281;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraRojo = 281;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraRojo = 281;
							alturaRojo = 602;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraRojo = 281;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraRojo = 281;
							alturaRojo = 681;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraRojo = 281;
							alturaRojo = 721;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraRojo = 281;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraRojo = 361;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraRojo = 481;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraRojo = 481;
							alturaRojo = 720;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraRojo = 481;
							alturaRojo = 680;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraRojo = 481;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraRojo = 481;
							alturaRojo = 601;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraRojo = 481;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraRojo = 481;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraRojo = 441;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraRojo = 481;
							alturaRojo = 442;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraRojo = 522;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraRojo = 562;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraRojo = 601;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraRojo = 641;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraRojo = 680;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraRojo = 720;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraRojo = 760;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraRojo = 760;
							alturaRojo = 401;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraRojo = 760;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraRojo = 720;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraRojo = 680;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraRojo = 641;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraRojo = 602;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraRojo = 562;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraRojo = 522;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraRojo = 482;
							alturaRojo = 320;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraRojo = 442;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraRojo = 482;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraRojo = 482;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraRojo = 482;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraRojo = 482;
							alturaRojo = 119;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraRojo = 482;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraRojo = 482;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraRojo = 482;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraRojo = 400;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraRojo = 400;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraRojo = 400;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraRojo = 400;
							alturaRojo = 120;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraRojo = 400;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraRojo = 400;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraRojo = 400;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraRojo = 400;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraRojo = 380;
							alturaRojo = 340;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorRojo = 0;
						turno = 2;
					}
					else if(tirada == 6 & salidaRojo == true)
					{
						contadorRojo = contadorRojo + 1;
						if(contadorRojo == 3)
						{
							anchuraRojo = 120;
							alturaRojo = 120;
							tableroPanel.repaint();
							salidaRojo = false;
							contadorRojo = 0;
							metaRojo = 0;
							turno = 2;
						}
					}
				}
				else if(turno == 2)
				{
					resultado.setForeground(Color.blue);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = true;
					if(tirada == 5 & salidaAzul == false)
					{
						anchuraAzul = 160;
						alturaAzul = 483;
						tableroPanel.repaint();
						salidaAzul = true;
					}
					else if(salidaAzul == true)
					{
						metaAzul = metaAzul + tirada;
						switch(metaAzul)
						{
						case 1:
							anchuraAzul = 199;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraAzul = 239;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraAzul = 281;
							alturaAzul = 441;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraAzul = 319;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraAzul = 281;
							alturaAzul = 522;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraAzul = 281;
							alturaAzul = 562;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraAzul = 281;
							alturaAzul = 602;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraAzul = 281;
							alturaAzul = 641;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraAzul = 281;
							alturaAzul = 681;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraAzul = 281;
							alturaAzul = 721;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraAzul = 281;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraAzul = 361;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraAzul = 481;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraAzul = 481;
							alturaAzul = 720;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraAzul = 481;
							alturaAzul = 680;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraAzul = 481;
							alturaAzul = 641;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraAzul = 481;
							alturaAzul = 601;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraAzul = 481;
							alturaAzul = 562;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraAzul = 481;
							alturaAzul = 522;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraAzul = 441;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraAzul = 481;
							alturaAzul = 442;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraAzul = 522;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraAzul = 562;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraAzul = 601;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraAzul = 641;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraAzul = 680;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraAzul = 720;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraAzul = 760;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraAzul = 760;
							alturaAzul = 401;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraAzul = 760;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraAzul = 720;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraAzul = 680;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraAzul = 641;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraAzul = 602;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraAzul = 562;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraAzul = 522;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraAzul = 482;
							alturaAzul = 320;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraAzul = 442;
							alturaAzul = 280;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraAzul = 482;
							alturaAzul = 239;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraAzul = 482;
							alturaAzul = 199;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraAzul = 482;
							alturaAzul = 159;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraAzul = 482;
							alturaAzul = 119;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraAzul = 482;
							alturaAzul = 80;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraAzul = 482;
							alturaAzul = 40;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraAzul = 482;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraAzul = 400;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraAzul = 280;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraAzul = 280;
							alturaAzul = 42;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraAzul = 280;
							alturaAzul = 81;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraAzul = 280;
							alturaAzul = 121;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraAzul = 280;
							alturaAzul = 160;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraAzul = 280;
							alturaAzul = 199;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraAzul = 280;
							alturaAzul = 239;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraAzul = 320;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraAzul = 280;
							alturaAzul = 319;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraAzul = 239;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraAzul = 199;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraAzul = 160;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraAzul = 120;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraAzul = 80;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraAzul = 40;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraAzul = 1;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraAzul = 1;
							alturaAzul = 359;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraAzul = 41;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraAzul = 81;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraAzul = 121;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraAzul = 161;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraAzul = 201;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraAzul = 241;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraAzul = 281;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraAzul = 341;
							alturaAzul = 381;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorAzul = 0;
						turno = 3;
					}
					else if(tirada == 6 & salidaAzul == true)
					{
						contadorAzul = contadorAzul + tirada;
						if(contadorAzul == 3)
						{
							anchuraAzul = 120;
							alturaAzul = 640;
							tableroPanel.repaint();
							salidaAzul = false;
							contadorAzul = 0;
							metaAzul = 0;
							turno = 3;
						}
					}
				}
				else if(turno == 3)
				{
					resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					turnoAzul = false;
					if(tirada == 5 & salidaAmarillo == false)
					{
						anchuraAmarillo = 480;
						alturaAmarillo = 603;
						tableroPanel.repaint();
						salidaAmarillo = true;
					}
					else if(salidaAmarillo == true)
					{
						metaAmarillo = metaAmarillo + tirada;
						switch(metaAmarillo)
						{
						case 1:
							anchuraAmarillo = 481;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraAmarillo = 481;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraAmarillo = 441;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraAmarillo = 481;
							alturaAmarillo = 442;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraAmarillo = 522;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraAmarillo = 562;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraAmarillo = 601;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraAmarillo = 641;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraAmarillo = 680;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraAmarillo = 720;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraAmarillo = 760;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraAmarillo = 760;
							alturaAmarillo = 401;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraAmarillo = 760;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraAmarillo = 720;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraAmarillo = 680;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraAmarillo = 641;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraAmarillo = 602;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraAmarillo = 562;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraAmarillo = 522;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraAmarillo = 482;
							alturaAmarillo = 320;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraAmarillo = 442;
							alturaAmarillo = 280;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraAmarillo = 482;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraAmarillo = 482;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraAmarillo = 482;
							alturaAmarillo = 159;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraAmarillo = 482;
							alturaAmarillo = 119;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraAmarillo = 482;
							alturaAmarillo = 80;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraAmarillo = 482;
							alturaAmarillo = 40;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraAmarillo = 482;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraAmarillo = 400;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraAmarillo = 280;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraAmarillo = 280;
							alturaAmarillo = 42;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraAmarillo = 280;
							alturaAmarillo = 81;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraAmarillo = 280;
							alturaAmarillo = 121;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraAmarillo = 280;
							alturaAmarillo = 160;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraAmarillo = 280;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraAmarillo = 280;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraAmarillo = 320;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraAmarillo = 280;
							alturaAmarillo = 319;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraAmarillo = 239;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraAmarillo = 199;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraAmarillo = 160;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraAmarillo = 120;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraAmarillo = 80;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraAmarillo = 40;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraAmarillo = 1;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraAmarillo = 1;
							alturaAmarillo = 359;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraAmarillo = 1;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraAmarillo = 40;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraAmarillo = 80;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraAmarillo = 120;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraAmarillo = 160;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraAmarillo = 199;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraAmarillo = 239;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraAmarillo = 281;
							alturaAmarillo = 441;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraAmarillo = 319;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraAmarillo = 281;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraAmarillo = 281;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraAmarillo = 281;
							alturaAmarillo = 602;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraAmarillo = 281;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraAmarillo = 281;
							alturaAmarillo = 681;	
							tableroPanel.repaint();
							break;
						case 61:
							anchuraAmarillo = 281;
							alturaAmarillo = 721;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraAmarillo = 281;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraAmarillo = 361;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraAmarillo = 361;
							alturaAmarillo = 720;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraAmarillo = 361;
							alturaAmarillo = 680;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraAmarillo = 361;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraAmarillo = 361;
							alturaAmarillo = 601;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraAmarillo = 361;
							alturaAmarillo = 561;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraAmarillo = 361;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraAmarillo = 361;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraAmarillo = 381;
							alturaAmarillo = 421;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorAmarillo = 0;
						turno = 1;
					}
					else if(tirada == 6 & salidaAmarillo == true)
					{
						contadorAmarillo = contadorAmarillo + 1;
						if(contadorAmarillo == 3)
						{
							anchuraAmarillo = 640;
							alturaAmarillo = 640;
							tableroPanel.repaint();
							salidaAmarillo = false;
							contadorAmarillo = 0;
							metaAmarillo = 0;
							turno = 1;
						}
					}
				}
			}
			else if(numJugadores == 4)
			{
				if(turno == 1)
				{
					resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					turnoAzul = false;
					turnoVerde = false;
					if(tirada == 5 & salidaRojo == false) 
					{
						anchuraRojo = 280;
						alturaRojo = 160;
						tableroPanel.repaint();
						salidaRojo = true;
					}
					else if(salidaRojo == true)
					{
						metaRojo = metaRojo + tirada;
						switch(metaRojo)
						{
						case 1:
							anchuraRojo = 280;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraRojo = 280;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraRojo = 320;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraRojo = 280;
							alturaRojo = 319;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraRojo = 239;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraRojo = 199;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraRojo = 160;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraRojo = 120;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraRojo = 80;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraRojo = 40;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraRojo = 1;
							alturaRojo = 279;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraRojo = 1;
							alturaRojo = 359;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraRojo = 1;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraRojo = 40;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraRojo = 80;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraRojo = 120;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraRojo = 160;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraRojo = 199;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraRojo = 239;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraRojo = 281;
							alturaRojo = 441;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraRojo = 319;
							alturaRojo = 481;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraRojo = 281;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraRojo = 281;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraRojo = 281;
							alturaRojo = 602;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraRojo = 281;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraRojo = 281;
							alturaRojo = 681;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraRojo = 281;
							alturaRojo = 721;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraRojo = 281;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraRojo = 361;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraRojo = 481;
							alturaRojo = 760;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraRojo = 481;
							alturaRojo = 720;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraRojo = 481;
							alturaRojo = 680;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraRojo = 481;
							alturaRojo = 641;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraRojo = 481;
							alturaRojo = 601;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraRojo = 481;
							alturaRojo = 562;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraRojo = 481;
							alturaRojo = 522;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraRojo = 441;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraRojo = 481;
							alturaRojo = 442;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraRojo = 522;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraRojo = 562;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraRojo = 601;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraRojo = 641;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraRojo = 680;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraRojo = 720;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraRojo = 760;
							alturaRojo = 482;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraRojo = 760;
							alturaRojo = 401;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraRojo = 760;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraRojo = 720;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraRojo = 680;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraRojo = 641;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraRojo = 602;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraRojo = 562;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraRojo = 522;
							alturaRojo = 281;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraRojo = 482;
							alturaRojo = 320;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraRojo = 442;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraRojo = 482;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraRojo = 482;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraRojo = 482;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraRojo = 482;
							alturaRojo = 119;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraRojo = 482;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraRojo = 482;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraRojo = 482;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraRojo = 400;
							alturaRojo = 1;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraRojo = 400;
							alturaRojo = 40;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraRojo = 400;
							alturaRojo = 80;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraRojo = 400;
							alturaRojo = 120;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraRojo = 400;
							alturaRojo = 159;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraRojo = 400;
							alturaRojo = 199;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraRojo = 400;
							alturaRojo = 239;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraRojo = 400;
							alturaRojo = 280;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraRojo = 380;
							alturaRojo = 340;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorRojo = 0;
						turno = 2;
					}
					else if(tirada == 6 & salidaRojo == true)
					{
						contadorRojo = contadorRojo + 1;
						if(contadorRojo == 3)
						{
							anchuraRojo = 120;
							alturaRojo = 120;
							tableroPanel.repaint();
							salidaRojo = false;
							contadorRojo = 0;
							metaRojo = 0;
							turno = 2;
						}
					}
				}
				else if(turno == 2)
				{
					resultado.setForeground(Color.blue);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = true;
					turnoVerde = false;
					if(tirada == 5 & salidaAzul == false)
					{
						anchuraAzul = 160;
						alturaAzul = 483;
						tableroPanel.repaint();
						salidaAzul = true;
					}
					else if(salidaAzul == true)
					{
						metaAzul = metaAzul + tirada;
						switch(metaAzul)
						{
						case 1:
							anchuraAzul = 199;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraAzul = 239;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraAzul = 281;
							alturaAzul = 441;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraAzul = 319;
							alturaAzul = 481;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraAzul = 281;
							alturaAzul = 522;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraAzul = 281;
							alturaAzul = 562;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraAzul = 281;
							alturaAzul = 602;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraAzul = 281;
							alturaAzul = 641;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraAzul = 281;
							alturaAzul = 681;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraAzul = 281;
							alturaAzul = 721;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraAzul = 281;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraAzul = 361;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraAzul = 481;
							alturaAzul = 760;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraAzul = 481;
							alturaAzul = 720;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraAzul = 481;
							alturaAzul = 680;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraAzul = 481;
							alturaAzul = 641;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraAzul = 481;
							alturaAzul = 601;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraAzul = 481;
							alturaAzul = 562;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraAzul = 481;
							alturaAzul = 522;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraAzul = 441;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraAzul = 481;
							alturaAzul = 442;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraAzul = 522;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraAzul = 562;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraAzul = 601;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraAzul = 641;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraAzul = 680;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraAzul = 720;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraAzul = 760;
							alturaAzul = 482;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraAzul = 760;
							alturaAzul = 401;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraAzul = 760;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraAzul = 720;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraAzul = 680;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraAzul = 641;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraAzul = 602;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraAzul = 562;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraAzul = 522;
							alturaAzul = 281;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraAzul = 482;
							alturaAzul = 320;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraAzul = 442;
							alturaAzul = 280;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraAzul = 482;
							alturaAzul = 239;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraAzul = 482;
							alturaAzul = 199;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraAzul = 482;
							alturaAzul = 159;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraAzul = 482;
							alturaAzul = 119;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraAzul = 482;
							alturaAzul = 80;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraAzul = 482;
							alturaAzul = 40;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraAzul = 482;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraAzul = 400;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraAzul = 280;
							alturaAzul = 1;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraAzul = 280;
							alturaAzul = 42;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraAzul = 280;
							alturaAzul = 81;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraAzul = 280;
							alturaAzul = 121;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraAzul = 280;
							alturaAzul = 160;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraAzul = 280;
							alturaAzul = 199;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraAzul = 280;
							alturaAzul = 239;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraAzul = 320;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraAzul = 280;
							alturaAzul = 319;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraAzul = 239;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraAzul = 199;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraAzul = 160;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraAzul = 120;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraAzul = 80;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraAzul = 40;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraAzul = 1;
							alturaAzul = 279;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraAzul = 1;
							alturaAzul = 359;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraAzul = 41;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraAzul = 81;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraAzul = 121;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraAzul = 161;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraAzul = 201;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraAzul = 241;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraAzul = 281;
							alturaAzul = 361;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraAzul = 341;
							alturaAzul = 381;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorAzul = 0;
						turno = 3;
					}
					else if(tirada == 6 & salidaAzul == true)
					{
						contadorAzul = contadorAzul + tirada;
						if(contadorAzul == 3)
						{
							anchuraAzul = 120;
							alturaAzul = 640;
							tableroPanel.repaint();
							salidaAzul = false;
							contadorAzul = 0;
							metaAzul = 0;
							turno = 3;
						}
					}
				}
				else if(turno == 3)
				{
					resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					turnoAzul = false;
					if(tirada == 5 & salidaAmarillo == false)
					{
						anchuraAmarillo = 480;
						alturaAmarillo = 603;
						tableroPanel.repaint();
						salidaAmarillo = true;
					}
					else if(salidaAmarillo == true)
					{
						metaAmarillo = metaAmarillo + tirada;
						switch(metaAmarillo)
						{
						case 1:
							anchuraAmarillo = 481;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraAmarillo = 481;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraAmarillo = 441;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraAmarillo = 481;
							alturaAmarillo = 442;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraAmarillo = 522;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraAmarillo = 562;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraAmarillo = 601;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraAmarillo = 641;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraAmarillo = 680;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraAmarillo = 720;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraAmarillo = 760;
							alturaAmarillo = 482;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraAmarillo = 760;
							alturaAmarillo = 401;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraAmarillo = 760;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraAmarillo = 720;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraAmarillo = 680;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraAmarillo = 641;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraAmarillo = 602;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraAmarillo = 562;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraAmarillo = 522;
							alturaAmarillo = 281;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraAmarillo = 482;
							alturaAmarillo = 320;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraAmarillo = 442;
							alturaAmarillo = 280;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraAmarillo = 482;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraAmarillo = 482;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraAmarillo = 482;
							alturaAmarillo = 159;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraAmarillo = 482;
							alturaAmarillo = 119;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraAmarillo = 482;
							alturaAmarillo = 80;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraAmarillo = 482;
							alturaAmarillo = 40;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraAmarillo = 482;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraAmarillo = 400;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraAmarillo = 280;
							alturaAmarillo = 1;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraAmarillo = 280;
							alturaAmarillo = 42;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraAmarillo = 280;
							alturaAmarillo = 81;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraAmarillo = 280;
							alturaAmarillo = 121;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraAmarillo = 280;
							alturaAmarillo = 160;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraAmarillo = 280;
							alturaAmarillo = 199;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraAmarillo = 280;
							alturaAmarillo = 239;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraAmarillo = 320;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraAmarillo = 280;
							alturaAmarillo = 319;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraAmarillo = 239;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraAmarillo = 199;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraAmarillo = 160;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraAmarillo = 120;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraAmarillo = 80;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 44:
							anchuraAmarillo = 40;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraAmarillo = 1;
							alturaAmarillo = 279;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraAmarillo = 1;
							alturaAmarillo = 359;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraAmarillo = 1;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraAmarillo = 40;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraAmarillo = 80;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraAmarillo = 120;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraAmarillo = 160;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraAmarillo = 199;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraAmarillo = 239;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraAmarillo = 281;
							alturaAmarillo = 441;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraAmarillo = 319;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraAmarillo = 281;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraAmarillo = 281;
							alturaAmarillo = 562;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraAmarillo = 281;
							alturaAmarillo = 602;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraAmarillo = 281;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraAmarillo = 281;
							alturaAmarillo = 681;	
							tableroPanel.repaint();
							break;
						case 61:
							anchuraAmarillo = 281;
							alturaAmarillo = 721;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraAmarillo = 281;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraAmarillo = 361;
							alturaAmarillo = 760;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraAmarillo = 361;
							alturaAmarillo = 720;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraAmarillo = 361;
							alturaAmarillo = 680;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraAmarillo = 361;
							alturaAmarillo = 641;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraAmarillo = 361;
							alturaAmarillo = 601;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraAmarillo = 361;
							alturaAmarillo = 561;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraAmarillo = 361;
							alturaAmarillo = 522;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraAmarillo = 361;
							alturaAmarillo = 481;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraAmarillo = 381;
							alturaAmarillo = 421;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorAmarillo = 0;
						turno = 4;
					}
					else if(tirada == 6 & salidaAmarillo == true)
					{
						contadorAmarillo = contadorAmarillo + 1;
						if(contadorAmarillo == 3)
						{
							anchuraAmarillo = 640;
							alturaAmarillo = 640;
							tableroPanel.repaint();
							salidaAmarillo = false;
							contadorAmarillo = 0;
							metaAmarillo = 0;
							turno = 4;
						}
					}
				}
				else if(turno == 4)
				{				
					resultado.setForeground(Color.green);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = false;
					turnoVerde = true;
					if(tirada == 5 & salidaVerde == false)
					{
						anchuraVerde = 600;
						alturaVerde = 280;
						tableroPanel.repaint();
						salidaVerde = true;
					}
					else if(salidaVerde == true)
					{
						metaVerde = metaVerde + tirada;
						switch(metaVerde)
						{
						case 1:
							anchuraVerde = 562;
							alturaVerde = 281;
							tableroPanel.repaint();
							break;
						case 2:
							anchuraVerde = 522;
							alturaVerde = 281;
							tableroPanel.repaint();
							break;
						case 3:
							anchuraVerde = 482;
							alturaVerde = 320;
							tableroPanel.repaint();
							break;
						case 4:
							anchuraVerde = 442;
							alturaVerde = 280;
							tableroPanel.repaint();
							break;
						case 5:
							anchuraVerde = 482;
							alturaVerde = 239;
							tableroPanel.repaint();
							break;
						case 6:
							anchuraVerde = 482;
							alturaVerde = 199;
							tableroPanel.repaint();
							break;
						case 7:
							anchuraVerde = 482;
							alturaVerde = 159;
							tableroPanel.repaint();
							break;
						case 8:
							anchuraVerde = 482;
							alturaVerde = 119;
							tableroPanel.repaint();
							break;
						case 9:
							anchuraVerde = 482;
							alturaVerde = 80;
							tableroPanel.repaint();
							break;
						case 10:
							anchuraVerde = 482;
							alturaVerde = 40;
							tableroPanel.repaint();
							break;
						case 11:
							anchuraVerde = 482;
							alturaVerde = 1;
							tableroPanel.repaint();
							break;
						case 12:
							anchuraVerde = 400;
							alturaVerde = 1;
							tableroPanel.repaint();
							break;
						case 13:
							anchuraVerde = 280;
							alturaVerde = 1;
							tableroPanel.repaint();
							break;
						case 14:
							anchuraVerde = 280;
							alturaVerde = 42;
							tableroPanel.repaint();
							break;
						case 15:
							anchuraVerde = 280;
							alturaVerde = 81;
							tableroPanel.repaint();
							break;
						case 16:
							anchuraVerde = 280;
							alturaVerde = 121;
							tableroPanel.repaint();
							break;
						case 17:
							anchuraVerde = 280;
							alturaVerde = 160;
							tableroPanel.repaint();
							break;
						case 18:
							anchuraVerde = 280;
							alturaVerde = 199;
							tableroPanel.repaint();
							break;
						case 19:
							anchuraVerde = 280;
							alturaVerde = 239;
							tableroPanel.repaint();
							break;
						case 20:
							anchuraVerde = 320;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 21:
							anchuraVerde = 280;
							alturaVerde = 319;
							tableroPanel.repaint();
							break;
						case 22:
							anchuraVerde = 239;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 23:
							anchuraVerde = 199;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 24:
							anchuraVerde = 160;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 25:
							anchuraVerde = 120;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 26:
							anchuraVerde = 80;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 27:
							anchuraVerde = 40;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 28:
							anchuraVerde = 1;
							alturaVerde = 279;
							tableroPanel.repaint();
							break;
						case 29:
							anchuraVerde = 1;
							alturaVerde = 359;
							tableroPanel.repaint();
							break;
						case 30:
							anchuraVerde = 1;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 31:
							anchuraVerde = 40;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 32:
							anchuraVerde = 80;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 33:
							anchuraVerde = 120;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 34:
							anchuraVerde = 160;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 35:
							anchuraVerde = 199;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 36:
							anchuraVerde = 239;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 37:
							anchuraVerde = 281;
							alturaVerde = 441;
							tableroPanel.repaint();
							break;
						case 38:
							anchuraVerde = 319;
							alturaVerde = 481;
							tableroPanel.repaint();
							break;
						case 39:
							anchuraVerde = 281;
							alturaVerde = 522;
							tableroPanel.repaint();
							break;
						case 40:
							anchuraVerde = 281;
							alturaVerde = 562;
							tableroPanel.repaint();
							break;
						case 41:
							anchuraVerde = 281;
							alturaVerde = 602;
							tableroPanel.repaint();
							break;
						case 42:
							anchuraVerde = 281;
							alturaVerde = 641;
							tableroPanel.repaint();
							break;
						case 43:
							anchuraVerde = 281;
							alturaVerde = 681;	
							tableroPanel.repaint();
							break;
						case 44:
							anchuraVerde = 281;
							alturaVerde = 721;
							tableroPanel.repaint();
							break;
						case 45:
							anchuraVerde = 281;
							alturaVerde = 760;
							tableroPanel.repaint();
							break;
						case 46:
							anchuraVerde = 361;
							alturaVerde = 760;
							tableroPanel.repaint();
							break;
						case 47:
							anchuraVerde = 481;
							alturaVerde = 760;
							tableroPanel.repaint();
							break;
						case 48:
							anchuraVerde = 481;
							alturaVerde = 720;
							tableroPanel.repaint();
							break;
						case 49:
							anchuraVerde = 481;
							alturaVerde = 680;
							tableroPanel.repaint();
							break;
						case 50:
							anchuraVerde = 481;
							alturaVerde = 641;
							tableroPanel.repaint();
							break;
						case 51:
							anchuraVerde = 481;
							alturaVerde = 601;
							tableroPanel.repaint();
							break;
						case 52:
							anchuraVerde = 481;
							alturaVerde = 562;
							tableroPanel.repaint();
							break;
						case 53:
							anchuraVerde = 481;
							alturaVerde = 522;
							tableroPanel.repaint();
							break;
						case 54:
							anchuraVerde = 441;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 55:
							anchuraVerde = 481;
							alturaVerde = 442;
							tableroPanel.repaint();
							break;
						case 56:
							anchuraVerde = 522;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 57:
							anchuraVerde = 562;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 58:
							anchuraVerde = 601;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 59:
							anchuraVerde = 641;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 60:
							anchuraVerde = 680;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 61:
							anchuraVerde = 720;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 62:
							anchuraVerde = 760;
							alturaVerde = 482;
							tableroPanel.repaint();
							break;
						case 63:
							anchuraVerde = 760;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 64:
							anchuraVerde = 720;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 65:
							anchuraVerde = 680;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 66:
							anchuraVerde = 640;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 67:
							anchuraVerde = 600;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 68:
							anchuraVerde = 560;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 69:
							anchuraVerde = 520;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 70:
							anchuraVerde = 480;
							alturaVerde = 401;
							tableroPanel.repaint();
							break;
						case 71:
							anchuraVerde = 420;
							alturaVerde = 381;
							tableroPanel.repaint();
							break;
						}
					}
					if(tirada != 6)
					{
						contadorVerde = 0;
						turno = 1;
					}
					else if(tirada == 6 & salidaVerde == true)
					{
						contadorVerde = contadorVerde + 1;
						if(contadorVerde == 3)
						{
							anchuraVerde = 640;
							alturaAmarillo = 120;
							tableroPanel.repaint();
							salidaVerde = false;
							contadorVerde = 0;
							metaVerde = 0;
							turno = 1;
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Apéndice de método generado automáticamente

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Apéndice de método generado automáticamente

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Apéndice de método generado automáticamente

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Apéndice de método generado automáticamente

	}
}