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
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<!-- 树形下拉框start -->
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
					
						
						
						<form action="payroll/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SALARY_ID" id="SALARY_ID" value="${pd.SALARY_ID}"/>
						<input type="hidden" name="USER_ID" id="USER_ID" value=""/>
						<div id="zhongxin" style="padding-top: 13px;">
						<c:if test="${fx != 'head'}">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						    <tr>
							    <td style="width:75px;text-align: right;padding-top: 13px;">用户:</td>
							    
							    <td id="juese2"><input type="text" class="form-control" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="200" placeholder="请选择用户" title="用户" aria-label="USERNAME" aria-describedby="USER_ID" readonly></td>
  								<td id="juese3"><button type="button" class="btn btn-primary" name="USERNAME" id="USERNAME"  onclick="xuanTp('USERNAME');">选择用户</button></td>
							</tr>
						</table>
							
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								
								
								<td style="width:75px;text-align: right;padding-top: 13px;">工资月份:</td>
								<td><input class="span10 date-picker" name="DATE" id="DATE" value="${pd.DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="工资月份" title="工资月份" style="width:98%;"/></td>
									
								<td style="width:75px;text-align: right;padding-top: 13px;">其本工资:</td>
								<td><input type="number" name="SALARY_AMOUNT" id="SALARY_AMOUNT" value="${pd.SALARY_AMOUNT}" maxlength="50" placeholder="这里输入其本工资" title="其本工资" style="width:98%;"/>&nbsp;元</td>
							</tr>
							
						</table>
						</c:if>
						
						 <c:if test="${fx == 'head'}">
											<input name="USERNAME" id="USERNAME" value="${pd.USERNAME }" type="hidden" />
										</c:if> 
								
								
						
						<table id="table_report" class="table table-striped table-bordered table-hover">
						    <th class="left" colspan="10">津贴</th>
						    
							<tr>
							     <td style="width:75px;text-align: right;padding-top: 13px;">津贴:</td>
								 <td id="juese4">
									<select class="chosen-select form-control" name="ALLOWANCE1" id="ALLOWANCE1" data-placeholder="请选择津贴" style="vertical-align:top;" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${allowanceList}" var="allowance">
											<option value="${allowance.CAT_NAME }" <c:if test="${allowance.CAT_ID == pd.CAT_ID }">selected</c:if>>${allowance.CAT_NAME }</option>
										</c:forEach>
									</select>
								</td>
								
								
								<td style="width:75px;text-align: right;padding-top: 13px;">津贴币值:</td>
								<td><input type="number" name="ALLOWANCE1_AMOUNT" id="ALLOWANCE1_AMOUNT" value="${pd.ALLOWANCE1_AMOUNT}" maxlength="50" placeholder="这里输入津贴" title="津贴" style="width:98%;"/>&nbsp;元</td>
							</tr>
							
							<tr>
							     <td style="width:75px;text-align: right;padding-top: 13px;">其他津贴:</td>
								 <td id="juese">
									<select class="chosen-select form-control" name="ALLOWANCE2" id="ALLOWANCE2" data-placeholder="请选择津贴" style="vertical-align:top;" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${allowanceList}" var="allowance2">
											<option value="${allowance2.CAT_NAME }" <c:if test="${allowance2.CAT_ID == pd.CAT_ID }">selected</c:if>>${allowance2.CAT_NAME }</option>
										</c:forEach>
									</select>
								</td>
								
								
								
								<td style="width:75px;text-align: right;padding-top: 13px;">津贴币值:</td>
								<td><input type="number" name="ALLOWANCE2_AMOUNT" id="ALLOWANCE2_AMOUNT" value="${pd.ALLOWANCE2_AMOUNT}" maxlength="50" placeholder="这里输入其他津贴" title="其他津贴" style="width:98%;"/>&nbsp;元</td>
							</tr>

						</table>
								
								
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<th class="left" colspan="10">扣除</th>
							<tr>
							     <td style="width:75px;text-align: right;padding-top: 13px;">扣除:</td>
								 <td id="juese5">
									<select class="chosen-select form-control" name="DEDUCTION1" id="DEDUCTION1" data-placeholder="请选择扣除" style="vertical-align:top;" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${deductionList}" var="deduction">
											<option value="${deduction.CAT_NAME }" <c:if test="${deduction.CAT_ID == pd.CAT_ID }">selected</c:if>>${deduction.CAT_NAME }</option>
										</c:forEach>
									</select>
								</td>
								
								
								
								<td style="width:75px;text-align: right;padding-top: 13px;">扣除币值:</td>
								<td><input type="number" name="DEDUCTION1_AMOUNT" id="DEDUCTION1_AMOUNT" value="${pd.DEDUCTION1_AMOUNT}" maxlength="50" placeholder="这里输入扣除" title="扣除" style="width:98%;"/>&nbsp;元</td>
							</tr>
							
							<tr>
							     <td style="width:75px;text-align: right;padding-top: 13px;">其他扣除:</td>
								 <td id="juese">
									<select class="chosen-select form-control" name="DEDUCTION2" id="DEDUCTION2" data-placeholder="请选择其他扣除" style="vertical-align:top;" style="width:98%;" >
										<option value=""></option>
										<c:forEach items="${deductionList}" var="deduction2">
											<option value="${deduction2.CAT_NAME }" <c:if test="${deduction2.CAT_ID == pd.CAT_ID }">selected</c:if>>${deduction2.CAT_NAME }</option>
										</c:forEach>
									</select>
								</td>
								
								
								
								<td style="width:75px;text-align: right;padding-top: 13px;">扣除币值:</td>
								<td><input type="number" name="DEDUCTION2_AMOUNT" id="DEDUCTION2_AMOUNT" value="${pd.DEDUCTION2_AMOUNT}" maxlength="50" placeholder="这里输入其他扣除" title="其他扣除" style="width:98%;"/>&nbsp;元</td>
							</tr>
						</table>
						
						
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
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
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		
		//保存
		function save(){
		  /* $("#USER_ID").val(); */
		  if($("#SALARY_AMOUNT").val()==""){
			$("#SALARY_AMOUNT").tips({
				side:3,
	            msg:'请输入其本工资',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SALARY_AMOUNT").focus();
			return false;
		}   
		
		if($("#DATE").val()==""){
				$("#DATE").tips({
					side:3,
		            msg:'请输入工资月份',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DATE").focus();
			return false;
			}  
		
		 if($("#USERNAME").val()==""){
			$("#juese2").tips({
				side:3,
	            msg:'选择用户',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USERNAME").focus();
			return false;
		}      
		
		 
		
			$("#Form").submit();
			
			$("#zhongxin").hide();
			
			$("#zhongxin2").show();
		
			
		}
		
		//选择图片
	function xuanTp(ID){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="选择用户";
		 diag.URL = '<%=basePath%>user/listfortc.do';
		 diag.Width = 860;
		 diag.Height = 680;
		 diag.CancelEvent = function(){ //关闭事件
		    /*  var id = diag.innerFrame.contentWindow.document.getElementById('xzid').value;
		     var xzValue = diag.innerFrame.contentWindow.document.getElementById('xzvalue').value;
		     
			 $("#"+ID).val(xzValue);
			 $("#"+id).val(id);
			 //alert("id : " + id + " xzvalue " + xzValue); */
			 
			 
			  $("#"+ID).val(diag.innerFrame.contentWindow.document.getElementById('xzvalue').value); 

			  $("#USER_ID").val(diag.innerFrame.contentWindow.document.getElementById('xzid').value);
			 
			 diag.close();
		 };
		 diag.show();
		 
		
	}
	
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>