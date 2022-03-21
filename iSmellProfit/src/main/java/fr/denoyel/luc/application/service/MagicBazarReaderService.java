package fr.denoyel.luc.application.service;

import fr.denoyel.luc.application.model.MagicBazarCard;
import fr.denoyel.luc.application.model.UrlEditionEnum;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Service
class MagicBazarReaderService {
    private final String PLAY_IN_RACHAT_URL = "https://www.play-in.com/rachat";

    /**
     * @return An array of all the cards on the market
     */
    List<MagicBazarCard> getMagicBazarCards() {
        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();

        for (int pageNumber = 1; pageNumber < 36; pageNumber++) {
            magicBazarCardList.addAll(getCardsFromHotList(pageNumber));
        }

        UrlEditionEnum.stream().forEach(
                url -> magicBazarCardList.addAll(getCardsFromEdition(url.getValue()))
        );

        log.info("[I SMELL PROFITS] Nombre de carte en rachat sur le magicbazar : {}", magicBazarCardList.size());
        return magicBazarCardList;
    }


    private List<MagicBazarCard> getCardsFromHotList(int pageNumber) {

        String url = (PLAY_IN_RACHAT_URL + "/hotlist/magic?p=" + pageNumber);

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();


        Document doc = new Document("");
        try {
            log.info("[I SMELL PROFITS] Getting card from hotlist page number " + pageNumber);
            doc = getHtmlPageFromUrl(url);

        } catch (Exception error) {
            log.error("[I SMELL PROFITS] Error while getting information from page : {} ", pageNumber, error);
        }


        Elements newsHeadlines = doc.select(".container_info_card");

        for (Element headline : newsHeadlines) {
            MagicBazarCard magicBazarCard = getMagicBazarCardFromHotList(headline);

            magicBazarCardList.add(magicBazarCard);

        }

        return magicBazarCardList;

    }

    private List<MagicBazarCard> getCardsFromEdition(String edition) {
        String url = (PLAY_IN_RACHAT_URL + "/rachat/edition/" + edition);

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();

        Document doc = new Document("");
        try {
            log.info("[I SMELL PROFITS] Getting card from edition : " + edition);
            doc = getHtmlPageFromUrl(url);

        } catch (Exception e) {
            log.error("[I SMELL PROFITS] Error while getting information from page : " + edition + " : " + e);
        }

        Elements newsHeadlines = doc.select(".filterElement.cards");

        for (Element headline : newsHeadlines) {
            List<MagicBazarCard> resultList = getMagicBazardCardFromEdition(headline);

            magicBazarCardList.addAll(resultList);
        }


        return magicBazarCardList;
    }


    private Document getHtmlPageFromUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .referrer("http://www.google.com")
                .get();
    }


    /**
     * Create a new MagicBazard Card from the Element
     *
     * @param headline : containing the information of the cards
     * @return a new MagicBazardCard Object
     */
    private MagicBazarCard getMagicBazarCardFromHotList(Element headline) {
        MagicBazarCard.MagicBazarCardBuilder magicBazarCard = MagicBazarCard.builder();

        String nameVf = headline.select(".name_vf").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.nameVf(nameVf);

        String nameVO = headline.select(".name_vo").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.nameVo(nameVO);

        magicBazarCard.extension(headline.select(".extension").select("img[src$=.png]").get(0).attr("title"));

        magicBazarCard.lang(headline.select(".lang").select("img[src$=.png]").get(0).attr("title"));

        String etat = headline.select(".etat").toString().replaceAll("<([^<]*)>", "").trim();
        magicBazarCard.etat(etat);

        String price = headline.select(".price_product").toString()
                .replaceAll("<([^<]*)>", "").trim()
                .replace("&nbsp;€", " ")
                .replaceAll(",", ".")
                .replaceAll("\\s+", "");
        magicBazarCard.buyPrice(Double.parseDouble(price));


        if (headline.select(".foil").select("img[src$=.png]").size() > 1) {

            magicBazarCard.foil(headline.select(".foil").select("img[src$=.png]").get(0).attr("title"));
        }

        return magicBazarCard.build();
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
            MagicBazarCard.MagicBazarCardBuilder magicBazarCard = MagicBazarCard.builder();

            //information commune aux cartes
            magicBazarCard.nameVf(nameVf);
            magicBazarCard.nameVo(nameVo);
            magicBazarCard.extension(extension);


            //Informations spécifique
            Double buyPrice = Double.parseDouble(el.attr("data-prix"));
            magicBazarCard.buyPrice(buyPrice);

            String foil = el.attr("data-foil").equals("O") ? "FOIL" : null;
            magicBazarCard.foil(foil);

            String[] lang_etat = el.toString().replaceAll("<([^<]*)>", "").trim().split("\\s+");

            String lang = lang_etat[0];
            magicBazarCard.lang(lang);

            String etat = lang_etat[1];
            magicBazarCard.etat(etat);

            magicBazarCardList.add(magicBazarCard.build());

        });

        return magicBazarCardList;
    }

}

