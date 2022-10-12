package org.example;
import java.util.Scanner;

/* Программа, которая определяет, является ли строка палиндромом */
public class Palindrome {
    public static void main(String[] args) {
        System.out.println("Write a line below to check if there are any palindrome words:");
        Scanner sc = new Scanner(System.in);
        args = sc.nextLine().split(" ");
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (isPalindrome(s))
                System.out.println(s + " - is palindrome");
            else
                System.out.println(s + " - is not palindrome");
        }
    }

    /* Метод, переворачивающий строку */
    public static String reverseString(String str) {
        String reversedStr = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversedStr += str.charAt(i);
        }
        return reversedStr;
    }

    /* Метод, сравнивающий первоначальную строку с её перевернутой версией */
    public static boolean isPalindrome(String s) {
        return s.equals(reverseString(s));
    }
}
