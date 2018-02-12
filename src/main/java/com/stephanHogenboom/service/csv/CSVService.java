package com.stephanHogenboom.service.csv;

import com.stephanHogenboom.cache.ConfigCache;
import com.stephanHogenboom.acces.MasterClassDAO;
import com.stephanHogenboom.model.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CSVService {
    private ConfigCache configCache = new ConfigCache();
    private MasterClassDAO dao = new MasterClassDAO();
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);


    /**
     * Crude thread starting method that inserts csv files in the background
     */
    public void startInsertingThread() {

        try {
            service.schedule(() -> {

                for (String csv : CsvInserts.insertList) {
                    try {
                        insertMasterClasser(csv);
                        configCache.getConfigCache().put("delay_in_mil_sec", "25000");
                        Thread.sleep(Integer.parseInt(configCache.getConfigCache().get("delay_in_mil_sec")));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }, Integer.parseInt(configCache.getConfigCache().getOrDefault("initial_delay_in_sec", "60")), TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertMasterClasser(String csvFile) {
        try {
            System.out.printf("tryin to insert csv value: %s \n", csvFile);
            if (!configCache.getConfigCache().get("delimiter").equals(",")) {
                System.out.println("insertion failed!");
                return;
            }
            String[] parts = csvFile.split(configCache.getConfigCache().get("delimiter"));
            Company company = new Company(Integer.parseInt(parts[0]), parts[1]);
            AddressBuilder bldr = new AddressBuilder();
            System.out.println(company);
            Address address = bldr.setCountry(parts[2])
                    .setPostalCode(parts[3])
                    .setStreet(parts[4])
                    .setHouseNumber(Integer.parseInt(parts[5]))
                    .setExtension(parts[6])
                    .setCity(parts[7])
                    .setKixCode()
                    .build();
            JobType jt = new JobType();
            jt.setOid(Integer.parseInt(parts[8]));
            MasterClasser mc = new MasterClasser();
            mc.setCompany(company);
            mc.setJobType(jt);
            mc.setAddress(address);
            mc.setFullName(parts[9]);
            mc.setStartDate(LocalDate.now().minusMonths((int) (Math.random() * 15) + 1));
            mc.setTelephoneNumber(parts[10]);
            mc.setEmail(parts[11]);
            FieldManager fm = new FieldManager();
            fm.setOid(Integer.parseInt(parts[12]));
            mc.setFieldManager(fm);
            dao.insertMasterClasser(mc);
            System.out.println("insertion succesfull!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private List<String> getAllMasterclassersAsCsVStrings() {
        return dao.getAllMasterClassers()
                .stream()
                .map(this::masterClasserToCSVString)
                .collect(Collectors.toList());
    }

    public void writeCsvFileToFile() {
        String fileName = LocalDate.now().toString();
        Path directory = Paths.get("csv");
        Path destination = Paths.get(String.format("csv/%s", fileName));
        try {
            Files.createDirectories(directory);
            Files.write(destination, getAllMasterclassersAsCsVStrings(), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String toDoubleDigitString(int id) {
        String digit = String.valueOf(id);
        if(digit.length() == 1) {
            return "0".concat(digit);
        }
        return digit;
    }

    private String masterClasserToCSVString(MasterClasser masterClasser) {
        String specializationString;
        if (masterClasser.getSpecializations() != null) {
            specializationString = masterClasser.getSpecializations()
                    .stream().map(specialization -> toDoubleDigitString(specialization.getId()))
                    .collect(Collectors.joining());
        } else {
            specializationString = "";
        }

        Address address = masterClasser.getAddress();
        return String.valueOf(masterClasser.getCompany().getOid()) + "," +
                String.valueOf(masterClasser.getCompany().getName()) + "," +
                address.getCountry() + "," +
                address.getPostalCode() + "," +
                address.getPostalCode() + "," +
                address.getStreet() + "," +
                address.getHouseNumber() + "," +
                address.getExtension() + "," +
                address.getCity() + "," +
                masterClasser.getFullName() + "," +
                masterClasser.getTelephoneNumber() + "," +
                masterClasser.getEmail() + "," +
                masterClasser.getFieldManager().getOid() + "," +
                specializationString;
    }
}
