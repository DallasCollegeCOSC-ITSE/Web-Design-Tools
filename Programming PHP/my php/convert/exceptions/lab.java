

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class lab
{
    public static void main(String[] args) throws FileNotFoundException {
      
    	Craftingtable ct=new Craftingtable();
    	
    	//human player
        lab p = new lab();
        p.inventory.owner = p;
        p.inventory.putOn(Slot.Torso, new Clothes(Slot.Torso,0,1,0));
        p.inventory.putOn(Slot.Pants, new Clothes(Slot.Pants,1,1,0));
        p.inventory.putOn(Slot.Hands, new Clothes(Slot.Hands,1,0,1));
        p.inventory.add(new Food("Donut",300,10,-5,10,-10),20);
        p.inventory.add(new WoodStick(),10);
        p.inventory.add(new Match(),5);
        p.inventory.add(new WoodStick(),10);


        //dog player
        lab dog = new lab();
        dog.dogInventory.owner = dog;
        dog.dogInventory.putOnDog(Slot.Muzzle, new dogClothes(Slot.Muzzle,0,1,0));
        dog.dogInventory.putOnDog(Slot.Collar, new dogClothes(Slot.Collar,1,1,0));
        dog.dogInventory.putOnDog(Slot.Shoes, new dogClothes(Slot.Shoes,1,0,1));
        dog.dogInventory.add(new Food("Dog Food",500,20,-5,10,-20),10);
        dog.dogInventory.add(new WoodStick(),10);
       

        //lets let some time pass
        for (int time=0;time<50;time++)
        {
            p.inventory.updateCondition();
            dog.inventory.updateCondition();
        }
        
        ct.craftingtable("F:\\eclipse-jee-2021-12-R-win32-x86_64\\eclipse\\Workspace\\Lab 7\\src\\recipes (1).txt");
        
        p.PrintInfo("Human");
        dog.PrintInfo("Dog");
        
        

    }

    int calories, energy, focus, warmth, fatigue, defense, thermalInsulation, offense;
    Inventory inventory = new Inventory();
    Dog dogInventory=new Dog();
    public lab()
    {
        //player baseline
        calories = 800;
        energy = 100;
        focus = 50;
        warmth = 100;
        fatigue = 0;
        defense = 0;
        thermalInsulation = 1;
        offense = 1;
    }
    public void PrintInfo(String name)
    {
        System.out.println("\nPlayer: " +name);
        System.out.println("Calories: "+this.calories);
        System.out.println("Energy: "+this.energy);
        System.out.println("Focus: "+this.focus);
        System.out.println("Warmth: "+this.warmth);
        System.out.println("Fatigue: "+this.fatigue);
        System.out.println("Defense: "+this.defense);
        System.out.println("ThermalInsulation: "+this.thermalInsulation);
        System.out.println("Offense: "+this.offense);
        System.out.println("Bag: "+this.inventory.print());
        System.out.println("Bag: "+this.dogInventory.print());
    }

    
   

}

class Inventory
{
    
	private  ArrayList<Item> bag = new ArrayList<>();
    lab owner;
lab pet;
    ArrayList<WearableSlot> wearables = new ArrayList<>(Arrays.asList(new WearableSlot[]{
            new WearableSlot(Slot.Torso,null),
            new WearableSlot(Slot.Pants,null),
            new WearableSlot(Slot.Hands,null),
            new WearableSlot(Slot.Shoes,null),
            new WearableSlot(Slot.Weapon,null),
            new WearableSlot(Slot.Vehicle,null),
    }));

    public void add(Item i, int weight)
    {
        final int MAX=30;
        if(weight<=MAX) {
            getBag().add(i);
        }else{
            System.out.println("Can not add " + i+" to bag. Weight of "+ weight +" exceeds weight limit.");
        }
    }
   
	public  String print()
    {
        return getBag().toString();
    }
    public void updateCondition()
    {
        for (int i=0;i<getBag().size();i++)
        {
            if (getBag().get(i) instanceof Conditional)
                ((Conditional)getBag().get(i)).UpdateCondition();
        }
        for (int i=0;i<wearables.size();i++)
        {
            if (wearables.get(i) instanceof Conditional)
                if (((Conditional)wearables.get(i)).UpdateCondition())
                {
                    wearables.remove(i);
                    i--;
                }

        }
    }
    public Item remove(int index)
    {
        return getBag().remove(index);
    }
    //note that there is no get, remove the item do what you will to it and return it to the bag.

