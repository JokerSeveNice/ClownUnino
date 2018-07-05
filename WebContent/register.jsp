<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<title>ClownUnino注册</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/bootstrap.min.css"/>
		<link rel="icon" href="<%=request.getContextPath() %>/imgs/jokericon.jpg" />
	</head>
	<style type="text/css">
		body{
			margin: 0;
			padding: 0;
			font-family: "楷体";
			background-image: url(<%=request.getContextPath()%>/imgs/zcbg.jpg);
		}
		label{
			padding-top: 5px;
			font-size: 15px;
		}
		.butterfly{
			position: absolute;
			bottom: 0;
		}
		form{
			width: 500px;
			background-color: white;
			margin: auto;
			border: 1px solid white;
			border-radius: 10px;
			opacity: 0.7;
			position: absolute;
			top:25%;
			left:32%;
			}
		.error{
			color:red;
		}
	</style>
	<body>
				
		<div class="butterfly">
			<img src="<%=request.getContextPath() %>/imgs/butterfly.png" />
		</div>
		
		
		<form  id="register" action="registerServlet" method="post" class="form-horizontal">	
			<div class="form-group">
				<div class="col-md-4 col-md-offset-5">
					<h1>注册</h1>
				</div>	
			</div>
			
			<div class="form-group">
				<div class="col-md-3 text-right">
					<label>用户名</label>
				</div>
				<div class="col-md-7">
					<input type="text" name="username" class="form-control" placeholder="请输入用户名"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-3 text-right">
					<label>密码</label>
				</div>
				<div class="col-md-7">
					<input type="password" name="password" id="password" class="form-control" placeholder="请输入密码"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-3 text-right">
					<label>确认密码</label>
				</div>
				<div class="col-md-7">
					<input type="password" name="repassword" id="repassword" class="form-control" placeholder="请再次输入密码"/>
				</div>
			</div>
			
			
			
			<div class="form-group">
				<div class="col-md-7 col-md-offset-3">
					<input type="submit" value="注册" class="btn-info form-control"/>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-5 col-md-offset-5">
					<sapn style="color:red;">${requestScope.errors}</sapn>
					<sapn style="color:red;">${requestScope.error}</sapn>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-8 col-md-offset-7">
					<span><a href="<%=request.getContextPath() %>/index.jsp">已有账号</a>|<a href="#">忘记密码?</a></span>
				</div>
			</div>
		</form>
		
		<!--
        	导入JS文件
       -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/validation/lib/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/validation/dist/jquery.validate.min.js"></script>
		
		
		<script>
		
		//验证
		$(function(){
			$("#register").validate({
				rules:{
					username:{
						required:true,
						rangelength:[5,15]
					},
					password:{
						required:true,
						rangelength:[5,20]
					},
					repassword:{
						equalTo:"#password"
					}
				},
				messages:{
					username:{
						required:"用户名不能为空！",
						rangelength:"字符串长度为5-15，请重新输入！"
					},
					password:{
						required:"密码不能为空！",
						rangelength:"密码长度为5-20，请重新输入！"
					},
					repassword:{
						equalTo:"密码不一致，请重新输入！"
					}
				}
			});
		});
			
		</script>
		

	</body>
</html>