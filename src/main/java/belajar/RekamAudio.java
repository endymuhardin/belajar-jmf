/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package belajar;

import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.media.control.StreamWriterControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import jmapps.util.StateHelper;

/**
 *
 * @author endy
 */
public class RekamAudio {
    public static void main(String[] args) throws Exception {
        // Audio Format
        Format audioFormat = new AudioFormat(AudioFormat.LINEAR);
        
        // Daftar CaptureDevice yang support
        Vector daftarDevice = CaptureDeviceManager.getDeviceList(audioFormat);
        System.out.println("Jumlah Device : "+daftarDevice.size());
        
        // Gunakan device yang pertama
        CaptureDeviceInfo device = (CaptureDeviceInfo) daftarDevice.firstElement();
        
        // Datasource dari device
        Processor proc = Manager
                .createRealizedProcessor(new ProcessorModel(device.getLocator(), 
                new Format[]{audioFormat}, 
                new FileTypeDescriptor(FileTypeDescriptor.WAVE)));
        DataSource ds = proc.getDataOutput();

        
        // File tujuan
        MediaLocator destfile = new MediaLocator("file:///home/endy/tmp/output.wav");
        
        // output sink
        DataSink output = Manager.createDataSink(ds, destfile);
        output.open();
        
        // save max sampai 1 MB
        StreamWriterControl swc = (StreamWriterControl) 
                proc.getControl("javax.media.control.StreamWriterControl");
        swc.setStreamSizeLimit(1000 * 1000);
        
        // mulai merekam
        output.start();
        
        
        // 5 detik saja
        proc.start();
        Thread.sleep(5 * 1000);
        proc.stop();
        
        // clean up
        proc.close();
        output.close();
        
    }
}
