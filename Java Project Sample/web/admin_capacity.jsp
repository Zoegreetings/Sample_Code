<%@ page import="edu.gatech.saad.p3.dao.DAOFacade" %>
<%@ page import="java.io.*,java.util.*" %>

<h1>Course Capacity:</h1>

<% 
	DAOFacade facade = DAOFacade.getInstance(); 
	Vector<Integer> courseIds = facade.getCourseIds();
	HashMap<Integer,String> name_map = new HashMap<Integer,String>();
	for (Integer id : courseIds) {
		String name = facade.getCourseCode(id) + ": " + facade.getCourseName(id);
		name_map.put(id,name);
		String cap = request.getParameter(name);
		if ( cap != null && cap.length() > 0 ) {
			facade.setCapacity(id,Integer.parseInt(cap));
		}		

	}
%>

<form method="POST"> 
<% 
	for (Integer id : courseIds) {		
			
			int cap = facade.getCapacity(id);
			String cap_str = new String("");
			if ( cap >= 0 ) cap_str += cap;
						
			%><input type="text" name="<%= name_map.get(id) %>" size="5" value="<%= cap_str %>">&nbsp;&nbsp;<%= name_map.get(id) %><br><%
	}
%>

<p><input type="submit" value="Submit" name="B1"><input type="reset" value="Reset" name="B2"></p>
</form>
