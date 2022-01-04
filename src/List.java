import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;



public interface List<T> extends Iterable<T> {
	
	default int size(){
		int c = 0;
		for( T e : this) 
			c++;
	return c;	
	}
	
	default boolean contains(T x) {
		if(isEmpty()) throw new NoSuchElementException();
		else {
			boolean flag = false;
			for(T e : this) {
				if(e.equals(x))
					flag = true; break;
			}
			return flag;
		}
		
	}
	
	default void clear() {
		Iterator<T>it = this.iterator();
		while(it.hasNext()) {
			it.next();
			it.remove();
		}
			
	}
	 default void add(T x) {
		addLast(x);
	}
	
	default void addFirst(T elem) {
		ListIterator<T>lit = this.listIterator();
		lit.add(elem);
	}
	
	default void addLast(T elem) {
		ListIterator<T>lit  = this.listIterator();
		if(!(lit.hasNext())) addFirst(elem);
		while(lit.hasNext()) {
			lit.next();
			if(!(lit.hasNext()))
				lit.add(elem);
		}
	}
	
	default T getFirst() {
		if(isEmpty()) throw new NoSuchElementException();
		else {
			T primo = null;
			for(T x : this) { 
				primo = x; break;
			}
			return primo;
		}
	
	}
	
	default T getLast() {
		if(isEmpty()) throw new NoSuchElementException();
		else {
			T ultimo = null;
			for(T x : this)
				ultimo = x;
			return ultimo;
		}
		
	}
	
	 default T removeFirst() {
		if(isEmpty()) throw new NoSuchElementException();
		else {
			T eliminato = null;
			Iterator<T>it = this.iterator();
			while(it.hasNext()) {
				eliminato = it.next();
				it.remove(); break;
			}
			return eliminato;
		}
	}
	
	default T removeLast() {
		if(isEmpty()) throw new NoSuchElementException();
		else {
			T eliminato = null;
			Iterator<T> it = this.iterator();
			while(it.hasNext()) {
			eliminato = it.next();
			}
		it.remove();
		return eliminato;
		}
	}
	 
	default void remove(T x) {
		Iterator<T> it = this.iterator();
		while(it.hasNext()) {
			T e = it.next();
			if(e.equals(x)) { it.remove(); break;}
		}
	}
	
	
	 default boolean isEmpty() {
		Iterator<T>it = this.iterator();
		return !it.hasNext();
	}
	
	 default boolean isFull() {
		return false;
	}
	
	static<T> void sort(List<T>l,Comparator<T>c) {
		boolean sort = false;
		if(l.isEmpty()) throw new NoSuchElementException("La lista � vuota");
		else if(l.size()==1) return;
		else{
			sort = false;
			while(sort == false) {
				ListIterator<T>lit = l.listIterator();
				int i = 0;
				T x = lit.next();
				while(lit.hasNext()) {
					T j = lit.next();
					if(c.compare(x,j)>0) {
						lit.set(x); lit.previous();lit.previous();lit.set(j);lit.next();x = lit.next();
						i++;
					}
					else {x = j;}
					if((!lit.hasNext()) && i == 0) sort = true;	
				}
			}
		}
	}
	
	ListIterator<T>listIterator();
	ListIterator<T>listIterator(int from);
	
	default void salva(String file) throws IOException {
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
		for(T x : this)
			o.writeObject(x);
		o.close();
	}
	
	@SuppressWarnings("unchecked")
	default void ripristina(String file) throws IOException{
		ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
		this.clear();
		for(;;) {
			try {
				this.add((T)o.readObject());
			} catch(EOFException ex) {
				break;
			}
			catch(ClassNotFoundException ex2) {throw new IOException();}
			catch(ClassCastException ex3) {throw new IOException();}
		}
		o.close();
	}
}
