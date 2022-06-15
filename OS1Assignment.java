import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
public class OS1Assignment {
    private static final int address_byte = 8;
    private static final int PAGE_SIZE = 128;
    private static final int FRAME_NUMBER = 8;
    private static final int PAGE_NUMBER = 32;

    public static void main(String[] args) throws IOException {

        String address = "OS1testsequence";
        File input_file = new File(address);
        FileInputStream file_in = new FileInputStream(input_file);
        ArrayList<Long> arrlong = new ArrayList<>();
        ArrayList<String> arrStr = new ArrayList<>();
        DataInputStream data_in = new DataInputStream(file_in);

        //byte array
        byte[] data = new byte[8];


        while (data_in.available()>0) {
                arrlong.add(Long.reverseBytes(data_in.readLong())); //reversing the input into bytes
            findAddress(arrlong);

        }
        System.out.println("My Array of bytes in : ");
        System.out.println(arrlong);

        data_in.close();
        //converting my bytes to Binary
        for(Long k : arrlong){

            arrStr.add(Long.toBinaryString(k));
            //getLogicalAddress(arrStr);

        }
        System.out.println("My Array of bytes in Binary : ");
        System.out.println(arrStr);

    }

    public static void findAddress(ArrayList<Long> arr) throws IOException{
        ArrayList<Long> arr1 = new ArrayList<>();
        for(int i =0; i != arr.size() ; i++){
            //the page table with only seven entries
            int[]  page_table = new int[]{2,4,1,7,3,5,6};
            //calculating the offset using the Page size
            long offset = arr.get(i) % PAGE_SIZE;
            //page entries to go into table
            //System.out.println(offset);
            long page_table_entry = arr.get(i) / PAGE_SIZE;
            //frame entry
            int frame_entry = page_table[(int)page_table_entry];
            //frame address
            int frame_address = (PAGE_SIZE * frame_entry) + (int)offset;
            arr1.add((long)frame_address);
        }
        //writing the physical address in Hex
        WriteHexToFile(arr1);
        //System.out.println( arr1);


    }

    // method to write the final physical address as Hex

    public static void WriteHexToFile(ArrayList<Long> final_address) throws IOException{
        File myWriter = new File("output-OS1.txt");
        OutputStream out = new FileOutputStream(myWriter);
        //myWriter.close();
        //Converting the vector values to hex and then to string
        for(int i =0 ; i< final_address.size();i++){
            String v = "0x" +Long.toHexString((final_address.get(i)))+"\n";
            //int vv = Integer. parseInt(v);
            byte[] b = v.getBytes(StandardCharsets.UTF_8);
            byte ba = (byte)'\n';
            out.write((b));


        }
        out.close();


    }


}
