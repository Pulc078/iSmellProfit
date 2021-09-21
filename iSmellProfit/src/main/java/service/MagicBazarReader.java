package service;

import model.MagicBazarCard;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MagicBazarReader {

    private static Logger logger = Logger.getLogger(MagicBazarReader.class);


    public MagicBazarReader() {
    }


    /**
     * Retourne les éléments de la page de la hotlist
     *
     * @param i : page du magicbazar sur lequel récupérer les informations
     * @return la liste des cartes de la page
     */
    public List<MagicBazarCard> getHotList(int i) {
        String url = ("https://www.play-in.com/rachat/hotlist/magic?p=" + i);

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();


        Document doc = new Document("");
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .referrer("http://www.google.com")
                    .get();

        } catch (Exception e) {
            logger.info("Impossible de récuppérer les éléments de la page " + i + " : " + e);
        }


        Elements newsHeadlines = doc.select(".container_info_card");

        for (Element headline : newsHeadlines) {
            MagicBazarCard magicBazarCard = getMagicBazarCard(headline);

            magicBazarCardList.add(magicBazarCard);

        }

        return magicBazarCardList;

    }

    /**
     * Create a new MagicBazard Card from the Element
     *
     * @param headline : containing the information of the cards
     * @return a new MagicBazardCard Object
     */
    private MagicBazarCard getMagicBazarCard(Element headline) {
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

}

