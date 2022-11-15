package smoothcurvega;

import java.util.Random;
/**
 * @author Reem, Bassant, Mahmoud
 */
public class chromosome {
    	public double name[];
        private double fitnessValue;
    public chromosome() {
        this.fitnessValue = 0;
    }
        public void copy(chromosome c)
        {
            this.name = new double[c.name.length];
            for(int i=0 ; i< c.name.length; i++)
            {
                this.name[i]=c.name[i];
            }
            this.fitnessValue =c.fitnessValue;
        }
        public chromosome(int D) {
        name = new double[D+1];
         for (int i = 0; i < D+1; i++) {
             Random r = new Random();
             name[i] = -10 + r.nextDouble() * (10 - (-10));
        }
    }

    public chromosome(double[] name) {
        this.name = name;
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

    public String tooString(int j)
    {   String S="";
        S+="TC";
        S+= j+1;
        S+= " {name=";
        for(int i=0 ; i<name.length; i++)
        { S+=String.format("%.5f",name[i])+"  ";
        }
        S+=" fitnessValue= " + String.format("%.5f",fitnessValue)+ '}'; 
    return S;
    }

   
        

}
