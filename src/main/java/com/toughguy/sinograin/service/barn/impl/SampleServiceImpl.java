package com.toughguy.sinograin.service.barn.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.toughguy.sinograin.model.barn.CornExaminingReport;
import com.toughguy.sinograin.model.barn.Handover;
import com.toughguy.sinograin.model.barn.Sample;
import com.toughguy.sinograin.model.barn.TestItem;
import com.toughguy.sinograin.model.barn.WarehouseCounterPlace;
import com.toughguy.sinograin.model.barn.WheatExaminingReport;
import com.toughguy.sinograin.pagination.PagerModel;
import com.toughguy.sinograin.persist.barn.prototype.ICornExaminingReportDao;
import com.toughguy.sinograin.persist.barn.prototype.IHandoverDao;
import com.toughguy.sinograin.persist.barn.prototype.ISampleDao;
import com.toughguy.sinograin.persist.barn.prototype.IWheatExaminingReportDao;
import com.toughguy.sinograin.service.barn.prototype.ISampleService;
import com.toughguy.sinograin.service.barn.prototype.ITestItemService;
import com.toughguy.sinograin.service.barn.prototype.IWarehouseCounterPlaceService;
import com.toughguy.sinograin.service.impl.GenericServiceImpl;
import com.toughguy.sinograin.util.POIUtils;





@Service
public class SampleServiceImpl extends GenericServiceImpl<Sample, Integer> implements ISampleService {
	
	@Autowired
	IWheatExaminingReportDao wheatExaminingReportDao;
	
	@Autowired
	ISampleDao  sampleDao;
	
	@Autowired
	IHandoverDao  handoverDao;
	
	@Autowired
	ICornExaminingReportDao icornExaminingReportDao;
	
	@Autowired
	IWarehouseCounterPlaceService  iWarehouseCounterPlaceService;
	
	@Autowired
	ITestItemService  testItemService;
	@Override
	public PagerModel<Sample> findPaginatedMobile(Map<String, Object> params) {	
		return  ((ISampleDao)dao).findPaginatedMobile(params);
	}

	@Override
	public Sample findBySampleNo(String sampleNo) {
		return ((ISampleDao)dao).findBySampleNo(sampleNo);	
	}

	@Override
	public void deleteByPId(int pId) {
		// TODO Auto-generated method stub
		((ISampleDao)dao).deleteByPId(pId);
	}

	@Override
	public Sample findBySampleNum(String sampleNum) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findBySampleNum(sampleNum);	
	}
	
	@Override
	public List<Sample> findSamplesByTask(String taskName) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findSamplesByTask(taskName);	
	}
	
	
	/*
	 * 导出玉米
	 * 
	 */
	public void Export(HttpServletResponse response,String ids,String title) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/玉米验收情况汇总表.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 10000);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sheet = workbook.getSheetAt(0);
//			Row row = sheet.createRow(0);
//			Region region = new Region(0, (short) 0, 1, (short) 41);
//			
//			Cell cell = row.createCell(0);
//			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 1, (short) 41));
//			utils.setRegionStyle(sheet, region, utils.Style(workbook));
//			cell.setCellValue(title);
			 
			Row row1 = sheet.createRow(7);
			CellRangeAddress region1 = new CellRangeAddress(7, (short) 7, 0, (short) 6);
			 
			Cell cell1 = row1.createCell(0);
			utils.setRegionStyle(sheet, region1, utils.Style1(workbook));
			sheet.addMergedRegion(region1);
			cell1.setCellValue("合计");
			
