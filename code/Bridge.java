import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Bridge{
    
    static int all_min1;
    static int all_min2;
    
    public static void main(String[] args){

        //Receives inputs
        Scanner s = new Scanner(System.in);
        ArrayList<Person> people = new ArrayList<Person>();
        System.out.println("How many are the members of the family?");
        int N = Integer.parseInt(s.nextLine());
        for(int i=0;i<N;i++){
            System.out.println("What is the crossing time of person "+(i+1)+"?");
            int ct = Integer.parseInt(s.nextLine());
            people.add(new Person(ct));
        }
        System.out.println("What is the crossing time limit? ");
        int limit = Integer.parseInt(s.nextLine());
        s.close();
        Collections.sort(people);

        //Two smallest crossing times
        all_min1 = people.get(people.size() - 1).get_crossing_time();
        all_min2 = people.get(people.size() - 2).get_crossing_time();

        //Creation of root
        Node root = new Node("right", new ArrayList<Person>(), people, 0);
        root.find_f();

        Searcher a = new Searcher();
        long start = System.currentTimeMillis();
        ArrayList<Node> return_list = a.searcher(root);
        long end = System.currentTimeMillis();
        //Prints solution
        System.out.println("Execution time: " + (end - start) + " msec");
        if(limit < return_list.get(return_list.size() - 1).get_g()){
            System.out.println("Solution not found");
        }else{
            for (Node i : return_list){

                System.out.println("the lamp is on the "+i.get_lamp()+" side");
    
                Collections.sort(i.get_left());
                System.out.println("The left side after the change has: ");
                for(Person j : i.get_left()){
                    System.out.println(j.get_crossing_time());
                }
    
                System.out.println(" ");
    
                System.out.println("The right side after the change has: ");
                for(Person j : i.get_right()){
                    System.out.println(j.get_crossing_time());
                }

                System.out.println("----------------------------------------------");    
            }
        }
        System.out.println("Crossing time : "+(return_list.get(return_list.size() - 1).get_g()));

    }
}