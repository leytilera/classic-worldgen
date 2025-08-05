package dev.tilera.cwg.quadrants;

import java.util.function.Function;

public class QuadrantManager<T> {
    
    private T nw;
    private T sw;
    private T ne;
    private T se;    

    public QuadrantManager(T nw, T sw, T ne,
            T se) {
        this.nw = nw;
        this.sw = sw;
        this.ne = ne;
        this.se = se;
    }

    public T getFor(int x, int z) {
        if (x >= 0 && z >= 0) {
            return se;
        } else if (x >= 0 && z < 0) {
            return ne;
        } else if (x < 0 && z >= 0) {
            return sw;
        } else {
            return nw;
        }
    }

    public <E> QuadrantManager<E> map(Function<T, E> mapper) {
        return new QuadrantManager<E>(
                mapper.apply(nw), 
                mapper.apply(sw), 
                mapper.apply(ne), 
                mapper.apply(se)
            );
    }
    
}
