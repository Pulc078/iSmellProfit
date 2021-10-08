package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupHelper {

    public JsoupHelper() {
    }

    private static final Logger logger = LogManager.getLogger(JsoupHelper.class);

    /**
     * Initie une connexion vers l'url pour lequel on cherche à scrapper les donénes
     *
     * @param url de connexion
     * @param errorMessage le message d'erreur en cas d'imposibilité de connexion
     * @return un documnet HTLM contenant les informations
     */
    public Document getConnexion(String url, String errorMessage){
        Document doc = new Document("");
        try {
            doc =   Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .referrer("http://www.google.com")
                    .get()
            ;

        } catch (Exception e) {
            logger.error(errorMessage + e);
        }

        return doc;
    }
}
