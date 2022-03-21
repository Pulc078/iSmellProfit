package fr.denoyel.luc.application.service;


import fr.denoyel.luc.application.model.MagicBazarCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class ISmellProfitService {

    private MagicBazarReaderService magicBazarReaderService;
    private ExcelWriterService excelWriterService;


    public void createExcelFileForProfit() {
        log.info("[I SMELL PROFITS] Début de la génération des profits");

        List<MagicBazarCard> magicBazarCardList = magicBazarReaderService.getMagicBazarCards();

        excelWriterService.writeExceFileOutput(magicBazarCardList);
    }
}
