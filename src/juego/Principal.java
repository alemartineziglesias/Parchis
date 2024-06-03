package juego;

public class Principal
{

	public static void main(String[] args) 
    {
        Modelo model = new Modelo();
        Vista view = new Vista();
        new Controlador(model, view);
    }

}
