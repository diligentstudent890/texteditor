# CSC 207: Text Editor

**Author**: Brian He

## Resources Used

+ _(TODO: fill me in)_
+ ...My IDE: NetBeans 24
+ ...JDK23
+ ...Talked with Prof.Osera
+ ... https://osera.cs.grinnell.edu/ttap/data-structures-labs/text-editor.html

## Changelog

_(TODO: fill me in with a log of your committed changes)_

commit 0c66ce20b975115270086ba4a492943ed68f8c55 (HEAD -> main, origin/main, origin/HEAD)
Author: Brian He <brianhe@Brians-MacBook-Air.local>
Date:   Sun Feb 23 23:30:07 2025 -0600

    Finalized everything and reviewed tests over the weekend

commit 4278b3bcf20042695351140ebc7f8f6f1ea2c0db
Author: Brian He <brianhe@Brians-MacBook-Air.local>
Date:   Fri Feb 21 01:19:01 2025 -0600

    Polished everything I had atm

commit 18e928e642c15ccd4917fd0e506184142cb4f1fb
Author: Brian He <brianhe@Brians-MacBook-Air.local>
Date:   Thu Feb 20 01:16:50 2025 -0600

    Forgot to commit - Finished part2 and part3

commit 494a3329b2e826030884cd2f530b2762e4642afb
Author: Brian He <brianhe@Brians-MacBook-Air.local>
Date:   Wed Feb 19 00:19:40 2025 -0600

    Finished part1

commit 32a90495f40bd92ce905d4d78fbdab4dbaa6d5f9
Author: Peter-Michael Osera <osera@cs.grinnell.edu>
Date:   Thu Feb 13 12:40:05 2025 -0600

    Project files

commit 02dc92144ecc088bcefb4a9798df0934efe300c1
Author: Peter-Michael Osera <osera@cs.grinnell.edu>
Date:   Thu Feb 13 12:39:53 2025 -0600

    initial commit

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