package sokkelo;

/**
 *
 * @author lauri
 */
public class Seina extends Sokkelo {

    private final char MERKKI = '.';
    
    /**
     *
     * @param rivi seinän rivi index
     * @param sarake seinän sarake index
     */
    public Seina(int rivi, int sarake) {
            super.rivi(rivi);
            super.sarake(sarake);
    }
    
    /**
     *
     * @return palauttaa seinän merkin
     */
    public char merkki(){
        return MERKKI;
    }

    /**
     *
     * @return merkin tulostaminen
     */
    @Override
    public String ui() {
        return MERKKI+"";
    }
    
    public String toString(){
        return this.getClass().getSimpleName()+"    "+EROTIN+ super.toString()+EROTIN;
    }
}