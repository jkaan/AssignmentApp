package nl.hz.modules.exams.views;

import java.util.List;

import nl.hz.bict.sor21314.team1.entities.RandomExam;
import nl.hz.bict.sor21314.team1.services.RandomExamService;
import nl.hz.bict.sor21314.team1.student.StudentView;
import nl.hz.modules.exams.controllers.ChooseExamController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class represents the view where a student can choose an exam he/she
 * would like to take
 * 
 * @author Joey <joey94jk@gmail.com>
 * 
 */
public class ChooseExamView extends StudentView implements View {
	private static final long serialVersionUID = -2396048198440063866L;
	
	public static final String NAME_NAV = "student/exams";
	public static final String NAME_VIEW = "Exams";
	
	private ChooseExamController chooseExamController;
	private RandomExamService randomExamService;
	
	private HorizontalLayout contentLayout;	

	private Table examTable;
	private Button startButton;

	public ChooseExamView() {
		super();		
	}
	
	protected void initContentLayout() {
		randomExamService = new RandomExamService();
		chooseExamController = new ChooseExamController(this, randomExamService);
		
		contentLayout = new HorizontalLayout();
		contentLayout.setSizeFull();
		
		Layout examList = getExamList();
		Layout sideText = getSideText();
		contentLayout.addComponents(examList, sideText);
		contentLayout.setExpandRatio(examList, 7);
		contentLayout.setExpandRatio(sideText, 3);
		
		setContentLayout(contentLayout);
		setViewTitle(NAME_VIEW);
	}
	
	/**
	 * @return Returns layout component that contains the exam list
	 */
	private Layout getExamList() {
		VerticalLayout layout = new VerticalLayout();
		
		examTable = new Table();
		examTable.setStyleName(Reindeer.TABLE_BORDERLESS);
		examTable.setSizeFull();
		examTable.setPageLength(0);
		examTable.setNullSelectionAllowed(false);
		examTable.setSelectable(true);
		examTable.setImmediate(true);
		
		examTable.addContainerProperty("Exam", String.class, null);		
		
		VerticalLayout buttonContainer = new VerticalLayout();
		buttonContainer.setMargin(new MarginInfo(true, false, true, true));
		buttonContainer.setWidth("100%");
		startButton = new Button("Start Exam", chooseExamController);
		buttonContainer.addComponent(startButton);
		buttonContainer.setComponentAlignment(startButton, Alignment.MIDDLE_RIGHT);
		
		layout.addComponents(examTable, buttonContainer);
		
		return layout;
	}
	
	/**
	 * @return Returns layout component that contains the side text
	 */
	private Layout getSideText() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		
		Label title = new Label("Welcome");
		title.setStyleName(Reindeer.LABEL_H2);
		
		Label text = new Label("Choose an exam in the list on the left side of this page. "
				+ "While taking the exam, you don't have to press the back button. You can't navigate back to the previous question. "
				+ "When finished the exam, your result is shown. It's not possible to take the exam again.");
		
		layout.addComponents(title, text);
		
		return layout;
	}
	
	/**
	 * Reload the list
	 */
	private void reload() {
		// Get all the available exams
		List<RandomExam> availableRandomExams = randomExamService
				.getExamsForUser();

		// Add all the random exams to the list to be shown in the GUI
		for (RandomExam exam : availableRandomExams) {
			String examString = "Random Exam: " + exam.getRandomExamId();

			examTable.addItem(new Object[] {
					examString
			}, exam.getRandomExamId());
		}
	}
	
	public Long getSelectedExamId() {
		return (Long) examTable.getValue();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		
		reload();
	}
}
