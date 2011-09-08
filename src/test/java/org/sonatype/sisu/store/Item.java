package org.sonatype.sisu.store;

public class Item
{

    private String f1;

    private int f2;

    public Item()
    {

    }

    public Item( final String f1, final int f2 )
    {
        this.f1 = f1;
        this.f2 = f2;
    }

    public String getF1()
    {
        return f1;
    }

    public void setF1( final String f1 )
    {
        this.f1 = f1;
    }

    public int getF2()
    {
        return f2;
    }

    public void setF2( final int f2 )
    {
        this.f2 = f2;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( f1 == null ) ? 0 : f1.hashCode() );
        result = prime * result + f2;
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final Item other = (Item) obj;
        if ( f1 == null )
        {
            if ( other.f1 != null )
            {
                return false;
            }
        }
        else if ( !f1.equals( other.f1 ) )
        {
            return false;
        }
        if ( f2 != other.f2 )
        {
            return false;
        }
        return true;
    }

}
