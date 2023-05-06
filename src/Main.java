public class Main {  //wyniki: https://docs.google.com/spreadsheets/d/1QiDLjMX_lORQf7Wva6eKuCVYIUOWK37q4Ch1MktIQm0
    static int liczbaRequestow = 10000;
    static int rozmiarPamieciWitrualnej = 200;
    static int rozmiarPamieciFizycznej = 20;
    static boolean algorytmyPodpisane = true;
    public static void main(String[] args) {
        Requests requesty = new Requests();
        requesty.generateRequests(0, rozmiarPamieciWitrualnej, liczbaRequestow);
        System.out.println(requesty.requests);

        Simulation symulacja = new Simulation(rozmiarPamieciFizycznej, requesty.requests, algorytmyPodpisane);
        System.out.println(symulacja.RAND());
        System.out.println(symulacja.FIFO());
        System.out.println(symulacja.OPT());
        System.out.println(symulacja.LRU());
        System.out.println(symulacja.ALRU());

    }
}