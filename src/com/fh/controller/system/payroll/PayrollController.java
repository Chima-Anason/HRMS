package com.fh.controller.system.payroll;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import com.fh.entity.system.Payroll;
import com.fh.entity.system.Role;
import com.fh.entity.system.SalaryCategory;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.fhoa.myleave.MyleaveManager;
import com.fh.service.system.allowanceCategory.AllowanceCategoryManager;
import com.fh.service.system.deductionCategory.DeductionCategoryManager;
import com.fh.service.system.fhbutton.FhbuttonManager;
import com.fh.service.system.payroll.PayrollManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;

/** 
 * 说明：HRMS
 * 创建人：chima
 * 修改时间：2021-10-18
 */
@Controller
@RequestMapping(value="/payroll")
public class PayrollController extends BaseController {
	
	String menuUrl = "payroll/list.do"; //菜单地址(权限用)
	@Resource(name="payrollService")
	private PayrollManager payrollService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="allowanceCategoryService")
	private AllowanceCategoryManager allowanceCategoryService;
	@Resource(name="deductionCategoryService")
	private DeductionCategoryManager deductionCategoryService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="myleaveService")
	private MyleaveManager myleaveService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Payroll");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SALARY_ID", this.get32UUID());	//主键
		pd.put("STATUS", "0");
		pd.put("SEND_TIME", DateUtil.getTime());
		
		//if the value is "" from the view, then set the value to be 0.00
		String Basic_salary = pd.getString("SALARY_AMOUNT");
		if(null == Basic_salary || "".equals(Basic_salary)){
			 Basic_salary = "0.00";
		}
		
		String Allowee1 = pd.getString("ALLOWANCE1_AMOUNT");
		if(null == Allowee1 || "".equals(Allowee1)){
			Allowee1 = "0.00";
		}
		String Allowee2 = pd.getString("ALLOWANCE2_AMOUNT");
		if(null == Allowee2 || "".equals(Allowee2)){
			Allowee2 = "0.00";
		}
		
		String Deduct1 = pd.getString("DEDUCTION1_AMOUNT");
		if(null == Deduct1 || "".equals(Deduct1)){
			Deduct1 = "0.00";
		}
		
		String Deduct2 = pd.getString("DEDUCTION2_AMOUNT");
		if(null == Deduct2 || "".equals(Deduct2)){
			Deduct2 = "0.00";
		}
		
		//PageData leaveCount = myleaveService.getUserLeaveCount(pd);
		
		
		//convert to double
		double BS = Double.parseDouble(Basic_salary);
		double A1 = Double.parseDouble(Allowee1);
		double A2 = Double.parseDouble(Allowee2);
		double D1 = Double.parseDouble(Deduct1);
		double D2 = Double.parseDouble(Deduct2);
		
		//2decimal place converter
		final DecimalFormat df = new DecimalFormat("0.00");	
		
		//convert the value to 2d.p then insert value 
		pd.put("SALARY_AMOUNT", df.format(BS));
		pd.put("ALLOWANCE1_AMOUNT", df.format(A1));
		pd.put("ALLOWANCE2_AMOUNT", df.format(A2));
		pd.put("DEDUCTION1_AMOUNT", df.format(D1));
		pd.put("DEDUCTION2_AMOUNT", df.format(D2));
		
		//put Total Allowance = Allowance1_Amount + Allowance2_Amount
		double T1 = A1 + A2;
		pd.put("TOTAL_ALLOWANCE",  df.format(T1));
		
		//put Total Deduction = Deduction1_Amount + Deduction2_Amount
		double T2 = D1 + D2;
		pd.put("TOTAL_DEDUCTION", df.format(T2));
		
		
		//put Net_Salary = (Total Allowance - Total Deduction) + Salary_Amount 
		double net = ((A1 + A2) - (D1 + D2)) + BS;
		//if the net value is "" set it to 0.00 else put the original value
		pd.put("NET_SALARY",  "".equals(net)?"0.00":df.format(net));
		

		payrollService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Payroll");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		payrollService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Payroll");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SEND_TIME", DateUtil.getTime());
		pd.put("STATUS", "0");
		
		String Basic_salary = pd.getString("SALARY_AMOUNT");
		if(null == Basic_salary || "".equals(Basic_salary)){
			 Basic_salary = "0.00";
		}
		
		String Allowee1 = pd.getString("ALLOWANCE1_AMOUNT");
		if(null == Allowee1 || "".equals(Allowee1)){
			Allowee1 = "0.00";
		}
		String Allowee2 = pd.getString("ALLOWANCE2_AMOUNT");
		if(null == Allowee2 || "".equals(Allowee2)){
			Allowee2 = "0.00";
		}
		
		String Deduct1 = pd.getString("DEDUCTION1_AMOUNT");
		if(null == Deduct1 || "".equals(Deduct1)){
			Deduct1 = "0.00";
		}
		
		String Deduct2 = pd.getString("DEDUCTION2_AMOUNT");
		if(null == Deduct2 || "".equals(Deduct2)){
			Deduct2 = "0.00";
		}
		
		
		double BS = Double.parseDouble(Basic_salary);
		double A1 = Double.parseDouble(Allowee1);
		double A2 = Double.parseDouble(Allowee2);
		double D1 = Double.parseDouble(Deduct1);
		double D2 = Double.parseDouble(Deduct2);
		
		final DecimalFormat df = new DecimalFormat("0.00");	
		
		pd.put("SALARY_AMOUNT", df.format(BS));
		pd.put("ALLOWANCE1_AMOUNT", df.format(A1));
		pd.put("ALLOWANCE2_AMOUNT", df.format(A2));
		pd.put("DEDUCTION1_AMOUNT", df.format(D1));
		pd.put("DEDUCTION2_AMOUNT", df.format(D2));
		
		
		//put Total Allowance = Allowance1_Amount + Allowance2_Amount
		double T1 = A1 + A2;
		pd.put("TOTAL_ALLOWANCE",  df.format(T1));
				
		//put Total Deduction = Deduction1_Amount + Deduction2_Amount
		double T2 = D1 + D2;
		pd.put("TOTAL_DEDUCTION", df.format(T2));
		
		
		//put Net_Salary = (Total Allowance - Total Deduction) + Salary_Amount 
		double net = ((A1 + A2) - (D1 + D2)) + BS;
		pd.put("NET_SALARY",  "".equals(net)?"0.00":df.format(net));
		
		payrollService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Payroll");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String DATE = pd.getString("DATE");	
		
		if(DATE != null && !"".equals(DATE)){
			pd.put("DATE", DATE);
		}
		
		
		//error to be noted: trying to make calculation and add to only the front page but it doesn't have a column in DB
		/*List<Payroll> payrollList_s = payrollService.listAllPayrolls(pd);
		
		for (int i=0; i < payrollList_s.size() ;i++) {
			
			
			//String Allowee1 = payrollList_s.get(i).getString("ALLOWANCE1_AMOUNT");
			String Allowee1 = payrollList_s.get(i).getALLOWANCE1_AMOUNT();
			if(null == Allowee1 || "".equals(Allowee1)){
				Allowee1 = "0.00";
			}
			String Allowee2 = payrollList_s.get(i).getALLOWANCE2_AMOUNT();
			if(null == Allowee2 || "".equals(Allowee2)){
				Allowee2 = "0.00";
			}
			String Deduct1 = payrollList_s.get(i).getDEDUCTION1_AMOUNT();
			if(null == Deduct1 || "".equals(Deduct1)){
				Deduct1 = "0.00";
			}
			String Deduct2 = payrollList_s.get(i).getDEDUCTION2_AMOUNT();
			if(null == Deduct2 || "".equals(Deduct2)){
				Deduct2 = "0.00";
			}
			
			double A1 = Double.parseDouble(Allowee1);
			double A2 = Double.parseDouble(Allowee2);
			double D1 = Double.parseDouble(Deduct1);
			double D2 = Double.parseDouble(Deduct2);
			
			final DecimalFormat df = new DecimalFormat("0.00");
			
			//put Total Allowance = Allowance1_Amount + Allowance2_Amount
			double T1 = A1 + A2;
			pd.put("TOTAL_ALLOWANCE",  df.format(T1));
			
			//put Total Deduction = Deduction1_Amount + Deduction2_Amount
			double T2 = D1 + D2;
			pd.put("TOTAL_DEDUCTION", df.format(T2));
			
		}*/
		
		//Admin can view all but user can only view his related page
		pd.put("USERNAME", "admin".equals(Jurisdiction.getUsername())?"":Jurisdiction.getUsername()); //除admin用户外，只能查看自己的数据
		page.setPd(pd);
		
		List<PageData>	varList = payrollService.list(page);
		mv.setViewName("system/payroll/payroll_list");
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
		List<SalaryCategory> allowanceList = allowanceCategoryService.listAllAllowancesCategory(pd);
		List<SalaryCategory> deductionList = deductionCategoryService.listAllDeductionsCategory(pd);
		List<User> userList = userService.listAllUsers(pd);
		pd = payrollService.findById(pd);	//根据ID读取
		
		PageData p = payrollService.findValueByIdForView(pd);
		String username = p.getString("USERNAME");
		pd.put("USERNAME", username);
		
		String department = p.getString("DEPARTMENTNAME");
		pd.put("DEPARTMENTNAME", department);
		
		//check if the viewer is Admin and disable the CONFIRM Button at the front page
		String user = Jurisdiction.getUsername();
		pd.put("user", user);
		mv.setViewName("system/payroll/payroll_view");
		mv.addObject("allowanceList", allowanceList);
		mv.addObject("deductionList", deductionList);
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/handle")
	public ModelAndView handle() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"确认");
		//Error to be Noted: if this line below is allowed the handle button can't be executed because the edit is set to be null
		/*if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
*/		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String msg = pd.getString("msg");
		if(!"admin".equals(Jurisdiction.getUsername())){
			if("yes".equals(msg)){
				payrollService.accept(pd);
			}
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
		List<SalaryCategory> allowanceList = allowanceCategoryService.listAllAllowancesCategory(pd);
		List<SalaryCategory> deductionList = deductionCategoryService.listAllDeductionsCategory(pd);
		List<User> userList = userService.listAllUsers(pd);
		mv.setViewName("system/payroll/payroll_edit");
		mv.addObject("msg", "save");
		mv.addObject("allowanceList", allowanceList);
		mv.addObject("deductionList", deductionList);
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
		List<SalaryCategory> allowanceList = allowanceCategoryService.listAllAllowancesCategory(pd);
		List<SalaryCategory> deductionList = deductionCategoryService.listAllDeductionsCategory(pd);
		List<User> userList = userService.listAllUsers(pd);
		pd = payrollService.findById(pd);//根据ID读取	
		mv.setViewName("system/payroll/payroll_edit");
		mv.addObject("allowanceList", allowanceList);
		mv.addObject("deductionList", deductionList);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Payroll");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			payrollService.deleteAll(ArrayDATA_IDS);
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
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Bank到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("员工工号");	//1
		titles.add("员工用户名");	//2
		titles.add("部门");	//3
		titles.add("工资月份");	//4
		titles.add("其本工资 (元)");	//5
		titles.add("全津贴 (元)");	//6
		titles.add("全扣除 (元)");	//7
		titles.add("净工资 (元)");	//8
		dataMap.put("titles", titles);
		List<PageData> varOList = payrollService.listForExcel(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("BIANMA"));	    //1
			vpd.put("var2", varOList.get(i).getString("USERNAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("DEPARTMENTNAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("DATE"));	    //4
			vpd.put("var5", varOList.get(i).getString("SALARY_AMOUNT"));	    //5
			vpd.put("var6", varOList.get(i).getString("TOTAL_ALLOWANCE"));	    //6
			vpd.put("var7", varOList.get(i).getString("TOTAL_DEDUCTION"));	    //7
			vpd.put("var8", varOList.get(i).getString("NET_SALARY"));	    //8
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
