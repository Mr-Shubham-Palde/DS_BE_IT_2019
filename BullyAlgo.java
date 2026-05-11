import java.util.*;

public class BullyAlgo {

    int coordinator;
    int[] process; // 1 = alive, 0 = crashed

    // Election triggered when coordinator crashes
    public void election(int n, int initiator) {
        System.out.println("\n--- Election Started by Process " + initiator + " ---");

        // Show which processes are alive from initiator onwards
        System.out.println("Processes calling for election (from initiator up):");
        for (int i = initiator - 1; i < n; i++) {
            if (process[i] == 1)
                System.out.println("  Process " + (i + 1) + " is ALIVE - participates");
            else
                System.out.println("  Process " + (i + 1) + " is DEAD - skipped");
        }

        // Check if all processes are dead
        int aliveCount = 0;
        for (int i = 0; i < n; i++)
            if (process[i] == 1) aliveCount++;

        if (aliveCount == 0) {
            System.out.println("\n*** All Processes Are Crashed - No Election Possible ***");
            return;
        }

        // Highest alive process wins (bully rule)
        for (int i = n - 1; i >= 0; i--) {
            if (process[i] == 1) {
                coordinator = i + 1;
                System.out.println("\n*** New Coordinator is Process " + coordinator +
                        " (highest alive ID wins - Bully Rule) ***");
                return;
            }
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        process = new int[n];

        // All processes start alive
        for (int i = 0; i < n; i++) process[i] = 1;
        coordinator = n; // highest ID is coordinator by default
        System.out.println("All processes alive. Initial Coordinator: Process " + coordinator);

        int choice;
        do {
            // Show current state before every menu
            System.out.print("\nAlive processes: ");
            for (int i = 0; i < n; i++)
                if (process[i] == 1) System.out.print((i + 1) + " ");
            System.out.println("\nCurrent Coordinator: Process " + coordinator);

            System.out.println("\n1. Crash a process");
            System.out.println("2. Recover a process");
            System.out.println("3. Display current coordinator");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter process to crash: ");
                    int cp = sc.nextInt();
                    if (cp < 1 || cp > n) {
                        System.out.println("Invalid process ID.");
                    } else if (process[cp - 1] == 0) {
                        System.out.println("Process " + cp + " is already crashed.");
                    } else {
                        process[cp - 1] = 0;
                        System.out.println("Process " + cp + " has been crashed.");
                        if (cp == coordinator) {
                            // Coordinator crashed - trigger election
                            System.out.println("Coordinator crashed! Triggering election...");
                            System.out.print("Enter initiator process: ");
                            int init = sc.nextInt();
                            if (init < 1 || init > n || process[init - 1] == 0)
                                System.out.println("Invalid initiator.");
                            else
                                election(n, init);
                        }
                    }
                    break;

                case 2:
                    // Show crashed processes
                    System.out.print("Crashed processes: ");
                    for (int i = 0; i < n; i++)
                        if (process[i] == 0) System.out.print((i + 1) + " ");
                    System.out.print("\nEnter process to recover: ");
                    int rp = sc.nextInt();

                    if (rp < 1 || rp > n) {
                        System.out.println("Invalid process ID.");
                    } else if (process[rp - 1] == 1) {
                        System.out.println("Process " + rp + " is not crashed.");
                    } else {
                        process[rp - 1] = 1;
                        System.out.println("Process " + rp + " has recovered.");
                        // Bully rule - if recovered process has higher ID, it takes over
                        if (rp > coordinator) {
                            coordinator = rp;
                            System.out.println("Process " + rp +
                                    " has higher ID than current coordinator - becomes new Coordinator.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Current Coordinator: Process " + coordinator);
                    break;

                case 4:
                    System.out.println("Exiting.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }

    public static void main(String[] args) {
        new BullyAlgo().run();
    }
}