import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class DivisorPairSolverTest {

    private DivisorPairSolver solver;
    @BeforeEach
    void setUp() {
        solver = new DivisorPairSolver();
    }

    @Test
    void testCalculateDivisorCounts() {
        List<Integer> expectedCounts = Arrays.asList(0, 0, 1, 1, 2, 1, 3, 1, 3, 2, 3, 1);
        List<Integer> actualCounts = solver.calculateDivisorCounts(11);
        assertEquals(expectedCounts, actualCounts);
    }

    @Test
    void testFindPairCounts() {
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        int[] testCaseLimits = {10, 20, 30};
        int[] expectedResults = {1, 2, 4};
        userInputWrapper.setTestCaseLimits(testCaseLimits);
        userInputWrapper.setTestCases(3);
        int[] actualResults = solver.findPairCounts(userInputWrapper);
        assertArrayEquals(expectedResults, actualResults);
    }

    @Test
    void testFindPairCountsSingle() {
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        int[] testCaseLimits = {15};
        int[] expectedResults = {2};
        userInputWrapper.setTestCaseLimits(testCaseLimits);
        userInputWrapper.setTestCases(1);
        int[] actualResults = solver.findPairCounts(userInputWrapper);
        assertArrayEquals(expectedResults, actualResults);
    }

    @Test
    void testFindPairCountsWithLargeLimit() {
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        int[] testCaseLimits = {100};
        int[] expectedResults = {15};
        userInputWrapper.setTestCaseLimits(testCaseLimits);
        userInputWrapper.setTestCases(1);
        int[] actualResults = solver.findPairCounts(userInputWrapper);
        assertArrayEquals(expectedResults, actualResults);
    }

    @Test
    void testFindPairCountsWithNegativeNumbers() {
        int[] testCaseLimits = {-10, -20, -30};
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        userInputWrapper.setTestCaseLimits(testCaseLimits);
        userInputWrapper.setTestCases(3);
        assertThrows(InvalidTestCaseException.class, () -> solver.findPairCounts(userInputWrapper));
    }

    @Test
    void testFindPairCountsWithZeroTestCase() {
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        userInputWrapper.setTestCases(0);
        assertThrows(InvalidTestCaseException.class, () -> solver.findPairCounts(userInputWrapper));
    }
}