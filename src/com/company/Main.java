package com.company;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;


public class Main {

    public static ArrayList<People> students = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        nf.setMinimumFractionDigits(1);
        Scanner sc = new Scanner(new File("rawDataU6.txt"));
        sc.useDelimiter("\t");
        while(sc.hasNextLine()) { //This is the loop to take the raw Data in
            int secretNum;
            try {
                secretNum = sc.nextInt();
            }
            catch(Exception e) {
                sc.nextLine();
                continue;
            }
            double q1 = 0;
            q1 += findPoints(sc.next());
            q1 += findPoints(sc.next());
            try {
                q1 -= sc.nextInt() * 0.25;
            }
            catch(InputMismatchException e) {

            }
            sc.next(); //skip comments
            double q2 = 0;
            q2 += findPoints(sc.next());
            q2 += findPoints(sc.next());
            q2 += findPoints(sc.next());
            try {
                q2 -= sc.nextInt() * 0.25;
            }
            catch(InputMismatchException e) {

            }
            scoreSum(secretNum, q1, q2);
            sc.nextLine();
        }
        Scanner sc2 = new Scanner(new File("names.txt")); // borrowed tommy s' sample names because they are hilarious
        sc2.useDelimiter("\t|\r\n|\r|\n");

        while(sc2.hasNextLine()) { //the names file is being taken in
            String name = sc2.next();
            int num = sc2.nextInt();
            for(People s : students) {
                if(num == s.num) {
                    s.assignName(name);
                    break;
                }
            }
            if(sc2.hasNextLine())
                sc2.nextLine();
        }
        Collections.sort(students); //sorts the people list by name alphabeticly with the Comparable implememtation
        int maxTabs = 1;
        for(People s : students) { //this for loop accounts for spacing with differnt length entries
            if(s.name.length() / 4 > maxTabs)
                maxTabs = s.name.length() / 4;
        }
        int maxGraders = 0;
        for(People s : students) {
            if(s.gradesQ1.size() > maxGraders)
                maxGraders = s.gradesQ1.size();
        }
        System.out.print("Name" + addTabSpacing(maxTabs) + "Secret Number\tAvg Total \t\tAvg FR Q1 \t\tAvg FR Q2\t ");
        for(int i = 1; i <= maxGraders; i++) {
            System.out.print("\tScorer " + i + " Total\tScorer " + i + " Q1\tScorer " + i + " Q2");
        }
        System.out.println("\t Graders Disagree");
        for(People s : students) {
            System.out.print(s.name + addTabSpacing(maxTabs + 1 - s.name.length() / 4) + s.num + "\t\t\t\t" + nf.format(s.avgGrade()) + "/19" + addTabSpacing(4 - (nf.format(s.avgGrade()).length() + 3) / 4) + "" + nf.format(s.avgQ1()) + "/7" + addTabSpacing(4 - (nf.format(s.avgQ1()).length() + 2) / 4) + "" + nf.format(s.avgQ2()) + "/12" + addTabSpacing(4 - (nf.format(s.avgQ2()).length() + 3) / 4));
            for(int i = 1; i <= s.gradesQ1.size(); i++) {
                System.out.print(s.gradesQ1.get(i - 1) + s.gradesQ2.get(i - 1) + "/19" + addTabSpacing(4 - (nf.format(s.gradesQ1.get(i - 1) + s.gradesQ2.get(i - 1)).length() + 3) / 4) + s.gradesQ1.get(i - 1) + "/7" + addTabSpacing(3 - (nf.format(s.gradesQ1.get(i - 1)).length() + 2) / 4) + s.gradesQ2.get(i - 1) + "/12" + addTabSpacing(3 - (nf.format(s.gradesQ2.get(i - 1)).length() + 3) / 4));
                if (i == 2 && (Math.abs((s.gradesQ1.get(i - 2) + s.gradesQ2.get(i - 2))- (s.gradesQ1.get(i - 1) + s.gradesQ2.get(i - 1))) >=  4)){
                 System.out.print("  True");
                }
            }
            System.out.println("");
        }
    }


    public static String addTabSpacing(int i) { // this method makes spacing for a pleasing output
        String tab = "";
        for(int j = 0; j < i; j++) {
            tab += "\t";
        }
        return tab;
    }


    public static double findPoints(String s) {
        if(s.equals(""))
            return 0;
        String[] points = s.split(", ");
        double score = 0;
        for(String t : points) {
            Scanner find = new Scanner(t);
            find.next(); //skip "+"
            try {
                score += Double.parseDouble(find.next());
            }
            catch(Exception e) {

            }
        }
        return score;
    }


    public static void scoreSum(int num, double q1, double q2) {
        for(People s : students) {
            if(num == s.num) {
                s.gradesQ1.add(q1);
                s.gradesQ2.add(q2);
                return;
            }
        }
        students.add(new People(num));
        students.get(students.size() - 1).gradesQ1.add(q1);
        students.get(students.size() - 1).gradesQ2.add(q2);
    }
}
