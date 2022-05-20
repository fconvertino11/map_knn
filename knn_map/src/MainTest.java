import data.Data;
import data.Example;
import data.ExampleSizeException;
import data.TrainingDataException;
import mining.KNN;

import java.io.FileNotFoundException;

public class MainTest {

	/**
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException{
		try {
			Data trainingSet= new Data("simple.dat");
			System.out.println(trainingSet);
			
			KNN knn=new KNN(trainingSet);
			
			Example e=new Example(2);
			e.set("A",0);
			e.set("B",1);
			try{System.out.println("Prediction with K=1:"+knn.predict(e, 1));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}
	
			try{System.out.println("Prediction with K=2:"+knn.predict(e, 2));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}
			
			try{System.out.println("Prediction with K=3:"+knn.predict(e, 3));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}
			
			try{System.out.println("Prediction with K=4:"+knn.predict(e, 4));
			} 
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}

			e=new Example(2);
			e.set("A",0);
			e.set("B",1);

			System.out.println("Prediction with K=1:"+knn.predict(e, 2));


			// read example withKeyboard
			try{System.out.println("Prediction with K=4:"+knn.predict());
			}
			catch (ExampleSizeException exc) {System.out.println(exc.getMessage());		}


		}
		catch(TrainingDataException exc){
			System.out.println(exc.getMessage());
		}
		catch(FileNotFoundException f){
			System.out.println("File non trovato");
		}
		catch (ExampleSizeException exc) {
			System.out.println(exc.getMessage());
		}
	}

}
