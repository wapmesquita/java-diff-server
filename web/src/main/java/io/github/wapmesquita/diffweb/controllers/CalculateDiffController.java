package io.github.wapmesquita.diffweb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wapmesquita.diffmodel.model.Diff;
import io.github.wapmesquita.diffservice.exception.DiffServerIllegalArgumentException;
import io.github.wapmesquita.diffservice.exception.DiffServerNotFoundException;
import io.github.wapmesquita.diffservice.model.CalculatedDiff;
import io.github.wapmesquita.diffservice.service.DiffService;
import io.github.wapmesquita.diffservice.service.DiffServiceImpl;
import io.github.wapmesquita.diffweb.utils.Constants;
import io.github.wapmesquita.diffweb.dto.DiffDto;
import io.github.wapmesquita.diffweb.exception.RestException;

public class CalculateDiffController {

    private static final Logger log = LoggerFactory.getLogger(CalculateDiffController.class);

    private DiffService diffService = new DiffServiceImpl();

    public DiffDto calculateDiff(String id) {
        log.info("Calculating diff {}", id);
        try {
            Diff diff = diffService.find(id);
            CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);
            return new DiffDto(diff, calculatedDiff);
        }
        catch (DiffServerNotFoundException e) {
            throw new RestException(Constants.HTTP_NOT_FOUND, e.getMessage(), e);
        }
        catch (DiffServerIllegalArgumentException e) {
            throw new RestException(Constants.HTTP_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
