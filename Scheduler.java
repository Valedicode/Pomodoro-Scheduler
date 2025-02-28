import java.time.LocalTime; // Import LocalTime class for representing time
class Scheduler{
    
    int currSessionId;
    LocalTime currTime;
    int totalSessions;
    boolean sport;
    boolean lunch;
    boolean dinner;
    LocalTime lunchTime;
    LocalTime dinnerTime;
    //constructor
    public Scheduler(LocalTime currTime, int totalSessions, boolean sport){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
    }

    //create schedule by creating multiple sessions and acitvities 
    public void printMorningSchedule(int pomodoro, int breakTime, int unitLength){
        //morning
        printActivity(25, "Morning Routine");
        printActivity(40, "Breakfast");

        //5 minutes break 
        currTime = currTime.plusMinutes(5);

        //noon
        printSession(pomodoro, breakTime, unitLength);
        printActivity(40, "Lunch");

        //10 minutes break 
        currTime = currTime.plusMinutes(10);

        //evening
        printSession(pomodoro, breakTime, unitLength);
        printActivity(60, "Dinner");
        //10 minutes break + 20 minutes break to prepare sport clothes and driving to Fit X
        currTime = currTime.plusMinutes(10);
        if(sport){
            printActivity(120, "Gym");
        }
        //print sessions until sleepin time is reached
        while(currSessionId < totalSessions){
            printSession(pomodoro, breakTime, unitLength);
        }
    }

    public void printTimeSchedule(int pomodoro, int breakTime, int unitLength){
        //lunch time
        printSession(pomodoro, breakTime, unitLength);
        printActivity(60, "Dinner");
        //10 minutes break + 20 minutes break to prepare sport clothes and driving to Fit X
        currTime = currTime.plusMinutes(10);
        if(sport){
            printActivity(120, "Gym");
        }
        //print sessions until sleeping time is reached
        while(currSessionId < totalSessions){
            printSession(pomodoro, breakTime, unitLength);
        }
    }

    //print time schedule of a single acitivity in format: <hours:minutes> - <hours:minute>: <activity>
    public void printActivity(int minutes, String activity){
        System.out.print(currTime + " - ");
        currTime = currTime.plusMinutes(minutes);
        System.out.println(currTime + ": " + activity);
    }

    //print time schedule of a single acitivity in format: <hours:minutes> - <hours:minute>: <x> Session
    public void printSession(int minutes, int breakTime, int unit){
            //create pomodoro sessions -> number of sessions equals the value of unit
            for(int i = 0; i < unit; i++){
                if(currSessionId <= totalSessions-1){
                    System.out.print(currTime + " - ");
                    currTime = currTime.plusMinutes(minutes);
                    System.out.println(currTime + ": " + (currSessionId+1) + ".Session");
                    currSessionId++;
                    //10 minutes break only if session is not the last session in the unit
                    if(i < (unit-1)){
                        printActivity(10, "Break");
                    }
                    else{
                        printActivity(20, "Big Break");
                    }
                }
            }
    }
}