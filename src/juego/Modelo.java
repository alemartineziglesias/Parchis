package juego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Modelo 
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/parchis";
	String login = "root";
	String password = "Mr9+agar";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
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
}