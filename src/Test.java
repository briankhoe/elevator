public class Test {

  public static void test() {
    Building building = new Building(20, 1, Integer.MAX_VALUE);
    ElevatorThread elevator = new ElevatorThread(building);
    Rider rider1 = new Rider(1, 19, 3, building);
    rider1.start();
  	Rider rider2 = new Rider(2, 15, 5, building);
  	rider2.start();
  	Rider rider3 = new Rider(3, 5, 8, building);
  	rider3.start();
  	Rider rider4 = new Rider(4, 2, 19, building);
  	rider4.start();
  	elevator.start();
  }

}