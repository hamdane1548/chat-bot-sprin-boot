package net.oussama.chatboot.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Aitools {
    @Tool(name = "findgetEmploye",description = "Get infomramtion abour a given employee")
    public Employee getEmployee(@ToolParam(description = "The employee name ") String name){
        return new Employee(name,12300,4);
    }
    @Tool(description = "get all employee")
   public List<Employee> getEmployees(){
        return List.of(new Employee("Hasan",12300,4),
                new Employee("Mohamed",34000,1),
                new Employee("Imane",23000,3)

                );
   }
}
record Employee(String name,double salary,int seniority){

}