    public void putOn(Slot s, Wearable item)
    {
        if (getSlot(s).currentItem != null)
            getSlot(s).currentItem.takeOff(this.owner);

        getSlot(s).currentItem = item;
        item.putOn(this.owner);

    }
    public void putOnDog(Slot s, Wearable item) {
    	

         getSlot(s).currentItem = item;
         item.putOn(this.owner);
		
	}
    public void eat(Consumable c, float percent)
    {
        if (c.Consume(percent, owner))
            getBag().remove(c);

    }
    public WearableSlot getSlot(Slot s)
    {
        for (WearableSlot w : wearables)
            if (w.slot == s)
                return w;
        return null;
    }

	public  ArrayList<Item> getBag() {
		return bag;
	}
}
enum Slot
{
    Torso,
    Pants,
    Hands,
    Shoes,
    Weapon,
    Vehicle,
    Face,
    Paws,
    Collar,
    Muzzle
}
interface Conditional
{
    default boolean UpdateCondition()
    {
        System.out.println("this items condition never changes: "+this);
        return false;
    }
}
interface Wearable extends Conditional{

    void takeOff(lab owner);
    void putOn(lab owner);
}
interface Weapon extends Wearable
{}
class WearableSlot
{
    public WearableSlot(Slot slot, Wearable currentItem)
    {
        this.slot = slot;
        this.currentItem = currentItem;
    }
    public Wearable putOnItem(Wearable newItem)
    {
        Wearable old = currentItem;
        currentItem = newItem;
        return old;
    }
    Slot slot;
    Wearable currentItem;
}

class Clothes extends Item implements Wearable
{
    int conditionRate = 1;
    int condition = 100;
    Slot slotToBeWornOn;
    int defense;
    int thermalInsulation;
    int offense;
    public Clothes(Slot slot, int i, int j, int k)
    {
        slotToBeWornOn = slot;
        defense = i;
        thermalInsulation = j;
        offense = k;
    }
    @Override
    public void takeOff(lab owner)
    {
        owner.defense -= defense;
        owner.thermalInsulation -= thermalInsulation;
        owner.offense -= offense;
    }
    @Override
    public void putOn(lab owner)
    {
        owner.defense += defense;
        owner.thermalInsulation += thermalInsulation;
        owner.offense += offense;

    }
    @Override
    public boolean UpdateCondition()
    {
        condition -= conditionRate;
        if (condition <= 0)
            return true;
        return false;
    }
}

abstract class Item
{

}
interface Consumable
{
    public abstract boolean Consume(float percentage, lab p);
}
class Food extends Item implements Consumable, Conditional
{
    int condition;
    String name;
    int calories, energy, focus, warmth, fatigue;
    public Food(String name, int calories, int energy, int focus, int warmth, int fatigue)
    {
        this.name = name;
        this.calories = calories;
        this.energy = energy;
        this.focus = focus;
        this.warmth = warmth;
        this.fatigue = fatigue;
        condition = 1;
    }
    //Percentage 0-1f
    public boolean Consume(float percentage, lab p)
    {
        if (condition <=0)
            return true; //and don't eat

        calories -=(int)(calories * percentage) ;
        p.calories += (int)(calories * percentage);

        energy -= (int)(energy * percentage);
        p.energy += (int)(energy * percentage);

        focus -= (int)(focus * percentage);
        p.focus += (int)(focus * percentage);

        warmth -= (int)(warmth * percentage);
        p.warmth += (int)(warmth * percentage);

        fatigue -= (int)(fatigue * percentage);
        p.fatigue += (int)(fatigue * percentage);

        if (calories <= 0)
            return true;
        return false;
    }
    public boolean UpdateCondition()
    {
        condition--;
        //the food has spoiled, but it will stay in your bag until you look at it
        return false;
    }
    public String toString()
    {
        String s = "";
        if (condition < 0)
            s+="spoiled!!! ";
        s+=name;
        return s;
    }
}
abstract class Useable extends Item
{
    public abstract Item Combine(ArrayList<Item> otherItems);
    public abstract void Use(lab owner);
}

