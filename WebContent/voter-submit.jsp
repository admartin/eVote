
<html>
<body>

	<%@ page language="java" import="java.text.DateFormat" import="java.text.SimpleDateFormat" import="java.sql.Connection" import="java.util.Date"  import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
    	
	    
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
	    	
	    	String name = request.getParameter("cc1");
	 	    System.out.println("VOTED ON " + name);
	     	Candidate cand = objectLayer.createCandidate(name,null,-1,0);
	     	
	     	
	     	//VoteRecord record = objectLayer.createVoteRecord(null,null);
	     	
	     	List <Candidate> cands = persistence.restoreCandidate(cand);
	     	System.out.println("HERELOOKATTHISSHIT " + cands.get(0).getVoteCount());
	     	cands.get(0).addVote();
	     	System.out.println("HERELOOKATTHISSHIT " + cands.get(0).getVoteCount());
	     	
	     	persistence.storeCandidate(cands.get(0));  
	     	
	     	//If successful, go back to the ballot 
			%>
          	<script language="javascript">
          	window.location='voter-ballot.jsp';
          	</script>
          	<%
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