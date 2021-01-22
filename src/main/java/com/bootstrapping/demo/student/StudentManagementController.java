package com.bootstrapping.demo.student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentManagementController {

	private List<Student> lst=new ArrayList(Arrays.asList(new Student(12,"qwerty"),new Student(23,"chimmi"),new Student(24,"Chnaga")));
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getStudent()
	{
		return lst;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void addStudent(@RequestBody Student student)
	{
		lst.add(student);
		System.out.println("Student added successfully");
	}
	@DeleteMapping(path="{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void removeStudent(@PathVariable ("studentId") Integer studentId)
	{
	
		lst.removeIf(x->studentId.equals(x.getSid()));
		System.out.println("Student deleted successfully");
	}
	@PutMapping(path="studentId")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable Integer studentId,@RequestBody Student student)
	{
		System.out.println("Student upadted successfully");
	}
}
