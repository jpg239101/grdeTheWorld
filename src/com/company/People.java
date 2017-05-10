package com.company;
import java.util.ArrayList;

/**
 * Created by Jacob on 5/7/2017.
 */
public class People implements Comparable<People> {
    public int num;
    public String name = "";

    public ArrayList<Double> gradesQ1 = new ArrayList<>();
    public ArrayList<Double> gradesQ2 = new ArrayList<>();

    public People(int n) {
        num = n;
    }

    public void assignName(String s) {
        name = s;
    }

    public int compareTo(People s) {
        if(this.name.compareTo(s.name) > 0)
            return 1;
        else if(this.name.compareTo(s.name) < 0)
            return -1;
        else
            return 0;
    }

    public double avgQ1() {
        double sum = 0;
        for(double d : gradesQ1)
            sum += d;
        return sum / gradesQ1.size();
    }

    public double avgQ2() {
        double sum = 0;
        for(double d : gradesQ2)
            sum += d;
        return sum / gradesQ2.size();
    }

    public double avgGrade() {
        return avgQ1() + avgQ2();
    }
}