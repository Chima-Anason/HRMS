package com.fh.controller.system.leave;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.Role;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.leave.LeaveManager;

/** 
 * 说明：Used the Dictionary for my Leave in the HRMS System
 * 创建人：chima
 * 修改时间：2021-10-18
 */
@Controller
@RequestMapping(value="/leave")
public class LeaveController extends BaseController {
	
	String menuUrl = "leave/list.do"; //菜单地址(权限用)
	@Resource(name="leaveService")
	private LeaveManager leaveService;
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Dictionaries(leave)");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DICTIONARIES_ID", this.get32UUID());	//主键
		pd.put("PARENT_ID", "ce174640544549f1b31c8f66e01c111b");
		leaveService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 * @param DICTIONARIES_ID
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String DICTIONARIES_ID) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除Dictionaries(leave)");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("DICTIONARIES_ID", DICTIONARIES_ID);
		String errInfo = "success";
		if(leaveService.listSubDictByParentId(DICTIONARIES_ID).size() > 0){
			errInfo = "false";
		}else{
			pd = leaveService.findById(pd);					
			if("yes".equals(pd.getString("YNDEL")))return null;		
			if(null != pd.get("TBSNAME") && !"".equals(pd.getString("TBSNAME"))){
				String TBFIELD = pd.getString("TBFIELD");
				if(Tools.isEmpty(TBFIELD))TBFIELD = "BIANMA"; 		
				pd.put("TBFIELD", TBFIELD);
				String[] table = pd.getString("TBSNAME").split(",");
				for(int i=0;i<table.length;i++){
					pd.put("thisTable", table[i]);
					try {
						if(Integer.parseInt(leaveService.findFromTbs(pd).get("zs").toString())>0){
							errInfo = "false";
							break;
						}
					} catch (Exception e) {
							errInfo = "false2";
							break;
					}
				}
			}
		}
		if("success".equals(errInfo)){
			leaveService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Dictionaries(leave)");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		leaveService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Dictionaries(leave)");
		/*ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("DICTIONARIES_ID") == null || "".equals(pd.getString("DICTIONARIES_ID").trim())){
				pd.put("DICTIONARIES_ID", "ce174640544549f1b31c8f66e01c111b");										//默认列出第一组角色(初始设计系统用户和会员组不能删除)
			}
			PageData fpd = new PageData();
			fpd.put("DICTIONARIES_ID", "ce174640544549f1b31c8f66e01c111b");
			List<Dictionaries> roleList = leaveService.listLeaveByPId(fpd);		//列出组(页面横向排列的一级组)
			List<Dictionaries> roleList_z = leaveService.listLeaveByPId(pd);		//列出此组下架角色
			pd = leaveService.findById(pd);							//取得点击的角色组(横排的)
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/leave/leave_list");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;*/
		
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DICTIONARIES_ID = null == pd.get("DICTIONARIES_ID")?"":pd.get("DICTIONARIES_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			DICTIONARIES_ID = pd.get("id").toString();
		}
		pd.put("DICTIONARIES_ID", "ce174640544549f1b31c8f66e01c111b");					//上级ID the leave super id in the dictionary
		
		page.setPd(pd);
		List<PageData>	varList = leaveService.list(page);	//列出Dictionaries列表
		mv.addObject("pd", leaveService.findById(pd));		//传入上级所有信息
		mv.addObject("DICTIONARIES_ID", DICTIONARIES_ID);			//上级ID
		mv.setViewName("system/leave/leave_list");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());					//按钮权限
		return mv;
	}
	
	
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="/listAllDict")
	public ModelAndView listAllDict(Model model,String DICTIONARIES_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(leaveService.listAllDict("0"));
			String json = arr.toString();
			json = json.replaceAll("DICTIONARIES_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("DICTIONARIES_ID",DICTIONARIES_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("system/dictionaries/dictionaries_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}*/
	
	/**
	 * 显示列表ztree (用于代码生成器引用数据字典)
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value="/listAllDictToCreateCode")
	public ModelAndView listAllDictToCreateCode(Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(leaveService.listAllDictToCreateCode("0"));
			String json = arr.toString();
			json = json.replaceAll("DICTIONARIES_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "click");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("pd", pd);	
			mv.setViewName("system/dictionaries/dictionaries_ztree_windows");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}*/
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DICTIONARIES_ID = null == pd.get("DICTIONARIES_ID")?"":pd.get("DICTIONARIES_ID").toString();
		pd.put("DICTIONARIES_ID", DICTIONARIES_ID);					//上级ID
		pd.put("PARENT_ID", "ce174640544549f1b31c8f66e01c111b");
		mv.addObject("pds",leaveService.findById(pd));		//传入上级所有信息
		mv.addObject("DICTIONARIES_ID", DICTIONARIES_ID);			//传入ID，作为子级ID用
		mv.setViewName("system/leave/leave_edit");
		mv.addObject("msg", "save");
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DICTIONARIES_ID = pd.getString("DICTIONARIES_ID");
		pd = leaveService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("DICTIONARIES_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",leaveService.findById(pd));				//传入上级所有信息
		mv.addObject("DICTIONARIES_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("DICTIONARIES_ID",DICTIONARIES_ID);							//复原本ID
		mv.setViewName("system/leave/leave_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(leaveService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
