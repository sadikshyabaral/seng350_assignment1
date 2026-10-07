import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:tcp -h elw-b238-35 -p 9099");

            Demo.PrinterPrx twoway = Demo.PrinterPrx.checkedCast(
                communicator.propertyToProxy("Printer.Proxy")).ice_twoway().ice_secure(false);
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            //PrinterPrx printer = PrinterPrx.checkedCast(base);

            if(printer == null)
            {
                throw new Error("Invalid proxy");
            }
            
            String username = System.getProperty("user.name");
            String hostname = "";
            // hostname lookup can throw exception since is a query, so wrap in try-catch block
            try
            {
                hostname = InetAddress.getLocalHost().getHostName();
            }
            catch (UnknownHostException e)
            {
                System.err.println("Warning: Could not determine hostname. Proceeding with empty hostname.");
            }

            System.out.println("Type your message and press Enter (type 'exit' to quit):");

            try (Scanner scanner = new Scanner(System.in))
            {
                while (true)
                {
                    System.out.print("press Enter to send message or type 'exit' to quit: "); //change this if you want to change prompt to user every line
                    if (!scanner.hasNextLine())
                    {
                        break;
                    }

                    String input = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(input.trim()))
                    {
                        System.out.println("Terminating client connection.");
                        break;
                    }

                    if (!input.trim().isEmpty())
                    {
                        String formattedMessage = username + ":" + hostname + ":" + input;
                        int result = printer.printString(formattedMessage);

                        System.out.println("Server response: " + result);
                    }
                }
            }
        }
    }
}