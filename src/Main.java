import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        File f = new File("sandu.txt");

        ArrayList<String> fRead = new ArrayList<>();

        if(f.exists()){

            System.out.println("il file: " + f.getName() + " esiste\n");

            try {

                BufferedReader br = new BufferedReader(new FileReader(f));

                String s = "";
                while((s = br.readLine()) != null){
                    fRead.add(s);
                }

                br.close();

            } catch (Exception e) {
                System.err.println("impossibile leggere il file\n");
            }
        }

        String prodotti[] = new String[fRead.size()];
        double costoUnitario[] = new double[fRead.size()];
        double quantita[] = new double[fRead.size()];
        double sconto[] = new double[fRead.size()];

        ArrayList<String> htmlFile = new ArrayList<>();

        for(int x = 0; x < fRead.size(); x++){
            String s[] = fRead.get(x).split(" ");

            if(s.length == 3 || s.length == 4){
                prodotti[x] = s[0];
                costoUnitario[x] = Double.parseDouble(s[1]);
                quantita[x] = Double.parseDouble(s[2]);

                BigDecimal bd = new BigDecimal((costoUnitario[x] - (costoUnitario[x] / 100 * sconto[x]))).setScale(2, RoundingMode.HALF_EVEN);
                double scontato = bd.doubleValue();

                if(s.length == 4){
                    sconto[x] = Double.parseDouble(s[3].substring(0, s[3].length() - 1));
                    htmlFile.add("<tr><td>" + prodotti[x] + "</td><td>" + costoUnitario[x] + "</td><td>" + quantita[x] + "</td><td>" + sconto[x] + "</td><td>" + scontato + "</td></tr>");
                    System.out.println("prodotti: " + prodotti[x] + "; costo unitario: " + costoUnitario[x] + "; quantita: " + quantita[x] + ";  sconto: " + sconto[x] + ";" + "; prezzo scontato: " + scontato + ";");
                } else {
                    sconto[x] = -1;
                    htmlFile.add("<tr><td>" + prodotti[x] + "</td><td>" + costoUnitario[x] + "</td><td>" + quantita[x] + "</td><td>/</td><td>/</td></tr>");
                    System.out.println("prodotti: " + prodotti[x] + "; costo unitario: " + costoUnitario[x] + "; quantita: " + quantita[x] + ";");
                }

            } else {
                System.err.println("Tabella in formato invalido\n");
            }
        }


        File html = new File("index.html");
        try {
            if(html.createNewFile()){

                BufferedWriter bw = new BufferedWriter(new FileWriter(html));

                bw.write("<!DOCTYPE html>");
                bw.write("<html>");
                bw.write("<body>");
                bw.write("<table>");
                bw.write("<th>Tipo</th><th>Prezzo</th><th>Quantita</th><th>Sconto</th><th>Prezo scontato</th>");

                for(int x = 0; x < htmlFile.size(); x++){
                    bw.write("<td>" + htmlFile.get(x) + "</td>");
                }

                bw.write("</table>");
                bw.write("</body>");
                bw.write("</html>");

                bw.close();
            } else {
                System.err.println("File html esiste già");
            }

            System.out.println("\nHTML file path: " + html.getAbsolutePath());

        } catch (Exception e){
            System.err.println("Impossibile creare il file html");
            e.printStackTrace();
        }

    }
}