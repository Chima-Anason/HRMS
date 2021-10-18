package com.fh.controller.system.user;

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

import net.sf.json.JSONArray;

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
import com.fh.entity.system.User;
import com.fh.service.fhoa.datajur.DatajurManager;
import com.fh.service.fhoa.department.DepartmentManager;
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
import com.fh.util.PageData;
import com.fh.util.ObjectExcelView;
import com.fh.util.PathUtil;
import com.fh.util.Tools;

/** 
 * 类名称：UserController
 * 创建人：FH fh313596790qq(青苔)
 * 更新时间：2018年6月24日
 * @version
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	String menuUrl = "user/listUsers.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	@Resource(name="departmentService")
	private DepartmentManager departmentService;
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	
	/**显示用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsers(page);	//列出用户列表
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**删除用户
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除user");
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.deleteU(pd);
		FHLOG.save(Jurisdiction.getUsername(), "删除系统用户："+pd);
		out.write("success");
		out.close();
	}
	
	/**去新增用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect(Jurisdiction.getDEPARTMENT_ID(),zdepartmentPdList));
		
		
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		mv.setViewName("system/user/user_edit2");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**保存用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增user");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID());	//ID 主键
		pd.put("LAST_LOGIN", "");				//最后登录时间
		pd.put("IP", "");						//IP
		pd.put("STATUS", "0");					//状态
		pd.put("SKIN", "no-skin");				//用户默认皮肤
		pd.put("RIGHTS", "");	
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
		/*String p = pd.getString("NAME");		
		//String PASSWORD = p+"001";
		String PASSWORD = User.setPASSWORD(p+"001");
		//pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("NAME"), PASSWORD));	//密码加密
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), PASSWORD).toString());	//密码加密
*/		
		
		//added
		String DEPARTMENT_IDS = departmentService.getDEPARTMENT_IDS(pd.getString("DEPARTMENT_ID"));//获取某个部门所有下级部门ID
		pd.put("DATAJUR_ID", pd.getString("USER_ID")); //主键
		pd.put("DEPARTMENT_IDS", DEPARTMENT_IDS);		//部门ID集
		datajurService.save(pd);						//把此员工默认部门及以下部门ID保存到组织数据权限表
	
		if(null == userService.findByUsername(pd)){	//判断用户名是否存在
			userService.saveU(pd); 					//执行保存
			FHLOG.save(Jurisdiction.getUsername(), "新增系统用户："+pd.getString("USERNAME"));
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUsername(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断邮箱是否存在
	 * @return
	 */
	@RequestMapping(value="/hasE")
	@ResponseBody
	public Object hasE(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUE(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasN")
	@ResponseBody
	public Object hasN(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(userService.findByUN(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**去修改用户页面(系统用户列表修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		JSONArray arr = JSONArray.fromObject(departmentService.listAllDepartmentToSelect(Jurisdiction.getDEPARTMENT_ID(),zdepartmentPdList));
		mv.addObject("zTreeNodes", (null == arr ?"":arr.toString()));
		
		if("1".equals(pd.getString("USER_ID"))){return null;}		//不能修改admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		mv.addObject("fx", "user");
		pd = userService.findById(pd);								//根据ID读取
		List<Role> froleList = new  ArrayList<Role>();				//存放副职角色
		String ROLE_IDS = pd.getString("ROLE_IDS");					//副职角色ID
		if(Tools.notEmpty(ROLE_IDS)){
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for(int i=0;i<roleList.size();i++){
				Role role = roleList.get(i);
				String roleId = role.getROLE_ID();
				for(int n=0;n<arryROLE_ID.length;n++){
					if(arryROLE_ID[n].equals(roleId)){
						role.setRIGHTS("1");	//此时的目的是为了修改用户信息上，能看到副职角色都有哪些
						break;
					}
				}
				froleList.add(role);
			}
		}else{
			froleList = roleList;
		}
		mv.setViewName("system/user/user_edit2");
		
		mv.addObject("depname", departmentService.findById(pd).getString("NAME"));
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("froleList", froleList);
		return mv;
	}
	
	/**去修改用户页面(个人修改)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditMyU")
	public ModelAndView goEditMyU() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("fx", "head");
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd.put("USERNAME", Jurisdiction.getUsername());
		pd = userService.findByUsername(pd);						//根据用户名读取
		//mv.setViewName("system/user/user_edit2");
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editMyU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**查看用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String USERNAME = pd.getString("USERNAME");
		if("admin".equals(USERNAME)){return null;}	//不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		if(null == pd){
			PageData rpd = new PageData();
			rpd.put("RNUMBER", USERNAME);							//用户名查不到数据时就把数据当作角色的编码去查询角色表(工作流的待办人物，查看代办人资料时用到)
			rpd = roleService.getRoleByRnumber(rpd);
			mv.addObject("rpd", rpd);
		}
		mv.setViewName("system/user/user_view");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**去修改用户页面(在线管理页面打开)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditUfromOnline")
	public ModelAndView goEditUfromOnline() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if("admin".equals(pd.getString("USERNAME"))){return null;}	//不能查看admin用户
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);	//列出所有系统用户角色
		pd = userService.findByUsername(pd);						//根据ID读取
		List<Role> froleList = new  ArrayList<Role>();				//存放副职角色
		String ROLE_IDS = pd.getString("ROLE_IDS");					//副职角色ID
		if(Tools.notEmpty(ROLE_IDS)){
			String arryROLE_ID[] = ROLE_IDS.split(",fh,");
			for(int i=0;i<roleList.size();i++){
				Role role = roleList.get(i);
				String roleId = role.getROLE_ID();
				for(int n=0;n<arryROLE_ID.length;n++){
					if(arryROLE_ID[n].equals(roleId)){
						role.setRIGHTS("1");	//此时的目的是为了修改用户信息上，能看到副职角色都有哪些
						break;
					}
				}
				froleList.add(role);
			}
		}else{
			froleList = roleList;
		}
		mv.setViewName("system/user/user_edit2");
		
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("froleList", froleList);
		return mv;
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改User");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(!Jurisdiction.getUsername().equals(pd.getString("USERNAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("ROLE_ID", userService.findByUsername(pd).getString("ROLE_ID")); //对角色ID还原本人角色ID
			pd.put("ROLE_IDS", userService.findByUsername(pd).getString("ROLE_IDS")); //对角色ID还原本人副职角色ID
		}
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editU(pd);	//执行修改
		String DEPARTMENT_IDS = this.departmentService.getDEPARTMENT_IDS(pd.getString("DEPARTMENT_ID"));//获取某个部门所有下级部门ID
		pd.put("DATAJUR_ID", pd.getString("USER_ID")); //主键
		pd.put("DEPARTMENT_IDS", DEPARTMENT_IDS);		//部门ID集
		this.datajurService.edit(pd);						//把此员工默认部门及以下部门ID保存到组织数据权限表
		FHLOG.save(Jurisdiction.getUsername(), "修改系统用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editMyU")
	public ModelAndView editMyU() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改MyUser");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(!Jurisdiction.getUsername().equals(pd.getString("USERNAME"))){		//如果当前登录用户修改用户资料提交的用户名非本人
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}  //校验权限 判断当前操作者有无用户管理查看权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限判断当前操作者有无用户管理修改权限
			if("admin".equals(pd.getString("USERNAME")) && !"admin".equals(Jurisdiction.getUsername())){return null;}	//非admin用户不能修改admin
		}else{	//如果当前登录用户修改用户资料提交的用户名是本人，则不能修改本人的角色ID
			pd.put("ROLE_ID", userService.findByUsername(pd).getString("ROLE_ID")); //对角色ID还原本人角色ID
			pd.put("ROLE_IDS", userService.findByUsername(pd).getString("ROLE_IDS")); //对角色ID还原本人副职角色ID
			
		}
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		userService.editMyU(pd);	//执行修改
		/*String DEPARTMENT_IDS = this.departmentService.getDEPARTMENT_IDS(pd.getString("DEPARTMENT_ID"));//获取某个部门所有下级部门ID
		pd.put("DATAJUR_ID", pd.getString("USER_ID")); //主键
		pd.put("DEPARTMENT_IDS", DEPARTMENT_IDS);		//部门ID集
		this.datajurService.edit(pd);	*/					//把此员工默认部门及以下部门ID保存到组织数据权限表
		FHLOG.save(Jurisdiction.getUsername(), "修改系统用户："+pd.getString("USERNAME"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除user");
		FHLOG.save(Jurisdiction.getUsername(), "批量删除user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 批量删除
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/addAllU")
	@ResponseBody
	public Object addAllU() throws Exception {
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量增加user");
		FHLOG.save(Jurisdiction.getUsername(), "批量增加user");
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String USER_IDS = pd.getString("USER_IDS");
		if(null != USER_IDS && !"".equals(USER_IDS)){
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.addAllU(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}*/
	
	/**导出用户信息到EXCEL
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "导出用户信息到EXCEL");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			if(Jurisdiction.buttonJurisdiction(menuUrl, "cha")){
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
				String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
				if(lastLoginStart != null && !"".equals(lastLoginStart)){
					pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
				}
				if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
					pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
				} 
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("用户名"); 		//1
				titles.add("员工编号");  		//2
				titles.add("姓名");			//3
				titles.add("英文");	        //4
				titles.add("职位");			//5
				titles.add("手机");			//6
				titles.add("邮箱");			//7
				titles.add("最近登录");		//8
				titles.add("上次登录IP");	//9
				titles.add("部门");	//10
				titles.add("职责");	//11
				titles.add("性别");	//12
				titles.add("出生日期");	//13
				titles.add("民族");	//14
				titles.add("岗位类别");	//15
				titles.add("参加工作时间");	//16
				titles.add("籍贯");	//17
				titles.add("政治面貌");	//18
				titles.add("入团时间");	//19
				titles.add("身份证号");	//20
				titles.add("婚姻状况");	//21
				titles.add("进本单位时间");	//22
				titles.add("现岗位");	//23
				titles.add("上岗时间");	//24
				titles.add("学历");	//25
				titles.add("毕业学校");	//26
				titles.add("专业");	//27
				titles.add("职称");	//28
				titles.add("职业资格证");	//29
				titles.add("劳动合同时长");	//30
				titles.add("签订日期");	//31
				titles.add("终止日期");	//32
				titles.add("现住址");	//33
				titles.add("备注");	//34
				dataMap.put("titles", titles);
				List<PageData> userList = userService.listAllUser(pd);
				List<PageData> varList = new ArrayList<PageData>();
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("USERNAME"));		//1
					vpd.put("var2", userList.get(i).getString("BIANMA"));		//2
					vpd.put("var3", userList.get(i).getString("NAME"));			//3
					vpd.put("var4", userList.get(i).getString("NAME_EN"));	    //4
					vpd.put("var5", userList.get(i).getString("ROLE_NAME"));	//5
					vpd.put("var6", userList.get(i).getString("TEL"));		//6
					vpd.put("var7", userList.get(i).getString("EMAIL"));		//7
					vpd.put("var8", userList.get(i).getString("LAST_LOGIN"));	//8
					vpd.put("var9", userList.get(i).getString("IP"));			//9
					vpd.put("var10", userList.get(i).getString("DEPARTMENT_ID"));	    //10
					vpd.put("var11", userList.get(i).getString("FUNCTIONS"));	    //11
					vpd.put("var12", userList.get(i).getString("SEX"));	    //12
					vpd.put("var13", userList.get(i).getString("BIRTHDAY"));	    //13
					vpd.put("var14", userList.get(i).getString("NATION"));	    //14
					vpd.put("var15", userList.get(i).getString("JOBTYPE"));	    //15
					vpd.put("var16", userList.get(i).getString("JOBJOINTIME"));	    //16
					vpd.put("var17", userList.get(i).getString("FADDRESS"));	    //17
					vpd.put("var18", userList.get(i).getString("POLITICAL"));	    //18
					vpd.put("var19", userList.get(i).getString("PJOINTIME"));	    //19
					vpd.put("var20", userList.get(i).getString("SFID"));	    //20
					vpd.put("var21", userList.get(i).getString("MARITAL"));	    //21
					vpd.put("var22", userList.get(i).getString("DJOINTIME"));	    //22
					vpd.put("var23", userList.get(i).getString("POST"));	    //23
					vpd.put("var24", userList.get(i).getString("POJOINTIME"));	    //24
					vpd.put("var25", userList.get(i).getString("EDUCATION"));	    //25
					vpd.put("var26", userList.get(i).getString("SCHOOL"));	    //26
					vpd.put("var27", userList.get(i).getString("MAJOR"));	    //27
					vpd.put("var28", userList.get(i).getString("FTITLE"));	    //28
					vpd.put("var29", userList.get(i).getString("CERTIFICATE"));	    //29
					vpd.put("var30", userList.get(i).get("CONTRACTLENGTH").toString());	//30
					vpd.put("var31", userList.get(i).getString("CSTARTTIME"));	    //31
					vpd.put("var32", userList.get(i).getString("CENDTIME"));	    //32
					vpd.put("var33", userList.get(i).getString("ADDRESS"));	    //33
					vpd.put("var34", userList.get(i).getString("BZ"));	    //34
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				mv = new ModelAndView(erv,dataMap);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users2.xls", "Users.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		FHLOG.save(Jurisdiction.getUsername(), "从EXCEL导入到数据库");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/*存入数据库操作======================================*/
			pd.put("RIGHTS", "");					//权限
			pd.put("LAST_LOGIN", "");				//最后登录时间
			pd.put("IP", "");						//IP
			pd.put("STATUS", "0");					//状态
			pd.put("SKIN", "no-skin");				//默认皮肤
			pd.put("ROLE_ID", "fhadminzhuche");
			pd.put("RIGHTS", "");	
			pd.put("DEPARTMENT_ID", "");
			pd.put("BIRTHDAY", "");
			pd.put("JOBJOINTIME", "");
			pd.put("PJOINTIME", "");
			pd.put("DJOINTIME", "");
			pd.put("POJOINTIME", "");
			pd.put("CSTARTTIME", "");
			pd.put("CENDTIME", "");
/*			List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
			pd.put("ROLE_ID", roleList.get(0).getROLE_ID());		//设置角色ID为随便第一个
*/			/*
			 * var0 :用户名
			 * var1 :员工编号
			 * var2 :姓名
			 * var3 :英文
			 * var4 :手机
			 * var5 :邮箱
			 * var6 :部门
			 * var7 :职责
			 * var8 :性别
			 * var9 :出生日期
			 * var10 :民族
			 * var11 :岗位类别
			 * var12 :参加工作时间
			 * var13 :籍贯
			 * var14 :政治面貌
			 * var15 :入团时间
			 * var16 :身份证号
			 * var17 :婚姻状况
			 * var18 :进本单位时间
			 * var19 :现岗位
			 * var20 :上岗时间
			 * var21 :学历
			 * var22 :毕业学校
			 * var23 :专业
			 * var24 :职称
			 * var25 :职业资格证
			 * var26 :劳动合同时长
			 * var27 :签订日期
			 * var28 :终止日期
			 * var29 :现住址
			 * var30 :备注
			 */
			for(int i=0;i<listPd.size();i++){		
				pd.put("USER_ID", this.get32UUID());										//ID
			
				
				pd.put("BIANMA", listPd.get(i).getString("var1"));							//编号已存在就跳过
				

				
				pd.put("NAME", listPd.get(i).getString("var2"));							//姓名
				
				pd.put("NAME_EN", listPd.get(i).getString("var3"));							//姓名
				
				pd.put("TEL", listPd.get(i).getString("var4"));							//手机号
				
				//pd.put("DEPARTMENT_ID", listPd.get(i).getString("var6")); 
				
				pd.put("FUNCTIONS", listPd.get(i).getString("var6"));
				
				pd.put("SEX", listPd.get(i).getString("var7"));
				
				//pd.put("BIRTHDAY", listPd.get(i).getString("var9"));
				
				pd.put("NATION", listPd.get(i).getString("var8"));
				
				pd.put("JOBTYPE", listPd.get(i).getString("var9"));
				
				//pd.put("JOBJOINTIME", listPd.get(i).getString("var12"));
				
				pd.put("FADDRESS", listPd.get(i).getString("var10"));
				
				pd.put("POLITICAL", listPd.get(i).getString("var11"));
				
				//pd.put("PJOINTIME", listPd.get(i).getString("var15"));
				
				pd.put("SFID", listPd.get(i).getString("var12"));
				
				pd.put("MARITAL", listPd.get(i).getString("var13"));
				
				//pd.put("DJOINTIME", listPd.get(i).getString("var18"));
				
				pd.put("POST", listPd.get(i).getString("var14"));
				
				//pd.put("POJOINTIME", listPd.get(i).getString("var20"));
				
				pd.put("EDUCATION", listPd.get(i).getString("var15"));
				
				pd.put("SCHOOL", listPd.get(i).getString("var16"));
				
				pd.put("MAJOR", listPd.get(i).getString("var17"));
				
				pd.put("FTITLE", listPd.get(i).getString("var18"));
				
				pd.put("CERTIFICATE", listPd.get(i).getString("var19"));
				
				pd.put("CONTRACTLENGTH", listPd.get(i).getString("var20"));
				
				//pd.put("CSTARTTIME", listPd.get(i).getString("var27"));
				
				//pd.put("CENDTIME", listPd.get(i).getString("var28"));
				
				pd.put("ADDRESS", listPd.get(i).getString("var21"));
				
				pd.put("BZ", listPd.get(i).getString("var22"));								//备注
				
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var0"));	//根据姓名汉字生成全拼
				pd.put("USERNAME", USERNAME);	
				if(userService.findByUsername(pd) != null){									//判断用户名是否重复
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var0"))+Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}
				
				
				if(Tools.checkEmail(listPd.get(i).getString("var5"))){						//邮箱格式不对就跳过
					pd.put("EMAIL", listPd.get(i).getString("var5"));						
					if(userService.findByUE(pd) != null){									//邮箱已存在就跳过
						continue;
					}
				}else{
					continue;
				}
			
				
				
				pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, "123").toString());	//默认密码123
				if(userService.findByUN(pd) != null){
					continue;
				}
				userService.saveU(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**显示用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listUsersForWindow")
	public ModelAndView listUsersForWindow(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		} 
		page.setPd(pd);
		List<PageData>	userList = userService.listUsersBystaff(page);	//列出用户列表(弹窗选择用)
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		mv.setViewName("system/user/window_user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**列表(用于弹窗)
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listfortc")
	public ModelAndView listfortc(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	userList = userService.listUsersBystaff(page);	//列出用户列表(弹窗选择用)
		pd.put("ROLE_ID", "1");
		List<Role> roleList = roleService.listAllRolesByPId(pd);//列出所有系统用户角色
		//pd = userService.findById(pd);
		mv.setViewName("system/user/window_user_list2");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}

}
