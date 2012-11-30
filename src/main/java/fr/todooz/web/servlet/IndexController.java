package fr.todooz.web.servlet;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.DummyData;
import fr.todooz.domain.Task;
import fr.todooz.service.TaskService;

@Controller
public class IndexController {

	@Inject
	private TaskService taskService;

	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("tasks", taskService.findAll());

		return "index";
	}
	
	@PostConstruct
	public void bootstrap() {
		
		if(taskService.count() == 0){
		Task task = new Task();

	    task.setDate(new Date());
	    task.setTitle("Read Effective Java");
	    task.setText("Tache 1");
	    task.setTags("java,java");
	    
	    Task task1 = new Task();

	    task1.setDate(new Date());
	    task1.setTitle("Read Effective Java");
	    task1.setText("Tache 2");
	    task1.setTags("java,java");

	    Task task2 = new Task();

	    task2.setDate(new Date());
	    task2.setTitle("Read Effective Java");
	    task2.setText("Tache 3 ");
	    task2.setTags("java,java");
	    
	    taskService.save(task);
	    taskService.save(task1);
	    taskService.save(task2);
	    
		}
	}

}