package com.loy.e.core.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;  
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class TableToExcelUtil {
	 /** 
     *   
     * @param sheetName 
     * @param html 
     * @param headNum表头的行数 
     * @throws FileNotFoundException 
     */  
    public static void createExcelFormTable(String sheetName,String html,int headNum ,OutputStream os) throws FileNotFoundException{  
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet(sheetName);  
        CellStyle headStyle = createHeadStyle(wb);  
        CellStyle bodyStyle = createBodyStyle(wb);  
        
        SAXBuilder sb = new SAXBuilder();  
        html = "<!DOCTYPE table[<!ENTITY nbsp \"\">]>"+html;
        ByteArrayInputStream is = new ByteArrayInputStream(html.getBytes()); 
        InputStreamReader isr = null;
        try {
			isr = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
        try {  
            org.jdom.Document document = sb.build(isr);  
            //获取table节点  
            Element root = document.getRootElement();  
            //获取tr的list  
            @SuppressWarnings("unchecked")
			List<Element> trList = root.getChildren("tr");  
            int[][] area = getCellArea(trList);  
            //循环创建行  
            for(int i=0;i<trList.size();i++){  
                HSSFRow row = sheet.createRow(i);  
                @SuppressWarnings("unchecked")
				List<Element> tdList = trList.get(i).getChildren("td");  
                //该行td的序号  
                int tdIndex = 0;  
                for(int ii=0;ii<area[i].length;ii++){  
                    row.createCell(ii);  
                    HSSFCell cell = row.getCell(ii);  
                    //判断是否为表头，使用对应的excel格式  
                    if(i<headNum){  
                        cell.setCellStyle(headStyle);  
                    }else{  
                        cell.setCellStyle(bodyStyle);  
                    }  
                    //如果对应的矩阵数字为1，则和横向前一单元格合并  
                    if(area[i][ii]==1){  
                        sheet.addMergedRegion(new CellRangeAddress(i,i,ii-1,ii));  
                    }else if(area[i][ii]==2){//如果对应的矩阵数字为2，则和纵向的前一单元格合并  
                        sheet.addMergedRegion(new CellRangeAddress(i-1,i,ii,ii));  
                    }else{//如果为0，显示td中对应的文字,td序号加1  
                        cell.setCellValue(getInnerText(tdList.get(tdIndex)));  
                        tdIndex ++;  
                    }  
                      
                }  
                  
            }  
          
        wb.write(os);  
        } catch (JDOMException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 导出excel表格二维数组：0为文字占用格，1为横向被合并格，2为纵向合并格 
     * @param trList 
     * @return 
     * zyn 
     * 2012-12-21 下午1:35:40 
     */  
    private static int[][] getCellArea(List<Element> trList){  
        //获取table单元格矩阵  
        Element headtr = trList.get(0);  
        @SuppressWarnings("unchecked")
		List<Element> headTdList = headtr.getChildren("td");  
        //每行的未经合并的单元格个数  
        int cols = 0;  
        for(Element e:headTdList){  
            int colspan = Integer.valueOf(null==e.getAttributeValue("colspan")?"0":e.getAttributeValue("colspan"));  
            if(colspan==0){  
                colspan =1;  
            }  
            cols += colspan;  
        }  
        //初始化单元格矩阵  
        int[][] area = new int[trList.size()][cols];  
        for(int i=0;i<trList.size();i++){  
            Element tr = trList.get(i);  
            @SuppressWarnings("unchecked")
			List<Element> tdList = tr.getChildren("td");  
            //该行到ii个单元格为止被合并的单元格个数  
            int rowColspan = 0;  
            for(int ii=0;ii<tdList.size();ii++){  
                //本单元格跨度计算前的td数  
                int oldIndex = ii+rowColspan;  
                Element td = tdList.get(ii);  
                int colspan = Integer.valueOf(null==td.getAttributeValue("colspan")?"0":td.getAttributeValue("colspan"));  
                //colspan为0或者1证明未合并  
                colspan = colspan>1?colspan:1;  
                rowColspan += colspan-1;  
                //单元格需要被横向合并声明为1  
                for(int m=1;m<colspan;m++){  
                    area[i][oldIndex+m]=1;  
                }  
                int rowspan = Integer.valueOf(null==td.getAttributeValue("rowspan")?"0":td.getAttributeValue("rowspan"));  
                rowspan = rowspan>1?rowspan:1;  
                //单元格需要被纵向向合并声明为2  
                for(int m=1;m<rowspan;m++){  
                    area[m+i][oldIndex] = 2;  
                }  
            }  
        }  
        /*for(int a=0;a<area.length;a++){ 
            for(int b =0;b<area[0].length;b++){ 
                System.out.print(area[a][b]); 
            } 
            System.out.println(""); 
        }*/  
        return area;  
    }  
      
      
    /**- 
     * 设置表头样式 
     * @param wb 
     * @return 
     */  
    private static CellStyle createHeadStyle(Workbook wb){  
        CellStyle style = wb.createCellStyle();  
        Font headerFont = wb.createFont();  
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        style.setAlignment(CellStyle.ALIGN_CENTER);  
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());  
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setFont(headerFont);  
  
        style.setBorderRight(CellStyle.BORDER_THIN);  
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderBottom(CellStyle.BORDER_THIN);  
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderLeft(CellStyle.BORDER_THIN);  
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderTop(CellStyle.BORDER_THIN);  
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
        return style;  
    }  
      
    /**- 
     * 设置表单记录样式 
     * @param wb 
     * @return 
     */  
    private static CellStyle createBodyStyle(Workbook wb){  
        CellStyle style = wb.createCellStyle();  
        Font headerFont = wb.createFont();  
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);  
        style.setAlignment(CellStyle.ALIGN_CENTER);  
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());  
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setFont(headerFont);  
  
        style.setBorderRight(CellStyle.BORDER_THIN);  
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderBottom(CellStyle.BORDER_THIN);  
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderLeft(CellStyle.BORDER_THIN);  
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
        style.setBorderTop(CellStyle.BORDER_THIN);  
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
        return style;  
    }  
      
    private static String getInnerText(Element td){  
        String txt = "";  
          if(td.getText()==null || td.getText().equals("")){  
           if(null != td.getChildren()){  
            for(int i=0;i<td.getChildren().size();i++){  
                Element e = (Element)td.getChildren().get(i);  
                 txt += getInnerText(e);  
            }  
         }  
          }else{  
     txt = td.getText();  
      }  
      return txt; }  
      
   
}
