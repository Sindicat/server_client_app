package com.github.sindicat.client_server_app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadedServer
    implements Runnable
{

    public MultiThreadedServer(int port)
    {
        serverSocket = null;
        isStopped = false;
        runningThread = null;
        arrThreads = null;
        serverPort = port;
        arrThreads = new ArrayList();
    }

    public void run()
    {
        synchronized(this)
        {
            runningThread = Thread.currentThread();
        }
        openServerSocket();
        Thread thread;
        for(; !isStopped(); thread.start())
        {
            Socket clientSocket = null;
            try
            {
                clientSocket = serverSocket.accept();
            }
            catch(IOException e)
            {
                if(isStopped())
                {
                    System.out.println("Server Stopped.");
                    return;
                } else
                {
                    throw new RuntimeException("Error accepting client connection", e);
                }
            }
            thread = new Thread(new WorkerRunnable(clientSocket, "Multithreaded Server"));
        }

        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped()
    {
        return isStopped;
    }

    public synchronized void stop()
    {
        isStopped = true;
        try
        {
            serverSocket.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket()
    {
        try
        {
            serverSocket = new ServerSocket(serverPort);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Cannot open port 8080", e);
        }
        System.out.println("Server status: listening...");
    }

    protected int serverPort;
    protected ServerSocket serverSocket;
    protected boolean isStopped;
    protected Thread runningThread;
    protected ArrayList arrThreads;
}
