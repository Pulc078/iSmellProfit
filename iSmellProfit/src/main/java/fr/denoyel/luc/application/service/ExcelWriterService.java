package fr.denoyel.luc.application.service;

import fr.denoyel.luc.application.model.MagicBazarCard;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


@Slf4j
@NoArgsConstructor
@Service
class ExcelWriterService {


    private final static String MAGIC_BAZAR_OUTPUT_FILE = "MagicBazardCards.xlsx";


    void writeExceFileOutput(List<MagicBazarCard> magicBazarCardList){
        try {
            writeExcel(magicBazarCardList);
        } catch(IOException e) {
            log.info("[I SMELL PROFITS] Impossible de créer le fichier Excel", e);
        }
    }


    private void writeExcel(List<MagicBazarCard> magicBazarCardList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        log.info("[I SMELL PROFITS] Writing Excel File");

        XSSFFont font = createTableFont(workbook);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);

        createTableHeadline(style, row);

        for (MagicBazarCard card : magicBazarCardList) {
            rowCount = writeTableRow(sheet, rowCount, card);
        }

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter filepath for output like C:\\exemple\\de\\chemin  : ");
        String sortie = reader.nextLine();

        try (FileOutputStream outputStream = new FileOutputStream(sortie + "\\" + MAGIC_BAZAR_OUTPUT_FILE)) {
            workbook.write(outputStream);
        }

        log.info("File {} is complete", MAGIC_BAZAR_OUTPUT_FILE);
    }

    private XSSFFont createTableFont(Workbook wb) {
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);
        return font;
    }

    private int writeTableRow(Sheet sheet, int rowCount, MagicBazarCard card) {
        Row row;
        row = sheet.createRow(++rowCount);
        log.info("[I SMELL PROFITS] Writing line " + rowCount);
        writeLigne(card, row);
        return rowCount;
    }

    private void createTableHeadline(CellStyle style, Row row) {
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
    }


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
