public class Program {
    public static void main(String[] args) {
        boolean isDev = false;
        if (args.length > 0 && args[0].equals("--profile=dev")) {
            isDev = true;
        }
        Menu menu = new Menu(isDev);
        menu.run();
    }
}