


$(document).ready(function() {

    //Stops the submit request
    $("#individualStuHisPageForm").submit(function(e){
           e.preventDefault();
    });
    
    
    
     //checks for the button click event
    $("#individualStuHisPageForm_qery_btn").click(function(e){   
    			
     		    		
     		var studentId = $("input#studentId").val();
     		console.log("student id is "+studentId);
          	
            //make the AJAX request, dataType is set to json
            //meaning we are expecting JSON data in response from the server
            $.ajax({
                type: "POST",
                url: "/service/result/hist_data",
                data: {
                'studentId': studentId,
                },
                dataType: "json",  
                headers: {
 				  'Content-Type': undefined
				},             
                //if received a response from the server
                success: function( data, textStatus, jqXHR) { 
                	var obj = JSON.parse(data);    
                	console.log("response from service " + obj );         		
              		 $("#individualStuHisPageResponse").html(obj);
                    
                },
               
                //If there was no response from the server
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Something really bad happened " + textStatus);
                      $("#ajaxResponse").html(jqXHR.responseText);
                },
               
                //capture the request before it was sent to server
                beforeSend: function(jqXHR, settings){                   
                    //disable the button until we get the response
                    $('#individualStuHisPageForm_qery_btn').attr("disabled", true);
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
   		           //  console.log("response from service " + reached to end );
                    //enable the button 
                    $('#individualStuHisPageForm_qery_btn').attr("disabled", false);
                }
    
            });        
    });

});
