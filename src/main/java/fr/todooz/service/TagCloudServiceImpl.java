package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import fr.todooz.domain.Task;
import fr.todooz.util.TagCloud;

public class TagCloudServiceImpl implements TagCloudService {
	
	   @Inject
	   private TaskService taskService;

	
	public TagCloud buildTagCloud(){
		List<Task> tasks= taskService.findAll();
		TagCloud tagCloud = new TagCloud();
		for(Task task : tasks){
			String[] tab= task.getTags().split(",");
			tagCloud.add(tab);
		}
		return tagCloud;	
	}
}
