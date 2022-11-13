package smoothcurvega;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Reem
 */
public class SmoothCurveGA {

    static int populationSize = 8;
    static ArrayList<chromosome> population = new ArrayList<chromosome>();
    static ArrayList<chromosome> matingPool = new ArrayList<chromosome>();
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

    public static void calculateFitness(ArrayList<chromosome> chromosomes,ArrayList<point> points) {
        for(int i=0 ; i<chromosomes.size(); i++)
        {  chromosome currentChromo=chromosomes.get(i);
                double Sum=0;
            for (int j = 0; j < points.size(); j++) {
               point currentPoint=points.get(j);
               double Error=0;
                    for (int k = 0; k < currentChromo.name.length; k++) {
                         Error+=currentChromo.name[k]*(pow(currentPoint.getX(),k));
                    }
                    Error=Error-currentPoint.getY();
                    Error=pow(Error,2);
                    Sum+=Error;
            }
            Sum=Sum/points.size();
            // Sum=1/Sum;
            currentChromo.setFitnessValue(Sum);
        }
    }
    
    public static ArrayList<Integer> tournamentSelection()
    {       ArrayList<Integer> result = new ArrayList<>();
            Random r = new Random();
             int index1 = r.nextInt(((population.size()-1) - 0) + 1) + 0;
             r = new Random();
             int index2 = r.nextInt(((population.size()-1) - 0) + 1) + 0;
             while(index1==index2)
             {
             index2 = r.nextInt(((population.size()-1) - 0) + 1) + 0;
             }
             System.out.println(index1 + "  " +  index2);
                  
            if(population.get(index2).getFitnessValue()>=population.get(index1).getFitnessValue())
             // bnakhod el error el so8yar
            {
                matingPool.add(population.get(index1));
            }
            else {matingPool.add(population.get(index2));}
            
            result.add(index1);
            result.add(index2);
            return result;         
    }
    public static void crossOver(ArrayList<Integer> result) //2 point crossOver
    {       //chromosome offSpring1 = new chromosome();
            //chromosome offSpring2 = new chromosome();
             Random r = new Random();
             int crossOver1 =r.nextInt((population.get(0).name.length-1 - 0) + 1) + 0;
                    //range from 0 --> chromosome
             r = new Random();
             int crossOver2 =r.nextInt((population.get(0).name.length-1 - 0) + 1) + 0;
             while(crossOver1==crossOver2)//to make sure that they are not equal
             {
                 crossOver2 =r.nextInt((population.get(0).name.length-1 - 0) + 1) + 0;
             }
             if(crossOver2< crossOver1) // make crossOver1 the smaller number
             { int tmp=crossOver2;
             crossOver2=crossOver1;
             crossOver1=tmp;
             }
            System.out.println(crossOver1 + " " +crossOver2);

            double r2 = randomNumber(0, 1);
               if(r2<=pc)//preform cross over
               {    System.out.println("preform crossOver");
                  for(int i=crossOver1 ; i<crossOver2 ; i++)
                  {
                  double temp = population.get(result.get(0)).name[i];
                  population.get(result.get(0)).name[i]=population.get(result.get(1)).name[i];
                  population.get(result.get(1)).name[i]=temp;
                  }
               }
              // else //khalek f parents
              // {
               
               //}
             
    }
    
     public void nonUniformMutation(int currentGeneration ,int maxGenerations)
     {   double y;
        double delta;
         int upperBound =10;
             int lowerBound=-10;
         for (int i = 0; i < population.size(); i++) {
          
             chromosome currentChromo=population.get(i);
              for(int j=0 ; j <currentChromo.name.length ; j++)
              { double x = randomNumber(0, 1);
                if(x<=pm) //do mutation
                {
                double lb=currentChromo.name[j]-(lowerBound);
                double ub=upperBound-currentChromo.name[j];
                double r1 = randomNumber(0, 1);
               boolean check;
                if(r1<=0.5)// take y=lp
                {
                y=lb;
                check=true;
                }
                
                else { y=ub;
                 check=false;

                }
                double r = randomNumber(0, 1);
                double b = randomNumber(0.5, 5);

                 delta =y*(1-pow(r,pow(1 - (currentGeneration/maxGenerations),b)));
                if(check){
                currentChromo.name[j]-=delta;
                }
                else {
                 currentChromo.name[j]+=delta;
                }
                
                } // end of the do mutation if
                
                // else do not do mutation
              }
         }
          

     }

    public static void main(String[] args) {
        // TODO code application logic here
        int N, D;
       intializePopulation(5);
//       chromosome c = new chromosome(2);
//       c.name[0]=1.95;
//       c.name[1]=8.16;
//       c.name[2]=-2;
//       population.add(c);
//       
//       c = new chromosome(2);
//       c.name[0]=4.26;
//       c.name[1]=-7.4;
//       c.name[2]=-2.5;
//       population.add(c);
//       c = new chromosome(2);
//
//       c.name[0]=3.36;
//       c.name[1]=-0.3;
//       c.name[2]=-6.2;
//       population.add(c);
//       c = new chromosome(2);
//
//       c.name[0]=0.23;
//       c.name[1]=0.12;
//       c.name[2]=4.62;
//       population.add(c);
        ArrayList<point> points = new ArrayList<point>();
        point p = new point(1, 5);
        points.add(p);
        p = new point(2, 8);
        points.add(p);
        p = new point(3, 13);
        points.add(p);
        p = new point(4, 20);
        points.add(p);
        calculateFitness(population,points);
        printPopulation(population);
        crossOver(tournamentSelection());
        System.out.println("after cross over and selection");
        printPopulation(population);

    }

}
