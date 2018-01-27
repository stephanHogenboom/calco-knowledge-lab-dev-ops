package com.stephanHogenboom.service.report;

import com.stephanHogenboom.masterclassers.MasterClassDAO;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class ReportService {
    MasterClassDAO dao = new MasterClassDAO();

    public void createMasterClasserReport() {
        JasperReportBuilder report = DynamicReports.report();
        report.columns(
                col.column("", "oid", type.integerType()),
                col.column("Name", "fullName", type.stringType()),
                col.column("job","jobType.name", type.stringType()),
                col.column("company", "company.name", type.stringType()))
                .title(cmp.text("Master classer overview"))
                .pageFooter(cmp.pageXofY())
                .setDataSource(dao.getAllMasterClassers());
                try {
                    Path reportDir = Paths.get("reports");
                    Files.createDirectories(reportDir);
                    report.toPdf(new FileOutputStream(String.format("reports/%s", LocalDate.now().toString())));
                    report.show();
                } catch (DRException | IOException e) {
                    System.out.println(e.getMessage());
                }

    }
}
