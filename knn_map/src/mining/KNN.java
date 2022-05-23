package mining;

import data.Data;
import data.Example;
import data.ExampleSizeException;
import utility.Keyboard;

public class KNN {
    //Modella il miner
    //Attributi
    //Modella il training set
     final Data data;
    //Metodi
    //Avvalora il training set
    public KNN(Data trainingSet){
        data=trainingSet;
    }
    //Predice il valore target dell’esempio passato come parametro (fare uso di avgClosest )
    public Double predict(Example e, int k)throws ExampleSizeException {
        return data.avgClosest(e,k);
    }

    public Double predict() throws ExampleSizeException{
        Example e=readExample();
        System.out.println("Con che accuratezza vuoi che venga eseguito l'algoritmo? (inserisci intero)");
        int k=Keyboard.readInt();
        return data.avgClosest(e,k);
    }
    public Example readExample(){
        Example e=new Example(0);
        System.out.println("Quanti attributi conterrà la predizione?");
        int predictionSize= Keyboard.readInt();
        for(int i=0; i<predictionSize;i++){
            System.out.println("Inserisci i dati relativi all'attributo numero "+ (i+1));
            System.out.println(" Nome: ");
            e.add(Keyboard.readString(),i);
        }
        return e;
    }
}
