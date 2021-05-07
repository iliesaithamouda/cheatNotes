import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GeneralTree<E> extends AbstractTree<E> {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    protected Node<E> root = null;
    private int size = 0;

    protected Node<E> createNode(E e, Node<E> parent){
        return new Node<E>(e, parent);
    }

    protected Node<E> validate(Node<E> p) throws IllegalArgumentException{
        if(!(p instanceof Node))
            throw new IllegalArgumentException("Not a valid position type");

        Node<E> node = (Node<E>) p;

        if(node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in tree");

        return node;
    }

    public int size(){
        return size;
    }

    public Node<E> root(){
        return root;
    }

    @Override
    public Node<E> parent(Node<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public int numChildren(Node<E> p) throws IllegalArgumentException {
        return children(p).size();
    }

    @Override
    public Iterable<Node<E>> children(Node<E> p) throws IllegalArgumentException {
        return validate(p).getChildren();
    }

    public Node<E> addRoot(E e) throws IllegalStateException{
        if(!isEmpty())
            throw new IllegalStateException("Tree is not empty");

        return createNode(e, null);
    }

    public Node<E> addChild(Node<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);

        Node<E> child = createNode(e, parent);
        parent.addChild(child);
        size++;
        return child;
    }

    public E set(Node<E> p, E e) throws IllegalArgumentException{
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public List<Node<E>> getLeaves(Node<E> node, List<Node<E>> leaves) {
        node.getChildren().forEach(child -> {
            if (child.hasChildren()) {
                getLeaves(child, leaves);
            } else {
                leaves.add(child);
            }
        });

        return leaves;
    }

    public List<E> getAllChildren(Node<E> node, List<E> nodes) {
        node.getChildren().forEach(child -> {
            nodes.add(child.getElement());
            if (child.hasChildren()) {
                getAllChildren(child, nodes);
            }
        });
        return nodes;
    }

    public boolean anyDuplicateParent(Node<E> node, List<E> elements) {
        if (elements.contains(node.getElement())) {
            LOG.info("node in list return true {}", node.getElement());
            return true;
        } else if (this.isRoot(node)){
            LOG.info("node == root return false {}", node.getElement());
            return false;
        } else {
            LOG.info("node != root and not in list, continue... {}", node.getElement());
            elements.add(node.getElement());
            return anyDuplicateParent(node.getParent(), elements);
        }

    }

    public List<E> getDuplicateNodeOnSameBranch() {
        List<E> duplicatesOnSameBranch = new LinkedList<>();
        // get list of leaves
        List<Node<E>> leaves = getLeaves(this.root, new LinkedList<>());
        leaves.forEach( leaf -> {
            LOG.info("new leaf =================== ");
            if (anyDuplicateParent(leaf, new LinkedList<>())){
                duplicatesOnSameBranch.add(leaf.getElement());
            }
        });
        // each leaf, traverse branch to tree root and see if a parent is same

        return duplicatesOnSameBranch;
    }

    public List<E> getAllChildren() {
        return getAllChildren(this.root, new LinkedList<>());
    }
}
