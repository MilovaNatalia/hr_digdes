package servlets;

//import com.digdes.controllers.PositionApiController;
//import com.digdes.dto.PositionDto;
//import com.digdes.servlets.PositionServlet;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

class PositionServletTest {

//    private AutoCloseable closeable;
//    PositionServlet servlet;
//
//    @Spy
//    PositionApiController controller;
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        closeable = MockitoAnnotations.openMocks(this);
//        servlet = new PositionServlet(controller);
//    }
//
//    @AfterEach
//    public void releaseMocks() throws Exception {
//        closeable.close();
//    }
//
//    @Test
//    void test_doGet_notEmptyBody() throws IOException, ServletException {
//        String reqBody = "{\"id\":1,\"name\":\"Junior Java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        servlet.doGet(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doGet_nullId() throws ServletException, IOException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", null);
//        when(request.getParameter("id")).thenReturn(null);
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        servlet.doGet(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doGet_nullStringId() throws ServletException, IOException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"null"});
//        when(request.getParameter("id")).thenReturn("null");
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        servlet.doGet(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doGet_All() throws IOException, ServletException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        List<PositionDto> positions = new ArrayList<>();
//        positions.add(new PositionDto(1L, "Junior Java"));
//        positions.add(new PositionDto(2L, "Intern Java"));
//        when(controller.getAll()).thenReturn(positions);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doGet(request, response);
//        String result = sw.getBuffer().toString().trim();
//        String json = "[{\"id\":1,\"name\":\"Junior Java\"}," +
//                "{\"id\":2,\"name\":\"Intern Java\"}]";
//        assertEquals(json, result);
//    }
//
//    @Test
//    void test_doGet_byId_existingPosition() throws IOException, ServletException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"1"});
//        when(request.getParameter("id")).thenReturn("1");
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(controller.get(1L)).thenReturn(Optional.of(new PositionDto(1L, "Junior Java")));
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doGet(request, response);
//        String result = sw.getBuffer().toString().trim();
//        String json = "{\"id\":1,\"name\":\"Junior Java\"}";
//        assertEquals(result, json);
//    }
//
//    @Test
//    void test_doGet_byId_nonExistentPosition() throws IOException, ServletException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"100"});
//        when(request.getParameter("id")).thenReturn("100");
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doGet(request, response);
//        String result = sw.getBuffer().toString().trim();
//        String json = "";
//        assertEquals(result, json);
//    }
//
//    @Test
//    void test_doPost_withParameters() throws ServletException, IOException {
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"1"});
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        servlet.doPost(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPost_emptyBody() throws IOException, ServletException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        servlet.doPost(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPost_nullOperation() throws IOException, ServletException {
//        String reqBody = "{\"operation\":null,\"id\":null,\"name\":\"null\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("POST");
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPost(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPost_createNonExistentPosition() throws IOException, ServletException {
//        String reqBody = "{\"operation\":create,\"id\":null,\"name\":\"Booker\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("POST");
//        when(controller.create(new PositionDto(null, "Booker"))).thenReturn(true);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPost(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("true",result);
//    }
//
//    @Test
//    void test_doPost_createExistingPosition() throws IOException, ServletException {
//        String reqBody = "{\"operation\":create,\"id\":null,\"name\":\"Junior java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("POST");
//        when(controller.create(new PositionDto(null, "Junior java"))).thenReturn(true);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPost(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("false",result);
//    }
//
//    @Test
//    void test_doPost_findExistingPosition() throws IOException, ServletException {
//        String reqBody = "{\"operation\":find,\"id\":null,\"name\":\"Junior Java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("POST");
//        when(controller.find(null)).thenReturn(new ArrayList<>());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPost(request, response);
//        String result = sw.getBuffer().toString().trim();
//        String json = "[]";
//        assertEquals(json, result);
//    }
//
//    @Test
//    void test_doPut_withParameters() throws ServletException, IOException {
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"1"});
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        servlet.doPut(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPut_emptyBody() throws IOException, ServletException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        servlet.doPut(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPut_positionWithNullFields() throws IOException, ServletException {
//        String reqBody = "{\"id\":null,\"name\":\"null\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        when(request.getMethod()).thenReturn("PUT");
//        servlet.doPut(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doPut_existingPosition() throws IOException, ServletException {
//        String reqBody = "{\"id\":1,\"name\":\"Senior java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("PUT");
//        when(controller.update(new PositionDto(1L, "Senior java"))).thenReturn(true);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPut(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("true",result);
//    }
//
//    @Test
//    void test_doPut_nonExistentPosition() throws IOException, ServletException {
//        String reqBody = "{\"id\":100,\"name\":\"Senior java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("PUT");
//        when(controller.update(new PositionDto(100L, "Senior java"))).thenReturn(false);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doPut(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("false",result);
//    }
//
//
//    @Test
//    void test_doDelete_withParameters() throws ServletException, IOException {
//        Map<String, String[]> parameterMap = new HashMap<>();
//        parameterMap.put("id", new String[]{"1"});
//        when(request.getParameterMap()).thenReturn(parameterMap);
//        servlet.doDelete(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doDelete_emptyBody() throws ServletException, IOException {
//        String reqBody = "";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        servlet.doDelete(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doDelete_positionWithNullFields() throws IOException, ServletException {
//        String reqBody = "{\"id\":null,\"name\":\"null\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("DELETE");
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doDelete(request, response);
//        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST);
//    }
//
//    @Test
//    void test_doDelete_existingPosition() throws IOException, ServletException {
//        String reqBody = "{\"id\":1,\"name\":\"Senior java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("DELETE");
//        when(controller.delete(new PositionDto(1L, "Senior java"))).thenReturn(true);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doDelete(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("true",result);
//    }
//
//    @Test
//    void test_doDelete_nonExistentPosition() throws IOException, ServletException {
//        String reqBody = "{\"id\":100,\"name\":\"Senior java\"}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(reqBody)));
//        when(request.getMethod()).thenReturn("DELETE");
//        when(controller.delete(new PositionDto(100L, "Senior java"))).thenReturn(false);
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        when(response.getWriter()).thenReturn(pw);
//        servlet.doDelete(request, response);
//        String result = sw.getBuffer().toString().trim();
//        assertEquals("false",result);
//    }
}