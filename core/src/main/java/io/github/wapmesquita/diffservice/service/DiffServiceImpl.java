package io.github.wapmesquita.diffservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import io.github.wapmesquita.diffmodel.model.Diff;
import io.github.wapmesquita.diffmodel.repositories.DiffRepository;
import io.github.wapmesquita.diffmodel.repositories.DiffRepositoryImpl;
import io.github.wapmesquita.diffservice.exception.DiffServerIllegalArgumentException;
import io.github.wapmesquita.diffservice.exception.DiffServerNotFoundException;
import io.github.wapmesquita.diffservice.model.CalculatedDiff;

import static io.github.wapmesquita.diffservice.model.DiffResult.EQUAL;
import static io.github.wapmesquita.diffservice.model.DiffResult.NOT_EQUAL_SIZE;
import static io.github.wapmesquita.diffservice.model.DiffResult.SAME_SIZE;

public class DiffServiceImpl implements DiffService {

    private static final Logger log = LoggerFactory.getLogger(DiffServiceImpl.class);

    private DiffRepository diffRepository = new DiffRepositoryImpl();

    /**
     * Try to find the Diff in the repository and throws DiffServerNotFoundException if not found
     */
    @Override
    public Diff find(String id) throws DiffServerNotFoundException {
        log.info("Loading diff with ID: " + id);

        return diffRepository.find(id).orElseThrow(() -> new DiffServerNotFoundException(Diff.class));
    }

    @Override
    public Diff saveLeft(String id, List<String> value) {
        log.info("Saving left diff with ID: " + id);

        Diff diff = findDiffAndReturnNewIfNull(id);
        diff.setLeft(value);
        return diffRepository.save(diff);
    }

    @Override
    public Diff saveRight(String id, List<String> value) {
        log.info("Saving right diff with ID: " + id);

        Diff diff = findDiffAndReturnNewIfNull(id);
        diff.setRight(value);
        return diffRepository.save(diff);
    }

    /**
     * Try to find the diff which has the @param id and return a new Instance if not found.
     */
    private Diff findDiffAndReturnNewIfNull(String id) {
        return diffRepository.find(id).orElse(new Diff(id));
    }

    @Override
    public CalculatedDiff calculateDiff(Diff diff) throws DiffServerIllegalArgumentException {
        return calculateDiff(diff.getLeft(), diff.getRight());
    }

    /**
     * Calculate the differences between two List<String> and return @CalculateDiff
     */
    @Override
    public CalculatedDiff calculateDiff(List<String> left, List<String> right) throws DiffServerIllegalArgumentException {
        log.debug("Calculating Diff;\nLeft: {}\nRight: {}", left, right);

        if (right == null) {
            throw new DiffServerIllegalArgumentException("Please, provide the right side to compare.");
        }

        if (left == null) {
            throw new DiffServerIllegalArgumentException("Please, provide the left side to compare.");
        }

        if (left.equals(right)) {
            return new CalculatedDiff(EQUAL);
        }
        else if (left.size() != right.size()) {
            return new CalculatedDiff(NOT_EQUAL_SIZE);
        }
        else {
            return returnSameSizeDiff(left, right);
        }
    }

    private CalculatedDiff returnSameSizeDiff(List<String> left, List<String> right) throws DiffServerIllegalArgumentException {
        CalculatedDiff calculatedDiff = new CalculatedDiff(SAME_SIZE);
        for (int lineNumber = 0; lineNumber < left.size(); lineNumber++) {
            calculatedDiff.addLine(left.get(lineNumber), right.get(lineNumber));
        }
        return calculatedDiff;
    }

}
