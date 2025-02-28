import java.util.Scanner;  // Import the Scanner class
import java.time.LocalTime; // Import LocalTime class for representing time
class Main{           public static void main(String [] args){
        int pomodoro, breakTime, hours, minutes, totalSessions, scheduleType;
        String gym;
        boolean active, valid, sport, falseInput;
        Scanner input = new Scanner(System.in);
        //init pomodoro settings
        pomodoro = 50;
        breakTime = 10;
        active = true;
        valid = false;
        sport = false;
        falseInput = true;
        scheduleType = -1;
        //launch program
        while(active){
            sport = false;
            falseInput = true;
            System.out.println("Please choose one of the functions below by entering the number assigned to the function");
            System.out.println("1 - Start Scheduler");
            System.out.println("2 - Close Scheduler");
            System.out.println("----------------------------------------------------------------------------------------");
            switch(input.nextInt()){
                //launch scheduler
                case 1:
                    //starting screen
                    System.out.println("The scheduler will be started...");
                    //choose scheduler type -> morning or lunchtime
                    while(!valid){
                        System.out.println("Select one of the following options:");
                        System.out.println("1 - Morning Schedule");
                        System.out.println("2 - Schedule for current time");
                        scheduleType = input.nextInt();
                        switch(scheduleType){
                            case 1:
                                System.out.println("----------------------------------------------------------------------------------------");
                                System.out.println("Morning schedule selected, initialization process started...");
                                System.out.println("Are you hitting the gym today? Please answer with yes or no");
                                gym = input.next();
                                while(falseInput){
                                    
                                    if(gym.equalsIgnoreCase("yes")){
                                        sport = true;
                                        falseInput = false;
                                    }
                                    else if(gym.equalsIgnoreCase("no")){
                                        sport = false;
                                        falseInput = false;
                                    }
                                    else{
                                        System.out.println("False input, please answer with yes or no");
                                    }
                                }
                                valid = true;
                                break;
                            case 2:
                                System.out.println("----------------------------------------------------------------------------------------");
                                System.out.println("Current time schedule selected, initialization process started...");
                                System.out.println("Are you hitting the gym today? Please answer with yes or no");
                                gym = input.next();
                                while(falseInput){
                                    if(gym.equalsIgnoreCase("yes")){
                                        sport = true;
                                        falseInput = false;
                                    }
                                    else if(gym.equalsIgnoreCase("no")){
                                        sport = false;
                                        falseInput = false;
                                    }
                                    else{
                                        System.out.println("False input, please answer with yes or no");
                                    }
                                }
                                valid = true;
                                break;
                            default:
                                System.out.println("Invalid input, please enter either 1 or 2");
                                System.out.println("----------------------------------------------------------------------------------------");
                        }
                    }
                    valid = false;
                    //init scheduler
                    System.out.println("Enter total number of sessions");
                    totalSessions = input.nextInt();
                    System.out.println("How many sessions should be absolved in one unit?");
                    IntWrapper unitLength = new IntWrapper(input.nextInt());
                    Scheduler schedule;
                    if(scheduleType == 1){
                        System.out.println("At what time should I start to create your schedule? Please firstly submit the hours and then the minutes");
                    
                    hours = input.nextInt();
                    minutes = input.nextInt();
                    LocalTime time = LocalTime.of(hours,minutes);
                    schedule = new Scheduler(time, totalSessions, sport);
                    }
                    else{
                        LocalTime currTime = LocalTime.now();
                        LocalTime currTimeFiltered = currTime.withSecond(0).withNano(0);
                        schedule = new Scheduler(currTimeFiltered, totalSessions, sport);
                    }
                    //push initialization process to the top to hide it
                    for(int i = 0; i < 8; i++){
                        System.out.println();
                    }
                    System.out.println("Great! Here is your today's schedule, Kevin:");
                    //create schedule
                    if(scheduleType == 1){
                        schedule.printMorningSchedule(pomodoro, breakTime, unitLength.value);
                    }
                    else{
                        schedule.printTimeSchedule(pomodoro, breakTime, unitLength.value);
                    }
                    break;
                case 2:
                    //end screen
                    System.out.println("The scheduler will be closed...");
                    active = false;
                    //close scheduler
                    input.close();
                    break;
                default:
                    System.out.println("Invalid input, please enter either 1 or 2");
            }
        }
    }
}
