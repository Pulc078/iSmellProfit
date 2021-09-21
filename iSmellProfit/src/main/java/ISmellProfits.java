import model.MagicBazarCard;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.MagicBazarReader;

import java.util.ArrayList;
import java.util.List;

public class ISmellProfits {

    private static Logger logger = Logger.getLogger(ISmellProfits.class);


    public static void main(String[] args) {

        List<MagicBazarCard> magicBazarCardList = new ArrayList<>();

      for (int i = 1; i < 36; i++) {
            MagicBazarReader magicBazarReader = new MagicBazarReader();

            magicBazarCardList.addAll(magicBazarReader.getHotList(i));

        }





        logger.info("Nombre de carte sur la hotlist de magicbazar : " + magicBazarCardList.size());
        for (MagicBazarCard m : magicBazarCardList) {
            logger.info(m.toString());
        }

        // service.ExcelWriter excelWriter = new service.ExcelWriter();

        // excelWriter.writeExcel(magicBazarCardList);

    }


}
