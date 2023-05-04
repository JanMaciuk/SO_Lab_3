import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {
    public int physicalMemorySize;
    public boolean label;  // czy wypisywać nazwę algorytmu
    public ArrayList<Integer> requests;
    public Simulation(int physicalMemorySize, ArrayList<Integer> Requests, boolean label) { // Parametry ogólne symulacji:
        this.physicalMemorySize = physicalMemorySize;
        this.requests = Requests;
        this.label = label;

    }
    public int RAND() {
        if (label) System.out.print("RAND: ");
        int errors = 0;
        ArrayList<Integer> physicalMemory = new ArrayList<>();

        for (Integer request : requests) {
            if (!physicalMemory.contains(request)) { // jeżeli ramka jest w pamięci to nic nie robimy
                if (physicalMemory.size() < physicalMemorySize) {
                    physicalMemory.add(request); // jeżeli mamy miejsce w pamięci to dodajemy bez zastępowania
                }
                else { physicalMemory.set(ThreadLocalRandom.current().nextInt(0, physicalMemorySize), request); }
                errors++;
            }
        }
        return errors;
    }

    public int FIFO() {
        if (label) System.out.print("FIFO: ");
        int errors = 0;
        int lastReplaced = 0;
        ArrayList<Integer> physicalMemory = new ArrayList<>();

        for (Integer request : requests) {
            if (lastReplaced >= physicalMemorySize) { lastReplaced = 0; }

            if (!physicalMemory.contains(request)) { // jeżeli ramka jest w pamięci to nic nie robimy
                if (physicalMemory.size() < physicalMemorySize) {
                    physicalMemory.add(request); // jeżeli mamy miejsce w pamięci to dodajemy bez zastępowania
                }
                else { physicalMemory.set(lastReplaced, request); lastReplaced++; }
                errors++;
            }
        }
        return errors;
    }

    public int OPT() {
        if (label) System.out.print("OPT:  ");
        int errors = 0;
        ArrayList<Integer> requestsOPT = new ArrayList<>(requests);
        ArrayList<Integer> physicalMemory = new ArrayList<>();
        int indexToReplace = 0;
        for (Integer request : requests) {
            if (!physicalMemory.contains(request)) {
                if (physicalMemory.size() < physicalMemorySize) {
                    physicalMemory.add(request); // jeżeli mamy miejsce w pamięci to dodajemy bez zastępowania
                }
                else {
                    for (Integer memoryElement : physicalMemory) {
                        if (requestsOPT.indexOf(memoryElement) > indexToReplace) {
                            indexToReplace = physicalMemory.indexOf(memoryElement);
                        }
                    }
                    physicalMemory.set(indexToReplace, request);
                }
                errors++;
            }
            requestsOPT.remove(request); //Usuwam wykonany request (żeby potem nie psuł wyszukiwania indeksu do zastąpienia)
        }
        return errors;
    }

    public int LRU() {
        if (label) System.out.print("LRU:  ");
        int errors = 0;
        ArrayList<Integer> physicalMemory = new ArrayList<>();
        int currentRequestIndex = -1; // indeks aktualnie przetwarzanego requestu, inicjalizowany z -1, bo zwiększany przed użyciem.

        for (Integer request : requests) {
            int indexOfRequestToReplace = requests.size()-1;
            currentRequestIndex++;
            if (!physicalMemory.contains(request)) { // jeżeli ramka jest w pamięci to nic nie robimy
                if (physicalMemory.size() < physicalMemorySize) {
                    physicalMemory.add(request); // jeżeli mamy miejsce w pamięci to dodajemy bez zastępowania
                }
                else {
                    // szukam indeksu elementu z pamięci fizycznej, który najdłużej nie był używany
                    for (Integer memoryElement : physicalMemory) {
                        for (int i = currentRequestIndex; i >= 0; i--) {
                            if (requests.get(i).equals(memoryElement)) {
                                if (i < indexOfRequestToReplace) { indexOfRequestToReplace = i; }
                                break;
                            }
                        }
                    }
                    physicalMemory.set(physicalMemory.indexOf(requests.get(indexOfRequestToReplace)), request);
                }
                errors++;
            }
        }
        return errors;
    }

    public int ALRU() {
        if (label) System.out.print("ALRU: ");
        int errors = 0;
        int lastReplaced = 0;
        boolean[] szansa = new boolean[physicalMemorySize];
        ArrayList<Integer> physicalMemory = new ArrayList<>();

        for (Integer request : requests) {
            if (lastReplaced >= physicalMemorySize) {
                lastReplaced = 0;
            }

            if (!physicalMemory.contains(request)) { // jeżeli ramka jest w pamięci to nic nie robimy
                if (physicalMemory.size() < physicalMemorySize) {
                    physicalMemory.add(request); // jeżeli mamy miejsce w pamięci to dodajemy bez zastępowania
                    szansa[physicalMemory.indexOf(request)] = true; // Ustawiam bit drugiej szansy na 1 przy dodawaniu.
                }
                else {
                    while (szansa[lastReplaced]) { //Ustawiam bit drugiej szansy na 0, dopóki nie znajdę ramki która już ma 0.
                        szansa[lastReplaced] = false;
                        lastReplaced++;
                        if (lastReplaced >= physicalMemorySize) {
                            lastReplaced = 0;
                        }
                    }
                    physicalMemory.set(lastReplaced, request);
                    szansa[lastReplaced] = true; // Ustawiam bit drugiej szansy na 1 przy dodawaniu.
                    lastReplaced++;
                }
                errors++;
            }
        }
        return errors;
    }
}
