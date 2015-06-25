package playerCreator;

import java.util.Scanner;

public class PlayerCreator {
	public static void main(String[] args) {
		Scanner keyb = new Scanner(System.in); //Initialize scanner for user input
		int race = raceSelection(keyb); //Collect important information from user
		int gender = genderSelection(keyb);
		int numClasses = numClassesSelection(keyb, race);
		int[] classes = classesSelection(keyb, race, numClasses);
		int alignment = alignmentSelection(keyb, classes);
		int[] abilityDice = abilityDiceSelection(classes);
		int[][] abilities = spreadSheet(abilityDice);
		int[] stats = columnSelection(keyb, abilities);
		int[] allowedWeapons = weaponProfX(classes);
		int[] weapons = weaponChoices(keyb, allowedWeapons, classes);
		
		
		
		
		
		
		
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
	public static int[] abilityDiceSelection(int[] classes){ //TODO Ability dice selection methods
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

	/**
	 * makes the ability spread sheet, 7 attributes 20 times. This method is called in main.
	 * @param abilityDice, the array containing the amount of dice to be rolled per attribute.
	 * @return the 2d array of all the rolls.
	 */
	public static int[][] spreadSheet(int[] abilityDice){ //TODO Ability Selection methods
		int[][] sheet = new int[7][20];
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 7; j++){
				sheet[j][i] = abilityScore(diceRolls(abilityDice[j]));
			}
		}		
		return sheet;
	}
	/**
	 * This method creates an array of various sizes corresponding to the optimized array values, and fills it with d6 roll values. Used in spreadSheet.
	 * ex: fighter strength array is filled with 9 d6 rolls.
	 * @param numDice, the size of the array, contained in the abilityDice array.
	 * @return an array of the rolls for the specified attribute.
	 */
	public static int[] diceRolls(int numDice){
		int[] rolls = new int[numDice];
		for(int i = 0; i < rolls.length; i++){
			rolls[i] = (int)(Math.random()*6)+1;
		}
		return rolls;
	}
	/**
	 * Returns the sum of the top 3 rolls of an attribute. Used in spreadSheet. (It`s a selection sort that spits out the sum of the last 3 values.
	 * @param diceRolls, the array containing the value of the rolls for said ability.
	 * @return the int that represents the ability score, the sum of the top 3 rolls.
	 */
	public static int abilityScore(int[] diceRolls){
		for (int i = 0; i < diceRolls.length - 1; i++){
			int index = i;
			for (int j = i + 1; j < diceRolls.length; j++)
				if (diceRolls[j] < diceRolls[index])
					index = j;
			int smallerNumber = diceRolls[index]; 
			diceRolls[index] = diceRolls[i];
			diceRolls[i] = smallerNumber;
			}
		return (diceRolls[diceRolls.length-1]+diceRolls[diceRolls.length-2]+diceRolls[diceRolls.length-3]);
	}
	/**
	 * Takes in the users choice of stats, from the 20 columns. Used in main.
	 * @param chosenColumn, int from the user.
	 * @param spreadSheet, the 2d array with all of the rolled stats.
	 * @return the characters base stats, before any racial modification.
	 */
	public static int[] columnSelection(Scanner keyb, int[][] spreadSheet){
		println("	1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	16	17	18	19	20");
		println("___________________________________________________________________________________________________________________________________________________________________");
		for(int i = 0; i < 7; i++){
			if(i == 0)
				print("Str |	");
			else if(i == 1)
				print("Int |	");
			else if(i == 2)
				print("Wis |	");
			else if(i == 3)
				print("Dex |	");
			else if(i == 4)
				print("Con |	");
			else if(i == 5)
				print("Char|	");
			else if(i == 6)
				print("Com |	");
			for(int j = 0; j < 20; j++){
				System.out.print(spreadSheet[i][j] + "	");
			}
			System.out.println();
		}
		println("Choose your column wisely, enter 1 for the first column, 2 for the 2nd, etc...");
		int chosenColumn = keyb.nextInt();
		int[] chosenStats = new int[7];
		for(int i = 0; i < 7; i++){
			chosenStats[i] = spreadSheet[i][chosenColumn - 1];
		}
		return chosenStats;
	}
	
