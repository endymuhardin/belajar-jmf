/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package belajar;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.format.AudioFormat;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.RemoteListener;
import javax.media.rtp.SessionAddress;
import javax.media.rtp.SessionListener;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.rtp.event.RemoteEvent;
import javax.media.rtp.event.SessionEvent;

/**
 *
 * @author endy
 */
public class PenerimaAudio {

    public static void main(String[] args) throws Exception {
        String url = "rtp://10.42.43.1:12340/audio/1";
        //String url = "file:///home/endy/tmp/dancing-queen.mp3";
        menggunakanControllerListener(url);
        //menggunakanRtpManager();
        
        System.in.read();
    }

    private static void menggunakanControllerListener(String url) throws Exception {
        MediaLocator m = new MediaLocator(url);
        final Player p = Manager.createPlayer(m);

        System.out.println("State Unrealized : " + Player.Unrealized);
        System.out.println("State Realizing : " + Player.Realizing);
        System.out.println("State Realized : " + Player.Realized);
        System.out.println("State Prefetching : " + Player.Prefetching);
        System.out.println("State Prefetched : " + Player.Prefetched);

        System.out.println("Menginstankan player : " + p.getClass().getName());
        System.out.println("Player state : " + p.getState());
        p.addControllerListener(new ControllerListener() {

            public void controllerUpdate(ControllerEvent ce) {
                System.out.println("Event : " + ce.getClass().getName());
                System.out.println("Source : " + ce.getSource().getClass().getName());
                System.out.println("State : " + p.getState());

                if (RealizeCompleteEvent.class.isAssignableFrom(ce.getClass())) {
                    System.out.println("Player siap, mari kita jalankan");
                    p.start();
                }
            }
        });
        p.realize();

    }

    private static void menggunakanRtpManager() {
        try {
            RTPManager manager = RTPManager.newInstance();
            manager.addReceiveStreamListener(new ReceiveStreamListener() {

                public void update(ReceiveStreamEvent ce) {
                    System.out.println("Stream Listener");
                    System.out.println("Event : " + ce.getClass().getName());
                    System.out.println("Source : " + ce.getSource().getClass().getName());
                }
            });

            manager.addSessionListener(new SessionListener() {

                public void update(SessionEvent ce) {
                    System.out.println("Session Listener");
                    System.out.println("Event : " + ce.getClass().getName());
                    System.out.println("Source : " + ce.getSource().getClass().getName());
                }
            });
            
            manager.addRemoteListener(new RemoteListener() {

                public void update(RemoteEvent ce) {
                    System.out.println("Remote Listener");
                    System.out.println("Event : " + ce.getClass().getName());
                    System.out.println("Source : " + ce.getSource().getClass().getName());
                }
            });
            
            manager.addFormat(new AudioFormat(AudioFormat.MPEGLAYER3), 0);
            
            SessionAddress srclocalAddr = new SessionAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostName()), 3000);
            manager.initialize(srclocalAddr);
            
            System.out.println("RTP Manager ada di "+srclocalAddr.toString());
            
        } catch (Exception ex) {
            Logger.getLogger(PenerimaAudio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
