package cn.forgeeks.awesome.common.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtil {

  public static List<Integer> getNatureNumbers(int count) {
    return Stream.iterate(0, n -> n + 1).limit(count).collect(Collectors.toList());
  }

  public static List<Double> getRandomNumbers(int count) {
    return Stream.generate(Math::random).limit(count).collect(Collectors.toList());
  }
}
