package data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
/**
 * Classe che si occupa della gestione e delle operazioni sui dati.
 */
public class Data {
    /**
     * Array di oggetti istanza della classe Example.
     */
    ArrayList<Example> data;
    /**
     * Array di valori della variabile target, un valore per ogni esempio (istanza
     * di Example) memorizzato in data.
     */
    ArrayList<Double> target;
    /**
     * Numero di esempi memorizzato in data.
     */
    int numberOfExamples;
    /**
     * Array delle variabili indipendenti che definiscono lo schema degli oggetti
     * istanza di Example.
     */
    ArrayList<Attribute> explanatorySet;
    /**
     * Attributo target.
     */
    ContinuousAttribute classAttribute;

    /**
     * È il costruttore di classe.
     * @param fileName  Nome del file contenente il training set
     * @throws FileNotFoundException è lanciata quando il file non viene trovato
     * @throws TrainingDataException può essere lanciata a causa di problemi vari del
     *                               training set
     */
    public Data(String fileName) throws FileNotFoundException,TrainingDataException {
        File inFile = new File(fileName);
        Scanner sc = new Scanner(inFile);
        String line = sc.nextLine();
        if (!line.contains("@schema")) //Mi assicuro che il file inizi con "@schema"
            throw new TrainingDataException("Errore nel training set fornito, non è uno schema");
         //Divido la prima riga in parola
        // la prima sarà "@schema" e la seconda il numero di attributi
        String[] s = line.split(" ");

        // popolare explanatory Set
        boolean targetPresente = false;     //Controllo eccezioni
        boolean trovatoDiscreto = false;    //Controllo eccezioni
        int attributiTrovati = 0;           //Controllo eccezioni
        final int explanatorySetSize = Integer.parseInt(s[1]);  //la seconda parola sarà il numero di attributi
        if (explanatorySetSize < 0) //se il numero è negativo lancio un'eccezione, deve essere maggiore di zero
            throw new TrainingDataException("Errore critico:Questo data set prevede un numero negativo di attributi");
        explanatorySet = new ArrayList<>();
        if(explanatorySetSize==0)   //se il numero è zero lancio un eccezione, il dataset non può essere vuoto
            throw new TrainingDataException("Errore critico:Questo data set non prevede alcun attributo");

        //Leggo i tipi di attributi
        short iAttribute = 0;
        line = sc.nextLine(); //Leggo la prossima riga
        while (!line.contains("@data")) {
            s = line.split(" ");    //leggo il tipo di attributo (@desc/@target)
            if (s[0].equals("@desc")) {   // aggiungo l'attributo allo spazio descrittivo
                // @desc motor discrete
                trovatoDiscreto = true; //Gestione eccezioni
                attributiTrovati++;     //Gestione eccezioni
                if(attributiTrovati > explanatorySetSize)
                    throw new TrainingDataException("Errore critico:Questo data set descrive più attributi di quanti ne siano previsti");
                explanatorySet.add(new DiscreteAttribute(s[1], iAttribute));    //Aggiungo l'attributo all'explanatory set
            } else if (s[0].equals("@target")) {
                targetPresente = true;  //Gestione eccezioni
                classAttribute = new ContinuousAttribute(s[1], iAttribute);
            } else
                throw new TrainingDataException("Errore critico:Attributo n.ro " + (iAttribute+1) + " di tipo non valido");
            iAttribute++;
            line = sc.nextLine();

        }
        if(!targetPresente)
            throw new TrainingDataException("Errore critico:Non è presente un attributo target");
        if(!trovatoDiscreto && true)//TODO sostituire true con trovatoContinuo (da inserire)
            throw new TrainingDataException("Errore critico:Non è presente alcun attributo oltre l'attributo target");
        if(attributiTrovati<getExpSetSize())
            throw new TrainingDataException("Errore critico:Questo data set descrive meno attributi di quanti ne siano previsti");

        // avvalorare numero di esempi
        numberOfExamples = Integer.parseInt(line.split(" ")[1]);
        if(numberOfExamples==0)
            throw new TrainingDataException("Training set vuoto");
        // popolare data e target
        data = new ArrayList<>(0);
        target = new ArrayList<>(0);
        short iRow = 0;
        while (sc.hasNextLine()) {
            Example e = new Example(0);
            line = sc.nextLine();
            // assumo che attributi siano tutti discreti
            s = line.split(","); // E,E,5,4, 0.28125095
            for (short jColumn = 0; jColumn < s.length - 1; jColumn++) {
                e.add(s[jColumn], jColumn);
            }
            if(iRow==numberOfExamples)
                throw new TrainingDataException("Sono stati dichiarati meno dati di quanti ce ne siano");
            data.add(e);
            target.add(Double.valueOf(s[s.length - 1]));
            iRow++;
        }
        if(iRow!=numberOfExamples)
            throw new TrainingDataException("Sono stati dichiarati più dati di quanti ce ne sono");
        sc.close();

    }

