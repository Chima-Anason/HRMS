package com.fh.controller.system.assignTraining;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Role;
import com.fh.entity.system.Training;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.system.assignTraining.AssignTrainingManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.training.TrainingManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：HRMS
 * 创建人：chima
 * 修改时间：2021-10-18
 */
@Controller
@RequestMapping(value="/assignTraining")
public class AssignTrainingController extends BaseController {
	
	String menuUrl = "assignTraining/list.do"; //菜单地址(权限用)
	@Resource(name="assignTrainingService")
	private AssignTrainingManager assignTrainingService;
	@Resource(name="trainingService")
	private TrainingManager trainingService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增AssignTraining");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ASS_ID", this.get32UUID());	//主键
		pd.put("STATUZ", "2");	
		assignTrainingService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	
	/**列表(用于弹窗)
	 * @param page
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/listfortc")
	public ModelAndView listfortc(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYW = pd.getString("keyword");	//检索条件
		if(null != KEYW && !"".equals(KEYW)){
			pd.put("KEYW", KEYW.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = headmanService.list(page);	//列出Pictures列表
		mv.setViewName("system/headman/headman_list_tc");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	*/
	
	/**To determine if the headman no exists
	 * @return
	 *//*
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(headmanService.findByHeadmanNo(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	*/
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除AssignTraining");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		assignTrainingService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AssignTraining");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		assignTrainingService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表AssignTraining");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String STARTTIME = pd.getString("STARTTIME");	//开始时间
		String ENDTIME = pd.getString("ENDTIME");		//结束时间
		if(STARTTIME != null && !"".equals(STARTTIME)){
			pd.put("STARTTIME", STARTTIME+" 00:00:00");
		}
		if(ENDTIME != null && !"".equals(ENDTIME)){
			pd.put("ENDTIME", ENDTIME+" 00:00:00");
		} 
		String curUser = Jurisdiction.getUsername();//pd.getString("USERNAME");
		System.out.println("curuser is : " + curUser);
		// user can only see his training
		if(curUser == "admin"){
			pd.put("curUser", "");
		}else{
			pd.put("curUser", curUser);
		}
		/*pd.put("curUser", "admin");*/
		//pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);
		List<PageData>	varList = assignTrainingService.list(page);
		//List<Training> trainingList = trainingService.listTrainingToSelect(pd);
		//List<User> userList = userService.listAllUsers(pd);
		mv.setViewName("system/assignTraining/assignTraining_list");
		mv.addObject("varList", varList);
		//mv.addObject("trainingList", trainingList);
		//mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Training> trainingList = trainingService.listTrainingToSelect(pd);
		List<User> userList = userService.listAllUsers(pd);
		//List<PageData>	userList = userService.listUsersBystaff(page);	//列出用户列表(弹窗选择用)
		//pd.put("ROLE_ID", "1");
		//List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.setViewName("system/assignTraining/assignTraining_edit");
		mv.addObject("msg", "save");
		mv.addObject("trainingList", trainingList);
		mv.addObject("userList", userList);
		//mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
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
		List<Training> trainingList = trainingService.listTrainingToSelect(pd);
		List<User> userList = userService.listAllUsers(pd);
		pd = trainingService.findById(pd);	//根据ID读取
		mv.setViewName("system/assignTraining/assignTraining_edit");
		mv.addObject("trainingList", trainingList);
		mv.addObject("userList", userList);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除AssignTraining");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			assignTrainingService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 *//*
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Headman到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("名称");	//1
		titles.add("权限标识");	//2
		titles.add("备注");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = headmanService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("HEADMAN_NAME"));	//1
			vpd.put("var2", varOList.get(i).getString("HEADMAN_NO"));	//2
			vpd.put("var3", varOList.get(i).getString("CREATED_TIME"));	//3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}*/
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
