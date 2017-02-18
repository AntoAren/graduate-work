package by.bsu.zakharankou.restservices.controller.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.List;

/**
 * General view to define list of the specified elements.
 * Contains list of elements, count of all elements and sort options.
 * @param <T> the type of elements in this list
 */
public class ListView<T> {

    private final List<T> list;
    private final long totalCount;
    private CustomSort sort;

    public ListView(final List<T> list) {
        this(list, list.size());
    }

    public ListView(final List<T> list, final long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public ListView(final List<T> list, final long totalCount, final Sort sort) {
        this.list = list;
        this.totalCount = totalCount;
        this.sort = new CustomSort(sort);
    }

    public List<T> getList() {
        return list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public CustomSort getSort() {
        return sort;
    }

    public static class CustomSort {

        private String property;
        private Direction direction;

        public CustomSort(final Sort sort) {
            if (sort != null) {
                for (Order order : sort) {
                    if (order != null) {
                        this.property = order.getProperty();
                        this.direction = order.getDirection();
                        break;
                    }
                }
            }
        }

        public String getProperty() {
            return property;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}
