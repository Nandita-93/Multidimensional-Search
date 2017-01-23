import java.util.*;
/**
*  Program to do multidimensional search
*  @author Badhrinath Santhanam, Indhumathi Suryanarayanan, Karthik Ramakrishnan, Nandita Balasubramanian
*/
public class MDS {
	
	//Inner class for representing the id, price and description
	class MDSEntry {
		public long id;
		public long[] description;
		public double price;

		MDSEntry(long id, long[] desc, double price, int size) {
			this.id = id;
			this.description = new long[size];
			this.description = desc;
			this.price = price;
		}

		MDSEntry() {
			id = 0;
			description = null;
			price = 0.0;
		}
	}

	TreeMap<Long, MDSEntry> tm = new TreeMap<>(); // Create a TreeMap for the
	// operations.
	HashMap<Long, TreeSet<Long>> hm1 = new HashMap<>(); //HashMap to store based on the description
	HashMap<Long, ArrayList<Long>> hm2 = new HashMap<>(); // HashMap to store based on ID and desc for same same func
	
	/**
	 * Method insert to add the values into the data structures.
	 * Will add into all the three data structures. If the item desc changes, then in the the HashMap - remove the id from the desc.
	 * @param id 
	 * 			: ID of the item
	 * @param price
	 * 			: Price of the item
	 * @param description
	 * 			: Desc - long[] containing the desc
	 * @param size
	 * 			: size of the desc array
	 * @return
	 * 			: 0 if id exists 
	 * 			  1 if the item is new 
	 */
	int insert(long id, double price, long[] description, int size) {
		// Description of item is in description[0..size-1].
		// Copy them into your data structure.
		long[] de = new long[size];
		for (int i = 0; i < size; i++) {
			de[i] = description[i];
		}
		int out = 0;
		if (tm.containsKey(id)) {
			for (int i = 0; i <tm.get(id).description.length-1 ; i++) {
				if (hm1.containsKey(tm.get(id).description[i])) {
					hm1.get(tm.get(id).description[i]).remove(id);
				}
			}
			tm.get(id).description = de;
			tm.get(id).price = price;
		} else {
			tm.put(id, new MDSEntry(id, de, price, size));
			out = 1;
		}
		TreeSet<Long> tsTemp = new TreeSet<>();
		tsTemp.add(id);
		for (int i = 0; i < size; i++) {
			if (hm1.containsKey(de[i])) {
				hm1.get(de[i]).add(id);
			} else {
				hm1.put(de[i], tsTemp);
			}
		}
		if (size >= 8) {
			
			Arrays.sort(de);
			ArrayList<Long> desc = new ArrayList<Long>();
			for (int i = 0; i < de.length; i++) {
				desc.add(de[i]);
				// }
				hm2.put(id, desc);
			}
		}
		return out; // Item is newly added to the Map
	}

	/**
	 * Method to find the price given the id
	 * @param id
	 * 			: id of the item
	 * @return
	 * 			: price if present, else 0 if not available
	 */
	double find(long id) {
		if (tm.containsKey(id))
		{
			double findOut = Math.round((tm.get(id).price+0.000001) * 100.0) / 100.0;
			return findOut; // return price if present
		}
		return 0; // returns zero, if id is not present
	}

	/**
	 * Method to delete the id from the data structure
	 * @param id
	 * 			: Id to be deleted
	 * @return
	 * 			: Returns the sum of the desc array.
	 */
	long delete(long id) {
		if (tm.containsKey(id)) {
			long sum = 0;
			for (int i = 0; i < tm.get(id).description.length; i++) {
				if (hm1.containsKey(tm.get(id).description[i])) {
					hm1.get(tm.get(id).description[i]).remove(id);
				}
				sum += tm.get(id).description[i];
			}
			tm.remove(id);
			hm2.remove(id);
			return sum; // returns the sum of the longs in the description
		}
		return 0; // return zero
	}

