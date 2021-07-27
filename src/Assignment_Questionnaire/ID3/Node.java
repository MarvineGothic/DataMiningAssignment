package Assignment_Questionnaire.ID3;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Vector;

import static javax.swing.tree.DefaultMutableTreeNode.EMPTY_ENUMERATION;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("unchecked")
public class Node<E> implements TreeNode {
    private E label;
    private E branch;
    private Node<E> parent;
    private Vector leaves;

    public Node(E label, E branch, Node<E> parent) {
        this.label = label;
        this.branch = branch;
        this.parent = parent;
    }

    public void addLeaf(Node<E> leaf) {
        if (leaves == null) leaves = new Vector<>();
        this.leaves.add(leaf);
    }

    public void addParent(Node<E> parent) {
        this.parent = parent;
    }

    public E getBranch() {
        return branch == null ? (E) "" : (E) (branch + ".");
    }

    public void setBranch(E branch) {
        this.branch = branch;
    }

    public E getLabel() {
        if (label instanceof Class) return (E) ((Class) label).getSimpleName();
        return label;
    }

    public void setLabel(E label) {
        this.label = label;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (leaves == null)
            throw new ArrayIndexOutOfBoundsException("node has no children");
        return (TreeNode) leaves.elementAt(childIndex);
    }

    @Override
    public int getChildCount() {
        return leaves == null ? 0 : leaves.size();
    }

    public Node<E> getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(node)) {
            return -1;
        }
        return leaves.indexOf((Node<E>) node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return (getChildCount() == 0);
    }

    @Override
    public Enumeration children() {
        if (leaves == null) {
            return EMPTY_ENUMERATION;
        } else {
            return leaves.elements();
        }
    }

    public Vector<Node<E>> getLeaves() {
        return leaves;
    }

    public boolean isNodeChild(TreeNode aNode) {
        boolean retval;
        retval = aNode != null && getChildCount() != 0 && (aNode.getParent() == this);
        return retval;
    }

    /*@Override
    public String toString() {
        return "Node{" +
                "label = " + getLabel() +
                ", branch = " + branch +
                ", parent = " + getParent() +
               *//* ", leaf = " + leaves.size() +*//*
                '}';
    }*/
    @Override
    public String toString() {
        return getLabel() + "";
    }
}
