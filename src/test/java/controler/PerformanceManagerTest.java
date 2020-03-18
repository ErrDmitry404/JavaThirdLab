package controler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import manager.HallManager;
import manager.PerformanceManager;
import model.Actor;
import model.Musician;
import model.Performance;
import model.ShowType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

  public class PerformanceManagerTest {

    private PerformanceManager entertaiment;
    private HallManager hallChoice;

    @Before
    public void setUp() throws Exception {
      entertaiment = new PerformanceManager();
      hallChoice = new HallManager();

      hallChoice.addHall("Musical Hall", "old address 2", 30, 10);
      hallChoice.addHall("Circus Hall", "near city address 5", 50, 20);
      hallChoice.addHall("Ballet Hall", "new address 6", 70, 30);
      hallChoice.addHall("Cinema Theatre", "new address 9", 50, 20);
      hallChoice.addHall("Orchestral Hall", "new address 6", 60, 20);
      hallChoice.addHall("Concert Hall", "new address 6", 80, 30);

      Performance musicalAct = entertaiment
          .createPerformance(hallChoice.chooseHall(0), "Musical", ShowType.MUSICAL, LocalDate.of(2020, 4, 6), 90, 80);
      Performance circusAct = entertaiment
          .createPerformance(hallChoice.chooseHall(1), "Circus with Wolves", ShowType.CIRCUS, LocalDate.of(2020, 4, 1), 60, 40);
      Performance balletAct = entertaiment
          .createPerformance(hallChoice.chooseHall(2), "Ballet Show", ShowType.BALLET, LocalDate.of(2020, 4, 9), 90, 130);
      Performance movieAct = entertaiment
          .createPerformance(hallChoice.chooseHall(3), "Blockbuster", ShowType.MOVIE, LocalDate.of(2020, 4, 10), 90, 70);
      Performance orchestralAct = entertaiment
          .createPerformance(hallChoice.chooseHall(4), "Grand Orchestral", ShowType.ORCHESTRAL, LocalDate.of(2020, 4, 8), 180, 180);
      Performance concertAct = entertaiment
          .createPerformance(hallChoice.chooseHall(5), "Grand Concert", ShowType.CONCERT, LocalDate.of(2020, 4, 8), 180, 100);

      musicalAct.addMember(new Musician("John Jo", 30, 1800, "Guitar", "Pro"));
      musicalAct.addMember(new Musician("Sebastian Nar", 45, 700, "Drums", "Novice"));
      musicalAct.addMember(new Musician("Sebastian Vito", 41, 900, "Piano", "Novice"));
      circusAct.addMember(new Actor("Bob Joe", 19, 3000, "Animal Trainer", 10));
      balletAct.addMember(new Actor("Sarah Mo", 23, 9000, "Choreographer", 70));
      movieAct.addMember(new Actor("Bob Sars", 25, 20000, "main-role", 20));
      movieAct.addMember(new Actor("Ronald Sars", 22, 19000, "main-role", 30));
      orchestralAct.addMember(new Musician("Sebastian Sui", 48, 19000, "Violin", "Pro"));
      concertAct.addMember(new Musician("Rick Sui", 35, 17000, "Saxophone", "Pro"));
    }


    @Test
    public void getPerformancesWithMusicians() {
      List<Performance> result = new ArrayList<>();

      Performance musicalByName = entertaiment.getByName("Musical");
      Performance orchestralByName = entertaiment.getByName("Grand Orchestral");
      Performance concertByName = entertaiment.getByName("Grand Concert");
      result.add(musicalByName);
      result.add(orchestralByName);
      result.add(concertByName);
      Assert.assertEquals(result, entertaiment.getPerformancesWithMusicians());

    }

    @Test
    public void Sorting_Performance_By_Name_Using_Inner_Static_Class_Test() {
      List<Performance> current = entertaiment.getPerformances();
      current.sort(new Performance.PerformanceByName());

      Assert.assertTrue(current.size() > 2);
      Assert.assertTrue(current.get(0).getName().compareTo(current.get(1).getName()) < 0);
    }

    @Test
    public void Sorting_Performance_By_Duration_Using_Inner_Class_Test() {
      List<Performance> current = entertaiment.getPerformances();
      Performance temporal = new Performance();
      Comparator<Performance> comparator  =  temporal.new PerformanceByDuration();
      current.sort(comparator);


      Assert.assertTrue(current.size() > 2);
      Assert.assertTrue(Integer.compare(current.get(0).getDuration(), current.get(2).getDuration()) < 0);
    }

    @Test
    public void Sorting_Performance_By_Duration_Using_Lambda() {
      List<Performance> current = entertaiment.getPerformances();
      current.sort(Performance.getPerformanceById());

      Assert.assertTrue(current.size() > 2);
      Assert.assertTrue(Long.compare(current.get(0).getId(), current.get(2).getId()) < 0);
    }
  }