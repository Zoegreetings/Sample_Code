$(document).ready(function() {

    //Stops the submit request
    $("#demandForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#saveButton").click(function(e){
    
     		dataString = $("#demandForm").serialize();
     		
     		console.log("form data is"+ dataString);     		
          
            //get the form data using another method 
            var desiredcourselist = $("input#desiredcourselist").val();         
            var courseCount = $("select#courseCount").val();
            
            console.log("desiredcourselist " + desiredcourselist);
            console.log("courseCount " + Password);
            
            
            var loginDetail = {};
            loginDetail.userName = User_Name;
            loginDetail.password= Password;            
           	loginDetail.role=role;
           
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/student/stu_pref",
                data: {           
               
                'courseCount': courseCount,
		'desiredcourselist': desiredcourselist,
               
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
              	console.log("student demand saved successfully ");
                    //window.location = "stu_welcome.html";
                },
               
                //If there was no resonse from the server
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

