package net.staticstudios.mines.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 * <br>
 * <br> This class will hold a list of weighted elements, and can be used to randomly select an element from the list based on their respective weights.
 */
public class WeightedElements<T> {

    private double totalWeight;

    /**
     * A static method to get a random element from a list of weighted elements.
     *
     * @param weightedElements A list of weighted elements.
     * @return A random element from the list while factoring in the elements' weights.
     */
    public static <T> T getRandom(List<WeightedElement<T>> weightedElements, double totalWeight) {
        double random = Math.random() * totalWeight;
        double currentWeight = 0;
        for (WeightedElement<T> weightedElement : weightedElements) {
            currentWeight += weightedElement.weight();
            if (random < currentWeight) {
                return weightedElement.element();
            }
        }
        return weightedElements.get(0).element();
    }

    /**
     * Create a new WeightedElements object.
     *
     * @param o      The object to hold.
     * @param weight The weight of the object.
     * @return A new WeightedElements object.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull WeightedElement<T> of(T o, double weight) {
        return new WeightedElement<T>(o, weight);
    }

    //List of weighted elements
    private final List<WeightedElement<T>> elements = new ArrayList<WeightedElement<T>>();

    /**
     * @return The list of weighted elements.
     */
    public List<WeightedElement<T>> getElements() {
        return elements;
    }

    /**
     * @param index Where to get the element from in the list
     * @return The element at the given index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public WeightedElement<T> getElement(int index) {
        return elements.get(index);
    }

    /**
     * @param index The position of the object in the list.
     * @return The object at the given index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public T getObject(int index) {
        return elements.get(index).element();
    }


    /**
     * @param index The position of the object in the list.
     * @return The weight of the object at the given index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public double getChance(int index) {
        return (elements.get(index).weight() / totalWeight) * 100;
    }

    /**
     * @param o The object to look for in the list.
     * @return The weight of the object if it is contained in the list, otherwise it will return 0.
     */
    public double getChance(Object o) {
        for (WeightedElement<T> element : elements) {
            if (element.element().equals(o)) {
                return (element.weight() / totalWeight) * 100;
            }
        }
        return 0;
    }

    /**
     * @return A new list of objects, with the same elements as the original list, but without having a weight associated with them.
     */
    public List<T> getObjectList() {
        List<T> elements = new ArrayList<>();
        for (WeightedElement<T> weightedElement : this.elements) {
            elements.add(weightedElement.element());
        }
        return elements;
    }

    /**
     * Add an element to the list.
     *
     * @return The current WeightedElements object.
     */
    public WeightedElements<T> add(WeightedElement<T> e) {
        elements.add(e);
        totalWeight += e.weight();
        return this;
    }

    /**
     * Creates and adds a new WeightedElement to the internal list.
     *
     * @return The current WeightedElements object.
     */
    public WeightedElements<T> add(T o, double weight) {
        return add(of(o, weight));
    }

    /**
     * @param e The object to remove from the internal list.
     * @return The current WeightedElements object.
     */
    public WeightedElements<T> remove(WeightedElement<T> e) {
        elements.remove(e);
        totalWeight -= e.weight();
        return this;
    }

    /**
     * Creates a new WeightedElement and remove any element from the internal list that matches the newly created object.
     *
     * @return The current WeightedElements object.
     */
    public WeightedElements<T> remove(T o, double weight) {
        return remove(of(o, weight));
    }

    /**
     * @param e The new list of WeightedElements.
     * @return The current WeightedElements object.
     */
    @SafeVarargs
    public final WeightedElements<T> set(WeightedElement<T>... e) {
        clear();
        for (WeightedElement<T> element : e) {
            elements.add(element);
            totalWeight += element.weight();
        }
        return this;
    }

    /**
     * Clear the internal list.
     *
     * @return The current WeightedElements object.
     */
    public WeightedElements<T> clear() {
        elements.clear();
        totalWeight = 0;
        return this;
    }

    /**
     * Get the total weight of all elements in the list.
     *
     * @return The total weight of all elements in the list.
     */
    public double getTotalWeight() {
        return totalWeight;
    }

    /**
     * @return A random element from the internal list of all WeightedElements while factoring in each element's weight.
     */
    public T getRandom() {
        return getRandom(elements, totalWeight);
    }


    public boolean equals(WeightedElements<T> we) {
        return we == this || we.totalWeight == this.totalWeight && we.elements.equals(this.elements);
    }


//    public static String example() {
//        //Creates a new WeightedElements object, adds 4 weighted elements to it, and gets a random element from it.
//        return new WeightedElements<String>()
//                .add("There is a 50% chance to return this string.", 50)
//                .add("There is a 25% chance to return this string", 25)
//                .add("There is a 15% chance to return this string", 15)
//                .add("There is a 10% chance to return this string", 10)
//                .getRandom();
//    }
}
