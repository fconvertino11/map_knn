package data;
public class Example {
    Object[] example;

    public Example(int size) {
        example = new Object[size];
    }

    public void set(Object o, int index) {
        example[index] = o;
        return;
    }

    Object get(int index) {
        return example[index];
    }

    void swap(Example e) throws ExampleSizeException{
        if(e.example.length!=this.example.length)
            throw new ExampleSizeException("Non posso scambiare i due array di attributi perché di dimensione diversa");
        Example tmp = new Example(0);
        tmp.example = e.example;
        e.example = this.example;
        this.example = tmp.example;
    }

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
