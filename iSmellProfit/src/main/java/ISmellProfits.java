import model.MagicBazarCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MagicBazarReader;

import java.io.IOException;
import java.util.List;

public class ISmellProfits {

    private static final Logger logger = LogManager.getLogger(ISmellProfits.class);

    public static void main(String[] args) {
        MagicBazarReader magicBazarReader = new MagicBazarReader();

        List<MagicBazarCard> magicBazarCardList = magicBazarReader.getMagicBazarCards();

        service.ExcelWriter excelWriter = new service.ExcelWriter();

        try {
            excelWriter.writeExcel(magicBazarCardList);
        } catch (IOException e) {
            logger.info("Impossile de créer le fichier Excel : " + e);
        }

    }


}
