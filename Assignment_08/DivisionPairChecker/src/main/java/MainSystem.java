public class MainSystem {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        UserInputWrapper userInputWrapper = inputHandler.readInputs();
        DivisorPairSolver divisorPairSolver = new DivisorPairSolver();

        try {
            int[] results = divisorPairSolver.findPairCounts(userInputWrapper);
            System.out.println("Output:");
            for (int result : results) {
                System.out.println(result);
            }
        }
        catch (InvalidTestCaseException invalidTestCaseException){
            System.out.println(invalidTestCaseException.getMessage());
        }
    }
}
