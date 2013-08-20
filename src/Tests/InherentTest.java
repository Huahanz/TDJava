package Tests;

public class InherentTest {
	public static void main(String[] args) {
		//To be simple. Methods start from right hand side. Properties start from left hand side. Override apply to both. INCONTINUES. 
		
		//Property : The getting value of a property is decided by the left hand class type. 
		//Bear in mind. The same name properties in the superclass and the subclass have different references
		//, which means they are independent with each other except the override. They may contain different values. 
		//You should treat them as different name properties. 
		//Actually, after compiled, the properties are has their class types (right hand class type) as their prefixes.  
		//ONLY EXCEPT when a property cannot be found in the local, it goes to the super class perproty with the same name. 
		
		//Method : To the opposite, the call of a method is decided by the right hand class type. 
		//Once called, the method will getting executed from where it defined. 
		//The compiler first checks its local class, if not find, checks the supper class. 
		//WHen the method trying to read a variable inside it's body, it will use the same rules. 
		//It's principal is straightforward - NEVER CHECK ITS SUBCLASS. Because it may have different types of subclass.  
		//The method first reaches into the lowest level which is the right hand class type, and starts calling from there. 
		
		//Memory structure : 
		// | class_type | super.property0 | sub.property0 | method0.reference (this is static, got initialize by the right hand side) | 

		SubClass sub0 = new SubClass();
		System.out.println(sub0.x); // 3, this will first checks the x in SubClass, so return 3.
		System.out.println(sub0.y); // 4, the same thing. 
		System.out.println(sub0.getX()); // 3, this will first checks the SubClass, so return 3.
		System.out.println(sub0.getY()); // 2
		sub0.setB(20); 
		System.out.println(sub0.getB()); // 13, the same name properties have differet copy of value. 
		sub0.setC(99);
		System.out.println(sub0.getC()); // 19, same thing.
		System.out.println("-----------------");
		
		SuperClass sub = new SubClass();
		System.out.println(sub.x); //1, this will first check the x in SuperClass, so return 1.
		System.out.println(sub.y); //2 this same thing. 
		System.out.println(sub.getX()); //3, this will first check the getX method in SubClass, so return 3.
		System.out.println(sub.getY()); //2, since there is no getY method in SubClass, it go to the SuperClass to find this method. 
		sub.setB(50);
		System.out.println(sub.getB()); // 13, the same name properties have differet copy of value. 
		sub.setC(98);
		System.out.println(sub.getC()); // 19, same thing. regardless of the calling environment. 
		
		System.out.println(sub.getZ()); // 990, the same name properties are also override as the same name methods. 
		System.out.println(sub.z); // 990, If not found, go to the superclass. 

		System.out.println(((SuperClass)sub).getX()); // 3. The casting doesn't matter. 
		System.out.println(((SuperClass)sub).getY()); // 2

		SuperClass sup = new SubClass();
		System.out.println(sup.test0()); 
		// 1110, the nested method calls is not CONTINUES. 
		//The last method call has no relevance with the following method call. 
	}
}

abstract class SuperClass {
	int x = 1;
	int y = 2;
	int z = 990;
	int b = 8;
	public int getB(){
		return this.b;
	}
	public void setB(int b){
		this.b = b;
	}
	int c = 19;
	public int getC(){
		return this.c;
	}
	public void setC(int c){
		this.c = c;
	}
	int aa = 20;
	int test0(){
		return this.getAa(); 
	}
	int getAa(){
		return this.aa;
	}
	int getX() {
		return x;
	}
	
	int getY(){
		return this.y;
	}
	
	int getZ(){
		System.out.println("in super");
		return this.z + 100;
	}
	void setX(int x){
		this.x = x;
	}

	int myFun() {
		return 5;
	}
}

class SubClass extends SuperClass {
	int x = 3;
	int y = 4;
	int b = 13;
	public int getB(){
		return this.b;
	}
	int c = 21;
	public void setC(int c){
		this.c = c;
	}
	int aa = 1110;
	int getAa(){
		return this.aa;
	}
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