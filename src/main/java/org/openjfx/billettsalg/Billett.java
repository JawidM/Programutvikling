
package org.openjfx.billettsalg;

public class Billett {
    private String arrangementInfo;
    private String fornavn;
    private String etternavn;
    private String epost;
    private int telefonNummer;
    private int antallBilletter;

    public Billett(String arrangementInfo, String fornavn, String etternavn, String epost, int telefonNummer, int antallBilletter) {
        this.arrangementInfo = arrangementInfo;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.telefonNummer = telefonNummer;
        this.antallBilletter = antallBilletter;
    }

    public String getArrangementInfo() {
        return arrangementInfo;
    }

    public void setArrangementInfo(String arrangementInfo) {
        this.arrangementInfo = arrangementInfo;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public int getTelefonNummer() {
        return telefonNummer;
    }

    public void setTelefonNummer(int telefonNummer) {
        this.telefonNummer = telefonNummer;
    }

    public int getAntallBilletter() {
        return antallBilletter;
    }

    public void setAntallBilletter(int antallBilletter) {
        this.antallBilletter = antallBilletter;
    }

    @Override
    public String toString() {
        return "Arrangementet som er valgt:" + arrangementInfo + 
                "\n Kjøperen:" + fornavn +" "+ etternavn + "\n Epost adresse:" + epost + ", TelefonNummer:" + telefonNummer + "\n Antall billetter kjøpt:" + antallBilletter +"\n";
    }
}
