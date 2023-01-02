
package yogi_bear;


public class HighScore {
    
    private final String name;
    private final int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
    // getters
    public String getName() {return name;}
    public int getScore() {return score;}
    public static int compareByScore(HighScore hs1, HighScore hs2) {
        return Integer.compare(hs2.getScore(), hs1.getScore());
    }
}
