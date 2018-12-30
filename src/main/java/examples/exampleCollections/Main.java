package examples.exampleCollections;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Main {
    public static void main(String... args) {
        //createImmutableCollectionUsingOf();
        //createImmutableCollectionUsingCopyOf();ç
        stream_operations();
    }

    public static void createImmutableCollectionUsingOf() {
        //Java 9, new factory methods:
        System.out.println();
        List.of("This ", "is ", "created ", "by ", "List.of()")
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        Set.of("This ", "is ", "created ", "by ", "Set.of() ")
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map.of(1, "This ", 2, "is ", 3, "built ", 4, "by ", 5, "Map.of() ")
                .entrySet().forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map.ofEntries(
                entry(1, "This "),
                entry(2, "is "),
                entry(3, "built "),
                entry(4, "by "),
                entry(5, "Map.ofEntries() ")
        ).entrySet().forEach(System.out::print);
        System.out.println();

        //Randomization of order of immutable Set/Map elements from run to run.
        System.out.println();
        Map.of(1, "Elements ", 2, "order ", 3, "of ", 4, "Map.of() ", 5, "changes ")
                .entrySet().forEach(System.out::print);

        //Mutable
        System.out.println();
        List<Integer> li = Arrays.asList(1, 2, 3, 4, 5);
        li.set(2, 0);
        li.forEach(System.out::print);
        li.forEach(i -> {
            int j = li.get(2);
            li.set(2, j + 1);
        });
        System.out.println();
        li.forEach(System.out::print);
        System.out.println();

        //Immutable
        List<Integer> l1 = List.of(1, 2, 3, 4, 5);
        //l1.set(2, 9); //UnsupportedOperationException

        List<String> l2 = List.of("This ", "is ", "immutable");
        //l2.add("Is it?");     //UnsupportedOperationException
        //l2.set(1, "is not");  //UnsupportedOperationException

        //No null
        //List<String> list = List.of("This ", "is ", "not ", "created ", null);

        //Set<String>
        Set<String> set = Set.of("a", "b", "c");
        //set.remove("b");  //UnsupportedOperationException
        //set.add("e");     //UnsupportedOperationException
        //set = Set.of("a", "b", "c", null); //NullPointerException

        //Map<Integer, String>
        Map<Integer, String> map = Map.of(1, "one", 2, "two", 3, "three");
        //map.remove(2);          //UnsupportedOperationException
        //map.put(5, "five ");    //UnsupportedOperationException
        //map = Map.of(1, "one", 2, "two", 3, null); //NullPointerException
        //map = Map.ofEntries(entry(1, "one"), null); //NullPointerException
    }

    static class A{}
    static class B extends A{}

    public static void createImmutableCollectionUsingCopyOf() {

        List<Integer> list = Arrays.asList(1,2,3);
        list = List.copyOf(list);
        //list.set(1, 0);     //UnsupportedOperationException
        //list.remove(1);     //UnsupportedOperationException

        Set<Integer> setInt = Set.copyOf(list);
        //setInt.add(42);       //UnsupportedOperationException
        //setInt.remove(3);  //UnsupportedOperationException

        Set<String> set = new HashSet<>(Arrays.asList("a","b","c"));
        set = Set.copyOf(set);
        //set.add("d");     //UnsupportedOperationException
        //set.remove("b");  //UnsupportedOperationException

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one ");
        map.put(2, "two ");
        map = Map.copyOf(map);
        //map.remove(2);          //UnsupportedOperationException
        //map.put(3, "three ");    //UnsupportedOperationException

        List<A> listA = Arrays.asList(new B(), new B());
        Set<A> setA = new HashSet<>(listA);

        List<B> listB = Arrays.asList(new B(), new B());
        setA = new HashSet<>(listB);

        //List<B> listB = Arrays.asList(new A(), new A()); //error
        //Set<B> setB = new HashSet<>(listA);  //error

    }

    public static void stream_operations() {

        System.out.println();
        int sum = Stream.of( 1,2,3,4,5,6,7,8,9 )
                .filter(i -> i % 2 != 0)
                .peek(i -> System.out.print(i))
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("\nsum = " + sum);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .filter(s -> s.contains("i"))
                .peek(s -> System.out.println("peek() after filter(): " + s))
                .forEach(System.out::println);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                //For Debug. Everything done has no effect
                .peek(s -> {
                    s = s + "?";
                    System.out.println("peek(): " + s);
                })
                .map(s -> s + "!")
                .forEach(System.out::println);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .map(s -> s.contains("i"))
                .forEach(System.out::println);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .filter(s -> s.contains("Th"))
                //Convert into stream of characters
                .flatMap(s -> Pattern.compile("(?!^)").splitAsStream(s))
                .forEach(System.out::println);

        System.out.println();
        Stream.concat(Stream.of(4,5,6), Stream.of(1,2,3))
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9))
                //Function.identity() is a function that returns its input argument (to obtain values instead of objects)
                .flatMap(Function.identity())
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        System.out.print("reduce(concat).forEach(): ");
        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9))
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of("3","2","1")
                .parallel()
                //does not insure order
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of("3","2","1")
                .parallel()
                //insure order
                .forEachOrdered(System.out::print);

        System.out.println();
        System.out.println();
        Stream.of( "That ", "is ", "a ", null, "Stream.of(literals)" )
                //get empty optional when null
                .map(Optional::ofNullable) //Optional.of() throws NPE
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(String::toString)
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.println();

        int sum1 = Stream.of(1,2,3)
                //In the "second reduce" receives 1+2 from before operation
                .reduce((p,e) -> {
                    System.out.println(p);
                    return p + e;
                })
                .orElse(10);
        System.out.println("Stream.of(1,2,3).reduce(acc): " + sum1);

        System.out.println();
        System.out.println();

        //U reduce(U identity, BiFunction<U,T,U> accumulator, BinaryOperator<U> combiner)
        int sum11 = Stream.of(1,2,1,1)
                .reduce((p,e) -> {
                    System.out.println("p="+p+", e="+e);
                    return p + e;
                }).orElse(0);
        System.out.println("Stream.of(1,2,1,1).reduce(acc): " + sum11);

        int sum2 = Stream.of(1,2,3)
                .reduce(0, (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).reduce(0,acc): " + sum2);

        System.out.println();
        System.out.println();

        String sum31 = Stream.of(1,2,3)
                .reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).reduce(,acc,comb): " + sum31);

        System.out.println();
        System.out.println();

        //"combiner" is called only for parallel processing in order to assemble (combine)
        String sum32 = Stream.of(1,2,3).parallel()
                .reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).parallel.reduce(,acc,comb): " + sum32);

        System.out.println();
        System.out.println();

        String sum41 = Stream.of(1,2,3)
                .map(i -> i.toString() + ",")
                .reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).map.reduce(,acc): " + sum41.substring(0, sum41.length()-1));

        System.out.println();
        System.out.println();

        String sum42 = Stream.of(1,2,3).parallel()
                .map(i -> i.toString() + ",")
                .reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).parallel.map.reduce(,acc): " + sum42.substring(0, sum42.length()-1));

        System.out.println();

        Object[] os = Stream.of(1,2,3).toArray();
        Arrays.stream(os).forEach(System.out::print);

        System.out.println();
        System.out.println();

        String[] sts = Stream.of(1,2,3).map(i -> i.toString()).toArray(String[]::new);
        Arrays.stream(sts).forEach(System.out::print);

        System.out.println();
        System.out.println();

        double aa = Stream.of(1,2,3).map(Thing::new)
                .collect(Collectors.averagingInt(Thing::getSomeInt));
        System.out.println("stream(1,2,3).averagingInt(): " + aa);

        System.out.println();
        String as = Stream.of(1,2,3).map(Thing::new)
                .map(Thing::getSomeStr)
                .collect(Collectors.joining(","));
        System.out.println("stream(1,2,3).joining(,): " + as);

        System.out.println();
        String ss = Stream.of(1,2,3).map(Thing::new)
                .map(Thing::getSomeStr)
                .collect(Collectors.joining(",", "[","]"));
        System.out.println("stream(1,2,3).joining(,[,]): " + ss);
        System.out.println();
    }
}
