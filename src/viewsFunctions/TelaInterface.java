/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package viewsFunctions;

import entities.Genero;
import entities.Musica;
import entities.Usuario;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public interface TelaInterface {

    public ImageIcon enviaIdTcp(int cont);
    
     public void interrompeStreamTCP();

    public void onlyNum(javax.swing.JTextField notaText);

    public void sortLista(DefaultListModel lista);

    public void sortLista2(DefaultListModel lista2);

    public void paintItRed(javax.swing.JLabel alertaLabel);

    public void prencheLista(DefaultListModel lista2, Usuario usuario);

    public void avaliaMusicas(javax.swing.JLabel confirmacaoLabel, Usuario usuario, javax.swing.JLabel alertaLabel, DefaultListModel lista, DefaultListModel lista2, javax.swing.JTextField notaText, javax.swing.JList<Musica> listaMusicas, javax.swing.JList<Musica> listaRecomendacoes, boolean recomendacao);

    public void setId(int userId);

    public int getId();
    
}
