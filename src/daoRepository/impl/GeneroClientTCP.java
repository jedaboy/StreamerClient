/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package daoRepository.impl;

import daoRepository.GeneroTCP;
import dbRepository.DB;
import dbRepository.DbException;
import entities.Genero;
import entities.Mensagem;
import entities.Status;
import entities.Usuario;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class GeneroClientTCP implements GeneroTCP {

    private Socket skt = null;

    public GeneroClientTCP(Socket skt) {
        this.skt = skt;
    }

    //Lista todos os generos disponiveis no DB.
    @Override
    public List<Genero> getGenerosTCP() {

        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "pega_generos";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO);
            //m.setParam("registro", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            List<Genero> generos = (List<Genero>) r.getParam("Generos");

            System.out.println("lista de generos recebida");

            return generos;
        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void addGeneroFavTCP(Usuario usuario, Genero genero) {
        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando  Mensagem");

            String msg = "adiciona_generos";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO_ADD);
            m.setParam("Usuario", usuario);
            m.setParam("Genero", genero);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            //  Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            //  List<Genero> generos = (List<Genero>) r.getParam("Generos");
            System.out.println("Genero adicionado");

        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();

        }
    }

    @Override
    public void rmvGeneroFavTCP(Usuario usuario, Genero genero) {
        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "remove_genero";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO_RMV);
            m.setParam("Usuario", usuario);
            m.setParam("Genero", genero);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            //  Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            //  List<Genero> generos = (List<Genero>) r.getParam("Generos");
            System.out.println("Genero removido");

        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();

        }

    }

    @Override
    public List<Genero> getGeneroFavTCP(Usuario usuario) {

        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "pega_generos_favoritos";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO_FAV);
            m.setParam("Usuario", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            List<Genero> generos = (List<Genero>) r.getParam("Generos_Favoritos");

            System.out.println("lista de generos favoritos recebida");

            return generos;
        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();
            return null;
        }
    }
    //Lista os generos nao favoritados do usuario.

    //Retorna o genero que possui o id informado.
    @Override
    public Genero getGeneroByIdTCP(Integer idGenero) {
        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "pega_genero_pelo_id";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO_BY_ID);
            m.setParam("IDGenero", idGenero);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            Genero genero = (Genero) r.getParam("Genero_By_ID");

            System.out.println("Genero by id recivedIH");

            return genero;
        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> getIdGeneroFavTCP(Usuario usuario) {
        try {

            //skt = new Socket("3.134.125.175", 19224);
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "pega_genero_favorito_pelo_id";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("GENEROS");
            m.setStatus(Status.GENERO_USUARIO);
            m.setParam("Genero_Usuario", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer

            Mensagem r = (Mensagem) oIS.readObject();
            // Mensagem reply = null;   
            List<Integer> ids = (List<Integer>) r.getParam("IdGeneroFav");

            System.out.println("Genero by id recivedIH");

            return ids;
        } catch (Exception ex) {
            System.out.println("TCP conection error");
            ex.printStackTrace();
            return null;
        }
    }

    //Instancia um genero a partir do ResultSet inforamado.
    /*  private Genero instantiateGenero(ResultSet rs) throws SQLException {
        Genero genero = new Genero();
        genero.setId(rs.getInt("id"));
        genero.setNome(rs.getString("nome"));
        return genero;
    }*/
}
