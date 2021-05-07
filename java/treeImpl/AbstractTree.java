package com.autodesk.webdelivery.dotcom.core.utils.abstractTree;

public abstract class AbstractTree<E> implements Tree<E> {

    public boolean isRoot(Node<E> p) {
        return p == root();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isInternal(Node<E> p) {
        return numChildren(p) > 0;
    }

    public boolean isExternal(Node<E> p) {
        return numChildren(p) == 0;
    }

    public int depth(Node<E> p) {
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }
}
