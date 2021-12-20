/**
 * O clasa ce formeaza o pereche (origin, destination) intre 2 mafioti
 * @author alex
 *
 */
public class Relation {
    private Integer origin;
    private Integer destination;

    public Relation(Integer origin, Integer destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Integer getOrigin() {
        return origin;
    }
    public void setOrigin(Integer origin) {
        this.origin = origin;
    }
    public Integer getDestination() {
        return destination;
    }
    public void setDestination(Integer destination) {
        this.destination = destination;
    }
    
    @Override
    public String toString() {
        return origin.toString() + " " + destination.toString() + "\n";
    }
}