	/**
	 * Method to find the minimum price given a desc
	 * @param des
	 * 			: Description
	 * @return
	 * 			: Minimum price
	 */
	double findMinPrice(long des) {
		TreeSet<Long> idTreeSet = hm1.get(des);
		Double min = Double.MAX_VALUE;
		for (Long id : idTreeSet) {
			if (min > tm.get(id).price) {
				min = tm.get(id).price;
			}
		}
		if (min == Double.MAX_VALUE) {
			return 0;
		}
		double finalMin = Math.floor((min+0.000001) * 100.0) / 100.0;
		return finalMin;
	}

	/**
	 * Method to find the max price given a desc
	 * @param des
	 * 			: Description
	 * @return
	 * 			: Max price
	 */
	double findMaxPrice(long des) {
		TreeSet<Long> idTreeSet = hm1.get(des);
		Double max = Double.MIN_VALUE;
		for (Long id : idTreeSet) {
			if (max < tm.get(id).price) {
				max = tm.get(id).price;
			}
		}
		if (max == Double.MIN_VALUE) {
			return 0;
		}
		double finalMax = Math.floor((max+0.000001) * 100.0) / 100.0;
		return finalMax;
	}
	
	/**
	 * Method to find the price range of items containing the desc
	 * @param des
	 * 			: Description of item
	 * @param lowPrice
	 * 			: Lower Bound
	 * @param highPrice
	 * 			: Upper Bound
	 * @return
	 * 			: count of the price in the range of the bound
	 */
	int findPriceRange(long des, double lowPrice, double highPrice) {
		int count = 0;
		if (hm1.containsKey(des)) {
			TreeSet<Long> ts = hm1.get(des);
			for (Long id : ts) {
				if (tm.get(id).price >= lowPrice
						&& tm.get(id).price <= highPrice) {
					count++;
				}
			}
			return count;
		}
		return 0;
	}

	/**
	 * Method to hike the price given the rate and update the price and return the net hike of the output
	 * @param minid
	 * 			: Range minid of the price
	 * @param maxid
	 * 			: Range maxid of price
	 * @param rate
	 * 			: Rate of increase
	 * @return
	 * 			: return the sum of net hike 
	 */
	double priceHike(long minid, long maxid, double rate) {
		double netHike = 0;
		NavigableMap<Long, MDSEntry> nm = tm.subMap(minid, true, maxid, true);
		for (Long id : nm.keySet()) {
			double newPrice = 0;
			double oldPrice = tm.get(id).price;
			newPrice = oldPrice + oldPrice * (rate / 100);
			netHike += newPrice - oldPrice;
			tm.get(id).price = newPrice;
		}
		double finalHike = Math.floor((netHike+0.000001) * 100.0) / 100.0;
		return finalHike;
	}

	/**
	 * Method to return the range of the prices from low to high and return the count
	 * @param lowPrice
	 * 				: Lower Bound
	 * @param highPrice
	 * 				: Upper Bound
	 * @return
	 * 				: return the count of the items in the range
	 */
	int range(double lowPrice, double highPrice) {
		int count = 0;
		for (Map.Entry<Long, MDSEntry> entry : tm.entrySet()) {
			Long ID = entry.getKey();
			if (tm.get(ID).price >= lowPrice && tm.get(ID).price <= highPrice) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Method to find the same same
	 * The description of the item contains 8 or more numbers, and
	 * The description of the item contains exactly the same set of numbers as another item.
	 * @return
	 */
	int samesame() {

		ArrayList<Boolean> sameSameVisit = new ArrayList<Boolean>(hm2.size());
		for (int i = 0; i < hm2.size(); i++) {
			sameSameVisit.add(false);
		}
		int i = 0;
		for (Map.Entry<Long, ArrayList<Long>> entry1 : hm2.entrySet()) {
			if (sameSameVisit.get(i) == false) {
				int j = 0;
				for (Map.Entry<Long, ArrayList<Long>> entry2 : hm2.entrySet()) {
					if (sameSameVisit.get(j) == false) {
						if (entry1.getKey() != entry2.getKey()
								&& entry1.getValue().equals(entry2.getValue())) {
							sameSameVisit.set(i, true);
							sameSameVisit.set(j, true);
						}
					}
					j++;
				}
			}
			i++;
		}
		int count = 0;
		for (Boolean bool : sameSameVisit) {
			if (bool == true)
				count++;
		}

		return count;
	}
}
