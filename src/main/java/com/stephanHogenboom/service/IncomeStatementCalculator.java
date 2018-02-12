package com.stephanHogenboom.service;

import com.google.common.annotations.VisibleForTesting;
import com.stephanHogenboom.model.MasterClasser;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeStatementCalculator {
    private final int maxRaises = 4;
    private final int monthsPerRaise = 6;
    private final int bonusPerRaise = 150;

    public List<HashMap<MasterClasser, HashMap<String, Integer>>> getIncomseStatementsForMasterClasser(List<MasterClasser> mcs) {
       return mcs.
                stream()
                .map(this::addIncomeStatementToMasterClasser)
                .collect(Collectors.toList());
    }


    HashMap<MasterClasser, HashMap<String, Integer>> addIncomeStatementToMasterClasser(MasterClasser mc) {
        HashMap<MasterClasser, HashMap<String, Integer>> incomeStatementForMasterClasser = new HashMap<>();
        incomeStatementForMasterClasser.put(mc, caculateIncomeStatementCurrentDay(mc));
        return incomeStatementForMasterClasser;
    }

    @VisibleForTesting
    HashMap<String, Integer> caculateIncomeStatementCurrentDay(MasterClasser mc) {
        return caculateIncomeStatement(mc, LocalDate.now());
    }

    @VisibleForTesting
    HashMap<String, Integer> caculateIncomeStatement(MasterClasser mc, LocalDate date) {
        HashMap<String, Integer> incomeStatement = new HashMap<>();
        int monthsWorked = calculateMonthsWorkedForPeriod(mc.getStartDate(), date);

        int raises = (monthsWorked > maxRaises * monthsPerRaise) ? maxRaises : (monthsWorked / monthsPerRaise);
        int monthlyCosts = 2200 + raises * bonusPerRaise;
        incomeStatement.put("monthly costs", monthlyCosts);

        int monthlyIncome = mc.getJobType().getOid() == 12 ? 0 : 4000 + ((monthsWorked - 3) / 3) * 300;
        incomeStatement.put("monthly income ", monthlyIncome);
        incomeStatement.put("monthly profit", monthlyIncome - monthlyCosts);

        return incomeStatement;
    }

    @VisibleForTesting
    int calculateMonthsWorkedForPeriod(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) throw new IllegalStateException("Startdate cannot be before the end date!");
        Period periodWorked = start.until(end);
        int monthsWorked = periodWorked.getMonths();
        int yearsWorked = periodWorked.getYears();
        return monthsWorked + 12 * yearsWorked;
    }

}
