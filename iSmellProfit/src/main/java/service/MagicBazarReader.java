package service;

import model.MagicBazarCard;
import model.UrlEditionEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MagicBazarReader {

    private static final Logger logger = LogManager.getLogger(MagicBazarReader.class);


    public MagicBazarReader() {
    }


    /**
     * @return An array of all the cards on the market
     */
    public List<MagicBazarCard> getMagicBazarCards() {
        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();

        for (int i = 1; i < 36; i++) {
            magicBazarCardList.addAll(getHotList(i));
        }

        UrlEditionEnum.stream().forEach(url -> {
            magicBazarCardList.addAll(getEdition(url.getValue()));
        });

        logger.info("Nombre de carte en rachat sur le magicbazar : " + magicBazarCardList.size());
        return magicBazarCardList;
    }


    /**
     * Retourne les éléments de la page de la hotlist
     *
     * @param i : page du magicbazar sur lequel récupérer les informations
     * @return la liste des cartes de la page
     */
    private List<MagicBazarCard> getHotList(int i) {
        String url = ("https://www.play-in.com/rachat/hotlist/magic?p=" + i);

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();


        Document doc = new Document("");
        try {
            logger.info("Getting card from hotlist page number " + i);
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .referrer("http://www.google.com")
                    .get();

        } catch (Exception e) {
            logger.error("Error while getting information from page : " + i + " : " + e);
        }


        Elements newsHeadlines = doc.select(".container_info_card");

        for (Element headline : newsHeadlines) {
            MagicBazarCard magicBazarCard = getMagicBazarCardFromHotList(headline);

            magicBazarCardList.add(magicBazarCard);

        }

        return magicBazarCardList;

    }

    /**
     * Return the list of card for the specific edition
     *
     * @param edition wanted for the search
     * @return a array of MagicBazarCard
     */
    private List<MagicBazarCard> getEdition(String edition) {
        String url = ("https://www.play-in.com/rachat/edition/" + edition);

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();

        Document doc = new Document("");
        try {
            logger.info("Getting card from edition : " + edition);
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .referrer("http://www.google.com")
                    .get();

        } catch (Exception e) {
            logger.error("Error while getting information from page : " + edition + " : " + e);
        }

        Elements newsHeadlines = doc.select(".filterElement.cards");

        for (Element headline : newsHeadlines) {
            List<MagicBazarCard> resultList = getMagicBazardCardFromEdition(headline);

            magicBazarCardList.addAll(resultList);
        }


        return magicBazarCardList;
    }

    /**
     * Create a new MagicBazard Card from the Element
     *
     * @param headline : containing the information of the cards
     * @return a new MagicBazardCard Object
     */
    private MagicBazarCard getMagicBazarCardFromHotList(Element headline) {
        MagicBazarCard magicBazarCard = new MagicBazarCard();

        String nameVf = headline.select(".name_vf").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.setNameVf(nameVf);

        String nameVO = headline.select(".name_vo").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.setNameVo(nameVO);

        magicBazarCard.setExtension(headline.select(".extension").select("img[src$=.png]").get(0).attr("title"));

        magicBazarCard.setLang(headline.select(".lang").select("img[src$=.png]").get(0).attr("title"));

        String etat = headline.select(".etat").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.setEtat(etat);

        String price = headline.select(".price_product").toString()
                .replaceAll("<([^<]*)>", "").trim()
                .replace("&nbsp;€", " ")
                .replaceAll(",", ".")
                .replaceAll("\\s+", "");
        magicBazarCard.setBuyPrice(Double.parseDouble(price));


        if (headline.select(".foil").select("img[src$=.png]").size() > 1) {

            magicBazarCard.setFoil(headline.select(".foil").select("img[src$=.png]").get(0).attr("title"));
        }
        return magicBazarCard;
    }


    /**
     * Return the list of cars from the edition page
     *
     * @param headline : containing the information of the cards
     * @return a new Array of MagicBazardCard Object
     */
    private List<MagicBazarCard> getMagicBazardCardFromEdition(Element headline) {
        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();
        String nameVf = headline.select(".tr.name.name_hover.onHoverImg").get(0).toString().replaceAll("<([^<]*)>", "").trim();

        String nameVo = headline.select(".tr.name.name_hover.onHoverImg").get(1).toString().replaceAll("<([^<]*)>", "").trim();

        String extension = headline.select(".ext").select("img[src$=.png]").get(0).attr("title");

        List<Element> elementList = headline.select(".tr.lang_etat").select("option[data-id]");

        elementList.forEach(el -> {
            MagicBazarCard magicBazarCard = new MagicBazarCard();

            //information commune aux cartes
            magicBazarCard.setNameVf(nameVf);
            magicBazarCard.setNameVo(nameVo);
            magicBazarCard.setExtension(extension);


            //Informations spécifique
            Double buyPrice = Double.parseDouble(el.attr("data-prix"));
            magicBazarCard.setBuyPrice(buyPrice);

            String foil = el.attr("data-foil").equals("O") ? "FOIL" : null;
            magicBazarCard.setFoil(foil);

            String[] lang_etat = el.toString().replaceAll("<([^<]*)>", "").trim().split("\\s+");

            String lang = lang_etat[0];
            magicBazarCard.setLang(lang);

            String etat = lang_etat[1];
            magicBazarCard.setEtat(etat);

            magicBazarCardList.add(magicBazarCard);

        });

        return magicBazarCardList;
    }

}

