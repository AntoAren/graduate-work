package by.bsu.zakharankou.restservices.controller.util;

import org.springframework.data.domain.*;

/**
 * Utility class to work with pagination info.
 */
public final class Paging {

    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Creates pagination. If <code>offset</code> or <code>max</code> is invalid then appropriate default value is used.
     * If <code>offset</code> and <code>max</code> are <code>null</code> then method returns pagination for retrieving all items.
     *
     * @param offset string representation of the number of elements to skip before selection
     * @param size   string representation of the number of items of the page
     * @return pagination information
     */
    public static Pageable createPageable(final String offset, final String size) {
        return createPageable(offset, size, null);
    }

    /**
     * Creates pagination with sort options. If <code>offset</code> or <code>max</code> is invalid then appropriate default value is used.
     * If <code>offset</code> and <code>max</code> are <code>null</code> then method returns pagination for retrieving all items.
     * Sort options may be <code>null</code>.
     *
     * @param offset string representation of the number of elements to skip before selection
     * @param size   string representation of the number of items of the page
     * @param sort   sorting parameters
     * @return pagination information
     */
    public static Pageable createPageable(final String offset, final String size, final Sort sort) {
        //if there are no offset and max parameters then pageable object should get all items
        if (offset == null && size == null) {
            return new PageRequest(0, 1, sort) {//constructor requires positive size parameter, so we pass '1'
                @Override
                public int getOffset() {
                    return 0;
                }

                @Override
                public int getPageSize() {
                    return 0;
                }
            };
        }

        final int offsetValue = Paging.parseOffset(offset);
        final int sizeValue = Paging.parseSize(size);
        return new PageRequest(offsetValue / sizeValue, sizeValue, sort) {
            @Override
            public int getOffset() {
                return offsetValue;
            }
        };
    }

    /**
     * Converts a specified offset to a number.
     * If the value is blank, not a number, zero or negative then default value is used.
     *
     * @param offset offset
     * @return parsed number or default value if specified number is not valid
     */
    private static int parseOffset(final String offset) {
        try {
            int offsetValue = Integer.parseInt(offset);
            return (offsetValue >= 0) ? offsetValue : DEFAULT_OFFSET;
        } catch (NullPointerException | NumberFormatException e) {
            return DEFAULT_OFFSET;
        }
    }

    /**
     * Converts a specified size to a number.
     * If the value is blank, not a number or negative number then default value is used.
     *
     * @param size size
     * @return parsed number or default value if specified number is not valid
     */
    private static int parseSize(final String size) {
        try {
            int sizeValue = Integer.parseInt(size);
            return (sizeValue > 0) ? sizeValue : DEFAULT_PAGE_SIZE;
        } catch (NullPointerException | NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }
}
