<!DOCTYPE html>
<html>
<?php

require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/prDAO.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/ClientListDAO.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";
require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/ClientList.php";

session_start();
if($_SESSION["logUser"]===null) header("location:login.html");


?>
<head>
	<meta charset="utf-8">
	<title>Mythos Club-PR</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/poupup.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	<link rel="shortcut icon" href="images/favicon.ico" />   
	<link rel="stylesheet" href="css/swiper.min.css">
    <link rel="stylesheet" href="css/slide.css">
</head>
<body>
		<!-- **************** includo l'header **************** -->
		<?php include "header.php"?>
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
			<br>
			<section class="content" >
             <section class="container">
                <div class="row">
					<div style="width:99%;text-align:center;">
						<hr style='margin-top:5px;background-color:#fff;  border: 1px solid white;width:99%;'>
							<h2 style='margin-bottom:15px;'>&nbsp;<i class="fas fa-plus-circle"></i> Aggiungi cliente<br><br><br></h2>
							<form action="src/control/AddToList.php" method='post' id="f1" name="f1" style="width:99%; margin:auto;"><!--[A-Za-z’´']+[A-Za-z’´' ]*-->
									<input type="text" name="name" class="form-control" style='width:99%;margin:auto;margin-bottom:10px;' pattern="[A-Za-z&egrave;&ograve;&agrave;&ugrave;&igrave;’´']+[A-Za-z&egrave;&ograve;&agrave;&ugrave;&igrave;’´' ]*" required placeholder='Nome' title="Inserire solo lettere! No caratteri speciali o numeri! Non può iniziare con uno spazio!">
									<input type="text" name="lastname" class="form-control" style='width:99%;margin:auto;' pattern="[A-Za-z&egrave;&ograve;&agrave;&ugrave;&igrave;’´']+[A-Za-z&egrave;&ograve;&agrave;&ugrave;&igrave;’´' ]*" required placeholder='Cognome' title="Inserire solo lettere! No caratteri speciali o numeri o! Non può iniziare con uno spazio!">
								
								<br>
								<button type="submit" class="btn btn-primary btn-lg btn-block" onclick="return reserve()"><i class="fas fa-plus-circle"></i> Inserisci</button>
								<br><br><br><br><br>
								<hr style='margin-top:10px;background-color:#fff;  border: 1px solid white;width:99%;'>
							</form>
									
							<h2 style='margin-bottom:15px;'>&nbsp;<i class="fas fa-clipboard-check"></i> Clienti in lista<br><br><br></h2>
							<div class='table-responsive'>
								<table class="table" border=0 style='background-color:white; text-align:center; margin-bottom:20px;'>
								<thead class="thead-dark">
									<tr>
									<th scope="col">Codice</th>
									<th scope="col">Cognome</th>
									<th scope="col">Nome</th>
									<th scope="col">Data</th>
									<th scope="col">Entrato</th>
									<th scope="col">Elimina</th>
									</tr>
								</thead>
								<tbody>
								
									<?php
                                    $listDao=new ClientListDAO();
                                    $clients=$listDao->doRetrieveByPrId($_SESSION["logUser"]->getUsername());

                                    foreach ($clients as $value){
									?>
										<tr>
											<td title=''><?=$value->getClient_id()?></td>
											<td title=''><?=$value->getSurname()?></td>
											<td title=''><?=$value->getName()?></td>
											<td title=''><?=$value->getAdded_date()?></td>
											<td title=''>
												<?php
													if($value->isEntered()==true)
														echo "<i class='fas fa-circle' style='color:#32CD32;font-size:20px;'></i>";
													else
														echo "<i class='fas fa-circle' style='color:#e10b00;font-size:20px;'></i>";
												?>
											</td>
											<td title='elimina ingresso'>
											<input type='hidden' value="<?=$value->getName()?>" id='clientName<?=$value->getClient_id()?>'><a onclick=fun(<?=$value->getClient_id()?>)><i class="fas fa-user-times" style='cursor:pointer; font-size:20px;'></i></a></td>
										</tr>
									<?php
									}
									?>
								</tbody>
								</table>
							</div>
						
                  
					
						</div>
                   </div><!-- chiude il div row -->
               </section>
        </section>
		<?php
			include "footer.html";
		?>
		
		<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
<script>
	function fun(id){
		name=$("#clientName"+id).val();
		if(confirm("Sei sicuro di voler eliminare "+ name +"?")) {
			delClientToList(id);
		}
	}
	function CloseAlert() {
		$("#alertBox").hide();
	}


	function OpenAlert() {
		$(" .toast").toast('show');
	}
	function delClientToList(pr_id){
		$.get("src/control/DelClientToList.php?id=" + pr_id,
			function(data){
		        //alert(data);
				$("body").load("index.php");
				//location.href="index.php";
			});
	}
	<?php
        $addedId="";
        if($_GET){
            if (isset($_GET["addedId"])){
                $addedId=$_GET["addedId"];
    ?>
                    $( document ).ready(function() {
                        $("#msg").html("Aggiunto cliente <br>con codice <h2 style='display:inline;'><?=$addedId?></h2>");
                        OpenAlert();
                    });
    <?php
            }
            else if (isset($_GET["errorMessage"])) {
                $errorMessage=$_GET["errorMessage"];
    ?>
                    $( document ).ready(function() {
                        $("#msg").html("<?=$errorMessage?>");
                        OpenAlert();
                    });
    <?php
            }
	?>

				
	<?php
		}
	?>
	
</script>

</body>
</html>