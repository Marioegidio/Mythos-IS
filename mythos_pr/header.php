    <header>
        <nav class="container-fluid ">
            <div class="row text-center" style='display:inline;font-size:25px;'>
                <div class="logo" style="vertical-align:middle;float:left;margin-top:8px; margin-bottom:5px;">
                    <a class="navbar-brand" href="#" style="border-bottom:4px solid #5A565E; border-radius:90px; padding:13px; margin:auto; background-color:#FFFFF0;"> 
                        <img src="images/logo.png" style=" margin-top:10px;width: 200px" alt="Logo">
                    </a>
                </div>
                

                <div  class='headSpan' style='vertical-align:middle;'>

                        <span><i class="fas fa-user"> <?=$_SESSION["logUser"]->getUsername()?></i>&nbsp;&nbsp;</span>
                    
                    <div class="" style='vertical-align:middle;font-size:30px;margin-top:0;padding:0;float: right;'>
                        <a href="logout.php" style='color:white;'> <i class="fas fa-sign-out-alt"></i></a>
                    </div>
                </div>
            </div>

        </nav>
    </header>