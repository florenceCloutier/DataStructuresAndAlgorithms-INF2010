/*
 * Implementation de la classe Interview
 * file Interview.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * Interview et l'implementation des methodes de cette derniere.
 */

package tp2;

import java.util.*;
import java.util.HashMap;

public class Interview {
    /**
     * TIME COMPLEXITY POINTS
     * Worst Case : O(n^2)
     * > O(n^2) => 0
     *
     * Average Case
     * O(n) => 3.5
     * O(n log n) => 2
     * O(n^2) => 0.5
     * > O(n^2) => 0
     *
     * Finds all pairs within values which sum up to targetSum
     * @param values All possible values that can form a pair (can contain duplicates)
     * @param targetSum Pairs should add up to this
     * @return A collection containing all valid pairs with no permutations, but all combinations (empty collection if none found)
     */
    public Collection<MatchingPair> matchingPairs(Collection<Integer> values, Integer targetSum){
        ArrayList<MatchingPair> pairs = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        //Creates a Hashmap with the values O(n)
        for (Integer value : values) {
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1); //If the value is present twice or more
            } else {
                map.put(value, 1); //If the value is only there once
            }
        }

        //Searches for pairs in the HashMap O(n)
        for (Integer value : values) {
            Integer complement = targetSum - value;
            if (map.containsKey(complement)) {
                if (value == complement) {
                    int n = map.get(value);
                    int distinctCombination = n*(n-1)/2; //Formula for the number of distinct combination of n elements
                    for (int i = 0; i < distinctCombination; i++) {
                        pairs.add(new MatchingPair(value, complement));
                    }
                } else {
                    int combinations = map.get(value) * map.get(complement);
                    for (int i = 0; i < combinations; i++) {
                        pairs.add(new MatchingPair(value, complement));
                    }
                    map.remove(value);
                }
                map.remove(complement);
            }
        }

        return pairs;
    }
}

