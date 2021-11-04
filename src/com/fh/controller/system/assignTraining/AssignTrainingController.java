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
import com.fh.util.DateUtil;
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
		pd.put("SEND_TIME", DateUtil.getTime());				//发送时间
		pd.put("FROM_USERNAME", Jurisdiction.getUsername());	//发培训人
		
		userService.findById(pd);
		String username = pd.getString("USERNAME");
		pd.put("TO_USERNAME", username); 
		
		assignTrainingService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
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
		pd.put("STATUZ", "2");	
		pd.put("SEND_TIME", DateUtil.getTime());				//发送时间
		userService.findById(pd);
		String username = pd.getString("USERNAME");
		pd.put("TO_USERNAME", username); 
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
		
		String STATUZ = pd.getString("STATUZ");				
		if(null != STATUZ && !"".equals(STATUZ)){
			pd.put("STATUZ", STATUZ);
		}
		String STARTTIME = pd.getString("STARTTIME");	//开始时间
		String ENDTIME = pd.getString("ENDTIME");		//结束时间
		if(STARTTIME != null && !"".equals(STARTTIME)){
			pd.put("STARTTIME", STARTTIME+" 00:00:00");
		}
		if(ENDTIME != null && !"".equals(ENDTIME)){
			pd.put("ENDTIME", ENDTIME+" 00:00:00");
		} 
		
		//updates on reload AssignTraining list page (for each column in trainingList_s(compare end time with current time to update the status to be 0(finished/完成))) 
		List<Training> trainingList_s = trainingService.listTrainingToSelect(pd);
		for (int i=0; i < trainingList_s.size() ;i++) {

			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date endTime = sdformat.parse(trainingList_s.get(i).getENDTIME());
			Date CURRENTTIME = new Date();
			if(endTime.compareTo(CURRENTTIME) < 0){
				String status = trainingList_s.get(i).getSTATUS();
				if(status != "0"){
					String training_id = trainingList_s.get(i).getTRAINING_ID();
					pd.put("TRAINING_ID", training_id);
					trainingService.editStatus(pd);
				}
			}
		}
		
		//Error to be noted
		/*String curUser = Jurisdiction.getUsername();//pd.getString("USERNAME");
		System.out.println("curuser is : " + curUser);*/
		// user can only see his training
/*		if("admin".equals(Jurisdiction.getUsername())){
			pd.put("USERNAME", "");
		}else if(!"admin".equals(Jurisdiction.getUsername())){
			pd.put("USERNAME", Jurisdiction.getUsername());
			pd.put("STATUZ", "1");	
		}*/
		/*pd.put("curUser", "admin");*/
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);

		List<PageData>	varList = assignTrainingService.list(page);
		mv.setViewName("system/assignTraining/assignTraining_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/historyList")
	public ModelAndView historyList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AssignTraining(完成的培训)");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String STATUZ = pd.getString("STATUZ");				
		if(null != STATUZ && !"".equals(STATUZ)){
			pd.put("STATUZ", STATUZ);
		}
		String STARTTIME = pd.getString("STARTTIME");	//开始时间
		String ENDTIME = pd.getString("ENDTIME");		//结束时间
		if(STARTTIME != null && !"".equals(STARTTIME)){
			pd.put("STARTTIME", STARTTIME+" 00:00:00");
		}
		if(ENDTIME != null && !"".equals(ENDTIME)){
			pd.put("ENDTIME", ENDTIME+" 00:00:00");
		} 
		
		//updates on reload AssignTraining list page (for each column in trainingList_s(compare end time with current time to update the status to be 0(finished/完成))) 
		List<Training> trainingList_s = trainingService.listTrainingToSelect(pd);
		for (int i=0; i < trainingList_s.size() ;i++) {

			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date endTime = sdformat.parse(trainingList_s.get(i).getENDTIME());
			Date CURRENTTIME = new Date();
			if(endTime.compareTo(CURRENTTIME) < 0){
				String status = trainingList_s.get(i).getSTATUS();
				if(!"0".equals(status)){
					String training_id = trainingList_s.get(i).getTRAINING_ID();
					pd.put("TRAINING_ID", training_id);
					trainingService.editStatus(pd);
				}
			}
		}
		
		//Error to be noted
		/*String curUser = Jurisdiction.getUsername();//pd.getString("USERNAME");
		System.out.println("curuser is : " + curUser);*/
		// user can only see his training
/*		if("admin".equals(Jurisdiction.getUsername())){
			pd.put("USERNAME", "");
		}else if(!"admin".equals(Jurisdiction.getUsername())){
			pd.put("USERNAME", Jurisdiction.getUsername());
			pd.put("STATUZ", "1");	
		}*/
		/*pd.put("curUser", "admin");*/
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);

		List<PageData>	varList = assignTrainingService.historyList(page);
		mv.setViewName("system/assignTraining/historyAssignTraining_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	 /**去查看页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/goView")
		public ModelAndView goView()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			//if the user view the assigned training then the status will change to already read (STATUS = 1)
			if(!"admin".equals(Jurisdiction.getUsername())){ 
				assignTrainingService.editStatus(pd);
			}
			pd = assignTrainingService.findById(pd);	//根据ID读取
			mv.setViewName("system/assignTraining/assignTraining_view");
			mv.addObject("pd", pd);
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
		mv.setViewName("system/assignTraining/assignTraining_edit");
		mv.addObject("msg", "save");
		mv.addObject("trainingList", trainingList);
		mv.addObject("userList", userList);
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
		pd = assignTrainingService.findById(pd);//根据ID读取	
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
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
