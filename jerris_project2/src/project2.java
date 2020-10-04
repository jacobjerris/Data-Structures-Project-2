/*
Jacob Jerris
N01419995
9/30/20

COP 3530: Project 2 - Stacks and Priority Queues
 */



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class project2 {


    public static void main(String[] args) throws IOException {
        Scanner csvFileName = new Scanner(System.in);

        String line = "";


        //Asks user for CSV file name, searches then loads the entire file
        System.out.print("Please enter the CSV file name: ");
        String csvFile = csvFileName.next();

        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                total++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\nTotal records read: " + (total - 1) + "\n\n");

        //Creates an array of countries, but subtracts one to skip the line at the top.
        Country[] countries = new Country[total - 1];
        Stack s1 = new Stack(countries.length);


        int excellentCounter = 0;
        int veryGoodCounter = 0;
        int goodCounter = 0;
        int fairCounter = 0;
        int poorCounter = 0;


        int indx = 0;
        try (BufferedReader br2 = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br2.readLine()) != null) {
                if (indx > 0) {
                    countries[(indx - 1)] = new Country(line.split(","));
                }
                indx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        };


        for(int i =0; i < countries.length; i++) {
            if(countries[i].getCFR() < 1) {
                excellentCounter++;
            } else if((countries[i].getCFR() > 1) && (countries[i].getCFR() < 2)) {
                veryGoodCounter++;
            } else if((countries[i].getCFR() > 2) && (countries[i].getCFR() < 5)) {
                goodCounter++;
            } else if ((countries[i].getCFR() > 5) && (countries[i].getCFR() < 10)) {
                fairCounter++;
            } else {
                poorCounter++;
            }
        }

        Priority excellentQueue = new Priority(excellentCounter);
        Priority veryGoodQueue = new Priority(veryGoodCounter);
        Priority goodQueue = new Priority(goodCounter);
        Priority fairQueue = new Priority(fairCounter);
        Priority poorQueue = new Priority(poorCounter);

        for(int i =0; i < countries.length; i++) {
            if(countries[i].getCFR() < 1) {
                excellentQueue.insert(countries[i]);
            } else if((countries[i].getCFR() > 1) && (countries[i].getCFR() < 2)) {
                veryGoodQueue.insert(countries[i]);
            } else if((countries[i].getCFR() > 2) && (countries[i].getCFR() < 5)) {
                goodQueue.insert(countries[i]);
            } else if ((countries[i].getCFR() > 5) && (countries[i].getCFR() < 10)) {
                fairQueue.insert(countries[i]);
            } else {
                poorQueue.insert(countries[i]);
            }
        }




        System.out.println("Excellent Queue");
        System.out.println("---------------");
        excellentQueue.printQueue();
        System.out.println("\nVery Good Queue");
        System.out.println("---------------");
        veryGoodQueue.printQueue();
        System.out.println("\nGood Queue");
        System.out.println("----------");
        goodQueue.printQueue();
        System.out.println("\nFair Queue");
        System.out.println("----------");
        fairQueue.printQueue();
        System.out.println("\nPoor Queue");
        System.out.println("----------");
        poorQueue.printQueue();
        System.out.println("\n");


        while(!excellentQueue.isEmpty()) {
            s1.push(excellentQueue.remove());
        }

        while(!veryGoodQueue.isEmpty()) {
            s1.push(veryGoodQueue.remove());
        }

        while(!goodQueue.isEmpty()) {
            s1.push(goodQueue.remove());
        }

        while(!fairQueue.isEmpty()) {
            s1.push(fairQueue.remove());
        }

        while(!poorQueue.isEmpty()) {
            s1.push(poorQueue.remove());
        }

        System.out.println("Final Stack");
        System.out.println("-----------");
        s1.printStack();
    }


    /*
        Jacob Jerris
        09/30/20
        <p>
        Inside this class is where the Priority object is created.
        <p>
        It also houses the methods that allow the Priority object to insert and remove items.
     */

    static class Priority {
        private int maxSize;
        private Country[] queueArray;
        private int nItems;

        //Constructor for making the Priority objects
        public Priority(int s) {
            maxSize = s;
            queueArray = new Country[maxSize];
            nItems = 0;
        }

        /*
            The insert method was taking from the slides.
            <p>
            @param: Country object (item) which is the objects inside the Country array
            @return: this returns nothing since all it does is sort the number that was provided.
         */
        public void insert(Country item) {
            int j;

            if(nItems == 0) {
                queueArray[nItems++] = item;
            } else {
                for(j=nItems-1; j>=0; j--) {
                    if(item.getCFR() > queueArray[j].getCFR()) {
                        queueArray[j+1] = queueArray[j];
                    } else {
                       break;
                    }
                }
                queueArray[j+1] = item;
                nItems++;
            }
        }

        /*
            This method allows for the user to return whatever is in the current index and delete it from the current array
            <p>
            @param: nothing
            @return: the info determined by whatever index its currently at.
         */
        public Country remove() {
            return queueArray[--nItems];
        }

        /*
            This method allows for the user to check if the Priority queue is empty or not
            <p>
            @param: nothing
            @return: true if the number of items in the array is equal to 0
         */
        public boolean isEmpty() {
            return (nItems ==0);
        }

        /*
            This method allows for the user to check if the Priority queue is full or not
            <p>
            @param: nothing
            @return: true if the number of items in the array is equal to the max size variable
         */
        public boolean isFull() {
            return (nItems == maxSize);
        }

        /*
            This method allows the user to print the queue whenever it's complete
            <p>
            @param: nothing
            @return: returns the Country object depending on what index its currently at. Loops until it reaches the end of the array.
         */
        public void printQueue() {
            for(int i =0; i < queueArray.length; i++) {
                System.out.println(queueArray[i]);
            }
        }
    }



    /*
        Jacob Jerris
        09/30/20
        <p>
        Inside this class is where the Stack object is created.
        <p>
        It also houses the methods that allow the Stack object to push and pop items.
     */
