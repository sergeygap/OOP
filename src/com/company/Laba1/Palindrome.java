package com.company.Laba1;

import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String[] words = str.split(" ");
        boolean var;
        //Циклом foreach перебираем все слова в строке
        for (String word : words) {
            var = isPalindrome(word);//for (int i=0; i<array.length; i++)
//            {// жду об
//
//                Блок операторов;
//            }
        }
    }
    //Метод reverseString создаёт новую строку и заполняет её начиная с последнего символа исходной строки
    public static String reverseString(String s){
        String somestr = "";
        for(int i = s.length() -1; i >= 0; --i){
            somestr += s.charAt(i);
        }
        return somestr;
    }
    //isPalindrome сверяет строки с помощью встроенной функции equals и выводит ответ в зависимости от выполнения условия
    public static boolean isPalindrome(String s){
        if(s.equals(reverseString(s))){
            System.out.println(s+" - Палиндром ");
        }
        else
            System.out.println(s+" - Не палиндром ");

        return s.equals(reverseString(s));
    }
}
//java Palindrome madam racecar apple kayak song noon