	/**This method returns a list of integers that correspond to specific weapons, the list represents the weapons that the character can become proficient in.
	 * @param classes, an array representing the chosen classes of the character.
	 * @return the list of allowable weapons.
	 */
	public static int[] weaponProfX(int[] classes){ //TODO Weapon Proficiency selection methods
		
		int[] a = new int[0];
		int[] b = new int[0];
		int[] c = new int[0];
		int[] cleric = {2,6,7,11,16};
		int[] druid = {2,4,5,7,13,14,15,16};
		int[] m_u = {4,5,16};
		int[] thief = {2,4,5,14,17,18,19,33};
		int[] monk = {1,2,3,4,8,9,10,12,15,16};
		int[] fighter = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
		int[] weaponProfs;
		
		
		for(int i = 0; i<classes.length; i++){
			if(classes[i] == 1 || classes[i] == 2 || classes[i] == 5 || classes[i] == 6 || classes[i] == 7 || classes[i] == 12){
				if(i == 0)
					a = fighter;
				else if(i == 1)
					b = fighter;
				else if(i == 2)
					c = fighter;
			else if(classes[i] == 3)
				if(i == 0)
					a = cleric;
				else if(i == 1)
					b = cleric;
				else if(i == 2)
					c = cleric;
			}
			else if(classes[i] == 4){
				if(i == 0)
					a = druid;
				else if(i == 1)
					b = druid;
				else if(i == 2)
						c = druid;
			}
			else if(classes[i] == 8 || classes[i] == 9){
				if(i == 0)
					a = m_u;
				else if(i == 1)
					b = m_u;
				else if(i == 2)
					c = m_u;
			}
			else if(classes[i] == 10 || classes[i] == 11){
				if(i == 0)
					a = thief;
				else if(i == 1)
					b = thief;
				else if(i == 2)
					c = thief;
			}
			else if(classes[i] == 13){
				if(i == 0)
					a = monk;
				else if(i == 1)
					b = monk;
				else if(i == 2)
					c = monk;
			}
		}
		weaponProfs = mergeArrays(a,b,c);
		return weaponProfs;
	}
	
	/**
	 * Used in weaponProfX, this method merges the lists of allowable weapons of different classes for multi-classed characters.
	 * @param a allowed weapons for 1st class.
	 * @param b allowed weapons for 2nd class.
	 * @param c allowed weapons for 3rd class.
	 * @return same return as weaponProfX.
	 */
	public static int[] mergeArrays(int[] a, int[] b, int[] c){
		
		int[] weaponProfs = new int[a.length + b.length + c.length];
		
		for(int i = 0; i < a.length; i++){
			weaponProfs[i] = a[i];
		}
		for(int i = 0; i < b.length; i++){
			weaponProfs[i+a.length] = b[i];
		}
		for(int i = 0; i < c.length; i++){
			weaponProfs[i+a.length+b.length] = c[i];
		}
		
		int[] allowedWeapons;
		allowedWeapons = removeDuplicates(weaponProfs);
		
		return allowedWeapons;
	}
	
	/**
	 * Used in mergeArrays, this method removes duplicates and sorts the list of allowable weapons.
	 * @param array, the merged array of the allowable weapons.
	 * @return a sorted array of the allowable weapons without any duplicates.
	 */
	public static int[] removeDuplicates(int[] array){
	    boolean[] range = new boolean[41]; //values must default to false
	    int totalItems = 0;

	    for( int i = 0; i < array.length; ++i ){
	        if( range[array[i]] == false ){
	            range[array[i]] = true;
	            totalItems++;
	        }
	    }
	    int[] sortedArray = new int[totalItems];
	    int c = 0;
	    for( int i = 0; i < range.length; ++i ){
	        if( range[i] == true ){
	            sortedArray[c++] = i;
	        }
	    }
	    return sortedArray;
	}
	/**
	 * This method used in main, prints out a list of all the allowable weapons. And then takes user input to fill the character`s starting weapons.
	 * @param keyb scanner.
	 * @param allowedWeapons, the list of allowable weapons.
	 * @param classes, the array of the characters classes.
	 * @return an integer array of the characters starting weapons.
	 */
	public static int[] weaponChoices(Scanner keyb, int[] allowedWeapons, int[] classes){
		//weapons will be returned.
		int[] weapons;
		//Based off of the characters classes, we determine the characters inital number of weapons.
		if(classes[0] == 6 || classes[classes.length/2] == 6 || classes[classes.length-1] == 6)		
			weapons = new int[6];
		else if(classes[0] == 14 || classes[classes.length/2] == 14 || classes[classes.length-1] == 14)		
			weapons = new int[5];
		else if(classes[0] == 5 || classes[classes.length/2] == 5 || classes[classes.length-1] == 5)		
			weapons = new int[4];
		else if(classes[0] == 1 || classes[classes.length/2] == 1 || classes[classes.length-1] == 1 || classes[0] == 2 || classes[classes.length/2] == 2 || classes[classes.length-1] == 2
				|| classes[0] == 7 || classes[classes.length/2] == 7 || classes[classes.length-1] == 7 || classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12)
			weapons = new int[3];
		else if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || 
				classes[classes.length-1] == 4 || classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || 
				classes[classes.length/2] == 11 || classes[classes.length-1] == 11)
			weapons = new int[2];
		else
			weapons = new int[1];
		
