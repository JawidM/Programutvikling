
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
        super();
        this.typeArrangement = typeArrangement;
        this.sted = sted;
        this.navn = navn;
        this.artist = artist;
        this.dato = dato;
        this.tid = tid;
    }

    public String getTypeArrangement() {
        return typeArrangement;
    }

    public void setTypeArrangement(String typeArrangement) {
        this.typeArrangement = typeArrangement;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LocalDate getDato() {
        return dato;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Type Arrangement:" + typeArrangement + ", Sted:" + sted + ", Navn:" + navn + 
                ", Artist/Leder:" + artist + ", Dato:" + dato + ", Klokka:" + tid +"\n";
    }
    
    
}  
