package signup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class serverFlow {

    /*
     * This method can be used to generate the URL an account owner would use to allow your app to access their account.
     * After visiting the URL, the account owner is prompted to log in and allow your app to access their account.
     * They are then redirected to your redirect URI with the authorization code appended as a query parameter.
     * e.g.: http://localhost:8080/?code={authorization_code}
     */

    /**
     * @param redirectUri   URL Encoded Redirect URI
     * @param clientId      API Key
     * @return              Full Authentication URL
     */

    public String getAuthenticationUrl(String redirectUri, String clientId){

        // Create authorization URL
        String baseUrl = "https://api.cc.email/v3/idfed";

        return baseUrl + "?client_id=" + clientId + "&scope=contact_data&response_type=code"
                + "&redirect_uri=" + redirectUri;
    }

    /*
     * This method can be used to exchange an authorization code for an access token.
     * Make this call by passing in the code present when the account owner is redirected back to you after authenticating.
     * The response will contain an 'access_token' and 'refresh_token' in a JSON object.
     */

    /**
     * @param redirectUri   URL Encoded Redirect URI
     * @param clientId      API Key
     * @param clientSecret  API Secret
     * @param authCode      Authorization Code
     * @return              Full Authentication URL
     */

    public StringBuffer getAccessToken(String redirectUri, String clientId, String clientSecret, String authCode)
            throws IOException {

        StringBuffer authResult = new StringBuffer();

        // Make authorization header with API Key:API Secret and encode
        String credentials = clientId + ":" + clientSecret;
        String auth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        String baseAccessUrl = "https://idfed.constantcontact.com/as/token.oauth2";
        // Create request URL
        String fullUrl = baseAccessUrl + "?code=" + authCode + "&redirect_uri=" + redirectUri
                + "&grant_type=authorization_code";
            URL authorizeUrl = new URL(fullUrl);

            // Open connection
            HttpURLConnection con = (HttpURLConnection) authorizeUrl.openConnection();

            // Set Method
            con.setRequestMethod("POST");

            // Add Auth Header
            con.setRequestProperty("Authorization", auth);

             // Read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                authResult.append(inputLine);
            }
            // Close the stream
            in.close();

        return authResult;
    }

    /*
     * This method is used to exchange a refresh token for a new access token and refresh token.
     * Make this call by passing in the refresh token returned with the access token.
     * The response will contain a new 'access_token' and 'refresh_token'.
     */

    /**
     * @param clientId      API Key
     * @param clientSecret  API Secret
     * @param refresh_token Refresh Token
     * @return              Full Authentication URL
     */

    public StringBuffer refreshToken(String baseRequestUrl, String clientId, String clientSecret, String refresh_token)
            throws IOException {

        StringBuffer refreshResult = new StringBuffer();

        // Make authorization header with API Key:API Secret and encode
        String credentials = clientId + ":" + clientSecret;
        String auth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        // Create refresh request URL
        String fullRefreshUrl = baseRequestUrl + "?refresh_token=" + refresh_token + "&grant_type=refresh_token";

            URL authorizeUrl = new URL(fullRefreshUrl);

            // Open connection
            HttpURLConnection con = (HttpURLConnection) authorizeUrl.openConnection();

            // Set Method
            con.setRequestMethod("POST");

            // Add Auth Header
            con.setRequestProperty("Authorization", auth);

            // Read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                refreshResult.append(inputLine);
            }
            // Close the stream
            in.close();

        return refreshResult;
    }

}
