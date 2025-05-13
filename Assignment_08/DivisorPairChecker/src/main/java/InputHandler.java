import java.util.Scanner;

class InputHandler {
    public UserInputWrapper readInputs() {
        UserInputWrapper userInputWrapper = new UserInputWrapper();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of test cases: ");
        int testCases = scanner.nextInt();
        userInputWrapper.setTestCases(testCases);
        int[] testCaseLimits = new int[testCases];
        System.out.println("Enter the values:");
        for (int index = 0; index < testCases; index++) {
            testCaseLimits[index] = scanner.nextInt();
        }
        userInputWrapper.setTestCaseLimits(testCaseLimits);
        scanner.close();
        return userInputWrapper;
    }
}