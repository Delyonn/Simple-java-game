package sokkelo.sisalto;

import apulaiset.Suunnallinen;

/**
 *
 * @author lauri
 */
public abstract class Liikkuvat extends REMluokka implements Suunnallinen {
    private char suunta;
    
    @Override
    public char suunta() {
        return suunta;
    }
    
    @Override
    public void suunta(char ilmansuunta) throws IllegalArgumentException {
        if (ilmansuunta == POHJOINEN) {
            suunta = POHJOINEN;
        } else if (ilmansuunta == ETELA) {
            suunta = ETELA;
        } else if (ilmansuunta == ITA) {
            suunta = ITA;
        } else if (ilmansuunta == LANSI) {
            suunta = LANSI;
        }
    }
    @Override
    public String toString() {
        return super.toString()+EROTIN+suunta+"   ";
    }

}
