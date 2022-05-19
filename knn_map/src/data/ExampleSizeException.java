package data;

public class ExampleSizeException extends Exception{
    ExampleSizeException(String messaggio_di_errore){
        super(messaggio_di_errore);
    }
    ExampleSizeException(){
        super();
    }
}
