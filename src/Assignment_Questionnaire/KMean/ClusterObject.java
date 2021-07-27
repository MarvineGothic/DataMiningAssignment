package Assignment_Questionnaire.KMean;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class ClusterObject {
    private Map<String, Double> attributes;
    private Object clazz;

    private ClusterObject() {
        attributes = new TreeMap<>();
    }

    public static Builder newBuilder() {
        return new ClusterObject().new Builder();
    }

    @Override
    public String toString() {
        String result = String.format("Class: %-15s ", clazz);
        for (Map.Entry attr : attributes.entrySet()) {
            result = result.concat(String.format("%s %.2f  ", attr.getKey(), attr.getValue()));
        }
        return result;
    }

    public Object getClazz() {
        return clazz;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClusterObject object = (ClusterObject) o;

        return attributes != null ? attributes.equals(object.attributes) : object.attributes == null;
    }

    @Override
    public int hashCode() {
        return attributes != null ? attributes.hashCode() : 0;
    }

    public class Builder {
        private Builder() {
        }

        public Builder setAttribute(String name, double value) {
            attributes.put(name, value);
            return this;
        }

        public Builder setClazz(Object clazz) {
            ClusterObject.this.clazz = clazz;
            return this;
        }

        public ClusterObject build() {
            return ClusterObject.this;
        }
    }
}