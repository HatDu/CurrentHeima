package console;

import java.util.Scanner;

public class Test1_Scanner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println(input.nextInt());
        System.out.println(input.nextFloat());
        System.out.println(input.next());
        System.out.println(input.hasNext());
        System.out.println(input.hasNextLine());
    }
}
