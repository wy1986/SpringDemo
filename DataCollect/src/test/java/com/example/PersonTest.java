package com.example;

import com.example.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    Person person;

    @Autowired
    ApplicationContext con;

    @Test
    public void testImportResource(){
        boolean b = con.containsBean("helloworldService");
        System.out.println(b);
    }

    @Test
    public void printPerson()
    {
        System.out.println(person.toString());
    }
}
