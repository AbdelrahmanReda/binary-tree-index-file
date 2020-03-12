import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
    static void readFile(String path) {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            for (int i = 0; i < file.length() / 4; i++) {
                if (i%4==0 && i !=0)
                    System.out.println();
                System.out.print(" "+file.readInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    static void CreateRecordsFile(String filename, int numberOfRecords) {
        try {
            RandomAccessFile treeIndexFile = new RandomAccessFile(filename, "rw");
            for (int i = 1; i < numberOfRecords; i++) {

                treeIndexFile.writeInt(i);
                treeIndexFile.writeInt(0);
                treeIndexFile.writeInt(0);
                treeIndexFile.writeInt(0);

            }
            treeIndexFile.writeInt(-1);
            treeIndexFile.writeInt(0);
            treeIndexFile.writeInt(0);
            treeIndexFile.writeInt(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void handleReferences(RandomAccessFile file, int offset, int reference){
        try {
            file.seek(0);
            file.seek(16);
            int x = file.readInt();
            while (true)
            {
                if (x<offset) {
                    file.readInt();
                    file.readInt();
                    int leftNodeByteOffset = (int) file.getFilePointer();
                    int leftNodeValue = file.readInt();
                    if (leftNodeValue == -1) {
                        file.seek(leftNodeByteOffset);
                        file.writeInt(reference);
                        break;
                    } else {
                        file.seek(leftNodeValue * 16);
                        x = file.readInt();
                    }
                }
                else {
                    file.readInt();
                    int rightNodeByteOffset = (int) file.getFilePointer();
                    int rightNodeValue = file.readInt();
                    if (rightNodeValue == -1) {
                        file.seek(rightNodeByteOffset);
                        file.writeInt(reference);
                        break;
                    } else {
                        file.seek(rightNodeValue * 16);
                        x = file.readInt();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int InsertNewRecordAtIndex(String filename, int Key, int ByteOffset) {
        //open the tree file
        try {
            RandomAccessFile treeIndexFile = new RandomAccessFile(filename, "rw");
            int freeRecordIndex = treeIndexFile.readInt();
            if (freeRecordIndex != -1) {
                treeIndexFile.seek(0);
                if ((freeRecordIndex + 1) == treeIndexFile.length() / 16)
                    treeIndexFile.writeInt(-1);
                else
                    treeIndexFile.writeInt(freeRecordIndex + 1);

                if (freeRecordIndex == 1) {
                    treeIndexFile.seek(0);
                    treeIndexFile.seek(16);
                    treeIndexFile.writeInt(Key);
                    treeIndexFile.writeInt(ByteOffset);
                    treeIndexFile.writeInt(-1);
                    treeIndexFile.writeInt(-1);
                }
                else {
                    /*seek to the proper place to insert any new record*/
                    treeIndexFile.seek(freeRecordIndex * 16);
                    treeIndexFile.writeInt(Key);
                    treeIndexFile.writeInt(ByteOffset);
                    treeIndexFile.writeInt(-1);
                    treeIndexFile.writeInt(-1);
                    handleReferences(treeIndexFile, Key, freeRecordIndex);
                }

            } else
                System.out.println("can't insert to file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
    void SearchRecordInIndex(String filename, int Key) {
    }

    void DisplayBinarySearchTreeInOrder(String FileName) {
    }

    void DisplayIndexFileContent(String filename) {
    }

    public static void main(String args[]) {
        String treeIndexFile = "src/containers/tree,bin";
        CreateRecordsFile(treeIndexFile, 8);
        readFile(treeIndexFile);
        InsertNewRecordAtIndex(treeIndexFile, 5, 12);
        InsertNewRecordAtIndex(treeIndexFile, 12, 24);
        InsertNewRecordAtIndex(treeIndexFile, 3, 36);
        InsertNewRecordAtIndex(treeIndexFile, 9, 48);
        InsertNewRecordAtIndex(treeIndexFile, 8, 60);
        InsertNewRecordAtIndex(treeIndexFile, 2, 72);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);
        InsertNewRecordAtIndex(treeIndexFile, 4, 84);


        readFile(treeIndexFile);

    }
}
