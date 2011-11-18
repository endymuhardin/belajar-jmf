package belajar;

import java.io.File;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        
        MediaLocator audiofile = new MediaLocator(
                new File("/home/endy/tmp/dancing-queen.mp3")
                .toURI().toURL());
        
        MediaLocator streaming 
                = new MediaLocator("rtp://192.168.58.35:12340/audio");
        
        System.out.println("Membuat player");
        Player audioPlayer = Manager
                .createRealizedPlayer(audiofile);
        System.out.println("Player sudah diinisialisasi");
        
        audioPlayer.start();
    }
}
