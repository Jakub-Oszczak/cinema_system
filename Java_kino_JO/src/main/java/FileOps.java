import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class FileOps {

    public FileOps() {

    }

    public static void writeToFile(String nazwa, List<String> dane) {

        File csvFile = new File(nazwa);

        try{
            csvFile.createNewFile();
        }
        catch (Exception e) {
            System.out.println("Nie można stworzyć pliku");
        }


//        if (csvFile.isFile()) {
//            // create BufferedReader and read data from csv
//        }


        try {
            FileWriter csvWriter = new FileWriter(nazwa, true);

            csvWriter.append(String.join(",", dane));
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        }
        catch (Exception e) {
            System.out.println("Problem z plikiem");
        }

    }

    public static long countLines(String nazwa) {
        Path path = Paths.get(nazwa);
        long lines = 0;

        try {
            lines = Files.lines(path).count();
        }
        catch (Exception e){
            System.out.println("Problem z liczeniem linii");
        }

        return lines;
    }

    public static List<List<String>> readFileToList(String nazwa) {
        List<List<String>> table = new ArrayList<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(nazwa));
            String row;

            while ((row = csvReader.readLine()) != null) {
                List<String> data = Arrays.asList(row.split(","));
                table.add(data);
            }
            csvReader.close();

            return table;
        }
        catch (Exception e) {
            System.out.println("Problem z czytaniem pliku");
            return table;
        }
    }

    public static String [][] readFile(String nazwa) {
        List<String []> table = new ArrayList<>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(nazwa));
            String row;

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                table.add(data);
            }
            csvReader.close();

            String[][] tabelaFilmow = new String[table.size()][];
            int i = 0;
            for (String [] next : table) {
                tabelaFilmow[i++] = next;
            }
            return tabelaFilmow;
        }
        catch (Exception e) {
            System.out.println("Problem z czytaniem pliku");
            String[][] tabelaFilmow = new String[0][0];
            return tabelaFilmow;
        }
    }

    public static boolean parseFileForLogin(String nazwa, String login) {
        File file = new File(nazwa);

        try {
            Scanner scanner = new Scanner(file);

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                List<String> somebodyLoginAndPass = new ArrayList<String>(Arrays.asList(line.split(",")));
                if(somebodyLoginAndPass.get(0).equals(login)) {
                    return true;
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("Błąd pliku");
        }
        return false;
    }

    public static List<String> getLine(String nazwa, String id) {
        File file = new File(nazwa);
        List<String> finalList = new ArrayList<>();


        try {
            Scanner scanner = new Scanner(file);

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                List<String> daneFilmu = new ArrayList<String>(Arrays.asList(line.split(",")));
                if(daneFilmu.get(0).equals(id)) {
                    finalList.addAll(daneFilmu);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("Błąd pliku");
        }
        return finalList;
    }

    public static int parseFileForID(String nazwa, String id) {
        File file = new File(nazwa);

        try {
            Scanner scanner = new Scanner(file);

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                List<String> daneFilmu = new ArrayList<String>(Arrays.asList(line.split(",")));
                if(daneFilmu.get(0).equals(id)) {
                    return lineNum;
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("Błąd pliku");
        }
        return -1;
    }

    public static void deleteLineFromFile(String nazwa, int numerLini) {
        String tempFile = "temp.csv";
        File newFile = new File(tempFile);

        if (newFile.exists()) {
            newFile.delete();
            deleteLineFromFile(nazwa, numerLini);
            return;
        }

        File oldFile = new File(nazwa);

        int line = 0;
        String currentLine;

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(nazwa);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                line++;

                if(numerLini != line) {
                    pw.println(currentLine);
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();

            oldFile.delete();
            writeFromFileToFile("temp.csv","repertuar.csv");
            newFile.delete();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void writeFromFileToFile(String file1, String file2) {
//        File targetFile = new File(file2);
//        targetFile.delete();
        File target = new File(file2);

        int line = 0;
        String currentLine;

        try {
            FileWriter fw = new FileWriter(file2, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(file1);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                pw.println(currentLine);
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getPassword(String nazwa, String login) {
        File file = new File(nazwa);

        try {
            Scanner scanner = new Scanner(file);

            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                List<String> somebodyLoginAndPass = new ArrayList<String>(Arrays.asList(line.split(",")));
                if(somebodyLoginAndPass.get(0).equals(login)) {
                    return somebodyLoginAndPass.get(1);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("Błąd pliku");
        }
        return "false";
    }

}
