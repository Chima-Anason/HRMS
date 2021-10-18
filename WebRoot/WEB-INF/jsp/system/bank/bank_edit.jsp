<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
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
								<form action="bank/${msg }.do" name="bankForm" id="bankForm" method="post">
									<input type="hidden" name="BANK_ID" id="bank_id" value="${pd.BANK_ID }"/>
									<%-- <textarea style="display: none;" name="CATEGORY_IDS" id="CATEGORY_IDS" >${pd.CATEGORY_IDS }</textarea> --%>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<%-- 
										<c:if test="${fx == 'head'}">
											<input name="CATEGORY_ID" id="category_id" value="${pd.CATEGORY_ID }" type="hidden" />
										</c:if> --%>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">银行账号:</td>
										
											<td><input type="text" name="ACC_NO" id="ACC_NO" value="${pd.ACC_NO }" maxlength="32" placeholder="这里输入银行账号" title="银行账号" onblur="hasN('${pd.ACC_NO }')" style="width:99%;"/></td>
										</tr>
										
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">银行账户名:</td>
											<td><input type="text" name="ACC_NAME" id="ACC_NAME"value="${pd.ACC_NAME }" placeholder="这里输入银行账户名" maxlength="64" title="银行账户名" style="width:98%;"/></td>
										</tr>
										
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">银行名:</td>
											<td><input type="text" name="BANK_NAME" id="BANK_NAME"value="${pd.BANK_NAME }" placeholder="这里输入银行名" maxlength="64" title="银行名" style="width:98%;"/></td>
										</tr>
										
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		 $('#form-field-select-4').addClass('tag-input-style');
		if($("#bank_id").val()!=""){
			/* $("#building_name").attr("readonly","readonly");
			$("#building_name").css("color","gray"); */
		} 
	});
	//保存
	function save(){
		/* if($("#category_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择类别',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#category_id").focus();
			return false;
		}
		 */
		
		if($("#ACC_NO").val()==""){
			$("#ACC_NO").tips({
				side:3,
	            msg:'输入银行账号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#ACC_NO").focus();
			return false;
		}else{
			$("#ACC_NO").val($.trim($("#ACC_NO").val()));
		}
		
		
		
		if($("#ACC_NAME").val()==""){
			$("#ACC_NAME").tips({
				side:3,
	            msg:'输入银行账户名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#ACC_NAME").focus();
			return false;
		}else{
			$("#ACC_NAME").val($.trim($("#ACC_NAME").val()));
		}
		
		
		if($("#BANK_NAME").val()==""){
			$("#BANK_NAME").tips({
				side:3,
	            msg:'输入银行名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#BANK_NAME").focus();
			return false;
		}else{
			$("#BANK_NAME").val($.trim($("#BANK_NAME").val()));
		}
		

			$("#bankForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		
	}
	
	//判断编码是否存在
	function hasN(ACC_NO){
		var ACC_NO = $("#ACC_NO").val();
		
		$.ajax({
			type: "POST",
			url: '<%=basePath%>bank/hasN.do',
	    	data: {ACC_NO:ACC_NO,ACC_NO:ACC_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#ACC_NO").tips({
							side:3,
				            msg:'帐号'+ACC_NO+'已存在!',
				            bg:'#AE81FF',
				            time:3
				        });
					    $("#ACC_NO").val('');   
				 }
			}
		});
	}
	
	<%-- //判断用户名是否存在
	function hasU(){
		var ROOM_NAME = $.trim($("#room_name").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>room/hasU.do',
	    	data: {ROOM_NAME:ROOM_NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#roomForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#building_name").css("background-color","#D16E6C");
					setTimeout("$('#room_name').val('此用户名已存在!')",500);
				 }
			}
		});
	} --%>
	
	
	
	//判断编码是否存在
	<%-- function hasN(BUILDING_NAME){
		var NUMBER = $.trim($("#BUILDING_NO").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>room/hasN.do',
	    	data: {ROOM_NO:ROOM_NO,ROOM_NAME:ROOM_NAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#ROOM_NO").tips({
							side:3,
				            msg:'编号 '+ROOM_NO+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#ROOM_NO").val('');
				 }
			}
		});
	}
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	}); --%>
	
	/* //移除副职角色
	function removeCategoryId(CATEGORY_ID){
		var OCATEGORY_IDS = $("#CATEGORY_IDS");
		var CATEGORY_IDS = OCATEGORY_IDS.val();
		CATEGORY_IDS = CATEGORY_IDS.replace(CATEGORY_ID+",fh,","");
		OCATEGORY_IDS.val(CATEGORY_IDS);
	}
	//添加副职角色
	function addCategoryId(CATEGORY_ID){
		var OCATEGORY_IDS = $("#CATEGORY_IDS");
		var CATEGORY_IDS = OCATEGORY_IDS.val();
		if(!isContains(CATEGORY_IDS,CATEGORY_ID)){
			CATEGORY_IDS = CATEGORY_IDS + CATEGORY_ID + ",fh,";
			OCATEGORY_IDS.val(CATEGORY_IDS);
		}
	} */
	function isContains(str, substr) {
	     return str.indexOf(substr) >= 0;
	 }
</script>
</html>