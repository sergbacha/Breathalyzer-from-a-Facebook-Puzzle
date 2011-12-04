/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package breathalyzer;

/**
 *
 * @author admin
 */
public class Breathalyzer {
    
    public static final String WORDS_FILE = "/Users/admin/NetBeansProjects/Breathalyzer/src/breathalyzer/tw106.txt";
//TEMP SENTENCE
    public static final String INPUT_TEXT = "tihs sententcnes iss nout varrry goud";


//    tihs sententcnes iss nout varrry goud
    public static final String RANDOM_WORD1 = "tihs";
    public static final String RANDOM_WORD2 = "sententcnes";
    public static final String RANDOM_WORD3 = "iss";
    public static final String RANDOM_WORD4 = "nout";
    public static final String RANDOM_WORD5 = "varrry";
    public static final String RANDOM_WORD6 = "goud";

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //if (args[0] == null){
        //    return;
        //}
        
        Breathalyzer b = new Breathalyzer();

        Dictionary d = new Dictionary();
        d.populateDictionary(WORDS_FILE);
        boolean x = d.contains(RANDOM_WORD1);

        int y = 0;
        int distance = y;
        y = d.getClosestWordDistance(RANDOM_WORD1);
        distance+=y;
        y = d.getClosestWordDistance(RANDOM_WORD2);
        distance+=y;
        y = d.getClosestWordDistance(RANDOM_WORD3);
        distance+=y;
        y = d.getClosestWordDistance(RANDOM_WORD4);
        distance+=y;
        y = d.getClosestWordDistance(RANDOM_WORD5);
        distance+=y;
        y = d.getClosestWordDistance(RANDOM_WORD6);
        distance+=y;


        System.out.println(x);
        System.out.println(y);
     }
}
