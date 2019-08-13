import java.util.Vector;

public class MessageBoard implements StringConsumer, StringProducer
{
    private Vector<ConnectionProxy> mb;
    MessageBoard(){
        mb = new Vector<ConnectionProxy>();
    }
    public void consume(String str)
    {
        mb.forEach((proxy) -> proxy.consume(str));
    }
    public void addConsumer(StringConsumer sc)
    {
        mb.add((ConnectionProxy)sc);
    }
    public void removeConsumer(StringConsumer sc)
    {
        ConnectionProxy proxy = (ConnectionProxy)sc;
        proxy.Stop();
        System.out.println(mb.indexOf(sc));
        mb.remove(sc);
    }
}