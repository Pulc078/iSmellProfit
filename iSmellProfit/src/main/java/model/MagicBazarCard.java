package model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MagicBazarCard {

    private String nameVf;
    private String nameVo;
    private String extension;
    private String lang;
    private String etat;
    private Double buyPrice;
    private String foil;


    public String getNameVf() {
        return nameVf;
    }

    public void setNameVf(String nameVf) {
        this.nameVf = nameVf;
    }

    public String getNameVo() {
        return nameVo;
    }

    public void setNameVo(String nameVo) {
        this.nameVo = nameVo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    public String getFoil() {
        return foil;
    }

    public void setFoil(String foil) {
        this.foil = foil;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String toCsvRow() {
        return Stream.of(nameVf, nameVo, extension, lang, etat, foil, buyPrice.toString())
                .map(value -> value.replaceAll("\"", "\"\""))
                .map(value -> Stream.of("\"", ",").anyMatch(value::contains) ? "\"" + value + "\"" : value)
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString() {
        return "MagicBazarCard{" +
                "nameVf='" + nameVf + '\'' +
                ", nameVo='" + nameVo + '\'' +
                ", extension='" + extension + '\'' +
                ", lang='" + lang + '\'' +
                ", etat='" + etat + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", foil='" + foil + '\'' +
                '}';
    }
}
