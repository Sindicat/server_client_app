package com.github.sindicat.client_server_app;

import java.io.*;

public class StartServer
{

    public StartServer()
    {
    }

    public static void main(String args[])
        throws IOException
    {
        MultiThreadedServer server = new MultiThreadedServer(Integer.parseInt(args[0]));
        (new Thread(server)).start();
        String line;
        do
        {
            BufferedReader br;
            for(br = new BufferedReader(new InputStreamReader(System.in)); !br.ready(););
            line = br.readLine();
        } while(!line.equalsIgnoreCase("stop"));
        System.out.println("Stopping Server");
        server.stop();
        System.exit(0);
    }
}
