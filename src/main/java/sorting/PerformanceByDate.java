package sorting;

import java.io.Serializable;
import java.util.Comparator;
import model.Performance;

public class PerformanceByDate implements Comparator<Performance> , Serializable {

  @Override
  public int compare(Performance o1, Performance o2) {
    return o1.getStartTime().compareTo(o2.getStartTime());
  }
}
