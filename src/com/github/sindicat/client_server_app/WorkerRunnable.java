package com.github.sindicat.client_server_app;// Decompiled by DJ v3.12.12.101 Copyright 2016 Atanas Neshkov  Date: 23.03.2018 19:19:48

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkerRunnable
    implements Runnable
{

    public WorkerRunnable(Socket clientSocket, String serverText)
    {
        this.clientSocket = null;
        this.serverText = null;
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run()
    {
        try
        {
            System.out.println((new StringBuilder()).append("New client").append(clientSocket.getInetAddress()).append(" was connected to server").toString());
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            ArrayList integers = new ArrayList();
            Scanner scanner = null;
            try
            {
                scanner = new Scanner(new File("com/github/sindicat/client_server_app/data.txt"));
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            int number;
            for(; scanner.hasNextInt(); integers.add(Integer.valueOf(number)))
                number = scanner.nextInt();

            int i = 0;
            do
            {
                if(clientSocket.isClosed())
                    break;
                String request = "";
                try
                {
                    request = input.readLine();
                }
                catch(SocketException exx)
                {
                    System.out.println();
                    input.close();
                    output.close();
                    clientSocket.close();
                    System.out.println((new StringBuilder()).append("Connection for client").append(clientSocket.getInetAddress()).append(" was closed").toString());
                    break;
                }
                if(request == null || request.equals("quit") || i == integers.size())
                {
                    output.println("Connection was closed");
                    input.close();
                    output.close();
                    clientSocket.close();
                    System.out.println((new StringBuilder()).append("Connection for client").append(clientSocket.getInetAddress()).append(" was closed").toString());
                    break;
                }
                System.out.println((new StringBuilder()).append(clientSocket.getInetAddress()).append(" Request:").append(request).toString());
                output.println(integers.get(i));
                i++;
            } while(true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    protected Socket clientSocket;
    protected String serverText;
}
