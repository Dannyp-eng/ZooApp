import java.util.*;

// ─────────────────────────────────────────────────────────────────────────────
//  ZOO FEEDING SCHEDULER
//  Run with:  java ZooApp.java
//
//  OOP Concepts:
//    ✔ Classes & Objects   — Animal, Lion, Elephant, Crocodile, Parrot, Penguin
//    ✔ Encapsulation       — private fields + validated getters/setters
//    ✔ Inheritance         — Animal → Mammal → Lion/Elephant
//                            Animal → Reptile → Crocodile
//                            Animal → Bird   → Parrot/Penguin
//    ✔ Polymorphism        — overridden eat/sleep/makeSound (runtime)
//                            overloaded feed/addAnimal (compile-time)
// ─────────────────────────────────────────────────────────────────────────────

// ══════════════════════════════════════════════════════════════════════════════
//  LAYER 1 — Abstract base class  (Encapsulation + base for Inheritance)
// ══════════════════════════════════════════════════════════════════════════════
abstract class Animal {

    private String name;
    private int    age;
    private double weightKg;
    private String enclosure;
    private int    feedEveryHours;
    private String lastFed = "Not yet fed";

    public Animal(String name, int age, double weightKg,
                  String enclosure, int feedEveryHours) {
        this.name          = name;
        this.age           = age;
        this.weightKg      = weightKg;
        this.enclosure     = enclosure;
        this.feedEveryHours = feedEveryHours;
    }

    // Abstract methods — every subclass MUST provide its own version (Polymorphism)
    public abstract String eat();
    public abstract String makeSound();
    public abstract String sleep();
    public abstract String getType();

    // Concrete method inherited by ALL subclasses
    public String getInfo() {
        return String.format("%-12s | %-18s | Age %-2d | %6.1f kg | %-14s | every %2dh | fed: %s",
                name, getType(), age, weightKg, enclosure, feedEveryHours, lastFed);
    }

    // Overloaded feed() — compile-time polymorphism
    public String feed(String time) {
        lastFed = time;
        return "  [" + time + "] " + name + " → " + eat();
    }
    public String feed() { return feed("now"); }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getName()          { return name; }
    public int    getAge()           { return age; }
    public double getWeightKg()      { return weightKg; }
    public String getEnclosure()     { return enclosure; }
    public int    getFeedEveryHours(){ return feedEveryHours; }
    public String getLastFed()       { return lastFed; }

    // ── Setters with validation (Encapsulation) ───────────────────────────────
    public void setName(String n) {
        if (n == null || n.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        name = n;
    }
    public void setAge(int a) {
        if (a < 0) throw new IllegalArgumentException("Age cannot be negative.");
        age = a;
    }
    public void setWeightKg(double w) {
        if (w <= 0) throw new IllegalArgumentException("Weight must be positive.");
        weightKg = w;
    }
    public void setEnclosure(String e)      { enclosure = e; }
    public void setFeedEveryHours(int h) {
        if (h < 1) throw new IllegalArgumentException("Interval must be >= 1 hour.");
        feedEveryHours = h;
    }
}

// ══════════════════════════════════════════════════════════════════════════════
//  LAYER 2 — Intermediate abstract classes  (Inheritance)
// ══════════════════════════════════════════════════════════════════════════════
abstract class Mammal extends Animal {
    private boolean furry;

    public Mammal(String name, int age, double weightKg,
                  String enclosure, int feedEveryHours, boolean furry) {
        super(name, age, weightKg, enclosure, feedEveryHours);
        this.furry = furry;
    }

    @Override public String sleep() {
        return getName() + " curls up for a deep mammal sleep.";
    }
    public String groom() {
        return furry ? getName() + " grooms its thick fur coat."
                     : getName() + " smooths down its short coat.";
    }
}

abstract class Reptile extends Animal {
    private double bodyTempC;

