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
		int[] chosenStats = columnSelection(keyb, abilities);
		int[] allowedWeapons = weaponProfX(classes);
		int[] weapons = weaponChoices(keyb, allowedWeapons, classes);
		int deity = divineChoice(keyb, classes, alignment);
		String name = nameSelection(keyb);
		double secondarySkill = secondarySkillsCalculator();
		int[] expNeeded = experienceNeededCalculator(classes);
		int[] savingThrows = savingThrowCalculator(classes);
		double[] stats = abilityScoreAdjustment(classes, chosenStats, race, gender);
		int[] baseBonuses = baseBonusesCalculator(stats);
		int height = heightCalculator(race, gender);
		int weight = weightCalculator(race, gender);
		String birthPlace = birthPlaceCalculator(race);
		//TODO String[] normalItems = normalItemsCalculator(classes);
		//TODO int gold = goldCalculator(classes);
		//TODO int hitPoints = hitPointsCalculator(classes, stats);
		//TODO String titles = titlesCalculator(classes);
		//TODO int[] levelMaximum = levelMaximumCalculator(classes, race);
		//TODO String[] miscInfo = miscInfoCalculator(classes, race, 
		//TODO classAbilities = classAbilitiesCalculator(classes); //Back of the sheet(s)
		//TODO String-converter
		//TODO outwriter
		
		
		
		
		
		
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
			while(rolls[i]<=1)
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
		println("Please enter chosen ability scores (by column):");
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
		else if(classes[0] == 1 || classes[classes.length/2] == 1 || classes[classes.length-1] == 1 || classes[0] == 2 || classes[classes.length/2] == 2 || 
				classes[classes.length-1] == 2 || classes[0] == 7 || classes[classes.length/2] == 7 || classes[classes.length-1] == 7 || classes[0] == 12 || 
				classes[classes.length/2] == 12 || classes[classes.length-1] == 12)
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
		println("Please enter chosen weapons; if specialization is possible multiples of the same weapon will be taken as a specialization in that weapon");
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
					|| classes[classes.length/2] == 12 && isRepeatedClasses(weapons) || classes[classes.length-1] == 12 && isRepeatedClasses(weapons) 
					|| classes[0] == 1 && isRepeatedClasses(weapons) || classes[classes.length/2] == 1 && isRepeatedClasses(weapons) 
					|| classes[classes.length-1] == 1 && isRepeatedClasses(weapons))
				error("Non-Fighters and Cavaliers may not specialize in a weapon");
			else if(flag7)
				error("One of the chosen weapons is not in the list of allowable weapons.");
			
			else
				flag6 = false;
		}
		return weapons;
	}
	public static int divineChoice(Scanner keyb, int[] classes, int alignment){ //TODO Deity selection method
		
		int deity = 0;
		boolean flag8 = true;
		
		String[] dList = {"Raven","Coyote","Hastseltsi (god of racing)","Hastsezini (fire god)","Heng (thunder spirit)","Hotoru (wind god)", "Shakak (winter spirit)",
							"Snake-Man","Tobadzistsini (war spirit)",
							
							"Anu (god of the sky)","Anshar (god of darkness and the night)", "Druaga (ruler of the devil world)",
							"Girru (god of fire)", "Ishtar (goddess of love and war)","Marduk (god of the city, wind, thunder, storms and rain)","Nergal (god of the underworld)",
							"Ramman (god of storms and thunder)",
							
							"Dagda (dozen king)","Arawn (god of the dead)","Brigit (goddess of fire and poetry)","Diancecht (physician of the gods)",
							"Dunatis (god of the mountains and peaks)", "Goibhnie (blacksmith of the gods)", "Lugh (god of generalities)", "Manannan Mac Lir (god of the sea)",
							"Morrigan (goddess of war)", "Nuada (god of war)", "Oghma (god of knowledge)", "Silvanus (god of the forests and nature)",
							
							"Quetzalcoatl (god of the air)", "Camaxtli (god of fate)", "Camazotz (bat god)", "Chalchiuhtlicue (goddess of running water and love)",
							"Huhueteotl (fire god)", "Huitzilopochtli (god of war)", "Itzamna (god of medicine)", "Mictlantecuhtli (god of death)", "Tezcatlipoca (sun god)",
							"Tlaloc (rain god)", "Tlazolteotl (goddess of vice)", "Xochipilli (god of gambling and chance)",
							
							"Shang-ti (supreme god of the heavens, god of the sky and agriculture)", "Chao Kung Ming (demigod of war)", 
							"Chih-Chiang Fyu-Ya (god of archers, punisher of the gods)", "Chih Sung-tzu (lord of rain)", "Chung Kuel (god of truth and testing)", 
							"Fei Lien & Feng Po (counts of the wind)", "Huan-Ti (god of war)", "Kuan Yin (goddess of mercy and child bearing)", "Lei Kung (duke of thunder)", 
							"Lu Yueh (god of epidemics)", "No Cha (demigod of thieves)", "Shan Hai Ching (god of wind and sea)", "Tou Mu (goddess of the north star)", 
							"Wen Chung (minister of thunder)", "Yen-Wang-Yeh (judge of the dead)",
							
							"Ra (sun god)", "Anhur (god of war)", "Anubis (guardian of the dead)", "Apshai (god of insects)", "Bast (cat goddess)", "Bes (god of luck)", 
							"Geb (god of the earth)", "Horus (son of Osiris)", "Isis (goddess of magic and fertility)", "Nephthis (goddess of wealth and protector of the dead)", 
							"Osiris (god of nature and the dead)", "Ptah (creator of the universe)", "Seker (god of light)", "Set (god of evil and the night)", 
							"Shu (god of the sky)", "Tefnut (goddess of storms and flowing water)", "Thoth (god of knowledge)",
							
							"Ahto (god of the seas and water)", "Kiputytto (goddess of sickness)", "Mielikki (goddess of nature)", "Loviatar (goddess of hurt)", 
							"Hiisi (god of evil)", "Ilmatar (goddess of mothers)", "Surma (demigod of death)", "Tuonetar (goddess of the underworld)", 
							"Tuoni (god of the underworld)", "Ukko (supreme god of the Kalevala)", "Untamo (god of sleep and dreams)",
							
							"Zeus (god of the air)", "Aphrodite (goddess of love, beauty and passion)", "Apollo (god of the sun, prophecy, music and archery)", "Ares (god of war)",
							"Artemis (goddess of the hunt)", "Athena (goddess of wisdom and combat)", "Demeter (goddess of agriculture)", "Dionysus (god of wine)", 
							"Hades (god of the underworld and death)", "Hecate (goddess of magic)", "Hephaestus (god of blacksmiths)", "Hera (goddess of marriage and intrigue)", 
							"Hermes (god of thieves, liars, gamblers and arbitrators)", "Nike (goddess of victory)", "Pan (god of nature and wild passion)", 
							"Poseidon (god of seas, ocean, streams and earthquakes)", "Prometheus (greater titan)", "Tyche (goddess of good fortune)",
							
							"Indra (god of the atmosphere, storms and rain)", "Agni (god of fire and lightning)", "Kali (black earth mother)", "Karttikeya (demigod of war)", 
							"Lakshmi (goddess of fortune)", "Ratri (goddess of the night, thieves and robbers)", "Rudra (storm god, god of the dead)", "Surya (sun god)", 
							"Tvashtri (demigod of artifice and science)", "Ushas (goddess of the dawn)", "Varuna (goddess of order & protector of oaths)", 
							"Vishnu (god of mercy and light)", "Yama (demigod of death)",
							
							"Amaterasu Omikami (goddess of the sun)", "Ama-Tsu-Mara (god of blacksmiths)", "Daikoku (god of wealth and luck)", "Ebisu (god of luck through hardwork)",
							"Hachiman (war god)", "Kishijoten (goddess of luck)", "Oh-Kuni-Nushi (patron of heroes)", "Raiden (god of thunder and patron of fletchers)", 
							"Susanowo (storm god and lord of the earth)", "Tsukiyomi (moon god)",
							
							"Aarth", "Death", "gods of Lankhmar", "gods of Trouble", "Hate", "Issek of the Jug", "Kos (god of dooms)", "Nehwon Earth god", "Rat god", "Red god", 
							"Spider god", "Tyaa (winged goddess of evil birds)", "Votishal",
							
							"Hruggek (god of Bugbears)", "Skerrit (god of Centaurs)", "Moradin (god of Dwarves)", "Corelion Larethian (god of Elves)", 
							"Deep Sashelas (god of Aquatic Elves)", "Lolth (god of Drow Elves)", "Rillifane Rallathil (god of Wood Elves)", "Surtur (god of Fire Giants)", 
							"Thrym (god of Frost Giants)", "Grolantor (god of Hill Giants)", "Skoraeus Stonebones (god of Stone Giants)", "Yeenoghu (god of Gnolls)", 
							"Garl Glittergold (god of Gnomes)", "Maglubiyet (god of Goblins)", "Yondalla (god of Halflings)", "Demogorgon (god of Ixitxachitl)", 
							"Kurtulmak (god of Kobolds)", "Blibdoolpoolp (god of Kuo-Toa)", "Semuanya (god of Lizard Men)", "Eadro (god of Mermen)", "Vaprak (god of Ogres)", 
							"Gruumsh (god of Orcs)", "Sekolah (god of Sahuagin)", "Laogzed (god of Troglodytes)",
							
							"Odin All Father (supreme ruler of the gods)", "Aegir (god of storms and the sea)", "Baldur (god of beauty and charisma)", 
							"Bragi (god of poetry and eloquence and song)", "Forseti (god of justice)", "Frey (god of sunshine and the Elves)", 
							"Freya (goddess of love and fertility)", "Frigga (goddess of the atmosphere)", "Heimdall (the bright god)", "Hel (goddess of death)", 
							"Idun (goddess of spring and eternal youth)", "Loki (god of mischief strife and fire)", "Modi (god of courage and berserk rage)", "Norns (the fates)", 
							"Sif (goddess of excellence and skill in battle)", "Thor (god of thunder)", "Tyr (god of war and law)", "Uller (god of hunting, archery and winter)", 
							"Vidar (god of strength and silence)",
							
							"Enlil (air and war god)", "Enki (god of the rivers and ocean)", "Inanna (war goddess/goddess of love)", "Ki (goddess of nature)", "Nanna-Sin (moon god)",
							"Nin-Hursag (goddess of the earth)", "Utu (sun god)",
							
							"Azura", "Boethiah", "Clavicus Vile", "Hermaeus Mora", "Hircine", "Jyggalag", "Malacath", "Mehrunes Dagon", "Meridia", "Molag Bal", "Mephala", 
							"Nocturnal", "Nemira", "Peryite", "Sanguine", "Sheogorath", "Vaermina"};
		
		println("\nPlease enter chosen deity:");
		for(int i=0; i<dList.length; i++){
			if(i==0)
				println("American Indian Pantheon:");
			else if(i==9)
				println("Babylonian Pantheon:");
			else if(i==17)
				println("Celtic Pantheon:");
			else if(i==29)
				println("Central American Pantheon:");
			else if(i==41)
				println("Chinese Pantheon:");
			else if(i==56)
				println("Egyptian Pantheon:");
			else if(i==73)
				println("Finnish Pantheon:");
			else if(i==84)
				println("Greek Pantheon:");
			else if(i==102)
				println("Indian Pantheon:");
			else if(i==115)
				println("Japanese Pantheon:");
			else if(i==125)
				println("Nehwon Pantheon:");
			else if(i==138)
				println("Nonhuman Pantheon:");
			else if(i==162)
				println("Norse Pantheon:");
			else if(i==181)
				println("Sumerian Pantheon:");
			else if(i==188)
				println("Daedric Pantheon:");
			
			println("\t" + (i+1) + ". \t" + dList[i]);
		}
		
		while(flag8){
			deity = keyb.nextInt();
		
		
			if(deity < 1 || deity > 205)
				error("Invalid option chosen. Please choose between the designated choices.");
			else if (deity == 1 && classes[0] == 3 && alignment != 7 || deity == 1 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 1 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 2 && classes[0] == 3 && alignment != 8 || deity == 2 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 2 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 3 && classes[0] == 3 && alignment != 5 || deity == 3 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 3 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 4 && classes[0] == 3 && alignment != 3 || deity == 4 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 4 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 5 && classes[0] == 3 && alignment != 1 || deity == 5 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 5 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 6 && classes[0] == 3 && alignment != 7 || deity == 6 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 6 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 7 && classes[0] == 3 && alignment != 9 || deity == 7 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 7 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 8 && classes[0] == 3 && alignment != 7 || deity == 8 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 8 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 9 && classes[0] == 3 && alignment != 6 || deity == 9 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 9 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 10 && classes[0] == 3 && alignment != 2 || deity == 10 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 10 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 11 && classes[0] == 3 && alignment != 9 || deity == 11 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 11 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 12 && classes[0] == 3 && alignment != 3 || deity == 12 && classes[classes.length/2] == 3 && alignment != 3|| 
					deity == 12 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 13 && classes[0] == 3 && alignment != 1 || deity == 13 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 13 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 14 && classes[0] == 3 && alignment != 5 || deity == 14 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 14 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 15 && classes[0] == 3 && alignment != 2 || deity == 15 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 15 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 16 && classes[0] == 3 && alignment != 6 || deity == 16 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 16 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 17 && classes[0] == 3 && alignment != 5 || deity == 17 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 17 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 18 && classes[0] == 3 && alignment != 5 || deity == 18 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 18 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 19 && classes[0] == 3 && alignment != 3 || deity == 19 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 19 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 20 && classes[0] == 3 && alignment != 5 || deity == 20 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 20 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 21 && classes[0] == 3 && alignment != 1 || deity == 21 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 21 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 22 && classes[0] == 3 && alignment != 5 || deity == 22 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 22 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 23 && classes[0] == 3 && alignment != 5 || deity == 23 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 23 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 24 && classes[0] == 3 && alignment != 5 || deity == 24 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 24 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 25 && classes[0] == 3 && alignment != 8 || deity == 25 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 25 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 26 && classes[0] == 3 && alignment != 5 || deity == 26 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 26 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 27 && classes[0] == 3 && alignment != 5 || deity == 27 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 27 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 28 && classes[0] == 3 && alignment != 5 || deity == 28 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 28 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 29 && classes[0] == 3 && alignment != 5 || deity == 29 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 29 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 30 && classes[0] == 3 && alignment != 2 || deity == 30 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 30 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 31 && classes[0] == 3 && alignment != 5 || deity == 31 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 31 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 32 && classes[0] == 3 && alignment != 9 || deity == 32 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 32 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 33 && classes[0] == 3 && alignment != 7 || deity == 33 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 33 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 34 && classes[0] == 3 && alignment != 9 || deity == 34 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 34 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 35 && classes[0] == 3 && alignment != 5 || deity == 35 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 35 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 36 && classes[0] == 3 && alignment != 4 || deity == 36 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 36 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 37 && classes[0] == 3 && alignment != 3 || deity == 37 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 37 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 38 && classes[0] == 3 && alignment != 9 || deity == 38 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 38 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 39 && classes[0] == 3 && alignment != 3 || deity == 39 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 39 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 40 && classes[0] == 3 && alignment != 9 || deity == 40 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 40 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 41 && classes[0] == 3 && alignment != 5 || deity == 41 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 41 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 42 && classes[0] == 3 && alignment != 2 || deity == 42 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 42 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 43 && classes[0] == 3 && alignment != 4 || deity == 43 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 43 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 44 && classes[0] == 3 && alignment != 9 || deity == 44 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 44 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 45 && classes[0] == 3 && alignment != 5 || deity == 45 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 45 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 46 && classes[0] == 3 && alignment != 1 || deity == 46 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 46 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 47 && classes[0] == 3 && alignment != 4 || deity == 47 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 47 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 48 && classes[0] == 3 && alignment != 7 || deity == 48 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 48 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 49 && classes[0] == 3 && alignment != 1 || deity == 49 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 49 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 50 && classes[0] == 3 && alignment != 3 || deity == 50 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 50 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 51 && classes[0] == 3 && alignment != 9 || deity == 51 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 51 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 52 && classes[0] == 3 && alignment != 6 || deity == 52 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 52 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 53 && classes[0] == 3 && alignment != 2 || deity == 53 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 53 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 54 && classes[0] == 3 && alignment != 9 || deity == 54 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 54 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 55 && classes[0] == 3 && alignment != 8 || deity == 55 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 55 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 56 && classes[0] == 3 && alignment != 5 || deity == 56 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 56 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 57 && classes[0] == 3 && alignment != 4 || deity == 57 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 57 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 58 && classes[0] == 3 && alignment != 1 || deity == 58 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 58 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 59 && classes[0] == 3 && alignment != 1 || deity == 59 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 59 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 60 && classes[0] == 3 && alignment != 5 || deity == 60 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 60 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 61 && classes[0] == 3 && alignment != 7 || deity == 61 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 61 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 62 && classes[0] == 3 && alignment != 5 || deity == 62 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 62 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 63 && classes[0] == 3 && alignment != 5 || deity == 63 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 63 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 64 && classes[0] == 3 && alignment != 2 || deity == 64 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 64 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 65 && classes[0] == 3 && alignment != 4 || deity == 65 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 65 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 66 && classes[0] == 3 && alignment != 7 || deity == 66 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 66 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 67 && classes[0] == 3 && alignment != 1 || deity == 67 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 67 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 68 && classes[0] == 3 && alignment != 2 || deity == 68 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 68 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 69 && classes[0] == 3 && alignment != 4 || deity == 69 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 69 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 70 && classes[0] == 3 && alignment != 3 || deity == 70 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 70 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 71 && classes[0] == 3 && alignment != 1 || deity == 71 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 71 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 72 && classes[0] == 3 && alignment != 1 || deity == 72 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 72 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 73 && classes[0] == 3 && alignment != 5 || deity == 73 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 73 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 74 && classes[0] == 3 && alignment != 4 || deity == 74 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 74 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 75 && classes[0] == 3 && alignment != 9 || deity == 75 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 75 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 76 && classes[0] == 3 && alignment != 4 || deity == 76 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 76 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 77 && classes[0] == 3 && alignment != 3 || deity == 77 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 77 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 78 && classes[0] == 3 && alignment != 9 || deity == 78 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 78 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 79 && classes[0] == 3 && alignment != 1 || deity == 79 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 79 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 80 && classes[0] == 3 && alignment != 6 || deity == 80 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 80 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 81 && classes[0] == 3 && alignment != 9 || deity == 81 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 81 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 82 && classes[0] == 3 && alignment != 8 || deity == 82 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 82 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 83 && classes[0] == 3 && alignment != 1 || deity == 83 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 83 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 84 && classes[0] == 3 && alignment != 5 || deity == 84 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 84 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 85 && classes[0] == 3 && alignment != 7 || deity == 85 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 85 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 86 && classes[0] == 3 && alignment != 7 || deity == 86 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 86 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 87 && classes[0] == 3 && alignment != 7 || deity == 87 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 87 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 88 && classes[0] == 3 && alignment != 9 || deity == 88 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 88 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 89 && classes[0] == 3 && alignment != 5 || deity == 89 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 89 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 90 && classes[0] == 3 && alignment != 1 || deity == 90 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 90 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 91 && classes[0] == 3 && alignment != 4 || deity == 91 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 91 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 92 && classes[0] == 3 && alignment != 8 || deity == 92 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 92 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 93 && classes[0] == 3 && alignment != 6 || deity == 93 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 93 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 94 && classes[0] == 3 && alignment != 3 || deity == 94 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 94 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 95 && classes[0] == 3 && alignment != 5 || deity == 95 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 95 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 96 && classes[0] == 3 && alignment != 5 || deity == 96 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 96 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 97 && classes[0] == 3 && alignment != 5 || deity == 97 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 97 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 98 && classes[0] == 3 && alignment != 2 || deity == 98 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 98 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 99 && classes[0] == 3 && alignment != 8 || deity == 99 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 99 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 100 && classes[0] == 3 && alignment != 8 || deity == 100 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 100 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 101 && classes[0] == 3 && alignment != 4 || deity == 101 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 101 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 102 && classes[0] == 3 && alignment != 5 || deity == 102 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 102 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 103 && classes[0] == 3 && alignment != 8 || deity == 103 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 103 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 104 && classes[0] == 3 && alignment != 8 || deity == 104 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 104 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 105 && classes[0] == 3 && alignment != 9 || deity == 105 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 105 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 106 && classes[0] == 3 && alignment != 7 || deity == 106 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 106 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 107 && classes[0] == 3 && alignment != 7 || deity == 107 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 107 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 108 && classes[0] == 3 && alignment != 6 || deity == 108 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 108 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 109 && classes[0] == 3 && alignment != 2 || deity == 109 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 109 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 110 && classes[0] == 3 && alignment != 1 || deity == 110 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 110 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 111 && classes[0] == 3 && alignment != 5 || deity == 111 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 111 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 112 && classes[0] == 3 && alignment != 4 || deity == 112 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 112 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 113 && classes[0] == 3 && alignment != 2 || deity == 113 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 113 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 114 && classes[0] == 3 && alignment != 1 || deity == 114 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 114 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 115 && classes[0] == 3 && alignment != 2 || deity == 115 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 115 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 116 && classes[0] == 3 && alignment != 1 || deity == 116 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 116 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 117 && classes[0] == 3 && alignment != 5 || deity == 117 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 117 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 118 && classes[0] == 3 && alignment != 1 || deity == 118 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 118 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 119 && classes[0] == 3 && alignment != 1 || deity == 119 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 119 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 120 && classes[0] == 3 && alignment != 7 || deity == 120 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 120 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 121 && classes[0] == 3 && alignment != 4 || deity == 121 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 121 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 122 && classes[0] == 3 && alignment != 7 || deity == 122 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 122 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 123 && classes[0] == 3 && alignment != 5 || deity == 123 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 123 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 124 && classes[0] == 3 && alignment != 8 || deity == 124 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 124 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 125 && classes[0] == 3 && alignment != 4 || deity == 125 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 125 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 126 && classes[0] == 3 && alignment != 2 || deity == 126 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 126 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 127 && classes[0] == 3 && alignment != 5 || deity == 127 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 127 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 128 && classes[0] == 3 && alignment != 6 || deity == 128 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 128 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 129 && classes[0] == 3 && alignment != 9 || deity == 129 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 129 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 130 && classes[0] == 3 && alignment != 9 || deity == 130 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 130 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 131 && classes[0] == 3 && alignment != 1 || deity == 131 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 131 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 132 && classes[0] == 3 && alignment != 5 || deity == 132 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 132 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 133 && classes[0] == 3 && alignment != 9 || deity == 133 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 133 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 134 && classes[0] == 3 && alignment != 9 || deity == 134 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 134 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 135 && classes[0] == 3 && alignment != 5 || deity == 135 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 135 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 136 && classes[0] == 3 && alignment != 9 || deity == 136 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 136 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 137 && classes[0] == 3 && alignment != 9 || deity == 137 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 137 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 138 && classes[0] == 3 && alignment != 1 || deity == 138 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 138 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 139 && classes[0] == 3 && alignment != 9 || deity == 139 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 139 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 140 && classes[0] == 3 && alignment != 5 || deity == 140 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 140 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 141 && classes[0] == 3 && alignment != 1 || deity == 141 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 141 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 142 && classes[0] == 3 && alignment != 7 || deity == 142 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 142 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 143 && classes[0] == 3 && alignment != 7 || deity == 143 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 143 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 144 && classes[0] == 3 && alignment != 9 || deity == 144 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 144 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 145 && classes[0] == 3 && alignment != 7 || deity == 145 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 145 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 146 && classes[0] == 3 && alignment != 3 || deity == 146 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 146 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 147 && classes[0] == 3 && alignment != 9 || deity == 147 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 147 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 148 && classes[0] == 3 && alignment != 9 || deity == 148 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 148 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 149 && classes[0] == 3 && alignment != 5 || deity == 149 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 149 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 150 && classes[0] == 3 && alignment != 9 || deity == 150 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 150 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 151 && classes[0] == 3 && alignment != 1 || deity == 151 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 151 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 152 && classes[0] == 3 && alignment != 3 || deity == 152 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 152 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 153 && classes[0] == 3 && alignment != 1 || deity == 153 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 153 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 154 && classes[0] == 3 && alignment != 9 || deity == 154 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 154 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 155 && classes[0] == 3 && alignment != 3 || deity == 155 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 155 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 156 && classes[0] == 3 && alignment != 6 || deity == 156 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 156 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 157 && classes[0] == 3 && alignment != 5 || deity == 157 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 157 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 158 && classes[0] == 3 && alignment != 5 || deity == 158 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 158 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 159 && classes[0] == 3 && alignment != 9 || deity == 159 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 159 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 160 && classes[0] == 3 && alignment != 3 || deity == 160 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 160 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 161 && classes[0] == 3 && alignment != 3 || deity == 161 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 161 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 162 && classes[0] == 3 && alignment != 9 || deity == 162 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 162 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 163 && classes[0] == 3 && alignment != 4 || deity == 163 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 163 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 164 && classes[0] == 3 && alignment != 8 || deity == 164 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 164 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 165 && classes[0] == 3 && alignment != 4 || deity == 165 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 165 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 166 && classes[0] == 3 && alignment != 4 || deity == 166 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 166 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 167 && classes[0] == 3 && alignment != 1 || deity == 167 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 167 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 168 && classes[0] == 3 && alignment != 4 || deity == 168 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 168 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 169 && classes[0] == 3 && alignment != 4 || deity == 169 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 169 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 170 && classes[0] == 3 && alignment != 2 || deity == 170 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 170 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 171 && classes[0] == 3 && alignment != 1 || deity == 171 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 171 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 172 && classes[0] == 3 && alignment != 6 || deity == 172 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 172 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 173 && classes[0] == 3 && alignment != 7 || deity == 173 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 173 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 174 && classes[0] == 3 && alignment != 9 || deity == 174 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 174 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 175 && classes[0] == 3 && alignment != 7 || deity == 175 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 175 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 176 && classes[0] == 3 && alignment != 5 || deity == 176 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 176 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 177 && classes[0] == 3 && alignment != 7 || deity == 177 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 177 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 178 && classes[0] == 3 && alignment != 7 || deity == 178 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 178 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 179 && classes[0] == 3 && alignment != 1 || deity == 179 && classes[classes.length/2] == 3 && alignment != 1 || 
					deity == 179 && classes[classes.length-1] == 3 && alignment != 1)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 180 && classes[0] == 3 && alignment != 7 || deity == 180 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 180 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 181 && classes[0] == 3 && alignment != 7 || deity == 181 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 181 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 182 && classes[0] == 3 && alignment != 4 || deity == 182 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 182 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 183 && classes[0] == 3 && alignment != 2 || deity == 183 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 183 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 184 && classes[0] == 3 && alignment != 3 || deity == 184 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 184 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 185 && classes[0] == 3 && alignment != 5 || deity == 185 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 185 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 186 && classes[0] == 3 && alignment != 7 || deity == 186 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 186 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 187 && classes[0] == 3 && alignment != 5 || deity == 187 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 187 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 188 && classes[0] == 3 && alignment != 7 || deity == 188 && classes[classes.length/2] == 3 && alignment != 7 || 
					deity == 188 && classes[classes.length-1] == 3 && alignment != 7)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 189 && classes[0] == 3 && alignment != 4 || deity == 189 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 189 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 190 && classes[0] == 3 && alignment != 9 || deity == 190 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 190 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 191 && classes[0] == 3 && alignment != 9 || deity == 191 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 191 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 192 && classes[0] == 3 && alignment != 5 || deity == 192 && classes[classes.length/2] == 3 && alignment != 5 || 
					deity == 192 && classes[classes.length-1] == 3 && alignment != 5)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 193 && classes[0] == 3 && alignment != 8 || deity == 193 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 193 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 194 && classes[0] == 3 && alignment != 2 || deity == 194 && classes[classes.length/2] == 3 && alignment != 2 || 
					deity == 194 && classes[classes.length-1] == 3 && alignment != 2)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 195 && classes[0] == 3 && alignment != 9 || deity == 195 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 195 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 196 && classes[0] == 3 && alignment != 9 || deity == 196 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 196 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 197 && classes[0] == 3 && alignment != 4 || deity == 197 && classes[classes.length/2] == 3 && alignment != 4 || 
					deity == 197 && classes[classes.length-1] == 3 && alignment != 4)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 198 && classes[0] == 3 && alignment != 3 || deity == 198 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 198 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 199 && classes[0] == 3 && alignment != 8 || deity == 199 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 199 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 200 && classes[0] == 3 && alignment != 8 || deity == 200 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 200 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 201 && classes[0] == 3 && alignment != 9 || deity == 201 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 201 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 202 && classes[0] == 3 && alignment != 6 || deity == 202 && classes[classes.length/2] == 3 && alignment != 6 || 
					deity == 202 && classes[classes.length-1] == 3 && alignment != 6)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 203 && classes[0] == 3 && alignment != 9 || deity == 203 && classes[classes.length/2] == 3 && alignment != 9 || 
					deity == 203 && classes[classes.length-1] == 3 && alignment != 9)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 204 && classes[0] == 3 && alignment != 8 || deity == 204 && classes[classes.length/2] == 3 && alignment != 8 || 
					deity == 204 && classes[classes.length-1] == 3 && alignment != 8)
				error("Clerics must be the same alignment as their deity");
			else if (deity == 205 && classes[0] == 3 && alignment != 3 || deity == 205 && classes[classes.length/2] == 3 && alignment != 3 || 
					deity == 205 && classes[classes.length-1] == 3 && alignment != 3)
				error("Clerics must be the same alignment as their deity");
			else if(deity == 2 && alignment != 8 && ((classes[0] < 10 || classes[0] > 11) && (classes[classes.length/2] > 11 || classes[classes.length/2] < 10) && (classes[classes.length-1] > 11 || classes[classes.length-1] < 10)))
					error("Those who worship the Coyote must be of chaotic neutral alignment and/or be thieves.");
			else if(deity == 5 && !(alignment == 1 || alignment ==4 || alignment == 7))
				error("Those who worship Heng must be of good alignment.");
			else if(deity == 6 && alignment != 7)
				error("Those who worship Hotoru must be of chaotic good alignment.");
			else if(deity == 8 && alignment != 7)
				error("Those who worship the Snake-Man must be of chaotic good alignment.");
			else if(deity == 9 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Tobadzistsini must be warriors (fighter/fighter sub classes).");
			else if(deity == 11 && !(alignment == 6 || alignment == 9))
				error("Those who worship Anshar must be of either neutral evil or chaotic evil alignment.");
			else if(deity == 12 && alignment != 3)
				error("Those who worship Druaga must be of the lawful evil alignment.");
			else if(deity == 13 && !(alignment == 1 || alignment == 4))
				error("Those who worship Girru must of either lawful good or neutral good alignment.");
			else if(deity == 15 && alignment != 2)
				error("Those who worship Marduk must be of lawful neutral alignment.");
			else if(deity == 16 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Nergal must be of evil alignment");
			else if(deity == 17 && !(alignment == 2 || alignment == 5 || alignment == 8))
				error("Those who worship Ramman must be neutral.");
			else if(deity == 18 && !(alignment == 2 || alignment == 5 || alignment == 8))
				error("Those who worship Ramman must be neutral.");
			else if(deity == 19 && (alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Arawn must be of non-good alignment.");
			else if(deity == 22 && alignment != 5)
				error("Those who worship Dunatis must be of true neutral alignment.");
			else if(deity == 24 && alignment != 5)
				error("Those who worship Lugh must be of true neutral alignment.");
			else if(deity == 26 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Morrigan must be warriors (fighter/fighter sub classes).");
			else if(deity == 27 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Nuada must be warriors (fighter/fighter sub classes).");
			else if(deity == 28 && alignment != 5)
				error("Those who worship Oghma must be of true neutral alignment.");
			else if(deity == 32 && alignment != 9)
				error("Those who worship Camazotz must be of chaotic evil alignment.");
			else if(deity == 34 && alignment != 9)
				error("Those who worship Huhueteotl must be of chaotic evil alignment.");
			else if(deity == 35 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Huitzilopochtli must be warriors (fighter/fighter sub classes).");
			else if(deity == 36 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Itzamna must be of good alignment.");
			else if(deity == 38 && alignment != 9)
				error("Those who worship Tezcatlipoca must be of chaotic evil alignment.");
			else if(deity == 40 && alignment != 9)
				error("Those who worship Tlazolteotl must be of chaotic evil alignment.");
			else if(deity == 43 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Chao Kung Ming must be warriors (fighter/fighter sub classes).");
			else if(deity == 44 && alignment != 3 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Chih-Chiang Fyu-Ya must be archers (fighter/fighter sub classes) or of lawful evil alignment.");
			else if(deity == 46 && alignment != 1)
				error("Those who worship Chung Kuel must be of lawful good alignment");
			else if(deity == 47 && alignment != 4)
				error("Those who worship the counts of the wind must be of neutral good alignment.");
			else if(deity == 48 && alignment !=7 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Huan-Ti must be warriors (fighter/fighter sub classes).");
			else if(deity == 49 && alignment != 1)
				error("Those who worship Kuan Yin must be of lawful good alignment.");
			else if(deity == 50 && alignment != 3)
				error("Those who worship Lei Kung must be of lawful evil alignment.");
			else if(deity == 51 && alignment != 9)
				error("Those who worship Lu Yueh must be of chaotic evil alignment.");
			else if(deity == 52 && !(classes[0] == 10 || classes[0] ==11 || classes[classes.length/2] == 10 || classes[classes.length/2] == 11 || classes[classes.length-1] == 10 || classes[classes.length-1] == 11))
				error("Those who worship No Cha must be thieves.");
			else if(deity == 54 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Tou Mu must be of evil alignment.");
			else if(deity == 55 && alignment != 8)
				error("Those who worship Wen Chung must be of chaotic neutral alignment.");
			else if(deity == 56 && alignment != 5)
				error("Those who worship Yen-Wang-Yeh must be of true neutral alignment.");
			else if(deity == 57 && alignment != 4)
				error("Those who worship Ra must be of neutral good alignment.");
			else if(deity == 58 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Anhur must be warriors (fighter/fighter sub classes).");
			else if(deity == 61 && !(alignment == 6 || alignment == 7 || alignment ==9))
				error("Those who worship Bast must be of chaotic alignment.");
			else if(deity == 63 && !(alignment == 4 || alignment == 5 || alignment == 6))
				error("Those who worship Geb must be of neutral alignment.");
			else if(deity == 64 && alignment != 2)
				error("Those who worship Horus must be of lawful neutral alignment.");
			else if(deity == 65 && alignment != 4)
				error("Those who worship Isis must be of neutral good alignment.");
			else if(deity == 66 && alignment != 7)
				error("Those who worship Nephthis must be of chaotic good alignment.");
			else if(deity == 67 && alignment != 1)
				error("Those who worship Osiris must be of lawful good alignment.");
			else if(deity == 68 && alignment != 2)
				error("Those who worship Ptah must be of lawful neutral alignment.");
			else if(deity == 70 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Set must be of evil alignment.");
			else if(deity == 71 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Shu must be of good alignment.");
			else if(deity == 72 && alignment != 1)
				error("Those who worsip Tefnut must be of lawful good alignment.");
			else if(deity == 74 && alignment != 4)
				error("Those who worship Ahto must be of neutral good alignment.");
			else if(deity == 75 && alignment != 9)
				error("Those who worship Kiputytto must be of chaotic evil alignment.");
			else if(deity == 76 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Mielikki must be of good alignment.");
			else if(deity == 77 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Loviatar must be of evil alignment.");
			else if(deity == 78 && alignment != 9)
				error("Those who worship Hiisi must be of chaotic evil alignment.");
			else if(deity == 79 && !(alignment == 1 || alignment == 2 || alignment == 3))
				error("Those who worship Ilmatar must be of lawful alignment.");
			else if(deity == 80 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Surma must be of evil alignmnet.");
			else if(deity == 81 && alignment != 9)
				error("Those who worship Tuonetar must be of chaotic evil alignment.");
			else if(deity == 82 && !(alignment == 7 || alignment == 8 || alignment == 9))
				error("Those who worship Tuoni must be of chaotic alignment.");
			else if(deity == 83 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Ukko must be of good alignment.");
			else if(deity == 84 && alignment != 5)
				error("Those who worship Untamo must be of true neutral alignment.");
			else if(deity == 85 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Zeus must be of good alignment.");
			else if(deity == 86 && !(alignment == 7 || alignment == 8 || alignment == 9))
				error("Those who worship Aphrodite must be of chaotic alignment.");
			else if(deity == 88 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Ares must be warriors (fighter/fighter sub classes).");
			else if(deity == 89 && alignment != 5)
				error("Those who worship Artemis must be of true neutral alignment.");
			else if(deity == 90 && alignment != 1 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Athena must either be of lawful good alignment or warriors (fighter/fighter sub classes).");
			else if(deity == 91 && (alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Demeter must be of non-evil alignment.");
			else if(deity == 92 && !(alignment == 7 || alignment == 8 || alignment == 9))
				error("Those who worship Dionysus must be of chaotic alignment.");
			else if(deity == 93 && alignment != 6)
				error("Those who worship Hades must be of neutral evil alignment.");
			else if(deity == 94 && !(classes[0] == 3 || classes[0] == 4 || classes[0] == 8 || classes[0] == 9 || classes[classes.length/2] == 3 || classes[classes.length/2] == 4 || classes[classes.length/2] == 8|| classes[classes.length/2] == 9 || classes[classes.length-1] == 3 || classes[classes.length-1] == 4 || classes[classes.length-1] == 8 || classes[classes.length-1] == 9))
				error("Those who do not wield magic may not worship Hecate.");
			else if(deity == 97 && !(alignment == 4 || alignment == 5 || alignment == 6 || classes[0] == 10 || classes[0] == 11 || classes[classes.length/2] == 10 || classes[classes.length/2] == 11 || classes[classes.length-1] == 10 || classes[classes.length-1] == 11))
				error("Those who worship Hermes must be of neutral alignment and/or thieves.");
			else if(deity == 98 && alignment != 2)
				error("Those who worship Nike must be of lawful neutral alignment.");
			else if(deity == 99 && alignment != 8)
				error("Those who worship Pan must be of chaotic neutral alignment.");
			else if(deity == 101 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Prometheus must be of good alignment.");
			else if(deity == 102 && !(alignment == 4 || alignment == 5 || alignment ==6))
				error("Those who worship Tyche must be of neutral alignment.");
			else if(deity == 103 && alignment != 8)
				error("Those who worship Indra must be of chaotic neutral alignment.");
			else if(deity == 104 && alignment !=8)
				error("Those who worship Agni must be of chaotic neutral alignment.");
			else if(deity == 105 && alignment != 9)
				error("Those who worship Kali must be of chaotic neutral.");
			else if(deity == 106 && alignment != 4 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Karttikeya must be of chaotic good alignment and/or be warriors (fighter/fighter sub classes).");
			else if(deity == 107 && alignment != 4)
				error("Those who worship Lakshmi must be of chaotic good alignment.");
			else if(deity == 108 && !(alignment == 6 || classes[0] == 10 || classes[0] == 11 || classes[classes.length/2] == 10 || classes[classes.length/2] == 11 || classes[classes.length-1] == 10 || classes[classes.length-1] == 11))
				error("Those who worship Ratri must be of neutral evil alignment and/or be thieves.");
			else if(deity == 109 && alignment != 2)
				error("Those who worship Rudra must be of lawful neutral alignment.");
			else if(deity == 110 && alignment != 1)
				error("Those who worship Surya must be of lawful good alignment.");
			else if(deity == 111 && alignment != 5)
				error("Those who worship Tvashtri must be of true neutral alignment.");
			else if(deity == 112 && alignment != 4)
				error("Those who worship Ushas must be of neutral good alignment.");
			else if(deity == 113 && alignment !=2)
				error("Those who worship Varuna must be of lawful good alignment.");
			else if(deity == 114 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Vishnu must be of good alignment.");
			else if(deity == 115 && !(alignment == 4 || alignment == 5 || alignment == 6))
				error("Those who worship Yama must be of neutral alignment.");
			else if(deity == 116 && alignment != 1)
				error("Those who worship Amaterasu Omikami must be of lawful good alignment.");
			else if(deity == 119 && alignment != 1)
				error("Those who worship Ebisu must be of lawful good alignment.");
			else if(deity == 120 && (!(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Hachiman must be warriors (fighter/fighter sub classes).");
			else if(deity == 122 && !(alignment == 1 || alignment == 4 || alignment == 7 || alignment == 8))
				error("Those who worship Oh-Kuni-Nushi must be of either good or chaotic non evil alignment.");
			else if(deity == 123 && alignment != 5)
				error("Those who worship Raiden must be of true neutral alignment.");
			else if(deity == 124 && !(alignment == 7 || alignment == 8 || alignment == 9))
				error("Those who worship Susanowo must be of chaotic alignment.");
			else if(deity == 125 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Tsukiyomi must be of good alignment.");
			else if(deity == 126 && alignment != 2)
				error("Those who worship Aarth must be of lawful good alignment.");
			else if(deity == 128 && alignment != 6)
				error("Those who worship the gods of Lankhmar must be of neutral evil alignment.");
			else if(deity == 129 && alignment != 9)
				error("Those who worship the gods of Trouble must be of chaotic evil alignment.");
			else if(deity == 130 && alignment != 9)
				error("Those who worship Hate must be chaotic evil.");
			else if(deity == 131 && alignment != 1)
				error("Those who worship Issek of the Jug must be lawful good.");
			else if(deity == 132 && (!(alignment == 4 || alignment == 5 || alignment == 6) && !(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worship Kos must be of neutral alignment and/or warriors (fighter/fighter sub classes).");
			else if(deity == 133 && alignment != 9)
				error("Those who worship the Nehwon Earth god must be of chaotic evil alignment.");
			else if(deity == 134 && alignment != 9)
				error("Those who worship the Rat god must be of chaotic evil alignment.");
			else if(deity == 135 && (!(alignment == 4 || alignment == 5 || alignment == 6) && !(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7)))
				error("Those who worhsip the Red god must be of neutral alignment or be warriors.");
			else if(deity == 136 && alignment != 9)
				error("Those who worhsip the Spider god must be of chaotic evil alignment.");
			else if(deity == 137 && alignment != 9)
				error("Those who worship Tyaa must be of chaotic evil alignment.");
			else if(deity == 138 && alignment != 1)
				error("Those who worship Votishal must be of lawful good alignment.");
			else if(deity == 139 && alignment != 9)
				error("Those who worship Hruggek must be of chaotic evil alignment.");
			else if(deity == 140 && !(alignment == 4 || alignment == 7))
				error("Those who worship Skerrit must be of chaotic good or neutral good alignment.");
			else if(deity == 141 && alignment != 1)
				error("Those who worship Moradin must be of lawful good alignment.");
			else if(deity == 142 && alignment != 7)
				error("Those who worship Corelion Larethian must be of chaotic good alignment.");
			else if(deity == 143 && alignment != 7)
				error("Those who worship Deep Sashelas must be of chaotic good alignment.");
			else if(deity == 144 && alignment != 9)
				error("Those who worship Lolth must be of chaotic evil alignment.");
			else if(deity == 145 && alignment != 7)
				error("Those who worship Rillifane Rallathil must be of chaotic good alignment.");
			else if(deity == 146 && alignment != 6)
				error("Those who worship Surtur must be of lawful evil alignment.");
			else if(deity == 147 && alignment != 9)
				error("Those who worship Thrym must be of chaotic evil alignment.");
			else if(deity == 148 && alignment != 9)
				error("Those who worship Grolantor must be of chaotic evil alignment.");
			else if(deity == 149 && alignment != 5)
				error("Those who worship Skoraeus Stonebones must be of true neutral alignment.");
			else if(deity == 150)
				error("Those who worship Yeenoghu must be gnolls.");
			else if(deity == 151 && alignment != 1)
				error("Those who worship Gark Glittergold must be of lawful good alignment.");
			else if(deity == 152 && alignment != 3)
				error("Those who worship Maglubiyet must be of lawful evil alignment.");
			else if(deity == 153 && alignment != 1)
				error("Those who worship Yondalla must be of lawful good alignment.");
			else if(deity == 154)
				error("Those who worship Demogorgon must be Ixitxachitl.");
			else if(deity == 155 && alignment != 3)
				error("Those who worship Kurtulmak must be of lawful evil alignment.");
			else if(deity == 156 && alignment != 6)
				error("Those who worhsip Blibdoolpoolp must be of neutral evil alignment.");
			else if(deity == 157 && alignment != 5)
				error("Those who worship Semuanya must be of true neutral alignment.");
			else if(deity == 158 && alignment != 5)
				error("Those who worship Eadro must be of true neutral alignment.");
			else if(deity == 159 && alignment != 9)
				error("Those who worship Vaprak must be of chaotic evil alignment.");
			else if(deity == 160 && alignment != 3)
				error("Those who worship Gruumsh must be of lawful evil alignment.");
			else if(deity == 161 && alignment != 3)
				error("Those who worship Sekolah must be of lawful evil alignment.");
			else if(deity == 162 && alignment !=9)
				error("Those who worship Laogzed must be of chaotic evil alignment.");
			else if(deity == 163 && !(alignment == 1 || alignment == 2 || alignment == 4 || alignment == 5 || alignment == 7 || alignment == 8))
				error("Those who worship Odin must be of good or neutral alignment.");
			else if(deity == 164 && alignment != 8)
				error("Those who worship Aegir must be of chaotic neutral alignment.");
			else if(deity == 165 && alignment != 4)
				error("Those who worship Baldur must be of neutral good alignment.");
			else if(deity == 166 && alignment != 4)
				error("Those who worship Bragi must be of neutral good alignment.");
			else if(deity == 167 && alignment != 1)
				error("Those who worship Forseti must be of lawful good alignment.");
			else if(deity == 168 && alignment != 4)
				error("Those who worship Frey must be of neutral good alignment.");
			else if(deity == 169 && alignment != 4)
				error("Those who worship Freya must be of neutral good alignment.");
			else if(deity == 170 && alignment != 2)
				error("Those who worship Frigga must be of lawful neutral alignment.");
			else if(deity == 171 && (alignment != 1 && !(classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8)))
				error("Those who worship Heimdall must be of lawful good alignment or be magic-users.");
			else if(deity == 173 && alignment != 7)
				error("Those who worship Idun must be of chaotic good alignment.");
			else if(deity == 174 && alignment !=9)
				error("Those who worship Loki must be of chaotic evil alignment.");
			else if(deity == 175 && alignment != 7)
				error("Those who worship Modi must be of chaotic good alignment.");
			else if(deity == 177 && alignment !=7)
				error("Those who worship Sif must be of chaotic good alignment.");
			else if(deity == 179 && !(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7))
				error("Those who worship Tyr must be warriors.");
			else if(deity == 180 && alignment != 8)
				error("Those who worship Uller must be of chaotic neutral alignment.");
			else if(deity == 181 && alignment != 7)
				error("Those who worship Vidar must be of chaotic good alignment.");
			else if(deity == 182 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Enlil must be of good alignment.");
			else if(deity == 183 && alignment != 2)
				error("Those who worship Enki must be of lawful neutral alignment.");
			else if(deity == 184 && !(classes[0] == 1 || classes[0] == 2 || classes[0] ==5 || classes[0] == 6 || classes[0] == 7) && !(classes[classes.length/2] == 1 || classes[classes.length/2] == 2 || classes[classes.length/2] == 5 || classes[classes.length/2] == 6 || classes[classes.length/2] == 7) && !(classes[classes.length-1] == 1 || classes[classes.length-1] == 2 || classes[classes.length-1] == 5 || classes[classes.length-1] == 6 || classes[classes.length-1] == 7))
				error("Those who worship Inanna must be warriors or lovers.");
			else if(deity == 185 && alignment != 5)
				error("Those who worship Ki must be of true neutral alignment.");
			else if(deity == 186 && alignment != 7)
				error("Those who worship Nanna-Sin must be of chaotic good alignment.");
			else if(deity == 187 && alignment !=5)
				error("Those who worship Nin-Hursag must be of true neutral alignment.");
			else if(deity == 188 && alignment != 7)
				error("Those who worship Utu must be of chaotic good alignment.");
			else if(deity == 189 && !(alignment == 1 || alignment == 2 || alignment == 4 || alignment == 5 || alignment == 7 || alignment == 8))
				error("Those who worship Azura cannot be of evil alignment.");
			else if(deity == 190 && alignment <= 3)
				error("Those who worship Boethiah cannot be of lawful alignment.");
			else if(deity == 191 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Clavicus Vile must be of evil alignment.");
			else if(deity == 194 && alignment > 3)
				error("Those who worship Jyggalag must be of lawful alignment.");
			else if(deity == 195 && alignment != 9)
				error("Those who worship Malacath must be of chaotic evil alignment.");
			else if(deity == 196 && alignment != 9)
				error("Those who worship Mehrunes Dagon must be of chaotic evil alignment.");
			else if(deity == 197 && !(alignment == 1 || alignment == 4 || alignment == 7))
				error("Those who worship Meridia must be of good alignment.");
			else if(deity == 198 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Molag Bal must be of evil alignment.");
			else if(deity == 199 && alignment < 7)
				error("Those who worship Mephala must be of chaotic alignment.");
			else if(deity == 201 && alignment != 9)
				error("Those who worship Nemira must be of chaotic evil alignment.");
			else if(deity == 202 && !(alignment == 3 || alignment == 6 || alignment == 9))
				error("Those who worship Peryite must be of evil alignment.");
			else if(deity == 203 && alignment < 7)
				error("Those who worship Sanguine must be of chaotic alignment.");
			else if(deity == 204 && alignment < 7)
				error("Those who worship Sheogorath must be of chaotic alignment.");
			else if(deity == 205 && !(alignment == 2 || alignment == 3 || alignment == 5 || alignment == 6 || alignment == 8 || alignment == 9))
				error("Those who whorship Vaermina cannot be of good alignment.");
				flag8 = false;
		}
		return deity;
	}
	/**
	 * Used in main to determine character's name
	 * @param keyb Keyboard scanner
	 * @return String representing character's name
	 */
	public static String nameSelection(Scanner keyb){ //TODO Name method
		boolean flag9 = true;
		String name = null;
		
		while(flag9){
			print("Please enter your character's name:");
			keyb.nextLine();
			name = keyb.nextLine();
		
			println(name + " is your character's name. Are you sure?(y/n)");
			if(keyb.next().equalsIgnoreCase("y")){
				flag9 = false;
			}
		}
		return name;
	}
	/**
	 * Used in main, this method calculates the characters secondary skill as per the rules in the DMG p.12
	 * @return integer representing the secondary skill(s) of the character
	 */
	public static double secondarySkillsCalculator(){ //TODO Secondary skill method
		double secondarySkill = (int)(Math.random()*100); //Roll for secondary skill
		double secondarySkill2 = 0;
		if(secondarySkill < 68) //If a regular skill, return it.
			return secondarySkill;
		else if(secondarySkill > 67 && secondarySkill < 85) //If no skill of measurable worth, return 0
			return 0;
		else{
			while(secondarySkill2 > 84 || secondarySkill > 84){ //If "Roll Twice", keep rolling until neither option is "Roll Twice"
				secondarySkill = (int)(Math.random()*100);
				secondarySkill2= (int)(Math.random()*100);
			}
			return secondarySkill + (secondarySkill2/100); //Add the secondary skills in such a way that the numbers before the period are one skill, after are another
		}
	}
	/**
	 * Used in main, this method calculates the experience needed in all classes for the character
	 * @param classes the integer array representing the classes
	 * @return integer array representing the value of experience needed in each class
	 */
	public static int[] experienceNeededCalculator(int[] classes){ //TODO Experience needed method
		int[] expNeeded = new int[classes.length];
		for(int i=0;i<classes.length;i++)
			if(classes[i] == 1)
				expNeeded[i] = 2500;
			else if(classes[i] == 2)
				expNeeded[i] = 2750;
			else if(classes[i] == 3)
				expNeeded[i] = 1500;
			else if(classes[i] == 4)
				expNeeded[i] = 2000;
			else if(classes[i] == 5)
				expNeeded[i] = 2000;
			else if(classes[i] == 6)
				expNeeded[i] = 6000;
			else if(classes[i] == 7)
				expNeeded[i] = 2250;
			else if(classes[i] == 8)
				expNeeded[i] = 2500;
			else if(classes[i] == 9)
				expNeeded[i] = 2250;
			else if(classes[i] == 10 || classes[i] == 11)
				expNeeded[i] = 1250;
			else if(classes[i] == 12)
				expNeeded[i] = 1500;
			else if(classes[i] == 13)
				expNeeded[i] = 2250;
			else if(classes[i] == 14)
				expNeeded[i] = 100000; //Unknown value for Bard class
		return expNeeded;
	}
	/**
	 * Used in main, this method calculates the characters saving throws based on their class selection.
	 * @param classes, integer array representing the chosen classes
	 * @return integer array representing the best combination of saving throws for the character
	 */
	public static int[] savingThrowCalculator(int[] classes){ //TODO Saving throw method
		int[] savingThrows = new int[5];
		
		if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || //Paralyzation, Poison
				classes[classes.length-1] == 4)																										// or Death Magic
			savingThrows[0] = 10;
		else if(classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || classes[classes.length/2] == 11 ||
				classes[classes.length-1] == 11 ||classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12 || classes[0] == 13 || 
				classes[classes.length/2] == 13 || classes[classes.length-1] == 13 || classes[0] == 14 || classes[classes.length/2] == 14 || classes[classes.length-1] == 14)
			savingThrows[0] = 13;
		else if(classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8 || classes[0] == 9
				|| classes[classes.length/2] == 9 || classes[classes.length-1] == 9)
			savingThrows[0] = 14;
		else
			savingThrows[0] = 16;
		if(classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || classes[classes.length/2] == 11 || //Petrification/Polymorph
				classes[classes.length-1] == 11 ||classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12 || classes[0] == 13 || 
				classes[classes.length/2] == 13 || classes[classes.length-1] == 13 || classes[0] == 14 || classes[classes.length/2] == 14 || classes[classes.length-1] == 14)
			savingThrows[1] = 12;
		else if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || 
				classes[classes.length-1] == 4 || classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8 || classes[0] == 9 || 
				classes[classes.length/2] == 9 || classes[classes.length-1] == 9)
			savingThrows[1] = 13;
		else
			savingThrows[1] = 17;
		if(classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8 || classes[0] == 9 //Rod, Staff, or Wand
				|| classes[classes.length/2] == 9 || classes[classes.length-1] == 9)
			savingThrows[2] = 11;
		else if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || 
					classes[classes.length-1] == 4 || classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || 
					classes[classes.length/2] == 11 || classes[classes.length-1] == 11 ||classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12 
					|| classes[0] == 13 || classes[classes.length/2] == 13 || classes[classes.length-1] == 13 || classes[0] == 14 || classes[classes.length/2] == 14 || 
					classes[classes.length-1] == 14)
			savingThrows[2] = 14;
		else
			savingThrows[2] = 18;
		if(classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8 || classes[0] == 9 //Breath Weapon
				|| classes[classes.length/2] == 9 || classes[classes.length-1] == 9)
			savingThrows[3] = 15;
		else if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || 
					classes[classes.length-1] == 4 || classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || 
					classes[classes.length/2] == 11 || classes[classes.length-1] == 11 ||classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12 
					|| classes[0] == 13 || classes[classes.length/2] == 13 || classes[classes.length-1] == 13 || classes[0] == 14 || classes[classes.length/2] == 14 || 
					classes[classes.length-1] == 14)
			savingThrows[3] = 16;
		else
			savingThrows[3] = 20;
		if(classes[0] == 8 || classes[classes.length/2] == 8 || classes[classes.length-1] == 8 || classes[0] == 9 //Spell
				|| classes[classes.length/2] == 9 || classes[classes.length-1] == 9)
			savingThrows[4] = 12;
		else if(classes[0] == 3 || classes[classes.length/2] == 3 || classes[classes.length-1] == 3 || classes[0] == 4 || classes[classes.length/2] == 4 || 
					classes[classes.length-1] == 4 || classes[0] == 10 || classes[classes.length/2] == 10 || classes[classes.length-1] == 10 || classes[0] == 11 || 
					classes[classes.length/2] == 11 || classes[classes.length-1] == 11 ||classes[0] == 12 || classes[classes.length/2] == 12 || classes[classes.length-1] == 12 
					|| classes[0] == 13 || classes[classes.length/2] == 13 || classes[classes.length-1] == 13 || classes[0] == 14 || classes[classes.length/2] == 14 || 
					classes[classes.length-1] == 14)
			savingThrows[4] = 15;
		else
			savingThrows[4] = 19;
		
		return savingThrows;
	}
	/**
	 * Used in main, this method modifies ability scores to correspond with race modifications, race max/min, gender max/min, and class modifications
	 * @param classes, the integer array representing the classes of the character
	 * @param chosenStats, the ability scores chosen by the user
	 * @param race, the race of the character
	 * @param gender, the gender of the character
	 * @return integer array representing the final ability scores of the player character
	 */
	public static double[] abilityScoreAdjustment(int[] classes, int[] chosenStats, int race, int gender){ //TODO Ability score adjustment method
		if(race == 3){
			chosenStats[0] += 1;
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[6] += 2;
		}
		else if(race == 4){
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[6] += 2;
		}
		else if(race == 5){
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[0] += 2;
			chosenStats[6] += 2;
		}
		else if(race == 6){
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[6] += 2;
		}
		else if(race == 7){
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[6] += 2;
		}
		else if(race == 8){
			chosenStats[3] += 1;
			chosenStats[4] += -1;
			chosenStats[0] += 1;
			chosenStats[1] += -1;
			chosenStats[6] += 1;
		}
		else if(race == 9 || race == 10 || race == 11){
			chosenStats[4] += 1;
			chosenStats[5] += -1;
			chosenStats[6] += -1;
		}
		else if(race == 12 || race == 13){
			chosenStats[6] += -1;
		}
		else if(race == 14){
			chosenStats[6] += 1;
		}
		else if(race == 15){
			chosenStats[3] += 1;
			chosenStats[0] += -1;
		}
		else if(race == 16){
			chosenStats[0] += 1;
			chosenStats[4] += 1;
			chosenStats[5] += -2;
			chosenStats[6] += -3;
		}
			
		if(gender == 1 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8)){
			if(chosenStats[0] < 3)
				chosenStats[0] = 3;
			else if(chosenStats[0] > 18)
				chosenStats[0] = 18;
			if(chosenStats[1] < 8)
				chosenStats[1] = 8;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 7)
				chosenStats[3] = 7;
			else if(chosenStats[3] > 19)
				chosenStats[3] = 19;
			if(chosenStats[4] < 6)
				chosenStats[4] = 6;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 8)
				chosenStats[5] = 8;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 2 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8)){
			if(chosenStats[0] < 3)
				chosenStats[0] = 3;
			else if(chosenStats[0] > 16)
				chosenStats[0] = 16;
			if(chosenStats[1] < 8)
				chosenStats[1] = 8;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 7)
				chosenStats[3] = 7;
			else if(chosenStats[3] > 19)
				chosenStats[3] = 19;
			if(chosenStats[4] < 6)
				chosenStats[4] = 6;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 8)
				chosenStats[5] = 8;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 1 && (race == 9 || race == 10 || race == 11)){
			if(chosenStats[0] < 8)
				chosenStats[0] = 8;
			else if(chosenStats[0] > 18)
				chosenStats[0] = 18;
			if(chosenStats[1] < 3)
				chosenStats[1] = 3;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 3)
				chosenStats[3] = 3;
			else if(chosenStats[3] > 17)
				chosenStats[3] = 17;
			if(chosenStats[4] < 12)
				chosenStats[4] = 12;
			else if(chosenStats[4] > 19)
				chosenStats[4] = 19;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 16)
				chosenStats[5] = 16;
		}
		else if(gender == 2 && (race == 9 || race == 10 || race == 11)){
			if(chosenStats[0] < 8)
				chosenStats[0] = 8;
			else if(chosenStats[0] > 17)
				chosenStats[0] = 17;
			if(chosenStats[1] < 3)
				chosenStats[1] = 3;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 3)
				chosenStats[3] = 3;
			else if(chosenStats[3] > 17)
				chosenStats[3] = 17;
			if(chosenStats[4] < 12)
				chosenStats[4] = 12;
			else if(chosenStats[4] > 19)
				chosenStats[4] = 19;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 16)
				chosenStats[5] = 16;
		}
		else if(gender == 1 && (race == 12 || race == 13)){
			if(chosenStats[0] < 6)
				chosenStats[0] = 6;
			else if(chosenStats[0] > 18)
				chosenStats[0] = 18;
			if(chosenStats[1] < 7)
				chosenStats[1] = 7;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 3)
				chosenStats[3] = 3;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 8)
				chosenStats[4] = 8;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 2 && (race == 12 || race == 13)){
			if(chosenStats[0] < 6)
				chosenStats[0] = 6;
			else if(chosenStats[0] > 15)
				chosenStats[0] = 15;
			if(chosenStats[1] < 7)
				chosenStats[1] = 7;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 3)
				chosenStats[3] = 3;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 8)
				chosenStats[4] = 8;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 1 && (race == 14)){
			if(chosenStats[0] < 3)
				chosenStats[0] = 3;
			else if(chosenStats[0] > 18)
				chosenStats[0] = 18;
			if(chosenStats[1] < 4)
				chosenStats[1] = 4;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 6)
				chosenStats[3] = 6;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 6)
				chosenStats[4] = 6;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 2 && (race == 14)){
			if(chosenStats[0] < 3)
				chosenStats[0] = 3;
			else if(chosenStats[0] > 17)
				chosenStats[0] = 17;
			if(chosenStats[1] < 4)
				chosenStats[1] = 4;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 18)
				chosenStats[2] = 18;
			if(chosenStats[3] < 6)
				chosenStats[3] = 6;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 6)
				chosenStats[4] = 6;
			else if(chosenStats[4] > 18)
				chosenStats[4] = 18;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 1 && (race == 15)){
			if(chosenStats[0] < 6)
				chosenStats[0] = 6;
			else if(chosenStats[0] > 17)
				chosenStats[0] = 17;
			if(chosenStats[1] < 6)
				chosenStats[1] = 6;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 17)
				chosenStats[2] = 17;
			if(chosenStats[3] < 8)
				chosenStats[3] = 8;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 10)
				chosenStats[4] = 10;
			else if(chosenStats[4] > 19)
				chosenStats[4] = 19;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(gender == 2 && (race == 15)){
			if(chosenStats[0] < 6)
				chosenStats[0] = 6;
			else if(chosenStats[0] > 14)
				chosenStats[0] = 14;
			if(chosenStats[1] < 6)
				chosenStats[1] = 6;
			else if(chosenStats[1] > 18)
				chosenStats[1] = 18;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 17)
				chosenStats[2] = 17;
			if(chosenStats[3] < 8)
				chosenStats[3] = 8;
			else if(chosenStats[3] > 18)
				chosenStats[3] = 18;
			if(chosenStats[4] < 10)
				chosenStats[4] = 10;
			else if(chosenStats[4] > 19)
				chosenStats[4] = 19;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 18)
				chosenStats[5] = 18;
		}
		else if(race == 16){
			if(chosenStats[0] < 6)
				chosenStats[0] = 6;
			else if(chosenStats[0] > 18)
				chosenStats[0] = 18;
			if(chosenStats[1] < 3)
				chosenStats[1] = 3;
			else if(chosenStats[1] > 17)
				chosenStats[1] = 17;
			if(chosenStats[2] < 3)
				chosenStats[2] = 3;
			else if(chosenStats[2] > 14)
				chosenStats[2] = 14;
			if(chosenStats[3] < 3)
				chosenStats[3] = 3;
			else if(chosenStats[3] > 17)
				chosenStats[3] = 17;
			if(chosenStats[4] < 13)
				chosenStats[4] = 13;
			else if(chosenStats[4] > 19)
				chosenStats[4] = 19;
			if(chosenStats[5] < 3)
				chosenStats[5] = 3;
			else if(chosenStats[5] > 12)
				chosenStats[5] = 12;
		}
		double[] stats = new double[7];
		double strength = chosenStats[0];
		if(strength == 18 && (classes[0] == 1 || classes[classes.length/2] == 1 || classes[classes.length-1] == 1 || classes[0] == 2 || classes[classes.length/2] == 2 || 
				classes[classes.length-1] == 2 || classes[0] == 5 || classes[classes.length/2] == 5 || classes[classes.length-1] == 5 || classes[0] == 6 || 
				classes[classes.length/2] == 6 || classes[classes.length-1] == 6 || classes[0] == 7 || classes[classes.length/2] == 7 || classes[classes.length-1] == 7))
			strength += Math.random() + 0.01;
		
		stats[0] = strength;
		for(int i=0; i<6; i++)
			stats[i+1] = chosenStats[i+1];
		
		return stats;
	}
	/**
	 * Used in main, this method determine's the characters base bonuses based off the characters ability scores
	 * @param stats, the ability scores of the character
	 * @return integer array representing the base bonuses of the character
	 */
	public static int[] baseBonusesCalculator(double[] stats){ //TODO Base bonuses method
		int[] baseBonuses = new int[9];
		
		if(stats[0] < 16);  	//Strength bonuses
		else if(stats[0] < 17){
			baseBonuses[1] = 1;
		}
		else if(stats[0] < 18){
			baseBonuses[0] = 1;
			baseBonuses[1] = 1;
		}
		else if(stats[0] == 18){
			baseBonuses[0] = 1;
			baseBonuses[1] = 2;
		}
		else if(stats[0] < 18.51){
			baseBonuses[0] = 1;
			baseBonuses[1] = 3;
		}
		else if(stats[0] < 18.76){
			baseBonuses[0] = 2;
			baseBonuses[1] = 3;
		}
		else if(stats[0] < 18.91){
			baseBonuses[0] = 2;
			baseBonuses[1] = 4;
		}
		else if(stats[0] < 19){
			baseBonuses[0] = 2;
			baseBonuses[1] = 5;
		}
		else{ 
			baseBonuses[0] = 3;
			baseBonuses[1] = 6;
		}
		
		if(stats[2] < 15);		//Magic adjustment (Wisdom)
		else if(stats[2] == 15)
			baseBonuses[2] = 1;
		else if(stats[2] == 16)
			baseBonuses[2] = 2;
		else if(stats[2] == 17)
			baseBonuses[2] = 3;
		else if(stats[2] == 18)
			baseBonuses[2] = 4;
		else
			baseBonuses[2] = 5;
		
		if(stats[3] < 15);		//Dexterity bonuses
		else if(stats[3] == 15){
			baseBonuses[4] = -1;
		}
		else if(stats[3] == 16){
			baseBonuses[3] = 1;
			baseBonuses[4] = -2;
		}
		else if(stats[3] == 17){
			baseBonuses[3] = 2;
			baseBonuses[4] = -3;
		}
		else{
			baseBonuses[3] = 3;
			baseBonuses[4] = -4;
		}
		
		if(stats[4] < 15);		//Constitution bonus to hit points
		else if(stats[4] == 15){
			baseBonuses[5] = 1;
			baseBonuses[6] = 1;
		}
		else if(stats[4] == 16){
			baseBonuses[5] = 2;
			baseBonuses[6] = 2;
		}
		else if(stats[4] == 17){
			baseBonuses[5] = 2;
			baseBonuses[6] = 3;
		}
		else if(stats[4] == 18){
			baseBonuses[5] = 2;
			baseBonuses[6] = 4;
		}
		else{
			baseBonuses[5] = 2;
			baseBonuses[6] = 5;
		}
		
		if(stats[5] < 13);		//Reaction adjustment (Charisma)
		else if(stats[5] == 13)
			baseBonuses[7] = 5;
		else if(stats[5] == 14)
			baseBonuses[7] = 10;
		else if(stats[5] == 15)
			baseBonuses[7] = 15;
		else if(stats[5] == 16)
			baseBonuses[7] = 25;
		else if(stats[5] == 17)
			baseBonuses[7] = 30;
		else if(stats[5] == 18)
			baseBonuses[7] = 35;
		else
			baseBonuses[7] = 40;
		
		if(stats[6] < 14);		//Reaction adjustment (Comeliness)
		else if(stats[6] == 14)
			baseBonuses[8] = 14;
		else if(stats[6] == 15)
			baseBonuses[8] = 15;
		else if(stats[6] == 16)
			baseBonuses[8] = 16;
		else if(stats[6] == 17)
			baseBonuses[8] = 17;
		else if(stats[6] == 18)
			baseBonuses[8] = 27;
		else if(stats[6] == 19)
			baseBonuses[8] = 29;
		else
			baseBonuses[8] = 30;
			
		return baseBonuses;
	}
	/**
	 * Used in main, this method calculates the character's height based on their race and gender
	 * @param race, the race of the character
	 * @param gender, the gender of the character
	 * @return integer representing the height (in inches) of the character
	 */
	public static int heightCalculator (int race, int gender){ //TODO height method
		int height;
		if(gender == 1 && race == 1)
			height = 84 + ((int)(Math.random() * 5) -2);
		else if(gender == 2 && race == 1)
			height = 72 + ((int)(Math.random() * 5) -2);
		else if(gender == 1 && race == 2)
			height = 72 + ((int)(Math.random() * 25) -12);
		else if(gender == 2 && race == 2)
			height = 66 + ((int)(Math.random() * 13) -6);
		else if(gender == 1 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8))
			height = 60 + ((int)(Math.random() * 13) -6);
		else if(gender == 2 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8))
			height = 54 + ((int)(Math.random() * 13) -6);
		else if(gender == 1 && (race == 9 || race == 10 || race == 11))
			height = 48 + ((int)(Math.random() * 13) -6);
		else if(gender == 2 && (race == 9 || race == 10 || race == 11))
			height = 46 + ((int)(Math.random() * 9) -4);
		else if(gender == 1 && (race == 12 || race == 13))
			height = 42 + ((int)(Math.random() * 7) -3);
		else if(gender == 2 && (race == 12 || race == 13))
			height = 39 + ((int)(Math.random() * 7) -3);
		else if(gender == 1 && race == 14)
			height = 66 + ((int)(Math.random() * 13) -6);
		else if(gender == 2 && race == 14)
			height = 62 + ((int)(Math.random() * 13) -6);
		else if(gender == 1 && race == 15)
			height = 36 + ((int)(Math.random() * 9) -4);
		else if(gender == 2 && race == 15)
			height = 33 + ((int)(Math.random() * 7) -3);
		else if(gender == 1 && race == 16)
			height = 66 + ((int)(Math.random() * 9) -4);
		else
			height = 62 + ((int)(Math.random() * 7) -3);
		
		return height;
	}
	/**
	 * Used in main, this method calculates the character's height based on their race and gender
	 * @param race, the race of the character
	 * @param gender, the gender of the character
	 * @return integer representing the height (in inches) of the character
	 */
	public static int weightCalculator (int race, int gender){ //TODO weight method
		int weight;
		if(gender == 1 && race == 1)
			weight = 250 + ((int)(Math.random() * 101) -50);
		else if(gender == 2 && race == 1)
			weight = 175 + ((int)(Math.random() * 101) -50);
		else if(gender == 1 && race == 2)
			weight = 175 + ((int)(Math.random() * 81) -40);
		else if(gender == 2 && race == 2)
			weight = 130 + ((int)(Math.random() * 81) -40);
		else if(gender == 1 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8))
			weight = 100 + ((int)(Math.random() * 31) -15);
		else if(gender == 2 && (race == 3 || race == 4 || race == 5 || race == 6 || race == 7 || race == 8))
			weight = 80 + ((int)(Math.random() * 21) -10);
		else if(gender == 1 && (race == 9 || race == 10 || race == 11))
			weight = 150 + ((int)(Math.random() * 41) -20);
		else if(gender == 2 && (race == 9 || race == 10 || race == 11))
			weight = 120 + ((int)(Math.random() * 41) -20);
		else if(gender == 1 && (race == 12 || race == 13))
			weight = 80 + ((int)(Math.random() * 17) -8);
		else if(gender == 2 && (race == 12 || race == 13))
			weight = 75 + ((int)(Math.random() * 17) -8);
		else if(gender == 1 && race == 14)
			weight = 130 + ((int)(Math.random() * 41) -20);
		else if(gender == 2 && race == 14)
			weight = 100 + ((int)(Math.random() * 31) -15);
		else if(gender == 1 && race == 15)
			weight = 60 + ((int)(Math.random() * 21) -10);
		else if(gender == 2 && race == 15)
			weight = 50 + ((int)(Math.random() * 17) -8);
		else if(gender == 1 && race == 16)
			weight = 150 + ((int)(Math.random() * 61) -30);
		else
			weight = 120 + ((int)(Math.random() * 51) -25);
		
		return weight;
	}
	/**
	 * Used in main, this method calculates the character's birth place based on their race
	 * @param race, the race of the character
	 * @return String representing the country of birth of the character
	 */
	public static String birthPlaceCalculator(int race){ //TODO birth place method
		if(race == 1){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Corusk Mts.";
			else if(randomPlace == 2)
				return "Griff Mts.";
			else if(randomPlace == 3)
				return "Plains of the Paynims";
			else
				return "The Great Kingdom";
		}
		else if(race == 2){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Great Kingdom";
			else if(randomPlace == 2)
				return "Onnwal";
			else if(randomPlace == 3)
				return "The Scarlet Brotherhood";
			else 
				return "Theocracy of the Pale";
		}
		else if(race == 3){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Ket";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else 
				return "North Province";
		}
		else if(race == 4){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Ket";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else 
				return "North Province";
		}
		else if(race == 5){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Ket";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else 
				return "North Province";
		}
		else if(race == 6){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Underdark, city of Erelhei-Cinlu";
			else if(randomPlace == 2)
				return "Underdark, city of Undrek'Thoz";
			else if(randomPlace == 3)
				return "Underdark, city of Menzoberranzan";
			else 
				return "Underdark, city of Karsoluthiyl";
		}
		else if(race == 7){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Ket";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else 
				return "North Province";
		}
		else if(race == 8){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Ket";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else 
				return "North Province";
		}
		else if(race == 9){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Gloriole Mts.";
			else if(randomPlace == 2)
				return "Crystalmist Mts.";
			else if(randomPlace == 3)
				return "Yatil Mts.";
			else 
				return "Lortmil Mts.";
		}
		else if(race == 10){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Gloriole Mts.";
			else if(randomPlace == 2)
				return "Crystalmist Mts.";
			else if(randomPlace == 3)
				return "Yatil Mts.";
			else 
				return "Lortmil Mts.";
		}
		else if(race == 11){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Underdark, city of Gracklstugh";
			else if(randomPlace == 2)
				return "Underdark, city of Dunspeirrin";
			else if(randomPlace == 3)
				return "Underdark, city of Drik Hargunen";
			else 
				return "Underdark, city of Sshamath";
		}
		else if(race == 12){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Underdark, city of Blingdenstone";
			else if(randomPlace == 2)
				return "Underdark, city of Golothaer";
			else if(randomPlace == 3)
				return "Underdark, city of Sschindylryn";
			else 
				return "Underdark, city of Ust Natha";
		}
		else if(race == 13){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Ratik";
			else if(randomPlace == 2)
				return "The Yeomaney";
			else if(randomPlace == 3)
				return "Ket";
			else
				return "Kingdom of Nyrond";
		}
		else if(race == 14){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "The Lordship of Isles";
			else if(randomPlace == 2)
				return "Onnwal";
			else if(randomPlace == 3)
				return "The Spindrift Isles";
			else
				return "Theocracy of the Pale";
		}
		else if(race == 15){
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Duchy of Ten";
			else if(randomPlace == 2)
				return "Veluna";
			else if(randomPlace == 3)
				return "See of Medegia";
			else
				return "Ket";
		}
		else{
			int randomPlace =(int)(Math.random()*4+1);
			if(randomPlace == 1)
				return "Bone March";
			else if(randomPlace == 2)
				return "The Great Kingdom";
			else if(randomPlace == 3)
				return "The Scarlet Brotherhood";
			else
				return "South Province";
		}
	}
	/**
	 * returns the index at which the number is found in an array.
	 * @param choice, the number the method searches for.
	 * @param options, the array the method searches in.
	 * @return the index at which the number is found.
	 */
	public static int isFound(int choice, int[] options){ //TODO utility methods
		
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
