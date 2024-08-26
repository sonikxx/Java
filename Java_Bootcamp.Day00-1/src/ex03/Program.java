import java.util.Scanner;

class Program {

    static String resAllWeek = "";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String curWeek = in.nextLine();
        int countWeek = 1;
        while (!curWeek.equals("42")) {
            if (!curWeek.equals("Week " + countWeek)) {
                System.err.println("Illegal Argument");
                in.close();
                System.exit(-1);
            } else {
                addNumber(in.nextLine());
            }
            if (countWeek != 18) {
                curWeek = in.nextLine();
                ++countWeek;
            } else {
                break;
            }
        }
        if (curWeek.equals("42"))
            --countWeek;
        for (int i = 1; i <= countWeek; ++i) {
            System.out.print("Week " + i + " ");
            for (int j = 0; j < resAllWeek.charAt(i - 1) - '0'; ++j) {
                System.out.print("=");
            }
            System.out.println(">");
        }
        in.close();
    }

    static void addNumber(String curGrade) {
        String[] grade = curGrade.split(" ");
        int min = 9;
        for (String tmp_grade : grade) {
            int n = tmp_grade.charAt(0) - '0';
            if (n < min) {
                min = n;
            }
        }
        resAllWeek += min;
    }
}
