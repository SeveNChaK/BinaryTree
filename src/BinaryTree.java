import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private Node findParent(Node<T> child) {
        Node<T> parent = new Node(null);
        Node<T> current = root;
        if (child == root) return null;
        while (current != null) {
            if (current.left == child) {
                parent = current;
                break;
            }
            if (current.right == child) {
                parent = current;
                break;
            }
            if (child.value.compareTo(current.value) > 0) {
                current = current.right;
                continue;
            }
            if (child.value.compareTo(current.value) < 0) {
                current = current.left;
                continue;
            }
        }
        return parent;
    }

    @Override
    public boolean remove(Object obj) {

        Node<T> current = find((T) obj);
        Node<T> parent;

        if (current == null)
        {
            return false;
        }

            // Находим удаляемый узел
        parent = findParent(current);
        size--;

            // Случай 1: Если нет детей справа, левый ребенок встает на место удаляемого.
        if (current.right == null) {
            if (parent == null) {
                root = current.left;
            } else {
                int result = parent.value.compareTo(current.value);
                if (result > 0) {
                    // Если значение родителя больше текущего,
                    // левый ребенок текущего узла становится левым ребенком родителя
                    parent.left = current.left;
                } else if (result < 0) {
                    // Если значение родителя меньше текущего,
                    // левый ребенок текущего узла становится правым ребенком родителя
                    parent.right = current.left;
                }
            }
        }
            // Случай 2: Если у правого ребенка нет детей слева, то он занимает место удаляемого узла.
        else if (current.right.left == null) {
            if (parent == null) {
                root = current.right;
            } else {
                int result = parent.value.compareTo(current.value);
                if (result > 0) {
                    // Если значение родителя больше текущего,
                    // правый ребенок текущего узла становится левым ребенком родителя.
                    parent.left = current.right;
                } else if (result < 0) {
                    // Если значение родителя меньше текущего,
                    // правый ребенок текущего узла становится правым ребенком родителя.
                    parent.right = current.right;
                }
            }
        }
        // Случай 3: Если у правого ребенка есть дети слева, крайний левый ребенок из правого поддерева заменяет удаляемый узел.
        else {
                // Найдем крайний левый узел.
            Node leftmost = current.right.left;
            Node leftmostParent = current.right;
            while (leftmost.left != null) {
                leftmostParent = leftmost;
                leftmost = leftmost.left;
            }
                // Левое поддерево родителя становится правым поддеревом крайнего левого узла.
            leftmostParent.left = leftmost.right;
                // Левый и правый ребенок текущего узла становится левым и правым ребенком крайнего левого.
            leftmost.left = current.left;
            leftmost.right = current.right;
            if (parent == null) {
                root = leftmost;
            } else {
                int result = parent.value.compareTo(current.value);
                if (result > 0) {
                    // Если значение родителя больше текущего,
                    // крайний левый узел становится левым ребенком родителя.
                    parent.left = leftmost;
                } else if (result < 0) {
                    // Если значение родителя меньше текущего,
                    // крайний левый узел становится правым ребенком родителя.
                    parent.right = leftmost;
                }
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> curNode = null;

        private Stack<Node<T>> pastRoots;
        private boolean comeBack;
        private Node<T> result;
        private int count;


        private BinaryTreeIterator() {
            pastRoots = new Stack<>();
            curNode = root;
            count = 0;
        }

        private void findNext() {
            result = curNode;
            if (!comeBack && curNode.left != null) {
                pastRoots.push(curNode);
                curNode = curNode.left;
                findNext();
                return;
            } else if (curNode.right != null) {
                curNode = curNode.right;
                comeBack = false;
            } else {
                if (pastRoots.size() != 0)
                    curNode = pastRoots.pop();
                comeBack = true;
            }
            count++;
        }

        @Override
        public boolean hasNext() {
            return !(count == size);
        }

        @Override
        public T next() {
            if (hasNext())
                findNext();
            if (result == null) throw new NoSuchElementException();
            return result.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}