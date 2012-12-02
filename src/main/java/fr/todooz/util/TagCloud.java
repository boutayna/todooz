package fr.todooz.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

public class TagCloud {

	private Set<String> tags = new HashSet<String>();

	public void add(String... args) {
		if (args != null) {
			for (String arg : args) {
				if(arg != "" && arg != null)
				{tags.add(arg);}
			}
		}
	}

	public boolean contains(String tag) {
		if (tags.contains(tag))
			return true;
		return false;
	}

	public int size() {
		return tags.size();
	}
	
	public void top(int nbrTag){
		List<Object> list = new ArrayList<Object>(tags);
		list=list.subList(0, nbrTag);
		tags = new HashSet(list);

	}

	public void shuffle() {
		List<Object> list = new ArrayList<Object>(tags);
		Collections.shuffle(list);
		tags = new HashSet(list);	
	}

	public Set<String> getTags() {
		return tags;
	}
	
}
