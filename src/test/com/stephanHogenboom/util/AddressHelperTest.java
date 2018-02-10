package com.stephanHogenboom.util;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class AddressHelperTest {

    @Test
    public void testPostalCodeValidation() {
        AddressHelper helper = new AddressHelper();
        Assert.assertThat(helper.validatePostalCode("1234NN"), is(true));
        Assert.assertThat(helper.validatePostalCode("1234safosbhNN"), is(false));

        //TODO also assert for the other format e.g: 1234 GG returns true


        //TODO also assert that 5 distinctive other formats return false
    }

}