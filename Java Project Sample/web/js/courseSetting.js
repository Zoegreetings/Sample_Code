$(document).ready(function() {

    //Stops the submit request
    $("#courseForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#courseButton").click(function(e){
    
     		dataString = $("#courseForm").serialize();
     		
     		console.log("form data is"+ dataString);     		
          
            //Read data for the course selections in the table. Get the form data using another method 
            var course_select= null;
			checkboxes = document.getElementsByClassName("checkbox"); 
			for (var i = 0; i < checkboxes.length; i++) {
				var checkbox = checkboxes[i];
				checkbox.onclick = function() {
					var currentRow = this.parentNode.parentNode;
					var courseID_index = currentRow.getElementsByTagName("td")[0];
					course_select=courseID_index.textContent );
  			 	 };
			} 
			
			 $("#courseSettingsSaveResponse").html("course settings saved successfully!");
			          
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/cs/course_settings",
                data: {          
               
                'course_select'=course_select,
			
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
              		console.log("course settings saved successfully");
                      $("#courseSettingsSaveResponse").html("course settings saved successfully!");
                    
                  
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
                    $('#courseButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#courseButton').attr("disabled", false);
                }
     
            });        
    });

});

