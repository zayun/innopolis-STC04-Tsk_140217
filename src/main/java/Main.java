import CarShop.CarNotFoundException;
import CarShop.Store;
import logging.CustomAppender;
import org.apache.log4j.Logger;

/**
 * Created by sa on 08.02.17.
 */
public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);
    private static CustomAppender customAppender =new CustomAppender();

    static {
        //DOMConfigurator.configure("src/main/resources/log4j.xml");
        logger.addAppender(customAppender);
    }

    public static void main(String[] args) {
        Store store = new Store();
        logger.trace("store created");
        store.createCar(500000, "kia-rio",
                "B146AA");


        try {
            store.sellCar("kia-reva",
                    "Jhon",
                    "Konner" ,
                    "+79126241898");
        } catch (CarNotFoundException e) {
            System.out.println(" ");
        }
        store.save();
    }
}
