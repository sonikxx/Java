package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import chaselogic.Coordinate;
import chaselogic.NextStepFinder;

public class Game {
    private Settings settings;
    private char[][] mapGame;
    private Player player;
    private Enemies enemies;
    private Goal goal;
    private Walls walls;

    public Game(Settings settings) {
        this.settings = settings;
        if (settings.wallsCount < 1)
            throw new IllegalParametersException("You can`t play without walls");
        else if (settings.enemiesCount < 1)
            throw new IllegalParametersException("You can`t play without enemies");
        else if (settings.size < 5)
            throw new IllegalParametersException("Very small size");
        else if (settings.size > 100)
            throw new IllegalParametersException("Very big size");
        else if (settings.size * settings.size < settings.enemiesCount + settings.wallsCount + 10)
            throw new IllegalParametersException("Very small size for enemies and walls");
        this.settings.size = settings.size;
        this.settings.profile = settings.profile;
        this.mapGame = new char[settings.size][settings.size];
        this.player = new Player(settings.size, settings.playerChar, settings.playerColor);
        this.goal = new Goal(settings.goalChar, settings.goalColor);
        this.walls = new Walls(settings.wallChar, settings.wallColor, settings.wallsCount);
        this.enemies = new Enemies(settings.enemyChar, settings.enemyColor, settings.enemiesCount);
    }