//			Cell cell2 = row1.createCell(7);
//			cell2.setCellStyle(utils.Style1(workbook));
//			cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			cell2.setCellFormula("SUM(H9)");
			for (int i = 8; i < 42; i++) {
			Cell createCell = row1.createCell(i);
			createCell.setCellStyle(utils.Style1(workbook));
			createCell.setCellValue("");
		}
			
			Row row2 = sheet.createRow(8);
			CellRangeAddress region2 = new CellRangeAddress(8, (short) 8, 1, (short) 6);
			
			Cell cell3 = row2.createCell(1);
			utils.setRegionStyle(sheet, region2, utils.Style1(workbook));
			sheet.addMergedRegion(region2);
			cell3.setCellValue("小计");
			
			for (int i = 8; i < 42; i++) {
				Cell createCell = row2.createCell(i);
				createCell.setCellStyle(utils.Style1(workbook));
				createCell.setCellValue("");
			}
			
			Cell cell4 = row2.createCell(7);
			cell4.setCellStyle(utils.Style1(workbook));
			cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell4.setCellFormula("SUM(AB9)");
			String pLibraryName = null;
			int startRow = 10;
			int endRow = 7;
			//是否合并
			boolean is = false;
			//是否是第一次
			boolean isFirst = false;
			//记录上一次的i值
			int oldI=0;
			String[] id = ids.split(",");
			//string[]转int[]
			int intId[] = new int[id.length];
			for(int i=0;i<id.length;i++) {
				intId[i] = Integer.parseInt(id[i]);
			}
			//冒泡排序
			for(int k=0;k<intId.length;k++) {
				for(int j=k + 1;j<intId.length;j++) {
					if(intId[k] > intId[j]) {
						int temp = intId[k];
						intId[k] = intId[j];
						intId[j] = temp;
					}
				}
			}
			for (int i = 0; i < intId.length; i++) {
				CornExaminingReport cornExaminingReport = icornExaminingReportDao.findBasicSituation(intId[i]);
				Row row3 =null;
				if(isFirst == true) {
					row3 = sheet.createRow(startRow + (i-oldI));
					row3.setHeight((short) 300); // 行高
				} else {
					row3 = sheet.createRow(startRow + (i-oldI-1));
					row3.setHeight((short) 300); // 行高
				}
				if(pLibraryName == null || pLibraryName.equals("")) {
					pLibraryName = cornExaminingReport.getpLibraryName();
					Cell cellPLibraryName = row3.createCell(0);
					cellPLibraryName.setCellStyle(utils.Style1(workbook));
					cellPLibraryName.setCellValue(cornExaminingReport.getpLibraryName());
					if(i != intId.length-1) {
						CornExaminingReport newCornExaminingReport = icornExaminingReportDao.findBasicSituation(intId[i+1]);
							if(cornExaminingReport.getpLibraryName().equals(newCornExaminingReport.getpLibraryName())) {
								startRow = i+9;
								isFirst = true;
							} else {
								endRow = i+9;
								startRow=i+11;
							}
						}
				} else if(pLibraryName.equals(cornExaminingReport.getpLibraryName())){
					is = true;
					if(i == intId.length -1) {
						row3 = sheet.createRow(startRow + (i-oldI-1));
						row3.setHeight((short) 300); // 行高
						endRow = startRow + (i-oldI-1);
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow,endRow , (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style2(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(pLibraryName);
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(pLibraryName);
						}
					} 
				} else {
					if(i != intId.length-1) {
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow,  endRow, (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style1(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(cornExaminingReport.getpLibraryName());
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(cornExaminingReport.getpLibraryName());
						}
						CornExaminingReport newCornExaminingReport = icornExaminingReportDao.findBasicSituation(intId[i+1]);
						if(cornExaminingReport.getpLibraryName().equals(newCornExaminingReport.getpLibraryName())) {
							CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
							Row rowXiaoJi = sheet.createRow(endRow+1);
							Cell cellXiaoJi = rowXiaoJi.createCell(0);
							utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
							sheet.addMergedRegion(regionXiaoJi);
							cellXiaoJi.setCellValue("小计");
							endRow = startRow + (i-oldI-1);
							
							for (int j = 8; j < 42; j++) {
								Cell createCell = rowXiaoJi.createCell(j);
								createCell.setCellStyle(utils.Style1(workbook));
								createCell.setCellValue("");
							}
							
						} else {
							CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
							Row rowXiaoJi = sheet.createRow(endRow+1);
							Cell cellXiaoJi = rowXiaoJi.createCell(0);
							utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
							sheet.addMergedRegion(regionXiaoJi);
							cellXiaoJi.setCellValue("小计");
							endRow = startRow + (i-oldI-1);
							startRow = endRow+2;
							oldI = i;
							
							for (int j = 8; j < 42; j++) {
								Cell createCell = rowXiaoJi.createCell(j);
								createCell.setCellStyle(utils.Style1(workbook));
								createCell.setCellValue("");
							}
						}
						
						
					} else {
						if(is == true) {
							endRow = startRow + (i-oldI-1);
						}
						CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
						Row rowXiaoJi = sheet.createRow(endRow+1);
						Cell cellXiaoJi = rowXiaoJi.createCell(0);
						utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
						sheet.addMergedRegion(regionXiaoJi);
						cellXiaoJi.setCellValue("小计");
						row3 = sheet.createRow(startRow + (i-oldI)+1);
						row3.setHeight((short) 300); // 行高
						
						for (int j = 8; j < 42; j++) {
							Cell createCell = rowXiaoJi.createCell(j);
							createCell.setCellStyle(utils.Style1(workbook));
							createCell.setCellValue("");
						}
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow, endRow, (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style1(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(cornExaminingReport.getpLibraryName());
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(cornExaminingReport.getpLibraryName());
						}
						
					}
					isFirst = false;
					is = false;
					pLibraryName = cornExaminingReport.getpLibraryName();
				}
				Cell cell6 = row3.createCell(1);
				cell6.setCellStyle(utils.Style1(workbook));
				cell6.setCellValue(i+1);
				
				Cell cell7 = row3.createCell(2);
				cell7.setCellStyle(utils.Style1(workbook));
				cell7.setCellValue(cornExaminingReport.getLibraryName());
				
				Cell cell8 = row3.createCell(3);
				cell8.setCellStyle(utils.Style1(workbook));
				cell8.setCellValue("监"+cornExaminingReport.getSampleNum());
				
				Cell cell9 = row3.createCell(4);
				cell9.setCellStyle(utils.Style1(workbook));
				cell9.setCellValue(cornExaminingReport.getSampleNo());
				
				
				Cell cell10 = row3.createCell(5);
				cell10.setCellStyle(utils.Style1(workbook));
				cell10.setCellValue(cornExaminingReport.getPosition());
				
				
				Cell cell11 = row3.createCell(6);
				cell11.setCellStyle(utils.Style1(workbook));
				cell11.setCellValue(cornExaminingReport.getSort());
				
				Cell cell12 = row3.createCell(7);
				cell12.setCellStyle(utils.Style1(workbook));
				cell12.setCellValue(Double.valueOf(cornExaminingReport.getAmount()));
				
				Cell cell13 = row3.createCell(8);
				cell13.setCellStyle(utils.Style1(workbook));
				cell13.setCellValue(cornExaminingReport.getGainTime());
				
				Cell cell14 = row3.createCell(9);
				cell14.setCellStyle(utils.Style1(workbook));
				cell14.setCellValue(sdf.format(cornExaminingReport.getStorageTime()));
				
				Cell cell15 = row3.createCell(10);
				cell15.setCellStyle(utils.Style1(workbook));
				cell15.setCellValue(sdf.format(cornExaminingReport.getCheckApplyTime()));//
				
				Cell cell16 = row3.createCell(11);
				cell16.setCellStyle(utils.Style1(workbook));
				cell16.setCellValue(sdf.format(cornExaminingReport.getAssignMissionTime()));//
				
				Cell cell17 = row3.createCell(12);
				cell17.setCellStyle(utils.Style1(workbook));
				if(cornExaminingReport.getSampleTime()==null){
					cell17.setCellValue("");
				}else{
					cell17.setCellValue(sdf.format(cornExaminingReport.getSampleTime()));
				}
				CellRangeAddress region4 = new CellRangeAddress(row3.getRowNum(), row3.getRowNum(), (short) 13, (short) 16);
				Cell celll = row3.createCell(13);
				utils.setRegionStyle(sheet, region4, utils.Style1(workbook));
				sheet.addMergedRegion(region4);
				celll.setCellStyle(utils.Style1(workbook));
				celll.setCellValue("");
		//		
				Cell cell18 = row3.createCell(17);
				cell18.setCellStyle(utils.Style1(workbook));
				if(cornExaminingReport.getRemark()==null){
					cell18.setCellValue("");
				}else{
					cell18.setCellValue(cornExaminingReport.getRemark());
				}
				
				Cell cell19 = row3.createCell(18);
				cell19.setCellStyle(utils.Style14(workbook));
				cell19.setCellValue(cornExaminingReport.getLength());//长（保留两位小数）
				
				Cell cell20 = row3.createCell(19);
				cell20.setCellStyle(utils.Style14(workbook));
				cell20.setCellValue(cornExaminingReport.getWide());//宽（保留两位小数）
				
				Cell cell21 = row3.createCell(20);
				cell21.setCellStyle(utils.Style14(workbook));
				cell21.setCellValue(cornExaminingReport.getHigh());//高（保留两位小数）
				
				Cell cell22 = row3.createCell(21);
				cell22.setCellStyle(utils.Style16(workbook));
				cell22.setCellValue(cornExaminingReport.getDeductVolume());//扣除体积（整数）
				
				Cell cell23 = row3.createCell(22);
				cell23.setCellStyle(utils.Style15(workbook));
				cell23.setCellValue(cornExaminingReport.getRealVolume());//实际体积（保留一位小数）
				
				Cell cell24 = row3.createCell(23);
				cell24.setCellStyle(utils.Style16(workbook));
				cell24.setCellValue(cornExaminingReport.getRealCapacity());//容重（整数）
				
				Cell cell25 = row3.createCell(24);
				cell25.setCellStyle(utils.Style14(workbook));
				cell25.setCellValue(cornExaminingReport.getCorrectioFactor());//修正系数（保留两位小数）
				
				Cell cell26 = row3.createCell(25);
				cell26.setCellStyle(utils.Style15(workbook));
				cell26.setCellValue(cornExaminingReport.getAveDensity());//平均密度（保留一位小数）
				
				Cell cell27 = row3.createCell(26);
				cell27.setCellStyle(utils.Style16(workbook));
				cell27.setCellValue(cornExaminingReport.getUnQuality());//测量计算数（整数）
				
				Cell cell28 = row3.createCell(27);
				cell28.setCellStyle(utils.Style16(workbook));
				cell28.setCellValue(cornExaminingReport.getGrainQuality());//保管账计算数（整数）
				
				Cell cell29 = row3.createCell(28);
				cell29.setCellStyle(utils.Style15(workbook));
				cell29.setCellValue(cornExaminingReport.getSlip());//差率（保留一位小数）
				
				Cell createCell = row3.createCell(29);
				createCell.setCellStyle(utils.Style27(workbook));
				createCell.setCellValue(cornExaminingReport.getQualityGrade());
				
//				List<CornExaminingReport> cornExaminingReport1 = icornExaminingReportDao.findQualityAcceptance(intId[i]);
				List<TestItem> testItems = testItemService.findResult(intId[i]);
				String jieguopanding1 = null;
				String jieguopanding2 = null;
				Cell createCell2 = row3.createCell(30);
				createCell2.setCellStyle(utils.Style1(workbook));
				Cell cell30 = row3.createCell(31);
				cell30.setCellStyle(utils.Style1(workbook));
				Cell cell31 = row3.createCell(32);
				cell31.setCellStyle(utils.Style1(workbook));
				Cell cell32 = row3.createCell(33);
				cell32.setCellStyle(utils.Style1(workbook));
				Cell cell33 = row3.createCell(34);
				cell33.setCellStyle(utils.Style1(workbook));
				Cell cell34 = row3.createCell(35);
				cell34.setCellStyle(utils.Style1(workbook));
				Cell cell35 = row3.createCell(36);
				cell35.setCellStyle(utils.Style1(workbook));
				Cell cell36 = row3.createCell(37);
				cell36.setCellStyle(utils.Style1(workbook));
				Cell cell37 = row3.createCell(38);
				cell37.setCellStyle(utils.Style1(workbook));
				Cell cell38 = row3.createCell(39);
				cell38.setCellStyle(utils.Style1(workbook));
				Cell cell39 = row3.createCell(40);
				cell39.setCellStyle(utils.Style1(workbook));
				//备注
				Cell cell40 = row3.createCell(41);
				cell40.setCellStyle(utils.Style1(workbook));
				for(TestItem t:testItems) {
					if(t.getTestItem() == 1) {
						createCell2.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 650) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 2) {
						cell30.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 14.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 3) {
						cell31.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 1.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 5) {
						cell32.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 8.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 6) {
						cell33.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 2.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 7) {
						cell34.setCellValue(t.getResult());
						if(t.getResult().equals("正常")) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 10) {
						cell36.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 65) {
  						jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) <= 78){
							jieguopanding2 = "轻度不宜存";
						} else if(Double.parseDouble(t.getResult()) > 78){
							jieguopanding2 = "重度不宜存";
						}
					} else if(t.getTestItem() == 11) {
						cell37.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 70) {
							jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) >= 60){
							jieguopanding2 = "轻度不宜存";
						} else if(Double.parseDouble(t.getResult()) < 60){
							jieguopanding2 = "重度不宜存";
						}
					} else if(t.getTestItem() == 12) {
						cell38.setCellValue(t.getResult());
						if(cornExaminingReport.getJieguopanding2() != null) {
							if(t.getResult().equals("正常") && cornExaminingReport.getJieguopanding2().equals("宜存")) {
								jieguopanding2 = "宜存";
							} else if(t.getResult().equals("正常") && cornExaminingReport.getJieguopanding2().equals("轻度不宜存")){
								jieguopanding2 = "轻度不宜存";
							} else if(t.getResult().equals("基本正常")){
								jieguopanding2 = "重度不宜存";
							}
						}
					}
				}
				cell35.setCellValue(jieguopanding1);
				cell39.setCellValue(jieguopanding2);
			}
			String Sum = "Sum(H10:H" + (id.length + 9) + ")";
			Cell cell2 = row1.createCell(7);
			cell2.setCellStyle(utils.Style1(workbook));
			cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell2.setCellFormula(Sum);
