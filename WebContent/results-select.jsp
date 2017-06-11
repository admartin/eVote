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
                           Select a Ballot <br>
                        </h1>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-6">
                        
                        <form action ="voter-result.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                             <div id="ballot1"> </div>
                            </div>
                            
                        </form>
                        
                        <form  action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot2"> </div>
                           
                            </div>
                        </form>
                        
                        
                        <form  action ="voter-result.jsp"role="form">
                            <div class="form-group">
                             
                             <div id="ballot3"> </div>
                             
                            </div>
                        </form>
                        
                        
                        <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot4"> </div>
                            
                            </div>
                        </form>
                      
                        <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot5"> </div>
                            
                            </div>
                        </form>
                      
                        <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot6"> </div>
                            
                            </div>
                        </form>
                      
                        <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot7"> </div>
                            
                            </div>
                        </form>
                        
                        <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot8"> </div>
                            
                            </div>
                        </form>
                      
                      <form action ="voter-result.jsp" role="form">
                            <div class="form-group">
                             
                             <div id="ballot9"> </div>
                            
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
         
    		      
   
</body>
</html>


	<%@ page language="java" import ="java.util.Date" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
	
	
	
	Connection conn = null;
    ObjectLayer objectLayer = null;
    PersistenceLayer persistence = null;
    
    System.out.println("TRYING THIS " + request.getParameter("bal"));
  
    
  
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
    	
    	
    	
    	Ballot ballot = objectLayer.createBallot();
    	List <Ballot> ballots = persistence.restoreBallot(ballot);
    	
    	
    	HttpSession sess = request.getSession(false); 
    
    	sess.setAttribute("balcount", 0);
		
		List<String> list = new ArrayList<String>();
		
		for(Ballot b : ballots) {
			
			if(b.getElectoralDistrict().getId()==(long)sess.getAttribute("district")) {
			
				
				
				SimpleDateFormat ft = 
              	      new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
			   
    		   Date now = new Date();
               Date open1 = ft.parse(b.getCloseDate());
               Date close1 = ft.parse(b.getOpenDate());
    		
    			if(true) {

    				sess.setAttribute("balcount", (int)sess.getAttribute("balcount")+1);
    				sess.setAttribute("balo", b.getName());
    				sess.setAttribute("balid", b.getId());
					
		    	 	%>
		          	<script language="javascript">
		          	var div = document.createElement('div');
		          	

		            div.innerHTML = '<label > ${balo} </label> <br>'
    				+ '<button type="submit" name ="bal" value = ${balid} class="btn-default"> View </button>';
		              document.getElementById("ballot" + ${balcount} ).appendChild(div);
		              
		             
		          	</script>
		          	<%
		          	
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
