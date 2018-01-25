package com.stephanHogenboom.service.report;

import com.stephanHogenboom.masterclassers.MasterClassDAO;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class ReportService {
    MasterClassDAO dao = new MasterClassDAO();

    public void buildSimpleReport() {
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
                    report.show();

                    report.toPdf(new FileOutputStream("report.pdf"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DRException e) {
                    e.printStackTrace();
                }

    }
}
