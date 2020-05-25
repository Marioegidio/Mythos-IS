<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.*,entity.*,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp">
	<jsp:param value="home" name="active_menu"/>
	<jsp:param value="Home" name="title"/>
</jsp:include>
	
			<div style="position: fixed; top:15%;right:1%;z-index:10; width:300px;">
			    <div class="toast" role="alert" data-delay="2500" style="z-index:20; border:1px dotted #141414; ">
			        <div class="toast-header">
			            <strong class="mr-auto text-primary" style='font-size:1.5em;color:#27d280!important;'><i class="fas fa-exclamation"></i> <span id="msg" style='min-width:100%;'></span></strong>
			            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
			                <span aria-hidden="true">×</span>
			            </button>
			        </div>
			    </div>
			</div>
			
			<section class="content" onload='uploadTotOrders()'>
             <section class="container">
                  <div class="row">
	                  <div id='internal-container' style='width:100%; margin-top:20px;'>
	                  	<hr style='margin-top:10px;background-color:#fff;  border: 1px solid white;width:100%;'>
						
						<h2 style='margin-bottom:15px; cursor:pointer;' onclick='openProducts()'>
							<i class="fas fa-clipboard-check"></i> Tutti i Prodotti<br><br><br>
						</h2>
	                   <div class='table-responsive shadow shadow-1' style='display:none;' id='productsSection'>
		                	<table class="table" border=0 style='background-color:white; text-align:center; margin-bottom:20px;'>
							  <thead class="thead-dark">
							    <tr>
							      <th scope="col">Nome</th>
							      <th scope="col">Descrizione</th>
							      <th scope="col">Prezzo €</th>
							      <th scope="col">Magazzino</th>
							      <th scope='col'>Locazione</th>
							      <th scope="col">Cambusa</th>
							      <th scope="col">Bar</th>
							      <th scope="col">Mod.</th>
							    </tr>
							  </thead>
							  <tbody>
							  <c:forEach items="${products}" var="product">
								  <c:set var='selectNone' value='' scope='page'/>
								  <c:set var='selectBar' value='' scope='page'/>
								  <c:set var='selectGalley' value='' scope='page'/>
								  <c:set var='selectBoth' value='' scope='page'/>
								  <c:set var='disabledAll' value='' scope='page'/>
								  <c:set var='disabledBar' value='' scope='page'/>
								  <c:set var='disabledGalley' value='' scope='page'/>
								  <c:if test='${product.flagType==4}'>
								  	<c:set var='style' value="style=display:none;background-color:#eeeadd;" scope='page'/>
								  </c:if>
								  <c:if test='${product.flagType!=4}'>
								  	<c:set var='style' value="" scope='page'/>
								  </c:if>
							  		<tr <c:out value="${style}"/> id='tr<c:out value="${product.getIdProduct()}"/>'>
								      <td title='<c:out value="${product.getDescription()}"/>'><c:out value="${product.getName()}"/></td>
								      <td><c:out value="${product.getDescription()}"/></td>
								      <td>
								      	€ <input type='number' min='0' value="<c:out value="${product.price}"/>" id='modPrice<c:out value="${product.getIdProduct()}"/>' step='0.25' style='border-radius:3px;border-width:0.5px;width:65px;height:30px;text-align:center;'>
								      	<!-- <img src='images/upload.png' style='cursor:pointer;width:25px;' onclick="modPrice(<c:out value="${product.getIdProduct()}"/>)"> -->
								      	</td>
								      <td>
								      	<input type='number' min='0' value="<c:out value="${product.quantityWarehouse}"/>" id='addQtyW<c:out value="${product.getIdProduct()}"/>' style='border-radius:3px;border-width:0.5px;width:55px;height:30px;text-align:center;'>
								      	<!-- <img src='images/upload.png' style='cursor:pointer;width:25px;' onclick="modWareHouse(<c:out value="${product.getIdProduct()}"/>)"> -->
								      </td>
								      <td>
								      <!-- mi serve per selezionare quale opzione della select devo mettere come anteprimas -->
								     <c:choose>
								     		<c:when test='${product.flagType==0}'>
								 				<c:set var='selectNone' value='selected' scope='page'/>
								 				<c:set var='disabledAll' value='disabled' scope='page'/>
								     		</c:when>
								     		<c:when test='${product.flagType==1}'>
								 				<c:set var='selectBar' value='selected' scope='page'/>
								 				 <c:set var='disabledGalley' value='disabled' scope='page'/>
								     		</c:when>
								     		<c:when test='${product.flagType==2}'>
								 				<c:set var='selectGalley' value='selected' scope='page'/>
								 				<c:set var='disabledBar' value='disabled' scope='page'/>
								     		</c:when>
								     		<c:when test='${product.flagType==3}'>
								 				<c:set var='selectBoth' value='selected' scope='page'/>
								     		</c:when>
								     	</c:choose>
								      	<select id='location<c:out value="${product.getIdProduct()}"/>' onchange="modFlag(<c:out value="${product.getIdProduct()}"/>)" class="form-control" style='width:auto; margin:auto;'>
								      		<option value='0' <c:out value="${selectNone}"/>>Nessuna</option>
								      		<option value='1' <c:out value="${selectBar}"/>>Bar</option>
								      		<option value='2' <c:out value="${selectGalley}"/>>Cambusa</option>
								      		<option value='3' <c:out value="${selectBoth}"/>>Entrambe</option>
								      	</select>
								      </td>
								      <td>
								      	<input type='number' min='0' <c:out value="${disabledAll}${disabledGalley}"/> value="<c:out value="${product.quantityGalley}"/>" id='addQtyG<c:out value="${product.getIdProduct()}"/>' style='border-radius:3px;border-width:0.5px;width:55px; height:30px;text-align:center;'>
								      	<!-- <img src='images/top_down.png' style='cursor:pointer;width:25px;' onclick="<c:out value="${disabledAll}${disabledGalley}"/>if(confirm('Ti sei accertato che nessuno stava facendo un ordine nel momento in cui hai cliccato?')) plusGalley(<c:out value="${product.getIdProduct()}"/>)"> -->
								      </td>
								      <td>
								      	<input type='number' min='0' <c:out value="${disabledAll}${disabledBar}"/> value="<c:out value="${product.quantityBar}"/>" id='addQtyB<c:out value="${product.getIdProduct()}"/>' style='border-radius:3px;border-width:0.5px;width:55px;height:30px;text-align:center;'>
								      	<!-- <img src='images/top_down.png' style='cursor:pointer; width:25px;' onclick="<c:out value="${disabledAll}${disabledBar}"/>plusBar(<c:out value="${product.getIdProduct()}"/>)"> -->
								      </td>
								      <td><img src='images/top_down.png' style='cursor:pointer; width:25px;' onclick="editProduct(<c:out value="${product.getIdProduct()}"/>)"></td>
								    </tr>
							  </c:forEach>
							  </tbody>
							</table>
	                	</div>
	                
	                  <hr style='margin-top:15px;background-color:#fff;  border: 1px solid white;width:100%;'>
	                 
					<!-- ********************************************************************************************* -->
	                  <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openGalley()'>
	                  	<i class="fas fa-clipboard-check"></i> Prodotti Cambusa
	                  </h2>
	                   <div class='table-responsive shadow shadow-1' style='display:none;' id='galleySection'>
	                   
	                     <table border=0 class="table" id='assignedTables' style='background-color:white; text-align:center;'>
	                     
						  <thead class="thead-dark">
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Nome</th>
						      <th scope="col">Descrizione</th>
						      <th scope="col">Quantità</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach items="${productsGalley}" var='product'>
								<tr>
							      <th scope="row"><i class="fas fa-circle" style='color:#32CD32;font-size:20px;'></i></th>
							      <td style=''><c:out value="${product.getName()}"/></td>
							      <td style=''><c:out value="${product.getDescription()}"/></td>
							      <td style=''><c:out value="${product.getQuantityGalley()}"/></td>
							   
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					  </div>
						
						<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
						
						<!-- ********************************************************************************************** -->
						<h2 style='margin-bottom:15px; cursor:pointer;' onclick='openBar()'>
							<i class="fas fa-clipboard-check"></i> Prodotti Bar
						</h2>
						
	                   <div class='table-responsive shadow shadow-1' style='display:none;' id='barSection'>
	                   
	                     <table border=0 class="table" id='assignedTables' style='background-color:white; text-align:center;'>
	                     
						  <thead class="thead-dark">
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Nome</th>
						      <th scope="col">Descrizione</th>
						      <th scope="col">Quantità</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach items="${productsBar}" var='product'>
								<tr>
							      <th scope="row"><i class="fas fa-circle" style='color:#32CD32;font-size:20px;'></i></th>
							      <td style=''><c:out value="${product.getName()}"/></td>
							      <td style=''><c:out value="${product.getDescription()}"/></td>
							      <td style=''><c:out value="${product.getQuantityBar()}"/></td>
							   
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					  </div>
					  
						<!-- ******************************************************************************************** -->
						<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
						
						<h2 style='margin-bottom:15px; cursor:pointer;' onclick='openOrders()'>
							<i class="fas fa-clipboard-check"></i> Ordini
						</h2>
						<div style='display:none; width:100%;' id='ordersSection'>
							<br>
							<jsp:include page="table.jsp"/>
							<br>
		                 
			                 <div id='ordersTable' class='table-responsive shadow shadow-1'></div>
			                 <div id='ordersTableTotal'></div>
						</div>
						
	                  
	                 <!-- ******************************************************************************************** -->
	                 <hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
	                 <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openAmount()'>
	                 	<i class="fas fa-clipboard-check"></i> Incasso Serata
	                 </h2>
	                 
	                 <div id='ordersTableTot' class='table-responsive' style='margin-top:30px;display:none;' > 
	                 	<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
						<table class='table' style='background-color:white; text-align:center;'>
							<thead class='thead-dark'>
								<tr>
									<th scope='col'>Tavolo</th>
									<th scope='col'>Prenotazione</th>
									<th scope='col'>#Persone</th>
									<th scope='col'>Luxus</th>
									<th scope='col' colspan=2>Budget Iniziale</th>
									<th scope='col' colspan=2>Budget Attuale</th>
									<th scope='col' colspan=2>Importo Speso</th>
									<th scope='col' colspan=2>Totale Tavolo</th>
									<th scope='col' colspan=2>Di Cui Extra</th>
								</tr>
							</thead>
							<tbody style='font-weight:600;'>
								<c:forEach items="${totals}" var='total'>
									<tr>
										<td style='vertical-align:middle;'><c:out value="${total.tab.tableNumber}"/></td>
										<td style='vertical-align:middle;'><c:out value="${total.tab.reservationName}"/></td>
										<td style='vertical-align:middle;'><c:out value="${total.tab.peopleNumber}"/></td>
										<c:choose>
											<c:when test="${total.tab.isLuxus()}">
												<c:set var="initialBudget" value="${total.tab.peopleNumber * config.luxusTablePrice}" /> 
												<td style='vertical-align:middle;'>Sì</td>
											</c:when>
											<c:otherwise>
												<c:set var="initialBudget" value="${total.tab.peopleNumber * config.tablePrice}" /> 
												<td style='vertical-align:middle;'>No</td>	
											</c:otherwise>
										</c:choose>
										<c:set var="tableTotal" value="${total.tab.budget+total.total}"/>			   
										
										<td style='vertical-align:middle; padding-right:0;' align=right><c:out value="${initialBudget}"/></td><td style='padding-left:3px;' align=left>€</td>
										<td style='vertical-align:middle; padding-right:0;' align=right><c:out value="${total.tab.budget}"/></td><td style='padding-left:3px;' align=left>€</td>	
										<td style='vertical-align:middle; padding-right:0;' align=right><c:out value="${total.total}"/></td><td style='padding-left:3px;' align=left>€</td>
										<td style='vertical-align:middle; padding-right:0;' align=right><c:out value="${tableTotal}"/></td><td style='padding-left:3px;' align=left>€</td>
										<td style='vertical-align:middle; padding-right:0;' align=right><c:out value="${total.extraPayments}"/></td><td style='padding-left:3px;' align=left>€</td>
									</tr>
									<c:set var="totalAmount" value="${totalAmount + total.total}"/>
									<c:set var="totalPeopleNumber" value="${totalPeopleNumber + total.tab.peopleNumber}"/>
									<c:set var="totalBudget" value="${totalBudget + total.tab.budget}"/>
									<c:set var="totalExtra" value="${totalExtra + total.extraPayments}"/>
									<c:set var="totalInitialBudget" value="${totalInitialBudget + initialBudget}"/>
								
								</c:forEach>
								<tr style='font-weight:700;'>
									<th scope='col' ></th>
									<th scope='col' >Totali</th>
									<td scope='col' ><c:out value="${totalPeopleNumber}"/></td>
									<td scope='col' >-</td>
									<td scope='col' style='vertical-align:middle; padding-right:0;' align=right><c:out value="${totalInitialBudget}"/></td><td style='padding-left:3px;' align=left>€</td>
									<td scope='col' style='vertical-align:middle; padding-right:0;' align=right><c:out value="${totalBudget}"/></td><td style='padding-left:3px;' align=left>€</td>
									<td scope='col' style='vertical-align:middle; padding-right:0;' align=right><c:out value="${totalAmount}"/></td><td style='padding-left:3px;' align=left>€</td>
									<td scope='col' style='vertical-align:middle; padding-right:0;' align=right><c:out value="${totalBudget+totalAmount}"/></td><td style='padding-left:3px;' align=left>€</td>
									<td scope='col' style='vertical-align:middle; padding-right:0;' align=right><c:out value="${totalExtra}"/></td><td style='padding-left:3px;' align=left>€</td>
								</tr>
							</tbody>
						</table>
	                 </div>
						
						<!-- ******************************************************************************************** -->
	                 	<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
	                 		<h2 style='margin-bottom:15px; cursor:pointer;' onclick='openListEntrance()'>
	                 			<i class="fas fa-list-alt"></i> Ingresso in lista
	                 		</h2>
	                 	<div style='display:none; width:100%;' id='entranceSection'>
		                 		<h5 id='numOfClients'></h5>
							<br>
							<select class="selectpicker form-control" id="prList" onchange='filterPrClients()'>
						     	<option class="bs-title-option" value="0" selected>---Seleziona Pr---</option>
						     	
						    </select>
						    <br><br>
						    <div class='table-responsive shadow shadow-1' >
			                     <table class="table" border=0 style='background-color:white; text-align:center;'>
			                     
								  <thead class="thead-dark">
								  	<tr>
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
							<br>
							<button type="button" class="btn btn-primary btn-lg btn-block" onclick="generatePrint()"><i class="fas fa-print"></i> Stampa Elenco Clienti e Incasso Serata</button>
						 	<br>
		                  	<div style='margin:auto; margin-bottom:30px; margin-top:50px;'>
		                  	<h5 style='text-align:center;'>Gestisci prenotazioni liste online</h5>
		                  	 <div class="onoffswitch" style='margin:auto;'>
							    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" onchange="ReservationStateChange()">
							    <label class="onoffswitch-label" for="myonoffswitch">
							        <span class="onoffswitch-inner"></span>
							        <span class="onoffswitch-switch"></span>
							    </label>
							</div>
		                  	</div>
	                 	</div>
						
						
							<!-- ******************************************************************************************** -->
	                 	<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
	                 		<h2  style='margin-bottom:15px; cursor:pointer;' onclick='openNewPr()'>
	                 			<i class="fas fa-user-plus"></i> Gestione PR
	                 		</h2>
	                 		
		                 	<div id='newPrSection' style='margin-top:50px; display:none;'>
		                 		<div id='insertPr'>
			                 		<h4>Inserisci nuovo</h4>
							    	<input type="text" id="namePrInsert" class="form-control" style='width:49%;vertical-align:middle; float:left;' placeholder='Nome'>
									<input type="text" id="lastnamePrInsert" class="form-control" style='width:49%; vertical-align:middle;float:right;margin-bottom:20px;' placeholder='Cognome'><br>
									<input type="text" id="passwordPrInsert" class="form-control" style='margin:auto;width:100%; vertical-align:middle;' placeholder='Password'>
									<br><button type="button" class="btn btn-primary btn-lg btn-block" onclick="newPrInsert()"><i class="fas fa-user-plus"></i> Aggiungi Nuovo</button>
		                 		</div>
		                 		
								<div id='deletePr' style='margin-top:50px;'>
									<h4> Elimina un pr</h4>
									<div class='table-responsive shadow shadow-1'>
					                	<table class="table" border=0 style='background-color:white; text-align:center; margin-bottom:20px;'>
										  <thead class="thead-dark">
										    <tr>
											  <th scope="col">Username</th>
										      <th scope="col">Password</th>
										      <th scope="col">Cognome</th>
											  <th scope="col">Nome</th>
										      <th scope="col">Elimina</th>
										    </tr>
										  </thead>
										  <tbody id='showPrSection'></tbody>
										</table>
				                	</div>
		                 		</div>
							</div>
	                 	
	                 	
	                 	<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
	                 	 <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openTablesManage()'>
						 	<i class="fas fa-tags"></i> Gestione tavoli
						 </h2>
							 <div id="editTablesSection" style="display:none; margin-top:50px;">
							 	<h4>Modifica costo dei tavoli</h4><br>
							    	<div style="float:left; width:25%;margin-right:7%;">Costo tavolo Normale<br><input type="number" id="notLuxusPrice" class="form-control" value="<c:out value="${config.tablePrice}"/>"></div>
									<div style="float:left; width:25%; marign-right:7%;">Costo tavolo Luxus<br><input type="number" id="luxusPrice" class="form-control" value="<c:out value="${config.luxusTablePrice}"/>"><br></div>
									<br><button type="button" class="btn btn-primary btn-lg" style="width:57%;" onclick="editTablesPrice()"><i class="fas fa-user-plus"></i> Modifica</button>
							 		<br><br><br>
							
								<h4>Modifica tavoli</h4>
								<h5 style="color:red;">ATTENZIONE: modifica qui il budget solo se strettamente necessario!!</h5>
			                     <table class="table"  border=0 style='background-color:white;  width:100%; text-align:center;'>
			                     
								  <thead class="thead-dark">
								    <tr>
								      <th scope="col" style="width:30px;"></th>
								      <th scope="col">Tavolo</th>
								      <th scope="col">Luxus</th>
								      <th scope="col">Applica</th>
								    </tr>
								  </thead>
								  <tbody>
									<c:forEach items="${tables}" var='table'>
										<tr>
										  <td style="text-align:center;"><i class="fas fa-circle" style='color:#32CD32;font-size:20px;'></i></td>
									      <td><c:out value="${table.getTableNumber()}"/></td>
										  <td>   
										  	<select id="isLuxus<c:out value="${table.getTableNumber()}"/>" class="form-control" style="width:auto; margin:auto;" >
										  		<c:choose>
											      	<c:when test="${table.isLuxus()}">
											      		<option value="true" selected>Sì</option>
											      		<option value="false">No</option>
											      	</c:when>
											      	<c:otherwise>
											      		<option value="true" >Sì</option>
											      		<option value="false" selected>No</option>
											      	</c:otherwise>
										        </c:choose>
										      </select>
										  </td>
									      <td><i class="fas fa-check-circle" style='color:#32CD32; font-size:25px; cursor:pointer;' onclick="modTable(<c:out value="${table.getTableNumber()}"/>)"></i></td>
						      
									    </tr>
									</c:forEach>
								  </tbody>
								</table>
							</div>
						
						
		                 <hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
		                 <h2 style='margin-bottom:15px; cursor:pointer;' onclick='openErase()'>
		                 	<i class="fas fa-eraser"></i> Elimina dati
		                 </h2>
		                 <div class='btn-large' style='display:none; width:100%;' id='eraseSection'>
		                 	<br><br>
						 	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="if(confirm('Sei sicuro di voler eliminare tutti gli ordini?')) clearOrders()"><i class="fas fa-eraser"></i> Azzera Tavoli</button>
						 	<br><br>
						 	<button type="button" class="btn btn-primary btn-lg btn-block" onclick="if(confirm('Sei sicuro di voler resettare gli ingressi in lista?')) clearClients()"><i class="fas fa-eraser"></i> Azzera Liste</button>
						 </div>
	                  </div>
                 </div><!-- chiude il div row -->
               </section>
        </section>
        
