package Core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe qui représente une erreur lancée pendant l'exécution
 */
public class Error {
    /**
     * Date exacte à laquelle l'exception à été lancée
     */
    private Date throwDate;
    /**
     * Exception lancée
     */
    private Exception exception;
    /**
     * Message personnalisée
     */
    private String customMessage;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public Error(Date throwDate, Exception exception, String customMessage) {
        this.throwDate = throwDate;
        this.exception = exception;
        this.customMessage = customMessage;
    }

    public Date getThrowDate() {
        return throwDate;
    }

    public Exception getException() {
        return exception;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("[");
        message.append(this.dateFormat.format(this.throwDate));
        message.append("] : ");
        message.append(this.customMessage);
        return message.toString();
    }
}
