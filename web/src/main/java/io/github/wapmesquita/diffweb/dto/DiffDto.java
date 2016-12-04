package io.github.wapmesquita.diffweb.dto;

import java.util.List;

import io.github.wapmesquita.diffmodel.model.Diff;
import io.github.wapmesquita.diffservice.model.CalculatedDiff;
import io.github.wapmesquita.diffservice.model.DiffResult;
import io.github.wapmesquita.diffservice.model.Line;

public class DiffDto {

    private Diff diff;
    private CalculatedDiff calculatedDiff;

    public DiffDto() {
    }

    public DiffDto(Diff diff, CalculatedDiff calculatedDiff) {
        this.diff = diff;
        this.calculatedDiff = calculatedDiff;
    }

    public List<String> getLinesLeft() {
        return this.diff.getLeft();
    }

    public List<String> getLinesRight() {
        return this.diff.getRight();
    }

    public List<Line> getDifferentLines() {
        return this.calculatedDiff.getDifferentLines();
    }

    public DiffResult getDiffResult() {
        return this.calculatedDiff.getType();
    }
}
