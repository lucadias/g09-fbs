/*
 * Copyright 2016 Roland Gisler
 * Hochschule Luzern Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.appe.fbs.business;

import ch.hslu.appe.stock.StockFactory;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Testfälle für {@link ch.hslu.appe.fbs.business.DemoSafeToDelete}.
 */
public final class DemoSafeToDeleteTest {

    /**
     * Test method for
     * {@link ch.hslu.appe.fbs.business.DemoSafeToDelete#addition(int, int)} .
     */
    @Test
    public void testAddition() {
        assertEquals(5L, new DemoSafeToDelete().addition(2, 3));
    }

    @Test
    public void testStockAvailable() {
        Assert.assertNotNull(StockFactory.getStock());
    }
}
