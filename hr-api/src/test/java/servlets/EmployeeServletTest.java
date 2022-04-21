//package servlets;
//
//import com.digdes.controllers.EmployeeApiController;
//import com.digdes.dto.EmployeeDto;
//import com.digdes.dto.EmployeeResponseDto;
//import com.digdes.models.EmployeeGender;
//import com.digdes.servlets.EmployeeServlet;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.*;
//import java.sql.Date;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class EmployeeServletTest {
//
////    private AutoCloseable closeable;
////    EmployeeServlet servlet;
////
////    @Mock
////    EmployeeApiController controller;
////    @Mock
////    private HttpServletRequest request;
////    @Mock
////    private HttpServletResponse response;
////
////
////    @BeforeEach
////    public void setUp() throws Exception {
////        closeable = MockitoAnnotations.openMocks(this);
////        servlet = new EmployeeServlet(controller);
////    }
////
////    @AfterEach
////    public void releaseMocks() throws Exception {
////        closeable.close();
////    }
////
////    @Test
////    void test_doGet_notEmptyBody() throws IOException, ServletException {
////        String reqBody = "{\"id\":1}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        servlet.doGet(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doGet_nullId() throws ServletException, IOException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", null);
////        when(request.getParameter("id")).thenReturn(null);
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        servlet.doGet(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doGet_nullStringId() throws ServletException, IOException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"null"});
////        when(request.getParameter("id")).thenReturn("null");
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        servlet.doGet(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doGet_All() throws IOException, ServletException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        List<EmployeeResponseDto> employees = new ArrayList<>();
////        EmployeeResponseDto employee1 =
////                new EmployeeResponseDto(1L, "David", "Jones", null, "example1@example.ru",
////                        EmployeeGender.MALE, new Date(121, 3, 19), "It Service", "Junior java");
////        EmployeeResponseDto employee2 = new EmployeeResponseDto(2L, "Ivan", "Ivanov", "Ivanovich", "example@example.ru",
////                EmployeeGender.MALE, new Date(100, 0, 1), "It Service", "Intern java");
////        employees.add(employee1);
////        employees.add(employee2);
////        when(controller.getAll()).thenReturn(employees);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doGet(request, response);
////        String result = sw.getBuffer().toString().trim();
////        String json =
////                "[{\"id\":1,\"firstName\":\"David\",\"lastName\":\"Jones\",\"patronymic\":null," +
////                        "\"email\":\"example1@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"апр. 19, 2021\",\"departmentName\":\"It Service\",\"positionName\":\"Junior java\"}," +
////                "{\"id\":2,\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\",\"patronymic\":\"Ivanovich\"," +
////                "\"email\":\"example@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"янв. 1, 2000\",\"departmentName\":\"It Service\",\"positionName\":\"Intern java\"}]";
////        assertEquals(json, result);
////    }
////
////    @Test
////    void test_doGet_byId_existingEmployee() throws IOException, ServletException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"1"});
////        when(request.getParameter("id")).thenReturn("1");
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        EmployeeResponseDto employee1 =
////                new EmployeeResponseDto(1L, "David", "Jones", null, "example1@example.ru",
////                        EmployeeGender.MALE, new Date(121, 3, 19), "It Service", "Junior java");
////        when(controller.get(1L)).thenReturn(Optional.of(employee1));
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doGet(request, response);
////        String result = sw.getBuffer().toString().trim();
////        String json = "{\"id\":1,\"firstName\":\"David\",\"lastName\":\"Jones\",\"patronymic\":null," +
////                "\"email\":\"example1@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"апр. 19, 2021\",\"departmentName\":\"It Service\",\"positionName\":\"Junior java\"}";
////        assertEquals(result, json);
////    }
////
////    @Test
////    void test_doGet_byId_nonExistentEmployee() throws IOException, ServletException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"100"});
////        when(request.getParameter("id")).thenReturn("100");
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doGet(request, response);
////        String result = sw.getBuffer().toString().trim();
////        String json = "";
////        assertEquals(result, json);
////    }
////
////    @Test
////    void test_doPost_withParameters() throws ServletException, IOException {
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"1"});
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        servlet.doPost(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPost_emptyBody() throws IOException, ServletException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        servlet.doPost(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPost_nullOperation() throws IOException, ServletException {
////        String reqBody = "{\"operation\":null,\"id\":null,\"name\":\"null\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("POST");
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doPost(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPost_createNonExistentEmployee() throws IOException, ServletException {
////        String reqBody = "{\"operation\":\"create\",\"id\":1,\"firstName\":\"David\",\"lastName\":\"Jones2\",\"patronymic\":null," +
////                "\"email\":\"example2@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"апр. 19, 2021\",\"departmentId\":2,\"positionId\":1}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("POST");
////        EmployeeDto employee1 =
////                new EmployeeDto(1L, "David", "Jones2", null, "example2@example.ru",
////                        EmployeeGender.MALE, new Date(121, 3, 19), 2L, 1L);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        when(controller.create(employee1)).thenReturn(true);
////        servlet.doPost(request, response);
////        String result = sw.getBuffer().toString().trim();
////        assertEquals("true",result);
////    }
////
////    @Test
////    void test_doPost_createExistingEmployee() throws IOException, ServletException {
////        String reqBody = "{\"operation\":\"create\",\"id\":1,\"firstName\":\"David\",\"lastName\":\"Jones\",\"patronymic\":null," +
////                "\"email\":\"example1@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"апр. 19, 2021\",\"departmentId\":2,\"positionId\":1}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("POST");
////        EmployeeDto employee1 =
////                new EmployeeDto(1L, "David", "Jones", null, "example1@example.ru",
////                        EmployeeGender.MALE, new Date(121, 3, 19), 2L, 1L);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        when(controller.create(employee1)).thenReturn(false);
////        servlet.doPost(request, response);
////        String result = sw.getBuffer().toString().trim();
////        verify(response, times(0)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////        assertEquals("false",result);
////    }
//
////    @Test
////    void test_doPost_findExistingEmployee() throws IOException, ServletException {
////        String reqBody = "{\"operation\":\"create\",\"id\":1,\"firstName\":\"David\",\"lastName\":\"Jones\",\"patronymic\":null," +
////                "\"email\":\"example1@example.ru\",\"gender\":\"MALE\",\"birthDate\":\"апр. 19, 2021\",\"departmentId\":2,\"positionId\":1}";;
////        PositionDto position = new PositionDto(null, "Junior Java");
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("POST");
////        when(controller.find(position)).thenReturn(new ArrayList<>());
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doPost(request, response);
////        String result = sw.getBuffer().toString().trim();
////        String json = "[]";
////        assertEquals(json, result);
////    }
////
////    @Test
////    void test_doPut_withParameters() throws ServletException, IOException {
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"1"});
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        servlet.doPut(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPut_emptyBody() throws IOException, ServletException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        servlet.doPut(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPut_positionWithNullFields() throws IOException, ServletException {
////        String reqBody = "{\"id\":null,\"name\":\"null\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        when(request.getMethod()).thenReturn("PUT");
////        servlet.doPut(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doPut_existingPosition() throws IOException, ServletException {
////        String reqBody = "{\"id\":1,\"name\":\"Senior java\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("PUT");
////        when(controller.update(new PositionDto(1L, "Senior java"))).thenReturn(true);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doPut(request, response);
////        String result = sw.getBuffer().toString().trim();
////        assertEquals("true",result);
////    }
////
////    @Test
////    void test_doPut_nonExistentPosition() throws IOException, ServletException {
////        String reqBody = "{\"id\":100,\"name\":\"Senior java\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("PUT");
////        when(controller.update(new PositionDto(100L, "Senior java"))).thenReturn(false);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doPut(request, response);
////        String result = sw.getBuffer().toString().trim();
////        assertEquals("false",result);
////    }
////
////
////    @Test
////    void test_doDelete_withParameters() throws ServletException, IOException {
////        Map<String, String[]> parameterMap = new HashMap<>();
////        parameterMap.put("id", new String[]{"1"});
////        when(request.getParameterMap()).thenReturn(parameterMap);
////        servlet.doDelete(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doDelete_emptyBody() throws ServletException, IOException {
////        String reqBody = "";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        servlet.doDelete(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doDelete_positionWithNullFields() throws IOException, ServletException {
////        String reqBody = "{\"id\":null,\"name\":\"null\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("DELETE");
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doDelete(request, response);
////        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
////    }
////
////    @Test
////    void test_doDelete_existingPosition() throws IOException, ServletException {
////        String reqBody = "{\"id\":1,\"name\":\"Senior java\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("DELETE");
////        when(controller.delete(new PositionDto(1L, "Senior java"))).thenReturn(true);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doDelete(request, response);
////        String result = sw.getBuffer().toString().trim();
////        assertEquals("true",result);
////    }
////
////    @Test
////    void test_doDelete_nonExistentPosition() throws IOException, ServletException {
////        String reqBody = "{\"id\":100,\"name\":\"Senior java\"}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(reqBody)));
////        when(request.getMethod()).thenReturn("DELETE");
////        when(controller.delete(new PositionDto(100L, "Senior java"))).thenReturn(false);
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        when(response.getWriter()).thenReturn(pw);
////        servlet.doDelete(request, response);
////        String result = sw.getBuffer().toString().trim();
////        assertEquals("false",result);
////    }
//}