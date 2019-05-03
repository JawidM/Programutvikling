
package org.openjfx.billettsalg;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillettReader {
    public static List<Billett> readBilletter(String path) throws IOException, InvalidBillettFormatException{
        ArrayList<Billett> billetter = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get(path).toAbsolutePath());
            
            String line = null; // read first line

            // read the rest and create Billett for each line
            int lineCounter = 1;
            while((line=reader.readLine()) != null) {
                //if (lineCounter %2 == 0) {
                billetter.add(parseBillett(line));
                //}
                lineCounter++;
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return billetter;
    }

    private static Billett parseBillett(String line) throws InvalidBillettFormatException {
        // split line string into seks using the separator ","
        String[] splitArr = line.split("]");
        String arrangementInfo = splitArr[0] + "] ";
        String[] split = line.split(",");
        if(split.length != 12) {
            throw new InvalidBillettFormatException("Must use comma , to separate the three data fields");
        }
        String fornavn = split[6];
        String  etternavn = split[7];
        String epost = split[8];
        int telefonNummer = parseNumber(split[9], "Tast inn telefonnummeret ");
        String typeBillett = split[10];
        int antallBilletter = parseNumber(split[11], "Velg antall billetter ");

        return new Billett(arrangementInfo, fornavn, etternavn, epost, telefonNummer, typeBillett, antallBilletter);
    }

    private static int parseNumber(String str, String errorMessage) throws InvalidBillettFormatException {
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidBillettFormatException(errorMessage);
        }
        return number;
    }
}
