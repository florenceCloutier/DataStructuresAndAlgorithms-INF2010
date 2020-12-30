package tests;

import tp2.Interview;
import tp2.MatchingPair;

import java.util.Collection;
import java.util.LinkedList;

public class InterviewTester {
    public static Double start (){
        // Worst Case : O(n^2)
        // > O(n^2) => 0

        // Average Case
        // O(n) => 3.5
        // O(n log n) => 2
        // O(n^2) => 0.5
        // > O(n^2) => 0
        double timeComplexityPoints = 3.5;

        double subTotal = 0.0;

        subTotal += Corrector.executeUnitTest("matchingPairsWithEmptyValueArray", InterviewTester::matchingPairsWithEmptyValueArray, 0.25);
        subTotal += Corrector.executeUnitTest("matchingPairsWithNoMatchingPairs", InterviewTester::matchingPairsWithNoMatchingPairs, 0.25);

        subTotal += Corrector.executeUnitTest("matchingPairsOnlyPositiveNumbers", InterviewTester::matchingPairsOnlyPositiveNumbers, 0.25);
        subTotal += Corrector.executeUnitTest("matchingPairsWithNegativeNumbers", InterviewTester::matchingPairsWithNegativeNumbers, 0.25);

        subTotal += Corrector.executeUnitTest("matchingPairsWithSumOfSameNumber", InterviewTester::matchingPairsWithSumOfSameNumber, 0.5);

        subTotal += Corrector.executeUnitTest("matchingPairsContainsNoPermutations", InterviewTester::matchingPairsContainsNoPermutations, 1.0);
        subTotal += Corrector.executeUnitTest("matchingPairsContainsAllCombinations", InterviewTester::matchingPairsContainsAllCombinations, 1.5);

        return subTotal + timeComplexityPoints;
    }

    public static boolean matchingPairsWithEmptyValueArray(){
        Interview myInterview = new Interview();

        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(new LinkedList<Integer>(), 10);
        if (matchingPairs == null) return false;

        return matchingPairs.isEmpty();
    }

    public static boolean matchingPairsWithNoMatchingPairs() {
        Interview myInterview = new Interview();

        Collection<Integer> values = new LinkedList<Integer>();
        int n = 100;
        for (int i = 1; i <= n; ++i) values.add(i);

        int targetSum = 2*n;
        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, targetSum);
        if (matchingPairs == null) return false;

        return matchingPairs.isEmpty();
    }

    public static boolean matchingPairsOnlyPositiveNumbers() {
        Interview myInterview = new Interview();

        Collection<Integer> values = new LinkedList<Integer>();
        int n = 99;
        for (int i = 0; i <= n; ++i) values.add(i);

        int targetSum = n/2;
        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, targetSum);
        if (matchingPairs == null) return false;

        Collection<MatchingPair> validMatchingPairs = new LinkedList<MatchingPair>();
        for (int i = 0; i <= targetSum/2; ++i) validMatchingPairs.add(new MatchingPair(i, targetSum - i));

        return matchingPairs.containsAll(validMatchingPairs) &&
                validMatchingPairs.containsAll(matchingPairs) &&
                matchingPairs.size() == validMatchingPairs.size();
    }

    public static boolean matchingPairsWithNegativeNumbers() {
        Interview myInterview = new Interview();

        Collection<Integer> values = new LinkedList<Integer>();
        int n = 100;
        for (int i = 1; i <= n/2; ++i) {
            values.add(i);
            values.add(-i);
        }

        int targetSum = 0;
        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, targetSum);
        if (matchingPairs == null) return false;

        Collection<MatchingPair> validMatchingPairs = new LinkedList<MatchingPair>();
        for (int i = 1; i <= n/2; ++i) validMatchingPairs.add(new MatchingPair(i, targetSum - i));

        return matchingPairs.containsAll(validMatchingPairs) &&
                validMatchingPairs.containsAll(matchingPairs) &&
                matchingPairs.size() == validMatchingPairs.size();
    }

    public static boolean matchingPairsWithSumOfSameNumber() {
        Interview myInterview = new Interview();

        int value = 5;
        Collection<Integer> values = new LinkedList<Integer>();
        values.add(value);

        int targetSum = value * 2;

        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, targetSum);
        if (matchingPairs == null) return false;
        boolean isValidWithoutEnoughOccurrences = matchingPairs.isEmpty();

        values.add(value);

        matchingPairs = myInterview.matchingPairs(values, targetSum);
        if (matchingPairs == null) return false;
        boolean isValidWithEnoughOccurrences = matchingPairs.size() == 1 && matchingPairs.contains(new MatchingPair(value,value));

        return isValidWithoutEnoughOccurrences && isValidWithEnoughOccurrences;
    }

    public static boolean matchingPairsContainsNoPermutations() {
        Interview myInterview = new Interview();

        Collection<Integer> values = new LinkedList<Integer>();
        int value1 = 4;
        int value2 = 6;
        values.add(value1);
        values.add(value2);

        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, 10);
        if (matchingPairs == null) return false;

        boolean containsPermutations = matchingPairs.size() != 1;

        MatchingPair onlyGoodPair = new MatchingPair(value1,value2);
        matchingPairs.removeIf((matchingPair) -> matchingPair.equals(onlyGoodPair));
        boolean containsOnlyGoodPairs = matchingPairs.isEmpty();

        return !containsPermutations && containsOnlyGoodPairs;
    }

    public static boolean matchingPairsContainsAllCombinations() {
        Interview myInterview = new Interview();

        Collection<Integer> values = new LinkedList<Integer>();
        int n = 100;
        int value1 = 4;
        int value2 = 6;
        for (int i = 0; i < n; ++i) {
            values.add(value1);
            values.add(value2);
        }

        Collection<MatchingPair> matchingPairs = myInterview.matchingPairs(values, 10);
        if (matchingPairs == null) return false;

        int combinationCount = (int)Math.pow(n,2);
        boolean containsAllCombinations = matchingPairs.size() == combinationCount;

        MatchingPair onlyGoodPair = new MatchingPair(value1,value2);
        matchingPairs.removeIf((matchingPair) -> matchingPair.equals(onlyGoodPair));
        boolean containsOnlyGoodPairs = matchingPairs.isEmpty();

        return containsAllCombinations && containsOnlyGoodPairs;
    }
}
