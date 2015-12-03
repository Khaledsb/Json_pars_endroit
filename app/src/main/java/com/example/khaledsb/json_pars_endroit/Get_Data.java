package com.example.khaledsb.json_pars_endroit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Khaled Sb on 03/12/2015.
 */
public class Get_Data {


    public static String getInfo(){


        URL oracle = null;
        BufferedReader in = null;
        URLConnection yc = null;
        String inputLine, result="";

        try {

            oracle = new URL("http://10.0.0.2/android");
            yc = oracle.openConnection();
            in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));


            while ((inputLine = in.readLine()) != null) {
                //  inputLine+=inputLine;
                System.out.println(inputLine+"\n");
                result=result+inputLine;
            }

            System.out.println("res  :" + result);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




     return result;

    }
}
