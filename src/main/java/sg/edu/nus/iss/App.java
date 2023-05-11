package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 * run server app (java sg.edu.nus.iss/App localhost 12345)
 */
public class App 
{
    public static void main( String[] args ) throws NumberFormatException, UnknownHostException, IOException
    {
        String serverHost = args[0];
        String serverPort = args[1];
        //establish connection to server in day 4 slide 8
        // server must be started first
        Socket socket = new Socket(serverHost, Integer.parseInt(serverPort));
        //set up console input from keyboard
        //variable to save keyboard inputs
        //variable to save msgReceived
        Console con = System.console();
        String keyBoardInput = "";
        String msgReceived = "";

        //similar to server in day 4 slide 9
        //Try initialise input stream and decorate it
        try(InputStream is = socket.getInputStream())
        {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            // try initialise output stream and decorate it
            try(OutputStream os = socket.getOutputStream())
            {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                //while loop
                while(!keyBoardInput.equals("close"))
                {
                    keyBoardInput = con.readLine("Enter a command: ");
                    //send message across through the communication tunnel
                    dos.writeUTF(keyBoardInput);
                    dos.flush();
                    //receive message from server (response) and process it
                    msgReceived = dis.readUTF();
                    System.out.println(msgReceived);
                }
                //close output stream in reverse order
               
                //bos.flush();
                dos.close();
                bos.close();
                os.close();
            } catch(EOFException ex)
            {
                //ex.printStackTrace();
            }
            dis.close();
            bis.close();

        } catch(EOFException ex) 
        {
            ex.printStackTrace();
        }
        socket.close();
    }
}
