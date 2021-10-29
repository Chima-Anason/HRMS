<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
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
						<form action="allowanceCategory/${msg}.do" name="form1" id="form1"  method="post">
						<input type="hidden" name="CAT_ID" id="id" value="${pd.CAT_ID}"/>
						<input name="PARENT_ID" id="parent_id" value="${pd.parent_id }" type="hidden">
							<div id="zhongxin" style="padding-top:13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top:15px;">
								
								<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">津贴名称:</td>
								<td><input type="text" name="CAT_NAME" id="categoryName" value="${pd.CAT_NAME}" maxlength="70" placeholder="这里输入津贴名称" title="津贴名称" style="width:98%;"/></td>
								</tr>
								<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">英文名:</td>
								<td><input type="text" name="EN_NAME" id="englishName" value="${pd.EN_NAME}" maxlength="70" placeholder="这里输入英文名" title="英文名称" style="width:98%;"/></td>
								</tr>
								<tr>
								<td class="center" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
							</table>
							</div>
						</form>
					
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>

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
	<%@ include file="../index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	top.hangge();
	//保存
	function save(){
		 if($("#categoryName").val()==""){
			$("#categoryName").tips({
				side:3,
	            msg:'请输入津贴名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#categoryName").focus();
			return false;
		}
		 if($("#englishName").val()==""){
			$("#englishName").tips({
				side:3,
	            msg:'请输入英文名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#englishName").focus();
			return false;
		}
		
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
	
	</script>
</body>
</html>

