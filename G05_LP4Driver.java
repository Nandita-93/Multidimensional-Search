import java.util.*;
import java.io.*;

/** 
 *  Program to do multidimensional search
*  @author Badhrinath Santhanam, Indhumathi Suryanarayanan, Karthik Ramakrishnan, Nandita Balasubramanian
 */
public class G05_LP4Driver {
    static long[] description;
    static final int DLENGTH = 100000;

    public static void main(String[] args)  throws FileNotFoundException {
	Scanner in;
	if(args.length > 0) {
	    in = new Scanner(new File(args[0]));
        } else {
	    in = new Scanner(System.in);
	}
	String s;
	double rv = 0;
	description = new long[DLENGTH];

	Timer timer = new Timer();
	MDS mds = new MDS();

	while(in.hasNext()) {
	    s = in.next();
	    if(s.charAt(0) == '#') {
		s = in.nextLine();
		continue;
	    }
	    if(s.equals("Insert")) {
		long id = in.nextLong();
		double price = in.nextDouble();
		long des = in.nextLong();
		int index = 0;
		while(des != 0) {
		    description[index++] = des;
		    des = in.nextInt();
		}
		description[index] = 0;
		rv += mds.insert(id, price, description, index);
	    } else if(s.equals("Find")) {
		long id = in.nextLong();
		rv += mds.find(id);
	    } else if(s.equals("Delete")) {
		long id = in.nextLong();
		rv += mds.delete(id);
	    } else if(s.equals("FindMinPrice")) {
		long des = in.nextLong();
		rv += mds.findMinPrice(des);
	    } else if(s.equals("FindMaxPrice")) {
		long des = in.nextLong();
		rv += mds.findMaxPrice(des);
	    } else if(s.equals("FindPriceRange")) {
		long des = in.nextLong();
		double lowPrice = in.nextDouble();
		double highPrice = in.nextDouble();
		rv += mds.findPriceRange(des, lowPrice, highPrice);
	    } else if(s.equals("PriceHike")) {
		long minid = in.nextLong();
		long maxid = in.nextLong();
		double rate = in.nextDouble();
		rv += mds.priceHike(minid, maxid, rate);
	    } else if(s.equals("Range")) {
		double lowPrice = in.nextDouble();
		double highPrice = in.nextDouble();
		rv += mds.range(lowPrice, highPrice);
	    } else if(s.equals("SameSame")) {
		rv += mds.samesame();
	    } else if(s.equals("End")) {
		break;
	    } else {
		System.out.println("Houston, we have a problem.\nUnexpected line in input: "+ s);
		System.exit(0);
	    }
	}
	//rv = (double) Math.round( rv * 100 ) / 100;
	System.out.printf("%.2f\n", rv);
	System.out.println(timer.end());
	in.close();
    }
}

/*SAMPLE I/O
INPUT: lp4-data/lp4-1.txt
OUTPUT:
1450.08
Time: 27 msec.
Memory: 4 MB / 128 MB.

INPUT: lp4-data/samesame.txt
OUTPUT:
22.00
Time: 31 msec.
Memory: 4 MB / 128 MB.

INPUT: lp4-data2/bad.txt
OUTPUT:
1016105100.00
Time: 14621 msec.
Memory: 176 MB / 865 MB.
*/