    public Reptile(String name, int age, double weightKg,
                   String enclosure, int feedEveryHours, double bodyTempC) {
        super(name, age, weightKg, enclosure, feedEveryHours);
        this.bodyTempC = bodyTempC;
    }

    @Override public String sleep() {
        return getName() + " enters cold-blooded torpor (body temp: " + bodyTempC + "°C).";
    }
    public String bask() {
        return getName() + " basks under the heat lamp to warm up.";
    }
    public double getBodyTempC()           { return bodyTempC; }
    public void   setBodyTempC(double t)   { bodyTempC = t; }
}

abstract class Bird extends Animal {
    private double wingspanCm;
    private boolean canFly;

    public Bird(String name, int age, double weightKg,
                String enclosure, int feedEveryHours,
                double wingspanCm, boolean canFly) {
        super(name, age, weightKg, enclosure, feedEveryHours);
        this.wingspanCm = wingspanCm;
        this.canFly     = canFly;
    }

    @Override public String sleep() {
        return getName() + " tucks its head under a wing and roosts.";
    }
    public String fly() {
        return canFly ? getName() + " soars on its " + wingspanCm + " cm wings."
                      : getName() + " cannot fly — stretches its " + wingspanCm + " cm wings.";
    }
}

// ══════════════════════════════════════════════════════════════════════════════
//  LAYER 3 — Concrete classes  (Full Inheritance + Polymorphism overrides)
// ══════════════════════════════════════════════════════════════════════════════
class Lion extends Mammal {
    private String prideRole;

    public Lion(String name, int age, double weightKg, String enclosure, String prideRole) {
        super(name, age, weightKg, enclosure, 8, true);
        this.prideRole = prideRole;
    }
    @Override public String eat()       { return getName() + " tears into 15 kg of raw beef. [Carnivore]"; }
    @Override public String makeSound() { return getName() + " lets out a thunderous ROOOAAR!"; }
    @Override public String getType()   { return "Lion (" + prideRole + ")"; }
}

class Elephant extends Mammal {
    private double tuskLenCm;

    public Elephant(String name, int age, double weightKg, String enclosure, double tuskLenCm) {
        super(name, age, weightKg, enclosure, 4, true);
        this.tuskLenCm = tuskLenCm;
    }
    @Override public String eat()       { return getName() + " grazes through 150 kg of grass and fruit. [Herbivore]"; }
    @Override public String makeSound() { return getName() + " raises its trunk and TRUMPETS!"; }
    @Override public String getType()   { return "Elephant (tuskLength=" + tuskLenCm + " cm)"; }
}

class Crocodile extends Reptile {
    private double jawPsi;

    public Crocodile(String name, int age, double weightKg, String enclosure, double jawPsi) {
        super(name, age, weightKg, enclosure, 48, 30.0);
        this.jawPsi = jawPsi;
    }
    @Override public String eat()       { return getName() + " death-rolls and swallows whole fish. [Carnivore]"; }
    @Override public String makeSound() { return getName() + " HISSSES and snaps its " + (int)jawPsi + " psi jaws!"; }
    @Override public String getType()   { return "Crocodile"; }
}

class Parrot extends Bird {
    private String[] vocab;

    public Parrot(String name, int age, double weightKg, String enclosure, String[] vocab) {
        super(name, age, weightKg, enclosure, 6, 60.0, true);
        this.vocab = vocab;
    }
    @Override public String eat()       { return getName() + " nibbles seeds, nuts, and fresh fruit. [Omnivore]"; }
    @Override public String makeSound() {
        String word = vocab.length > 0 ? vocab[(int)(Math.random() * vocab.length)] : "Squawk";
        return getName() + " screeches: \"" + word + "!\"";
    }
    @Override public String getType()   { return "Parrot"; }
}

class Penguin extends Bird {
    private double swimSpeedKmh;

