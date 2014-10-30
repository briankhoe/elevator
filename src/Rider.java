public class Rider implements Runnable {
  // Each rider has own thread
  // When riders run, each rider gets list of entrances and exits
  // While input not null "follow your instructions"
  // try to get on elevator, request floor, exit, if you have another command, do it
  // Hardcode it in for now
  private int curFloor;
  private Building building;
  private EventBarrier eventBarrier;
  private int riderId

  public Runner (riderId) {
    curFloor = 0;
    building = new Building();
    eventBarrier = new EventBarrier();
    this.riderId = riderId;
  }

  public void run() {

  }
}