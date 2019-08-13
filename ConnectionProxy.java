import java.net.*;
import java.io.*;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer
{
    Socket socket           = null;
    InputStream is          = null;
    DataInputStream dis     = null;
    OutputStream os         = null;
    DataOutputStream dos    = null;
    ClientDescriptor client = null;
    ClientGUI GUI           = null;
    Boolean running         = true;

    ConnectionProxy(ClientGUI client)
    {
        GUI = client;
    }
    ConnectionProxy(Socket socket)
    {
        this.addSocket(socket);
    }
    public void addSocket(Socket socket)
    {
        this.socket = socket;
        try{
            is = socket.getInputStream();
            /**Input stream was created..       */
            dis = new DataInputStream(is);
            /**Data input stream was created..  */
            os = socket.getOutputStream();
            /**Output stream was created..      */
            dos = new DataOutputStream(os);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void consume(String str)
    {
        try {
            dos.writeUTF(str);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void addConsumer(StringConsumer sc)
    {
        client = (ClientDescriptor)sc;
    }
    public void removeConsumer(StringConsumer sc)
    {
        if (is!=null) {
            try { is.close(); } catch(IOException e) {e.printStackTrace();}
        }
        if (dis!=null) {
            try { dis.close(); } catch(IOException e) {e.printStackTrace();}
        }
        if (os!=null) {
            try { os.close(); } catch(IOException e) {e.printStackTrace();}
        }
        if (dos!=null) {
            try { dos.close(); } catch(IOException e) {e.printStackTrace();}
        }
        if (client!=null)
            client.removeConsumer(this);
    }
    public void Stop(){
        running = false;
    }
    @Override
    public void run() {
        String msg="";
        while(running){
            try {
                msg = dis.readUTF();
            }
            catch(IOException e){
                e.printStackTrace();
                running = false;
            }
            if (GUI == null)
                client.consume(msg);
            else
                GUI.consume(msg);
        }
    }
}