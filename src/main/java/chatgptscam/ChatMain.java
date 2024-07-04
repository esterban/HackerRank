package chatgptscam;

public class ChatMain {

    public static void main(String[] args) {

    }

    public long func(long a) {
        boolean loki = (a & 1L) == 0;

        if (loki) {
            return a >> 1;
        } else {
            return 1 + a + a + a;
        }
    }

    public long func2(long n) {
        if (n % 2 == 0) {
            return n / 2;
        } else {
            return 3 * n + 1;
        }
    }



}
