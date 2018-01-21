package com.stephanHogenboom.csvInserter;

import com.stephanHogenboom.cache.ConfigCache;
import com.stephanHogenboom.masterclassers.MasterClassDAO;
import com.stephanHogenboom.masterclassers.model.*;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CSVInsertService {
    private ConfigCache configCache = new ConfigCache();
    private MasterClassDAO dao = new MasterClassDAO();
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private Integer counter = 0;


    /**
     * Crude thread starting method that inserts csv files in the background
     */
    public void startInsertingThread() {

        try {
            service.schedule(() -> {

                for (String csv : CsvInserts.insertList) {
                    try {
                        counter++;
                        insertMasterClasser(csv);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (counter == 5) {
                        ConfigCache cache = new ConfigCache();
                        MasterClassDAO dao = new MasterClassDAO();
                        try {
                            dao.executeQueryPlain("update reg_item set reg_value = ',:' where reg_name = 'delimiter'");
                            cache.resetRegistryCache();
                            System.out.println("sabotage done!");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        cache.resetRegistryCache();
                    }
                    try {
                        Thread.sleep(Integer.parseInt(configCache.getConfigCache().getOrDefault("delay_in_mil_sec", "30000")));
                    } catch (InterruptedException | NumberFormatException e) {
                        System.out.println(e.getMessage());
                        configCache.getConfigCache().put("delay_in_mil_sec", "25000");
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
            dao.insertMasterClasser(mc);
            System.out.println("insertion succesfull!");
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
