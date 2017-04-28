// bind the on-change event for the input element (triggered when a file
// is chosen)
$(document).ready(function () {
    $("#train-nn").on("click", uploadFile);
});

/**
 * Upload the file sending it via Ajax at the Spring Boot server.
 */
function uploadFile() {
  
       
      
            // Handle upload success
            $("#train-nn").text("nn trained........").addClass("alert alert-success");
     
      
            // Handle upload error
            $("#upload-file-message").text("error occured whie training").addClass("alert alert-danger");

}