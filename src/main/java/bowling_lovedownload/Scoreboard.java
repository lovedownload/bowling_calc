package bowling_lovedownload;

class Scoreboard {
	public static void print(String pin, String score)
	{  
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |  10 |");
        System.out.println("-------------------------------------------");
        System.out.println("|" + pin);
        System.out.println("-------------------------------------------");
        System.out.println("|" + score);
        System.out.println("-------------------------------------------");
	}
}
