package smoothcurvega;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Reem, Bassant, Mahmoud
 */
public class SmoothCurveGA {

    static int populationSize = 8;
    static ArrayList<chromosome> population = new ArrayList<chromosome>();
    static double pc = 0.7;
    static double pm = 0.1;

    public static void intializePopulation(int d) {
        for (int i = 0; i < populationSize; i++) {
            chromosome temp = new chromosome(d);
            population.add(temp);
        }
    }

    public static double randomNumber(double min, double max) {
        return (double) Math.random() * (max - min) + min;
    }

    public static void printPopulation(ArrayList<chromosome> p) {

        for (int i = 0; i < p.size(); i++) {
            p.get(i).tooString(i);
        }
    }

    public static void calculateFitness(ArrayList<chromosome> chromosomes, ArrayList<point> points) {
        for (int i = 0; i < chromosomes.size(); i++) {
            chromosome currentChromo = chromosomes.get(i);
            double Sum = 0;
            for (int j = 0; j < points.size(); j++) {
                point currentPoint = points.get(j);
                double Error = 0;
                for (int k = 0; k < currentChromo.name.length; k++) {
                    Error += currentChromo.name[k] * (pow(currentPoint.getX(), k));
                }
                Error = Error - currentPoint.getY();
                Error = pow(Error, 2);
                Sum += Error;
            }
            Sum = Sum / points.size();
            // Sum=1/Sum;
            currentChromo.setFitnessValue(Sum);
        }
    }

    public static ArrayList<chromosome> tournamentSelection(int range) {
        ArrayList<chromosome> selected = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            Random r = new Random();
            int index1 = r.nextInt(((population.size() - 1) - 0) + 1) + 0;
            r = new Random();
            int index2 = r.nextInt(((population.size() - 1) - 0) + 1) + 0;
            while (index1 == index2) {
                index2 = r.nextInt(((population.size() - 1) - 0) + 1) + 0;
            }

            if (population.get(index2).getFitnessValue() >= population.get(index1).getFitnessValue()) // bnakhod el error el so8yar
            {
                selected.add(population.get(index1));
            } else {
                selected.add(population.get(index2));
            }
        }

        return selected;
    }

    public static ArrayList<chromosome> crossOver(ArrayList<chromosome> selected) //2 point crossOver
    {
        ArrayList<chromosome> offsprings = new ArrayList<>();
        for (int i = 0; i < selected.size(); i += 2) {
            chromosome offSpring1 = new chromosome();
            chromosome offSpring2 = new chromosome();

            offSpring1.copy(selected.get(i));
            offSpring2.copy(selected.get(i + 1));
            Random r = new Random();
            int crossOver1 = r.nextInt((selected.get(0).name.length - 1 - 0) + 1) + 0;
            //range from 0 --> chromosome
            r = new Random();
            int crossOver2 = r.nextInt((selected.get(0).name.length - 1 - 0) + 1) + 0;
            while (crossOver1 == crossOver2)//to make sure that they are not equal
            {
                crossOver2 = r.nextInt((population.get(0).name.length - 1 - 0) + 1) + 0;
            }
            if (crossOver2 < crossOver1) // make crossOver1 the smaller number
            {
                int tmp = crossOver2;
                crossOver2 = crossOver1;
                crossOver1 = tmp;
            }

            double r2 = randomNumber(0, 1);
            if (r2 <= pc)//preform cross over
            {
                for (int j = crossOver1; j < crossOver2; j++) {
                    double temp = offSpring1.name[j];
                    offSpring1.name[j] = offSpring2.name[j];
                    offSpring2.name[j] = temp;

                }
            }

            offsprings.add(offSpring1);
            offsprings.add(offSpring2);

        }
        return offsprings;
    }

    public static void nonUniformMutation(int currentGeneration, int maxGenerations, ArrayList<chromosome> offsprings) {
        double y;
        double delta;
        int upperBound = 10;
        int lowerBound = -10;
        for (int i = 0; i < offsprings.size(); i++) {

            chromosome currentChromo = offsprings.get(i);
            for (int j = 0; j < currentChromo.name.length; j++) {
                double x = randomNumber(0, 1);
                if (x <= pm) //do mutation
                {
                    double lb = currentChromo.name[j] - (lowerBound);
                    double ub = upperBound - currentChromo.name[j];
                    double r1 = randomNumber(0, 1);
                    boolean check;
                    if (r1 <= 0.5)// take y=lp
                    {
                        y = lb;
                        check = true;
                    } else {
                        y = ub;
                        check = false;
                    }
                    double r = randomNumber(0, 1);
                    double b = randomNumber(0.5, 5);

                    delta = y * (1 - pow(r, pow(1 - (currentGeneration / maxGenerations), b)));
                    if (check) {
                        currentChromo.name[j] -= delta;
                    } else {
                        currentChromo.name[j] += delta;
                    }

                }

            }
        }

    }

    public static void sortBasedOnFitness(ArrayList<chromosome> temp) {
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(i).getFitnessValue() < temp.get(j).getFitnessValue()) {
                    chromosome tmp = temp.get(i);
                    temp.set(i, temp.get(j));
                    temp.set(j, tmp);
                }
            }
        }
    }

    public static void etlisimReplacement(ArrayList<chromosome> offsprings, ArrayList<point> points) {
        ArrayList<chromosome> All = new ArrayList<chromosome>();
        All.addAll(population);
        All.addAll(offsprings);
        calculateFitness(offsprings, points);
        sortBasedOnFitness(All);
        for (int i = 0; i < population.size(); i++) {
            population.set(i, All.get(i));
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileWriter myWriter = new FileWriter("output.txt");

        int maxGenerations = 5;
        File file = new File("curve_fitting_input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int TC = Integer.parseInt(br.readLine());
        for (int t = 0; t < TC; t++) {
            ArrayList<point> points = new ArrayList<point>();
            population = new ArrayList<chromosome>();
            int N = -1;
            int D = -1;
            while ((st = br.readLine()) != null) {

                if (st.isEmpty()) {
                    continue;
                } else {
                    String[] pair = st.split(" ");
                    if (N == -1) {
                        N = Integer.parseInt(pair[0]);
                    } //number of data points
                    if (D == -1) {
                        D = Integer.parseInt(pair[1]);
                    } //degree
                    else {
                        for (int i = 0; i < N; i++) {

                            pair = st.split(" ");
                            double x = Double.parseDouble(pair[0]);
                            double y = Double.parseDouble(pair[1]);
                            point p = new point(x, y);
                            points.add(p);
                            if (i != N - 1) {
                                st = br.readLine();
                            }
                        }
                        break;
                    }
                }
            }
            intializePopulation(D);
            for (int currentGeneration = 0; currentGeneration < maxGenerations; currentGeneration++) {
                calculateFitness(population, points);
                ArrayList<chromosome> selected = new ArrayList<>();
                selected = tournamentSelection(population.size());
                ArrayList<chromosome> offsprings = new ArrayList<>();
                offsprings = crossOver(selected);
                nonUniformMutation(currentGeneration, maxGenerations, offsprings);
                etlisimReplacement(offsprings, points);
            }

            String tmp = population.get(0).tooString(t);

            myWriter.write(tmp);
            myWriter.write("\n");

        }
        myWriter.close();
    }

}
