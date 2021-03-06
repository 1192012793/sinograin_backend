package com.toughguy.sinograin.service.barn.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.util.CellRangeAddress;
import com.toughguy.sinograin.model.barn.ReturnSingle;
import com.toughguy.sinograin.model.barn.Sample;
import com.toughguy.sinograin.model.barn.TestItem;
import com.toughguy.sinograin.pagination.PagerModel;
import com.toughguy.sinograin.persist.barn.prototype.ITestItemDao;
import com.toughguy.sinograin.service.barn.prototype.IReturnSingleService;
import com.toughguy.sinograin.service.barn.prototype.ISampleService;
import com.toughguy.sinograin.service.barn.prototype.ITestItemService;
import com.toughguy.sinograin.service.impl.GenericServiceImpl;
import com.toughguy.sinograin.util.POIUtils;


@Service
public class TestItemServiceImpl extends GenericServiceImpl<TestItem, Integer> implements ITestItemService {

	@Autowired
	private ISampleService sampleService;
	@Autowired
	private IReturnSingleService returnSingleService;
	@Override
	public List<TestItem> findResult(int sampleId) {
		// TODO Auto-generated method stub
		return ((ITestItemDao)dao).findResult(sampleId);
	}

	@Override
	public List<Sample> getSampleBySortAndTestItem(TestItem testItem) {
		// TODO Auto-generated method stub
		return ((ITestItemDao)dao).getSampleBySortAndTestItem(testItem);
	}
	@Override
	public List<Sample> getAllSampleBySortAndTestItem(TestItem testItem) {
		// TODO Auto-generated method stub
		return ((ITestItemDao)dao).getAllSampleBySortAndTestItem(testItem);
	}
	//样品确认单导出
  	@Override
  	public void expotexpotTestItem(HttpServletResponse response,int sampleId){
  		// TODO Auto-generated method stub
  		try {
  		//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/中央事权粮油样品登记簿.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个             
			Sheet sheet = workbook.getSheetAt(0);  
			
			//第一行数据内容
			Row row = sheet.createRow(0);
			CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) 8);
			Cell createCell = row.createCell(0);
			utils.setRegionStyle(sheet, region1, utils.Style6(workbook));
			sheet.addMergedRegion(region1);
			createCell.setCellValue("样品确认单");//名字要居中
			
			//第二行数据内容
			Row row1 = sheet.createRow(1);
			Cell createCell1 = row1.createCell(0);
			createCell1.setCellStyle(utils.Style6(workbook));
			createCell1.setCellValue("检验编号");
			
			Cell createCel = row1.createCell(1);
			CellRangeAddress region2 = new CellRangeAddress(1, 1, (short) 1, (short) 8);//要居左
			utils.setRegionStyle(sheet, region2, utils.Style8(workbook));
			sheet.addMergedRegion(region2);
			
