import java.time.Duration;
import java.time.LocalTime; // Import LocalTime class for representing time
class Scheduler{
    
    int currSessionId;
    LocalTime currTime;
    LocalTime dinnerTime;
    LocalTime lunchTime;
    LocalTime sportTime;
    int totalSessions;
    boolean sport;
    boolean sportFin;
    boolean dinner;
    boolean lunch;
    //constructor
    public Scheduler(LocalTime currTime, int totalSessions, boolean sport, LocalTime dinnerTime, LocalTime lunchTime, LocalTime sportTime){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
        this.dinnerTime = dinnerTime;
        this.lunchTime = lunchTime;
        this.sportTime = sportTime;
        this.dinner = false;
        this.lunch = false;
        this.sportFin = false;
    }

    public Scheduler(LocalTime currTime, int totalSessions, boolean sport, LocalTime dinnerTime, LocalTime lunchTime){
        currSessionId = 0;
        this.currTime = currTime;
        this.totalSessions = totalSessions;
        this.sport = sport;
        this.dinnerTime = dinnerTime;
        this.lunchTime = lunchTime;
        this.dinner = false;
        this.lunch = false;
        this.sportFin = false;
    }

    public void printTimeSchedule(int pomodoro, int breakTime, int unitLength){
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
            //Duration residualTime;
            //long rest;
            // schedule order: lunch -> sport -> dinner
            if(lunchTime.isBefore(sportTime) && sportTime.isBefore(dinnerTime)){
                for(int i = 0; i < unit; i++){
                    // print one learning unit comprising x sessions 
                    if(currSessionId <= totalSessions-1){
                        //lunch time
                        // case 1: lunch time and session time overlap -> adapt session duration up to the time when lunch starts
                        if(!lunch){
                            overlapSession(minutes, lunch, "Lunch", lunchTime);
                        }
                        // case 2: session breaks and lunch time can overlap -> if overlap just start lunchTime
                        if(!lunch){
                            overlapBreak(i, unit, minutes, lunch, "Lunch", lunchTime);
                            break;
                        }
    
                        //sport time 
                        if(sport){
                            if(!sportFin){
                                overlapSession(minutes, sportFin, "Gym", sportTime);
                            }
                        }

                        // case 2: session breaks and sport time can overlap -> if overlap just start sportTime
                        if(sport){
                            if(!sportFin){
                                overlapBreak(i, unit, minutes, sportFin, "Gym", sportTime);
                                break;
                            }
                        }
                        
                        // dinner time
                        if(lunch){
                            if(!dinner){
                                overlapSession(minutes, dinner, "Dinner", dinnerTime);
                                break;
                            }
                            else{
                                printSession(50, currSessionId+1);
                            }
                        }
    
                        // consider edge case that because of fixed dinnerTime break can just be couple of minutes 
                        // -> instead just start dinnerTime right away
                        if(lunch){
                            if(!dinner){
                                overlapBreak(i, unit, minutes, dinner, "Dinner", dinnerTime);
                                break;
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
            // schedule order: lunch -> dinner -> sport
            else if(lunchTime.isBefore(sportTime) && dinnerTime.isBefore(sportTime)){
                for(int i = 0; i < unit; i++){
                    // print one learning unit comprising x sessions 
                    if(currSessionId <= totalSessions-1){
                        //lunch time
                        // case 1: lunch time and session time overlap -> adapt session duration up to the time when lunch starts
                        if(!lunch){
                            overlapSession(minutes, lunch, "Lunch", lunchTime);
                        }
                        // case 2: session breaks and lunch time can overlap -> if overlap just start lunchTime
                        if(!lunch){
                            overlapBreak(i, unit, minutes, lunch, "Lunch", lunchTime);
                            break;
                        }
                        // dinner time
                        if(lunch){
                            if(!dinner){
                                overlapSession(minutes, dinner, "Dinner", dinnerTime);
                            }
                        }
    
                        // consider edge case that because of fixed dinnerTime break can just be couple of minutes 
                        // -> instead just start dinnerTime right away
                        if(lunch){
                            if(!dinner){
                                overlapBreak(i, unit, minutes, dinner, "Dinner", dinnerTime);
                                break;
                            }
                        }

                        //sport time 
                        if(sport){
                            if(!sportFin){
                                overlapSession(minutes, sportFin, "Gym", sportTime);
                                break;
                            }
                            else{
                                printSession(50, currSessionId+1);
                            }
                        }
    
                        // case 2: session breaks and sport time can overlap -> if overlap just start sportTime
                        if(sport){
                            if(!sportFin){
                                overlapBreak(i, unit, minutes, sportFin, "Gym", sportTime);
                                break;
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
            //schedule order: sport -> lunch -> dinner
            else if(sportTime.isBefore(lunchTime)){
                for(int i = 0; i < unit; i++){
                    // print one learning unit comprising x sessions 
                    if(currSessionId <= totalSessions-1){
                        //sport time 
                        if(sport){
                            if(!sportFin){
                                overlapSession(minutes, sportFin, "Gym", sportTime);
                            }
                        }
    
                        // case 2: session breaks and sport time can overlap -> if overlap just start sportTime
                        if(sport){
                            if(!sportFin){
                                overlapBreak(i, unit, minutes, sportFin, "Gym", sportTime);
                                break;
                            }
                        }

                        //lunch time
                        // case 1: lunch time and session time overlap -> adapt session duration up to the time when lunch starts
                        if(!lunch){
                            overlapSession(minutes, lunch, "Lunch", lunchTime);
                        }
                        // case 2: session breaks and lunch time can overlap -> if overlap just start lunchTime
                        if(!lunch){
                            overlapBreak(i, unit, minutes, lunch, "Lunch", lunchTime);
                            break;
                        }
                        
                        // dinner time
                        if(lunch){
                            if(!dinner){
                                overlapSession(minutes, dinner, "Dinner", dinnerTime);
                                break;
                            }
                            else{
                                printSession(50, currSessionId+1);
                            }
                        }
    
                        // consider edge case that because of fixed dinnerTime break can just be couple of minutes 
                        // -> instead just start dinnerTime right away
                        if(lunch){
                            if(!dinner){
                                overlapBreak(i, unit, minutes, dinner, "Dinner", dinnerTime);
                                break;
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
    private void overlapSession(int minutes, boolean activity, String action,LocalTime timePoint){
        Duration residualTime;
        long rest;
        if(!activity){
            if(currTime.plusMinutes(minutes).isAfter(timePoint)){
                residualTime = Duration.between(currTime, timePoint);
                rest = residualTime.toMinutes() % 60;
                if(rest == 0){
                    printActivity(minutes, action);
                }
                else if(rest < 20){
                    printActivity(rest, "Puffer - not meaningful to start a new session");
                    printActivity(60, action);
                }
                else{
                    //print session but with adapted length since in conflict with action
                    printSession(rest, currSessionId+1);
                    //print action
                    printActivity(60, action);
                }
                switch(action){
                    case "Lunch":
                        lunch = true;
                        break;
                    case "Gym":
                        sportFin = true;
                        break;
                    case "Dinner":
                        dinner = true;
                        break;
                }
            }
            else{
                printSession(50, currSessionId+1);
            }
        }
    }
    private void overlapBreak(int i, int unit, int minutes, boolean activity, String action,LocalTime timePoint){
        if(i < (unit-1)){
            if(!activity){
            //small session break overlaps with lunch -> just start lunchTime 
                if(currTime.plusMinutes(10).isAfter(timePoint)){
                    printActivity(60, action);
                    switch(action){
                        case "Lunch":
                            lunch = true;
                            break;
                        case "Gym":
                            sportFin = true;
                            break;
                        case "Dinner":
                            dinner = true;
                            break;
                    }
                }
                else{
                    printActivity(10, "Break");
                }   
            }
            else{
                printActivity(10, "Break");
            }
        }
        else{
            if(!activity){
            //big session break overlaps with lunch -> just start lunchTime 
                if(currTime.plusMinutes(20).isAfter(timePoint)){
                    printActivity(60, action);
                    switch(action){
                        case "Lunch":
                            lunch = true;
                            break;
                        case "Gym":
                            sportFin = true;
                            break;
                        case "Dinner":
                            dinner = true;
                            break;
                    }
                }
                else{
                    printActivity(20, "Big Break");
                }
            }
            else{
                printActivity(20, "Big Break");
            }
        }
    }
}