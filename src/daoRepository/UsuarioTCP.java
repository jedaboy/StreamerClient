/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoRepository;

import entities.Usuario;
import java.net.Socket;

/**
 *
 * @author jedaf
 */
public interface UsuarioTCP {
    boolean register(Usuario usuario);
    Usuario login(Usuario usuario)throws Exception;
    void encerraSessao(Usuario usuario) throws Exception; 
    public Socket getSocketTCP();
}
