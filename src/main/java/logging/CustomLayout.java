package logging;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by smoldyrev on 14.02.17.
 */
public class CustomLayout extends PatternLayout {
    public String format(LoggingEvent loggingEvent)
    {

        String logText = "\nCustomApp:"+loggingEvent.timeStamp+" "
                +loggingEvent.getLocationInformation().getClassName()
                +", "+loggingEvent.getLocationInformation().getMethodName()
                +":"+(loggingEvent.getLocationInformation().getLineNumber())
                +"("+loggingEvent.getMessage().toString()+")\n";
        //System.out.println(logText);

        return logText.toString();
    }
}
