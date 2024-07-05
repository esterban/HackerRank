package hackerrank.java.primechecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.in;

class Prime {
    private Set<Integer> primesFound = new HashSet<>();

    public void checkPrime(int... args) {
        List<String> primesList = new ArrayList<>();

        for (int arg : args) {
            boolean isPrime = true;

            if (arg > 1) {
                for (int counter = 2; counter < arg; ++counter) {
                    if ((double)arg / (double)counter == arg / counter) {
                        isPrime = false;
                        break;
                    }
                }

                if (isPrime) {
                    primesList.add(String.valueOf(arg));
                }

            }
        }

        System.out.println(String.join(" " , primesList));
    }
}


public class Solution {

    public static void main(String[] args) {
        try {
//            System.in

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int n1 = Integer.parseInt(br.readLine());
            int n2 = Integer.parseInt(br.readLine());
            int n3 = Integer.parseInt(br.readLine());
            int n4 = Integer.parseInt(br.readLine());
            int n5 = Integer.parseInt(br.readLine());
            Prime ob = new Prime();
            ob.checkPrime(n1);
            ob.checkPrime(n1, n2);
            ob.checkPrime(n1, n2, n3);
            ob.checkPrime(n1, n2, n3, n4, n5);
            Method[] methods = Prime.class.getDeclaredMethods();
            Set<String> set = new HashSet<>();
            boolean overload = false;
            for (int i = 0; i < methods.length; i++) {
                if (set.contains(methods[i].getName())) {
                    overload = true;
                    break;
                }
                set.add(methods[i].getName());

            }
            if (overload) {
                throw new Exception("Overloading not allowed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

