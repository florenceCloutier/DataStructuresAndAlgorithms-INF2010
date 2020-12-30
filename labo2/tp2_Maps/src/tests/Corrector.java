package tests;

import java.util.Collections;
import java.util.function.Supplier;

public final class Corrector {
    private static Integer unitTestCount = 0;
    private static Integer testerCount = 0;

    public static double start() {
        double subTotal = 0.0;

        double codeStylePoints = 1.0;

        subTotal += executeTester("HashMapTester", HashMapTester::start, 11.5);
        subTotal += executeTester("InterviewTester", InterviewTester::start, 7.5);

        return subTotal + codeStylePoints;
    }

    public static double executeUnitTest(String unitTestName, Supplier<Boolean> function, double pointsForTest) {
        String prefix = String.join("", Collections.nCopies(testerCount, "    "));
        prefix += String.join("", Collections.nCopies(++unitTestCount, " -> "));

        System.out.println(prefix + unitTestName + ":");

        boolean isSuccess = false;

        try {
            isSuccess = function.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        double result = isSuccess ? pointsForTest : 0.0;

        System.out.println(prefix + result + " / " + pointsForTest);
        --unitTestCount;

        return result;
    }

    public static double executeTester(String testerName, Supplier<Double> function, double outOf) {
        String prefix = String.join("", Collections.nCopies(testerCount++, "    "));
        System.out.println(prefix + "[ " + testerName + " ]");

        double result = function.get();

        result = Math.min(result, outOf);

        System.out.println(prefix + result + " / " + outOf);
        --testerCount;

        return result;
    }
}
