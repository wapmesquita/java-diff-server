package io.github.wapmesquita.diffweb.dto;

import java.util.List;

import io.github.wapmesquita.diffservice.model.DiffResult;
import io.github.wapmesquita.diffservice.model.Line;

public class DiffDtoClient {

    public List<String> linesLeft;
    public List<String> linesRight;
    public DiffResult diffResult;
    public List<Line> differentLines;

    @Override
    public String toString() {
        return "DiffDtoClient{" +
                "linesLeft=" + linesLeft +
                ", linesRight=" + linesRight +
                ", diffResult=" + diffResult +
                ", differentLines=" + differentLines +
                '}';
    }
}
