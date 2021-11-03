<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					<div class="span6">
					
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 10px;">
							<th class="center" colspan="10">工资信息</th>
							<form action="payroll/handle.do" name="Form" id="Form" method="post">
							<input type="hidden" name="SALARY_ID" id="SALARY_ID" value="${pd.SALARY_ID}"/>
							<input type="hidden" name="msg" id="msg" value=""/>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">收讫人</td>
								<td style="padding-top: 10px;">${pd.USERNAME}</td>		
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">收讫部门：</td>
								<td style="padding-top: 10px;">${pd.DEPARTMENTNAME}</td>		
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">付款时间：</td>
								<td style="padding-top: 10px;">${pd.SEND_TIME} </td>		
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">工资月份:：</td>
								<td style="padding-top: 10px;">${pd.DATE} </td>		
							</tr>
							
							
							</form>
						</table>
						<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top: 10px;">
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">津贴:</td>
								<td style="padding-top: 10px;">  ${pd.ALLOWANCE1} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">津贴币值:</td>
								<td style="padding-top: 10px;">  ${pd.ALLOWANCE1_AMOUNT} </td>	
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">其他津贴:</td>
								<td style="padding-top: 10px;">  ${pd.ALLOWANCE2} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">其他津贴币值:</td>
								<td style="padding-top: 10px;">  ${pd.ALLOWANCE2_AMOUNT} </td>	
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">扣除:</td>
								<td style="padding-top: 10px;">  ${pd.DEDUCTION1} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">扣除币值:</td>
								<td style="padding-top: 10px;">  ${pd.DEDUCTION1_AMOUNT} </td>	
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">其他扣除:</td>
								<td style="padding-top: 10px;">  ${pd.DEDUCTION2} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">其他扣除币值:</td>
								<td style="padding-top: 10px;">  ${pd.DEDUCTION2_AMOUNT} </td>	
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">全津贴 :</td>
								<td style="padding-top: 10px;"> ${pd.TOTAL_ALLOWANCE} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">全扣除 :</td>
								<td style="padding-top: 10px;"> ${pd.TOTAL_DEDUCTION} </td>	
							</tr>
							
							<tr>	
								<td style="width:75px;text-align: right;padding-top: 10px;">其本工资:</td>
								<td style="padding-top: 10px;"> ${pd.SALARY_AMOUNT} </td>	
								<td style="width:75px;text-align: right;padding-top: 10px;">净工资 :</td>
								<td style="padding-top: 10px;"> ${pd.NET_SALARY} </td>	
							</tr>
							
							
							
							<c:if test="${null == pd.user or pd.user != 'admin' }">
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-success" onclick="handle('yes');"><i class="ace-icon glyphicon glyphicon-ok"></i>确认</a>
									
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>					
							</tr>
							</c:if>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
							<!-- </form> -->
						</table>
						</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->

		<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 百度富文本编辑框-->
	<%-- <script type="text/javascript">
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor_full/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/lang/zh-cn/zh-cn.js"></script> --%>
	
	<!-- 确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		//办理任务
		function handle(msg){
			$("#msg").val(msg);
			 /* $("#OPINION").val(getContent());
			if($("#OPINION").val()==""){
				$("#omsg").tips({
					side:3,
		            msg:'请输入审批意见',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPINION").focus(); 
			return false;
			}  */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>