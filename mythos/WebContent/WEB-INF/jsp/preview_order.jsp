<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.*,entity.*,java.util.*" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	
<%!	
	ArrayList<Product> productsArray=null;	
%>
<%
	productsArray=(ArrayList<Product>) request.getAttribute("products");

%>
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
    
    <hr style='margin-top:15px;background-color:#fff;  border: 1px solid white;width:90%;'>
     <h2 style='color:#fff; text-align:center;'>
     	&nbsp;<i class="fas fa-plus-circle"></i> Ordine tavolo <c:out value="${order.getTable().getTableNumber()}"  />
     	<br> <button type="button" class="btn btn-primary btn-sm" onclick="location.href='.'">Cambia tavolo</button>
     </h2>
     <br>
			<section class="content">
             <section class="container">
                  <div class="row" >
                  	<!-- ***************************************************************************** -->	
	                 <!-- ***************************INFORMAZIONI TAVOLO****************************** -->
	                 
						<div style='width:99%; margin-bottom:20px;'> 
							<hr style='margin-top:10px;background-color:#fff;  border: 1px solid white;width:100%;'>
							<h4 style='text-align:center;width:100%;cursor:pointer' onclick=""> &nbsp;<i class="fas fa-info"></i> Informazioni Tavolo</h4>
						</div>
						
		                <div class='table-responsive'>
		                	<table class="table" style='background-color:white; text-align:center; margin-bottom:20px;'>
							  <thead class="thead-dark">
							    <tr>
							      <th scope="col"></th>
							      <th scope="col">Tavolo</th>
							      <th scope="col">Nome Prenotazione</th>
							      <th scope="col">#Persone</th>
							      <th scope="col" colspan=2>Budget Disponibile</th>
							    </tr>
							  </thead>
							  <tbody>
									<tr>
								      <th scope="row"><i class="fas fa-circle" style='color:#32CD32;font-size:15px;'></i></th>
								      <td><c:out value="${order.getTable().getTableNumber()} (${order.getTable().getTableName()})"/></td>
								      <td><c:out value="${order.getTable().getReservationName()}"/></td>
								      <td><c:out value="${order.getTable().getPeopleNumber()}"/></td>
								      <td style='text-align:right; padding-right:3px;'><c:out value="${order.getTable().getBudget()}"/></td>
								      <td style='text-align:left; padding-left:3px;'>€</td>
								    </tr>
							  </tbody>
							</table>
		                </div>
		                <!-- *************************FINE INFO TAVOLO******************************* -->
						
						<div style='width:99%; margin-bottom:20px;'> 
				                <hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
								<h4 style='text-align:center;width:100%;cursor:pointer' onclick=""> &nbsp;<i class="far fa-hand-point-down"></i> Scorri per selezionare</h4>
						</div>
						<!--****************************************************************-->
						<!--*****************   VISUALIZZAZIONE PRODOTTI  *********************-->
						<!-- preparo i div per lo slider ********************************** -->
						<div class="swiper-container">
							<div class="swiper-wrapper"></div>
						
							<div class="swiper-button-next">
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 27 44"><path d="M27,22L27,22L5,44l-2.1-2.1L22.8,22L2.9,2.1L5,0L27,22L27,22z"></svg>
							</div>
							<div class="swiper-button-prev"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 27 44"><path d="M0,22L22,0l2.1,2.1L4.2,22l19.9,19.9L22,44L0,22L0,22L0,22z"></svg></div>
						</div>
						<div style='width:99%; margin-bottom:20px;'> 
						    <hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:100%;'>
							<h4 style='text-align:center;width:100%;cursor:pointer' onclick=""> &nbsp;<i class="fas fa-wine-bottle"></i> Tutti i prodotti</h4>
						</div>
						
						<p class='append-buttons'>
					  	<%
					  		int i=1;
					  		for(Product p: productsArray){
							    out.write("<div id='divA' style='cursor:pointer; margin-top:5px;' class='slide-"+i+"' >"+p.getName()+"</div>");
							    i++;
							}
					  	%>
					  	</p>
					
						<!-- ******************** -->
						<!-- inizializzo lo swiper con tutti i prodotti letti dall attributo della request -->
						<script>
						  var swiper = new Swiper('.swiper-container', {
						    effect:  "coverflow",
						    speed: 230,
						    slidesPerView: 2,
						    centeredSlides: true,
						    spaceBetween: 1,
						    pagination: {
						      el: '.swiper-pagination',
						      type: 'fraction',
						    },
						    navigation: {
						      nextEl: '.swiper-button-next',
						      prevEl: '.swiper-button-prev',
						    },
						    virtual: {
						      slides: (function () {
						        var slides = [];
						        
						        <% 
						        	for(Product p: productsArray){
						        		String item="<div class='card shadow-1'>"+
						        						"<div class='card-body'>"+
															"<h4 class='card-title'>"+p.getName()+"</h4>"+
															"<div style='overflow-y: auto; height: 35px; margin-bottom: 1px;'>"+
																"<p class='card-text'>"+p.getDescription()+"</p>"+
															"</div>"+
															"<div class='card-info'>"+
																"<span class='card-price'>"+p.getPrice()+"</span>"+
																"<textarea id='note"+p.getIdProduct()+"' maxlength='255' placeholder='Inserisci una nota' style='display:none;'></textarea>"+
															"</div>";
										//se il prodotto è disponibile visualizzo la qta,altrimenti visualizzo un icona 
										if(p.getQuantityGalley()>0)
													item += "Qta. <span id='qta"+p.getIdProduct()+"'>"+p.getQuantityGalley()+"</span><br>";
										else
													item += "Esaurito <span id=><i class='fas fa-times' style='color:#e10b00; font-size:25px; margin-top:5px;'></i></span><br>";
										
													//aggiungo ora il div per la quantità
						                            item += "<div class='number-input'>"+
																"<button onmousedown=\\\"this.parentNode.querySelector('#quant"+p.getIdProduct()+"').stepDown()\\\"></button>"+								                            
																"<input  class='quantity qtaInput' min='1' max="+p.getQuantityGalley()+" name='quantity' value='1' type='number' id='quant"+p.getIdProduct()+"' >"+
																"<button onmousedown=\\\"this.parentNode.querySelector('#quant"+p.getIdProduct()+"').stepUp()\\\" class='plus'></button>"+
						                          			"</div>"+	  
						                            		"<button type='button' class='btn' onclick='addToCart("+p.getIdProduct()+")'>Aggiungi</button>";
							                            
												    	/*se è una bottiglia di prosecco do la possibilità di non farla pagare
							                            if(p.getIdProduct()==20){
							                            	item+= "<button type='button' class='btn' onclick='addToCart("+p.getIdProduct()+")'>Omaggio</button>";
							                            }*/
													//}
							                        //else  item+="Esaurito <span id=><i class='fas fa-times' style='color:#e10b00;'></i></span><br>";
							                        
												item += "</div>"+
													"</div>";
						        %>
						      	  
						      	  slides.push("<%= item %>");
						      	 // dimensioniFinestraBroswer=window.innerWidth;
						        <% 
						        	}
						        %>
						        return slides;
						      }()),
						    },
						  });
						  <%
						    i=0;
							for(Product p: productsArray){
									out.print("document.querySelector('.slide-"+(i+1)+"').addEventListener('click', function (e) { "+
											 "e.preventDefault(); "+
											 "swiper.slideTo("+i+", 100); "+
											 "});\n");
									i++;
							}
						%>
						</script>
						<!-- ************* FINE VISUALIZZAZIONE PRODOTTI ************************ -->   	
						<!-- ********************************************************************* -->	
						 
						 
						<!-- ************************************************************************************ -->
						<!-- ********************** INIZIO VISUALIZZAZIONE DETTAGLI E PRODOTTI CARRELLO *******************  -->
						
	                  	<hr style='margin-top:80px;background-color:#fff;  border: 1px solid white;width:99%;'>
						<h4 style='text-align:center;width:100%;cursor:pointer' onclick="openTableDetails()"> &nbsp;<i class="fas fa-bars"></i> Dettagli tavolo</h4><br>
				    	 	<div class='containerDetails center' id="tableDetailsSection" style="margin-bottom:20px;">
								<ul class="ks-cboxtags">
					    			<c:forEach items="${details}" var="detail">
					    				<c:choose>
					    					<c:when test="${cartList.containsDetail(detail)}">
						    					<li>
													<input checked id="checkbox<c:out value="${detail.idDetail}"/>" name='details' type="checkbox" onchange='removeDetail(<c:out value="${detail.idDetail}"/>)' value='<c:out value="${detail.idDetail}"/>'>
									    			<label for="checkbox<c:out value="${detail.idDetail}"/>"><c:out value="${detail.description}"/></label>
									    		</li>
					    					</c:when>
					    					<c:otherwise>
					    						<li>
													<input id="checkbox<c:out value="${detail.idDetail}"/>" name='details' type="checkbox" onchange='addDetail(<c:out value="${detail.idDetail}"/>)' value='<c:out value="${detail.idDetail}"/>'>
									    			<label for="checkbox<c:out value="${detail.idDetail}"/>"><c:out value="${detail.description}"/></label>
									    		</li>
					    					</c:otherwise>
					    				</c:choose>
									</c:forEach>					    		
								</ul>
					    		<c:if test='${cartList.getDetailCount()>0 && (cartList==null || cartList.getProductCount()<=0) }'>
					    			<div class='btn-large'>
							    		<button type="button" class="btn btn-primary btn-lg btn-block" onclick="detailConfirm()"><i class="far fa-check-circle"></i> Invia Dettagli</button><br><br><br>
							    	</div>
					    		</c:if>
							</div>
							
							
							<div style='width:99%; margin-bottom:20px;'> 
			                	<hr style='margin-top:40px;background-color:#fff;  border: 1px solid white;width:100%;'>
								<h4 style='text-align:center;width:100%;cursor:pointer' onclick=""> &nbsp;<i class="fas fa-coins"></i> Anteprima</h4>
							</div>
						
					    	<ul class="list-group" id="orderList" style='margin-top:20px;'>	
							    <c:if test="${cartList!=null && cartList.getProductCount()>0}">
							    	<c:set var="totalOrder" value="0" scope="page"/>
							    	<c:set var="totalQuantityOrder" value="0" scope="page"/>
							    	
							    	<c:forEach items="${cartList.getProductList().values()}" var="cartproduct">
							    		<c:set var="tot" value="0" scope="page"/>
							    		
							    		<li class="list-group-item d-flex justify-content-between align-items-center">
									    	<span class="mr-auto"><c:out value="${cartproduct.getQuantity()} x ${cartproduct.getProduct().getName()} "/></span>
									    	<c:set var="t" value="${cartproduct.getProduct().price * cartproduct.quantity}" scope="page"/>
									    	<c:set var="tot" value="${(tot + t)}" scope="page"/>
									    	<span class="badge badge-primary badge-pill">
									    		<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${tot}"/> €
									    	</span>&nbsp;
									    	<!-- passo la quantità originale del prodotto, così quando lo elimino dal carrello la resetto -->
									    	<span class="btn btn-primary btn-sm fa fa-times" style="cursor:poiter;" 
									    		  onclick="DelToCart(<c:out value="${cartproduct.getProduct().getIdProduct()},${cartproduct.getProduct().getQuantityGalley()}"/>)">
									    	</span>
								    	</li>
										<!-- campi hidden che servono per aggiornare la disponibilità in caso di ricaricamento pagina. li preleva dal carrello e li scala alla quantità visualizzata nello slider-->
									    	<input type='hidden' value='<c:out value="${cartproduct.getProduct().getIdProduct()}"/>' id='idHidden<c:out value="${cartproduct.getProduct().getIdProduct()}"/>'>
									    	<input type='hidden' value='<c:out value="${cartproduct.getQuantity()}"/>' id='qtaHidden<c:out value="${cartproduct.getProduct().getIdProduct()}"/>'>
								    		<!-- end -->
								    		
								    	<!-- script per aggiornare la disponibilità -->
										<script>
											id=$("#idHidden"+<c:out value="${cartproduct.getProduct().getIdProduct()}"/>).val();
											quantity=$("#qta"+id).text()-$("#qtaHidden"+<c:out value='${cartproduct.getProduct().getIdProduct()}'/>).val();
											//alert(quantity);
											$("#qta"+id).text(quantity);
										</script>
										<!-- end -->
										
										<c:set var="totalOrder" value="${(tot + totalOrder)}" scope="page"/>
										<c:set var="totalQuantityOrder" value="${totalQuantityOrder + cartproduct.getQuantity()}" scope="page"/>
										<c:if test="${giftId==cartproduct.getProduct().getIdProduct() }"> 
											<c:set var="proseccoNumber" value="${cartproduct.quantity}" scope="page"/>
										</c:if>
							    	</c:forEach>
							    	
							    	<!-- ******** TOTALE ORDINE ************** -->
							    	<li class="list-group-item d-flex justify-content-between align-items-center">
							    		<span class="mr-auto"><b><c:out value="${totalQuantityOrder}"/> x Totale </b></span>
							    		<span class="badge badge-primary badge-pill" id='totalOrder'>
							    			<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${totalOrder}"/> €
									    </span>
							    	</li>
							
							    	<c:if test="${(order.table.budget-totalOrder)<0 and order.table.budget>0}">
								    	<li class="list-group-item d-flex justify-content-between align-items-center">
								    		<span class="mr-auto"><b>Cash o pos</b></span>
								    		<span class="badge badge-primary badge-pill" id='totalOrder'>
								    			<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${totalOrder-order.table.budget}"/> €
										    </span>
								    	</li>
								    </c:if>
							    	<br>
							    	<br>
							    	<h4  style='color:#fff; text-align:center;'><i class="fas fa-credit-card"></i> Metodo di pagamento</h4>
							    	<select class="selectpicker form-control" id="pay_method" style='width:85%;'>
							    		<c:set var="disabledCumulabile" value="selected" scope="page"/>
										<c:if test="${(order.table.budget-totalOrder)<0}">
							    		    <c:set var="disabledCumulabile" value="disabled" scope="page"/>
										</c:if>
										<c:if test="${(order.table.budget-totalOrder)>=0}">
							    		    <c:set var="disabledCombo" value="disabled" scope="page"/>
							    		    <c:set var="disabledCashPos" value="disabled" scope="page"/>
										</c:if>
										<c:if test="${(order.table.budget)>0}">
							    		    <c:set var="disabledCashPos" value="disabled" scope="page"/>
										</c:if>
										<c:if test="${order.table.budget<1}">
							    		    <c:set var="disabledCombo" value="disabled" scope="page"/>
										</c:if>
				        				<option class="bs-title-option" value="cumulabile" <c:out value="${disabledCumulabile}" /> >Cumulabile</option>
				        				<option class="bs-title-option" value="cumulabile_cash" <c:out value="${disabledCombo}" /> >Cumulabile+cash</option>
				        				<option class="bs-title-option" value="cumulabile_pos" <c:out value="${disabledCombo}" /> >Cumulabile+pos</option>
				        				<option class="bs-title-option" value="cash" <c:out value="${disabledCashPos}" />>Cash</option>
				        				<option class="bs-title-option" value="pos" <c:out value="${disabledCashPos}" />>Pos</option>
				        			</select><br><br>
							    	<div class='btn-large'>
							    		<button type="button" id="submitButton" class="btn btn-primary btn-lg btn-block" onclick="orderConfirm(<c:out value='${totalOrder}'/>)"><i class="far fa-check-circle"></i> Conferma Ordine</button>
							    	</div>
							    </c:if>
						    </ul>
							<!-- ********************** FINE VISUALIZZAZIONE PRODOTTI CARRELLO *******************  -->
							<!-- ************************************************************************************ -->
							
						    <div class="line"></div>
	
						    	<!-- end order preview -->
					    	
                   </div><!-- chiude il div row -->
               </section>
        </section>
