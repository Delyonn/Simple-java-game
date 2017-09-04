package sokkelo.sisalto;

/**
 *
 * @author lauri
 */
public class Robotti extends Liikkuvat {
    
    /**
     * Robotti luokka
     */
    public static final char MERKKI = 'R';
    
    /**
     *
     * @param e Energia
     * @param s Suunta
     * @param rivi Robotin rivi index
     * @param sarake Robotin sarake index
     * @throws IllegalArgumentException epäonnistuessa
     */
    public Robotti(int e, char s, int rivi, int sarake) throws IllegalArgumentException {
        super.energia(e);
        super.rivi(rivi);
        super.sarake(sarake);
        super.suunta(s);
    }
    

    /**
     *
     * @return Robotin merkki
     */
    @Override
    public String ui() {
        return MERKKI+"";
    }
    
    @Override
    public String toString(){
        return this.getClass().getSimpleName()+"  "+ EROTIN+ super.toString()+EROTIN;
    }
}