//			 FileOutputStream out = new FileOutputStream("E://玉米检测报表.xls");  
//			 workbook.write(out);
			
			
			//将修改后的文件写出到D:\\excel目录下  
	        //FileOutputStream output = new FileOutputStream("D:\\辅机1.xls");
			String title2 = "小麦验收情况汇总表";
			OutputStream output = response.getOutputStream();
    		response.reset();
    		response.setHeader("Content-disposition", "attachment; filename="+new String( title2.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
    		response.setContentType("application/vnd.ms-excel;charset=utf-8");
    		workbook.write(output);
    		output.close();  
	        //关闭流  
	        output.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 导出小麦
	 * 
	 */
	@Override
	public void ExeclPOI(HttpServletResponse response,String ids,String title) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
        	//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/小麦验收情况汇总表.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sheet = workbook.getSheetAt(0);
			sheet.createFreezePane(10,7,10,7 ); //冻结行列
//			Row row = sheet.createRow(0);
//			Region region = new Region(0, (short) 0, 1, (short) 41);
//			 
//			Cell cell = row.createCell(0);
//			utils.setRegionStyle(sheet, region, utils.Style(workbook));
//			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 1, (short) 41));
//			cell.setCellValue(title);
			 
			Row row1 = sheet.createRow(7);
			CellRangeAddress region1 = new CellRangeAddress(7,  7, (short) 0, (short) 6);
			 
			Cell cell1 = row1.createCell(0);
			utils.setRegionStyle(sheet, region1, utils.Style1(workbook));
			sheet.addMergedRegion(region1);
			cell1.setCellValue("合计");
			for (int i = 8; i < 43; i++) {
				Cell createCell = row1.createCell(i);
				createCell.setCellStyle(utils.Style1(workbook));
				createCell.setCellValue("");
			}
			Cell cell2 = row1.createCell(7);
			cell2.setCellStyle(utils.Style1(workbook));
			cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell2.setCellFormula("SUM(H9)");		
			
			Row row2 = sheet.createRow(8);
			CellRangeAddress region2 = new CellRangeAddress(8, 8, (short) 1, (short) 6);
			
			Cell cell3 = row2.createCell(1);
			utils.setRegionStyle(sheet, region2, utils.Style1(workbook));
			sheet.addMergedRegion(region2);
			cell3.setCellValue("小计");
			for (int i = 8; i < 43; i++) {
				Cell createCell = row2.createCell(i);
				createCell.setCellStyle(utils.Style1(workbook));
				createCell.setCellValue("");
			}
			Cell cell4 = row2.createCell(7);
			cell4.setCellStyle(utils.Style1(workbook));
			cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell4.setCellFormula("SUM(AB9)");
						
			String pLibraryName = null;
			int startRow = 10;
			int endRow = 7;
			//是否合并
			boolean is = false;
			//是否是第一次
			boolean isFirst = false;
			//记录上一次的i值
			int oldI=0;
			String[] id = ids.split(",");
			//string[]转int[]
			int intId[] = new int[id.length];
			for(int i=0;i<id.length;i++) {
				intId[i] = Integer.parseInt(id[i]);
			}
			//冒泡排序
			for(int k=0;k<intId.length;k++) {
				for(int j=k + 1;j<intId.length;j++) {
					if(intId[k] > intId[j]) {
						int temp = intId[k];
						intId[k] = intId[j];
						intId[j] = temp;
					}
				}
			}
			for (int i = 0; i < intId.length; i++) {
				Row row3 =null;
				if(isFirst == true) {
					row3 = sheet.createRow(startRow + (i-oldI));
					row3.setHeight((short) 300); // 行高
				} else {
					row3 = sheet.createRow(startRow + (i-oldI-1));
					row3.setHeight((short) 300); // 行高
				}
				//查询基本情况
				WheatExaminingReport Wobjiect = wheatExaminingReportDao.findBasicSituation(intId[i]);
				if(pLibraryName == null || pLibraryName.equals("")) {
					pLibraryName = Wobjiect.getpLibraryName();
					Cell cellPLibraryName = row3.createCell(0);
					cellPLibraryName.setCellStyle(utils.Style1(workbook));
					cellPLibraryName.setCellValue(Wobjiect.getpLibraryName());
					if(i != intId.length-1) {
						WheatExaminingReport newWobjiect = wheatExaminingReportDao.findBasicSituation(intId[i + 1]);
							if(Wobjiect.getpLibraryName().equals(newWobjiect.getpLibraryName())) {
								startRow = i+9;
								isFirst = true;
							} else {
								endRow = i+9;
								startRow=i+11;
							}
						}
				} else if(pLibraryName.equals(Wobjiect.getpLibraryName())){
					is = true;
					if(i == intId.length -1) {
						row3 = sheet.createRow(startRow + (i-oldI-1));
						row3.setHeight((short) 300); // 行高
						endRow = startRow + (i-oldI-1);
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow, endRow, (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style1(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(pLibraryName);
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(pLibraryName);
						}
					} 
				} else {
					if(i != intId.length-1) {
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow, endRow, (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style1(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(Wobjiect.getpLibraryName());
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(Wobjiect.getpLibraryName());
						}
						WheatExaminingReport newWobjiect = wheatExaminingReportDao.findBasicSituation(intId[i + 1]);
						if(Wobjiect.getpLibraryName().equals(newWobjiect.getpLibraryName())) {
							CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
							Row rowXiaoJi = sheet.createRow(endRow+1);
							Cell cellXiaoJi = rowXiaoJi.createCell(0);
							utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
							sheet.addMergedRegion(regionXiaoJi);
							cellXiaoJi.setCellValue("小计");
							endRow = startRow + (i-oldI-1);
							for (int j = 8; j < 43; j++) {
								Cell createCell = rowXiaoJi.createCell(j);
								createCell.setCellStyle(utils.Style1(workbook));
								createCell.setCellValue("");
							}
						} else {
							CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
							Row rowXiaoJi = sheet.createRow(endRow+1);
							Cell cellXiaoJi = rowXiaoJi.createCell(0);
							utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
							sheet.addMergedRegion(regionXiaoJi);
							cellXiaoJi.setCellValue("小计");
							endRow = startRow + (i-oldI-1);
							startRow = endRow+2;
							oldI = i;
							for (int j = 8; j < 43; j++) {
								Cell createCell = rowXiaoJi.createCell(j);
								createCell.setCellStyle(utils.Style1(workbook));
								createCell.setCellValue("");
							}
						}
						
						
					} else {
						if(is == true) {
							endRow = startRow + (i-oldI-1);
						}
						CellRangeAddress regionXiaoJi = new CellRangeAddress(endRow+1, endRow+1, (short) 0, (short) 6);
						Row rowXiaoJi = sheet.createRow(endRow+1);
						Cell cellXiaoJi = rowXiaoJi.createCell(0);
						utils.setRegionStyle(sheet, regionXiaoJi, utils.Style1(workbook));
						sheet.addMergedRegion(regionXiaoJi);
						cellXiaoJi.setCellValue("小计");
						row3 = sheet.createRow(startRow + (i-oldI)+1);
						row3.setHeight((short) 300); // 行高
						for (int j = 8; j < 43; j++) {
							Cell createCell = rowXiaoJi.createCell(j);
							createCell.setCellStyle(utils.Style1(workbook));
							createCell.setCellValue("");
						}
						if(is == true) {
							//合并直属库单元格
							CellRangeAddress region3 = new CellRangeAddress(startRow, endRow, (short) 0, (short) 0);
							Cell cellPLibraryName = row3.createCell(0);
							utils.setRegionStyle(sheet, region3, utils.Style1(workbook));
							sheet.addMergedRegion(region3);
							cellPLibraryName.setCellStyle(utils.Style2(workbook));
							cellPLibraryName.setCellValue(Wobjiect.getpLibraryName());
						} else {
							Cell cellPLibraryName = row3.createCell(0);
							cellPLibraryName.setCellStyle(utils.Style1(workbook));
							cellPLibraryName.setCellValue(Wobjiect.getpLibraryName());
						}
						
					}
					isFirst = false;
					is = false;
					pLibraryName = Wobjiect.getpLibraryName();
				}
				Cell cellsum = row3.createCell(1);
				cellsum.setCellStyle(utils.Style1(workbook));
				cellsum.setCellValue(i+1);
				
				Cell cell6 = row3.createCell(2);
				cell6.setCellStyle(utils.Style1(workbook));
				cell6.setCellValue(Wobjiect.getLibraryName());
				
				Cell cell7 = row3.createCell(3);
				cell7.setCellStyle(utils.Style1(workbook));
				cell7.setCellValue("监"+Wobjiect.getSampleNum());
				
				Cell cell8 = row3.createCell(4);
				cell8.setCellStyle(utils.Style1(workbook));
				cell8.setCellValue(Wobjiect.getSampleNo());
				
				Cell cell9 = row3.createCell(5);
				cell9.setCellStyle(utils.Style1(workbook));
				cell9.setCellValue(Wobjiect.getPosition());
				
				Cell cell10 = row3.createCell(6);
				cell10.setCellStyle(utils.Style1(workbook));
				cell10.setCellValue(Wobjiect.getSort());
				
				Cell cell11 = row3.createCell(7);
				cell11.setCellStyle(utils.Style1(workbook));
				cell11.setCellValue(Double.valueOf(Wobjiect.getAmount()));
				
				Cell cell12 = row3.createCell(8);
				cell12.setCellStyle(utils.Style1(workbook));
				cell12.setCellValue(Wobjiect.getGainTime());
				
				Cell cell13 = row3.createCell(9);
				cell13.setCellStyle(utils.Style1(workbook));
				cell13.setCellValue(sdf.format(Wobjiect.getStorageTime()));
				
				Cell createCell = row3.createCell(10);
				createCell.setCellStyle(utils.Style1(workbook));
				createCell.setCellValue(sdf.format(Wobjiect.getCheckApplyTime()));
				
				Cell createCell1 = row3.createCell(11);
				createCell1.setCellStyle(utils.Style1(workbook));
				createCell1.setCellValue(sdf.format(Wobjiect.getAssignMissionTime()));
				
				Cell cell14 = row3.createCell(12);
				cell14.setCellStyle(utils.Style1(workbook));
				if(Wobjiect.getSampleTime() == null){
					cell14.setCellValue("");
				}else{
					cell14.setCellValue(sdf.format(Wobjiect.getSampleTime()));
				}
				
				CellRangeAddress region5 = new CellRangeAddress(row3.getRowNum(), row3.getRowNum(), (short) 13, (short) 15);
				Cell createCell2 = row3.createCell(13);
				utils.setRegionStyle(sheet, region5, utils.Style1(workbook));
				sheet.addMergedRegion(region5);
				createCell2.setCellStyle(utils.Style1(workbook));
				createCell2.setCellValue("");
			//
				Cell cell15 = row3.createCell(16);
				cell15.setCellStyle(utils.Style1(workbook));
				if(Wobjiect.getRemark()==null){
					cell15.setCellValue("");
				}else{
					cell15.setCellValue(Wobjiect.getRemark());
				}
				
				Cell cell16 = row3.createCell(17);
				cell16.setCellStyle(utils.Style14(workbook));
				cell16.setCellValue(Wobjiect.getLength());  //长（保留两位小数）
				
				Cell cell17 = row3.createCell(18);
				cell17.setCellStyle(utils.Style14(workbook));  
				cell17.setCellValue(Wobjiect.getWide());    //宽（保留两位小数）
				
				Cell cell18 = row3.createCell(19);
				cell18.setCellStyle(utils.Style14(workbook));
				cell18.setCellValue(Wobjiect.getHigh());    //高（保留两位小数）
				
				Cell cell19 = row3.createCell(20);
				cell19.setCellStyle(utils.Style16(workbook));
				cell19.setCellValue(Wobjiect.getDeductVolume());//扣除体积（整数）
				
				Cell cell20 = row3.createCell(21);
				cell20.setCellStyle(utils.Style15(workbook));
				cell20.setCellValue(Wobjiect.getRealVolume());  //实际体积（保留一位小数）
				
				Cell cell21 = row3.createCell(22);
				cell21.setCellStyle(utils.Style16(workbook));
				cell21.setCellValue(Wobjiect.getRealCapacity()); //容重（整数）
				
				Cell cell22 = row3.createCell(23);
				cell22.setCellStyle(utils.Style14(workbook));
				cell22.setCellValue(Wobjiect.getCorrectioFactor());//修正系数（保留两位小数）
				
				Cell cell23 = row3.createCell(24);
				cell23.setCellStyle(utils.Style15(workbook));
				cell23.setCellValue(Wobjiect.getAveDensity());//平均密度（保留一位小数）
				
				Cell cell24 = row3.createCell(25);
				cell24.setCellStyle(utils.Style16(workbook));
				cell24.setCellValue(Wobjiect.getUnQuality());//测量计算数（整数）
				
				Cell cell25 = row3.createCell(26);
				cell25.setCellStyle(utils.Style16(workbook));
				cell25.setCellValue(Wobjiect.getGrainQuality());//保管账计算数（整数）
				
				Cell cell26 = row3.createCell(27);
				cell26.setCellStyle(utils.Style15(workbook));
				cell26.setCellValue(Wobjiect.getSlip());      //差率（保留一位小数）
				
				Cell cell27 = row3.createCell(28);
				cell27.setCellStyle(utils.Style27(workbook));
				cell27.setCellValue(Wobjiect.getQualityGrade());
				
//				Cell cell39 = row3.createCell(39);
//				cell39.setCellStyle(utils.Style1(workbook));
//				cell39.setCellValue("");
//				
//				Cell cell40 = row3.createCell(40);
//				cell40.setCellStyle(utils.Style1(workbook));
//				cell40.setCellValue("");
//				
//				Cell cell41 = row3.createCell(41);
//				cell41.setCellStyle(utils.Style1(workbook));
//				cell41.setCellValue("");
				
				//查询质量验收情况
//				List<WheatExaminingReport> Wobjiect1 = wheatExaminingReportDao.findQualityAcceptance(intId[i]);
				List<TestItem> testItems = testItemService.findResult(intId[i]);
				String jieguopanding1 = null;
				String jieguopanding2 = null;
				Cell cell28 = row3.createCell(29);
				cell28.setCellStyle(utils.Style1(workbook));
				Cell cell29 = row3.createCell(30);
				cell29.setCellStyle(utils.Style1(workbook));
				Cell cell30 = row3.createCell(31);
				cell30.setCellStyle(utils.Style1(workbook));
				Cell cell31 = row3.createCell(32);
				cell31.setCellStyle(utils.Style1(workbook));
				Cell cell32 = row3.createCell(33);
				cell32.setCellStyle(utils.Style1(workbook));
				Cell cell33 = row3.createCell(34);
				cell33.setCellStyle(utils.Style1(workbook));
				Cell cell34 = row3.createCell(35);
				cell34.setCellStyle(utils.Style1(workbook));
				Cell cell35 = row3.createCell(36);
				cell35.setCellStyle(utils.Style1(workbook));
				Cell cell36 = row3.createCell(37);
				cell36.setCellStyle(utils.Style1(workbook));
				//湿面筋不知道该取那个值
				Cell cell37 = row3.createCell(38);
				cell37.setCellStyle(utils.Style1(workbook));
				cell37.setCellValue("");
				Cell cell38 = row3.createCell(39);
				cell38.setCellStyle(utils.Style1(workbook));
				Cell cell39 = row3.createCell(40);
				cell39.setCellStyle(utils.Style1(workbook));
				Cell cell40 = row3.createCell(41);
				cell40.setCellStyle(utils.Style1(workbook));
				//备注
				Cell cell41 = row3.createCell(42);
				cell41.setCellStyle(utils.Style1(workbook));
				cell41.setCellValue("");
				for(TestItem t:testItems) {
					System.out.println(t.getTestItem());
					if(t.getTestItem() == 1) {
						cell28.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 750) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 2) {
						cell29.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 12.5) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 3) {
						cell30.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 1.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 4) {
						cell31.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 0.5) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 5) {
						cell32.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 8.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 8) {
						cell33.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 60) {
							jieguopanding1 = "达标";
						} else if(Double.parseDouble(t.getResult()) > 45 && Double.parseDouble(t.getResult()) < 60) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 7) {
						cell34.setCellValue(t.getResult());
						if(t.getResult().equals("正常")) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					} else if(t.getTestItem() == 9) {
						cell36.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 180) {
							jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) < 180){
							jieguopanding2 = "轻度不宜存";
						}
					} else if(t.getTestItem() == 11) {
						cell38.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 70) {
							jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) >= 60 && Double.parseDouble(t.getResult()) < 70){
							jieguopanding2 = "轻度不宜存";
						} else if(Double.parseDouble(t.getResult()) < 60){
							jieguopanding2 = "重度不宜存";
						}
					} else if(t.getTestItem() == 12) {
						cell39.setCellValue(t.getResult());
						if(jieguopanding2 != null) {
							if(t.getResult().equals("正常") && jieguopanding2.equals("宜存")) {
								jieguopanding2 = "宜存";
							} else if(t.getResult().equals("正常") && jieguopanding2.equals("轻度不宜存")){
								jieguopanding2 = "轻度不宜存";
							} else if(t.getResult().equals("基本正常")){
								jieguopanding2 = "重度不宜存";
							}
						}
					}
				}
				cell35.setCellValue(jieguopanding1);
				cell40.setCellValue(jieguopanding2);
			}
			String Sum = "Sum(H10:H" + (id.length + 9) + ")";
			Cell cellAA = row1.createCell(7);
			cellAA.setCellStyle(utils.Style1(workbook));
			cellAA.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellAA.setCellFormula(Sum);
