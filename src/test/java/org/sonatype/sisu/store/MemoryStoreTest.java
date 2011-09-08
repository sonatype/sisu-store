package org.sonatype.sisu.store;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.sonatype.sisu.store.MemoryStore;

public class MemoryStoreTest
{

    @Test
    public void operations()
        throws Exception
    {
        final MemoryStore<Item> items = new MemoryStore<Item>();

        assertThat( "Items size", items.get().size(), is( 0 ) );

        items.add( new Item( "1", 1 ) );
        items.add( new Item( "2", 2 ) );

        assertThat( "Items size", items.get().size(), is( 2 ) );

        items.remove( new Item( "2", 2 ) );

        assertThat( "Items size", items.get().size(), is( 1 ) );
    }

}
