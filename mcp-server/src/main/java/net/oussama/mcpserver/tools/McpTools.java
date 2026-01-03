package net.oussama.mcpserver.tools;

import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class McpTools {
    @McpTool(name = "findgetEmploye",description = "Get infomramtion abour a given employee")
    public Employee getEmployee(@McpArg(description = "The employee name ") String name){
        return new Employee(name,12300,4);
    }
    @McpTool(description = "get all employee")
    public List<Employee> getEmployees(){
        return List.of(new Employee("Hasan",12300,4),
                new Employee("Mohamed",34000,1),
                new Employee("Imane",23000,3)

        );
    }
}
record Employee(String name,double salary,int seniority){

}