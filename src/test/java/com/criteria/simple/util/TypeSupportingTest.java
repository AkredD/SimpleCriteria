package com.criteria.simple.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;


class TypeSupportingTest {

    @Test
    void evaluateTargetValue() throws ClassNotFoundException {
        String test1 = "1";
        Integer testResult = TypeSupporting.evaluateTargetValue(Integer.class, test1);
        Assert.isTrue(testResult == 1);

        String test2 = "2";
        Long testResult2 = TypeSupporting.evaluateTargetValue(Long.class, test2);
        Assert.isTrue(testResult2 == 2L);
    }

}
