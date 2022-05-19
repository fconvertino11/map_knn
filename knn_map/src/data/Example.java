package data;

/**
 * Classe che contiene una serie di attributi continui e/o discreti
 */
public class Example {
    Object[] example;

    /**
     * Costruttore di classe
     * @param size Numero di attributi
     */
    public Example(int size) {
        example = new Object[size];
    }

    /**
     * Setter di un oggetto dell'attributo example
     * @param o oggetto da scrivere in example[index]
     * @param index posizione di example in cui scrivere "o"
     */
    public void set(Object o, int index) {
        example[index] = o;
        return;
    }

    /**
     * Getter di un oggetto dell'attributo example
     * @param index posizione di example in cui leggere l'oggetto
     * @return il valore letto
     */
    Object get(int index) {
        return example[index];
    }

    /**
     * Scambia due oggetti di tipo example
     * @param e oggetto da scambiare con quello che richiama questo metodo
     * @throws ExampleSizeException
     */
    void swap(Example e) throws ExampleSizeException{
        if(e.example.length!=this.example.length)
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
    double distance(Example e)throws ExampleSizeException {
        double value = 0.0;
        if(e.example.length==this.example.length && e.example.length!=0 && this.example.length!=0){
            for(int i=0; i<e.example.length; i++){
                if(e.get(i).equals(this.get(i)));
                else
                    value++;
            }
        }
        else
            throw new ExampleSizeException("I due oggetti confrontati sono di lunghezza diversa");
        return value;
    }
}
