package eapli.base.warehouse.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Square implements ValueObject {

    private Integer lsquare;

    private Integer wsquare;

    protected Square(){}

    public Square (final int lsquare, final int wsquare){
        if(lsquare > 0 && wsquare > 0){
            this.lsquare = lsquare;
            this.wsquare = wsquare;
        }else{
            throw new IllegalArgumentException("Square location is invalid");
        }
    }

    public Integer lSquare() {
        return lsquare;
    }

    public Integer wSquare() {
        return wsquare;
    }

    public static Square valueOf(final int lsquare, final int wsquare) {
        return new Square(lsquare, wsquare);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Square)) {
            return false;
        }

        final Square that = (Square) obj;
        return Objects.equals(this.lsquare, that.lsquare) && Objects.equals(this.wsquare, that.wsquare);
    }

    @Override
    public String toString() {
        return String.format("Square -> x: %d, y: %d", lsquare, wsquare);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
