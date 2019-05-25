package com.lxg.spring.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;



public class GcpBigqueryRest {

	  // [START bigquery_label_dataset]
	  //static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	  //static final JsonFactory JSON_FACTORY = new JacksonFactory();

	  public static class Dataset {
	    @Key private Map<String, String> labels;

	    public Map<String, String> getLabels() {
	      return this.labels;
	    }

	    public Dataset addLabel(String key, String value) {
	      if (this.labels == null) {
	        this.labels = new HashMap<>();
	      }
	      this.labels.put(key, value);
	      return this;
	    }
	  }


	    
		  private static String getAccessToken3() throws IOException {
			  InputStream credentialsJSON = new FileInputStream("D:/workSpace-springboot/googleCloud/token/DC+-+INS+Data+Analyst-9d34c5b27313.json");
			    JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
			    HttpTransport httpTransport = null;
				try {
					httpTransport = GoogleNetHttpTransport.newTrustedTransport();
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    GoogleCredential googleCredential = GoogleCredential.fromStream(credentialsJSON ,httpTransport,JSON_FACTORY);
			    
			    googleCredential.refreshToken();
			  return googleCredential.getAccessToken();
			}
		  
	  private static String getAccessToken() throws IOException {
		  GoogleCredential googleCredential = GoogleCredential
		      .fromStream(new FileInputStream("D:/workSpace-springboot/googleCloud/token/DC+-+INS+Data+Analyst-9d34c5b27313.json"))
		      .createScoped(Arrays.asList("https://accounts.google.com/o/oauth2/auth"));
		  googleCredential.refreshToken();
		  return googleCredential.getAccessToken();
		}
	  
	  private static String getAccessToken1() throws IOException {
		  
		    GoogleCredential credential = GoogleCredential.getApplicationDefault();
		    credential = credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/bigquery"));
			  
//			InputStream resourceAsStream = AuthTest.class.getClassLoader().getResourceAsStream("Google-Play-Android-Developer.json");
//			GoogleCredential credential = GoogleCredential.fromStream(resourceAsStream);
			  

		    credential.refreshToken();
		    return credential.getAccessToken();
		}
	  
	  
	  /**
	   * Add or modify a label on a dataset.
	   *
	   * <p>See <a href="https://cloud.google.com/bigquery/docs/labeling-datasets">the BigQuery
	   * documentation</a>.
	   */
	  public static void labelDataset02(
	      String projectId, String datasetId, String labelKey, String labelValue) throws IOException {

	   
	    String accessToken = getAccessToken1();

	    //看上面 
		HttpTransport HTTP_TRANSPORT = null;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		
	    // Set the content of the request.
	    Dataset dataset = new Dataset();
	    dataset.addLabel(labelKey, labelValue);
	    HttpContent content = new JsonHttpContent(JSON_FACTORY, dataset);

	    // Send the request to the BigQuery API.
	    String urlFormat =
	        "https://www.googleapis.com/bigquery/v2/projects/%s/datasets/%s"
	            + "?fields=labels&access_token=%s";
	    GenericUrl url = new GenericUrl(String.format(urlFormat, projectId, datasetId, accessToken));
	    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
	    HttpRequest request = requestFactory.buildPostRequest(url, content);
	    request.setParser(JSON_FACTORY.createJsonObjectParser());

	    // Workaround for transports which do not support PATCH requests.
	    // See: http://stackoverflow.com/a/32503192/101923
	    request.setHeaders(new HttpHeaders().set("X-HTTP-Method-Override", "PATCH"));
	    HttpResponse response = request.execute();

	    // Check for errors.
	    if (response.getStatusCode() != 200) {
	      throw new RuntimeException(response.getStatusMessage());
	    }

	    Dataset responseDataset = response.parseAs(Dataset.class);
	    System.out.printf(
	        "Updated label \"%s\" with value \"%s\"\n",
	        labelKey, responseDataset.getLabels().get(labelKey));
	  }
	  // [END bigquery_label_dataset]

	  // [START bigquery_label_table]
	  public static class Table {
	    @Key private Map<String, String> labels;

	    public Map<String, String> getLabels() {
	      return this.labels;
	    }

	    public Table addLabel(String key, String value) {
	      if (this.labels == null) {
	        this.labels = new HashMap<>();
	      }
	      this.labels.put(key, value);
	      return this;
	    }
	  }

	  /**
	   * Add or modify a label on a table.
	   *
	   * <p>See <a href="https://cloud.google.com/bigquery/docs/labeling-datasets">the BigQuery
	   * documentation</a>.
	   */
	  public static void labelTable(
	      String projectId,
	      String datasetId,
	      String tableId,
	      String labelKey,
	      String labelValue)
	      throws IOException {

		  HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
		  JsonFactory JSON_FACTORY = new JacksonFactory();

//		  GoogleCredential credential = new GoogleCredential.Builder()
//		      .setTransport(HTTP_TRANSPORT)
//		      .setJsonFactory(JSON_FACTORY)
//		      .setServiceAccountId("...gserviceaccount.com")
//		      .setServiceAccountScopes("https://www.googleapis.com/auth/androidpublisher")
//		      .setServiceAccountPrivateKeyFromP12File(keyFile)
//		      .build();
//		  
	    // Authenticate requests using Google Application Default credentials.
	    GoogleCredential credential = GoogleCredential.getApplicationDefault();
	    //credential = credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/bigquery"));

	    // Get a new access token.
	    // Note that access tokens have an expiration. You can reuse a token rather than requesting a
	    // new one if it is not yet expired.
	    credential.refreshToken();
	    String accessToken = credential.getAccessToken();

	    // Set the content of the request.
	    Table table = new Table();
	    table.addLabel(labelKey, labelValue);
	    HttpContent content = new JsonHttpContent(JSON_FACTORY, table);

	    // Send the request to the BigQuery API.
	    String urlFormat =
	        "https://www.googleapis.com/bigquery/v2/projects/%s/datasets/%s/tables/%s"
	            + "?fields=labels&access_token=%s";
	    GenericUrl url =
	        new GenericUrl(String.format(urlFormat, projectId, datasetId, tableId, accessToken));
	    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
	    HttpRequest request = requestFactory.buildPostRequest(url, content);
	    request.setParser(JSON_FACTORY.createJsonObjectParser());

	    // Workaround for transports which do not support PATCH requests.
	    // See: http://stackoverflow.com/a/32503192/101923
	    request.setHeaders(new HttpHeaders().set("X-HTTP-Method-Override", "PATCH"));
	    HttpResponse response = request.execute();

	    // Check for errors.
	    if (response.getStatusCode() != 200) {
	      throw new RuntimeException(response.getStatusMessage());
	    }

	    Table responseTable = response.parseAs(Table.class);
	    System.out.printf(
	        "Updated label \"%s\" with value \"%s\"\n",
	        labelKey, responseTable.getLabels().get(labelKey));
	  }
	  // [END bigquery_label_table]

	  public static void printUsage() {
	    System.err.println("Command expects 4 or 5 arguments:");
	    System.err.println("\tproject dataset [table] key value");
	  }

	  public static void main(final String[] args) throws IOException, InterruptedException {
//	    if (args.length != 4 && args.length != 5) {
//	      printUsage();
//	      System.exit(1);
//	    }

	    
	    String projectId = "luminous-night-238606";
	    String datasetId = "usa_names";
	    String tableId = "usa_1910_current";
	    
	    String labelKey = "111";
	    String labelValue = "111";
	    
	    //labelTable(projectId, datasetId, tableId, labelKey, labelValue);
	      
	    labelDataset02(projectId, datasetId, tableId, labelKey);
  
	  }
}
