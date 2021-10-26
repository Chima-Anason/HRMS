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
					
					<form action="training/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="TRAINING_ID" id="TRAINING_ID" value="${pd.TRAINING_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td>
									<div class="input-group bootstrap-timepicker">
									<input readonly="readonly" class="form-control" type="text" name="STARTTIME" id="STARTTIME" value="${pd.STARTTIME}" maxlength="100" placeholder="这里输入开始时间" title="开始时间" style="width:100%;"/>
									<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
									</div>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td>
									<div class="input-group bootstrap-timepicker">
									<input readonly="readonly" class="form-control" type="text" name="ENDTIME" id="ENDTIME" value="${pd.ENDTIME}" maxlength="100" placeholder="这里输入结束时间" title="结束时间" style="width:100%;"/>
									<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">培训名称:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="50" placeholder="这里输入培训名称" title="培训名称" style="width:76%;"/></td>
								<td style="width:75px;text-align: right;padding-top: 13px;">培训时长:</td>
								<td><input type="text" name="HOW_LONG" id="HOW_LONG" value="${pd.HOW_LONG}" maxlength="50" placeholder="这里输入时长" title="时长" style="width:76%;"/>&nbsp;天/时</td>
							</tr>
							<tr>
									<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
									<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" title="备注" style="width:98%;" /></td>
									<td style="width:79px;text-align: right;padding-top: 13px;">状态:</td>
									<td>
										<select name="STATUS" title="状态">
										<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >进行中</option>
										<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >完成</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="center" colspan="6">
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		$(function() {
			//日期框(带时间)
			$('.form-control').datetimepicker().next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
		});
		
		//初始分类,调用数据字典
		$(function() {
			var TYPE = "${pd.TYPE}";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
		    	data: {DICTIONARIES_ID:'ce174640544549f1b31c8f66e01c111b'},//ce174640544549f1b31c8f66e01c111b 为请假类型ID
				dataType:'json',
				cache: false,
				success: function(data){
					 $.each(data.list, function(i, dvar){
						 if(TYPE == dvar.BIANMA){
							 $("#TYPE").append("<option value="+dvar.NAME+" selected='selected'>"+dvar.NAME+"</option>");
						 }else{
							 $("#TYPE").append("<option value="+dvar.NAME+">"+dvar.NAME+"</option>");
						 }
					 });
				}
			});
		});
		
		//保存
		function save(){
			if($("#STARTTIME").val()==""){
				$("#STARTTIME").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STARTTIME").focus();
			return false;
			}
			if($("#ENDTIME").val()==""){
				$("#ENDTIME").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENDTIME").focus();
			return false;
			}
			if($("#ENDTIME").val() < $("#STARTTIME").val){
				$("#ENDTIME").tips({
					side:3,
		            msg:'结束时间不能小于开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENDTIME").focus();
			return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入培训名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#HOW_LONG").val()==""){
				$("#HOW_LONG").tips({
					side:3,
		            msg:'请输入时长',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#HOW_LONG").focus();
			return false;
			}
			/* if($("#BZ").val()==""){
				$("#BZ").tips({
					side:3,
		            msg:'请输入事由',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#REASON").focus();
			return false;
			} */
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>