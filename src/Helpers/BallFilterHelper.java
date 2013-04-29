package Helpers;

import java.util.*;

import balls.Ball;


////
//		This design is depend on slow reflection and Arraylist. 
//		When we come into this class, there must exists another more elegant and efficient way. 
/////////////////////////////////////////////////////////////
//     This class include method of the following:
////	Find balls/ball in/not in arrayList
////	Find balls/ball of className from arrayList (support super class identification)
////	Find target with weight
////	Judge if it is a subclass
/////////////////////////////////////////////////////////////
public class BallFilterHelper {
	
	// /////// if the (inClassName) is the subClass of the (screenClassName)
	public boolean isSubclass(String screenClassName, String inClassName) {

		Class temp = null;
		try {
			temp = Class.forName(inClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		while (!temp.getName().equals("java.lang.Object")) {
			if (temp.getName().equals(screenClassName)) {
				return true;
			}
			temp = (Class) temp.getGenericSuperclass();
		}

		return false;
	}

	// ///deprecated
	public ArrayList<Ball> filterBall(String className, ArrayList<Ball> balls) {
		ArrayList<Ball> results = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return results;
		}
		for (Ball b : balls) {

			if (b.getClass().getName().equals(className)) {
				results.add(b);
			}
		}
		return results;
	}

	// ///delete ball from balls
	public ArrayList<Ball> filterBall(ArrayList<Ball> balls, Ball ball) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return result;
		}

		for (Ball b : balls) {
			if (!b.equals(ball)) {
				result.add(b);
			}
		}
		return result;

	}

	// //delete balls2 from balls1; balls1 > balls2
	public ArrayList<Ball> filterNonInArrayList(ArrayList<Ball> balls1,
			ArrayList<Ball> balls2) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls1.size() == 0) {
			return result;
		}

		if (balls2.size() == 0) {
			return balls1;
		}

		for (Ball b : balls1) {
			if (!balls2.contains(b)) {
				result.add(b);
			}
		}
		return result;
	}

	// ////find the balls2 from balls1; balls1 > balls2
	public ArrayList<Ball> filterInArrayList(ArrayList<Ball> balls1,
			ArrayList<Ball> balls2) {

		ArrayList<Ball> result = new ArrayList<Ball>();

		for (Ball b : balls1) {
			if (balls2.contains(b)) {
				result.add(b);
			}
		}
		return result;
	}

	// //find ball NOT belongs to class (in classNameList) from balls
	public ArrayList<Ball> filterExceptClassBall(ArrayList<Ball> balls,
			Collection<String> classNameList) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return result;
		}
		// ArrayList<Ball> result = new ArrayList<Ball>();
		//
		// for(Ball b:balls)
		// {
		// for(Iterator<String> it=classNameList.iterator();it.hasNext();)
		// {
		// String className = it.next();
		//
		// System.out.println("in filter////////////  screen "+className +
		// "  from"+b.getClass().getName());
		//
		// if(!this.isSubclass(className, b.getClass().getName()))
		// {
		// System.out.println("++++++++++++");
		// result.add(b);
		// }
		// }
		//
		// }
		ArrayList<Ball> wantClassArray = new ArrayList<Ball>();
		wantClassArray = this.filterWantClassBall(balls, classNameList);
		result = this.filterNonInArrayList(balls, wantClassArray);
		for (Ball b : balls) {
			// System.out.println(b.getClass().getName()+"!!!!!!!!!!!!");
		}

		return result;
	}

	// //find ball belongs to class (in classNameList) from balls
	public ArrayList<Ball> filterWantClassBall(ArrayList<Ball> balls,
			Collection<String> classNameList) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return result;
		}

		for (Ball b : balls) {
			for (Iterator<String> it = classNameList.iterator(); it.hasNext();) {
				String className = it.next();
				// System.out.println("in filter////////////  screen "+className
				// + "  from"+b.getClass().getName());
				if (this.isSubclass(className, b.getClass().getName())) {
					// System.out.println("-----------------");
					result.add(b);
				}
			}

		}

		return result;
	}

	// find ball NOT belong to class (className) from balls
	public ArrayList<Ball> filterExceptClassBall(ArrayList<Ball> balls,
			String className) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return result;
		}

		for (Ball b : balls) {

			if (!this.isSubclass(className, b.getClass().getName())) {
				result.add(b);
			}
		}
		return result;
	}

	// find balls in class of (className) from (balls)
	public ArrayList<Ball> filterWantClassBall(ArrayList<Ball> balls,
			String className) {
		ArrayList<Ball> result = new ArrayList<Ball>();
		if (balls.size() == 0) {
			return result;
		}

		for (Ball b : balls) {
			if (this.isSubclass(className, b.getClass().getName())) {
				result.add(b);
			}
		}
		return result;
	}

	// find the WANTED ball by the use of METHOD: WEIGHT from source and return
	// balls
	// public Ball findBall(ArrayList<Ball> balls, Ball source, String tag)
	// {
	// Ball result = balls.get(0);
	// for(Ball b:balls)
	// {
	// if(source.weight(b, tag)< source.weight(result, tag))
	// {
	// result = b;
	// }
	// }
	// //System.out.println(result);
	// return result;
	// }
	// ////////////////////////find the min one
	public Ball findBallByMethod(ArrayList<Ball> balls, Ball source,
			String methodName) {
		BallReflectHelper bp = new BallReflectHelper();

		if (balls.size() < 1) {
			return null;
		}
		Ball result = balls.get(0);
		for (Ball b : balls) {
			Object[] ab = new Object[1];
			ab[0] = b;
			Object[] ar = new Object[1];
			ar[0] = result;

			Double bb = (Double) bp.implementMethodNoNewClass(source,
					methodName, ab);
			Double br = (Double) bp.implementMethodNoNewClass(source,
					methodName, ar);
			if (bb < br) {
				result = b;
			}
		}
		// System.out.println(result);
		return result;
	}

	// ////the following three methods is used to add AL (add) to AL (to),
	// return (to)
	public ArrayList<Object> addArrayListObject(ArrayList<Object> to,
			ArrayList<Object> add) {
		if (add.size() == 0) {
			return to;
		}
		for (Object o : add) {
			to.add(o);
		}
		return to;
	}

	public ArrayList<Ball> addArrayListBall(ArrayList<Ball> to,
			ArrayList<Ball> add) {
		if (add.size() == 0) {
			return to;
		}
		for (Ball o : add) {
			to.add(o);
		}
		return to;
	}

