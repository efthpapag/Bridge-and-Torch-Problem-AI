# General information
This program models and solves the bridge and torch problem.
The programming language used for development
of the program was Java.

# Use instractions
1. Go to the folder containing the .java files through the command prompt
2. Compile the Bridge.java file with the command javac Bridge.java (all other files will be compiled automatically).
3. Run the program with the java Bridge command.
4. Enter the number of people to cross the bridge.
5. Enter the time each person needs to cross the bridge (enter each time separately).
6. Enter the maximum time in which the program must be solved.

# Modeling the problem 
The classes used in the program are Bridge,
Searcher, Node and Person. The implementations of the Person class
correspond to the individuals who make up the group of individuals who
 are trying to cross the bridge. The realizations of the class
Nodes correspond to states, in which, individuals are in
either side and not in motion, with the lamp on either side, while switching Nodes
correspond to the change of state of the problem, as a result
of some movement of person/persons and lamp. Searcher is the class
the one in which the A* algorithm is executed and the list with those Nodes, which correspond to the states of which the series consists
of situations followed to the end is completed. Bridge is the
class, inside which the main method exists.


# Heuristic
The heuristic we use exactly approximates the cost from
the node we are at until the end. For its calculation
2 types are used, one for the cases we are in
in a node with the lamp on the left side and one for
the cases where the lamp is on the right. We notice that the
cost of a node until we reach the end is a) the cost
that we would have to get to the end without him though
limitation of the lamp, i.e. practically the sum of the years
of each person one by one, however ignoring the 2 youngest
times (eg 1,3,6,8,12->12+6). Apart from this cost, that which
we are left for the exact calculation is b) a combination of 2
of shorter times of the problem (eg 1,3,6,8,12->1+1+3+3+3=2*1 +
3*3). Indeed, we see how to calculate the cost
of the root node we have 12 + 6 + 2*1 + 3*3=29. The only thing that us
what remains is we know the multipliers of 1 and 3 and will
we can always estimate the cost exactly. Here solving
essentially as a system, we ended up with the multipliers with
lamp to be on the left and the multipliers with the lamp
to be on the right as follows.

If(lamp=”left”){
    H = h(without limiting the lamp) + α*min1 + β*min2
}else{
    H = h(without limiting the lamp) + γ*min1 + δ*min2
}
Where a = (right.size() + Nmod2)div2
    β = 2*(right.size( ) + 1 - Nmod2)div2
    γ= (right.size() - 2 + Nmod2)div2
    δ= right.size() - 1 - Nmod2

Now having a formula for the exact calculation of costs up to
finally, what we need is a way for this guy to
it only comes out right when we make the right move. Therefore
we split the problem into 2 imaginary situations for each case
where the lamp is on the right and 2 for each case where the lamp
is on the left. Each case out of a total of 4,
determined by whether the number of individuals located to the right or left respectively is odd or odd. Based on these
cases take value min1 and min2 that we saw above and
only if we make the right move in the positions of min1, min2 will
the really shortest times of the problem are entered (in
case of our example 1 and 3 respectively). To every other
case the cost will be higher and he will not prefer it
specific node A*.



# Program flow
Upon starting the program, the user is prompted to
type some inputs, necessary for initialization
of the problem, and is generated based on some of the inputs
these, the Node corresponding to its initial state
problem. An object of the class is then created
Searcher and with the initial Node as an argument, the solving process starts
of the problem, which is timed.When this is completed,
in the event that the total time of crossing the bridge, o
which is different from the time duration of the process
and arises from the movements of the atoms, is less than one
time limit given as input to the program, then
some elements of the Nodes located in are printed
aforementioned list populated inside the Searcher class,
during the resolution process. Alternatively, a sentence is printed
indicating that no solution was found with a total traversal time
bridge shorter than the time limit.
