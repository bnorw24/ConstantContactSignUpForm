<!--
Brianna Norwood
December 9, 2018
Bellevue University

This JSP is gather and display the information in the contact info
-->

<jsp:setProperty name="auth" property="authCode" />

<html>
<head>
    <title>My Jmml</title>
</head>
<style>
    body {
        color: #E6E9DB;
    }
    h1,h2,h3 {
        color: #E6E9DB;
    }
    div{
        color: #E6E9DB;
    }
    td{
        padding: 10px;
    }
    .submitbutton {
        background-color: white;
        color: black;
        border: 2px solid #008CBA;
        border-radius: 8px;
        padding: 10px 60px;
    }
    .submitbutton:hover{
        background-color: #008CBA;
        color: white;
    }
</style>
<body style="background-color: #2B2B2B">
<br /><br />
<h2 style="text-align:center">Contact Information</h2>

<!-- Attempt to create the table -->
<%
    try {
        request.getAttribute("authCode");
        String authCode = request.getParameter("code");
        request.setAttribute("authCode", authCode);
        // Just verifies it in console
        System.out.println("Here is the auth code: " + authCode);
    }catch(Exception a){
        System.out.println("Could not get Auth Code: " + a);
    }

%>

<jsp:getProperty name="auth" property="authCode"/>

<%
    if(request.getMethod().equals("GET")){

    }%>

<!-- Input Form -->
<form method='post' action='<% request.getRequestURL(); %>'>
    <div align="center">
        <label>Email Address</label><br /><br />
        <input type='text' name='email' size='50' placeholder="Email" required />
        <br />
        <br />
        <%--<label>First Name</label><br /><br />--%>
        <%--<input type='text' name='firstName' size='50' placeholder="First Name" />--%>
        <%--<br />--%>
        <%--<br />--%>
        <%--<label>Last Name</label><br /><br />--%>
        <%--<input type='text' name='lastName' size='50' placeholder="Last Name" />--%>
        <%--<br />--%>
        <%--<br />--%>
        <input class="submitbutton" type='submit' value='Submit'/>
        <br />
    </div>
</form>

<div align="center">
    <!-- Give the user the option to return to the first page -->
    <a href="http://localhost:8080/Home">
        <input class="submitbutton" type="button" value="Return" align="center" />
    </a>
    <br />
    <br />

    <!-- Posts info after user presses submit -->
    <%


        if(request.getMethod().equals("POST")) {

            String email = request.getParameter("email");
//            String firstName = request.getParameter("firstName");
//            String lastName = request.getParameter("lastName");
//
//            // Constant Contact connection information
//            CCApi2 api = new CCApi2("rpzgcbf62cqn7nzgmm9zq7yy", "656e7075-6d7b-4466-b343-f302cad02636");
//            System.out.println("Successfully connected!");
//
//            // Create new contact object
//            Contact contact1 = new Contact();
//            ContactListMetaData md = new ContactListMetaData();
//            md.setId("1823298194");
//            ContactListMetaData[] contactListMetaData = new ContactListMetaData[1];
//            contactListMetaData[0] = md;
//            contact1.setContactLists(contactListMetaData);
//
//            // Create new email address
//            EmailAddress emailAddress = new EmailAddress();
//            emailAddress.setEmailAddress(email);
//            EmailAddress[] emailAddresses = new EmailAddress[1];
//            emailAddresses[0] = emailAddress;
//
//            // Set variables to update on contact
//            contact1.setEmailAddresses(emailAddresses);
//            contact1.setFirstName(firstName);
//            contact1.setLastName(lastName);
//
//            Response<Paged<Contact>> contactid = api.getContactService().getContactsByEmail(email).execute();
//            System.out.println("Server response for contact is: " + contactid);
//
//            Paged<Contact> contactid1 = api.getContactService().getContactsByEmail(email).execute().body();
//            List<Contact> listcontact = contactid1.getResults();
//            System.out.println("Here are the listcontact results: " + listcontact);
//
//            if (listcontact.isEmpty()) {
//                try {
//                    System.out.println("Creating contact now!");
//                    api.getContactService().createContact(contact1, OptInSource.ACTION_BY_VISITOR).execute();
//
//                    System.out.println("Contact created!");
//                } catch (Exception ctcEx1) {
//                    System.out.println("Could not create contact for this reason: " + ctcEx1);
//                }
//            }
//            else {
//                try {
//                    String contact_id = listcontact.get(0).getId();
//                    System.out.println("Here is the contact_id: " + contact_id);
//
//                    System.out.println("Updating contact now!");
//                    api.getContactService().updateContact(contact1, contact_id,
//                            OptInSource.ACTION_BY_OWNER).execute();
//                }
//                catch (Exception e) {
//                    System.out.println("Could not retrieve contact because of: " + e);
//                }
//            }
//
//            out.println("<br />Thank you for submitting your info!<br />");
//            out.println(email);
        }%>

</div>
</body>
</html>