package mining;

import data.Data;
import data.Example;
import data.ExampleSizeException;
import data.TrainingDataException;
import utility.Keyboard;

/**
 * Questa classe si occupa di acquisire il data set
 * tramite la classe Data e richiama il metodo che
 * effettivamente fa la previsione su di esso
 */
public class KNN {
    /**
     * Attributo che contiene il training set
     */
     final Data data;

    /**
     * Costruttore di classe
     * @param trainingSet È il training set su cui operare
     */
    public KNN(Data trainingSet){
        data=trainingSet;
    }

    /**
     * Predice il valore target dell’esempio passato come parametro ( fare uso di avgClosest )
     * @param e Dati su cui effettuare la previsione
     * @param k Accuratezza del knn da applicare
     * @return Ritorna il valore calcolato
     * @throws ExampleSizeException è lanciata quando si prova
     *                              a operare con dati di dimensione diversa
     * @throws TrainingDataException è lanciata quando il Training set contiene dati non validi
     */
    public Double predict(Example e, int k) throws ExampleSizeException, TrainingDataException {
        return data.avgClosest(e,k);
    }

    /**
     * Predice il valore target dell’esempio letto da tastiera
     * @return Ritorna il valore calcolato
     * @throws ExampleSizeException è lanciata quando si prova
     *                              a operare con dati di dimensione diversa
     * @throws TrainingDataException è lanciata quando il Training set contiene dati non validi
     */
    public Double predict() throws ExampleSizeException, TrainingDataException {
        Example e=readExample();
        boolean valid=false;
        int k;
        do{
            System.out.println("Con che accuratezza vuoi che venga eseguito l'algoritmo? (inserisci intero)");
            k = Keyboard.readInt();
            if (k > 0)
                valid = true;
        }while(!valid);
        return predict(e,k);
    }

    /**
     * Legge una serie di attributi da tastiera
     * @return Ritorna un oggetto di tipo Example contenente i dati letti
     */
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
