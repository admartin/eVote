<html>
<body>

<script type="text/javascript">
function login(){
	var pass = document.getElementById(pass).value;
	window.location='eo-home.jsp';
}
</script>


	<%@ page language="java" import="java.sql.Connection" import="edu.uga.cs.evote.EVException" import="edu.uga.cs.evote.entity.imp.*" import="edu.uga.cs.evote.entity.*" import="edu.uga.cs.evote.object.*" import="edu.uga.cs.evote.object.impl.*" import="edu.uga.cs.evote.persistence.PersistenceLayer" import="edu.uga.cs.evote.persistence.impl.DbUtils" import="edu.uga.cs.evote.persistence.impl.*" import="java.util.List" %>
	<%
		Connection conn = null;
	    ObjectLayer objectLayer = null;
	    PersistenceLayer persistence = null;
	    
	  
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	   // response.sendRedirect("eo-home.jsp?username="+username);
	    System.out.println("USERNAME HEre" + username );
	    
	    HttpSession sess = request.getSession(); 
	    sess.setAttribute("username", username);
	    sess.setAttribute("password", password);
	    
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
	    	ElectionsOfficer eo1 = objectLayer.createElectionsOfficer(null, null, username, password, null, null);
            List <ElectionsOfficer> eolist = persistence.restoreElectionsOfficer(eo1);
           
           if(eolist.size() < 1)
            {    %>
            	<script language="javascript">
            	alert("Invalid Username");
            	window.location='eo-login.html';
            	</script>
            	<%
            }
            else if(password.equals(eolist.get(0).getPassword()) == false)
            {
            	 %>
             	<script language="javascript">
             	alert("Invalid Password");
             	window.location='eo-login.html';
             	</script>
             	<%
            }
            else
            {
            	sess.setAttribute("email", eolist.get(0).getEmailAddress());
            	System.out.println( eolist.get(0).getEmailAddress());
            	sess.setAttribute("fname", eolist.get(0).getFirstName());
            	sess.setAttribute("lname", eolist.get(0).getLastName());
            	 %>
              	<script language="javascript">
              	window.location="eo-home.html";
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
	