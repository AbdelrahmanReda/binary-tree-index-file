import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
    static void readFile(String path){
        try {
            RandomAccessFile file = new RandomAccessFile(path,"rw");
            for (int i = 0; i <file.length()/4 ; i++) {
                System.out.println(file.readInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void CreateRecordsFile (String filename, int numberOfRecords){
        try {
            RandomAccessFile treeFile = new RandomAccessFile(filename,"rw");
            for (int i = 1; i <numberOfRecords ; i++) {

                treeFile.writeInt(i);
                treeFile.writeInt(0);
                treeFile.writeInt(0);
                treeFile.writeInt(0);

            }
            treeFile.writeInt(-1);
            treeFile.writeInt(0);
            treeFile.writeInt(0);
            treeFile.writeInt(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int InsertNewRecordAtIndex (String filename, int Key, int ByteOffset){return 0; }
    void SearchRecordInIndex (String filename, int Key){}
    void DisplayBinarySearchTreeInOrder (String FileName){}
    void DisplayIndexFileContent (String filename){}

    public static void main (String args[]){
        String treeFile = "src/containers/tree,bin";
        CreateRecordsFile(treeFile,8);
        readFile(treeFile);
    }
}
