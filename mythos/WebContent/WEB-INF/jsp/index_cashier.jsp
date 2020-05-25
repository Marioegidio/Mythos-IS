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
                  <div style='width:100%;' id='internal-container'>
                  	<hr style='margin-top:5px;background-color:#fff;  border: 1px solid white;width:100%;'>
					<h2 style='margin-bottom:15px; cursor:pointer;' onclick='openReservation()'>
						<i class="fas fa-plus-circle"></i> Nuova Prenotazione 
					</h2>
					<div style='display:block;' id='reservationSection'>
						<br>
						<h6 style='text-align: center; margin-bottom: 2%;'>Seleziona un tavolo in verde per prenotarlo</h6>
						<form action="ReserveTable" method='post' style="width: 100%; margin: auto;" accept-charset="UTF-8">
	
							<jsp:include page="table.jsp" />
	
							<br>
							<h3> Hai selezionato il tavolo <span id='table'>...</span> </h3>
							<br> <input type='hidden' name='idTable' id='hidden_table'>
							<input type='text' name='reserv_name' class="form-control" placeholder='Prenotato a nome di...' required><br>
							<div style='display: inline;text-align:center;'>
								<input type="number" name="people_num" class="form-control"
									style='width: 50%; margin:auto;vertical-align: middle;' min=0
									required step="1" placeholder='Numero di persone'> 
								<!-- <input
									type="number" name="budget" class="form-control"
									style='width: 49%; float: right; vertical-align: middle;'
									required step="1" min=0 placeholder='Budget'> -->
							</div>
							<br><br><br><br><br>
							<button type="submit" class="btn btn-primary btn-lg btn-block" onclick="return reserve()">
								<i class="fas fa-check"></i> Prenota
							</button>
							<br><br>
							</form>
				</div>
					<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
					
                  <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openAssignedTable()'>
                  	<i class="fas fa-clipboard-check"></i> Tavoli assegnati
				 </h2>
                   <div class='table-responsive' style='width:100%;display:none;' id='assignedTableSection'>
                   
                     <table border=0 class="table" id='assignedTables' style='background-color:white; text-align:center;'>
                     
					  <thead class="thead-dark">
					    <tr>
					      <th scope="col" style="width:10px;"></th>
					      <th scope="col">Tavolo</th>
					      <th scope="col">Nome Prenotazione</th>
					      <th scope="col">Luxus</th>
					      <th scope="col">#Persone</th>
					      <th scope="col" colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Budget</th>
					      
					      <!-- <th scope="col"><i class="fas fa-plus-circle"></i></th> -->
					      <th style='width:10px;'></th>
					      <th style='width:10px;'></th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach items="${tablesAssigned}" var='table'>
							<tr>
						      <th scope="row"><i class="fas fa-circle" style='color:#32CD32;font-size:20px;'></i></th>
						      <td style=''><c:out value="${table.getTableNumber()} (${table.getTableName()})"/></td>
						      <td><input type="text" id="reserveName<c:out value="${table.getTableNumber()}"/>" class="form-control" value="<c:out value="${table.getReservationName()}"/>"></td>
						      <c:choose>
						      	<c:when test="${table.isLuxus()}">
						      		<td>Sì</td>
						      	</c:when>
						      	<c:otherwise>
						      		<td>No</td>
						      	</c:otherwise>
						      </c:choose>
						      <td><input type='number' min=0 value='<c:out value="${table.getPeopleNumber()}"/>' id='people<c:out value="${table.getTableNumber()}"/>' style='border-width:0.5px;border-radius:3px;width:55px;text-align:center;'></td>
						      <td style='text-align:right;'><c:out value="${table.getBudget()}"/></td>
						      <td style='text-align:left;'>€</td>
						     <!--  <td style="width:30px;"><input type='number' placeholder='0' min='-<c:out value="${table.getBudget()}"/>' id='addBudget<c:out value="${table.getTableNumber()}"/>' style='border-radius:3px;border-width:0.5px;width:55px;text-align:center;'></td> -->
						      <td><i class="fas fa-check-circle" style='color:#32CD32; font-size:25px; cursor:pointer;' onclick="mod(<c:out value="${table.getTableNumber()}"/>,<c:out value="${table.getBudget()}"/>)"></i></td>
						      <td><i class="fas fa-times" style='color:#d11a2a; font-size:25px; cursor:pointer;' onclick="if(confirm('Eliminare la prenotazione?')) del(<c:out value="${table.getTableNumber()}"/>)"></i></td>
						    </tr>
						</c:forEach>
					  </tbody>
					</table>
				  </div>
					<hr style='margin-top:100px;background-color:#fff;  border: 1px solid white;width:100%;'>
					
					 <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openNotAssignedTable()'>
					 	<i class="fas fa-tags"></i> Tavoli disponibili
					 </h2>
                     <table class="table" id='notAssignedTables' border=0 style='background-color:white; display:none; text-align:center;'>
                     
					  <thead class="thead-dark">
					    <tr>
					      <th scope="col"></th>
					      <th scope="col">Tavolo</th>
					      <th scope="col">Nome </th>
					      <th scope="col">Capacit&agrave;</th>
					      <th scope="col">Luxus</th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach items="${tablesNotAssigned}" var='table'>
							<tr>
						      <th scope="row"><i class="fas fa-circle" style='color:#f9d532; font-size:20px;'></i></th>
						      <td><c:out value="${table.getTableNumber()}"/></td>
						      <td><c:out value="${table.getTableName()}"/></td>
						      <td><c:out value="${table.getCapacity()}"/></td>
						      <c:choose>
						      	<c:when test="${table.isLuxus()}">
						      		<td>Sì</td>
						      	</c:when>
						      	<c:otherwise>
						      		<td>No</td>
						      	</c:otherwise>
						      </c:choose>
						    </tr>
						</c:forEach>
					  </tbody>
					</table>
					
					
					<hr style='margin-top:100px;background-color:#fff;  border: 1px solid white;width:100%;'>
					
					 <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openListEntrance()'>
						 <i class="fas fa-list-alt"></i> Ingresso in lista
					 </h2>
					 <div style="display:none;width:100%;" id='listEntranceSection'>
					 	<br>
							<form style='display:inline;width:100%;'>
							<div style='display:inline;width:100%;' >
								<input type="text" id="pr_lastname" class="form-control" onchange='uploadList()' style='width:49%; float:left;' placeholder='Cognome pr o cliente...'>
								<input type="text" id="pr_name" class="form-control" onchange='uploadList()' style='width:49%; float:right;' placeholder='Nome pr o cliente...'><br><br>
								<input type="text" id="client_id" class="form-control" onchange='uploadListById()' style='margin-top:10px;' placeholder='Codice cliente...'>
								
							</div>
							<button style='margin-top:20px;' type="button" class="btn btn-primary btn-lg btn-block" onclick="">
								<i class="fas fa-search"></i> Cerca
							</button>
							<br><br><br><br>
							</form>
							<div class='table-responsive' style="width:99%">
			                     <table class="table" border=0 style='background-color:white; text-align:center;'>
			                     
								  <thead class="thead-dark">
								  	<tr>
								  	  <th scope="col">Id</th>
								  	  <th scope="col">Cognome</th>
								      <th scope="col">Nome</th>
									  <th scope="col">Data</th>
									  <th scope='col'>Pr</th>
									  <th scope="col">Entrato</th>
								  	</tr>
								  </thead>
								  <tbody id='clientList'>
								  </tbody>
								</table>
							</div>
					</div>
                  </div><!-- chiude il div contenitore -->
					
                   </div><!-- chiude il div row -->
               </section>
        </section>
