package data;

import java.util.ArrayList;

/**
 * Classe che contiene una serie di attributi continui e/o discreti
 */
public class Example {
    /**
     * Array contenente i dati degli attributi
     */
    ArrayList<Object> example;

    /**
     * Costruttore di classe
     * @param size Numero di attributi
     */
    public Example(int size) {
        example = new ArrayList<>(size);
    }

    /**
     * Setter di un oggetto dell'attributo example
     * @param o oggetto da scrivere in example[index]
     * @param index posizione di example in cui scrivere "o"
     */
    public void set(Object o, int index) {
        example.set(index, o);
    }

    /**
     * Aggiunge un nuovo elemento all'array
     * @param o elemento da aggiungere
     * @param index posizione in cui aggiungerlo
     */
    public void add(Object o, int index) {
        example.add(index, o);
    }
    /**
     * Getter di un oggetto dell'attributo example
     * @param index posizione di example in cui leggere l'oggetto
     * @return il valore letto
     */
    Object get(int index) {
        return example.get(index);
    }

    /**
     * Scambia due oggetti di tipo example
     * @param e oggetto da scambiare con quello che richiama questo metodo
     * @throws ExampleSizeException Viene lanciata se i due Example sono di dimensione diversa
     */
    void swap(Example e) throws ExampleSizeException{
        if(e.length()!=this.length())
            throw new ExampleSizeException("Non posso scambiare i due array di attributi perché di dimensione diversa");
        Example tmp = new Example(0);

        tmp.example = e.example;
        e.example = this.example;
        this.example = tmp.example;
    }

    /**
     * Calcola la distanza tra due oggetti di tipo example
     * @param e oggetto da cui calcolare la distanza
     * @return distanza calcolata
     * @throws ExampleSizeException lanciata se i due oggetti confrontati hanno
     *                              lunghezze diverse o uno dei due è di
     *                              lunghezza 0
     */
    double distance (Example e) throws ExampleSizeException {
        double value = 0.0;
        if (e.length() == this.length() && e.length() != 0 && this.length() != 0) {
            for (int i = 0; i < e.length(); i++) {
                if (!e.get(i).equals(this.get(i))) {
                    value++;
                }
            }
        } else {
            throw new ExampleSizeException("I due oggetti confrontati sono di lunghezza diversa");
        }
        return value;
    }

    int length(){
        return example.size();
    }
}
