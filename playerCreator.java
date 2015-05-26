package playerCreator;

import java.util.Scanner;

public class PlayerCreator {

	public static void main(String[] args) {
		Scanner keyb = new Scanner(System.in); //Initialize scanner for user input
		int race = raceSelection(keyb); //Collect important information from user
		int gender = genderSelection(keyb);
		int numClasses = numClassesSelection(keyb, race);
		int[] classes = classesSelection(keyb, race, numClasses);	
		//int weaponProfs = weaponProfsSelection(keyb, classes);
		int[] abilityDice = abilityDiceSelection(classes);
		
		
		
		
		
		
		
	}
	/**
	 * Used in main to determine the race chosen by the user
	 * @param keyb keyboard scanner used for user input
	 * @return integer representing the desired race
	 */
	public static int raceSelection(Scanner keyb){ //TODO Race selection method
		int race = 0; 
		boolean flag1 = true;
		while(flag1){
			println("Please enter chosen race: \nBarbarian = 1 \nHuman = 2 \nGrey Elf = 3 \nHigh Elf = 4 \nWild Elf = 5 \nDrow Elf = 6 "
					+ "\nValley Elf = 7 \nWood Elf = 8 \nHill Dwarf = 9 \nMountain Dwarf = 10 \nGrey Dwarf = 11 \nDeep Gnome = 12 \nSurface Gnome = 13 \nHalf-Elf"
					+ " = 14 \nHalfling = 15 \nHalf-Orc = 16");
			race = keyb.nextInt(); 
			keyb.nextLine();
			if(race > 0 && race < 17){
				flag1 = false;
			}
		}
		return race;
	}
	/**
	 * Used in main to determine the gender chosen by the user
	 * @param keyb keyboard scanner used for user input
	 * @return integer representing the desired gender
	 */
	public static int genderSelection(Scanner keyb){ //TODO Gender seletion method
		int gender = 0; 
		boolean flag2 = true;
		while(flag2){
			println("Please enter chosen gender: \nMale = 1 \nFemale = 2");
			gender = keyb.nextInt(); 
			keyb.nextLine();
			if(gender > 0 && gender < 3){
				flag2 = false;
			}
		} 
		return gender;
	}
	/**
	 * Used in main to determine the number of classes chosen by the user
	 * @param keyb keyboard scanner used for user input
	 * @param race integer representing the race chosen
	 * @return integer representing the number of desired classes
	 */
	public static int numClassesSelection(Scanner keyb, int race){ //TODO Number of classes method

		int numClasses = 0;
		boolean flag3 = true;
		while(flag3){
			print("Please enter chosen number of classes:");
			numClasses = keyb.nextInt();
			keyb.nextLine();
			
			if(numClasses < 1 || numClasses >3) 
				error("Incorrect number of classes.");
			else if(race==2 && numClasses>1)
				error("Players that are Human can only be single class.");
			else if(race==1 && numClasses>1)
				error("Players that are Barbarian can only be single class.");
			else if(race>2 && numClasses <2){
				error("Warning! Single classed characters should be human to avoid level cap limitations. Do you wish to proceed with non-human choice?(y/n)");
					if(keyb.next().equalsIgnoreCase("y")){
						flag3 = false;
					}
					else if(keyb.next().equalsIgnoreCase("n")){
						print("n received. Exiting.. ");
						System.exit(0);
					}
					else{
						error("Invalid input. Terminating..");
						System.exit(1);
					}
			}
			else
				flag3 = false;
		}
		return numClasses;
	}
	/**
	 * Used in main to determine which classes are chosen by the user
	 * @param keyb keyboard scanner used for user input
	 * @param race integer representing the race chosen
	 * @param numClasses integer representing the number of desired classes
	 * @return integer array representing the classes the user has chosen
	 */
	public static int[] classesSelection(Scanner keyb, int race, int numClasses){ //TODO Class selection method
		int[] classes = new int[numClasses];
		boolean flag4 = true;
		while(flag4){
			for(int i = 0; i<classes.length; i++){
				println("Please enter chosen class: \nCavalier = 1 \nPaladin = 2 \nCleric = 3 \nDruid = 4 \nFighter = 5 \nBarbarian = 6 \nRanger = 7 \nMagic-User = 8"
						+ " \nIllusionist = 9 \nThief = 10 \nThief-Acrobat = 11 \nAssassin = 12 \nMonk = 13 \nBard = 14");
				classes[i] = keyb.nextInt();
				keyb.nextLine();
			}
			if(classes[0]<1 || classes[classes.length/2]<1 || classes[classes.length-1]<1 || classes[0]>14 || classes[classes.length/2]>14 || 
					classes[classes.length-1]>14)
				error("Incorrect options chosen. Please choose between the designated classes.");
			else if(classes[0] !=6 && race == 1)
				error("Player characters that are Barbarians must choose the Barbarian class.");
			else if(isRepeatedClasses(classes))
				error("Player characters cannot choose the same class more than once.");
			else if(classes[0] == 2 && race==3 || classes[classes.length/2] == 2 && race==3 || classes[classes.length-1] == 2 && race==3)
				error("Player characters that are Grey Elves cannot be the Paladin class");
			else if(classes[0] == 6 && race==3 || classes[classes.length/2] == 6 && race==3 || classes[classes.length-1] == 6 && race==3)
				error("Player characters that are Grey Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==3 || classes[classes.length/2] == 9 && race==3 || classes[classes.length-1] == 9 && race==3)
				error("Player characters that are Grey Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==3 || classes[classes.length/2] == 13 && race==3 || classes[classes.length-1] == 13 && race==3)
				error("Player characters that are Grey Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==3 || classes[classes.length/2] == 14 && race==3 || classes[classes.length-1] == 14 && race==3)
				error("Player characters that are Grey Elves cannot be the Bard class");
			else if(classes[0] == 2 && race==4 || classes[classes.length/2] == 2 && race==4 || classes[classes.length-1] == 2 && race==4)
				error("Player characters that are High Elves cannot be the Paladin class");
			else if(classes[0] == 6 && race==4 || classes[classes.length/2] == 6 && race==4 || classes[classes.length-1] == 6 && race==4)
				error("Player characters that are High Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==4 || classes[classes.length/2] == 9 && race==4 || classes[classes.length-1] == 9 && race==4)
				error("Player characters that are High Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==4 || classes[classes.length/2] == 13 && race==4 || classes[classes.length-1] == 13 && race==4)
				error("Player characters that are High Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==4 || classes[classes.length/2] == 14 && race==4 || classes[classes.length-1] == 14 && race==4)
				error("Player characters that are High Elves cannot be the Bard class");
			else if(classes[0] == 1 && race==5 || classes[classes.length/2] == 1 && race==5 || classes[classes.length-1] == 1 && race==5)
				error("Player characters that are Wild Elves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==5 || classes[classes.length/2] == 2 && race==5 || classes[classes.length-1] == 2 && race==5)
				error("Player characters that are Wild Elves cannot be the Paladin class");
			else if(classes[0] == 3 && race==5 || classes[classes.length/2] == 3 && race==5 || classes[classes.length-1] == 3 && race==5)
				error("Player characters that are Wild Elves cannot be the Cleric class");
			else if(classes[0] == 6 && race==5 || classes[classes.length/2] == 6 && race==5 || classes[classes.length-1] == 6 && race==5)
				error("Player characters that are Wild Elves cannot be the Barbarian class");
			else if(classes[0] == 7 && race==5 || classes[classes.length/2] == 7 && race==5 || classes[classes.length-1] == 7 && race==5)
				error("Player characters that are Wild Elves cannot be the Ranger class");
			else if(classes[0] == 8 && race==5 || classes[classes.length/2] == 8 && race==5 || classes[classes.length-1] == 8 && race==5)
				error("Player characters that are Wild Elves cannot be the Magic-User class");
			else if(classes[0] == 9 && race==5 || classes[classes.length/2] == 9 && race==5 || classes[classes.length-1] == 9 && race==5)
				error("Player characters that are Wild Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==5 || classes[classes.length/2] == 13 && race==5 || classes[classes.length-1] == 13 && race==5)
				error("Player characters that are Wild Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==5 || classes[classes.length/2] == 14 && race==5 || classes[classes.length-1] == 14 && race==5)
				error("Player characters that are Wild Elves cannot be the Bard class");
			else if(classes[0] == 2 && race==6 || classes[classes.length/2] == 2 && race==6 || classes[classes.length-1] == 2 && race==6)
				error("Player characters that are Drow Elves cannot be the Paladin class");
			else if(classes[0] == 4 && race==6 || classes[classes.length/2] == 4 && race==6 || classes[classes.length-1] == 4 && race==6)
				error("Player characters that are Drow Elves cannot be the Druid class");
			else if(classes[0] == 6 && race==6 || classes[classes.length/2] == 6 && race==6 || classes[classes.length-1] == 6 && race==6)
				error("Player characters that are Drow Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==6 || classes[classes.length/2] == 9 && race==6 || classes[classes.length-1] == 9 && race==6)
				error("Player characters that are Drow Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==6 || classes[classes.length/2] == 13 && race==6 || classes[classes.length-1] == 13 && race==6)
				error("Player characters that are Drow Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==6 || classes[classes.length/2] == 14 && race==6 || classes[classes.length-1] == 14 && race==6)
				error("Player characters that are Drow Elves cannot be the Bard class");
			else if(classes[0] == 1 && race==7 || classes[classes.length/2] == 1 && race==7 || classes[classes.length-1] == 1 && race==7)
				error("Player characters that are Valley Elves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==7 || classes[classes.length/2] == 2 && race==7 || classes[classes.length-1] == 2 && race==7)
				error("Player characters that are Valley Elves cannot be the Paladin class");
			else if(classes[0] == 6 && race==7 || classes[classes.length/2] == 6 && race==7 || classes[classes.length-1] == 6 && race==7)
				error("Player characters that are Valley Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==7 || classes[classes.length/2] == 9 && race==7 || classes[classes.length-1] == 9 && race==7)
				error("Player characters that are Valley Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==7 || classes[classes.length/2] == 13 && race==7 || classes[classes.length-1] == 13 && race==7)
				error("Player characters that are Valley Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==7 || classes[classes.length/2] == 14 && race==7 || classes[classes.length-1] == 14 && race==7)
				error("Player characters that are Valley Elves cannot be the Bard class");
			else if(classes[0] == 1 && race==8 || classes[classes.length/2] == 1 && race==8 || classes[classes.length-1] == 1 && race==8)
				error("Player characters that are Wood Elves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==8 || classes[classes.length/2] == 2 && race==8 || classes[classes.length-1] == 2 && race==8)
				error("Player characters that are Wood Elves cannot be the Paladin class");
			else if(classes[0] == 6 && race==8 || classes[classes.length/2] == 6 && race==8 || classes[classes.length-1] == 6 && race==8)
				error("Player characters that are Wood Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==8 || classes[classes.length/2] == 9 && race==8 || classes[classes.length-1] == 9 && race==8)
				error("Player characters that are Wood Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==8 || classes[classes.length/2] == 13 && race==8 || classes[classes.length-1] == 13 && race==8)
				error("Player characters that are Wood Elves cannot be the Monk class");
			else if(classes[0] == 14 && race==8 || classes[classes.length/2] == 14 && race==8 || classes[classes.length-1] == 14 && race==8)
				error("Player characters that are Wood Elves cannot be the Bard class");
			else if(classes[0] == 1 && race==9 || classes[classes.length/2] == 1 && race==9 || classes[classes.length-1] == 1 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==9 || classes[classes.length/2] == 2 && race==9 || classes[classes.length-1] == 2 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Paladin class");
			else if(classes[0] == 4 && race==9 || classes[classes.length/2] == 4 && race==9 || classes[classes.length-1] == 4 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Druid class");
			else if(classes[0] == 6 && race==9 || classes[classes.length/2] == 6 && race==9 || classes[classes.length-1] == 6 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Barbarian class");
			else if(classes[0] == 7 && race==9 || classes[classes.length/2] == 7 && race==9 || classes[classes.length-1] == 7 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Ranger class");
			else if(classes[0] == 8 && race==9 || classes[classes.length/2] == 8 && race==9 || classes[classes.length-1] == 8 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Magic-User class");
			else if(classes[0] == 9 && race==9 || classes[classes.length/2] == 9 && race==9 || classes[classes.length-1] == 9 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==9 || classes[classes.length/2] == 13 && race==9 || classes[classes.length-1] == 13 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Monk class");
			else if(classes[0] == 14 && race==9 || classes[classes.length/2] == 14 && race==9 || classes[classes.length-1] == 14 && race==9)
				error("Player characters that are Hill Dwarves cannot be the Bard class");
			else if(classes[0] == 1 && race==10 || classes[classes.length/2] == 1 && race==10 || classes[classes.length-1] == 1 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==10 || classes[classes.length/2] == 2 && race==10 || classes[classes.length-1] == 2 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Paladin class");
			else if(classes[0] == 4 && race==10 || classes[classes.length/2] == 4 && race==10 || classes[classes.length-1] == 4 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Druid class");
			else if(classes[0] == 6 && race==10 || classes[classes.length/2] == 6 && race==10 || classes[classes.length-1] == 6 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Barbarian class");
			else if(classes[0] == 7 && race==10 || classes[classes.length/2] == 7 && race==10 || classes[classes.length-1] == 7 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Ranger class");
			else if(classes[0] == 8 && race==10 || classes[classes.length/2] == 8 && race==10 || classes[classes.length-1] == 8 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Magic-User class");
			else if(classes[0] == 9 && race==10 || classes[classes.length/2] == 9 && race==10 || classes[classes.length-1] == 9 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==10 || classes[classes.length/2] == 13 && race==10 || classes[classes.length-1] == 13 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Monk class");
			else if(classes[0] == 14 && race==10 || classes[classes.length/2] == 14 && race==10 || classes[classes.length-1] == 14 && race==10)
				error("Player characters that are Mountain Dwarves cannot be the Bard class");
			else if(classes[0] == 1 && race==11 || classes[classes.length/2] == 1 && race==11 || classes[classes.length-1] == 1 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Cavalier class");
			else if(classes[0] == 2 && race==11 || classes[classes.length/2] == 2 && race==11 || classes[classes.length-1] == 2 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Paladin class");
			else if(classes[0] == 4 && race==11 || classes[classes.length/2] == 4 && race==11 || classes[classes.length-1] == 4 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Druid class");
			else if(classes[0] == 6 && race==11 || classes[classes.length/2] == 6 && race==11 || classes[classes.length-1] == 6 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Barbarian class");
			else if(classes[0] == 7 && race==11 || classes[classes.length/2] == 7 && race==11 || classes[classes.length-1] == 7 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Ranger class");
			else if(classes[0] == 8 && race==11 || classes[classes.length/2] == 8 && race==11 || classes[classes.length-1] == 8 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Magic-User class");
			else if(classes[0] == 9 && race==11 || classes[classes.length/2] == 9 && race==11 || classes[classes.length-1] == 9 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==11 || classes[classes.length/2] == 13 && race==11 || classes[classes.length-1] == 13 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Monk class");
			else if(classes[0] == 14 && race==11 || classes[classes.length/2] == 14 && race==11 || classes[classes.length-1] == 14 && race==11)
				error("Player characters that are Grey Dwarves cannot be the Bard class");
			else if(classes[0] == 1 && race==12 || classes[classes.length/2] == 1 && race==12 || classes[classes.length-1] == 1 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Cavalier class");
			else if(classes[0] == 2 && race==12 || classes[classes.length/2] == 2 && race==12 || classes[classes.length-1] == 2 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Paladin class");
			else if(classes[0] == 4 && race==12 || classes[classes.length/2] == 4 && race==12 || classes[classes.length-1] == 4 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Druid class");
			else if(classes[0] == 6 && race==12 || classes[classes.length/2] == 6 && race==12 || classes[classes.length-1] == 6 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Barbarian class");
			else if(classes[0] == 7 && race==12 || classes[classes.length/2] == 7 && race==12 || classes[classes.length-1] == 7 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Ranger class");
			else if(classes[0] == 8 && race==12 || classes[classes.length/2] == 8 && race==12 || classes[classes.length-1] == 8 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Magic-User class");
			else if(classes[0] == 13 && race==12 || classes[classes.length/2] == 13 && race==12 || classes[classes.length-1] == 13 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Monk class");
			else if(classes[0] == 14 && race==12 || classes[classes.length/2] == 14 && race==12 || classes[classes.length-1] == 14 && race==12)
				error("Player characters that are Deep Gnomes cannot be the Bard class");
			else if(classes[0] == 1 && race==13 || classes[classes.length/2] == 1 && race==13 || classes[classes.length-1] == 1 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Cavalier class");
			else if(classes[0] == 2 && race==13 || classes[classes.length/2] == 2 && race==13 || classes[classes.length-1] == 2 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Paladin class");
			else if(classes[0] == 4 && race==13 || classes[classes.length/2] == 4 && race==13 || classes[classes.length-1] == 4 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Druid class");
			else if(classes[0] == 6 && race==13 || classes[classes.length/2] == 6 && race==13 || classes[classes.length-1] == 6 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Barbarian class");
			else if(classes[0] == 7 && race==13 || classes[classes.length/2] == 7 && race==13 || classes[classes.length-1] == 7 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Ranger class");
			else if(classes[0] == 8 && race==13 || classes[classes.length/2] == 8 && race==13 || classes[classes.length-1] == 8 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Magic-User class");
			else if(classes[0] == 13 && race==13 || classes[classes.length/2] == 13 && race==13 || classes[classes.length-1] == 13 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Monk class");
			else if(classes[0] == 14 && race==13 || classes[classes.length/2] == 14 && race==13 || classes[classes.length-1] == 14 && race==13)
				error("Player characters that are Surface Gnomes cannot be the Bard class");
			else if(classes[0] == 6 && race==14 || classes[classes.length/2] == 6 && race==14 || classes[classes.length-1] == 6 && race==14)
				error("Player characters that are Half-Elves cannot be the Barbarian class");
			else if(classes[0] == 9 && race==14 || classes[classes.length/2] == 9 && race==14 || classes[classes.length-1] == 9 && race==14)
				error("Player characters that are Half-Elves cannot be the Illusionist class");
			else if(classes[0] == 13 && race==14 || classes[classes.length/2] == 13 && race==14 || classes[classes.length-1] == 13 && race==14)
				error("Player characters that are Half-Elves cannot be the Monk class");
			else if(classes[0] == 1 && race==15 || classes[classes.length/2] == 1 && race==15 || classes[classes.length-1] == 1 && race==15)
				error("Player characters that are Halflings cannot be the Cavalier class");
			else if(classes[0] == 2 && race==15 || classes[classes.length/2] == 2 && race==15 || classes[classes.length-1] == 2 && race==15)
				error("Player characters that are Halflings cannot be the Paladin class");
			else if(classes[0] == 6 && race==15 || classes[classes.length/2] == 6 && race==15 || classes[classes.length-1] == 6 && race==15)
				error("Player characters that are Halflings cannot be the Barbarian class");
			else if(classes[0] == 7 && race==15 || classes[classes.length/2] == 7 && race==15 || classes[classes.length-1] == 7 && race==15)
				error("Player characters that are Halflings cannot be the Ranger class");
			else if(classes[0] == 8 && race==15 || classes[classes.length/2] == 8 && race==15 || classes[classes.length-1] == 8 && race==15)
				error("Player characters that are Halflings cannot be the Magic-User class");
			else if(classes[0] == 9 && race==15 || classes[classes.length/2] == 9 && race==15 || classes[classes.length-1] == 9 && race==15)
				error("Player characters that are Halflings cannot be the Illusionist class");
			else if(classes[0] == 12 && race==15 || classes[classes.length/2] == 12 && race==15 || classes[classes.length-1] == 12 && race==15)
				error("Player characters that are Halflings cannot be the Assassin class");
			else if(classes[0] == 13 && race==15 || classes[classes.length/2] == 13 && race==15 || classes[classes.length-1] == 13 && race==15)
				error("Player characters that are Halflings cannot be the Monk class");
			else if(classes[0] == 14 && race==15 || classes[classes.length/2] == 14 && race==15 || classes[classes.length-1] == 14 && race==15)
				error("Player characters that are Halflings cannot be the Bard class");
			else if(classes[0] == 1 && race==16 || classes[classes.length/2] == 1 && race==16 || classes[classes.length-1] == 1 && race==16)
				error("Player characters that are Half-Orcs cannot be the Cavalier class");
			else if(classes[0] == 2 && race==16 || classes[classes.length/2] == 2 && race==16 || classes[classes.length-1] == 2 && race==16)
				error("Player characters that are Half-Orcs cannot be the Paladin class");
			else if(classes[0] == 4 && race==16 || classes[classes.length/2] == 4 && race==16 || classes[classes.length-1] == 4 && race==16)
				error("Player characters that are Half-Orcs cannot be the Druid class");
			else if(classes[0] == 6 && race==16 || classes[classes.length/2] == 6 && race==16 || classes[classes.length-1] == 6 && race==16)
				error("Player characters that are Half-Orcs cannot be the Barbarian class");
			else if(classes[0] == 7 && race==16 || classes[classes.length/2] == 7 && race==16 || classes[classes.length-1] == 7 && race==16)
				error("Player characters that are Half-Orcs cannot be the Ranger class");
			else if(classes[0] == 8 && race==16 || classes[classes.length/2] == 8 && race==16 || classes[classes.length-1] == 8 && race==16)
				error("Player characters that are Half-Orcs cannot be the Magic-User class");
			else if(classes[0] == 9 && race==16 || classes[classes.length/2] == 9 && race==16 || classes[classes.length-1] == 9 && race==16)
				error("Player characters that are Half-Orcs cannot be the Illusionist class");
			else if(classes[0] == 13 && race==16 || classes[classes.length/2] == 13 && race==16 || classes[classes.length-1] == 13 && race==16)
				error("Player characters that are Half-Orcs cannot be the Monk class");
			else if(classes[0] == 14 && race==16 || classes[classes.length/2] == 14 && race==16 || classes[classes.length-1] == 14 && race==16)
				error("Player characters that are Half-Orcs cannot be the Bard class");
			else if(classes[0] == 1 && classes[classes.length/2] == 4 || classes[0] == 1 && classes[classes.length-1] == 4 || 
						classes[classes.length/2] == 1 && classes[classes.length-1] == 4 || classes[0] == 4 && classes[classes.length/2] == 1 || 
						classes[0] == 4 && classes[classes.length-1] == 1 || classes[classes.length/2] == 4 && classes[classes.length-1] == 1)
				error("Classes Cavalier and Thief have a conflict of alignment.");
			else if(classes[0] == 1 && classes[classes.length/2] == 10 || classes[0] == 1 && classes[classes.length-1] == 10 || 
					classes[classes.length/2] == 1 && classes[classes.length-1] == 10 || classes[0] == 10 && classes[classes.length/2] == 1 || 
					classes[0] == 10 && classes[classes.length-1] == 1 || classes[classes.length/2] == 10 && classes[classes.length-1] == 1)
			error("Classes Cavalier and Druid have a conflict of alignment.");
			else if(classes[0] == 1 && classes[classes.length/2] == 11 || classes[0] == 1 && classes[classes.length-1] == 11 || 
					classes[classes.length/2] == 1 && classes[classes.length-1] == 11 || classes[0] == 11 && classes[classes.length/2] == 1 || 
					classes[0] == 11 && classes[classes.length-1] == 1 || classes[classes.length/2] == 11 && classes[classes.length-1] == 1)
			error("Classes Cavalier and Thief-Acrobat have a conflict of alignment.");
			else if(classes[0] == 1 && classes[classes.length/2] == 12 || classes[0] == 1 && classes[classes.length-1] == 12 || 
					classes[classes.length/2] == 1 && classes[classes.length-1] == 12 || classes[0] == 12 && classes[classes.length/2] == 1 || 
					classes[0] == 12 && classes[classes.length-1] == 1 || classes[classes.length/2] == 12 && classes[classes.length-1] == 1)
			error("Classes Cavalier and Assassin have a conflict of alignment.");
			else if(classes[0] == 2 && classes[classes.length/2] == 4 || classes[0] == 2 && classes[classes.length-1] == 4 || 
					classes[classes.length/2] == 2 && classes[classes.length-1] == 4 || classes[0] == 4 && classes[classes.length/2] == 2 || 
					classes[0] == 4 && classes[classes.length-1] == 2 || classes[classes.length/2] == 4 && classes[classes.length-1] == 2)
			error("Classes Paladin and Druid have a conflict of alignment.");
			else if(classes[0] == 2 && classes[classes.length/2] == 10 || classes[0] == 2 && classes[classes.length-1] == 10 || 
					classes[classes.length/2] == 2 && classes[classes.length-1] == 10 || classes[0] == 10 && classes[classes.length/2] == 2 || 
					classes[0] == 10 && classes[classes.length-1] == 2 || classes[classes.length/2] == 10 && classes[classes.length-1] == 2)
			error("Classes Paladin and Thief have a conflict of alignment.");
			else if(classes[0] == 2 && classes[classes.length/2] == 11 || classes[0] == 2 && classes[classes.length-1] == 11 || 
					classes[classes.length/2] == 2 && classes[classes.length-1] == 11 || classes[0] == 11 && classes[classes.length/2] == 2 || 
					classes[0] == 11 && classes[classes.length-1] == 2 || classes[classes.length/2] == 11 && classes[classes.length-1] == 2)
			error("Classes Paladin and Thief-Acrobat have a conflict of alignment.");
			else if(classes[0] == 2 && classes[classes.length/2] == 12 || classes[0] == 2 && classes[classes.length-1] == 12 || 
					classes[classes.length/2] == 2 && classes[classes.length-1] == 12 || classes[0] == 12 && classes[classes.length/2] == 2 || 
					classes[0] == 12 && classes[classes.length-1] == 2 || classes[classes.length/2] == 12 && classes[classes.length-1] == 2)
			error("Classes Paladin and Assassin have a conflict of alignment.");
			else if(classes[0] == 2 && classes[classes.length/2] == 14 || classes[0] == 2 && classes[classes.length-1] == 14 || 
					classes[classes.length/2] == 2 && classes[classes.length-1] == 14 || classes[0] == 14 && classes[classes.length/2] == 2 || 
					classes[0] == 14 && classes[classes.length-1] == 2 || classes[classes.length/2] == 14 && classes[classes.length-1] == 2)
			error("Classes Paladin and Bard have a conflict of alignment.");
			else if(classes[0] == 4 && classes[classes.length/2] == 7 || classes[0] == 4 && classes[classes.length-1] == 7 || 
					classes[classes.length/2] == 4 && classes[classes.length-1] == 7 || classes[0] == 7 && classes[classes.length/2] == 4 || 
					classes[0] == 7 && classes[classes.length-1] == 4 || classes[classes.length/2] == 7 && classes[classes.length-1] == 4)
			error("Classes Druid and Ranger have a conflict of alignment.");
			else if(classes[0] == 4 && classes[classes.length/2] == 12 || classes[0] == 4 && classes[classes.length-1] == 12 || 
					classes[classes.length/2] == 4 && classes[classes.length-1] == 12 || classes[0] == 12 && classes[classes.length/2] == 4 || 
					classes[0] == 12 && classes[classes.length-1] == 4 || classes[classes.length/2] == 12 && classes[classes.length-1] == 4)
			error("Classes Druid and Assassin have a conflict of alignment.");
			else if(classes[0] == 4 && classes[classes.length/2] == 13 || classes[0] == 4 && classes[classes.length-1] == 13 || 
					classes[classes.length/2] == 4 && classes[classes.length-1] == 13 || classes[0] == 13 && classes[classes.length/2] == 4 || 
					classes[0] == 13 && classes[classes.length-1] == 4 || classes[classes.length/2] == 13 && classes[classes.length-1] == 4)
			error("Classes Druid and Monk have a conflict of alignment.");
			else if(classes[0] == 7 && classes[classes.length/2] == 10 || classes[0] == 7 && classes[classes.length-1] == 10 || 
					classes[classes.length/2] == 7 && classes[classes.length-1] == 10 || classes[0] == 10 && classes[classes.length/2] == 7 || 
					classes[0] == 10 && classes[classes.length-1] == 7 || classes[classes.length/2] == 10 && classes[classes.length-1] == 7)
			error("Classes Ranger and Thief have a conflict of alignment.");
			else if(classes[0] == 7 && classes[classes.length/2] == 11 || classes[0] == 7 && classes[classes.length-1] == 11 || 
					classes[classes.length/2] == 7 && classes[classes.length-1] == 11 || classes[0] == 11 && classes[classes.length/2] == 7 || 
					classes[0] == 11 && classes[classes.length-1] == 7 || classes[classes.length/2] == 11 && classes[classes.length-1] == 7)
			error("Classes Ranger and Thief-Acrobat have a conflict of alignment.");
			else if(classes[0] == 7 && classes[classes.length/2] == 12 || classes[0] == 7 && classes[classes.length-1] == 12 || 
					classes[classes.length/2] == 7 && classes[classes.length-1] == 12 || classes[0] == 12 && classes[classes.length/2] == 7 || 
					classes[0] == 12 && classes[classes.length-1] == 7 || classes[classes.length/2] == 12 && classes[classes.length-1] == 7)
			error("Classes Ranger and Assassin have a conflict of alignment.");
			else
				flag4 = false;
		}
		return classes;
	}
	/**
	 * Used in main to determine which alignment is chosen by the user
	 * @param keyb keyboard scanner for user input
	 * @param classes the integer array of classes chosen
	 * @return integer representing which alignment the user has chosen
	 */
	public static int alignmentSelection(Scanner keyb, int[] classes){ //TODO Alignment selection method
		int align = 0;
		boolean flag5 = true;
		while(flag5){
			println("Please enter chosen alignment: \nLawful Good = 1 \nLawful Neutral = 2 \nLawful Evil = 3 \nNeutral Good = 4 \nTrue Neutral = 5 \nNeutral Evil = 6"
				+ "\nChaotic Good = 7 \nChaotic Neutral = 8 \nChaotic Evil = 9");
			align = keyb.nextInt();
			keyb.nextLine();
			if(align < 1 || align > 9)
				error("Incorrect options chosen. Please choose between the designated alignments.");
			else if(classes[0] == 1 && align == 2 || classes[classes.length/2] == 1 && align == 2 || classes[classes.length-1] == 1 && align == 2)
				error("Player characters that are Cavaliers cannot be Lawful Neutral.");
			else if(classes[0] == 1 && align == 3 || classes[classes.length/2] == 1 && align == 3 || classes[classes.length-1] == 1 && align == 3)
				error("Player characters that are Cavaliers cannot be Lawful Evil.");
			else if(classes[0] == 1 && align == 5 || classes[classes.length/2] == 1 && align == 5 || classes[classes.length-1] == 1 && align == 5)
				error("Player characters that are Cavaliers cannot be True Neutral.");
			else if(classes[0] == 1 && align == 6 || classes[classes.length/2] == 1 && align == 6 || classes[classes.length-1] == 1 && align == 6)
				error("Player characters that are Cavaliers cannot be Neutral Evil.");
			else if(classes[0] == 1 && align == 8 || classes[classes.length/2] == 1 && align == 8 || classes[classes.length-1] == 1 && align == 8)
				error("Player characters that are Cavaliers cannot be Chaotic Neutral.");
			else if(classes[0] == 1 && align == 9 || classes[classes.length/2] == 1 && align == 9 || classes[classes.length-1] == 1 && align == 9)
				error("Player characters that are Cavaliers cannot be Chaotic Evil.");
			else if(classes[0] == 2 && align == 2 || classes[classes.length/2] == 2 && align == 2 || classes[classes.length-1] == 2 && align == 2)
				error("Player characters that are Paladins cannot be Lawful Neutral.");
			else if(classes[0] == 2 && align == 3 || classes[classes.length/2] == 2 && align == 3 || classes[classes.length-1] == 2 && align == 3)
				error("Player characters that are Paladins cannot be Lawful Evil.");
			else if(classes[0] == 2 && align == 4 || classes[classes.length/2] == 2 && align == 4 || classes[classes.length-1] == 2 && align == 4)
				error("Player characters that are Paladins cannot be Neutral Good.");
			else if(classes[0] == 2 && align == 5 || classes[classes.length/2] == 2 && align == 5 || classes[classes.length-1] == 2 && align == 5)
				error("Player characters that are Paladins cannot be True Neutral.");
			else if(classes[0] == 2 && align == 6 || classes[classes.length/2] == 2 && align == 6 || classes[classes.length-1] == 2 && align == 6)
				error("Player characters that are Paladins cannot be Neutral Evil.");
			else if(classes[0] == 2 && align == 7 || classes[classes.length/2] == 2 && align == 7 || classes[classes.length-1] == 2 && align == 7)
				error("Player characters that are Paladins cannot be Chaotic Good.");
			else if(classes[0] == 2 && align == 8 || classes[classes.length/2] == 2 && align == 8 || classes[classes.length-1] == 2 && align == 8)
				error("Player characters that are Paladins cannot be Chaotic Neutral.");
			else if(classes[0] == 2 && align == 9 || classes[classes.length/2] == 2 && align == 9 || classes[classes.length-1] == 2 && align == 9)
				error("Player characters that are Paladins cannot be Chaotic Evil.");
			else if(classes[0] == 4 && align == 1 || classes[classes.length/2] == 4 && align == 1 || classes[classes.length-1] == 4 && align == 1)
				error("Player characters that are Druids cannot be Lawful Good.");
			else if(classes[0] == 4 && align == 2 || classes[classes.length/2] == 4 && align == 2 || classes[classes.length-1] == 4 && align == 2)
				error("Player characters that are Druids cannot be Lawful Neutral.");
			else if(classes[0] == 4 && align == 3 || classes[classes.length/2] == 4 && align == 3 || classes[classes.length-1] == 4 && align == 3)
				error("Player characters that are Druids cannot be Lawful Evil.");
			else if(classes[0] == 4 && align == 4 || classes[classes.length/2] == 4 && align == 4 || classes[classes.length-1] == 4 && align == 4)
				error("Player characters that are Druids cannot be Neutral Good.");
			else if(classes[0] == 4 && align == 6 || classes[classes.length/2] == 4 && align == 6 || classes[classes.length-1] == 4 && align == 6)
				error("Player characters that are Druids cannot be Neutral Evil.");
			else if(classes[0] == 4 && align == 7 || classes[classes.length/2] == 4 && align == 7 || classes[classes.length-1] == 4 && align == 7)
				error("Player characters that are Druids cannot be Chaotic Good.");
			else if(classes[0] == 4 && align == 8 || classes[classes.length/2] == 4 && align == 8 || classes[classes.length-1] == 4 && align == 8)
				error("Player characters that are Druids cannot be Chaotic Neutral.");
			else if(classes[0] == 4 && align == 9 || classes[classes.length/2] == 4 && align == 9 || classes[classes.length-1] == 4 && align == 9)
				error("Player characters that are Druids cannot be Chaotic Evil.");
			else if(classes[0] == 6 && align == 1 || classes[classes.length/2] == 6 && align == 1 || classes[classes.length-1] == 6 && align == 1)
				error("Player characters that are Barbarians cannot be Lawful Good.");
			else if(classes[0] == 6 && align == 2 || classes[classes.length/2] == 6 && align == 2 || classes[classes.length-1] == 6 && align == 2)
				error("Player characters that are Barbarians cannot be Lawful Neutral.");
			else if(classes[0] == 6 && align == 3 || classes[classes.length/2] == 6 && align == 3 || classes[classes.length-1] == 6 && align == 3)
				error("Player characters that are Barbarians cannot be Lawful Evil.");
			else if(classes[0] == 7 && align == 2 || classes[classes.length/2] == 7 && align == 2 || classes[classes.length-1] == 7 && align == 2)
				error("Player characters that are Rangers cannot be Lawful Neutral.");
			else if(classes[0] == 7 && align == 3 || classes[classes.length/2] == 7 && align == 3 || classes[classes.length-1] == 7 && align == 3)
				error("Player characters that are Rangers cannot be Lawful Evil.");
			else if(classes[0] == 7 && align == 5 || classes[classes.length/2] == 7 && align == 5 || classes[classes.length-1] == 7 && align == 5)
				error("Player characters that are Rangers cannot be True Neutral.");
			else if(classes[0] == 7 && align == 6 || classes[classes.length/2] == 7 && align == 6 || classes[classes.length-1] == 7 && align == 6)
				error("Player characters that are Rangers cannot be Neutral Evil.");
			else if(classes[0] == 7 && align == 8 || classes[classes.length/2] == 7 && align == 8 || classes[classes.length-1] == 7 && align == 8)
				error("Player characters that are Rangers cannot be Chaotic Neutral.");
			else if(classes[0] == 7 && align == 9 || classes[classes.length/2] == 7 && align == 9 || classes[classes.length-1] == 7 && align == 9)
				error("Player characters that are Rangers cannot be Chaotic Evil.");
			else if(classes[0] == 10 && align == 1 || classes[classes.length/2] == 10 && align == 1 || classes[classes.length-1] == 10 && align == 1)
				error("Player characters that are Thieves cannot be Lawful Good.");
			else if(classes[0] == 10 && align == 4 || classes[classes.length/2] == 10 && align == 4 || classes[classes.length-1] == 10 && align == 4)
				error("Player characters that are Thieves cannot be Neutral Good.");
			else if(classes[0] == 10 && align == 7 || classes[classes.length/2] == 10 && align == 7 || classes[classes.length-1] == 10 && align == 7)
				error("Player characters that are Thieves cannot be Chaotic Good.");
			else if(classes[0] == 11 && align == 1 || classes[classes.length/2] == 11 && align == 1 || classes[classes.length-1] == 11 && align == 1)
				error("Player characters that are Thief-Acrobats cannot be Lawful Good.");
			else if(classes[0] == 11 && align == 4 || classes[classes.length/2] == 11 && align == 4 || classes[classes.length-1] == 11 && align == 4)
				error("Player characters that are Thief-Acrobats cannot be Neutral Good.");
			else if(classes[0] == 11 && align == 7 || classes[classes.length/2] == 11 && align == 7 || classes[classes.length-1] == 11 && align == 7)
				error("Player characters that are Thief-Acrobats cannot be Chaotic Good.");
			else if(classes[0] == 12 && align == 1 || classes[classes.length/2] == 12 && align == 1 || classes[classes.length-1] == 12 && align == 1)
				error("Player characters that are Assassins cannot be Lawful Good.");
			else if(classes[0] == 12 && align == 2 || classes[classes.length/2] == 12 && align == 2 || classes[classes.length-1] == 12 && align == 2)
				error("Player characters that are Assassins cannot be Lawful Neutral.");
			else if(classes[0] == 12 && align == 4 || classes[classes.length/2] == 12 && align == 4 || classes[classes.length-1] == 12 && align == 4)
				error("Player characters that are Assassins cannot be Neutral Good.");
			else if(classes[0] == 12 && align == 5 || classes[classes.length/2] == 12 && align == 5 || classes[classes.length-1] == 12 && align == 5)
				error("Player characters that are Assassins cannot be Tre Neutral.");
			else if(classes[0] == 12 && align == 7 || classes[classes.length/2] == 12 && align == 7 || classes[classes.length-1] == 12 && align == 7)
				error("Player characters that are Assassins cannot be Chaotic Good.");
			else if(classes[0] == 12 && align == 8 || classes[classes.length/2] == 12 && align == 8 || classes[classes.length-1] == 12 && align == 8)
				error("Player characters that are Assassins cannot be Chaotic Neutral.");
			else if(classes[0] == 13 && align == 4 || classes[classes.length/2] == 13 && align == 4 || classes[classes.length-1] == 13 && align == 4)
				error("Player characters that are Monks cannot be Neutral Good.");
			else if(classes[0] == 13 && align == 5 || classes[classes.length/2] == 13 && align == 5 || classes[classes.length-1] == 13 && align == 5)
				error("Player characters that are Monks cannot be True Neutral.");
			else if(classes[0] == 13 && align == 6 || classes[classes.length/2] == 13 && align == 6 || classes[classes.length-1] == 13 && align == 6)
				error("Player characters that are Monks cannot be Neutral Evil.");
			else if(classes[0] == 13 && align == 7 || classes[classes.length/2] == 13 && align == 7 || classes[classes.length-1] == 13 && align == 7)
				error("Player characters that are Monks cannot be Chaotic Good.");
			else if(classes[0] == 13 && align == 8 || classes[classes.length/2] == 13 && align == 8 || classes[classes.length-1] == 13 && align == 8)
				error("Player characters that are Monks cannot be Chaotic Neutral.");
			else if(classes[0] == 13 && align == 9 || classes[classes.length/2] == 13 && align == 9 || classes[classes.length-1] == 13 && align == 9)
				error("Player characters that are Monks cannot be Chaotic Evil.");
			else if(classes[0] == 14 && align == 1 || classes[classes.length/2] == 14 && align == 1 || classes[classes.length-1] == 14 && align == 1)
				error("Player characters that are Bards cannot be Lawful Good.");
			else if(classes[0] == 14 && align == 3 || classes[classes.length/2] == 14 && align == 3 || classes[classes.length-1] == 14 && align == 3)
				error("Player characters that are Bards cannot be Lawful Evil.");
			else if(classes[0] == 14 && align == 7 || classes[classes.length/2] == 14 && align == 7 || classes[classes.length-1] == 14 && align == 7)
				error("Player characters that are Bards cannot be Chaotic Good.");
			else if(classes[0] == 14 && align == 9 || classes[classes.length/2] == 14 && align == 9 || classes[classes.length-1] == 14 && align == 9)
				error("Player characters that are Bards cannot be Chaotic Evil.");
			else
				flag5 = false;
		}
		return align;
	}
	/**
	 * Used in main to determine how many dice the character is to roll to determine ability scores
	 * @param classes the integer array of classes chosen
	 * @return array of the final number of dice a character will roll of each ability score
	 */
	public static int[] abilityDiceSelection(int[] classes){ //TODO Ability dice selection method
		int[] cavalierAbilityDice = {8,6,4,7,9,3,5};
		int[] paladinAbilityDice = {7,5,8,3,6,9,4};
		int[] clericAbilityDice = {7,4,9,5,8,6,3};
		int[] druidAbilityDice = {7,4,8,5,6,9,3}; 
		int[] fighterAbilityDice = {9,3,5,7,8,6,4}; //These are the number of dice rolled for ability scores for each class, as per the Unearthed Arcana method V
		int[] rangerAbilityDice = {7,6,8,5,9,4,3};
		int[] magicuserAbilityDice = {4,9,7,8,6,5,3};
		int[] illusionistAbilityDice = {3,8,7,9,5,6,4};
		int[] thiefAbilityDice = {6,5,3,9,7,4,8};
		int[] assassinAbilityDice = {6,7,4,9,8,3,5};
		int[] monkAbilityDice = {7,5,9,8,6,4,3};
		int[] firstClassAbilityDice = new int[7];
		int[] secondClassAbilityDice = new int[7];
		int[] thirdClassAbilityDice = new int [7];
		
		if(classes[0] == 1) //Since ever character is at least single class, these statements always apply
			firstClassAbilityDice = cavalierAbilityDice;
		else if(classes[0] == 2)
			firstClassAbilityDice = paladinAbilityDice;
		else if(classes[0] == 3)
			firstClassAbilityDice = clericAbilityDice;
		else if(classes[0] == 4)
			firstClassAbilityDice = druidAbilityDice;
		else if(classes[0] == 5 || classes[0] == 6)
			firstClassAbilityDice = fighterAbilityDice;
		else if(classes[0] == 7)
			firstClassAbilityDice = rangerAbilityDice;
		else if(classes[0] == 8)
			firstClassAbilityDice = magicuserAbilityDice;
		else if(classes[0] == 9)
			firstClassAbilityDice = illusionistAbilityDice;
		else if(classes[0] == 10 || classes[0] == 11)
			firstClassAbilityDice = thiefAbilityDice;
		else if(classes[0] == 12)
			firstClassAbilityDice = assassinAbilityDice;
		else if(classes[0] == 13)
			firstClassAbilityDice = monkAbilityDice;
		
		if(classes.length > 1){ //If there is at least a second class, these statements apply
			if(classes[1] == 1)
				secondClassAbilityDice = cavalierAbilityDice;
			else if(classes[1] == 2)
				secondClassAbilityDice = paladinAbilityDice;
			else if(classes[1] == 3)
				secondClassAbilityDice = clericAbilityDice;
			else if(classes[1] == 4)
				secondClassAbilityDice = druidAbilityDice;
			else if(classes[1] == 5 || classes[1] == 6)
				secondClassAbilityDice = fighterAbilityDice;
			else if(classes[1] == 7)
				secondClassAbilityDice = rangerAbilityDice;
			else if(classes[1] == 8)
				secondClassAbilityDice = magicuserAbilityDice;
			else if(classes[1] == 9)
				secondClassAbilityDice = illusionistAbilityDice;
			else if(classes[1] == 10 || classes[1] == 11)
				secondClassAbilityDice = thiefAbilityDice;
			else if(classes[1] == 12)
				secondClassAbilityDice = assassinAbilityDice;
			else if(classes[1] == 13)
				secondClassAbilityDice = monkAbilityDice;
		}
		
		if(classes.length > 2){ //If the character is triple class, these statements apply
			if(classes[2] == 1)
				thirdClassAbilityDice = cavalierAbilityDice;
			else if(classes[2] == 2)
				thirdClassAbilityDice = paladinAbilityDice;
			else if(classes[2] == 3)
				thirdClassAbilityDice = clericAbilityDice;
			else if(classes[2] == 4)
				thirdClassAbilityDice = druidAbilityDice;
			else if(classes[2] == 5 || classes[2] == 6)
				thirdClassAbilityDice = fighterAbilityDice;
			else if(classes[2] == 7)
				thirdClassAbilityDice = rangerAbilityDice;
			else if(classes[2] == 8)
				thirdClassAbilityDice = magicuserAbilityDice;
			else if(classes[2] == 9)
				thirdClassAbilityDice = illusionistAbilityDice;
			else if(classes[2] == 10 || classes[2] == 11)
				thirdClassAbilityDice = thiefAbilityDice;
			else if(classes[2] == 12)
				thirdClassAbilityDice = assassinAbilityDice;
			else if(classes[2] == 13)
				thirdClassAbilityDice = monkAbilityDice;
		}
		
		return optimizeArray(firstClassAbilityDice, secondClassAbilityDice, thirdClassAbilityDice);
	}
	/**
	 * Used in classSelection method to determine if the character has chosen multiple of the same class
	 * @param classes the integer array of classes chosen
	 * @return whether or not multiple classes were chosen
	 */
	public static boolean isRepeatedClasses(int[] classes){
		int[] copy = new int[3];
		for(int i=0; i<classes.length; i++){
			copy[i] = classes[i];
			if(i==0){
				if(classes[i] == copy[1] || classes[i] == copy[2])
					return true;
			}
			else if (i==1){
				if(classes[i] == copy[0] || classes[i] == copy[2])
					return true;
			}
			else if (i==2){
				if(classes[i] == copy[0] || classes[i] == copy[1])
					return true;
			}
			else
				error("Invalid i value in method: isRepeatedClasses");
				
		}
		return false;
	}
	/**
	 * Used in abilityDiceSelection method to compile the arrays of class ability dice to return the best possible number of dice for that character to roll.
	 * @param a set of ability dice of the first class
	 * @param b set of ability dice of the second class
	 * @param c set of ability dice of the third class
	 * @return integer array that is the best combination of number of dice for the character
	 */
	public static int[] optimizeArray(int[] a, int[] b, int[] c){
		
		int[] x = new int[7];
		
		for(int i = 0; i < x.length; i++){
			if(a[i] >= b[i] && a[i] >= c[i])
				x[i] = a[i];
			else if(b[i] >= a[i] && b[i] >= c[i])
				x[i] = b[i];
			else
				x[i] = c[i];
		}
		return x;
	}
	public static void println(String s){ //System.out.println shortcut
		System.out.println(s);
	}
	public static void print(String s){//System.out.print shortcut
		System.out.print(s);
	}
	public static void error(String s){//Same as println but with "Error: " before the message
		System.out.println("Error: " + s);
	}
}
