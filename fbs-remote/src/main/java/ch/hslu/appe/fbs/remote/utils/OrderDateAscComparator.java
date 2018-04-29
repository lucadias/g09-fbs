package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import java.util.Comparator;

/**
 * This comparator compares orders ascending based on the date.
 *
 * @author Mischa Gruber
 */
public final class OrderDateAscComparator implements Comparator<OrderDTO> {
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
