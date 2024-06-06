package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Controlador implements WindowListener, ActionListener, MouseListener
{
	private Modelo m;
	private Vista v;

	public Controlador(Modelo modelo, Vista vista) 
	{
		this.m = modelo;
		this.v = vista;
		v.addWindowListener(this);
		v.addMouseListener(this);
		v.jugadores.addWindowListener(this);
		v.detallesJugadores.addWindowListener(this);
		v.jugar.addActionListener(this);
		v.estadisticas.addActionListener(this);
		v.aceptar.addActionListener(this);
		v.comenzar.addActionListener(this);
		v.volver.addActionListener(this);
		v.ayuda.addActionListener(this);
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

			v.tableroPanel = new Panel() 
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) 
				{
					super.paint(g);
					g.drawImage(v.tablero, 0, 0, this);
					g.drawImage(v.rojo, v.anchuraRojo, v.alturaRojo, 40, 40, this);
					g.drawImage(v.amarillo, v.anchuraAmarillo, v.alturaAmarillo, 40, 40, this);
					if(v.numJugadores == 3)
					{
						g.drawImage(v.azul, v.anchuraAzul, v.alturaAzul, 40, 40, this);
					}
					else if(v.numJugadores > 3)
					{
						g.drawImage(v.azul, v.anchuraAzul, v.alturaAzul, 40, 40, this);
						g.drawImage(v.verde, v.anchuraVerde, v.alturaVerde, 40, 40, this);
					}
				}
			};
			v.tableroPanel.setPreferredSize(new Dimension(900, 920));
			v.partida.add(v.tableroPanel, BorderLayout.EAST);

			v.jugadoresPanel.setPreferredSize(new Dimension(200, 800));
			v.jugadoresPanel.setLayout(new GridLayout(0, 1, 0, 50));
			v.jugadorRojo.setText(v.jugadorRojo.getText() + v.nombreRojo.getText());
			v.jugadoresPanel.add(v.jugadorRojo);
			if (v.numJugadores >= 2) 
			{
				v.jugadorAmarillo.setText(v.jugadorAmarillo.getText() + v.nombreAmarillo.getText());
				v.jugadoresPanel.add(v.jugadorAmarillo);
			}
			if (v.numJugadores >= 3) 
			{
				v.jugadorAzul.setText(v.jugadorAzul.getText() + v.nombreAzul.getText());
				v.jugadoresPanel.add(v.jugadorAzul);
			}
			if (v.numJugadores == 4) 
			{
				v.jugadorVerde.setText(v.jugadorVerde.getText() + v.nombreVerde.getText());
				v.jugadoresPanel.add(v.jugadorVerde);
			}
			int anchoDado = 0;
			int altoDado = 0;
			if(v.numJugadores == 2)
			{
				anchoDado = 170;
				altoDado = 170;
			}
			else if(v.numJugadores == 3)
			{
				anchoDado = 140;
				altoDado = 140;
			}
			else
			{
				anchoDado = 100;
				altoDado = 100;
			}
			v.dadoPanel = new Vista.ImagePanel("dado.png", anchoDado, altoDado);
			v.dadoPanel.setBackground(Color.white);
			v.dadoPanel.addMouseListener(this);
			v.jugadoresPanel.add(v.dadoPanel);
			if(v.numJugadores == 2)
			{
				v.resultado.setFont(new Font("Terminal", Font.PLAIN, 150));
			}
			else if(v.numJugadores == 3)
			{
				v.resultado.setFont(new Font("Terminal", Font.PLAIN, 120));
			}
			else
			{
				v.resultado.setFont(new Font("Terminal", Font.PLAIN, 90));
			}
			v.resultado.setAlignment(Label.CENTER);
			v.jugadoresPanel.add(v.resultado);
			Panel espacioInferior = new Panel();
			v.jugadoresPanel.add(espacioInferior);
			v.partida.add(v.jugadoresPanel, BorderLayout.WEST);
			v.partida.add(v.jugadoresPanel, BorderLayout.WEST);

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
		else if(e.getSource().equals(v.ayuda)) 
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

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource().equals(v.dadoPanel))
		{
			int tirada = (int) ((Math.random() * 6) + 1);
			boolean turnoRojo = true;
			boolean turnoAmarillo = false;
			boolean turnoAzul = false;
			boolean turnoVerde = false;
			v.resultado.setText(String.valueOf(tirada));
			if(v.numJugadores == 2)
			{
				if(v.turno == 1) 
				{
					v.resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					if(tirada == 5) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 2;
					}
				} 
				else if(v.turno == 2) 
				{
					v.resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					if(tirada == 5) 
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 1;
					}
				}
			}
			else if(v.numJugadores == 3)
			{
				if(v.turno == 1)
				{
					v.resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					turnoAzul = false;
					if(tirada == 5) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 2;
					}
				}
				else if(v.turno == 2)
				{
					v.resultado.setForeground(Color.blue);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = true;
					if(tirada == 5)
					{
						v.anchuraAzul = 160;
						v.alturaAzul = 483;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 3;
					}
				}
				else if(v.turno == 3)
				{
					v.resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					turnoAzul = false;
					if(tirada == 5)
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 1;
					}
				}
			}
			else if(v.numJugadores == 4)
			{
				if(v.turno == 1)
				{
					v.resultado.setForeground(Color.red);
					turnoRojo = true;
					turnoAmarillo = false;
					turnoAzul = false;
					if(tirada == 5) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 2;
					}
				}
				else if(v.turno == 2)
				{
					v.resultado.setForeground(Color.blue);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = true;
					if(tirada == 5)
					{
						v.anchuraAzul = 160;
						v.alturaAzul = 483;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 3;
					}
				}
				else if(v.turno == 3)
				{
					v.resultado.setForeground(Color.yellow);
					turnoRojo = false;
					turnoAmarillo = true;
					turnoAzul = false;
					if(tirada == 5)
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 4;
					}
				}
				else if(v.turno == 4)
				{
					v.resultado.setForeground(Color.green);
					turnoRojo = false;
					turnoAmarillo = false;
					turnoAzul = false;
					turnoVerde = true;
					if(tirada == 5)
					{
						v.anchuraVerde = 600;
						v.alturaVerde = 280;
						v.tableroPanel.repaint();
					}
					if(tirada != 6)
					{
						v.turno = 1;
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