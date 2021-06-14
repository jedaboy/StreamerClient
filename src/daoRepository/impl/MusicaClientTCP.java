package daoRepository.impl;

/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
import daoRepository.DaoFactory;
import daoRepository.GeneroTCP;
//import daoRepository.MusicaDao;
import daoRepository.MusicaTCP;
import dbRepository.DB;
import dbRepository.DbException;
import entities.Genero;
import entities.Mensagem;
import entities.Musica;
import entities.Status;
import entities.Usuario;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class MusicaClientTCP implements MusicaTCP {

    private Connection connection;
    private Socket skt = null;
    private int userId;
    private ObjectInputStream oIS = null;

    public MusicaClientTCP(Socket skt) {
        this.skt = skt;
    }

    public int getId() {

        return userId;
    }

    public void setId(int userId) {

        this.userId = userId;
    }

    @Override
    public ImageIcon enviaIdTCP(int cont) throws SQLException,
            UnsupportedAudioFileException, IOException {
        try {

            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "Envia_id " + userId;
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("STREAM");
            m.setStatus(Status.STREAM_ID);
            m.setParam("Musica_id", userId);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            System.out.println("Tier 1: enviaId");

            System.out.println("Enviando  Mensagem 2");
            msg = "Enviando imagem: oOS";

           boolean flag = true;
            System.out.println("cont: " + cont);
            long a = 0;

            if (cont > 0) {
                try {
                    byte buffer[] = new byte[oIS.available()];
                    do {

                        a += oIS.readByte();
                        if (oIS.available() == 0) {
                            
                            System.out.println("AV 1: "+oIS.available());
                            sleep(400);
                            System.out.println("AV 2: "+oIS.available());
                            if(oIS.available() == 0){
                            System.out.println("AV 3: "+oIS.available());
                            flag = false;
                            throw new Exception("Saia do loop, saia do loop imediatamente!");
                            }
                        }

                    } while (flag);

                } catch (Exception ex) {

                }
            }

            oOS.writeUTF(msg);
            oOS.flush();

            Image image = null;

            System.out.println("bYTES SKIPAdos: " + a);
            System.out.println(oIS.available());

            InputStream bufferedIn = new BufferedInputStream(oIS);
            image = ImageIO.read(bufferedIn);
            ImageIcon imageIc = new ImageIcon(image);
            System.out.println("Tier 1.5: Recebe imagem");
            return imageIc;
  
        } catch (OptionalDataException ex) {
            System.out.println("TCP conection error: enviaIdTCP: eof " + ex.eof);
            System.out.println("TCP conection error: enviaIdTCP: length " + ex.length);

            // return false;
        }
        return null;
    }

    @Override
    public void interrompeStreamTCP() throws SQLException,
            UnsupportedAudioFileException, IOException {
        try {
            Scanner sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();

            System.out.println("Interrupção");

            String msg = "Interrupção: musica selecionada -   " + userId;
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("STREAM");
            m.setStatus(Status.STREAM_END);
            m.setParam("Musica_id", userId);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Tier 1: interrompe");

            //return true;
        } catch (Exception ex) {
            System.out.println("TCP conection error: interrompeIdTCP");
            Logger.getLogger(MusicaClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            // return false;
        }
    }

    //Retorna todas as musicas do sistema.
    @Override
    public List<Musica> listarMusicasTCP() {
        try {
            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "listar_musicas";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("MUSICAS");
            m.setStatus(Status.ALL_MUSICS);
            //m.setParam("registro", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            List<Musica> musicas = (List<Musica>) r.getParam("All_Music");

            System.out.println("lista de musicas recebida");

            return musicas;
        } catch (Exception ex) {
            System.out.println("TCP conection error: listar musicas");
            ex.printStackTrace();
            return null;
        }

    }

    //Retorna todas as musicas que possuem pelo menos um genero favoritado pelo usuario.
    @Override
    public List<Musica> recomendacoesTCP(Usuario usuario
    ) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement("SELECT id_genero FROM usuario_genero WHERE usuario_genero.id_usuario = ?");

            st.setInt(1, usuario.getId());

            rs = st.executeQuery();

            //Caso o usuario nao possua generos favoritos, o sistema cria uma exception avisando o usuario.
            if (!rs.next()) {
                throw new DbException("O usuario nao possui generos favoritos!");
            }

            //modificado pois não exibia as musicas do primeiro genero favorito, pega os generos fav
            boolean a = true;
            List<Integer> idGeneros = new ArrayList<>();
            while (a) {
                idGeneros.add(rs.getInt("id_genero"));
                //  JOptionPane.showMessageDialog(null,idGeneros.toString());
                if (!rs.next()) {
                    a = false;
                }
            }

            List<Musica> musicas = new ArrayList<>();
            //MUDANCA 
            GeneroTCP generoDao = DaoFactory.createGenTCPCon();

            for (Integer idGenero : idGeneros) {                         //idGenero
                musicas.addAll(recomendacoesGeneroTCP(generoDao.getGeneroByIdTCP(idGenero)));
            }

            return musicas;
        } catch (SQLException e) {
            throw new DbException("Erro ao carregar as recomendacoes");
        } catch (IOException ex) {
            Logger.getLogger(MusicaClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    //Retorna todas as musicas que possuem pelo menos um genero favoritado pelo usuario e que nao foram avaliadas pelo usuario.
    @Override
    public List<Musica> musicasFavoritasNaoAvaliadasTCP(Usuario usuario
    ) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement("SELECT * FROM usuario_musica WHERE usuario_musica.id_usuario = ?");

            st.setInt(1, usuario.getId());

            rs = st.executeQuery();

            List<Integer> ids = new ArrayList<>();

            //Adiciona todos os ids das musicas ja avaliadas pelo usuario em uma lista.
            while (rs.next()) {
                int aux = rs.getInt("id_musica");
                ids.add(aux);
            }
            List<Musica> list = recomendacoesTCP(usuario);

            //Remove todas as musicas ja avaliadas pelo usuario da lista de recomendacoes.
            for (Iterator<Integer> i = ids.iterator(); i.hasNext();) {
                Integer id = i.next();
                for (Iterator<Musica> j = list.iterator(); j.hasNext();) {
                    Musica musica = j.next();
                    if (musica.getId() == id) {
                        j.remove();
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao carregar musicas nao avaliadas!");
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Integer> musicasFavoritasJaAvaliadasTCP(Usuario usuario
    ) {
        try {
            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "musicas_favoritas_avaliadas";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("MUSICAS");
            m.setStatus(Status.MUSICS_FAV_AV);
            m.setParam("Usuario", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            List<Integer> musicas = (List<Integer>) r.getParam("M_F_J_A");

            System.out.println("lista de musicas recebida");

            return musicas;
        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();
            return null;
        }

    }


    /* public List<Musica> musicasNaoAvaliadas(Usuario usuario) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement("SELECT * FROM usuario_musica WHERE usuario_musica.id_usuario = ?");

            st.setInt(1, usuario.getId());

            rs = st.executeQuery();

            List<Integer> ids = new ArrayList<>();

            //Adiciona todos os ids das musicas ja avaliadas pelo usuario em uma lista.
            while (rs.next()) {
                int aux = rs.getInt("id_musica");
                ids.add(aux);
            }

            /*funcao listarMusicas pega  todas as musicas da base de dados 
            e as coloca em uma lista e aqui as armazemanos em um array dinamico
            List<Musica> list = listarMusicas();

            //Remove todas as musicas ja avaliadas pelo usuario da lista de musicas.
            //percorre cada elemento do vetor ids 
           /* for (Musica musica : list) {
                for (Integer id : ids) {

                    if (musica.getId() == id) {
                        list.remove(musica);
                    }
                }
            }

            for (Iterator<Integer> i = ids.iterator(); i.hasNext();) {
                Integer id = i.next();
                for (Iterator<Musica> j = list.iterator(); j.hasNext();) {
                    Musica musica = j.next();
                    if (musica.getId() == id) {
                        j.remove();
                    }
                }
            }

            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao carregar musicas nao avaliadas!");
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    } */
    //Avalia a musica informada e adiciona no DB a informacao de qual usuario avaliou.
    @Override
    public void avaliarMusicaTCP(Usuario usuario, Musica musica,
            Integer nota
    ) {

        //Verifica se a nota inserida pelo usuario é valida.
        if (nota > 5 || nota < 1) {
            throw new DbException("Apenas valores entre 1 e 5");
        }

        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "Avaliação";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("MUSICAS");
            m.setStatus(Status.AVALIACAO);
            m.setParam("Usuario", usuario);
            m.setParam("Musica", musica);
            m.setParam("Nota", nota);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            //  Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            //  List<Genero> generos = (List<Genero>) r.getParam("Generos");
            System.out.println("Avaliacao enviada");
        } catch (IOException ex) {
            Logger.getLogger(MusicaClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Erro na avaliacao da musica!");
        }
    }

    //Retorna a musica com o id informado.
    @Override
    public Musica getMusicaByIdTCP(Integer id
    ) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            //Procura no DB a musica pelo id informado.
            st = connection.prepareStatement("SELECT * FROM musica WHERE musica.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            //Se houver uma musica com o id informado, o metodo retorna a musica.
            if (rs != null) {
                rs.next();
                Musica musica = instantiateMusica(rs);
                return musica;
            } //Se nao houver uma musica com o id informado, joga uma exception.
            else {
                throw new DbException("Musica nao encontrada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    //Retorna uma lista de musicas do genero informado.

    private List<Musica> recomendacoesGeneroTCP(Genero genero) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            //Busca no DB todas as musicas de um determinado genero.
            st = connection.prepareStatement("SELECT id_musica FROM genero_musica WHERE genero_musica.id_genero = ?");

            st.setInt(1, genero.getId());

            rs = st.executeQuery();

            List<Musica> musicasGen = new ArrayList<>();

            //Verifica se existem musicas do genero informado. Se nao houver, joga uma exception.
            if (!rs.next()) {
                throw new DbException("Nao existem musicas do genero informado!");
            } else {
                //Enquanto existir musicas do genero, adiciona as musicas na lista.
                while (rs.next()) {
                    //Instancia uma musica com os dados do ResultSet.
                    Musica musica = getMusicaByIdTCP(rs.getInt("id_musica"));
                    musicasGen.add(musica);
                }
            }
            return musicasGen;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    //Retorna uma musica com os dados do ResultSet informado.
    private Musica instantiateMusica(ResultSet rs) throws SQLException {
        //Instancia uma musica com os dados do RS.
        Musica musica = new Musica();
        musica.setId(rs.getInt("id"));
        musica.setGenId(rs.getInt("genero"));
        musica.setNome(rs.getString("nome"));
        musica.setArtista(rs.getString("artista"));
        musica.setNota(rs.getInt("nota"));
        musica.setAvaliacoes(rs.getInt("avaliacoes"));

        PreparedStatement st = null;
        ResultSet rs1 = null;
        //Procura os generos da musica informada.
        try {
            st = connection.prepareStatement("SELECT id_genero FROM genero_musica WHERE id_musica = ?");

            st.setInt(1, musica.getId());

            rs1 = st.executeQuery();

            //Cria a lista de generos e adiciona todos os generos da musica informada.        
            //Seta a lista criada como a lista de generos da musica.
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs1);
        }

        return musica;
    }
}
