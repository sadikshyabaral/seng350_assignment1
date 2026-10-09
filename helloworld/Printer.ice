module Demo {
    struct Response {
        int result;
        double serverExecutionTimeMs; // Server service execution time
    };

    interface Printer {
        Response printString(string s);
    };
};