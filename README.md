# CSC 207: Text Editor

**Author**: Brian He

## Resources Used

+ _(TODO: fill me in)_
+ ...My IDE: NetBeans 24
+ ...JDK23
+ ...Talked with Prof.Osera

## Changelog

_(TODO: fill me in with a log of your committed changes)_

## Analysis of the insert Method in SimpleStringBuffer
The insert method in SimpleStringBuffer takes a single character ch as input and
inserts it into the backing String at the current cursor position. 
The key operations performed by this method include:
	1.	Substring Extraction:
		text.substring(0, cursor) extracts the left portion of the string, which requires copying cursor characters.
		text.substring(cursor) extracts the right portion, copying (n - cursor) characters, where n is the current size of the buffer.
	2.	String Concatenation:
		The method concatenates the left substring, the character ch, and the right substring. This concatenation operation involves copying all characters from both substrings and the inserted character.

Mathematical Model

Let:
	n  be the total number of characters in the buffer before insertion, and
	i  be the cursor position (with  0 \leq i \leq n ).

The time cost can be modeled as:
	Left substring extraction:  O(i) 
	Right substring extraction:  O(n - i) 
	Concatenation:  O(i + 1 + (n - i)) = O(n + 1)  which simplifies to  O(n) 

Thus, the overall runtime  T(n)  for a single insert operation is:


T(n) = O(i) + O(n - i) + O(n) = O(n)


Big-O Characterization

The insert method is  O(n)  in the worst case.

My Justification

Java strings are immutable, meaning that every time an operation such as substring 
or concatenation is performed, a new string must be created, and all the characters 
must be copied into the new string. In our insert method, 
even though we are only inserting a single character, 
we perform two substring operations that traverse portions of
the existing string and then a concatenation that involves copying
the entire string (with the inserted character). These operations
are all linear with respect to the length of the current string,
leading to an overall runtime of  O(n) . For text editors that perform many insertions,
this approach may be highly inefficient, which motivates the use of alternative 
data structures (like linked lists or gap buffers) that can reduce the cost of such operations.