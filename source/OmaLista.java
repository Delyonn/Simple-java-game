package source;

import fi.uta.csjola.oope.lista.*;

/**
 *
 * @author lauri
 */
public class OmaLista extends LinkitettyLista {
    
    /**
     *
     * @param alkio
     * @return
     */
    public Object poistaKaikki(Object alkio){
        if(alkio != null){
            for(int i = 0; i < koko(); i++){
                if(alkio == alkio(i)){
                    poista(i);
                    i--;
                }
            }
            return alkio;
        } else {
            return null;
        }
    }

    /**
     *
     * @param alkio
     * @return
     */
    public Object poista(Object alkio) {
        if(alkio != null) {
            for(int i = 0; i < koko(); i++){
                if(alkio == alkio(i)){
                    poista(i);
                    return alkio;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     *
     * @param classname
     * @return
     */
    public Object alkio(String classname) {
        for (int i = 0; i < koko(); i++) {
            if(alkio(i).getClass().getSimpleName().equals(classname)) {
                return alkio(i);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     *
     * @param classname
     * @return
     */
    public Object poista(String classname) {
        if (classname != null) {
            for(int i = 0; i < koko(); i++) {
                if (classname.equals(alkio(i).getClass().getSimpleName())) {
                    return poista(i);
                }
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     *
     * @param o
     */
    @SuppressWarnings("unchecked")
    public void lisaaTarkasti(Object o) {
        if(koko() == 0 && o != null) {
            lisaaAlkuun(o);
        } else if(o != null && o instanceof Comparable){
            for(int i = 0; i < koko(); i++){
                Comparable vertaa = (Comparable)o;
                int tulos = vertaa.compareTo(alkio(i));
                if (tulos < 0){
                    lisaa(i, o);
                    return;
                }
            }
            lisaaLoppuun(o);
        }
    }

    /**
     *
     * @param classname
     * @return
     */
    public boolean sisaltaa(String classname) {
        if(classname != null && !classname.equals("")) {
            for (int i = 0; i < koko(); i++) {
                if(alkio(i).getClass().getSimpleName().equals(classname)) {
                    return true;
                }
            }
        }
        return false;
    }
}