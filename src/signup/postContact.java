package signup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class postContact {

    /*
     * This method posts a contact to the account if no contact matching the contact ID is found in the account.
     */

    /**
     * @param email         Email address of contact to be added
     * @param listId        ID of the list the contact will go into
     * @param access_token  Access token retrieved from getAccessToken call
     * @return              Full Authentication URL
     */

    public StringBuffer postContactData(String email, String listId, String access_token){

        System.out.println("Contact ID is empty, creating contact.");

        // Begin building JSON objects
        JSONObject contactPost = new JSONObject();
        JSONObject mainContactPost = new JSONObject();
        JSONArray listMemberships = new JSONArray();

        try {
            contactPost.put("address", email);
            mainContactPost.put("email_address", contactPost);
            mainContactPost.put("create_source", "Contact");
            listMemberships.put(listId);
            mainContactPost.put("list_memberships", listMemberships);

            System.out.println("JSON Object being sent to server: " + mainContactPost.toString());

        } catch (JSONException a){
            System.out.println("Could not send JSON object to create(POST) a new contact. Reason: " + a);
        }


        StringBuffer postResult = new StringBuffer();
        try {
            // Build URL
            URL contactPostUrl = new URL("https://api.cc.email/v3/contacts");

            // Do a post with built JSON Object
            HttpURLConnection con3 = (HttpURLConnection) contactPostUrl.openConnection();
            con3.setDoOutput(true);
            con3.setRequestMethod("POST");
            con3.setRequestProperty("Authorization", "Bearer " + access_token);
            con3.setRequestProperty("Content-Type","application/json; charset=utf-8");
            con3.setRequestProperty("Accept", "application/json; charset=utf-8");
            OutputStream os = con3.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(mainContactPost.toString());
            osw.flush();
            osw.close();
            os.close();

            BufferedReader in2 = new BufferedReader(new InputStreamReader(con3.getInputStream()));
            String inputLine;

            while ((inputLine = in2.readLine()) != null) {
                postResult.append(inputLine);
            }
            // Close the stream after reading info
            in2.close();
            System.out.println("Output from JSON POST Request: " + postResult);

        } catch (Exception b){
            System.out.println("Could not post contact to contact server. Reason: " + b);
        }

        return postResult;
    }
}
