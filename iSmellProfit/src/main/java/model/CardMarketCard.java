package model;

public class CardMarketCard {
    private String nameVf;
    private String nameVo;
    private String extension;
    private String lang;
    private String etat;
    private Double sellPrice;
    private String foil;
    private Integer amout;
    private String seller;


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

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getFoil() {
        return foil;
    }

    public void setFoil(String foil) {
        this.foil = foil;
    }

    public Integer getAmout() {
        return amout;
    }

    public void setAmout(Integer amout) {
        this.amout = amout;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "CardMarketCard{" +
                "nameVf='" + nameVf + '\'' +
                ", nameVo='" + nameVo + '\'' +
                ", extension='" + extension + '\'' +
                ", lang='" + lang + '\'' +
                ", etat='" + etat + '\'' +
                ", sellPrice=" + sellPrice +
                ", foil='" + foil + '\'' +
                ", amout=" + amout +
                ", seller='" + seller + '\'' +
                '}';
    }
}
