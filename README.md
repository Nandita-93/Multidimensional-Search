# Multidimensional-Search
Implemented a search engine that retrieves products from a large database based on multiple attributes, by organizing data using appropriate data structures like HashMaps and TreeSets for different combination of attributes

Driver Files:

LP4Driver.java

Source Files:

MDS.java
Timer.java

Multi-dimensional search: Consider the web site of a seller like Amazon. They carry tens of thousands of products, and each product has many attributes (Name, Size, Description, Keywords, Manufacturer, Price, etc.). The search engine allows users to specify attributes of products that they are seeking, and shows products that have most of those attributes. To make search efficient, the data is organized using appropriate data structures, such as balanced trees, and hashing. But, if products are organized by Name, how can search by price implemented efficiently? The solution, called indexing in databases, is to create a new set of references to the objects for each search field, and organize them to implement search operations on that field efficiently. As the objects change, these access structures have to be kept consistent.
In this project, each object has 3 attributes: id (long int), description (one or more long ints), and price (dollars and cents). The following operations are supported:

Insert(id,price,description): insert a new item. If an entry with the same id already exists, its description and price are replaced by the new values. If description is empty, then just the price is updated. Returns 1 if the item is new, and 0 otherwise.

Find(id): return price of item with given id (or 0, if not found).

Delete(id): delete item from storage. Returns the sum of the long ints that are in the description of the item deleted (or 0, if such an id did not exist).

FindMinPrice(n): given a long int n, find items whose description contains n (exact match with one of the long ints in the item's description), and return lowest price of those items.

FindMaxPrice(n): given a long int n, find items whose description contains n, and return highest price of those items.

FindPriceRange(n,low,high): given a long int n, find the number of items whose description contains n, and their prices fall within the given range, [low, high].

PriceHike(l,h,r): increase the price of every product, whose id is in the range [l,h], by r% Discard any fractional pennies in the new prices of items. Returns the sum of the net increases of the prices.

Range(low, high): number of items whose price is at least "low" and at most "high".

SameSame(): Find the number of items that satisfy all of the following conditions:
The description of the item contains 8 or more numbers, and,
The description of the item contains exactly the same set of numbers as another item.

Insert 22 19.97 475 1238 9742 0
// New item with id=22, price="$19.97", description="475 1238 9742"
// Return: 1

Insert 12 96.92 44 109 0
// Second item with id=12, price="96.92", description="44 109"
// Return: 1

Insert 37 47.44 109 475 694 88 0
// Another item with id=37, price="47.44", description="109 475 694 88"
// Return: 1

PriceHike 10 22 10
// 10% price increase for id=12 and id=22
// New price of 12: 106.61, Old price = 96.92.  Net increase = 9.69
// New price of 22: 21.96.  Old price = 19.97.  Net increase = 1.99
// Return: 11.68  (sum of 9.69 and 1.99)

FindMaxPrice 475		
// Return: 47.44 (id of items considered: 22, 37)

Delete 37
// Return: 1366 (=109+475+694+88)

FindMaxPrice 475		
// Return: 21.96 (id of items considered: 22)

End
// Lines after "End" are not processed.


Output specification

The output is a single number, which is the sum of the following values obtained by the algorithm as it processes the input.
Output:
1450.08


