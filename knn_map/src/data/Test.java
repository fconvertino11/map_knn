package data;

/**
 * Classe a scopo di testing
 */
public class Test {
    /**
     * Stampa una stringa colorata di verde
     * @param s stringa da stampare
     */
    static void test(String s){
        System.out.println("\033[42m" + "\033[1;30m" + s + "\033[0m");
    }
}
