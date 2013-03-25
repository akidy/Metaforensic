package Windows;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author andy737-1
 */
public class FrameIcons {

    ArrayList<Image> iconos = new ArrayList<>();

    private void InitialIcons() {

        iconos.add(new ImageIcon(getClass().getResource("/Images/icono16.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono32.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono48.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono64.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono128.png")).getImage());

    }

    public void SetIcon() {

        InitialIcons();
    }

    public ArrayList<Image> GetIcon() {

        return iconos;
    }
}
