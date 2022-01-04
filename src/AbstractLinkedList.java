import java.util.Iterator;

public abstract class AbstractLinkedList<T> implements List<T> {
	public String toString() {
		StringBuilder sb = new StringBuilder(500);
		sb.append('[');
		Iterator<T> it = iterator();
		while(it.hasNext()) {
			sb.append(it.next());
			if(it.hasNext()) sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@SuppressWarnings({ "unchecked" })
	public boolean equals(Object o) {
		if(!(o instanceof LinkedList)) return false;
		if(o == this) return true;
		LinkedList<T> ll =(LinkedList<T>)o;
		if(ll.size()!= this.size()) return false;
		Iterator<T> it1 = this.iterator(),it2 = ll.iterator();
		while(it1.hasNext()) {
			T a1 = it1.next(); 
			T a2 = it2.next();
			if(!(a1.equals(a2))) return false;
		}
		return true;
	}
	
	public int hashCode() {
		int primo = 23, h = 0;
		for(T x :this) h = h*primo+x.hashCode();
		return h;
		
	}
}
