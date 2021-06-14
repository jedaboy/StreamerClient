/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoRepository.impl;

import daoRepository.UsuarioTCP;
import dbRepository.DB;
import entities.Mensagem;
import entities.Status;
import entities.Usuario;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by VarunKr on 22-04-2015.
 */
public class UsuarioClientTCP implements UsuarioTCP {

    private Socket skt = null;

    public UsuarioClientTCP(Socket skt) throws IOException {

        this.skt = skt;
        //userId = 1;
    }

    @Override
    public Socket getSocketTCP() {
        return skt;
    }

    public void getTCPCon() throws IOException {

        //  m = (Mensagem) oIS.readObject();
        // System.out.println(m.getOperacao());
    }

    //Registra o usuario.
    @Override
    public boolean register(Usuario usuario) {
        try {
            Scanner sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "registro!";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("REGISTRO");
            m.setStatus(Status.REGISTRO);
            m.setParam("registro", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Usuario para cadastro enviado");

            Mensagem r = (Mensagem) oIS.readObject();
            if (r.getStatus().equals(Status.PARAMERROR)) {
                return false;
            } else {
                return true;
            }

        } catch (IOException ex) {
            System.out.println("TCP conection error, registro");
            ex.printStackTrace();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    //Procura um usuario com mesmo nome ou email no DB.
    //Realiza o login.
    @Override
    public Usuario login(Usuario usuario) throws Exception {
        Usuario usuarioLogin = null;
        try {

            Scanner sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();

            System.out.println("Enviando Mensagem");

            String msg = "login!";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("LOGIN");
            m.setStatus(Status.AUTENTICACAO);
            m.setParam("Login", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Usuario para login enviado");

            usuarioLogin = (Usuario) oIS.readObject();
            System.out.println("Usuario para login recebido");
            return usuarioLogin;

        } catch (Exception ex) {

            return null;

        }
    }

    @Override
    public void encerraSessao(Usuario usuario) throws Exception {

        try {
            ObjectInputStream oIS = null;
            ObjectOutputStream oOS = null;
            Scanner sc = null;
            sc = new Scanner(System.in);
            // skt = new Socket("3.134.125.175", 19224);

            //protocolo do app
            oOS = DB.getOutputStream();
            oIS = DB.getInputStream();
            //oIS = new ObjectInputStream(skt.getInputStream());

            System.out.println("Enviando Mensagem");

            String msg = "encerramento!";
            oOS.writeUTF(msg);
            oOS.flush();

            Mensagem m = new Mensagem("ENCERRAMENTO");
            m.setStatus(Status.SESSAO_ENCERRADA);
            m.setParam("Encerra_Sessao", usuario);

            oOS.writeObject(m);
            oOS.flush(); //libera buffer
            System.out.println("Solicitação de encerramento enviada");

            // usuarioLogin = (Usuario) oIS.readObject();
            //System.out.println("Usuario para login recebido");
            // return usuarioLogin;
        } catch (Exception ex) {

            System.out.println("Falha na solicitação de encerramento: " + ex);
            ex.printStackTrace();
        }

        /*Metodo para instanciar um objeto com os dados do DB.
    private Usuario instantiateUsuario (ResultSet rs) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setLogou(true);
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }*/
    }
}
