<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Jmml</title>
    <link href="example_style_sheet.css" rel="stylesheet" type="text/css">
</head>
<body>
<br /><br />
<h2>Contact Information</h2>

<% if(request.getMethod().equals("GET")){

    // Sets authCode to use in Servlet
    String authCode = "";
    try {
        authCode = request.getParameter("code");
        request.getSession().setAttribute("authCode", authCode);

        // Verifies it in console
        System.out.println("Here is the auth code from JSP: " + authCode);
    }catch(Exception a){
        System.out.println("Could not get Auth Code: " + a);
    }

    }%>

<!-- Input Form -->
<form action='result.jsp' method="post">
<%--<form method='post' action='<% request.getRequestURL(); %>'>--%>
    <div align="center">
        <label>Email Address</label><br /><br />
        <input type='text' name='email' size='50' placeholder="Email" required />
        <br />
        <br />
        <input class="submitbutton" type='submit' value='Submit'/>
        <br />
    </div>
</form>

</body>
</html>