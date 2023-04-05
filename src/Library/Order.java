package Library;

import java.util.Date;

enum Status
{
    ordered, shipped, receive;
}

public class Order {

    public String OID;
    public String UID;
    public Date orderDate;
    public String ISBN;
    public int inventory;
    public Status status;
}
