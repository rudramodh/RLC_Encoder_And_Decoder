import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Decoder {

    private int numOfRows;

    public Decoder() { //Constructor
        this.numOfRows = 0;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public Node[] readFile(String fileName) {
        //Reading a file
        try {
            //the file to be opened for reading
            File f = new File(fileName);
            Scanner sc = new Scanner(f);    //file to be scanned
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) { //if line is empty then do nothing
                    continue;
                }
                numOfRows++; // for getting the length of the array
            }
            Node[] arr = new Node[numOfRows];
            int i = 0;
            // Here we use the Scanner class to read file content line-by-line.
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // From the above line of code we got a line from the file
                // content. Now we want to split the line with space as the
                // character delimiter.
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(" ");
                while (lineScanner.hasNextInt()) {
                    int data = lineScanner.nextInt();
                    Node head = arr[i];
                    Node newNode = new Node(data);
                    if (head == null) {
                        arr[i] = newNode;
                    } else {
                        while (head.next != null) { // for inserting rest of the elements in the list
                            head = head.next;
                        }
                        head.next = newNode;
                    }
                }
                i++;
            }
            sc.close();     //closes the scanner
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printArrayOfLL(Node[] arr) { // printing the encoded result
        for (int i = 0; i < arr.length; i++) {
            Node head = arr[i];
            System.out.print("\n" + i + "--> ");
            while (head != null) {
                System.out.print(head.data + " ");
                head = head.next;
            }
        }
    }


    //decoding array of linked list to 2d matrix
    public void decode(Node[] arr, int row) {
        int idx = 0; // index of array of ll
        int[][] array = new int[row][row]; // creating new 2d array of size rows*cols
        int i = 0; // for rows
        int j = 0; // for cols
        while (i < row) {
            Node head = arr[idx]; //creating head at arr[idx] each time every row is complete
            while (j < row) {
                while (head != null) { // traversing until head is null
                    int headValueZeros = head.data;
                    int x = 0;
                    while (x < headValueZeros) { // increment x until head.data
                        array[i][j] = 0; //assigning 0 to arr[i][j] position
                        System.out.print(array[i][j] + " ");
                        j++; //incrementing j and x once the value is assigned
                        x++;
                    }
                    head = head.next;
                    if (head != null) { // if head is null at this point then it will come out of the loop
                        //same logic for as above
                        int headValueOnes = head.data;
                        int y = 0;
                        while (y < headValueOnes) {
                            array[i][j] = 1;
                            System.out.print(array[i][j] + " ");
                            j++;
                            y++;
                        }
                        head = head.next; // traversing head if there is any node remaining
                    } else {
                        break;
                    }
                }
            }
            j = 0; // j back to 0
            i++;
            idx++;
            System.out.println();
        }
    }

}
