public class PrinterI implements Demo.Printer
{
    @Override
    public double[] printString(String s, com.zeroc.Ice.Current current)
    {
        String[] parts = s.split(":", 3);
        double[] response = new double[2];

        if (parts.length == 3) {
            String username = parts[0].trim();
            String hostname = parts[1].trim();
            String message = parts[2].trim();

            // keep negative integers, remove hyphens from other formatted strings
            String parsedMessage = message.matches("^-\\d+$") ? message : message.replaceAll("-", "");

            // divinakdavid:elw-b238-44:uritbsljrhbglsr b5 rfhbglsjhdfba
            // divinakdavid:elw-b238-44:kfjgsjrgb 6 srfgbaljhgdb
            // divinakdavid:elw-b238-44: 1 1 2 3 5 8 13
            // divinakdavid:elw-b238-44:lalal 7 lalal

            try {
                int n = Integer.parseInt(parsedMessage);

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

                    // return final calculated fib(N) to client
                    response[0] = fib[n];
                    return response;
                }
            } catch (NumberFormatException e) {
                // not a valid integer
            }
        }

        // print raw message on server console if no positivie int
        System.out.println(s);
        response[0] = 0;
        
        // return 0 to client
        return response;
    }
}