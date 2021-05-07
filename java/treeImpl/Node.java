import java.util.LinkedList;
import java.util.List;

public class Node<E>{

    private E element;
    private Node<E> parent;
    private List<Node<E>> children;

    public Node(E element, Node<E> parent) {
        this.element = element;
        this.parent = parent;
        children = new LinkedList<>();
    }

    public E getElement() {
        return element;
    }

    public Node<E> getParent() {
        return parent;
    }


    public void setElement(E element) {
        this.element = element;
    }

    public void setParent(Node<E> parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node<E>> children) {
        this.children = children;
    }

    public List<Node<E>> getChildren() {
        return this.children;
    }

    public void removeChild(Node<E> child) {
        this.children.remove(child);
    }

    public void addChild(Node<E> child) {
        // make sure not adding self
        if (child != this) {
            this.children.add(child);
        }
    }

    public boolean hasChildren() {
        return this.children != null && !this.children.isEmpty();
    }

}
