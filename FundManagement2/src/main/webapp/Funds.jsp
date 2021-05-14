<%@ page import="com.paf10.Fund"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fund Management</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Fund.js"></script>
<link rel="stylesheet" href="views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">


				<h1>Fund Management</h1>
				<form method='post' action='Funds.jsp' id='formFund' name='formFund'>
					Fund Type: <input id='fundType' name='fundType' type='text' class='form-control col-md-3'><br> 
					Fund Source: <input id='fundSource' name='fundSource' type='text' class='form-control col-md-3'><br> 
					Fund Amount: <input id='fundAmount' name='fundAmount' type='text' class='form-control col-md-3'><br> 
					Fund Date: <input id='fundDate' name='fundDate' type='text' class='form-control col-md-3'><br> 
					<input id='btnSave' name='btnSave' type='button' value='Save' class='btn btn-primary'> 
					<input type='hidden' id='hidFundIDSave' name='hidFundIDSave' value=''>
				</form>

				<br>

				<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
				<div id='alertError' name='alertError' class='alert alert-danger'></div>

				<br>
				<div id="divFundsGrid">
				<%
				Fund fundObjRead = new Fund();
				out.print(fundObjRead.readFunds());
				%>
				</div>

			</div>
		</div>
	</div>
</body>
</html>