package service;

import model.MagicBazarCard;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExcelWriter {

    private static Logger logger = Logger.getLogger(ExcelWriter.class);

    public ExcelWriter() {
    }

    /**
     * Génère un fichier excel de la liste des cartes du MagicBazar
     *
     * @param magicBazarCardList : liste des cartes à renseigner sur le fichier excel
     * @throws IOException : en cas d'erreur sur l'écriture du fichier
     */
    public void writeExcel(List<MagicBazarCard> magicBazarCardList) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();

        logger.info("Writing Excel File");

        XSSFFont font= (XSSFFont) wb.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);

        CellStyle style = wb.createCellStyle();
        style.setFont(font);

        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);

        //Création des entêtes de colonne
        Cell cell = row.createCell(0);
        cell.setCellValue("Prix d'achat");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Nom Français");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Nom Anglais");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Etat");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Extension");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Langue");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Foil");
        cell.setCellStyle(style);


        //écriture des données du fichier excel
        for (MagicBazarCard card : magicBazarCardList) {
            row = sheet.createRow(++rowCount);
            logger.info("Writing line " + rowCount);
            writeLigne(card, row);
        }

        for (int i = 0; i <  7 ; i++) {
            sheet.autoSizeColumn(i);
        }

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter filepath for output like C:\\exemple\\de\\chemin  : ");
        String sortie = reader.nextLine();

        try (FileOutputStream outputStream = new FileOutputStream(sortie + "\\MagicBazardCards.xlsx")) {
            wb.write(outputStream);
        }

        logger.info("File MagicBazardCards.xlsx is complete");
    }


    /**
     * Ecrit les lignes du fichiers Excel
     *
     * @param card : carte portant les informations de la ligne
     * @param row  : numéro de la ligne à écrire
     */
    private static void writeLigne(MagicBazarCard card, Row row) {

        Cell cell = row.createCell(0);
        cell.setCellValue(card.getBuyPrice());

        cell = row.createCell(1);
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

    }
}
