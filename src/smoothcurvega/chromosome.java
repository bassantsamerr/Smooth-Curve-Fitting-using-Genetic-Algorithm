package smoothcurvega;

import java.util.Random;

public class chromosome {
    	public double name[];
        private double fitnessValue;
    public chromosome() {
        this.fitnessValue = 0;
    }
        
        public chromosome(int D) {
        name = new double[D+1];
         for (int i = 0; i < D+1; i++) {
             Random r = new Random();
             name[i] = -10 + r.nextDouble() * (10 - (-10));
        }
    }

    public double[] getName() {
        return name;
    }

    public void setName(double[] name) {
        this.name = name;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public void tooString(int j) 
    {
        System.out.print("C"+ j + " {name=");
        for(int i=0 ; i<name.length; i++)
        {  System.out.print(String.format("%.5f",name[i])+"  ");
        }
        System.out.println(" fitnessValue= " + String.format("%.5f",fitnessValue)+ '}'); 
    }
        

}
