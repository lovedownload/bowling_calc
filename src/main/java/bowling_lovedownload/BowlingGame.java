package bowling_lovedownload;

import java.util.Scanner;

public class BowlingGame {
	
	public int user;
	
	private int[] pin = new int[24];                    //�����߸� ���� �迭 
	private int strike = 0;                             //��Ʈ����ũ�� �����ϴ� ���� 
	private int spare = 0;                 				//������ �����ϴ� ���� 
	private int score = 0; 								//���ھ� ���� (������)
	//private int lastI = 0;
	private int lastJ = 0;								//���ʽ� ������ ó���� ���� 10�������� for���� ������ J���� �����ϴ� ��
	private String pin_value = "";                      //�����߸� ���� ������ �����ֱ� ���� ���� (ȭ�� ��� �� ����) 
	private String score_value = "";                    //������ �հ踦 �����ֱ� ���� ����  (ȭ�� ��� �� ����)
	private Scanner clearpin = new Scanner(System.in);  //���� �Է¹ޱ� ���� Scanner ��ü 
	
	//10 ������ ó�� 
	public void TenFrame(int i) {
		
		System.out.println();
		System.out.print("10 ������ ù��° : ");		
		
		for(int j = i; j < 20; j++)
		{
			lastJ = j;
			if(j > 18 && strike > 0)
			{
				System.out.println();
				System.out.print("10 ������ �ι�° : ");
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
				System.out.print("10 ������ �ι�° : ");
				pin[j+1] = clearpin.nextInt();
				SecoundPinCheck(pin[j+1], j);
				Spare(j);
			}
		}
	
		//���ʽ� ������ 
		if(strike == 1 || strike == 2 || spare == 1)
		{
			System.out.println();
			System.out.print("10 ������ ����° : ");
			pin[lastJ + 1] = clearpin.nextInt();
		}
		
		//10������ ù��°�� ��Ʈ����ũ�� ��� 
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
		
		//10������ ù��°�� �ι�°�� ��Ʈ����ũ�� ��� 
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
		
		//10������ ù��° �ι�°�� ������� ��� 
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

	//���� �Ϸ� ó�� 
	public void GameOver() {
		System.out.println();
		System.out.println("������ ����Ǿ����ϴ�.");
		System.out.println("���� ������ " + score + "���Դϴ�.");
		return;
	}
	
	//ù��° �Է¹��� ���� ��ȿ�� �˻� (0 ~ 10)
	public void FirstPinCheck(int first_pin, int i) {
		
		while(first_pin < 0 || first_pin > 10)
		{		
			System.out.println("0 ~ 10 ������ ���ڸ� �Է��ϼ���.");
			System.out.print("�Է� : ");		
			pin[i] = clearpin.nextInt();	
		}	
		return;
	}
	
	//�ι�° �Է¹��� ���� ��ȿ�� �˻� (ù��° �ɰ��� ���� 10�� �ʰ��ؼ��� �ȵ�)
	public void SecoundPinCheck(int secound_pin, int i) {
		
		while(secound_pin + pin[i] > 10)
		{
			System.out.println("���� �հ�� 10�� �ʰ��� �� �����ϴ�. �ٽ� �Է��ϼ���.");
			System.out.print("�Է� : ");
			pin[i+1] = clearpin.nextInt();
		}	
		return;
	}
	
	//�߰��ܰ��� ���� ��� �� ȭ�� ����� ���� �Լ� (��Ʈ����ũ�� �ƴ� ��츸 ȣ��)
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
	
	//��Ʈ����ũ ó�� (���� ��� �� ȭ�� ���)
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
	
	//����� ó�� (���� ��� �� ȭ�� ���)
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
	
	//��Ʈ����ũ�� ����� ó���� ���� �ʾ��� ��� ���� ����ϴ� �Լ� (10�������� ���ʽ� �������� �߻����� �ʾ��� ��� ���̸� ���߱� ���� �Լ��� �и�) 
	public void StringFormatGutterFrame(int i)
	{
		//10�������� ��� 
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
	
	//gutter üũ 
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
	
	//ù��° ���� �� ���� 
	public void SetFirstPin(int first_pin, int i)
	{
		pin[i] = first_pin;
	}
	
	//�ι�° ���� �� ���� 
	public void SetSecoundPin(int secound_pin, int i)
	{
		pin[i+1] = secound_pin;
	}
}
