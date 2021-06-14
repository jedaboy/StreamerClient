/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package views;

import controllers.GerenciadorDeFluxo;
import viewsFunctions.TelaFactory;
import daoRepository.DaoFactory;
//import daoRepository.MusicaDao;
//import daoRepository.UsuarioDao;
import entities.Musica;
import entities.Usuario;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import viewsFunctions.TelaInterface;
import daoRepository.UsuarioTCP;
import dbRepository.DB;
import entities.MusicData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jedaboy
 */
public class TelaAvaliacaoMusica extends javax.swing.JFrame {

    DefaultListModel lista = new DefaultListModel(); // cria uma lista compativel com  o campo do Jlist (propriedades, model, lista)
    DefaultListModel lista2 = new DefaultListModel(); // Esta lista2 é da jList que tem as musicas recomendadas
    ImageIcon image; //imagem que acompanha a musica
    MusicData musicData;

    private Usuario usuario;
    private Timer playTimer;
    private Timer clickTimer;

    private AudioInputStream ain = null;
    private int aux;
    private int flag = 0;
    private int tempoClick = 0;
    private int duracao = 0;
    private int idMusicaAnterior = 0;
    private int musicasClicadas = 0;
    private int cont = 0;
    private int secondsToSkip = 0;
    private SourceDataLine sourceDataLine = null;
    private boolean boolFlag = false;
    private boolean ignoreStateChange = false;
    private boolean started = false;
    private boolean bool = true;
    int repeat = 0;
    boolean recomendacao;

    private ObjectOutputStream oOS = null;
    private ObjectInputStream oIS = null;

