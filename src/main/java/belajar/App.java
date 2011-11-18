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
        
        MediaLocator audiofile = new MediaLocator(
                new File("/home/endy/tmp/dancing-queen.mp3")
                .toURI().toURL());
        
        MediaLocator streaming 
                = new MediaLocator("rtp://192.168.1.101:12340/audio");
        
        Player audioPlayer = Manager
                .createRealizedPlayer(streaming);
        
        audioPlayer.start();
    }
}
