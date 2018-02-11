package com.stephanHogenboom.service;

import com.stephanHogenboom.model.JobType;
import com.stephanHogenboom.model.MasterClasser;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IncomeStatementCalculatorTest {
    private IncomeStatementCalculator calculator = new IncomeStatementCalculator();
    private LocalDate testDate = LocalDate.of(2018,02,11);


    @Test
    public void testMonthsWorked() {
        LocalDate startDate = LocalDate.of(2017,01,01);
        LocalDate endDateOne = LocalDate.of(2018,01,01);
        LocalDate endDateTwo = endDateOne.plusMonths(5);
        assertThat(calculator.calculateMonthsWorkedForPeriod(startDate, endDateOne), is(12));
        assertThat(calculator.calculateMonthsWorkedForPeriod(startDate, endDateTwo), is(17));
    }

    @Test(expected = IllegalStateException.class)
    public void illegalStateCalculation() {
        calculator.calculateMonthsWorkedForPeriod(LocalDate.now(),LocalDate.now().minusMonths(5));
    }

    @Test
    public void testMonthlyCosts() {
        MasterClasser testPersoon = new MasterClasser();
        testPersoon.setStartDate(LocalDate.of(2017,5,1));
        testPersoon.setJobType(new JobType(2, "Operator"));
        HashMap<String,Integer> statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly costs"),is(2350));

        testPersoon.setStartDate(testPersoon.getStartDate().minusMonths(3)); //2017-02-1
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly costs"),is(2500));

        testPersoon.setStartDate(testPersoon.getStartDate().minusMonths(6)); //2016-08-1
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly costs"),is(2650));

        testPersoon.setStartDate(testPersoon.getStartDate().minusMonths(8));  //2015-12-1
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly costs"),is(2800));

        testPersoon.setStartDate(testPersoon.getStartDate().minusMonths(200));
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly costs"),is(2800));
    }

    @Test
    public void testMonthlyProfits() {
        MasterClasser testPersoon = new MasterClasser();
        testPersoon.setStartDate(LocalDate.of(2017,5,1));
        testPersoon.setJobType(new JobType(2, "Operator"));
        HashMap<String,Integer> statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly profit"), is(2250));

        testPersoon.setStartDate(LocalDate.of(2016,5,1));
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly profit"), is(3150));

        testPersoon.setStartDate(LocalDate.of(2016,1,1));
        statementMap = calculator.caculateIncomeStatement(testPersoon, testDate);
        assertThat(statementMap.get("monthly profit"), is(3300));
    }

}