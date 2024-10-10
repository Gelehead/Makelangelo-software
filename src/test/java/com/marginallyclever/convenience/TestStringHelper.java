package com.marginallyclever.convenience;

import com.marginallyclever.convenience.helpers.StringHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStringHelper {
    @Test
    public void testGetElapsedTime() {
        int testTime = 16000;
        String result = "04:26:04";
        assertEquals(StringHelper.getElapsedTime(testTime), result);
    }

    @Test
    public void testPaddedHex() {
        assertEquals("000001", StringHelper.paddedHex(0x1));
        assertEquals("0007ff", StringHelper.paddedHex(0x7ff));
        assertEquals("100001", StringHelper.paddedHex(0x100001));
        assertEquals("1000001", StringHelper.paddedHex(0x1000001));
        assertEquals("100000", StringHelper.paddedHex(0x100000));
    }
}
