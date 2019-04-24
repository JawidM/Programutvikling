
package org.openjfx.arrangementer;

import java.time.LocalDate;

public class Arrangement{
    private String typeArrangement;
    private String sted;
    private String navn;
    private String artist;
    private LocalDate dato;
    private int tid;

    public Arrangement(String typeArrangement, String sted, String navn, String artist, LocalDate dato, int tid) {
        this.typeArrangement = typeArrangement;
        this.sted = sted;
        this.navn = navn;
        this.artist = artist;
        this.dato = dato;
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "TypeArrangement=" + typeArrangement + ", \n"
                + "Sted=" + sted + ", \n"
                + "Navn=" + navn + ", \n"
                + "Artist=" + artist + ", \n"
                + "Dato=" + dato + ", \n"
                + "Klokka=" + tid +":00";
    }
}  
