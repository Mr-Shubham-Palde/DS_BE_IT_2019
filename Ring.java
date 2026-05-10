import java.util.*;

public class Ring {

    static int[] id;
    static boolean[] alive;
    static int n, coordinator;

    static void showRing() {
        System.out.println("\nCurrent Ring Status:");
        for (int i = 0; i < n; i++)
            System.out.println("  Index " + i + " | Process " + id[i] + " | " +
                (alive[i] ? "[ALIVE]" : "[DEAD] ") + (id[i] == coordinator ? " <-- Coordinator" : ""));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        id = new int[n];
        alive = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for process " + i + ": ");
            id[i] = sc.nextInt();
            alive[i] = true;
        }

        Arrays.sort(id);
        coordinator = id[n - 1];
        System.out.println("\nInitial Coordinator: Process " + coordinator);
        showRing();

        int choice;
        while (true) {
            System.out.println("\n1. Start Election\n2. Crash a process\n3. Quit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter index of process starting the election: ");
                int init = sc.nextInt();
                if (init < 0 || init >= n || !alive[init]) {
                    System.out.println("Invalid or crashed process.");
                    continue;
                }

                int max = id[init], current = (init + 1) % n;
                System.out.println("\n--- Election Message Passing ---");
                while (current != init) {
                    if (alive[current]) {
                        System.out.println("Process " + id[init] + " -> Process " + id[current] + " (passing election message)");
                        if (id[current] > max) max = id[current];
                    } else {
                        System.out.println("Process " + id[current] + " is DEAD - skipped");
                    }
                    current = (current + 1) % n;
                }
                System.out.println("Message returns to initiator Process " + id[init]);
                coordinator = max;
                System.out.println("\n*** New Coordinator: Process " + coordinator + " (highest ID in ring wins - Ring Rule) ***");
                showRing();

            } else if (choice == 2) {
                showRing();
                System.out.print("Enter index of process to crash: ");
                int cp = sc.nextInt();
                if (cp < 0 || cp >= n) System.out.println("Invalid index.");
                else if (!alive[cp]) System.out.println("Process " + id[cp] + " is already crashed.");
                else {
                    alive[cp] = false;
                    System.out.println("Process " + id[cp] + " has been crashed.");
                    if (id[cp] == coordinator) System.out.println("Coordinator crashed! Start an election.");
                    showRing();
                }

            } else if (choice == 3) {
                System.out.println("Program terminated.");
                sc.close();
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
