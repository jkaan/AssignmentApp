package nl.hz.modules.users.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import nl.hz.bict.sor21314.team1.services.UserService;

public class UserImportController {
	private UserService addUsersService;
	private String error;
	
	public UserImportController() {
		this.addUsersService = UserService.getInstance();
		error = "";
	}
	
	/**
	 * Read the uploaded CSV file and export to database.
	 * @param tempFile CSV file
	 */
	public void readCsv(File tempFile) {
		if (tempFile.exists()) {
			String splitBy = ";";
			String line = "";

			try {
				FileReader reader = new FileReader(tempFile.getPath());
				BufferedReader br = new BufferedReader(reader);
				int i = 1;
				while ((line = br.readLine()) != null) {
					// Split the string
					String[] cars = line.split(splitBy, -1);
					if (i != 1) {
						if (cars[2].length() == 0 || cars[3].length() == 0) {
							error = error + "Error on line " + i + ",";
						} else {
							addUsersService.insertUser(cars[2], cars[3],
									"student", "random");
						}
					}
					i++;
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return Error that was detected while processing import
	 */
	public String getError() {
		return error;
	}
}
