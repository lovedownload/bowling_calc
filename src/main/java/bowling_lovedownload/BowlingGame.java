package bowling_lovedownload;

import java.util.Scanner;

public class BowlingGame {
	
	public int user;
	
	private int[] pin = new int[24];                    //쓰러뜨린 핀의 배열 
	private int strike = 0;                             //스트라이크면 증가하는 변수 
	private int spare = 0;                 				//스페어면 증가하는 변수 
	private int score = 0; 								//스코어 변수 (누적됨)
	//private int lastI = 0;
	private int lastJ = 0;								//보너스 프레임 처리를 위해 10프레임의 for문의 마지막 J값을 저장하는 변
	private String pin_value = "";                      //쓰러뜨린 핀의 개수를 보여주기 위한 변수 (화면 출력 시 사용됨) 
	private String score_value = "";                    //점수의 합계를 보여주기 위한 변수  (화면 출력 시 사용됨)
	private Scanner clearpin = new Scanner(System.in);  //핀을 입력받기 위한 Scanner 객체 
	
	//10 프레임 처리 
	public void TenFrame(int i) {
		
		System.out.println();
		System.out.print("10 프레임 첫번째 : ");		
		
		for(int j = i; j < 20; j++)
		{
			lastJ = j;
			if(j > 18 && strike > 0)
			{
				System.out.println();
				System.out.print("10 프레임 두번째 : ");
			}
			
			if(j > 18 && spare == 1)
			{
				break;
			}
		
			pin[j] = clearpin.nextInt();
		
			FirstPinCheck(pin[j], j);
			
			if(j > 18 && pin[j] < 10)
			{
				SpareScore(j);
				break;
			}
			
			if(pin[j] == 10)
			{
				Strike(j);
				continue;
			}
			
			if(pin[j] < 10)
			{
				SpareScore(j);
				System.out.println();
				System.out.print("10 프레임 두번째 : ");
				pin[j+1] = clearpin.nextInt();
				SecoundPinCheck(pin[j+1], j);
				Spare(j);
			}
		}
	
		//보너스 프레임 
		if(strike == 1 || strike == 2 || spare == 1)
		{
			System.out.println();
			System.out.print("10 프레임 세번째 : ");
			pin[lastJ + 1] = clearpin.nextInt();
		}
		
		//10프레임 첫번째만 스트라이크일 경우 
		if(strike == 1)
		{
			SecoundPinCheck(pin[lastJ + 1], lastJ);
			
			if(pin[lastJ] + pin[lastJ+1] == 10)
			{
				pin_value += "/|";
				score = score + 20;
			}
			
			if(pin[lastJ] + pin[lastJ + 1] < 10)
			{
				pin_value += pin[lastJ + 1] + "|";
				score = score + 10 + pin[lastJ] + pin[lastJ + 1];
			}
			
			score_value += String.format("%5d|", score);
			strike = 0;
			Scoreboard.print(pin_value, score_value);
		}
		
		//10프레임 첫번째와 두번째가 스트라이크일 경우 
		if(strike == 2)
		{
			FirstPinCheck(pin[lastJ + 1], lastJ);
			
			if(pin[lastJ + 1] == 10)
			{
				pin_value += "X|";
				score = score + 30;
			}
			
			if(pin[lastJ + 1] < 10)
			{
				pin_value += pin[lastJ + 1] + "|";
				score = score + 20 + pin[lastJ + 1];
			}
			
			score_value += String.format("%5d|", score);
			strike = 0;
			Scoreboard.print(pin_value, score_value);
		}
		
		//10프레임 첫번째 두번째가 스페어일 경우 
		if(spare == 1)
		{
			SecoundPinCheck(pin[lastJ + 1], lastJ);
			
			if(pin[lastJ + 1] == 10)
			{
				pin_value += "X|";
				score = score + 20;
			}
			
			if(pin[lastJ + 1] < 10)
			{
				pin_value += pin[lastJ + 1] + "|";
				score = score + 10 + pin[lastJ + 1];
			}
			
			score_value += String.format("%5d|", score);
			spare = 0;
			Scoreboard.print(pin_value, score_value);
		}
		
		GameOver();
	}

	//게임 완료 처리 
	public void GameOver() {
		System.out.println();
		System.out.println("게임이 종료되었습니다.");
		System.out.println("최종 점수는 " + score + "점입니다.");
		return;
	}
	