<jsp:include page="footer.html"/>
<script>
	uploadList();//all'avvio carica tutte le liste dei pr e in pr stessi
	
	/*FUNZIONI PER APRIRE LE VARIE SEZIONI*/
	function openProducts(){
		$("#productsSection").fadeToggle(200);
	}
	function openGalley(){
		$("#galleySection").fadeToggle(200);
	}
	function openBar(){
		$("#barSection").fadeToggle(200);
	}
	function openOrders(){
		$("#ordersSection").fadeToggle(200);
	}
	function openAmount(){
		$("#ordersTableTot").fadeToggle(200);
	}
	function openListEntrance(){
		$("#entranceSection").fadeToggle(200);
	}
	function openErase(){
		$("#eraseSection").fadeToggle(200);	
	}
	function openTablesManage(){
		$("#editTablesSection").fadeToggle(200);	
	}
	function openNewPr(){
		$("#newPrSection").fadeToggle(200);	
	}
	
	
	/***************************Funzione per stampare la lista dei clienti***************************/
	/***********************************************************************************************/
	function generatePrint(){
	    var mywindow = window.open('', 'PRINT');
	
	    mywindow.document.write('<html><head><title>Lista Clienti</title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1>Lista Clienti</h1>');
	    mywindow.document.write(document.getElementById("entranceSection").innerHTML);
	    mywindow.document.write("<br><h1>Incasso serata</h1>");
	    mywindow.document.write(document.getElementById("ordersTableTot").innerHTML);
	    mywindow.document.write('</body></html>');
	
	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/
	
	    mywindow.print();
	    mywindow.close();
	
	    return true;
	}
	/*****************************************/
	/*************************************/
	
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
	
	function goToRow(id){
		var myElement = document.getElementById('tr'+id);
		var topPos = myElement.offsetTop;
		
		document.getElementById('productsSection').scrollTop = topPos;
		
		$('#tr'+id).addClass("trBgcolor");
		setTimeout(function(){ $('#tr'+id).removeClass("trBgcolor"); }, 10000);
	}
		

	function CloseAlert() {
		$("#alertBox").hide();
	}

	function OpenAlert() {
		//$("#alertBox").show(70);

		$('.toast').toast('show');
	}
	
	//funzione per abilitare e disabilitare i campi del bar e cambusa in basse ala locazione scelta
	function modFlag(id){
		var flagType = $('#location'+id).find(":selected").val();
		//disattivo tutto
		if(flagType==0){
			document.getElementById("addQtyB"+id).disabled = true;
			document.getElementById("addQtyG"+id).disabled = true;
		}
		//disattivo galley
		else if(flagType==1){
			document.getElementById("addQtyB"+id).disabled = false;
			document.getElementById("addQtyG"+id).disabled = true;

		}
		//disattivo bar
		else if(flagType==2){
			document.getElementById("addQtyB"+id).disabled = true;
			document.getElementById("addQtyG"+id).disabled = false;
		}
		else{//ATTIVO TUTTO
			document.getElementById("addQtyB"+id).disabled = false;
			document.getElementById("addQtyG"+id).disabled = false;
		}
	}
	
	
	function editProduct(id){
		var location = $('#location'+id).find(":selected").val();
		var warehouse=$('#addQtyW'+id).val();
		var galley=$('#addQtyG'+id).val();
		var bar=$('#addQtyB'+id).val();
		var price=$('#modPrice'+id).val();
		
		if(galley.trim().length==0 || warehouse.trim().length==0 || bar.trim().length==0 || price.trim().length==0){
			$("#msg").text("La quantità non può essere nulla!");
			OpenAlert();	
			return;
		}
		
		$.post("EditProduct",{	
				id: id, 
				price: price,
				warehouse: warehouse,
				location: location,
				galley:galley,
				bar:bar
				
		},function(data){
			if(data.length==0)
				$("#msg").html("Prodotto modificato correttamente");
			else
				$("#msg").html(data);
			
			OpenAlert();
		});
		
		updatePage();
		$("#productsSection").show();
		goToRow(id);
		
	}
	
	/*******************************************************************************************/
	/***************************FUNZIONE PER CARICARE GLI ORDINI QUANDO SI CLICCA SU UN TAVOLO*/
	function uploadOrders(id){
		//resetto il contenuto
		$("#ordersTable").html("");
		$("#ordersTableTotal").html("");
		 
		//var id=$('#tableNumber').find(":selected").val();
		var tableOrder="",totalAmount=0;
		$.getJSON("TableOrders?id=" + id, function(result){
			if(!result){
				$("#msg").text("Nessun Ordine Disponibile!");
				OpenAlert();
				return;
			}
   		    $.each(result, function(i, order){
   		    	
   		    	//document.write(order.idOrder+" ");
   		 		var qtytot=0,pricetot=0;
   		    	tableOrder+="<table class='table' style='background-color:white; text-align:center;'>"+
						 	  "<thead class='thead-dark'>"+
					  			"<tr><th scope='col'></th><th scope='col'>#Ordine</th><th scope='col'>Data</th><th scope='col'>Dettagli</th><th scope='col'>Pagamento</th></tr>"+
							  "</thead><tbody style='font-weight:600;'>"+
   		    				   "<tr>"+
	   		    				   "<td style='vertical-align:middle'><i class='fas fa-chevron-circle-down' style='font-size:25px; color:#32CD32; cursor:pointer;' onclick='openDetail("+order.idOrder+")'></i></td>"+
		   		    			   "<td style='vertical-align:middle'>"+order.idOrder+"</td>"+
		   		    			   "<td style='vertical-align:middle'>"+order.dateOrder+"</td>";
		   		    			   if(order.details)
		   		    					tableOrder+="<td style='vertical-align:middle'>"+order.details+"</td>";
		   		    				else
		   		    					tableOrder+="<td>Nessun dettaglio</td>";
		   		    				tableOrder+="<td  style='vertical-align:middle'>"+order.methodPay+"</td>";
   		    			      "</tr></tbody></table>";
   		    			      
   		    			tableDetailOrder="<center><table id='detailTable"+order.idOrder+"' class='table' style='display:none;width:80%;margin:0 auto;margin-bottom:80px;background-color:white; text-align:center;'>"+
			 								"<thead class='thead-dark'>"+
		   				 						"<tr><th scope='col'>Prodotto</th><th scope='col'>Descrizione</th><th scope='col'>Qta.</th><th scope='col' colspan=2>Prezzo €</th></tr>"+
	 										"</thead>"+
		   				 					"<tbody id='detailOrder"+order.idOrder+"'>";
		   				 				
		   				 	$.each(order.orderDetails, function(i, detail){
   		    				tableDetailOrder+="<tr>"+
   		    										"<td>"+detail.product.name+"</td>"+
   		    										"<td>"+detail.product.description+"</td>"+
   		    										"<td>"+detail.quantity+"</td>"+
   		    										"<td align=right>"+(detail.purchaseUnitPrice*detail.quantity)+"</td>"+
   		    										"<td align=left>€</td>"+
   		    									"</tr>";
   		    				//calcolo totali	
   		    				qtytot+=detail.quantity;
   		    				pricetot+=detail.purchaseUnitPrice*detail.quantity;
            		    });
   		    			tableDetailOrder+="	<tr><td><b>Totale</b></td><td>-</td>"+
   		    							 	"<td><b>"+qtytot+"</td>"+
   		    							 	"<td align=right><b>"+pricetot+"</td>"+
   		    								"<td align=left>€</td></tr>";

   		    			tableDetailOrder+="</tbody></table></center><br>";
   		    			tableOrder+=tableDetailOrder;
   		    			
   		    			totalAmount+=pricetot;
   		    }); 
   		 tableOrderTotal="";
   		tableOrderTotal+="<table class='table' style='background-color:white; text-align:center;'>"+
   		 				"<thead><tr><th scope='col'>L'incasso Totale del Tavolo N° "+id+" è di &nbsp;<span style='font-size:27px;'>"+totalAmount+" €</span></th></tr></thead>"+
   		 			 "</table>";
   		 $("#ordersTable").html(tableOrder);
   		 $("#ordersTableTotal").html("<br>"+tableOrderTotal);
   		 
   	  }).fail(function(data) { 
			$("#msg").text("Sessione scaduta,Rifare il login");
			OpenAlert();
			sleep(1500);
			location.href="Logout";
			
   	  });			
  		 
	}
	/*******************************************************************************************/
	/******************FINE*********************/
	
	
	function filterPrClients(){
		prName=$("#prList").val();
		if(prName=="0"){
			$("#clientList tr").fadeIn(200);
			return;
		}
		$("#clientList tr").filter(function() {
		      $(this).toggle($(this).text().indexOf(prName) > -1)
		   });
	}
	
	function openDetail(id){
		$("#detailTable"+id).fadeToggle(250);
	}
	
	//funzione per caricare tutte le liste dei pr
	function uploadList(){
		
		prClients="";
		cntClients=0;
		
		$.get("RetrieveAllClientsAndAllPr", 
			function(data){
				try {
					var result = JSON.parse(data);
				}catch(err) {
					alert(data+"Impossibile caricare i pr.\nRiprova fra po!");
					return;
				}
				
			    //carico tutti i clienti
				$.each(result.clients, function(i, prClient){
					prClients+="<tr>"+
			    				   "<td style='vertical-align:middle;'>"+prClient.surname+"</td>"+
			    				   "<td style='vertical-align:middle;'>"+prClient.name+"</td>"+
			    				   "<td style='vertical-align:middle;'>"+prClient.added_date+"</td>"+
			    				   "<td style='vertical-align:middle;'>"+prClient.pr.name+" "+prClient.pr.lastname+"</td>";
    				   
    				   if(prClient.entered==1){
    					   cntClients++;
    					   prClients+="<td style='vertical-align:middle;'><i class='fas fa-circle' style='color:#32CD32;font-size:20px;'> </i> Sì</td>";
    				   }   
    				   else 
    					   prClients+="<td style='vertical-align:middle;'><i class='fas fa-circle' style='color:#e10b00;font-size:20px; cursor:pointer;' ></i> NO </td>";   //onclick=\"if(confirm('Confermare l\\' ingresso?')) changeClientStatus("+prClient.client_id+")\" 
    				   
    				   prClients+="</tr>";
				});

				prListName="";
				prListNameToShow="";//per caricare i pr nella tabella della sezione 'gestione pr'
				
				//scorro tutti i pr
				$.each(result.pr, function(i, pr){
					prListName+="<option class='bs-title-option' value='"+pr.name+" "+pr.lastname+"'>"+pr.name+" "+pr.lastname+"</option>";
					
					//per la gestione dei pr
					prListNameToShow+="<tr>"+
										"<td>"+pr.username+"</td>"+
										"<td>"+pr.password+"</td>"+
										"<td>"+pr.lastname+"</td>"+
										"<td>"+pr.name+"</td>"+
										"<td><a onclick=deletePr(\""+pr.username+"\") ><i class='fas fa-user-times' style='cursor:pointer; font-size:20px;'></i></a></td>"+
									   "</tr>";
				});
				
				if(result.option.flagList==1){
					$("#myonoffswitch").prop('checked', true);
				}
				
				$("#prList").html("<option class='bs-title-option' value='0' selected>---Seleziona Pr---</option>")
				$("#prList").append(prListName);
				$("#clientList").html(prClients);
				$("#numOfClients").html("Sono entrate <i>"+cntClients+"/"+result.clients.length+"</i> persone in lista.");
				$("#showPrSection").html(prListNameToShow);
			});
	}
	
	function ReservationStateChange(){
		$.get("ReservationStateChange", 
				function(data){
					if(data!=1){
						$("#msg").text("Errore modifica,riprovare");
						OpenAlert();
					}
					uploadList();
					
		});
	}
	
	function editTablesPrice(){
		
		priceNormal=$("#notLuxusPrice").val();
		priceLuxus=$("#luxusPrice").val();
		
		$.post("EditReservationPrice",{	
					priceNormal: priceNormal, 
					priceLuxus: priceLuxus
				},function(data){
					if(data.length==0)
						$("#msg").html("Prezzi modificati correttamente");
					else
						$("#msg").html(data);
					
					OpenAlert();
				});
	}
	
	function modTable(id){
		
		isLuxus=$("#isLuxus"+id+" option:selected").val();
		
		$.post("EditTableType",{	
					id:id,
					isLuxus: isLuxus
				},function(data){
					if(data.length==0)
						$("#msg").html("Tavolo modificato correttamente");
					else
						$("#msg").html(data);
					
					OpenAlert();
				});	
	}
	
	function clearOrders(){
		
		$.get("ClearReservations", 
				function(data){
					updatePage();
					$("#eraseSection").show();
					$("#msg").text("Tavoli Azzerati!");
					OpenAlert();
					//forse è superfluo-> uploadList()
					uploadList();
		});	
	}
	
	function clearClients(){
		$.get("ClearClients", 
				function(data){
					
					updatePage();
					$("#eraseSection").show();
					$("#msg").text("Liste Azzerate!");
					OpenAlert();
					//forse è superfluo-> uploadList()
					uploadList();
		});	
	}
	
	//funzione per asseganre ad ogni tavolo che quando viene cliccato,
	//richiama la funzione uploadOrders() 
	$( document ).ready(function() {
		 $(".td-available, .td-unavailable").click(function() {
				id=this.id; //recupero l'id del td
				uploadOrders(id)
		 });
	});
	
	function newPrInsert(){
		var name=$("#namePrInsert").val().trim();
		name = name.replace(" ", "_");
		var lastname=$("#lastnamePrInsert").val().trim();
		lastname = lastname.replace(" ", "_");
		var password=$("#passwordPrInsert").val().trim();
		
		if(name.length==0 || lastname.length==0 || password.length==0){
			$("#msg").text("Compilare tutti i campi!");
			OpenAlert();
			return ;
		}
		
		$.get("NewPrInsert?name="+name+"&lastname="+lastname+"&password="+password, 
				function(data){
					updatePage();
					$("#newPrSection").show();
					if(data.length==0)
						$("#msg").text("Pr inserito!");
					else
						$("#msg").text(data);
					
					OpenAlert();
		});	
	}
	
	function deletePr(id){
		$.get("DeletePrOnline?usern="+id, 
				function(data){
					updatePage();
					$("#newPrSection").show();
					$("#msg").text("Pr eliminato!");
					OpenAlert();
		});	
	}
	
	
	
</script>
</body>
</html>