package Tests;

import java.util.ArrayList;

public class JVMTest{
	public static void main(String[] args){
		Pub p = new Pub();
		Esp e = new Esp();
		e.run(p);
		System.out.println(p.s.size());
	}
}

class Pub{
	ArrayList<Integer> s = new ArrayList<Integer>();
	public ArrayList<Integer> getS(){
		s.add(12);
		return s;
	}
}

class Esp{
	public void run(Pub p){
		ArrayList<Integer> ss = p.getS();
//		ss.add(1);
		ss = new ArrayList<Integer>();
		ss.clear();
	}
}