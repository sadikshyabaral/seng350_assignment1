public class PrinterI implements Demo.Printer
{
    public void printString(String s, com.zeroc.Ice.Current current)
    {
        String trimmed = s.trim();

        // Check if the received message is a positive integer
        if (trimmed.matches("\\d+")) {
            int n = Integer.parseInt(trimmed);

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

                // get client hostname and username for prefix
                String clientName = current.id.name; 
                System.out.println("client name:" + clientName + "fib:" + seriesOutput.toString().trim());

                // Return the final calculated value fib(N)
                //return fib[n];
            }
        }
        System.out.println(s);
    }
}