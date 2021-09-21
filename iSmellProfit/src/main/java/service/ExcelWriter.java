package service;

import model.MagicBazarCard;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    public ExcelWriter() {
    }

    /**
     * Génère un fichier excel de la liste des cartes du MagicBazar
     *
     * @param magicBazarCardList : liste des cartes à renseigner sur le fichier excel
     * @throws IOException : en cas d'erreur sur l'écriture du fichier
     */
    void writeExcel(List<MagicBazarCard> magicBazarCardList) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        for (MagicBazarCard card : magicBazarCardList) {
            Row row = sheet.createRow(++rowCount);
            writeLigne(card, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream("C:/Users/lucde/OneDrive/Bureau/Luc/plaayground/iSmellProfit/output/MagicBazar.xls")) {
            workbook.write(outputStream);
        }
    }


    /**
     * Ecrit les lignes du fichiers Excel
     *
     * @param card : carte portant les informations de la ligne
     * @param row  : numéro de la ligne à écrire
     */
    private static void writeLigne(MagicBazarCard card, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(card.getNameVf());

        cell = row.createCell(2);
        cell.setCellValue(card.getNameVo());

        cell = row.createCell(3);
        cell.setCellValue(card.getEtat());

        cell = row.createCell(4);
        cell.setCellValue(card.getExtension());

        cell = row.createCell(5);
        cell.setCellValue(card.getLang());

        cell = row.createCell(6);
        cell.setCellValue(card.getFoil());

        cell = row.createCell(7);
        cell.setCellValue(card.getBuyPrice());
    }
}
