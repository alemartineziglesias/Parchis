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

	public int restoRojo(int metaRojo)
	{
		int resto;
		resto = metaRojo - 71;
		metaRojo = 71 - resto;
		return metaRojo;
	}

	public int restoAmarillo(int metaAmarillo) 
	{
		int resto;
		resto = metaAmarillo - 71;
		metaAmarillo = 71 - resto;
		return metaAmarillo;
	}

	public int restoAzul(int metaAzul) 
	{
		int resto;
		resto = metaAzul - 71;
		metaAzul = 71 - resto;
		return metaAzul;
	}

	public int restoVerde(int metaVerde) 
	{
		int resto;
		resto = metaVerde - 71;
		metaVerde = 71 - resto;
		return metaVerde;
	}

	public void rellenarDatos2(String nombreRojo, String nombreAmarillo, int victoriasRojo, int victoriasAmarillo)
	{
		String sentencia = "INSERT INTO jugadores VALUES (null, '" + nombreRojo + "', " + victoriasRojo + "), (null, '" + nombreAmarillo + "', " + victoriasAmarillo + ");";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}

	public void rellenarDatos3(String nombreRojo, String nombreAzul, String nombreAmarillo, int victoriasRojo, int victoriasAzul, int victoriasAmarillo)
	{
		String sentencia = "INSERT INTO jugadores VALUES (null, '" + nombreRojo + "', " + victoriasRojo + "), (null, '" + nombreAmarillo + "', " + victoriasAmarillo + "), (null, '" + nombreAzul + "', " + victoriasAzul + ");";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}


	public void rellenarDatos4(String nombreRojo, String nombreAzul, String nombreAmarillo, String nombreVerde, int victoriasRojo, int victoriasAzul, int victoriasAmarillo, int victoriasVerde)
	{
		String sentencia = "INSERT INTO jugadores VALUES (null, '" + nombreRojo + "', " + victoriasRojo + "), (null, '" + nombreAmarillo + "', " + victoriasAmarillo + "), (null, '" + nombreAzul + "', " + victoriasAzul + "), (null, '" + nombreVerde + "', " + victoriasVerde + ");";
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}
}