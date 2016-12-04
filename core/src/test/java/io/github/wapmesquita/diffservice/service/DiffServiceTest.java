package io.github.wapmesquita.diffservice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.github.wapmesquita.diffmodel.model.Diff;
import io.github.wapmesquita.diffmodel.repositories.DiffRepository;
import io.github.wapmesquita.diffservice.exception.DiffServerIllegalArgumentException;
import io.github.wapmesquita.diffservice.exception.DiffServerNotFoundException;
import io.github.wapmesquita.diffservice.model.CalculatedDiff;
import io.github.wapmesquita.diffservice.model.Line;

import static io.github.wapmesquita.diffservice.model.DiffResult.EQUAL;
import static io.github.wapmesquita.diffservice.model.DiffResult.NOT_EQUAL_SIZE;
import static io.github.wapmesquita.diffservice.model.DiffResult.SAME_SIZE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    @Mock
    private DiffRepository diffRepository;

    @Spy
    @InjectMocks
    private DiffService diffService = new DiffServiceImpl();

    @Before
    public void setupMocks() {
        doAnswer(invokation -> invokation.getArgumentAt(0, Diff.class))
                .when(diffRepository).save(any());
        doReturn(Optional.empty()).when(diffRepository).find(any());
    }

    @After
    public void cleanMocks() {
        reset(diffRepository);
        reset(diffService);
    }

    @Test
    public void testSaveLeft() {
        String id = "testId";
        List<String> value = asList("left");
        Diff diff = diffService.saveLeft(id, value);

        verify(diffRepository, times(1)).save(any());

        assertEquals(diff.getLeft(), value);
        assertEquals(Collections.emptyList(), diff.getRight());
    }

    @Test
    public void testSaveRight() {
        String id = "testId";
        List<String> value = asList("right");
        Diff diff = diffService.saveRight(id, value);

        verify(diffRepository, times(1)).save(any());

        assertEquals(diff.getRight(), value);
        assertEquals(Collections.emptyList(), diff.getLeft());
    }

    @Test
    public void testSaveBoth() {
        String id = "testId";
        List<String> valueRight = asList("right");
        List<String> valueLeft = asList("left");

        Diff diffRight = diffService.saveRight(id, valueRight);

        verify(diffRepository, times(1)).save(any());

        doReturn(Optional.of(diffRight))
                .when(diffRepository).find(id);

        Diff diffLeft = diffService.saveLeft(id, valueLeft);

        verify(diffRepository, times(2)).save(any());

        assertEquals(diffLeft, diffRight);
        assertEquals(diffLeft.getRight(), valueRight);
        assertEquals(diffLeft.getLeft(), valueLeft);
    }

    @Test(expected = DiffServerNotFoundException.class)
    public void shouldThrowWhenNotFound() throws DiffServerNotFoundException {
        doReturn(Optional.empty()).when(diffRepository).find(any());
        diffService.find("test");
    }

    @Test
    public void shouldReturnEqualDiff() throws DiffServerIllegalArgumentException {
        List<String> values = asList("ab;cd;ef;gh;ij;kl;m".split(";"));

        CalculatedDiff expected = new CalculatedDiff(EQUAL);

        Diff diff = new Diff();
        diff.setLeft(values);
        diff.setRight(values);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);

        assertEquals(expected, calculatedDiff);
    }

    @Test
    public void shouldReturnEqualDiffWhenBothIsEmpty() throws DiffServerIllegalArgumentException {
        List<String> values = Collections.emptyList();

        CalculatedDiff expected = new CalculatedDiff(EQUAL);

        Diff diff = new Diff();
        diff.setLeft(values);
        diff.setRight(values);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);

        assertEquals(expected, calculatedDiff);
    }

    @Test
    public void shouldReturnNotEqualSizeDiff() throws DiffServerIllegalArgumentException {
        List<String> smallValue = asList("ab;cd;ef".split(";"));
        List<String> bigValue = asList("ab;cd;ef;gh;ij;kl;m".split(";"));

        CalculatedDiff expected = new CalculatedDiff(NOT_EQUAL_SIZE);

        Diff diffRightBigger = new Diff();
        diffRightBigger.setLeft(smallValue);
        diffRightBigger.setRight(bigValue);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diffRightBigger);

        assertEquals(expected, calculatedDiff);

        Diff diffLeftBigger = new Diff();
        diffLeftBigger.setLeft(bigValue);
        diffLeftBigger.setRight(smallValue);

        calculatedDiff = diffService.calculateDiff(diffLeftBigger);

        assertEquals(expected, calculatedDiff);

    }

    @Test
    public void shouldReturnNotEqualSizeDiffWhenLeftIsEmpty() throws DiffServerIllegalArgumentException {
        List<String> value = asList("ab;cd;ef".split(";"));

        CalculatedDiff expected = new CalculatedDiff(NOT_EQUAL_SIZE);

        Diff diff = new Diff();
        diff.setLeft(Collections.emptyList());
        diff.setRight(value);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);

        assertEquals(expected, calculatedDiff);
    }

    @Test
    public void shouldReturnNotEqualSizeDiffWhenRightIsEmpty() throws DiffServerIllegalArgumentException {
        List<String> value = asList("ab;cd;ef".split(";"));

        CalculatedDiff expected = new CalculatedDiff(NOT_EQUAL_SIZE);

        Diff diff = new Diff();
        diff.setRight(Collections.emptyList());
        diff.setLeft(value);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);

        assertEquals(expected, calculatedDiff);
    }

    @Test
    public void shouldReturnSameSizeDiffOneLineDifferent() throws DiffServerIllegalArgumentException {
        List<String> value1 = asList("ab;cd;ef;gh;ij;kl;mj".split(";"));
        List<String> value2 = asList("ab;cd;ef;ghi;ij;kl;mj".split(";"));

        int differentLineNumber = 4;

        Diff diff = new Diff();
        diff.setLeft(value1);
        diff.setRight(value2);

        CalculatedDiff calculatedDiff = diffService.calculateDiff(diff);

        assertEquals(SAME_SIZE, calculatedDiff.getType());
        for (Line line : calculatedDiff.getLines()) {
            if (line.getLineNumber() == differentLineNumber) {
                assertFalse(line.isEqual());
            }
            else {
                assertTrue(line.isEqual());
            }
        }
    }

    @Test(expected = DiffServerIllegalArgumentException.class)
    public void shouldThrowWhenCalculateDiffAndLeftIsNull() throws DiffServerIllegalArgumentException {
        diffService.calculateDiff(null, Collections.emptyList());
    }

    @Test(expected = DiffServerIllegalArgumentException.class)
    public void shouldThrowWhenCalculateDiffAndRightIsNull() throws DiffServerIllegalArgumentException {
        diffService.calculateDiff(Collections.emptyList(), null);
    }

}
