package com.enrichservice.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.enrichservice.model.Employee;

public class EmployeeItemProcessorTest {

    private static final Long TEST_EMP_ID = 1235L;
    private static final String EMPLOYEES_ENDPOINT_URL = "http://localhost:8080/api/employees/";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeItemProcessor employeeItemProcessor = new EmployeeItemProcessor();

    private Employee testEmployee;
    private Employee employeeBeforeEnrich;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(employeeItemProcessor, "employeeDataUrl", EMPLOYEES_ENDPOINT_URL);
        testEmployee = new Employee(TEST_EMP_ID, "testName", 10000.0);
        employeeBeforeEnrich = new Employee(1l, null, null);
    }

    @Test
    public void testProcess() throws Exception {
        // given
        Employee expectedEmployee = new Employee(TEST_EMP_ID, "testName", 10000.0);
        when(restTemplate.getForObject("http://localhost:8080/api/employees/1", Employee.class, testEmployee.getId()))
                .thenReturn(testEmployee);

        // when
        Employee processedEmployee = employeeItemProcessor.process(employeeBeforeEnrich);

        // then
        assertNotNull(processedEmployee);
        assertEquals(expectedEmployee, processedEmployee);
    }

    @Test(expected = Exception.class)
    public void testProcessEx() throws Exception {
        Employee processedEmployeeEx = employeeItemProcessor.process(employeeBeforeEnrich);

        assertNotNull(processedEmployeeEx);
        assertEquals(employeeBeforeEnrich, processedEmployeeEx);
    }
}