			//根据样品id查询全部检验项目和检验编号
			Sample sample = sampleService.find(sampleId);
			//将样品编号放入excel中
			createCel.setCellValue(sample.getSampleNum());
			//将检验项目转为文字
			String checkedsNum1 = sample.getCheckeds();
			String[] checkedsNum2 = checkedsNum1.split(",");
			String checkedsWord1 = "";
			for(int i=0;i<checkedsNum2.length;i++) {
				if("1".equals(checkedsNum2[i])){
					checkedsWord1 += "容重,";
				}else if("2".equals(checkedsNum2[i])){
					checkedsWord1 += "水分,";
				}else if("3".equals(checkedsNum2[i])){
					checkedsWord1 += "杂质,";
				}else if("4".equals(checkedsNum2[i])){
					checkedsWord1 += "矿物质,";
				}else if("5".equals(checkedsNum2[i])){
					checkedsWord1 += "不完善粒,";
				}else if("6".equals(checkedsNum2[i])){
					checkedsWord1 += "生霉粒,";
				}else if("7".equals(checkedsNum2[i])){
					checkedsWord1 += "色泽气味(质量指标),";
				}else if("8".equals(checkedsNum2[i])){
					checkedsWord1 += "硬度指数,";
				}else if("9".equals(checkedsNum2[i])){
					checkedsWord1 += "面筋吸水量,";
				}else if("10".equals(checkedsNum2[i])){
					checkedsWord1 += "脂肪酸值,";
				}else if("11".equals(checkedsNum2[i])){
					checkedsWord1 += "品尝评分值,";
				}else if("12".equals(checkedsNum2[i])){
					checkedsWord1 += "色泽气味(储存品质指标),";
				}else if("13".equals(checkedsNum2[i])){
					checkedsWord1 += "真菌毒素(黄曲霉毒素B1),";
				}else if("14".equals(checkedsNum2[i])){
					checkedsWord1 += "真菌毒素(脱氧雪腐镰刀菌烯醇),";
				}else if("15".equals(checkedsNum2[i])){
					checkedsWord1 += "真菌毒素(玉米赤霉烯酮),";
				}else if("16".equals(checkedsNum2[i])){
					checkedsWord1 += "重金属(铅),";
				}else if("17".equals(checkedsNum2[i])){
					checkedsWord1 += "重金属(镉),";
				}else if("18".equals(checkedsNum2[i])){
					checkedsWord1 += "重金属(汞),";
				}else if("19".equals(checkedsNum2[i])){
					checkedsWord1 += "重金属(砷),";
				}
			}
			String checkedsWord2 = checkedsWord1.substring(0, checkedsWord1.length()-1);
			String[] checkedsWord3 = checkedsWord2.split(",");
			int ceil = (int)Math.ceil(checkedsWord3.length/3.0);
			//根据样品ID查询检测结果
			List<TestItem> result = ((ITestItemDao)dao).findResult(sampleId);
			//循环导出excel
			Row row3 = null;
			for(int b=0; b<ceil; b++) {
				row3 = sheet.createRow(3+b);
				Cell createCell2 = row3.createCell(0);
				createCell2.setCellStyle(utils.Style6(workbook));
				if((b)*3 < checkedsWord3.length){
					createCell2.setCellValue(checkedsWord3[(b)*3]); //检验项目
					Boolean isValue = false;
					for(int i=1;i<=result.size();i++) {
						int testItem = result.get(i-1).getTestItem();
						String testItem4 = "";
						if(1 == testItem) {
  							testItem4 += "容重";
  						}else if(2 == testItem){
  							testItem4 += "水分";
  						}else if(3 == testItem){
  							testItem4 += "杂质";
  						}else if(4 == testItem){
  							testItem4 += "矿物质";
  						}else if(5 == testItem){
  							testItem4 += "不完善粒";
  						}else if(6 == testItem){
  							testItem4 += "生霉粒";
  						}else if(7 == testItem){
  							testItem4 += "色泽气味(质量指标)";
  						}else if(8 == testItem){
  							testItem4 += "硬度指数";
  						}else if(9 == testItem){
  							testItem4 += "面筋吸水量";
  						}else if(10 == testItem){
  							testItem4 += "脂肪酸值";
  						}else if(11 == testItem){
  							testItem4 += "品尝评分值";
  						}else if(12 == testItem){
  							testItem4 += "色泽气味(储存品质指标)";
  						}else if(13 == testItem){
  							testItem4 += "真菌毒素(黄曲霉毒素B1)";
  						}else if(14 == testItem){
  							testItem4 += "真菌毒素(脱氧雪腐镰刀菌烯醇)";
  						}else if(15 == testItem){
  							testItem4 += "真菌毒素(玉米赤霉烯酮)";
  						}else if(16 == testItem){
  							testItem4 += "重金属(铅)";
  						}else if(17 == testItem){
  							testItem4 += "重金属(镉)";
  						}else if(18 == testItem){
  							testItem4 += "重金属(汞)";
  						}else if(19 == testItem){
  							testItem4 += "重金属(砷)";
  						}
						if(checkedsWord3[(b)*3].equals(testItem4)) {
							System.out.println(result.get(i-1).getResult());
							Cell createCell3 = row3.createCell(1);
							createCell3.setCellStyle(utils.Style6(workbook));
							createCell3.setCellValue(result.get(i-1).getResult());
							Cell createCell4 = row3.createCell(2);
							createCell4.setCellStyle(utils.Style6(workbook));
							createCell4.setCellValue(result.get(i-1).getPrincipal());
							isValue = true;
						}else {
							if(isValue == false) {
								Cell createCell3 = row3.createCell(1);
								createCell3.setCellStyle(utils.Style6(workbook));
								createCell3.setCellValue("");
								Cell createCell4 = row3.createCell(2);
								createCell4.setCellStyle(utils.Style6(workbook));
								createCell4.setCellValue("");
							} else {
								
							}
						}
					}

				}else {
					createCell2.setCellValue("");
				}
				Cell createCell6 = row3.createCell(3);
				createCell6.setCellStyle(utils.Style6(workbook));
				if(1+(b)*3 < checkedsWord3.length){
					createCell6.setCellValue(checkedsWord3[1+(b)*3]);  //检验项目
					boolean isValue = false;
					for(int i=1;i<=result.size();i++) {
						int testItem = result.get(i-1).getTestItem();
						String testItem4 = "";
						if(1 == testItem) {
  							testItem4 += "容重";
  						}else if(2 == testItem){
  							testItem4 += "水分";
  						}else if(3 == testItem){
  							testItem4 += "杂质";
  						}else if(4 == testItem){
  							testItem4 += "矿物质";
  						}else if(5 == testItem){
  							testItem4 += "不完善粒";
  						}else if(6 == testItem){
  							testItem4 += "生霉粒";
  						}else if(7 == testItem){
  							testItem4 += "色泽气味(质量指标)";
  						}else if(8 == testItem){
  							testItem4 += "硬度指数";
  						}else if(9 == testItem){
  							testItem4 += "面筋吸水量";
  						}else if(10 == testItem){
  							testItem4 += "脂肪酸值";
  						}else if(11 == testItem){
  							testItem4 += "品尝评分值";
  						}else if(12 == testItem){
  							testItem4 += "色泽气味(储存品质指标)";
  						}else if(13 == testItem){
  							testItem4 += "真菌毒素(黄曲霉毒素B1)";
  						}else if(14 == testItem){
  							testItem4 += "真菌毒素(脱氧雪腐)";
  						}else if(15 == testItem){
  							testItem4 += "真菌毒素(镰刀菌烯醇玉米赤霉烯酮)";
  						}else if(16 == testItem){
  							testItem4 += "重金属(铅)";
  						}else if(17 == testItem){
  							testItem4 += "重金属(镉)";
  						}else if(18 == testItem){
  							testItem4 += "重金属(汞)";
  						}else if(19 == testItem){
  							testItem4 += "重金属(砷)";
  						}
						if(checkedsWord3[1+(b)*3].equals(testItem4)) {
							Cell createCell3 = row3.createCell(4);
							createCell3.setCellStyle(utils.Style6(workbook));
							createCell3.setCellValue(result.get(i-1).getResult());
							Cell createCell4 = row3.createCell(5);
							createCell4.setCellStyle(utils.Style6(workbook));
							createCell4.setCellValue(result.get(i-1).getPrincipal());
							isValue = true;
						} else {
							if(isValue == false) {
								Cell createCell3 = row3.createCell(4);
								createCell3.setCellStyle(utils.Style6(workbook));
								createCell3.setCellValue("");
								Cell createCell4 = row3.createCell(5);
								createCell4.setCellStyle(utils.Style6(workbook));
								createCell4.setCellValue("");
							} else {
								
							}
						}
					}
				}else{
					createCell6.setCellValue("");
				}
				Cell createCell9 = row3.createCell(6);
				createCell9.setCellStyle(utils.Style6(workbook));
				if(2+(b)*3 < checkedsWord3.length){
					createCell9.setCellValue(checkedsWord3[2+(b)*3]);  //检验项目
					boolean isValue = false;
					for(int i=1;i<=result.size();i++) {
						int testItem = result.get(i-1).getTestItem();
						String testItem4 = "";
						if(1 == testItem) {
  							testItem4 += "容重";
  						}else if(2 == testItem){
  							testItem4 += "水分";
  						}else if(3 == testItem){
  							testItem4 += "杂质";
  						}else if(4 == testItem){
  							testItem4 += "矿物质";
  						}else if(5 == testItem){
  							testItem4 += "不完善粒";
  						}else if(6 == testItem){
  							testItem4 += "生霉粒";
  						}else if(7 == testItem){
  							testItem4 += "色泽气味(质量指标)";
  						}else if(8 == testItem){
  							testItem4 += "硬度指数";
  						}else if(9 == testItem){
  							testItem4 += "面筋吸水量";
  						}else if(10 == testItem){
  							testItem4 += "脂肪酸值";
  						}else if(11 == testItem){
  							testItem4 += "品尝评分值";
  						}else if(12 == testItem){
  							testItem4 += "色泽气味(储存品质指标)";
  						}else if(13 == testItem){
  							testItem4 += "真菌毒素(黄曲霉毒素B1)";
  						}else if(14 == testItem){
  							testItem4 += "真菌毒素(脱氧雪腐镰刀菌烯醇)";
  						}else if(15 == testItem){
  							testItem4 += "真菌毒素(玉米赤霉烯酮)";
  						}else if(16 == testItem){
  							testItem4 += "重金属(铅)";
  						}else if(17 == testItem){
  							testItem4 += "重金属(镉)";
  						}else if(18 == testItem){
  							testItem4 += "重金属(汞)";
  						}else if(19 == testItem){
  							testItem4 += "重金属(砷)";
  						}
						if(checkedsWord3[2+(b)*3].equals(testItem4)) {
							Cell createCell3 = row3.createCell(7);
							createCell3.setCellStyle(utils.Style6(workbook));
							createCell3.setCellValue(result.get(i-1).getResult());
							Cell createCell4 = row3.createCell(8);
							createCell4.setCellStyle(utils.Style6(workbook));
							createCell4.setCellValue(result.get(i-1).getPrincipal());
							isValue = true;
						} else {
							if(isValue == false) {
								Cell createCell3 = row3.createCell(7);
								createCell3.setCellStyle(utils.Style6(workbook));
								createCell3.setCellValue("");
								Cell createCell4 = row3.createCell(8);
								createCell4.setCellStyle(utils.Style6(workbook));
								createCell4.setCellValue("");
							} else {
								
							}
						}
					}
				}else{
					createCell9.setCellValue("");
				}
			}
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+new Date().getTime()+".xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			workbook.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PagerModel<TestItem> findTestItem(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		PagerModel<TestItem> pts = dao.findPaginated(params);
		for(TestItem pt:pts.getData()) {
			Sample sample = sampleService.find(pt.getSampleId());
			ReturnSingle rs = returnSingleService.findBySampleId(pt.getSampleId());
			
			//判断样品状态是否为已检测
			if(sample.getDetectionState() == 1) {
				if("".equals(rs) || rs == null) {
					pt.setState(-1);
				} else if(rs.getReturnState() == 1){
					pt.setState(2);
				} else {
					pt.setState(-1);
				}
			} else if(sample.getDetectionState() == 2) {
				//判断是否有归还单
				if("".equals(rs) || rs == null) {
					pt.setState(1);
				} else if(rs.getReturnState() == 1){
					pt.setState(2);
				} else {
					pt.setState(1);
				}
			}
		}
		return pts;
	}
}



