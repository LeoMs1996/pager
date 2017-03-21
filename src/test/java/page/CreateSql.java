package page;

import java.util.Random;

public class CreateSql {
	public static void main(String[] args) {
		int j = 0, k = 0;
		Random random = new Random();
		for(int i = 1; i<1000; i++)
		{
			j=random.nextInt(2)+1;
			k=random.nextInt(100)+1;
			System.out.println("insert into t_student(id,stu_name,age,gender,address)"
					+ " values('" + i + "', '第" + i + "个学生','" + k + "','" + j + "','第" + i +"个学生地址');");
		}
	}
}
