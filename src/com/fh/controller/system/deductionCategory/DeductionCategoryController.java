
package com.fh.controller.system.deductionCategory;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.service.system.deductionCategory.DeductionCategoryManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;

/** 
 * 类名称：DeductionCategoryController
 * 创建人：
 * 更新时间：2021年4月29日
 * @version
 */
@Controller
@RequestMapping(value="/deductionCategory")
public class DeductionCategoryController extends BaseController {
	
	String menuUrl = "deductionCategory/listDeductionsCategory.do"; //菜单地址(权限用)
	@Resource(name="deductionCategoryService")
	private DeductionCategoryManager deductionCategoryService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	

	
	/**Display the list of deductions category
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listDeductionsCategory")
	public ModelAndView listDeductionsCategory(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	deductionCategoryList = deductionCategoryService.deductionCategorylistPage(page);	//列出楼宇列表/Get the list of deduction category
		pd.put("PARENT_ID", "02");
		mv.setViewName("system/deductionCategory/deductionCategory_list");
		mv.addObject("deductionCategoryList", deductionCategoryList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**Delete deduction category
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除deduction");
		PageData pd = new PageData();
		pd = this.getPageData();
		deductionCategoryService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除deduction："+pd);
		out.write("success");
		out.close();
	}
	
	/**Go to adding new deduction page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/deductionCategory/deductionCategory_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**Save deduction
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增room");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PARENT_ID", "02");
		pd.put("CAT_ID", this.get32UUID());		
		if(deductionCategoryService.findByCategory_Name(pd) == null || "".equals(deductionCategoryService.findByCategory_Name(pd))){	//Determine if the deduction name exists
			deductionCategoryService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增扣除："+pd.getString("CAT_NAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}

	
	/**Go To modify the deduction category page 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "deductionCategory");
		pd = deductionCategoryService.findById(pd);								//根据ID读取
		mv.setViewName("system/deductionCategory/deductionCategory_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**View the deduction category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = deductionCategoryService.findByCategory_Name(pd);						//根据ID读取
		mv.setViewName("system/deductionCategory/deductionCategory_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		return mv;
	}
	

	
	/**
	 * Modify the deduction
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改deduction");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		deductionCategoryService.editU(pd);					
		FHLOG.save(Jurisdiction.getUsername(), "修改扣除："+pd.getString("CAT_NAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * Batch deletion
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除deductionCategory");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除deductionCategory");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String CAT_IDS = pd.getString("CAT_IDS");
		if(null != CAT_IDS && !"".equals(CAT_IDS)){
			String ArrayCAT_IDS[] = CAT_IDS.split(",");
			deductionCategoryService.deleteAllU(ArrayCAT_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	

}
