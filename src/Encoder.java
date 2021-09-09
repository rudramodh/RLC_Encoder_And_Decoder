public class Encoder {
    //array of linked
    //pass 2d array inside convert method and the method should convert 2D array to compressed conversion.
    //2 other classes one for RLCencoder(receives 2D matrix and cobverts it compressed form)
    // and one for RLCdecoder (receives the compressed version and converts it to 2d matrix)
        /*
        0 --> 1 --> 2 --> 3 --> null (rows)|| array of LL
        |     |     |     |
        3     1     0     5 (First value will always be white pixels)
        |     |     |     |
        2     5     8     2
        |     |     |     |
        4     3     1     2
        |     |     |     |
       null  null  null  null

     */

    /*
    Stuff remaining to do: Optimise the code
     */

    private Node[] rows; // array of node
    private int numOfRows; // length of rows
    private int numOfNodes;

    public Encoder(int capacity) { // constructor
        this.numOfRows = capacity; // value provided by user will be the length
        rows = new Node[numOfRows]; // It will accordingly create the array
        this.numOfNodes = 0;
    }

    public Encoder() {
        this(28); // default length
        this.numOfNodes = 0;
    }

    public int getNumOfNodes() {
        return numOfNodes;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public Node[] getRows() {
        return rows;
    }

    //Encoding 2d Matrix to array of Linked List
    public void encode(int[][] arr, int i, int j) {
        if (i < 0 || i >= arr.length || j < 0 || j >= arr.length) { //checking for valid input
            System.out.println("Invalid input of j and i");
            return;
        }
        while (i < arr.length && j < arr[i].length) {
            boolean isNonZero = false;
            int countZero = 0; // for counting number of zero's
            int countNonZero = 0; // for counting number of non zero's
            while (j < arr[i].length) {

                if (arr[i][j] == 0) {
                    countZero++;
                    j++; //
                } else { // if arr[i][j] != 0 then come out of the while loop
                    break;
                }
            }
            Node head = rows[i]; // creating a head at starting position of the array
            Node newNode = new Node(countZero); // new Node to insert inside the list
            if (head == null) { // for inserting first element in the list
                numOfNodes++;
                rows[i] = newNode; //connecting the node
            } else {
                while (head.next != null) { // for inserting rest of the elements in the list
                    head = head.next;
                }
                numOfNodes++;
                head.next = newNode;
            }
            while (j < arr[i].length) { // for non zero
                if (arr[i][j] != 0) {
                    isNonZero = true; // if arr[i][j] != 0 then set isNonZero = true
                    countNonZero++;
                    j++;
                } else { //if arr[i][j] == 0 then come out of the loop
                    break;
                }
            }
            if (isNonZero) { // if isNonZero = true then only this if statement will run in rest of the cases it doesn't make sense to simply create a Node of 0 and attach it at the end
                head = rows[i]; // Bringing back the head to 1st position in case if it has traversed anywhere else
                Node newNodeNonZero = new Node(countNonZero); // creating new node of countNonZero value
                while (head.next != null) { // adding element at the end
                    head = head.next;
                }
                numOfNodes++;
                head.next = newNodeNonZero;
            }

            if (!(j < arr[i].length)) { //if j < arr[i].length then it simply goes up, otherwise j = 0, and i++
                j = 0;
                i++;
            }
        }
    }

    public void printArrayOfLL() { // printing the encoded result
        for (Node row : rows) {
            Node head = row;
            while (head != null) {
                System.out.print(head.data + " ");
                head = head.next;
            }
            System.out.println();
        }
    }
}
