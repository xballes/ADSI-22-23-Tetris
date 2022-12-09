package modelo;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor {

	
	
	
	private String[] archivos;	
	private Clip clip;
	private Clip lineaLimpia;
	private Clip tetris;
	private Clip gameOver;
	
	public Reproductor () {
		this.archivos = new String[1];
	
		this.archivos[0] = "tetrisOST";

		try { 

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
		
		/* Pre: int en dominio de array archivos. Dicho int apunta al nombre de la cancion que se desea reproducir en la array archivos. El .wav que tiene dicho nombre existe en la carpeta de recursos "materiales"
		        
		   Post: Se empezará a reproducir la música en loop infinito hasta que se le llame al metodo de pausar o terminar   
		
		
		
		*/
		
		try { 
			
			if (this.clip == null) {
				
				
				AudioInputStream audio = AudioSystem.getAudioInputStream(Reproductor.class.getResource(this.archivos[pId]+".wav"));
				this.clip = AudioSystem.getClip();
				this.clip.open(audio);
				this.clip.start();
				this.clip.setLoopPoints(0, -1);
				this.clip.loop(Clip.LOOP_CONTINUOUSLY);
			}

		} catch (Exception e) {
			System.out.println("DEBUG: NPATH NO ENCONTRADO");
		}

		

		

		
		
	}


	

	public void pausarMusica() {

	/*   Pre: Hay música en reproducción, ya que el atributo clip no es null
 	     Post: Se pausa la música, pero no se pierde el puntero a la reproducción, llamando a despausarMusica() será posible continuar la reproduccion
		
		  
	  
	 */

		
		if (this.clip != null) {this.clip.stop();}
		else {System.out.println("DEBUG: El método no se ejectuto, elige antes que música quieres que toque");}

	}

	public void despausarMusica() {



		if (this.clip != null) {this.clip.start();}
		else {System.out.println("DEBUG: El método no se ejectuto, elige antes que música quieres que toque");}	

}
	public void acabarMusica(boolean pGameOver) {
		this.clip.stop();
		this.clip = null;

		if (pGameOver) {
			this.gameOver.setMicrosecondPosition(0);
			this.gameOver.start();	
		}
		


	}
	
	
	public void sonarLineaLimpia() {
		this.lineaLimpia.setMicrosecondPosition(0);
		this.lineaLimpia.start();
		
		
		
	}
	
	public void sonarTetris() {
			this.tetris.setMicrosecondPosition(0);
			this.tetris.start();
		}
		
	
	
	
	
	
	
	
}
