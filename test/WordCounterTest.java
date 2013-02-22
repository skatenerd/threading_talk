import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/20/13
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordCounterTest {

    @Test
    public void countTheWords()throws Exception{
        List<BufferedReader> readers = new ArrayList<BufferedReader>();
        for(int i=1;i<11;i++){
            FileReader reader = new FileReader("./test/WordsFixtures/words_" + i+ ".txt");
            BufferedReader foo =new BufferedReader(reader, 100000);
            readers.add(foo);

            foo.readLine();
        }
        final WordCounter counter = new WordCounter(readers);
        long time = Timer.time(new Runnable(){
            public void run(){
                Map<String, AtomicInteger> counts = counter.countOccurrences();
                int sum=0;
                for(AtomicInteger foo:counts.values()){
                    sum+=foo.get();
                }
                System.out.println(sum);
            }
        });

        System.out.println(time);
    }

    @Test
    public void countTheWordsSequentially()throws Exception{
        List<BufferedReader> readers = new ArrayList<BufferedReader>();
        for(int i=1;i<11;i++){
            FileReader reader = new FileReader("./test/WordsFixtures/words_" + i+ ".txt");
            readers.add(new BufferedReader(reader, 100000));
        }
        final WordCounter counter = new WordCounter(readers);
        long time = Timer.time(new Runnable(){
            public void run(){
                Map<String, Integer> counts = counter.countOccurrencesSequentially();
                int sum=0;
                for(int foo:counts.values()){
                    sum+=foo;
                }
                System.out.println(sum);
            }
        });
        System.out.println(time);
    }


    @Test
    public void setupWordFile() throws Exception{
        FileReader reader = new FileReader("./test/WordsFixtures/words.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> words = new ArrayList<String>();
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            words.add(line);
        }

        for(int i=1;i<11;i++){
            FileWriter writer = new FileWriter("./test/WordsFixtures/words_" + i+ ".txt");
            for(int word = 0; word < 5000; word ++){
                writer.write(words.get((int)(Math.random()*words.size())) + "\n");

            }
        }
        FileWriter writer = new FileWriter("./test/WordsFixtures/long_words.txt");
        for(int word = 0; word < 1000000; word ++){
            writer.write(words.get((int)(Math.random()*words.size())) + "\n");

        }
    }


}
