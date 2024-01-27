package repository;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.quizninjafrontend.comment;
import com.example.quizninjafrontend.question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainRepository {
    String yourIp = "172.31.96.1";
    public void login(ExecutorService srv, Handler uiHandler, String username, String password) {
        // log info
        Log.d("DEV", "username: " + username);
        srv.submit(() -> {
            try {
                // Construct the request URL
                URL url = new URL("http://" + yourIp + ":8080/api/login");
                //URL url = new URL("http://localhost:8080/api/login");

                // Create a new HttpURLConnection object
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set the request method to POST
                conn.setRequestMethod("POST");

                // Set the Content-Type header to indicate the request body format
                conn.setRequestProperty("Content-Type", "application/json");

                // Create a JSON object to hold the login information
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", username);
                requestBody.put("password", password);

                // Convert the JSON object to a byte array
                byte[] postDataBytes = requestBody.toString().getBytes("UTF-8");

                // Enable output and set the content length of the request body
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

                // Write the request body bytes to the connection's output stream
                conn.getOutputStream().write(postDataBytes);

                // Check the response code to handle success or failure
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // The login was successful
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the response JSON
                    JSONObject responseJson = new JSONObject(response.toString());

                    // Send the user object to the UI thread
                    Message msg = new Message();
                    msg.obj = "authenticated";
                    uiHandler.sendMessage(msg);
                } else {
                    // The login failed
                    Message msg = new Message();
                    msg.obj = "notAuthenticated";
                    uiHandler.sendMessage(msg);
                }

                // Close the connection
                conn.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void register(ExecutorService srv, Handler uiHandler, String username, String password) {
        srv.submit(() -> {
            try {
                // Construct the request URL
                URL url = new URL("http://" + yourIp + ":8080/api/register");

                // Create a new HttpURLConnection object
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set the request method to POST
                conn.setRequestMethod("POST");

                // Set the Content-Type header to indicate the request body format
                conn.setRequestProperty("Content-Type", "application/json");

                // Create a JSON object to hold the login information
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", username);
                requestBody.put("password", password);

                // Convert the JSON object to a byte array
                byte[] postDataBytes = requestBody.toString().getBytes("UTF-8");

                // Enable output and set the content length of the request body
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

                // Write the request body bytes to the connection's output stream
                conn.getOutputStream().write(postDataBytes);

                // Check the response code to handle success or failure
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // The login was successful
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the response JSON
                    JSONObject responseJson = new JSONObject(response.toString());

                    // Send the user object to the UI thread
                    Message msg = new Message();
                    msg.obj = "registered";
                    uiHandler.sendMessage(msg);
                } else {
                    // The login failed
                    Message msg = new Message();
                    msg.obj = "notRegistered";
                    uiHandler.sendMessage(msg);
                }

                // Close the connection
                conn.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getAll(ExecutorService srv, Handler uiHandler){
        // log info
        Log.d("DEV", "getAll");
        srv.submit(()->{
            try {

                List<question> data = new ArrayList<>();

                URL url =
                        new URL("http://" + yourIp +  ":8080/api/questions");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i <arr.length() ; i++) {

                    JSONObject current = arr.getJSONObject(i);

                    question tempquestion = new question(
                            current);

                    data.add(tempquestion);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }


        });
    }

    public void addQuestion(ExecutorService srv, Handler uiHandler, String question, String answer, String course, String source) {
        srv.submit(() -> {
            try {
                // Construct the request URL
                URL url = new URL("http://" + yourIp + ":8080/api/postQuestion");

                // Create a new HttpURLConnection object
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set the request method to POST
                conn.setRequestMethod("POST");

                // Set the Content-Type header to indicate the request body format
                conn.setRequestProperty("Content-Type", "application/json");

                // Create a JSON object to hold the login information
                JSONObject requestBody = new JSONObject();
                requestBody.put("question_url", question);
                requestBody.put("solution_url", answer);
                requestBody.put("course", course);
                requestBody.put("source", source);
                //List<comment> comments = new ArrayList<comment>();
                requestBody.put("comments", new JSONArray());

                // Convert the JSON object to a byte array
                byte[] postDataBytes = requestBody.toString().getBytes("UTF-8");

                // Enable output and set the content length of the request body
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

                // Write the request body bytes to the connection's output stream
                conn.getOutputStream().write(postDataBytes);

                // Check the response code to handle success or failure
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // The login was successful
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the response JSON
                    JSONObject responseJson = new JSONObject(response.toString());

                    // Send the user object to the UI thread
                    Message msg = new Message();
                    msg.obj = "added";
                    uiHandler.sendMessage(msg);
                } else {
                    // The login failed
                    Message msg = new Message();
                    msg.obj = "notAdded";
                    uiHandler.sendMessage(msg);
                }

                // Close the connection
                conn.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void downloadImage(ExecutorService srv,String url, Handler uiHandler){

        srv.submit(()->{
            try {
                URL url1 = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());
                Message msg = new Message();
                msg.obj = bmp;
                uiHandler.sendMessage(msg);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }





}
