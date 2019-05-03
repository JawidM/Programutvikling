
package org.openjfx.arrangementer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ArrangementReader {
    public static List<Arrangement> readArrangementer(String path) throws IOException, InvalidArrangementFormatException{
        ArrayList<Arrangement> arrangementer = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get(path).toAbsolutePath());
            
            String line = null; // read first line

            // read the rest and create Arrangement for each line
            while((line=reader.readLine()) != null) {
                arrangementer.add(parseArrangement(line));
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }

        return arrangementer;
    }

    private static Arrangement parseArrangement(String line) throws InvalidArrangementFormatException {
        // split line string into seks using the separator ","
        String[] split = line.split(",");
        if(split.length != 6) {
            throw new InvalidArrangementFormatException("Must use comma , to separate the three data fields");
        }
        String typeArrangement = split[0];
        String sted = split[1];
        String navn = split[2];
        String artist = split[3];
        
        LocalDate dato = LocalDate.parse (split[4]);
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yyyy");
        //String formatedString = dato.format(dtf);
        
        
        int tid = parseNumber(split[5], "Velg tida");

        return new Arrangement(typeArrangement, sted, navn, artist, dato, tid );
    }

    private static int parseNumber(String str, String errorMessage) throws InvalidArrangementFormatException{
        int number;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new InvalidArrangementFormatException(errorMessage);
        }

        return number;
    }
}
