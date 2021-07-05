public class HelloGoodbye {
    public static void main(String[] args) {
        String nameOne = args[0];
        String nameTwo = args[1];
        System.out.println(String.format("Hello %s and %s.", nameOne, nameTwo));
        System.out.println(String.format("Goodbye %s and %s.", nameTwo, nameOne));
    }
}