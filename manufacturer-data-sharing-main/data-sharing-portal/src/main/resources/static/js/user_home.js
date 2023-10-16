$(document).ready(function(){
    var tabNameToDisplay = $('#tabNameToDisplay').val();
    if(tabNameToDisplay != undefined && tabNameToDisplay != '') {
        openTab(event, tabNameToDisplay);
    } else {
        openTab(event, 'CommonData');
    }

    // save access button registration
    $('#granted-button').click(function(event) {
        event.preventDefault();
        if($('input[name="private-data-access"]:checked').serialize() == '') {
            alert("Please select one Private data column");
        } else if($("#granted-producer-email").val() == '') {
            alert("Please enter the Producer email");
        } else {
            var dataAccessModel = {}
            dataAccessModel["grantedEmail"] = $("#granted-producer-email").val();
            dataAccessModel["owningProducerUsername"] = $("#owningProducerName").val();
            dataAccessModel["dataFieldNames"] = ''
            $('input[name="private-data-access"]:checked').each(function() {
                dataAccessModel["dataFieldNames"] += $(this).attr('value') + ","
            });

            $("#granted-button").prop("disabled", true);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/privatedataaccessgrant",
                data: JSON.stringify(dataAccessModel),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    $("#granted-button").prop("disabled", false);
                    $('input[name="private-data-access"]').prop('checked', false);
                    alertDiv = document.getElementById("alertDiv");
                    alertDiv.style.display = "block";
                    if(data["response"] == 'success') {
                        alertDiv.classList.remove('warnignAlert');
                        alertDiv.classList.remove('errorAlert');
                        alertDiv.className = 'alert';
                        alertDiv.classList.toggle("show");
                        $("#alertSpan").text("Data access has been given temporarily.");
                    } else if(data["response"] == 'alreadyFound'){
                        alertDiv.classList.remove('alert');
                        alertDiv.classList.remove('errorAlert');
                        alertDiv.className = 'warnignAlert';
                        alertDiv.classList.toggle("show");
                        $("#alertSpan").text("An active access to this data is already available.");
                    } else if(data["response"] == 'ownPermission') {
                        alertDiv.classList.remove('alert');
                        alertDiv.classList.remove('errorAlert');
                        alertDiv.className = 'warnignAlert';
                        alertDiv.classList.toggle("show");
                        $("#alertSpan").text("You can not grant access to yourself.");
                    } else if(data["response"] == 'sentUserNotFound') {
                          alertDiv.classList.remove('alert');
                          alertDiv.classList.remove('errorAlert');
                          alertDiv.className = 'warnignAlert';
                          alertDiv.classList.toggle("show");
                          $("#alertSpan").text("The given email is not found as an active user.");
                    } else {
                        alertDiv.classList.remove('alert');
                        alertDiv.classList.remove('errorAlert');
                        alertDiv.className = 'errorAlert';
                        alertDiv.classList.toggle("show");
                        $("#alertSpan").text("Technical error, please try again later.");
                    }
                },
                error: function (e) {
                    console.log("ERROR : ", e.responseText);
                    alertDiv.classList.remove('alert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'errorAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Technical error, please try again later.");
                    $("#granted-button").prop("disabled", false);
                }
            });
        }
    });

    // remove access button registration
    $('#removed-button').click(function(event) {
        event.preventDefault();
        if($('input[name="private-data-access"]:checked').serialize() == '') {
            alert("Please select one Private data column");
            return;
        }

        if($("#granted-producer-email").val() == '') {
            alert("Please enter the Producer email");
            return;
        }

        var dataAccessModel = {}
        dataAccessModel["grantedEmail"] = $("#granted-producer-email").val();
        dataAccessModel["owningProducerUsername"] = $("#owningProducerName").val();
        dataAccessModel["dataFieldNames"] = ''
        $('input[name="private-data-access"]:checked').each(function() {
            dataAccessModel["dataFieldNames"] += $(this).attr('value') + ","
        });

        $("#removed-button").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/privatedataaccessremove",
            data: JSON.stringify(dataAccessModel),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                $("#removed-button").prop("disabled", false);
                $('input[name="private-data-access"]').prop('checked', false);
                alertDiv = document.getElementById("alertDiv");
                alertDiv.style.display = "block";
                if(data["response"] == 'success') {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'alert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Data access has been removed permanently.");
                } else if(data["response"] == 'notFound'){
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'warnignAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("No active access to this data found for the given email.");
                } else if(data["response"] == 'ownPermission'){
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'warnignAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("You can not remove your access to your own private data.");
                } else if(data["response"] == 'removedUserNotFound') {
                    alertDiv.classList.remove('alert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'warnignAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("The given email is not found as an active user.");
                }else {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'errorAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Technical error, please try again later.");
                }
            },
            error: function (e) {
                console.log("ERROR : ", e.responseText);
                alertDiv.classList.remove('warnignAlert');
                alertDiv.classList.remove('errorAlert');
                alertDiv.className = 'errorAlert';
                alertDiv.classList.toggle("show");
                $("#alertSpan").text("Technical error, please try again later.");
                $("#removed-button").prop("disabled", false);
            }
        });
    });

    // upload file button registration
    $('#upload-button').click(function(event) {
        event.preventDefault();

        if($("#upload-file").val() == '') {
            alert("Please select a file.");
            return;
        }

        var form = $('#upload-form')[0];
        var data = new FormData(form);
        data.append("userName", $("#fileUploadProducerName").val());

        $("#upload-button").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "multipart/form-data",
            url: "/file",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                $("#upload-button").prop("disabled", false);
                alertDiv = document.getElementById("alertDiv");
                alertDiv.style.display = "block";
                if(data["response"] == 'success') {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'alert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("File Uploaded successfully.");
                } else if(data["response"] == 'invalidFile'){
                    alertDiv.classList.remove('alert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'warnignAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Invalid file type. Only MS Excel, XML, JSON and CSV are accepted.");
                } else {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('alert');
                    alertDiv.className = 'errorAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Technical error, please try again later.");
                }
            },
            error: function (e) {
                console.log("ERROR : ", e.responseText);
                alertDiv.classList.remove('warnignAlert');
                alertDiv.classList.remove('alert');
                alertDiv.className = 'errorAlert';
                alertDiv.classList.toggle("show");
                $("#alertSpan").text("Technical error, please try again later.");
                $("#upload-button").prop("disabled", false);
            }
        });
    });

    // sent reply button registration
    $('.reply-button-class').click(function(event) {
        var messageId = event.target.id.replace("reply-button", "")
        event.preventDefault();

        if($("#reply-body" + messageId).val() == '') {
            alert("Please fill your response first.");
            return;
        }

        $("#reply-button" + messageId).prop("disabled", true);

        var messageModel = {}
        messageModel["messageId"] = messageId;
        messageModel["messageReply"] = $("#reply-body" + messageId).val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/reply",
            data: JSON.stringify(messageModel),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                alertDiv = document.getElementById("alertDiv");
                alertDiv.style.display = "block";
                if(data["apiResponse"] == 'success') {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'alert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Message sent successfully.");
                    $("#reply-body"+ messageId).prop("disabled", true);
                    $("#reply-button" + messageId).removeAttr("style").hide();
                } else {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'errorAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Technical error, please try again later.");
                    $("#reply-button" + messageId).prop("disabled", false);
                }
            },
            error: function (e) {
                console.log("ERROR : ", e.responseText);
                alertDiv.classList.remove('warnignAlert');
                alertDiv.classList.remove('errorAlert');
                alertDiv.className = 'errorAlert';
                alertDiv.classList.toggle("show");
                $("#alertSpan").text("Technical error, please try again later.");
                $("#reply-button" + messageId).prop("disabled", false);
            }
        });
    });

    // request send button click registration
    $('#request-access-button').click(function(event){
        event.preventDefault();

        if($("#message-subject").val() == '' || $("#message-body").val() == '' || $('#producer-names').val() == 'Select Someone') {
            alert("Please fill all fields.");
            return;
        }

        $("#request-access-button").prop("disabled", true);

        var messageModel = {}
        messageModel["askedByProducerUsername"] = $("#sendRequestByProducerName").val();
        messageModel["askedToProducerUsername"] = $('#producer-names').val();
        messageModel["messageSubject"] = $("#message-subject").val();
        messageModel["messageBody"] = $("#message-body").val();


        $("#message-subject").val('');
        $("#message-body").val('');
        $('#producer-names').val('Select Someone').attr("selected","selected");

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/message",
            data: JSON.stringify(messageModel),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                alertDiv = document.getElementById("alertDiv");
                alertDiv.style.display = "block";
                if(data["apiResponse"] == 'success') {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'alert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Message sent successfully.");
                    $("#request-access-button").prop("disabled", false);
                } else {
                    alertDiv.classList.remove('warnignAlert');
                    alertDiv.classList.remove('errorAlert');
                    alertDiv.className = 'errorAlert';
                    alertDiv.classList.toggle("show");
                    $("#alertSpan").text("Technical error, please try again later.");
                    $("#request-access-button").prop("disabled", false);
                }
            },
            error: function (e) {
                console.log("ERROR : ", e.responseText);
                alertDiv.classList.remove('warnignAlert');
                alertDiv.classList.remove('errorAlert');
                alertDiv.className = 'errorAlert';
                alertDiv.classList.toggle("show");
                $("#alertSpan").text("Technical error, please try again later.");
                $("#request-access-button").prop("disabled", false);
            }
        });
    });

});

function closeAlert(){
    alertDiv = document.getElementById("alertDiv");
    alertDiv.style.display = "none";
}

function openTab(evt, tabName) {
  var i, tabcontent, tablinks;

  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(tabName).style.display = "block";
  document.getElementById(tabName+"Button").className += " active";
}
