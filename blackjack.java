package blackjack;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class blackjack {
	public static int debtAmount = 250;
	public static int totalMoneyEarned = 0;
	public static int debtPayed = 0;
	public static int totalDebtPayed = 0;
	public static int round = 1;
	public static int ante = 1;
	public static int interestAmount = 0;
	public static double interestRate = 0.07;
	public static double chipMultiplier = 1;
	public static long chipCount = 500;
	public static int startGame = 0;
	public static int blackjackChance = 0;
	public static int additionalChips = 0;
	public static int needAadditionalCards = 0;
	public static boolean evilDeal = false;
	public static boolean luckyCatCharm = false;
	public static File score = new File("score.txt");
	public static Random random = new Random();
	public static Scanner input = new Scanner(System.in);
	
	public static void finalStats() { //prints out the final ante and total chips earned, then asks if you want to save the score
		if (round == 4) {
			System.out.println("You ran out of time to pay your debts");
		}
		else {
			System.out.println("You ran out of money to pay your debts");
		}
		System.out.println("Final Ante: " + ante);
		System.out.println("Chips Earned: " + totalMoneyEarned);
		System.out.println("");
		printScore();
		System.out.print("Do you want to Save? (1: Yes)");
		int saveChoice = 0;
		try {
			saveChoice = input.nextInt();
		} catch(InputMismatchException e) {
			saveChoice = 2;
			input.next();
		}
		if (saveChoice == 1) {
			saveScore();
		}
	}
	
	public static void resetStats() { //resets the stats when you restart the game
		debtAmount = 250;
		totalMoneyEarned = 0;
		debtPayed = 0;
		totalDebtPayed = 0;
		round = 1;
		ante = 1;
		chipCount = 500;
		blackjackChance = 0;
		additionalChips = 0;
		needAadditionalCards = 0;
		chipMultiplier = 1;
		evilDeal = false;
		luckyCatCharm = false;
	}
	
	public static int sum(ArrayList<Integer> cards) { //meant to be used to add all of the cards together into a sum for easy checking
		int sum = 0;
		for (int card: cards) { // goes through all of the cards given the list and adds all of them up and returns the sum
			sum += card;
		}
		return sum;
	}
	
	public static void printScore() { //Used to print the scores saved to a txt file
		try (Scanner scoreScanner = new Scanner(score)) { // similar to with open(file) as data in python
		      while (scoreScanner.hasNextLine()) {//goes through the lines in the txt file and print them
		          String data = scoreScanner.nextLine();
		          System.out.println(data);
		      }
		} catch (FileNotFoundException e1) {
			System.out.println("An error occurred.");
			e1.printStackTrace();
		}
	}
	
	public static void saveScore() { //Used to save new scores to the txt file
		try (FileWriter scoreWriter = new FileWriter("score.txt")) {//creates a new FileWriter object	
			scoreWriter.write("Highest Ante: " + ante + "\n");//replaces score in the file
			scoreWriter.write("Most Chips Earned: " + totalMoneyEarned);//replaces score in the file
		} catch (IOException e) {
			System.out.println("An error occurred in saving data.");
			e.printStackTrace();
		}
	}
	
	public static void shop() {
		System.out.println("1 Chance to instantly blackjack(" + blackjackChance + "%) (Max 33%) Cost: 1000 Chips");
		System.out.println("2 Add Chips to your bet for Free(" + additionalChips + ") Cost: 100 Chips");
		System.out.println("3 Increase the required card sum by 1 (+" + needAadditionalCards + ") Cost: 1000 Chips");
		System.out.println("4 Multiplier for Chips after Winning ("+chipMultiplier+") (Max 4.0) Cost: 100 Chips");
		System.out.println("5 Evil Deal😈 (Only Buy Once) Cost: 10000 Chips");
		System.out.println("6 Increase Interest Rate by 1% ("+ interestRate +") Cost: 1000 Chips");
		System.out.println("7 Lucky Cat Charm (If you roll a 7, get your interest) Cost: 10000 Chips");
		System.out.println("Number in Shop to Buy, any other number to leave");
		int shopChoice = 1000;
		try {
		shopChoice = input.nextInt();
		} catch(InputMismatchException e) {
			shopChoice = 1000;
			input.next();
		}
		if (shopChoice == 1) { //random chance to pull a card that instantly blackjack, max percentage is 33
			if (chipCount > 1000 && blackjackChance < 33) {
				chipCount -= 1000;
				blackjackChance += 11;
			}
			else System.out.println("Invalidated Purchase");
		}
		else if (shopChoice == 2) { //adds free chips to bet
			if (chipCount > 100) {
				chipCount -= 100;
				additionalChips += 500;
			}
			else System.out.println("Broke");
		}
		else if (shopChoice == 3) { // increase the sum of cards needed to get to
			if (chipCount > 1000) {
				chipCount -= 1000;
				needAadditionalCards += 1;
			}
			else System.out.println("Broke");
		}
		else if (shopChoice == 4) { // self explanatory
			if (chipCount > 100 && chipMultiplier < 3) {
				chipCount -= 100;
				chipMultiplier += 0.1;
			}
			else System.out.println("Invalidated Purchase");
		}
		else if (shopChoice == 5) {// 5 times the money you earn from blackjack, but you instantly lose every chip if you lose
			if (chipCount > 10000 && !evilDeal) {
				chipCount -= 10000;
				evilDeal = true;
			}
			else if (evilDeal) System.out.println("You can only sell your soul once :)");
			else System.out.println("Broke");
		}
		else if (shopChoice == 6) { // increases the interest rate
			if (chipCount > 100) {
				chipCount -= 100;
				interestRate += 0.01;
			}
			else System.out.println("Broke");
		}
		else if (shopChoice == 7) {// gain money equal to your interest when you get a sum divisible by 7, taken from cloverpit
			if (chipCount > 10000 && !luckyCatCharm) {
				chipCount -= 10000;
				luckyCatCharm = true;
			}
			else if (luckyCatCharm) System.out.println("meow (You already own this)");
			else System.out.println("Broke");
		}
	}
	
public static void blackJack() {
	boolean gameOver = false;
	ArrayList<Integer> playerHand = new ArrayList<>(Arrays.asList(random.nextInt(10)+1,random.nextInt(10)+1)); //Gets two random ints and puts them in an ArrayList
	ArrayList<Integer> dealerHand = new ArrayList<>(Arrays.asList(random.nextInt(10)+1,random.nextInt(10)+1));
	if (sum(playerHand) == 22+needAadditionalCards) { //If the player got 11's, turn one into a one
			playerHand.set(0,1);
	}
	if (sum(dealerHand) == 22+needAadditionalCards) {
		playerHand.set(random.nextInt(0,1),1);
	}
	int betting = 0;
	int betAmount = 0;
	while (betting == 0) {
		System.out.print("How many Chips do you want to bet?:");
		try {
			betAmount = input.nextInt();
			if (betAmount > chipCount) {
				System.out.println("You are betting too much");
			}
			else if (betAmount < 0) {
				System.out.println("You can't bet a negative amount");
			}
			else {
				betAmount += additionalChips;
				betting = 1;
			}
		}
		
		catch(InputMismatchException e) {
			System.out.println("Pls put in a int");
			input.next();
		}
	}
	System.out.println("Dealer Cards: " + dealerHand.get(0) + ", UNKNOWN"); //prints one of the two of the dealers cards, like real blackjack
	while (!gameOver) {
		System.out.print("Your Cards: ");
		for (int card:playerHand) { //prints all of the players cards
			System.out.print(card + ", ");
		}
		System.out.println("");
		System.out.println("Current Sum: " + sum(playerHand)); //prints the current sum of the cards
		System.out.println("Needed Sum: " + (21 + needAadditionalCards)); //prints the amount the player needs to get to
		System.out.print("1 for Hit, 2 For Stand");
		int playerChoice = 3;
		try {
		playerChoice = input.nextInt();
		} catch(InputMismatchException e) {
			playerChoice = 3;
			input.next();
		}
		if (playerChoice==1) {
			int randomBlackjack = random.nextInt(1,101);
			if (randomBlackjack <= blackjackChance) { //Upgrade in shop which if the random number is less than blackjack chance, you draw a card which instantly blackjacks
				playerHand.add(21+needAadditionalCards-(sum(playerHand)));
			}
			else {
				int newCard = random.nextInt(10); //draw random card
				playerHand.add(newCard+1);
				if (luckyCatCharm && sum(playerHand)%7 == 0) {
					interestAmount = (int)(totalDebtPayed * interestRate);
					chipCount += interestAmount;
					totalMoneyEarned += interestAmount;
				}
				if (sum(playerHand) > 21+needAadditionalCards) {
					for (int i = 0; i < playerHand.size(); i++) {//if player goes over the required number, if they have an 11 turn into 1, like an ace
						if (playerHand.get(i) == 11) {
							playerHand.set(i, 1);
						}
					}
					if (sum(playerHand) > 21+needAadditionalCards) {// if didn't change, you lose
						gameOver = true;
						System.out.print("Your Cards: ");
						for (int card:playerHand) {
							System.out.print(card + ", ");
						}
						System.out.print("\n");
						System.out.println("Bust");
						if (evilDeal) chipCount = 0;
						else chipCount -= betAmount;
						round++;
						break;
					}
				}
			}
		}
		else if (playerChoice==2 || sum(playerHand) == 21) {//if player stands or gets 21
			while (sum(dealerHand) <= 18+needAadditionalCards) {//dealer stops drawing cards when they reach 18
				int newCard = random.nextInt(10);//draws card
				dealerHand.add(newCard+1);
				System.out.print("Dealer Cards: ");
				for (int card:dealerHand) {//prints the dealers cards
					System.out.print(card + ", ");
				}
				System.out.println("");
				System.out.print("Dealer's Sum: " + sum(dealerHand));
				System.out.print("\n");
				if (sum(dealerHand) > 21+needAadditionalCards) {//checking for busts and elevens
					for (int i = 0; i < dealerHand.size(); i++) {
						if (dealerHand.get(i) == 11) {
							dealerHand.set(i, 1);
						}
					}
				}
			}
			if (sum(dealerHand) > 21+needAadditionalCards) { //everything below is checking who won, and what the player gets
				System.out.println("Dealer Bust");
				gameOver = true;
				betAmount *= chipMultiplier;
				if (evilDeal) {
					betAmount *=5;
				}
				if (sum(playerHand) == 21+needAadditionalCards) {
					chipCount += (int)(betAmount * 1.5);
					totalMoneyEarned += (int)(betAmount * 1.5);
				}
				else {
					chipCount += betAmount;
					totalMoneyEarned += betAmount;
				}
			}
			else if (sum(dealerHand) > sum(playerHand)) {
				System.out.println("Dealer Wins");
				gameOver = true;
				betAmount -= additionalChips;
				chipCount -= betAmount;
				if (evilDeal) {
					chipCount = 0;
				}
			}
			else if (sum(dealerHand) < sum(playerHand)) {
				System.out.println("You Win");
				gameOver = true;
				if (evilDeal) {
					betAmount *= 5;
				}
				if (sum(playerHand) == 21+needAadditionalCards) { // if get blackjack, you get 1.5 times the money
					chipCount += (int)(betAmount * 1.5);
					totalMoneyEarned += (int)(betAmount * 1.5);
				}
				else {
					chipCount += betAmount;
					totalMoneyEarned += betAmount;
				}
			}
			else if (sum(dealerHand) == sum(playerHand)) {
				System.out.println("Tie");
				gameOver = true;
			}
			round ++;
			interestAmount = (int)(totalDebtPayed * interestRate);
			chipCount += interestAmount;
			totalMoneyEarned += interestAmount;
		}
		else {
			System.out.println("Please put in a valid number.");
		}
	}
}

public static void debtPayment() {
	System.out.println("Current Debt Amount: " + debtAmount);
	System.out.println("Current Debt Payed: " + debtPayed);
	System.out.print("How much money to deposit: ");
	int depositAmount = 0;
	try {
	depositAmount = input.nextInt();
	} catch(InputMismatchException e) {
		depositAmount = 0;
		input.next();
	}
	if (depositAmount < chipCount && depositAmount <= debtAmount) {
		debtPayed += depositAmount;
		chipCount -= depositAmount;
		totalDebtPayed += depositAmount;
		if (debtPayed >= debtAmount) {
			debtAmount = (int) (debtAmount*1.5);
			debtPayed = 0;
			round = 1;
			ante++;		
			}
		}
	else {
		System.out.println("Either don't have enough chips, or deposited to much");
	}
}
/* PLAYTESTERS: Me, Abhinav
public static void main(String[] args) {
	System.out.println(new ArrayList<>(Arrays.asList(1,4,6,8)));
	saveScore();
	printScore();
	ante = 10;
	totalMoneyEarned = 120000;
	saveScore();
	printScore();
}
*/
	public static void main(String[] args) {
		try {
			score.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		printScore();
		System.out.print("1 to Play, anything else to Quit ");
		try {startGame = input.nextInt();}
		catch (InputMismatchException e) {
			System.out.println("What the hell dude.");
			input.next();
			startGame = 1;
		}
		while (startGame == 1) {
			while (startGame == 1 && chipCount > 0 && round != 5) {
				int choice = 0;
				System.out.println("Current Ante: " + ante);
				System.out.println("Current Round: " + round);
				System.out.println("Interest Earned: " + interestAmount);
				if (round == 4 && chipCount > debtAmount-debtPayed) System.out.println("You should pay your debts");
				else if (round == 4) {
					break;
				}
				System.out.println("Current Chip Count: " + chipCount);
				System.out.print("1 to Blackjack, 2 to pay Debts, 3 to go to Shop");
				try {
				choice = input.nextInt();
				}
				catch (InputMismatchException e) {
					choice = 5;
					input.next();
				}
				if (choice == 1 && round != 4) {
					blackJack();
				}
				else if (choice == 2) {
					debtPayment();
				}
				else if (choice == 3 && round != 4) {
					shop();
				}
			}
			finalStats();
			resetStats();
			System.out.print("1 to Play, anything else to Quit ");
			try {
			startGame = input.nextInt();
			} catch (InputMismatchException e) {
				startGame = 1992;
			}
		}
		input.close();
	}
}

