package service;

import model.CardMarketCard;
import model.CompareCard;
import model.MagicBazarCard;
import model.MagicBazarVendorsEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MkmReader {

    public MkmReader() {
    }

    private static final Logger logger = LogManager.getLogger(MkmReader.class);

    /**
     * Retourne la liste des cartes qui sont en vente sur MBC ET en achat sur MKM
     *
     * @param magicBazarCardList la liste des cartes sur MBC
     * @return un array de CompareCard
     */
    public List<CompareCard> getComparaisonList(List<MagicBazarCard> magicBazarCardList) {
        List<CompareCard> compareCardList = new ArrayList<>();
        List<CardMarketCard> cardMarketCardList = new ArrayList<>();

        MagicBazarVendorsEnum.stream().forEach(seller -> compareCardList.addAll(getMkmCardsByVendor(seller.getValue(), magicBazarCardList, cardMarketCardList)));

        return compareCardList;
    }

    /**
     * Check the card from magicBazarCardList to the webpage of the specified seller on MKM
     *
     * @param seller             : specified MKM seller
     * @param magicBazarCardList : list of card on MagicBazard WishList
     * @param cardMarketCardList : list of card on MKM
     * @return a list of MKM card who are
     */
    private List<CompareCard> getMkmCardsByVendor(String seller, List<MagicBazarCard> magicBazarCardList, List<CardMarketCard> cardMarketCardList) {
        List<CompareCard> compareCardList = new ArrayList<>();
        logger.info("MKM seller : " + seller);
        for (MagicBazarCard mbc : magicBazarCardList) {
            logger.info("Looking for card " + mbc.getNameVo() + " from seller : " + seller);
            String url = "https://www.cardmarket.com/fr/Magic/Users/" + seller + "/Offers/Singles?name=" + mbc.getNameVo();

            JsoupHelper jsoupHelper = new JsoupHelper();
            Document doc = jsoupHelper.getConnexion(url, "Error while getting information from seller : " + seller + " : ");

            Elements newsHeadlines = doc.select(".article-row");

            for (Element headline : newsHeadlines) {
                cardMarketCardList.add(formatMkmCard(headline, seller));
            }

            for (CardMarketCard mkmCard : cardMarketCardList) {
                if (mkmCard.getNameVf().equals(mbc.getNameVf()) &&
                        mkmCard.getNameVo().equals(mbc.getNameVo()) &&
                        compareState(mbc, mkmCard) &&
                        mkmCard.getFoil().equals(mbc.getFoil()) &&
                        mkmCard.getLang().equals(mbc.getLang()) &&
                        mkmCard.getExtension().equals(mbc.getExtension())
                ) {

                    if (mkmCard.getSellPrice() < mbc.getBuyPrice()) {
                        CompareCard compareCard = new CompareCard();
                        compareCard.setNameVf(mkmCard.getNameVf());
                        compareCard.setNameVo(mkmCard.getNameVo());
                        compareCard.setEtat(mkmCard.getEtat());
                        compareCard.setExtension(mkmCard.getExtension());
                        compareCard.setFoil(mkmCard.getFoil());
                        compareCard.setBuyPrice(mbc.getBuyPrice());
                        compareCard.setSellPrice(mkmCard.getSellPrice());
                        compareCard.setSeller(mkmCard.getSeller());
                        compareCard.setAmount(mkmCard.getAmout());


                        double profit = (mbc.getBuyPrice() / mkmCard.getSellPrice()) * 100;
                        compareCard.setProfit((double) Math.round(profit));

                        compareCardList.add(compareCard);
                        logger.info("MATCH");
                    }


                }
            }

            cardMarketCardList.clear();
        }

        for (CompareCard card : compareCardList) {
            logger.info("La carte " + card.getNameVf() + " peut être acheter chez " + seller + " et revendu pour " + card.getProfit() + "% de benef");
        }


        return compareCardList;
    }

    /**
     * Permet de comparer les états validé pour la comparaison
     *
     * @param mbc carte magicBazard
     * @param mkmCard carte mkm
     * @return true si les conditions sont respectés .
     */
    private boolean compareState(MagicBazarCard mbc, CardMarketCard mkmCard) {
        return (mbc.getEtat().equals("Mint") && mkmCard.getEtat().equals("Mint/Nmint")) ||
                (mbc.getEtat().equals("Near Mint") && mkmCard.getEtat().equals("Mint/Nmint")) ||
                (mbc.getEtat().startsWith(mkmCard.getEtat()) ||
                        (mbc.getEtat().equals(mkmCard.getEtat())));

    }


    /**
     * Return the list of cards from the seller webpage
     *
     * @param headline : containing the information of the cards
     * @param seller
     * @return a new Array of CardMarketCard Object
     */
    private static CardMarketCard formatMkmCard(Element headline, String seller) {

        String nameVf = headline.select(".col-seller").toString().replaceAll("<([^<]*)>", "").trim();
        String nameVoData = headline.select(".col-seller")
                .select("a[href]")
                .attr("href");
        String nameVo = nameVoData
                .substring(nameVoData.lastIndexOf("/") + 1);

        Elements cardData = headline.select(".col-product").select(".product-attributes");
        String extension = cardData.select("a[href]").get(0).select("span").attr("data-original-title");
        String etat = cardData.select("a[href]").get(1).select("span").attr("data-original-title");

        String lang = cardData.select("span").get(3).attr("data-original-title");

        String foil = cardData.first().childNodeSize() > 4 ?
                cardData.first().child(4).attr("data-original-title") : null;

        String price = headline.select(".price-container").select("span")
                .toString()
                .replaceAll("<([^<]*)>", "").trim()
                .replace("€", "")
                .replaceAll(",", ".")
                .replaceAll("\\s+", "");
        Double priceDb = Double.parseDouble(price);
        String amount = headline.select(".amount-container").select("span")
                .toString().replaceAll("<([^<]*)>", "").trim();
        Integer amountInt = Integer.parseInt(amount);

        CardMarketCard cardMarketCard = new CardMarketCard();
        cardMarketCard.setNameVf(nameVf);
        cardMarketCard.setNameVo(nameVo);
        cardMarketCard.setEtat(etat);
        cardMarketCard.setExtension(extension);
        cardMarketCard.setFoil(foil);
        cardMarketCard.setLang(lang);
        cardMarketCard.setAmout(amountInt);
        cardMarketCard.setSeller(seller);
        cardMarketCard.setSellPrice(priceDb);

        return cardMarketCard;
    }
}
