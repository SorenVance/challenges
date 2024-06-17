package org.kramer.hungarian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Algorithm {
    public static boolean ckmin(int[] a, int b) {
      if (b < a[0]) {
        a[0] = b;
        return true;
      }
      return false;
    }

    public static List<Integer> hungarian(int[][] C) {
      int J = C.length, W = C[0].length;
      assert(J <= W);
      int[] job = new int[W + 1];
      Arrays.fill(job, -1);
      int[] ys = new int[J];
      int[] yt = new int[W + 1];
      List<Integer> answers = new ArrayList<>();
      int inf = Integer.MAX_VALUE;
      for (int j_cur = 0; j_cur < J; ++j_cur) {
        int w_cur = W;
        job[w_cur] = j_cur;
        int[] min_to = new int[W + 1];
        Arrays.fill(min_to, inf);
        int[] prv = new int[W + 1];
        Arrays.fill(prv, -1);
        boolean[] in_Z = new boolean[W + 1];
        while (job[w_cur] != -1) {
          in_Z[w_cur] = true;
          int j = job[w_cur];
          int delta = inf;
          int w_next = 0;
          for (int w = 0; w < W; ++w) {
            if (!in_Z[w]) {
              if (ckmin(min_to, C[j][w] - ys[j] - yt[w])) {
                prv[w] = w_cur;
              }
              if (min_to[w] < delta) {
                delta = min_to[w];
                w_next = w;
              }
            }
          }
          for (int w = 0; w <= W; ++w) {
            if (in_Z[w]) {
              ys[job[w]] += delta;
              yt[w] -= delta;
            } else {
              min_to[w] -= delta;
            }
          }
          w_cur = w_next;
        }
        for (int w; w_cur != W; w_cur = w) {
          job[w_cur] = job[w = prv[w_cur]];
        }
        answers.add(-yt[W]);
      }
      return answers;
    }

    public static void sanityCheckHungarian() {
      int[][] costs = {{8, 5, 9}, {4, 2, 4}, {7, 3, 8}};
      assert(hungarian(costs).equals(Arrays.asList(5, 9, 15)));
      System.err.println("Sanity check passed.");
    }

    public static void cordonBleu() {
      Scanner scanner = new Scanner(System.in);
      int N = scanner.nextInt();
      int M = scanner.nextInt();
      int[][] bottles = new int[N][2];
      int[][] couriers = new int[M][2];
      int[] rest = new int[2];
      for (int i = 0; i < N; i++) {
        bottles[i][0] = scanner.nextInt();
        bottles[i][1] = scanner.nextInt();
      }
      for (int i = 0; i < M; i++) {
        couriers[i][0] = scanner.nextInt();
        couriers[i][1] = scanner.nextInt();
      }
      rest[0] = scanner.nextInt();
      rest[1] = scanner.nextInt();
      int[][] costs = new int[N][N + M - 1];
      for (int b = 0; b < N; b++) {
        for (int c = 0; c < M; c++) {
          costs[b][c] = Math.abs(couriers[c][0] - bottles[b][0]) + Math.abs(couriers[c][1] - bottles[b][1]) + Math.abs(bottles[b][0] - rest[0]) + Math.abs(bottles[b][1] - rest[1]);
        }
        for (int i = 0; i < N - 1; i++) {
          costs[b][i + M] = 2 * (Math.abs(bottles[b][0] - rest[0]) + Math.abs(bottles[b][1] - rest[1]));
        }
      }
      System.out.println(hungarian(costs).get(hungarian(costs).size() - 1));
    }

    public static void main(String[] args) {
      sanityCheckHungarian();
      cordonBleu();
    }

}
