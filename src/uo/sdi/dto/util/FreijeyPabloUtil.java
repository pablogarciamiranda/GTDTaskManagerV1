package uo.sdi.dto.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uo.sdi.dto.Task;

public class FreijeyPabloUtil {
	
	public static void orderAscending (List<Task> param){
		Collections.sort(param, new Comparator<Task>(){
			@Override
			public int compare(Task o1, Task o2) {
				if (o1.getPlanned()==null)
					return 1;
				if (o2.getPlanned()==null)
					return -1;
				return o1.getPlanned().compareTo(o2.getPlanned());
			}
		});
	}
	
	public static void orderDescending (List<Task> param){
		Collections.sort(param, new Comparator<Task>(){
			@Override
			public int compare(Task o1, Task o2) {
				if (o1.getPlanned()==null)
					return 1;
				if (o2.getPlanned()==null)
					return -1;
				return o2.getPlanned().compareTo(o1.getPlanned());
			}
		});
	}

}
