import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {

	public MyLinkedList<MyLinkedList<String>> movielist;

    public MovieDB() {
        // FIXME implement this
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
		// 작성 중
		this.movielist = new MyLinkedList<MyLinkedList<String>>();
		this.movielist.numItems = 0;
		this.movielist.maxItem = "MAX";
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
		// 작성 중
		String target_genre = item.getGenre();
		String target_title = item.getTitle();
		MyLinkedList<String> target_list;

		if (movielist.size()==0) {

			target_list = new MyLinkedList<>();
			target_list.head.setItem(target_genre);
			Node<MyLinkedList<String>> find_target_list_node = movielist.head;
			find_target_list_node.insertNext(target_list);
			movielist.numItems +=1;
			movielist.maxItem = target_genre;

			Node<String> last = target_list.head;
			last.insertNext(target_title);
			target_list.numItems += 1;
			target_list.maxItem = target_title;

		} else {
			target_list = new MyLinkedList<>();
			Node<MyLinkedList<String>> target_list_node = movielist.head;
			while (target_list_node.getNext() != null) {
				Node<MyLinkedList<String>> target_list_node_next = target_list_node.getNext();
				String node_item = target_list_node_next.getItem().head.getItem();
				if (target_genre.equals(node_item)) {

					target_list = target_list_node_next.getItem();
					Node<String> last = target_list.head;

					while (last.getNext() != null) {
						if (target_title.compareTo(last.getNext().getItem()) == 0) {
							break;
						} else if (target_title.compareTo(last.getNext().getItem()) < 0) {
							last.insertNext(target_title);
							target_list.numItems += 1;
							break;
						} else if (last.getNext().getNext() == null) {
							last.getNext().insertNext(target_title);
							target_list.numItems += 1;
							target_list.maxItem = target_title;
						}

						last = last.getNext();
					}


					break;

				} else if (target_genre.compareTo(node_item) < 0) {

					target_list.head.setItem(target_genre);
					target_list.head.insertNext(target_title);
					target_list.numItems += 1;
					target_list.maxItem = target_title;
					target_list_node.insertNext(target_list);
					movielist.numItems += 1;

					break;

				} else if (target_genre.compareTo(node_item) > 0 && target_list_node_next.getNext() == null) {

					target_list.head.setItem(target_genre);
					target_list.head.insertNext(target_title);
					target_list.numItems += 1;
					target_list.maxItem = target_title;
					target_list_node_next.insertNext(target_list);
					movielist.numItems += 1;
					movielist.maxItem = target_genre;
					break;
				}

				target_list_node = target_list_node_next;
			}
		}
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
		// 작성 중
		String target_genre = item.getGenre();
		String target_title = item.getTitle();

		Node<MyLinkedList<String>> target_list_node = movielist.head;
		MyLinkedList<String> target_list;

		while (target_list_node.getNext()!=null) {
			target_list = target_list_node.getNext().getItem();

			if (target_genre.compareTo(target_list.head.getItem())<0){
				break;
			} else if ( target_genre.equals(target_list.head.getItem()) ) {

				Node<String> last = target_list.head;

				while (last.getNext()!=null){
					if ( target_title.compareTo(last.getNext().getItem())<0 ){
						break;
					} else if (target_title.equals(last.getNext().getItem())){

						last.removeNext();
						target_list.numItems-=1;
						if (target_list.numItems==0){
							target_list_node.removeNext();
							movielist.numItems-=1;
						}
						break;
					} else if (target_title.compareTo(target_list.maxItem)>0){
						break;
					}

					last = last.getNext();
				}
				break;
			} else if (target_genre.compareTo(movielist.maxItem)>0){
				break;
			}


			target_list_node = target_list_node.getNext();

		}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		results.numItems = 0;
		Node<MyLinkedList<String>> target_list_node = movielist.head;
		while (target_list_node.getNext() != null) {
			Node<String> movie_node = target_list_node.getNext().getItem().head;
			String get_genre = target_list_node.getNext().getItem().head.getItem();
			while(movie_node.getNext() != null){
				String get_title = movie_node.getNext().getItem();
				if (get_title.contains(term)){
					MovieDBItem one = new MovieDBItem(get_genre,get_title);
					results.add(one);
				}
				movie_node = movie_node.getNext();
			}
			target_list_node = target_list_node.getNext();
		}

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.
		MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
		results.numItems = 0;
		Node<MyLinkedList<String>> target_list_node = movielist.head;
		while (target_list_node.getNext() != null) {
			Node<String> movie_node = target_list_node.getNext().getItem().head;
			String get_genre = target_list_node.getNext().getItem().head.getItem();
			while(movie_node.getNext() != null){
				String get_title = movie_node.getNext().getItem();
				MovieDBItem one = new MovieDBItem(get_genre,get_title);
				results.add(one);
				movie_node = movie_node.getNext();
			}
			target_list_node = target_list_node.getNext();
		}

    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	public Genre(String name) {
		super(name);
		throw new UnsupportedOperationException("not implemented yet");
	}
	
	@Override
	public int compareTo(Genre o) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("not implemented yet");
	}
}

class MovieList implements ListInterface<String> {	
	public MovieList() {
	}

	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void add(String item) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String first() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void removeAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}
}