    /**
     * Metodo main della classe
     * @param args  Argomenti eventuali
     * @throws FileNotFoundException può essere lanciata da Data()
     */
    public static void main(String[] args) throws FileNotFoundException {
        try{
        Data trainingSet = new Data("simple.dat");
            System.out.println(trainingSet);}
        catch(TrainingDataException e){
            System.out.println("Il programma verrà ora terminato");
        }
    }

    // Restituisce la lunghezza dell'array explanatorySet[]

    /**
     * Restituisce la lunghezza dell'array explanatorySet[]
     * @return Restituisce la lunghezza calcolata
     */
    public int getExpSetSize() {
        return explanatorySet.size();
    }

    /**
     * Partiziona data rispetto all'elemento x di key e restituisce il punto di separazione
     * @param key Array da partizionare
     * @param inf punto di partenza della partizione
     * @param sup punto di fine della partizione
     * @return punto di separazione
     * @throws ExampleSizeException lanciata dallo swap
     */
    private int partition(ArrayList<Double> key, int inf, int sup)throws ExampleSizeException {
        int i, j;

        i = inf;
        j = sup;
        int med = (inf + sup) / 2;

        double x = key.get(med);

        data.get(inf).swap(data.get(med));

        double temp = target.get(inf);
        target.set(inf, target.get(med));
        target.set(med, temp);

        temp = key.get(inf);
        key.set(inf, key.get(med));
        key.set(med, temp);

        while (true) {

            while (i <= sup && key.get(i) <= x) {
                i++;

            }

            while (key.get(j) > x) {
                j--;
            }

            if (i < j) {
                data.get(i).swap(data.get(j));
                temp = target.get(i);
                target.set(i, target.get(j));
                target.set(j, temp);

                temp = key.get(i);
                key.set(i, key.get(j));
                key.set(j, temp);

            } else
                break;
        }
        data.get(inf).swap(data.get(j));

        temp = target.get(inf);
        target.set(inf, target.get(j));
        target.set(j, temp);

        temp = key.get(inf);
        key.set(inf, key.get(j));
        key.set(j, temp);

        return j;

    }

    /**
     * Algoritmo quicksort per l'ordinamento di data
     * usando come relazione d'ordine totale "<=" definita su key
     * @param key Array da ordinare
     * @param inf posizione di partenza
     * @param sup posizione finale
     * @throws ExampleSizeException lanciata dallo swap() di example
     */
    private void quicksort(ArrayList<Double> key, int inf, int sup) throws ExampleSizeException{

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

    /**
     * Calcola il valore medio più vicino agli attributi passati
     * @param e Valore Example su cui effettuare la predizione
     * @param k Accuratezza dell'algoritmo
     * @return il valore calcolato
     * @throws ExampleSizeException lanciata da distance()
     */
    public double avgClosest(Example e, int k) throws ExampleSizeException, TrainingDataException {
        double value;
        ArrayList<Double> key = new ArrayList<>();
        for (int i = 0; i < numberOfExamples; i++) {    //(1) Avvaloro key[]
            key.add(i, e.distance(data.get(i)));
        }
        quicksort(key, 0, key.size() - 1);    //(2) ordino key[], data[] e target[] in accordo con key[]
        double[] minDistances = new double[k];
        inizializzaValoriNegativi(minDistances);
        //Ci metto -1 in ogni posizione per inizializzare i valori
        Iterator<Double> keyIter= key.iterator();   //Creo un Iterator<Double> collegato a key
        while (keyIter.hasNext()) {
            Double valoreDaInserire= keyIter.next();
            //Leggo il prossimo elemento e lo salvo in una variabile temporanea
            //(posso leggerlo solo una volta ma lo uso due volte)
            if (nonPresente(valoreDaInserire, minDistances)) {
                inserisciDistanza(valoreDaInserire, minDistances);//se key[i] non è presente provo a inserirlo
            }
            //Identifico le k distanze minori
        }
        int counter = 0;//inizializzo a zero il contatore e la somma
        double somma = 0;

        keyIter= key.iterator(); //Azzero l'iterator
        Iterator<Double> targetIter= target.iterator(); //ne creo un altro per ciclare attraverso target
        Double targetTmp;
        while (keyIter.hasNext()) {
            targetTmp= targetIter.next();
            if (!nonPresente(keyIter.next(), minDistances)) {//Controllo se il valore fa parte di quelli che mi interessano
                counter++;                    //In tal caso aumento il contatore e aggiungo alla somma
                somma += targetTmp;
            }
        }
        if (counter <= 0)
            throw new TrainingDataException("Per qualche ragione sono stati individuati 0 elementi validi");                       //Se il contatore vale zero o meno vuol dire che c'è stato qualche errore/manomissione
        value = somma / counter;            //calcolo il valore medio
        return value;
    }
    /**
     * Inizia i valori di un array di double a -1.0
     * @param array Array da inizializzare a -1.0
     */
    private void inizializzaValoriNegativi(double[] array) {
        Arrays.fill(array, -1.0);
    }
    /**
     * Verifica che un elemento (value) sia presente in un array di double
     */
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

    /**
     * Prova a inserire un elemento (value) in un array ordinato(crescente) se c'è posto
     * @param value Valore da provare a inserire in array
     * @param array Array in cui provare a inserire la distanza
     */
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

}
