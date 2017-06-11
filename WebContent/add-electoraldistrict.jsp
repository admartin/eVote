

<html>
<body>

	<%@ page language="java" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String dist = request.getParameter("dist");
	    	    
	    System.out.println("USERNAME " + dist );
	    
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
	   
	    	ElectoralDistrict ed1 = objectLayer.createElectoralDistrict(-1,dist,null,null);
	    	List<ElectoralDistrict> edlist = persistence.restoreElectoralDistrict(ed1);
	    	if(edlist.size() == 0)
	    	{
				persistence.storeElectoralDistrict(ed1);
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
	          	alert("Invalid district. Name already exists.");
	          	window.location='add-electoral-district.html';
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