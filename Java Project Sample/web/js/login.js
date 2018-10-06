$(document).ready(function() {

    //Stops the submit request
    $("#loginForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#myButton").click(function(e){    		
    
     		dataString = $("#loginForm").serialize();
     		
     		console.log("form data is"+ dataString);
     		console.log("form data is username"+ dataString.User_Name);
     		console.log("form data is pwd"+ dataString.Password);
     		
     		
          
            //get the form data using another method 
            var User_Name = $("input#User_Name").val();
            var Password = $("input#Password").val();  
            var role = $("select#role").val();
            
			// Check browser support
			if (typeof(Storage) != "undefined") {
				console.log("settin local stoge User_Name " + User_Name);
			    // Store
			    localStorage.setItem("userId", User_Name );
			  
			} 
			            
            console.log("User_Name " + User_Name);
            console.log("Password " + Password);
            console.log("role " + role);
            
            localStorage.setItem("userId",User_Name);
            
            
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/login",
                data: {                
                'userName': User_Name,
                'password': Password,
                'role': role                
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
                	sessionStorage.userName = User_Name;
                	// if role is student should exhibit student page. otherwise admin page. 
                	if (role=='Student'){
                		window.location = "stu_welcome.html";
                	}
                	else if (role == 'Admin'){
                		window.location = "admin_welcome.html";
                	}
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#ajaxResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){
                    //adding some Dummy data to the request
                    settings.data += "&dummyData=whatever";
                    //disable the button until we get the response
                    $('#myButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#myButton').attr("disabled", false);
                }
     
            });        
    });

});

