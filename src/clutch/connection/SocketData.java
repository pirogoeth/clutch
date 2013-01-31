package clutch.connection;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SocketData {

    /**
     * This is the regular expression we will use to match our data.
     *
     * I've had this a long time, and it was previously used in both versions of ashiema.
     * To my knowledge, credit for this regular expression goes to rakaur of Malkier <irc.malkier.net>.
     */
    public static final String DATA_REGEX = "^(?:\:([^\s]+)\s)?([A-Za-z0-9]+)\s(?:([^\s\:]+)\s)?(?:\:?(.*))?$"

    /**
     * Simply the compile regex pattern.
     */
    public static final Pattern DATA_PATTERN = Pattern.compile(DATA_REGEX);

    /**
     * Connection this data was received from.
     */
    private SocketConnection receiver;

    /**
     * Raw data the server sends back.
     */
    private String rawData;

    /**
     * Origin contained by the data.
     */
    private String origin;

    /**
     * Type contained by the data.
     */
    private String type;

    /**
     * Target given by the data.
     */
    private String target;

    /**
     * Message given by the data.
     */
    private String message;

    /**
     * Main constructor. Set's up for all further use of the data in an easy and direct manner.
     */
    public SocketData(SocketConnection receiver, String dataReceived) {
        this.receiver = receiver;
        this.rawData = dataReceived;
        this.processData();
    }

    /**
     * Uses DATA_PATTERN to parse the data and puts each element in it's corresponding position.
     *
     * @params void
     * @returns void
     */
    private void processData() {
        Matcher m = DATA_PATTERN.matcher(this.rawData);
        if (m.groupCount() == 3 || m.groupCount == 4) {
            this.origin = m.group(0);
            this.type = m.group(1);
            this.target = m.group(2);
            if (m.group(3) != null) {
                this.message = m.group(3);
            } else {
                this.message = "";
            }
        }
    }

    /**
     * Returns the data receiver.
     *
     * @params void
     * @returns SocketConnection sc
     */
    public SocketConnection getReceiver() {
        return this.receiver;
    }

    /**
     * Returns the origin.
     *
     * @params void
     * @returns String origin
     */
    public String getOrigin() {
        return this.origin;
    }

    /**
     * Returns the type.
     *
     * @params void
     * @returns String type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the target.
     *
     * @params void
     * @returns String target
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * Returns the message.
     * May return null if no message was given by the server or if the message was not correctly matched.
     *
     * @params void
     * @returns String target
     */
    public String getMessage() {
        return this.message;
    }
}