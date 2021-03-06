/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package views;

import controllers.GerenciadorDeFluxo;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import daoRepository.DaoFactory;
//import daoRepository.MusicaDao;
//import daoRepository.UsuarioDao;
import daoRepository.UsuarioTCP;
import daoRepository.impl.UsuarioClientTCP;
import dbRepository.DB;
import entities.Genero;
import entities.Musica;
import entities.Usuario;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import java.util.*;

/**
 *
 * @author Lawliet
 */
public class TelaLogin extends javax.swing.JFrame {

    boolean erro = false;
    UsuarioTCP usuarioTCP;
    /**
     * Creates new form teste
     */
    public TelaLogin() {
        try{
       
        usuarioTCP = DaoFactory.createUserTCPCon();
        
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                                 System.out.println("Encerramento na classe Login");
                  
                    //passa o usuario a ser encerrado
                    Usuario usuario = new Usuario(null,null);
                    usuarioTCP.encerraSessao(usuario);
                }  catch (Exception ex) {
                    Logger.getLogger(TelaDashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        
        }catch(Exception e){
            e.printStackTrace();
        }
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/logo_redondo.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        chewbacca = new javax.swing.JLabel();
        CadastroLabel = new javax.swing.JLabel();
        loginLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/logo_redondo.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLogin.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        txtLogin.setBorder(null);
        txtLogin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoginMouseClicked(evt);
            }
        });
        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoginKeyReleased(evt);
            }
        });
        jPanel1.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 410, 40));

        txtSenha.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        txtSenha.setBorder(null);
        txtSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSenhaMouseClicked(evt);
            }
        });
        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSenhaKeyReleased(evt);
            }
        });
        jPanel1.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 630, 400, 40));

        jLabel1.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Senha ou Us??ario inv??lidos!");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 680, 200, 30));

        jLabel5.setFont(new java.awt.Font("Ink Free", 0, 15)); // NOI18N
        jLabel5.setText("Entrar");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(392, 705, 50, 30));

        chewbacca.setFont(new java.awt.Font("Ink Free", 0, 15)); // NOI18N
        chewbacca.setText("Inscrever-se");
        chewbacca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(chewbacca, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 710, -1, 20));

        CadastroLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CadastroLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CadastroLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CadastroLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CadastroLabelMouseExited(evt);
            }
        });
        jPanel1.add(CadastroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 700, 130, 40));

        loginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabelMouseExited(evt);
            }
        });
        jPanel1.add(loginLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 706, 130, 30));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 790, 150, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Tela_login.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(535, 865));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void OpenWindowAnimation() {

        for (double i = 0.0; i <= 1.0; i = i + 0.1) {

            jLabel1.setVisible(false);
            String val = i + "";
            float f = Float.valueOf(val);
            this.setOpacity(f);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
            }

        }

    }

    private void CloseWindowAnimation() {

        for (double i = 1.0; i >= 0; i = i - 0.1) {

            jLabel1.setVisible(false);
            String val = i + "";
            float f = Float.valueOf(val);
            this.setOpacity(f);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {

            }

        }

    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jLabel1.setVisible(false);

    }//GEN-LAST:event_formWindowOpened

    private void txtSenhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyReleased
        txtSenha.setForeground(Color.black);
    }//GEN-LAST:event_txtSenhaKeyReleased

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void txtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSenhaMouseClicked
        txtSenha.setForeground(Color.black);
    }//GEN-LAST:event_txtSenhaMouseClicked

    private void txtLoginKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyReleased
        txtLogin.setForeground(Color.black);
    }//GEN-LAST:event_txtLoginKeyReleased

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginActionPerformed

    private void txtLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoginMouseClicked
        txtLogin.setForeground(Color.black);
    }//GEN-LAST:event_txtLoginMouseClicked

    //BOTAO LOGIN
    private void loginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseClicked

        try {
            // acao executada quando botao de login eh acionado
            //pega o login do usu??rio
            String nome = txtLogin.getText();
            //pega a senha do usu??rio como char[] e converte para String
            String senha = new String(txtSenha.getPassword()).trim();

            //alteracao jedaboy
            // boolean logou = false;
           
            Usuario usuario = new Usuario(nome, senha);
            usuario = usuarioTCP.login(usuario);

            if (usuario.getLogou()) {

                GerenciadorDeFluxo chamaMain = new GerenciadorDeFluxo();
                //informa qual tela deve ser exibida em seguida, neste caso a tela dashboard
                chamaMain.setContFlux(2);
                //passa o id do usuario Atual para a classe gerenciador de fluxo
                chamaMain.setUsuAtual(usuario);
               
                if (usuario == null) {
                    System.out.println("Usuario Null");
                }

                if (usuarioTCP == null) {
                    System.out.println("Conex??o Null LOGIN");
                }

                chamaMain.setUsuTCPAtual(usuarioTCP);
                //chama a clase fluxo principal que chamara a classe gerenciador de tela para exibir a tela desejada
                chamaMain.fluxoPrincipal();
                // fecha a janela atual
                dispose();

            }

        } catch (Exception e) {
            jLabel1.setVisible(true);
            txtLogin.setForeground(Color.red);
            txtSenha.setForeground(Color.red);
            e.printStackTrace();
        }
    }//GEN-LAST:event_loginLabelMouseClicked

    // BOTAO CADASTRO
    private void CadastroLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CadastroLabelMouseClicked
        GerenciadorDeFluxo chamaMain = new GerenciadorDeFluxo();
        chamaMain.setContFlux(1);
        chamaMain.fluxoPrincipal();
        dispose();
    }//GEN-LAST:event_CadastroLabelMouseClicked

    private void loginLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseEntered
        loginLabel.setIcon(new ImageIcon(getClass().getResource("/images/pressbutton.png")));
    }//GEN-LAST:event_loginLabelMouseEntered

    private void loginLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseExited
        loginLabel.setIcon(new ImageIcon(getClass().getResource("/images/button.png")));
    }//GEN-LAST:event_loginLabelMouseExited

    private void CadastroLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CadastroLabelMouseEntered
        CadastroLabel.setIcon(new ImageIcon(getClass().getResource("/images/pressbutton.png")));
    }//GEN-LAST:event_CadastroLabelMouseEntered

    private void CadastroLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CadastroLabelMouseExited
        CadastroLabel.setIcon(new ImageIcon(getClass().getResource("/images/button.png")));
    }//GEN-LAST:event_CadastroLabelMouseExited

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CadastroLabel;
    private javax.swing.JLabel chewbacca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
