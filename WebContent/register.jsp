<html>
<body>


<%@ page language="java" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String fname = request.getParameter("fname");
	    String lname = request.getParameter("lname");
	    String address = request.getParameter("address");
	    String city = request.getParameter("city");
	    String state = request.getParameter("state");
	    int zip = Integer.parseInt( request.getParameter("zip"));
	    int age = Integer.parseInt( request.getParameter("age"));
	    String email = request.getParameter("email");
	    //compare zip against districts and assign one
	    //or for sake of project randomly assign
	    
	    
	    System.out.println(fname);
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
	    	address += " " + city + ", " + state + " " + zip;
	    	int dist = (int) (Math.random() * 10) + 1;
	    	ElectoralDistrict ed = objectLayer.createElectoralDistrict(dist,"Dist" + dist, null,null);
	    	List <ElectoralDistrict> eds = persistence.restoreElectoralDistrict(ed);
	    	//ed = eds.get(0);
	    	System.out.println(username);
	    	Voter vo1 = objectLayer.createVoter(null, null, null, username, null, null, null,-1, null,-1);
			List <Voter> volist = persistence.restoreVoter(vo1);
        	System.out.println("STORING " + volist.size());    
            if(volist.size() == 0){
                
    	    	Voter v1 = objectLayer.createVoter(fname, null, lname, username, password, email, address, age, null,-1);
    			persistence.storeVoter(v1);
    			
    			%>
              	<script language="javascript">
              	window.location='voter-login.html';
              	</script>
              	<%
    	   		
            }
            else
            {    %>
            	<script language="javascript">
            	alert("Invalid Username");
            	window.location='register.html';
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
	
	</html>
</body>