/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.project1.debug;

/**
 *
 * @author Flávio J. Saraiva
 */
public class Debug {

    /**
     * A variable indicating if debug is enabled or not.
     */
    public static volatile boolean ENABLED = false;

    /**
     * Prints a message if debug is enabled. Adds a line terminator at the end.
     *
     * @param msg Message to print.
     */
    public static void println(Object msg) {
        assert (msg != null);
        if (ENABLED) {
            System.out.println("DEBUG: " + msg);
        }
    }

    /**
     * Prints a formatted message if debug is enabled.
     *
     * @param format Message format.
     * @param args Message arguments.
     */
    public static void format(String format, Object... args) {
        assert (format != null);
        if (ENABLED) {
            System.out.format("DEBUG: " + format, args);
        }
    }

    /**
     * Prints the stack trace if debug is enabled.
     *
     * @param ex Exception to print.
     */
    public static void printStackTrace(Throwable ex) {
        assert (ex != null);
        if (ENABLED) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Lê os argumentos da consola para saber se deve estar ativo ou não.
     *
     * @param args
     */
    public static void main(String[] args) {
        for (String arg : args) {
            if ("--debug".equals(arg)) {
                ENABLED = true;
            } else if ("--nodebug".equals(arg)) {
                ENABLED = false;
            }
        }
    }
}
