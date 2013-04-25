package Tests;

public class InherentTest {
	public static void main(String[] args) {

		//The property is first getting from supper class. 
		//Start from the assigned class type, the obj first check its method from local class, if not find, it then check the supper class. AND IT NEVER CHECK ITS SUBCLASS. 
		SubClass sub0 = new SubClass();
		System.out.println(sub0.x);
		System.out.println(sub0.y);
		// 3, 4

		SuperClass sub = new SubClass();
		System.out.println(sub.x);
		System.out.println(sub.y);
		// 1, 2

		System.out.println(sub.getX());
		// 1

		System.out.println(sub.myFun());
		// 6
		
		sub = new SubClass();
		
		sub.setX(10);
		System.out.println(sub.getX());
		
		sub = new SuperClass();
		System.out.println(sub.getX());
		
		sub = new SubClass();
		System.out.println(sub.getY());
		System.out.println(sub.getZ());

		System.out.println(((SuperClass)sub).getY());

	}
}

class SuperClass {
	int x = 1;
	int y = 2;
	int z = 0;
	
	int getX() {
		return x;
	}
	
	int getY(){
		return this.y;
	}
	
	int getZ(){
		return this.z;
	}
	void setX(int x){
		this.x = x;
	}

	int myFun() {
		return 5;
	}
}

class SubClass extends SuperClass {
//	int x = 3;
	int y = 4;

	int getX() {
		return x;
	}
	
	void setX(int x){
		this.x = x;
	}
	
	int getZ(){
		return this.z;
	}
		
	int myFun() {
		return 6;
	}
}