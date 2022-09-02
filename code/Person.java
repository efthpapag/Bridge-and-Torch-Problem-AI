public class Person implements Comparable<Person> {

    private int crossing_time;

    //Constructor
    public Person(int crossing_time){
        this.crossing_time=crossing_time;
    }

    //returns the time it takes specific person to cross the bridge
    public int get_crossing_time(){
        return this.crossing_time;
    }

    //compares 2 objects based on time crossing them
    @Override
    public int compareTo(Person p){
        return Integer.compare(p.get_crossing_time(),this.crossing_time);
    }
}