package com.stephanHogenboom.util;

import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class SimpleLogConfig {
    File file;

    public SimpleLogConfig(File file) {
        this.file = file;
    }

    public void configureLogging() {
        try {

            if (!file.exists()){
                file.mkdirs();
                System.out.println("directories created");
            }
            // set log dir according to current day
            file = new File(file.getAbsolutePath()+"/" + LocalDate.now().getMonth().name() + "-" + LocalDate.now().getDayOfMonth());
            FileOutputStream fos = new FileOutputStream(file);
            //we want to print in standard "System.out" and in "file"
            TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
            PrintStream ps = new PrintStream(myOut, true); //true - auto-flush after println
            System.setOut(ps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
