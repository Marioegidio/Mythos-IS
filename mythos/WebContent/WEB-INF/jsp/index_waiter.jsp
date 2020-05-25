<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.*,entity.*,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp">
	<jsp:param value="Home" name="title"/>
</jsp:include>
			<div style="position: fixed; top:15%;right:1%;z-index:10;">
			    <div class="toast" role="alert" data-delay="2500" style="z-index:20; border:1px dotted #141414;">
			        <div class="toast-header">
			            <strong class="mr-auto text-primary" style='font-size:1.5em;color:#27d280!important;'><i class="fas fa-exclamation"></i> <span id="msg"></span></strong>
			            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
			                <span aria-hidden="true">×</span>
			            </button>
			        </div>
			    </div>
			</div>

			<section class="content">
             <section class="container">
                  <div class="row">
                  	  <div style="width:100%; text-align:center;">
						<h6 style='text-align: center; margin-bottom: 2%;'>Seleziona un tavolo in rosso per prendere un'ordinazione</h6>
						<div class="table-responsive">
							<jsp:include page="table.jsp"/>
						</div>
						
					  </div>
                   </div><!-- chiude il div row -->
               </section>
        </section>
<jsp:include page="footer.html"/>
<script>

	function CloseAlert() {
		$("#alertBox").hide();
	}

	function OpenAlert() {
		//$("#alertBox").show(70);

		$('.toast').toast('show');
	}
	

	//solo quando il documento è pronto associo la function al click
	$( document ).ready(function() {
		 $(".td-unavailable").on("click", function() {
				id=this.id;
				location.href="PreviewOrder?idTable="+id;
		 }); 
		 
		$(".td-available").on("click", function() {
				id = this.id;
				$("#msg").text("Il tavolo "+ id +" è vuoto.");
				OpenAlert();
		});   
	});
</script>
</body>
</html>