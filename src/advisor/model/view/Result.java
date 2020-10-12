package advisor.model.view;

public class Result {
    private String output;
    private Result(String output) {
        this.output = output;
    }

    public static Result of(String output) {
        return new Result(output);
    }
    public static Result empty(){
        return new Result("");
    }

    public String getOutput() {
        return output;
    }
}
