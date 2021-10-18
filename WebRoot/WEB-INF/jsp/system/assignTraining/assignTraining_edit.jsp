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
					
					<form action="assignTraining/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ASS_ID" id="ASS_ID" value="${pd.ASS_ID}"/>
						<textarea style="display: none;" name="TRAINING_IDS" id="TRAINING_IDS" >${pd.TRAINING_IDS }</textarea>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">培训:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="TRAINING_ID" id="training_id" data-placeholder="请选择培训" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${trainingList}" var="training">
												<option value="${training.TRAINING_ID }" <c:if test="${training.TRAINING_ID == pd.TRAINING_ID }">selected</c:if>>${training.NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="TRAINING_ID" id="training_id" value="${pd.TRAINING_ID }" type="hidden" />
										</c:if>
							<c:if test="${fx != 'head'}">
										<tr>

  												<td style="width:79px;text-align: right;padding-top: 13px;">用户:</td>
  												<td ><input type="text" class="form-control" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="200" placeholder="请选择用户" title="用户" aria-label="USERNAME" aria-describedby="USER_ID" readonly></td>
  												<td id="juese2"><button class="btn btn-outline-secondary" type="button" name="USER_ID" id="USER_ID"  onclick="xuanTp('USER_ID');">选择用户</button></td>
										</tr>
										</c:if>
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
		
		  if($("#training_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择培训',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#training_id").focus();
			return false;
		}   
		
		
		  if($("#USER_ID").val()==""){
			$("#juese2").tips({
				side:3,
	            msg:'选择用户',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USER_ID").focus();
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
		     var id = diag.innerFrame.contentWindow.document.getElementById('xzid').value;
		     var xzValue = diag.innerFrame.contentWindow.document.getElementById('xzvalue').value;
		     
			 //$("#"+ID).val(xzValue);
			  //$("#"+id).val(id);
			 alert("id : " + id + " xzvalue " + xzValue);
			 diag.close();
		 };
		 diag.show();
		 
		
	}
	
	
	//移除副职角色
	function removeTrainingId(TRAINING_ID){
		var OTRAINING_IDS = $("#TRAINING_IDS");
		var TRAINING_IDS = OTRAINING_IDS.val();
		TRAINING_IDS = TRAINING_IDS.replace(TRAINING_ID+",fh,","");
		OTRAINING_IDS.val(TRAINING_IDS);
	}
	//添加副职角色
	function addTrainingId(TRAINING_ID){
		var OTRAINING_IDS = $("#TRAINING_IDS");
		var TRAINING_IDS = OTRAINING_IDS.val();
		if(!isContains(TRAINING_IDS,TRAINING_ID)){
			TRAINING_IDS = TRAINING_IDS + TRAINING_ID + ",fh,";
			OTRAINING_IDS.val(TRAINING_IDS);
		}
	}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>