package sokkelo.sisalto;

/**
 *
 * @author lauri
 */
public class Esine extends REMluokka {
    
    /**
     * Esine luokka. Sisältää kaikki esineen ominaisuudet
     */
    public static final char MERKKI = 'E';
    
    /**
     *
     * @param e energia
     * @param rivi Esineen rivi index
     * @param sarake Esineen sarake index
     */
    public Esine(int e, int rivi, int sarake) {
        super.energia(e);
        super.rivi(rivi);
        super.sarake(sarake);
    }
    
    /**
     *
     * @return Esineen merkki
     */
    @Override
    public String ui() {
        return MERKKI+"";
    }
    
    @Override
    public String toString(){
        return this.getClass().getSimpleName() +"    " +EROTIN+super.toString()+EROTIN;
    }
}