<jsp:include page="footer.html"/>
<script>
	/*FUNZIONI PER APRIRE LE VARIE SEZIONI*/
	
	function openOrderDetails(){
		$("#orderDetailsSection").fadeToggle(200);
	}
	/***********************************/
	function CloseAlert(){
		$("#alertBox").hide();
	}
	
	function OpenAlert() {
		//$("#alertBox").show(70);

		$('.toast').toast('show');
	}
	
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
	
	function addToCart(id){
			var quant=document.getElementById("quant"+id).value;
			var not=document.getElementById("note"+id).value;
			
			document.getElementById("quant"+id).value="";
			document.getElementById("note"+id).value="";
			
			if(!quant || quant<1){
				$("#msg").text("Inserisci Quantita'");
				document.getElementById("quant"+id).value=1;
				OpenAlert();
				return;
			}
			
			//se la quantità è già arrivata a 0 non faccio proprio la chiamata alla servlet
			if($("#qta"+id).text()==0){
				$("#msg").text("Esaurito!");
				OpenAlert();
				return;
			}
	
			$.get("AddProductToCart?id=" + id + "&quant=" + quant + "&note=" + not,
				function(data){
					document.getElementById("quant"+id).value=1;
					document.getElementById("note"+id).value="";
					$("#orderList").load(location.href + " #orderList");
					$(".containerDetails").load(location.href + " .containerDetails");
					
					if(data!=1){
						$("#msg").html(data);
						OpenAlert();
					}else{
					    //se non ci sono stati errori,scalo la quantità dal div
			    	 	quantity=$("#qta"+id).text()-quant;
			    		$("#qta"+id).text(quantity);
					}
			});	
	}
	
	function DelToCart(id,quant){
		
		$.get("RemoveProductFromCart?id=" + id, 
				function(data){
					$("#qta"+id).text(quant);
					$("#orderList").load(location.href + " #orderList");
					$(".containerDetails").load(location.href + " .containerDetails");
					
			});
	}
	
	function orderConfirm(tot){
		
		var methodPay=$('#pay_method').find(":selected").val();
		
		//disabilito il pulsante
		$('#submitButton').attr('disabled',true);
		
		$.post("OrderConfirm", 
				{	tot: tot
				},function(data){
					$("#msg").html(data);
					OpenAlert();
					setTimeout(function(){ location.href='.'; }, 1500);
				});
	}
	
	function applyDiscount(){
		$.get("ApplyDiscount", 
				function(data){
					$("#orderList").load(location.href + " #orderList");
					$(".containerDetails").load(location.href + " .containerDetails");
				});
	}
	
	function addDetail(id){
		check=false;
		if(!$('#checkbox'+id).is(":checked"))
			check=true;

		//passo se è settata o meno così la servlet a seconda del parametro,aggiunge o elimina quel dettaglio
		$.get("AddDetailToCart?check="+check+"&id="+id, 
				function(data){
					$(".containerDetails").load(location.href + " .containerDetails");
					//updatePage();
				});
	}
	
	function removeDetail(id){
		check=false;
		if(!$('#checkbox'+id).is(":checked"))
			check=true;

		//passo se è settata o meno così la servlet a seconda del parametro,aggiunge o elimina quel dettaglio
		$.get("RemoveDetailFromCart?check="+check+"&id="+id, 
				function(data){
					$(".containerDetails").load(location.href + " .containerDetails");
					//updatePage();
				});
	}
	
	function detailConfirm(){
		$.get("OnlyDetailsConfirm", 
				function(data){
					$("#msg").text(data);
					OpenAlert();
					setTimeout(function(){ location.href='.'; }, 1500);
				});
	}
	
</script>
</body>
</html>