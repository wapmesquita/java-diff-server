package io.github.wapmesquita.diffservice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.github.wapmesquita.diffservice.exception.DiffServerIllegalArgumentException;

/**
 * The class used to store the diff result compiled.
 */
public class CalculatedDiff {

    private DiffResult type;
    private int lineNumber = 1;
    private List<Line> lines = new ArrayList<>();

    public CalculatedDiff(DiffResult type) {
        this.type = type;
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public DiffResult getType() {
        return type;
    }

    public void addLine(Line line) {
        line.setLineNumber(lineNumber++);
        lines.add(line);
    }

    /**
     * Add a new line to the result and compare if the lines are equals
     */
    public void addLine(String leftValue, String rightValue) throws DiffServerIllegalArgumentException {
        if (leftValue == null || rightValue == null) {
            throw new DiffServerIllegalArgumentException("Values can not be null!");
        }

        boolean equal = leftValue.equals(rightValue);
        Line line = new Line(leftValue, rightValue, equal);

        this.addLine(line);
    }

    public List<Line> getDifferentLines() {
        return this.getLines().stream().filter(l -> !l.isEqual()).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        CalculatedDiff that = (CalculatedDiff) o;

        if (lineNumber != that.lineNumber) { return false; }
        if (type != that.type) { return false; }
        return lines.equals(that.lines);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + lineNumber;
        result = 31 * result + lines.hashCode();
        return result;
    }
}
