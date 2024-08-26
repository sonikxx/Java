import java.util.Scanner;

class Program {
    static String[] names = new String[10];
    static int countName = 0;
    static String[] timetable = new String[10];
    static int countDate = 0;
    static int countVisit = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String curName = in.nextLine();
        while (!curName.equals(".")) {
            names[countName] = curName;
            curName = in.nextLine();
            ++countName;
        }
        String curDate = in.nextLine();
        while (!curDate.equals(".")) {
            timetable[countDate] = curDate;
            curDate = in.nextLine();
            ++countDate;
        }
        String[] visits = new String[50 * countName];
        String curVisit = in.nextLine();
        while (!curVisit.equals(".")) {
            visits[countVisit] = curVisit;
            curVisit = in.nextLine();
            ++countVisit;
        }
        int[] dates = getDate();
        printTable(dates, visits);
        in.close();
    }

    static void printTable(int[] dates, String[] visits) {
        String[] time = new String[countDate];
        String[] weekday = new String[countDate];
        for (int j = 0; j < countDate; ++j) {
            time[j] = timetable[j].split(" ")[0];
            weekday[j] = timetable[j].split(" ")[1];
        }
        int i = 0;
        int start = 0;
        while (dates[i] == 0) {
            ++start;
            ++i;
        }
        System.out.print("\t");
        while (dates[i] != 0) {
            System.out.print(time[i % countDate] + ":00 "
                    + weekday[i % countDate] + " " + (dates[i] <= 9 ? " " : "") + dates[i] + "|");
            ++i;
        }
        System.out.println();
        for (int j = 0; j < countName; ++j) {
            System.out.print(names[j] + "    ");
            for (int k = 0; k < i - start; ++k) {
                System.out.print("        "
                        + checkVisit(names[j], time[(k + start) % countDate], dates[k + start], visits)
                        + "|");
            }
            System.out.println();
        }
    }

    static int[] getDate() {
        int[] res = new int[50];
        int indexRes = 0;
        for (int week = 0; week < 5; ++week) {
            for (int i = 0; i < countDate; ++i) {
                String tmp = timetable[i];
                res[indexRes] = getDay(tmp.split(" ")[1], week);
                ++indexRes;
            }
        }
        return res;
    }

    static int getDay(String weekday, int week) {
        switch (weekday) {
            case "MO":
                return week * 7;
            case "TU":
                return week * 7 + 1;
            case "WE":
                return week * 7 + 2;
            case "THU":
                return week * 7 + 3;
            case "FR":
                return week * 7 + 4;
            default:
                return 0;
        }
    }

    static String checkVisit(String name, String time, int date, String[] visits) {
        for (int i = 0; i < countVisit; ++i) {
            String[] tmp = visits[i].split(" ");
            if (tmp[0].equals(name) && tmp[1].equals(time) && tmp[2].equals(date + "")) {
                if (tmp[3].equals("HERE"))
                    return " 1";
                else
                    return "-1";
            }
        }
        return "  ";
    }
}