<jsp:include page="footer.html"/>
<script>


	<%
		String error=request.getParameter("errorMessage");
		if(error!=null)
			out.print("printError('"+error+"')");
	%>
	
	/*FUNZIONI PER APRIRE LE VARIE SEZIONI*/
	function openReservation(){
		$("#reservationSection").fadeToggle(200);
	}
	function openAssignedTable(){
		$("#assignedTableSection").fadeToggle(200);
	}
	function openNotAssignedTable(){
		$("#notAssignedTables").fadeToggle(200);
	}
	function openListEntrance(){
		$("#listEntranceSection").fadeToggle(200);
	}
	/***********************************/
	function CloseAlert() {
		$("#alertBox").hide();
	}
	function OpenAlert() {
		//$("#alertBox").show(70);

		$('.toast').toast('show');
	}

	function printError(str){
		$("#msg").text(str);
		OpenAlert();
	}
	
	function reserve() {

		var id = $("#hidden_table").val();

		if (id == 0) {
			$("#msg").text("Inserisci Tavolo'");
			OpenAlert();
			return false;
		}
		
		return true;
		//location.href="PreviewOrder?id=" + id;
	}
	
	function del(id){
		$.get("RemoveReservationTable?id=" + id, 
				function(data){
					$('body').load(document.URL);
					openAssignedTable();
		});
	}
	
	//questa funzione mi aggiungera' direttamente il budget in base a  quante perosno in piu' sono state inserite oppure tolte.
	//Non resettara' il budget che gia' c'era
	function mod(id,actualBudget){
		
		var peop=$('#people'+id).val();
		var name=$('#reserveName'+id).val();

		if(peop<0){
			$("#msg").text("Non puoi inserire un numero di persone negativo. ");
			OpenAlert();	
			return;
		}
	   
		$.get("EditTableReservation?id=" + id+"&people="+peop+"&name="+name, 
				function(data){

					updatePage();
					$("#assignedTableSection").show();
					
					if(data.length==0)
						$("#msg").text("Prenotazione modificata");
					else
						$("#msg").text(data);
					
					OpenAlert();	
		});
	}
	
