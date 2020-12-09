package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.security.SecureRandom;

public class Main {
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    private static void Menu(String[] args){
        System.out.println("Available moves:");
        for(int i=0;i<args.length;i++)
            System.out.println(i+1+" - "+args[i]);
        System.out.println(0 +" - "+ "Exit");
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        if(args.length<3 || args.length%2==0){
            System.out.println("Wrong input");
            System.out.println("You need to input odd number of unique elements and there amount must be more than 2");
            System.out.println("Examples:\n1) 1 2 3\n2) rock paper scissors lizard Spock");
            return;
        }
        for(int i=0;i<args.length;i++){
            for(int b=i+1;b<args.length;b++){
                if(args[i]==args[b]){
                    System.out.println("Wrong input");
                    System.out.println("You need to input odd number of unique elements and there amount must be more than 2");
                    System.out.println("Examples:\n1) 1 2 3\n2) rock paper scissors lizard Spock");
                    return;
                }
            }
        }
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        StringBuilder key = new StringBuilder();
        for (byte b : bytes) {
            key.append(String.format("%02x", b));
        }
        int computer= (int) (Math.random() * args.length);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String originalString = key.toString()+args[computer];
        byte[] hashbytes = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        String HMAC = bytesToHex(hashbytes);
        System.out.print("HMAC:");
        System.out.println(HMAC);
        Scanner sc = new Scanner(System.in);
        int person;
        do {
            Menu(args);
            System.out.println("Enter your move:");
            while (!sc.hasNextInt()) {
                Menu(args);
                System.out.println("Enter your move:");
                sc.next();
            }
            person = sc.nextInt();
        } while (person < 0 || person>args.length);
        if(person==0)
            return;
        System.out.println("Your move: "+args[person-1]);
        System.out.println("Computer move: "+args[computer]);
        computer++;
        if(person==computer)
            System.out.println("Draw!");
        else if(computer>person) {
            if(computer-person> args.length/2)
                System.out.println("You win!");
            else
                System.out.println("You lose!");
        }
        else{
            if(person-computer>args.length/2)
                System.out.println("You lose!");
            else
                System.out.println("You win!");
        }
        System.out.println(key);
    }
}