static class Stack {
    private int maxSize;
    private Country[] stackArray;
    private int top;


    //Constructor for creating the stack objects
        public Stack(int s) {
            maxSize = s;
            stackArray = new Country[maxSize];
            top = -1;
         }

        /*
          The push method was taking from the slides.
          <p>
          @param: Country object (j) which is the objects inside the Country array
          @return: this returns nothing since all it does is push the current number to the top of the stack.
       */
        public void push(Country j) {
            stackArray[++top] = j;
        }

        /*
            This method allows for the user to check if the Stack is full or not
            <p>
            @param: nothing
            @return: true if the top of the stack is equal to -1
         */
        public boolean isFull() {
            return (top == -1);
        }

        /*
            This method allows for the user to check if the Stack is empty or not
            <p>
            @param: nothing
            @return: true if the top of the stack is equal to the max size -1
         */
        public boolean isEmpty() {
            return (top == maxSize-1);
        }

        /*
            This method allows the user to print the Stack whenever it's complete
            <p>
            @param: nothing
            @return: returns the Country object depending on what index its currently at. Loops until it reaches the end of the array.
         */
        public void printStack() {
            for(int i =0; i < stackArray.length; i++) {
                System.out.println(stackArray[i]);
            }
        }

}


static class Country {
    private String name;
    private String capitol;
    private double population;
    private double GDP;
    private int covidCases;
    private double covidDeaths;


    public Country(String[] values) {
        this.capitol = values[1];
        this.name = values[0];
        this.population = Double.parseDouble(values[2]);
        this.GDP = Double.parseDouble(values[3]);
        this.covidCases = Integer.parseInt(values[4]);
        this.covidDeaths = Double.parseDouble(values[5]);
        }

    /*
  This takes in nothing
  <p>
  This returns whatever the covidCases has been set to.
*/
    public int getCovidCases() {
        return covidCases;
    }

    /*
        This returns whatever the covidDeaths has been set to.
    */
    public double getCovidDeaths() {
        return covidDeaths;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the GDP has been set to.
    */
    public double getGDP() {
        return GDP;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the population has been set to.
    */
    public double getPopulation() {
        return population;
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the capitol has been set to.
    */
    public String getCapitol() {
        return capitol;
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the name has been set to.
    */
    public String getName() {
        return name;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the result of covidCases/covidDeaths has been set to, if the covidCases are greater than
        0. If not, it returns 0.
    */

    public double getCFR() {
        if(covidCases > 0) {
            return (covidDeaths/covidCases) * 100;
        } else {
            return 0;
        }
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the result of GDP/population is.
    */
    public double getGDPPerCap() {
        return (GDP/population);
    }

    /*
        This takes in a String called capitol and sets that to the object parameter
     */
    public void setCapitol(String capitol) {
        this.capitol = capitol;
    }

    /*
        This takes in a int called covidCases and sets that to the object parameter
     */
    public void setCovidCases(int covidCases) {
        this.covidCases = covidCases;
    }

    /*
        This takes in a double called covidDeaths and sets that to the object parameter
     */
    public void setCovidDeaths(double covidDeaths) {
        this.covidDeaths = covidDeaths;
    }

    /*
        This takes in a double called GDP and sets that to the object parameter
     */
    public void setGDP(double GDP) {
        this.GDP = GDP;
    }

    /*
        This takes in a String called name and sets that to the object parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
    This takes in a double called population and sets that to the object parameter
 */
    public void setPopulation(double population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return (getName() + " " + getCapitol() + " " + getPopulation() + " " + getGDP() + " "  + getCovidCases() + " " + getCovidDeaths());
    }
}
}



