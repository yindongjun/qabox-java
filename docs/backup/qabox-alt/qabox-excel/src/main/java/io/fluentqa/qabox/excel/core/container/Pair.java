
package io.fluentqa.qabox.excel.core.container;

/**
 * 键值对容器对象. immutable。

 */
public class Pair<K, V> {

    private final K key;

    private final V value;

    private Integer repeatSize;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public Integer getRepeatSize() {
        return repeatSize;
    }

    public void setRepeatSize(Integer repeatSize) {
        this.repeatSize = repeatSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Pair)) {
            return false;
        } else {
            Pair<?, ?> other = (Pair) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$key = this.getKey();
                Object other$key = other.getKey();
                if (this$key == null) {
                    if (other$key != null) {
                        return false;
                    }
                } else if (!this$key.equals(other$key)) {
                    return false;
                }

                Object this$value = this.getValue();
                Object other$value = other.getValue();
                if (this$value == null) {
                    return other$value == null;
                } else return this$value.equals(other$value);
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Pair;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        return result;
    }
}
