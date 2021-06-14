/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package viewsFunctions;

import viewsFunctions.impl.TelaImpl;

public class TelaFactory {

    int i;
   
  

    public static TelaInterface createTesteImpl(){
        return new TelaImpl();
    }

}

