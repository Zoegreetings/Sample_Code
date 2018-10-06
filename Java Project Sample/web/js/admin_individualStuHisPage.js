$(document).ready(function() {

    //Stops the submit request
    $("#individualStuHisPageForm").submit(function(e){
           e.preventDefault();
    });
    
       
    //checks for the button click event
    $("#individualStuHisPageForm_qery_btn").click(function(e){
    
     		
            //get the form data using another method 
            var studentId = $("input#studentId").val();         
            console.log("studentId " + studentId);
          
            
                       
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
              		var obj = JSON.stringify(data);    
                	console.log("response from service " + JSON.stringify(data) );         		
                	//console.log("response from service " + obj.appInfo.appname );
              		
              		 $("#individualStuHisPageResponse").html(obj);
              		 //$("#individualStuHisPageResponse").html(obj.appInfo.appname);
              		
                   // window.location = "stu_welcome.html";
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
                    $('#individualStuHisPageForm_qery_btn').attr("disabled", true);                  
                    
                },
               
                //this is called after the response or error functions are finsihed
                //so that we can take some action
                complete: function(jqXHR, textStatus){
                    //enable the button 
                    $('#individualStuHisPageForm_qery_btn').attr("disabled", false);
                }
     
            });        
    });

});

