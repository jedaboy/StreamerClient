package daoRepository;

import entities.Genero;
import entities.Usuario;

import java.util.List;

public interface GeneroTCP {

    
    
    List<Genero> getGenerosTCP();
    
    void addGeneroFavTCP(Usuario usuario, Genero genero);

    void rmvGeneroFavTCP(Usuario usuario, Genero genero);
    
    List<Genero> getGeneroFavTCP(Usuario usuario);
    
    List<Integer> getIdGeneroFavTCP(Usuario usuario);

    Genero getGeneroByIdTCP(Integer a);
}
