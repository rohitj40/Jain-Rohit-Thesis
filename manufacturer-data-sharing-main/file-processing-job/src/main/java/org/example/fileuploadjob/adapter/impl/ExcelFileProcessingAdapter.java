package org.example.fileuploadjob.adapter.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.fileuploadjob.adapter.FileProcessingAdapter;
import org.example.fileuploadjob.model.FileModel;
import org.example.fileuploadjob.model.ManufacturerDataModel;
import org.example.fileuploadjob.util.DataFieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Qualifier("ExcelFileProcessingAdapter")
public class ExcelFileProcessingAdapter implements FileProcessingAdapter {
    private static final Logger log = LoggerFactory.getLogger(ExcelFileProcessingAdapter.class);

    @Override
    public FileModel getDataModelList(File file) {
        LocalDateTime processingTime = LocalDateTime.now();

        Set<String> fieldNames = new HashSet<>();
        List<ManufacturerDataModel> models = new ArrayList<>();

        String producerUserName = getProducerUsername(file);

        try (FileInputStream fileIS = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fileIS);
            Sheet sheet = workbook.getSheetAt(0);
            Map<Integer, String> fieldPositions = new HashMap<>();

            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            for (int index = firstRow; index <= lastRow; index++) {
                Row row = sheet.getRow(index);
                for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    // header row
                    if (index == 0) {
                        fieldPositions.put(cellIndex, getCellValueAsString(cell));
                    } else {
                        if("Producer ID".equals(fieldPositions.get(cellIndex))) {
                            continue;
                        }
                        ManufacturerDataModel model = new ManufacturerDataModel();
                        model.setProducerUsername(producerUserName);
                        model.setCommonData(false);
                        model.setSourceFile(file.getName());
                        model.setDataFieldName(fieldPositions.get(cellIndex).trim());
                        model.setDataFieldValue(getCellValueAsString(cell).trim());
                        model.setStartDateTime(processingTime);
                        model.setEndDateTime(processingTime.plusYears(300));
                        model.setUploadDateTime(processingTime);
                        for (DataFieldName fieldNameEnum: DataFieldName.values()) {
                            if(fieldNameEnum.getSynonyms().stream()
                                    .anyMatch(s -> s.replace("//s", "").toUpperCase()
                                            .equals(model.getDataFieldName().replace("//s", "").toUpperCase()))) {
                                model.setDataFieldName(fieldNameEnum.getDataFieldName());
                            }
                        }
                        models.add(model);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        models.stream().forEach(m -> fieldNames.add(m.getDataFieldName()));

        FileModel output = new FileModel();
        output.setFieldNames(fieldNames);
        output.setModels(models);

        return output;
    }


    public static String getCellValueAsString(Cell cell) {
        CellType cellType = cell.getCellType().equals(CellType.FORMULA)
                ? cell.getCachedFormulaResultType() : cell.getCellType();
        if (cellType.equals(CellType.STRING)) {
            return cell.getStringCellValue();
        }
        if (cellType.equals(CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toString();
            } else {
                return Double.toString(cell.getNumericCellValue());
            }
        }
        if (cellType.equals(CellType.BOOLEAN)) {
            return Boolean.toString(cell.getBooleanCellValue());
        }
        return "NA";
    }
}
