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
package GUI;

import Windows.Clean;
import Windows.FrameIcons;
import Windows.ModalDialog;
import Windows.RunnableViewer;
import Windows.ValidateInfo;
import Windows.WindowsStyle;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 * Clase que gestiona y crea la interfaz de usuario
 *
 * @author andy737-1
 * @version 1.0
 */
public class CollectorGUI extends javax.swing.JFrame {

    private Collector gs;
    private ValidateInfo valinfo;
    private ModalDialog md;
    private Thread t;

    /**
     * Constructor de GUI
     */
    public CollectorGUI() {

        WindowsStyle ws = new WindowsStyle();
        FrameIcons ic = new FrameIcons();
        ws.SetStyle();
        ic.SetIcon();
        this.setIconImages(ic.GetIcon());
        initComponents();
        this.setLocationRelativeTo(null);
        t = null;
    }

    private void ExitApp() {
        int estado = CleanDialog("Salir", "La recolección no se ha completado.\n\n¿Deseas salir de la aplicación?");
        if (estado == 2) {
            System.exit(0);
        } else if (estado == 0) {
            System.exit(0);
        }
    }

    private void CleanGUI() throws IOException {

        CleanDialog("Limpiar", "¿Deseas limpiar los campos y opciones?");
    }

    /**
     * Limpieza de interfaz
     */
    public void CleanGUIDirect() {
        if (this != null) {
            Clean.getAllComponents(this);
            Clean.CleanTxt();
            Clean.CleanCombo();
            Clean.CleanCheck();
            Clean.CleanRadio();
        }
    }

    private int CleanDialog(String titulo, String dialogo) {

        valinfo = new ValidateInfo();
        int estado = 0;
        SetValues();
        valinfo.setValues(gs);
        valinfo.GeneralValidate();
        if (valinfo.getEstado()) {
            md = new ModalDialog();
            md.setDialogo(dialogo);
            md.setTitulo(titulo);
            md.Dialog();
            if (this != null && md.getSeleccion() == 0) {
                Clean.getAllComponents(this);
                Clean.CleanTxt();
                Clean.CleanCombo();
                Clean.CleanCheck();
                Clean.CleanRadio();
                estado = 1;
            }
            estado = estado + 1;
        }
        return estado;
    }

    private void SetValues() {

        gs = new Collector();
        gs.setDirectorioRecoleccion(txtDirectorioRecoleccion.getText());
        gs.setDirectorioSalida(txtDirectorioSalida.getText());
        gs.setTipoArchivo(this);
        gs.setRecursivo(rdbRecursivo.isSelected());
        if (cmbHashTipo.getSelectedItem() != null) {
            gs.setTipoHash(cmbHashTipo.getSelectedItem().toString());
        } else {
            gs.setTipoHash(null);
        }

    }

    private boolean SelectDir(JTextField txt) throws IOException {

        boolean ciclo = false;
        int rseleccion = fchSeleccion.showDialog(this, "Aceptar");
        if (rseleccion == JFileChooser.APPROVE_OPTION) {
            File directorio = new File(fchSeleccion.getSelectedFile().toPath().toString());
            if (directorio.isDirectory()) {
                txt.setText(directorio.getPath());
                txt.setForeground(Color.black);
                rdbRecursivo.setEnabled(true);
                ciclo = false;
            } else {
                txt.setText("");
                md = new ModalDialog();
                md.setDialogo("El directorio no existe.");
                md.setTitulo("Error de ruta");
                md.setFrame(this);
                md.DialogErrFix();
                txt.requestFocus(true);
                rdbRecursivo.setEnabled(false);
                rdbRecursivo.setSelected(false);
                ciclo = true;
            }
        }
        return ciclo;

    }

