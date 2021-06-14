/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package application;

/* Importando por garantia */
import controllers.GerenciadorDeFluxo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Collections;
import javax.swing.SwingUtilities;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author jedaboy
 */
public class ClassePrincipal {
     public static void main(String[] args) {
   
 
    GerenciadorDeFluxo genFlux = new GerenciadorDeFluxo();
    genFlux.fluxoPrincipal();

}
}
