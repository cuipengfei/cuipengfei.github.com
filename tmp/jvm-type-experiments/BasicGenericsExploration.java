import java.util.*;
import java.util.function.*;

/**
 * Basic Generics Exploration Experiment
 * Goal: Analyze Java generics implementation at bytecode level
 */
public class BasicGenericsExploration<T extends Comparable<T>> {
    private T value;
    private List<T> items;
    
    public BasicGenericsExploration(T value) {
        this.value = value;
        this.items = new ArrayList<>();
    }
    
    /**
     * Generic method: test method-level type parameters
     */
    public <U> Optional<U> transform(Function<T, U> mapper) {
        return Optional.ofNullable(value).map(mapper);
    }
    
    /**
     * Test generic bounds constraint
     */
    public <U extends Number> double calculateSum(List<U> numbers) {
        return numbers.stream().mapToDouble(Number::doubleValue).sum();
    }
    
    /**
     * Test covariant wildcards
     */
    public void processProducers(List<? extends T> producers) {
        for (T item : producers) {
            items.add(item);
        }
    }
    
    /**
     * Test contravariant wildcards
     */
    public void processConsumers(List<? super T> consumers) {
        consumers.add(value);
    }
    
    /**
     * Test type erasure causing overload restrictions
     * Note: The method below would cause compilation error if uncommented
     */
    public void process(List<String> strings) {
        System.out.println("Processing strings: " + strings.size());
    }
    
    // public void process(List<Integer> integers) {
    //     System.out.println("Processing integers: " + integers.size());
    // }
    
    /**
     * Test bridge method generation
     */
    public T getValue() {
        return value;
    }
    
    /**
     * Test nested generics
     */
    public Optional<List<T>> getOptionalItems() {
        return items.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(items));
    }
    
    /**
     * Test raw type compatibility
     */
    @SuppressWarnings("rawtypes")
    public void workWithRawType(BasicGenericsExploration raw) {
        // Unchecked conversion warning here
        T val = (T) raw.getValue();
    }
    
    public static void main(String[] args) {
        BasicGenericsExploration<String> exploration = new BasicGenericsExploration<>("Hello");
        
        // Test generic method
        Optional<Integer> length = exploration.transform(String::length);
        System.out.println("String length: " + length.orElse(0));
        
        // Test bounds constraint
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        double sum = exploration.calculateSum(numbers);
        System.out.println("Sum: " + sum);
        
        // Test wildcards
        List<String> strings = Arrays.asList("A", "B", "C");
        exploration.processProducers(strings);
        
        List<Object> objects = new ArrayList<>();
        exploration.processConsumers(objects);
        System.out.println("Objects after processing: " + objects);
    }
}