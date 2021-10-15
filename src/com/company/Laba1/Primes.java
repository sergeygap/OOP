package com.company.Laba1;

public class Primes {
    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++){
            if (isPrime(i)) System.out.println(i);
        }
    }
    public static boolean isPrime(int n)// принемает значение "i" из main и проверяет простое число или нет
    {                                   //если нет, то прнемает булево значени false, если да, то true
        for (int i = 2; i < n; i++) {
            if ((n % i) == 0) return false; // когда метод принемает значение false or true, то сразу пркаращает свою работу for на одной итерации
        }
        return true;

    }

}

