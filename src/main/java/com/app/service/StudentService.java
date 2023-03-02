package com.app.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exception.CourseNotFoundException;
import com.app.custom_exception.InvalidCredentialException;
import com.app.dao.CompanyRepository;
import com.app.dao.CourseRepository;
import com.app.dao.CredentialRepository;
import com.app.dao.StudentRepository;
import com.app.dto.DtoToInsertPlacementDetails;
import com.app.dto.SendPlacementDetailsDto;
import com.app.dto.SuccessMessageDto;
import com.app.pojos.Batch;
import com.app.pojos.Course;
import com.app.pojos.CourseName;
import com.app.pojos.Credential;
import com.app.pojos.PlacementDetails;
import com.app.pojos.Project;
import com.app.pojos.Question;
import com.app.pojos.Round;
import com.app.pojos.SelectionStatus;
import com.app.pojos.Student;
import com.app.pojos.StudentPhoto;
import com.app.pojos.StudentResume;

@Service
@Transactional
public class StudentService implements IStudentService{
	 
	@Autowired
	private StudentRepository  studentRepo;
	
	@Autowired
	 private CourseRepository courseRepo;
	
	@Autowired 
	private CredentialRepository credRepo;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	// student Registraton
	@Override
	public Student studentRegistration(int year,String batch, String courseName,Student student) {
	     Batch batch1=Batch.valueOf(batch.toUpperCase());
	     CourseName courseName1=CourseName.valueOf(courseName.toUpperCase());
	     Optional<Course> courseOptional = courseRepo.findByCourseNameAndBatchAndYear(courseName1, batch1, year);
	     Course course=  courseOptional.orElseThrow(()-> new CourseNotFoundException("can not the find the course !!"));
	     course.getStudents().add(student);
	     student.setCourse(course);
	    return student;
	}

	@Override
	 public String registerTest(Student student) {
		 Optional<Course> courseOptional = courseRepo.findByCourseNameAndBatchAndYear(student.getCourse().getCourseName(),
				 student.getCourse().getBatch(), student.getCourse().getYear());
	     Course course=  courseOptional.orElseThrow(()-> new CourseNotFoundException("can not the find the course !!"));
	     course.getStudents().add(student);
	     student.setCourse(course);
	     return "Hurrah successfully registered";
	 }

	// store Credential
	@Override
	public int studentCredential(int sid,  Credential credential) {
		System.out.println("in service"+sid+credential.getPassword()+credential.getUserName());
		Student student = studentRepo.findById(sid).get();
		student.setCredential(credential);
		studentRepo.save(student);
		return sid;
	}

	// store project details
	@Override
	public int studentProject(int sid,Project project) {
		Student student = studentRepo.findById(sid).get();
		student.getProjects().add(project);
		studentRepo.save(student);
		return sid;
	}

	
	// store student resume
	@Override
	public int studentResume(int sid,MultipartFile studentResume) throws IOException {
		// create resume class instance and set the property by fetching multipart file
		// and then store the resume instance
		// in the database
		StudentResume stdResume=new StudentResume();
		stdResume.setResumeName(studentResume.getOriginalFilename());
		stdResume.setResumeContent(studentResume.getBytes());
		
		Student student = studentRepo.findById(sid).get();
		student.setResume(stdResume);
		studentRepo.save(student);
		return sid;
	}

	// store student photo
	@Override
	public int studentPhoto(int sid,MultipartFile studentPhoto) throws IOException {
		// create Photo class instance and set the property by fetching multipart file
		// and then store the Photo instance
		// in the database
		StudentPhoto stdPhoto =new StudentPhoto();
		stdPhoto.setPhoto(studentPhoto.getBytes());
		
		Student student = studentRepo.findById(sid).get();
		student.setPhoto(stdPhoto);
		studentRepo.save(student);
		return sid;
	}


	// validate Student credential
	@Override
	public Student validateLogin(Credential cred) {
		Credential credential=credRepo.findByUserNameAndPassword(cred.getUserName(),cred.getPassword()).
				orElseThrow(()->new InvalidCredentialException("invalid credential!") );
		
		// fetch the student Record using the credential
		Optional<Student> student = studentRepo.findByCredential(credential);
		student.get().getPhoto().getPhoto();
		
		return student.get();
	}


	@Override
	public SuccessMessageDto studentPlacement(int sid, DtoToInsertPlacementDetails placementDetails) {
		// create the placement details pojo
		PlacementDetails transientPlacementDetais =new PlacementDetails();
		transientPlacementDetais.setIsSelected(SelectionStatus.valueOf(placementDetails.getIsSelected().toUpperCase()));
		transientPlacementDetais.setRound(Round.valueOf(placementDetails.getRound().toUpperCase()));
		transientPlacementDetais.setCompany(companyRepo.findByName(placementDetails.getCompanyName().toUpperCase()));
	
		// insert the RECORD
		Student student = studentRepo.findById(sid).get();
	    student.getPlacementDetails().add(transientPlacementDetais);
	    studentRepo.save(student);
	    
		return new SuccessMessageDto("student placement datials is added successfully");
	}


	@Override
	public List<Student> allStudentInParticularCourse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Project> getAllProject(int sid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public StudentResume downloadResume(int sid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SendPlacementDetailsDto> getAllPlacementDetails(int sid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Question> getAllQuestion() {
		// TODO Auto-generated method stub
		return null;
	}
}
