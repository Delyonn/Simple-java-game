package sokkelo.sisalto;
import apulaiset.Suunnallinen;
import sokkelo.Sokkelo;

/**
 *
 * @author lauri
 */
public abstract class REMluokka extends Sokkelo implements Comparable<REMluokka> {

    private int energia;
    
    /**
     *
     * @return energia
     */
    public int energia() {
        return energia;
    }

    /**
     *
     * @param e energia
     * @throws IllegalArgumentException jos energiaksi asetetaan alle 0
     */
    public void energia(int e) throws IllegalArgumentException {
        if (e >= 0) {
            energia = e;
        } else {
            throw new IllegalArgumentException("Virheellinen energia!");
        }
    }

    @Override
    public int compareTo(REMluokka s) {
        if(energia() > s.energia()) {
            return 1;
        } else if(energia() == s.energia()) {
            return 0;
        } else {
            return -1;
        }
    }
    
    @Override
    public String toString() {
        String e = energia+"";
        while(e.length() <= 3) {
            e=e+" ";
        }
        return super.toString()+EROTIN+e;
    }

}