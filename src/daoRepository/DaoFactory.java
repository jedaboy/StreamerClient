/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package daoRepository;

import daoRepository.impl.GeneroClientTCP;
import daoRepository.impl.Mp3DataClientTCP;
import daoRepository.impl.UsuarioClientTCP;
import daoRepository.impl.MusicaClientTCP;
//import daoRepository.impl.MusicaDaoJDBC;
//import daoRepository.impl.UsuarioDaoJDBC;
import dbRepository.DB;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class DaoFactory {

   
  
   public static Mp3DataTCP createMp3DataTCP() throws IOException, SQLException {
        return new Mp3DataClientTCP(DB.getTCP());
    }
    
    public static MusicaTCP createMusicTCPCon() {
        return new MusicaClientTCP(DB.getTCP());
    }

    public static UsuarioClientTCP createUserTCPCon() throws IOException {
        return new UsuarioClientTCP(DB.getTCP());
    }

    public static GeneroClientTCP createGenTCPCon() throws IOException {
        return new GeneroClientTCP(DB.getTCP());
    }
}
