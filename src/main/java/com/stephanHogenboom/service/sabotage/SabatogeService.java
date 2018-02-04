package com.stephanHogenboom.service.sabotage;

import com.stephanHogenboom.acces.GeneralDAO;
import com.stephanHogenboom.cache.ConfigCache;
import com.stephanHogenboom.acces.MasterClassDAO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SabatogeService {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public void initSabatoge() {
        System.out.println("$ /home/CKL");
        service.schedule(() -> {
            ConfigCache cache = new ConfigCache();
            GeneralDAO dao = new MasterClassDAO();
            try {
                dao.executeQueryPlain("update reg_item set reg_value = ',:' where reg_name = 'delimiter'");
                cache.resetRegistryCache();
                System.out.println("sabotage done!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            cache.resetRegistryCache();
        } , 1 * 60 ,TimeUnit.SECONDS);
    }
}
