package com.app.dao;

import static com.app.pojos.Batch.JAN;
import static com.app.pojos.CourseName.DAC;
import static com.app.pojos.CourseName.DAI;
import static com.app.pojos.CourseName.DASSD;
import static com.app.pojos.CourseName.DBDA;
import static com.app.pojos.CourseName.DESD;
import static com.app.pojos.CourseName.DGI;
import static com.app.pojos.CourseName.DIOT;
import static com.app.pojos.CourseName.DITISS;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.pojos.Course;

@SpringBootTest
public class CourseDaoTest {
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Test
	public void insertCourse() {
		List<Course> course=Arrays.asList(new Course(DAC,JAN, 2022),new Course(DBDA,JAN,2022),new Course(DESD,JAN,2022),
				new Course(DIOT,JAN,2022),new Course(DASSD,JAN,2022),new Course(DAI,JAN,2022),new Course(DGI,JAN,2022),
				new Course(DITISS,JAN,2022)
				);
		List<Course> list = courseRepo.saveAll(course);
		assert(list.size()==course.size());
	}
}
