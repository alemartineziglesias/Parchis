package juego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;

public class Menu extends Frame implements WindowListener, ActionListener 
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

	public Menu() 
	{
		setBackground(Color.blue);
		setLocation(550, 250);
		setSize(300, 225);
		setTitle("Parchís");
		setResizable(false);
		addWindowListener(this);
		herramienta = getToolkit();
		imagen = herramienta.getImage("logo.png");
		setLayout(new BorderLayout());
		Panel panelBotones = new Panel();
		panelBotones.setLayout(new FlowLayout());
		Panel botonesVertical = new Panel();
		botonesVertical.setLayout(new BoxLayout(botonesVertical, BoxLayout.Y_AXIS));
		botonesVertical.add(jugar);
		jugar.addActionListener(this);
		botonesVertical.add(ayuda);
		ayuda.addActionListener(this);
		botonesVertical.add(estadisticas);
		estadisticas.addActionListener(this);
		panelBotones.add(botonesVertical);
		add(panelBotones, BorderLayout.SOUTH);
		jugadores.setLayout(new GridLayout(4, 1));
		jugadores.setSize(200, 150);
		jugadores.addWindowListener(this);
		jugadores.setBackground(Color.blue);
		jugadores.add(lblJugadores);
		choJugadores.add("2 jugadores");
		choJugadores.add("3 jugadores");
		choJugadores.add("4 jugadores");
		jugadores.add(choJugadores);
		jugadores.add(aceptar);
		aceptar.addActionListener(this);
		setVisible(true);
	}

	public static void main(String[] args) 
	{
		new Menu();
	}

	public void paint(Graphics g) 
	{
		g.drawImage(imagen, 55, 15, this);
	}

	public void windowActivated(WindowEvent e) 
	{
		
	}

	public void windowClosed(WindowEvent e) 
	{
		
	}

	public void windowClosing(WindowEvent e) 
	{
		if (jugadores.isActive()) 
		{
			jugadores.setVisible(false);
		} 
		else 
		{
			System.exit(0);
		}
	}

	public void windowDeactivated(WindowEvent e) 
	{
		
	}

	public void windowDeiconified(WindowEvent e) 
	{
		
	}

	public void windowIconified(WindowEvent e) 
	{
		
	}

	public void windowOpened(WindowEvent e) 
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(jugar)) 
		{
			jugadores.setLocationRelativeTo(this);
			jugadores.setVisible(true);
		}
	}
}