/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package belajar;

import java.io.File;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

/**
 *
 * @author endy
 */
public class PengirimAudio {
    public static void main(String[] args) throws Exception {
        MediaLocator sumber 
                = new MediaLocator(
                new File("/home/endy/tmp/Celebrate.mp3")
                .toURI().toURL());
        
        MediaLocator tujuan 
                = new MediaLocator("rtp://127.0.1.1:3000/audio/1");
        
        Format[] format = new Format[]{new AudioFormat(
                AudioFormat.MPEG_RTP)};
        ContentDescriptor cd = new ContentDescriptor(
                ContentDescriptor.RAW_RTP);
        
        DataSource ds = Manager.createDataSource(sumber);
        Processor proc = Manager
                .createRealizedProcessor(
                new ProcessorModel(ds, format, cd));
        
        DataSink sink = Manager
                .createDataSink(proc.getDataOutput(), tujuan);
        
        // kirim data
        proc.start();
        sink.open();
        sink.start();
        System.out.println("Stream sudah dijalankan");
        
        // tetap kirim sampai user tekan enter
        System.in.read();
        
        // stop kirim data
        System.out.println("Stream dihentikan");
        sink.stop();
        sink.close();
        proc.stop();
        proc.close();
    }
}
