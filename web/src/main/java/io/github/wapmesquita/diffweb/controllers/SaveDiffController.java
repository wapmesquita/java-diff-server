package io.github.wapmesquita.diffweb.controllers;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import io.github.wapmesquita.diffservice.service.DiffService;
import io.github.wapmesquita.diffservice.service.DiffServiceImpl;
import io.github.wapmesquita.diffweb.utils.Constants;
import io.github.wapmesquita.diffweb.enums.DiffOption;
import io.github.wapmesquita.diffweb.exception.RestException;

public class SaveDiffController {

    private DiffService diffService = new DiffServiceImpl();

    public void save(String id, DiffOption diffOption, List<String> value) {
        validateParams(id, diffOption, value);

        if (diffOption == DiffOption.LEFT) {
            diffService.saveLeft(id, value);
        }
        else {
            diffService.saveRight(id, value);
        }
    }

    private void validateParams(String id, DiffOption diffOption, List<String> value) {
        if (StringUtils.isEmpty(id)) {
            throw new RestException(Constants.HTTP_BAD_REQUEST, "Invalid id!");
        }

        if (diffOption == null) {
            throw new RestException(Constants.HTTP_BAD_REQUEST, "Invalid option!");
        }

        if (value == null) {
            throw new RestException(Constants.HTTP_BAD_REQUEST, "Value to compare is null!");
        }
    }

}