class WoodStick extends Useable implements Weapon
{
    int conditionRate = 1;
    int condition = 100;
    int offense = 5;
    int defense = 3;
    public void takeOff(lab owner)
    {
        owner.offense -= offense;
        owner.defense -= defense;

    }
    public void putOn(lab owner)
    {
        owner.offense += offense;
        owner.defense += defense;

    }
    public Item Combine(ArrayList<Item> otherItems)
    {
        System.out.println(this+ " + "+otherItems.get(0));
        if (otherItems.get(0) instanceof Match)
        {
            System.out.println("I have created fire");
            return new Fire();
        }
        return null;
    }
    @Override
    public void Use(lab owner)
    {
        System.out.println("Oww");
        owner.calories-=1;
    }
    @Override
    public boolean UpdateCondition()
    {
        condition -= conditionRate;
        if (condition <= 0)
            return true;
        return false;
    }
}
class FireHardenedStick extends WoodStick
{
    public FireHardenedStick(int o, int d)
    {
        offense = o;
        defense = d;
        conditionRate = 4;
    }
}
class Match extends Useable
{

    @Override
    public Item Combine(ArrayList<Item> otherItems)
    {
        System.out.println(this+ " + "+otherItems.get(0));
        if (otherItems.get(0) instanceof WoodStick)
        {
            System.out.println("I have created fire");
            return new Fire();
        }
        return null;
    }

    @Override
    public void Use(lab owner)
    {
        System.out.println("Ahhh, warmth.... aw.. so short and fleeting");
        owner.warmth += 10;
    }

}
class Fire extends Useable
{
    @Override
    public Item Combine(ArrayList<Item> otherItems)
    {
        if (otherItems.get(0) instanceof WoodStick)
            return new FireHardenedStick(8,3);
        return null;
    }
    @Override
    public void Use(lab owner)
    {
        owner.warmth += 100;
    }
}


////////////////////////////CRAFTING TABLE
 class Craftingtable {
	public void craftingtable(String FileName) throws FileNotFoundException
	{
		ReadFile(FileName);
	}
	
    //READING FILE METHOD
    private static void ReadFile(String fileName) throws FileNotFoundException { 
    	
        Scanner scan=new Scanner(new File(fileName));
        
        
        ArrayList<String> craftingRecipes=new ArrayList<>();
        ArrayList<String> ItemName=new ArrayList<>();

        String line=null;
        
        while(scan.hasNextLine() ) {
            line = scan.nextLine();

            if(!line.matches("[A-Z]\\w*")) { //do same thing for "#cargo list for vehicle file

                if(line.length() >0) {
                    craftingRecipes.add(line);
                }
            }

            if(line.matches("[A-Z]\\w*")) { //do same thing for "#cargo list for vehicle file

                if(line.length() >0) {
                    ItemName.add(line);
                }
            }

        }
        
        
        System.out.println("\nI want to create a...");//recipe menu
        System.out.println("Enter the corresponding number.");
        for(int i=0;i<ItemName.size();i++) {
        	System.out.println(i+". "+ItemName.get(i));
        }
        Scanner s=new Scanner(System.in);
        int choice = s.nextInt();
        switch(choice) {
        	case 0:create("MatchStick");
        	break;
        	case 1: create("HardenedStick");
        	break;
        	case 2: create("Lantern");
        	break;
        	case 3:create("Compass");
        	break;
        	case 4:create("Bow");
        	break;
        	case 5: create("Arrow");
        	break;
        	case 6:create("Shield");
        	break;
        	case 7:create("Sword");
        	break;
         default: System.out.print("Please reselect.");
        		
        	
        
        }
       
    }
   
    public static void create(String recipeName) { //adding items
    	switch(recipeName) {
    	case "MatchStick": 
System.out.println("Match Stick has been created");
break;
    	case "HardenedStick": 
    		System.out.println("Hardened Stick has been created");
    		break;
    		
    	case "Lantern": 
    		System.out.println("Lantern has been created");
    		break;
    	case "Compass": 
    		System.out.println("Compass has been created");
    		break;
    	case "Bow": 
    		System.out.println("Bow has been created");
    		break;
    	case "Arrow": 
    		System.out.println("Arrow has been created");
    		break;
    	case "Shield": 
    		System.out.println("Shield has been created");
    		break;
    	case "Sword": 
    		System.out.println("Sword has been created");
    		break;
    	}
    }
   
 }
 


