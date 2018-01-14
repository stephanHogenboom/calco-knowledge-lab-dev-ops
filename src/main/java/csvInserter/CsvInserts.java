package csvInserter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CsvInserts {
    List<String> inserts = new ArrayList();
    public static String mc1 = "1,ABN AMRO,NL,6565JH,Blaakstraat,12,a,Blaricum,4,david jansen,0101234567,daar@calco.nl";
    public static String mc2 = "2,NN,NL,1111AD,doprstraat,11,,Gent,6,Gerrit Boomsma,0104956467,gboomsma@calco.nl";
    public static String mc3 = "1,ABN AMRO,NL,4456JJ,gindersteeg,4,,Hoofddorp,5,jan klaasen,0612346567,jklaassen@calco.nl";
    public static String mc4 = "3,ING,NL,7643,achterhoeksteeg,12,H,vlietland,7,joop endstra,0801234567,jedndstra@calco.nl";
    public static String mc5 = "1,ABN AMRO,NL,3200AS,steegweg,33,G,Den Haag,6,jan pieterzoon,0606060606,jpieterzoon@calco.nl";
    public static String mc6 = "5,VOLKS BANK,NL,6565JH,neerweg,54,a,gent,5,raoul jansen,069876541,rjansen@calco.nl";
    public static String mc7 = "6,POST NL,NL,1234AS,dagsteeg,12,a,amsterdam,4,erwin vlaming,06706958,evlaming@calco.nl";
    public static String mc8 = "2,NN,NL,2345AS,terugweg,24,a,rotterdam,3,bert van basten,07023432221,bvanbasten@calco.nl";
    public static String mc9 = "3,ING,NL,2345FF,looilaan,43,a,Utrecht,2,reol schouten,0801234568,rschouten@calco.nl";
    public static String mc10 = "5,VOLKS BANK,NL,1245KK,nederkade,2,a,rotterdam,1,sander jansen,1234567890,sjansen@calco.nl";
    public static String mc11 = "6,POST NL,NL,2345HH,bovenstraat,4,a,Utrecht,4,giel beelen,09876543231,gbeelen@calco.nl";
    public static String mc12 = "8,Deltaloid,NL,1234HG,omlaan,3,a,rotterdam,2,justin bieber,9876543210,jbierber@calco.nl";
    public static String mc13 = "9,SVB,NL,2323JJ,regenkade,2,a,Blaricum,3,tim van dops,8765432109,tvandops@calco.nl";
    public static String mc14 = "10,Heineken AMRO,NL,4565AA,omweg,11,a,Utrecht,8,david hansen,0181234567,dhansen@calco.nl";
    public static String mc15 = "9,SVB,NL,4455HG,daustrasse,34,a,rotterdam,9,edgar jansen,5432109877,ejansen@calco.nl";

    public static List<String> insertList =
         Arrays.asList(mc1, mc2, mc3, mc4, mc5, mc6, mc7, mc8, mc9, mc10, mc11, mc12, mc13, mc14, mc15);
}
