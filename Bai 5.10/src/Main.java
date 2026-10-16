import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner mrBeast=new Scanner(System.in);
        System.out.println("Nhap ten file:");
        String filename= mrBeast.nextLine();
        Map<String ,String> map=new HashMap<>();
        FileReader f=null;
        BufferedReader b=null;
        boolean success=false;
        try {
            f = new FileReader(filename);
            b = new BufferedReader(f);
            String line;
            while ((line = b.readLine()) != null) {
                 String[] arr= line.split("=",2);
                 if (arr.length == 2) {
                     map.put(arr[0], arr[1]);
                 }
            }
            if (!map.containsKey("username")) {
                throw new InvalidConfigException("thieu username");
            }
            if (!map.containsKey("timeout")) {
                throw new InvalidConfigException("thieu timeout");
            }
            int timeout = Integer.parseInt(map.get("timeout"));
            if (timeout <= 0) {
                throw new InvalidConfigException("timeout phai lon hon 0");
            }
            if (map.containsKey("maxConnections")) {
                int maxConnections = Integer.parseInt(map.get("maxConnections"));
                if (maxConnections <= 1) {
                    throw new InvalidConfigException("maxConnections phai lon hon 1");
                }
            }
            success=true;
        } catch (FileNotFoundException e){
            System.out.println("khong tim thay file");
        } catch (IOException e){
            System.out.println("Loi khi doc file");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("sai form so");
        } catch (InvalidConfigException e){
            System.out.println("Invalid congfig: "+e.getMessage());
        } finally {
            if (f!=null){
                try {
                    f.close();
                } catch (IOException e) {
                    System.out.println("Loi khi dong file");
                }
            }
            if (success==true){
                for (Map.Entry<String, String> m: map.entrySet()){
                    System.out.println("Key: "+m.getKey()+" - Value: "+m.getValue());
                }
                System.out.println("Config loaded successfully");
            }
            System.out.println("Program finished");
        }
    }
}