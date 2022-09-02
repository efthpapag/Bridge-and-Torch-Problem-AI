import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

public class Node implements Comparable<Node> {
    private String lamp;
    private ArrayList<Person> left = new ArrayList<Person>();
    private ArrayList<Person> right = new ArrayList<Person>();
    private int g;
    private int f;

    //Constructor
    public Node(String lamp,ArrayList<Person> left,ArrayList<Person> right,int g){
        this.lamp = lamp;
        this.g = g;
        for(Person p : left){
            this.left.add(p);
        }
        for(Person p : right){
            this.right.add(p);
        }
    }

    /*Returns children
    node. The children's lamp is on the opposite side of
    that of the node we are in, while each child
    corresponds to one of the possible moves they may have
    done depending on whether we pass the lamp from right to left
    or vice versa. Depending on the side the lamp is on,
    on the parent node, there is a limit to the number of people who
    they move to the other side. The child's g is the
    result of adding the g of the parent and the time that
    it takes the slowest moving person to cross.
    Additionally, we calculate the child's f by calling find f. */
    public ArrayList<Node> get_children(){
        String children_lamp;
        ArrayList<Node> children = new ArrayList<Node>();
        if (lamp.equals("left")){

            children_lamp = "right";
            Set<Set<Person>> comb = combinations(this.left);

            //Creates the children based on the combinations of people
            for(Set<Person> s : comb){

                if(s.size() == 1){

                    int children_g = this.g;
                    ArrayList<Person> l = new ArrayList<Person>();
                    for(Person i : this.left){
                        l.add(i);
                    }
                    ArrayList<Person> r = new ArrayList<Person>();
                    for(Person i : this.right){
                        r.add(i);
                    }

                    //Finds the maximum crossing time of the people moving
                    int max = -1;
                    for(Person p : s){
                        if (max < p.get_crossing_time()){
                            max = p.get_crossing_time();
                        }
                        l.remove(p);
                        r.add(p);
                    }
                    children_g += max;

                    //Creation of child
                    Node n = new Node(children_lamp,l,r,children_g);
                    n.find_f();
                    children.add(n);

                }
            }
        }else{
            
            children_lamp = "left";
            Set<Set<Person>> comb = combinations(this.right);
            
            //Creates the children based on the combinations of people
            for(Set<Person> s : comb){

                if(s.size() == 2){

                    int children_g=this.g;
                    ArrayList<Person> l = new ArrayList<Person>();
                    for(Person i : this.left){
                        l.add(i);
                    }
                    ArrayList<Person> r = new ArrayList<Person>();
                    for(Person i : this.right){
                        r.add(i);
                    }
                    
                    //Finds the maximum crossing time of the people moving
                    int max=-1;
                    for(Person p : s){
                        if (max < p.get_crossing_time()){
                            max = p.get_crossing_time();
                        }
                        r.remove(p);
                        l.add(p);
                    }
                    children_g += max;

                    //Creation of child
                    Node n = new Node(children_lamp,l,r,children_g);
                    n.find_f();
                    children.add(n);
                }  
            }
        }

        return children;
    }

    //Produces a set of all the possible combinations of people in arr
    public Set<Set<Person>> combinations(ArrayList<Person> arr){
        Set<Set<Person>> comb = new HashSet<Set<Person>>();
        for (Person i:arr){
            for(Person j:arr){
                Set<Person> s = new HashSet<Person>();
                s.add(i);
                s.add(j);
                comb.add(s);
            }
        }
        return comb;
    }

    //Contains heuristic
    public void find_f(){
        int h = 0;
        Collections.sort(right);//In descending order
        int n = left.size()+right.size();//The number of people
        int min1;
        int min2;

        //Finds the total cost for crossing without the lamp constraint
        int[] helper = new int[right.size()];
        int i = 0;
        for(Person p:this.right){
            helper[i]=p.get_crossing_time();
            i++;
        }
        bubbleSort(helper);

        for(i=0;i<right.size();i=i+2){
            //Do not include the two smallest crossing times
            if((helper[i]!=Bridge.all_min1)&&(helper[i]!=Bridge.all_min2)){
                h=h+helper[i];
            }
        }        

        //Finds the two smallest crossing times on the left side
        int min1left = Integer.MAX_VALUE;
        int min2left = Integer.MAX_VALUE;
        for(Person l:this.left){
            if(min1left>l.get_crossing_time()){
                min2left=min1left;
                min1left=l.get_crossing_time();
            }else if(min2left>l.get_crossing_time()){
                min2left=l.get_crossing_time();
            }
        }

        //Finds the two smallest crossing times on the right side
        int min1right = Integer.MAX_VALUE;
        int min2right = Integer.MAX_VALUE;
        for(Person l:this.right){
            if(min1right>l.get_crossing_time()){
                min2right=min1right;
                min1right=l.get_crossing_time();
            }else if(min2right>l.get_crossing_time()){
                min2right=l.get_crossing_time();
            }
        }

        if (this.lamp.equals("left")){
            
            //Checks if the number of people on the left side is even or odd
            if(left.size()%2 == 0){
                min2 = min2left;
                min1 = min1left;
            }else{
                min2 = min1left;
                min1 = min1right;
            }

            if((right.size() == 1)&&(n%2 != 0)){
                if(right.get(0).get_crossing_time() == Bridge.all_min1){
                    h = h + min1left;
                }else if(right.get(0).get_crossing_time() == Bridge.all_min2){
                    h = h + min1right;
                }             
            }

            //Heuristic function
            h = h + ((right.size() + n%2)/2)*min1 + ((right.size() + 1 - n%2)/2)*2*min2;

        }
        else{

            //Checks if the number of the people on the right side is odd or even//
            if(((right.size()%2 == 0)&&(n%2 != 0))||(right.size()%2 != 0)&&(n%2 == 0)){
                min2 = min1left;
                //Resolves a case of tie between nodes with equal f
                if(min2 == Bridge.all_min1){
                    min2 = Bridge.all_min2;
                }
                min1 = min1right;
            }else{
                min2 = min2right;
                min1 = min1right;
            }

            //Heuristic function
            if(right.size()>1+n%2){
                h=h+(right.size()-1-n%2)*min2;
            }

            if(right.size()+n%2>2){
                h=h+(((right.size()-2+n%2)/2)*min1);
            }
        }
    
        this.f = h + this.g;
    }

    public int get_g(){
        return this.g;
    }

    public String get_lamp(){
        return this.lamp;
    }

    public ArrayList<Person> get_left(){
        return this.left;
    }


    public ArrayList<Person> get_right(){
        return this.right;
    }

    public Boolean isTerminal(){
        return this.right.isEmpty();
    }

    public int get_f(){
        return this.f;
    }

    @Override
    public int compareTo(Node n){
        return Integer.compare(this.f, n.get_f());
    }

    //sorts a group of integers from the older to the younger
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        int temp = 0;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr[j-1] < arr[j]){
                    //swap elements
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}