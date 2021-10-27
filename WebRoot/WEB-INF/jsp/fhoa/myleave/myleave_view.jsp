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
					
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<form action="myleave/handle.do" name="Form" id="Form" method="post">
							<input type="hidden" name="MYLEAVE_ID" id="MYLEAVE_ID" value="${pd.MYLEAVE_ID}"/>
							<input type="hidden" name="msg" id="msg" value=""/>
							<tr>
								<td style="padding-top: 13px;">
<%-- 								<c:if test="${pd.TYPE != '2' }">
								发信人：${pd.TO_USERNAME}&nbsp;&nbsp;
								收信人：${pd.FROM_USERNAME}&nbsp;&nbsp;
								</c:if> --%>
								<%-- <c:if test="${pd.TYPE == '2' }"> --%>
								请假人：${pd.USERNAME}&nbsp;&nbsp;&nbsp;&nbsp;
								类型：${pd.TYPE}&nbsp;&nbsp;&nbsp;&nbsp;
								时长：${pd.WHENLONG}&nbsp;&nbsp;&nbsp;&nbsp;
								<%-- </c:if> --%>
								发请假时间：${pd.SEND_TIME}
							</tr>
							<tr>
								<td>${pd.REASON}</td>
							</tr>
							<c:if test="${null == pd.user or pd.user == 'admin' }">
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-success" onclick="handle('yes');"><i class="ace-icon glyphicon glyphicon-ok"></i>批准</a>
									<%-- <c:if test="${QX.Reject == 1 }"> --%>
										<a class="btn btn-mini btn-primary" onclick="handle('no');"><i class="ace-icon glyphicon glyphicon-remove"></i>驳回</a>
									<%-- </c:if> --%>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>					
							</tr>
							</c:if>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
							</form>
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
	<script type="text/javascript">
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor_full/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/lang/zh-cn/zh-cn.js"></script>
	
	
	<!-- 百度富文本编辑框-->
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