package com.example.samsonaiyegbusi.events2you.REST_calls;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by samsonaiyegbusi on 01/03/16.
 */
public class HTTP_Methods {

    String ip = "http://192.168.0.8:8080/Events2You_API/API";

    public String GET(String desiredUrl) {
        try {

            URL url = new URL(ip + desiredUrl);

            HttpURLConnection connect2Rest = (HttpURLConnection) url.openConnection();


            if (connect2Rest.getResponseCode() != 200) {
                throw new IOException(connect2Rest.getResponseMessage());
            }

            BufferedReader is = new BufferedReader(new InputStreamReader(connect2Rest.getInputStream()));
            String inString;
            StringBuilder sb = new StringBuilder();
            while ((inString = is.readLine()) != null) {
                sb.append(inString + "\n");
            }

            String xml = sb.toString();

            return xml;

        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return null;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String POST(String url, String parameters)
    {



        try {

            URL Url = new URL(ip + url );

            byte[] postData = parameters.getBytes( StandardCharsets.UTF_8 );

            HttpURLConnection connect2Rest = (HttpURLConnection) Url.openConnection();

            connect2Rest.setInstanceFollowRedirects( false );
            connect2Rest.setRequestMethod( "POST" );
            connect2Rest.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connect2Rest.setRequestProperty( "charset", "utf-8");
            connect2Rest.setRequestProperty( "Content-Length", Integer.toString( postData.length ));


            connect2Rest.setDoOutput( true );

            connect2Rest.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream(connect2Rest.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = connect2Rest.getResponseCode();


            BufferedReader is = new BufferedReader(new InputStreamReader(connect2Rest.getInputStream()));
            String inString;
            StringBuilder sb = new StringBuilder();
            while ((inString = is.readLine()) != null) {
                sb.append(inString + "\n");
            }

            is.close();
            String xml = sb.toString();

            System.out.print(responseCode);

            return xml;



        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return null;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String PUT(String url, String parameters)
    {



        try {

            URL Url = new URL(ip + url );

            byte[] postData = parameters.getBytes( StandardCharsets.UTF_8 );

            HttpURLConnection connect2Rest = (HttpURLConnection) Url.openConnection();

            connect2Rest.setInstanceFollowRedirects( false );
            connect2Rest.setRequestMethod( "PUT" );
            connect2Rest.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connect2Rest.setRequestProperty( "charset", "utf-8");
            connect2Rest.setRequestProperty( "Content-Length", Integer.toString( postData.length ));


            connect2Rest.setDoOutput( true );

            connect2Rest.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream(connect2Rest.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = connect2Rest.getResponseCode();


            BufferedReader is = new BufferedReader(new InputStreamReader(connect2Rest.getInputStream()));
            String inString;
            StringBuilder sb = new StringBuilder();
            while ((inString = is.readLine()) != null) {
                sb.append(inString + "\n");
            }

            is.close();
            String xml = sb.toString();

            System.out.print(responseCode);

            return xml;



        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String DELETE(String url)
    {

        try {
            URL Url = new URL(url );


            HttpURLConnection connect2Rest = (HttpURLConnection) Url.openConnection();
            connect2Rest.setDoInput(true);
            connect2Rest.setInstanceFollowRedirects( false );
            connect2Rest.setRequestMethod("DELETE");
          connect2Rest.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connect2Rest.setRequestProperty( "charset", "utf-8");


            connect2Rest.setUseCaches(false);
            int responseCode = connect2Rest.getResponseCode();


            BufferedReader is = new BufferedReader(new InputStreamReader(connect2Rest.getInputStream()));
            String inString;
            StringBuilder sb = new StringBuilder();
            while ((inString = is.readLine()) != null) {
                sb.append(inString + "\n");
            }

            is.close();
            String xml = sb.toString();

            System.out.print(responseCode);

            return xml;

        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return null;
    }

}
