package yi.master.util.excel;


import cn.hutool.core.util.StrUtil;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.service.DataPoolService;
import yi.master.business.testconfig.service.PoolDataItemService;
import yi.master.business.testconfig.service.PoolDataNameService;
import yi.master.business.testconfig.service.PoolDataValueService;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入导出数据池相关文件
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2021/1/6 11:12
 */
public class ExcelPoolDataTemplate {
    private static final Logger logger = Logger.getLogger(ExcelPoolDataTemplate.class);

    /**
     *  下载数据池导入模板
     * @author xuwangcheng
     * @date 2021/1/6 16:45
     * @param dataPool dataPool
     * @param names names
     * @param items items
     * @return {@link String}
     */
    public static String exportDocument (DataPool dataPool, List<PoolDataName> names, List<PoolDataItem> items) {
        String path = null;
        OutputStream outputStream = null;
        String fileName = "dataPoolTemplate_" + System.currentTimeMillis() + ".xlsx";
        try {
            //创建excel
            Workbook wb = PoiExcelUtil.createWorkBook(PoiExcelUtil.XLSX);
            Sheet sheet = PoiExcelUtil.createSheet(wb, dataPool.getName() + "_数据导入模板");
            // 创建变量名行
            // 空出第一行
            Row itemRow = sheet.createRow(0);
            for (int i = 1;i <= names.size();i++) {
                Row nameRow = sheet.createRow(i);
                Cell cell = nameRow.createCell(0);
                cell.setCellValue(names.get(i - 1).getName());
            }
            // 创建池类别列
            CellStyle headerStyle = PoiExcelUtil.createHeadCellStyle(wb);
            Cell titleCell = itemRow.createCell(0);
            titleCell.setCellValue("变量名");
            titleCell.setCellStyle(headerStyle);
            for (int i = 1;i <= items.size();i++) {
                PoolDataItem item = items.get(i - 1);
                Cell cell = itemRow.createCell(i);
                cell.setCellValue(item.getName());
                cell.setCellStyle(headerStyle);
            }

            path = SystemConsts.EXCEL_FILE_FOLDER + File.separator + fileName;
            outputStream = new FileOutputStream(new File(FrameworkUtil.getProjectPath() + File.separator + path));

            wb.write(outputStream);
        } catch (Exception e) {
            logger.error("导出数据池变量模板文件失败！", e);
            throw new YiException(AppErrorCode.EXPORT_FILE_ERROR);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return path;
    }

    /**
     *  excel导入数据池数据
     * @author xuwangcheng
     * @date 2021/1/6 16:45
     * @param poolId poolId
     * @param filePath filePath
     * @return
     */
    public static void importItemValues (Integer poolId, String filePath) {
        DataPoolService dataPoolService = (DataPoolService) FrameworkUtil.getSpringBean(DataPoolService.class);

        DataPool dataPool = dataPoolService.get(poolId);
        if (dataPool == null) {
            throw new YiException("数据池信息不存在");
        }

        PoolDataValueService poolDataValueService = (PoolDataValueService) FrameworkUtil.getSpringBean(PoolDataValueService.class);

        try {
            Map<Integer, Map<Integer, String>> infos = parseExcelInfo(filePath, poolId);

            // 保存到数据库
            for (Integer itemId:infos.keySet()) {
                Map<Integer, String>  info = infos.get(itemId);
                for (Integer nameId:info.keySet()) {
                    if (itemId != null && nameId != null) {
                        poolDataValueService.updateItemValue(itemId, nameId, info.get(nameId));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("导入出错", e);
            throw new YiException("导入出错");
        }

    }

    /**
     *  根据文件路径解析类别->变量->值
     * @author xuwangcheng
     * @date 2021/1/6 16:46
     * @param filePath filePath
     * @param poolId poolId
     * @return {@link Map}
     */
    private static Map<Integer, Map<Integer, String>> parseExcelInfo (String filePath, Integer poolId) {
        Map<Integer, Map<Integer, String>> infos = new HashMap<>();
        if (StrUtil.isBlank(filePath)) {
            return infos;
        }
        XSSFWorkbook xssfWorkbook = null;
        try {
            InputStream is = new FileInputStream(filePath);
            xssfWorkbook = new XSSFWorkbook(is);
            is.close();
        } catch (IOException e) {
            logger.error(e);
        }

        if (xssfWorkbook != null) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            List<Integer> itemNames = parseItems(xssfSheet, poolId);
            if (itemNames.size() == 0) {
                throw new YiException(AppErrorCode.TEMPLATE_FILE_ERROR);
            }
            List<Integer> valueNames = parseNames(xssfSheet, poolId);
            if (valueNames.size() == 0) {
                throw new YiException(AppErrorCode.TEMPLATE_FILE_ERROR);
            }


            for (int i = 1;i <= itemNames.size();i++) {
                infos.put(itemNames.get(i - 1), parseValues(valueNames, xssfSheet, i));
            }

        }

        return infos;
    }

    /**
     *  解析值value
     * @author xuwangcheng
     * @date 2021/1/6 16:46
     * @param valueNames valueNames
     * @param xssfSheet xssfSheet
     * @param colIndex colIndex
     * @return {@link Map}
     */
    private static Map<Integer, String> parseValues (List<Integer> valueNames, XSSFSheet xssfSheet, int colIndex) {
       Map<Integer, String> values = new HashMap<>(valueNames.size());
       for (int i = 1;i <= valueNames.size();i++) {
           XSSFRow row = xssfSheet.getRow(i);
           XSSFCell cell = row.getCell(colIndex);
           values.put(valueNames.get(i - 1), PoiExcelUtil.getValue(cell));
       }

       return values;
    }

    /**
     *  解析类别
     * @author xuwangcheng
     * @date 2021/1/6 16:47
     * @param xssfSheet xssfSheet
     * @param poolId poolId
     * @return {@link List}
     */
    private static List<Integer> parseItems (XSSFSheet xssfSheet, Integer poolId) {
        // 第一行从第二列开始是类别
        XSSFRow xssfRow = xssfSheet.getRow(0);
        PoolDataItemService poolDataItemService = (PoolDataItemService) FrameworkUtil.getSpringBean(PoolDataItemService.class);
        List<Integer> itemNames = new ArrayList<>();
        boolean flag = true;

        int i = 1;
        while (flag) {
            XSSFCell cell = xssfRow.getCell(i);
            String name = PoiExcelUtil.getValue(cell);
            if (StrUtil.isBlank(name)) {
                flag = false;
            } else {
                PoolDataItem poolDataItem = poolDataItemService.findByName(name, poolId);
                itemNames.add(poolDataItem == null ? null : poolDataItem.getItemId());
            }
            i ++;
        }

        return itemNames;
    }

    /**
     *  解析变量名称
     * @author xuwangcheng
     * @date 2021/1/6 16:47
     * @param xssfSheet xssfSheet
     * @param poolId poolId
     * @return {@link List}
     */
    private static List<Integer> parseNames (XSSFSheet xssfSheet, Integer poolId) {
        List<Integer> names = new ArrayList<>();
        PoolDataNameService poolDataNameService = (PoolDataNameService) FrameworkUtil.getSpringBean(PoolDataNameService.class);
        boolean flag = true;
        int i = 1;
        while (flag) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            if (xssfRow == null) {
                flag = false;
                continue;
            }
            XSSFCell cell = xssfRow.getCell(0);
            String name = PoiExcelUtil.getValue(cell);
            if (StrUtil.isBlank(name)) {
                flag = false;
            } else {
                PoolDataName poolDataName = poolDataNameService.findByName(name, poolId);
                names.add(poolDataName == null ? null : poolDataName.getId());
            }
            i ++;
        }

        return names;
    }
}
