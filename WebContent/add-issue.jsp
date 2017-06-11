
<html>
<body>

	<%@ page language="java" import="java.text.DateFormat" import="java.text.SimpleDateFormat" import="java.sql.Connection" import="java.util.Date"  import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String iss = request.getParameter("issue");
	    String balname = request.getParameter("ballot");
	    
	       
	    System.out.println("Issue " + iss );
	    
	    
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
	    	Ballot b2 = objectLayer.createBallot(balname,null,null,false,null);
	    	
	    	List <Ballot> ballots2 = persistence.restoreBallot(b2);
	    	
	    	System.out.println("GOT ballotID " + balname);
	    	
	    	if(ballots2.size() == 1){
		    	Issue i1 = objectLayer.createIssue(iss,0,0,ballots2.get(0));
		    	persistence.storeIssue(i1);
		    	List <Issue> issues = persistence.restoreIssue(i1);
		    	
		    	BallotItem bi1 = objectLayer.createBallotItem(ballots2.get(0), 0, -1,issues.get(0).getId());
		    	persistence.storeBallotItem(bi1);
		    	System.out.println("GOT " + issues.get(0).getId());
				
				%>
	          	<script language="javascript">
	          	window.location='eo-home.html';
	          	</script>
	          	<%
	    	}

	    	else
	    	{
	    		%>
	    		<script language="javascript">
	    		alert("Invalid ballot name. Does not exist.");
	          	window.location='add-issue.html';
	          	</script>
	          	<%
	    		
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
	
	</body>
</html>