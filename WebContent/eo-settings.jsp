<!DOCTYPE html>
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
<body>
<script>
        var timer = setTimeout(function() {
            window.location='main.html'
        }, 600000);
</script>
<script language="javascript">
        function verify(){
        	if(confirm("Are you sure you wish to delete your account?")){
        		window.location.href = "delete-eo.jsp";
        	}
        	return false;
        }
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
                <a class="navbar-brand" href="eo-home.html">eVote</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <input type="text" value="${username}" /> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="eo-profile.jsp"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="eo-profile.jsp"><i class="fa fa-fw fa-gear"></i> Settings</a>
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
                    <li>
                        <a href="eo-home.html">
                            <i class="fa fa-fw fa-home"></i>
                            Home
                        </a>
                    </li>

                    <li>
                        <a href="javascript:;" data-toggle="collapse"
                           data-target="#create">
                            <i class="fa fa-fw fa-table"></i>
                            Create<i class="fa fa-fw fa-caret-down"></i>
                        </a>
                        <ul id="create" class="collapse">
                            <li> <a href="add-ballot.html"> Ballot</a> </li>
                            <li>
                                <a href="add-election.html"> Election</a>
                            </li>
                            <li> <a href="add-issue.html"> Issue</a> </li>
                            <li> <a href=# onclick = "addDist()"> District</a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse"
                           data-target="#valid">
                            <i class="fa fa-fw fa-table"></i>
                            Validate<i class="fa fa-fw fa-caret-down"></i>
                        </a>
                        <ul id="valid" class="collapse">
                            <li> <a href="validate-ballot.html"> Ballot</a> </li>
                            <li>
                                <a href="validate-election.html"> Election</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="eo-view.html"> View Results</a>
                    </li>
                    <li>
                        <a href="ballot-search.html"> Ballot Search</a>
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
                            eVote <small>Profile</small>
                        </h1>
                    </div>
                </div>
                <!-- /.row -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header"></h1>
                        </div>
                    </div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">  <input id="email" type="text" value="${email}" onfocus="this.value='';"/> </p>
                        </div>
                      </div>
                     <div class="form-group">
                        <label class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">
                			<input id="password" name="password" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must contain 8 or more characters, one number, and one uppercase letter' : ''); if(this.checkValidity()) form.password2.pattern = this.value;" value="${password}" onfocus="this.value='';"/>
                        	</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10">
                            <p class="form-control-static">
                            <input id="password2" name="password2" type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');" value="${password}" onfocus="this.value='';"/>
                            </p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group-row" style="padding:inherit">
                            <a href="eo-profile.jsp" class="btn btn-default" role="button" style="float:right">Cancel</a>
                            <a href="eo-profile.jsp" class="btn btn-default" role="button" style="float:right">Save</a>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group-row" style="padding:inherit">
                            <button class="btn btn-danger" onclick="return verify()" style="float:right">Delete Account</button>
                        </div>
                    </div>
                </form>
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /#page-wrapper -->
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

	<%@ page language="java" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	    
	    HttpSession sess = request.getSession(false);
	    String username = (String) sess.getAttribute("username");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
	    sess.removeAttribute("email");
	    sess.setAttribute("email", email);
	    sess.removeAttribute("password");
	    sess.setAttribute("password", password);
	    
	    System.out.println("USERNAME" + username );
	    
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
	    	
	    	ElectionsOfficer eo1 = objectLayer.createElectionsOfficer(null, null, username, password, email, null);
	    	
            List <ElectionsOfficer> eolist = persistence.restoreElectionsOfficer(eo1);
           
            if(eolist.size() < 1)
            {    %>
            	<script language="javascript">
            	alert("UH OH");
            	</script>
            	<%
            }
           
            else
            {
            	persistence.storeElectionsOfficer(eo1);
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
	