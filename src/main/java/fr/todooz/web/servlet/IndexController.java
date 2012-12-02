package fr.todooz.web.servlet;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class IndexController {

	@Inject
	private TagCloudService tagCloudService;

	@Inject
	private TaskService taskService;
	
	@ModelAttribute("tagCloud")
	public TagCloud tagCloud() {
	    return tagCloudService.buildTagCloud();
	}

	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("tasks", taskService.findAll());	
		return "index";
	}

	@RequestMapping("/search")
	public String search(String query, Model model) {
		model.addAttribute("tasks", taskService.findByQuery(query));
		return "index";
	}

	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Model model) {
		model.addAttribute("tasks", taskService.findByTag(tag));
		return "index";
	}

	@RequestMapping("/today")
	public String today(Model model) {
		model.addAttribute("tasks", taskService.findByInterval(todayInterval()));
		return "index";
	}

	@RequestMapping("/tomorrow")
	public String tomorrow(Model model) {
		model.addAttribute("tasks",
				taskService.findByInterval(tomorrowInterval()));
		return "index";
	}

	private Interval todayInterval() {
		DateMidnight today = new DateMidnight();

		return new Interval(today, today.plusDays(1));
	}

	private Interval tomorrowInterval() {
		DateTime tomorrow = DateTime.now().plusDays(1);
		return new Interval(tomorrow, tomorrow.plusDays(1));
	}

	@PostConstruct
	public void bootstrap() {

		if (taskService.count() == 0) {
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