package com.example.minesweeper;

import java.util.ArrayList;
import java.util.Scanner;

public class MineSweeperImpl implements MineSweeper {
	private static final char MINE_SYMBOL = '*';
	private static final String PRELIMINARY_VALIDATION_REGEX = "^([*.]+\\n)+$";
	
	ArrayList<String> mineFieldArray;
	Integer n, m;

	public static void main(String[] args) {
		MineSweeper ms = new MineSweeperImpl();
		ms.setMineField("*...\n..*.\n....");
		System.out.println(ms.getHintField());
	}
	
	public void setMineField(String mineField) throws IllegalArgumentException {
		
		/*Adding a new line character at the end of the input string simplifies interpretation.*/
		//if(!mineField.endsWith("\n")) //This can be added to allow new line at the end of string in the original input.
				mineField = mineField + "\n";
		
		if(!mineField.matches(PRELIMINARY_VALIDATION_REGEX)) throw new IllegalArgumentException();
		
		try(Scanner scanner = new Scanner(mineField)) {
			m = scanner.nextLine().length();
		}

		mineFieldArray = new ArrayList<String>();
			
		try(Scanner scanner = new Scanner(mineField)) {
			n = 0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if(line.length() != m) {
					mineFieldArray = null;
					n = null;
					m = null;
					throw new IllegalArgumentException();
				}
				
				mineFieldArray.add(line.substring(0, m)); //Adding string without trailing new line char.
				n++;
			}
		}
	}

	public String getHintField() throws IllegalStateException {
		String result = "";
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {

				if(mineFieldArray.get(i).charAt(j) == MINE_SYMBOL) result = result + MINE_SYMBOL;
				else {
					int sum = 0;
					for(int k = -1; k <= 1; k++) {
						for(int l = -1; l <=1; l++) {
							if(k != 0 || l != 0) {
								if(0 <= i+k && i+k < n && 0 <= j+l && j+l < m && mineFieldArray.get(i+k).charAt(j+l) == MINE_SYMBOL)
										sum++;
							}
						}
					}
					result = result + sum;
				}
			}
			result = result + "\n";
		}
		return result;
	}

}
