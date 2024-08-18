public class SortCounters {
    private int comp;
    private int mov;
    private int time;

    public SortCounters(int comp, int mov, int time) {
        this.comp = comp;
        this.mov = mov;
        this.time = time;
    }

    public int getComp() {
        return comp;
    }

    public int getMov() {
        return mov;
    }

    public int getTime() {
        return time;
    }
}