//			FileOutputStream out = new FileOutputStream("E://小麦检测报表.xls");  
//			workbook.write(out);
			
			//将修改后的文件写出到D:\\excel目录下  
	        //FileOutputStream output = new FileOutputStream("D:\\辅机1.xls");
	        OutputStream output = response.getOutputStream();
    		response.reset();
    		response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
    		response.setContentType("application/vnd.ms-excel;charset=utf-8");
    		workbook.write(output);
    		output.flush();  
	        //将Excel写出        
//	        workbook.write(output);  
	        //关闭流  
//	        fileInput.close();  
	        output.close();  
			}catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
	
	/*
	 * 导出小麦质量验收情况表
	 * 
	 */
	public  void ExportXMzhiliang(HttpServletResponse response,String ids,String title) {
		try {
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/小麦质量验收情况表.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sh = workbook.getSheetAt(0);  
//			 HSSFRow row = sh.createRow(8);
//			 row.createCell(0).setCellValue("bb");
//			 sh.getRow(9).getCell(2).setCellValue("xx");
//			 sh.getRow(9).getCell(3).setCellValue("xx");
//			 sh.getRow(9).getCell(4).setCellValue("x");
//			 sh.getRow(9).getCell(5).setCellValue("xx");
			String[] idStrs = ids.split(",");
			List<WheatExaminingReport> ws = new ArrayList<WheatExaminingReport>();
			for(int i=0;i<idStrs.length;i++) {
				WheatExaminingReport Wobjiect = wheatExaminingReportDao.findBasicSituation(Integer.parseInt(idStrs[i]));
				ws.add(Wobjiect);
				List<TestItem> testItems = testItemService.findResult(Integer.parseInt(idStrs[i]));
				Row row = sh.createRow(8+i);
				row.setHeight((short) 300); // 行高
				Cell cell1 = row.createCell(0);
				cell1.setCellStyle(utils.Style1(workbook));
				cell1.setCellValue(Wobjiect.getTaskName());
				
				Cell cell2 = row.createCell(1);
				cell2.setCellStyle(utils.Style1(workbook));
				cell2.setCellValue("监" + Wobjiect.getSampleNum());
				
				Cell cell3 = row.createCell(2);
				cell3.setCellStyle(utils.Style27(workbook));
				cell3.setCellValue(Wobjiect.getQualityGrade());
				//检验结果
				Cell cell4 = row.createCell(3);
				cell4.setCellStyle(utils.Style1(workbook));
				Cell cell5 = row.createCell(4);
				cell5.setCellStyle(utils.Style1(workbook));
				Cell cell6 = row.createCell(5);
				cell6.setCellStyle(utils.Style1(workbook));
				Cell cell7 = row.createCell(6);
				cell7.setCellStyle(utils.Style1(workbook));
				Cell cell8 = row.createCell(7);
				cell8.setCellStyle(utils.Style1(workbook));
				Cell cell9 = row.createCell(8);
				cell9.setCellStyle(utils.Style1(workbook));
				Cell cell10 = row.createCell(9);
				cell10.setCellStyle(utils.Style1(workbook));
				//结果判定
				Cell cell11 = row.createCell(10);
				cell11.setCellStyle(utils.Style1(workbook));
				Cell cell12 = row.createCell(11);
				cell12.setCellStyle(utils.Style1(workbook));
				//湿面筋
				Cell cell13 = row.createCell(12);
				cell13.setCellStyle(utils.Style1(workbook));
				Cell cell14 = row.createCell(13);
				cell14.setCellStyle(utils.Style1(workbook));
				Cell cell15 = row.createCell(14);
				cell15.setCellStyle(utils.Style1(workbook));
				//结果判定
				Cell cell16 = row.createCell(15);
				cell16.setCellStyle(utils.Style1(workbook));
				
				String jieguopanding1 = null;
				String jieguopanding2 = null;
				for(TestItem t:testItems) {
					//容重
					if(t.getTestItem() == 1) {
						cell4.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 750) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//水分
					} else if(t.getTestItem() == 2) {
						cell5.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 12.5) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//杂质
					} else if(t.getTestItem() == 3) {
						cell6.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 1.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//矿物质
					} else if(t.getTestItem() == 4) {
						cell7.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 0.5) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//不完善粒
					} else if(t.getTestItem() == 5) {
						cell8.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) <= 8.0) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//硬度指数
					} else if(t.getTestItem() == 8) {
						cell9.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 60) {
							jieguopanding1 = "达标";
						} else if(Double.parseDouble(t.getResult()) > 45 && Double.parseDouble(t.getResult()) < 60) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//色泽气味（质量指标）
					} else if(t.getTestItem() == 7) {
						cell10.setCellValue(t.getResult());
						if(t.getResult().equals("正常")) {
							jieguopanding1 = "达标";
						} else {
							jieguopanding1 = "不达标";
						}
					//面筋吸水量
					} else if(t.getTestItem() == 9) {
						cell12.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 180) {
							jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) < 180){
							jieguopanding2 = "轻度不宜存";
						}
					//品尝评分
					} else if(t.getTestItem() == 11) {
						cell14.setCellValue(t.getResult());
						if(Double.parseDouble(t.getResult()) >= 70) {
							jieguopanding2 = "宜存";
						} else if(Double.parseDouble(t.getResult()) >= 60 && Double.parseDouble(t.getResult()) < 70){
							jieguopanding2 = "轻度不宜存";
						} else if(Double.parseDouble(t.getResult()) < 60){
							jieguopanding2 = "重度不宜存";
						}
					//色泽气味(储存品质指标)
					} else if(t.getTestItem() == 12) {
						cell15.setCellValue(t.getResult());
						if(jieguopanding2 != null) {
							if(t.getResult().equals("正常") && jieguopanding2.equals("宜存")) {
								jieguopanding2 = "宜存";
							} else if(t.getResult().equals("正常") && jieguopanding2.equals("轻度不宜存")){
								jieguopanding2 = "轻度不宜存";
							} else if(t.getResult().equals("基本正常")){
								jieguopanding2 = "重度不宜存";
							}
						}
					}
				}
				cell11.setCellValue(jieguopanding1);
				cell16.setCellValue(jieguopanding2);
			}

