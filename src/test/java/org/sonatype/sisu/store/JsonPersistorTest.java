package org.sonatype.sisu.store;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.sonatype.sisu.store.FileSystemStorage;
import org.sonatype.sisu.store.JsonPersisterFactory;
import org.sonatype.sisu.store.MemoryStore;

public class JsonPersistorTest
{

    @Test
    public void storeAndLoad()
        throws Exception
    {
        final MemoryStore<Item> si = new MemoryStore<Item>();
        si.add( new Item( "1", 1 ) );
        si.add( new Item( "2", 2 ) );

        final FileSystemStorage fss = new FileSystemStorage( "target/items.json" );
        new JsonPersisterFactory().createFor( si, Item.class, fss ).save();

        final MemoryStore<Item> li = new MemoryStore<Item>();
        new JsonPersisterFactory().createFor( li, Item.class, fss ).load();

        final Collection<Item> storeItems = li.get();

        assertThat( "Items loaded", storeItems.size(), is( 2 ) );
        assertItemsClass( storeItems );
    }

    @Test
    public void loadFromClasspath()
        throws Exception
    {
        final MemoryStore<Item> li = new MemoryStore<Item>();
        final FileSystemStorage fss = new FileSystemStorage( "items.json" );
        new JsonPersisterFactory().createFor( li, Item.class, fss ).load();

        final Collection<Item> storeItems = li.get();
        assertThat( "Items loaded", storeItems.size(), is( 2 ) );
        assertItemsClass( storeItems );
    }

    private void assertItemsClass( final Collection<Item> items )
    {
        for ( final Object item : items )
        {
            assertThat( "Items class ", item.getClass(), is( CoreMatchers.<Object> equalTo( Item.class ) ) );
        }

    }

}
