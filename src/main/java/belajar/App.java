package belajar;

import java.io.File;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        
        File audiofile = new File("/home/endy/tmp/dancing-queen.mp3");
        
        Player audioPlayer = Manager.createRealizedPlayer(
                new MediaLocator(audiofile.toURI().toURL()));
        
        audioPlayer.start();
    }
}
