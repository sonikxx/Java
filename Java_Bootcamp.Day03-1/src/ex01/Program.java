public class Program {
    public static void main(String[] args) {
        if (args.length == 0 || (args.length == 1 && !args[0].startsWith("--count="))) {
            System.err.println("Usage: java Program --count=10");
            System.exit(-1);
        }
        Integer count = Integer.parseInt(args[0].split("=")[1]);
        if (count <= 0) {
            System.err.println("Count must be greater than 0");
            System.exit(-1);
        }
        Thread threadEgg = new Thread(new ThreadForPrint("Egg", count), "Egg");
        Thread threadHen = new Thread(new ThreadForPrint("Hen", count), "Hen");
        threadEgg.start();
        threadHen.start();
    }

    public static synchronized void syncPrint(String print, Integer count) {
        while (true) {
            System.out.println(print);
            try {
                Program.class.notify();
                --count;
                if (count == 0) {
                    break;
                }
                Program.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
