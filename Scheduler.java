import java.time.Duration;
import java.time.LocalTime; // Import LocalTime class for representing time
class Scheduler{
    
    int currSessionId;
    LocalTime currTime;
    LocalTime dinnerTime;
    LocalTime lunchTime;
    int totalSessions;
    boolean sport;
    boolean dinner;
    boolean lunch;
    //constructor
    public Scheduler(LocalTime currTime, int totalSessions, boolean sport, LocalTime dinnerTime, LocalTime lunchTime){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
        this.dinnerTime = dinnerTime;
        this.lunchTime = lunchTime;
        this.dinner = false;
        this.lunch = false;
    }

    public Scheduler(LocalTime currTime, int totalSessions, boolean sport, LocalTime dinnerTime){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
        this.dinnerTime = dinnerTime;
        this.dinner = false;
    }


    public Scheduler(LocalTime currTime, int totalSessions, boolean sport){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
    }

    //create schedule by creating multiple sessions and acitvities 
    //bug: conflict with newly implemented printPomodoro method
    public void printMorningSchedule(int pomodoro, int breakTime, int unitLength){
        //morning
        printActivity(25, "Morning Routine");
        printActivity(40, "Breakfast");

        //5 minutes break 
        currTime = currTime.plusMinutes(5);

        //noon
        printPomodoro(pomodoro, breakTime, unitLength);
        printActivity(40, "Lunch");

        //10 minutes break 
        currTime = currTime.plusMinutes(10);

        //evening
        printPomodoro(pomodoro, breakTime, unitLength);
        printActivity(60, "Dinner");
        //10 minutes break + 20 minutes break to prepare sport clothes and driving to Fit X
        currTime = currTime.plusMinutes(10);
        if(sport){
            printActivity(120, "Gym");
        }
        //print sessions until sleepin time is reached
        while(currSessionId < totalSessions){
            printPomodoro(pomodoro, breakTime, unitLength);
        }
    }

    public void printTimeSchedule(int pomodoro, int breakTime, int unitLength){
        //lunch time
        printPomodoro(pomodoro, breakTime, unitLength);
        //10 minutes break + 20 minutes break to prepare sport clothes and driving to Fit X
        currTime = currTime.plusMinutes(10);
        if(sport){
            printActivity(120, "Gym");
        }
        //print sessions until sleeping time is reached
        while(currSessionId < totalSessions){
            printPomodoro(pomodoro, breakTime, unitLength);
        }
    }

    //print time schedule of a single acitivity in format: <hours:minutes> - <hours:minute>: <activity>
    private void printActivity(long minutes, String activity){
        System.out.print(currTime + " - ");
        currTime = currTime.plusMinutes(minutes);
        System.out.println(currTime + ": " + activity);
    }

    private void printSession(long minutes, int sessionId){
        System.out.print(currTime + " - ");
        currTime = currTime.plusMinutes(minutes);
        System.out.println(currTime + ": " + (sessionId) + ".Session");
        currSessionId++;
    }

    //print time schedule of a single acitivity in format: <hours:minutes> - <hours:minute>: <x> Session
    private void printPomodoro(int minutes, int breakTime, int unit){
            //create pomodoro sessions -> number of sessions equals the value of unit
            Duration residualTime;
            long rest;
            for(int i = 0; i < unit; i++){
                if(currSessionId <= totalSessions-1){
                    //lunch time
                    if(!lunch){
                        if(currTime.plusMinutes(minutes).isAfter(lunchTime)){
                            residualTime = Duration.between(currTime, lunchTime);
                            rest = residualTime.toMinutes() % 60;
                            if(rest < 20){
                                printActivity(rest, "Puffer - not meaningful to start a new session");
                                printActivity(60, "lunch");
                                lunch = true;
                                break;
                            }
                            else{
                                printSession(rest, currSessionId+1);
                                printActivity(60, "lunch");
                                lunch = true;
                                break;
                            }
                        }
                        printSession(50, currSessionId+1);
                    }
                    // dinner time
                    if(lunch){
                        if(!dinner){
                            if(currTime.plusMinutes(minutes).isAfter(dinnerTime)){
                                residualTime = Duration.between(currTime, dinnerTime);
                                rest = residualTime.toMinutes() % 60;
                                if(rest < 20){
                                    printActivity(rest, "Puffer - not meaningful to start a new session");
                                    printActivity(60, "Dinner");
                                    dinner = true;
                                    break;
                                }
                                else{
                                    printSession(rest, currSessionId+1);
                                    printActivity(60, "Dinner");
                                    dinner = true;
                                    break;
                                }
                            }
                            printSession(50, currSessionId+1);
                        }
                        else{
                            printSession(50, currSessionId+1);
                        }
                    }

                    // consider edge case that because of fixed dinnerTime break can just be couple of minutes -> instead just start dinnerTIme right away
                    if(!lunch){
                        if(i < (unit-1)){
                            if(currTime.plusMinutes(10).isAfter(lunchTime)){
                                printActivity(60, "Lunch");
                                lunch = true;
                                break;
                            }
                            else{
                                printActivity(10, "Break");
                            }
                        }
                        else{
                            if(currTime.plusMinutes(20).isAfter(lunchTime)){
                                printActivity(60, "Lunch");
                                lunch = true;
                                break;
                            }
                            else{
                                printActivity(20, "Big Break");
                            }
                        }
                    }

                    // consider edge case that because of fixed dinnerTime break can just be couple of minutes -> instead just start dinnerTIme right away
                    if(lunch){
                        if(!dinner){
                            if(i < (unit-1)){
                                if(currTime.plusMinutes(10).isAfter(dinnerTime)){
                                    printActivity(60, "Dinner");
                                    dinner = true;
                                    break;
                                }
                                else{
                                    printActivity(10, "Break");
                                }
                            }
                            else{
                                if(currTime.plusMinutes(20).isAfter(dinnerTime)){
                                    printActivity(60, "Dinner");
                                    dinner = true;
                                    break;
                                }
                                else{
                                    printActivity(20, "Big Break");
                                }
                            }
                        }
                        else{
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
    }
}