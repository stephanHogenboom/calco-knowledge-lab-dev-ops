package com.stephanHogenboom.service.csv;

import java.util.Arrays;
import java.util.List;

abstract class CsvInserts {
    private static String mc1 = "1,ABN AMRO,NL,6565JH,Blaakstraat,12,a,Blaricum,4,david jansen,0101234567,daar@calco.nl,4";
    private static String mc2 = "2,NN,NL,1111AD,doprstraat,11,,Gent,6,Gerrit Boomsma,0104956467,gboomsma@calco.nl,3";
    private static String mc3 = "1,ABN AMRO,NL,4456JJ,gindersteeg,4,,Hoofddorp,5,jan klaasen,0612346567,jklaassen@calco.nl,2";
    private static String mc4 = "3,ING,NL,7643,achterhoeksteeg,12,H,vlietland,7,joop endstra,0801234567,jedndstra@calco.nl,5";
    private static String mc5 = "1,ABN AMRO,NL,3200AS,steegweg,33,G,Den Haag,6,jan pieterzoon,0606060606,jpieterzoon@calco.nl,10";
    private static String mc6 = "5,VOLKS BANK,NL,6565JH,neerweg,54,a,gent,5,raoul jansen,069876541,rjansen@calco.nl,9";
    private static String mc7 = "6,POST NL,NL,1234AS,dagsteeg,12,a,amsterdam,4,erwin vlaming,06706958,evlaming@calco.nl,6";
    private static String mc8 = "2,NN,NL,2345AS,terugweg,24,a,rotterdam,3,bert van basten,07023432221,bvanbasten@calco.nl,6";
    private static String mc9 = "3,ING,NL,2345FF,looilaan,43,a,Utrecht,2,reol schouten,0801234568,rschouten@calco.nl,6";
    private static String mc10 = "5,VOLKS BANK,NL,1245KK,nederkade,2,a,rotterdam,1,sander jansen,1234567890,sjansen@calco.nl,5";
    private static String mc11 = "6,POST NL,NL,2345HH,bovenstraat,4,a,Utrecht,4,giel beelen,09876543231,gbeelen@calco.nl,7";
    private static String mc12 = "8,Deltaloid,NL,1234HG,omlaan,3,a,rotterdam,2,justin bieber,9876543210,jbierber@calco.nl,2";
    private static String mc13 = "9,SVB,NL,2323JJ,regenkade,2,a,Blaricum,3,tim van dops,8765432109,tvandops@calco.nl,7";
    private static String mc14 = "10,Heineken AMRO,NL,4565AA,omweg,11,a,Utrecht,8,david hansen,0181234567,dhansen@calco.nl,8";
    private static String mc15 = "9,SVB,NL,2121FF, cynapol ,12, ga,Assen,8, fred jansen,543214457,fjansen@calco.nl,7";
    private static String mc17 = "9,SVB,NL,4455HG,daustrasse,34,a,rotterdam,7,edgar jansen-zoon,'061267311',ezoon@calco.nl,4";
    private static String mc18 = "9,SVB,NL,4412JJ,winsteeg,34,a,den haag,5,edgar Hanso,0612673177,eHanso@calco.nl,3";
    private static String mc19 = "9,SVB,NL,4422JK,windgasse,34,a,rotterdam,1,willem jansen,0612654532,wjansen@calco.nl,2";
    private static String mc20 = "9,SVB,NL,2122KK,raamweg,34,a,vlaardingen,2,thai ni,06324209877,tni@calco.nl,1";
    private static String mc21 = "9,SVB,NL,9012KK,daulaan,34,a,rotterdam,4,xi japon,0632432141,xjapon@calco.nl,1";
    private static String mc22 = "9,SVB,NL,1235JJ,witsteeg,34,a,schiedam,3,francois la leutre,0612334123,fleutren@calco.nl,7";
    private static String mc23 = "9,SVB,NL,9089KK,blauwstraat,34,a,den helder,4,vido dido,0614231444,vdido@calco.nl,8";
    private static String mc24 = "9,SVB,NL,2112JJ,rodenlaan,34,a,kappelle,6,fred flinstonw,0612532133,fflinstow@calco.nl,10";
    private static String mc25 = "9,SVB,NL,1122JH,laardingerweg,34,a,Zwolle,7,kadae,0612341223,kadae@calco.nl,2";
    static List<String> insertList =
            Arrays.asList(mc1, mc2, mc3, mc4, mc5, mc6, mc7, mc8, mc9, mc10, mc11, mc12, mc13, mc14, mc15, mc17, mc18, mc19
                    , mc20, mc21, mc22, mc23, mc24, mc25);
}
