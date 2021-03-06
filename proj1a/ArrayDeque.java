public class ArrayDeque<T> {
	private T[] items;
	private int size;
	private int nextFirst;
	private int nextLast;
	private static final int INITIAL_CAPACITY = 8;

	public ArrayDeque() {
		items = (T[]) new Object[INITIAL_CAPACITY];
		size = 0;
		nextFirst = 0;
		nextLast = 1;
	}

//Figure out negative number mod 8 to get the change to nextFrist and nextLast,
	private int minusOne(int index) {
		return Math.floorMod(index - 1, items.length);
	}

	private int plusOne(int index) {
		return Math.floorMod(index + 1, items.length);
	}

	private int plusOne(int index, int size) {
		return Math.floorMod(index + 1, size);
	}

	private int plusIndex(int firstIndex, int secondIndex) {
		return Math.floorMod(firstIndex + secondIndex, items.length);
	}

	public int size() {
		return size;
	}

	private int capacity() {
		return items.length;
	}

	private void extend() {
		resize(size() * 2);
	}

	private void shrink() {
		resize(items.length / 2);
	}

	private boolean isFull() {
		return size() == items.length;
	}

	private boolean isSparse() {
		return items.length >= 16 && size() < items.length / 4;
	}

	private void resize(int target) {
		System.out.printf("Resizing from %5d to %5d\n",items.length, target);
		T[] oldItems = items;
		int oldFirst = plusOne(nextFirst);
		int oldLast = minusOne(nextLast);

		items = (T[]) new Object[target];
		nextFirst = 0;
		nextLast = 1;
		for (int i = oldFirst; i != oldLast; i = plusOne(i, oldItems.length)){
			items[nextLast] = oldItems[i];
			nextLast = plusOne(nextLast); 
		} 
		items[nextLast] = oldItems[oldLast];
		nextLast = plusOne(nextLast);
	}

	public void addFirst(T item) {
		if (isFull()) {
			extend();
		}
		items[nextFirst] = item;
		nextFirst = minusOne(nextFirst);
		size++;
	}

	public void addLast(T item) {
		if (isFull()) {
			extend();
		}
		items[nextLast] = item;
		nextLast = plusOne(nextLast);
		size++;
	}

	public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

	public T removeFirst() {
		if (isSparse()) {
			shrink();
		}
		if (isEmpty()) {
			return null;
		}
		nextFirst = plusOne(nextFirst);
		T removeItem = items[nextFirst];
		items[nextFirst] = null;
		size--;
		return removeItem;
	}

	public T removeLast() {
		if(isSparse()) {
			shrink();
		}
		if (isEmpty()) {
			return null;
		}
		nextLast = minusOne(nextLast);
		T removeItem = items[nextLast];
		items[nextLast] = null;
		size--;
		return removeItem;
	}

	public T get(int index) {
		return items[plusIndex(nextFirst + 1, index)];
	}

	public void printDeque() {
		for (int i = plusOne(nextFirst); i != nextLast; i = plusOne(i)) {
			System.out.print(items[i] + " ");
		}
	}
  
}
