<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>eVote</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    
  
   
  
</head>

<body onload = "func()" >

 
<script>
        var timer = setTimeout(function() {
            window.location='main.html'
        }, 600000);
</script> 
    

    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="voter-home.html">eVote</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Username <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="voter-profile.html"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="voter-settings.html"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="main.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="voter-home.html"><i class="fa fa-fw fa-home"></i> Home</a>
                    <li>
                        <a href="voter-ballot.html"><i class="fa fa-fw fa-edit"></i> Vote</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-table"></i> Results<i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="voter-results.html"> Spring 2016 Primaries</a>
                            </li>
                            <li>
                                <a href="voter-results.html"> Fall 2016 Election</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Winter 2016 District 12 Ballot<br>
                        </h1>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-6">
                        <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                           
                            <div class="form-group">
                            
                             <div id="ballot1"> </div>
                             <div id="candidate1"> </div>
                             <div id="candidate2"> </div>
                             <div id="candidate3"> </div>
                              <div id="candidate4"> </div>
                             
                            <div>
                            <input type="submit" value="Submit">
                            </div>
                              
                            
                        </form>
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot4"> </div>
                             <div id="candidate5"> </div>
                             <div id="candidate6"> </div>
                             <div id="candidate97"> </div>
                           
                           
                             
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot7"> </div>
                             <div id="candidate7"> </div>
                             <div id="candidate8"> </div>
                             <div id="candidate9"> </div>
                           
                          
                             
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot10"> </div>
                             <div id="candidate10"> </div>
                             <div id="candidate11"> </div>
                             <div id="candidate12"> </div>
                            <div id="radio4"> </div>
                            </div>
                        </form>
                        
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot13"> </div>
                             <div id="candidate13"> </div>
                             <div id="candidate14"> </div>
                             <div id="candidate15"> </div>
                             <div id="radio5"> </div>
                             
                             
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot16"> </div>
                             <div id="candidate16"> </div>
                             <div id="candidate17"> </div>
                             <div id="candidate18"> </div>
                             <div id="radio6"> </div>
                             
                             
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                             
                             <div id="ballot19"> </div>
                             <div id="candidate19"> </div>
                             <div id="candidate20"> </div>
                             <div id="candidate21"> </div>
                           
                             
                             
                            </div>
                        </form>
                        
                        <form role="form">
                            <div class="form-group">
                            
                            <div id="r1"> </div>
                            
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                            
                            <div id="r2"> </div>
                            
                            </div>
                        </form>
                        <form role="form">
                            <div class="form-group">
                            
                            <div id="r3"> </div>
                            
                            </div>
                        </form>
                        
                    </div>
                </div>
            </div>
            <!-- /.row -->
         
        </div>
        <!-- /#wrapper -->
        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>
        <!-- Morris Charts JavaScript -->
        <script src="js/plugins/morris/raphael.min.js"></script>
        <script src="js/plugins/morris/morris.min.js"></script>
        <script src="js/plugins/morris/morris-data.js"></script>
   
   
   
	<script language="javascript">
	function checkB1() {
	
	if (document.getElementById('c1').checked) {
		
      	
    } else if(document.getElementById('c2').checked){
        alert("Box 2 is checked");
    } else if(document.getElementById('c3').checked)
    	alert("Box 3 is checked");
	}
  </script>
	
	<script language="javascript">
	function checkB2() {
	
	if (document.getElementById('c4').checked) {
        alert("Box1 is checked");
    } else if(document.getElementById('c5').checked){
        alert("Box 2 is checked");
    } else if(document.getElementById('c6').checked)
    	alert("Box 3 is checked");
	}
  </script>
  <script language="javascript">
	function checkB3() {
	
	if (document.getElementById('c7').checked) {
        alert("Box1 is checked");
    } else if(document.getElementById('c8').checked){
        alert("Box 2 is checked");
    } else if(document.getElementById('c9').checked)
    	alert("Box 3 is checked");
	}
  </script>
  
  
	
   
