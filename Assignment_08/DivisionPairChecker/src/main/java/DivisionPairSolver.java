import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DivisorPairSolver {
    public List<Integer> calculateDivisorCounts(int maxNumber) {
        List<Integer> divisorCounts = new ArrayList<>(Collections.nCopies(maxNumber + 1, 0));

        for (int divisor = 2; divisor <= maxNumber; divisor++) {
            for (int multiple = divisor; multiple <= maxNumber; multiple += divisor) {
                divisorCounts.set(multiple, divisorCounts.get(multiple) + 1);
            }
        }
        return divisorCounts;
    }

    public int getMaxNumber(int[] testCaseLimits){
        int maxNumber = Integer.MIN_VALUE;
        int testCases = testCaseLimits.length;
        for (int index = 0; index < testCases; index++) {
            if (testCaseLimits[index] > maxNumber) {
                maxNumber = testCaseLimits[index];
            }
        }
        if(maxNumber<0){
            throw new InvalidTestCaseException("Negative Values can not be evaluated");
        }
        return maxNumber;
    }

    public int[] findPairCounts(UserInputWrapper userInputWrapper) {
        if (userInputWrapper.getTestCases() < 1) {
            throw new InvalidTestCaseException("Number of test cases is not enough. Test Cases: " + userInputWrapper.getTestCases());
        }

        int[] testCaseLimits = userInputWrapper.getTestCaseLimits();
        int maxNumber = getMaxNumber(testCaseLimits);
        List<Integer> divisorCounts = calculateDivisorCounts(maxNumber + 1);

        int[] matchingCounts = new int[testCaseLimits.length];
        for (int testCaseIndex = 0; testCaseIndex < testCaseLimits.length; testCaseIndex++) {
            int testCaseValue = testCaseLimits[testCaseIndex];
            matchingCounts[testCaseIndex] = processTestCase(testCaseValue, divisorCounts, testCaseIndex);
        }
        return matchingCounts;
    }

    private int processTestCase(int testCaseValue, List<Integer> divisorCounts, int testCaseIndex) {
        if (testCaseValue < 0) {
            throw new InvalidTestCaseException("Test Case no. " + testCaseIndex + " is not valid.\nTest Case Input Value: " + testCaseValue);
        }
        int pairCount = 0;
        for (int currentNumber = 2; currentNumber < testCaseValue; currentNumber++) {
            if (divisorCounts.get(currentNumber) == divisorCounts.get(currentNumber + 1)) {
                pairCount++;
            }
        }
        return pairCount;
    }
}