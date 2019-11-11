package me.java.library.utils.excel;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.java.library.utils.base.ReflectUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * File Name             :  ExcelUtils
 *
 * @author :  sylar
 * Create :  2019-06-08
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
public class ExcelUtils {

    public static void writeFile(Workbook workbook, String fileName) throws Exception {
        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public static void writeHttpResponse(Workbook workbook, String fileName, HttpServletResponse response) throws Exception {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } finally {
            workbook.close();
        }
    }

    public static <T> Workbook toExcel(List<T> list) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (list == null || list.size() == 0) {
            return workbook;
        }

        List<Field> fields = getValidFields(list.get(0).getClass());
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle cellStyle = workbook.createCellStyle();

        Sheet sheet = workbook.createSheet();
        Row row;
        Cell cell;
        int columnIndex = 0;
        int rowIndex = 0;
        ExcelColumn excelColumn;

        //写入标题行
        row = sheet.createRow(rowIndex++);
        for (Field field : fields) {
            excelColumn = field.getAnnotation(ExcelColumn.class);
            // 列宽注意乘256
            sheet.setColumnWidth(columnIndex, excelColumn.width() * 256);
            // 写入标题
            cell = row.createCell(columnIndex);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(Strings.isNullOrEmpty(excelColumn.name()) ? field.getName() : excelColumn.name());
            columnIndex++;
        }

        //写入内容行
        for (T t : list) {
            row = sheet.createRow(rowIndex++);
            columnIndex = 0;
            Object fieldValue;
            for (Field field : fields) {
                cell = row.createCell(columnIndex++);
                cell.setCellStyle(cellStyle);
                fieldValue = field.get(t);
                writeCell(cell, fieldValue);
            }
        }

        return workbook;
    }

    public static <T> List<T> toBean(Workbook workbook, Class<T> clazz) throws Exception {

        List<Field> fields = getValidFields(clazz);
        Map<String, Field> columnMap = getColumnMap(fields);

        Sheet sheet = workbook.getSheetAt(0);
        Row titleRow = sheet.getRow(0);
        List<String> titles = Lists.newArrayList();
        for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
            titles.add(titleRow.getCell(i).getStringCellValue());
        }

        List<T> list = Lists.newArrayList();
        int columnCount = titles.size();
        Cell cell;
        Row row;
        T t;

        for (Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
            row = it.next();
            if (row == null) {
                break;
            }
            if (row.getRowNum() == 0) {
                continue;
            }

            t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                cell = row.getCell(i);
                if (null == cell) {
                    continue;
                }

                readCell(t, cell, columnMap.get(titles.get(i)));
            }
            list.add(t);
        }
        return list;
    }

    private static void readCell(Object target, Cell cell, Field field) throws Exception {
        String cellValue = cell.getStringCellValue();
        field.set(target, cellValue);

        Class type = field.getType();

        if (String.class.isAssignableFrom(type)) {
            field.set(target, cellValue);
        } else if (Number.class.isAssignableFrom(type)) {
            if (Byte.class.isAssignableFrom(type)) {
                field.set(target, Byte.valueOf(cellValue));
            } else if (Float.class.isAssignableFrom(type)) {
                field.set(target, Float.valueOf(cellValue));
            } else if (Short.class.isAssignableFrom(type)) {
                field.set(target, Short.valueOf(cellValue));
            } else if (Integer.class.isAssignableFrom(type)) {
                field.set(target, Integer.valueOf(cellValue));
            } else if (Double.class.isAssignableFrom(type)) {
                field.set(target, Double.valueOf(cellValue));
            } else if (Long.class.isAssignableFrom(type)) {
                field.set(target, Long.valueOf(cellValue));
            }
        } else if (Date.class.isAssignableFrom(type)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            field.set(target, sdf.parse(cellValue));
        } else if (DateTime.class.isAssignableFrom(type)) {
            field.set(target, DateTime.parse(cellValue));
        }

    }

    private static void writeCell(Cell cell, Object fieldValue) {
        CellStyle cellStyle = cell.getCellStyle();
        DataFormat dataFormat = cell.getSheet().getWorkbook().createDataFormat();

        if (fieldValue instanceof Date) {
            cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue((Date) fieldValue);
        } else if (fieldValue instanceof Number) {
            cellStyle.setDataFormat(dataFormat.getFormat("#,#0"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(Double.parseDouble(fieldValue.toString()));
        } else if (fieldValue != null) {
            cell.setCellStyle(cellStyle);
            cell.setCellValue(fieldValue.toString());
        }
    }

    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);

        style.setFont(font);
        return style;
    }

    private static <T> List<Field> getValidFields(Class<T> clazz) {
        List<Field> allFields = ReflectUtils.getAllFieldByOrder(clazz);
        List<Field> fields = Lists.newArrayList();

        ExcelColumn excelColumn;
        for (Field field : allFields) {
            field.setAccessible(true);
            excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null) {
                fields.add(field);
            }
        }

        return fields;
    }

    private static Map<String, Field> getColumnMap(List<Field> fields) {
        Map<String, Field> map = Maps.newHashMap();

        ExcelColumn column;
        for (Field field : fields) {
            column = field.getAnnotation(ExcelColumn.class);
            Preconditions.checkState(!map.containsKey(column.name()),
                    "Excel中不允许存在同名列标题");
            map.put(column.name(), field);
        }

        return map;
    }

}