    public Penguin(String name, int age, double weightKg, String enclosure, double swimSpeedKmh) {
        super(name, age, weightKg, enclosure, 5, 30.0, false);
        this.swimSpeedKmh = swimSpeedKmh;
    }
    @Override public String eat()       { return getName() + " gulps down fresh fish and squid. [Carnivore]"; }
    @Override public String makeSound() { return getName() + " honks and SQUAWKS loudly!"; }
    @Override public String getType()   { return "Penguin"; }
    public    String swim()             { return getName() + " zips through water at " + swimSpeedKmh + " km/h!"; }
}

// ══════════════════════════════════════════════════════════════════════════════
//  SCHEDULER — manages all animals using superclass references (Polymorphism)
// ══════════════════════════════════════════════════════════════════════════════
class FeedingScheduler {
    private String       zooName;
    private List<Animal> animals = new ArrayList<>();

    public FeedingScheduler(String zooName) { this.zooName = zooName; }

    // Overloaded addAnimal (compile-time polymorphism)
    public void addAnimal(Animal a)           { animals.add(a); }
    public void addAnimal(Animal... many)     { for (Animal a : many) addAnimal(a); }

    // Overloaded feedAll (compile-time polymorphism)
    public void feedAll(String time) {
        header("FEEDING ALL ANIMALS @ " + time);
        for (Animal a : animals) System.out.println(a.feed(time));  // runtime polymorphism
    }
    public void feedAll(String time, String type) {
        header("FEEDING " + type.toUpperCase() + "S @ " + time);
        boolean found = false;
        for (Animal a : animals) {
            if (a.getType().toLowerCase().contains(type.toLowerCase())) {
                System.out.println(a.feed(time));
                found = true;
            }
        }
        if (!found) System.out.println("  No animals of type '" + type + "' found.");
    }

    public void morningRoutine() {
        header("MORNING ROUTINE — " + zooName);
        for (Animal a : animals) System.out.println("  " + a.makeSound()); // runtime polymorphism
    }

    public void eveningRoutine() {
        header("EVENING ROUTINE — " + zooName);
        for (Animal a : animals) System.out.println("  " + a.sleep());     // runtime polymorphism
    }

    public void displayRoster() {
        header("ZOO ROSTER — " + zooName + " (" + animals.size() + " animals)");
        System.out.printf("  %-12s | %-18s | %-6s | %-9s | %-14s | %-10s | %s%n",
                "Name","Type","Age","Weight","Enclosure","Feed Every","Last Fed");
        System.out.println("  " + repeatChar('─', 95));
        for (Animal a : animals) System.out.println("  " + a.getInfo());
    }

    public void displaySchedule() {
        header("FEEDING SCHEDULE — " + zooName);
        System.out.printf("  %-12s | %-18s | %s%n", "Name", "Type", "Feed Every");
        System.out.println("  " + repeatChar('─', 48));
        for (Animal a : animals)
            System.out.printf("  %-12s | %-18s | every %d hours%n",
                    a.getName(), a.getType(), a.getFeedEveryHours());
    }

    public List<Animal> getAnimals() { return animals; }

    private String repeatChar(char ch, int count) {
        char[] arr = new char[count];
        java.util.Arrays.fill(arr, ch);
        return new String(arr);
    }

    private void header(String title) {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.printf ("  ║  %-52s║%n", title);
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
    }
}

// ══════════════════════════════════════════════════════════════════════════════
//  MAIN — entry point + interactive menu
// ══════════════════════════════════════════════════════════════════════════════
class ZooApp {
    static Scanner sc = new Scanner(System.in);
    static FeedingScheduler zoo;

