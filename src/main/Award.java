package main;

/*
 * 返回奖励类型接口
 */
public interface Award {
	public int DOUBLE_FIRE = 2;
	public int LIFE = 0;
	public int BOMB = 1;
	public int getType();//获得奖励的类型
}
