package model;

public class CompareCard {
    private String nameVf;
    private String nameVo;
    private String extension;
    private String lang;
    private String etat;
    private String foil;
    private Double buyPrice;
    private Double sellPrice;
    private Double profit;
    private String seller;
    private Integer amount;


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

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "CompareCard{" +
                "nameVf='" + nameVf + '\'' +
                ", nameVo='" + nameVo + '\'' +
                ", extension='" + extension + '\'' +
                ", lang='" + lang + '\'' +
                ", etat='" + etat + '\'' +
                ", foil='" + foil + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", profit=" + profit +
                ", seller='" + seller + '\'' +
                ", amount=" + amount +
                '}';
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
