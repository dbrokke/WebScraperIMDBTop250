// Importing JSoup classes as well as the Random class.
package WebScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Random;

// Class that Scrapes the Top 250 IMDB charts, chooses one movie for the user to watch
// and tells them the year it was released to assist them in finding it.
public class WebScraperIMDBTop250 {

    /*
     * getMovie() method returns a String array. The first element in this string is the movie title,
     * the second element in the string is the year it was released.
     */
    String[] getMovie() {

        // returnValues is my array for storing the selected movie's title and year.
        // movieTitles and movieYears store the titles and years of the top 250 movies.
        String[] returnValues = new String[2];
        String[] movieTitles = new String[250];
        String[] movieYears = new String[250];

        // Setting the URL to the IMDB Top 250 charts.
        // Counter helps keep proper looping.
        final String url = "https://www.imdb.com/chart/top/";
        int counter = 0;

        // Try block because of the IOException thrown by the connect method.
        try {

            // Connecting to the document, timing out if it takes more than 6 seconds, and grabbing the HTML.
            // Grabbing all the body elements from the selected class.
            final Document document = Jsoup.connect(url).timeout(6000).get();
            Elements body = document.select("tbody.lister-list");

            // For each of the body elements, I get the row and go into that row to...
            for (Element e : body.select("tr")) {

                // Grab the body element's movie title and year, then add each item
                // to its proper array. Making sure to increment the counter to stay consistent.
                String title = e.select("td.posterColumn img").attr("alt");
                String year = e.select("td.titleColumn span.secondaryInfo").text();
                movieTitles[counter] = title;
                movieYears[counter] = year;
                counter++;
            }
          // Catching the IOException that can be thrown by the connect method.
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        /* Now that I have the movie list, I'm creating a random object and generating a guess
         * between 0-249. Since the arrays are 0-based, I create the guess that way so it matches
         * how the array is numbered. In my output is where I fix the 0-1 issue.
         */
        Random rand = new Random();
        int guess = rand.nextInt(250);

        // Here I set my returnValues 2-item array to contain my selected movie Title and
        // that selected movie's release year. Then I return these items.
        returnValues[0] = movieTitles[guess];
        returnValues[1] = movieYears[guess];
        return returnValues;
    }
    public static void main(String[] args)
    {
        WebScraperIMDBTop250 moviePrinter = new WebScraperIMDBTop250();
        String[] movieInfo = moviePrinter.getMovie();
        System.out.printf("MOVIE: %s\t RELEASED: %s", movieInfo[0], movieInfo[1]);
    }

}
