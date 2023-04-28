import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main {
  
  private static int wood = 0;

  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean gameRunning = true;
        boolean night = false;
        int day = 1;
        wood = 5;
        int food = 3;
        int traps = 1;
        int matches = 7;
        int health = 100;
        int lunaticHealth = 150;

      List<String> items = new ArrayList<String>();
      items.add("wood");

        int attackNight = 2; // the night on which the lunatic attacks
        int nightsPassed = 0; // the number of nights that have passed
        while (gameRunning) {
            clearConsole();
            System.out.println("Day " + day);
            System.out.println("Health: " + health);
            System.out.println("Wood: " + wood);
            System.out.println("Food: " + food);
            System.out.println("Traps: " + traps);
            System.out.println("Matches: " + matches);

          
            if (night) {
// output night message character by character
System.out.println();
String sayNight = "It's night be careful.";
for (int i = 0; i < sayNight.length(); i++) {
    System.out.print(sayNight.charAt(i));
    try {
        Thread.sleep(100); // wait for 100 milliseconds before printing the next character
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
System.out.println();

              
                nightsPassed++; // increment the number of nights that have passed
                if (nightsPassed == attackNight) {
                    System.out.println("The axed crazed lunatic is out there. Protect yourself...");
                      health = playerDamage(health); 
                      lunaticHealth = lunaticAttack(health, lunaticHealth, traps);
                    nightsPassed = 0; // reset the counter
                }

            }
            else {

System.out.println();
          if(day == 1){
            String sayIntro = ("You wake up lost in cabin no clue as to how you arrived. \n"
                              + "As you slowly awaken you hear a cynical laughter. \n"
                              + "in the distance your guard is up");
for (int i = 0; i < sayIntro.length(); i++) {
    System.out.print(sayIntro.charAt(i));
    try {
        Thread.sleep(50); // wait for 85 milliseconds before printing the next character
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
          }
// output day message character by character
                System.out.println();

              String sayDay = "It's day. You can go out and gather resources. \n" +
                  "What do you want to do? \n";
for (int i = 0; i < sayDay.length(); i++) {
    System.out.print(sayDay.charAt(i));
    try {
        Thread.sleep(85); // wait for 50 milliseconds before printing the next character
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
       
System.out.println();
                
                System.out.println("1. Gather wood");
                System.out.println("2. Hunt for food");
                System.out.println("3. Set traps");
                System.out.println("4. Rest");
                System.out.println("5. End game");

                input = scanner.nextLine();
                switch (input) {
                    case "1":
                        wood += gatherResource("wood");
                        break;
                    case "2":
                        food += gatherResource("food");
                        break;
                    case "3":
                        int success = setTrap(wood, items);
                        traps += success;
                        wood -= success * 2;
                        break;
                    case "4":
                        rest(health, matches, food);
                        break;
                    case "5":
                        gameRunning = false;
                        break;
                    default:
                        break;
                }
            }

            if (health <= 0) {
                gameRunning = false;
                System.out.println("You died. Game over.");
            }    if (wood == 0 && food == 0 && traps == 0) {
                System.out.println("You don't have any resources left. You can't survive anymore.");
                gameRunning = false;
            }
              if (lunaticHealth <= 0) {
                gameRunning = false;
                System.out.println("Congratulations! You defeated the axed crazed lunatic and escaped the forest!");
            } else {
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                day++;
                night = !night;
            }
        }
        scanner.close();
    }

public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
}

public static int gatherResource(String resource) {
    Random rand = new Random();
    int amountGathered = 0;

    switch (resource) {
        case "wood":
            amountGathered = rand.nextInt(4) + 1;
            System.out.println("You gathered " + amountGathered + " wood.");
            break;
        case "food":
            amountGathered = rand.nextInt(3) + 1;
            System.out.println("You caught " + amountGathered + " fish.");
            break;
        default:
            break;
    }
    return amountGathered;
}


public static int setTrap(int wood, List<String> items) {
    int success = 0;
    if (wood >= 2 && items.contains("wood")) {
        System.out.println("You set a trap.");
        success = 1;
        items.remove("wood");
    } else {
        System.out.println("You don't have enough resources to set a trap.");
    }
    return success;
}
  
    public static int playerDamage(int health) {
    System.out.println("The axed crazed lunatic attacked you!");
    int damage = new Random().nextInt(65) + 5;
    health -= damage;
    System.out.println("You received " + damage + " damage. Your health is now " + health);

    return health;
}


public static int lunaticAttack(int health, int lunaticHealth, int traps) {
    int damage = new Random().nextInt(10) + 10;
   lunaticHealth -= damage;
    System.out.println("You dealt " + damage + " damage to the axed crazed lunatic. Their health is now " + lunaticHealth);

    if (traps > 0 && Math.random() < 1) {
        lunaticHealth -= 35;
        System.out.println("The lunatic falls into a trap and takes 35 damage! they are now " + lunaticHealth);
        traps--;
    }

    return lunaticHealth;
}

  

public static int rest(int health, int matches, int food) {
    Scanner scanner = new Scanner(System.in);
    int healAmount = 0;
    int foodToConsume = 0;
    int matchesToConsume = 0;

   System.out.println("How much do you want to rest?");
int restTime = scanner.nextInt();

if (restTime <= 0) {
    System.out.println("Invalid rest time.");
    return health;
}

if (restTime > matches || restTime > food) {
    System.out.println("You don't have enough matches or food to rest for that long.");
    return health;
}

matchesToConsume = restTime;
foodToConsume = restTime;

healAmount = restTime * 25; // Increase health by 25 for each rest hour

if (health + healAmount > 100) {
    healAmount = 100 - health;
}

matches -= matchesToConsume;
food -= foodToConsume;
health += healAmount;

System.out.println("You have rested for " + restTime + " hours.");
System.out.println("Your health has increased by " + healAmount + " points.");
System.out.println("You have consumed " + matchesToConsume + " matches and " + foodToConsume + " food.");

return health;
    }
 
}