//			 FileOutputStream out = new FileOutputStream("E://小麦质量.xls");  
//			 workbook.write(out);
			//将修改后的文件写出到D:\\excel目录下  
	        //FileOutputStream output = new FileOutputStream("D:\\辅机1.xls");
	        OutputStream output = response.getOutputStream();
    		response.reset();
    		response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
    		response.setContentType("application/vnd.ms-excel;charset=utf-8");
    		workbook.write(output);
    		output.flush();  
	        //将Excel写出        
	        workbook.write(output);  
	        //关闭流  
//	        fileInput.close();  
	        output.close();  

		} catch (Exception e) {
			e.printStackTrace();
		}  
   
	}
	
	
	
	/*
	 * 导出玉米质量验收情况表
	 * 
	 */
	public  void ExportYMzhiliang(HttpServletResponse response,String ids,String title) {
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/玉米质量验收情况表.xlsx"));
			POIUtils utils = new POIUtils();
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sh = workbook.getSheetAt(0);  
//			 HSSFRow row = sh.createRow(8);
//			 row.createCell(0).setCellValue("bb");
//			 sh.getRow(9).getCell(2).setCellValue("xx");
//			 sh.getRow(9).getCell(3).setCellValue("xx");
//			 sh.getRow(9).getCell(4).setCellValue("x");
//			 sh.getRow(9).getCell(5).setCellValue("xx");
			 
		
		List<CornExaminingReport> cs = new ArrayList<CornExaminingReport>();
		String[] idStrs = ids.split(",");
		for(int i=0;i<idStrs.length;i++) {
			CornExaminingReport c = icornExaminingReportDao.findBasicSituation(Integer.parseInt(idStrs[i]));
			cs.add(c);
			Row row = sh.createRow(8+i);
			row.setHeight((short) 300); // 行高
			Cell createCell = row.createCell(0);
			createCell.setCellStyle(utils.Style1(workbook));
			createCell.setCellValue(c.getTaskName());
			
			Cell createCell2 = row.createCell(1);
			createCell2.setCellStyle(utils.Style1(workbook));
			createCell2.setCellValue("监" + c.getSampleNum());
			
			Cell createCell3 = row.createCell(2);
			createCell3.setCellStyle(utils.Style27(workbook));
			createCell3.setCellValue(c.getQualityGrade());
			//检测结果
			Cell cell1 = row.createCell(3);
			cell1.setCellStyle(utils.Style1(workbook));
			Cell cell2 = row.createCell(4);
			cell2.setCellStyle(utils.Style1(workbook));
			Cell cell3 = row.createCell(5);
			cell3.setCellStyle(utils.Style1(workbook));
			Cell cell4 = row.createCell(6);
			cell4.setCellStyle(utils.Style1(workbook));
			Cell cell5 = row.createCell(7);
			cell5.setCellStyle(utils.Style1(workbook));
			Cell cell6 = row.createCell(8);
			cell6.setCellStyle(utils.Style1(workbook));
			Cell cell7 = row.createCell(9);
			cell7.setCellStyle(utils.Style1(workbook));
			Cell cell8 = row.createCell(10);
			cell8.setCellStyle(utils.Style1(workbook));
			Cell cell9 = row.createCell(11);
			cell9.setCellStyle(utils.Style1(workbook));
			Cell cell10 = row.createCell(12);
			cell10.setCellStyle(utils.Style1(workbook));
			Cell cell11 = row.createCell(13);
			cell11.setCellStyle(utils.Style1(workbook));
			List<TestItem> testItems = testItemService.findResult(Integer.parseInt(idStrs[i]));
			String jieguopanding1 = null;
			String jieguopanding2 = null;
			for(TestItem t:testItems) {
				//容重
				if(t.getTestItem() == 1) {
					cell1.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) >= 650) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//水分
				} else if(t.getTestItem() == 2) {
					cell2.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) <= 14.0) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//杂质
				} else if(t.getTestItem() == 3) {
					cell3.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) <= 1.0) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//不完善粒
				} else if(t.getTestItem() == 5) {
					cell4.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) <= 8.0) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//生霉粒
				} else if(t.getTestItem() == 6) {
					cell5.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) <= 2.0) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//色泽气味(质量指标)
				} else if(t.getTestItem() == 7) {
					cell6.setCellValue(t.getResult());
					if(t.getResult().equals("正常")) {
						jieguopanding1 = "达标";
					} else {
						jieguopanding1 = "不达标";
					}
				//脂肪酸酯
				} else if(t.getTestItem() == 10) {
					cell8.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) <= 65) {
						jieguopanding2 = "宜存";
					} else if(Double.parseDouble(t.getResult()) <= 78){
						jieguopanding2 = "轻度不宜存";
					} else if(Double.parseDouble(t.getResult()) > 78){
						jieguopanding2 = "重度不宜存";
					}
				//品尝评分
				} else if(t.getTestItem() == 11) {
					cell9.setCellValue(t.getResult());
					if(Double.parseDouble(t.getResult()) >= 70) {
						jieguopanding2 = "宜存";
					} else if(Double.parseDouble(t.getResult()) >= 60){
						jieguopanding2 = "轻度不宜存";
					} else if(Double.parseDouble(t.getResult()) < 60){
						jieguopanding2 = "重度不宜存";
					}
				//色泽气味(储存品质指标)
				} else if(t.getTestItem() == 12) {
					cell10.setCellValue(t.getResult());
					if(c.getJieguopanding2() != null) {
						if(t.getResult().equals("正常") && c.getJieguopanding2().equals("宜存")) {
							jieguopanding2 = "宜存";
						} else if(t.getResult().equals("正常") && c.getJieguopanding2().equals("轻度不宜存")){
							jieguopanding2 = "轻度不宜存";
						} else if(t.getResult().equals("基本正常")){
							jieguopanding2 = "重度不宜存";
						}
					}
				}
			}
			cell7.setCellValue(jieguopanding1);
			cell11.setCellValue(jieguopanding2);
		}
   