    public static void main(String[] args) {
        banner();
        zoo = buildZoo();

        boolean running = true;
        while (running) {
            menu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    zoo.displayRoster();
                    break;
                case "2":
                    zoo.displaySchedule();
                    break;
                case "3":
                    zoo.morningRoutine();
                    break;
                case "4":
                    zoo.eveningRoutine();
                    break;
                case "5":
                    feedAll();
                    break;
                case "6":
                    feedByType();
                    break;
                case "7":
                    demoPolymorphism();
                    break;
                case "8":
                    demoEncapsulation();
                    break;
                case "9":
                    demoInheritance();
                    break;
                case "0":
                    System.out.println("\n  Goodbye! The animals are well fed.\n");
                    running = false;
                    break;
                default:
                    System.out.println("\n  Invalid option. Try again.");
                    break;
            }
        }
        sc.close();
    }

    // ── Build the zoo ─────────────────────────────────────────────────────────
    static FeedingScheduler buildZoo() {
        FeedingScheduler z = new FeedingScheduler("Sunshine City Zoo");
        System.out.println("\n  Loading animals...");
        z.addAnimal(
            new Lion     ("Simba",   5, 190.0, "Savanna Pen",   "Alpha"),
            new Lion     ("Nala",    4, 130.0, "Savanna Pen",   "Hunter"),
            new Elephant ("Dumbo",  12,4800.0, "Elephant Yard", 95.0),
            new Crocodile("Rex",    20, 450.0, "Croc Pool",     2500.0),
            new Parrot   ("Polly",   3,   1.2, "Aviary",        new String[]{"Hello!", "Pretty bird!", "Feed me!"}),
            new Penguin  ("Skipper", 7,  10.5, "Penguin Cove",  36.0)
        );
        System.out.println("  Done! " + z.getAnimals().size() + " animals loaded.\n");
        return z;
    }

    // ── Menu actions ──────────────────────────────────────────────────────────
    static void feedAll() {
        System.out.print("\n  Enter feeding time (e.g. 08:00 AM): ");
        String t = sc.nextLine().trim();
        zoo.feedAll(t.isEmpty() ? "now" : t);
    }

    static void feedByType() {
        System.out.print("\n  Enter type to feed (e.g. Lion / Mammal / Bird / Reptile): ");
        String type = sc.nextLine().trim();
        System.out.print("  Enter feeding time: ");
        String t = sc.nextLine().trim();
        zoo.feedAll(t.isEmpty() ? "now" : t, type);
    }

    static void demoPolymorphism() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║  POLYMORPHISM DEMO                                   ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");

        System.out.println("\n  ► Runtime Polymorphism — Animal[] holds 5 different subclasses.");
        System.out.println("    Same method calls dispatch the correct override at runtime:\n");

        // Superclass references holding subclass objects
        Animal[] animals = {
            new Lion     ("Leo",    3, 170.0, "Pen A",  "Cub"),
            new Elephant ("Ellie",  8,3900.0, "Yard B", 80.0),
            new Crocodile("Crunch",15, 380.0, "Pool C", 2200.0),
            new Parrot   ("Rio",    2,   0.9, "Cage D", new String[]{"Ciao!"}),
            new Penguin  ("Tux",    5,  11.0, "Cove E", 30.0)
        };
        for (Animal a : animals) {
            System.out.println("  [" + a.getType() + "]");
            System.out.println("    eat()       → " + a.eat());
            System.out.println("    makeSound() → " + a.makeSound());
            System.out.println("    sleep()     → " + a.sleep());
            System.out.println();
        }

