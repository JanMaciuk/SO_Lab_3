import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

public class Requests {
    public ArrayList<Integer> requests = new ArrayList<>();

    public void generateRequests(int minIndex, int maxIndex, int requestsNumber) {
        int minRange = ThreadLocalRandom.current().nextInt(minIndex, maxIndex/2);
        int maxRange = ThreadLocalRandom.current().nextInt(minRange+2, Math.min(maxIndex, minRange+10));
        int i = 1;
        while (requestsNumber > 0) {

            if (ThreadLocalRandom.current().nextInt(1, 10) <i) { // Prawdopodobieństwo zmiany zakresu
                minRange = ThreadLocalRandom.current().nextInt(minIndex, maxIndex/2);
                maxRange = ThreadLocalRandom.current().nextInt(minRange+2, Math.min(maxIndex, minRange+10));
                i = 1;
            }
            else {i++;} // Zwiększam prawdopodobieństwo zmiany zakresu za każdym razem kiedy zakres nie został zmieniony

            int random1 = ThreadLocalRandom.current().nextInt(minRange, maxRange);
            requests.add(random1);
            requestsNumber--;


        }
    }


}
