import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;


public class ReadImage {
    public static Color[][] loadPixelsFromFile(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        Color[][] colors = new Color[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colors[x][y] = new Color(image.getRGB(x, y));
            }
        }
        return colors;
    }

    public static int[][] loadGrayPixelsFromFile(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        Raster raster = image.getData();

        int[][] pixels = new int[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pixels[x][y] = raster.getSample(x, y, 0);
            }
        }
        return pixels;
    }

    static void display(int[][] pixels) {
        for (int[] pixel : pixels) {
            for (int i : pixel) {
                System.out.print(i + " ");
            }
            System.out.println("");
        }
    }

    static void convertToBinary(int[][] pixels) {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] > 0)
                    pixels[i][j] = 1;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        int[][] pixels = loadGrayPixelsFromFile(new File("C:/utils/ImageProcessing/Test_Images/seven.png"));

        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();

        //To save the RLC encoded result to a txt file.
        // Creating a File object that represents the disk file.
        File file = new File("EncodedResult.txt");
        PrintStream o = new PrintStream(file);

        System.out.println(file.getName()+" is created successfully");
        System.out.println("For RLC encoded result go to "+file.getAbsolutePath());

        // Store current System.out before assigning a new value
        PrintStream console = System.out;

        // Assign o to output stream
        System.setOut(o);
        encoder.encode(pixels, 0, 0);
        encoder.printArrayOfLL();

        //up until this everything will be added to txt file.
        // Use stored value for output stream
        System.setOut(console);
        //After this everything will be written on console

        Node[] nodes = decoder.readFile("EncodedResult.txt");//Reading file and generating Array of LL

        System.out.println("\nAfter Decoding");
        decoder.decode(nodes, decoder.getNumOfRows());


    }
}