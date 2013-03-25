/*Clase encargada de crear el splash screen de inicio
 *Autor: Andrés de Jesús Hernádez Martínez
 *Version: 0.1
 *Fecha de creación: 18 de enero de 2012
 *Fecha de ultima modificación: 18 de enero de 2012
 */
package Run;

//import com.sun.awt.AWTUtilities;
import Windows.FrameIcons;
import com.sun.awt.AWTUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;

public class splash extends javax.swing.JFrame {

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

    @SuppressWarnings("SleepWhileInLoop")
    public void LoadProgressBar() {

        String[] label = {"Loading", "Loading.", "Loading..", "Loading...", "Loading....", "Loading.....", "Loading......","Loading.......","Loading........"};
        int cont = 0;

        for (int i = 0; i < 100; i++) {

            if (cont == 8) {
                cont = 0;
            }

            jLabel8.setText(label[cont]);

            try {
                Thread.sleep(38);
            } catch (InterruptedException ex) {
                Logger.getLogger(splash.class.getName()).log(Level.SEVERE, null, ex);
            }

            jProgressBar1.setValue(i);

            cont++;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbImg = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setName("SplashScreen"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        lbImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rsz_metaforensic_logo.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Loading");

        jProgressBar1.setBackground(new java.awt.Color(51, 204, 0));
        jProgressBar1.setForeground(new java.awt.Color(153, 0, 153));
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setName("pbrSplash"); // NOI18N
        jProgressBar1.setOpaque(true);
        jProgressBar1.setPreferredSize(new java.awt.Dimension(146, 5));

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
                            .addComponent(jLabel8)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lbImg;
    // End of variables declaration//GEN-END:variables
}
