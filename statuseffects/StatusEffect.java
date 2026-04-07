public abstract class StatusEffect {
    // Attributes
    private String name;
    private int duration;
    
    // Constructor
    public StatusEffect(String name, int duration){
        this.name = name;
        this.duration = duration;
    }

    // Getter
    public String getName(){return this.name;}
    public int getDuration(){return this.duration;}

    // Controlled Setter
    public void decrementDuration(){
        this.duration--;
    }
    // Methods
    public abstract void apply(Combatant target);
    public void onExpire(Combatant target){}

}