	//첫번째 입력받은 핀의 유효성 검사 (0 ~ 10)
	public void FirstPinCheck(int first_pin, int i) {
		
		while(first_pin < 0 || first_pin > 10)
		{		
			System.out.println("0 ~ 10 사이의 숫자를 입력하세요.");
			System.out.print("입력 : ");		
			pin[i] = clearpin.nextInt();	
		}	
		return;
	}
	
	//두번째 입력받은 핀의 유효성 검사 (첫번째 핀과의 합이 10을 초과해서는 안됨)
	public void SecoundPinCheck(int secound_pin, int i) {
		
		while(secound_pin + pin[i] > 10)
		{
			System.out.println("핀의 합계는 10을 초과할 수 없습니다. 다시 입력하세요.");
			System.out.print("입력 : ");
			pin[i+1] = clearpin.nextInt();
		}	
		return;
	}
	
	//중간단걔의 점수 계산 및 화면 출력을 위한 함수 (스트라이크가 아닐 경우만 호출)
	public void SpareScore(int i) {
		
		pin_value += pin[i] + "|";
		
		if((strike == 0 || strike == 1) && spare == 0)
		{
			Scoreboard.print(pin_value, score_value);	
		}
		
		if (strike == 2)
		{
			for(int j = 0; j < strike; j++)
			{
				score = score + 10;
			}
			
			score = score + pin[i];
			score_value += String.format("%3d|", score);
			strike = strike - 1;
			Scoreboard.print(pin_value, score_value);
		}
		
		if(spare == 1)
		{
			score = score + 10 + pin[i];
			score_value += String.format("%3d|", score);
			spare = 0;
			Scoreboard.print(pin_value, score_value);
		}
	}
	
	//스트라이크 처리 (점수 계산 및 화면 출력)
	public void Strike(int i) 
	{
		if(i >= 18)
		{
			pin_value += "X|";
		}
		
		if(i < 18)
		{
			pin_value += "X| |";
			pin[i+1] = 0;
		}
		
		if(strike < 3)
		{
			strike = strike + 1;
			
		}
		
		if(spare == 0 && strike < 3)
		{
			Scoreboard.print(pin_value, score_value);
			return;
		}
		
		if(strike == 3)
		{		
			score = score + 30;
			score_value += String.format("%3d|", score);
			strike = strike - 1;
			Scoreboard.print(pin_value, score_value);
			return;
		}
		
		if(spare == 1)
		{
			score = score + 20;
			score_value += String.format("%3d|", score);
			spare = 0;
			Scoreboard.print(pin_value, score_value);
			return;
		}		
	}
	
	//스페어 처리 (점수 계산 및 화면 출력)
	public void Spare(int i) 
	{
		if(strike == 1)
		{
			score = score + 10 + pin[i] + pin[i+1];
			score_value += String.format("%3d|", score);
			strike = 0;
		}
		
		/*if(spare == 1)
		{
			score = score + 10 + pin[i] + pin[i+1];
			score_value += String.format("%3d|", score);
			spare = 0;
		}*/
		
		if(pin[i] + pin[i+1] == 10 && i < 19)
		{
			spare = 1;
			pin_value += "/|";
			Scoreboard.print(pin_value, score_value);
			return;
		}
		
		if(pin[i] + pin[i+1] < 10)
		{
			pin_value += gutterCheck(i);
			score = score + pin[i] + pin[i+1];
			StringFormatGutterFrame(i);
		}
	}
	
	//스트라이크나 스페어 처리가 되지 않았을 경우 값을 출력하는 함수 (10프레임의 보너스 프레임이 발생하지 않았을 경우 넓이를 맞추기 위해 함수를 분리) 
	public void StringFormatGutterFrame(int i)
	{
		//10프레임일 경우 
		if(i == 18)
		{
			score_value += String.format("%5d|", score);
			Scoreboard.print(pin_value, score_value);
			GameOver();
			return;
		}
		
		score_value += String.format("%3d|", score);
		Scoreboard.print(pin_value, score_value);
		return;
	}
	
	//gutter 체크 
	public String gutterCheck(int i) {
		
		if(pin[i+1] == 0 && i < 18)
		{
			return "-|";
		}
		
		if(pin[i+1] == 0 && i == 18)
		{
			return "-|-|";
		}
		
		return pin[i+1] + "|";
	}
	
	//첫번째 핀의 값 설정 
	public void SetFirstPin(int first_pin, int i)
	{
		pin[i] = first_pin;
	}
	
	//두번째 핀의 값 설정 
	public void SetSecoundPin(int secound_pin, int i)
	{
		pin[i+1] = secound_pin;
	}
}
