package juego;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

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

	public Vista() {
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

	public void addMenuListeners(WindowListener windowListener, ActionListener actionListener) {
		addWindowListener(windowListener);
		jugadores.addWindowListener(windowListener);
		detallesJugadores.addWindowListener(windowListener);
		jugar.addActionListener(actionListener);
		aceptar.addActionListener(actionListener);
	}

	public void showJugadorDialog() {
		jugadores.setLocationRelativeTo(this);
		jugadores.setVisible(true);
	}

	public void hideJugadorDialog() {
		jugadores.setVisible(false);
	}

	public void showDetallesJugadores(int numJugadores) {
		detallesJugadores.removeAll();
		detallesJugadores.setLayout(new GridLayout(numJugadores + 1, 2));
		detallesJugadores.setSize(300, 50 * numJugadores);
		detallesJugadores.setBackground(Color.blue);

		for (int i = 1; i <= numJugadores; i++) {
			detallesJugadores.add(new Label("Nombre del jugador " + i + ":"));
			detallesJugadores.add(new TextField());
		}

		detallesJugadores.setVisible(true);
	}

	public int getNumeroDeJugadores() {
		return choJugadores.getSelectedIndex() + 2;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(imagen, 55, 15, this);
	}
}