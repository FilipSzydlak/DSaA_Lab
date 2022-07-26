package lab2;

import java.util.Objects;

public class Link {
    public String ref;

    public Link(String ref) {
        this.ref = ref;
    }

    // in the future there will be more fields
    public String getRef() {
        return ref;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return Objects.equals(ref, link.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }

}
