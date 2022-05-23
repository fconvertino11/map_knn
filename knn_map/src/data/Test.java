package data;

public class Test {
    static void test(String s){
        System.out.println("\033[42m" + "\033[1;30m" + s + "\033[0m");
    }
}
