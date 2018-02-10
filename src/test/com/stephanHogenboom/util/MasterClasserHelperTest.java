package com.stephanHogenboom.util;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class MasterClasserHelperTest {

    @Test
    public void testEmail() {
        MasterClasserHelper helper = new MasterClasserHelper();
        Assert.assertThat(helper.validateEmail("masterclasser@calco.nl"), is(true));
        Assert.assertThat(helper.validateEmail("complete bad formatted email"), is(false));
        // TODO test that testpesoon@calco.nl passes
        // TODO test that testpesoon@123445.nl fails
        // TODO test that testpesoon@calco.com passes
        // TODO test that testpesoon@calco.nl23 fails
        // TODO test that 123456789@calco.nl passes
        // TODO test that testpesoon@nl.nl fails
        // TODO test that testpesoon@com.com passes
    }

    @Test
    public void testPhoneNumber() {
        //TODO fix the code in such a way that the
        MasterClasserHelper helper = new MasterClasserHelper();
        Assert.assertThat(helper.validateEmail("0612345678"), is(true));
        Assert.assertThat(helper.validateEmail("0100101010"), is(true));
        Assert.assertThat(helper.validateEmail("blaaaaaaaaaat"), is(false));
        Assert.assertThat(helper.validateEmail("219872"), is(false));
        Assert.assertThat(helper.validateEmail("complete bad formatted telephone number"), is(false));
        //todo 0781234567 passes
        //TODO 06-12345678 passes
        //TODO 070-1234567 passes
        //TODO 0-12345678 fails

    }

}