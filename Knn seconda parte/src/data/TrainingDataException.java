package data;

public class TrainingDataException extends Exception {
    TrainingDataException(String errore_di_lettura) {
        super(errore_di_lettura);
    }
    TrainingDataException(){super();}
}