		//flag6 is the main flag for the while block, flag7 is used to determine the weapon chosen is in the list of allowable weapons.
		boolean flag6 = true;
		boolean flag7 = false;
		String[] list = {"Bo Stick", "Club", "Crossbow", "Dagger", "Dart", "Flail", "Hammer", "Hand Axe", "Javelin", 
				"Jo Stick", "Mace", "Pole Arm", "Scimitar", "Sling", "Spear", "Staff", "Broad Sword", "Long Sword",
				"Short Sword", "Bastard Sword", "Falchion Sword", "Kopache Sword", "2H Sword", "Battle Axe", "Caltrop",
				"Garrot", "Knife", "Lance", "Lasso", "Man Catcher", "Morning Star", "Military Pick", "Sap", "Trident",
				"Whip", "Atlatl", "Blow Gun", "Long Bow", "Short Bow", "Hand Crossbow"};
		for(int i = 0; i < allowedWeapons.length; i++)
			println(allowedWeapons[i] + ".	" + list[allowedWeapons[i]-1]);
		//Error checking, lots of error checking.
		while(flag6){
			flag7 = false;
			for(int i=0; i<weapons.length; i++){
				weapons[i] = keyb.nextInt();
				keyb.nextLine();
				if(isFound(weapons[i],allowedWeapons) < 0)
					flag7 = true;
					
			}
			if(weapons.length == 1 && isRepeatedClasses(weapons) || weapons.length == 2 && isRepeatedClasses(weapons) ||  classes[0] == 12 && isRepeatedClasses(weapons) 
					|| classes[classes.length/2] == 12 && isRepeatedClasses(weapons) || classes[classes.length-1] == 12 && isRepeatedClasses(weapons))
				error("Non-Fighters may not choose multiples of the same type of weapon");
			else if(flag7)
				error("One of the chosen weapons is not in the list of allowable weapons.");
			
			else
				flag6 = false;
		}
		for(int i = 0; i < weapons.length; i++)
			println(weapons[i] + ".	" + list[weapons[i]-1]);
		return weapons;
	}
	/**
	 * returns the index at which the number is found.
	 * @param choice, the number the method searches for.
	 * @param options, the array the method searches in.
	 * @return the index at which the number is found.
	 */
	public static int isFound(int choice, int[] options){
		
		for(int i = 0; i < options.length; i++){
			if(choice == options[i])
				return i;
		}
		return -1;
	}
	/**
	 * this method removes an unwanted number from an array.
	 * @param unwanted, the unwanted number.
	 * @param options, the array in which the unwanted number is hiding.
	 * @return an array cleansed of all unpleasant entities.
	 */
	public static int[] remove(int unwanted, int[] options){
		
		int[] newArray = new int[options.length-1];
		int index = 0;
		for(int i = 0; i < options.length; i++){
			if(unwanted == options[i]){
				for(int j = 0; j< newArray.length; j++){
					if(j == i){
						newArray[j] = options[++index];
						
					}
					else
						newArray[j] = options[index];
					index++;
				}
				return newArray;
			}
		}
		return options;
	}
	public static String divineChoice(int alignment, int[] classes){
		
		String[] Dlist = {"Raven","Coyote","Hastseltsi (God of Racing)","Hastsezini (Fire God)","Heng (Thunder Spirit)","Hotoru (Wind God)", "Shakak (Winter Spirit)",
							"Snake-Man","Tobadzistsini (War Spirit)",
							
							"Anu (God of the Sky)","Anshar (God of Darkness and the Night)", "Druaga (Ruler of the Devil World)",
							"Girru (God of Fire)", "Ishtar (Goddess of Love and War)","Marduk (God of the City, Wind, Thunder,Storms and Rain)","Nergal (God of the Underworld)",
							"Ramman (God of Storms and Thunder)",
							
							"Dagda (Dozen King)","Arawn (God of the Dead)","Brigit (Goddess of Fire and Poetry)","Diancecht (Physician of the Gods)",
							"Dunatis (God of the Mountains and Peaks)", "Goibhnie (Blacksmith of the Gods)", "Lugh (God of Generalities)", "Manannan Mac Lir (God of the Sea)",
							"Morrigan (Goddess of War)", "Nuada (God of War)", "Oghma (God of Knowledge)", "Silvanus (God of the Forests and Nature)",
							
							"Quetzalcoatl (God of the Air)", "Camaxtli (God of Fate)", "Camazotz (Bat God)", "Chalchiuhtlicue (Goddess of Running Water and Love)",
							"Huhueteotl (Fire God)", "Huitzilopochtli (God of War)", "Itzamna (God of Medicine)", "Mictlantecuhtli (God of Death)", "Tezcatlipoca (Sun God)",
							"Tlaloc (Rain God)", "Tlazolteotl (Goddess of Vice)", "Xochipilli (God of Gambling and Chance)",
							
							"Shang-ti (Supreme God of the Heavens, God of the Sky and Agriculture)", "Chao Kung Ming (Demigod of War)", "Chih-Chiang Fyu-Ya (God of Archers, Punisher of the Gods)",
							"Chih Sung-tzu (Lord of Rain)", "Chung Kuel (God of Truth and Testing)", "Fei Lien & Feng Po (Counts of the Wind)", "Huan-Ti (God of War)",
							"Kuan Yin (Goddess of Mercy and Child Bearing)", "Lei Kung (Duke of Thunder)", "Lu Yueh (God of Epidemics)", "No Cha (Demigod of Thieves)", 
							"Chan Hai Ching (God of Wind and Sea)", "Tou Mu (Goddess of the North Star)", "Wen Chung (Minister of Thunder)", "Yen-Wang-Yeh (Judge of the Dead)",
							
							"Ra (Sun God)", "Anhur (God of War)", "Anubis (Guardian of the Dead)", "Apshai (God of Insects)", "Bast (Cat Goddess)", "Bes (God of Luck)", "Geb (God of the Earth)",
							"Horus (Son of Osiris)", "Isis (Goddess of Magic and Fertility)", "Nephthis (Goddess of Wealth and Protector of the Dead)", "Osiris (God of Nature and the Dead)",
							"Ptah (Creator of the Universe)", "Seker (God of Light)", "Set (God of Evil and the Night)", "Shu (God of the Sky)", "Tefnut (Goddess of Storms and Flowing Water)",
							"Thoth (God of Knowledge)",
							
							"Ahto (God of the Seas and Water)", "Kiputytto (Goddess of Sickness)", "Mielikki (Goddess of Nature)", "Loviatar (Goddess of Hurt)", "Hiisi (God of Evil)",
							"Ilmatar (Goddess of Mothers)", "Surma (Demigod of Death)", "Tuonetar (Goddess of the Underworld)", "Tuoni (God of the Underworld)", "Ukko (Supreme God of the Kalevala)",
							"Untamo (God of Sleep and Dreams)",
							
							"Zeus (God of the Air)", "Aphrodite (Goddess of Love, Beauty and Passion)", "Apollo (God of the Sun, Prophecy, Music and Archery)", "Ares (God of War)",
							"Artemis (Goddess of the Hunt)", "Athena (Goddess of Wisdom and Combat)", "Demeter (Goddess of Agriculture)", "Dionysus (God of Wine)", "Hades (God of the Underworld and Death)",
							"Hecate (Goddess of Magic)", "Hephaestus (God of Blacksmiths)", "Hera (Goddess of Marriage and Intrigue)", "Hermes (God of Thieves, Liars, Gamblers and arbitrators)",
							"Nike (Goddess of Victory)", "Pan (God of Nature and Wild Passion)", "Poseidon (God of Seas, Ocean, Streams and Earthquakes)", "Prometheus (Greater Titan)", "Tyche (Goddess of Good Fortune)",
							
							"Indra (God of the Atmosphere, Storms and Rain)", "Agni (God of Fire and Lightning)", "Kali (Black Earth Mother)", "Karttikeya (Demigod of War)", "Lakshmi (Goddess of Fortune)", 
							"Ratri (Goddess of the Night, Thieves and Robbers)", "Rudra (Storm God, God of the Dead)", "Surya (Sun God)",  "Tvashtri (Demigod of Artifice and Science)", "Ushas (Goddess of the Dawn)",
							"Varuna (Goddess of Order & Protector of Oaths)", "Vishnu (God of Mercy and Light)", "Yama (Demigod of Death)",
							
							"Amaterasu Omikami (Goddess of the Sun)", "Ama-Tsu-Mara (God of Blacksmiths)", "Daikoku (God of Wealth and Luck)", "Ebisu (God of Luck Through Hardwork)", "Hachiman (War God)", "Kishijoten (Goddess of Luck)",
							"Oh-Kuni-Nushi (Patron of Heroes)", "Raiden (God of Thunder and Patron of Fletchers)", "Susanowo (Storm God and Lord of the Earth)", "Tsukiyomi (Moon God)",
							
							"Aarth", "Death", "Gods of Lankhmar", "Gods of Trouble", "Hate", "Issek of the Jug", "Kos (God of Dooms)", "Nehwon Earth God", "Rat God", "Red God", "Spider God", "Tyaa (Winged Goddess of Evil Birds)",
							"Votishal",
							
							"Hruggek (God of Bugbears)", "Skerrit (God of Centaurs)", "Moradin (God of Dwarves)", "Corelion Larethian (God of Elves)", "Deep Sashelas (God of Aquatic Elves)", "Lolth (God of Drow Elves)",
							"Rillifane Rallathil (God of Wood Elves)", "Surtur (God of Fire Giants)", "Thrym (God of Frost Giants)", "Grolantor (God of Hill Giants)", "Skoraeus Stonebones (God of Stone Giants)",
							"Yeenoghu (God of Knolls)", "Garl Glittergold (God of Gnomes)", "Maglubiyet (God of Goblins)", "Yondalla (God of Halflings)", "DemoGorgon (God of the Ixitxachitl)", "Kurtulmak (God of Kobolds)",
							"Blibdoolpoolp (God of Kuo-Toa)", "Semuanya (God of Lizard Men)", "Eadro (God of Mermen)", "Vaprak (God of Ogres)", "Gruumsh (God of Orcs)", "Sekolah (God of Sahuagin)", "Laogzed (God of Troglodytes)",
							
							"Odin Allfather (Supreme Ruler of the Gods)", "Aegir (God of Storms and the Sea)", "Baldur (God of Beauty and Charisma)", "Bragi (God of Poetry and Eloquence and Song)", "Forseti (God of Justice)",
							"Frey (God of Sunshine and the Elves)", "Freya (Goddess of Love and Fertility)", "Frigga (Goddess of the Atmosphere)", "Heimdall (The Bright God)", "Hel (Goddess of Death)", "Idun (Goddess of Spring and Eternal Youth)",
							"Loki (God of Mischief Strife and Fire)", "Modi (God of Courage and Berserk Rage)", "Norns (The Fates)", "Sif (Goddess of Excellence and Skill in Battle)", "Thor (God of Thunder)", "Tyr (God of War and Law)",
							"Uller (God of Hunting, Archery and Winter)", "Vidar (God of Strength and Silence)",
							
							"Enlil (Air and War God)", "Enki (God of the Rivers and Ocean)", "Inanna (War Goddess/Goddess of Love)", "Ki (Goddess of Nature)", "Nanna-Sin (Moon God)", "Nin-Hursag (Goddess of the Earth)", "Utu (Sun God)",
							
							"Azura", "Boethiah", "Clavicus Vile", "Hermaeus Mora", "Hircine", "Jyggalag", "Malacath", "Mehrunes Dagon", "Meridia", "Molag Bal", "Mephala", "Nocturnal", "Nemira", "Peryite", "Sanguine", "Sheogorath", "Vaermina"};
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
