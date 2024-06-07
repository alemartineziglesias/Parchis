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
		v.dlgSeguro.addWindowListener(this);
		v.si.addActionListener(this);
		v.no.addActionListener(this);
		v.estadisticas.addActionListener(this);
		v.aceptar.addActionListener(this);
		v.comenzar.addActionListener(this);
		v.limpiar.addActionListener(this);
		v.dlgError.addWindowListener(this);
		v.btnError.addActionListener(this);
		v.dlgVictoria.addWindowListener(this);
		v.revancha.addActionListener(this);
		v.salir.addActionListener(this);
		v.volver.addActionListener(this);
		v.ayuda.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(v.jugar)) 
		{
			v.jugadores.setLocationRelativeTo(null);
			v.jugadores.setResizable(false);
			v.jugadores.setVisible(true);
		} 
		else if (e.getSource().equals(v.aceptar)) 
		{
			v.numJugadores = v.choJugadores.getSelectedIndex() + 2;;
			v.jugadores.setVisible(false);
			v.detallesJugadores.removeAll();
			v.detallesJugadores.setLayout(new GridLayout(v.numJugadores + 1, 2));
			v.detallesJugadores.setSize(400, 50 * v.numJugadores);
			v.detallesJugadores.setResizable(false);
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
			v.detallesJugadores.add(v.limpiar);
			v.detallesJugadores.setVisible(true);
		}
		else if (e.getSource().equals(v.comenzar)) 
		{
			if(v.numJugadores == 2 & (v.nombreRojo.getText().equals("") | v.nombreAmarillo.getText().equals("")) | v.numJugadores == 3 & (v.nombreRojo.getText().equals("") | v.nombreAzul.getText().equals("") | v.nombreAmarillo.getText().equals("")) | v.numJugadores == 4 & (v.nombreRojo.getText().equals("") | v.nombreAzul.getText().equals("") | v.nombreAmarillo.getText().equals("") | v.nombreVerde.getText().equals("")))
			{
				v.dlgError.setLayout(new FlowLayout());
				v.dlgError.setSize(225, 100);
				v.lblError.setText("Debe escribir todos los nombres");
				v.dlgError.add(v.lblError);
				v.dlgError.add(v.btnError);
				v.dlgError.setLocationRelativeTo(null);
				v.dlgError.setResizable(false);
				v.dlgError.setVisible(true);
			}
			else if(v.numJugadores == 2 & (v.nombreRojo.getText().equals(v.nombreAmarillo.getText())) | v.numJugadores == 3 & (v.nombreRojo.getText().equals(v.nombreAmarillo.getText()) | v.nombreRojo.getText().equals(v.nombreAzul.getText()) | v.nombreAzul.getText().equals(v.nombreAmarillo.getText())) | v.numJugadores == 4 & (v.nombreRojo.getText().equals(v.nombreAzul.getText()) | v.nombreRojo.getText().equals(v.nombreAmarillo.getText()) | v.nombreRojo.getText().equals(v.nombreVerde.getText()) | v.nombreAzul.getText().equals(v.nombreAmarillo.getText()) | v.nombreAzul.getText().equals(v.nombreVerde.getText()) | v.nombreAmarillo.getText().equals(v.nombreVerde.getText())))
			{
				v.dlgError.setLayout(new FlowLayout());
				v.dlgError.setSize(275, 100);
				v.lblError.setText("Los nombres deben ser diferentes entre sí");
				v.dlgError.add(v.lblError);
				v.dlgError.add(v.btnError);
				v.dlgError.setLocationRelativeTo(null);
				v.dlgError.setResizable(false);
				v.dlgError.setVisible(true);
			}
			else
			{
				v.detallesJugadores.setVisible(false);
				this.v.setVisible(false);
				v.partida.setLayout(new BorderLayout());
				v.partida.setVisible(true);
				v.partida.setSize(1200, 838);
				v.partida.setLocationRelativeTo(null);
				v.partida.setResizable(false);
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
				File pdfFile = new File("Manual de Usuario Juego.pdf");
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
		else if(e.getSource().equals(v.si))
		{
			System.exit(0);
		}
		else if(e.getSource().equals(v.no))
		{
			v.dlgSeguro.setVisible(false);
		}
		else if(e.getSource().equals(v.btnError))
		{
			v.dlgError.setVisible(false);
		}
		else if(e.getSource().equals(v.revancha))
		{
			v.turno = 1;
			v.anchuraRojo = 120;
			v.alturaRojo = 120;
			v.anchuraAzul = 120;
			v.alturaAzul = 640;
			v.anchuraAmarillo = 640;
			v.alturaAmarillo = 640;
			v.anchuraVerde = 640;
			v.alturaVerde = 120;
			v.metaRojo = 0;
			v.metaAzul = 0;
			v.metaAmarillo = 0;
			v.metaVerde = 0;
			v.salidaRojo = false;
			v.salidaAzul = false;
			v.salidaAmarillo = false;
			v.salidaVerde = false;
			v.resultado.setText("");
			v.tableroPanel.repaint();
			v.dlgVictoria.setVisible(false);

		}
		else if(e.getSource().equals(v.salir))
		{
			m.conectar();
			if(v.numJugadores == 2)
			{
				m.rellenarDatos2(v.nombreRojo.getText(), v.nombreAmarillo.getText(), v.victoriasRojo, v.victoriasAmarillo);
				System.exit(0);
			}
			else if(v.numJugadores == 3)
			{
				m.rellenarDatos3(v.nombreRojo.getText(), v.nombreAzul.getText(), v.nombreAmarillo.getText(), v.victoriasRojo, v.victoriasAzul, v.victoriasAmarillo);
				System.exit(0);
			}
			else if(v.numJugadores == 4)
			{
				m.rellenarDatos4(v.nombreRojo.getText(), v.nombreAzul.getText(), v.nombreAmarillo.getText(), v.nombreVerde.getText(), v.victoriasRojo, v.victoriasAzul, v.victoriasAmarillo, v.victoriasVerde);
				System.exit(0);
			}
			m.desconectar();
		}
		else if (e.getSource().equals(v.limpiar)) 
		{
			v.nombreRojo.setText("");
			v.nombreAmarillo.setText("");
			v.nombreAzul.setText("");
			v.nombreVerde.setText("");
			v.nombreRojo.requestFocus();
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
			v.dlgSeguro.setLayout(new FlowLayout());
			v.dlgSeguro.setLocationRelativeTo(null);
			v.dlgSeguro.setSize(200, 100);
			v.dlgSeguro.add(v.lblSeguro);
			v.dlgSeguro.add(v.si);
			v.dlgSeguro.add(v.no);
			v.dlgSeguro.setVisible(true);
		}
		else if(v.dlgSeguro.isActive())
		{
			v.dlgSeguro.setVisible(false);
		}
		else if(v.dlgError.isActive())
		{
			v.dlgError.setVisible(false);
		}
		else if(v.dlgEstadisticas.isActive())
		{
			v.dlgEstadisticas.setVisible(false);
		}
		else if(v.dlgVictoria.isActive())
		{
			m.conectar();
			if(v.numJugadores == 2)
			{
				m.rellenarDatos2(v.nombreRojo.getText(), v.nombreAmarillo.getText(), v.victoriasRojo, v.victoriasAmarillo);
				System.exit(0);
			}
			else if(v.numJugadores == 3)
			{
				m.rellenarDatos3(v.nombreRojo.getText(), v.nombreAzul.getText(), v.nombreAmarillo.getText(), v.victoriasRojo, v.victoriasAzul, v.victoriasAmarillo);
				System.exit(0);
			}
			else if(v.numJugadores == 4)
			{
				m.rellenarDatos4(v.nombreRojo.getText(), v.nombreAzul.getText(), v.nombreAmarillo.getText(), v.nombreVerde.getText(), v.victoriasRojo, v.victoriasAzul, v.victoriasAmarillo, v.victoriasVerde);
				System.exit(0);
			}
			m.desconectar();
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
			v.resultado.setText(String.valueOf(tirada));
			if(v.numJugadores == 2)
			{
				if(v.turno == 1) 
				{
					v.resultado.setForeground(Color.red);
					if(tirada == 5 & v.salidaRojo == false) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
						v.salidaRojo = true;
					}
					else if(v.salidaRojo == true)
					{
						v.metaRojo = v.metaRojo + tirada;
						if(v.metaRojo > 71)
						{
							v.metaRojo = m.restoRojo(v.metaRojo);
						}
						switch(v.metaRojo)
						{
						case 1:
							v.anchuraRojo = 280;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraRojo = 280;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraRojo = 320;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraRojo = 280;
							v.alturaRojo = 319;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraRojo = 239;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraRojo = 199;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraRojo = 160;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraRojo = 120;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraRojo = 80;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraRojo = 40;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraRojo = 1;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraRojo = 1;
							v.alturaRojo = 359;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraRojo = 1;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraRojo = 40;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraRojo = 80;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraRojo = 120;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraRojo = 160;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraRojo = 199;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraRojo = 239;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraRojo = 281;
							v.alturaRojo = 441;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraRojo = 319;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraRojo = 281;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraRojo = 281;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraRojo = 281;
							v.alturaRojo = 602;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraRojo = 281;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraRojo = 281;
							v.alturaRojo = 681;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraRojo = 281;
							v.alturaRojo = 721;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraRojo = 281;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraRojo = 361;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraRojo = 481;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraRojo = 481;
							v.alturaRojo = 720;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraRojo = 481;
							v.alturaRojo = 680;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraRojo = 481;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraRojo = 481;
							v.alturaRojo = 601;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraRojo = 481;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraRojo = 481;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraRojo = 441;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraRojo = 481;
							v.alturaRojo = 442;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraRojo = 522;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraRojo = 562;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraRojo = 601;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraRojo = 641;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraRojo = 680;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraRojo = 720;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraRojo = 760;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraRojo = 760;
							v.alturaRojo = 401;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraRojo = 760;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraRojo = 720;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraRojo = 680;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraRojo = 641;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraRojo = 602;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraRojo = 562;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraRojo = 522;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraRojo = 482;
							v.alturaRojo = 320;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraRojo = 442;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraRojo = 482;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraRojo = 482;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraRojo = 482;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraRojo = 482;
							v.alturaRojo = 119;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraRojo = 482;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraRojo = 482;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraRojo = 482;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraRojo = 400;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraRojo = 400;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraRojo = 400;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraRojo = 400;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraRojo = 400;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraRojo = 400;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraRojo = 400;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraRojo = 400;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraRojo = 380;
							v.alturaRojo = 340;
							v.tableroPanel.repaint();
							v.victoriasRojo = v.victoriasRojo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorRojo = 0;
						v.turno = 2;
					}
					else if(tirada == 6 & v.salidaRojo == true)
					{
						v.contadorRojo = v.contadorRojo + 1;
						if(v.contadorRojo == 3)
						{
							v.anchuraRojo = 120;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							v.salidaRojo = false;
							v.contadorRojo = 0;
							v.metaRojo = 0;
							v.turno = 2;
						}
					}
				} 
				else if(v.turno == 2) 
				{
					v.resultado.setForeground(Color.yellow);
					if(tirada == 5 & v.salidaAmarillo == false) 
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
						v.salidaAmarillo = true;
					}
					else if(v.salidaAmarillo == true)
					{
						v.metaAmarillo = v.metaAmarillo + tirada;
						if(v.metaAmarillo > 71)
						{
							v.metaAmarillo = m.restoAmarillo(v.metaAmarillo);
						}
						switch(v.metaAmarillo)
						{
						case 1:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraAmarillo = 441;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 442;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraAmarillo = 601;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 401;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraAmarillo = 602;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 320;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraAmarillo = 442;
							v.alturaAmarillo = 280;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 159;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 119;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 80;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 40;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraAmarillo = 400;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 42;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 81;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 121;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 160;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraAmarillo = 320;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 319;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 359;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 441;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraAmarillo = 319;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 602;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 681;	
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 721;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 720;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 680;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 601;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 561;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraAmarillo = 381;
							v.alturaAmarillo = 421;
							v.tableroPanel.repaint();
							v.victoriasAmarillo = v.victoriasAmarillo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorAmarillo = 0;
						v.turno = 1;
					}
					else if(tirada == 6 & v.salidaAmarillo == true)
					{
						v.contadorAmarillo = v.contadorAmarillo + 1;
						if(v.contadorAmarillo == 3)
						{
							v.anchuraAmarillo = 640;
							v.alturaAmarillo = 640;
							v.tableroPanel.repaint();
							v.salidaAmarillo = false;
							v.contadorAmarillo = 0;
							v.metaAmarillo = 0;
							v.turno = 1;
						}
					}
				}
			}
			else if(v.numJugadores == 3)
			{
				if(v.turno == 1)
				{
					v.resultado.setForeground(Color.red);
					if(tirada == 5 & v.salidaRojo == false) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
						v.salidaRojo = true;
					}
					else if(v.salidaRojo == true)
					{
						v.metaRojo = v.metaRojo + tirada;
						if(v.metaRojo > 71)
						{
							v.metaRojo = m.restoRojo(v.metaRojo);
						}
						switch(v.metaRojo)
						{
						case 1:
							v.anchuraRojo = 280;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraRojo = 280;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraRojo = 320;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraRojo = 280;
							v.alturaRojo = 319;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraRojo = 239;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraRojo = 199;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraRojo = 160;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraRojo = 120;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraRojo = 80;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraRojo = 40;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraRojo = 1;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraRojo = 1;
							v.alturaRojo = 359;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraRojo = 1;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraRojo = 40;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraRojo = 80;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraRojo = 120;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraRojo = 160;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraRojo = 199;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraRojo = 239;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraRojo = 281;
							v.alturaRojo = 441;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraRojo = 319;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraRojo = 281;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraRojo = 281;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraRojo = 281;
							v.alturaRojo = 602;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraRojo = 281;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraRojo = 281;
							v.alturaRojo = 681;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraRojo = 281;
							v.alturaRojo = 721;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraRojo = 281;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraRojo = 361;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraRojo = 481;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraRojo = 481;
							v.alturaRojo = 720;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraRojo = 481;
							v.alturaRojo = 680;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraRojo = 481;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraRojo = 481;
							v.alturaRojo = 601;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraRojo = 481;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraRojo = 481;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraRojo = 441;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraRojo = 481;
							v.alturaRojo = 442;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraRojo = 522;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraRojo = 562;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraRojo = 601;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraRojo = 641;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraRojo = 680;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraRojo = 720;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraRojo = 760;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraRojo = 760;
							v.alturaRojo = 401;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraRojo = 760;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraRojo = 720;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraRojo = 680;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraRojo = 641;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraRojo = 602;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraRojo = 562;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraRojo = 522;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraRojo = 482;
							v.alturaRojo = 320;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraRojo = 442;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraRojo = 482;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraRojo = 482;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraRojo = 482;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraRojo = 482;
							v.alturaRojo = 119;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraRojo = 482;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraRojo = 482;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraRojo = 482;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraRojo = 400;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraRojo = 400;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraRojo = 400;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraRojo = 400;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraRojo = 400;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraRojo = 400;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraRojo = 400;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraRojo = 400;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraRojo = 380;
							v.alturaRojo = 340;
							v.tableroPanel.repaint();
							v.victoriasRojo = v.victoriasRojo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorRojo = 0;
						v.turno = 2;
					}
					else if(tirada == 6 & v.salidaRojo == true)
					{
						v.contadorRojo = v.contadorRojo + 1;
						if(v.contadorRojo == 3)
						{
							v.anchuraRojo = 120;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							v.salidaRojo = false;
							v.contadorRojo = 0;
							v.metaRojo = 0;
							v.turno = 2;
						}
					}
				}
				else if(v.turno == 2)
				{
					v.resultado.setForeground(Color.blue);
					if(tirada == 5 & v.salidaAzul == false)
					{
						v.anchuraAzul = 160;
						v.alturaAzul = 483;
						v.tableroPanel.repaint();
						v.salidaAzul = true;
					}
					else if(v.salidaAzul == true)
					{
						v.metaAzul = v.metaAzul + tirada;
						if(v.metaAzul > 71)
						{
							v.metaAzul = m.restoAzul(v.metaAzul);
						}
						switch(v.metaAzul)
						{
						case 1:
							v.anchuraAzul = 199;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraAzul = 239;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraAzul = 281;
							v.alturaAzul = 441;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraAzul = 319;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraAzul = 281;
							v.alturaAzul = 522;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraAzul = 281;
							v.alturaAzul = 562;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraAzul = 281;
							v.alturaAzul = 602;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraAzul = 281;
							v.alturaAzul = 641;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraAzul = 281;
							v.alturaAzul = 681;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraAzul = 281;
							v.alturaAzul = 721;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraAzul = 281;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraAzul = 361;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraAzul = 481;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraAzul = 481;
							v.alturaAzul = 720;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraAzul = 481;
							v.alturaAzul = 680;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraAzul = 481;
							v.alturaAzul = 641;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraAzul = 481;
							v.alturaAzul = 601;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraAzul = 481;
							v.alturaAzul = 562;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraAzul = 481;
							v.alturaAzul = 522;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraAzul = 441;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraAzul = 481;
							v.alturaAzul = 442;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraAzul = 522;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraAzul = 562;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraAzul = 601;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraAzul = 641;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraAzul = 680;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraAzul = 720;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraAzul = 760;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraAzul = 760;
							v.alturaAzul = 401;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraAzul = 760;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraAzul = 720;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraAzul = 680;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraAzul = 641;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraAzul = 602;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraAzul = 562;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraAzul = 522;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraAzul = 482;
							v.alturaAzul = 320;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraAzul = 442;
							v.alturaAzul = 280;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraAzul = 482;
							v.alturaAzul = 239;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraAzul = 482;
							v.alturaAzul = 199;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraAzul = 482;
							v.alturaAzul = 159;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraAzul = 482;
							v.alturaAzul = 119;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraAzul = 482;
							v.alturaAzul = 80;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraAzul = 482;
							v.alturaAzul = 40;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraAzul = 482;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraAzul = 400;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraAzul = 280;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraAzul = 280;
							v.alturaAzul = 42;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraAzul = 280;
							v.alturaAzul = 81;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraAzul = 280;
							v.alturaAzul = 121;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraAzul = 280;
							v.alturaAzul = 160;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraAzul = 280;
							v.alturaAzul = 199;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraAzul = 280;
							v.alturaAzul = 239;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraAzul = 320;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraAzul = 280;
							v.alturaAzul = 319;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraAzul = 239;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraAzul = 199;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraAzul = 160;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraAzul = 120;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraAzul = 80;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraAzul = 40;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraAzul = 1;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraAzul = 1;
							v.alturaAzul = 359;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraAzul = 41;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraAzul = 81;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraAzul = 121;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraAzul = 161;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraAzul = 201;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraAzul = 241;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraAzul = 281;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraAmarillo = 381;
							v.alturaAmarillo = 421;
							v.tableroPanel.repaint();
							v.victoriasAzul = v.victoriasAzul + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorAzul = 0;
						v.turno = 3;
					}
					else if(tirada == 6 & v.salidaAzul == true)
					{
						v.contadorAzul = v.contadorAzul + tirada;
						if(v.contadorAzul == 3)
						{
							v.anchuraAzul = 120;
							v.alturaAzul = 640;
							v.tableroPanel.repaint();
							v.salidaAzul = false;
							v.contadorAzul = 0;
							v.metaAzul = 0;
							v.turno = 3;
						}
					}
				}
				else if(v.turno == 3)
				{
					v.resultado.setForeground(Color.yellow);
					if(tirada == 5 & v.salidaAmarillo == false)
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
						v.salidaAmarillo = true;
					}
					else if(v.salidaAmarillo == true)
					{
						v.metaAmarillo = v.metaAmarillo + tirada;
						if(v.metaAmarillo > 71)
						{
							v.metaAmarillo = m.restoAmarillo(v.metaAmarillo);
						}
						switch(v.metaAmarillo)
						{
						case 1:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraAmarillo = 441;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 442;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraAmarillo = 601;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 401;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraAmarillo = 602;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 320;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraAmarillo = 442;
							v.alturaAmarillo = 280;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 159;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 119;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 80;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 40;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraAmarillo = 400;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 42;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 81;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 121;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 160;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraAmarillo = 320;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 319;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 359;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 441;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraAmarillo = 319;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 602;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 681;	
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 721;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 720;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 680;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 601;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 561;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraAmarillo = 381;
							v.alturaAmarillo = 421;
							v.tableroPanel.repaint();
							v.victoriasAmarillo = v.victoriasAmarillo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorAmarillo = 0;
						v.turno = 1;
					}
					else if(tirada == 6 & v.salidaAmarillo == true)
					{
						v.contadorAmarillo = v.contadorAmarillo + 1;
						if(v.contadorAmarillo == 3)
						{
							v.anchuraAmarillo = 640;
							v.alturaAmarillo = 640;
							v.tableroPanel.repaint();
							v.salidaAmarillo = false;
							v.contadorAmarillo = 0;
							v.metaAmarillo = 0;
							v.turno = 1;
						}
					}
				}
			}
			else if(v.numJugadores == 4)
			{
				if(v.turno == 1)
				{
					v.resultado.setForeground(Color.red);
					if(tirada == 5 & v.salidaRojo == false) 
					{
						v.anchuraRojo = 280;
						v.alturaRojo = 160;
						v.tableroPanel.repaint();
						v.salidaRojo = true;
					}
					else if(v.salidaRojo == true)
					{
						v.metaRojo = v.metaRojo + tirada;
						if(v.metaRojo > 71)
						{
							v.metaRojo = m.restoRojo(v.metaRojo);
						}
						switch(v.metaRojo)
						{
						case 1:
							v.anchuraRojo = 280;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraRojo = 280;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraRojo = 320;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraRojo = 280;
							v.alturaRojo = 319;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraRojo = 239;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraRojo = 199;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraRojo = 160;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraRojo = 120;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraRojo = 80;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraRojo = 40;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraRojo = 1;
							v.alturaRojo = 279;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraRojo = 1;
							v.alturaRojo = 359;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraRojo = 1;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraRojo = 40;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraRojo = 80;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraRojo = 120;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraRojo = 160;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraRojo = 199;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraRojo = 239;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraRojo = 281;
							v.alturaRojo = 441;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraRojo = 319;
							v.alturaRojo = 481;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraRojo = 281;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraRojo = 281;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraRojo = 281;
							v.alturaRojo = 602;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraRojo = 281;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraRojo = 281;
							v.alturaRojo = 681;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraRojo = 281;
							v.alturaRojo = 721;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraRojo = 281;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraRojo = 361;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraRojo = 481;
							v.alturaRojo = 760;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraRojo = 481;
							v.alturaRojo = 720;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraRojo = 481;
							v.alturaRojo = 680;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraRojo = 481;
							v.alturaRojo = 641;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraRojo = 481;
							v.alturaRojo = 601;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraRojo = 481;
							v.alturaRojo = 562;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraRojo = 481;
							v.alturaRojo = 522;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraRojo = 441;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraRojo = 481;
							v.alturaRojo = 442;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraRojo = 522;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraRojo = 562;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraRojo = 601;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraRojo = 641;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraRojo = 680;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraRojo = 720;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraRojo = 760;
							v.alturaRojo = 482;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraRojo = 760;
							v.alturaRojo = 401;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraRojo = 760;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraRojo = 720;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraRojo = 680;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraRojo = 641;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraRojo = 602;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraRojo = 562;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraRojo = 522;
							v.alturaRojo = 281;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraRojo = 482;
							v.alturaRojo = 320;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraRojo = 442;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraRojo = 482;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraRojo = 482;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraRojo = 482;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraRojo = 482;
							v.alturaRojo = 119;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraRojo = 482;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraRojo = 482;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraRojo = 482;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraRojo = 400;
							v.alturaRojo = 1;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraRojo = 400;
							v.alturaRojo = 40;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraRojo = 400;
							v.alturaRojo = 80;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraRojo = 400;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraRojo = 400;
							v.alturaRojo = 159;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraRojo = 400;
							v.alturaRojo = 199;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraRojo = 400;
							v.alturaRojo = 239;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraRojo = 400;
							v.alturaRojo = 280;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraRojo = 380;
							v.alturaRojo = 340;
							v.tableroPanel.repaint();
							v.victoriasRojo = v.victoriasRojo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorRojo = 0;
						v.turno = 2;
					}
					else if(tirada == 6 & v.salidaRojo == true)
					{
						v.contadorRojo = v.contadorRojo + 1;
						if(v.contadorRojo == 3)
						{
							v.anchuraRojo = 120;
							v.alturaRojo = 120;
							v.tableroPanel.repaint();
							v.salidaRojo = false;
							v.contadorRojo = 0;
							v.metaRojo = 0;
							v.turno = 2;
						}
					}
				}
				else if(v.turno == 2)
				{
					v.resultado.setForeground(Color.blue);
					if(tirada == 5 & v.salidaAzul == false)
					{
						v.anchuraAzul = 160;
						v.alturaAzul = 483;
						v.tableroPanel.repaint();
						v.salidaAzul = true;
					}
					else if(v.salidaAzul == true)
					{
						v.metaAzul = v.metaAzul + tirada;
						if(v.metaAzul > 71)
						{
							v.metaAzul = m.restoAzul(v.metaAzul);
						}
						switch(v.metaAzul)
						{
						case 1:
							v.anchuraAzul = 199;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraAzul = 239;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraAzul = 281;
							v.alturaAzul = 441;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraAzul = 319;
							v.alturaAzul = 481;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraAzul = 281;
							v.alturaAzul = 522;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraAzul = 281;
							v.alturaAzul = 562;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraAzul = 281;
							v.alturaAzul = 602;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraAzul = 281;
							v.alturaAzul = 641;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraAzul = 281;
							v.alturaAzul = 681;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraAzul = 281;
							v.alturaAzul = 721;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraAzul = 281;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraAzul = 361;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraAzul = 481;
							v.alturaAzul = 760;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraAzul = 481;
							v.alturaAzul = 720;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraAzul = 481;
							v.alturaAzul = 680;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraAzul = 481;
							v.alturaAzul = 641;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraAzul = 481;
							v.alturaAzul = 601;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraAzul = 481;
							v.alturaAzul = 562;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraAzul = 481;
							v.alturaAzul = 522;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraAzul = 441;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraAzul = 481;
							v.alturaAzul = 442;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraAzul = 522;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraAzul = 562;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraAzul = 601;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraAzul = 641;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraAzul = 680;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraAzul = 720;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraAzul = 760;
							v.alturaAzul = 482;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraAzul = 760;
							v.alturaAzul = 401;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraAzul = 760;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraAzul = 720;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraAzul = 680;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraAzul = 641;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraAzul = 602;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraAzul = 562;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraAzul = 522;
							v.alturaAzul = 281;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraAzul = 482;
							v.alturaAzul = 320;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraAzul = 442;
							v.alturaAzul = 280;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraAzul = 482;
							v.alturaAzul = 239;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraAzul = 482;
							v.alturaAzul = 199;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraAzul = 482;
							v.alturaAzul = 159;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraAzul = 482;
							v.alturaAzul = 119;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraAzul = 482;
							v.alturaAzul = 80;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraAzul = 482;
							v.alturaAzul = 40;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraAzul = 482;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraAzul = 400;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraAzul = 280;
							v.alturaAzul = 1;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraAzul = 280;
							v.alturaAzul = 42;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraAzul = 280;
							v.alturaAzul = 81;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraAzul = 280;
							v.alturaAzul = 121;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraAzul = 280;
							v.alturaAzul = 160;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraAzul = 280;
							v.alturaAzul = 199;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraAzul = 280;
							v.alturaAzul = 239;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraAzul = 320;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraAzul = 280;
							v.alturaAzul = 319;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraAzul = 239;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraAzul = 199;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraAzul = 160;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraAzul = 120;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraAzul = 80;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraAzul = 40;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraAzul = 1;
							v.alturaAzul = 279;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraAzul = 1;
							v.alturaAzul = 359;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraAzul = 41;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraAzul = 81;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraAzul = 121;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraAzul = 161;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraAzul = 201;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraAzul = 241;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraAzul = 281;
							v.alturaAzul = 361;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraAmarillo = 381;
							v.alturaAmarillo = 421;
							v.tableroPanel.repaint();
							v.victoriasAzul = v.victoriasAzul + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorAzul = 0;
						v.turno = 3;
					}
					else if(tirada == 6 & v.salidaAzul == true)
					{
						v.contadorAzul = v.contadorAzul + tirada;
						if(v.contadorAzul == 3)
						{
							v.anchuraAzul = 120;
							v.alturaAzul = 640;
							v.tableroPanel.repaint();
							v.salidaAzul = false;
							v.contadorAzul = 0;
							v.metaAzul = 0;
							v.turno = 3;
						}
					}
				}
				else if(v.turno == 3)
				{
					v.resultado.setForeground(Color.yellow);
					if(tirada == 5 & v.salidaAmarillo == false)
					{
						v.anchuraAmarillo = 480;
						v.alturaAmarillo = 603;
						v.tableroPanel.repaint();
						v.salidaAmarillo = true;
					}
					else if(v.salidaAmarillo == true)
					{
						v.metaAmarillo = v.metaAmarillo + tirada;
						if(v.metaAmarillo > 71)
						{
							v.metaAmarillo = m.restoAmarillo(v.metaAmarillo);
						}
						switch(v.metaAmarillo)
						{
						case 1:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraAmarillo = 441;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraAmarillo = 481;
							v.alturaAmarillo = 442;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraAmarillo = 601;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 482;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 401;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraAmarillo = 760;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraAmarillo = 720;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraAmarillo = 680;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraAmarillo = 641;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraAmarillo = 602;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraAmarillo = 562;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraAmarillo = 522;
							v.alturaAmarillo = 281;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 320;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraAmarillo = 442;
							v.alturaAmarillo = 280;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 159;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 119;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 80;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 40;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraAmarillo = 482;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraAmarillo = 400;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 1;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 42;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 81;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 121;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 160;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 199;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 239;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraAmarillo = 320;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraAmarillo = 280;
							v.alturaAmarillo = 319;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 279;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 359;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraAmarillo = 1;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraAmarillo = 40;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraAmarillo = 80;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraAmarillo = 120;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraAmarillo = 160;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraAmarillo = 199;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraAmarillo = 239;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 441;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraAmarillo = 319;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 562;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 602;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 681;	
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 721;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraAmarillo = 281;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 760;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 720;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 680;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 641;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 601;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 561;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 522;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraAmarillo = 361;
							v.alturaAmarillo = 481;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraAmarillo = 381;
							v.alturaAmarillo = 421;
							v.tableroPanel.repaint();
							v.victoriasAmarillo = v.victoriasAmarillo + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorAmarillo = 0;
						v.turno = 4;
					}
					else if(tirada == 6 & v.salidaAmarillo == true)
					{
						v.contadorAmarillo = v.contadorAmarillo + 1;
						if(v.contadorAmarillo == 3)
						{
							v.anchuraAmarillo = 640;
							v.alturaAmarillo = 640;
							v.tableroPanel.repaint();
							v.salidaAmarillo = false;
							v.contadorAmarillo = 0;
							v.metaAmarillo = 0;
							v.turno = 4;
						}
					}
				}
				else if(v.turno == 4)
				{				
					v.resultado.setForeground(Color.green);
					if(tirada == 5 & v.salidaVerde == false)
					{
						v.anchuraVerde = 600;
						v.alturaVerde = 280;
						v.tableroPanel.repaint();
						v.salidaVerde = true;
					}
					else if(v.salidaVerde == true)
					{
						v.metaVerde = v.metaVerde + tirada;
						if(v.metaVerde > 71)
						{
							v.metaVerde = m.restoVerde(v.metaVerde);
						}
						switch(v.metaVerde)
						{
						case 1:
							v.anchuraVerde = 562;
							v.alturaVerde = 281;
							v.tableroPanel.repaint();
							break;
						case 2:
							v.anchuraVerde = 522;
							v.alturaVerde = 281;
							v.tableroPanel.repaint();
							break;
						case 3:
							v.anchuraVerde = 482;
							v.alturaVerde = 320;
							v.tableroPanel.repaint();
							break;
						case 4:
							v.anchuraVerde = 442;
							v.alturaVerde = 280;
							v.tableroPanel.repaint();
							break;
						case 5:
							v.anchuraVerde = 482;
							v.alturaVerde = 239;
							v.tableroPanel.repaint();
							break;
						case 6:
							v.anchuraVerde = 482;
							v.alturaVerde = 199;
							v.tableroPanel.repaint();
							break;
						case 7:
							v.anchuraVerde = 482;
							v.alturaVerde = 159;
							v.tableroPanel.repaint();
							break;
						case 8:
							v.anchuraVerde = 482;
							v.alturaVerde = 119;
							v.tableroPanel.repaint();
							break;
						case 9:
							v.anchuraVerde = 482;
							v.alturaVerde = 80;
							v.tableroPanel.repaint();
							break;
						case 10:
							v.anchuraVerde = 482;
							v.alturaVerde = 40;
							v.tableroPanel.repaint();
							break;
						case 11:
							v.anchuraVerde = 482;
							v.alturaVerde = 1;
							v.tableroPanel.repaint();
							break;
						case 12:
							v.anchuraVerde = 400;
							v.alturaVerde = 1;
							v.tableroPanel.repaint();
							break;
						case 13:
							v.anchuraVerde = 280;
							v.alturaVerde = 1;
							v.tableroPanel.repaint();
							break;
						case 14:
							v.anchuraVerde = 280;
							v.alturaVerde = 42;
							v.tableroPanel.repaint();
							break;
						case 15:
							v.anchuraVerde = 280;
							v.alturaVerde = 81;
							v.tableroPanel.repaint();
							break;
						case 16:
							v.anchuraVerde = 280;
							v.alturaVerde = 121;
							v.tableroPanel.repaint();
							break;
						case 17:
							v.anchuraVerde = 280;
							v.alturaVerde = 160;
							v.tableroPanel.repaint();
							break;
						case 18:
							v.anchuraVerde = 280;
							v.alturaVerde = 199;
							v.tableroPanel.repaint();
							break;
						case 19:
							v.anchuraVerde = 280;
							v.alturaVerde = 239;
							v.tableroPanel.repaint();
							break;
						case 20:
							v.anchuraVerde = 320;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 21:
							v.anchuraVerde = 280;
							v.alturaVerde = 319;
							v.tableroPanel.repaint();
							break;
						case 22:
							v.anchuraVerde = 239;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 23:
							v.anchuraVerde = 199;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 24:
							v.anchuraVerde = 160;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 25:
							v.anchuraVerde = 120;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 26:
							v.anchuraVerde = 80;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 27:
							v.anchuraVerde = 40;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 28:
							v.anchuraVerde = 1;
							v.alturaVerde = 279;
							v.tableroPanel.repaint();
							break;
						case 29:
							v.anchuraVerde = 1;
							v.alturaVerde = 359;
							v.tableroPanel.repaint();
							break;
						case 30:
							v.anchuraVerde = 1;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 31:
							v.anchuraVerde = 40;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 32:
							v.anchuraVerde = 80;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 33:
							v.anchuraVerde = 120;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 34:
							v.anchuraVerde = 160;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 35:
							v.anchuraVerde = 199;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 36:
							v.anchuraVerde = 239;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 37:
							v.anchuraVerde = 281;
							v.alturaVerde = 441;
							v.tableroPanel.repaint();
							break;
						case 38:
							v.anchuraVerde = 319;
							v.alturaVerde = 481;
							v.tableroPanel.repaint();
							break;
						case 39:
							v.anchuraVerde = 281;
							v.alturaVerde = 522;
							v.tableroPanel.repaint();
							break;
						case 40:
							v.anchuraVerde = 281;
							v.alturaVerde = 562;
							v.tableroPanel.repaint();
							break;
						case 41:
							v.anchuraVerde = 281;
							v.alturaVerde = 602;
							v.tableroPanel.repaint();
							break;
						case 42:
							v.anchuraVerde = 281;
							v.alturaVerde = 641;
							v.tableroPanel.repaint();
							break;
						case 43:
							v.anchuraVerde = 281;
							v.alturaVerde = 681;	
							v.tableroPanel.repaint();
							break;
						case 44:
							v.anchuraVerde = 281;
							v.alturaVerde = 721;
							v.tableroPanel.repaint();
							break;
						case 45:
							v.anchuraVerde = 281;
							v.alturaVerde = 760;
							v.tableroPanel.repaint();
							break;
						case 46:
							v.anchuraVerde = 361;
							v.alturaVerde = 760;
							v.tableroPanel.repaint();
							break;
						case 47:
							v.anchuraVerde = 481;
							v.alturaVerde = 760;
							v.tableroPanel.repaint();
							break;
						case 48:
							v.anchuraVerde = 481;
							v.alturaVerde = 720;
							v.tableroPanel.repaint();
							break;
						case 49:
							v.anchuraVerde = 481;
							v.alturaVerde = 680;
							v.tableroPanel.repaint();
							break;
						case 50:
							v.anchuraVerde = 481;
							v.alturaVerde = 641;
							v.tableroPanel.repaint();
							break;
						case 51:
							v.anchuraVerde = 481;
							v.alturaVerde = 601;
							v.tableroPanel.repaint();
							break;
						case 52:
							v.anchuraVerde = 481;
							v.alturaVerde = 562;
							v.tableroPanel.repaint();
							break;
						case 53:
							v.anchuraVerde = 481;
							v.alturaVerde = 522;
							v.tableroPanel.repaint();
							break;
						case 54:
							v.anchuraVerde = 441;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 55:
							v.anchuraVerde = 481;
							v.alturaVerde = 442;
							v.tableroPanel.repaint();
							break;
						case 56:
							v.anchuraVerde = 522;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 57:
							v.anchuraVerde = 562;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 58:
							v.anchuraVerde = 601;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 59:
							v.anchuraVerde = 641;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 60:
							v.anchuraVerde = 680;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 61:
							v.anchuraVerde = 720;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 62:
							v.anchuraVerde = 760;
							v.alturaVerde = 482;
							v.tableroPanel.repaint();
							break;
						case 63:
							v.anchuraVerde = 760;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 64:
							v.anchuraVerde = 720;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 65:
							v.anchuraVerde = 680;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 66:
							v.anchuraVerde = 640;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 67:
							v.anchuraVerde = 600;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 68:
							v.anchuraVerde = 560;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 69:
							v.anchuraVerde = 520;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 70:
							v.anchuraVerde = 480;
							v.alturaVerde = 401;
							v.tableroPanel.repaint();
							break;
						case 71:
							v.anchuraVerde = 420;
							v.alturaVerde = 381;
							v.tableroPanel.repaint();
							v.victoriasVerde = v.victoriasVerde + 1;
							v.dlgVictoria.setLayout(new FlowLayout());
							v.dlgVictoria.setLocationRelativeTo(null);
							v.dlgVictoria.setSize(300, 100);
							v.dlgVictoria.add(v.lblVictoria);
							v.dlgVictoria.add(v.revancha);
							v.dlgVictoria.add(v.salir);
							v.dlgVictoria.setResizable(false);
							v.dlgVictoria.setVisible(true);
							break;
						}
					}
					if(tirada != 6)
					{
						v.contadorVerde = 0;
						v.turno = 1;
					}
					else if(tirada == 6 & v.salidaVerde == true)
					{
						v.contadorVerde = v.contadorVerde + 1;
						if(v.contadorVerde == 3)
						{
							v.anchuraVerde = 640;
							v.alturaAmarillo = 120;
							v.tableroPanel.repaint();
							v.salidaVerde = false;
							v.contadorVerde = 0;
							v.metaVerde = 0;
							v.turno = 1;
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