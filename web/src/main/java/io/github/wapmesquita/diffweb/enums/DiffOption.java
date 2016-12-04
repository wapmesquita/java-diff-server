package io.github.wapmesquita.diffweb.enums;

import java.util.Arrays;

import io.github.wapmesquita.diffweb.utils.Constants;
import io.github.wapmesquita.diffweb.exception.RestException;

public enum DiffOption {
    LEFT("left"), RIGHT("right");

    private String option;

    DiffOption(String option) {
        this.option = option;
    }

    public static DiffOption fromOption(String option) {
        return Arrays.stream(DiffOption.values())
                .filter(o -> o.getOption().equals(option))
                .findFirst()
                .orElseThrow(() -> new RestException(Constants.HTTP_NOT_FOUND, "Option " + option + " not found!"));
    }

    public String getOption() {
        return option;
    }
}
