<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv = "Content-Type" content = "text/html; charset=utf-8" />
	<title>Mythos Club-<c:out value="${param.title}"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<!-- <link rel="stylesheet" type="text/css" href="css/poupup.css"> -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	<link rel="shortcut icon" href="images/favicon.png" />   
	<link rel="stylesheet" href="css/swiper.min.css">
    <link rel="stylesheet" href="css/slide.css">  
    <link rel="stylesheet" href="css/maptable.css">  
    
    <script src="js/jquery.min.js"></script>
  	<script src="js/swiper.min.js"></script>
	
</head>
<body  style="-moz-user-select: none; -webkit-user-select: none; -ms-user-select:none; user-select:none;-o-user-select:none;" 
		 unselectable="on"
		 onselectstart="return false;">
	<header>
		<nav class="container-fluid ">
			<div class="row text-center" style='display:inline;font-size:25px;'>
				<div class="logo" style="vertical-align:middle;float:left;margin-top:8px; margin-bottom:5px;">
					<a class="navbar-brand" href="#" style="border-bottom:4px solid #5A565E; border-radius:90px; padding:13px; margin:auto; background-color:#FFFFF0;"> 
                        <img src="images/logo.png" style=" margin-top:10px;width: 200px" alt="Logo">
                    </a>
				</div>
				
				<div  class='headSpan' style='vertical-align:middle;'>

					<c:if test="${userLogged.getRole().getIdRole()==1}">
						<span><i class="fas fa-mobile"></i>&nbsp;&nbsp;<c:out
								value=" ${userLogged.getUsername()} "></c:out></span>
					</c:if>
					<c:if test="${userLogged.getRole().getIdRole()==2}">
						<span><i class="fas fa-tablet"></i>&nbsp;&nbsp;<c:out
								value=" ${userLogged.getUsername()} "></c:out></span>
					</c:if>
					<c:if test="${userLogged.getRole().getIdRole()==3}">
						<span><i class="fas fa-user"></i>&nbsp;&nbsp;<c:out
								value=" ${userLogged.getUsername()} "></c:out></span>
					</c:if>


					<div class="" style='vertical-align:middle;font-size:30px;margin-top:0;padding:0;float: right;z-index:21;'>
						<a href="Logout" style='color:white;'> <i class="fas fa-sign-out-alt"></i></a>
					</div>
				</div>
			</div>

		</nav>
	</header>

	
</body>