package com.sean.apigeeapi;

import com.google.api.services.apigee.v1.Apigee;
import com.google.api.services.apigee.v1.model.GoogleCloudApigeeV1DeveloperAppKey;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.apigee.v1.ApigeeScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.util.Collections;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {

        private static final String organizationId = "YOUR_PROJECT";
        private static final String developerEmail = "APIGEE_DEV";
        private static final String appName = "APIGEE_APP_NAME";
        private static final String key = "Xyz2";
        private static final String secret = "Xyz2";
        private static String getUri = String.format("organizations/%s/developers/%s/apps/%s/keys", organizationId,
                        developerEmail, appName);
        private static String uri = String.format("organizations/%s/developers/%s/apps/%s/keys/%s", organizationId,
                        developerEmail, appName, key);
        private static String createUri = String.format("organizations/%s/developers/%s/apps/%s", organizationId,
                        developerEmail, appName);
        // add your list of Apigee API Products here
        private static List<Object> apiProducts = List.of("helloworld");

        public static void main(String[] args) throws GeneralSecurityException, IOException {

                // 1. Set up the HTTP transport and JSON factory
                final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                final GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                // 2. Load credentials (using application default credentials)
                final GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                                .createScoped(Collections.singleton(ApigeeScopes.CLOUD_PLATFORM));

                // 3. Create the Apigee client
                Apigee apigee = new Apigee.Builder(httpTransport, jsonFactory, new HttpCredentialsAdapter(credentials))
                                .setApplicationName(appName)
                                .build();

                GoogleCloudApigeeV1DeveloperAppKey createdKey = createKeyRequest(apigee);

                // this will return a 400 Bad Request
                // GoogleCloudApigeeV1DeveloperAppKey updatedKey = updateKeyRequest(apigee,
                // List.of("helloworld"), createdKey);

                GoogleCloudApigeeV1DeveloperAppKey replacedKey = replaceKeyRequest(apigee, createdKey);
                System.out.println("\nReplaced Key:\n" + replacedKey);

                GoogleCloudApigeeV1DeveloperAppKey existingKey = getKeyRequest(apigee, key);
                System.out.println("\nFetched Existing Apigee Developer App Key:\n" + existingKey);

                // uncomment to see the createRequest execute for a new key and secret
                // createKeyRequest(apigee,"ABCDJ", "ABCDJ");
        }

        public static GoogleCloudApigeeV1DeveloperAppKey createKeyRequest(Apigee apigee, String newkey,
                        String newsecret) throws IOException {
                /**
                 * Creates a new Apigee API Key and Secret from the function parameters.
                 */
                // Send the create request
                GoogleCloudApigeeV1DeveloperAppKey apigeeKey = new GoogleCloudApigeeV1DeveloperAppKey();
                apigeeKey.setConsumerKey(newkey);
                apigeeKey.setConsumerSecret(newsecret);
                GoogleCloudApigeeV1DeveloperAppKey result = createKeyRequestHelper(apigee, apigeeKey);
                System.out.println("\nCreate Developer App Key request result:\n" + result);
                return result;
        }

        public static GoogleCloudApigeeV1DeveloperAppKey createKeyRequest(Apigee apigee) throws IOException {
                /**
                 * Creates an Apigee Developer API Key using the default key and secret entered
                 * in the final static variables above.
                 */
                // Send the create request
                GoogleCloudApigeeV1DeveloperAppKey apigeeKey = new GoogleCloudApigeeV1DeveloperAppKey();
                apigeeKey.setConsumerKey(key);
                apigeeKey.setConsumerSecret(secret);
                GoogleCloudApigeeV1DeveloperAppKey result = createKeyRequestHelper(apigee, apigeeKey);
                System.out.println("\nCreate Developer App Key request result:\n" + result);
                return result;
        }

        private static GoogleCloudApigeeV1DeveloperAppKey createKeyRequestHelper(Apigee apigee,
                        GoogleCloudApigeeV1DeveloperAppKey apigeeKey) throws IOException {
                /**
                 * Creates the request and sends it to the Apigee Management API.
                 */
                Apigee.Organizations.Developers.Apps.Keys.CreateRequest createRequestKey = apigee.organizations()
                                .developers()
                                .apps().keys().create(createUri, apigeeKey);
                GoogleCloudApigeeV1DeveloperAppKey result = createRequestKey.execute();
                return result;
        }

        public static GoogleCloudApigeeV1DeveloperAppKey updateKeyRequest(Apigee apigee,
                        GoogleCloudApigeeV1DeveloperAppKey apigeeKey) throws IOException {
                /**
                 * Uses the
                 */
                /* this is the method that fails with 404 Invalid Argument */
                apigeeKey.setApiProducts(apiProducts);
                // apigeeKey.setConsumerKey(key);
                // apigeeKey.setConsumerSecret(secret);
                Apigee.Organizations.Developers.Apps.Keys.UpdateDeveloperAppKey request = apigee.organizations()
                                .developers()
                                .apps().keys().updateDeveloperAppKey(createUri, apigeeKey);
                GoogleCloudApigeeV1DeveloperAppKey result = request.execute();
                System.out.println("\nCreate Developer App Key request result:\n" + result);
                return result;
        }

        public static GoogleCloudApigeeV1DeveloperAppKey getKeyRequest(Apigee apigee, String apigeeKey)
                        throws IOException {
                /**
                 * Fetches an existing Apigee API Key.
                 */
                String localUri = getUri + "/" + apigeeKey;
                // Get a specific developer app key
                Apigee.Organizations.Developers.Apps.Keys.Get request = apigee.organizations().developers()
                                .apps().keys().get(localUri);
                return request.execute();
        }

        public static GoogleCloudApigeeV1DeveloperAppKey replaceKeyRequest(Apigee apigee,
                        GoogleCloudApigeeV1DeveloperAppKey apigeeKey) throws IOException {
                /**
                 * Replaces the Apigee Developer API key
                 */
                apigeeKey.setApiProducts(apiProducts);
                Apigee.Organizations.Developers.Apps.Keys.ReplaceDeveloperAppKey replaceKeyRequest = apigee
                                .organizations().developers()
                                .apps().keys().replaceDeveloperAppKey(uri, apigeeKey);
                return replaceKeyRequest.execute();
        }
}
