package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Performance {

  private long id;
  private String name;
  private int duration;
  private LocalDate startTime;
  private ArrayList<Ticket> tickets;
  private ArrayList<Member> members = new ArrayList<Member>();
  private double basePrice;
  private ShowType type;
  private Hall hall;

  public Performance() {
  }

  public Performance(String name, int duration, LocalDate startTime, ShowType type,
      double basePrice, Hall hall) {
    this.id = Math.abs(UUID.randomUUID().getLeastSignificantBits()) % 1_000_00;
    this.name = name;
    this.duration = duration;
    this.startTime = startTime;
    this.basePrice = basePrice;
    this.type = type;
    this.hall = hall;

    generateTickets();

  }

  private void generateTickets() {
    tickets = new ArrayList<>();

    double koef = 1;

    for (int i = hall.getRows(); i >= 0; i--) {
      for (int j = 0; j < hall.getSeatsPerRow(); j++) {

        double price = basePrice + basePrice * koef / 100;
        koef++;

        Ticket current = new Ticket(price, j, i, this, false);
        tickets.add(current);

      }
    }
  }

  public void printTicketList() {
    for (int i = 0; i < tickets.size(); i++) {
      if (!tickets.get(i).isSold()) {
        System.out.println(tickets.get(i).toString());
      }
    }
  }

  public void buyTicket(int row, int seat) {

    if (row > this.hall.getRows() || row < 0 || seat > this.hall.getSeatsPerRow() || seat < 0) {
      System.out.println("Wrong seat or row!");
      return;
    }

    for (int i = 0; i < tickets.size(); i++) {
      Ticket current = tickets.get(i);
      if (current.getRow() == row && current.getSeat() == seat && !current.isSold()) {
        current.setSold(true);
        System.out.println("Ticket with row: " + row + " and seat:" + seat + " is sold");
        System.out.println(current.toString());

      }
    }
  }

  public double sumOfAllTickets() {
    double sum = 0;
    for (int i = 0; i < tickets.size(); i++) {
      sum += tickets.get(i).getPrice();
    }
    return sum;
  }


  public double getAveragePrice() {
    return sumOfAllTickets() / tickets.size();

  }


  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public LocalDate getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDate startTime) {
    this.startTime = startTime;
  }

  public ArrayList<Ticket> getTickets() {
    return tickets;
  }

  public ShowType getType() {
    return type;
  }

  public void setType(ShowType type) {
    this.type = type;

  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public void addMember(Member m) {
    members.add(m);
  }

  @Override
  public String toString() {
    return "Performance{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", duration=" + duration +
        ", startTime=" + startTime +
        ", type=" + type +
        '}';
  }


  public static class PerformanceByName implements Comparator<Performance> , Serializable {

    @Override
    public int compare(Performance o1, Performance o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

  public class PerformanceByDuration implements Comparator<Performance> {

    public PerformanceByDuration() {
    }

    @Override
    public int compare(Performance o1, Performance o2) {
      return Integer.compare(o1.duration, o2.duration);
    }
  }


  public static Comparator<Performance> getPerformanceByBasePrice() {
    return new Comparator<Performance>() {
      @Override
      public int compare(Performance o1, Performance o2) {
        return Double.compare(o1.basePrice, o2.basePrice);
      }
    };
  }

  public static Comparator<Performance> getPerformanceById() {
    return (o1,  o2)->  Double.compare(o1.id, o2.id);
  }

}
