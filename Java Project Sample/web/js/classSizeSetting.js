$(document).ready(function() {

    //Stops the submit request
    $("#classForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#classSizeButton").click(function(e){
    
     		
            //Read data for the class max size input in the table. Get the form data using another method 
            var classSize1 = $("input#classSize1").val();         
            var classSize2 = $("input#classSize2").val();
            var classSize3 = $("input#classSize3").val();
			var classSize4 = $("input#classSize4").val();
			var classSize5 = $("input#classSize5").val();
			var classSize6 = $("input#classSize6").val();
			var classSize7 = $("input#classSize7").val();
			var classSize8 = $("input#classSize8").val();
			var classSize9 = $("input#classSize9").val();
			var classSize10 = $("input#classSize10").val();
			var classSize11 = $("input#classSize11").val();
			var classSize12 = $("input#classSize12").val();
			var classSize13 = $("input#classSize13").val();
			var classSize14 = $("input#classSize14").val();
			var classSize15 = $("input#classSize15").val();
			var classSize16 = $("input#classSize16").val();
			var classSize17 = $("input#classSize17").val();
			var classSize18 = $("input#classSize18").val();
			
            
            	
			var inputArr= [classSize1,classSize2,classSize3,classSize4,classSize5,classSize6,classSize7,classSize8,classSize9,classSize10,classSize11,classSize12,classSize13,classSize14,classSize15,classSize16,classSize17,classSize18];
			
			var Data = { 'inputArr': inputArr };  
			
			var theIds = JSON.stringify(inputArr); 
			
			var inputData = "{'inputArr':" + theIds + "}";       
			console.log("data to send ="+inputArr);
          
           console.log("type of input " + typeof(inputArr));
           
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/cs/class_settings",
             	data: {
             	'inputArr': inputArr.toString(),
             	},
				
				dataType: "json",             
				
				 //if received a response from the server
                success: function( data, textStatus, jqXHR) {  
              	console.log("class sizes saved successfully ");
                    //window.location = "TA_Setting.html";
                    
                    $("#classSizeSaveResponse").html("Class capacity has been saved successfully!");
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
                    $('#classSizeButton').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#classSizeButton').attr("disabled", false);
                }
     
            });        
    });

});

