package com.toughguy.sinograin.controller.barn;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toughguy.sinograin.model.barn.SafetyReport;
import com.toughguy.sinograin.pagination.PagerModel;
import com.toughguy.sinograin.service.barn.prototype.ISafetyReportService;
import com.toughguy.sinograin.util.BackupUtil;
import com.toughguy.sinograin.util.Base64Transformation;
import com.toughguy.sinograin.util.JsonUtil;
import com.toughguy.sinograin.util.UploadUtil;

@Controller
@RequestMapping("/safetyReport")
public class SafetyReportController {

	@Autowired
	private ISafetyReportService safeService;
	
	@ResponseBody
	@RequestMapping("/getAll")
	//@RequiresPermissions("safety:all")
	public List<SafetyReport> getAll(){
		return safeService.findAll();
	}
	
	@ResponseBody
	@RequestMapping("/get")
	@RequiresPermissions("safety:getById")
	public SafetyReport get(int id){
		return safeService.find(id);
	}
	@ResponseBody
	@RequestMapping("/remove")
	//@RequiresPermissions("safety:remove")
	public String remove(int id){
		try {		
			safeService.delete(id);
		return "{ \"success\" : true }";
		} catch (Exception e) {
		e.printStackTrace();
		return "{ \"success\" : false }";
		}
	}
	@ResponseBody
	@RequestMapping(value = "/edit")
	//@RequiresPermissions("safety:edit")
	public String edit(SafetyReport report) {
		try {		
				safeService.update(report);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false }";
		}
	}
	@ResponseBody
	@RequestMapping(value = "/save")
	@RequiresPermissions("safety:save")
	public String saveSample(String params) {	
		
		List<SafetyReport> reportList = JsonUtil.jsonToList(params, SafetyReport.class); 
		try {
			for(SafetyReport report: reportList){
				report.setIsDeal(-1);
			safeService.save(report);
			}
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false }";
		}		
	}
	@ResponseBody
	@RequestMapping(value = "/uploadPic")
	//@RequiresPermissions("safety:upload")
	public String uploadPicture(MultipartFile pictureFile){
		if(UploadUtil.isPicture(pictureFile.getOriginalFilename())){
			try {
			 String path = UploadUtil.uploadPicture(pictureFile);
			 return "{ \"success\" : true ,\"msg\" :\""+ path +"\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{ \"success\" : false ,\"msg\" : \"上传失败\"}";
			}
		}else{
			return "{ \"success\" : false , \"msg\" : \"请上传正确图片格式的图片\"}";
		}	
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadBase64")
	//@RequiresPermissions("safety:uploadBase64")
	public String uploadPicture(String pictureFile){
		// 重命名文件
		String path = BackupUtil.rename("jpg");
			try {
			 String absolutePath = UploadUtil.getAbsolutePath("picture");
			// 先上传文件（绝对路径）
				File f = new File(absolutePath);  //无路径则创建 
				if(!f.exists()){
					f.mkdirs();
				}
			 Base64Transformation.base64StrToImage(pictureFile, absolutePath + "/" + path);
			 return "{ \"success\" : true ,\"msg\" :\""+ path +"\"}";
			} catch (Exception e) {
				e.printStackTrace();
				return "{ \"success\" : false ,\"msg\" : \"上传失败\"}";
		}
	}		
	
	@ResponseBody
	@RequestMapping(value = "/export/{params}") 
	@RequiresPermissions("safety:export")
	public String Export(HttpServletResponse response,@PathVariable String params) {
		try {			
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(params)) {
				// 参数处理
				map = om.readValue(params, new TypeReference<Map<String, Object>>() {});
			}
			List<SafetyReport> ss = safeService.findAll(map);
			List<Integer> idss = new ArrayList<Integer>();
			for(int i = 0;i<ss.size();i++) {
				idss.add(ss.get(i).getId());
			}
			int[] ids = new int[idss.size()];
			for(int i=0;i<idss.size();i++) {
				ids[i] = idss.get(i);
			}
			safeService.ExportSafetyReport(response,ids);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false }";
		}
	}

		
	
	@ResponseBody
	@RequestMapping(value = "/data")
	@RequiresPermissions("safety:list")
	public String data(String params) {
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(params)) {
				// 参数处理
				map = om.readValue(params, new TypeReference<Map<String, Object>>() {});
			}
			PagerModel<SafetyReport> pg = safeService.findPaginated(map);	
			// 序列化查询结果为JSON
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", pg.getTotal());
			result.put("rows", pg.getData());
			return om.writeValueAsString(result);
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"total\" : 0, \"rows\" : [] }";
		}
	}	
}
