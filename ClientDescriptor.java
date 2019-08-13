public class ClientDescriptor implements StringConsumer, StringProducer
{
    private static int i = 0;
    private String nick = "User";
    private String msg  = "";
    private MessageBoard board;
    ClientDescriptor()
    {
        nick = nick + i;
        i++;
    }
    public void consume(String str)
    {
        msg = nick+": "+str;
        board.consume(msg);
    }
    public void addConsumer(StringConsumer sc)
    {
        board = (MessageBoard)sc;

    }
    public void removeConsumer(StringConsumer sc)
    {
        ConnectionProxy proxy = (ConnectionProxy)sc;
        board.removeConsumer(proxy);
    }
}