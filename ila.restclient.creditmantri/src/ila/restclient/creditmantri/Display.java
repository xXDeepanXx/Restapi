package ila.restclient.creditmantri;

import java.util.Iterator;
import java.util.List;

public class Display {
public void DisplayAllOffer(List<String> approvedoffer, List<String> unapprovedoffer ) {
	
	Iterator<String> apporvediterator = approvedoffer.iterator();
	Iterator<String> unapporvediterator = unapprovedoffer.iterator();
	System.out.println(String.format("%-50s %-50s", "*------------------------------------------------", "*---------------------------------------------------*"));
	System.out.println(String.format("%-50s %-50s", "|                   Eligible Offer", " |           Not Eligible Offer                      | "));
	System.out.println(String.format("%-50s %-50s", "|------------------------------------------------", "*---------------------------------------------------|"));
	while (apporvediterator.hasNext() || unapporvediterator.hasNext()) 
	{
		String aoffer,uoffer;
		if (apporvediterator.hasNext())
			{ aoffer = apporvediterator.next();}
		else
			{ aoffer = "";}
		if (unapporvediterator.hasNext())
			{ uoffer = unapporvediterator.next();}
		else
			{ uoffer = "";}
	System.out.println(String.format("%-50s %-50s",aoffer,uoffer));
	}
	
} 
}
