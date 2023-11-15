package com.accion.ti

import com.accion.ti.dto.exception.ServiceException
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.LogFactory
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


class ExcelService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService


    def leerArchivo(MultipartFile archivoExcel, Integer hojaExcel, Integer inicioFila) throws ServiceException {
        try {

            if (archivoExcel && !archivoExcel.isEmpty()) {
                throw new ServiceException(messageService.getErrorDocumentoVacio());
            }

            def objectosExcel = []
            List<String> cabecera = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(archivoExcel.getInputStream())
            XSSFSheet hoja = workbook.getSheetAt(hojaExcel)
            XSSFFormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Cell celda in hoja.getRow(inicioFila).cellIterator()) {
                cabecera.add(celda.getStringCellValue());
            }

            boolean saltarCabecera = true
            for (Row fila in hoja.rowIterator()) {
                if (saltarCabecera) {
                    saltarCabecera = false
                    continue
                }
                def objetoExcel = [:]
                for (celda in fila.cellIterator()) {

                    String nombreCabecera = "${cabecera[celda.getColumnIndex()]}".trim();
                    boolean esFecha = nombreCabecera.toLowerCase().contains("fecha");
                    if (esFecha) {
                        celda.setCellType(Cell.CELL_TYPE_STRING);
                    }

                    switch (formulaEvaluator.evaluateInCell(celda).getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            objetoExcel[nombreCabecera] = celda.getStringCellValue().trim();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            objetoExcel[nombreCabecera] = esFecha ? celda.getDateCellValue() : celda.getNumericCellValue()
                            break;
                        default:
                            throw new ServiceException("Tipo no indentificado en [${celda.getRowIndex()}][${celda.getColumnIndex()}]")
                    }
                }
                objectosExcel.add(objetoExcel);
            }
            return objectosExcel;

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    
    CellStyle getCellStyleCenter(XSSFWorkbook workbook, String colorHexadecimal) {
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat('$#,##0.00000'));

        CellStyle style = workbook.createCellStyle(); // Creating Style
        style.setAlignment(HorizontalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Century Gothic");
        style.setFont(font);

         if (colorHexadecimal) {
            colorHexadecimal = colorHexadecimal.replace("#", "");
            byte[] rgbB = Hex.decodeHex(colorHexadecimal.toCharArray());
            style.setFillForegroundColor(new XSSFColor(rgbB));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        return style;
    }

    public void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

     
     InputStream obtenerArchivo(String rutaArchivo) throws ServiceException{
        try {
            return new FileInputStream(rutaArchivo);

        } catch (FileNotFoundException ex) {
            throw new ServiceException("Archivo no encontrado.");
         }
    }

}
