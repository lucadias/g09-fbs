package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;

import java.sql.Date;
import java.util.Comparator;

/**
 * OrderDateDescComparator
 *
 * @author Mischa Gruber
 */
public final class OrderDateDescComparator implements Comparator<OrderDTO> {
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
