public class Runner {

	public static void main (String[] args) {
        switch(args[0]) {
          case "ebtest":
            System.out.println("Event Barrier:");
            EventBarrierTest.test();
            break;
          case "buildingTest":
            System.out.println("Building Test:");
            Test.test();
            break;
        }
	}

}

