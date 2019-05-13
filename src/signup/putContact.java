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

public class putContact {

    /*
     * This method puts a contact into the account if the contact with the matching ID has been found in the account.
     */

    /**
     * @param email         Email address of contact to be updated
     * @param contact_id    Contact ID used to identify existing contact
     * @param listId        ID of the list the contact will be added into (or remain in)
     * @param access_token  Access token retrieved from getAccessToken call
     * @return              Full Authentication URL
     */

    public StringBuffer putContactData(String email, String contact_id, String listId, String access_token) {

        System.out.println("Contact ID exists, updating contact.");

        JSONObject contactPost = new JSONObject();
        JSONObject mainContactPost = new JSONObject();
        JSONArray listMemberships = new JSONArray();

        try {
            mainContactPost.put("contact_id", contact_id);
            contactPost.put("address", email);
            mainContactPost.put("email_address", contactPost);
            mainContactPost.put("update_source", "Contact");
            listMemberships.put(listId);
            mainContactPost.put("list_memberships", listMemberships);

            // Check JSON being sent to Server
            System.out.println("JSON Object being sent to server: " + mainContactPost.toString());

        } catch (JSONException a) {
            System.out.println("Could not send JSON object to create(PUT) a new contact. Reason: " + a);
        }

        // Define results
        StringBuffer putResult = new StringBuffer();

        try {
            String contactPutBaseUrl = "https://api.cc.email/v3/contacts/";

            // Build URL
            URL contactPutRequestUrl = new URL(contactPutBaseUrl + contact_id);

            // Open connection to update(PUT) contact
            HttpURLConnection con3 = (HttpURLConnection) contactPutRequestUrl.openConnection();
            con3.setDoOutput(true);
            con3.setRequestMethod("PUT");
            con3.setRequestProperty("Authorization", "Bearer " + access_token);
            con3.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con3.setRequestProperty("Accept", "application/json; charset=utf-8");
            OutputStream os = con3.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(mainContactPost.toString());
            osw.flush();
            osw.close();
            os.close();

            // Parse JSON results to String
            BufferedReader in2 = new BufferedReader(new InputStreamReader(con3.getInputStream()));
            String inputLine;

            while ((inputLine = in2.readLine()) != null) {
                putResult.append(inputLine);
            }

            // Close the stream after reading info
            in2.close();

            // Verify JSON PUR Request
            System.out.println("Output from JSON PUT request: " + putResult);

        } catch (Exception c) {
            System.out.println("Could not sent post PUT request. Reason: " + c);
        }

        return putResult;
    }
}

