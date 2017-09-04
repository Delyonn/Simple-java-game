package apulaiset;

/*
 * Scanner-luokan palveluita hydyntv apuluokka int-, double-, char-
 * ja String-tyyppisten sytteiden lukemiseen. Operaatiot lukevat sytett
 * jrpisesti, kunnes kyttj suostuu antamaan oikean tyyppisen sytteen.
 * (Mys pelkn Enter-nppimen painaminen aiheuttaa virheilmoituksen.)
 *
 * Luokkaa kytetn Lausekielinen ohjelmointi I ja II ja Olio-ohjelmoinnin
 * perusteet -kursseilla.
 *
 * Jorma Laurikkala (jorma.laurikkala@uta.fi), Informaatiotieteiden yksikk
 * (tietojenksittelytieteet), Tampereen yliopisto.
 *
 * Versio: 1.1.
 *
 * Viimeksi muutettu 8.9.2015 19.30.
 *
 */

import java.util.*;  // Scanner-luokka tll.

final public class In {
   /*
    * Vakiomuotoiset luokka-attribuutit.
    *
    */

   // Oletussytevirtaan liitetty sytteiden lukija.
   private static final Scanner READER = initializeREADER();

   // Virheilmoitus, joka tulostetaan, kun syte on vr tyyppi.
   private static final String BARF = "Virheellinen syte!";

   // Virheilmoitus, joka tulostetaan, kun on vakava ongelma.
   private static final String AARGH = "Virhe In-luokassa!";

   /*
    * Yksityiset luokkametodit.
    *
    */

   /* Metodi READER-attribuutin alustamiseen.
    */
   private static Scanner initializeREADER() {
      // Luodaan ja liitetn oletussytevirtaan.
      Scanner sc = new Scanner(System.in);

      // Lokalisoidaan siten, ett esimerkiksi desimaalimerkki on piste.
      Locale enLocale = new Locale("en");
      sc.useLocale(enLocale);

      // Palautetaan lukija.
      return sc;
   }

   /* Pysytetn ohjelma, jos jokin meni pahasti pieleen.
    */
   private static void doNotSoGracefulExit(Exception e) {
      // Herjataan.
      System.out.println(AARGH);
      
      // Tulostetaan poikkuspino.
      e.printStackTrace();
      
      // Suljetaan virtuaalikone.
      System.exit(1);
   }

   /*
    * Julkiset luokkametodit.
    *
    */

   /* Luetaan kyttjlt int-tyyppist sytett,
    * kunnes kyttj suostuu sellaisen antamaan.
    */
   public static int readInt() {
      // Luetaan, kunnes saadaan syte.
      int intval = 0;
      boolean inputOK = false;
      do {
         // Luetaan rivi ja yritetn muuttaa se kokonaisluvuksi.
         try {
            intval = Integer.parseInt(READER.nextLine());
            inputOK = true;
         }

         // Siepataan vrn tyypin aiheuttama poikkeus.
         catch (NumberFormatException e) {
            // Herjataan.
            System.out.println(BARF);
            inputOK = false;
         }

         // Siepataan yllttv poikkeus. (Jotain meni pahasti pieleen.)
         catch (Exception e) {
            doNotSoGracefulExit(e);
         }
      }
      while (!inputOK);

      // Palautetaan paluuarvo.
      return intval;
   }

   /* Luetaan kyttjlt double-tyyppist sytett,
    * kunnes kyttj suostuu sellaisen antamaan.
    */
   public static double readDouble() {
      // Luetaan, kunnes saadaan syte.
      double dblval = 0;
      boolean inputOK = false;
      do {
         // Luetaan rivi ja yritetn muuttaa se liukuluvuksi.
         try {
            dblval = Double.parseDouble(READER.nextLine());
            inputOK = true;
         }

         // Siepataan vrn tyypin aiheuttama poikkeus.
         catch (NumberFormatException e) {
            // Herjataan.
            System.out.println(BARF);
            inputOK = false;
         }

         // Siepataan yllttv poikkeus. (Jotain meni pahasti pieleen.)
         catch (Exception e) {
            doNotSoGracefulExit(e);
         }
      }
      while (!inputOK);

      // Palautetaan paluuarvo.
      return dblval;
   }

   /* Luetaan kyttjlt char-tyyppist sytett,
    * kunnes kyttj suostuu sellaisen antamaan.
    */
   public static char readChar() {
      // Luetaan, kunnes saadaan syte.
      char chrval = 0;
      boolean inputOK = false;
      do {
         try {
             // Luetaan rivi.
            String strval = READER.nextLine();

            // Tarkastellaan sytett.
            inputOK = strval.length() == 1;

            // Kyttj antoi yhden merkin, kuten piti.
            if (inputOK)
               chrval = strval.charAt(0);

            // Kyttj ei antanut merkkej tai hn antoi useita merkkej.
            else
               System.out.println(BARF);
         }

         // Siepataan yllttv poikkeus. (Jotain meni pahasti pieleen.)
         catch (Exception e) {
            doNotSoGracefulExit(e);
         }

      }
      while (!inputOK);

      // Palautetaan paluuarvo.
      return chrval;
   }

   /* Luetaan kyttjlt String-tyyppinen syte,
    * joka ei voi olla tyhj merkkijono ("").
    */
   public static String readString() {
      // Luetaan, kunnes saadaan syte.
      String strval = "";
      boolean inputOK = false;
      do {
         try {
            // Luetaan rivi.
            strval = READER.nextLine();

            // Halutaan ainakin yksi merkki.
            inputOK = strval.length() > 0;

            // Herjat tarvittaessa.
            if (!inputOK)
               System.out.println(BARF);
         }

         // Siepataan yllttv poikkeus. (Jotain meni pahasti pieleen.)
         catch (Exception e) {
            doNotSoGracefulExit(e);
         }
      }
      while (!inputOK);

      // Palautetaan paluuarvo.
      return strval;
   }
}
