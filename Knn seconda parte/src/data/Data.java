package data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Data {

    // Attributi definiti dallo studente
    // Array di oggetti istanza della classe Example
    Example[] data;
    // Array di valori della variabile target, un valore per ogni esempio (istanza
    // di Example) memorizzato in data
    Double[] target;
    // Numero di esempi memorizzato in data
    int numberOfExamples;
    // array delle variabili indipendenti che definiscono lo schema degli oggetti
    // istanza di Example
    Attribute[] explanatorySet;
    // ? attributo target
    ContinuousAttribute classAttribute;

    // Metodi
    // È il costruttore di classe
    public Data(String fileName) throws FileNotFoundException,TrainingDataException{

        File inFile = new File(fileName);
        Scanner sc = new Scanner(inFile);
        String line = sc.nextLine();
        if (!line.contains("@schema"))
            throw new TrainingDataException("Errore nel training set fornito, non è uno schema");

        String[] s = line.split(" ");

        // popolare explanatory Set
        boolean targetPresente=false;
        boolean trovatoDiscreto=false;
        int attributiTrovati=0;
        Attribute[] explanatorySet;
        {
            int explanatorySetSize = Integer.parseInt(s[1]);
            if (explanatorySetSize < 0)
                throw new TrainingDataException("Errore critico:Questo data set prevede un numero negativo di attributi");
            explanatorySet = new Attribute[explanatorySetSize];
        }
        if(explanatorySet.length==0)
            throw new TrainingDataException("Errore critico:Questo data set non prevede alcun attributo");

        short iAttribute = 0;
        line = sc.nextLine();
        while (!line.contains("@data")) {

            s = line.split(" ");
            if (s[0].equals("@desc")) { // aggiungo l'attributo allo spazio descrittivo
                // @desc motor discrete
                trovatoDiscreto=true;
                attributiTrovati++;
                if(attributiTrovati>explanatorySet.length)
                    throw new TrainingDataException("Errore critico:Questo data set descrive più attributi di quanti ne siano previsti");
                explanatorySet[iAttribute] = new DiscreteAttribute(s[1], iAttribute);
            } else if (s[0].equals("@target")) {
                targetPresente=true;
                classAttribute = new ContinuousAttribute(s[1], iAttribute);
            } else
                throw new TrainingDataException("Errore critico:Attributo n.ro " + (iAttribute+1) + " di tipo non valido");
            iAttribute++;
            line = sc.nextLine();

        }
        if(!targetPresente)
            throw new TrainingDataException("Errore critico:Non è presente un attributo target");
        if(!trovatoDiscreto && true)//TODO sostituire true con trovatoContinuo
            throw new TrainingDataException("Errore critico:Non è presente alcun attributo oltre l'attributo target");
        if(attributiTrovati<explanatorySet.length)
            throw new TrainingDataException("Errore critico:Questo data set descrive meno attributi di quanti ne siano previsti");

        // avvalorare numero di esempi
        numberOfExamples = Integer.parseInt(line.split(" ")[1]);
        if(numberOfExamples==0)
            throw new TrainingDataException("Training set vuoto");
        // popolare data e target
        data = new Example[numberOfExamples];
        target = new Double[numberOfExamples];

        short iRow = 0;
        while (sc.hasNextLine()) {
            Example e = new Example(explanatorySet.length);
            line = sc.nextLine();
            // assumo che attributi siano tutti discreti
            s = line.split(","); // E,E,5,4, 0.28125095
            for (short jColumn = 0; jColumn < s.length - 1; jColumn++)
                e.set(s[jColumn], jColumn);
            if(iRow==numberOfExamples)
                throw new TrainingDataException("Sono stati dichiarati meno dati di quanti ce ne siano");
            data[iRow] = e;
            target[iRow] = Double.valueOf(s[s.length - 1]);
            iRow++;

        }
        if(iRow!=numberOfExamples)
            throw new TrainingDataException("Sono stati dichiarati più dati di quanti ce ne sono");
        sc.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        try{
        Data trainingSet = new Data("servo.dat");
            System.out.println(trainingSet);}
        catch(TrainingDataException e){
            System.out.println("Il programma verrà ora terminato");
        }
    }

    // fornite dal docente per ordinare gli elementi di data, target e key in
    // accordo ai valori contenuti in key

    // Restituisce la lunghezza dell'array explanatorySet[]
    public int getNumberOfExplanatoryAttributes() {
        return explanatorySet.length;
    }

    /*
     * Partiziona data rispetto all'elemento x di key e restituisce il punto di
     * separazione
     */
    private int partition(double[] key, int inf, int sup)throws ExampleSizeException {
        int i, j;

        i = inf;
        j = sup;
        int med = (inf + sup) / 2;

        double x = key[med];

        data[inf].swap(data[med]);

        double temp = target[inf];
        target[inf] = target[med];
        target[med] = temp;

        temp = key[inf];
        key[inf] = key[med];
        key[med] = temp;

        while (true) {

            while (i <= sup && key[i] <= x) {
                i++;

            }

            while (key[j] > x) {
                j--;

            }

            if (i < j) {
                data[i].swap(data[j]);
                temp = target[i];
                target[i] = target[j];
                target[j] = temp;

                temp = key[i];
                key[i] = key[j];
                key[j] = temp;

            } else
                break;
        }
        data[inf].swap(data[j]);

        temp = target[inf];
        target[inf] = target[j];
        target[j] = temp;

        temp = key[inf];
        key[inf] = key[j];
        key[j] = temp;

        return j;

    }

    /*
     * Algoritmo quicksort per l'ordinamento di data
     * usando come relazione d'ordine totale "<=" definita su key
     *
     * @param A
     */
    private void quicksort(double[] key, int inf, int sup) throws ExampleSizeException{

        if (sup >= inf) {

            int pos;

            pos = partition(key, inf, sup);

            if ((pos - inf) < (sup - pos + 1)) {
                quicksort(key, inf, pos - 1);
                quicksort(key, pos + 1, sup);
            } else {
                quicksort(key, pos + 1, sup);
                quicksort(key, inf, pos - 1);
            }

        }

    }

    public double avgClosest(Example e, int k) throws ExampleSizeException{

        double value;
        double[] key = new double[numberOfExamples];
        for (int i = 0; i < numberOfExamples; i++) {    //(1) Avvaloro key[]
            key[i] = e.distance(data[i]);
        }
        quicksort(key, 0, key.length - 1);    //(2) ordino key[], data[] e target[] in accordo con key[]
        double[] minDistances = new double[k];
        inizializzaValoriNegativi(k, minDistances);    //Ci metto -1 in ogni posizione per inizializzare i valori
        for (int i = 0; i < numberOfExamples; i++) {
            if (nonPresente(key[i], minDistances)) {
                inserisciDistanza(key[i], minDistances);//se key[i] non è presente provo ad inserirlo
            }
            //Identifico le k distanze minori
        }
        int counter = 0;//inizializzo a zero il contatore e la somma
        double somma = 0;
        for (int i = 0; i < key.length; i++) {
            if (!nonPresente(key[i], minDistances)) {//Controllo se il valore fa parte di quelli che mi interessano
                counter++;                    //In tal caso aumento il contatore e aggiungo alla somma
                somma += target[i];
            }
        }
        if (counter <= 0)
            value = -1;                        //Se il contatore vale zero o meno vuol dire che c'è stato qualche errore/manomissione
            value = somma / counter;            //calcolo il valore medio
        return value;
    }

    //Inizia i valori di un array di double a -1.0
    private void inizializzaValoriNegativi(int k, double[] array) {
        for (int i = 0; i < k; i++)
            array[i] = -1.0;
    }

    //Verifica che un elemento (value) sia presente in un array di double
    private boolean nonPresente(double value, double[] array) {
        boolean esito = true;
        Double tmp = value;
        for (double v : array) {
            if (tmp.equals(v)) {
                esito = false;
                break;
            }
        }
        return esito;
    }

    //Inserisce un elemento (value) in un array ordinato(crescente) se c'è posto
    void inserisciDistanza(double value, double[] array) {
        boolean trovato = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1.0 || value < array[i]) {
                trovato = true;
                double tmp1 = value;
                double tmp2;
                for (int j = i; j < array.length; j++) {
                    tmp2 = array[j];
                    array[j] = tmp1;
                    tmp1 = tmp2;
                }
            }
            if (trovato)
                break;
        }
    }

    //momentaneo, per testing
    void printArrayOfDouble(double[] array) {
        for (double v : array)
            System.out.println(v);
    }
}
