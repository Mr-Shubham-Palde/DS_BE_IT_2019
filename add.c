#include <stdio.h>
#include <mpi.h>

int main(int argc, char *argv[])
{
    int rank, size;
    int N = 20;
    int num[20], local[20], sums[20];

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (N % size != 0) {
        if (rank == 0)
            printf("N must be divisible by number of processors\n");
        MPI_Finalize();
        return 0;
    }

    int chunk = N / size;

    if (rank == 0) {
        for (int i = 0; i < N; i++)
            num[i] = i + 1;
    }

    MPI_Scatter(num, chunk, MPI_INT,
                local, chunk, MPI_INT,
                0, MPI_COMM_WORLD);

    int local_sum = 0;
    for (int i = 0; i < chunk; i++)
        local_sum += local[i];

    MPI_Gather(&local_sum, 1, MPI_INT,
               sums, 1, MPI_INT,
               0, MPI_COMM_WORLD);

    if (rank == 0) {
        int total = 0;

        printf("Intermediate sums:\n");
        for (int i = 0; i < size; i++) {
            printf("local sum at rank %d is %d\n", i, sums[i]);
            total += sums[i];
        }

        printf("final sum = %d\n", total);
    }

    MPI_Finalize();
    return 0;
}

