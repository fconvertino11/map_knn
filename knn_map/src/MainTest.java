import data.*;
import mining.KNN;

import java.io.FileNotFoundException;

public class MainTest {
	/**
	 * Classe main in cui effettuare il testing
	 * @param args Eventuali argomenti che il metodo prende in ingresso
	 **/
	public static void main(String[] args) {
		try {
			Data trainingSet= new Data("knn_map/src/simple.dat");
			KNN knn=new KNN(trainingSet);

			Example e=new Example(0);
			e.add("A",0);
			e.add("B",1);

			try{System.out.println("Prediction with K=1-> "+knn.predict(e, 1));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}

			try{System.out.println("Prediction with K=2-> "+knn.predict(e, 2));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}

			try{System.out.println("Prediction with K=3-> "+knn.predict(e, 3));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}

			try{System.out.println("Prediction with K=4-> "+knn.predict(e, 4));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}

			e=new Example(0);

			e.add("A",0);
			e.add("B",1);

			// read example withKeyboard
			try{
				System.out.println("Custom prediction-> " + knn.predict());
			}
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}
		}
		catch(TrainingDataException exc){
			System.out.println(exc.getMessage());
		}
		catch(FileNotFoundException f){
			System.out.println("File non trovato");
		}
	}
}

