/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package breathalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Dictionary {

    private HashSet<String>[] wordsBySize; //Each index i in the array corresponds to
                                        //a hastable of words of size i


    public Dictionary() {

    }

    /**
     * Read in all the words in the given file into
     * the data structure
     * @param fileName
     */
    public void populateDictionary(String fileName){


        FileReader fr = null;
        BufferedReader br = null;

        
        try {
             fr = new FileReader(fileName);
             br = new BufferedReader(fr);

             //read for the longest word so as to create the right size array
             int longestWord = getLongestWord(br);
             wordsBySize = new HashSet[longestWord];

             br.close();
             fr.close();
             fr = new FileReader(fileName);
             br = new BufferedReader(fr);
             
             readWordsIntoHashSet(br);
                    
        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Just get the size of the longest character in the dictionary
     * @param br
     * @return
     */
    private int getLongestWord(BufferedReader br) {
        String s = null;
        int longest = 0;
        int currentLength;
        try {
            
            //Loop though the words 
            while ((s = br.readLine()) != null) {
                currentLength = s.length();
                if (currentLength > longest)
                    longest = currentLength;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }

        return longest;
    }

    /**
     * Reads the words from the input file into the array of hashsets.
     * The size of the word being read corresponds to the index of the hashset
     * @param br
     */

    private void readWordsIntoHashSet(BufferedReader br) {
        String s = null;
        int currentLength;
        
        try {

            //Loop though the words
            while ((s = br.readLine()) != null) {
                currentLength = s.length();
                if (wordsBySize[currentLength-1] == null){
                    wordsBySize[currentLength-1] = new HashSet<String>();
                }

                wordsBySize[currentLength-1].add(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Checks to see if the words exists in the dictionary
     * @param word
     * @return
     */
    public boolean contains(String word){
        if (wordsBySize[word.length()-1] == null)
            return false;
        
        return wordsBySize[word.length()-1].contains(word);
    }


    /**
     * Returns the minimum number of changes needed to make the passed in word
     * into a valid word in the dictionary
     * 
     * @param word
     * @return
     */
    public int getClosestWordDistance(String word){
        int currentIndex  = word.length()-1;

//        ArrayList indexesToCheck = calculateArrayOfIndexes(word.length());

        int currentDistance = runLevenshteinOnIndex(word, currentIndex);
        if (currentDistance == 1 )
            return currentDistance;
        int minDistance = currentDistance;

        int middleIndex = currentIndex;
        int leftIndex = middleIndex - 1;
        int rightIndex = middleIndex + 1;

        int offset = currentDistance; //offset for index, to go up or down
        int floor = middleIndex - offset;
        int ceiling = ( (middleIndex+offset) < (wordsBySize.length-1) ) ? (middleIndex+offset) : (wordsBySize.length-1);

        boolean aKeepGoing = true;
        boolean bKeepGoing = true;
        while(aKeepGoing && bKeepGoing){
            if(leftIndex > floor){
                currentDistance = runLevenshteinOnIndex(word, leftIndex);
                if (currentDistance < minDistance)
                    minDistance = currentDistance;
                leftIndex--;
                aKeepGoing = true;
            }
            else
                aKeepGoing = false;

            if(rightIndex < ceiling){
                currentDistance = runLevenshteinOnIndex(word, rightIndex);
                if (currentDistance < minDistance)
                    minDistance = currentDistance;
                rightIndex++;
                bKeepGoing = true;
            }
            else
                bKeepGoing = false;
        }
        

        return minDistance;
    }

    /**
     * get the distance betweent the two string
     * @param word
     * @param currentIndex
     * @return
     */

    private int runLevenshteinOnIndex(String word, int index) {
        word = word.toUpperCase();
        Iterator itr = wordsBySize[index].iterator();
        String currentWord = null;
        int minDistance = word.length()+1;
        int currentDistance = 0;

        while(itr.hasNext()){
            currentWord = (String) itr.next();
            currentDistance = getLevenshteinDistance(word, currentWord);

            if( currentDistance < minDistance )
                minDistance = currentDistance;
            
        }

        return minDistance;
    }



    /**
     * returns array containing ints that correspond to the indexes of
     * the wordsBySize array on whose word's we will run the
     * Levenshtein distance algorithm on.
     *
     * @param distance
     * @return
     */
    private ArrayList calculateArrayOfIndexes(int wordLength) {
        ArrayList indexes = new ArrayList();
        indexes.add(wordLength - 1);


        return indexes;
    }

    /**
     *Run Levenshteina algorithm
     *
     * 
//   declare int d[0..m, 0..n]
//
//   for i from 0 to m
//     d[i, 0] := i // the distance of any first string to an empty second string
//   for j from 0 to n
//     d[0, j] := j // the distance of any second string to an empty first string
//
//   for j from 1 to n
//   {
//     for i from 1 to m
//     {
//       if s[i] = t[j] then
//         d[i, j] := d[i-1, j-1]       // no operation required
//       else
//         d[i, j] := minimum
//                    (
//                      d[i-1, j] + 1,  // a deletion
//                      d[i, j-1] + 1,  // an insertion
//                      d[i-1, j-1] + 1 // a substitution
//                    )
//     }
//   }
     *
     * @return
     */
    private int getLevenshteinDistance(String aWord, String bWord) {
        int distance = 0;
        int aLength = aWord.length();
        int bLength = bWord.length();

        int d[][] = new int[aLength+1][bLength+1];
        int i=0;
        int j=0;

        for(; i<=aLength; i++ ){
            d[i][0] = i;// the distance of any first string to an empty second string
        }
        for(; j<=bLength; j++ ){
            d[0][j] = j;// the distance of any second string to an empty first string
        }
        
        for(i=1; i<=aLength; i++){
            for(j=1; j<=bLength; j++){
                if( aWord.charAt(i-1) == bWord.charAt(j-1))// no operation required
                    d[i][j] = d[i-1][j-1];
                else {
                    int min = d[i-1][j]+1;// a deletion
                    if( (d[i][j-1]+1) < min )// an insertion
                        min = d[i][j-1]+1;
                    if( (d[i-1][j-1]+1) < min )// a substitution
                        min = d[i-1][j-1]+1;

                     d[i][j] = min;
                }
                    
            }

        }

        return d[aLength][bLength];
    }
    
}
