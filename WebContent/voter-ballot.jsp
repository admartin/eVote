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
                         <div id="main"> </div>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-6">
                        
                        <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec1"> </div>  
                             <div id="ballot1"> </div>
                             <div id = "submit1"> </div>
                            
                            </div>
                            
                        </form>
                        
                       <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec2"> </div>  
                             <div id="ballot2"> </div>
                             <div id = "submit2"> </div>
                           
                               </div>
                            
                        </form>
                        
                         <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec3"> </div>  
                             <div id="ballot3"> </div>
                             <div id = "submit3"> </div>
                           
                               </div>
                            
                        </form>
                      
                        <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec4"> </div>  
                             <div id="ballot4"> </div>
                             <div id = "submit4"> </div>
                                                           </div>
                            
                        </form>
                        
                        <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec5"> </div>  
                             <div id="ballot5"> </div>
                             <div id = "submit5"> </div>
                           
                               </div>
                            
                        </form>
                        
                        <form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec6"> </div>  
                             <div id="ballot6"> </div>
                             <div id = "submit6"> </div>
                           
                               </div>
                            
                        </form>
                        
							<form action ="voter-submit.jsp" method="post" name = "ballot1" role="form">
                            <div class="form-group">
                            
                            <div id="elec7"> </div>  
                             <div id="ballot7"> </div>
                             <div id = "submit7"> </div>
                          
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
    
  
    String elec = request.getParameter("bal");
    String name = request.getParameter("cc1");
    String main = request.getParameter("main");
    System.out.println("THIS BETTER SAY 1 " + name + " " + elec + " "+ main);
    String content = "";
    List<String> cands = new ArrayList<String>(); 
    List<String> offs = new ArrayList<String>(); 
    
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
    	
    	%>
      	<script language="javascript">
      	var div = document.createElement('div');
      
        div.innerHTML = ' <h1 class="page-header"> '
             + '${main} </h1>';
          document.getElementById("main" ).appendChild(div);
        
      	</script>
      	<% 
	 	
    	
    	Ballot ballot = objectLayer.createBallot(null,null,null,false,null);
    	ballot.setId(Long.parseLong(elec));
    	List <Ballot> ballots = persistence.restoreBallot(ballot);
    	
    	int ccount = 0;
    	
    	HttpSession sess = request.getSession(false); 
    	sess.setAttribute("ecount", 0);
    	sess.setAttribute("ccount", 0);
    	sess.setAttribute("bcount", 0);
		sess.setAttribute("icount", 0);

    	//search for ballots in voter's electoral district
    	for(Ballot b : ballots) {
    		//matching voter district with ballot district
    	
    			//restore ballot items from ballot
    			BallotItem item = objectLayer.createBallotItem(null,0,-1,-1,b.getId());
    			List <BallotItem> items = persistence.restoreBallotItem(item);
    			System.out.println("items " + items.size());
				
    			//check if item is an election or an issue
    			for(BallotItem i : items) {
    				if(i.getElectionId()!=-1) {
    					
    					Election election = objectLayer.createElection(null,false);
    					election.setId(i.getElectionId());
    					Candidate c = objectLayer.createCandidate(null,null,election.getId(),-1);
    					
    					List<Candidate> can = persistence.restoreCandidate(c);
    					List<Election> offices = persistence.restoreElection(election);

    					
    					if(!offs.contains(offices.get(0).getOffice())) {
    						offs.add(offices.get(0).getOffice());
    					}
    					if(!cands.contains(can.get(0).getName())) {
    						cands.add(can.get(0).getName());
    					}
    					
    					
    				}
    			}
    			
    		
    		} //sort ballots
    	
    	//get voter
    	Voter voter = objectLayer.createVoter((String)sess.getAttribute("username"),null,null
     			,null,null,null,null,-1,null,-1);
     	
     	voter.setId((long)sess.getAttribute("userid"));
     	List<Voter> vot = persistence.restoreVoter(voter);
		//get voter records
     	VoteRecord record = objectLayer.createVoteRecord(null,vot.get(0),null,-1);
		List<VoteRecord> recs = persistence.restoreVoteRecord(record);
		List<String> toRemove = new ArrayList<String>();

		for(VoteRecord rec : recs) {
		for(String o :offs) {
			Election election = objectLayer.createElection(o, false);
      		List<Election> elcs = persistence.restoreElection(election);
		for(Election e: elcs) {
			System.out.println("MAYBE THIS " + e.getOffice());
			
				System.out.println("mfkdlmdsklfm of " + rec.getItemId());
				if(e.getId()==rec.getItemId()) {
					System.out.print("A MATCH at" + e.getOffice());
					if(!toRemove.contains(o)) {
						 toRemove.add(o);
				}}
			} 
  			}
		}
		offs.removeAll(toRemove);

		
		
		
		
		System.out.println("PLEASE" + offs.size());
    	for(String o :offs) {
    		
    		Election ee = objectLayer.createElection(o,false);
    		List<Election> els = persistence.restoreElection(ee);
    		sess.setAttribute("bcount", (int)sess.getAttribute("bcount")+1);
    		sess.setAttribute("bal", o);
    		sess.setAttribute("ecount", (int)sess.getAttribute("ecount")+1);

    		
    		%>
          	<script language="javascript">
          	var div = document.createElement('div');
          	var div2 = document.createElement('div');
         
              div.innerHTML = ' <input type="form-group" name=b1 value=${bal}>';
              document.getElementById("elec" + ${ecount} ).appendChild(div);
              div2.innerHTML = '  <div> <input type="submit" value="Submit"> </div>';
              document.getElementById("submit" + ${ecount} ).appendChild(div2);
          	</script>
          	<%
    	 	
    	 	
          	System.out.println("vrsize " + recs.size() );
          	System.out.println("elecs size " + els.size() );
          	System.out.println(); 
          
          	
          	System.out.println("vrsize " + recs.size() );
          	System.out.println("elecs size " + els.size() );
        	
              
              	
    		for(Election es : els) {
    		
    		
    		
    	 	Candidate c = objectLayer.createCandidate(null,null,es.getId(),-1);	
    	 	List<Candidate> cs = persistence.restoreCandidate(c);
    	 	
    	 	for(Candidate can : cs){
    	 	System.out.println("Candidate " +can.getName() + " on ballot " + o );
    	 	sess.setAttribute("can", can.getName());
    	 	
    	 	%>
          	<script language="javascript">
          	var div = document.createElement('div');
          	

            div.innerHTML = ' <div class="radio" > <label>'
            + '<input type="radio" name=cc1 value=${can}> ${can}'
            +' </label><br> </div>';
              document.getElementById("ballot" + ${bcount} ).appendChild(div);
             
          	</script>
          	<%
    	 	} //for cands
          	
    	
    	
    	}//for all elections
    }}
        
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
