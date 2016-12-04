package io.github.wapmesquita.diffservice.model;

public class Line {

    private String leftValue;
    private String rightValue;
    private boolean equal;
    private Integer lineNumber;

    public Line() {
    }

    public Line(String leftValue, String rightValue, boolean equal) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.equal = equal;
    }

    public void setValues(String leftValue, String rightValue) {
        this.setLeftValue(leftValue);
        this.setRightValue(rightValue);
    }

    public String getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(String leftValue) {
        this.leftValue = leftValue;
    }

    public String getRightValue() {
        return rightValue;
    }

    public void setRightValue(String rightValue) {
        this.rightValue = rightValue;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Line{" +
                "leftValue='" + leftValue + '\'' +
                ", rightValue='" + rightValue + '\'' +
                ", equal=" + equal +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
