
<html>
<body>

	<%@ page language="java" import="java.text.DateFormat" import="java.text.SimpleDateFormat" import="java.sql.Connection" import="java.util.Date"  import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String dist = request.getParameter("dist");
	    String open = request.getParameter("open");
	    String close = request.getParameter("close");
	    String bname =request.getParameter("bname");
	    System.out.println("Dist " + dist );
	    System.out.println("open " +open );
	    System.out.println("close " + close );
	   
	    
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
	   	     
	    	 String date = open.replace("T"," ");
	    	 String date1 = close.replace("T"," ");
             DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             SimpleDateFormat ft = 
            	      new SimpleDateFormat ("yyyy-MM-dd hh:mm");
             Date open1 = ft.parse(date);
             Date close1= ft.parse(date1);
            
             
             java.sql.Date odate = new java.sql.Date(open1.getTime()); 
             java.sql.Date cdate = new java.sql.Date(close1.getTime()); 
            
	    	ElectoralDistrict district =  objectLayer.createElectoralDistrict(-1,dist,null,null);
	    	
	    	List <ElectoralDistrict> dists = persistence.restoreElectoralDistrict(district);
	    	if(dists.size() > 0)
	    	{
	    		System.out.println(bname);
	    		Ballot ballot = objectLayer.createBallot(bname,null,null,false,null);
	    		List <Ballot> balList = persistence.restoreBallot(ballot);
	    		System.out.println(balList.size());
	    		ballot = objectLayer.createBallot(bname,open,close,false,dists.get(0));
	    		if(balList.size() < 1)
		    	{
	    			persistence.storeBallot(ballot);
	    			 System.out.println("BALLOT MADE " + bname);
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
		          	alert("Invalid ballot name. Ballot already exists.");
		          	window.location='add-ballot.html';
		          	</script>
		          	<%
	    		}
	    	}
	    	else
	    	{
	    		%>
	          	<script language="javascript">
	          	alert("Invalid district name. Does not exist.");
	          	window.location='add-ballot.html';
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