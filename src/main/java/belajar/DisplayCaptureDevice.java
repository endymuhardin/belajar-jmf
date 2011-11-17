/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package belajar;

import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;

/**
 *
 * @author endy
 */
public class DisplayCaptureDevice {
    public static void main(String[] args) {
        Format audio = new AudioFormat(AudioFormat.LINEAR);
        Format videoYuv = new VideoFormat(VideoFormat.YUV);
        Format videoRgb = new VideoFormat(VideoFormat.RGB);
        
        Vector daftarDevice = CaptureDeviceManager.getDeviceList(audio);
        daftarDevice.addAll(CaptureDeviceManager.getDeviceList(videoYuv));
        daftarDevice.addAll(CaptureDeviceManager.getDeviceList(videoRgb));
        
        System.out.println("Jumlah Device : "+daftarDevice.size());
        
        for(Object dev : daftarDevice){
            System.out.println("Class Device : "+dev.getClass().getName());
            CaptureDeviceInfo devInfo = (CaptureDeviceInfo) dev;
            
            Format[] daftarFormat = devInfo.getFormats();
            for(Format f : daftarFormat){
                System.out.println("Format : "+f.getEncoding());
                System.out.println("Class Format : "+f.getClass().getName());
                if(AudioFormat.class.isAssignableFrom(f.getClass())) {
                    AudioFormat af = (AudioFormat) f;
                    System.out.println("Frame Rate : "+af.getFrameRate());
                    System.out.println("Frame Size (bits) : "+af.getFrameSizeInBits());
                    System.out.println("Sample Rate : "+af.getSampleRate());
                    System.out.println("Sample Size (bits) : "+af.getSampleSizeInBits());
                } else if(VideoFormat.class.isAssignableFrom(f.getClass())) {
                    VideoFormat af = (VideoFormat) f;
                    System.out.println("Frame Rate : "+af.getFrameRate());
                    System.out.println("Size : "+af.getSize());
                    System.out.println("Max Data Length : "+af.getMaxDataLength());
                }
            }
        }
    }
}
