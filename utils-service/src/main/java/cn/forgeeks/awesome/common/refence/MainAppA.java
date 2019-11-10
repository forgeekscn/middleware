package cn.forgeeks.awesome.common.refence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.comparator.Comparators;

import java.util.*;
import java.util.stream.Collectors;

public class MainAppA {


    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        products.addAll(Arrays.asList(
                new Product(1L, "a", "", 100D, 0.4D),
                new Product(2L, "b", "", 200D, 1.1D),
                new Product(3L, "c", "", 300D, 1.2D),
                new Product(4L, "a", "", 400D, 2.1D),
                new Product(5L, "b", "", 500D, 4.2D),
                new Product(6L, "c", "", 600D, 2.3D)
                )
        );


        Optional.ofNullable(
                products.stream()
                        .filter(k -> k.getDiscount() > 11D)
                        .collect(Collectors.groupingBy(k -> k.getName(), Collectors.maxBy(Comparator.comparing(Product::getPrice))))
        ).orElse(Collections.emptyMap()).values().stream().forEach(k -> System.out.println(k.get().getName()));

        products.stream()
                .filter(k -> k.getDiscount() > 1D)
                .collect(
                        Collectors.groupingBy(k -> k.getName(),
                                Collectors.collectingAndThen(
                                        Collectors.reducing((a, b) ->
                                                new Product(a.getId(), a.getName(), a.getDesc(), a.getPrice() + b.getPrice(), a.getDiscount())),
                                        Optional::get
                                )
                        )
                ).forEach((name, product) -> System.out.println(product));


        Collections.emptyMap().values().stream().forEach(k -> k.getClass());
    }


}


@ToString
@AllArgsConstructor
@Data
class Product {

    private Long id;
    private String name;
    private String desc;

    private Double price;
    private Double discount;


}