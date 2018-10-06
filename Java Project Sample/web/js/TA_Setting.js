$(document).ready(function() {

    //Stops the submit request
    $("#TAForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#TAButton").click(function(e){
    
     		dataString = $("#TAForm").serialize();
     		
     		console.log("form data is"+ dataString);     		
          
            //Read data for the TA_class in the table. Get the form data using another method 
            checkboxes = document.getElementsByClassName("checkbox"); 
			for (var i = 0; i < checkboxes.length; i++) {
				var checkbox = checkboxes[i];
				checkbox.onclick = function() {
					var currentRow = this.parentNode.rowIndex;
					var secondColumn = currentRow.getElementsByTagName("td")[1];

        alert("My text is: " + secondColumn.textContent );
    };
} 
			
            
            
            
            var loginDetail = {};
            loginDetail.userName = User_Name;
            loginDetail.password= Password;            
           	loginDetail.role=role;
           
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/cs/ta_settings",
                data: {           
               
                //data
			
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
              	console.log("class sizes saved successfully ");
                    window.location = "courseSetting.html";
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
                    $('#TAButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#TAButton').attr("disabled", false);
                }
     
            });        
    });

});

