import java.util.ArrayList;
import java.util.Collections;

public class Searcher {

    private ArrayList<Node> front;

    //executes the A* algorithm and removes the nodes that are equal to the one selected
    public ArrayList<Node> searcher(Node root){
        ArrayList<Node> return_list = new ArrayList<Node>();//The list contains the nodes of the path to the end
        this.front = new ArrayList<Node>();
        this.front.add(root);
        Node current;
        //Search for the path
        while(!this.front.isEmpty()){ 
            current = this.front.remove(0);
            //Removes nodes with f equal to current's
            for(int i = 0; i < this.front.size(); i ++){
                if(this.front.get(i).get_f() == current.get_f()){
                    this.front.remove(i);
                }
            }
            //Returns the solution if the node is terminal
            if(current.isTerminal()){
                return_list.add(current);
                return return_list;
            }
            //Adds the current node and its children to the front and sorts it
            return_list.add(current);
            this.front.addAll(current.get_children());
            Collections.sort(this.front);
        }

        System.out.println("Null");
        return null;
    }
}