//	public ArrayList<Atom> addArrayListAtom(ArrayList<Atom> to,
//			ArrayList<Atom> add) {
//		if (add.size() == 0) {
//			return to;
//		}
//		for (Atom o : add) {
//			to.add(o);
//		}
//		return to;
//	}
//
//	// //////find the sextual target;;; except the gender (G) and the
//	// genderType.intermediate from balls
//	public ArrayList<Ball> filterForSex(ArrayList<Ball> balls,
//			BallModule.activeBall.genderType G) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//		if (balls.size() == 0) {
//			return result;
//		}
//
//		for (Ball b : balls) {
//			activeBall ab = (activeBall) b;
//			if (!(ab.getGender() == G || ab.getGender() == BallModule.activeBall.genderType.intermediate)) {
//				result.add(b);
//			}
//		}
//
//		return result;
//	}
//
//	// ///////delete the InSex ones or the Married ones
//	public ArrayList<Ball> filterSingle(ArrayList<Ball> balls) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//		if (balls.size() == 0) {
//			return result;
//		}
//
//		for (Ball b : balls) {
//			activeBall ab = (activeBall) b;
//			if (!ab.isInSex()) {
//				result.add(b);
//			}
//		}
//
//		return result;
//	}
//
//	// ///////delete the offsprings from sextual targets
//	public ArrayList<Ball> filterForForbid(ArrayList<Ball> balls, Ball source) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//		if (balls.size() == 0) {
//			return result;
//		}
//
//		ArrayList<Ball> offsprings = new ArrayList<Ball>();
//		ArrayList<Ball> ancestries = new ArrayList<Ball>();
//		ArrayList<Ball> gravida = new ArrayList<Ball>();
//
//		offsprings = this.findOffsprings(source);
//		ancestries = this.findAncestries(source);
//		gravida = this.findGravida(balls);
//
//		result = this.filterNonInArrayList(
//				balls,
//				this.addArrayListBall(offsprings,
//						this.addArrayListBall(ancestries, gravida)));
//
//		return result;
//	}
//
//	// /////////find pregnant women, return the balls in pregnant
//	private ArrayList<Ball> findGravida(ArrayList<Ball> balls) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//		if (balls.size() == 0) {
//			return result;
//		}
//
//		for (Ball b : balls) {
//			activeBall ab = (activeBall) b;
//			if (ab.isInPregnant()) {
//				result.add(b);
//			}
//		}
//
//		return result;
//
//	}
//
//	// //////////find offsprings
//	private ArrayList<Ball> findOffsprings(Ball source) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//
//		activeBall ss = (activeBall) source;
//		if (ss.getOffsprings().size() > 0) {
//			for (Ball b : ss.getOffsprings()) {
//				result.add(b);
//				result = this.addArrayListBall(result, this.findOffsprings(b));
//
//			}
//		}
//		return result;
//	}
//
//	// //////find ancestries
//	private ArrayList<Ball> findAncestries(Ball source) {
//		ArrayList<Ball> result = new ArrayList<Ball>();
//
//		activeBall ss = (activeBall) source;
//		if (ss.getFather() == null && ss.getMother() == null) {
//			return result;
//		}
//		if (ss.getFather() != null) {
//			result.add(ss.getFather());
//			result = this.addArrayListBall(result,
//					this.findAncestries(ss.getFather()));
//		}
//		if (ss.getMother() != null) {
//			result.add(ss.getMother());
//			result = this.addArrayListBall(result,
//					this.findAncestries(ss.getMother()));
//		}
//
//		return result;
//	}
//
//	// // *THIS METHOD IS FOR 3D APPLICATION*
//	// //delete BM in (balls2) from BM in (balls1)
//	public ArrayList<BallMirror> filterNonInArrayListBallMirror(
//			ArrayList<BallMirror> balls1, ArrayList<BallMirror> balls2) {
//
//		ArrayList<BallMirror> result = new ArrayList<BallMirror>();
//
//		for (BallMirror b : balls1) {
//			if (!balls2.contains(b)) {
//				result.add(b);
//			}
//		}
//		return result;
//	}

}
