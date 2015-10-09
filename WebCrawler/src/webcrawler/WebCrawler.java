package webcrawler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.GregorianCalendar;


public final class WebCrawler extends TimerTask {

    private final static long fONCE_PER_SECOND = 1000;
    private final static long fONCE_PER_MINUTE = 1000 * 60;
    private final static long fONCE_PER_HOUR = 1000 * 60 * 60;
    private final static long fONCE_PER_DAY = 1000 * 60 * 60 * 24;
     
    /**
     * Construct and use a TimerTask and Timer.
     *
     * @param args
     */
    public static void main(String[] args) {
        TimerTask crawlTheWeb = new WebCrawler();
        
        
    //perform the task at a given time interval starting now
        Timer timer = new Timer();
        Calendar now = new GregorianCalendar();
        timer.scheduleAtFixedRate(crawlTheWeb, now.getTime(), fONCE_PER_MINUTE);
    }

    /**
     * Implements TimerTask's abstract run method.
     */
    @Override
    public void run() {
        //DO STUFF
        System.out.println("Crawling the web...");
        new Engine().start();
    }

}
