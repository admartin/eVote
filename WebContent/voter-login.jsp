<html>
<body>


	<%@ page language="java" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    HttpSession sess = request.getSession(); 
	    sess.setAttribute("username", username);
	    sess.setAttribute("password", password);
	    
	    System.out.println("USERNAME " + username );
	    
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
	    	
	    	Voter vo1 = objectLayer.createVoter(null, null, null, username, password, null, null,22, null,-1);
            List <Voter> volist = persistence.restoreVoter(vo1);
            
            if(volist.size() < 1)
            {    %>
            	<script language="javascript">
            	alert("Invalid Username");
            	window.location='voter-login.html';
            	</script>
            	<%
            }
            else if(password.equals(volist.get(0).getPassword()) == false)
            {
            	 %>
             	<script language="javascript">
             	alert("Invalid Password");
             	window.location='voter-login.html';
             	</script>
             	<%
            }
            else
            {
            	//System.out.println(volist.get(0).getElectoralDistrict());
            	
            	sess.setAttribute("district", volist.get(0).getDistrict());
            	sess.setAttribute("address", volist.get(0).getAddress());
            	sess.setAttribute("fname", volist.get(0).getFirstName());
            	sess.setAttribute("lname", volist.get(0).getLastName());
            	sess.setAttribute("age", volist.get(0).getAge());
            	sess.setAttribute("email", volist.get(0).getEmailAddress());
            	 %>
              	<script language="javascript">
              	window.location='voter-home.html';
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