package controlador;

public class GestorPremios {

private static GestorPremios puntero;	
	
	
	private GestorPremios() {}
	
	public static GestorPremios getInstancia()
	{
		if (GestorPremios.puntero == null) {GestorPremios.puntero = new GestorPremios();}
		return GestorPremios.puntero;
	}
	
	
	
}
