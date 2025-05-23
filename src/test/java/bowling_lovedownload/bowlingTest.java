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
		Scanner clearpin = new Scanner(System.in);   // input scanner
		bg = new BowlingGame();
	}
	
	@Test
	public void Roll() 
	{	
		Scanner clearpin = new Scanner(System.in);   //핀 값을 입력 받기 위한 객체 설정 
		int first_pin = 0;                           //첫번째 핀의 입력 값 
		int secound_pin = 0;                         //두번째 핀의 입력 값 
		int lastI = 0;                               //10 프레임 처리를 위한 for문의 마지막 값 
		
		System.out.println("게임을 시작합니다. 모든 값은 숫자로 입력해주세요. (Strike 10, 그 외 0 ~ 9)");
		System.out.println();
		
		for(int i = 0; i < 19; i = i + 2)
		{	
			//10 프레임 시작 시 for문을 빠져 나감 
			if(i == 18)
			{
				lastI = i;
				break;
			}
			
			//첫번째 핀의 값 입력 
			System.out.println();
			System.out.print(((i + 2) / 2) + " 프레임 첫번째 : ");
			
			first_pin = clearpin.nextInt(); 
		
			//첫번째 핀의 유효성 검사 
			first_pin = bg.FirstPinCheck(first_pin, i);
			
			//첫번째 핀의 값 설정 
			bg.SetFirstPin(first_pin, i);
			
			if(first_pin == 10)
			{
				//스트라이크 처리 
				bg.Strike(i);
				continue;
			}
			
			if(first_pin < 10)
			{
				//중간단계의 결과 값 처리 
				bg.SpareScore(i);
				
				//두번째 핀의 값 입력 
				System.out.println();
				System.out.print(((i + 2) / 2) + " 프레임 두번째 : ");	
				secound_pin = clearpin.nextInt();
				
				
				//두번째 핀의 유효성 검사 
				secound_pin = bg.SecoundPinCheck(secound_pin, i);
				
				//두번째 핀의 값 설정 
				bg.SetSecoundPin(secound_pin, i);
				
				//스페어 처리 
				bg.Spare(i);
			}
		}
		
		//10 프레임 처리
		bg.TenFrame(lastI);
	}
}
