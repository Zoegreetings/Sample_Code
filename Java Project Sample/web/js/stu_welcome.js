$(document).ready(function() {

    //Stops the submit request
    $("#studentWelForm").submit(function(e){
           e.preventDefault();
    });

    $("#courseDemandButton").click(function(e){
    
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/cs/all_course_demands",
                data: {                              
                },
                dataType: "json",  
                headers: {
                  'Content-Type': undefined
                },             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
                     var obj = JSON.stringify(data);
                  	 console.log("response from service " );              
                     console.log( obj );
                     $("#courseDemandResponse").html(obj);
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#courseDemandResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){                    
                    //disable the button until we get the response
                    $('#courseDemandButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#courseDemandButton').attr("disabled", false);
                }
     
            });        
    });

$("#historyButton").click(function(e){
            var userId = localStorage.getItem("userId");
            console.log("userid is " +userId);
    
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/student/stu_demand_pref_hist",
                data: {
                'studentId': userId,                               
                },
                dataType: "json",  
                headers: {
                  'Content-Type': undefined
                },             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {
                    var obj = JSON.stringify(data);    
                    console.log("response from service " + JSON.stringify(data) );              
                    $("#demand_rec_HisResponse").html(obj);  

                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#demand_rec_HisResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){
                    //adding some Dummy data to the request
                    settings.data += "&dummyData=whatever";
                    //disable the button until we get the response
                    $('#historyButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#historyButton').attr("disabled", false);
                }
     
            });        
    });


});

