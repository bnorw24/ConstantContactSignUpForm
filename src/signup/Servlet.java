package signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.ws.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@javax.servlet.annotation.WebServlet(name = "signup.Servlet")
public class Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        // Define all necessary variables
        String redirectUri = "Your_Redirect_URI";
        String clientId = "Your_Client_ID";
        String clientSecret = "Your_Client_Secret";
        String authCode = (String) request.getSession().getAttribute("authCode");

        System.out.println("Here is your auth code from Servlet: " + authCode);

        // Get authentication info using auth code
        serverFlow getAuthResult = new serverFlow();
        StringBuffer authResult = getAuthResult.getAccessToken(redirectUri, clientId, clientSecret, authCode);

        /*
        Check for a better way to store this information, would be useful to have it moving forward
        That way you do not need to authenticate the app each time it is used, instead the access token could be
        reused or the refresh token could be used to get a new access token. Check into storing on database or
        locally. Add class for getting access token with refresh token for further use.
        */

        // Parse info from authResult
        String access_token = "";
        String refresh_token = "";
        String token_type = "";

        try {

            JSONObject jsonObj = new JSONObject(authResult.toString());

            access_token = jsonObj.getString("access_token");
            refresh_token = jsonObj.getString("refresh_token");
            token_type = jsonObj.getString("token_type");


        } catch (JSONException b) {
            System.out.println("Could not parse JSON information for access token: " + b);
        }

        System.out.println("Here is your access token: " + access_token);


        // Start java class for http call
        // Take access token and do an HTTP call
        String email = request.getParameter("email");
        StringBuffer contactResult = new StringBuffer();

        String baseContactUrl = "https://api.cc.email/v3/contacts?status=all&email=";
        // Build URL
        String fullUrlContactRequest = baseContactUrl + email;
        System.out.println("Full contact request URL: " + fullUrlContactRequest);
        URL contactUrl = new URL(fullUrlContactRequest);

        try {
            // Open connection
            HttpURLConnection con2 = (HttpURLConnection) contactUrl.openConnection();

            // Get Method for contact request
            con2.setRequestMethod("GET");

            // Add Headers
            con2.setRequestProperty("Authorization", "Bearer " + access_token);

            // Open input steam
            BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                contactResult.append(inputLine);
            }
            // Close the stream after reading info
            in.close();

        } catch (Exception a) {
            System.out.println("Could not process the Contact URL to get contact information. Reason: " + a);
        }

        System.out.println("Contact Results from Contact URL query: " + contactResult);

        // Class to get contact if they exist and post or put depending on result
        String contact_id = "";

        // Check to see if the contact currently exists by getting contact by email then getting contact_id from response
        try {

            JSONObject jsonObj = new JSONObject(contactResult.toString());

            JSONArray jsonarr = jsonObj.getJSONArray("contacts");

            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject contacts = jsonarr.getJSONObject(i);
                contact_id = contacts.getString("contact_id");
            }

            JSONArray contactArray = jsonObj.getJSONArray("contacts");
            contact_id = contactArray.getString(0);


        } catch (JSONException b) {
            System.out.println("Could not parse JSON information for contact ID: " + b);
        }

        System.out.println("Here is the contact id: " + contact_id);

        // Define list id for contacts to be sent to
        String listId = "6cdb1d80-6218-11e9-87d6-d4ae5292c4dd";

        if (contact_id.equals("")) {

            // Post Contact using postContact class
            postContact post = new postContact();
            StringBuffer postContactResult = post.postContactData(email, access_token, listId);
            System.out.println("Contact post successful, here are the results: " + postContactResult);

        } else {

            // Put Contact using putContact class
            putContact put = new putContact();
            StringBuffer putContactResult = put.putContactData(email, contact_id, access_token, listId);
            System.out.println("Contact put successful, here are the results: " + putContactResult);

        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {



    }
}
