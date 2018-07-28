package com.toughguy.sinograin.controller.barn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toughguy.sinograin.dto.XMPresentation;
import com.toughguy.sinograin.dto.YMPresentation;
import com.toughguy.sinograin.util.XwpfTUtil;

/**
 * 导出word 检验报告
 * @author YAO
 *
 */
@Controller
@RequestMapping("/export")
public class ExportWord {

	
	/**
     * POI小麦检验报告导出word
     * @throws Exception
     */
     @RequestMapping(value="exportWordXM")
     public void exportWordXM(HttpServletResponse response,XMPresentation presentation) throws Exception {  
            Map<String, Object> params = new HashMap<String, Object>();  
            params.put("${bianhao_1}", presentation.getBianhao_1());
            params.put("${bianhao_2}", presentation.getBianhao_2());
            params.put("${sort}", presentation.getSort());  
            params.put("${sampleNum}",presentation.getSampleNum());  
            params.put("${cunchudanwei}", presentation.getCunchudanwei());  
            params.put("${counter}",  presentation.getCounter());  
            params.put("${shengchanniandu}",  presentation.getShengchanniandu());  
            params.put("${quality}",  presentation.getQuality());  
            params.put("${daibiaoshuliang}",  presentation.getDaibiaoshuliang());  
            params.put("${sampleCount}",  presentation.getSampleCount());  
            params.put("${yangpinmiaoshu}",  presentation.getYangpinmiaoshu());  
            params.put("${yangpinzhuangtai}", presentation.getYangpinzhuangtai());  
            params.put("${qianyangren}",  presentation.getQianyangren());  
            params.put("${sampleTime}",  presentation.getSampleTime());  
            params.put("${qianyangyiju}",  presentation.getQianyangyiju());  
            params.put("${jianyanmudi}",  presentation.getJianyanmudi());  
            params.put("${jianyanshijian}",  presentation.getJianyanshijian());  
            params.put("${jianyanyiju}",  presentation.getJianyanyiju()); 
            params.put("${jianyanxiangmu}",  presentation.getJianyanxiangmu());  
            params.put("${Jianyanjielun}",  presentation.getJianyanjielun());  
            params.put("${beizhu}",  presentation.getBeizhu());  
            params.put("${rongzhongbiaozhunyaoqiu}",  presentation.getRongzhongbiaozhunyaoqiu());  
            params.put("${rongzhongjiancejieguo}",  presentation.getRongzhongjiancejieguo());  
            params.put("${rongzhongdanxiangpingjia}",  presentation.getRongzhongdanxiangpingjia());  
            params.put("${buwanshanlibiaozhunyaoqiu}",  presentation.getBuwanshanlibiaozhunyaoqiu());  
            params.put("${buwanshanlijiancejieguo}",  presentation.getBuwanshanlijiancejieguo());  
            params.put("${buwanshanlidanxiangpingjia}",  presentation.getBuwanshanlidanxiangpingjia());  
            params.put("${zazhizongliangbiaozhunyaoqiu}",  presentation.getZazhizongliangbiaozhunyaoqiu());  
            params.put("${zazhizongliangjiancejieguo}",  presentation.getZazhizongliangjiancejieguo());  
            params.put("${zazhizongliangdanxiangpingjia}",  presentation.getZazhizongliangdanxiangpingjia());  
            params.put("${zazhikuangwuzhibiaozhunyaoqiu}",  presentation.getZazhikuangwuzhibiaozhunyaoqiu());  
            params.put("${zazhikuangwuzhijiancejieguo}",  presentation.getZazhikuangwuzhijiancejieguo());  
            params.put("${zazhikuangwuzhidanxiangpingjia}",  presentation.getZazhikuangwuzhidanxiangpingjia());  
            params.put("${shuifenbiaozhunyaoqiu}",  presentation.getShuifenbiaozhunyaoqiu());  
            params.put("${shuifenjiancejieguo}",  presentation.getShuifenjiancejieguo());  
            params.put("${shuifendanxiangpingjia}",  presentation.getShuifendanxiangpingjia());  
            params.put("${yingduzhishu_1_biaozhunyaoqiu}",  presentation.getYingduzhishu_1_biaozhunyaoqiu());  
            params.put("${yingduzhishu_2_biaozhunyaoqiu}",  presentation.getYingduzhishu_2_biaozhunyaoqiu());  
            params.put("${yingduzhishu_3_biaozhunyaoqiu}",  presentation.getYingduzhishu_3_biaozhunyaoqiu());  
            params.put("${yingduzhishujiancejieguo}",  presentation.getYingduzhishujiancejieguo());  
            params.put("${yingduzhishudanxiangpingjia}",  presentation.getYingduzhishudanxiangpingjia());  
            params.put("${sezeqiweibiaozhunyaoqiu}",  presentation.getSezeqiweibiaozhunyaoqiu());  
            params.put("${sezeqiweijiancejieguo}",  presentation.getSezeqiweijiancejieguo());  
            params.put("${sezeqiweidanxiangpingjia}",  presentation.getSezeqiweidanxiangpingjia());  
            params.put("${mianjinxishui_yicun}",  presentation.getMianjinxishui_yicun());  
            params.put("${mianjinxishui_qingdubuyicun}",  presentation.getMianjinxishui_qingdubuyicun());  
            params.put("${mianjinxishui_zhongdubuyicun}",  presentation.getMianjinxishui_zhongdubuyicun());  
            params.put("${mianjinxishui_jianyanjieguo}",  presentation.getMianjinxishui_jianyanjieguo());  
            params.put("${pinchangpinfen_yicun}",  presentation.getPinchangpingfen_yicun());  
            params.put("${pinchangpinfen_qingdubuyicun}",  presentation.getPinchangpingfen_qingdubuyicun());  
            params.put("${pinchangpinfen_zhongdubuyicun}",  presentation.getPinchangpingfen_zhongdubuyicun());  
            params.put("${pinchangpinfen_jianyanjieguo}",  presentation.getPinchangpingfen_jianyanjieguo());  
            params.put("${sezeqiwei_yicun}",  presentation.getSezeqiwei_yicun());  
            params.put("${sezeqiwei_qingdubuyicun}",  presentation.getSezeqiwei_qingdubuyicun());  
            params.put("${sezeqiwei_zhongdubuyicun}",  presentation.getSezeqiwei_zhongdubuyicun());  
            params.put("${sezeqiwei_jianyanjieguo}",  presentation.getSezeqiwei_jianyanjieguo());  
            params.put("${jieguopanding}",  presentation.getJieguopanding());
            
            XwpfTUtil xwpfTUtil = new XwpfTUtil();  
      
            XWPFDocument doc;  
            String fileNameInResource = "upload/base/小麦检验报告.docx";
            InputStream is;  
            is = new FileInputStream(fileNameInResource); 
//            is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);      //本身就在编译路径下。。。。
            
            doc = new XWPFDocument(is);  
            
            xwpfTUtil.replaceInPara(doc, params);  
            //替换表格里面的变量  
            xwpfTUtil.replaceInTable(doc, params);  
            OutputStream os = response.getOutputStream();  
       
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Content-disposition","attachment;filename="+new String( "小麦检验报告".getBytes("gb2312"), "ISO8859-1" )+".docx");  
      
            doc.write(os);  
      
            xwpfTUtil.close(os);  
            xwpfTUtil.close(is);  
      
            os.flush();  
            os.close();  
        }

	
    /**
     * POI玉米检验报告导出word
     * @throws Exception
     */
    @RequestMapping(value="exportWordYM")
     public void exportWordYM(HttpServletResponse response,YMPresentation presentation) throws Exception {  
          
            Map<String, Object> params = new HashMap<String, Object>();  
      
            params.put("${bianhao_1}", presentation.getBianhao_1());
            params.put("${bianhao_2}", presentation.getBianhao_2()); 
            params.put("${sort}", presentation.getSort());  
            params.put("${sampleNum}",presentation.getSampleNum());  
            params.put("${cunchudanwei}", presentation.getCunchudanwei());  
            params.put("${counter}",  presentation.getCounter());  
            params.put("${shengchanniandu}",  presentation.getShengchanniandu());  
            params.put("${quality}",  presentation.getQuality());  
            params.put("${daibiaoshuliang}",  presentation.getDaibiaoshuliang());  
            params.put("${sampleCount}",  presentation.getSampleCount());  
            params.put("${yangpinmiaoshu}",  presentation.getYangpinmiaoshu());  
            params.put("${yangpinzhuangtai}", presentation.getYangpinzhuangtai());  
            params.put("${qianyangren}",  presentation.getQianyangren());  
            params.put("${sampleTime}",  presentation.getSampleTime());  
            params.put("${qianyangyiju}",  presentation.getQianyangyiju());  
            params.put("${jianyanmudi}",  presentation.getJianyanmudi());  
            params.put("${jianyanshijian}",  presentation.getJianyanshijian());  
            params.put("${jianyanyiju}",  presentation.getJianyanyiju()); 
            params.put("${jianyanxiangmu}",  presentation.getJianyanxiangmu());  
            params.put("${Jianyanjielun}",  presentation.getJianyanjielun());  
            params.put("${beizhu}",  presentation.getBeizhu());  
            params.put("${rongzhongbiaozhunyaoqiu}",  presentation.getRongzhongbiaozhunyaoqiu());  
            params.put("${rongzhongjiancejieguo}",  presentation.getRongzhongjiancejieguo());  
            params.put("${rongzhongdanxiangpingjia}",  presentation.getRongzhongdanxiangpingjia());  
            params.put("${buwanshanlizongliangbiaozhunyaoqiu}",  presentation.getBuwanshanlizongliangbiaozhunyaoqiu());  
            params.put("${buwanshanlizongliangjiancejieguo}",  presentation.getBuwanshanlizongliangjiancejieguo());  
            params.put("${buwanshanlizongliangdanxiangpingjia}",  presentation.getBuwanshanlizongliangdanxiangpingjia());  
            params.put("${buwanshanlishengmeilibiaozhunyaoqiu}",  presentation.getBuwanshanlishengmeilibiaozhunyaoqiu());  
            params.put("${buwanshanlishengmeilijiancejieguo}",  presentation.getBuwanshanlishengmeilijiancejieguo());  
            params.put("${buwanshanlishengmeilidanxiangpingjia}",  presentation.getBuwanshanlishengmeilidanxiangpingjia());  
            params.put("${zazhibiaozhunyaoqiu}",  presentation.getZazhibiaozhunyaoqiu());  
            params.put("${zazhijiancejieguo}",  presentation.getZazhijiancejieguo());  
            params.put("${zazhidanxiangpingjia}",  presentation.getZazhidanxiangpingjia());  
            params.put("${shuifenbiaozhunyaoqiu}",  presentation.getShuifenbiaozhunyaoqiu());  
            params.put("${shuifenjiancejieguo}",  presentation.getShuifenjiancejieguo());  
            params.put("${shuifendanxiangpingjia}",  presentation.getShuifendanxiangpingjia());  
            params.put("${zhifangsuanzhi_yicun}",  presentation.getZhifangsuanzhi_yicun());  
            params.put("${zhifangsuanzhi_qingdubuyicun}",  presentation.getZhifangsuanzhi_qingdubuyicun());  
            params.put("${zhifangsuanzhi_zhongdubuyicun}",  presentation.getZhifangsuanzhi_zhongdubuyicun());  
            params.put("${zhifangsuanzhi_jianyanjieguo}",  presentation.getZhifangsuanzhi_jianyanjieguo());  
            params.put("${pinchangpinfen_yicun}",  presentation.getPinchangpingfen_yicun());  
            params.put("${pinchangpinfen_qingdubuyicun}",  presentation.getPinchangpingfen_qingdubuyicun());  
            params.put("${pinchangpinfen_zhongdubuyicun}",  presentation.getPinchangpingfen_zhongdubuyicun());  
            params.put("${pinchangpinfen_jianyanjieguo}",  presentation.getPinchangpingfen_jianyanjieguo());  
            params.put("${sezeqiwei_yicun}",  presentation.getSezeqiwei_yicun());  
            params.put("${sezeqiwei_qingdubuyicun}",  presentation.getSezeqiwei_qingdubuyicun());  
            params.put("${sezeqiwei_zhongdubuyicun}",  presentation.getSezeqiwei_zhongdubuyicun());  
            params.put("${sezeqiwei_jianyanjieguo}",  presentation.getSezeqiwei_jianyanjieguo());  
            params.put("${jieguopanding}",  presentation.getJieguopanding());  
            
            XwpfTUtil xwpfTUtil = new XwpfTUtil();  
      
            XWPFDocument doc;  
            String fileNameInResource = "upload/base/玉米检验报告.docx";
            InputStream is;  
            is = new FileInputStream(fileNameInResource);  
//            is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);      //本身就在编译路径下。。。。
            
            doc = new XWPFDocument(is);  
            
            xwpfTUtil.replaceInPara(doc, params);  
            //替换表格里面的变量  
            xwpfTUtil.replaceInTable(doc, params);  
            OutputStream os = response.getOutputStream();  
      
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Content-disposition","attachment;filename="+new String( "玉米检验报告".getBytes("gb2312"), "ISO8859-1" )+".docx");  
      
            doc.write(os);  
      
            xwpfTUtil.close(os);  
            xwpfTUtil.close(is);  
      
            os.flush();  
            os.close();  
        }  
}
