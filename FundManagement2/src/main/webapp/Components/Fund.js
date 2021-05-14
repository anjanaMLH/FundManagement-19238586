$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});


// CLIENT-MODEL================================================================
function validateFundForm() {
    // TYPE
    if ($("#fundType").val().trim() == "") {
        return "Insert Fund Type.";
    }
    // SOURCE
    if ($("fundSource").val().trim() == "") {
        return "Insert Fund Source.";
    }
    // AMOUNT-------------------------------
    if ($("#fundAmount").val().trim() == "") {
        return "Insert Fund Amount.";
    }
    // is numerical value
    var tmpPrice = $("#fundAmount").val().trim();
    if (!$.isNumeric(tmpPrice)) {
        return "Insert a numerical value for Fund Amount.";
    }
    // convert to decimal price
    $("#fundAmount").val(parseFloat(tmpPrice).toFixed(2));
    // DATE------------------------
    if ($("#fundDate").val().trim() == "") {
        return "Insert Fund Date.";
    }
    return true;
}

$(document).on("click", "#btnSave", function (event) {
    // Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation-------------------
    var status = validateFundForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid------------------------
    var type = ($("#hidFundIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "FundAPI",
            type: type,
            data: $("#formFund").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onFundSaveComplete(response.responseText, status);
            }
        });
});

function onFundSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $("#alertSuccess").text("Successfully saved.");
            $("#alertSuccess").show();
            $("#divFundsGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while saving.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while saving..");
        $("#alertError").show();
    }
    $("#hidFundIDSave").val("");
    $("#formFund")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hidFundIDSave").val($(this).data("fundid"));
    $("#fundType").val($(this).closest("tr").find('td:eq(0)').text());
    $("#fundSource").val($(this).closest("tr").find('td:eq(1)').text());
    $("#fundAmount").val($(this).closest("tr").find('td:eq(2)').text());
    $("#fundDate").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function (event) {
    $.ajax(
        {
            url: "FundAPI",
            type: "DELETE",
            data: "fundID=" + $(this).data("fundid"),
            dataType: "text",
            complete: function (response, status) {
                onFundDeleteComplete(response.responseText, status);
            }
        });
});

function onFundDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "success") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divItemsGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
}