function uploadList(){

		var prName=" ";
		var prLastname=" ";
		prName=$("#pr_name").val().trim();
		prLastname=$("#pr_lastname").val().trim();
		if(prName.trim().length==0 && prLastname.trim().length==0){
			$("#clientList").html("");
			return;
		}
		
		prClients="";
		$.get(encodeURI("RetrieveClientsByPr?prName="+prName+"&prLastname="+prLastname), 
			function(data){
				try {
					var result = JSON.parse(data);
				}catch(err) {
					alert(data+" Riprova fra po!");
					return;
				}
				
				prClients=uploadTableClientList(result);
				
				$("#clientList").html(prClients);
			});
		
		$("#pr_name").val("");
		$("#pr_lastname").val("");
	}
	
	function uploadListById(){
		
		var client_id=" ";
		client_id=$("#client_id").val().trim();
		if(client_id.length==0){
			$("#clientList").html("");
			return;
		}
		
		prClients="";
		$.get(encodeURI("RetrieveClientById?id="+client_id), 
			function(data){
				try {
					var result = JSON.parse(data);
				}catch(err) {
					alert(data+" Riprova fra po!");
					return;
				}
				
				prClients=uploadTableClientList(result);
				
				$("#clientList").html(prClients);
			});
		
		$("#client_id").val("");
	}
	
	function uploadTableClientList(result){
		prClients="";
		if(result.length==0){
			$("#msg").text("Cliente non in lista!!");
			OpenAlert();
			$("#clientList").html("");
			return;
		}
		$.each(result, function(i, prClient){
			prClients+="<tr>"+
						   "<td style='vertical-align:middle;'>"+prClient.client_id+"</td>"+
			 			   "<td style='vertical-align:middle;'>"+prClient.surname+"</td>"+
	    				   "<td style='vertical-align:middle;'>"+prClient.name+"</td>"+
	    				   "<td style='vertical-align:middle;'>"+prClient.added_date+"</td>"+
	    				   "<td style='vertical-align:middle;'>"+prClient.pr.name+" "+prClient.pr.lastname+"</td>";
			   
			   if(prClient.entered==1)
				   prClients+="<td style='vertical-align:middle;'><i class='fas fa-circle' style='color:#32CD32;font-size:20px;'></i></td>";
			   else 
				   prClients+="<td style='vertical-align:middle;'><i class='fas fa-circle' onclick=\"changeClientStatus("+prClient.client_id+")\" style='color:#e10b00;font-size:20px; cursor:pointer;'></i></td>";   
			   
			   prClients+="</tr>";
		});	
		
		return prClients;
	}
	
	function changeClientStatus(client_id){
		$.get("UpdateClientStatus?id="+client_id, 
				function(data){
					try {
						var result = JSON.parse(data);
						uploadList();
					}catch(err) {
						alert(data+" Riprova fra po!");
						return;
					}
				});
	}
	
	$( document ).ready(function() {
		 $(".td-available").on("click", function() {
				id=this.id;
				$("#hidden_table").val(id);
				$("#table").text(id);
		 }); 
		 
		$(".td-unavailable").on("click", function() {
				id = this.id;
				$("#msg").text("Tavolo "+ id +" non disponibile!");
				OpenAlert();
		});   
	});
	
	
	
	/*FUNZIONE PER AGGIORNARE LA PAGINA IN BACKGROUND*/
	function reloadPage(){
	resp="";
	jQuery.ajaxSetup({async:false});
	
	$.get(document.URL,
			function(data){
				resp=data;
			});
	jQuery.ajaxSetup({async:true});
	return resp;
}

function updatePage(){
	$('body').html(reloadPage());
}
	
	
</script>
</body>
</html>