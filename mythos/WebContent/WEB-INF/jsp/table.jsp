<%@page language="java" contentType="text/html; charset=UTF-8"
    	pageEncoding="UTF-8"
    	import="javax.servlet.*,entity.*,java.util.*" %>
     <table class='maptable' border=0>
                        <tr>
                                <td rowspan=3 colspan=2 class="td-featureTop"></td>
                                <td id="1" >1</td>
                                <td id="2" >2</td>
                                <td id="3" >3</td>
                                <td id="4" >4</td>
                                <td id="23">23</td>
                                <td></td>
                        </tr>
                        <tr>
                                
                                <td class='min'></td>    
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                               
                        </tr>
                  
                        <tr>
                        		<td></td>
                                <td class='td-console' colspan=2></td>
                                <!--  scale top of map <td class='td-stairsTop'></td> -->
                                <td id="24">24</td>
                                <td></td>
                                <td id="26">26</td>
                        </tr>
                        <tr>
                                <td id="19" >19</td>
                                <td class='min'></td>
                                <td id="20" >20</td>
                                <td class='td-atrium'  rowspan="2" style="border-top-right-radius:0px;border-bottom-right-radius:0px; border-bottom-left-radius:0px;" ></td>
                                
                                <td class='td-atrium'  rowspan="4" style="border-top-left-radius:0px; border-bottom-left-radius:0px;"></td>
                                <td id="5" >5</td>
                                <td></td>
                                <td id="6"  rowspan="2"> 6</td>
                        </tr>
                        <tr>
                                <td id="18" >18</td>
                                <td class='min'></td>
                                <td id="17" >17</td>
                                <td id="7" >7</td>
                                <td></td>
                                
                        </tr>
                        <tr>
                                <td id="15" >15</td>
                                <td class='min'></td>
                                <td id="16" >16</td>
                                <td class='td-atrium' style="border-top-right-radius:0px; border-bottom-right-radius:0px; border-top-left-radius:0px; border-bottom-left-radius:0px;"></td> 
                                <td id="8" >8</td>
                                <td></td>
                                <td id="9"  rowspan="2">9</td>
                        </tr>
                        <tr>
                                <td id="14">14</td>
                                <td class='min'></td>
                                <td id="13" >13</td>
                                <td class='td-atrium' style="border-top-right-radius:0px; border-bottom-right-radius:0px; border-top-left-radius:0px;"></td> 
                                <td id="10" >10</td>
                                <td></td>
                        </tr>
                        <tr>
                                <td ></td>
                                <td class='min'></td>
                                <td class='td-stairs'></td>
                                <td></td>
                                <td></td>
                                <td id="25">25</td>
                                <td class='td-stairsTop'></td>
                                <td></td>
                        </tr>
                        <tr>
                                <td></td>
                                <td class='min'></td>
                                <td></td>
                                <td id="12">12</td>
                                <td id="11">11</td>
                                <td></td>
                                <td></td>
                                <td></td>
                        </tr>
                </table>
                <%
                //?aprire script prima per non farlo ad ogni cliclo del for?
				                ArrayList<Table> tables=(ArrayList<Table>)request.getAttribute("tables");
								int splitLength=9;
						  		for(Table t: tables){
						  			if(!t.isAssigned())
								    	out.write("<script> $('#"+t.getTableNumber()+"').addClass('td-available');</script>");
						  			else{
						  				out.write("<script> $('#"+t.getTableNumber()+"').addClass('td-unavailable');");
								    	if (t.getReservationName().length() > splitLength)
					  							out.write("$('#"+t.getTableNumber()+"').append('<br>"+ t.getReservationName().trim().substring(0,splitLength)+".');"+
							    				"</script>");
								    	else
								    		out.write("$('#"+t.getTableNumber()+"').append('<br>"+ t.getReservationName().trim()+"');"+
								    				"</script>");	
						  			}
								    	
								}
				%>
				
					
   