package io.github.wapmesquita.diffservice.service;

import java.util.List;

import io.github.wapmesquita.diffmodel.model.Diff;
import io.github.wapmesquita.diffservice.exception.DiffServerIllegalArgumentException;
import io.github.wapmesquita.diffservice.exception.DiffServerNotFoundException;
import io.github.wapmesquita.diffservice.model.CalculatedDiff;

public interface DiffService {
    Diff find(String id) throws DiffServerNotFoundException;

    Diff saveLeft(String id, List<String> value);

    Diff saveRight(String id, List<String> value);

    CalculatedDiff calculateDiff(Diff diff) throws DiffServerIllegalArgumentException;

    CalculatedDiff calculateDiff(List<String> left, List<String> right) throws DiffServerIllegalArgumentException;
}
