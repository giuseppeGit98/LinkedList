import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LinkedList<T> extends AbstractLinkedList<T> {
	
	private enum Move{UNKNOWN,FORWARD,BACKWARD}
	
	private static class Nodo<E>{
		E info;
		Nodo<E> next,prior;
	}
	
	private Nodo<T> first = null ,last = null;
	private int size = 0,contatore = 0;
	
	public int size() {
		return size;
	}

	
	public boolean contains(T x) {
		if(size == 0) 
			throw new NoSuchElementException("La lista � vuota");
		else {
		Nodo<T> cor = first;
		while(cor!= null && !cor.info.equals(x))cor = cor.next;
		return cor!= null;
		}
	}

	
	public void clear() {
		first  = null; last = null; size = 0;contatore++;
	}

	
	public void add(T x) {
		addLast(x);
	}
	

	
	public void addFirst(T elem) {
		Nodo<T>n = new Nodo<>(); n.info = elem;
		n.prior = null; n.next = first;
		if(first!= null) first.prior = n;
		first = n;
		if(first == null) first = n;
		size++;contatore++;
	}

	
	public void addLast(T elem) {
		Nodo<T>n = new Nodo<>(); n.info= elem;
		n.next =null;n.prior = last;
		if(last!=null) last.next = n;
		last = n;
		if(first == null)first = n;
		size++;contatore++;
	}

	
	public T getFirst() {
		if(size == 0) throw new NoSuchElementException("La lista � vuota");
		return first.info;
	}

	
	public T getLast() {
		if(size == 0) throw new NoSuchElementException("La lista � vuota");
		return last.info;
	}

	
	public T removeFirst() {
		Nodo<T>cor = first;
		T rimosso = null;
		if(size == 0) throw new NoSuchElementException("La lista � vuota");
		if(size==1) {rimosso = first.info; clear();}
		else {
			cor = first.next; cor.prior = null;
			rimosso = first.info; first = cor;
			size--;
		}
	contatore++;	
	return rimosso;
	}
	

	
	public T removeLast() {
		Nodo<T> cor = last;
		T rimosso = null;
		if(size == 0) throw new NoSuchElementException("La lista � vuota");
		if(size == 1) { rimosso = last.info; clear();}
		else {
			cor = last.prior; cor.next = null;
			rimosso = last.info; last = cor;
			size--;
		}
		contatore++;
		return rimosso;
	}


	public void remove(T x) {
		Nodo<T> cor = first;
		while(cor!= null && !cor.info.equals(x)) cor = cor.next;
		if(cor!= null) {//trovato
			if(cor == first) {
				first = first.next;
				if(first == null) last = null;
				else first.prior = null;
			}
			else if(cor == last) {//esistono certamente 2 nodi
				last = last.prior;
				last.next = null;
			}
			else { // elemento da eliminare in mezzo
				cor.prior.next = cor.next;
				cor.next.prior = cor.prior;
			}
		size--;
		contatore++;
		}
	}
	
	
	public boolean isEmpty() {
		return size == 0;
	}

	
	public void sort(Comparator<T> c) { //bubblesort
		if( first==null || first.next==null ) return;
		boolean scambi=true;
		Nodo<T> limite=null, pus=null;
		while( scambi ){
			Nodo<T> cor=first.next;
			scambi=false;
			while( cor!=limite ){
				if( c.compare(cor.info,cor.prior.info)<0 ){
					T park=cor.info;
					cor.info=cor.prior.info;
					cor.prior.info=park;
					pus=cor;
					scambi=true;
				}
				cor=cor.next;
			}//while
			limite=pus;
		}//while
	}
	
	public Iterator<T> iterator() {
		return new ListIteratorImpl();
	}

	
	public ListIterator<T> listIterator() {
		return new ListIteratorImpl();
	}

	public ListIterator<T> listIterator(int from) {
		return new ListIteratorImpl(from);
	}
	
	private class ListIteratorImpl implements ListIterator<T>{
		private Nodo<T> previous,next;
		private int cont = contatore;
		//L'iteratore � tra i nodi puntati da previous e next;
		//L'elemento corrente,nel movimento FORWARD, � previous mentre in quello BACKWARD � next;
		
		private Move lastMove = Move.UNKNOWN;
		
		public ListIteratorImpl() {
			previous = null; next = first;
		}
		
		public ListIteratorImpl(int da) {
			int cont = 0;
			if(da<0 || da>size) 
				throw new IllegalArgumentException("Posizione errata");
			else {
				previous = null; next = first;
				while(cont!= da) {
					previous = next; next = next.next;
					cont ++;
				}
			
			}
			
		}
		public void add(T elem) {
			if(cont!= contatore) throw new ConcurrentModificationException();
			Nodo<T>cor = new Nodo<>(); 
			cor.info = elem;
			if(previous == null && next == null) { //listavuota
				previous = cor; first = cor; last = cor; size++; lastMove = Move.UNKNOWN; return;
			}
			if(previous == null && next != null) { // aggiunta in testa
				previous = cor; first.prior = cor; cor.next = first; first = cor; size++; lastMove = Move.UNKNOWN;return;
			}
			if(next == null && previous != null) { //aggiunta in coda
				last.next = cor; cor.prior = last; previous= cor; last = cor; size++;lastMove = Move.UNKNOWN; return;
				
			}
			previous.next=cor;
			cor.prior = previous;
			next.prior= cor;
			cor.next = next;
			previous = cor;
			lastMove = Move.UNKNOWN;
			size++;
		}
		
		public boolean hasNext() {
			return next!=null;
		}

		
		public boolean hasPrevious() {
			return previous!= null;
		}

		
		public T next() {
			if(cont!= contatore) throw new ConcurrentModificationException();
			if(!hasNext()) 
				throw new NoSuchElementException();
			lastMove = Move.FORWARD;
			previous = next;
			next = next.next;
			return previous.info;
			
		}

		
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		
		public T previous() {
			if(cont!= contatore) throw new ConcurrentModificationException();
			if(!hasPrevious())
				throw new NoSuchElementException();
			lastMove = Move.BACKWARD;
			next = previous;
			previous = previous.prior;
			return next.info;
		}

		
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		
		public void remove() {
			if(cont!= contatore) throw new ConcurrentModificationException();
			if(lastMove == Move.UNKNOWN) 
				throw new IllegalStateException();
			Nodo<T>r = null; // r � il nodo da rimuovere
			if(lastMove == Move.FORWARD)
				r = previous;
			else
				r = next;
			// rimozione del nodo r
			if(r == first) {
				first = first.next;
				if(first == null) last = null;
				else first.prior = null;
			}
			else if(r == last) {
				last = last.prior;
				last.next = null;
			}
			else { // nel mezzo
				r.prior.next = r.next;
				r.next.prior = r.prior;
			}
			//update iterator
			if(lastMove == Move.FORWARD)
				previous = r.prior; //arretra
			else
				next = r.next; //avanza
			size --;
			lastMove = Move.UNKNOWN;
		} //remove

		
		public void set(T elem) {
			if(cont!= contatore) throw new ConcurrentModificationException();
			if(lastMove == Move.UNKNOWN)
				throw new IllegalStateException();
			if(lastMove == Move.FORWARD)
				previous.info = elem;
			else
				next.info = elem;
		}
			
			
	}	
}
