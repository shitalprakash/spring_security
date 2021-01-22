package com.bootstrapping.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
	
	List<Student> student_list=Arrays.asList(new Student(1,"qwe"),new Student(2,"asd"),new Student(3,"zxc"));
	@GetMapping(path="{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		return student_list.stream().filter(s->studentId.equals(s.getSid())).findFirst().
				orElseThrow(()->new IllegalStateException("no such student was found"));
		
	}
}
