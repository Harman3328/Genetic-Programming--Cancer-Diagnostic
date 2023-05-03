package ec.app.assignment1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Dataset {
    public void generator() {
        Random random = new Random();
        try {
            File file = new File("data1.txt");
            if (file.createNewFile()) {
                System.out.println("file created: " + file.getName());
            } else {
                System.out.println("file alreay exists");
            }
        } catch (IOException e) {
            System.out.println("error 1");
        }

        try {
            FileWriter fileWriter = new FileWriter("data1.txt"); 
            for (int i = 0; i < 500; i++) {
                float num = random.nextFloat();
                float result = (num * num * num * num * num) + (num * num * num * num) + (num * num) + (num);
                fileWriter.write(num + " " + result + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("error 2");
        }

    }

    public static void main(String[] args) {
        Dataset dataset = new Dataset();
        dataset.generator();
    }
}