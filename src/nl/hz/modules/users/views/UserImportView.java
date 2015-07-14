package nl.hz.modules.users.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import nl.hz.bict.sor21314.team1.administration.AdministrationView;
import nl.hz.modules.users.controllers.UserImportController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;

public class UserImportView extends AdministrationView implements View {
	private static final long serialVersionUID = -2117943996432326439L;

	public static final String NAME_NAV = "administration/users/import";
	public static final String NAME_VIEW = "Import CSV";

	private Label info;
	private Upload upload;
	private File tempFile;
	private VerticalLayout pageContent;
	private VerticalLayout error;
	
	private UserImportController controller;
	
	public UserImportView(){
		super();		
	}
	
	@Override
	protected void initContentLayout() {
		initTitle();
		controller = new UserImportController();
		pageContent = new VerticalLayout();
		error = new VerticalLayout();
		
		setContentLayout(pageContent);
		
		initLayout();
	}
	
	private void initTitle() {
		setViewTitle(NAME_VIEW);
	}
	
	private void initLayout() {
		info = new Label(
				"Choose a csv file to automatically upload some users");
		upload = new Upload("Upload it here", new Upload.Receiver() {
			private static final long serialVersionUID = 1734928525232403218L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				try {
					tempFile = File.createTempFile("temp", ".csv");
					FileOutputStream output = new FileOutputStream(tempFile);
					return output;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}

		});
		upload.addSucceededListener(new Upload.SucceededListener() {
			private static final long serialVersionUID = 280658765918766954L;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				controller.readCsv(tempFile);
				if (controller.getError() != null) {
					Label label = new Label(
							"CSV uploaded and inserted with the following errors:");
					error.addComponent(label);
					String[] line = controller.getError().split(",");
					for (int i = 0; i < line.length; i++) {
						Label errorLabel = new Label(line[i].toString());
						pageContent.addComponent(errorLabel);
					}
				} else {
					Notification.show("Users succesfully added");
				}
			}
		});
		upload.setButtonCaption("Upload Now");

		pageContent.addComponent(info);
		pageContent.addComponent(upload);
		pageContent.addComponent(error);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event); // User check
	}
	
}