    private void ValidateForm() {

        if (InputDir(txtDirectorioRecoleccion) && InputDir(txtDirectorioSalida)) {

            valinfo = new ValidateInfo();
            SetValues();
            valinfo.setValues(gs);
            try {
                valinfo.EspecificValidate();
            } catch (IOException ex) {
                Logger.getLogger(CollectorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            int err = valinfo.getError();
            switch (err) {
                case 1:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa un directorio para la recolección.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtDirectorioRecoleccion.requestFocus();
                    break;
                case 2:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa un directorio para almacenar el archivo\ngenerado que contiene los metadatos recolectados.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtDirectorioSalida.requestFocus();
                    break;
                case 3:
                    md = new ModalDialog();
                    md.setDialogo("Selecciona un tipo de hash para firmar los archivos\nque serán sometidos a la recolección de metadatos.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    cmbHashTipo.requestFocus();
                    break;
                case 4:
                    md = new ModalDialog();
                    md.setDialogo("Selecciona almenos un tipo de archivo para reacolección de metadatos.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    chkbDocx.requestFocus();
                    break;
                case 5:
                    md = new ModalDialog();
                    md.setDialogo("Para preservar la integridad de los directorios y archivos analizados para recolección de metadatos, el directorio de\nrecolección y el directorio donde se almacenara el archivo de salida deben ser distintos.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtDirectorioSalida.requestFocus();
                    break;
                case 6:
                    md = new ModalDialog();
                    md.setDialogo("Para preservar la integridad de los directorios y archivos analizados para recolección de metadatos, el directorio raiz de\nrecolección no debe contener al directorio donde se almacenara el archivo de salida.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtDirectorioSalida.requestFocus();
                    break;
                case 7:
                    try {
                        md = new ModalDialog();
                        md.setDialogo("La recolección de metadatos es una operación que puede\ntardar un tiempo considerable y no se puede cancelar o abortar.\n¿Deseas continuar?");
                        md.setFrame(this);
                        md.setTitulo("Advertencia");
                        md.DialogAd();
                        if (md.getSeleccion() == 0) {
                            CollectMetadata();
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(CollectorGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    System.exit(0);
            }

        }

    }

    private void CollectMetadata() throws IOException {

        this.setVisible(false);
        SetValues();
        RunnableViewer vw = new RunnableViewer(this, gs);
        Thread op = new Thread(vw);
        op.start();
    }

    private Boolean InputDir(JTextField txt) {

        if (!txt.getText().equals("")) {
            File directorio = new File(txt.getText());
            if (directorio.isDirectory()) {
                txt.setText(directorio.getPath());
                rdbRecursivo.setEnabled(true);
                return true;
            } else {
                md = new ModalDialog();
                md.setDialogo("El directorio no existe.");
                md.setTitulo("Error de ruta");
                md.setFrame(this);
                t = md.DialogErr();
                t.start();
                txt.requestFocus();
                txt.setText("");
                rdbRecursivo.setEnabled(false);
                rdbRecursivo.setSelected(false);
                return false;
            }
        } else {
            return true;
        }

    }

    private void SelectAll(String chk) {

        if (chk.equals("open")) {
            if (chkbOdt.isSelected() && chkbOds.isSelected() && chkbOdp.isSelected()) {
                chkbOdt.setSelected(false);
                chkbOds.setSelected(false);
                chkbOdp.setSelected(false);
            } else {
                chkbOdt.setSelected(true);
                chkbOds.setSelected(true);
                chkbOdp.setSelected(true);
            }
        } else {
            if (chk.equals("private")) {
                if (chkbDocx.isSelected() && chkbXlsx.isSelected() && chkbPptx.isSelected()) {
                    chkbDocx.setSelected(false);
                    chkbXlsx.setSelected(false);
                    chkbPptx.setSelected(false);
                } else {
                    chkbDocx.setSelected(true);
                    chkbXlsx.setSelected(true);
                    chkbPptx.setSelected(true);
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fchSeleccion = new javax.swing.JFileChooser();
        jRadioButton1 = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtDirectorioRecoleccion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSeleccionDirectorioR = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDirectorioSalida = new javax.swing.JTextField();
        btnSeleccionDirectorioS = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbHashTipo = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        lbAcercaDe = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbOfficeWAll = new javax.swing.JLabel();
        lbPdf = new javax.swing.JLabel();
        lbJpg = new javax.swing.JLabel();
        btnRecolectar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        lbOfficeOAll = new javax.swing.JLabel();
        chkbDocx = new javax.swing.JCheckBox();
        chkbXlsx = new javax.swing.JCheckBox();
        chkbPptx = new javax.swing.JCheckBox();
        chkbOdt = new javax.swing.JCheckBox();
        chkbOds = new javax.swing.JCheckBox();
        chkbOdp = new javax.swing.JCheckBox();
        chkbPdf = new javax.swing.JCheckBox();
        chkbJpg = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        rdbRecursivo = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();

        fchSeleccion.setAcceptAllFileFilterUsed(false);
        fchSeleccion.setDialogType(javax.swing.JFileChooser.CUSTOM_DIALOG);
        fchSeleccion.setApproveButtonText("Seleccionar");
        fchSeleccion.setApproveButtonToolTipText("");
        fchSeleccion.setCurrentDirectory(null);
        fchSeleccion.setDialogTitle("Selecciona un directorio");
        fchSeleccion.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fchSeleccion.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Metaforensic [Recolector]");
        setLocationByPlatform(true);
        setName("CollectorGUI"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/settings-3.png"))); // NOI18N
        jLabel1.setText("Configuración de recolección");

        txtDirectorioRecoleccion.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtDirectorioRecoleccion.setToolTipText("Directorio que contiene archivos para recolección de metadatos");
        txtDirectorioRecoleccion.setName(""); // NOI18N
        txtDirectorioRecoleccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDirectorioRecoleccionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirectorioRecoleccionFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Selecciona o ingresa la ruta del directorio:");

        btnSeleccionDirectorioR.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSeleccionDirectorioR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/folder-add.png"))); // NOI18N
        btnSeleccionDirectorioR.setMnemonic('e');
        btnSeleccionDirectorioR.setText("Seleccionar");
        btnSeleccionDirectorioR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionDirectorioRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("Selecciona o ingresa la ruta para guardar el archivo de salida:");

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/settings-3.png"))); // NOI18N
        jLabel4.setText("Configuración archivo de salida");

        txtDirectorioSalida.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtDirectorioSalida.setToolTipText("Directorio donde se guardara el archivo con los metadatos extraídos");
        txtDirectorioSalida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDirectorioSalidaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDirectorioSalidaFocusLost(evt);
            }
        });

        btnSeleccionDirectorioS.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSeleccionDirectorioS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stiffy.png"))); // NOI18N
        btnSeleccionDirectorioS.setMnemonic('s');
        btnSeleccionDirectorioS.setText("Seleccionar");
        btnSeleccionDirectorioS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionDirectorioSActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/safe.png"))); // NOI18N
        jLabel5.setText("Verificación de integridad");

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel6.setText("Selecciona tipo hash:");

        cmbHashTipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        cmbHashTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "crc64", "haval_128_3", "haval_128_4 ", "haval_128_5 ", "haval_160_3 ", "haval_160_4 ", "haval_160_5 ", "haval_192_3 ", "haval_192_4 ", "haval_192_5 ", "haval_224_3 ", "haval_224_4 ", "haval_224_5 ", "haval_256_3 ", "haval_256_4 ", "haval_256_5", "md5", "ripemd160", "ripemd320", "sha1", "sha224", "sha256", "sha384", "sha512", "tiger", "tiger2", "whirlpool2" }));
        cmbHashTipo.setSelectedIndex(-1);
        cmbHashTipo.setToolTipText("Firmar cada uno de los archivos encontrados");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbAcercaDe.setDisplayedMnemonic('a');
        lbAcercaDe.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        lbAcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/link-2.png"))); // NOI18N
        lbAcercaDe.setText("Acerca de");
        lbAcercaDe.setToolTipText("Información general de la aplicación");
        lbAcercaDe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbAcercaDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbAcercaDeMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tag-3.png"))); // NOI18N
        jLabel8.setText("Tipo de archivos");

        lbOfficeWAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/imgoffice.png"))); // NOI18N
        lbOfficeWAll.setToolTipText("Seleccionar todos (Windows Office)");
        lbOfficeWAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbOfficeWAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOfficeWAllMouseClicked(evt);
            }
        });

        lbPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/imagepdf_1.png"))); // NOI18N
        lbPdf.setToolTipText("Seleccionar todo (pdf)");
        lbPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbPdfMouseClicked(evt);
            }
        });

        lbJpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/imagenicon.png"))); // NOI18N
        lbJpg.setToolTipText("Seleccionar todo (jpg)");
        lbJpg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbJpg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbJpgMouseClicked(evt);
            }
        });

        btnRecolectar.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnRecolectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/droplet.png"))); // NOI18N
        btnRecolectar.setMnemonic('r');
        btnRecolectar.setText("Recolectar");
        btnRecolectar.setToolTipText("Iniciar recolección de metadatos");
        btnRecolectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecolectarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/paper-roll-ripped.png"))); // NOI18N
        btnLimpiar.setMnemonic('l');
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setToolTipText("Limpiar campos y opciones");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/out.png"))); // NOI18N
        btnSalir.setMnemonic('r');
        btnSalir.setText("Salir");
        btnSalir.setToolTipText("Salir de la aplicación");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lbOfficeOAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/imgopenoff.png"))); // NOI18N
        lbOfficeOAll.setToolTipText("Seleccionar todos (OpenOffice)");
        lbOfficeOAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbOfficeOAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbOfficeOAllMouseClicked(evt);
            }
        });

        chkbDocx.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbDocx.setMnemonic('d');
        chkbDocx.setText("docx");

        chkbXlsx.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbXlsx.setMnemonic('x');
        chkbXlsx.setText("xlsx");

        chkbPptx.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbPptx.setMnemonic('p');
        chkbPptx.setText("pptx");
        chkbPptx.setToolTipText("");

        chkbOdt.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbOdt.setMnemonic('t');
        chkbOdt.setText("odt");

        chkbOds.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbOds.setMnemonic('t');
        chkbOds.setText("ods");

        chkbOdp.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbOdp.setMnemonic('o');
        chkbOdp.setText("odp");

        chkbPdf.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbPdf.setMnemonic('f');
        chkbPdf.setText("pdf");

        chkbJpg.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        chkbJpg.setMnemonic('j');
        chkbJpg.setText("jpg");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/small_metaforensic_logo.png"))); // NOI18N

        rdbRecursivo.setText("Recolección recursiva entre directorios");
        rdbRecursivo.setToolTipText("");
        rdbRecursivo.setEnabled(false);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/repeat-2.png"))); // NOI18N

        jCheckBox1.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        jCheckBox1.setText("doc");
        jCheckBox1.setToolTipText("");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Office.png"))); // NOI18N
        jLabel10.setToolTipText("");

        jCheckBox2.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        jCheckBox2.setText("xls");

        jCheckBox3.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        jCheckBox3.setText("ppt");

        jCheckBox4.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        jCheckBox4.setText("png");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(184, 184, 184)
                        .addComponent(lbAcercaDe))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtDirectorioRecoleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSeleccionDirectorioR))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbRecursivo))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDirectorioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSeleccionDirectorioS))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(40, 40, 40)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cmbHashTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(jLabel5))))
                                .addGap(27, 27, 27)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(lbOfficeWAll))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lbOfficeOAll)
                                                    .addComponent(lbJpg, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lbPdf)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCheckBox1)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(chkbDocx)
                                                            .addComponent(chkbOdt)
                                                            .addComponent(chkbJpg))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jCheckBox4)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(chkbXlsx)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(chkbPptx))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(chkbOds)
                                                                    .addComponent(jCheckBox2))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jCheckBox3)
                                                                    .addComponent(chkbOdp)))))))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(chkbPdf)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnRecolectar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1))
                    .addComponent(lbAcercaDe))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDirectorioRecoleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSeleccionDirectorioR))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbRecursivo)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDirectorioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSeleccionDirectorioS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(chkbDocx)
                                            .addComponent(chkbXlsx)
                                            .addComponent(chkbPptx)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(lbOfficeWAll)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(chkbOdt)
                                    .addComponent(chkbOds)
                                    .addComponent(chkbOdp)))
                            .addComponent(lbOfficeOAll))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox1)
                                .addComponent(jCheckBox2)
                                .addComponent(jCheckBox3))
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(chkbJpg)
                                .addComponent(jCheckBox4))
                            .addComponent(lbJpg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPdf, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chkbPdf, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(cmbHashTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir)
                    .addComponent(btnLimpiar)
                    .addComponent(btnRecolectar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        ExitApp();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnSeleccionDirectorioRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionDirectorioRActionPerformed
        boolean ciclo = true;
        while (ciclo) {
            try {
                ciclo = SelectDir(txtDirectorioRecoleccion);
            } catch (IOException ex) {
                Logger.getLogger(CollectorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSeleccionDirectorioRActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ExitApp();
    }//GEN-LAST:event_formWindowClosing

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        try {
            CleanGUI();
        } catch (IOException ex) {
            Logger.getLogger(CollectorGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void lbOfficeWAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOfficeWAllMouseClicked
        SelectAll("private");
    }//GEN-LAST:event_lbOfficeWAllMouseClicked

    private void lbOfficeOAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbOfficeOAllMouseClicked
        SelectAll("open");
    }//GEN-LAST:event_lbOfficeOAllMouseClicked

    private void btnSeleccionDirectorioSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionDirectorioSActionPerformed

        boolean ciclo = true;
        while (ciclo) {
            try {
                ciclo = SelectDir(txtDirectorioSalida);
            } catch (IOException ex) {
                Logger.getLogger(CollectorGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnSeleccionDirectorioSActionPerformed

    private void txtDirectorioRecoleccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirectorioRecoleccionFocusLost
        if (!txtDirectorioRecoleccion.getText().equals("")) {
            File directorio = new File(txtDirectorioRecoleccion.getText());
            if (directorio.isDirectory()) {
                txtDirectorioRecoleccion.setForeground(Color.black);
                rdbRecursivo.setEnabled(true);
            } else {
                txtDirectorioRecoleccion.setForeground(Color.red);
                txtDirectorioRecoleccion.setText("ERROR");
                rdbRecursivo.setEnabled(false);
                rdbRecursivo.setSelected(false);

            }
        }
    }//GEN-LAST:event_txtDirectorioRecoleccionFocusLost

    private void txtDirectorioSalidaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirectorioSalidaFocusLost
        if (!txtDirectorioSalida.getText().equals("")) {
            File directorio = new File(txtDirectorioSalida.getText());
            if (directorio.isDirectory()) {
                txtDirectorioSalida.setForeground(Color.black);

            } else {
                txtDirectorioSalida.setForeground(Color.red);
                txtDirectorioSalida.setText("ERROR");
            }
        }
    }//GEN-LAST:event_txtDirectorioSalidaFocusLost

    private void btnRecolectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecolectarActionPerformed
        ValidateForm();
    }//GEN-LAST:event_btnRecolectarActionPerformed

    private void lbPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbPdfMouseClicked
        if (chkbPdf.isSelected()) {
            chkbPdf.setSelected(false);
        } else {
            chkbPdf.setSelected(true);
        }
    }//GEN-LAST:event_lbPdfMouseClicked

    private void lbJpgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbJpgMouseClicked
        if (chkbJpg.isSelected()) {
            chkbJpg.setSelected(false);
        } else {
            chkbJpg.setSelected(true);
        }
    }//GEN-LAST:event_lbJpgMouseClicked

    private void lbAcercaDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAcercaDeMouseClicked
        AboutUs au = new AboutUs(this, true);
        au.setVisible(true);
    }//GEN-LAST:event_lbAcercaDeMouseClicked

    private void txtDirectorioRecoleccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirectorioRecoleccionFocusGained
        if (txtDirectorioRecoleccion.getText().equals("ERROR")) {
            txtDirectorioRecoleccion.setForeground(Color.black);
            txtDirectorioRecoleccion.setText("");
            rdbRecursivo.setEnabled(false);
            rdbRecursivo.setSelected(false);
        }
    }//GEN-LAST:event_txtDirectorioRecoleccionFocusGained

    private void txtDirectorioSalidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDirectorioSalidaFocusGained
        if (txtDirectorioSalida.getText().equals("ERROR")) {
            txtDirectorioSalida.setForeground(Color.black);
            txtDirectorioSalida.setText("");
        }
    }//GEN-LAST:event_txtDirectorioSalidaFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRecolectar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSeleccionDirectorioR;
    private javax.swing.JButton btnSeleccionDirectorioS;
    private javax.swing.JCheckBox chkbDocx;
    private javax.swing.JCheckBox chkbJpg;
    private javax.swing.JCheckBox chkbOdp;
    private javax.swing.JCheckBox chkbOds;
    private javax.swing.JCheckBox chkbOdt;
    private javax.swing.JCheckBox chkbPdf;
    private javax.swing.JCheckBox chkbPptx;
    private javax.swing.JCheckBox chkbXlsx;
    private javax.swing.JComboBox cmbHashTipo;
    private javax.swing.JFileChooser fchSeleccion;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbAcercaDe;
    private javax.swing.JLabel lbJpg;
    private javax.swing.JLabel lbOfficeOAll;
    private javax.swing.JLabel lbOfficeWAll;
    private javax.swing.JLabel lbPdf;
    private javax.swing.JRadioButton rdbRecursivo;
    private javax.swing.JTextField txtDirectorioRecoleccion;
    private javax.swing.JTextField txtDirectorioSalida;
    // End of variables declaration//GEN-END:variables
}
