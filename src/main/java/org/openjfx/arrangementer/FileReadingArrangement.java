
package org.openjfx.arrangementer;

import java.io.IOException;
import java.util.List;

public class FileReadingArrangement {
    public static void main(String[] args){
        List<Arrangement> arrayListArrangement = null;

        try {
            arrayListArrangement = ArrangementReader.readArrangementer("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/arrangementer.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidArrangementFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }

        if(arrayListArrangement == null) { // some error has occurred
            System.exit(1);
        }
    }
}
