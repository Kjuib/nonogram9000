package com.fnod.nonogram.panels;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: hbillings
 * Date: 2/8/13
 * Time: 9:47 PM
 */
public class PnlCreateTest {

    @Test
    public void testIsHeightBigger() throws Exception {
        assertTrue(PnlCreate.isWidthBigger(100, 100, 100, 100));
        assertTrue(PnlCreate.isWidthBigger(100, 50, 100, 100));
        assertTrue(PnlCreate.isWidthBigger(30, 10, 376, 374));
        assertTrue(PnlCreate.isWidthBigger(30, 10, 369, 373));

        assertFalse(PnlCreate.isWidthBigger(50, 100, 100, 100));
        assertFalse(PnlCreate.isWidthBigger(50, 50, 100, 75));
        assertFalse(PnlCreate.isWidthBigger(10, 30, 100, 75));

    }
}
