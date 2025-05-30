package com.kapitonau.projectservice.repository;

import com.kapitonau.projectservice.model.EmployeeModel;
import com.kapitonau.projectservice.repository.custom.CustomEmployeeRepository;
import com.kapitonau.projectstudio.projectservice.dto.employee.EmployeeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long>{


    /*@Query("""
            select e from EmployeeModel e
                        left join SpaceEmployeeModel se on se.spaceEmployeeId.employeeId = e.employeeId
                                    where se.spaceEmployeeId.spaceId <> :spaceId or se.spaceEmployeeId.spaceId is null
            """)*/
    @Query(value = """
            select e.*
            from employees e
                     left join space_employees se on se.employee_id = e.employee_id
            where se.space_id <> :spaceId or se.space_id is null;
            """, nativeQuery = true)
    List<EmployeeModel> findAllNotInSpace(Long spaceId);


    @Query(value = """
            select count(e.*)
            from employees e
                     join space_employees se on se.employee_id = e.employee_id
            where se.space_id = :spaceId;
            """, nativeQuery = true)
    Object countEmployeeBySpaceId(Long spaceId);

    @Query(value = """
            select e.*
            from employees e
                     join space_employees se on se.employee_id = e.employee_id
            where se.space_id = :spaceId;
            """, nativeQuery = true)
    List<EmployeeModel> findAllBySpaceId(Long spaceId);
}
