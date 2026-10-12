/**
 * Our generic bag. It keeps entries in a chain of linked nodes. It doesn't
 * sort anything and duplicates are fine. It just holds the data.
 *
 * If the chain is empty, firstNode is null and numberOfEntries is 0. We
 * don't let null into the chain.
 *
 * remove(T), contains(T), and count(T) use equals(), so whatever T is
 * should have its own equals() method.
 *
 * @param <T>
 *            the type of thing we're storing
 * @author Angelo Pinillos-Sternberg
 * @version 2026.10.12
 */
public class LinkedChain<T>
{
    private Node<T> firstNode;
    private int numberOfEntries;

    /**
     * Makes a new chain with nothing in it.
     */
    public LinkedChain()
    {
        firstNode = null;
        numberOfEntries = 0;
    }


    /**
     * Puts newEntry at the front of the chain. This is O(1) since we don't
     * have to go through the chain.
     *
     * @param newEntry
     *            what we're adding
     * @throws IllegalArgumentException
     *            if newEntry is null
     */
    public void add(T newEntry)
    {
        if (newEntry == null)
        {
            throw new IllegalArgumentException("Cannot add a null entry.");
        }
        firstNode = new Node<T>(newEntry, firstNode);
        numberOfEntries++;
    }


    /**
     * Puts newEntry at the back of the chain. We have to go all the way
     * to the last node, so it's O(n). The queue uses this so songs play in
     * the order they were added.
     *
     * @param newEntry
     *            what we're adding
     * @throws IllegalArgumentException
     *            if newEntry is null
     */
    public void addToEnd(T newEntry)
    {
        if (newEntry == null)
        {
            throw new IllegalArgumentException("Cannot add a null entry.");
        }
        Node<T> newNode = new Node<T>(newEntry, null);
        if (firstNode == null)
        {
            firstNode = newNode;
        }
        else
        {
            Node<T> current = firstNode;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newNode;
        }
        numberOfEntries++;
    }


    /**
     * Takes the first entry off the front and gives it back. When the user
     * skips in the queue it does this. O(1).
     *
     * @return the entry we took off, or null if the chain was empty
     */
    public T remove()
    {
        if (firstNode == null)
        {
            return null;
        }
        T result = firstNode.data;
        firstNode = firstNode.next;
        numberOfEntries--;
        return result;
    }


    /**
     * Takes out the first entry that equals anEntry. We unlink the node
     * instead of swapping data around, so everything else stays in the
     * same order. O(n) since we might have to check every node.
     *
     * @param anEntry
     *            what we want to take out
     * @return true if we found it and took it out, false if it wasn't
     *         there or anEntry was null
     */
    public boolean remove(T anEntry)
    {
        if (anEntry == null || firstNode == null)
        {
            return false;
        }
        if (anEntry.equals(firstNode.data))
        {
            firstNode = firstNode.next;
            numberOfEntries--;
            return true;
        }
        Node<T> previous = firstNode;
        while (previous.next != null)
        {
            if (anEntry.equals(previous.next.data))
            {
                previous.next = previous.next.next;
                numberOfEntries--;
                return true;
            }
            previous = previous.next;
        }
        return false;
    }


    /**
     * Tells you if anEntry is anywhere in the chain. It just calls count(),
     * so it's O(n).
     *
     * @param anEntry
     *            what we're looking for
     * @return true if it's in there, false if it's not or anEntry is null
     */
    public boolean contains(T anEntry)
    {
        return count(anEntry) > 0;
    }


    /**
     * Counts how many times anEntry shows up in the chain. O(n).
     *
     * @param anEntry
     *            what we're counting
     * @return how many we found, or 0 if anEntry is null
     */
    public int count(T anEntry)
    {
        if (anEntry == null)
        {
            return 0;
        }
        int frequency = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                frequency++;
            }
            current = current.next;
        }
        return frequency;
    }


    /**
     * Gets the entry at index, where 0 is the front. O(n) because we have
     * to walk to it.
     *
     * @param index
     *            where the entry is
     * @return the entry at that spot
     * @throws IndexOutOfBoundsException
     *            if index is below 0 or not less than size()
     */
    public T get(int index)
    {
        if (index < 0 || index >= numberOfEntries)
        {
            throw new IndexOutOfBoundsException(
                "Index " + index + " is out of bounds for size "
                    + numberOfEntries + ".");
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.data;
    }


    /**
     * Copies everything into values. You have to pass the
     * array in because Java won't let us make a generic array.
     * O(n).
     *
     * @param values
     *            the array we fill up
     * @return the same array, now filled
     * @throws IllegalArgumentException
     *            if values is null or too small to fit everything
     */
    public T[] toArray(T[] values)
    {
        if (values == null || values.length < numberOfEntries)
        {
            throw new IllegalArgumentException(
                "Array must not be null and must hold at least "
                    + numberOfEntries + " entries.");
        }
        int index = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            values[index] = current.data;
            index++;
            current = current.next;
        }
        return values;
    }


    /**
     * How many entries are in the chain. O(1).
     *
     * @return the number of entries
     */
    public int size()
    {
        return numberOfEntries;
    }


    /**
     * Checks if there's nothing in the chain. O(1).
     *
     * @return true if it's empty
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    /**
     * Empties out the chain. O(1) since we just drop the first node and
     * everything after it goes with it.
     */
    public void clear()
    {
        firstNode = null;
        numberOfEntries = 0;
    }


    /**
     * One node in the chain. It has an entry and a link to the next node.
     *
     * @param <E>
     *            the type of thing in the node
     */
    private static class Node<E>
    {
        private E data;
        private Node<E> next;

        private Node(E data, Node<E> next)
        {
            this.data = data;
            this.next = next;
        }
    }
}
