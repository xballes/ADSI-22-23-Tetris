package modelo;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor {

	/* Está clase permite que se reproduzcan audios en segundo plano tras haber sido llamada para que empiece a 
	 * hacer sonar algo concreto (tanto música como efectos especiales como los que suenan cuando se limpian lineas
	 * y se obtienen puntos
	  
	  
	  CONSEJO: No pinches en el Project Explorer en los archivos .wav, Eclipse al menos tarda mucho en abrirlos
	           y se muestran en un formato inentendible (son archivos de música, no deberían ser leídos directamente
	           en el IDE).
	  
	 */
	
	
	
	
	
	private String[] archivos;	// Los nombres de los 3 archivos de música
	private Clip clip; // La música que se está reproduciendo actualmente se almacena aquí
	private Clip lineaLimpia; // El sonido de limpiar entre 1-3 lineas se almacena aquí
	private Clip tetris; // El sonido de limpiar 4 lineas de una (hacer un tetris) se almacena aquí
	private Clip gameOver; // La música que suena al acabar la partida se almacena aquí
	
	public Reproductor () {
		this.archivos = new String[3];
	
		this.archivos[0] = "tetrisOST";
		this.archivos[1]="tetrisMario";
		this.archivos[2]="versionModerna1";

		try { 
			
			// Inicializar los Clips que almacenan siempre de manera constante un único archivo de audio al instanciar la clase

			AudioInputStream audio = AudioSystem.getAudioInputStream(Reproductor.class.getResource("lineaLimpia.wav"));
			this.lineaLimpia = AudioSystem.getClip();
			this.lineaLimpia.open(audio);

			audio = AudioSystem.getAudioInputStream(Reproductor.class.getResource("tetris.wav"));
			this.tetris = AudioSystem.getClip();
			this.tetris.open(audio);
			
			audio = AudioSystem.getAudioInputStream(Reproductor.class.getResource("gameOver.wav"));
			this.gameOver = AudioSystem.getClip();
			this.gameOver.open(audio);

		} catch (Exception e) {
			System.out.println("DEBUG: PATH NO ENCONTRADO");
		}
}
		

	
	
	public void empezarMusica(int pId) {
		
		/* Pre: Id 0-2. Dicho int apunta al nombre de la cancion que se desea reproducir en la array archivos. El .wav en cuestion está en este mismo package
		        
		   Post: Se empieza a  reproducir la música en loop infinito hasta que se le llame al metodo de pausar o terminar   
		
		
		
		*/
		
		try { 
			
			if (this.clip == null) {
				
				
				// Inicializar el clip con la música correspondiente
				AudioInputStream audio = AudioSystem.getAudioInputStream(Reproductor.class.getResource(this.archivos[pId]+".wav"));
				this.clip = AudioSystem.getClip();
				this.clip.open(audio);
				
				// Empezar a sonar
				
				this.clip.start();
				
				// Marcar bucle en todo el audio (0 indica que empieza en el primer frame, -1 indica que acaba en el último frame <--MIRAR DOCUMENTANCION CLIP EN ORACLE PARA MAS INFORMACIÓN)
				
				this.clip.setLoopPoints(0, -1);
			
				// Hacer el bucle infinitas veces
				
				this.clip.loop(Clip.LOOP_CONTINUOUSLY);
			}

		} catch (Exception e) {
			System.out.println("DEBUG: NPATH NO ENCONTRADO");
		}

		

		

		
		
	}


	

	public void pausarMusica() {

	/*   Pre: Hay musica en reproduccion, ya que el atributo clip no es null
 	     Post: Se pausa la musica, pero no se pierde el puntero a la reproduccion, llamando a despausarMusica() sera posible continuar la reproduccion
		
		  
	  
	 */

		
		if (this.clip != null) {this.clip.stop();}
		else {System.out.println("DEBUG: El m�todo no se ejectuto, elige antes que m�sica quieres que toque");}

	}

	public void despausarMusica() {
		
		/*   Pre: Hay musica pausada, ya que el atributo clip no es null
	         Post: Se ha restablecido de nuevo que se toque en bucle y se continua la música desde donde se pausó
		
		  
	  
	 */


		if (this.clip != null) {
			this.clip.setLoopPoints(0, -1);
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
			this.clip.start();
		}
		else {System.out.println("DEBUG: El m�todo no se ejectuto, elige antes que m�sica quieres que toque");}	

}
	public void acabarMusica(boolean pGameOver) {
		
		
		/*   Pre: boolean indica si se quiere dejar de tocar la música porque la partida acabo (true)
		 *        o porque le dio a guardar para seguir más tarde (false)
        Post: Se ha parado por completo la música y se ha tocado el audio de gameOver si el jugador acabó la partida
	
	  
 
		 */
		
		this.clip.stop();
		this.clip = null;

		if (pGameOver) {
			this.gameOver.setMicrosecondPosition(0);
			this.gameOver.start();	
		}
		


	}
	
	
	public void sonarLineaLimpia() {
		
		// Post: Se ha hecho sonar el audio de limpiar 1-3 lineas
		
		this.lineaLimpia.setMicrosecondPosition(0);
		this.lineaLimpia.start();
		
		
		
	}
	
	public void sonarTetris() {
		
		// Post: Se ha hecho sonar el audio de limpiar 4 lineas (tetris)

		
			this.tetris.setMicrosecondPosition(0);
			this.tetris.start();
		}
		
	
	
	
	
	
	
	
}