package csvInserter;

import cache.ConfigCache;
import elements.AlertBox;
import masterclassers.MasterClassDAO;
import masterclassers.model.*;

import java.time.LocalDate;

public class CSVInsertService {
    private ConfigCache configCache = new ConfigCache();
    private MasterClassDAO dao = new MasterClassDAO();



    public void insertWithPrefab(){
        try {
            CsvInserts.insertList.forEach(this::insertMasterClasser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            AlertBox.display("error" ,"some thing went wrong during csv parsing \n");
        }
    }

    public void insertMasterClasser(String csvFile) {
        try {
            System.out.printf("tryin to insert csv value: %s", csvFile);
            if (!configCache.getConfigCache().get("delimiter").equals(",")) {
                System.out.println("csv parsing compromised!");
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
