package com.mmit.test;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mmit.jpit.Course;
@TestMethodOrder(OrderAnnotation.class)
class EntityManagerTest {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf=Persistence.createEntityManagerFactory("jpa-em");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em=emf.createEntityManager();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}

	@Test
	@Order(1)
	void test() {
		//Course
		Course course=new Course();
		course.setFees(200000);
		course.setLevel("Basic");
		course.setName("PHP");
		
		//insert to db
		em.getTransaction().begin();
		
		em.persist(course);//manage
//		em.detach(course);
//		course.setLevel("Advance");
		
		em.getTransaction().commit();
		
		//check
//		assertEquals(1,course.getId());
	}
	@Test
	@Order(2)
	void test2() {
		Course c1=em.find(Course.class,1);
		em.getTransaction().begin();
		c1.setLevel("Intermediate");
		c1.setName("Java EE");
		em.merge(c1);
		em.getTransaction().commit();
	}
	@Test
	@Order(3)
	void test3() {
		Course c1=em.find(Course.class, 1);
		em.getTransaction().begin();
		em.remove(c1);
		em.getTransaction().commit();
	}

}