///////////////////////////////////////////////////DOG 
class Dog extends Inventory{
	 private ArrayList<Item> dogBag = new ArrayList<>();
	    lab owner;

	    ArrayList<WearableSlot> wearables = new ArrayList<>(Arrays.asList(new WearableSlot[]{
	            new WearableSlot(Slot.Torso,null),
	            new WearableSlot(Slot.Paws,null),
	            new WearableSlot(Slot.Shoes,null),
	            new WearableSlot(Slot.Collar,null),
	            new WearableSlot(Slot.Muzzle,null)
	            
	    }));

	    public void add(Item i, int weight)
	    {
	        final int MAX=30;
	        if(weight<=MAX) {
	            dogBag.add(i);
	        }else{
	            System.out.println("Can not add " + i+" to bag. Weight of "+ weight +" exceeds weight limit.");
	        }
	    }
	    public String print()
	    {
	        return dogBag.toString();
	    }
	    public void updateCondition()
	    {
	        for (int i=0;i<dogBag.size();i++)
	        {
	            if (dogBag.get(i) instanceof Conditional)
	                ((Conditional)dogBag.get(i)).UpdateCondition();
	        }
	        for (int i=0;i<wearables.size();i++)
	        {
	            if (wearables.get(i) instanceof Conditional)
	                if (((Conditional)wearables.get(i)).UpdateCondition())
	                {
	                    wearables.remove(i);
	                    i--;
	                }

	        }
	    }
	    public Item remove(int index)
	    {
	        return dogBag.remove(index);
	    }
	    //note that there is no get, remove the item do what you will to it and return it to the bag.

	    public void putOnDog(Slot s, Wearable item)
	    {
	        if (getSlot(s).currentItem != null)
	            getSlot(s).currentItem.takeOff(this.owner);

	        getSlot(s).currentItem = item;
	        item.putOn(this.owner);

	    }
	    public void eat(Consumable c, float percent)
	    {
	        if (c.Consume(percent, owner))
	            dogBag.remove(c);

	    }
	    public WearableSlot getSlot(Slot s)
	    {
	        for (WearableSlot w : wearables)
	            if (w.slot == s)
	                return w;
	        return null;
	    }
	}
	
	
	interface dogWearable extends Conditional{

	    void takeOff(lab dog);
	    void putOn(lab dog);
	}
	
	class dogWearableSlot
	{
	    public dogWearableSlot(Slot slot, Wearable currentItem)
	    {
	        this.slot = slot;
	        this.currentItem = currentItem;
	    }
	    public Wearable putOnItem(Wearable newItem)
	    {
	        Wearable old = currentItem;
	        currentItem = newItem;
	        return old;
	    }
	    Slot slot;
	    Wearable currentItem;
}
	
	class dogClothes extends Item implements Wearable
	{
	    int conditionRate = 1;
	    int condition = 100;
	    Slot slotToBeWornOn;
	    int defense;
	    int thermalInsulation;
	    int offense;
	    public dogClothes(Slot slot, int i, int j, int k)
	    {
	        slotToBeWornOn = slot;
	        defense = i;
	        thermalInsulation = j;
	        offense = k;
	    }
	    @Override
	    public void takeOff(lab owner)
	    {
	        owner.defense -= defense;
	        owner.thermalInsulation -= thermalInsulation;
	        owner.offense -= offense;
	    }
	    @Override
	    public void putOn(lab owner)
	    {
	        owner.defense += defense;
	        owner.thermalInsulation += thermalInsulation;
	        owner.offense += offense;

	    }
	    @Override
	    public boolean UpdateCondition()
	    {
	        condition -= conditionRate;
	        if (condition <= 0)
	            return true;
	        return false;
	    }
	}


	