//		FileOutputStream out = new FileOutputStream("E://玉米质量.xls");  
//		 workbook.write(out);
		//将修改后的文件写出到D:\\excel目录下  
        //FileOutputStream output = new FileOutputStream("D:\\辅机1.xls");
        OutputStream output = response.getOutputStream();
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		workbook.write(output);
		output.flush();  
        //将Excel写出        
        workbook.write(output);  
        //关闭流  
//        fileInput.close();  
        output.close();  
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	@Override
	public Sample findAllCereals() {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findAllCereals();
	}


	@Override
	public List<Sample> findByCounterId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findByCounterId(params);
	}


	@Override
	public int saveRuku(Sample sample) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).saveRuku(sample);
	}

	@Override
	public Sample findBysampleNumMobile(String sampleNum) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findBysampleNumMobile(sampleNum);
	}
	@Override
	public void saveRukuXinxi(Sample sample) {
		// TODO Auto-generated method stub
		 ((ISampleDao)dao).saveRukuXinxi(sample);
	}
	
//	/*
//	 *导出样品登记薄
//	 */
//	@Override
//	public void ExportRegister(HttpServletResponse response, String storageTime) {
//		  FileInputStream fileInput;
//	      POIUtils utils = new POIUtils();
//			try {
//				fileInput = new FileInputStream("upload/base/中央事权粮油样品登记簿.xls");
//				//poi包下的类读取excel文件  
//				POIFSFileSystem ts = new POIFSFileSystem(fileInput);  
//				// 创建一个webbook，对应一个Excel文件            
//				HSSFWorkbook workbook = new HSSFWorkbook(ts);  
//				//对应Excel文件中的sheet，0代表第一个             
//				HSSFSheet sh = workbook.getSheetAt(0);  
//				List<Sample> sampleReport = sampleDao.findBystorageTime(storageTime);
//				SimpleDateFormat dateSample = new SimpleDateFormat("yyyy-MM-dd");
//				CellStyle style = workbook.createCellStyle();
//				style = utils.StyleSamplePlace(workbook);
//				for (int i = 0; i < sampleReport.size(); i++) {
//					//根据扦样编号查询样品
//					HSSFRow row = sh.createRow(5+i);
//					row.setHeight((short) 600); // 行高
//					Cell createCell = row.createCell(0);
//					createCell.setCellStyle(style);
//					createCell.setCellValue(sampleReport.get(i).getSampleNum()); //检验编号
//					
//					Cell createCell1 = row.createCell(1);
//					createCell1.setCellStyle(style);
//					createCell1.setCellValue(sampleReport.get(i).getSampleWord()); //扦样编号(文字)
//					
//					String sampleNum = sampleReport.get(i).getSampleNum();
//				    Handover handover = handoverDao.findsampleNums(sampleNum);
//				    if(handover == null || "".equals(handover)) {
//				    	Cell createCell2 = row.createCell(2);
//			    		createCell2.setCellStyle(style);
//			    		createCell2.setCellValue("");
//				    } else {
//				    	String checkeds = handover.getCheckeds();
//				    	System.out.println(checkeds);
//				    	String str ="";
//			    		String[] checked = checkeds.split(",");
//			    		for (int j = 0; j < checked.length; j++) {
//			    			if("1".equals(checked[j])){
//			    				str += "容重,";
//			    			}else if("2".equals(checked[j])){
//			    				str += "水分,";
//			    			}else if("3".equals(checked[j])){
//			    				str += "杂质,";
//			    			}else if("4".equals(checked[j])){
//			    				str += "矿物质,";
//			    			}else if("5".equals(checked[j])){
//			    				str += "不完善粒,";
//			    			}else if("6".equals(checked[j])){
//			    				str += "生霉粒,";
//			    			}else if("7".equals(checked[j])){
//			    				str += "色泽气味(质量指标),";
//			    			}else if("8".equals(checked[j])  && sampleReport.get(i).getSort().equals("小麦")){
//			    				str += "硬度指数,";
//			    			}else if("9".equals(checked[j])  && sampleReport.get(i).getSort().equals("小麦")){
//			    				str += "面筋吸水量,";
//			    			}else if("10".equals(checked[j])  && sampleReport.get(i).getSort().equals("玉米")){
//			    				str += "脂肪酸值,";
//			    			}else if("11".equals(checked[j])){
//			    				str += "品尝评分值,";
//			    			}else if("12".equals(checked[j])){
//			    				str += "色泽气味(储存品质指标),";
//			    			}else if("13".equals(checked[j]) && (!sampleReport.get(i).getSort().equals("小麦"))){
//			    				str += "真菌毒素(黄曲霉毒素B1),";
//			    			}else if("14".equals(checked[j])){
//			    				str += "真菌毒素(脱氧雪腐镰刀菌烯醇),";
//			    			}else if("15".equals(checked[j]) && (!sampleReport.get(i).getSort().equals("小麦"))){
//			    				str += "真菌毒素(玉米赤霉烯酮),";
//			    			}else if("16".equals(checked[j])){
//			    				str += "重金属(铅),";
//			    			}else if("17".equals(checked[j])){
//			    				str += "重金属(镉),";
//			    			}else if("18".equals(checked[j])){
//			    				str += "重金属(汞),";
//			    			}else if("19".equals(checked[j])){
//			    				str += "重金属(砷),";
//			    			}
//			    			
//			    		}
//			    		
//			    		String substring = str.substring(0,str.length()-1);
//			    		if(sampleReport.get(i).getSort().equals("小麦")) {
//			    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,色泽气味(质量指标),硬度指数,面筋吸水量,品尝评分值,色泽气味(储存品质指标),真菌毒素(脱氧雪腐镰刀菌烯醇),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "全指标项目");
//			    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,生霉粒,色泽气味(质量指标),硬度指数", "质量指标全项目");
//			    			substring = substring.replace("面筋吸水量,品尝评分值,色泽气味(储存品质指标)", "储存品质指标全项目");
//			    			substring = substring.replace("真菌毒素(脱氧雪腐镰刀菌烯醇),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "食品卫生指标全项目");
//			    		} else {
//			    			substring = substring.replace("容重,水分,杂质,不完善粒,生霉粒,色泽气味(质量指标),脂肪酸值,品尝评分值,色泽气味(储存品质指标),真菌毒素(黄曲霉毒素B1),真菌毒素(脱氧雪腐镰刀菌烯醇),真菌毒素(玉米赤霉烯酮),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "全指标项目");
//			    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,生霉粒,色泽气味(质量指标)", "质量指标全项目");
//			    			substring = substring.replace("脂肪酸值,品尝评分值,色泽气味(储存品质指标)", "储存品质指标全项目");
//			    			substring = substring.replace("真菌毒素(黄曲霉毒素B1),真菌毒素(脱氧雪腐镰刀菌烯醇),真菌毒素(玉米赤霉烯酮),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "食品卫生指标全项目");
//			    		}
//			    		
//			    		
//			    		Cell createCell2 = row.createCell(2);
//			    		createCell2.setCellStyle(style);
//			    		createCell2.setCellValue(substring);//检验项目
//			    		
//			    	}
//					
//					Cell createCell3 = row.createCell(3);
//					createCell3.setCellStyle(style);
//					createCell3.setCellValue(sampleReport.get(i).getAutograph()); //扦样人员
//					
//					Cell createCell4 = row.createCell(4);
//					createCell4.setCellStyle(style);
//					if(sampleReport.get(i).getSampleTime() == null ){
//					 createCell4.setCellValue(""); //扦样时间
//					}else{
//					 createCell4.setCellValue(dateSample.format(sampleReport.get(i).getSampleTime())); //扦样时间
//					}
//					
//					Cell createCell5 = row.createCell(5);
//					createCell5.setCellStyle(style);
//					createCell5.setCellValue(""); 					//工作人员
//					
//					Cell createCell6 = row.createCell(6);
//					createCell6.setCellStyle(style);
//					createCell6.setCellValue(""); 					//工作时间
//					
//					WarehouseCounterPlace w = iWarehouseCounterPlaceService.findDepotAndCounterByPlaceId(sampleReport.get(i).getPlaceId());
//					if(w == null ){
//						Cell createCell7 = row.createCell(7);
//						createCell7.setCellStyle(style);
//						createCell7.setCellValue(""); 	//存放位置
//					}else{
//						String placeName = w.getDepot()+ "--" +w.getCounter()+ "--" +w.getPlace();
//						Cell createCell7 = row.createCell(7);
//						createCell7.setCellStyle(style);
//						createCell7.setCellValue(placeName); 	//存放位置
//					}
//					
//					Cell createCell8 = row.createCell(8);
//					createCell8.setCellStyle(style);
//					createCell8.setCellValue(sampleReport.get(i).getRemark()); 	//备注
//				}
//				String title = "中央事权粮油样品登记薄";
//				OutputStream output = response.getOutputStream();
//				response.reset();
//				response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xls");
//				response.setContentType("application/vnd.ms-excel;charset=utf-8");
//				workbook.write(output);
//				output.flush();  
//		        //将Excel写出        
////		        workbook.write(output);  
//		        //关闭流  
//		        fileInput.close();  
//		        output.close();  
//			} catch (Exception e) {
//				e.printStackTrace();
//			}  
//		
//	}
	/*
	 *导出样品登记薄
	 */
	@Override
	public void ExportRegister(HttpServletResponse response, String storageTime) {
		XSSFWorkbook xssfWorkbook = null;
		SXSSFWorkbook workbook = null;
		Cell createCell = null;
		try {
			//输入模板文件
			xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/中央事权粮油样品登记簿.xlsx"));
			workbook = new SXSSFWorkbook(xssfWorkbook, 100);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sh = workbook.getSheetAt(0);  
			List<Sample> sampleReport = sampleDao.findBystorageTime(storageTime);
			System.out.println(sampleReport.size());
			SimpleDateFormat dateSample = new SimpleDateFormat("yyyy-MM-dd");
			CellStyle style = workbook.createCellStyle();
			style = utils.StyleSamplePlace(workbook);
			for (int i = 0; i < sampleReport.size(); i++) {
				Row row = sh.createRow(5+i);
				row.setHeight((short) 600); // 行高
				
				createCell = row.createCell(0);
				createCell.setCellStyle(style);
				createCell.setCellValue(sampleReport.get(i).getSampleNum()); //检验编号
				
				createCell = row.createCell(1);
				createCell.setCellStyle(style);
				createCell.setCellValue(sampleReport.get(i).getSampleWord()); //扦样编号(文字)
				
			    if(sampleReport.get(i).getCheckeds() == null || "".equals(sampleReport.get(i).getCheckeds())) {
			    	Cell createCell2 = row.createCell(2);
		    		createCell2.setCellStyle(style);
		    		createCell2.setCellValue("");
			    } else {
			    	String checkeds = sampleReport.get(i).getCheckeds();
			    	String str ="";
		    		String[] checked = checkeds.split(",");
		    		for (int j = 0; j < checked.length; j++) {
		    			if("1".equals(checked[j])){
		    				str += "容重,";
		    			}else if("2".equals(checked[j])){
		    				str += "水分,";
		    			}else if("3".equals(checked[j])){
		    				str += "杂质,";
		    			}else if("4".equals(checked[j])){
		    				str += "矿物质,";
		    			}else if("5".equals(checked[j])){
		    				str += "不完善粒,";
		    			}else if("6".equals(checked[j])){
		    				str += "生霉粒,";
		    			}else if("7".equals(checked[j])){
		    				str += "色泽气味(质量指标),";
		    			}else if("8".equals(checked[j])  && sampleReport.get(i).getSort().equals("小麦")){
		    				str += "硬度指数,";
		    			}else if("9".equals(checked[j])  && sampleReport.get(i).getSort().equals("小麦")){
		    				str += "面筋吸水量,";
		    			}else if("10".equals(checked[j])  && sampleReport.get(i).getSort().equals("玉米")){
		    				str += "脂肪酸值,";
		    			}else if("11".equals(checked[j])){
		    				str += "品尝评分值,";
		    			}else if("12".equals(checked[j])){
		    				str += "色泽气味(储存品质指标),";
		    			}else if("13".equals(checked[j]) && (!sampleReport.get(i).getSort().equals("小麦"))){
		    				str += "真菌毒素(黄曲霉毒素B1),";
		    			}else if("14".equals(checked[j])){
		    				str += "真菌毒素(脱氧雪腐镰刀菌烯醇),";
		    			}else if("15".equals(checked[j]) && (!sampleReport.get(i).getSort().equals("小麦"))){
		    				str += "真菌毒素(玉米赤霉烯酮),";
		    			}else if("16".equals(checked[j])){
		    				str += "重金属(铅),";
		    			}else if("17".equals(checked[j])){
		    				str += "重金属(镉),";
		    			}else if("18".equals(checked[j])){
		    				str += "重金属(汞),";
		    			}else if("19".equals(checked[j])){
		    				str += "重金属(砷),";
		    			}
		    			
		    		}
		    		
		    		String substring = str.substring(0,str.length()-1);
		    		if(sampleReport.get(i).getSort().equals("小麦")) {
		    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,色泽气味(质量指标),硬度指数,面筋吸水量,品尝评分值,色泽气味(储存品质指标),真菌毒素(脱氧雪腐镰刀菌烯醇),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "全指标项目");
		    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,生霉粒,色泽气味(质量指标),硬度指数", "质量指标全项目");
		    			substring = substring.replace("面筋吸水量,品尝评分值,色泽气味(储存品质指标)", "储存品质指标全项目");
		    			substring = substring.replace("真菌毒素(脱氧雪腐镰刀菌烯醇),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "食品卫生指标全项目");
		    		} else {
		    			substring = substring.replace("容重,水分,杂质,不完善粒,生霉粒,色泽气味(质量指标),脂肪酸值,品尝评分值,色泽气味(储存品质指标),真菌毒素(黄曲霉毒素B1),真菌毒素(脱氧雪腐镰刀菌烯醇),真菌毒素(玉米赤霉烯酮),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "全指标项目");
		    			substring = substring.replace("容重,水分,杂质,矿物质,不完善粒,生霉粒,色泽气味(质量指标)", "质量指标全项目");
		    			substring = substring.replace("脂肪酸值,品尝评分值,色泽气味(储存品质指标)", "储存品质指标全项目");
		    			substring = substring.replace("真菌毒素(黄曲霉毒素B1),真菌毒素(脱氧雪腐镰刀菌烯醇),真菌毒素(玉米赤霉烯酮),重金属(铅),重金属(镉),重金属(汞),重金属(砷)", "食品卫生指标全项目");
		    		}
		    		
		    		
		    		createCell = row.createCell(2);
		    		createCell.setCellStyle(style);
		    		createCell.setCellValue(substring);//检验项目
		    		
		    	}
				
			    createCell = row.createCell(3);
			    createCell.setCellStyle(style);
			    createCell.setCellValue(sampleReport.get(i).getAutograph()); //扦样人员
				
			    createCell = row.createCell(4);
				createCell.setCellStyle(style);
				if(sampleReport.get(i).getSampleTime() == null ){
				 createCell.setCellValue(""); //扦样时间
				}else{
				 createCell.setCellValue(dateSample.format(sampleReport.get(i).getSampleTime())); //扦样时间
				}
				
				createCell = row.createCell(5);
				createCell.setCellStyle(style);
				createCell.setCellValue(""); 					//工作人员
				
				createCell = row.createCell(6);
				createCell.setCellStyle(style);
				createCell.setCellValue(""); 					//工作时间
				
				String placeName = sampleReport.get(i).getDepot()+ "--" +sampleReport.get(i).getCounter()+ "--" +sampleReport.get(i).getPlace();
				createCell = row.createCell(7);
				createCell.setCellStyle(style);
				createCell.setCellValue(placeName); 	//存放位置
				
				createCell = row.createCell(8);
				createCell.setCellStyle(style);
				createCell.setCellValue(sampleReport.get(i).getRemark()); 	//备注
				
				if(i % 100 == 0) {
					((SXSSFSheet) sh).flushRows();
				}
			}
			String title = "中央事权粮油样品登记薄";
			OutputStream out = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        out.flush();
			workbook.write(out);
	        out.close();
	        workbook.dispose();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateDispose(Sample sample) {
		// TODO Auto-generated method stub
		((ISampleDao)dao).updateDispose(sample);
	}

	@Override
	public List<Sample> findBystorageTime(String storageTime) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findBystorageTime(storageTime);
	}

	@Override
	public PagerModel<Sample> findTemporaryPaginated(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findTemporaryPaginated(params);
	}
	/**
	 * 临时扦样列表（导出按brainTime）
	 */
	@Override
	public List<Sample> findAllExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findAllExport(map);
	}

	@Override
	public List<Sample> findByTaskId(int taskId) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findByTaskId(taskId);
	}

	@Override
	public List<Sample> findByDetectionState(int detectionState) {
		// TODO Auto-generated method stub
		return ((ISampleDao)dao).findByDetectionState(detectionState);
	}

	
}
