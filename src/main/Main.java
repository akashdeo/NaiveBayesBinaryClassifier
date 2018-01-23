/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Akash-PC
 */
public class Main {
    static String pathToTrainingData;
    static String pathToTestData;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        pathToTrainingData =args[0];
        pathToTestData=args[1];
       BufferedReader br = new BufferedReader(new FileReader(pathToTrainingData));
        ArrayList<Integer> examples = new ArrayList<>();
        String line;
        int i = 0;
        String[] split = null;
        while ((line = br.readLine()) != null) {
            split = line.split("\\s+");
            if(null!=split)
            {
              examples.add(i);
              i++;
            } 
        }
        i=0;
        br.close();
        BufferedReader br2 = new BufferedReader(new FileReader(pathToTrainingData));
        String line3;
        String[][] trainingData=new String[examples.size()][split.length];
        while ((line3 = br2.readLine()) != null) {
            String[] split1 = line3.split("\\s+");
            if(split1!=null)
            {
            for(int u=0;u<split1.length;u++)
            {
                trainingData[i][u]=split1[u];
            }
            i++;
            }
        }
        br2.close();
        int maxLength=split.length-1;
        ArrayList<Integer> examples1 = new ArrayList<>();
        ArrayList<Integer> examples0 = new ArrayList<>();
        for(int j=1;j<trainingData.length;j++)
        {
            if(Integer.parseInt(trainingData[j][maxLength])==1)
            {
                examples1.add(j);
                
            }else
            {
                examples0.add(j);
            }
        }
        double class1p=(examples1.size()*1.0)/(trainingData.length-1);
        double class0p=(examples0.size()*1.0)/(trainingData.length-1);
        System.out.println("Probability of class value =1 is"+class1p);
        System.out.println("Probability of class value =0 is"+class0p);
        int count1=0,count0=0,count11=0,count00=0;
        double probability_values1[][]=new double[split.length-1][2];
        double probability_values0[][]=new double[split.length-1][2];
        
         for(int k=0;k<split.length-1;k++)
            {
        for(Integer j:examples1)
        {
           
            if(Integer.parseInt(trainingData[j][k])==1)
            {
                count1++;
            }
            else
                count0++;
        }
        for(Integer j:examples0)
        {
           
            if(Integer.parseInt(trainingData[j][k])==1)
            {
                count11++;
            }
            else
                count00++;
        }
        probability_values1[k][0]=(count1*1.0)/examples1.size();
        probability_values1[k][1]=(count0*1.0)/examples1.size(); 
        probability_values0[k][0]=(count11*1.0)/examples0.size();
        probability_values0[k][1]=(count00*1.0)/examples0.size();
        count0=0;
        count1=0;
        count00=0;
        count11=0;
        }
        for(int j=0;j<split.length-1;j++)
        {
                System.out.println("P["+trainingData[0][j]+"=1/C=1]= "+probability_values1[j][0]);
                System.out.println("P["+trainingData[0][j]+"=0/C=1]= "+probability_values1[j][1]);
            
        }
        for(int j=0;j<split.length-1;j++)
        {
                System.out.println("P["+trainingData[0][j]+"=1/C=0]= "+probability_values0[j][0]);
                System.out.println("P["+trainingData[0][j]+"=0/C=0]= "+probability_values0[j][1]);
            
        }
        int noOfCorrectlyClassifiedInstances=0;
        for(int j=1;j<trainingData.length;j++)
        {
            double p1=1.0;
            double p2=1.0;
            
            for(int k=0;k<split.length-1;k++)
            {
               
               if("1".equals(trainingData[j][k]))
               {
                   p1=p1*probability_values1[k][0];
                   p2=p2*probability_values0[k][0];
               }
               else
               {
                   p1=p1*probability_values1[k][1];
                   p2=p2*probability_values0[k][1];
               }
            }
            p1=p1*class1p;
            p2=p2*class0p;
            if(p1>p2)
            {
                
                if(Integer.parseInt(trainingData[j][split.length-1])==1)
                     noOfCorrectlyClassifiedInstances++;   
            }
            else
            {
                if(p2>=p1)
            {
                if(Integer.parseInt(trainingData[j][split.length-1])==0)
                    noOfCorrectlyClassifiedInstances++;
            }
            }
        }
        double accuracyOfTrainingData=(noOfCorrectlyClassifiedInstances*1.0)/(examples.size()-1);
        System.out.println("TrainingAccuracy = "+accuracyOfTrainingData*100);
        br = new BufferedReader(new FileReader(pathToTestData));
        ArrayList<Integer> examples11 = new ArrayList<>();
        line="";
        i = 0;
        split = null;
        while ((line = br.readLine()) != null) {
            
            split = line.split("\\s+");
            if(null!=split)
            {
              examples11.add(i);
              i++;
            } 
            
        }
        i=0;
        br.close();
         br2 = new BufferedReader(new FileReader(pathToTestData));
         line3=null;
        String[][] testingData=new String[examples11.size()][split.length];
        
        while ((line3 = br2.readLine()) != null) {
           
            String[] split1 = line3.split("\\s+");
            if(split1!=null)
            {
            for(int u=0;u<split1.length;u++)
            {
                
                testingData[i][u]=split1[u];
            }
            i++;
            }
        }
        br2.close();
        noOfCorrectlyClassifiedInstances=0;
        for(int j=1;j<testingData.length;j++)
        {
            double p1=1.0;
            double p2=1.0;
            
            for(int k=0;k<split.length-1;k++)
            {
               
               if("1".equals(testingData[j][k]))
               {
                   p1=p1*probability_values1[k][0];
                   p2=p2*probability_values0[k][0];
               }
               else
               {
                   p1=p1*probability_values1[k][1];
                   p2=p2*probability_values0[k][1];
               }
            }
            p1=p1*class1p;
            p2=p2*class0p;
            if(p1>p2)
            {
                
                if(Integer.parseInt(testingData[j][split.length-1])==1)
                     noOfCorrectlyClassifiedInstances++;   
            }
            else
            {if(p2>=p1)
            {
                if(Integer.parseInt(testingData[j][split.length-1])==0)
                    noOfCorrectlyClassifiedInstances++;
            }
            }
        }
        double testAccuracy;
        testAccuracy=(noOfCorrectlyClassifiedInstances*1.0)/(testingData.length-1);
        System.out.println("Testing Accuracy = "+testAccuracy*100);
        
    
}
}
