/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package belajar;

import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.RealizeCompleteEvent;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

/**
 *
 * @author endy
 */
public class VoiceChat {
    
    private static DataSink output;
    private static Processor proc;
    private static Player player;
    
    public static void main(String[] args) throws Exception {
        
        String ipTujuan = "192.168.58.39";
        String ipAsal = "192.168.58.35";
        String portTujuan = "1234";
        String portAsal = "1112";
        startTransmit(ipTujuan, portTujuan);
        startReceive(ipAsal, portAsal);
        
        System.in.read();
        
        stop();
        
    }

    private static void startTransmit(String ipTujuan, String port) throws Exception {
        // Audio Format
        Format audioFormat = new AudioFormat(AudioFormat.LINEAR);
        
        // Daftar CaptureDevice yang support
        Vector daftarDevice = CaptureDeviceManager.getDeviceList(audioFormat);
        System.out.println("Jumlah Device : "+daftarDevice.size());
        
        // Gunakan device yang pertama
        CaptureDeviceInfo device = (CaptureDeviceInfo) daftarDevice.firstElement();
        System.out.println("Menggunakan capture device "+device.getLocator());
        
        // Datasource dari device
        proc = Manager
                .createRealizedProcessor(new ProcessorModel(device.getLocator(), 
                new Format[]{audioFormat}, // input
                new ContentDescriptor(ContentDescriptor.RAW_RTP))); // output
        DataSource ds = proc.getDataOutput();

        
        // Tujuan
        MediaLocator destfile = new MediaLocator("rtp://"+ipTujuan+":"+port+"/audio");
        
        // output sink
        output = Manager.createDataSink(ds, destfile);
        output.open();
        
        // mulai mengirim
        proc.start();
        output.start();
        
        System.out.println("Pengirim sudah berjalan");
    }
    
    private static void startReceive(String ip, String port) throws Exception {
        System.out.println("Menjalankan penerima di rtp://"+ip+":"+port+"/audio");
        MediaLocator source = new MediaLocator("rtp://"+ip+":"+port+"/audio");
        
        final Player p = Manager.createPlayer(source);

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
    
    private static void stop() throws Exception {
        proc.stop();
        output.stop();
        System.out.println("Pengirim stop");
        
        proc.close();
        output.close();
        System.out.println("Pengirim ditutup");
        
        player.stop();
        System.out.println("Penerima stop");
        
        player.close();
        System.out.println("Penerima ditutup");
    }
}
