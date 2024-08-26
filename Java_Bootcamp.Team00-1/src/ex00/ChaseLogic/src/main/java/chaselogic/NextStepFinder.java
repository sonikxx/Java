package chaselogic;

import java.util.ArrayList;
import java.util.List;

public class NextStepFinder implements Runnable {

    public NextStepFinder(Coordinate currentPosition, Coordinate targetPosition, char[][] field, int rows, int cols,
                          char wallChar, char enemyChar, char goalChar, char playerChar) {
        this.currentPosition = currentPosition;
        this.targetPosition = targetPosition;
        this.field = field;
        this.rows = rows;
        this.cols = cols;
        this.wallChar = wallChar;
        this.enemyChar = enemyChar;
        this.goalChar = goalChar;
        this.playerChar = playerChar;
        this.countPredictionSteps = 1;
    }

    public NextStepFinder(Coordinate currentPosition, Coordinate targetPosition, char[][] field, int rows, int cols,
                          char wallChar, char enemyChar, char goalChar, char playerChar, int countPredictionSteps) {
        this.currentPosition = currentPosition;
        this.targetPosition = targetPosition;
        this.field = field;
        this.rows = rows;
        this.cols = cols;
        this.wallChar = wallChar;
        this.enemyChar = enemyChar;
        this.goalChar = goalChar;
        this.playerChar = playerChar;
        this.countPredictionSteps = countPredictionSteps;
    }

    @Override
    public void run() {
        if (currentPosition.equals(targetPosition)) {
            nextPosition = targetPosition;
            return;
        }

        if (isWinStep()) {
            return;
        }

        Node startNode = new Node(0, currentPosition, targetPosition, null);
        List<Node> visitedNodes = new ArrayList<>();
        List<Node> waitingNodes = new ArrayList<>(getNeighbors(startNode, startNode.weight));
        while (!waitingNodes.isEmpty()) {
            Node currentNode = waitingNodes.remove(0);
            if (visitedNodes.contains(currentNode)) {
                continue;
            }
            visitedNodes.add(currentNode);
            if (currentNode.position.equals(targetPosition)) {
                nextPosition = currentNode.position;
                break;
            }
            waitingNodes.addAll(getNeighbors(currentNode, currentNode.weight));
        }

        int minWight = Integer.MAX_VALUE;
        Node nextPositionNode = null;

        while (!visitedNodes.isEmpty()) {
            nextPositionNode = visitedNodes.remove(0);
            if (nextPositionNode.weight < minWight) {
                minWight = nextPositionNode.weight;
                nextPosition = nextPositionNode.position;
            }
        }

        nextPosition = getPositionFromPath(nextPositionNode);
        if (nextPosition == null) {
            nextPosition = currentPosition;
        }
    }

    public Coordinate getNextStep() {
        return nextPosition;
    }

    private final Coordinate currentPosition;
    private Coordinate nextPosition;
    private Coordinate targetPosition;
    private final char[][] field;
    private final int rows;
    private final int cols;
    private final char wallChar;
    private final char enemyChar;
    private final char goalChar;
    private final char playerChar;
    private final int countPredictionSteps;

    private static class Node {
        public Coordinate position;
        public Node parent;
        public int weight;
        public int distanceToTarget;
        public int distanceFromStart;

        public Node(int distanceFromStart, Coordinate position, Coordinate targetPosition, Node parent) {
            this.position = position;
            this.parent = parent;
            this.distanceFromStart = distanceFromStart;
            this.distanceToTarget = Math.abs(position.x - targetPosition.x) + Math.abs(position.y - targetPosition.y);
            this.weight = distanceFromStart + distanceToTarget;
        }
    }

    private List<Node> getNeighbors(Node node, int weight) {
        List<Node> neighbors = new ArrayList<>();
        int[][] movements = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // left, right, up, down
        for (int[] movement : movements) {
            int newX = node.position.x + movement[0];
            int newY = node.position.y + movement[1];
            boolean outOfBounds = newX < 0 || newX >= rows || newY < 0 || newY >= cols;
            boolean canMove = !outOfBounds && field[newX][newY] != wallChar && field[newX][newY] != enemyChar && field[newX][newY] != goalChar;
            if (canMove) {
                Node newNode = new Node(node.distanceFromStart + 1, new Coordinate(newX, newY), targetPosition, node);
                if (weight >= newNode.weight) {
                    neighbors.add(newNode);
                }
            }
        }
        int minWight = Integer.MAX_VALUE;
        for (Node neighbor : neighbors) {
            if (neighbor.weight < minWight) {
                minWight = neighbor.weight;
            }
        }
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).weight > minWight) {
                neighbors.remove(i);
                i--;
            }
        }
        return neighbors;
    }

    private Coordinate getPositionFromPath(Node node) {
        if (node == null) {
            return null;
        }
        if (node.parent == null) {
            return node.position;
        }
        Node prevNode = node;
        List<Node> path = new ArrayList<>();
        while (prevNode.parent != null) {
            path.add(prevNode);
            prevNode = prevNode.parent;
        }
        int pathSize = path.size();
        if (pathSize > countPredictionSteps) {
            pathSize = countPredictionSteps;
        }
        Node nextPositionNode = null;
        for (int i = 0; i < pathSize; i++) {
            nextPositionNode = path.get(path.size() - 1);
            path.remove(path.size() - 1);
        }

        if (nextPositionNode == null) {
            return currentPosition;
        }
        return nextPositionNode.position;
    }

    private boolean isWinStep() {
        int[][] movements = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // left, right, up, down
        for (int[] movement : movements) {
            int newX = currentPosition.x + movement[0];
            int newY = currentPosition.y + movement[1];
            boolean outOfBounds = newX < 0 || newX >= rows || newY < 0 || newY >= cols;
            if (outOfBounds) {
                continue;
            }
            if (field[newX][newY] == playerChar) {
                nextPosition = new Coordinate(newX, newY);
                return true;
            }
        }
        return false;
    }
}