</body>
</html>



	<%@ page language="java" import ="java.util.Date" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
	
	
	
	Connection conn = null;
    ObjectLayer objectLayer = null;
    PersistenceLayer persistence = null;
    
  
    String dist = request.getParameter("dist");
    String name = request.getParameter("cc1");
    String content = "";
   
    List<String> list = new ArrayList<String>();
    
    System.out.println("Dist " + dist );
  
    try {
        conn = DbUtils.connect();
    } 
    catch (Exception seq) {
        System.err.println( "WriteTest: Unable to obtain a database connection" );
    }
    
    if( conn == null ) {
        System.out.println( "WriteTest: failed to connect to the database" );
        return;
    }
    
    // obtain a reference to the ObjectModel module      
    objectLayer = new ObjectLayerImpl();
    // obtain a reference to Persistence module and connect it to the ObjectModel        
    persistence = new PersistenceLayerImpl( conn, objectLayer ); 
    // connect the ObjectModel module to the Persistence module
    objectLayer.setPersistence( persistence );
    
    try {
    	
    	
    	 String label2 = "";
    	 String label3 = "WOW";
    	    
    	Ballot ballot = objectLayer.createBallot();
    	List <Ballot> ballots = persistence.restoreBallot(ballot);
    	int ccount = 0;
    	int bcount = 0;
    	HttpSession sess = request.getSession(false); 
    	sess.setAttribute("ecount", 0);
    	sess.setAttribute("ccount", 0);
    	sess.setAttribute("bacount", 0);
		sess.setAttribute("icount", 0);
    	//search for ballots in voter's electoral district
    	for(Ballot b : ballots)
    		//matching voter district with ballot district
    		if(b.getId()==(long)sess.getAttribute("district")) {
    			
    			
    			
    			SimpleDateFormat ft = 
              	      new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
			   
    		   Date now = new Date();
               Date open1 = ft.parse(b.getCloseDate());
               Date close1 = ft.parse(b.getOpenDate());
    		   String st = "";
               System.out.println("CAN YOU SEE Now " + b.getCloseDate() + "   " + open1 + "   " + close1);
    			
    			if(open1.before(now) && close1.after(now)) {
    			//restore ballot items from ballot
    			BallotItem item = objectLayer.createBallotItem(b,0,-1,-1);
    			List <BallotItem> items = persistence.restoreBallotItem(item);
    	
				
				List<String> bl = new ArrayList<String>();
				List<String> cs = new ArrayList<String>();		
    			//check if item is an election or an issue
    			for(BallotItem i : items) {
    			System.out.println("Size " + items.size() +" "+ i.getElectionId() + " " + i.getIssueId());	
					
    			
    			
    				if(i.getElectionId() != -1) {
    					
    					Election election = objectLayer.createElection(null,false);
    					election.setId(i.getElectionId());
    					
    					List <Election> elections = persistence.restoreElection(election);
    					sess.setAttribute("off", elections.get(0).getOffice());
					
    					if(!bl.contains(elections.get(0).getOffice())) {
    						bl.add(elections.get(0).getOffice());
    						bcount++;
    						sess.setAttribute("ballot", elections.get(0).getOffice());
    						%>
				          	<script language="javascript">
				          	var div = document.createElement('div');
				          	
				            div.innerHTML = '<label> ${ballot} </label>';
				              document.getElementById("ballot" + ${bcount} ).appendChild(div);
				             
				          	</script>
				          	<%
    					}
    					
    					Election election1 = objectLayer.createElection(elections.get(0).getOffice(),false);
    					List <Election> elections1 = persistence.restoreElection(election1);
						
						Candidate cande = objectLayer.createCandidate(null,null,elections1.get(0).getId(),0);
						List<Candidate> cande2 = persistence.restoreCandidate(cande);
						
						for(Candidate c :cande2) {
							sess.setAttribute("candidate", c.getName());
							sess.setAttribute("ccount", (int)sess.getAttribute("ccount")+1);
							%>
				          	<script language="javascript">
				          	var div = document.createElement('div');
				          	
				            div.innerHTML =' <div class="radio" > <label>'
				            + '<input type="radio" name=cc1 value=${candidate}> ${candidate}'
				            +' </label><br> </div>';
				              document.getElementById("candidate" + ${ccount} ).appendChild(div);
				             
				          	</script>
				          	<%
						}
						ccount=0;
    					
    				} 
    				else {
    					Issue issue = objectLayer.createIssue(null,-1,-1,b);
    					issue.setId(i.getIssueId());
    					List <Issue> issues = persistence.restoreIssue(issue);
    					
    					for( Issue is: issues) {
    						sess.setAttribute("icount", (int)sess.getAttribute("icount") +1);
    						System.out.println("Issue " + is.getQuestion());
    						sess.setAttribute("Issue", is.getQuestion());
    						
    						
    						%>
				          	<script language="javascript">
				          	var div = document.createElement('div');
				          	
				            div.innerHTML = 
	                       '<div class="form-group"> <div class="form-group"> <label>${Issue}</label>'
	                      + '</div> <div class="radio"> <label> <input name="optionsRadios" id="optionsRadios2" '
	                      + ' value="option1" name = "y" type="radio">Yes </label> '
	                       + '</div> <div class="radio"> <label> <input name="optionsRadios" id="optionsRadios2" '
	                      + ' value="option2" name = "n" type="radio">No </label><br> <br> <br> </div> </div>  </div>';
	                       document.getElementById("r" + ${icount} ).appendChild(div);
				             
				            
				             
				          	</script>
				          	<%
    						
    					}
    					
    				}
    				
    				
    			}
    		}
    	
    	
    	
    	
    	
    		}	
    }
        
    catch( EVException ce) {
        System.err.println( "Exception: " + ce );
        ce.printStackTrace();
    }
    catch( Exception e ) {
        e.printStackTrace();
    }
    finally {
        // close the connection
        try {
            conn.close();
        }
        catch( Exception e ) {
            System.err.println( "Exception: " + e );
        }
    }
    
    
    
%>

	<script language="javascript">
	          	function adde(String) {
    			var div = document.createElement('div');
    			
    			var Value = String;
				alert(Value);
    			
	          	}
				</script>
    			
    			
				 
