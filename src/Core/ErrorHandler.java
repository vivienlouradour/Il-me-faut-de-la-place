package Core;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Singleton
 * Gestion du stockage des erreurs
 * Permet d'avoir une liste des erreurs lancée pour les affichers dans l'IHM
 * Observable : notity quand une erreur est ajoutée (méthode addError)
 */
public class ErrorHandler extends Observable{
    private static ErrorHandler instance = new ErrorHandler();
    private ArrayList<Error> errorsCollection;

    private ErrorHandler(){
        errorsCollection = new ArrayList<>();
    }

    public static ErrorHandler getInstance() {
        return instance;
    }

    public void addError(Error error){
        notifyObservers();
        errorsCollection.add(error);
    }

    public ArrayList<Error> getErrorsCollection(){
        return this.errorsCollection;
    }
}


