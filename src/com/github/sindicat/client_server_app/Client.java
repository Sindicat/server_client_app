package com.github.sindicat.client_server_app;

import java.io.*;
import java.net.*;

public class Client
{

    public Client()
    {
    }

    public static void main(String args[])
        throws IOException
    {
        String address = args[0];
        String port = args[1];
        InetAddress ipAddress = InetAddress.getByName(address);
        Socket socket = new Socket(ipAddress, Integer.parseInt(port));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Enter number");
        do
        {
            if(socket.isOutputShutdown())
                break;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            if(!br.ready())
                continue;
            String request_line = br.readLine();
            String numberStr = "";
            String expStr = "";
            if(request_line.split(" ").length == 2)
            {
                numberStr = request_line.split(" ")[0];
                expStr = request_line.split(" ")[1];
            }
            if(numberStr.matches("^-?\\d+$") && numberStr.length() < 0x186a0)
            {
                if(expStr.equals("+"))
                {
                    String response = "";
                    out.println("request '+'");
                    try
                    {
                        response = in.readLine();
                    }
                    catch(SocketException es)
                    {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    if(response.equals("Connection was closed"))
                    {
                        System.out.println("Numbers were ended.");
                        break;
                    }
                    System.out.println((new StringBuilder()).append("Server response: ").append(response).toString());
                    System.out.println((new StringBuilder()).append("Result: ").append(numberStr).append("+").append(response).append("=").append(Integer.parseInt(numberStr) + Integer.parseInt(response)).toString());
                }
                if(expStr.equals("-"))
                {
                    String response = "";
                    out.println("request '-'");
                    try
                    {
                        response = in.readLine();
                    }
                    catch(SocketException es)
                    {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    if(response.equals("Connection was closed"))
                    {
                        System.out.println("Numbers were ended.");
                        break;
                    }
                    System.out.println((new StringBuilder()).append("Server response: ").append(response).toString());
                    System.out.println((new StringBuilder()).append("Result: ").append(numberStr).append("-").append(response).append("=").append(Integer.parseInt(numberStr) - Integer.parseInt(response)).toString());
                }
                if(expStr.equals("*"))
                {
                    String response = "";
                    out.println("request '*'");
                    try
                    {
                        response = in.readLine();
                    }
                    catch(SocketException es)
                    {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    if(response.equals("Connection was closed"))
                    {
                        System.out.println("Numbers were ended.");
                        break;
                    }
                    System.out.println((new StringBuilder()).append("Server response: ").append(response).toString());
                    System.out.println((new StringBuilder()).append("Result: ").append(numberStr).append("*").append(response).append("=").append(Integer.parseInt(numberStr) * Integer.parseInt(response)).toString());
                }
                if(expStr.equals("/"))
                {
                    String response = "";
                    out.println("request '/'");
                    try
                    {
                        response = in.readLine();
                    }
                    catch(SocketException es)
                    {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    if(response.equals("Connection was closed"))
                    {
                        System.out.println("Numbers were ended.");
                        break;
                    }
                    System.out.println((new StringBuilder()).append("Server response: ").append(response).toString());
                    System.out.println((new StringBuilder()).append("Result: ").append(numberStr).append("/").append(response).append("=").append(Integer.parseInt(numberStr) / Integer.parseInt(response)).toString());
                }
            } else
            if(request_line.equalsIgnoreCase("quit"))
            {
                out.println("quit");
                System.out.println("com.github.sindcat.server_client_app.Client kill connections");
                break;
            }
            System.out.println("Enter number");
        } while(true);
    }
}
