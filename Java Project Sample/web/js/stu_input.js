$(document).ready(function() {

    //Stops the submit request
    $("#demandForm").submit(function(e){
           e.preventDefault();
    });
    
    
    
     //checks for the button click event
    $("#recoButton").click(function(e){
    	$("#ajaxResponse2").html("");
    	$("#ajaxResponse").html("");
    
    		var userId = localStorage.getItem("userId");
        		
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/student/recs",
                data: {
                'userId': userId,
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) { 
                	var obj = JSON.stringify(data);
                	console.log("response from service " + obj ); 
                    $("#ajaxResponse2").html(obj);
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#ajaxResponse2").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){
                    //adding some Dummy data to the request
                    settings.data += "&dummyData=whatever";
                    //disable the button until we get the response
                    $('#recoButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#recoButton').attr("disabled", false);
                }
     
            });        
    });
    
    
   
    //checks for the button click event
    $("#saveButton").click(function(e){
    		$("#ajaxResponse2").html("");
    		$("#ajaxResponse").html("");
    
    
     		dataString = $("#demandForm").serialize();
     		
     		console.log("form data is"+ dataString);    
     		
     		var userId = localStorage.getItem("userId"); 	//user nameonly	
          
            //get the form data using another method 
            var desiredcourselist = $("input#desiredcourselist").val();         
            var courseCount = $("select#courseCount").val();
            
            console.log("desiredcourselist " + desiredcourselist);
            console.log("courseCount " + courseCount);
           console.log("userId " + userId);
            
                       
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/student/stu_pref",
                data: {           
               
                'courseCount': courseCount,
				'desiredcourselist': desiredcourselist,
				'userName': userId,
               
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
              		//console.log("student demand saved successfully ");
              		//console.log(data);
              		             		
              		 $("#ajaxResponse").html("student demand saved successfully");
              		
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
                    $('#saveButton').attr("disabled", true);                  
                    
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#saveButton').attr("disabled", false);
                }
     
            });        
    });

});

