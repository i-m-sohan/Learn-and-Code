public class UserInputWrapper {
    private int[] testCaseLimits;
    private int testCases;

    public void setTestCaseLimits(int[] testCaseLimits){
        this.testCaseLimits = testCaseLimits;
    }
    public void setTestCases(int testCases){
        this.testCases = testCases;
    }

    public int[] getTestCaseLimits() {
        return this.testCaseLimits;
    }

    public int getTestCases() {
        return this.testCases;
    }
}