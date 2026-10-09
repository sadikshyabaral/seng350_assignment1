import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrinterI implements Demo.Printer {
    @Override
    public Demo.Response printString(String s, com.zeroc.Ice.Current current)
    {
        long serverStart = System.nanoTime();

        Demo.Response response = new Demo.Response();
        response.result = 0;
        response.serverExecutionTimeMs = 0.0;

        String[] parts = s.split(":", 3);

        if (parts.length == 3) {
            String username = parts[0].trim();
            String hostname = parts[1].trim();
            String message = parts[2].trim();

            // keep negative integers intact, strip hyphens from other formats
            String parsedMessage = message.matches("^-\\d+$") ? message : message.replaceAll("-", "");

            // match the first number sequence in the parsed string
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(parsedMessage);

            if (matcher.find()) {
                try {
                    int n = Integer.parseInt(matcher.group());

                    if (n > 0) {
                        int[] fib = new int[n + 1];
                        StringBuilder seriesOutput = new StringBuilder();

                        for (int i = 1; i <= n; i++) {
                            if (i == 1 || i == 2) {
                                fib[i] = 1;
                            } else {
                                fib[i] = fib[i - 1] + fib[i - 2];
                            }
                            seriesOutput.append(fib[i]).append(" ");
                        }

                        // print username:hostname: fibonacci series
                        System.out.println(username + ":" + hostname + ": " + seriesOutput.toString().trim());

                        // store result
                        response.result = fib[n];
                    } else {
                        // gegative number found
                        System.out.println(s);
                    }
                } catch (NumberFormatException e) {
                    // number was too large to fit into an int
                    System.out.println(s);
                }
            } else {
                // no numeric digits found in message
                System.out.println(s);
            }
        } else {
            // invalid message structure
            System.out.println(s);
        }

        // record server end time & calculate Server Service Execution Time for ALL execution paths
        long serverEnd = System.nanoTime();
        double serverExecutionTimeMs = (serverEnd - serverStart) / 1_000_000.0;

        // print server service execution time
        System.out.printf("Server Execution Time: %.3f ms%n", serverExecutionTimeMs);

        response.serverExecutionTimeMs = serverExecutionTimeMs;
        return response;
    }
}