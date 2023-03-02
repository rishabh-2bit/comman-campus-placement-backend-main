package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.DtoToInsertPlacementDetails;
import com.app.dto.SendPlacementDetailsDto;
import com.app.dto.SuccessMessageDto;
import com.app.pojos.Credential;
import com.app.pojos.PlacementDetails;
import com.app.pojos.Project;
import com.app.pojos.Question;
import com.app.pojos.Student;
import com.app.pojos.StudentResume;

public interface IStudentService {

	Student studentRegistration(int year, String batch, String courseName, Student student);

	int studentCredential(int sid, Credential credential);

	int studentResume(int sid, MultipartFile studentResume) throws IOException;

	int studentPhoto(int sid, MultipartFile studentPhoto) throws IOException;

	int studentProject(int sid, Project project);
	// -------------------------------------------------------------

	public SuccessMessageDto  studentPlacement(int sid, DtoToInsertPlacementDetails placementDto);

	public List<Student> allStudentInParticularCourse();

	public List<Project> getAllProject(int sid);

	public StudentResume downloadResume(int sid);

    public	List<SendPlacementDetailsDto> getAllPlacementDetails(int sid);

	public   List<Question> getAllQuestion();

	//------------------------------------------------------------

	// all fetching method;
	Student validateLogin(Credential cred);
	
	 public String registerTest(Student student);

}
