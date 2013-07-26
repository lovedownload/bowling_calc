package bowling_lovedownload;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class bowlingTest {
	BowlingGame bg;
	
	@Before
	public void Setup()
	{
		//��ü ���� 
		bg = new BowlingGame();
	}
	
	@Test
	public void Roll() 
	{	
		Scanner clearpin = new Scanner(System.in);   //�� ���� �Է� �ޱ� ���� ��ü ���� 
		int first_pin = 0;                           //ù��° ���� �Է� �� 
		int secound_pin = 0;                         //�ι�° ���� �Է� �� 
		int lastI = 0;                               //10 ������ ó���� ���� for���� ������ �� 
		
		for(int i = 0; i < 19; i = i + 2)
		{	
			//10 ������ ���� �� for���� ���� ���� 
			if(i == 18)
			{
				lastI = i;
				break;
			}
			
			//ù��° ���� �� �Է� 
			System.out.println();
			System.out.print(((i + 2) / 2) + " ������ ù��° : ");
		
			first_pin = clearpin.nextInt();
			
			//ù��° ���� ��ȿ�� �˻� 
			bg.FirstPinCheck(first_pin, i);
			
			//ù��° ���� �� ���� 
			bg.SetFirstPin(first_pin, i);
			
			if(first_pin == 10)
			{
				//��Ʈ����ũ ó�� 
				bg.Strike(i);
				continue;
			}
			
			if(first_pin < 10)
			{
				//�߰��ܰ��� ��� �� ó�� 
				bg.SpareScore(i);
				
				//�ι�° ���� �� �Է� 
				System.out.println();
				System.out.print(((i + 2) / 2) + " ������ �ι�° : ");	
				secound_pin = clearpin.nextInt();
				
				//�ι�° ���� ��ȿ�� �˻� 
				bg.SecoundPinCheck(secound_pin, i);
				
				//�ι�° ���� �� ���� 
				bg.SetSecoundPin(secound_pin, i);
				
				//����� ó�� 
				bg.Spare(i);
			}
		}
		
		//10 ������ ó��
		bg.TenFrame(lastI);
	}
}
