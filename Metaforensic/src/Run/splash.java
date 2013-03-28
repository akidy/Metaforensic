/**
 * *****************************************************************************
 *
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * All Rights Reserved, https://github.com/andy737
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * E-mail: andy1818ster@gmail.com
 *
 *******************************************************************************
 */
package Run;

//import com.sun.awt.AWTUtilities;
import Windows.FrameIcons;
import com.sun.awt.AWTUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de la simulación del SplashScreen de inicio
 * 
 * @author andy737-1
 * @version 1.0
 */
public class splash extends javax.swing.JFrame {

    /**
     * Cosntructor que añade modificaciones esteticas al JFrame Splash
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public splash() {

        //WindowsStyle ws = new WindowsStyle();
        FrameIcons ic = new FrameIcons();
        //ws.SetStyle();
        ic.SetIcon();
        this.setIconImages(ic.GetIcon());
        initComponents();
        AWTUtilities.setWindowOpaque(this, false);
        this.setLocationRelativeTo(null);

    }

    /*
     * Simulación de carga de aplicación
     */
    @SuppressWarnings("SleepWhileInLoop")
    private void LoadProgressBar() {

        String[] label = {"Loading", "Loading.", "Loading..", "Loading...", "Loading....", "Loading.....", "Loading......", "Loading.......", "Loading........"};
        int cont = 0;

        for (int i = 0; i < 100; i++) {

            if (cont == 8) {
                cont = 0;
            }

            lbLoading.setText(label[cont]);

            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(splash.class.getName()).log(Level.SEVERE, null, ex);
            }

            pbLoading.setValue(i);

            cont++;
        }
    }

    /**
     * Metodo encargado de inciar el SplashScreen
     */
    public static void RunSplash() {
        splash sp = new splash();
        sp.setVisible(true);
        sp.LoadProgressBar();
        sp.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbImg = new javax.swing.JLabel();
        lbLoading = new javax.swing.JLabel();
        pbLoading = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setName("SplashScreen"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        lbImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rsz_metaforensic_logo.png"))); // NOI18N

        lbLoading.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbLoading.setText("Loading");

        pbLoading.setBackground(new java.awt.Color(51, 204, 0));
        pbLoading.setForeground(new java.awt.Color(153, 0, 153));
        pbLoading.setBorderPainted(false);
        pbLoading.setName("pbrSplash"); // NOI18N
        pbLoading.setOpaque(true);
        pbLoading.setPreferredSize(new java.awt.Dimension(146, 5));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbImg)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLoading)
                            .addComponent(pbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLoading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pbLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbImg;
    private javax.swing.JLabel lbLoading;
    private javax.swing.JProgressBar pbLoading;
    // End of variables declaration//GEN-END:variables
}
