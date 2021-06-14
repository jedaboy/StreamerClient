/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package views;

import controllers.GerenciadorDeFluxo;
import daoRepository.DaoFactory;
import daoRepository.GeneroTCP;
//import daoRepository.MusicaDao;
import daoRepository.UsuarioTCP;
//import daoRepository.UsuarioDao;
import entities.Genero;
import entities.Musica;
import entities.Usuario;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jedaboy
 */
public class TelaGenerosFavoritos extends javax.swing.JFrame {

    // cria uma lista compativel com  o campo do Jlist (propriedades, model, lista)
    DefaultListModel lista = new DefaultListModel();
    //lista para ser passada por referencia futuramente 
    // List<Genero> list = new ArrayList<>();
    GeneroTCP generoDao;
    //descartavel
    int x, y;

    private Usuario usuario;
    private UsuarioTCP usuarioTCP;

    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }

    /**
     * Creates new form Tela
     */
    public TelaGenerosFavoritos(UsuarioTCP usuarioTCP) {
        try {
            this.usuarioTCP = usuarioTCP;
            generoDao = DaoFactory.createGenTCPCon();
            initComponents();
            buscarGenero();

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    try {
                        System.out.println("Encerramento na classe T.G.F");
                        UsuarioTCP usuarioTCP = DaoFactory.createUserTCPCon();
                        //passa o usuario a ser encerrado
                        Usuario usuario = new Usuario(null, null);
                        usuarioTCP.encerraSessao(usuario);
                    } catch (Exception ex) {
                        Logger.getLogger(TelaDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

        } catch (IOException ex) {
            Logger.getLogger(TelaGenerosFavoritos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciaGenero() {

    }

    //busca e inicia os generos no combobox
    private void buscarGenero() {
        try {

            List<Genero> generos = generoDao.getGenerosTCP();
            generoComboBox.setModel(new DefaultComboBoxModel<>(generos.toArray(new Genero[0])));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Generos indisponíveis, tente novamente mais tarde. ");
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        setaRetorno = new javax.swing.JLabel();
        generoComboBox = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaGenerosFavoritos = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/logo_redondo.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setaRetorno.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        setaRetorno.setForeground(new java.awt.Color(153, 153, 153));
        setaRetorno.setText("← ");
        setaRetorno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setaRetornoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setaRetornoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setaRetornoMouseExited(evt);
            }
        });
        jPanel1.add(setaRetorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 25));

        generoComboBox.setEditable(true);
        generoComboBox.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        generoComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generoComboBoxMouseClicked(evt);
            }
        });
        generoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generoComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(generoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, 370, 30));

        jScrollPane2.setBorder(null);

        listaGenerosFavoritos.setModel(lista);
        listaGenerosFavoritos.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                listaGenerosFavoritosComponentRemoved(evt);
            }
        });
        listaGenerosFavoritos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaGenerosFavoritosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaGenerosFavoritos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaGenerosFavoritosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaGenerosFavoritos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 360, 200));

        jLabel3.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        jLabel3.setText("Remover");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(797, 385, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pressbutton_5.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(786, 377, 90, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/generos_favoritos.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 690));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void setaRetornoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseClicked
        // seta de retorno para dashboard
        GerenciadorDeFluxo chamaMain = new GerenciadorDeFluxo();
        chamaMain.setUsuAtual(usuario);
        chamaMain.setUsuTCPAtual(usuarioTCP);
        chamaMain.setContFlux(2);
        chamaMain.fluxoPrincipal();
        dispose();
    }//GEN-LAST:event_setaRetornoMouseClicked

    private void setaRetornoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseEntered
        setaRetorno.setForeground(Color.MAGENTA);

    }//GEN-LAST:event_setaRetornoMouseEntered

    private void setaRetornoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseExited
        setaRetorno.setForeground(Color.gray);
    }//GEN-LAST:event_setaRetornoMouseExited

    //adiciona todos os generos ao combobox
    private void generoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generoComboBoxActionPerformed

        //assim que o respectivo genero é clicado no combobox ele é passado para a lista abaixo
        Genero genero = (Genero) generoComboBox.getSelectedItem();
        //lista exibida na tela generofavoritotela
        // impede que um genero repetido seja adicionado a lista de generos favs do usuario
        boolean igual = true;
        int a = 0, cont = 0;

        cont = lista.size() - 1;

        if (cont >= 0) {
            do {

                if (genero.equals(lista.get(cont))) {
                    igual = true;
                    if (igual) {
                        a = 1;
                    }
                }
                cont--;
            } while (cont >= 0);

        }

        if (a == 0) {
            lista.addElement(genero);
             generoDao.addGeneroFavTCP(usuario, genero);
        }
        // lista criada para posteriormente ser passada para a tabela usuario no banco de dados
        //  System.out.println("TACOTCNO");

    }//GEN-LAST:event_generoComboBoxActionPerformed

    //adiciona genero favorito
   /* public void addgenero(Usuario usuario, Genero genero) {
        //Nao deixa genero repetido ser adicionado na lista de favoritos
        boolean igual = true;
        int a = 0, cont = 0;
        lista.get(0);
        cont = lista.size() - 1;
        System.out.println(cont);
        System.out.println(lista.get(0));

        if (cont >= 0) {
            do {

                if (genero.equals(lista.get(cont))) {
                    igual = true;
                    if (igual) {
                        a = 1;
                    }
                }
                cont--;
            } while (cont >= 0);

        }

        if (a == 0) {
        
            System.out.println("TACOTCNO");
        }

    }*/

    //remover
    private void generoComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generoComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_generoComboBoxMouseClicked

    //inicia a lista de generos favoritos com os generos favoritos do usuario
    private void listaGenerosFavoritosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaGenerosFavoritosAncestorAdded
        // TODO add your handling code here:

        List<Genero> list = new ArrayList<>();

        list = generoDao.getGeneroFavTCP(usuario);

        for (Genero val : list) {
            lista.addElement(val);
        }

    }//GEN-LAST:event_listaGenerosFavoritosAncestorAdded


    private void listaGenerosFavoritosComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_listaGenerosFavoritosComponentRemoved


    }//GEN-LAST:event_listaGenerosFavoritosComponentRemoved

    //remover
    private void listaGenerosFavoritosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaGenerosFavoritosMouseClicked

    }//GEN-LAST:event_listaGenerosFavoritosMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:

        int IndexGenero = listaGenerosFavoritos.getSelectedIndex();
        Genero genero = listaGenerosFavoritos.getModel().getElementAt(IndexGenero);

        generoDao.rmvGeneroFavTCP(usuario, genero);
        lista.remove(IndexGenero);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/images/pressbutton_5.png")));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/images/button5.png")));
    }//GEN-LAST:event_jLabel2MouseExited

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Genero> generoComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Genero> listaGenerosFavoritos;
    private javax.swing.JLabel setaRetorno;
    // End of variables declaration//GEN-END:variables
    //private Color color;
}