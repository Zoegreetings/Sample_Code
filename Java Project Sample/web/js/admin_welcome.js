$(document).ready(function() {

 	$("#aggregatedPrefHisResponse").html("");
 	$("#RecHisResponse").html("");

    //Stops the submit request
    $("#adminWelForm").submit(function(e){
           e.preventDefault();
    });
   

    $("#aggregatedPrefHisButton").click(function(e){
		    $("#aggregatedPrefHisResponse").html("");
		 	$("#RecHisResponse").html("");
		    		
    		console.log("query for aggregatedPrefHis");
    
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/result/aggregate_pref_hist_data",
                data: { //no data needs to sent to json

                },
                dataType: "json",  
                headers: {
                  'Content-Type': undefined
                },             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
                    var obj = JSON.stringify(data);    
                    console.log("response from service " + JSON.stringify(data) ); 
                    $("#aggregatedPrefHisResponse").html(obj);
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#aggregatedPrefHisResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){
                    
                    //disable the button until we get the response
                    $('#aggregatedPrefHisButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#aggregatedPrefHisButton').attr("disabled", false);
                }
     
            });        
    });
	$("#RecHisButton").click(function(e){
		
		$("#aggregatedPrefHisResponse").html("");
	 	$("#RecHisResponse").html("");
    
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/result/aggregate_rec_hist_data",
                data: { //no data needs to sent to json

                },
                dataType: "json",  
                headers: {
                  'Content-Type': undefined
                },             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
                    var obj = JSON.stringify(data);    
                    console.log("response from service " + JSON.stringify(data) ); 
                    $("#RecHisResponse").html(obj);
                  
                    if(obj=={})
                       $("RecHisResponse").html("no recommendations so far!");
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#RecHisResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){
                    //adding some Dummy data to the request
                    settings.data += "&dummyData=whatever";
                    //disable the button until we get the response
                    $('#RecHisButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#RecHisButton').attr("disabled", false);
                }
     
            });        
    });


});

