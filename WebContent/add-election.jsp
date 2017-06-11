<html>
<body>

	<%@ page language="java" import="java.text.DateFormat" import="java.text.SimpleDateFormat" import="java.sql.Connection" import="java.util.Date"  import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String dist = request.getParameter("dist");
	    String balname  = request.getParameter("balname");
	    String party = request.getParameter("party");
	    String info = request.getParameter("info");
	    String candidate = request.getParameter("candidate");
	    String partisan = request.getParameter("partisan2");
	    String office = request.getParameter("office");
	       
	    System.out.println("Dist " + dist );
	    System.out.println("party " +party );
	    System.out.println("info " + info );
	    System.out.println("candidate " + candidate );
	    System.out.println("partisan " + partisan );
	    
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
		   	     
		    	
		    	Election e1 = objectLayer.createElection(office,false);
				PoliticalParty p1 = objectLayer.createPoliticalParty(-1,party,null);
				
				List <Election> elec = persistence.restoreElection(e1);
				if(elec.size() == 0){
					persistence.storeElection(e1);
				}
				
				persistence.storePoliticalParty(p1);
				
				Candidate c1 = objectLayer.createCandidate(candidate,p1,elec.get(0).getId(),0);
				persistence.storeCandidate(c1);
				
		    	elec = persistence.restoreElection(e1);

		    	
		    	BallotItem bi1 = objectLayer.createBallotItem(ballots2.get(0), 0, elec.get(0).getId(),-1);
		    	persistence.storeBallotItem(bi1);
		    	//System.out.println("GOT " + dists.get(0).getName() + " "+  elections.get(0).getId());
				
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
	          	window.location='add-election.html';
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