        System.out.println("  ► Compile-time Polymorphism — overloaded feed() methods:");
        Animal a = new Lion("Mufasa", 10, 200.0, "Royal Den", "Alpha");
        System.out.println("    feed()           → " + a.feed());
        System.out.println("    feed(\"07:00 AM\") → " + a.feed("07:00 AM"));
    }

    static void demoEncapsulation() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║  ENCAPSULATION DEMO                                  ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");

        Lion lion = new Lion("Bruno", 3, 160.0, "Test Pen", "Hunter");

        System.out.println("\n  All fields are private — accessed only via getters:");
        System.out.println("    getName()          → " + lion.getName());
        System.out.println("    getAge()           → " + lion.getAge());
        System.out.println("    getWeightKg()      → " + lion.getWeightKg() + " kg");
        System.out.println("    getFeedEveryHours()→ " + lion.getFeedEveryHours() + "h");

        System.out.println("\n  Updating via setters:");
        lion.setWeightKg(175.5);
        System.out.println("    setWeightKg(175.5) → " + lion.getWeightKg() + " kg");
        lion.setAge(4);
        System.out.println("    setAge(4)          → " + lion.getAge());

        System.out.println("\n  Setter validation — setAge(-5):");
        try { lion.setAge(-5); }
        catch (IllegalArgumentException e) { System.out.println("    ✔ Caught: \"" + e.getMessage() + "\""); }

        System.out.println("  Setter validation — setWeightKg(0):");
        try { lion.setWeightKg(0); }
        catch (IllegalArgumentException e) { System.out.println("    ✔ Caught: \"" + e.getMessage() + "\""); }
    }

    static void demoInheritance() {
        System.out.println("\n  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║  INHERITANCE CHAIN DEMO                              ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println(
            "    Animal  (abstract)\n" +
            "    ├── Mammal   (abstract) → adds: furry, groom()\n" +
            "    │   ├── Lion            → adds: prideRole\n" +
            "    │   └── Elephant        → adds: tuskLenCm\n" +
            "    ├── Reptile  (abstract) → adds: bodyTempC, bask()\n" +
            "    │   └── Crocodile       → adds: jawPsi\n" +
            "    └── Bird     (abstract) → adds: wingspanCm, fly()\n" +
            "        ├── Parrot          → adds: vocab[]\n" +
            "        └── Penguin         → adds: swimSpeedKmh, swim()\n"
        );

        Lion      lion  = new Lion     ("Scar",   7, 185.0, "Den",   "Alpha");
        Crocodile croc  = new Crocodile("Nile",  25, 500.0, "Pool",  2800.0);
        Penguin   peng  = new Penguin  ("Wade",   6,  12.0, "Cove",  38.0);

        System.out.println("  Lion — getInfo() from Animal:");
        System.out.println("    " + lion.getInfo());
        System.out.println("  Lion — groom() from Mammal:");
        System.out.println("    " + lion.groom());

        System.out.println("\n  Crocodile — bask() from Reptile:");
        System.out.println("    " + croc.bask());

        System.out.println("\n  Penguin — fly() from Bird (can't fly):");
        System.out.println("    " + peng.fly());
        System.out.println("  Penguin — swim() its own method:");
        System.out.println("    " + peng.swim());
    }

    // ── UI helpers ────────────────────────────────────────────────────────────
    static void banner() {
        System.out.println(
            "  ╔══════════════════════════════════════════════════════════╗\n" +
            "  ║        ZOO FEEDING SCHEDULER                            ║\n" +
            "  ║        Java OOP MVP — Console Application               ║\n" +
            "  ╚══════════════════════════════════════════════════════════╝");
    }

    static void menu() {
        System.out.println(
            "\n" +
            "  ┌──────────────────────────────────────┐\n" +
            "  │              MAIN MENU               │\n" +
            "  ├──────────────────────────────────────┤\n" +
            "  │  1. View zoo roster                  │\n" +
            "  │  2. View feeding schedule            │\n" +
            "  │  3. Morning routine (sounds)         │\n" +
            "  │  4. Evening routine (sleep)          │\n" +
            "  │  5. Feed all animals                 │\n" +
            "  │  6. Feed animals by type             │\n" +
            "  │  ─────────────────────────────────── │\n" +
            "  │  7. Demo → Polymorphism              │\n" +
            "  │  8. Demo → Encapsulation             │\n" +
            "  │  9. Demo → Inheritance chain         │\n" +
            "  │  ─────────────────────────────────── │\n" +
            "  │  0. Exit                             │\n" +
            "  └──────────────────────────────────────┘");
        System.out.print("  Choose: ");
    }
}