    public void play() {
        try {
            Status next = Status.PLAYING;
            Scanner scanner = new Scanner(System.in);
            print();
            while (next == Status.PLAYING) {
                next = nextStepPlayer(scanner);
                if (next == Status.END || next == Status.LOSE) {
                    break;
                }
                print();
                if (next == Status.PLAYING) {
                    next = nextStepEnemy(settings.profile);
                    if (next == Status.PLAYING && settings.profile.equals("dev")) {
                        char answerPlayer = scanner.next().charAt(0);
                        while (answerPlayer != '8') {
                            System.err.println("Enter 8 to confirm the move enemy");
                            answerPlayer = scanner.next().charAt(0);
                        }
                        print();
                    }
                }
            }
            if (next == Status.WIN)
                System.out.println("You win :)");
            else if (next == Status.LOSE)
                System.out.println("You lose :(");
            else if (next == Status.END)
                System.out.println("Bye!");
        } catch (

        Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private void print() {
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < settings.size; ++i) {
            for (int j = 0; j < settings.size; ++j) {
                mapGame[i][j] = settings.emptyChar;
            }
        }
        mapGame[goal.coord.x][goal.coord.y] = goal.symbol;
        mapGame[player.coord.x][player.coord.y] = player.symbol;
        for (int i = 0; i < walls.count; ++i) {
            mapGame[walls.coord[i].x][walls.coord[i].y] = walls.symbol;
        }
        for (int i = 0; i < enemies.count; ++i) {
            mapGame[enemies.coord[i].x][enemies.coord[i].y] = enemies.symbol;
        }
        for (int i = 0; i < settings.size; i++) {
            for (int j = 0; j < settings.size; j++) {
                if (mapGame[i][j] == settings.emptyChar)
                    System.out.print(Ansi.colorize(String.valueOf(settings.emptyChar), settings.emptyColor));
                else if (mapGame[i][j] == goal.symbol)
                    System.out.print(Ansi.colorize(String.valueOf(goal.symbol), goal.color, settings.textColor));
                else if (mapGame[i][j] == enemies.symbol)
                    System.out.print(Ansi.colorize(String.valueOf(enemies.symbol), enemies.color, settings.textColor));
                else if (mapGame[i][j] == walls.symbol)
                    System.out.print(Ansi.colorize(String.valueOf(walls.symbol), walls.color, settings.textColor));
                else if (mapGame[i][j] == player.symbol)
                    System.out.print(Ansi.colorize(String.valueOf(player.symbol), player.color, settings.textColor));
            }
            System.out.println();
        }
    }

    private Status nextStepPlayer(Scanner scanner) {
        char next = scanner.next().charAt(0);
        Status res = Status.PLAYING;
        if (player.coord.y + 1 < settings.size && mapGame[player.coord.x][player.coord.y + 1] != settings.emptyChar &&
                player.coord.y - 1 >= 0 && mapGame[player.coord.x][player.coord.y - 1] != settings.emptyChar &&
                player.coord.x + 1 < settings.size && mapGame[player.coord.x + 1][player.coord.y] != settings.emptyChar
                &&
                player.coord.x - 1 >= 0 && mapGame[player.coord.x - 1][player.coord.y] != settings.emptyChar)
            res = Status.LOSE;
        else {
            switch (next) {
                case 'W':
                    res = player.stepW();
                    break;
                case 'S':
                    res = player.stepS();
                    break;
                case 'D':
                    res = player.stepD();
                    break;
                case 'A':
                    res = player.stepA();
                    break;
                case '9':
                    res = Status.END;
                    break;
                default:
                    res = Status.INNCORRECT;
                    break;
            }
        }
        if (res == Status.INNCORRECT) {
            System.err.println("You can`t make this move :(");
            System.err.println("Try another move: W - up, S - down, A - left, D - right");
            res = nextStepPlayer(scanner);
        } else if (res == Status.PLAYING && player.coord.x == goal.coord.x && player.coord.y == goal.coord.y)
            res = Status.WIN;
        return res;
    }

    private Status nextStepEnemy(String profile) {
        List<NextStepFinder> nextStepFinders = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        NextStepFinder target = new NextStepFinder(player.coord, goal.coord, mapGame, settings.size, settings.size,
                walls.symbol, enemies.symbol, goal.symbol, player.symbol, 3);
        target.run();
        Coordinate targetCoordinate = target.getNextStep();

        for (Coordinate enemyCoordinate : enemies.coord) {
            NextStepFinder nextStepFinder = new NextStepFinder(enemyCoordinate, targetCoordinate, mapGame,
                    settings.size, settings.size,
                    walls.symbol, goal.symbol, enemies.symbol, player.symbol);
            nextStepFinders.add(nextStepFinder);
            Thread t = new Thread(nextStepFinder);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < nextStepFinders.size(); i++) {
            if (mapGame[nextStepFinders.get(i).getNextStep().x][nextStepFinders.get(i)
                    .getNextStep().y] == enemies.symbol
                    && !enemies.coord[i].equals(nextStepFinders.get(i).getNextStep())) {
                nextStepFinders.set(i, new NextStepFinder(enemies.coord[i], targetCoordinate, mapGame, settings.size,
                        settings.size, walls.symbol, enemies.symbol, goal.symbol, player.symbol));
                nextStepFinders.get(i).run();
                i--;
                continue;
            }
            mapGame[enemies.coord[i].x][enemies.coord[i].y] = settings.emptyChar;
            mapGame[nextStepFinders.get(i).getNextStep().x][nextStepFinders.get(i).getNextStep().y] = enemies.symbol;
            enemies.coord[i] = nextStepFinders.get(i).getNextStep();
            print();
            if (profile.equals("dev")) {
                System.err.println("Start coordinate: " + enemies.coord[i].x + " " + enemies.coord[i].y);
                System.err.println("Next coordinate: " + nextStepFinders.get(i).getNextStep().x + " " +
                        nextStepFinders.get(i).getNextStep().y);
            }
            if (enemies.coord[i].equals(player.coord)) {
                return Status.LOSE;
            }
        }

        return Status.PLAYING;
    }

    private class Player {
        private Coordinate coord;
        private char symbol;
        private Attribute color;

        private Player(int size, char symbol, Attribute color) {
            int x = new Random().nextInt(size / 5 * 4 - size / 2 + 1) + size / 2;
            int y = new Random().nextInt(size - 1) + 1;
            this.coord = new Coordinate(x, y);
            this.symbol = symbol;
            this.color = color;
        }

        private Status stepA() {
            Status res = Status.INNCORRECT;
            if (this.coord.y > 0 && checkPosition(this.coord.x, this.coord.y - 1)) {
                --this.coord.y;
                res = Status.PLAYING;
            }
            return res;
        }

        private Status stepW() {
            Status res = Status.INNCORRECT;
            if (this.coord.x > 0 && checkPosition(this.coord.x - 1, this.coord.y)) {
                --this.coord.x;
                res = Status.PLAYING;
            }
            return res;
        }

        private Status stepS() {
            Status res = Status.INNCORRECT;
            if (this.coord.x >= 0 && this.coord.x < Game.this.settings.size - 1
                    && checkPosition(this.coord.x + 1, this.coord.y)) {
                ++this.coord.x;
                res = Status.PLAYING;
            }
            return res;
        }

        private Status stepD() {
            Status res = Status.INNCORRECT;
            if (this.coord.y >= 0 && this.coord.y < Game.this.settings.size - 1
                    && checkPosition(this.coord.x, this.coord.y + 1)) {
                ++this.coord.y;
                res = Status.PLAYING;
            }
            return res;
        }

        private boolean nearby(int x, int y) {
            return (Math.abs(this.coord.x - x) <= 1 && Math.abs(this.coord.y - y) <= 1);
        }

        private boolean checkPosition(int x, int y) {
            return (x >= 0 && x < Game.this.settings.size && y >= 0 && y < Game.this.settings.size
                    && (Game.this.mapGame[x][y] == Game.this.settings.emptyChar
                            || Game.this.mapGame[x][y] == goal.symbol));
        }

    }

    private class Enemies {
        private char symbol;
        private Attribute color;
        private int count;
        private Coordinate[] coord;

        private Enemies(char symbol, Attribute color, int count) {
            this.symbol = symbol;
            this.color = color;
            this.count = count;
            this.coord = new Coordinate[count];
            for (int i = 0; i < count; i++) {
                int x = new Random().nextInt(Game.this.settings.size);
                int y = new Random().nextInt(Game.this.settings.size);
                while (Game.this.player.nearby(x, y) || Game.this.goal.nearby(x, y)
                        || Game.this.walls.nearby(x, y) || this.nearbyBefore(x, y, i)) {
                    x = new Random().nextInt(Game.this.settings.size);
                    y = new Random().nextInt(Game.this.settings.size);
                }
                this.coord[i] = new Coordinate(x, y);
            }
        }

        private boolean nearbyBefore(int x, int y, int length) {
            for (int i = 0; i < length; i++) {
                if (this.coord[i].x == x && this.coord[i].y == y)
                    return true;
            }
            return false;
        }
    }

    private class Goal {
        private char symbol;
        private Attribute color;
        private Coordinate coord;

        private Goal(char symbol, Attribute color) {
            this.symbol = symbol;
            this.color = color;
            this.coord = new Coordinate(0, Game.this.settings.size / 2);
        }

        private boolean nearby(int x, int y) {
            return (this.coord.x == x && this.coord.y == y)
                    || (this.coord.x == x && (this.coord.y == y - 1 || this.coord.y == y + 1))
                    || (this.coord.y == y && (this.coord.x == x - 1 || this.coord.x == x + 1));
        }
    }

    private class Walls {
        private char symbol;
        private Attribute color;
        private int count;
        private Coordinate[] coord;

        private Walls(char symbol, Attribute color, int count) {
            this.symbol = symbol;
            this.color = color;
            this.count = count;
            this.coord = new Coordinate[count];

            for (int i = 0; i < count; i++) {
                int x = new Random().nextInt(Game.this.settings.size);
                int y = new Random().nextInt(Game.this.settings.size);
                while (Game.this.player.nearby(x, y) || this.nearbyBefore(x, y, i) || Game.this.goal.nearby(x, y)) {
                    x = new Random().nextInt(Game.this.settings.size);
                    y = new Random().nextInt(Game.this.settings.size);
                }
                this.coord[i] = new Coordinate(x, y);
            }
        }

        private boolean nearbyBefore(int x, int y, int length) {
            for (int i = 0; i < length; i++) {
                if (this.coord[i].x == x && this.coord[i].y == y)
                    return true;
            }
            return false;
        }

        private boolean nearby(int x, int y) {
            for (int i = 0; i < this.count; i++) {
                if (Math.abs(this.coord[i].x - x) <= 1 && Math.abs(this.coord[i].y - y) <= 1)
                    return true;
            }
            return false;
        }
    }

    public enum Status {
        WIN, LOSE, PLAYING, INNCORRECT, END
    }
}