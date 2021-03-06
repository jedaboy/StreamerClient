/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package views;

import controllers.GerenciadorDeFluxo;
import daoRepository.DaoFactory;
//import daoRepository.UsuarioDao;
import daoRepository.UsuarioTCP;
import daoRepository.impl.UsuarioClientTCP;
import entities.Usuario;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Lawliet
 */
public class TelaCadastro extends javax.swing.JFrame {

    private Usuario usuario;

    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }

    /**
     * Creates new form TelaCadastro
     */
    public TelaCadastro() {
        initComponents();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    System.out.println("Encerramento na classe Cadastro");
                    UsuarioTCP usuarioTCP = DaoFactory.createUserTCPCon();
                    //passa o usuario a ser encerrado
                    Usuario usuario = new Usuario(null, null);
                    usuarioTCP.encerraSessao(usuario);
                } catch (Exception ex) {
                    Logger.getLogger(TelaDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void OpenWindowAnimation() {

        String val = "0.0";
        float f = Float.valueOf(val);
        this.setOpacity(f);

        for (double i = 0.0; i <= 1.0; i = i + 0.1) {

            val = i + "";
            f = Float.valueOf(val);
            this.setOpacity(f);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                // Logger.getLogger(teste.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
            }

        }

    }

    private void CloseWindowAnimation() {

        for (double i = 1.0; i >= 0; i = i - 0.1) {

            // jLabel1.setVisible(false);
            String val = i + "";
            float f = Float.valueOf(val);
            this.setOpacity(f);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {

            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        emailText = new javax.swing.JTextField();
        nomeText = new javax.swing.JTextField();
        senhaText = new javax.swing.JPasswordField();
        senha2Text = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cadastroButton = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/logo_redondo.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emailText.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        emailText.setBorder(null);
        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });
        jPanel2.add(emailText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 420, 30));

        nomeText.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        nomeText.setBorder(null);
        nomeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeTextActionPerformed(evt);
            }
        });
        jPanel2.add(nomeText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 420, 40));

        senhaText.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        senhaText.setBorder(null);
        senhaText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                senhaTextMouseClicked(evt);
            }
        });
        senhaText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                senhaTextKeyReleased(evt);
            }
        });
        jPanel2.add(senhaText, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 410, 30));

        senha2Text.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        senha2Text.setBorder(null);
        senha2Text.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                senha2TextMouseClicked(evt);
            }
        });
        senha2Text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                senha2TextActionPerformed(evt);
            }
        });
        senha2Text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                senha2TextKeyReleased(evt);
            }
        });
        jPanel2.add(senha2Text, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 410, 30));

        jLabel6.setFont(new java.awt.Font("Ink Free", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Sua senha deve ter no m??nimo 6 caracteres");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 190, 40));

        jLabel7.setFont(new java.awt.Font("Ink Free", 0, 10)); // NOI18N
        jLabel7.setText("Sua senha deve ter no m??nimo 6 caracteres");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 190, 40));

        jLabel1.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Nome de usu??rio ou email ja cadastrado!");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 250, 30));

        jLabel3.setFont(new java.awt.Font("Ink Free", 0, 22)); // NOI18N
        jLabel3.setText("Cadastrar ");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 670, -1, -1));

        cadastroButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pressbutton_2.png"))); // NOI18N
        cadastroButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cadastroButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cadastroButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cadastroButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cadastroButtonMouseExited(evt);
            }
        });
        jPanel2.add(cadastroButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 660, 150, 50));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("??? ");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel5.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setText("As senhas n??o s??o iguais!");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 620, 200, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela_cadastro.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 490, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void paintItRed() {
        jLabel5.setVisible(true);
        senhaText.setForeground(Color.red);
        senha2Text.setForeground(Color.red);
        jLabel1.setVisible(false);

    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jLabel1.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(true);
    }//GEN-LAST:event_formWindowOpened

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // seta de retorno esta invisivel por estar por debaixo do jPanel, esta no canto esquerdo superior
        GerenciadorDeFluxo chamaMain = new GerenciadorDeFluxo();
        chamaMain.setContFlux(0);
        chamaMain.fluxoPrincipal();
        dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        jLabel4.setForeground(Color.MAGENTA);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setForeground(Color.gray);
    }//GEN-LAST:event_jLabel4MouseExited

    private void cadastroButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cadastroButtonMouseClicked
        String senha1 = new String(senhaText.getPassword()).trim();
        String senha2 = new String(senha2Text.getPassword()).trim();

        if (senha1.length() >= 6 && senha2.length() >= 6) {
            if (senha1.equals(senha2)) {

                if (!senha1.equals("") && !senha2.equals("") && !nomeText.getText().equals("") && !emailText.getText().equals("")) {

                    try {
                        Usuario usuario = new Usuario(nomeText.getText(), senha1, emailText.getText());
                        UsuarioTCP usuarioTCP = DaoFactory.createUserTCPCon();

                        if (usuarioTCP.register(usuario)) {

                            GerenciadorDeFluxo chamaMain = new GerenciadorDeFluxo();
                            chamaMain.setUsuAtual(usuario);
                            chamaMain.setContFlux(0);
                            chamaMain.fluxoPrincipal();
                            dispose();
                            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso");
                        } else {
                            jLabel1.setVisible(true);
                        }
                        //  setVisible(false);
                    } catch (IOException ex) {
                        Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    paintItRed();
                }

                //JOptionPane.showMessageDialog(null, "Bem vindo!!!");
            } else {

                paintItRed();

            }
        } else {
            jLabel6.setVisible(true);
            jLabel7.setVisible(false);
        }
    }//GEN-LAST:event_cadastroButtonMouseClicked

    private void cadastroButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cadastroButtonMouseEntered
        cadastroButton.setIcon(new ImageIcon(getClass().getResource("/images/pressbutton_2.png")));
    }//GEN-LAST:event_cadastroButtonMouseEntered

    private void cadastroButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cadastroButtonMouseExited
        cadastroButton.setIcon(new ImageIcon(getClass().getResource("/images/button_2.png")));
    }//GEN-LAST:event_cadastroButtonMouseExited

    private void senha2TextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senha2TextKeyReleased
        senha2Text.setForeground(Color.black);
    }//GEN-LAST:event_senha2TextKeyReleased

    private void senha2TextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_senha2TextMouseClicked
        senha2Text.setForeground(Color.black);
    }//GEN-LAST:event_senha2TextMouseClicked

    private void senhaTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaTextKeyReleased
        senhaText.setForeground(Color.black);
    }//GEN-LAST:event_senhaTextKeyReleased

    private void senhaTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_senhaTextMouseClicked
        senhaText.setForeground(Color.black);
    }//GEN-LAST:event_senhaTextMouseClicked

    private void nomeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeTextActionPerformed

    private void senha2TextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_senha2TextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_senha2TextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cadastroButton;
    private javax.swing.JTextField emailText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nomeText;
    private javax.swing.JPasswordField senha2Text;
    private javax.swing.JPasswordField senhaText;
    // End of variables declaration//GEN-END:variables
}
