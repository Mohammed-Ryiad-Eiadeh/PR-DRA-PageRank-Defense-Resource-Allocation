import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

public class test {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("D:\\PR-DRA\\Results Shown in Table\\Results Table 8 with S = 20 units.csv"), StandardCharsets.UTF_8);
        lines.remove(0);
        for (int i = 0; i < lines.size(); i++) {
            var tt = lines.get(i).split(",", 2)[1];
            var s = tt;
            var arr = s.split(",");
            System.out.print(lines.get(i).split(",")[0] + "    ");
            for (var ii = 0; ii < arr.length; ii++) {
                System.out.print(" & " + new DecimalFormat("#.##").format(Double.parseDouble(arr[ii])));
            }
            System.out.println();
        }
    }
}