    // getters e setters para saber qual é o usuario atual
    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;
    }

    // getters e setters para determinar qual a jlist a  ser exibida
    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;
    }

    //Principal construtor da classe
    public TelaAvaliacaoMusica() throws IOException, SQLException {

        initComponents();

        labelPic.setText("");

        playTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                atualizacaoDeEstado();
            }
        });

        clickTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    tempoClick = 0;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //delimita a entrada de avaliações apenas a caracteres numericos
        onlyNum();

        action.setVisible(false);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Timer atualizacaoTardia = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("a");
                        if (!slider.getValueIsAdjusting()) {
                            if (cont > 0) {
                                sourceDataLine.stop();
                                started = false;
                            }

                            secondsToSkip = slider.getValue();

                            // bifurcacao
                            if (secondsToSkip > aux) {
                                if (!boolFlag) {

                                } else {
                                    try {
                                        // resetado, voltou ao menos uma vez
                                        System.out.println("resetado");
                                        if (ain.markSupported()) {
                                            ain.reset();
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } else {
                                try {
                                    //reseta
                                    if (cont > 0) {
                                        System.out.println("resetado");
                                        if (ain.markSupported()) {
                                            ain.reset();
                                        }
                                    }
                                    boolFlag = true;
                                } catch (IOException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            if (secondsToSkip > 0) {

                                try {
                                    selecionaPontoDaMusica(secondsToSkip);
                                    cont = secondsToSkip;
                                    sourceDataLine.start();
                                    started = true;
                                } catch (UnsupportedAudioFileException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }

                    }
                });

                //faz com que a thread que cuida do slider não se repita indefinidamente
                atualizacaoTardia.setRepeats(false);

                // ignora a atualização do slider a cada segundo
                if (ignoreStateChange) {
                    return;
                }
                // restarta a thread
                atualizacaoTardia.restart();

            }
        });

        // manda mensagem para o servidor quando o usuario fecha a aplicação
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    System.out.println("Encerramento na classe T.A.M");
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

    //construtor secundario para continuar com a musica em curso (temporariamente descontinuado)
    public TelaAvaliacaoMusica(MusicData musicData) throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {

        playTimer = musicData.getPlayTimer();

        image = musicData.getImage();
        //mp3DataDao = musicData.getMp3DataDao();

        initComponents();
        labelPic.setText("");

        //sourceLoad();
        playTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                atualizacaoDeEstado();
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Timer atualizacaoTardia = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        try {
                            //String fileName = "C:\\Users\\jedaf\\Desktop\\sou-um-milagre-eliana-ribeiro.mp3";
                            // AudioInputStream audioInputStream = program.getAudioInputStream(fileName);

                            if (!slider.getValueIsAdjusting()) {

                                secondsToSkip = slider.getValue();

                                //System.out.println("bifurcacao");
                                if (secondsToSkip > aux) {
                                    if (!boolFlag) {
                                        System.out.println("live stream, sempre em frente");

                                        if (ain.markSupported()) {
                                            ain.reset();
                                        }
                                    } else {
                                        System.out.println("resetado, voltou pelo menos uma vez");
                                        if (ain.markSupported()) {
                                            ain.reset();
                                        }
                                    }
                                } else {
                                    System.out.println("reseta");
                                    if (ain.markSupported()) {
                                        ain.reset();
                                    }
                                    boolFlag = true;
                                }

                                //System.out.println("A");
                                // System.out.println("Cheguei aqui");
                                if (secondsToSkip > 0) {

                                    selecionaPontoDaMusica(secondsToSkip);
                                    cont = secondsToSkip;

                                    new Thread() {
                                        @Override
                                        public void run() {

                                            try {
                                                tocaStreamDeMusica();
                                            } catch (IOException ex) {
                                                Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (UnsupportedAudioFileException ex) {
                                                Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (LineUnavailableException ex) {
                                                Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                        }

                                    };

                                }
                            }

                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

                atualizacaoTardia.setRepeats(false);

                if (ignoreStateChange) {
                    return;
                }
                atualizacaoTardia.restart();

            }
        });

        labelPic.setIcon(image);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    System.out.println("Encerramento na classe T.A.M");
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

    //delimita a entrada de avaliações apenas a caracteres numericos
    public void onlyNum() {

        TelaInterface tela = TelaFactory.createTesteImpl();
        tela.onlyNum(notaText);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        setaRetorno = new javax.swing.JLabel();
        ListaPainel1 = new javax.swing.JScrollPane();
        listaMusicas = new javax.swing.JList<>();
        notaText = new javax.swing.JTextField();
        confirmacaoLabel = new javax.swing.JLabel();
        alertaLabel = new javax.swing.JLabel();
        ListaPainel2 = new javax.swing.JScrollPane();
        listaRecomendacoes = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        avaliarButton = new javax.swing.JLabel();
        labelTimeCounter = new javax.swing.JLabel();
        action = new javax.swing.JButton();
        totalDuration = new javax.swing.JLabel();
        slider = new javax.swing.JSlider();
        labelPic = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/logo_redondo.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
        jPanel1.add(setaRetorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 25));

        ListaPainel1.setBorder(null);

        listaMusicas.setModel(lista);
        listaMusicas.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaMusicasAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaMusicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMusicasMouseClicked(evt);
            }
        });
        ListaPainel1.setViewportView(listaMusicas);

        jPanel1.add(ListaPainel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 85, 290, 220));

        notaText.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        notaText.setBorder(null);
        notaText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                notaTextKeyPressed(evt);
            }
        });
        jPanel1.add(notaText, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 432, 60, 20));
        notaText.getAccessibleContext().setAccessibleName("");

        confirmacaoLabel.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        confirmacaoLabel.setText("Musica Avaliada!");
        jPanel1.add(confirmacaoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 100, 20));

        alertaLabel.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        alertaLabel.setForeground(new java.awt.Color(255, 0, 51));
        alertaLabel.setText("entrada invalida");
        jPanel1.add(alertaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 100, 20));

        ListaPainel2.setBorder(null);

        listaRecomendacoes.setModel(lista2);
        listaRecomendacoes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaRecomendacoesAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaRecomendacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaRecomendacoesMouseClicked(evt);
            }
        });
        ListaPainel2.setViewportView(listaRecomendacoes);

        jPanel1.add(ListaPainel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 85, 310, 220));

        jLabel3.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        jLabel3.setText("Avaliar");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 60, 40));

        avaliarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pressbutton_4.png"))); // NOI18N
        avaliarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        avaliarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseExited(evt);
            }
        });
        jPanel1.add(avaliarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 110, 60));

        labelTimeCounter.setText("00:00:00");
        jPanel1.add(labelTimeCounter, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 410, -1, -1));

        action.setText("Play");
        action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actionMouseClicked(evt);
            }
        });
        jPanel1.add(action, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 460, -1, -1));

        totalDuration.setText("Duração: ");
        totalDuration.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                totalDurationAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel1.add(totalDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 410, -1, -1));

        slider.setMaximum(464);
        slider.setValue(0);
        jPanel1.add(slider, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 400, -1, -1));
        jPanel1.add(labelPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 440, 280));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/recomendacao.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void setaRetornoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseClicked
        try {  // seta de retorno para dashboard
            GerenciadorDeFluxo chamaMain;
            //  musicData = new MusicData(image, mp3DataDao, playTimer, frameCount, duration, format, clip, playing);
            chamaMain = new GerenciadorDeFluxo(musicData);

            //if (playing) {
            //   MiniTela mT = new MiniTela(musicData);
            //    mT.setVisible(true);
            //    chamaMain.setMiniTela(mT, playing);
            //  }
            chamaMain.setUsuAtual(usuario);
            chamaMain.setContFlux(2);
            chamaMain.fluxoPrincipal();
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_setaRetornoMouseClicked

    private void setaRetornoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseEntered
        setaRetorno.setForeground(Color.MAGENTA);

    }//GEN-LAST:event_setaRetornoMouseEntered

    private void setaRetornoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseExited
        setaRetorno.setForeground(Color.gray);
    }//GEN-LAST:event_setaRetornoMouseExited

    //inicia a lista1  com musicas aleatorias ordenadas pela nota
    private void listaMusicasAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaMusicasAncestorAdded

        /*   if (!recomendacao) {
            MusicaDao musicaDao = DaoFactory.createMusicaDao();
            TelaInterface tela = TelaFactory.createTesteImpl();
            tela.prencheLista(lista, lista2, usuario, recomendacao);
            tela.sortLista(lista);
            // sortLista();
            listaMusicas.setModel(lista);
        }*/

    }//GEN-LAST:event_listaMusicasAncestorAdded

    //cria uma lista dinamica "L", que recebe todos os elementos da DefaultListModel, lista do jlist
    // "L" então é ordenada de acordo com a media geral, e em seguida invertida para a ordem nao crescente
    // ordenador da lista1, cuja as musicas são de generos variados
    //exibe o alerta de entrada invalida em vermelho
    public void paintItRed() {
        alertaLabel.setVisible(true);
        alertaLabel.setForeground(Color.red);

    }

    //Limita a entrada para dados numericos apenas
    private void notaTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notaTextKeyPressed

        onlyNum();

    }//GEN-LAST:event_notaTextKeyPressed
    //Alerta de entrada invalida, determina o que estará visivel ao inicio da janela
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //determina o que estará visivel ao inicio da janela
        gerenciadorDeLista(recomendacao);

    }//GEN-LAST:event_formWindowOpened
    //determina o que estará visivel ao inicio da janela

    private void gerenciadorDeLista(Boolean recomendacao) {

        alertaLabel.setVisible(false);
        confirmacaoLabel.setVisible(false);
        ListaPainel2.setVisible(recomendacao);
        ListaPainel1.setVisible(!recomendacao);

    }

    //inicia a lista2  com musicas aleatorias dos generos favoritos do usuario, ordenadas pela nota
    private void listaRecomendacoesAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaRecomendacoesAncestorAdded

        TelaInterface tela = TelaFactory.createTesteImpl();
        tela.prencheLista(lista2, usuario);
        tela.sortLista2(lista2);
        listaRecomendacoes.setModel(lista2);

    }//GEN-LAST:event_listaRecomendacoesAncestorAdded

    private void avaliarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseClicked

        TelaInterface tela = TelaFactory.createTesteImpl();
        /*caso o usuario tenha clicado em recomendação o jList apresentado sera o que tem as musicas 
        da preferencia do mesmo caso não tenha clicado o jList em questão tera musicas de todos os generos, 
        com exceção das  musicas que ele já avaliou, este trecho também determina qual jlist esta tendo seus
        elementos selecionados pelo mouse*/
        alertaLabel.setVisible(false);
        confirmacaoLabel.setVisible(false);

        tela.avaliaMusicas(confirmacaoLabel, usuario, alertaLabel, lista, lista2, notaText, listaMusicas, listaRecomendacoes, recomendacao);

    }//GEN-LAST:event_avaliarButtonMouseClicked

    private void avaliarButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseEntered
        avaliarButton.setIcon(new ImageIcon(getClass().getResource("/images/pressbutton_4.png")));
    }//GEN-LAST:event_avaliarButtonMouseEntered

    private void avaliarButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseExited
        avaliarButton.setIcon(new ImageIcon(getClass().getResource("/images/button4.png")));
    }//GEN-LAST:event_avaliarButtonMouseExited

    private void listaMusicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMusicasMouseClicked

    }//GEN-LAST:event_listaMusicasMouseClicked

    private void totalDurationAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_totalDurationAncestorAdded

    }//GEN-LAST:event_totalDurationAncestorAdded

    private void actionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actionMouseClicked
        //labelPic.setIcon(image);
        //carrega imagemIcon com blob

        if (!started) {
            if (cont == 0) {
                try {

                    System.out.println("Cont: " + cont);
                    System.out.println("SourceLoad! ");
                    sourceLoad();

                } catch (SQLException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("sourceDataLine start! ");
                sourceDataLine.start();
                started = true;

            }
            action.setText("Stop");

            playTimer.start();

        } else {
            System.out.println("sourceDataLine stop! ");
            sourceDataLine.stop();
            started = false;
            action.setText("Play");
            playTimer.stop();

        }


    }//GEN-LAST:event_actionMouseClicked

    private void listaRecomendacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaRecomendacoesMouseClicked

        if (tempoClick == 0) {
            TelaInterface tela = TelaFactory.createTesteImpl();
            int IndexMusica = listaRecomendacoes.getSelectedIndex();
            Musica musica = listaRecomendacoes.getModel().getElementAt(IndexMusica);

            int id = musica.getId();
            if (idMusicaAnterior == id && flag == 1) {
                System.out.println("grugluYehYeah!");
            } else {
                //interrompe a stream no servidor
                if (flag > 0) {
                    System.out.println("FLAG: " + flag);
                    tela.setId(id);
                    tela.interrompeStreamTCP();
                    sourceDataLine.stop();
                    //encerra a thread de execução sonora
                    bool = false;
                    flag = 0;

                    System.out.println("Mecanismo de interrupção acionado!");

                } else {

                    duracao = musica.getDuracao();
                    totalDuration.setText("duração:" + converteSeg(duracao));
                    slider.setMaximum(musica.getDuracao());
                    slider.setValue(0);
                    System.out.println("FLAG: " + flag);

                    tela.setId(id);
                    labelPic.setIcon(tela.enviaIdTcp(musicasClicadas));
                    bool = true;
                    repeat = 0; 
                    playTimer.stop();
                    cont = 0;
                    if (started) {
                        System.out.println("Started == true ");
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("sourceDataLine stop! ");
                                    sourceDataLine.stop();
                                    started = false;
                                    action.setText("Play");
                                    sourceLoad();

                                    System.out.println("---------------------------------------FIM DA THREAD DE SKIP-------------------------------------------");

                                } catch (UnsupportedAudioFileException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                } catch (LineUnavailableException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(TelaAvaliacaoMusica.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }.start();
                    }
                }
                action.setVisible(true);
                musicasClicadas++;
                tempoClick = 1;
                clickTimer.start();
            }
            idMusicaAnterior = id;
        }
    }//GEN-LAST:event_listaRecomendacoesMouseClicked

    //carrego o audio
    public void sourceLoad() throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        new Thread() {
            @Override
            public void run() {
                System.out.println("Tier 2");
                try {

                    ObjectInputStream oIS = null;

                    //protocolo do app
                    oIS = DB.getInputStream();
                    // add buffer for mark/reset support

                    InputStream bufferedIn = new BufferedInputStream(oIS);
                    ain = AudioSystem.getAudioInputStream(bufferedIn);

                    if (flag == 0) {
                        System.out.println("Marca de Reset no segundo: " + cont);
                        ain.mark(Integer.MAX_VALUE);
                        flag = 1;

                    };

                    System.out.println(" Iniciando player de musica ");
                    tocaStreamDeMusica();
                    System.out.println("FIM DA THREAD SOURCELOAD ");

                } catch (IOException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(TelaAvaliacaoMusica.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

        }.start();
    }

    public ByteArrayOutputStream converteSeg(int time) {
        int valor = time;
        int horas = valor / 3600;
        int restoHoras = valor % 3600;
        int minutos = restoHoras / 60;
        int restoMinutos = restoHoras % 60;
        int segundos = restoMinutos;

        System.out.printf(String.valueOf(slider.getValue()));
        System.out.printf(" %02d:%02d:%02d \n", horas, minutos, segundos);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.printf(" %02d:%02d:%02d \n", horas, minutos, segundos);
// Put things back
        System.out.flush();
        System.setOut(old);

        return baos;
    }

    /////////////////////////////////////////// SOURCE DATA LINE IMPLEMENTACAO///////////////////////////////////////////////////////////
    public void atualizacaoDeEstado() {
        ignoreStateChange = true;

        Timer cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                cont++;
                try {
                    if (cont >= duracao) {

                        repeat = 1;
                        System.out.println("Acabou");
                        action.setText("Play");
                        if (ain.markSupported()) {
                            ain.reset();
                        }
                        System.out.println("Stream de audio resetada");
                        selecionaPontoDaMusica(1);
                        sourceDataLine.stop();
                        started = false;
                        playTimer.stop();
                        slider.setValue(0);
                        cont = 0;

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int time = cont;

                baos = converteSeg(time);

                labelTimeCounter.setText(baos.toString());

            }
        });
        cronometro.start();
        cronometro.setRepeats(false);
        cronometro.restart();

        slider.setValue(cont);
        aux = slider.getValue();
        ignoreStateChange = false;
    }

    //pote de ouro!
    private void selecionaPontoDaMusica(int secondsToSkip)
            throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {

        AudioFormat audioFormat = ain.getFormat();
        long bytesToSkip = 16384 * secondsToSkip;
        long justSkipped = 0;

        ain.skip(bytesToSkip);

        //System.out.println("");
    }

    long total = 0;

    //G(OLD)
    public void tocaStreamDeMusica()
            throws IOException, UnsupportedAudioFileException,
            LineUnavailableException {

        try {

            //informação sobre o formato da stream
            AudioFormat format = ain.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                    format);

            // se o formato não for suportado de forma direta, tenta se efetuar a conversão
            // para PCM.
            if (!AudioSystem.isLineSupported(info)) {
                // Este é o formato PCM para o qual queremos converter nosso audio
                // Estes parametros são informações sobre o formato do audio 
                // não precisa entender para usos casuais.
                AudioFormat pcm
                        = new AudioFormat(format.getSampleRate(), 16,
                                format.getChannels(), true, false);
                // Get a wrapper stream around the input stream that does the
                // transcoding for us.

                //Pega um (wrapper: empacotamento(?) ao redor da stream de entrada que faz a transcrição
                //para nos(???)
                ain = AudioSystem.getAudioInputStream(pcm, ain);

                // atualiza o formato e a variavel "info para os novos dados que foram transcritos
                format = ain.getFormat();
                info = new DataLine.Info(SourceDataLine.class, format);
            }

            // Abre a linha através da qual tocaremos o stream de audio
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);

            sourceDataLine.open(format);

            // aloca um buffer para ler e escrever a stream de entrada na linha
            // Make it large enough to hold 4k audio frames*.
            // perceba que a SourceDataLine possui o proprio buffer interno.
            int framesize = format.getFrameSize();
            byte[] buffer = new byte[4 * 1024 * framesize]; // o buffer
            int numbytes = 0;                               // a quantia de bytes

            // nos ainda não iniciamos a linha
            int bytesread = 0;
            while (bytesread != -1 && bool) {  // saimos do loop quando chegamos no fim da stream
                // Primeiro, lemos alguns bytes da stream de entrada
                // interruptor = Thread.interrupted();
                if (started) {

                    bytesread = ain.read(buffer, numbytes, buffer.length - numbytes);
                    // se não tiver mais bytes para se ler estamos encerrados

                    numbytes += bytesread;
                    total += bytesread;

                }
                if (Thread.interrupted()) {
                    break;
                }

                // Agora nos pegamos um pouco de bytes de audio  para escrever nesta linha,
                // tocamos a linha que ira reproduzir os dados que estiverem nela
                if (cont == 0 && !started && repeat != 1) {

                    sourceDataLine.start();

                    System.out.println("Tocando");
                    started = true;
                }

                // We must write bytes to the line in an integer multiple of
                // the framesize.  So figure out how many bytes we'll write.
                //precisamos escrever os bytes na linha em um inteiro multiplo
                //do tamnho do framesize, então .... quantos bytes nos iremos escrever
                // Now write the bytes. The line will buffer them and play
                // them. This call will block until all bytes are written.
                //agora escrevemos os bytes a linha ira buffer(?) e então nos tocaremos ela, 
                // Esta chamada será bloqueada até que todos os bytes sejam gravados.
                if (started) {
                    int bytestowrite = numbytes;
                    sourceDataLine.write(buffer, 0, bytestowrite);
                    numbytes = 0;
                }
                // If we didn't have an integer multiple of the frame size, 
                // then copy the remaining bytes to the start of the buffer.
                // Se não tivermos um múltiplo inteiro do tamanho do frame,
                // então copiamos os bytes restantes para o início do buffer.

            }
            // Now block until all buffered sound finishes playing.
            // Agora bloqueamos até que todo o som armazenado no buffer termine de tocar.
            //sourceDataLine.drain();
        } finally { // Always relinquish the resources we use
            //sempre feche os recusos que utilizar (guarde, encerre ou algo do genero)
            // System.out.println("finally  Stream player");
            //System.out.println(total);

            if (sourceDataLine != null) {
                //System.out.println("SOURCE DATA LINE IS CLOSED ");
                // sourceDataLine.close();
                // started = false;
            }
            if (ain != null) {
                //System.out.println("AUDIO INPUT STREAM IS CLOSED ");
                // ain.close();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ListaPainel1;
    private javax.swing.JScrollPane ListaPainel2;
    private javax.swing.JButton action;
    private javax.swing.JLabel alertaLabel;
    private javax.swing.JLabel avaliarButton;
    private javax.swing.JLabel confirmacaoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel labelPic;
    private javax.swing.JLabel labelTimeCounter;
    private javax.swing.JList<Musica> listaMusicas;
    private javax.swing.JList<Musica> listaRecomendacoes;
    private javax.swing.JTextField notaText;
    private javax.swing.JLabel setaRetorno;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel totalDuration;
    // End of variables declaration//GEN-END:variables
    //private Color color;
}
// esse programinha possui cerca de 5.829 linhas de codigo
