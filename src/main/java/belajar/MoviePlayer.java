/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MoviePlayer.java
 *
 * Created on Nov 15, 2011, 11:15:46 AM
 */
package belajar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author endy
 */
public class MoviePlayer extends javax.swing.JFrame {

    /** Creates new form MoviePlayer */
    public MoviePlayer() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFile = new javax.swing.JTextField();
        btnOpen = new javax.swing.JButton();
        pnlTengah = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(522, 50));

        jLabel1.setText("Movie");

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFile, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpen)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout pnlTengahLayout = new javax.swing.GroupLayout(pnlTengah);
        pnlTengah.setLayout(pnlTengahLayout);
        pnlTengahLayout.setHorizontalGroup(
            pnlTengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );
        pnlTengahLayout.setVerticalGroup(
            pnlTengahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        getContentPane().add(pnlTengah, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        JFileChooser fc = new JFileChooser();
        int hasil = fc.showOpenDialog(this);
        if(hasil == JFileChooser.APPROVE_OPTION) {
            File film = fc.getSelectedFile();
            openFile(film);
            txtFile.setText(film.getAbsolutePath());
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void openFile(File f){
        MediaLocator mediafile = null;
        Player mediaPlayer = null;
        try {
            mediafile = new MediaLocator(f.toURI().toURL());
            Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
            mediaPlayer = Manager.createRealizedPlayer(mediafile);
            
            // tampilan video
            this.getContentPane().remove(pnlTengah);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            
            
            Component viscomp = mediaPlayer.getVisualComponent();
            if(viscomp != null){
                panel.add(viscomp, BorderLayout.CENTER);
            } else {
                JLabel lbl = new JLabel("Tidak ada gambarnya");
                panel.add(lbl, BorderLayout.CENTER);
            }
            // tombol play, pause, dsb
            Component controller = mediaPlayer.getControlPanelComponent();
            panel.add(controller, BorderLayout.SOUTH);
            
            this.getContentPane().add(panel, BorderLayout.CENTER);
            mediaPlayer.start();
            
            // workaround supaya tampilannya refresh
            this.setVisible(false);
            this.setVisible(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            
            if(mediaPlayer != null){
                mediaPlayer.close();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MoviePlayer mp = new MoviePlayer();
                mp.setTitle("Contoh Movie Player");
                mp.setSize(800, 600);
                mp.setLocationRelativeTo(null);
                mp.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlTengah;
    private javax.swing.JTextField txtFile;
    // End of variables declaration//GEN-END:variables
}