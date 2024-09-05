package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Getenv {
    String path1 ;
    String pathArray[];
    public String [] getPath() throws InterruptedException {
         String path   = System.getenv("DDAS_DIRECTORIES");
        if (path !=null)
        {
                pathArray = path.split(System.getProperty("path.separator"));
            for (int i = 0; i<pathArray.length;i++)
            {
                System.out.println(pathArray[i]);
            }
            return  pathArray;
        }else
        {
            System.out.println("variable are not found ");
            return pathArray;
        }
    }
    public String getusernName () throws IOException, InterruptedException {
        String username;
        StringBuilder builder = new StringBuilder();
        Process process = Runtime.getRuntime().exec("whoami /user");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while ((username = reader.readLine())!=null)
        {
            builder.append(username).append("\n");
        }
        reader.close();
        process.waitFor();

        String output[]= builder.toString().split("\n");
        if (output.length>1)
        {
            String [] parts = output[6].split("\\s+");
             username =  parts[parts.length-2];
        }

        return username;
    }

}


