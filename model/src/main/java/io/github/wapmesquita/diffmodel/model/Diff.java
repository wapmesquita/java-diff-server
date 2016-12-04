package io.github.wapmesquita.diffmodel.model;

import java.util.Collections;
import java.util.List;

public class Diff extends BaseEntity<String> {

    private String id;
    private List<String> left;
    private List<String> right;

    public Diff() {
    }

    public Diff(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public List<String> getLeft() {
        if (left == null) { return Collections.emptyList(); }
        return left;
    }

    public void setLeft(List<String> left) {
        this.left = left;
    }

    public List<String> getRight() {
        if (right == null) { return Collections.emptyList(); }
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }
}
